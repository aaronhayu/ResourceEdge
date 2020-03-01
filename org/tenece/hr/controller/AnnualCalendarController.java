
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

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.tenece.web.common.BaseController;
import org.tenece.web.common.ConfigReader;
import org.tenece.web.common.DateUtility;
import org.tenece.web.data.AnnualCalendar;
import org.tenece.web.data.Company;
import org.tenece.web.data.Employee;
import org.tenece.web.services.AnnualCalendarService;

/**
 *
 * @author jeffry.amachree
 */
public class AnnualCalendarController extends BaseController{
    
    private AnnualCalendarService annualCalendarService;
    
    /** Creates a new instance of DepartmentController */
    public AnnualCalendarController() {
    }
    
    public ModelAndView viewAll(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("annual_cal_view");

        //get list of calendars
        List<AnnualCalendar> list = null;

        if(this.getUserPrincipal(request).getGroupCompanyUser() == 1){
            list = getAnnualCalendarService().findAllAnnualCalendar();
            view.addObject("showCompany", "Y");
        }else{
            Employee employee = this.getEmployeeService().findEmployeeBasicById(getUserPrincipal(request).getEmployeeId());
            list = getAnnualCalendarService().findAllAnnualCalendarByCompany(employee.getCompanyCode());
        }

        view.addObject("result", list);
        return view;
    }
    
    public ModelAndView edit(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("annual_cal_edit");
        String param = request.getParameter("idx");
        if(param == null){
            return viewAll(request, response);
        }
        int id = Integer.parseInt(param);
        AnnualCalendar calendar = getAnnualCalendarService().findAnnualCalendarByID(id);

        view = getAndAppendCompanyToView(view, request);
//        List<Company> companyList = this.getApplicationService().findAllCompany();
//        view.addObject("companyList", companyList);
//
//        if(this.getUserPrincipal(request).getGroupCompanyUser() == 0){
//            Employee employee = this.getEmployeeService().findEmployeeBasicById(getUserPrincipal(request).getEmployeeId());
//            view.addObject("companyCode", employee.getCompanyCode());
//            view.addObject("isAdmin", "0");
//        }else{
//            view.addObject("isAdmin", "1");
//        }

        view.addObject("calendar", calendar);
        return view;
    }
    
    public ModelAndView newRecord(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("annual_cal_edit");

        view = getAndAppendCompanyToView(view, request);

        view.addObject("calendar", new AnnualCalendar());
        return view;
    }
    
    public ModelAndView processRequest(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("message");
        String operation = request.getParameter("txtMode");
        //operation mode: N= New, E=Edit
        if(operation.trim().equals("N")){
            AnnualCalendar calendar = new AnnualCalendar();
            String code = request.getParameter("txtCode");
            String desc = request.getParameter("txtDesc");
            String strStartDate = request.getParameter("txtStartDate");
            String companyCode = request.getParameter("cbCompany");
            
            if(code == null && (code.equals(""))){
                view = new ModelAndView("error");
                view.addObject("message", "Invalid code assigned to calendar");
            }
            Date effectiveDate = new Date();
            try {
                effectiveDate = DateUtility.getDateFromString(strStartDate, ConfigReader.DATE_FORMAT);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }

            calendar.setCode(code);
            calendar.setDescription(desc);
            calendar.setStartDate(effectiveDate);
            calendar.setEndDate(null);
            calendar.setStatus("P");
            calendar.setCompanyCode(companyCode);
            
            boolean saved = annualCalendarService.updateAnnualCalendar(calendar, AnnualCalendarService.MODE_INSERT);
            if(saved == false){
                view = new ModelAndView("error");
                view.addObject("message", "Can not save annual calendar settings. Please try again later");
                return view;
            }
            
            view.addObject("message", "Record Saved Successfully.");
            
        }else if(operation.trim().equals("E")){
            AnnualCalendar calendar = new AnnualCalendar();
            String code = request.getParameter("txtCode");
            String desc = request.getParameter("txtDesc");
            String strStartDate = request.getParameter("txtStartDate");

            String companyCode = request.getParameter("cbCompany");
            String txtId = request.getParameter("txtId");
            
            if(txtId == null || txtId.trim().equals("")){
                view = new ModelAndView("error");
                view.addObject("message","Unable to complete update.");
                return view;
            }

            Date effectiveDate = new Date();
            try {
                effectiveDate = DateUtility.getDateFromString(strStartDate, ConfigReader.DATE_FORMAT);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }

            calendar.setCode(code);
            calendar.setDescription(desc);
            calendar.setStartDate(effectiveDate);
            calendar.setEndDate(null);
            calendar.setStatus("P");
            calendar.setId(Integer.parseInt(txtId));
            calendar.setCompanyCode(companyCode);
            
            boolean saved = annualCalendarService.updateAnnualCalendar(calendar, AnnualCalendarService.MODE_UPDATE);
            if(saved == false){
                view = new ModelAndView("error");
                view.addObject("message", "Can not update annual calendar settings. Please try again later");
                return view;
            }
            
            view.addObject("message", "Record Saved Successfully.");
        }
        return view;
    }

    /**
     * @return the annualCalendarService
     */
    public AnnualCalendarService getAnnualCalendarService() {
        return annualCalendarService;
    }

    /**
     * @param annualCalendarService the annualCalendarService to set
     */
    public void setAnnualCalendarService(AnnualCalendarService annualCalendarService) {
        this.annualCalendarService = annualCalendarService;
    }
    
}
