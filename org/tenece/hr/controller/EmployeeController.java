/*
 * (c) Copyright 2009 The Tenece Professional Services.
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
 * even if Strategiex has been advised of the possibility of such damages.
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
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.tenece.web.common.BaseController;
import org.tenece.web.common.ConfigReader;
import org.tenece.web.common.DateUtility;
import org.tenece.web.data.Company;
import org.tenece.web.data.Employee;
import org.tenece.web.data.EmployeeChildren;
import org.tenece.web.data.EmployeeDependent;
import org.tenece.web.data.EmployeeEducation;
import org.tenece.web.data.EmployeeEmergencyContact;
import org.tenece.web.data.EmployeePicture;
import org.tenece.web.data.EmployeeSalary;
import org.tenece.web.data.EmployeeSkill;
import org.tenece.web.data.EmployeeSpouse;
import org.tenece.web.data.EmployeeSupervisor;
import org.tenece.web.data.EmployeeWorkExperience;
import org.tenece.web.data.FileUpload;
import org.tenece.web.data.SalaryGrade;
import org.tenece.web.data.Skill;
import org.tenece.web.data.TransactionType;
import org.tenece.web.data.Users;
import org.tenece.web.services.EmployeeService;
import org.tenece.web.services.SetupService;

/**
 *
 * @author jeffry.amachree
 */
public class EmployeeController extends BaseController{
    
    private EmployeeService employeeService = new EmployeeService();
    private SetupService setupService = new SetupService();
    /** Creates a new instance of EmployeeController */
    public EmployeeController() {
    }
    
    public ModelAndView viewAllEmployee(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("employee_view");
        
        String cbSearch = request.getParameter("cbSearch");
        String txtSearch = request.getParameter("txtSearch");

        List<Employee> list = null;
        if(cbSearch == null || txtSearch == null){
            //view.addObject("result", getEmployeeService().findAllEmployeeForBasic());
            if(this.getUserPrincipal(request).getGroupCompanyUser() == 1){
                list = getEmployeeService().findAllEmployeeForBasic();
                view.addObject("showCompany", "Y");
            }else{
                Employee employee = this.getEmployeeService().findEmployeeBasicById(getUserPrincipal(request).getEmployeeId());
                list = getEmployeeService().findAllEmployeeForBasicByCompany(employee.getCompanyCode());
            }
        }else{
            //view.addObject("result", getEmployeeService().findAllEmployeeForBasic(cbSearch, txtSearch));
            if(this.getUserPrincipal(request).getGroupCompanyUser() == 1){
                list = getEmployeeService().findAllEmployeeForBasic(cbSearch, txtSearch);
                view.addObject("showCompany", "Y");
            }else{
                Employee employee = this.getEmployeeService().findEmployeeBasicById(getUserPrincipal(request).getEmployeeId());
                list = getEmployeeService().findAllEmployeeForBasicByCompany(employee.getCompanyCode(), cbSearch, txtSearch);
            }
        }

        //get company info
        view = getAndAppendCompanyToView(view, request);

        view.addObject("result", list);
        return view;
    }
    
    public ModelAndView viewEmployee(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("employee_edit");
        
        String strId = request.getParameter("idx");
        if(strId == null || strId.trim().equals("")){
            view = new ModelAndView("error");
            view.addObject("message","Unable to preview employee data");
            return view;
        }
        int id = Integer.parseInt(strId);
        
        view.addObject("dept", setupService.findAllDepartments());
        view.addObject("category", setupService.findAllEmployeeCategory());
        view.addObject("type", setupService.findAllEmployeeTypes());
        view.addObject("jobTitle", setupService.findAllJobTitles());
        view.addObject("salaryGradeList", setupService.findAllSalaryGrade());

        //get employee picture when loading data
        try{
            EmployeePicture picture = getEmployeeService().getEmployeePicture(id);
            String filePath = request.getSession().getServletContext().getRealPath("./upload/" + String.valueOf(id) + "_" + picture.getFileName());
            File file = new File(filePath);
            DataOutputStream dOut = new DataOutputStream(new FileOutputStream(file));
            dOut.write(picture.getPicture());
            dOut.flush();
            dOut.close();
            
            System.out.println("Path...." + file.getAbsolutePath());
            
            view.addObject("emp_picture", String.valueOf(id) + "_" + picture.getFileName());
        }catch(Exception e){
            System.out.println("Employee Picture does not exist.");
            //e.printStackTrace();
        }
        //get company info
        view = getAndAppendCompanyToView(view, request);

        view.addObject("employee", getEmployeeService().findEmployeeBasicById(id));
        return view;
    }

    public ModelAndView viewEmployeeRelatedInfo(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("employee_others_edit");

        String strId = request.getParameter("idx");
        if(strId == null || strId.trim().equals("")){
            view = new ModelAndView("error_mini");
            view.addObject("message","Unable to preview employee data");
            return view;
        }
        int id = Integer.parseInt(strId);

        view.addObject("employee", getEmployeeService().findEmployeeBasicById(id));
        return view;
    }

    public ModelAndView newRecord(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("employee_edit");

        String cbCompany = "";
        if(this.getUserPrincipal(request).getGroupCompanyUser() == 1){
            cbCompany = request.getParameter("cbCompany");
            if(cbCompany == null){
                cbCompany = "";
            }
        }else{
            Employee employee = getEmployeeService().findEmployeeBasicById(
                    getUserPrincipal(request).getEmployeeId());
            cbCompany = employee.getCompanyCode();
        }
        
        //get company info
        view = this.getAndAppendCompanyToView(view, request);
        
        //get all needed objects
        view.addObject("dept", setupService.findAllDepartmentsByCompany(cbCompany));
        view.addObject("category", setupService.findAllEmployeeCategoryByCompany(cbCompany));
        view.addObject("type", setupService.findAllEmployeeTypesByCompany(cbCompany));
        view.addObject("jobTitle", setupService.findAllJobTitlesByCompany(cbCompany));
        view.addObject("salaryGradeList", setupService.findAllSalaryGradeByCompany(cbCompany));
        
        //view.addObject("result", employeeService.findEmployeeBasicById(0));
        Employee employee = new Employee();
        employee.setCompanyCode(cbCompany);
        
        
        view.addObject("employee", employee);
        
        return view;
    }
    
    public ModelAndView contactData(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("employee_contact");
        
        String id = request.getParameter("id");
        Employee employeeContact = getEmployeeService().findEmployeeContactById(Long.parseLong(id));
        
        view.addObject("empid", id);
        view.addObject("contact", employeeContact);
        view.addObject("countries", setupService.findAllCountries());

        return view;
    }
    
    public ModelAndView childrenData(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("employee_children_view");
        
        String id = request.getParameter("id");
        String childId = request.getParameter("idx");
        if(id == null){
            id = "0";
        }
        if(childId == null){
            childId = "0";
        }
        
        //System.out.println(childId + "----ID>>>>>>>.for children: " + id);
        List<EmployeeChildren> employeeChildren = getEmployeeService().findEmployeeChildren(Integer.parseInt(id));
        
        view.addObject("record", getEmployeeService().findEmployeeChild(Integer.parseInt(childId)));
        view.addObject("empid", id);
        view.addObject("result", employeeChildren);
        return view;
    }

    public ModelAndView spouseData(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("employee_spouse_view");

        String id = request.getParameter("id");
        String spouseId = request.getParameter("idx");
        if(id == null){
            id = "0";
        }
        if(spouseId == null){
            spouseId = "0";
        }

        //System.out.println(childId + "----ID>>>>>>>.for spouse: " + id);
        List<EmployeeSpouse> employeeSpouse = getEmployeeService().findAllEmployeeSpouse(Integer.parseInt(id));

        view.addObject("record", getEmployeeService().findEmployeeSpouse(Integer.parseInt(spouseId)));
        view.addObject("empid", id);
        view.addObject("result", employeeSpouse);
        return view;
    }

    public ModelAndView emergencyData(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("employee_emergency_view");
        
        String id = request.getParameter("id");
        String xid = request.getParameter("idx");
        if(id == null){
            id = "0";
        }
        if(xid == null){
            xid = "0";
        }
        
        //System.out.println(xid + "----ID>>>>>>>.for emergency: " + id);
        List<EmployeeEmergencyContact> employeeEmergency = getEmployeeService().findEmployeeEmergencyContacts(Long.parseLong(id));
        
        view.addObject("record", getEmployeeService().findEmergencyContacts(Integer.parseInt(xid)));
        view.addObject("empid", id);
        view.addObject("result", employeeEmergency);
        view.addObject("relationships", setupService.findAllRelationships());

        return view;
    }
    
    public ModelAndView dependentData(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("employee_dependent_view");
        
        String id = request.getParameter("id");
        String childId = request.getParameter("idx");
        if(id == null){
            id = "0";
        }
        if(childId == null){
            childId = "0";
        }
        
        //System.out.println(childId + "----ID>>>>>>>.for dependent: " + id);
        List<EmployeeDependent> employeeDependent = getEmployeeService().findEmployeeDependents(Integer.parseInt(id));
        
        view.addObject("record", getEmployeeService().findEmployeeDependent(Integer.parseInt(childId)));
        view.addObject("empid", id);
        view.addObject("result", employeeDependent);
        view.addObject("relationships", setupService.findAllRelationships());

        return view;
    }
    
    public ModelAndView educationData(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("employee_education_view");
        
        String id = request.getParameter("id");
        String xid = request.getParameter("idx");
        if(id == null){
            id = "0";
        }
        if(xid == null){
            xid = "0";
        }
        
        //System.out.println(xid + "----ID>>>>>>>.for education: " + id);
        List<EmployeeEducation> employeeEducation = getEmployeeService().findEmployeeEducation(Integer.parseInt(id));
        
        view.addObject("record", getEmployeeService().findEducationByID(Integer.parseInt(xid)));
        view.addObject("empid", id);
        view.addObject("result", employeeEducation);
        return view;
    }
    
    public ModelAndView workExperienceData(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("employee_work_view");
        
        String id = request.getParameter("id");
        String xid = request.getParameter("idx");
        if(id == null){
            id = "0";
        }
        if(xid == null){
            xid = "0";
        }
        
        //System.out.println(xid + "----ID>>>>>>>.for work exp: " + id);
        List<EmployeeWorkExperience> employeeWorkExp = getEmployeeService().findEmployeeWorkExperience(Integer.parseInt(id));
        
        view.addObject("record", getEmployeeService().findEmployeeWorkExperienceByID(Integer.parseInt(xid)));
        view.addObject("empid", id);
        view.addObject("result", employeeWorkExp);
        return view;
    }
    
    public ModelAndView skillData(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("employee_skill_view");
        
        String id = request.getParameter("id");
        String xid = request.getParameter("idx");
        if(id == null){
            id = "0";
        }
        if(xid == null){
            xid = "0";
        }
        
        //System.out.println(xid + "----ID>>>>>>>.for skill: " + id);
        List<EmployeeSkill> employeeSkill = getEmployeeService().findEmployeeSkills(Integer.parseInt(id));
        
        List<Skill> skills = this.setupService.findAllSkills();
        view.addObject("skills", skills);
        view.addObject("record", getEmployeeService().findEmployeeSkillByID(Integer.parseInt(xid)));
        view.addObject("empid", id);
        view.addObject("result", employeeSkill);
        return view;
    }
    
    public ModelAndView salaryData(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("employee_salary");
        
        String id = request.getParameter("id");
        
        //System.out.println("----ID>>>>>>>.for salary: " + id);
        
        List<SalaryGrade> grades = this.setupService.findAllSalaryGrade();
        view.addObject("grades", grades);
        //pass employee ID, cos an employer is to have only one payment
        view.addObject("salary", getEmployeeService().findEmployeeSalary(Integer.parseInt(id)));
        view.addObject("empid", id);
        return view;
    }
    
    public ModelAndView reportToData(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("employee_reportto");
        
        String id = request.getParameter("id");
        
        //System.out.println("----ID>>>>>>>.for report to: " + id);
        
        List<Employee> employees = this.getEmployeeService().findAllEmployeeForBasic();
        view.addObject("employees", employees);
        view.addObject("empid", id);
        view.addObject("record", getEmployeeService().findEmployeeSupervisorsBySub(Integer.parseInt(id)));
        return view;
    }
    
    
    public ModelAndView uploadPicture(HttpServletRequest request, HttpServletResponse response) throws Exception{
        ModelAndView view = new ModelAndView("employee_picture_upload");
        
        String strIdx = request.getParameter("idx");
        int idx = Integer.parseInt(strIdx);
        
        view.addObject("employee_id", idx);
        
        return view;
    }
    
    public ModelAndView deleteEmployee(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("");
        List<Long> ids = new java.util.ArrayList<Long>();
        //get mode of request
        String mode = request.getParameter("mode");
        //check if mode is for more than one record
        //get mode, if mode is equals 1, then process for multiple
        if(mode != null && mode.trim().equals("1")){
            String[] args = request.getParameterValues("_chk");
            for(String id : args){
                ids.add(Long.parseLong(id));
            }
        }else{//then it is zero
            ids.add(Long.parseLong(request.getParameter("id")));
        }
        getEmployeeService().deActivateEmployee(ids);
       //delete
        return viewAllEmployee(request, response);
    }
    
    public ModelAndView processUploadRequest(HttpServletRequest request, HttpServletResponse response) throws Exception{
        
        ModelAndView view = new ModelAndView("success");
        
        String strIdx = request.getParameter("txtIdx");
        int idx = Integer.parseInt(strIdx);
        
        if (!(request instanceof MultipartHttpServletRequest)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Expected multipart request");
            return null;
        }
        //System.out.println("-------1------------");
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("txtFile");
        
        FileUpload fileUpload = new FileUpload();
        fileUpload.setFileName(file.getName());
        try {
            fileUpload.setBytes(file.getBytes());
        } catch (IOException ex) {
            ex.printStackTrace();
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
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        //process file
        getEmployeeService().saveUploadedPicture(fileUpload, idx);
        view.addObject("message", "Passport Photograph successfully uploaded.");
        
        return view;
    }
    
    public ModelAndView processRequest(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("message");
        String operation = request.getParameter("txtMode");
        //operation mode: N= New, E=Edit
        if(operation.trim().equals("N")){
            Employee emp = new Employee();
            String empNo = request.getParameter("txtNo");
            String firstName = request.getParameter("txtFirstName");
            String lastName = request.getParameter("txtLastName");
            String salutation = request.getParameter("txtSalutation");
            String dept = request.getParameter("cbDept");
            String jobTitle = request.getParameter("cbJobTitle");
            String empType = request.getParameter("cbType");
            String category = request.getParameter("cbCategory");
            String gender = request.getParameter("cbGender");
            String marital = request.getParameter("cbMarital");
            String strDOB = request.getParameter("txtDate");
            String strEmplymentDate = request.getParameter("txtEmploymentDate");

            String email = request.getParameter("txtEmail");
            String salaryGrade = request.getParameter("cbSalaryGrade");
            String companyCode = request.getParameter("txtHCompanyCode");

            //verify if emp number id exist
            Employee oldEmp = getEmployeeService().findEmployeeBasicByEmpNumber(Integer.parseInt(empNo));
            if(oldEmp != null){
                view  = new ModelAndView("error");
                view.addObject("message", "Employee Number already exist in the system.");
                return view;
            }
            
            Date dob = new Date();
            Date employmentDate = new Date();
            try {
                dob = DateUtility.getDateFromString(strDOB, ConfigReader.DATE_FORMAT);
                employmentDate = DateUtility.getDateFromString(strEmplymentDate, ConfigReader.DATE_FORMAT);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            
            emp.setEmployeeID(Long.parseLong(empNo));
            emp.setSalutation(salutation);
            emp.setFirstName(firstName);
            emp.setLastName(lastName);
            emp.setDepartmentId(Integer.parseInt(dept));
            emp.setJobTitleId(Integer.parseInt(jobTitle));
            emp.setEmployeeTypeId(Integer.parseInt(empType));
            emp.setCategoryId(Integer.parseInt(category));
            emp.setGender(gender);
            emp.setMaritalStatus(marital);
            emp.setDateOfBirth(dob);
            emp.setEmploymentDate(employmentDate);
            emp.setConfirmationStatus(0);
            emp.setEmploymentStatus("ACTIVE");
            emp.setConfirmationDate(new Date());
            emp.setEmail(email);
            emp.setSalaryGradeId(Integer.parseInt(salaryGrade));
            emp.setCompanyCode(companyCode);
            
            boolean saved = getEmployeeService().updateEmployeeBasic(emp, getEmployeeService().MODE_INSERT);
            if(saved == false){
                view = new ModelAndView("error");
                view.addObject("message", "Unable to save new employee information.");
                return view;
            }
            
            view.addObject("message", "Record Saved Successfully.");
            
        }else if(operation.trim().equals("E")){
            Employee emp = new Employee();
            String empNo = request.getParameter("txtNo");
            String id = request.getParameter("txtId");
            String firstName = request.getParameter("txtFirstName");
            String lastName = request.getParameter("txtLastName");
            String salutation = request.getParameter("txtSalutation");
            String dept = request.getParameter("cbDept");
            String jobTitle = request.getParameter("cbJobTitle");
            String empType = request.getParameter("cbType");
            String category = request.getParameter("cbCategory");
            String gender = request.getParameter("cbGender");
            String marital = request.getParameter("cbMarital");
            String strDOB = request.getParameter("txtDate");
            String strEmplymentDate = request.getParameter("txtEmploymentDate");
            String email = request.getParameter("txtEmail");
            String salaryGrade = request.getParameter("cbSalaryGrade");
            String companyCode = request.getParameter("txtHCompanyCode");
            
            //verify if emp number id exist
            Employee oldEmp = getEmployeeService().findEmployeeBasicByEmpNumber(Integer.parseInt(empNo));
            if(oldEmp != null && Long.parseLong(id) != oldEmp.getEmployeeNumber()){
                view  = new ModelAndView("error");
                view.addObject("message", "New Employee number already exist in the system. Specify a different (unassigned) number.");
                return view;
            }

            Date dob = new Date();
            Date employmentDate = new Date();
            try {
                dob = DateUtility.getDateFromString(strDOB, ConfigReader.DATE_FORMAT);
                employmentDate = DateUtility.getDateFromString(strEmplymentDate, ConfigReader.DATE_FORMAT);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            //id for the table
            emp.setEmployeeNumber(Long.parseLong(id));
            //staff identity number
            emp.setEmployeeID(Long.parseLong(empNo));
            emp.setSalutation(salutation);
            emp.setFirstName(firstName);
            emp.setLastName(lastName);
            emp.setDepartmentId(Integer.parseInt(dept));
            emp.setJobTitleId(Integer.parseInt(jobTitle));
            emp.setEmployeeTypeId(Integer.parseInt(empType));
            emp.setCategoryId(Integer.parseInt(category));
            emp.setGender(gender);
            emp.setMaritalStatus(marital);
            emp.setDateOfBirth(dob);
            emp.setEmploymentDate(employmentDate);
            emp.setEmail(email);
            emp.setSalaryGradeId(Integer.parseInt(salaryGrade));
            emp.setCompanyCode(companyCode);
            
            boolean saved = getEmployeeService().updateEmployeeBasic(emp, getEmployeeService().MODE_UPDATE);
            if(saved == false){
                view = new ModelAndView("error");
                view.addObject("message", "Unable to modify existing employee information.");
                return view;
            }
            view.addObject("message", "Record Saved Successfully.");
        }
        return view;
    }
    
    public ModelAndView processContact(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("message");
        String operation = request.getParameter("txtMode");
        String employeeNumber = request.getParameter("txtId");
        //operation mode: N= New, E=Edit
        if(operation.trim().equals("N")){
            Employee emp = new Employee();
            String address1 = request.getParameter("txtAddress1");
            String address2 = request.getParameter("txtAddress2");
            String city = request.getParameter("txtCity");
            String state = request.getParameter("txtState");
            String country = request.getParameter("txtCountry");
            String zipCode = request.getParameter("txtZip");
            //String email = request.getParameter("txtEmail");
            String homePhone = request.getParameter("txtHPhone");
            String officePhone = request.getParameter("txtOPhone");
            String mobile = request.getParameter("txtMobile");
            employeeNumber = request.getParameter("txtId");
            
            emp.setEmployeeNumber(Long.parseLong(employeeNumber));
            emp.setAddress1(address1);
            emp.setAddress2(address2);
            emp.setCity(city);
            emp.setState(state);
            emp.setZipCode(zipCode);
            emp.setCountry(country);
            //emp.setEmail(email);
            emp.setHomePhone(homePhone);
            emp.setOfficePhone(officePhone);
            emp.setCellPhone(mobile);
            
            getEmployeeService().updateEmployeeContact(emp, getEmployeeService().MODE_UPDATE);
            
            view.addObject("message", "Record Saved Successfully.");
            
        }else if(operation.trim().equals("E")){
            Employee emp = new Employee();
            String address1 = request.getParameter("txtAddress1");
            String address2 = request.getParameter("txtAddress2");
            String city = request.getParameter("txtCity");
            String state = request.getParameter("txtState");
            String country = request.getParameter("txtCountry");
            String zipCode = request.getParameter("txtZip");
            //String email = request.getParameter("txtEmail");
            String homePhone = request.getParameter("txtHPhone");
            String officePhone = request.getParameter("txtOPhone");
            String mobile = request.getParameter("txtMobile");
            employeeNumber = request.getParameter("txtId");
            
            emp.setEmployeeNumber(Long.parseLong(employeeNumber));
            emp.setAddress1(address1);
            emp.setAddress2(address2);
            emp.setCity(city);
            emp.setState(state);
            emp.setZipCode(zipCode);
            emp.setCountry(country);
            //emp.setEmail(email);
            emp.setHomePhone(homePhone);
            emp.setOfficePhone(officePhone);
            emp.setCellPhone(mobile);
            
            getEmployeeService().updateEmployeeContact(emp, getEmployeeService().MODE_UPDATE);
            
            view.addObject("message", "Record Saved Successfully.");
        }
        
        view = new ModelAndView(new RedirectView("contact_employee.hd?id=" + employeeNumber));
        return view;
    }

    public ModelAndView processChildren(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("message");
        String operation = request.getParameter("txtMode");
        String employeeNumber = request.getParameter("txtEId");
        //operation mode: N= New, E=Edit
        boolean saved = false;
        if(operation.trim().equals("N")){
            EmployeeChildren emp = new EmployeeChildren();
            String sequence = request.getParameter("txtSeq");
            String birthName = request.getParameter("txtName");
            String dob = request.getParameter("txtDate");
            String placeOfBirth = request.getParameter("txtPlace");
            String address = request.getParameter("txtAddress");
            String remarks = request.getParameter("txtRemarks");
            employeeNumber = request.getParameter("txtEId");
            
            Date effectiveDate = new Date();
            try {
                effectiveDate = DateUtility.getDateFromString(dob, ConfigReader.DATE_FORMAT);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            
            emp.setEmployeeId(Integer.parseInt(employeeNumber));
            emp.setSequence(Integer.parseInt(sequence));
            emp.setBirthName(birthName);
            emp.setDate_Of_Birth(effectiveDate);
            emp.setPlaceOfBirth(placeOfBirth);
            emp.setPresentAddress(address);
            emp.setRemarks(remarks);
            
            saved = getEmployeeService().updateEmployeeChildren(emp, getEmployeeService().MODE_INSERT);
            
            view.addObject("message", "Record Saved Successfully.");
            
        }else if(operation.trim().equals("E")){
            EmployeeChildren emp = new EmployeeChildren();
            String sequence = request.getParameter("txtSeq");
            String birthName = request.getParameter("txtName");
            String dob = request.getParameter("txtDate");
            String placeOfBirth = request.getParameter("txtPlace");
            String address = request.getParameter("txtAddress");
            String remarks = request.getParameter("txtRemarks");
            employeeNumber = request.getParameter("txtEId");
            String id = request.getParameter("txtId");
            
            Date effectiveDate = new Date();
            try {
                effectiveDate = DateUtility.getDateFromString(dob, ConfigReader.DATE_FORMAT);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            
            emp.setId(Integer.parseInt(id));
            emp.setEmployeeId(Integer.parseInt(employeeNumber));
            emp.setSequence(Integer.parseInt(sequence));
            emp.setBirthName(birthName);
            emp.setDate_Of_Birth(effectiveDate);
            emp.setPlaceOfBirth(placeOfBirth);
            emp.setPresentAddress(address);
            emp.setRemarks(remarks);
            
            saved = getEmployeeService().updateEmployeeChildren(emp, getEmployeeService().MODE_UPDATE);
            
            view.addObject("message", "Record Saved Successfully.");
        }
        
        view = new ModelAndView(new RedirectView("view_children_employee.hd?id=" + employeeNumber));
        if(saved == false){
            view.addObject("message", "Unable to save record.");
        }
        return view;
    }

    public ModelAndView processSpouse(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("message");
        String operation = request.getParameter("txtMode");
        String employeeNumber = request.getParameter("txtEId");
        //operation mode: N= New, E=Edit
        boolean saved = false;
        if(operation.trim().equals("N")){
            System.out.println("=====New ====");
            EmployeeSpouse emp = new EmployeeSpouse();
            String fullName = request.getParameter("txtName");
            String dob = request.getParameter("txtDate");
            String placeOfBirth = request.getParameter("txtPlace");
            String address = request.getParameter("txtAddress");
            employeeNumber = request.getParameter("txtEId");

            Date effectiveDate = new Date();
            try {
                effectiveDate = DateUtility.getDateFromString(dob, ConfigReader.DATE_FORMAT);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }

            emp.setEmployeeId(Integer.parseInt(employeeNumber));
            emp.setFullName(fullName);
            emp.setDate_Of_Birth(effectiveDate);
            emp.setPlaceOfBirth(placeOfBirth);
            emp.setAddress(address);

            saved = getEmployeeService().updateEmployeeSpouse(emp, getEmployeeService().MODE_INSERT);

            view.addObject("message", "Record Saved Successfully.");

        }else if(operation.trim().equals("E")){
            EmployeeSpouse emp = new EmployeeSpouse();
            String fullName = request.getParameter("txtName");
            String dob = request.getParameter("txtDate");
            String placeOfBirth = request.getParameter("txtPlace");
            String address = request.getParameter("txtAddress");
            employeeNumber = request.getParameter("txtEId");
            String id = request.getParameter("txtId");

            Date effectiveDate = new Date();
            try {
                effectiveDate = DateUtility.getDateFromString(dob, ConfigReader.DATE_FORMAT);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }

            emp.setId(Integer.parseInt(id));
            emp.setEmployeeId(Integer.parseInt(employeeNumber));
            emp.setFullName(fullName);
            emp.setDate_Of_Birth(effectiveDate);
            emp.setPlaceOfBirth(placeOfBirth);
            emp.setAddress(address);

            saved = getEmployeeService().updateEmployeeSpouse(emp, getEmployeeService().MODE_UPDATE);

            view.addObject("message", "Record Saved Successfully.");
        }

        view = new ModelAndView(new RedirectView("view_spouse_employee.hd?id=" + employeeNumber));
        if(saved == false){
            System.out.println("Unable to save spouse.....");
            view.addObject("message", "Unable to save record.");
        }
        return view;
    }


    public ModelAndView processDependent(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("message");
        String operation = request.getParameter("txtMode");
        String employeeNumber = request.getParameter("txtEId");
        //operation mode: N= New, E=Edit
        boolean saved = false;
        if(operation.trim().equals("N")){
            EmployeeDependent emp = new EmployeeDependent();
            String sequence = request.getParameter("txtSeq");
            String birthName = request.getParameter("txtName");
            String relationship = request.getParameter("txtRelationship");
            employeeNumber = request.getParameter("txtEId");
            
            emp.setEmployeeId(Integer.parseInt(employeeNumber));
            emp.setSequence(Integer.parseInt(sequence));
            emp.setName(birthName);
            emp.setRelationship(relationship);
            
            saved = getEmployeeService().updateEmployeeDependent(emp, getEmployeeService().MODE_INSERT);
            
            view.addObject("message", "Record Saved Successfully.");
            
        }else if(operation.trim().equals("E")){
            EmployeeDependent emp = new EmployeeDependent();
            String sequence = request.getParameter("txtSeq");
            String birthName = request.getParameter("txtName");
            String relationship = request.getParameter("txtRelationship");
            employeeNumber = request.getParameter("txtEId");
            String id = request.getParameter("txtId");
            
            emp.setId(Integer.parseInt(id));
            emp.setEmployeeId(Integer.parseInt(employeeNumber));
            emp.setSequence(Integer.parseInt(sequence));
            emp.setName(birthName);
            emp.setRelationship(relationship);
            
            saved = getEmployeeService().updateEmployeeDependent(emp, getEmployeeService().MODE_UPDATE);
            
            view.addObject("message", "Record Saved Successfully.");
        }
        
        view = new ModelAndView(new RedirectView("view_dependent_employee.hd?id=" + employeeNumber));
        if(saved == false){
            view.addObject("message", "Unable to save record.");
        }
        return view;
    }
    
    public ModelAndView processEmergency(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("message");
        String operation = request.getParameter("txtMode");
        String employeeNumber = request.getParameter("txtEId");
        //operation mode: N= New, E=Edit
        boolean saved = false;
        if(operation.trim().equals("N")){
            EmployeeEmergencyContact emp = new EmployeeEmergencyContact();
            String homePhone = request.getParameter("txtHPhone");
            String mobile = request.getParameter("txtMPhone");
            String officePhone = request.getParameter("txtOPhone");
            String name = request.getParameter("txtName");
            String relationship = request.getParameter("txtRelationship");
            employeeNumber = request.getParameter("txtEId");
            
            emp.setEmployeeId(Integer.parseInt(employeeNumber));
            emp.setContactName(name);
            emp.setHomePhone(homePhone);
            emp.setMobilePhone(mobile);
            emp.setOfficePhone(officePhone);
            emp.setRelationship(relationship);
            
            saved = getEmployeeService().updateEmployeeEmergencyContact(emp, getEmployeeService().MODE_INSERT);
            
            view.addObject("message", "Record Saved Successfully.");
            
        }else if(operation.trim().equals("E")){
            EmployeeEmergencyContact emp = new EmployeeEmergencyContact();
            String homePhone = request.getParameter("txtHPhone");
            String mobile = request.getParameter("txtMPhone");
            String officePhone = request.getParameter("txtOPhone");
            String name = request.getParameter("txtName");
            String relationship = request.getParameter("txtRelationship");
            employeeNumber = request.getParameter("txtEId");
            String id = request.getParameter("txtId");
            
            emp.setId(Integer.parseInt(id));
            emp.setEmployeeId(Integer.parseInt(employeeNumber));
            emp.setContactName(name);
            emp.setHomePhone(homePhone);
            emp.setMobilePhone(mobile);
            emp.setOfficePhone(officePhone);
            emp.setRelationship(relationship);
            
            saved = getEmployeeService().updateEmployeeEmergencyContact(emp, getEmployeeService().MODE_UPDATE);
            
            view.addObject("message", "Record Saved Successfully.");
        }
        
        view = new ModelAndView(new RedirectView("view_emergency_employee.hd?id=" + employeeNumber));
        if(saved == false){
            view.addObject("message", "Unable to save record.");
        }
        return view;
    }
    
    public ModelAndView processEducation(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("message");
        String operation = request.getParameter("txtMode");
        String employeeNumber = request.getParameter("txtEId");
        //operation mode: N= New, E=Edit
        if(operation.trim().equals("N")){
            EmployeeEducation emp = new EmployeeEducation();
            String inst = request.getParameter("txtInst");
            String course = request.getParameter("txtCourse");
            String year = request.getParameter("txtYear");
            String qualification = request.getParameter("txtQualification");
            String startDate = request.getParameter("txtSDate");
            String endDate = request.getParameter("txtEDate");
            employeeNumber = request.getParameter("txtEId");
            
            Date _startDate = new Date();
            Date _endDate = new Date();
            try {
                _startDate = DateUtility.getDateFromString(startDate, ConfigReader.DATE_FORMAT);
                _endDate = DateUtility.getDateFromString(endDate, ConfigReader.DATE_FORMAT);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            
            emp.setEmployeeId(Integer.parseInt(employeeNumber));
            emp.setInstitution(inst);
            emp.setCourse(course);
            emp.setEducationYear(Long.parseLong(year));
            emp.setQualification(qualification);
            emp.setStartDate(_startDate);
            emp.setEndDate(_endDate);
            
            getEmployeeService().updateEmployeeEducation(emp, getEmployeeService().MODE_INSERT);
            
            view.addObject("message", "Record Saved Successfully.");
            
        }else if(operation.trim().equals("E")){
            EmployeeEducation emp = new EmployeeEducation();
            String inst = request.getParameter("txtInst");
            String course = request.getParameter("txtCourse");
            String year = request.getParameter("txtYear");
            String qualification = request.getParameter("txtQualification");
            String startDate = request.getParameter("txtSDate");
            String endDate = request.getParameter("txtEDate");
            employeeNumber = request.getParameter("txtEId");
            String id = request.getParameter("txtId");
            
            Date _startDate = new Date();
            Date _endDate = new Date();
            try {
                _startDate = DateUtility.getDateFromString(startDate, ConfigReader.DATE_FORMAT);
                _endDate = DateUtility.getDateFromString(endDate, ConfigReader.DATE_FORMAT);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            
            emp.setId(Integer.parseInt(id));
            emp.setEmployeeId(Integer.parseInt(employeeNumber));
            emp.setInstitution(inst);
            emp.setCourse(course);
            emp.setEducationYear(Long.parseLong(year));
            emp.setQualification(qualification);
            emp.setStartDate(_startDate);
            emp.setEndDate(_endDate);
            
            getEmployeeService().updateEmployeeEducation(emp, getEmployeeService().MODE_UPDATE);
            
            view.addObject("message", "Record Saved Successfully.");
        }
        
        view = new ModelAndView(new RedirectView("view_education_employee.hd?id=" + employeeNumber));
        return view;
    }
    
    public ModelAndView processSkill(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("message");
        String operation = request.getParameter("txtMode");
        String employeeNumber = request.getParameter("txtEId");
        //operation mode: N= New, E=Edit
        if(operation.trim().equals("N")){
            EmployeeSkill emp = new EmployeeSkill();
            String skillId = request.getParameter("cbSkill");
            String comment = request.getParameter("txtComment");
            String years = request.getParameter("txtYears");
            employeeNumber = request.getParameter("txtEId");
            
            emp.setEmployeeId(Integer.parseInt(employeeNumber));
            emp.setSkillId(Integer.parseInt(skillId));
            emp.setComment(comment);
            emp.setYearsOfExperience(Integer.parseInt(years));
            
            getEmployeeService().updateEmployeeSkills(emp, getEmployeeService().MODE_INSERT);
            
            view.addObject("message", "Record Saved Successfully.");
            
        }else if(operation.trim().equals("E")){
            EmployeeSkill emp = new EmployeeSkill();
            String skillId = request.getParameter("cbSkill");
            String comment = request.getParameter("txtComment");
            String years = request.getParameter("txtYears");
            employeeNumber = request.getParameter("txtEId");
            String id = request.getParameter("txtId");
            
            emp.setId(Integer.parseInt(id));
            emp.setEmployeeId(Integer.parseInt(employeeNumber));
            emp.setSkillId(Integer.parseInt(skillId));
            emp.setComment(comment);
            emp.setYearsOfExperience(Integer.parseInt(years));
            
            getEmployeeService().updateEmployeeSkills(emp, getEmployeeService().MODE_UPDATE);
            
            view.addObject("message", "Record Saved Successfully.");
        }
        
        view = new ModelAndView(new RedirectView("view_skill_employee.hd?id=" + employeeNumber));
        return view;
    }

    public ModelAndView processWorkExperience(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("message");
        String operation = request.getParameter("txtMode");
        String employeeNumber = request.getParameter("txtEId");
        //operation mode: N= New, E=Edit
        if(operation.trim().equals("N")){
            EmployeeWorkExperience emp = new EmployeeWorkExperience();
            String employer = request.getParameter("txtEmployer");
            String title = request.getParameter("txtTitle");
            String comment = request.getParameter("txtComment");
            String startDate = request.getParameter("txtSDate");
            String endDate = request.getParameter("txtEDate");
            String internal = request.getParameter("chkInternal");
            employeeNumber = request.getParameter("txtEId");
            
            if(internal == null){
                internal = "0";
            }
            
            Date _startDate = new Date();
            Date _endDate = new Date();
            try {
                _startDate = DateUtility.getDateFromString(startDate, ConfigReader.DATE_FORMAT);
                _endDate = DateUtility.getDateFromString(endDate, ConfigReader.DATE_FORMAT);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            
            emp.setEmployeeId(Integer.parseInt(employeeNumber));
            emp.setPreviousEmployer(employer);
            emp.setPreviousJobTitle(title);
            emp.setInternalMovement(Integer.parseInt(internal));
            emp.setComment(comment);
            emp.setStartDate(_startDate);
            emp.setEndDate(_endDate);
            
            getEmployeeService().updateEmployeeWorkExperience(emp, getEmployeeService().MODE_INSERT);
            
            view.addObject("message", "Record Saved Successfully.");
            
        }else if(operation.trim().equals("E")){
            EmployeeWorkExperience emp = new EmployeeWorkExperience();
            String employer = request.getParameter("txtEmployer");
            String title = request.getParameter("txtTitle");
            String comment = request.getParameter("txtComment");
            String startDate = request.getParameter("txtSDate");
            String endDate = request.getParameter("txtEDate");
            String internal = request.getParameter("chkInternal");
            employeeNumber = request.getParameter("txtEId");
            String id = request.getParameter("txtId");
            
            if(internal == null){
                internal = "0";
            }
            
            Date _startDate = new Date();
            Date _endDate = new Date();
            try {
                _startDate = DateUtility.getDateFromString(startDate, ConfigReader.DATE_FORMAT);
                _endDate = DateUtility.getDateFromString(endDate, ConfigReader.DATE_FORMAT);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            
            emp.setId(Integer.parseInt(id));
            emp.setEmployeeId(Integer.parseInt(employeeNumber));
            emp.setPreviousEmployer(employer);
            emp.setPreviousJobTitle(title);
            emp.setInternalMovement(Integer.parseInt(internal));
            emp.setComment(comment);
            emp.setStartDate(_startDate);
            emp.setEndDate(_endDate);
            
            getEmployeeService().updateEmployeeWorkExperience(emp, getEmployeeService().MODE_UPDATE);
            
            view.addObject("message", "Record Saved Successfully.");
        }
        
        view = new ModelAndView(new RedirectView("view_work_employee.hd?id=" + employeeNumber));
        return view;
    }
    
    public ModelAndView processEmployeeSalary(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("message");
        String operation = request.getParameter("txtMode");
        String employeeNumber = request.getParameter("txtEId");
        //operation mode: N= New, E=Edit
        if(operation.trim().equals("N")){
            EmployeeSalary emp = new EmployeeSalary();
            String basic = request.getParameter("txtBasic");
            //String freq = request.getParameter("txtFreq");
            String grade = request.getParameter("cbGrade");
            employeeNumber = request.getParameter("txtEId");
            
            emp.setEmployeeId(Integer.parseInt(employeeNumber));
            emp.setBasicSalary(Double.parseDouble(basic));
            emp.setSalaryGrade(Integer.parseInt(grade));
            
            
            getEmployeeService().updateEmployeeSalary(emp, getEmployeeService().MODE_INSERT);
            
            view.addObject("message", "Record Saved Successfully.");
            
        }else if(operation.trim().equals("E")){
            EmployeeSalary emp = new EmployeeSalary();
            String basic = request.getParameter("txtBasic");
            //String freq = request.getParameter("txtFreq");
            String grade = request.getParameter("cbGrade");
            employeeNumber = request.getParameter("txtEId");
            String id = request.getParameter("txtId");
            
            emp.setId(Integer.parseInt(id));
            emp.setEmployeeId(Integer.parseInt(employeeNumber));
            emp.setBasicSalary(Double.parseDouble(basic));
            emp.setSalaryGrade(Integer.parseInt(grade));
            
            getEmployeeService().updateEmployeeSalary(emp, getEmployeeService().MODE_UPDATE);
            
            view.addObject("message", "Record Saved Successfully.");
        }
        
        view = new ModelAndView(new RedirectView("salary_of_employee.hd?id=" + employeeNumber));
        return view;
    }

    public ModelAndView processReportTo(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("message");
        String operation = request.getParameter("txtMode");
        String employeeNumber = request.getParameter("txtEId");
        //operation mode: N= New, E=Edit
        if(operation.trim().equals("N")){
            EmployeeSupervisor emp = new EmployeeSupervisor();
            String supervisor = request.getParameter("cbEmp");
            String mode = request.getParameter("cbMode");
            employeeNumber = request.getParameter("txtEId");
            
            emp.setSubEmployeeId(Integer.parseInt(employeeNumber));
            emp.setReportingMode(Integer.parseInt(mode));
            emp.setSupEmployeeId(Integer.parseInt(supervisor));
            
            getEmployeeService().updateEmployeeReportTo(emp, getEmployeeService().MODE_INSERT);
            
            view.addObject("message", "Record Saved Successfully.");
            
        }else if(operation.trim().equals("E")){
            EmployeeSupervisor emp = new EmployeeSupervisor();
            String supervisor = request.getParameter("cbEmp");
            String mode = request.getParameter("cbMode");
            employeeNumber = request.getParameter("txtEId");
            
            emp.setSubEmployeeId(Integer.parseInt(employeeNumber));
            emp.setReportingMode(Integer.parseInt(mode));
            emp.setSupEmployeeId(Integer.parseInt(supervisor));
            
            //get the original data
            EmployeeSupervisor original = getEmployeeService().findEmployeeSupervisorsBySub(Integer.parseInt(employeeNumber));
            
            getEmployeeService().updateEmployeeReportTo(emp, original, getEmployeeService().MODE_UPDATE);
            
            view.addObject("message", "Record Saved Successfully.");
        }
        
        view = new ModelAndView(new RedirectView("view_reportto_employee.hd?id=" + employeeNumber));
        return view;
    }
    
    

    public ModelAndView deleteChildren(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("");
        List<Long> ids = new java.util.ArrayList<Long>();
        //get mode of request
        String mode = request.getParameter("mode");
        //check if mode is for more than one record
        //get mode, if mode is equals 1, then process for multiple
        if(mode != null && mode.trim().equals("1")){
            String[] args = request.getParameterValues("_chk");
            for(String id : args){
                ids.add(Long.parseLong(id));
            }
        }else{//then it is zero
            ids.add(Long.parseLong(request.getParameter("id")));
        }
        getEmployeeService().deleteEmployeeChild(ids);
        String empId = request.getParameter("empid");
        //delete
        view = new ModelAndView(new RedirectView("view_children_employee.hd?id=" + empId));
        return view;
    }

    public ModelAndView deleteSpouse(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("");
        List<Long> ids = new java.util.ArrayList<Long>();
        //get mode of request
        String mode = request.getParameter("mode");
        //check if mode is for more than one record
        //get mode, if mode is equals 1, then process for multiple
        if(mode != null && mode.trim().equals("1")){
            String[] args = request.getParameterValues("_chk");
            for(String id : args){
                ids.add(Long.parseLong(id));
            }
        }else{//then it is zero
            ids.add(Long.parseLong(request.getParameter("id")));
        }
        getEmployeeService().deleteEmployeeSpouse(ids);
        String empId = request.getParameter("empid");
        //delete
        view = new ModelAndView(new RedirectView("view_spouse_employee.hd?id=" + empId));
        return view;
    }

    public ModelAndView deleteDependent(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("");
        List<Long> ids = new java.util.ArrayList<Long>();
        //get mode of request
        String mode = request.getParameter("mode");
        //check if mode is for more than one record
        //get mode, if mode is equals 1, then process for multiple
        if(mode != null && mode.trim().equals("1")){
            String[] args = request.getParameterValues("_chk");
            for(String id : args){
                ids.add(Long.parseLong(id));
            }
        }else{//then it is zero
            ids.add(Long.parseLong(request.getParameter("id")));
        }
        getEmployeeService().deleteEmployeeDependent(ids);
        String empId = request.getParameter("empid");
        //delete
        view = new ModelAndView(new RedirectView("view_dependent_employee.hd?id=" + empId));
        return view;
    }
    public ModelAndView deleteEducation(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("");
        List<Long> ids = new java.util.ArrayList<Long>();
        //get mode of request
        String mode = request.getParameter("mode");
        //check if mode is for more than one record
        //get mode, if mode is equals 1, then process for multiple
        if(mode != null && mode.trim().equals("1")){
            String[] args = request.getParameterValues("_chk");
            for(String id : args){
                ids.add(Long.parseLong(id));
            }
        }else{//then it is zero
            ids.add(Long.parseLong(request.getParameter("id")));
        }
        getEmployeeService().deleteEmployeeEducation(ids);
        String empId = request.getParameter("empid");
        //delete
        view = new ModelAndView(new RedirectView("view_education_employee.hd?id=" + empId));
        return view;
    }

    public ModelAndView deleteEmergency(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("");
        List<Long> ids = new java.util.ArrayList<Long>();
        //get mode of request
        String mode = request.getParameter("mode");
        //check if mode is for more than one record
        //get mode, if mode is equals 1, then process for multiple
        if(mode != null && mode.trim().equals("1")){
            String[] args = request.getParameterValues("_chk");
            for(String id : args){
                ids.add(Long.parseLong(id));
            }
        }else{//then it is zero
            ids.add(Long.parseLong(request.getParameter("id")));
        }
        getEmployeeService().deleteEmployeeEmmergencyContact(ids);
        String empId = request.getParameter("empid");
        //delete
        view = new ModelAndView(new RedirectView("view_emergency_employee.hd?id=" + empId));
        return view;
    }

    public ModelAndView deleteSkill(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("");
        List<Long> ids = new java.util.ArrayList<Long>();
        //get mode of request
        String mode = request.getParameter("mode");
        //check if mode is for more than one record
        //get mode, if mode is equals 1, then process for multiple
        if(mode != null && mode.trim().equals("1")){
            String[] args = request.getParameterValues("_chk");
            for(String id : args){
                ids.add(Long.parseLong(id));
            }
        }else{//then it is zero
            ids.add(Long.parseLong(request.getParameter("id")));
        }
        getEmployeeService().deleteEmployeeSkill(ids);
        String empId = request.getParameter("empid");
        //delete
        view = new ModelAndView(new RedirectView("view_skill_employee.hd?id=" + empId));
        return view;
    }

    public ModelAndView deleteWorkExperience(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("");
        List<Long> ids = new java.util.ArrayList<Long>();
        //get mode of request
        String mode = request.getParameter("mode");
        //check if mode is for more than one record
        //get mode, if mode is equals 1, then process for multiple
        if(mode != null && mode.trim().equals("1")){
            String[] args = request.getParameterValues("_chk");
            for(String id : args){
                ids.add(Long.parseLong(id));
            }
        }else{//then it is zero
            ids.add(Long.parseLong(request.getParameter("id")));
        }
        getEmployeeService().deleteEmployeeWorkExperience(ids);
        String empId = request.getParameter("empid");
        //delete
        view = new ModelAndView(new RedirectView("view_work_employee.hd?id=" + empId));
        return view;
    }

    /* =====================  UNCONFIRMED OPERATIONS ============================= */
    public ModelAndView viewAllUnconfirmedEmployee(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("employee_confirmation_view");

        String companyCode = this.getActiveEmployeeCompanyCode(request);

        //get all unconfirmed staff
        List<Employee> employeeList = getEmployeeService().findAllUnconfirmedEmployeeByCompany(companyCode, null, null);
        
        //add object to UI view
        view.addObject("result", employeeList);
        return view;
    }

    public ModelAndView editUnconfirmedEmployee(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("employee_confirmation_edit");
        String idx = request.getParameter("idx");

        //get employee id
        Employee employee = getEmployeeService().findEmployeeBasicById(Integer.parseInt(idx));
        //add object to UI view
        view.addObject("employee", employee);
        return view;
    }

    public ModelAndView processUnconfirmedEmployee(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("success_mini");
        try{
            String employeeNumber = request.getParameter("txtEmp");
            String confirmedDate = request.getParameter("txtDate");

            Employee employee = new Employee();
            employee.setConfirmationDate(DateUtility.getDateFromString(confirmedDate, ConfigReader.DATE_FORMAT));
            employee.setConfirmationStatus(1);
            employee.setEmployeeNumber(Long.parseLong(employeeNumber));

            int affected = getEmployeeService().updateUnconfirmedEmployeeStatus(employee);
            if(affected == 0){
                throw new Exception("Unable to save confirmation data");
            }
            view.addObject("message", "Confirmation of employee was successful.");
        }catch(Exception e){
            view = new ModelAndView("error_mini");
            view.addObject("message", "Unable to confirm employee, please try again later");
            return view;
        }
        return view;
    }

    /* ================ TERMINATION ===============================*/
    public ModelAndView viewAllTerminatedEmployee(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("employee_terminate_view");

        String companyCode = this.getActiveEmployeeCompanyCode(request);

        //get all unconfirmed staff
        List<Employee.Termination> employeeList = getEmployeeService().findAllTermination();
        //add object to UI view
        view.addObject("result", employeeList);
        return view;
    }

    public ModelAndView newTerminatedEmployee(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("employee_terminate_edit");

        String companyCode = this.getActiveEmployeeCompanyCode(request);

        //get employee id and data
        List<Employee> employeeList = getEmployeeService().findAllEmployeeForBasicByCompany(companyCode);
        
        //add object to UI view
        view.addObject("employeeList", employeeList);
        view.addObject("terminate", new Employee.Termination());
        return view;
    }

    public ModelAndView editTerminatedEmployee(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("employee_terminate_edit");
        //get index from request as parameter
        String strIdx = request.getParameter("idx");
        //get Termination by id
        Employee.Termination termination = getEmployeeService().findTerminationbyId(Long.parseLong(strIdx));

        //get employee id and data
        Employee employee = getEmployeeService().findEmployeeBasicById(termination.getEmployeeNumber());
        
        //add object to UI view
        view.addObject("employee", employee);
        view.addObject("terminate", termination);
        
        return view;
    }

    public ModelAndView employeeTermination(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("employee_trnx_terminate_edit");
        //get user principal
        Users user = this.getUserPrincipal(request);

        long employeeId = (long)user.getEmployeeId();
        //check if APPROVAL route is already defined for the specific transaction
        String transParentId = "TERM";
        if(getEmployeeService().isApprovalRouteDefined(transParentId, employeeId) == 0){
            TransactionType transactionType = getEmployeeService().findTransactionTypeByParentID(transParentId);
            view = new ModelAndView("error");
            view.addObject("message", "No approval route defined for \" " + transactionType.getDescription() + "\"");
            return view;
        }

        //get Termination by id
        Employee.Termination termination = new Employee.Termination();
        //get employee detail
        Employee employee = getEmployeeService().findEmployeeBasicById(user.getEmployeeId());

        //add object to UI view
        view.addObject("employee", employee);
        view.addObject("terminate", termination);

        return view;
    }

    public long processTransaction(Object object, String description, long employeeId, String transCode){
        return getEmployeeService().initiateTransaction(object, description, employeeId, transCode);
    }

    public ModelAndView processTerminatedEmployee(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("message");
        String operation = request.getParameter("txtMode");
        String saveType = request.getParameter("txtType");
        if(saveType == null){
            saveType = "";
        }
        if(operation == null){
            view = new ModelAndView("error");
            view.addObject("message", "Unable to dtermin the type of operation required.");
            operation = "";
        }

        //operation mode: N= New, E=Edit
        if(operation.trim().equals("N")){
            Employee.Termination emp = new Employee.Termination();
            String cbEmployee = request.getParameter("cbEmployee");
            String cbOperation = request.getParameter("cbMode");
            String strDate = request.getParameter("txtDate");
            String strPeriod = request.getParameter("txtPeriod");
            String reason = request.getParameter("txtReason");

            Date _Date = new Date();
            try{
                _Date = DateUtility.getDateFromString(strDate, ConfigReader.DATE_FORMAT);
            }catch(Exception er){
                
            }

            emp.setEmployeeNumber(Long.parseLong(cbEmployee));
            emp.setOperation(cbOperation);
            emp.setPeriod(strPeriod);
            emp.setReason(reason);
            emp.setEffectiveDate(_Date);

            int role = this.getUserLoginRole(request);
            if(role == 0 && saveType.trim().equals("TRNX")){
                long transactionID = processTransaction(emp, "Termination", emp.getEmployeeNumber(), "TERM");
                if(transactionID == 0L){
                    view = new ModelAndView("error");
                    view.addObject("message", "Unable to complete transaction request.");
                    return view;
                }
                long nextApprover = getEmployeeService().findNextApprovingOfficer(transactionID);
                Employee supervisor = getEmployeeService().findEmployeeBasicById(nextApprover);
                view = new ModelAndView("success");
                view.addObject("message", "Transaction has been forwarded to " + supervisor.getFullName() + " for approval.");
                return view;
            }else{

                getEmployeeService().updateTermination(emp, EmployeeService.MODE_INSERT);
            }
            view.addObject("message", "Record Saved Successfully.");

        }else if(operation.trim().equals("E")){
            Employee.Termination emp = new Employee.Termination();
            String cbEmployee = request.getParameter("cbEmployee");
            String cbOperation = request.getParameter("cbMode");
            String strDate = request.getParameter("txtDate");
            String strPeriod = request.getParameter("txtPeriod");
            String reason = request.getParameter("txtReason");

            String strId = request.getParameter("txtId");

            Date _Date = new Date();
            try{
                _Date = DateUtility.getDateFromString(strDate, ConfigReader.DATE_FORMAT);
            }catch(Exception er){

            }

            emp.setEmployeeNumber(Long.parseLong(cbEmployee));
            emp.setOperation(cbOperation);
            emp.setPeriod(strPeriod);
            emp.setReason(reason);
            emp.setEffectiveDate(_Date);
            emp.setId(Long.parseLong(strId));

            getEmployeeService().updateTermination(emp, EmployeeService.MODE_UPDATE);

            view.addObject("message", "Record Saved Successfully.");
        }

        return view;
    }

    public ModelAndView viewAllEmployeeForPromotion(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("employee_promotion_view");

        String cbSearch = request.getParameter("cbSearch");
        String txtSearch = request.getParameter("txtSearch");

        String companyCode = this.getActiveEmployeeCompanyCode(request);

        if(cbSearch == null || txtSearch == null){
            view.addObject("result", getEmployeeService().findAllEmployeeForBasicByCompany(companyCode));
        }else{
            view.addObject("result", getEmployeeService().findAllEmployeeForBasicByCompany(companyCode, cbSearch, txtSearch));
        }
        return view;
    }

    public ModelAndView newEmployeePromotion(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("employee_promotion_new");

        String strIdx = request.getParameter("idx");//employee id
        String companyCode = this.getActiveEmployeeCompanyCode(request);

        //get employee basic info
        Employee employee = getEmployeeService().findEmployeeBasicById(Integer.parseInt(strIdx));
        view.addObject("employee", employee);
        //get last promotion for employee
        view.addObject("promotion", getEmployeeService().findLastEmployeePromotionHistory(Long.parseLong(strIdx)));
        
        //get list of items to use for drop down
        view.addObject("category", setupService.findAllEmployeeCategoryByCompany(companyCode));
        view.addObject("jobTitle", setupService.findAllJobTitlesByCompany(companyCode));
        view.addObject("salaryGradeList", setupService.findAllSalaryGradeByCompany(companyCode));
        return view;
    }
    //saveEmployeePromotion
    public ModelAndView saveEmployeePromotion(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("employee_promotion_new");

        String strEmpId = request.getParameter("txtId");//employee id
        String strJobId = request.getParameter("cbJobTitle");
        String strSalaryGrade = request.getParameter("cbSalaryGrade");
        String strCategory = request.getParameter("cbCategory");
        String strReason = request.getParameter("txtReason");

        Employee.Promotion promotion = new Employee.Promotion();
        promotion.setCategoryId(Integer.parseInt(strCategory));
        promotion.setJobTitleId(Integer.parseInt(strJobId));
        promotion.setSalaryGradeId(Integer.parseInt(strSalaryGrade));
        promotion.setEmployeeNumber(Long.parseLong(strEmpId));
        promotion.setReason(strReason);
        promotion.setEffectiveDate(new Date());
        promotion.seteStatus("A");

        boolean saved = getEmployeeService().updateEmployeePromotionHistory(promotion, EmployeeService.MODE_INSERT);
        if(saved == false){
            view = new ModelAndView("error");
            view.addObject("message", "Unable to save promotion/demotion information for the specified employee");
            return view;
        }

        view = this.viewAllEmployeeForPromotion(request, response);
        view.addObject("message","Operation completed successfully.");
        return view;

    }

    public SetupService getSetupService() {
        return setupService;
    }

    public void setSetupService(SetupService setupService) {
        this.setupService = setupService;
    }

    /**
     * @return the employeeService
     */
    public EmployeeService getEmployeeService() {
        return employeeService;
    }

    /**
     * @param employeeService the employeeService to set
     */
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
}
