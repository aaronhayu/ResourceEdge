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

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.tenece.web.common.BaseController;
import org.tenece.web.common.ConfigReader;
import org.tenece.web.common.DateUtility;
import org.tenece.web.data.Employee;
import org.tenece.web.data.EmployeeBank;
import org.tenece.web.data.EmployeeChildren;
import org.tenece.web.data.EmployeeDependent;
import org.tenece.web.data.EmployeeEducation;
import org.tenece.web.data.EmployeeEmergencyContact;
import org.tenece.web.data.TransactionType;
import org.tenece.web.data.Users;
import org.tenece.web.services.EmployeeService;
import org.tenece.web.services.SetupService;

/**
 *
 * @author jeffry.amachree
 */
public class ESSController extends BaseController {

    private EmployeeService employeeService = new EmployeeService();
    private SetupService setupService = new SetupService();

    public ModelAndView editBioData(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("employee/employee_bio_edit");

        //get current employee
        Users employee = this.getUserPrincipal(request);
        //get employee id
        int id = employee.getEmployeeId();
        
        //check if APPROVAL route is already defined for the specific transaction
        String transParentId = "ESS-BIO";
        if(employeeService.isApprovalRouteDefined(transParentId, (long)id) == 0){
            TransactionType transactionType = employeeService.findTransactionTypeByParentID(transParentId);
            view = new ModelAndView("error");
            view.addObject("message", "No approval route defined for \" " + transactionType.getDescription() + "\"");
            return view;
        }

        view.addObject("contact", getEmployeeService().findEmployeeContactById(id));
        view.addObject("employee", getEmployeeService().findEmployeeBasicById(id));
        view.addObject("countries", setupService.findAllCountries());
        return view;
    }

    public ModelAndView editBeneficiaries(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("employee/employee_beneficiaries_edit");
        //get current employee
        Users employee = this.getUserPrincipal(request);
        //get employee id
        int id = employee.getEmployeeId();

        //check if APPROVAL route is already defined for the specific transaction
        String transParentId = "ESS-BENE";
        if(employeeService.isApprovalRouteDefined(transParentId, (long)id) == 0){
            TransactionType transactionType = employeeService.findTransactionTypeByParentID(transParentId);
            view = new ModelAndView("error");
            view.addObject("message", "No approval route defined for \" " + transactionType.getDescription() + "\"");
            return view;
        }

        return view;
    }

    public ModelAndView editChildren(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("employee/employee_children_view");


        Users employee = this.getUserPrincipal(request);
        //get employee id
        int id = employee.getEmployeeId();

        String delMode = request.getParameter("del");
        String mode = request.getParameter("mode");
        String childId = request.getParameter("idx");
        if(childId == null){
            childId = "0";
        }
        
        if(mode == null){
            int affected = employeeService.migrateEmployeeESSChildren(id);
        }

        if(delMode != null && delMode.trim().equals("Y")){
            employeeService.deleteESS_EmployeeChild(Integer.parseInt(childId));
            childId = "0";
        }
        
        List<EmployeeChildren> employeeChildren = employeeService.findESS_EmployeeChildren(id);


        view.addObject("record", employeeService.findESS_EmployeeChild(Integer.parseInt(childId)));
        view.addObject("empid", id);
        view.addObject("result", employeeChildren);
        return view;
    }

    public ModelAndView editDependent(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("employee/employee_dependent_view");


        Users employee = this.getUserPrincipal(request);
        //get employee id
        int id = employee.getEmployeeId();

        String delMode = request.getParameter("del");
        String mode = request.getParameter("mode");
        String childId = request.getParameter("idx");
        if(childId == null){
            childId = "0";
        }

        if(mode == null){
            int affected = employeeService.migrateEmployeeESSDependents(id);
        }

        if(delMode != null && delMode.trim().equals("Y")){
            employeeService.deleteESS_EmployeeDependent(Integer.parseInt(childId));
            childId = "0";
        }

        List<EmployeeDependent> employeeDependent = employeeService.findESS_EmployeeDependent(id);


        view.addObject("record", employeeService.findESS_EmployeeDependentBySequence(Integer.parseInt(childId)));
        view.addObject("empid", id);
        view.addObject("result", employeeDependent);
        view.addObject("relationships", setupService.findAllRelationships());
        return view;
    }

    public ModelAndView approveChildren(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("transaction/employee_children_view");

        //get id from request
        String strIdx = request.getParameter("idx");

        //get transaction initiator employee id
        int id = Integer.parseInt(strIdx);
        
        //get list of children
        List<EmployeeChildren> employeeChildren = employeeService.findESS_EmployeeChildren(id);

        //get old items of children (initially saved)
        List<EmployeeChildren> oldEmployeeChildren = employeeService.findEmployeeChildren(id);

        view.addObject("empid", id);
        view.addObject("result", employeeChildren);
        view.addObject("oldResult", oldEmployeeChildren);
        return view;
    }

    public ModelAndView approveDependent(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("transaction/employee_dependent_view");
        //get id from request
        String strIdx = request.getParameter("idx");

        //get transaction initiator employee id
        int id = Integer.parseInt(strIdx);

        List<EmployeeDependent> employeeDependent = employeeService.findESS_EmployeeDependent(id);
        //get old/initiallky saved record(s)
        List<EmployeeDependent> oldEmployeeDependent = employeeService.findEmployeeDependents(id);

        view.addObject("empid", id);
        view.addObject("result", employeeDependent);
        view.addObject("oldResult", oldEmployeeDependent);
        return view;
    }

    public ModelAndView editEducation(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("employee/employee_education_view");

        Users employee = this.getUserPrincipal(request);
        //get employee id
        int id = employee.getEmployeeId();

        //check if APPROVAL route is already defined for the specific transaction
        String transParentId = "ESS-EDU";
        if(employeeService.isApprovalRouteDefined(transParentId, (long)id) == 0){
            TransactionType transactionType = employeeService.findTransactionTypeByParentID(transParentId);
            view = new ModelAndView("error");
            view.addObject("message", "No approval route defined for \" " + transactionType.getDescription() + "\"");
            return view;
        }

        String delMode = request.getParameter("del");
        String mode = request.getParameter("mode");

        String xid = request.getParameter("idx");
        
        if(xid == null){
            xid = "0";
        }

        if(mode == null){
            int affected = employeeService.migrateEmployeeESSEducation(id);
        }

        if(delMode != null && delMode.trim().equals("Y")){
            employeeService.deleteESS_EmployeeEducation(Integer.parseInt(xid));
            xid = "0";
        }
        
        System.out.println(xid + "----ID>>>>>>>.for education: " + id);
        List<EmployeeEducation> employeeEducation = employeeService.findESS_EmployeeEducation(id);

        view.addObject("record", employeeService.findESS_EmployeeEducationByID(Integer.parseInt(xid)));
        view.addObject("empid", id);
        view.addObject("result", employeeEducation);
        return view;
    }

    public ModelAndView editEmergency(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("employee/employee_emergency_view");

        Users employee = this.getUserPrincipal(request);
        //get employee id
        int id = employee.getEmployeeId();

        //check if APPROVAL route is already defined for the specific transaction
        String transParentId = "ESS-EMER";
        if(employeeService.isApprovalRouteDefined(transParentId, (long)id) == 0){
            TransactionType transactionType = employeeService.findTransactionTypeByParentID(transParentId);
            view = new ModelAndView("error");
            view.addObject("message", "No approval route defined for \" " + transactionType.getDescription() + "\"");
            return view;
        }

        String delMode = request.getParameter("del");
        String mode = request.getParameter("mode");

        String xid = request.getParameter("idx");

        if(xid == null){
            xid = "0";
        }

        if(mode == null){
            int affected = employeeService.migrateEmployeeESSEmergencyContact(id);
        }

        if(delMode != null && delMode.trim().equals("Y")){
            employeeService.deleteESS_EmployeeEmergencyContact(Integer.parseInt(xid));
            xid = "0";
        }

        System.out.println(xid + "----ID>>>>>>>.for emergency: " + id);
        List<EmployeeEmergencyContact> employeeEmergency = employeeService.findESS_EmployeeEmergencyContact(id);

        view.addObject("record", employeeService.findESS_EmployeeEmergencyContactByID(Integer.parseInt(xid)));
        view.addObject("empid", id);
        view.addObject("result", employeeEmergency);
        view.addObject("relationships", setupService.findAllRelationships());
        return view;
    }

    public ModelAndView editEmployeeBank(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("employee/employee_bank_edit");

        Users employee = this.getUserPrincipal(request);
        //get employee id
        int id = employee.getEmployeeId();

        //check if APPROVAL route is already defined for the specific transaction
        String transParentId = "ESS-BANK";
        if(employeeService.isApprovalRouteDefined(transParentId, (long)id) == 0){
            TransactionType transactionType = employeeService.findTransactionTypeByParentID(transParentId);
            view = new ModelAndView("error");
            view.addObject("message", "No approval route defined for \" " + transactionType.getDescription() + "\"");
            return view;
        }

        view.addObject("empid", id);
        view.addObject("bank", employeeService.findEmployeeBankDetail((long)id));
        return view;
    }

    public long processTransaction(Object object, String description, long initiator, String transTypeParentID){
        return getEmployeeService().initiateTransaction(object, description, initiator, transTypeParentID);
    }

    public ModelAndView processBioData(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("success");
        try{
            //get current employee
            Users employee = this.getUserPrincipal(request);
            //get employee id
            int id = employee.getEmployeeId();

            //get aLL parameters
            Employee emp = new Employee();
            String empNumber = String.valueOf(id);
            String firstName = request.getParameter("txtFirstName");
            String lastName = request.getParameter("txtLastName");
            String salutation = request.getParameter("txtSalutation");
            String gender = request.getParameter("cbGender");
            String marital = request.getParameter("cbMarital");
            String strDOB = request.getParameter("txtDate");

            String address1 = request.getParameter("txtAddress1");
            String address2 = request.getParameter("txtAddress2");
            String city = request.getParameter("txtCity");
            String state = request.getParameter("txtState");
            String country = request.getParameter("txtCountry");
            String zipCode = request.getParameter("txtZip");
            String email = request.getParameter("txtEmail");
            String homePhone = request.getParameter("txtHPhone");
            String officePhone = request.getParameter("txtOPhone");
            String mobile = request.getParameter("txtMobile");

            Date dob = new Date();
            try {
                dob = DateUtility.getDateFromString(strDOB, ConfigReader.DATE_FORMAT);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }

            emp.setEmployeeNumber(Long.parseLong(empNumber));
            emp.setSalutation(salutation);
            emp.setFirstName(firstName);
            emp.setLastName(lastName);
            emp.setGender(gender);
            emp.setMaritalStatus(marital);
            emp.setDateOfBirth(dob);

            emp.setAddress1(address1);
            emp.setAddress2(address2);
            emp.setCity(city);
            emp.setState(state);
            emp.setZipCode(zipCode);
            emp.setCountry(country);
            emp.setEmail(email);
            emp.setHomePhone(homePhone);
            emp.setOfficePhone(officePhone);
            emp.setCellPhone(mobile);

            long transactionID = processTransaction(emp, "Bio-Data/Contact Request", id, "ESS-BIO");
            if(transactionID == 0L){
                view = new ModelAndView("error");
                view.addObject("message", "Unable to complete transaction request.");
                return view;
            }
            long nextApprover = employeeService.findNextApprovingOfficer(transactionID);
            Employee supervisor = employeeService.findEmployeeBasicById(nextApprover);
            view = new ModelAndView("success");
            view.addObject("message", "Transaction has been forwarded to " + supervisor.getFullName() + " for approval.");
            
        }catch(Exception e){
            view = new ModelAndView("error");
            view.addObject("message", "Unable to process transaction");
            return view;
        }
        
        return view;
    }

    public ModelAndView processEmployeeBank(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("message");
        String operation = request.getParameter("txtMode");

        Users employee = this.getUserPrincipal(request);
        //get employee id
        int id = employee.getEmployeeId();

        EmployeeBank emp = new EmployeeBank();
        String bank = request.getParameter("txtBank");
        String branch = request.getParameter("txtBranch");
        String accountName = request.getParameter("txtAcctName");
        String accountNo = request.getParameter("txtAcctNumber");
        String accountType = request.getParameter("rdType");

        emp.setEmployeeId(id);
        emp.setBankName(bank);
        emp.setBranch(branch);
        emp.setAccountName(accountName);
        emp.setAccountNumber(accountNo);
        emp.setAccountType(accountType);
        emp.seteStatus("P");


        //employeeService.updateEmployeeBankDetail(emp, employeeService.MODE_INSERT);

        long transactionID = processTransaction(emp, "Modify Payment/Bank Information", id, "ESS-BANK");
        if(transactionID == 0L){
            view = new ModelAndView("error");
            view.addObject("message", "Unable to complete transaction request.");
            return view;
        }
        long nextApprover = employeeService.findNextApprovingOfficer(transactionID);
        Employee supervisor = employeeService.findEmployeeBasicById(nextApprover);
        view = new ModelAndView("success");
        view.addObject("message", "Transaction has been forwarded to " + supervisor.getFullName() + " for approval.");

        return view;
    }

    public ModelAndView processbeneficiaries(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("message");
        String operation = request.getParameter("txtMode");

        try{
            //get current employee
            Users user = this.getUserPrincipal(request);
            //get employee id
            int id = user.getEmployeeId();

            Employee employee = employeeService.findEmployeeBasicById(id);
            
            //initiate transaction
            //object in this case is null because we are dealing with multiple records
            //in addition, when saving transaction, we will pick all records for processing
            long transactionID = processTransaction(employee, "Beneficiaries Modification Request", id, "ESS-BENE");
            if(transactionID == 0L){
                view = new ModelAndView("error");
                view.addObject("message", "Unable to complete transaction request.");
                return view;
            }
            long nextApprover = employeeService.findNextApprovingOfficer(transactionID);
            Employee supervisor = employeeService.findEmployeeBasicById(nextApprover);
            view = new ModelAndView("success");
            view.addObject("message", "Transaction has been forwarded to " + supervisor.getFullName() + " for approval.");
            return view;
        }catch(Exception e){
            view = new ModelAndView("error");
            view.addObject("message", "Unable to process transaction");
            return view;
        }

    }

    public ModelAndView processChildren(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("message");
        String operation = request.getParameter("txtMode");
        String employeeNumber = request.getParameter("txtEId");
        //operation mode: N= New, E=Edit
         if(operation.trim().equals("N")){
            EmployeeChildren emp = new EmployeeChildren();
            String sequence = request.getParameter("txtSeq");
            String birthName = request.getParameter("txtName");
            String dob = request.getParameter("txtDate");
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
            emp.setTransactionId(0);
            emp.seteStatus("M");

            employeeService.updateESS_EmployeeChildren(emp, employeeService.MODE_INSERT);

            view.addObject("message", "Record Saved Successfully.");

        }else if(operation.trim().equals("E")){
            EmployeeChildren emp = new EmployeeChildren();
            String sequence = request.getParameter("txtSeq");
            String birthName = request.getParameter("txtName");
            String dob = request.getParameter("txtDate");
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
            emp.setTransactionId(0);
            emp.seteStatus("M");

            employeeService.updateESS_EmployeeChildren(emp, employeeService.MODE_UPDATE);

            view.addObject("message", "Record Saved Successfully.");
        }

        view = new ModelAndView(new RedirectView("edit_children_ess.hd?mode=M&id=" + employeeNumber));
        return view;
    }

    public ModelAndView processDependent(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("message");
        String operation = request.getParameter("txtMode");
        String employeeNumber = request.getParameter("txtEId");
        //operation mode: N= New, E=Edit
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
            emp.setTransactionId(0L);
            emp.seteStatus("M");

            employeeService.updateESS_EmployeeDependent(emp, employeeService.MODE_INSERT);

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
            emp.setTransactionId(0L);
            emp.seteStatus("M");

            employeeService.updateESS_EmployeeDependent(emp, employeeService.MODE_UPDATE);

            view.addObject("message", "Record Saved Successfully.");
        }

        view = new ModelAndView(new RedirectView("edit_dependent_ess.hd?mode=M&id=" + employeeNumber));
        return view;
    }

    public ModelAndView processEducation(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("message");

        //get current employee
        Users user = this.getUserPrincipal(request);
        //get employee id
        int employeeId = user.getEmployeeId();

        String operation = request.getParameter("txtMode");
        String employeeNumber = request.getParameter("txtEId");

        String action = request.getParameter("txtAction");

        if(action != null && action.trim().equals("A")){
            Employee employee = employeeService.findEmployeeBasicById(employeeId);
            long transactionID = processTransaction(employee, "Education Modification Request", employeeId, "ESS-EDU");

            if(transactionID == 0L){
                view = new ModelAndView(new RedirectView("edit_education_ess.hd?message=Unable to process transaction.&mode=M&id=" + employeeNumber));
                view.addObject("message", "Unable to complete transaction request.");
                return view;
            }
            long nextApprover = employeeService.findNextApprovingOfficer(transactionID);
            Employee supervisor = employeeService.findEmployeeBasicById(nextApprover);
            view = new ModelAndView("success");
            view.addObject("message", "Transaction has been forwarded to " + supervisor.getFullName() + " for approval.");
            return view;
        }
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

            emp.setEmployeeId(employeeId);
            emp.setInstitution(inst);
            emp.setCourse(course);
            emp.setEducationYear(Long.parseLong(year));
            emp.setQualification(qualification);
            emp.setStartDate(_startDate);
            emp.setEndDate(_endDate);
            emp.setTransactionId(0L);
            emp.seteStatus("M");

            employeeService.updateESS_EmployeeEducation(emp, employeeService.MODE_INSERT);

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
            emp.setEmployeeId(employeeId);
            emp.setInstitution(inst);
            emp.setCourse(course);
            emp.setEducationYear(Long.parseLong(year));
            emp.setQualification(qualification);
            emp.setStartDate(_startDate);
            emp.setEndDate(_endDate);
            emp.setTransactionId(0L);
            emp.seteStatus("M");

            employeeService.updateESS_EmployeeEducation(emp, employeeService.MODE_UPDATE);

            view.addObject("message", "Record Saved Successfully.");
        }

        
        view = new ModelAndView(new RedirectView("edit_education_ess.hd?mode=M&id=" + employeeNumber));
        return view;
    }

    public ModelAndView processEmergency(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("message");

        //get current employee
        Users user = this.getUserPrincipal(request);
        //get employee id
        int employeeId = user.getEmployeeId();

        String operation = request.getParameter("txtMode");
        String employeeNumber = request.getParameter("txtEId");

        String action = request.getParameter("txtAction");

        if(action != null && action.trim().equals("A")){
            Employee employee = employeeService.findEmployeeBasicById(employeeId);
            long transactionID = processTransaction(employee, "Emmergency Contact Modification Request", employeeId, "ESS-EMER");
            
            if(transactionID == 0L){
                view = new ModelAndView(new RedirectView("edit_emergency_ess.hd?message=Unable to process transaction.&mode=M&id=" + employeeNumber));
                view.addObject("message", "Unable to complete transaction request.");
                return view;
            }
            long nextApprover = employeeService.findNextApprovingOfficer(transactionID);
            Employee supervisor = employeeService.findEmployeeBasicById(nextApprover);
            view = new ModelAndView("success");
            view.addObject("message", "Transaction has been forwarded to " + supervisor.getFullName() + " for approval.");
            return view;
        }

        //operation mode: N= New, E=Edit
        if(operation.trim().equals("N")){
            EmployeeEmergencyContact emp = new EmployeeEmergencyContact();
            String homePhone = request.getParameter("txtHPhone");
            String mobile = request.getParameter("txtMPhone");
            String officePhone = request.getParameter("txtOPhone");
            String name = request.getParameter("txtName");
            String relationship = request.getParameter("txtRelationship");
            employeeNumber = request.getParameter("txtEId");

            emp.setEmployeeId(employeeId);
            emp.setContactName(name);
            emp.setHomePhone(homePhone);
            emp.setMobilePhone(mobile);
            emp.setOfficePhone(officePhone);
            emp.setRelationship(relationship);
            emp.setTransactionId(0L);
            emp.seteStatus("M");

            employeeService.updateESS_EmployeeEmergencyContact(emp, employeeService.MODE_INSERT);

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
            emp.setEmployeeId(employeeId);
            emp.setContactName(name);
            emp.setHomePhone(homePhone);
            emp.setMobilePhone(mobile);
            emp.setOfficePhone(officePhone);
            emp.setRelationship(relationship);
            emp.setTransactionId(0L);
            emp.seteStatus("M");

            employeeService.updateESS_EmployeeEmergencyContact(emp, employeeService.MODE_UPDATE);

            view.addObject("message", "Record Saved Successfully.");
        }

        view = new ModelAndView(new RedirectView("edit_emergency_ess.hd?mode=M&id=" + employeeNumber));
        return view;
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

    /**
     * @return the setupService
     */
    public SetupService getSetupService() {
        return setupService;
    }

    /**
     * @param setupService the setupService to set
     */
    public void setSetupService(SetupService setupService) {
        this.setupService = setupService;
    }
}
