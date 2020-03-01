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
import java.util.Date;
import java.util.List;
import java.util.Vector;
import org.tenece.web.data.EmployeeAttendance;

/**
 *
 * @author jeffry.amachree
 */
public class EmployeeAttendanceDAO extends BaseDAO{
    
    /**
     * Creates a new instance of DepartmentDAO
     */
    public EmployeeAttendanceDAO() {
    }
    
    /* ************* EmployeeAttendance ********** */
    public List<EmployeeAttendance> getAllAttendance(){
        Connection connection = null;
        List<EmployeeAttendance> records = new ArrayList<EmployeeAttendance>();
        try{
            connection = this.getConnection(true);
            ResultSet rst = this.executeQuery(DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_ATTENDANCE_SELECT, connection);
            
            while(rst.next()){
                EmployeeAttendance attendance = new EmployeeAttendance();
                attendance.setId(rst.getInt("idx"));
                attendance.setEmployeeId(rst.getInt("employee_id"));
                attendance.setActionDate(new Date(rst.getDate("action_date").getTime()));
                attendance.setActionType(rst.getString("action_type"));
                attendance.setActionTime(rst.getString("action_time"));
                attendance.setDevice(rst.getString("device"));
                
                attendance.setPunchError(rst.getString("punch_error"));
                attendance.setCustom1(rst.getString("custom1"));
                attendance.setCustom2(rst.getString("custom1"));
                attendance.setEmployeeName(rst.getString("firstname") + " " + rst.getString("lastname"));
                
                records.add(attendance);
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
    
    public List<EmployeeAttendance> getAllEmployeeAttendance(int employeeId){
        Connection connection = null;
        List<EmployeeAttendance> records = new ArrayList<EmployeeAttendance>();
        try{
            Vector param =new Vector();
            Vector type = new Vector();
            //set parameter....
            param.add(employeeId); type.add("NUMBER");
            
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(
                    connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_ATTENDANCE_SELECT_BY_EMPLOYEE, param, type);
            
            while(rst.next()){
                EmployeeAttendance attendance = new EmployeeAttendance();
                attendance.setId(rst.getInt("idx"));
                attendance.setEmployeeId(rst.getInt("employee_id"));
                attendance.setActionDate(new Date(rst.getDate("action_date").getTime()));
                attendance.setActionType(rst.getString("action_type"));
                attendance.setActionTime(rst.getString("action_time"));
                attendance.setDevice(rst.getString("device"));
                
                attendance.setPunchError(rst.getString("punch_error"));
                attendance.setCustom1(rst.getString("custom1"));
                attendance.setCustom2(rst.getString("custom1"));
                attendance.setEmployeeName(rst.getString("firstname") + " " + rst.getString("lastname"));
                
                records.add(attendance);
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
    
    public List<EmployeeAttendance> getAttendance(int id){
        Connection connection = null;
        List<EmployeeAttendance> records = new ArrayList<EmployeeAttendance>();
        try{
            Vector param =new Vector();
            Vector type = new Vector();
            //set parameter....
            param.add(id); type.add("NUMBER");
            
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(
                    connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_ATTENDANCE_SELECT_BY_ID, param, type);
            
            while(rst.next()){
                EmployeeAttendance attendance = new EmployeeAttendance();
                attendance.setId(rst.getInt("idx"));
                attendance.setEmployeeId(rst.getInt("employee_id"));
                attendance.setActionDate(new Date(rst.getDate("action_date").getTime()));
                attendance.setActionType(rst.getString("action_type"));
                attendance.setActionTime(rst.getString("action_time"));
                attendance.setDevice(rst.getString("device"));
                
                attendance.setPunchError(rst.getString("punch_error"));
                attendance.setCustom1(rst.getString("custom1"));
                attendance.setCustom2(rst.getString("custom1"));
                
                records.add(attendance);
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
    
    public int createNewAttendance(EmployeeAttendance attendance){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(attendance.getEmployeeId()); type.add("NUMBER");
            param.add(attendance.getActionDate()); type.add("DATE");
            param.add(attendance.getActionType()); type.add("STRING");
            param.add(attendance.getActionTime()); type.add("STRING");
            param.add(attendance.getDevice()); type.add("STRING");
            param.add(attendance.getPunchError()); type.add("STRING");
            param.add(attendance.getCustom1()); type.add("STRING");
            param.add(attendance.getCustom2()); type.add("STRING");
            
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_ATTENDANCE_INSERT,
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
    
    public int createBulkAttendance(List<EmployeeAttendance> attendanceList, String companyCode){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            connection = this.getConnection(false);
            startTransaction(connection);
            int saved = 0;
            for(EmployeeAttendance attendance : attendanceList){
                param =new Vector();
                type = new Vector();
                param.add(attendance.getEmployeeId()); type.add("NUMBER");
                param.add(companyCode); type.add("STRING");
                param.add(attendance.getActionDate()); type.add("DATE");
                param.add(attendance.getActionType()); type.add("STRING");
                param.add(attendance.getActionTime()); type.add("STRING");
                param.add(attendance.getDevice()); type.add("STRING");
                param.add(attendance.getPunchError()); type.add("STRING");
                param.add(attendance.getCustom1()); type.add("STRING");
                param.add(attendance.getCustom2()); type.add("STRING");

                System.out.println("Query..." + DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_ATTENDANCE_INSERT);
                
                int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_ATTENDANCE_INSERT,
                        param, type);
                
                saved = saved + 1;
            }
            
            commitTransaction(connection);
            
            return saved;
        }catch(Exception e){
            try{
                rollbackTransaction(connection);
            }catch(Exception er){}
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
    
    public int updateAttendance(EmployeeAttendance attendance){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(attendance.getEmployeeId()); type.add("NUMBER");
            param.add(attendance.getActionDate()); type.add("DATE");
            param.add(attendance.getActionType()); type.add("STRING");
            param.add(attendance.getActionTime()); type.add("STRING");
            param.add(attendance.getDevice()); type.add("STRING");
            param.add(attendance.getPunchError()); type.add("STRING");
            param.add(attendance.getCustom1()); type.add("STRING");
            param.add(attendance.getCustom2()); type.add("STRING");
            param.add(attendance.getId()); type.add("NUMBER");
            
            
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_ATTENDANCE_UPDATE,
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
    
    public int deleteAttendance(int attendance){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(attendance); type.add("NUMBER");
            
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_ATTENDANCE_DELETE,
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
    
    public int deleteAttendance(List<Integer> attendanceList){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            
            
            connection = this.getConnection(false);
            this.startTransaction(connection);
            for(int attendance : attendanceList){
                param.add(attendance); type.add("NUMBER");
                int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_ATTENDANCE_DELETE,
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
