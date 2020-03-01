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
import org.tenece.web.data.PerformanceTarget;

/**
 *
 * @author jeffry.amachree
 */
public class PerformanceTargetDAO extends BaseDAO{
    
    /**
     * Creates a new instance of DepartmentDAO
     */
    public PerformanceTargetDAO() {
    }
    
    /* ************* Performance Target ********** */
    public List<PerformanceTarget> getAllPerformanceTargets(){
        Connection connection = null;
        List<PerformanceTarget> records = new ArrayList<PerformanceTarget>();
        try{
            connection = this.getConnection(true);
            ResultSet rst = this.executeQuery(DatabaseQueryLoader.getQueryAssignedConstant().PERFORMANCE_TARGET_SELECT, connection);
            
            while(rst.next()){
                PerformanceTarget target = new PerformanceTarget();
                target.setId(rst.getInt("idx"));
                target.setEmployeeId(rst.getInt("employee_id"));
                target.setAssignment(rst.getString("assignment"));
                target.setTargetWeight(rst.getInt("target_weight"));
                target.setTargetAmount(rst.getDouble("target_amount"));
                target.setStartDate(new java.util.Date(rst.getDate("start_date").getTime()));
                target.setEndDate(new java.util.Date(rst.getDate("end_date").getTime()));
                target.setNote(rst.getString("note"));
                target.setStatus(rst.getString("status"));
                target.setTransactionId(rst.getLong("transactionid"));
                target.seteStatus(rst.getString("estatus"));
                
                records.add(target);
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
        return records;
    }

    public PerformanceTarget getPerformanceTargetById(int id){
        return getPerformanceTarget(id, DatabaseQueryLoader.getQueryAssignedConstant().PERFORMANCE_TARGET_SELECT_BY_ID);
    }
    
    public PerformanceTarget getPerformanceTargetByTransaction(long id){
        return getPerformanceTarget(id, DatabaseQueryLoader.getQueryAssignedConstant().PERFORMANCE_TARGET_SELECT_BY_TRANSACTION);
    }
    public PerformanceTarget getPerformanceTargetByTransaction(Connection connection, long id){
        return getPerformanceTarget(id, DatabaseQueryLoader.getQueryAssignedConstant().PERFORMANCE_TARGET_SELECT_BY_TRANSACTION, connection);
    }

    private PerformanceTarget getPerformanceTarget(long id, String query){
        Connection connection = null;
        PerformanceTarget records = null;
        
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            //set parameter
            param.add(id); type.add("NUMBER");
            
            connection = this.getConnection(true);
            System.out.println("Query: " + query);
            ResultSet rst = this.executeParameterizedQuery(connection, query, param, type);
            
            if(rst.next()){
                PerformanceTarget target = new PerformanceTarget();
                target.setId(rst.getInt("idx"));
                target.setEmployeeId(rst.getInt("employee_id"));
                target.setAssignment(rst.getString("assignment"));
                target.setTargetWeight(rst.getInt("target_weight"));
                target.setTargetAmount(rst.getDouble("target_amount"));
                target.setStartDate(new java.util.Date(rst.getDate("start_date").getTime()));
                target.setEndDate(new java.util.Date(rst.getDate("end_date").getTime()));
                target.setNote(rst.getString("note"));
                target.setStatus(rst.getString("status"));
                target.setTransactionId(rst.getLong("transactionid"));
                target.seteStatus(rst.getString("estatus"));
                
                records = target;
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
        return records;
    }

    private PerformanceTarget getPerformanceTarget(long id, String query, Connection connection){
        PerformanceTarget records = null;

        Vector param =new Vector();
        Vector type = new Vector();
        try{
            //set parameter
            param.add(id); type.add("NUMBER");

            ResultSet rst = this.executeParameterizedQuery(connection, query, param, type);

            if(rst.next()){
                PerformanceTarget target = new PerformanceTarget();
                target.setId(rst.getInt("idx"));
                target.setEmployeeId(rst.getInt("employee_id"));
                target.setAssignment(rst.getString("assignment"));
                target.setTargetWeight(rst.getInt("target_weight"));
                target.setTargetAmount(rst.getDouble("target_amount"));
                target.setStartDate(new java.util.Date(rst.getDate("start_date").getTime()));
                target.setEndDate(new java.util.Date(rst.getDate("end_date").getTime()));
                target.setNote(rst.getString("note"));
                target.setStatus(rst.getString("status"));
                target.setTransactionId(rst.getLong("transactionid"));
                target.seteStatus(rst.getString("estatus"));

                records = target;
            }
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
        return records;
    }
    
    public List<PerformanceTarget> getAllPerformanceTargetForEmployee(long employeeId){
        Connection connection = null;
        List<PerformanceTarget> records = new ArrayList<PerformanceTarget>();
        
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            //set parameter
            param.add(employeeId); type.add("NUMBER");
            
            connection = this.getConnection(true);
            System.out.println("SQL Query:" + DatabaseQueryLoader.getQueryAssignedConstant().PERFORMANCE_TARGET_SELECT_BY_EMPLOYEE);
            
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().PERFORMANCE_TARGET_SELECT_BY_EMPLOYEE, param, type);
            
            while(rst.next()){
                PerformanceTarget target = new PerformanceTarget();
                target.setId(rst.getInt("idx"));
                target.setEmployeeId(rst.getInt("employee_id"));
                target.setAssignment(rst.getString("assignment"));
                target.setTargetWeight(rst.getInt("target_weight"));
                target.setTargetAmount(rst.getDouble("target_amount"));
                target.setStartDate(new java.util.Date(rst.getDate("start_date").getTime()));
                target.setEndDate(new java.util.Date(rst.getDate("end_date").getTime()));
                target.setNote(rst.getString("note"));
                target.setStatus(rst.getString("status"));
                target.setTransactionId(rst.getLong("transactionid"));
                target.seteStatus(rst.getString("estatus"));
                
                records.add(target);
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
        return records;
    }
    
    public int updatePerformanceTarget(PerformanceTarget target){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(target.getEmployeeId()); type.add("NUMBER");
            param.add(target.getAssignment()); type.add("STRING");
            param.add(target.getTargetWeight()); type.add("NUMBER");
            param.add(target.getTargetAmount()); type.add("AMOUNT");
            param.add(target.getStartDate()); type.add("DATE");
            param.add(target.getEndDate()); type.add("DATE");
            param.add(target.getNote()); type.add("STRING");
            param.add(target.getStatus()); type.add("STRING");
            param.add(target.getTransactionId()); type.add("NUMBER");
            param.add(target.geteStatus()); type.add("STRING");
            param.add(target.getId()); type.add("NUMBER");
            
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().PERFORMANCE_TARGET_UPDATE,
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
    
    public int updatePerformanceTarget(PerformanceTarget target, Connection connection) throws Exception{
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(target.getEmployeeId()); type.add("NUMBER");
            param.add(target.getAssignment()); type.add("STRING");
            param.add(target.getTargetWeight()); type.add("NUMBER");
            param.add(target.getTargetAmount()); type.add("AMOUNT");
            param.add(target.getStartDate()); type.add("DATE");
            param.add(target.getEndDate()); type.add("DATE");
            param.add(target.getNote()); type.add("STRING");
            param.add(target.getStatus()); type.add("STRING");
            param.add(target.getTransactionId()); type.add("NUMBER");
            param.add(target.geteStatus()); type.add("STRING");
            param.add(target.getId()); type.add("NUMBER");
            
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().PERFORMANCE_TARGET_UPDATE,
                    param, type);
            
            return i;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }finally{
            
        }
    }//:~
    
    public int insertPerformanceTarget(PerformanceTarget target){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(target.getEmployeeId()); type.add("NUMBER");
            param.add(target.getAssignment()); type.add("STRING");
            param.add(target.getTargetWeight()); type.add("NUMBER");
            param.add(target.getTargetAmount()); type.add("AMOUNT");
            param.add(target.getStartDate()); type.add("DATE");
            param.add(target.getEndDate()); type.add("DATE");
            param.add(target.getNote()); type.add("STRING");
            param.add(target.getStatus()); type.add("STRING");
            param.add(target.getTransactionId()); type.add("NUMBER");
            param.add(target.geteStatus()); type.add("STRING");
            
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().PERFORMANCE_TARGET_INSERT,
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

    public int insert_ePerformanceTarget(PerformanceTarget target, Connection connection){

        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(target.getEmployeeId()); type.add("NUMBER");
            param.add(target.getAssignment()); type.add("STRING");
            param.add(target.getTargetWeight()); type.add("NUMBER");
            param.add(target.getTargetAmount()); type.add("AMOUNT");
            param.add(target.getStartDate()); type.add("DATE");
            param.add(target.getEndDate()); type.add("DATE");
            param.add(target.getNote()); type.add("STRING");
            param.add(target.getStatus()); type.add("STRING");
            param.add(target.getTransactionId()); type.add("NUMBER");
            param.add(target.geteStatus()); type.add("STRING");

            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().PERFORMANCE_TARGET_INSERT,
                    param, type);

            return i;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }//:~


    public int deletePerformanceTarget(PerformanceTarget target){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(target.getId()); type.add("NUMBER");
            
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().PERFORMANCE_TARGET_DELETE,
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
    
    public int deletePerformanceTargets(List<Integer> targets){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            
            
            connection = this.getConnection(false);
            this.startTransaction(connection);
            for(int target : targets){
                param.add(target); type.add("NUMBER");
                int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().PERFORMANCE_TARGET_DELETE,
                        param, type);
            }
            this.commitTransaction(connection);
            return 1;
        }catch(Exception e){
            try {
                this.rollbackTransaction(connection);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
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
