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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.tenece.web.data.EmployeeDependent;

/**
 *
 * @author jeffry.amachree
 */
public class EmployeeDependentDAO extends BaseDAO{
    
    /**
     * Creates a new instance of DepartmentDAO
     */
    public EmployeeDependentDAO() {
    }
    
    public EmployeeDependent getDependentById(int id){
        Connection connection = null;
        EmployeeDependent records = new EmployeeDependent();
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_DEPENDENTS_SELECT_BY_ID,
                    param, type);
            
            while(rst.next()){
                EmployeeDependent item = new EmployeeDependent();
                item.setId(rst.getInt("dependent_id"));
                item.setEmployeeId(rst.getInt("emp_number"));
                item.setSequence(rst.getInt("seqno"));
                item.setName(rst.getString("fullname"));
                item.setRelationship(rst.getString("relationship"));
                
                records = item;
            }
        }catch(Exception e){
            
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return records;
    }
    
    public List<EmployeeDependent> getEmployeeDependentById(int id){
        Connection connection = null;
        List<EmployeeDependent> records = new ArrayList<EmployeeDependent>();
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_DEPENDENTS_SELECT_BY_EMPLOYEE,
                    param, type);
            
            while(rst.next()){
                EmployeeDependent item = new EmployeeDependent();
                item.setId(rst.getInt("dependent_id"));
                item.setEmployeeId(rst.getInt("emp_number"));
                item.setSequence(rst.getInt("seqno"));
                item.setName(rst.getString("fullname"));
                item.setRelationship(rst.getString("relationship"));
                
                records.add(item);
            }
        }catch(Exception e){
            
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return records;
    }
    
    public int createNewEmployeeDependent(EmployeeDependent EmployeeDependent){
        Connection connection = null;
        EmployeeDependent record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(EmployeeDependent.getEmployeeId()); type.add("NUMBER");
            param.add(EmployeeDependent.getSequence()); type.add("NUMBER");
            param.add(EmployeeDependent.getName()); type.add("STRING");
            param.add(EmployeeDependent.getRelationship()); type.add("STRING");
            
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_DEPENDENTS_INSERT,
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
    
    public int updateEmployeeDependent(EmployeeDependent EmployeeDependent){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(EmployeeDependent.getEmployeeId()); type.add("NUMBER");
            param.add(EmployeeDependent.getSequence()); type.add("NUMBER");
            param.add(EmployeeDependent.getName()); type.add("STRING");
            param.add(EmployeeDependent.getRelationship()); type.add("STRING");
            param.add(EmployeeDependent.getId()); type.add("NUMBER");
            
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_DEPENDENTS_UPDATE,
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

    public int deleteEmployeeDependent(List<Long> ids){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{

            int i = 0;
            connection = this.getConnection(false);
            startTransaction(connection);
            for(long id : ids){
                param =new Vector();
                type = new Vector();
                param.add(id); type.add("NUMBER");
                i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_DEPENDENTS_DELETE,
                        param, type);
            }
            commitTransaction(connection);
            return i;
        }catch(Exception e){
            e.printStackTrace();
            try {
                rollbackTransaction(connection);
            } catch (SQLException ex) {
                Logger.getLogger(EmployeeDependentDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return 0;
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }//:~

    public int migrateEmployeeDependentsToESS(long id){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            startTransaction(connection);

            this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_ESS_DEPENDENTS_DELETE_ALL_BY_EMPLOYEE,
                    param, type);

            int rows = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_ESS_DEPENDENTS_MIGRATION,
                    param, type);

            commitTransaction(connection);
            return rows;
        }catch(Exception e){
            e.printStackTrace();

            try{ rollbackTransaction(connection); }catch(Exception ex){}
            return 0;
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public int migrateEmployeeESSDependentsToDefault(long id, Connection connection) throws Exception{
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");

            this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_DEPENDENTS_DELETE_ALL_BY_EMPLOYEE,
                    param, type);

            int rows = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_ESS_DEPENDENTS_MIGRATION_REVERSED,
                    param, type);

            return rows;
        }catch(Exception e){
            throw e;
        }
    }//:!

    public int archiveEmployeeESSDependent(long transactionId, Connection connection) throws Exception{
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(transactionId);
            type.add("NUMBER");

            int rows = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_ESS_DEPENDENTS_MIGRATION_ARCHIVED,
                    param, type);

            return rows;
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }//:!

    public int initiateTransactionForESS(long transactionId, long employeeId, Connection connection) throws Exception{
        return processTransactionForESS(transactionId, employeeId, connection, "P", "M");
    }

    public int processTransactionForESS(long transactionId, long employeeId, Connection connection, String eStatus, String currentStatus) throws Exception{
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(transactionId); type.add("NUMBER");
            param.add(eStatus); type.add("STRING");
            param.add(employeeId); type.add("NUMBER");
            param.add(currentStatus); type.add("STRING");

            int rows = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_ESS_DEPENDENT_INITIATE_TRANSACTION,
                    param, type);

            return rows;
        }catch(Exception e){
            e.printStackTrace();

            throw e;
        }
    }

    public EmployeeDependent getESS_DependentBySequence(int id){
        Connection connection = null;
        EmployeeDependent records = new EmployeeDependent();
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_ESS_DEPENDENTS_SELECT_BY_SEQUENCE,
                    param, type);

            while(rst.next()){
                EmployeeDependent item = new EmployeeDependent();
                item.setEmployeeId(rst.getInt("emp_number"));
                item.setSequence(rst.getInt("seqno"));
                item.setName(rst.getString("fullname"));
                item.setRelationship(rst.getString("relationship"));

                records = item;
            }
        }catch(Exception e){

        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return records;
    }

    public List<EmployeeDependent> getESS_EmployeeDependents(int id){
        Connection connection = null;
        List<EmployeeDependent> records = new ArrayList<EmployeeDependent>();
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_ESS_DEPENDENTS_SELECT_BY_EMPLOYEE,
                    param, type);

            while(rst.next()){
                EmployeeDependent item = new EmployeeDependent();
                item.setEmployeeId(rst.getInt("emp_number"));
                item.setSequence(rst.getInt("seqno"));
                item.setName(rst.getString("fullname"));
                item.setRelationship(rst.getString("relationship"));

                records.add(item);
            }
        }catch(Exception e){

        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return records;
    }

    public int createNewESS_EmployeeDependent(EmployeeDependent employeeDependent){
        Connection connection = null;
        EmployeeDependent record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(employeeDependent.getEmployeeId()); type.add("NUMBER");
            param.add(employeeDependent.getSequence()); type.add("NUMBER");
            param.add(employeeDependent.getName()); type.add("STRING");
            param.add(employeeDependent.getRelationship()); type.add("STRING");
            if(employeeDependent.getTransactionId() == 0){
                param.add(employeeDependent.getTransactionId()); type.add("NUMBER_NULL");
            }else{
                param.add(employeeDependent.getTransactionId()); type.add("NUMBER");
            }
            param.add(employeeDependent.geteStatus()); type.add("STRING");

            System.out.println(">>>Query:" + DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_ESS_DEPENDENTS_INSERT);
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_ESS_DEPENDENTS_INSERT,
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

    public int updateESS_EmployeeDependent(EmployeeDependent employeeDependent){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(employeeDependent.getEmployeeId()); type.add("NUMBER");
            param.add(employeeDependent.getSequence()); type.add("NUMBER");
            param.add(employeeDependent.getName()); type.add("STRING");
            param.add(employeeDependent.getRelationship()); type.add("STRING");
            param.add(employeeDependent.getSequence()); type.add("NUMBER");

            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_ESS_DEPENDENTS_UPDATE,
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

    public int deleteESS_EmployeeDependent(EmployeeDependent employeeDependent){
        Connection connection = null;
        EmployeeDependent record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{

            param.add(employeeDependent.getSequence()); type.add("NUMBER");

            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_ESS_DEPENDENTS_DELETE,
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
