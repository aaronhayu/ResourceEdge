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

package org.tenece.hr.data.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import org.tenece.hr.db.DatabaseQueryLoader;
import org.tenece.web.data.Employee_Payroll;
import org.tenece.web.data.Payroll_PayEvent;
import org.tenece.web.data.Payroll_PayPeriod;
import org.tenece.web.data.Payroll_PayAccount;
import org.tenece.web.services.PayrollService;
import org.tenece.web.data.EmployeeBank;
import org.tenece.web.services.EmployeeService;
/**
 * Tenece Professional Services Limited
 * @author amachree
 */
public class PayrollDAO extends BaseDAO{

    private PayrollService payrollService;
    private EmployeeService employeeService;
    private static Connection transactionConnection;

    public Employee_Payroll.Attribute getEmployeeAttribute(long employeeId, String attributeCode, int periodId){
        return getEmployeeAttribute(employeeId, attributeCode, periodId, false);
    }
    public Employee_Payroll.Attribute getEmployeeAttribute(long employeeId, String attributeCode, int periodId, boolean useExistingTrnxConnection){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        Employee_Payroll.Attribute _attribute = new Employee_Payroll.Attribute();

        try{
            param.add(employeeId); type.add("NUMBER");
            param.add(attributeCode); type.add("STRING");
            param.add(periodId); type.add("NUMBER");

            if(useExistingTrnxConnection == false){
                connection = this.getConnection(true);
            }else{
                if(transactionConnection != null && transactionConnection.isClosed() == false){
                    connection = transactionConnection;
                }else{
                   connection = this.getConnection(true);
                }
            }
            ResultSet rst = this.executeParameterizedQuery(connection,
                    DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_PAYEVENT_ATTRIBUTE_SELECT_BY_CODE_PERIOD_AND_EMPLOYEE,
                    param, type);

            while(rst.next()){
                Employee_Payroll.Attribute attribute = new Employee_Payroll.Attribute();
                attribute.setValue_Amount(rst.getDouble("value_amount"));
                attribute.setAttributeName(rst.getString("description"));

                _attribute = attribute;
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                if(useExistingTrnxConnection == false){
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return _attribute;
    }

    public int createPayEventFromPolicy(long employeeId, int policyId, int periodId){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            connection = this.getConnection(false);
            //start transaction
            startTransaction(connection);

            //delete all pending record
            param.add(employeeId); type.add("NUMBER");
            int del = this.executeParameterizedUpdate(connection, 
                    DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_EMPLOYEE_PAYEVENT_DELETE_ALL,
                        param, type);

            //re-initialize vactors
            param = new Vector();
            type = new Vector();
            //get all payitems to use for employee
            //use the parameter to get default events for user
            param.add(policyId); type.add("NUMBER");
            param.add(periodId); type.add("NUMBER");
            param.add(periodId); type.add("NUMBER");
            param.add(employeeId); type.add("NUMBER"); //used for second query in union
            param.add(periodId); type.add("NUMBER"); //used for second query in union
            param.add(periodId); type.add("NUMBER"); //used for second query in union
            ResultSet rst = this.executeParameterizedQuery(connection,
                    DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_PAYEVENT_INITIAL_ACCOUNT_AND_AMOUNT_SELECT,
                        param, type);

            List<Payroll_PayEvent> events = new ArrayList<Payroll_PayEvent>();
            while(rst.next()){
                Payroll_PayEvent event = new Payroll_PayEvent();
                event.setAccountId(rst.getInt("accountId"));
                Double value = rst.getDouble("amount");
                String description = rst.getString("description");
                String strGroupId = rst.getString("groupid");
                if(value == null){ value = new Double(0); }
                event.setItemValue(value.doubleValue());
                event.setEmployeeId(employeeId);
                event.setPeriodId(periodId);
                event.setDerived(1);
                event.setRegisteredDate(new Date());
                event.setNarration(description);
                event.setPayable(Integer.parseInt(strGroupId));
                //add the event to the list
                events.add(event);
            }

            //initiate the new record
            int counter = 0;
            for(Payroll_PayEvent event : events){
                //re-initialize vactors
                param = new Vector();
                type = new Vector();
                //set parameters in the appropriate order
                param.add(event.getEmployeeId()); type.add("NUMBER");
                param.add(event.getPeriodId()); type.add("NUMBER");
                param.add(event.getAccountId()); type.add("NUMBER");
                param.add(event.getDerived()); type.add("NUMBER");
                param.add(event.getItemValue()); type.add("AMOUNT");
                param.add(event.getRegisteredDate()); type.add("DATE");
                param.add(event.getNarration()); type.add("STRING");
                param.add(event.getPayable()); type.add("NUMBER");

                int i = this.executeParameterizedUpdate(connection, 
                        DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_PAYEVENT_INITIAL_INSERT,
                        param, type);
                counter = counter + i;
            }
            if(counter != events.size()){
                throw new Exception("Incorrect number of updates");
            }
            commitTransaction(connection);
            return counter;
        }catch(Exception e){
            e.printStackTrace();
            try{ rollbackTransaction(connection); }catch(Exception re){}
            return 0;
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public int generatePayEventForActiveEmployees(int periodId){
        List<EmployeeBank> employeeBankList = new ArrayList<EmployeeBank>();
        Connection connection = null;
        try{
            employeeBankList = getEmployeeService().findAllActiveEmployeeBankDetail();
            connection = this.getConnection(false);
            //start transaction
            startTransaction(connection);
            for(EmployeeBank bank : employeeBankList){
                long employeeId = (long) bank.getEmployeeId();
                int policyId = bank.getPolicyId();
                
                int counter = generatePayEventFromPolicy(employeeId, policyId, periodId, connection);
                
            }
            commitTransaction(connection);
            return employeeBankList.size();
        }catch(Exception e){
            e.printStackTrace();
            try{ rollbackTransaction(connection); }catch(Exception re){}
            return 0;
        }finally{
            transactionConnection = null;
            try {
                if(!connection.isClosed()) connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public int generatePayEventForActiveEmployeesByCompany(int periodId, String companyCode){
        List<EmployeeBank> employeeBankList = new ArrayList<EmployeeBank>();
        Connection connection = null;
        try{
            employeeBankList = getEmployeeService().findAllActiveEmployeeBankDetailByCompany(companyCode);
            connection = this.getConnection(false);
            //start transaction
            startTransaction(connection);
            for(EmployeeBank bank : employeeBankList){
                long employeeId = (long) bank.getEmployeeId();
                int policyId = bank.getPolicyId();

                int counter = generatePayEventFromPolicy(employeeId, policyId, periodId, connection);

            }
            commitTransaction(connection);
            return employeeBankList.size();
        }catch(Exception e){
            e.printStackTrace();
            try{ rollbackTransaction(connection); }catch(Exception re){}
            return 0;
        }finally{
            transactionConnection = null;
            try {
                if(!connection.isClosed()) connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public int generatePayEventFromPolicy(long employeeId, int policyId, int periodId){
        Connection connection = null;
        try{

            connection = this.getConnection(false);
            //start transaction
            startTransaction(connection);
            int counter = generatePayEventFromPolicy(employeeId, policyId, periodId, connection);
            if(counter == 0){ throw new Exception("Unusual Error"); }
            commitTransaction(connection);
            return counter;
        }catch(Exception e){
            e.printStackTrace();
            try{ rollbackTransaction(connection); }catch(Exception re){}
            return 0;
        }finally{
            transactionConnection = null;
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public int generatePayEventFromPolicy(long employeeId, int policyId, int periodId, Connection connection) throws Exception{
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            
            //delete all pending record
            param.add(employeeId); type.add("NUMBER");
            int del = this.executeParameterizedUpdate(connection,
                    DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_EMPLOYEE_PAYEVENT_DELETE_ALL,
                        param, type);

            //re-initialize vactors
            param = new Vector();
            type = new Vector();
            //get all payitems to use for employee
            //use the parameter to get default events for user
            param.add(policyId); type.add("NUMBER");
            param.add(periodId); type.add("NUMBER");
            param.add(periodId); type.add("NUMBER");
            param.add(employeeId); type.add("NUMBER"); //used for second query in union
            param.add(periodId); type.add("NUMBER"); //used for second query in union
            param.add(periodId); type.add("NUMBER"); //used for second query in union
            ResultSet rst = this.executeParameterizedQuery(connection,
                    DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_PAYEVENT_INITIAL_ACCOUNT_AND_AMOUNT_SELECT,
                        param, type);

            List<Payroll_PayEvent> events = new ArrayList<Payroll_PayEvent>();
            while(rst.next()){
                Payroll_PayEvent event = new Payroll_PayEvent();
                event.setAccountId(rst.getInt("accountId"));
                Double value = rst.getDouble("amount");
                String description = rst.getString("description");
                String strGroupId = rst.getString("groupid");

                if(value == null){ value = new Double(0); }
                event.setItemValue(value.doubleValue());
                event.setEmployeeId(employeeId);
                event.setPeriodId(periodId);
                event.setDerived(1);
                event.setRegisteredDate(new Date());
                event.setNarration(description);
                event.setPayable(Integer.parseInt(strGroupId));
                
                //add the event to the list
                events.add(event);
            }

            //initiate the new record
            int counter = 0;
            for(Payroll_PayEvent event : events){
                //re-initialize vactors
                param = new Vector();
                type = new Vector();
                //set parameters in the appropriate order
                param.add(event.getEmployeeId()); type.add("NUMBER");
                param.add(event.getPeriodId()); type.add("NUMBER");
                param.add(event.getAccountId()); type.add("NUMBER");
                param.add(event.getDerived()); type.add("NUMBER");
                param.add(event.getItemValue()); type.add("AMOUNT");
                param.add(event.getRegisteredDate()); type.add("DATE");
                param.add(event.getNarration()); type.add("STRING");
                param.add(event.getPayable()); type.add("NUMBER");

                int i = this.executeParameterizedUpdate(connection,
                        DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_PAYEVENT_INITIAL_INSERT,
                        param, type);
                counter = counter + i;
            }
            if(counter != events.size()){
                throw new Exception("Incorrect number of updates");
            }

            //********now do the calculations********
            //intantiate list of event object to use..
            List<Payroll_PayEvent> allEvents = new ArrayList<Payroll_PayEvent>();

            //get list of account current inserted for employee
            List<Payroll_PayAccount> accounts = getPayEventAccount_FromPolicy(employeeId, periodId, connection);

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

                transactionConnection = connection;

                //check the type of input type to determine calculation
                if(inputType == PayrollService.INPUT_TYPE_AMOUNT){
                    if(amount != 0){
                        amount = account.getItemValue();
                    }else{
                        amount = evaluateFormula(formula, employeeId, periodId, true);
                    }
                }else if(inputType == PayrollService.INPUT_TYPE_MINUTE){
                    quantity = (int)Math.round(account.getItemValue());
                    int hours = (int) Math.round(quantity / 60);
                    unit_Price = evaluateFormula(formula, employeeId, periodId, true);
                    amount = hours * unit_Price;
                }else if (inputType == PayrollService.INPUT_TYPE_DAY || inputType == PayrollService.INPUT_TYPE_UNIT){
                    quantity = (int)Math.round(account.getItemValue());
                    unit_Price = evaluateFormula(formula, employeeId, periodId, true);
                    amount = quantity * unit_Price;
                }
                event.setEmployeeId(employeeId);
                event.setPeriodId(periodId);
                event.setAccountId(account.getId());
                event.setAmount(amount);
                event.setUnitPrice(unit_Price);
                event.setQuantity(quantity);
                event.setStartDate(new Date());
                //int isPayable = checkIfAccount_IsPayable(account.getId());
                //if(isPayable == -1) { throw new Exception("Invalid Query to get Payable Flag for Account"); }
                //event.setPayable(isPayable);

                //add event to the list (events)
                allEvents.add(event);
            }
            //remember if qty and unit price is zero... null must be inserted for their values into the db
            int saved = updatePayEvent(allEvents, connection);
            if(saved == 0){ throw new Exception("Error saving. Unable to complete payevent task. returned zero."); }

            return counter;
        }catch(Exception e){
            throw e;
        }finally{
            transactionConnection = null;
        }
    }

    public List<Payroll_PayAccount> getPayEventAccount_FromPolicy(long employeeId, int periodId){
        Connection connection = null;
        try{
            connection = this.getConnection(false);
            return getPayEventAccount_FromPolicy(employeeId, periodId, connection);
        }catch(Exception e){
            e.printStackTrace();
            return new ArrayList<Payroll_PayAccount>();
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    public List<Payroll_PayAccount> getPayEventAccount_FromPolicy(long employeeId, int periodId, Connection connection){
        
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            //get all payitems to use for employee
            //use the parameter to get default events for user
            param.add(employeeId); type.add("NUMBER");
            param.add(periodId); type.add("NUMBER");
            ResultSet rst = this.executeParameterizedQuery(connection, 
                    DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_PAYEVENT_ACCOUNT_SELECT,
                        param, type);

            List<Payroll_PayAccount> accounts = new ArrayList<Payroll_PayAccount>();
            while(rst.next()){
                Payroll_PayAccount account = new Payroll_PayAccount();
                account.setId(rst.getInt("accountId"));
                account.setFormula(rst.getString("formula"));
                Double value = rst.getDouble("item_value");
                if(value == null){ value = new Double(0); }
                account.setItemValue(value.doubleValue());
                account.setCalculationSequence(rst.getInt("calcseq"));
                account.setInputType(rst.getInt("inputtype"));

                //add the event to the list
                accounts.add(account);
            }

            return accounts;
        }catch(Exception e){
            e.printStackTrace();
            return new ArrayList<Payroll_PayAccount>();
        }
    }

    public int updatePayEvent(List<Payroll_PayEvent> events){
        Connection connection = null;
        try{
            connection = this.getConnection(false);
            //start transaction
            startTransaction(connection);
            int updated = updatePayEvent(events, connection);
            commitTransaction(connection);
            return updated;
        }catch(Exception e){
            e.printStackTrace();
            try{ rollbackTransaction(connection); }catch(Exception re){}
            return 0;
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    public int updatePayEvent(List<Payroll_PayEvent> events, Connection connection) throws Exception{
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            
            //initiate the new record
            int counter = 0;
            for(Payroll_PayEvent event : events){
                //re-initialize vactors
                param = new Vector();
                type = new Vector();
                //set parameters in the appropriate order

                param.add(event.getAmount()); type.add("AMOUNT");
                if(event.getUnitPrice() == 0){
                    param.add(event.getUnitPrice()); type.add("NUMBER_NULL");
                }else{
                    param.add(event.getUnitPrice()); type.add("AMOUNT");
                }
                if(event.getQuantity() == 0){
                    param.add(event.getQuantity()); type.add("NUMBER_NULL");
                }else{
                    param.add(event.getQuantity()); type.add("NUMBER");
                }
                //param.add(event.getPayable()); type.add("NUMBER");

                param.add(event.getAccountId()); type.add("NUMBER");
                param.add(event.getEmployeeId()); type.add("NUMBER");
                param.add(event.getPeriodId()); type.add("NUMBER");

                int i = this.executeParameterizedUpdate(connection,
                        DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_PAYEVENT_CALCULATION_UPDATE,
                        param, type);
                counter = counter + i;
            }
            if(counter != events.size()){
                throw new Exception("Incorrect number of updates");
            }
            return counter;
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    public List<Payroll_PayEvent> getPayEvent_ByEmployee(long employeeId, int periodId){
        return getPayEvent_ByEmployee(employeeId, periodId, false);
    }
    public List<Payroll_PayEvent> getPayEvent_ByEmployee(long employeeId, int periodId, boolean useArchive){
        Connection connection = null;
        try{
            connection = this.getConnection(false);
            return getPayEvent_ByEmployee(employeeId, periodId, connection, useArchive);

        }catch(Exception e){
            e.printStackTrace();
            return new ArrayList<Payroll_PayEvent>();
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    public List<Payroll_PayEvent> getPayEvent_ByEmployee(long employeeId, int periodId, Connection connection){
        return getPayEvent_ByEmployee(employeeId, periodId, connection, false);
    }
    public List<Payroll_PayEvent> getPayEvent_ByEmployee(long employeeId, int periodId, Connection connection, boolean useArchive){

        Vector param =new Vector();
        Vector type = new Vector();
        try{
            //get all payitems to use for employee
            //use the parameter to get default events for user
            param.add(employeeId); type.add("NUMBER");
            param.add(periodId); type.add("NUMBER");

            //get the query to use
            String query = DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_PAYEVENT_SELECT_BY_EMPLOYEE_AND_PERIOD;
            if(useArchive == true){
                query = DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_PAYEVENT_SELECT_BY_EMPLOYEE_AND_PERIOD_FROM_ARCHIVE;
            }
            ResultSet rst = this.executeParameterizedQuery(connection,
                    query, param, type);

            List<Payroll_PayEvent> events = new ArrayList<Payroll_PayEvent>();
            while(rst.next()){
                Payroll_PayEvent event = new Payroll_PayEvent();
                event.setEmployeeId(rst.getLong("emp_number"));
                event.setPeriodId(rst.getInt("periodid"));
                event.setAccountId(rst.getInt("accountid"));

                Double itemValue = rst.getDouble("item_value");
                if(itemValue == null){ itemValue = new Double(0); }
                event.setItemValue(itemValue);

                event.setNarration(rst.getString("narrative"));
                Double amount = rst.getDouble("amount");
                if(amount == null){ amount = new Double(0); }
                event.setAmount(amount.doubleValue());
                event.setUnitPrice(rst.getDouble("unit_price"));
                event.setDerived(rst.getInt("derived"));
                event.setQuantity(rst.getInt("quantity"));
                event.setPayable(rst.getInt("payable"));

                event.setPayableDescription(rst.getString("name"));
                //add the event to the list
                events.add(event);
            }

            return events;
        }catch(Exception e){
            e.printStackTrace();
            return new ArrayList<Payroll_PayEvent>();
        }
    }

    public List<Payroll_PayPeriod.PaymentSummary> getAllPaymentSummary_ForActivePeriod(){
        Connection connection = null;
        try{
            connection = this.getConnection(false);
            return getAllPaymentSummary_ForActivePeriod(connection, null);

        }catch(Exception e){
            e.printStackTrace();
            return new ArrayList<Payroll_PayPeriod.PaymentSummary>();
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public List<Payroll_PayPeriod.PaymentSummary> getAllPaymentSummary_ForActivePeriodByCompany(String code){
        Connection connection = null;
        try{
            connection = this.getConnection(false);
            return getAllPaymentSummary_ForActivePeriod(connection, code);

        }catch(Exception e){
            e.printStackTrace();
            return new ArrayList<Payroll_PayPeriod.PaymentSummary>();
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public List<Payroll_PayPeriod.PaymentSummary> getAllPaymentSummary_ForActivePeriod(Connection connection, String companyCode){

        try{
            //get all payitems to use for employee
            //use the parameter to get default events for user
            ResultSet rst = null;
            if(companyCode == null){
                rst = this.executeQuery(DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_PAYEVENT_SUMMARY_SELECT_BY_ACTIVE_PERIOD, connection);
            }else{
                Vector param = new Vector();
                Vector type = new Vector();
                param.add(companyCode); type.add("STRING");

                rst = this.executeParameterizedQuery(connection,
                        DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_PAYEVENT_SUMMARY_SELECT_BY_ACTIVE_PERIOD_AND_COMPANY, param, type);
            }

            List<Payroll_PayPeriod.PaymentSummary> payments = new ArrayList<Payroll_PayPeriod.PaymentSummary>();
            while(rst.next()){
                Payroll_PayPeriod.PaymentSummary payment = new Payroll_PayPeriod.PaymentSummary();
                payment.setEmployeeId(rst.getInt("emp_number"));
                payment.setBankName(rst.getString("bank_name"));
                payment.setBranch(rst.getString("bank_branch"));
                payment.setAccountName(rst.getString("account_name"));
                payment.setAccountNumber(rst.getString("account_number"));
                payment.setAccountType(rst.getString("account_type"));

                payment.setFirstName(rst.getString("firstname"));
                payment.setLastName(rst.getString("lastname"));

                Double totalEarning = rst.getDouble("totalearning");
                if(totalEarning == null){ totalEarning = new Double(0); }
                payment.setTotalEarning(totalEarning);

                Double totalDeduction = rst.getDouble("totaldeduction");
                if(totalDeduction == null){ totalDeduction = new Double(0); }
                payment.setTotalDeduction(totalDeduction);
                
                 Double totalOthers = rst.getDouble("totalothers");
                if(totalOthers == null){ totalOthers = new Double(0); }
                payment.setTotalOthers(totalOthers);

                //add the event to the list
                payments.add(payment);
            }

            return payments;
        }catch(Exception e){
            e.printStackTrace();
            return new ArrayList<Payroll_PayPeriod.PaymentSummary>();
        }
    }

    public List<Payroll_PayPeriod.PaymentSummary> getAllPaymentSummary_ForAllPeriod(long employeeId){
        Connection connection = null;
        try{
            connection = this.getConnection(false);
            return getAllPaymentSummary_ForAllPeriod(employeeId, connection);

        }catch(Exception e){
            e.printStackTrace();
            return new ArrayList<Payroll_PayPeriod.PaymentSummary>();
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public List<Payroll_PayPeriod.PaymentSummary> getAllPaymentSummary_ForAllPeriod(long employeeId, Connection connection){


        try{
            Vector param = new Vector();
            Vector type = new Vector();
            //set parameters in the appropriate order
            param.add(employeeId); type.add("NUMBER");
            //get all payitems to use for employee
            //use the parameter to get default events for user
            CallableStatement csmt = this.executeProcedure(connection, DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_PAYEVENT_SUMMARY_FOR_ALL_PERIODS, param, type);
            ResultSet rst = csmt.getResultSet();

            List<Payroll_PayPeriod.PaymentSummary> payments = new ArrayList<Payroll_PayPeriod.PaymentSummary>();
            while(rst.next()){
                Payroll_PayPeriod.PaymentSummary payment = new Payroll_PayPeriod.PaymentSummary();
                payment.setId(rst.getInt("periodid"));
                payment.setStartTime(new Date(rst.getDate("starttime").getTime()));
                payment.setEndTime(new Date(rst.getDate("endtime").getTime()));
                payment.seteStatus(rst.getString("estatus"));

                Double totalEarning = rst.getDouble("totalearning");
                if(totalEarning == null){ totalEarning = new Double(0); }
                payment.setTotalEarning(totalEarning);

                Double totalDeduction = rst.getDouble("totaldeduction");
                if(totalDeduction == null){ totalDeduction = new Double(0); }
                payment.setTotalDeduction(totalDeduction);

                 Double totalOthers = rst.getDouble("totalothers");
                if(totalOthers == null){ totalOthers = new Double(0); }
                payment.setTotalOthers(totalOthers);

                //add the event to the list
                payments.add(payment);
            }

            return payments;
        }catch(Exception e){
            e.printStackTrace();
            return new ArrayList<Payroll_PayPeriod.PaymentSummary>();
        }
    }

    public double getAttribute(String attributeCode, long employeeId, int periodId) throws Exception{
        return getAttribute(attributeCode, employeeId, periodId, false);
    }

    public double getAttribute(String attributeCode, long employeeId, int periodId, boolean useTranxConnection) throws Exception{
        //call DAO and pass empid and periodId as parameter
        Employee_Payroll.Attribute attribute = getEmployeeAttribute(employeeId, attributeCode, periodId, useTranxConnection);
        //check if value is valid number otherwise return 0
        double valueAmount = attribute.getValue_Amount();
        //return value
        return valueAmount;
    }

    public double evaluateFormula (String formula, long employeeId, int periodId) throws Exception{
        return evaluateFormula(formula, employeeId, periodId, false);
    }

    public double evaluateFormula (String formula, long employeeId, int periodId, boolean useExistingTrnxConnection) throws Exception{

        //String formula = "(-1) * attribute(salary) * 0.05";
        //remove all spaces in the formula
        formula = formula.replaceAll(" ", "");

        String attributeFromFormula = "";
        String attributeCode = "";
        if(formula.indexOf("attribute") != -1){
            //get attribute formula out of the main formula [attribute(attributecode)]
            attributeFromFormula = formula.substring(formula.indexOf("attribute"));
            attributeFromFormula = attributeFromFormula.substring(0, attributeFromFormula.indexOf(")") + 1);

            //get the attribute code from the attribute formula
            attributeCode = attributeFromFormula.replace("attribute", "").replace("(", "").replace(")", "");

            //get the corresponding value from the database based on the attributeCode for the employee
            double value = getAttribute(attributeCode, employeeId, periodId, useExistingTrnxConnection);

            //replace the attribute Fornula from the main formula
            formula = formula.replace(attributeFromFormula, String.valueOf(value));
        }

        //use BeanShell Interpreter (Initiate it using default Constructor)
        bsh.Interpreter interpreter = new bsh.Interpreter();

        //evaluate the formula
        interpreter.eval("result=" + formula);

        //get result after calculation and return
        String result = String.valueOf(interpreter.get("result"));
        return Double.parseDouble(result);
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
