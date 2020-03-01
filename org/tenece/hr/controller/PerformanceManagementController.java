
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
import org.tenece.web.data.Employee;
import org.tenece.web.data.PerformanceReport;
import org.tenece.web.data.PerformanceTarget;
import org.tenece.web.data.TransactionType;
import org.tenece.web.data.Users;
import org.tenece.web.data.Users;
import org.tenece.web.services.EmployeeService;
import org.tenece.web.services.PerformanceTargetService;

/**
 *
 * @author jeffry.amachree
 */
public class PerformanceManagementController extends BaseController{
    
    private PerformanceTargetService performanceTargetService = new PerformanceTargetService();
    private EmployeeService employeeService = new EmployeeService();
    
    /** Creates a new instance of DepartmentController */
    public PerformanceManagementController() {
    }
    
    /* ------------ Performance Target --------------- */
    public ModelAndView viewAllTargetByEmployee(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("target_view");
        long employeeId = Integer.parseInt(request.getParameter("idx"));
        System.out.println("Employee ===== Idx === "+employeeId);
        Employee employee = employeeService.findEmployeeBasicById(employeeId);
        view.addObject("employee", employee);
        
        List<PerformanceTarget> list = performanceTargetService.findPerformanceTargetForEmployee(employeeId);
        view.addObject("result", list);
        return view;
    }
    
    public ModelAndView viewAllEmployee(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("target_employee_view");

        String companyCode = this.getActiveEmployeeCompanyCode(request);

        //do search here...
        String cbSearch = request.getParameter("cbSearch");
        String txtSearch = request.getParameter("txtSearch");

        //get list of employee
        List<Employee> employees = null;
        if(cbSearch == null || txtSearch == null){
            employees = getEmployeeService().findAllEmployeeForBasicByCompany(companyCode);
        }else{
            employees = getEmployeeService().findAllEmployeeForBasicByCompany(companyCode, cbSearch, txtSearch);
        }
        
        view.addObject("result", employees);
        
        return view;
    }
    
    public ModelAndView editTarget(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("target_edit");
        String param = request.getParameter("idx");
        if(param == null){
            return viewAllTargetByEmployee(request, response);
        }
        
        //List<Employee> employees = getEmployeeService().findAllEmployeeForBasic();
        //view.addObject("employees", employees);
        try{
            int id = Integer.parseInt(param);
            PerformanceTarget target = performanceTargetService.findPerformanceTargetById(id);
            
            //get employee id to pick his record
            long employeeId = target.getEmployeeId();
            Employee employee = this.employeeService.findEmployeeBasicById(employeeId);
            if(employee == null){
                return viewAllEmployee(request, response);
            }

            view.addObject("employee", employee);
            view.addObject("target", target);
        }catch(Exception e){
            e.printStackTrace();
            return viewAllTargetByEmployee(request, response);
        }
        return view;
    }
    
    public ModelAndView newTargetTransactionRecord(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("target_edit");
        
        try{
            long employeeID = 0;

            Users  user = this.getUserPrincipal(request);
            
            long employeeId = (long)user.getEmployeeId();
            //check if APPROVAL route is already defined for the specific transaction
            String transParentId = "KPI";
            if(employeeService.isApprovalRouteDefined(transParentId, employeeId) == 0){
                TransactionType transactionType = employeeService.findTransactionTypeByParentID(transParentId);
                view = new ModelAndView("error");
                view.addObject("message", "No approval route defined for \" " + transactionType.getDescription() + "\"");
                return view;
            }

            int role = this.getUserLoginRole(request);
            employeeID = this.getUserPrincipal(request).getEmployeeId();
            
            Employee employee = this.employeeService.findEmployeeBasicById(employeeID);
            if(employee == null){
                throw new Exception("Unable to process transaction request");
            }
            view.addObject("employee", employee);
            view.addObject("TRNX", "TRNX");
            view.addObject("target", new PerformanceTarget());
        }catch(Exception e){
            e.printStackTrace();
            view = new ModelAndView("error");
            view.addObject("message", e.getMessage());
            return view;
        }
        return view;
    }

    public ModelAndView newTargetRecord(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("target_edit");

        try{
            
            long employeeID = Integer.parseInt(request.getParameter("idx"));
            Employee employee = this.employeeService.findEmployeeBasicById(employeeID);
            if(employee == null){
                return viewAllEmployee(request, response);
            }
            view.addObject("employee", employee);
            view.addObject("target", new PerformanceTarget());
        }catch(Exception e){
            e.printStackTrace();
            return viewAllEmployee(request, response);
        }
        return view;
    }
    
    public ModelAndView deleteTarget(HttpServletRequest request, HttpServletResponse response){
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
        performanceTargetService.deletePerformanceTargets(ids);
       //delete
        return viewAllTargetByEmployee(request, response);
    }
    public long processTargetTransaction(PerformanceTarget target){
        return performanceTargetService.initiateTransaction(target, "Performance Target", target.getEmployeeId(), "KPI");
        //return performanceTargetService.updatePerformanceTargetTransaction(target, performanceTargetService.MODE_INSERT);
    }
    
    public ModelAndView processTargetRequest(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("message");
        String operation = request.getParameter("txtMode");
        String type = request.getParameter("txtType");
        if(type == null){
            type = "";
        }
        System.out.println("operation = "+ operation);
        System.out.println("type = "+ type);
        //operation mode: N= New, E=Edit
        if(operation.trim().equals("N")){
            PerformanceTarget target = new PerformanceTarget();
            String staff = request.getParameter("cbEmp");
            String assignment = request.getParameter("txtAssigment");
            String weight = request.getParameter("txtWeight");
            String amount = request.getParameter("txtAmount");
            String startDate = request.getParameter("txtStartDate");
            String endDate = request.getParameter("txtEndDate");
            String note = request.getParameter("txtNote");
            
            Date _startDate = new Date();
            Date _endDate = new Date();
            try {
                _startDate = DateUtility.getDateFromString(startDate, ConfigReader.DATE_FORMAT);
                _endDate = DateUtility.getDateFromString(endDate, ConfigReader.DATE_FORMAT);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            
            if(staff != null && (!staff.equals(""))){
                target.setEmployeeId(Integer.parseInt(staff));
            }
            target.setAssignment(assignment);
            target.setTargetWeight(Integer.parseInt(weight));
            target.setTargetAmount(Double.parseDouble(amount));
            target.setStartDate(_startDate);
            target.setEndDate(_endDate);
            target.setNote(note);
            target.setStatus("P");

            int role = this.getUserLoginRole(request);
            if(role == 0 && type.trim().equals("TRNX")){
                long transactionID = processTargetTransaction(target);
                if(transactionID == 0L){
                    view = new ModelAndView("error");
                    view.addObject("message", "Unable to complete transaction request.");
                    return view;
                }
                long nextApprover = performanceTargetService.findNextApprovingOfficer(transactionID);
                Employee supervisor = employeeService.findEmployeeBasicById(nextApprover);
                view = new ModelAndView("success");
                view.addObject("message", "Transaction has been forwarded to " + supervisor.getFullName() + " for approval.");
                return view;
            }else{
                if(role != 0){
                    boolean saved = performanceTargetService.updatePerformanceTarget(target, performanceTargetService.MODE_INSERT);
                    if(saved == false){
                        view = new ModelAndView("error");
                        view.addObject("message", "Error processing Key Performance Indicator(KPI) request. Please try again later");
                        return view;
                    }
                }
                view.addObject("message", "Record Saved Successfully.");
            }

        }else if(operation.trim().equals("E")){
            PerformanceTarget target = new PerformanceTarget();
            String staff = request.getParameter("cbEmp");
            String assignment = request.getParameter("txtAssigment");
            String weight = request.getParameter("txtWeight");
            String amount = request.getParameter("txtAmount");
            String startDate = request.getParameter("txtStartDate");
            String endDate = request.getParameter("txtEndDate");
            String note = request.getParameter("txtNote");
            String id = request.getParameter("txtId");
            
            Date _startDate = new Date();
            Date _endDate = new Date();
            try {
                _startDate = DateUtility.getDateFromString(startDate, ConfigReader.DATE_FORMAT);
                _endDate = DateUtility.getDateFromString(endDate, ConfigReader.DATE_FORMAT);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            
            if(staff != null && (!staff.equals(""))){
                target.setEmployeeId(Integer.parseInt(staff));
            }
            target.setAssignment(assignment);
            target.setTargetWeight(Integer.parseInt(weight));
            target.setTargetAmount(Double.parseDouble(amount));
            target.setStartDate(_startDate);
            target.setEndDate(_endDate);
            target.setNote(note);
            target.setStatus("P");
            
            target.setId(Integer.parseInt(id));
            
            boolean saved = performanceTargetService.updatePerformanceTarget(target, performanceTargetService.MODE_UPDATE);
            if(saved == false){
                view = new ModelAndView("error");
                view.addObject("message", "Error processing Key Performance Indicator(KPI) request. Please try again later");
                return view;
            }

            view.addObject("message", "Record Saved Successfully.");
        }
        return view;
    }
    
    /* ---------- Performance report ------------ */
    //step 1- show all employee
    public ModelAndView viewAllEmployee_ForReport(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("targetreport_employee_view");

        String companyCode = this.getActiveEmployeeCompanyCode(request);
        //do search here...
        String cbSearch = request.getParameter("cbSearch");
        String txtSearch = request.getParameter("txtSearch");

        //get list of employee
        List<Employee> employees = null;
        if(cbSearch == null || txtSearch == null){
            employees = getEmployeeService().findAllEmployeeForBasicByCompany(companyCode);
        }else{
            employees = getEmployeeService().findAllEmployeeForBasicByCompany(companyCode, cbSearch, txtSearch);
        }
        
        view.addObject("result", employees);
        
        return view;
    }
    //step 2- show targets for employee with option to add report
    public ModelAndView viewAllTargetForEmployee(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("targetreport_view");
        long employeeId = Integer.parseInt(request.getParameter("idx"));
        
        Employee employee = employeeService.findEmployeeBasicById(employeeId);
        view.addObject("employee", employee);
        
        List<PerformanceTarget> list = performanceTargetService.findPerformanceTargetForEmployee(employeeId);
        view.addObject("result", list);
        return view;
    }
    
    //step 3- show all reports for target that was selected
    public ModelAndView viewAllReportsForTarget(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("targetreport_report_view");
        
        int targetId = Integer.parseInt(request.getParameter("idx"));
        
        PerformanceTarget target = performanceTargetService.findPerformanceTargetById(targetId);
        
        long employeeId = target.getEmployeeId();
        
        Employee employee = employeeService.findEmployeeBasicById(employeeId);
        view.addObject("employee", employee);
        
        view.addObject("target", target);
        
        List<PerformanceReport> list = performanceTargetService.findPerformanceReportByTarget(target.getId());
        view.addObject("result", list);
        return view;
    }
    
    public ModelAndView editTargetReport(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("targetreport_edit");
        String param = request.getParameter("idx");
        if(param == null){
            return viewAllTargetByEmployee(request, response);
        }
        
        //List<Employee> employees = getEmployeeService().findAllEmployeeForBasic();
        //view.addObject("employees", employees);
        try{
            int id = Integer.parseInt(param);
            PerformanceReport report = performanceTargetService.findPerformanceReportById(id);
            PerformanceTarget target = performanceTargetService.findPerformanceTargetById(report.getTargetId());
            
            //get employee id to pick his record
            long employeeId = target.getEmployeeId();
            Employee employee = this.employeeService.findEmployeeBasicById(employeeId);
            if(employee == null){
                return viewAllEmployee(request, response);
            }

            view.addObject("employee", employee);
            view.addObject("target", target);
            view.addObject("report", report);
        }catch(Exception e){
            e.printStackTrace();
            return viewAllTargetByEmployee(request, response);
        }
        return view;
    }

    public ModelAndView newTargetReport(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("targetreport_edit");

        //List<Employee> employees = getEmployeeService().findAllEmployeeForBasic();
        //view.addObject("employees", employees);
        try{

            int targetID = Integer.parseInt(request.getParameter("idx"));
            PerformanceTarget target = this.performanceTargetService.findPerformanceTargetById(targetID);

            long employeeID = target.getEmployeeId();
            Employee employee = this.employeeService.findEmployeeBasicById(employeeID);
            if(employee == null){
                return viewAllReportsForTarget(request, response);
            }
            System.out.println("employee for new report....." + employee);
            view.addObject("employee", employee);
            view.addObject("target", target);
            view.addObject("report", new PerformanceReport());
        }catch(Exception e){
            e.printStackTrace();
            return viewAllEmployee(request, response);
        }
        return view;
    }

    public ModelAndView newESSTargetReport(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("employee/targetreport_edit");
        //get user profile from session
        Users user = this.getUserPrincipal(request);
        long employeeId = user.getEmployeeId();
        try{
            //get list of all targets for the active employee
            List<PerformanceTarget> targetList = performanceTargetService.findPerformanceTargetForEmployee(employeeId);
            //add list to view
            view.addObject("targetList", targetList);

            //get parameter for isPostback
            String selectedTarget = request.getParameter("cbTarget");

            //create default target and report that will be used
            PerformanceTarget target = new PerformanceTarget();
            PerformanceReport report = new PerformanceReport();

            //confirm if form is isPostBack; if it is pick the target details
            if(selectedTarget == null){
                
            }else{
                target = performanceTargetService.findPerformanceTargetById(Integer.parseInt(selectedTarget));
                Employee employee = employeeService.findEmployeeBasicById(employeeId);
                //add items to view
                view.addObject("employee", employee);
                view.addObject("target", target);
                view.addObject("report", report);
            }

        }catch(Exception e){
            e.printStackTrace();
            return viewAllEmployee(request, response);
        }
        return view;
    }

    public ModelAndView newESSTargetEquiry(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("employee/target_enquiry_view");
        //get user profile from session
        Users user = this.getUserPrincipal(request);
        long employeeId = user.getEmployeeId();
        try{
            //get list of all targets for the active employee
            List<PerformanceTarget> targetList = performanceTargetService.findPerformanceTargetForEmployee(employeeId);
            //add list to view
            view.addObject("result", targetList);

        }catch(Exception e){
            e.printStackTrace();
            return viewAllEmployee(request, response);
        }
        return view;
    }
    
    
    public ModelAndView processTargetReportRequest(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("message");
        String operation = request.getParameter("txtMode");
        //operation mode: N= New, E=Edit
        if(operation.trim().equals("N")){
            PerformanceReport report = new PerformanceReport();
            String targetID = request.getParameter("txtTargetId");
            String progress = request.getParameter("txtProgress");
            String amountAcheived = request.getParameter("txtWeight");
            String reportDate = request.getParameter("txtDate");
            String status = request.getParameter("chkStatus");
            String note = request.getParameter("txtNote");
            
            Date _Date = new Date();
            try {
                _Date = DateUtility.getDateFromString(reportDate, ConfigReader.DATE_FORMAT);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            
            if(targetID != null && (!targetID.equals(""))){
                report.setTargetId(Integer.parseInt(targetID));
            }else{
                //throw error
                return newTargetReport(request, response);
            }
            report.setProgress(progress);
            report.setAmountAcheived(Integer.parseInt(amountAcheived));
            report.setReportDate(_Date);
            report.setNote(note);
            if(String.valueOf(status).equals("1")){
                //set  status of report to complete - A
                //also set status of target to A
                report.setStatus("A");
            }else{
                report.setStatus("P");
            }
            
            boolean saved = performanceTargetService.updatePerformanceReport(report, performanceTargetService.MODE_INSERT);
            if(saved == false){
                view = new ModelAndView("error");
                view.addObject("message", "Error processing Key Performance Indicator(KPI) report request. Please try again later");
                return view;
            }

            view.addObject("message", "Record Saved Successfully.");
            
        }else if(operation.trim().equals("E")){
            PerformanceReport report = new PerformanceReport();
            String targetID = request.getParameter("txtTargetId");
            String progress = request.getParameter("txtProgress");
            String amountAcheived = request.getParameter("txtWeight");
            String reportDate = request.getParameter("txtDate");
            String status = request.getParameter("chkStatus");
            String note = request.getParameter("txtNote");
            
            String id = request.getParameter("txtId");
            
            Date _Date = new Date();
            try {
                _Date = DateUtility.getDateFromString(reportDate, ConfigReader.DATE_FORMAT);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            
            if(targetID != null && (!targetID.equals(""))){
                report.setTargetId(Integer.parseInt(targetID));
            }else{
                //throw error
                return newTargetReport(request, response);
            }
            
            report.setProgress(progress);
            report.setAmountAcheived(Integer.parseInt(amountAcheived));
            report.setReportDate(_Date);
            report.setNote(note);
            if(String.valueOf(status).equals("1")){
                //set  status of report to complete - A
                //also set status of target to A
                report.setStatus("A");
            }else{
                report.setStatus("P");
            }
            
            report.setId(Integer.parseInt(id));
            
            boolean saved = performanceTargetService.updatePerformanceReport(report, performanceTargetService.MODE_UPDATE);
            if(saved == false){
                view = new ModelAndView("error");
                view.addObject("message", "Error processing Key Performance Indicator(KPI) report request. Please try again later");
                return view;
            }
            
            view.addObject("message", "Record Saved Successfully.");
        }
        return view;
    }

    public EmployeeService getEmployeeService() {
        return employeeService;
    }

    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    
    
}
