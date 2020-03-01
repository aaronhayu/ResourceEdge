
/*
 * (c) Copyright 2009 The Tenece Professional Services.
 *
 * Created on 27 May 2009, 13:28
 *
 * ============================================================
 * Project Info:  http://tenece.com
 * Project Lead:  Aaron Osikhena (aaron.osikhena@tenece.com);
 * ============================================================
 *
 *
 * Licensed under the Tenece Professional Services;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.tenece.com/
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * - Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * - Redistribution in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in
 *   the documentation and/or other materials provided with the
 *   distribution.
 *
 * Neither the name of Strategiex. or the names of
 * contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
 *
 * This software is provided "as is," without a warranty of any
 * kind. All express or implied conditions, representations and
 * warranties, including any implied warranty of merchantability,
 * fitness for a particular purpose or non-infringement, are hereby
 * excluded.
 * Tenece and its licensors shall not be liable for any damages
 * suffered by licensee as a result of using, modifying or
 * distributing the software or its derivatives. In no event will Strategiex
 * or its licensors be liable for any lost revenue, profit or data, or
 * for direct, indirect, special, consequential, incidental or
 * punitive damages, however caused and regardless of the theory of
 * liability, arising out of the use of or inability to use software,
 * even if Tenece Professional Services has been advised of the possibility of such damages.
 *
 * You acknowledge that Software is not designed, licensed or intended
 * for use in the design, construction, operation or maintenance of
 * any nuclear facility.
 */

package org.tenece.hr.controller;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.tenece.web.common.BaseController;
import org.tenece.web.common.ConfigReader;
import org.tenece.web.common.DateUtility;
import org.tenece.web.data.Company;
import org.tenece.web.data.FileUpload;
import org.tenece.web.services.ApplicationService;

/**
 *
 * @author jeffry.amachree
 */
public class CompanyController extends BaseController{
    
    private ApplicationService applicationService = new ApplicationService();
    
    /** Creates a new instance of DepartmentController */
    public CompanyController() {
    }

    public ModelAndView viewAllCompany(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("company_view");

        if(this.getUserPrincipal(request).getGroupCompanyUser() == 1){
            view.addObject("result", getApplicationService().findAllCompany());
            view.addObject("enableDelete", "Y");
        }else{
            String companyCode = this.getActiveEmployeeCompanyCode(request);
            List<Company> companyList = new ArrayList<Company>();
            companyList.add(getApplicationService().findCompany(companyCode));
            view.addObject("result", companyList);
            view.addObject("enableDelete", "N");
        }
        return view;
    }

    public ModelAndView newCompany(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("company_edit");
        view.addObject("company_picture", "");

        view.addObject("company", new Company());
        return view;
    }

    public ModelAndView edit(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("company_edit");

        String code = request.getParameter("idx");

        Company company = getApplicationService().findCompany(code);
        if(company == null){
            company = new Company();
        }

        //get company picture when loading data
        try{
            String id = company.getCode();
            String filePath = request.getSession().getServletContext().getRealPath("./upload/" + String.valueOf(id) + "_" + company.getLogoName());
            File file = new File(filePath);
            DataOutputStream dOut = new DataOutputStream(new FileOutputStream(file));
            dOut.write(company.getLogo());
            dOut.flush();
            dOut.close();

            view.addObject("company_picture", String.valueOf(id) + "_" + company.getLogoName());
        }catch(Exception e){
            e.printStackTrace();
            view.addObject("company_picture", "");
        }

        view.addObject("company", company);
        return view;
    }
    
    
    public ModelAndView processRequest(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("message");
        String operation = request.getParameter("txtMode");

        if (!(request instanceof MultipartHttpServletRequest)) {
            try {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Expected multipart request");
            } catch (IOException ex) {
                Logger.getLogger(CompanyController.class.getName()).log(Level.SEVERE, null, ex);
            }
            return new ModelAndView("error");
        }

        boolean changeLogo = false;
        FileUpload fileUpload = new FileUpload();
        try{
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile file = multipartRequest.getFile("txtFile");

            //confirm if file size is valid.
            if(file.getSize() != 0){
                //check validity of file by confirming extention
                String logoFileName = "";
                if((file.getOriginalFilename().endsWith("gif")) || (file.getOriginalFilename().endsWith("jpg")) ||
                        (file.getOriginalFilename().endsWith("png"))){
                    //check content type (if valid)
                    if(!ConfigReader.getAllowedImagesContentType().contains(file.getContentType())){
                        view = new ModelAndView("error");
                        view.addObject("message","Invalid File Type. Only the following file types are allowed: gif, jpg and png");
                        return view;
                    }
                    //build a dynamic file name that will be valid across system
                    logoFileName = "vml_logo_" + DateUtility.getDateAsString(new Date(), "ddMMyyyy_HHmmss") + "." + file.getOriginalFilename().substring(file.getOriginalFilename().length() - 3);
                }else{
                    view = new ModelAndView("error");
                    view.addObject("message","Invalid File Type. Only the following file types are allowed: gif, jpg and png");
                    return view;
                }
                
                //set file name. this name will be used to access the image
                fileUpload.setFileName(logoFileName);
                try {
                    fileUpload.setBytes(file.getBytes());
                } catch (IOException ex) {
                    throw ex;
                }
                fileUpload.setSize(file.getSize());
                fileUpload.setContentType(file.getContentType());
                fileUpload.setOrginalFileName(file.getOriginalFilename());

                //save file in archive
                File destination = new File("./" + file.getOriginalFilename());
                fileUpload.setAbsolutePath(destination.getAbsolutePath());
                try {
                    file.transferTo(destination);
                } catch (IllegalStateException ex) {
                    ex.printStackTrace();
                    throw ex;
                } catch (IOException ex) {
                    ex.printStackTrace();
                    throw ex;
                }
                changeLogo = true;
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }

        //operation mode: N= New, E=Edit
        if(operation.trim().equals("N")){
            Company company = new Company();
            String code = request.getParameter("txtCode");
            String name = request.getParameter("txtName");
            String legalName = request.getParameter("txtLegalName");
            String address1 = request.getParameter("txtAddress1");
            String address2 = request.getParameter("txtAddress2");
            String city = request.getParameter("txtCity");
            String state = request.getParameter("txtState");
            String country = request.getParameter("txtCountry");
            String phone = request.getParameter("txtPhone");
            String fax = request.getParameter("txtFax");
            String email = request.getParameter("txtEmail");
            String website = request.getParameter("txtWebsite");
            String leave = request.getParameter("txtLeave");
            String hours = request.getParameter("txtHour");
            String useDefaultWorkFlow = request.getParameter("chkWorkflow");

            if(useDefaultWorkFlow == null){
                useDefaultWorkFlow = "0";
            }
            
            if(code == null || code.trim().equals("")){
                view = new ModelAndView("error");
                view.addObject("message", "Company Code must be supplied.");
                return view;
            }

            if(changeLogo == false){
                view = new ModelAndView("error");
                view.addObject("message", "Company Logo must be specified before saving.");
                return view;
            }

            company.setCode(code);
            company.setName(name);
            company.setLegalName(legalName);
            company.setAddress1(address1);
            company.setAddress2(address2);
            company.setCity(city);
            company.setState(state);
            company.setCountry(country);
            company.setPhone(phone);
            company.setFax(fax);
            company.setEmail(email);
            company.setWebsite(website);
            company.setLeaveLimit(Integer.parseInt(leave));
            company.setDailyWorkingHours(Integer.parseInt(hours));
            company.setUseDefaultWorkflow(Integer.parseInt(useDefaultWorkFlow));

            company.setLogo(fileUpload.getBytes());
            company.setLogoName(fileUpload.getFileName());
            
            boolean row = getApplicationService().updateCompany(company, ApplicationService.MODE_INSERT, changeLogo);
            if(row == false){
                view = new ModelAndView("error");
                view.addObject("message", "Company Code must be supplied.");
                return view;
            }
            view.addObject("message", "Record Saved Successfully.");
            
        }else if(operation.trim().equals("E")){
            Company company = new Company();
            String code = request.getParameter("txtCode");
            String name = request.getParameter("txtName");
            String legalName = request.getParameter("txtLegalName");
            String address1 = request.getParameter("txtAddress1");
            String address2 = request.getParameter("txtAddress2");
            String city = request.getParameter("txtCity");
            String state = request.getParameter("txtState");
            String country = request.getParameter("txtCountry");
            String phone = request.getParameter("txtPhone");
            String fax = request.getParameter("txtFax");
            String email = request.getParameter("txtEmail");
            String website = request.getParameter("txtWebsite");
            String leave = request.getParameter("txtLeave");
            String hours = request.getParameter("txtHour");
            String useDefaultWorkFlow = request.getParameter("chkWorkflow");
            if(useDefaultWorkFlow == null){
                useDefaultWorkFlow = "0";
            }

            String hiddenId = request.getParameter("txtId");
            if(!hiddenId.equals(code)){
                view = new ModelAndView("error");
                view.addObject("message", "Existing code for the company does not match with modification code.");
                return view;
            }

            company.setCode(code);
            company.setName(name);
            company.setLegalName(legalName);
            company.setAddress1(address1);
            company.setAddress2(address2);
            company.setCity(city);
            company.setState(state);
            company.setCountry(country);
            company.setPhone(phone);
            company.setFax(fax);
            company.setEmail(email);
            company.setWebsite(website);
            company.setLogo(fileUpload.getBytes());
            company.setLogoName(fileUpload.getFileName());
            company.setLeaveLimit(Integer.parseInt(leave));
            company.setDailyWorkingHours(Integer.parseInt(hours));
            company.setUseDefaultWorkflow(Integer.parseInt(useDefaultWorkFlow));

            boolean saved = getApplicationService().updateCompany(company, ApplicationService.MODE_UPDATE, changeLogo);

            if(saved == false){
                view = new ModelAndView("error");
                view.addObject("message", "Company Code must be supplied.");
                return view;
            }
            view.addObject("message", "Record Saved Successfully.");
        }

        try{
            if(changeLogo == true){
                this.stampCompanyLogoURL(request);
            }
        }catch(Exception ex){

        }

        return view;
    }

    /**
     * @return the applicationService
     */
    public ApplicationService getApplicationService() {
        return applicationService;
    }

    /**
     * @param applicationService the applicationService to set
     */
    public void setApplicationService(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }
    
}
