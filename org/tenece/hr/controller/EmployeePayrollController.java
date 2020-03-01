/*
 * (c) Copyright 2009, 2010 The Tenece Professional Services.
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.tenece.hr.scheduler.PayrollCalculationTrigger;
import org.tenece.web.common.BaseController;
import org.tenece.web.data.Employee;
import org.tenece.web.data.Employee_Payroll;
import org.tenece.web.data.EmployeeBank;
import org.tenece.web.data.Payroll_PayPeriod;
import org.tenece.web.data.Payroll_AccountGroup;
import org.tenece.web.data.Payroll_PayEvent;

import org.tenece.web.data.SalaryGrade;
import org.tenece.web.data.Users;
import org.tenece.web.services.EmployeePayrollService;
import org.tenece.web.services.PayrollConfigurationService;
import org.tenece.web.services.EmployeeService;
import org.tenece.web.services.PayrollService;
import org.tenece.web.services.ReportService;
import org.tenece.web.services.SetupService;

/**
 * Tenece Professional Services Limited
 * @author amachree
 */

public class EmployeePayrollController extends BaseController{
    private PayrollCalculationTrigger payrollCalculationTrigger;
    private EmployeePayrollService employeePayrollService;
    private PayrollConfigurationService payrollConfigurationService;
    private EmployeeService employeeService;
    private PayrollService payrollService;
    private ReportService reportService;

    public int calculatePayrollForEmployee(long employeeId){
        
        Payroll_PayPeriod period = payrollConfigurationService.findActivePayroll_PayPeriod();
        EmployeeBank bank = employeeService.findEmployeeBankDetail(employeeId);
        
        int saved = getPayrollService().calculateEmployeePayStub(bank.getEmployeeId(), bank.getPolicyId(), period.getId());
        return saved;
    }

    public ModelAndView calculateAllActiveEmployeePayroll(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("payroll/payroll_payperiod_calculation_msg");

        String companyCode = this.getActiveEmployeeCompanyCode(request);

        Payroll_PayPeriod period = payrollConfigurationService.findActivePayroll_PayPeriodByCompany(companyCode);

        int saved = getPayrollService().calculateAllPayStubByCompany(period.getId(), companyCode);
        if(saved == 0){
            view.addObject("message", "<em style='color:red'>Unable to complete payroll calculation. Please try again.</em>");
        }else{
            view.addObject("message", "Payroll calculation completed successfully.");
        }
        return view;
    }

    public ModelAndView scheduleCalculationForAllActiveEmployeePayroll(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("payroll/payroll_payperiod_calculation_msg");

        String companyCode = this.getActiveEmployeeCompanyCode(request);

        Payroll_PayPeriod period = payrollConfigurationService.findActivePayroll_PayPeriodByCompany(companyCode);

        try{
            payrollCalculationTrigger.setPayrollService(payrollService);
            payrollCalculationTrigger.startService(companyCode);
            view.addObject("message", "Payroll scheduler started successfully. Check 'Active Scheduled Job' under report for status update.");
        }catch(Exception e){
            view.addObject("message", "<em style='color:red'>Unable to start payroll calculation. Please try again.</em>");
        }
        
        return view;
    }

    public ModelAndView previewPayroll_Settings(HttpServletRequest request, HttpServletResponse response){
        
        ModelAndView view = new ModelAndView("payroll/payroll_employee_setting");
        
        String param = request.getParameter("idx");
        if(param == null){
            param = "0";
        }
        return previewPayroll_Settings(param);
    }

    public ModelAndView previewPayroll_Settings(String param){

        ModelAndView view = new ModelAndView("payroll/payroll_employee_setting");
        try{

            //check if the value is integer
            long id = Long.parseLong(param);
            view.addObject("idx", id);
            //get employee info
            Employee employee = getEmployeeService().findEmployeeBasicById((int)id);
            view.addObject("employee", employee);
            //System.out.println(">>>>" + employee.getFirstName());
            
            //check if bank and policy has been defined previously
            EmployeeBank bank = getEmployeeService().findEmployeeBankDetail(id);
            if(bank == null || bank.getPolicyId() <= 0){
                view.addObject("showOthers", 0);
            }else{
                view.addObject("showOthers", 1);
            }

        }catch(Exception er){
            er.printStackTrace();
            //return viewAllPayroll_Policy(request, response);
        }
        //return the preview view
        return view;
    }
    
    //editPayItemsPayroll_Policy
    public ModelAndView editPayrollPayItems(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("payroll/payroll_employee_payitem_edit");

        //String companyCode = this.getActiveEmployeeCompanyCode(request);

        String param = request.getParameter("idx");
        if(param == null){
            param = "0";
        }
        long id = Long.parseLong(param);
        String companyCode = this.employeeService.findEmployeeBasicById(id).getCompanyCode();

        view.addObject("employeeId", param);
        view.addObject("items", employeePayrollService.findAllPayroll_PayItemByID(id));
        view.addObject("newItem", new Employee_Payroll.PayItem());
        view.addObject("accountList", getPayrollConfigurationService().findAllPayroll__PayAccountByCompany(companyCode));
        return view;

    }

    public ModelAndView editPayrollPayItems(long id, String companyCode){
        ModelAndView view = new ModelAndView("payroll/payroll_employee_payitem_edit");

        view.addObject("employeeId", String.valueOf(id));
        view.addObject("items", employeePayrollService.findAllPayroll_PayItemByID(id));
        view.addObject("newItem", new Employee_Payroll.PayItem());
        view.addObject("accountList", getPayrollConfigurationService().findAllPayroll__PayAccountByCompany(companyCode));
        return view;

    }

    public ModelAndView savePayrollPayItems(HttpServletRequest request, HttpServletResponse response){
        //String companyCode = this.getActiveEmployeeCompanyCode(request);
        //get old records
        String[] accounts = request.getParameterValues("cbAccount");
        String[] amounts = request.getParameterValues("txtAmount");
        String strEmployeeId = request.getParameter("txtId");

        String companyCode = this.employeeService.findEmployeeBasicById(Long.parseLong(strEmployeeId)).getCompanyCode();

        //check again for bug
        if(strEmployeeId == null || strEmployeeId.trim().equals("")){
            return editPayrollPayItems(Long.parseLong(strEmployeeId), companyCode);
        }

        //get current period
        Payroll_PayPeriod period = getPayrollConfigurationService().findActivePayroll_PayPeriodByCompany(companyCode);

        List<Employee_Payroll.PayItem> items = new ArrayList<Employee_Payroll.PayItem>();
        //check if there was any previous record
        if(accounts != null){
            for(int i = 0; i < accounts.length; i++){

                Employee_Payroll.PayItem item = new Employee_Payroll.PayItem();
                item.setAccountId(Integer.parseInt(accounts[i]));
                item.setItemValue(Double.parseDouble(amounts[i]));
                item.setEmployeeId(Long.parseLong(strEmployeeId));
                item.setFromPeriodId(period.getId());
                items.add(item);
            }
        }//
        //check if new record was specified
        String newAccount = request.getParameter("cbNewAccount");
        String newAmount = request.getParameter("txtNewAmount");

        if((newAccount.trim().equals("") || newAccount.trim().equals("0"))
                && (newAmount.trim().equals("") || newAmount.trim().equals("0"))){
            //nothing was selected
        }else{
            Employee_Payroll.PayItem item = new Employee_Payroll.PayItem();
            item.setAccountId(Integer.parseInt(newAccount));
            item.setItemValue(Double.parseDouble(newAmount));
            item.setEmployeeId(Long.parseLong(strEmployeeId));
            item.setFromPeriodId(period.getId());
            items.add(item);
        }

        int saved = employeePayrollService.updatePayroll_PayItems(items);
        if(saved > 0){
            ModelAndView view = editPayrollPayItems(Long.parseLong(strEmployeeId), companyCode);
            view.addObject("message", "Policy pay items saved successfully.");
            return view;
        }else{
            ModelAndView view = editPayrollPayItems(Long.parseLong(strEmployeeId), companyCode);
            view.addObject("message", "Unable to save pay items for policy.");
            return view;
        }

    }

    public ModelAndView deletePayrollPayItem(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("");
        List<Integer> ids = new ArrayList<Integer>();
        String strEmployeeId = "";
        //String companyCode = this.getActiveEmployeeCompanyCode(request);
        //get policy id
        strEmployeeId = request.getParameter("pidx");

        String companyCode = this.employeeService.findEmployeeBasicById(Long.parseLong(strEmployeeId)).getCompanyCode();

        try{
            //get mode of request
            String mode = request.getParameter("mode");

            if(strEmployeeId == null || strEmployeeId.trim().equals("")){
                view = editPayrollPayItems(request, response);
                view.addObject("message", "Unable to locate Employee based on indentifier.");
                return view;
            }
            //check if mode is for more than one record
            //get mode, if mode is equals 1, then process for multiple
            if(mode != null && mode.trim().equals("1")){
                String[] args = request.getParameterValues("_chk");
                for(String id : args){
                    ids.add(Integer.parseInt(id));
                }
            }else{//then it is zero
                String id = request.getParameter("id");
                ids.add(Integer.parseInt(id));
            }
            for(int id : ids){
                employeePayrollService.deletePayroll_PayItem(Long.parseLong(strEmployeeId), id);
            }
           //after delete, reload list back
            return editPayrollPayItems(Long.parseLong(strEmployeeId), companyCode);
        }catch(Exception e){
            view = editPayrollPayItems(Long.parseLong(strEmployeeId), companyCode);
            view.addObject("message", "Unable to delete. System Error occured.");
            return view;
        }
    }

    public ModelAndView editPayrollAttributes(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("payroll/payroll_employee_attribute_edit");

        String param = request.getParameter("idx");
        if(param == null){
            param = "0";
        }
        long id = Long.parseLong(param);

        view.addObject("employeeId", param);
        view.addObject("items", employeePayrollService.findAllPayroll_AttributeByID(id));
        return view;

    }

    public ModelAndView editPayrollAttributes(long id){
        ModelAndView view = new ModelAndView("payroll/payroll_employee_attribute_edit");

        view.addObject("employeeId", String.valueOf(id));
        view.addObject("items", employeePayrollService.findAllPayroll_AttributeByID(id));
        return view;

    }

    public ModelAndView savePayrollAttributes(HttpServletRequest request, HttpServletResponse response){
        String companyCode = this.getActiveEmployeeCompanyCode(request);

        //get old records
        String[] attributeIds = request.getParameterValues("txtHAttributeId");
        String[] amounts = request.getParameterValues("txtAmount");
        String strEmployeeId = request.getParameter("txtId");

        //check again for bug
        if(strEmployeeId == null || strEmployeeId.trim().equals("")){
            return editPayrollAttributes(Long.parseLong(strEmployeeId));
        }

        //get current period
        Payroll_PayPeriod period = getPayrollConfigurationService().findActivePayroll_PayPeriodByCompany(companyCode);

        List<Employee_Payroll.Attribute> attributes = new ArrayList<Employee_Payroll.Attribute>();
        //check if there was any previous record
        if(attributeIds != null){
            for(int i = 0; i < attributeIds.length; i++){

                Employee_Payroll.Attribute attribute = new Employee_Payroll.Attribute();
                attribute.setAttributeId(Integer.parseInt(attributeIds[i]));
                attribute.setValue_Amount(Double.parseDouble(amounts[i]));
                attribute.setEmployeeId(Long.parseLong(strEmployeeId));
                attribute.setFromPeriodId(period.getId());
                attributes.add(attribute);
            }
        }//
        
        int saved = employeePayrollService.updatePayroll_Attributes(attributes);
        if(saved > 0){
            ModelAndView view = editPayrollAttributes(Long.parseLong(strEmployeeId));
            view.addObject("message", "Policy Attribute(s) saved successfully.");
            return view;
        }else{
            ModelAndView view = editPayrollAttributes(Long.parseLong(strEmployeeId));
            view.addObject("message", "Unable to save attribute for employee.");
            return view;
        }

    }

    public ModelAndView viewPayrollPayStub(HttpServletRequest request, HttpServletResponse response){
        String param = request.getParameter("idx");
        if(param == null){
            param = "0";
        }
        return viewPayrollPayStub(param);
    }
    public ModelAndView viewPayrollPayStub(String param){
        ModelAndView view = new ModelAndView("payroll/payroll_employee_paystub_edit");

        long id = Long.parseLong(param);

        String companyCode = employeeService.findEmployeeBasicById(id).getCompanyCode();

        view.addObject("employeeId", param);
        //get all account group defined on the system
        List<Payroll_AccountGroup> groupList = payrollConfigurationService.findAllPayroll_AccountGroupByCompany(companyCode);
        view.addObject("groupList", groupList);
        //get payevents already calculated
        Payroll_PayPeriod period = payrollConfigurationService.findActivePayroll_PayPeriodByCompany(companyCode);
        List<Payroll_PayEvent> events = payrollService.findAllPayEvent_ByEmployee(id, period.getId());
        view.addObject("eventList", events);
        return view;

    }

    public ModelAndView calculatePayrollEventForEmployee(HttpServletRequest request, HttpServletResponse response){
        //ModelAndView view = new ModelAndView("success");
        System.out.println("1 =========>");
        //get employee Id
        String strEmpId = request.getParameter("txtId");
        if(strEmpId == null){
            ModelAndView view = new ModelAndView("error_mini");
            return view;
        }
        long employeeId = Long.parseLong(strEmpId);
        int saved = calculatePayrollForEmployee(employeeId);
        System.out.println("=========>" + saved);
        return viewPayrollPayStub(strEmpId);
    }

    public ModelAndView viewPayrollPaySlips(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("payroll/payroll_employee_payslips_view");

        String param = request.getParameter("idx");
        if(param == null){
            param = "0";
        }
        long id = Long.parseLong(param);

        view.addObject("employeeId", param);
        
        view.addObject("summaryList",  payrollService.findAllPayPeriodSummary_ByEmployee(id));
        return view;
    }

    public ModelAndView stubView_PayrollEventForEmployee(HttpServletRequest request, HttpServletResponse response){
        String periodId = request.getParameter("idx");
        String status = request.getParameter("status");
        String param = request.getParameter("emp");
        if(param == null){
            param = "0";
        }
        if(periodId == null){
            periodId = "0";
        }
        ModelAndView view = new ModelAndView("payroll/payroll_employee_paystub_view");

        long id = Long.parseLong(param);

        view.addObject("employeeId", param);
        //get all account group defined on the system
        List<Payroll_AccountGroup> groupList = payrollConfigurationService.findAllPayroll_AccountGroup();
        view.addObject("groupList", groupList);
        //get payevents already calculated and check if it will be from archive
        boolean useArchive = true;
        if(status.trim().equals("active")){
            useArchive = false;
        }

        int iPeriodId = Integer.parseInt(periodId);
        List<Payroll_PayEvent> events = payrollService.findAllPayEvent_ByEmployee(id, iPeriodId, useArchive);
        view.addObject("eventList", events);
        return view;
    }

    public ModelAndView reportPayrollEventForEmployee(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("payroll/payslip_view");

        String periodId = request.getParameter("idx");
        String status = request.getParameter("status");
        String param = request.getParameter("emp");
        if(param == null){
            param = "0";
        }
        if(periodId == null){
            periodId = "0";
        }

        long id = Long.parseLong(param);

        view.addObject("employeeId", param);
        view.addObject("periodId", periodId);
        //get report to use
        try{
            view.addObject("report", getReportService().findReportByID("PAYSLIP_EMP"));
        }catch(Exception ex){
            view.addObject("message", "Invalid Report Type. Contact Your System Administrator for Assistance.");
        }
        return view;
    }

    /* **************** Employee Bank and Policy Setting ************** */
    
    public ModelAndView bankData(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("/employee/bank_edit");

        String id = request.getParameter("identity");

        //System.out.println("----ID>>>>>>>.for bank to: " + id);
        
        view.addObject("empid", id);
        view.addObject("policyList", payrollConfigurationService.findAllPayroll_Policies());
        view.addObject("bank", getEmployeeService().findEmployeeBankDetail(Long.parseLong(id)));
        return view;
    }
    public ModelAndView bankData(long employeeId){
        ModelAndView view = new ModelAndView("employee_bank");

        //System.out.println("----employeeId>>>>>>>.for bank to: " + employeeId);

        view.addObject("empid", String.valueOf(employeeId));
        view.addObject("policyList", payrollConfigurationService.findAllPayroll_Policies());
        view.addObject("bank", getEmployeeService().findEmployeeBankDetail(employeeId));
        return view;
    }
    
    public ModelAndView processBank(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("message");
        String operation = request.getParameter("txtMode");
        String employeeNumber = request.getParameter("txtEId");
        //operation mode: N= New, E=Edit
        if(operation.trim().equals("N")){
            EmployeeBank emp = new EmployeeBank();
            String bank = request.getParameter("txtBank");
            String branch = request.getParameter("txtBranch");
            String accountName = request.getParameter("txtAcctName");
            String accountNo = request.getParameter("txtAcctNumber");
            String accountType = request.getParameter("rdType");
            employeeNumber = request.getParameter("txtEId");
            String strPolicyId = request.getParameter("cbPolicy");

            emp.setEmployeeId(Integer.parseInt(employeeNumber));
            emp.setBankName(bank);
            emp.setBranch(branch);
            emp.setAccountName(accountName);
            emp.setAccountNumber(accountNo);
            emp.setAccountType(accountType);
            emp.setPolicyId(Integer.parseInt(strPolicyId));

            getEmployeeService().updateEmployeeBankDetail(emp, getEmployeeService().MODE_INSERT);
            view = previewPayroll_Settings(employeeNumber);
            view.addObject("message", "Record Saved Successfully.");
            return view;
        }else if(operation.trim().equals("E")){
            EmployeeBank emp = new EmployeeBank();
            String bank = request.getParameter("txtBank");
            String branch = request.getParameter("txtBranch");
            String accountName = request.getParameter("txtAcctName");
            String accountNo = request.getParameter("txtAcctNumber");
            String accountType = request.getParameter("rdType");
            employeeNumber = request.getParameter("txtEId");
            String strPolicyId = request.getParameter("cbPolicy");

            emp.setEmployeeId(Integer.parseInt(employeeNumber));
            emp.setBankName(bank);
            emp.setBranch(branch);
            emp.setAccountName(accountName);
            emp.setAccountNumber(accountNo);
            emp.setAccountType(accountType);
            emp.setPolicyId(Integer.parseInt(strPolicyId));

            getEmployeeService().updateEmployeeBankDetail(emp, getEmployeeService().MODE_UPDATE);

            view.addObject("message", "Record Saved Successfully.");
        }

        view = bankData(Long.parseLong(employeeNumber));
        return view;
    }

    /* ============ ESS Windows =================== */
    public ModelAndView viewESSPayrollPaySlips(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("payroll/payroll_ess_payslips_view");

        Users essUser = getUserPrincipal(request);
        long id = (long)essUser.getEmployeeId();

        view.addObject("employeeId", id);

        view.addObject("summaryList",  payrollService.findAllPayPeriodSummary_ByEmployee(id));
        return view;
    }


    /**
     * @return the employeePayrollService
     */
    public EmployeePayrollService getEmployeePayrollService() {
        return employeePayrollService;
    }

    /**
     * @param employeePayrollService the employeePayrollService to set
     */
    public void setEmployeePayrollService(EmployeePayrollService employeePayrollService) {
        this.employeePayrollService = employeePayrollService;
    }

    /**
     * @return the payrollConfigurationService
     */
    public PayrollConfigurationService getPayrollConfigurationService() {
        return payrollConfigurationService;
    }

    /**
     * @param payrollConfigurationService the payrollConfigurationService to set
     */
    public void setPayrollConfigurationService(PayrollConfigurationService payrollConfigurationService) {
        this.payrollConfigurationService = payrollConfigurationService;
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
     * @return the payrollService
     */
    public PayrollService getPayrollService() {
        return payrollService;
    }

    /**
     * @param payrollService the payrollService to set
     */
    public void setPayrollService(PayrollService payrollService) {
        this.payrollService = payrollService;
    }

    /**
     * @return the payrollCalculationTrigger
     */
    public PayrollCalculationTrigger getPayrollCalculationTrigger() {
        return payrollCalculationTrigger;
    }

    /**
     * @param payrollCalculationTrigger the payrollCalculationTrigger to set
     */
    public void setPayrollCalculationTrigger(PayrollCalculationTrigger payrollCalculationTrigger) {
        this.payrollCalculationTrigger = payrollCalculationTrigger;
    }

    /**
     * @return the reportService
     */
    public ReportService getReportService() {
        return reportService;
    }

    /**
     * @param reportService the reportService to set
     */
    public void setReportService(ReportService reportService) {
        this.reportService = reportService;
    }

    
}
