
/*
 * (c) Copyright 2010 The Tenece Professional Services.
 *
 * Created on 6 February 2009, 09:57
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

package org.tenece.web.services;

import java.util.ArrayList;
import java.util.List;
import org.tenece.hr.data.dao.PayrollDAO;
import org.tenece.hr.data.dao.PayrollConfigurationDAO;
import org.tenece.web.data.Employee_Payroll;
import org.tenece.web.data.Payroll_PayEvent;
import org.tenece.web.data.Payroll_PayAccount;
import org.tenece.web.data.Payroll_PayPeriod;
import org.tenece.web.data.Employee;
import org.tenece.web.data.EmployeeBank;
import org.tenece.web.data.Scheduler;

/**
 *
 * @author jeffry.amachree
 */
public class PayrollService extends BaseService{
    private PayrollDAO payrollDAO;
    private PayrollConfigurationDAO payrollConfigurationDAO;
    private long employeeId;
    private int periodId;

    //fixed objects
    public static int INPUT_TYPE_AMOUNT = 0;
    public static int INPUT_TYPE_MINUTE = 1;
    public static int INPUT_TYPE_DAY = 2;
    public static int INPUT_TYPE_UNIT = 3;

    /** Creates a new instance of PayrollService */
    public PayrollService() {
    }

    /**
     *
     * @param accountGroupName
     * @return
     * @throws Exception - This is required because there is need to know when an error has occured
     */
    public float getPeriodSum(String accountGroupName) throws Exception{
        //call DAO and pass empid and periodId as parameter

        //check if value is valid number otherwise return 0

        return 0;
    }

    /**
     * This method is being initiated by Quartz Scheduler - It will check and know if calcultion was done before
     */
    public void calculateAllPayStub(){
        //check if payment date has reached.
        Scheduler scheduler = getPayrollConfigurationDAO().getSchedulerByCode("PAY01");
        if(scheduler == null || scheduler.getStatus().trim().equals("A")){
            getPayrollConfigurationDAO().deleteScheduler("PAY01");
            int i  = getPayrollConfigurationDAO().createScheduler("PAY01");
        }

        Payroll_PayPeriod period = getPayrollConfigurationDAO().getActivePayroll_PayPeriod();
        int rows = getPayrollDAO().generatePayEventForActiveEmployees(period.getId());
        getPayrollConfigurationDAO().updateSchedulerStatus("PAY01");
        System.out.println(rows + " records generated===================>>>Finish Calculation of Payroll for employees.");
    }
    public int calculateAllPayStub(int periodId){
        return getPayrollDAO().generatePayEventForActiveEmployees(periodId);
    }
    public int calculateAllPayStubByCompany(int periodId, String companyCode){
        return getPayrollDAO().generatePayEventForActiveEmployeesByCompany(periodId, companyCode);
    }
    public int calculateEmployeePayStub(long employeeId, int policyId, int periodId){
        //create payevent from policy
        return getPayrollDAO().generatePayEventFromPolicy(employeeId, policyId, periodId);
    }
    
    public int createPayEventFromPolicy(long employeeId, int policyId, int periodId){
        return getPayrollDAO().createPayEventFromPolicy(employeeId, policyId, periodId);
    }

    public boolean calculatePayEvents(long employeeId, int periodId){
        try{
            //intantiate list of event object to use..
            List<Payroll_PayEvent> events = new ArrayList<Payroll_PayEvent>();

            //get list of account current inserted for employee
            List<Payroll_PayAccount> accounts = getPayrollDAO().getPayEventAccount_FromPolicy(employeeId, periodId);

            //go thru list and make calculations where needed
            for(Payroll_PayAccount account : accounts){

                Payroll_PayEvent event = new Payroll_PayEvent();

                double amount = account.getItemValue();
                double unit_Price = 0;
                int quantity = 0;
                int inputType = account.getInputType();
                String formula = account.getFormula();
                //check if formula is null from DB
                formula = formula == null? "0" : formula;

                //check the type of input type to determine calculation
                if(inputType == PayrollService.INPUT_TYPE_AMOUNT){
                    if(amount != 0){
                        amount = account.getItemValue();
                    }else{
                        amount = payrollDAO.evaluateFormula(formula, employeeId, periodId);
                    }
                }else if(inputType == PayrollService.INPUT_TYPE_MINUTE){
                    quantity = (int)Math.round(account.getItemValue());
                    int hours = (int) Math.round(quantity / 60);
                    unit_Price = payrollDAO.evaluateFormula(formula, employeeId, periodId);
                    amount = hours * unit_Price;
                }else if (inputType == PayrollService.INPUT_TYPE_DAY || inputType == PayrollService.INPUT_TYPE_UNIT){
                    quantity = (int)Math.round(account.getItemValue());
                    unit_Price = payrollDAO.evaluateFormula(formula, employeeId, periodId);
                    amount = quantity * unit_Price;
                }
                event.setEmployeeId(employeeId);
                event.setPeriodId(periodId);
                event.setAccountId(account.getId());
                event.setAmount(amount);
                event.setUnitPrice(unit_Price);
                event.setQuantity(quantity);
                //int isPayable = getPayrollDAO().checkIfAccount_IsPayable(account.getId());
                //if(isPayable == -1) { throw new Exception("Invalid Query to get Payable Flag for Account"); }
                //sevent.setPayable(isPayable);

                //add event to the list (events)
                events.add(event);
            }
            //remember if qty and unit price is zero... null must be inserted for their values into the db
            int saved = getPayrollDAO().updatePayEvent(events);
            if(saved == 0){ throw new Exception("Error saving. Unable to complete payevent task. returned zero."); }
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    public List<Payroll_PayEvent> findAllPayEvent_ByEmployee(long employeeId, int periodId){
        return getPayrollDAO().getPayEvent_ByEmployee(employeeId, periodId);
    }
    public List<Payroll_PayEvent> findAllPayEvent_ByEmployee(long employeeId, int periodId, boolean useArchive){
        return getPayrollDAO().getPayEvent_ByEmployee(employeeId, periodId, useArchive);
    }

    public List<Payroll_PayPeriod.PaymentSummary> findAllPayPeriodSummary_ByEmployee(long employeeId){
        return getPayrollDAO().getAllPaymentSummary_ForAllPeriod(employeeId);
    }
    /**
     * @return the employeeId
     */
    public long getEmployeeId() {
        return employeeId;
    }

    /**
     * @param employeeId the employeeId to set
     */
    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * @return the periodId
     */
    public int getPeriodId() {
        return periodId;
    }

    /**
     * @param periodId the periodId to set
     */
    public void setPeriodId(int periodId) {
        this.periodId = periodId;
    }

    /**
     * @return the payrollDAO
     */
    public PayrollDAO getPayrollDAO() {
        return payrollDAO;
    }

    /**
     * @param payrollDAO the payrollDAO to set
     */
    public void setPayrollDAO(PayrollDAO payrollDAO) {
        this.payrollDAO = payrollDAO;
    }

    /**
     * @return the payrollConfigurationDAO
     */
    public PayrollConfigurationDAO getPayrollConfigurationDAO() {
        return payrollConfigurationDAO;
    }

    /**
     * @param payrollConfigurationDAO the payrollConfigurationDAO to set
     */
    public void setPayrollConfigurationDAO(PayrollConfigurationDAO payrollConfigurationDAO) {
        this.payrollConfigurationDAO = payrollConfigurationDAO;
    }


}
