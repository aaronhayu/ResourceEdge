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

package org.tenece.hr.data.dao;

import org.tenece.hr.db.DatabaseQueryLoader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import org.tenece.web.data.EmployeeBank;

/**
 *
 * @author jeffry.amachree
 */
public class EmployeeBankDetailDAO extends BaseDAO{
    
    /**
     * Creates a new instance of DepartmentDAO
     */
    public EmployeeBankDetailDAO() {
    }

    public List<EmployeeBank> getAllValidEmployeeBankDetail(){
        Connection connection = null;
        List<EmployeeBank> banks = new ArrayList<EmployeeBank>();
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection,
                    DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_BANK_DETAIL_ALL_ACTIVE_SELECT,
                    param, type);

            while(rst.next()){
                EmployeeBank bank = new EmployeeBank();
                bank.setId(rst.getInt("id"));
                bank.setEmployeeId(rst.getInt("emp_number"));
                bank.setBankName(rst.getString("bank_name"));
                bank.setBranch(rst.getString("bank_branch"));

                bank.setAccountName(rst.getString("account_name"));
                bank.setAccountNumber(rst.getString("account_number"));
                bank.setAccountType(rst.getString("account_type"));
                bank.setPolicyId(rst.getInt("policyid"));

                banks.add(bank);
            }
            return banks;
        }catch(Exception e){
            e.printStackTrace();

        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return banks;
    }

    public List<EmployeeBank> getAllValidEmployeeBankDetailByCompany(String code){
        Connection connection = null;
        List<EmployeeBank> banks = new ArrayList<EmployeeBank>();
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(code); type.add("STRING");
            
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection,
                    DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_BANK_DETAIL_ALL_ACTIVE_SELECT_BY_COMPANY,
                    param, type);

            while(rst.next()){
                EmployeeBank bank = new EmployeeBank();
                bank.setId(rst.getInt("id"));
                bank.setEmployeeId(rst.getInt("emp_number"));
                bank.setBankName(rst.getString("bank_name"));
                bank.setBranch(rst.getString("bank_branch"));

                bank.setAccountName(rst.getString("account_name"));
                bank.setAccountNumber(rst.getString("account_number"));
                bank.setAccountType(rst.getString("account_type"));
                bank.setPolicyId(rst.getInt("policyid"));

                banks.add(bank);
            }
            return banks;
        }catch(Exception e){
            e.printStackTrace();

        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return banks;
    }

    public EmployeeBank getEmployeeBankDetailById(long id){
        Connection connection = null;
        EmployeeBank bank = new EmployeeBank();
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_BANK_DETAIL_SELECT,
                    param, type);
            
            while(rst.next()){
                bank.setId(rst.getInt("id"));
                bank.setEmployeeId(rst.getInt("emp_number"));
                bank.setBankName(rst.getString("bank_name"));
                bank.setBranch(rst.getString("bank_branch"));
                
                bank.setAccountName(rst.getString("account_name"));
                bank.setAccountNumber(rst.getString("account_number"));
                bank.setAccountType(rst.getString("account_type"));
                bank.setPolicyId(rst.getInt("policyid"));
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
        return bank;
    }
    
    
    public int createNewEmployeeBankDetail(EmployeeBank employeeBank){
        Connection connection = null;
        EmployeeBank record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(employeeBank.getEmployeeId()); type.add("NUMBER");
            param.add(employeeBank.getBankName()); type.add("STRING");
            param.add(employeeBank.getBranch()); type.add("STRING");
            param.add(employeeBank.getAccountName()); type.add("STRING");
            param.add(employeeBank.getAccountNumber()); type.add("STRING");
            param.add(employeeBank.getAccountType()); type.add("STRING");
            param.add(employeeBank.getPolicyId()); type.add("NUMBER");
            
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_BANK_DETAIL_INSERT,
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
    
    public int updateEmployeeBankDetail(EmployeeBank employeeBank){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            
            param.add(employeeBank.getBankName()); type.add("STRING");
            param.add(employeeBank.getBranch()); type.add("STRING");
            param.add(employeeBank.getAccountName()); type.add("STRING");
            param.add(employeeBank.getAccountNumber()); type.add("STRING");
            param.add(employeeBank.getAccountType()); type.add("STRING");
            param.add(employeeBank.getEmployeeId()); type.add("NUMBER");
            param.add(employeeBank.getPolicyId()); type.add("NUMBER");
            
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_BANK_DETAIL_UPDATE,
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

    public EmployeeBank getESS_EmployeeBankDetailByTransaction(long id){
        Connection connection = null;
        EmployeeBank bank = null;
        try{
            connection = this.getConnection(true);
            return getESS_EmployeeBankDetailByTransaction(id, connection);
        }catch(Exception e){
            e.printStackTrace();
            return bank;
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    public EmployeeBank getESS_EmployeeBankDetailByTransaction(long id, Connection connection){
        EmployeeBank bank = new EmployeeBank();
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_ESS_BANK_DETAIL_SELECT,
                    param, type);

            while(rst.next()){
                bank.setEmployeeId(rst.getInt("emp_number"));
                bank.setBankName(rst.getString("bank_name"));
                bank.setBranch(rst.getString("bank_branch"));

                bank.setAccountName(rst.getString("account_name"));
                bank.setAccountNumber(rst.getString("account_number"));
                bank.setAccountType(rst.getString("account_type"));
                bank.setTransactionId(rst.getLong("transactionid"));
                bank.seteStatus(rst.getString("estatus"));

            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return bank;
    }

    public int createNewESS_EmployeeBankDetail(EmployeeBank employeeBank, Connection connection){
        EmployeeBank record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(employeeBank.getEmployeeId()); type.add("NUMBER");
            param.add(employeeBank.getBankName()); type.add("STRING");
            param.add(employeeBank.getBranch()); type.add("STRING");
            param.add(employeeBank.getAccountName()); type.add("STRING");
            param.add(employeeBank.getAccountNumber()); type.add("STRING");
            param.add(employeeBank.getAccountType()); type.add("STRING");
            if(employeeBank.getTransactionId() == 0){
                param.add(employeeBank.getTransactionId()); type.add("NUMBER_NULL");
            }else{
                param.add(employeeBank.getTransactionId()); type.add("NUMBER");
            }
            param.add(employeeBank.geteStatus()); type.add("STRING");

            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_ESS_BANK_DETAIL_INSERT,
                    param, type);

            return i;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }//:~

    public int updateESS_EmployeeBankDetail(EmployeeBank employeeBank, Connection connection){
        Vector param =new Vector();
        Vector type = new Vector();
        try{

            param.add(employeeBank.getBankName()); type.add("STRING");
            param.add(employeeBank.getBranch()); type.add("STRING");
            param.add(employeeBank.getAccountName()); type.add("STRING");
            param.add(employeeBank.getAccountNumber()); type.add("STRING");
            param.add(employeeBank.getAccountType()); type.add("STRING");
            param.add(employeeBank.getEmployeeId()); type.add("NUMBER");
            if(employeeBank.getTransactionId() == 0){
                param.add(employeeBank.getTransactionId()); type.add("NUMBER_NULL");
            }else{
                param.add(employeeBank.getTransactionId()); type.add("NUMBER");
            }
            param.add(employeeBank.geteStatus()); type.add("STRING");

            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_ESS_BANK_DETAIL_UPDATE,
                    param, type);

            return i;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }//:~

    public int updateESS_EmployeeBankDetailStatus(EmployeeBank employeeBank, Connection connection){
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(employeeBank.geteStatus()); type.add("STRING");
            param.add(employeeBank.getTransactionId()); type.add("NUMBER");

            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_ESS_BANK_DETAIL_UPDATE_STATUS_BY_TRANSACTION,
                    param, type);

            return i;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }//:~

    public int migrateESS_EmployeeBankDetailStatus(long transactionId, Connection connection){
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(transactionId); type.add("NUMBER");

            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_ESS_BANK_DETAIL_MIGRATE_FROM_ESS,
                    param, type);

            return i;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }//:~

    public int migrateESS_EmployeeBankDetailStatus(EmployeeBank bank, Connection connection){
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(bank.getBankName()); type.add("STRING");
            param.add(bank.getBranch()); type.add("STRING");
            param.add(bank.getAccountName()); type.add("STRING");
            param.add(bank.getAccountNumber()); type.add("STRING");
            param.add(bank.getAccountType()); type.add("STRING");
            param.add(bank.getEmployeeId()); type.add("NUMBER");

            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_ESS_BANK_DETAIL_MIGRATE_UPDATE_FROM_ESS,
                    param, type);

            return i;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }//:~
    
}
