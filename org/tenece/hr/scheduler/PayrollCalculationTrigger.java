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

package org.tenece.hr.scheduler;

import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SimpleTrigger;
import org.tenece.hr.data.dao.BaseDAO;
import org.tenece.hr.data.dao.EmployeeBankDetailDAO;
import org.tenece.hr.data.dao.PayrollConfigurationDAO;
import org.tenece.hr.data.dao.PayrollDAO;
import org.tenece.web.data.EmployeeBank;
import org.tenece.web.data.Payroll_PayPeriod;
import org.tenece.web.data.Scheduler;
import org.tenece.web.services.PayrollService;
/**
 * Tenece Professional Services Limited
 * @author amachree
 */
public class PayrollCalculationTrigger extends BaseDAO implements Job {

    private SimpleTrigger trigger = null;
    private JobDetail job = null;
    private int delay = 10000;
    private String triigerName, jobName = "";
    private PayrollService payrollService;

    //company code to use
    private String companyCode = "";

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        System.out.println("Running Payroll Calculator Scheduler Instance...");
        try{
            int calculated = calculatePayStubForAllEmployee(companyCode);
            System.out.println("Total records calculated: " + calculated);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                stopService();
            } catch (Exception ex) {
                //Logger.getLogger(PayrollCalculationTrigger.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public boolean startService(String companyCode) throws Exception{

        TaskManager taskManager = TaskManager.getInstance();

        job = new JobDetail(companyCode + "_payrollCalculator", //job name
            taskManager.getScheduler().DEFAULT_GROUP, //job group
            PayrollCalculationTrigger.class); //job class to execute

        //do not repeat the trigger...
        trigger = new SimpleTrigger(companyCode + "_payrollCalculatorTrigger",
            taskManager.getScheduler().DEFAULT_GROUP,
            new Date(), null, //start now and dont end
            0, //SimpleTrigger.STATE_NORMAL,
            0L); //delay);//10 seconds

        taskManager.getScheduler().scheduleJob(job, trigger);

        return true;
    }

    public void stopService() throws Exception{
        try{
            System.out.println("Stopping Trigger: (payrollCalculator)" + trigger);
            //trigger.setEndTime(new Date());
            TaskManager taskManager = TaskManager.getInstance();
            taskManager.getScheduler().unscheduleJob("payrollCalculator", taskManager.getScheduler().DEFAULT_GROUP);
            //taskManager.getScheduler().removeTriggerListener("DataCollectorTrigger");
            taskManager.getScheduler().deleteJob("payrollCalculator", taskManager.getScheduler().DEFAULT_GROUP);
        }catch(Exception e){}
    }

    private int calculatePayStubForAllEmployee(String companyCode) throws Exception{

        PayrollConfigurationDAO configDAO = new PayrollConfigurationDAO();
        //put record into scheduler table for status
        //Scheduler scheduler = configDAO.getSchedulerByCode("PAY02");
        //if(scheduler == null || scheduler.getStatus().trim().equals("A")){
        configDAO.deleteScheduler("PAY02");
        int ins  = configDAO.createScheduler("PAY02");
        //}
        int rows = 0;
        Connection connection = null;
        try{
            //int rows = new PayrollService().calculateAllPayStub();
            Payroll_PayPeriod period = configDAO.getActivePayroll_PayPeriodByCompany(companyCode);
            List<EmployeeBank> employeeWithPolicy = new EmployeeBankDetailDAO().getAllValidEmployeeBankDetailByCompany(companyCode);
            
            //get connection and start a new transaction
            connection = this.getConnection(false);
            //start transaction
            startTransaction(connection);
            for(EmployeeBank employee : employeeWithPolicy){
                int _rows = new PayrollDAO().generatePayEventFromPolicy(employee.getEmployeeId(), employee.getPolicyId(), period.getId(), this.getConnection(false));
                rows = rows + _rows;
            }
            commitTransaction(connection);
            
        }catch(Exception er){
            er.printStackTrace();
            rollbackTransaction(connection);
        }finally{
            //update scheduler
            configDAO.updateSchedulerStatus("PAY02");
        }
        return rows;
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
}
