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

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.tenece.web.common.BaseController;
import org.tenece.web.common.ConfigReader;
import org.tenece.web.common.FormatUtility;
import org.tenece.web.data.ApprovalRoute;
import org.tenece.web.data.Company;
import org.tenece.web.data.Employee;
import org.tenece.web.data.Transaction;
import org.tenece.web.data.TransactionAudit;
import org.tenece.web.data.TransactionType;
import org.tenece.web.data.Users;
import org.tenece.web.services.EmployeeService;
import org.tenece.web.services.TransactionService;

/**
 *
 * @author jeffry.amachree
 */
public class TransactionController extends BaseController{
    private TransactionService transactionService = new  TransactionService();
    private EmployeeService employeeService = new EmployeeService();
    /** Creates a new instance of PayrollController */
    public TransactionController() {
    }

    public List<TransactionType> findAllTransactionType(){
        return transactionService.findAllTransactionType();
    }

    public ModelAndView viewEmployeesForApprovalRoute(HttpServletRequest request, HttpServletResponse response){
        //appraisal_employee_view
        ModelAndView view = new ModelAndView("trans_route_employee");
        
        //do search here...
        String cbSearch = request.getParameter("cbSearch");
        String txtSearch = request.getParameter("txtSearch");

        String companyCode = this.getActiveEmployeeCompanyCode(request);

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
    
    public ModelAndView viewApprovalRoute(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("trans_route");

        //get employee id
        String strId = request.getParameter("idx");
        String companyCode = this.getActiveEmployeeCompanyCode(request);

        Company company = getActiveCompany(companyCode);

        if(company.getUseDefaultWorkflow() == 0){
            List<TransactionType> list = transactionService.findAllTransactionType();
            List<Employee> listEmployee = employeeService.findAllEmployeeForBasicByCompany(companyCode);

            //get approval route already define... (if existing)

            view.addObject("result", list);
            view.addObject("employee", listEmployee);
            view.addObject("emp", employeeService.findEmployeeBasicById(Long.parseLong(strId)));
            view.addObject("approvalCout", ConfigReader.NUMBER_OF_APPROVAL_LEVEL);

            List<ApprovalRoute> existingRoute = transactionService.findAllApprovalRoute(Long.parseLong(strId));
            System.out.println(">>>>>>>>>>>>>.." + existingRoute.size());
            view.addObject("existingRoute", existingRoute);
        }else{
            //show default approval route based on reporting-to (direct supervisor) and department structure
            view = new ModelAndView("trans_route_def_ess");
            //generate approval route on the fly
            boolean generated = transactionService.generateDefaultApprovalRoute(Long.parseLong(strId));
            if(generated == false){
                view = new ModelAndView("error");
                view.addObject("message", "Unable to generate approval workflow for ");
                return view;
            }
            
            List<ApprovalRoute> existingRoute = transactionService.findAllApprovalRouteByDefault(Long.parseLong(strId));
            
            view.addObject("existingRoute", existingRoute);
        }
        return view;
    }

    public ModelAndView viewMyApprovalRoute(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("trans_route_ess");

        //get employee id
        Users user = this.getUserPrincipal(request);
        long employeeId = (long)user.getEmployeeId();

        String companyCode = this.getActiveEmployeeCompanyCode(request);
        Company company = getActiveCompany(companyCode);

        if(company.getUseDefaultWorkflow() == 0){
            List<TransactionType> list = transactionService.findAllTransactionType();
            List<Employee> listEmployee = employeeService.findAllEmployeeForBasicByCompany(companyCode);

            //get approval route already define... (if existing)

            view.addObject("result", list);
            view.addObject("employee", listEmployee);
            view.addObject("emp", employeeService.findEmployeeBasicById(employeeId));
            view.addObject("approvalCout", ConfigReader.NUMBER_OF_APPROVAL_LEVEL);

            List<ApprovalRoute> existingRoute = transactionService.findAllApprovalRoute(employeeId);
            System.out.println("ESS >>>>>>>>>>>>.." + existingRoute.size());
            view.addObject("existingRoute", existingRoute);
        }else{
            //show default approval route based on reporting to and department structure
            view = new ModelAndView("trans_route_def_ess");

            //generate approval route on the fly
            boolean generated = transactionService.generateDefaultApprovalRoute(employeeId);
            if(generated == false){
                view = new ModelAndView("error");
                view.addObject("message", "Unable to generate approval workflow for ");
                return view;
            }
            
            List<ApprovalRoute> existingRoute = transactionService.findAllApprovalRouteByDefault(employeeId);
            System.out.println("ESS >>>>>>>>>>>>.." + existingRoute.size());
            view.addObject("existingRoute", existingRoute);
        }

        return view;
    }

    public ModelAndView processApprovalRoute(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("success");

        try{
            int approvallevelCount = ConfigReader.NUMBER_OF_APPROVAL_LEVEL;
            String[] txtTypes = request.getParameterValues("txtType");
            String strEmployeeId = request.getParameter("txtEmp");

            List<ApprovalRoute> routes = new ArrayList<ApprovalRoute>();
            for(int a =0; a < txtTypes.length; a++){

                //get trans type id from parameter object
                String strIds = txtTypes[a];
                //add values to route object
                int index = 0;
                for(int i =0; i <= approvallevelCount; i++){
                    //get route level user
                    String strLevel = request.getParameter("cbLevel" + strIds + "_" + i);

                    if(strLevel != null && (!strLevel.trim().equals(""))){
                        ApprovalRoute route = new ApprovalRoute();
                        route.setTransactionType(Integer.parseInt(strIds));
                        route.setEmployeeId(Integer.parseInt(strEmployeeId));
                        route.setAuthorizer(Integer.parseInt(strLevel));
                        route.setApprovalLevel(i);
                        routes.add(route);
                        System.out.println(strIds + "_" + i + ">>>>>>>>>>>>...." + strLevel);

                        index++;
                    }//:~if
                }//:~ for approvalCount
            }//:~ for TransType

            boolean saved = transactionService.saveApprovalRoute(routes);

            System.out.println("After saving, Status report..." + saved);
            if(saved == true){
                view.addObject("message", "Approval Route Successfully Saved.");
            }else{
                throw new Exception("Unable to save.");
            }
            return view;
        }catch(Exception e){
            view = viewApprovalRoute(request, response);
            view.addObject("message", "Error processing request.");
            return view;
        }
    }

    public ModelAndView processTransaction(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("success");

        //get user information from session
        Users user = this.getUserPrincipal(request);

        //get the exact action for the transaction
        //this action is either reject or approve
        String action = request.getParameter("txtAction");
        if(action == null){
            view = new ModelAndView("error");
            view.addObject("message", "Unable to determine the action to take on transaction");
            return view;
        }
        
        if((!action.trim().equals("A")) && (!action.trim().equals("R"))){
            view = new ModelAndView("error");
            view.addObject("message", "Unable to determine the exact action to take on transaction");
            return view;
        }

        //get trnx id
        String strId = request.getParameter("txtId");
        if(!FormatUtility.isNumber(strId)){
            //return error page
            view = new ModelAndView("error");
            return view;
        }
        long transactionId = Long.parseLong(strId);

        //get transaction
        Transaction transaction = this.transactionService.findTransactionById(transactionId);
        //set the new action that will determine the route to use
        transaction.setStatus(action);
        //call appropriate method
        try{
            boolean saved = transactionService.processTransaction(transaction, user.getEmployeeId());
            if(saved == false){
                throw new Exception("Error Processing Transaction Request.");
            }
            
            long nextAuthorizer = transactionService.findNextApprovingOfficer(transaction.getId());
            if(nextAuthorizer == 0){
                if(transaction.getStatus().trim().equals("R")){
                    view.addObject("message", "Appraisal Transaction Successfully Rejected.");
                }else{
                    view.addObject("message", "Transaction was successfully approved.");
                }
            }else if(nextAuthorizer == -1){
                view.addObject("message", "Transaction was successfully forwarded to the next authorizing officer.");
            }else{
                Employee supervisor = employeeService.findEmployeeBasicById(nextAuthorizer);
                view.addObject("message", "Transaction has been forwarded to " + supervisor.getFullName() + " for approval.");
            }
            return view;
        }catch(Exception e){
            e.printStackTrace();
            view = new ModelAndView("error");
            view.addObject("message", e.getMessage());
            return view;
        }
        
    }

    public ModelAndView viewMyTransactionHistory(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("transaction/transaction_audit");

        String strId = request.getParameter("idx");
        long transactionId = Long.parseLong(strId);
        List<TransactionAudit> list = transactionService.findAllTransactionAudit(transactionId);

        view.addObject("transaction", transactionService.findTransactionById(transactionId));
        view.addObject("list", list);
        return view;
    }

    public ModelAndView viewMyTransactionStatus(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("transaction/transaction_status");

        //get user information from session
        Users user = this.getUserPrincipal(request);
        
        //do search here...
        String cbSearch = request.getParameter("cbSearch");
        String txtSearch = request.getParameter("txtSearch");

        //get list of employee
        List<Transaction> tranxStatus = null;
        if(cbSearch == null || txtSearch == null){
            tranxStatus = transactionService.findAllTransactionStatusByEmployee(user.getEmployeeId());
        }else{
            tranxStatus = transactionService.findAllTransactionStatusByEmployee(user.getEmployeeId(), cbSearch, txtSearch);
        }

        view.addObject("list", tranxStatus);
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

}
