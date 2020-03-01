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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import org.tenece.hr.db.DatabaseQueryLoader;
import org.tenece.web.data.Employee_Payroll;

/**
 * Tenece Professional Services Limited
 * @author amachree
 */
public class EmployeePayrollDAO extends BaseDAO {
    
    /* --------------- Payroll Employee pay Items ---------------- */
    public List<Employee_Payroll.PayItem> getPayroll_PayItemById(long id){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        List<Employee_Payroll.PayItem> items = new ArrayList<Employee_Payroll.PayItem>();

        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_EMPLOYEE_PAYITEM_SELECT,
                    param, type);

            while(rst.next()){
                Employee_Payroll.PayItem item = new Employee_Payroll.PayItem();
                item.setEmployeeId(rst.getLong("emp_number"));
                item.setAccountId(rst.getInt("accountid"));
                item.setItemValue(rst.getDouble("item_value"));
                item.setFromPeriodId(rst.getInt("fromperiodid"));
                item.setToPeriodId(rst.getInt("toperiodid"));

                items.add(item);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return items;
    }

    public int createNewPayroll_PayItems(List<Employee_Payroll.PayItem> items){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            connection = this.getConnection(false);
            //start transaction
            startTransaction(connection);

            //delete all pending record
            param.add(items.get(0).getEmployeeId()); type.add("NUMBER");
            int del = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_EMPLOYEE_PAYITEM_DELETE_ALL,
                        param, type);

            //initiate the new record
            int counter = 0;
            for(Employee_Payroll.PayItem item : items){
                //re-initialize vactors
                param = new Vector();
                type = new Vector();
                //set parameters in the appropriate order
                param.add(item.getEmployeeId()); type.add("NUMBER");
                param.add(item.getAccountId()); type.add("NUMBER");
                param.add(item.getItemValue()); type.add("AMOUNT");
                param.add(item.getFromPeriodId()); type.add("NUMBER");
                param.add(item.getToPeriodId()); type.add("NUMBER_NULL");

                int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_EMPLOYEE_PAYITEM_INSERT,
                        param, type);
                counter = counter + i;
            }
            if(counter != items.size()){
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
    }//:~

    public int deletePayroll_PayItem(long employeeId, int accountId){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(employeeId); type.add("NUMBER");
            param.add(accountId); type.add("NUMBER");

            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_EMPLOYEE_PAYITEM_DELETE,
                    param, type);

            return i;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }//:~

    /* ================ Employee Attributes for current Pay Period ============= */
    public List<Employee_Payroll.Attribute> getPayroll_AttributeById(long id){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        List<Employee_Payroll.Attribute> attributes = new ArrayList<Employee_Payroll.Attribute>();

        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_EMPLOYEE_ATTRIBUTE_SETTINGS,
                    param, type);

            while(rst.next()){
                Employee_Payroll.Attribute attribute = new Employee_Payroll.Attribute();
                attribute.setAttributeId(rst.getInt("attributeid"));
                attribute.setValue_Amount(rst.getDouble("value_amount"));
                attribute.setEmployeeId(rst.getLong("emp_number"));
                attribute.setFromPeriodId(rst.getInt("fromperiodid"));
                //item.setToPeriodId(rst.getInt("toperiodid"));
                attribute.setAttributeName(rst.getString("attributename"));

                attributes.add(attribute);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return attributes;
    }

    public int createNewPayroll_Attributes(List<Employee_Payroll.Attribute> attributes){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            connection = this.getConnection(false);
            //start transaction
            startTransaction(connection);

            //delete all pending record
            param.add(attributes.get(0).getEmployeeId()); type.add("NUMBER");
            int del = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_EMPLOYEE_ATTRIBUTE_DELETE_ALL,
                        param, type);

            //initiate the new record
            int counter = 0;
            for(Employee_Payroll.Attribute attribute : attributes){
                //re-initialize vactors
                param = new Vector();
                type = new Vector();
                //set parameters in the appropriate order
                param.add(attribute.getEmployeeId()); type.add("NUMBER");
                param.add(attribute.getAttributeId()); type.add("NUMBER");
                param.add(attribute.getValue_Amount()); type.add("AMOUNT");
                param.add(attribute.getFromPeriodId()); type.add("NUMBER");
                param.add(attribute.getToPeriodId()); type.add("NUMBER_NULL");

                int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_EMPLOYEE_ATTRIBUTE_INSERT,
                        param, type);
                counter = counter + i;
            }
            if(counter != attributes.size()){
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
    }//:~

    public int deletePayroll_Attribute(long employeeId, int attributeId){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(employeeId); type.add("NUMBER");
            param.add(attributeId); type.add("NUMBER");

            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_EMPLOYEE_ATTRIBUTE_DELETE,
                    param, type);

            return i;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }//:~
}
