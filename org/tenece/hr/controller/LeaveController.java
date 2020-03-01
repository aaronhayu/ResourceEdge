
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.tenece.web.common.BaseController;
import org.tenece.web.common.ConfigReader;
import org.tenece.web.common.DateUtility;
import org.tenece.web.data.Company;
import org.tenece.web.data.Employee;
import org.tenece.web.data.Leave;
import org.tenece.web.data.LeaveType;
import org.tenece.web.data.TransactionType;
import org.tenece.web.data.Users;
import org.tenece.web.services.EmployeeService;
import org.tenece.web.services.LeaveService;

/**
 *
 * @author jeffry.amachree
 */
public class LeaveController extends BaseController{
    
    private LeaveService leaveService;
    private EmployeeService employeeService;
    
    /** Creates a new instance of DepartmentController */
    public LeaveController() {
    }
    
    public ModelAndView viewAllLeaveType(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("leavetype_view");

        List<LeaveType> list = null;

        if(this.getUserPrincipal(request).getGroupCompanyUser() == 1){
            list = leaveService.findAllLeaveType();
            view.addObject("showCompany", "Y");
        }else{
            Employee employee = this.getEmployeeService().findEmployeeBasicById(getUserPrincipal(request).getEmployeeId());
            list = getLeaveService().findAllLeaveTypeByCompany(employee.getCompanyCode());
        }

        view.addObject("result", list);
        return view;
    }
    
    public ModelAndView editLeaveType(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("leavetype_edit");
        String param = request.getParameter("idx");
        if(param == null){
            return viewAllLeaveType(request, response);
        }
        int id = Integer.parseInt(param);
        LeaveType dept = leaveService.findLeaveTypeById(id);
        
        view.addObject("leave", dept);

        //get company info
        view = getAndAppendCompanyToView(view, request);

        return view;
    }
    
    public ModelAndView newLeaveType(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("leavetype_edit");
        
        view.addObject("leave", new LeaveType());
        
        //get company info
        view = getAndAppendCompanyToView(view, request);
        
        return view;
    }
    
    public ModelAndView deleteLeaveType(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("");
        List<Integer> ids = new ArrayList<Integer>();
        //get mode of request
        String mode = request.getParameter("mode");
        //check if mode is for more than one record
        //get mode, if mode is equals 1, then process for multiple
        if(mode != null && mode.trim().equals("1")){
            String[] args = request.getParameterValues("_chk");
            for(String id : args){
                ids.add(Integer.parseInt(id));
            }
        }else{//then it is zero
            ids.add(Integer.parseInt(request.getParameter("id")));
        }
        leaveService.deleteLeaveType(ids);
       //delete
        return viewAllLeaveType(request, response);
    }
    
    public ModelAndView processLeaveTypeRequest(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("message");
        String operation = request.getParameter("txtMode");
        //operation mode: N= New, E=Edit
        if(operation.trim().equals("N")){
            LeaveType leaveType = new LeaveType();
            String description = request.getParameter("txtDescription");
            String guide = request.getParameter("txtGuide");
            String chkPaid = request.getParameter("chkPaid");
            String companyCode = request.getParameter("cbCompany");

            if(chkPaid == null || chkPaid.trim().equals("")){
                chkPaid = "0";
            }
            
            leaveType.setDescription(description);
            leaveType.setGuide(guide);
            leaveType.setPaidLeave(Integer.parseInt(chkPaid));
            leaveType.setCompanyCode(companyCode);
            
            boolean saved = leaveService.updateLeaveType(leaveType, LeaveService.MODE_INSERT);
            if(saved == false){
                view = new ModelAndView("error");
                view.addObject("message", "Error processing leave type request. Please try again later");
                return view;
            }

            view.addObject("message", "Record Saved Successfully.");
            
        }else if(operation.trim().equals("E")){
            LeaveType leaveType = new LeaveType();
            String description = request.getParameter("txtDescription");
            String guide = request.getParameter("txtGuide");
            String chkPaid = request.getParameter("chkPaid");
            String companyCode = request.getParameter("cbCompany");
            
            if(chkPaid == null || chkPaid.trim().equals("")){
                chkPaid = "0";
            }
            
            String txtId = request.getParameter("txtId");
            
            if(txtId == null || txtId.trim().equals("")){
                view = new ModelAndView("error");
                view.addObject("message","Unable to complete update.");
                return view;
            }
            leaveType.setId(Integer.parseInt(txtId));
            leaveType.setDescription(description);
            leaveType.setGuide(guide);
            leaveType.setPaidLeave(Integer.parseInt(chkPaid));
            leaveType.setCompanyCode(companyCode);
            
            boolean saved = leaveService.updateLeaveType(leaveType, LeaveService.MODE_UPDATE);
            if(saved == false){
                view = new ModelAndView("error");
                view.addObject("message", "Error processing leave type request. Please try again later");
                return view;
            }

            view.addObject("message", "Record Saved Successfully.");
        }
        return view;
    }

    public ModelAndView viewAllLeave(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("leave_view");

        List<Leave> list = null;

        String companyCode = this.getActiveEmployeeCompanyCode(request);

        String cbSearch = request.getParameter("cbSearch");
        String txtSearch = request.getParameter("txtSearch");
        if(cbSearch == null || txtSearch == null){
            list = leaveService.findAllLeaveByCompany(companyCode);
        }else{
            list = leaveService.findAllLeaveByCompany(companyCode, cbSearch, txtSearch);
        }

        view.addObject("result", list);
        return view;
    }

    
    public ModelAndView editLeave(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("leave_edit");
        String param = request.getParameter("idx");
        if(param == null){
            return viewAllLeave(request, response);
        }
        long id = Integer.parseInt(param);
        Leave leave = leaveService.findLeaveById(id);

        String companyCode = this.getActiveEmployeeCompanyCode(request);

        //get employee data that is required
        long empId = leave.getEmployeeId();
        Employee employee = employeeService.findEmployeeBasicById(empId);
        //get list of leave type
        List<LeaveType> leaveTypeList = leaveService.findAllLeaveTypeByCompany(companyCode);
        List<Employee> employeeList = employeeService.findAllEmployeeForBasicByCompany(companyCode);
        //get company info
        Company company = getApplicationService().findCompany(employee.getCompanyCode());

        //add objects to view (UI)
        view.addObject("usedLeave", leaveService.countNumberOfLeaveUsed(empId));
        view.addObject("leave_limit", company.getLeaveLimit());
        view.addObject("employee", employee);
        view.addObject("leaveTypeList", leaveTypeList);
        view.addObject("employeeList", employeeList);

        view.addObject("leave", leave);
        return view;
    }

    public ModelAndView newLeave(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("leave_edit");

        //create a new leave object...
        Leave leave = new Leave();

        String companyCode = this.getActiveEmployeeCompanyCode(request);

        //cbSelect will hold the value of the employee drop down
        String cbSelect = request.getParameter("cbSelect");
        if(cbSelect == null || cbSelect.trim().equals("")){ 
            cbSelect = "";
        }else{
            //get employee data that is required
            long empId = Integer.parseInt(cbSelect);
            Employee employee = employeeService.findEmployeeBasicById(empId);
            Employee empTmp = employeeService.findEmployeeContactById(empId);

            //check if the value is null
            String addr = empTmp.getAddress1() == null ? "" : empTmp.getAddress1();
            String phone = empTmp.getCellPhone() == null ? "" : empTmp.getCellPhone();
            leave.setContactAddress(addr);
            leave.setContactMobile(phone);

            List<LeaveType> leaveTypeList = leaveService.findAllLeaveTypeByCompany(companyCode);

            //get company info
            Company company = getApplicationService().findCompany(employee.getCompanyCode());

            //add objects to view (UI)
            view.addObject("usedLeave", leaveService.countNumberOfLeaveUsed(empId));
            view.addObject("leave_limit", company.getLeaveLimit());

            view.addObject("employee", employee);
            view.addObject("leaveTypeList", leaveTypeList);
        }

        List<Employee> employeeList = employeeService.findAllEmployeeForBasicByCompany(companyCode);
        view.addObject("employeeList", employeeList);
        view.addObject("leave", new Leave());
        return view;
    }

    public ModelAndView deleteLeave(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("");
        List<Integer> ids = new ArrayList<Integer>();
        //get mode of request
        String mode = request.getParameter("mode");
        //check if mode is for more than one record
        //get mode, if mode is equals 1, then process for multiple
        if(mode != null && mode.trim().equals("1")){
            String[] args = request.getParameterValues("_chk");
            for(String id : args){
                ids.add(Integer.parseInt(id));
            }
        }else{//then it is zero
            ids.add(Integer.parseInt(request.getParameter("id")));
        }
        leaveService.deleteLeave(ids);
       //delete
        return viewAllLeave(request, response);
    }

    public ModelAndView applyForLeave(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("leave_application_edit");

        String companyCode = this.getActiveEmployeeCompanyCode(request);

        //get user from session
        int role = this.getUserLoginRole(request);
        if(role == 1){
            view = new ModelAndView("error");
            view.addObject("message", "Unable to complete operation, User is administrator.");
        }
        Users user = this.getUserPrincipal(request);
        long employeeId = user.getEmployeeId();

        //check if APPROVAL route is already defined for the specific transaction
        String transParentId = "LEAVE";
        if(employeeService.isApprovalRouteDefined(transParentId, employeeId) == 0){
            TransactionType transactionType = employeeService.findTransactionTypeByParentID(transParentId);
            view = new ModelAndView("error");
            view.addObject("message", "No approval route defined for \" " + transactionType.getDescription() + "\"");
            return view;
        }
        //check for pending transaction for employee
        int countPending = leaveService.countNumberOfPendingLeave(employeeId);
        if(countPending > 0 ){
            view = new ModelAndView("error");
            view.addObject("message", "You have pending leave application. Contact your supervisor for more information.");
            return view;
        }
        
        //create a blank and default leave  object
        Leave leave = new Leave();

        //get employee data that is required
        Employee employee = employeeService.findEmployeeBasicById(employeeId);
        //get list of leave type
        List<LeaveType> leaveTypeList = leaveService.findAllLeaveTypeByCompany(companyCode);
        List<Employee> employeeList = employeeService.findAllEmployeeForBasicByCompany(companyCode);

        //get company info
        Company company = getApplicationService().findCompany(employee.getCompanyCode());

        //add objects to view (UI)
        view.addObject("usedLeave", leaveService.countNumberOfLeaveUsed(employeeId));
        view.addObject("leave_limit", company.getLeaveLimit());
        view.addObject("employee", employee);
        view.addObject("leaveTypeList", leaveTypeList);
        view.addObject("employeeList", employeeList);

        view.addObject("leave", leave);
        return view;
    }

    public long processTransaction(Leave leave){
        return leaveService.initiateTransaction(leave, "Leave Application", leave.getEmployeeId(), "LEAVE");
    }
    public long processTransaction(Object object, String description, long employeeId, String transactionCode){
        return leaveService.initiateTransaction(object, description, employeeId, transactionCode);
    }

    public ModelAndView processLeaveRequest(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("success");
        String operation = request.getParameter("txtMode");
        String saveType = request.getParameter("txtType");

        //check if save type is null
        saveType = saveType == null ? "" : saveType;
        
        //operation mode: N= New, E=Edit
        if(operation.trim().equals("N")){
            Leave leave = new Leave();
            String strLeaveTypeID = request.getParameter("cbLeave");
            String strStartDate = request.getParameter("txtStartDate");
            String strEndDate = request.getParameter("txtEndDate");

            String reliefOfficer = request.getParameter("cbRelief");
            String contactAddress = request.getParameter("txtAddress");
            String contactPhone = request.getParameter("txtPhone");
            String reason = request.getParameter("txtComment");
            String txtEmp = request.getParameter("txtEmp");
            int currentYear = 0;
            try{
                currentYear = DateUtility.getCurrentYear();
            }catch(Exception e){
                view = new ModelAndView("error");
                return view;
            }
            
            Date _startDate = new Date();
            Date _endDate = new Date();
            try {
                _startDate = DateUtility.getDateFromString(strStartDate, ConfigReader.DATE_FORMAT);
                _endDate = DateUtility.getDateFromString(strEndDate, ConfigReader.DATE_FORMAT);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }

            leave.setContactAddress(contactAddress);
            leave.setContactMobile(contactPhone);
            leave.setEmployeeId(Integer.parseInt(txtEmp));
            leave.setEndDate(_endDate);
            leave.setStartDate(_startDate);
            leave.setLeaveTypeId(Long.parseLong(strLeaveTypeID));
            leave.setReason(reason);
            leave.setReliefOfficer(Integer.parseInt(reliefOfficer));
            leave.setTransactionId(0);
            leave.setYear(currentYear);
            leave.seteStatus("A");

            int role = this.getUserLoginRole(request);

            if(role == 0 && saveType.trim().equals("TRNX")){
                long transactionID = processTransaction(leave);
                if(transactionID == 0L){
                    view = new ModelAndView("error");
                    view.addObject("message", "Unable to complete transaction request.");
                    return view;
                }
                long nextApprover = leaveService.findNextApprovingOfficer(transactionID);
                Employee supervisor = employeeService.findEmployeeBasicById(nextApprover);
                view = new ModelAndView("success");
                view.addObject("message", "Transaction has been forwarded to " + supervisor.getFullName() + " for approval.");
            }else{
                if(role == 0){
                    view = new ModelAndView("error");
                    view.addObject("message", "Unable to complete operation, User is not an administrator.");
                }
                boolean saved = leaveService.updateLeave(leave, LeaveService.MODE_INSERT);
                if(saved == false){
                    view = new ModelAndView("error");
                    view.addObject("message", "Error processing leave application request. Please try again later");
                    return view;
                }
            }
            view = new ModelAndView("success");
            view.addObject("message", "Record Saved Successfully.");

        }else if(operation.trim().equals("E")){
            Leave leave = new Leave();
            String strLeaveTypeID = request.getParameter("cbLeave");
            String strStartDate = request.getParameter("txtStartDate");
            String strEndDate = request.getParameter("txtEndDate");

            String reliefOfficer = request.getParameter("cbRelief");
            String contactAddress = request.getParameter("txtAddress");
            String contactPhone = request.getParameter("txtPhone");
            String reason = request.getParameter("txtComment");
            String txtEmp = request.getParameter("txtEmp");
            int currentYear = 0;
            
            String txtId = request.getParameter("txtId");
            try{
                currentYear = DateUtility.getCurrentYear();
            }catch(Exception e){
                view = new ModelAndView("error");
                return view;
            }

            if(txtId == null || txtId.trim().equals("")){
                view = new ModelAndView("error");
                view.addObject("message","Unable to complete update.");
                return view;
            }
            
            Date _startDate = new Date();
            Date _endDate = new Date();
            try {
                _startDate = DateUtility.getDateFromString(strStartDate, ConfigReader.DATE_FORMAT);
                _endDate = DateUtility.getDateFromString(strEndDate, ConfigReader.DATE_FORMAT);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }

            leave.setId(Long.parseLong(txtId));
            leave.setContactAddress(contactAddress);
            leave.setContactMobile(contactPhone);
            leave.setEmployeeId(Integer.parseInt(txtEmp));
            leave.setEndDate(_endDate);
            leave.setStartDate(_startDate);
            leave.setLeaveTypeId(Long.parseLong(strLeaveTypeID));
            leave.setReason(reason);
            leave.setReliefOfficer(Integer.parseInt(reliefOfficer));
            leave.setTransactionId(0);
            leave.setYear(currentYear);
            leave.seteStatus("A");

            boolean saved = leaveService.updateLeave(leave, LeaveService.MODE_UPDATE);
            if(saved == false){
                view = new ModelAndView("error");
                view.addObject("message", "Error processing leave request. Please try again later");
                return view;
            }

            view = new ModelAndView("success");
            view.addObject("message", "Record Saved Successfully.");
        }
        return view;
    }

    /* ------------- Leave Resumption ----------------------*/
    

    public ModelAndView resumeLeave(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("leave_resumption_edit");
        String param = request.getParameter("idx");
        if(param == null){
            return viewAllLeave(request, response);
        }
        
        long id = Integer.parseInt(param);
        Leave.LeaveResumption leaveResume = leaveService.findLeaveResumptionById(id);

        //get leave data that is required
        Leave leave = leaveService.findLeaveById(id);

        //add objects to view (UI)
        view.addObject("leave_resume", leaveResume);
        view.addObject("leave", leave);
        return view;
    }

    public ModelAndView resumeFromLeave(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("leave_resumption_edit");
        //get last active leave and load for resumption
        Users user = this.getUserPrincipal(request);

        long employeeId = (long)user.getEmployeeId();
        //check if APPROVAL route is already defined for the specific transaction
        String transParentId = "LEAVE-R";
        if(employeeService.isApprovalRouteDefined(transParentId, employeeId) == 0){
            TransactionType transactionType = employeeService.findTransactionTypeByParentID(transParentId);
            view = new ModelAndView("error");
            view.addObject("message", "No approval route defined for \" " + transactionType.getDescription() + "\"");
            return view;
        }

        Leave leave = leaveService.findLastLeaveForEmployee(user.getEmployeeId());
        

        long id = leave.getId();
        Leave.LeaveResumption leaveResume = leaveService.findLeaveResumptionById(id);

        //add objects to view (UI)
        view.addObject("leave_resume", leaveResume);
        view.addObject("leave", leave);
        view.addObject("TRNX", "TRNX");
        return view;
    }

    public long processResumptionTransaction(Leave leave){
        return leaveService.initiateTransaction(leave, "Leave Resumption", leave.getEmployeeId(), "LEAVE-R");
    }

    public ModelAndView processLeaveResumptionRequest(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("success");
        String operation = request.getParameter("txtMode");
        String saveType = request.getParameter("txtType");

        //check if save type is null
        saveType = saveType == null ? "" : saveType;

        //operation mode: N= New, E=Edit
        if(operation.trim().equals("N")){
            Leave.LeaveResumption leave = new Leave.LeaveResumption();
            String strLeaveID = request.getParameter("txtLeaveId");
            String strStartDate = request.getParameter("txtStartDate");
            String strEndDate = request.getParameter("txtEndDate");

            String reason = request.getParameter("txtComment");
            String txtEmp = request.getParameter("txtEmp");
            
            Date _startDate = new Date();
            Date _endDate = new Date();
            try {
                _startDate = DateUtility.getDateFromString(strStartDate, ConfigReader.DATE_FORMAT);
                _endDate = DateUtility.getDateFromString(strEndDate, ConfigReader.DATE_FORMAT);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }

            leave.setEndDate(_endDate);
            leave.setStartDate(_startDate);
            leave.setLeaveId(Long.parseLong(strLeaveID));
            leave.setReason(reason);
            leave.setTransactionId(0);
            leave.seteStatus("A");

            int role = this.getUserLoginRole(request);

            if(role == 0 && saveType.trim().equals("TRNX")){
                long transactionID = processTransaction(leave, "Leave Resumption", Long.parseLong(txtEmp), "LEAVE-R");
                if(transactionID == 0L){
                    view = new ModelAndView("error");
                    view.addObject("message", "Unable to complete transaction request.");
                    return view;
                }
                long nextApprover = leaveService.findNextApprovingOfficer(transactionID);
                Employee supervisor = employeeService.findEmployeeBasicById(nextApprover);
                view = new ModelAndView("success");
                view.addObject("message", "Transaction has been forwarded to " + supervisor.getFullName() + " for approval.");
                
            }else{
                if(role == 0){
                    view = new ModelAndView("error");
                    view.addObject("message", "Unable to complete operation, User is not an administrator.");
                }
                leaveService.updateLeaveResumption(leave, LeaveService.MODE_INSERT);
            }
            view = new ModelAndView("success");
            view.addObject("message", "Record Saved Successfully.");

        }
        return view;
    }
    
    /**
     * @return the leaveService
     */
    public LeaveService getLeaveService() {
        return leaveService;
    }

    /**
     * @param leaveService the leaveService to set
     */
    public void setLeaveService(LeaveService leaveService) {
        this.leaveService = leaveService;
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
