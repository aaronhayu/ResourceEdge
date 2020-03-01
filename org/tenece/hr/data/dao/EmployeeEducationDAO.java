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
import org.tenece.web.data.EmployeeEducation;

/**
 *
 * @author jeffry.amachree
 */
public class EmployeeEducationDAO extends BaseDAO{
    
    /**
     * Creates a new instance of DepartmentDAO
     */
    public EmployeeEducationDAO() {
    }
    
    public EmployeeEducation getEducationById(int id){
        Connection connection = null;
        EmployeeEducation records = new EmployeeEducation();
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_EDUCATION_SELECT_BY_ID,
                    param, type);
            
            while(rst.next()){
                EmployeeEducation item = new EmployeeEducation();
                item.setId(rst.getInt("edu_code"));
                item.setEmployeeId(rst.getInt("emp_number"));
                item.setInstitution(rst.getString("institution"));
                item.setCourse(rst.getString("course"));
                item.setEducationYear(rst.getLong("edu_year"));
                item.setQualification(rst.getString("edu_qualification"));
                item.setStartDate(rst.getDate("edu_start_date"));
                item.setEndDate(rst.getDate("edu_end_date"));
                
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
    
    public List<EmployeeEducation> getEmployeeEducationById(int id){
        Connection connection = null;
        List<EmployeeEducation> records = new ArrayList<EmployeeEducation>();
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_EDUCATION_SELECT_BY_EMPLOYEE,
                    param, type);
            
            while(rst.next()){
                EmployeeEducation item = new EmployeeEducation();
                item.setId(rst.getInt("edu_code"));
                item.setEmployeeId(rst.getInt("emp_number"));
                item.setInstitution(rst.getString("institution"));
                item.setCourse(rst.getString("course"));
                item.setEducationYear(rst.getLong("edu_year"));
                item.setQualification(rst.getString("edu_qualification"));
                item.setStartDate(rst.getDate("edu_start_date"));
                item.setEndDate(rst.getDate("edu_end_date"));
                
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
    
    public int createNewEmployeeEducation(EmployeeEducation EmployeeEducation){
        Connection connection = null;
        EmployeeEducation record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(EmployeeEducation.getEmployeeId()); type.add("NUMBER");
            param.add(EmployeeEducation.getInstitution()); type.add("STRING");
            param.add(EmployeeEducation.getCourse()); type.add("STRING");
            param.add(EmployeeEducation.getEducationYear()); type.add("NUMBER");
            param.add(EmployeeEducation.getQualification()); type.add("STRING");
            param.add(EmployeeEducation.getStartDate()); type.add("DATE");
            param.add(EmployeeEducation.getEndDate()); type.add("DATE");
            
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_EDUCATION_INSERT,
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
    
    public int updateEmployeeEducation(EmployeeEducation EmployeeEducation){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(EmployeeEducation.getEmployeeId()); type.add("NUMBER");
            param.add(EmployeeEducation.getInstitution()); type.add("STRING");
            param.add(EmployeeEducation.getCourse()); type.add("STRING");
            param.add(EmployeeEducation.getEducationYear()); type.add("NUMBER");
            param.add(EmployeeEducation.getQualification()); type.add("STRING");
            param.add(EmployeeEducation.getStartDate()); type.add("DATE");
            param.add(EmployeeEducation.getEndDate()); type.add("DATE");
            param.add(EmployeeEducation.getId()); type.add("NUMBER");
            
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_EDUCATION_UPDATE,
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

    public int deleteEmployeeEducation(List<Long> ids){
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
                i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_EDUCATION_DELETE,
                        param, type);
            }
            commitTransaction(connection);
            return i;
        }catch(Exception e){
            e.printStackTrace();
            try {
                rollbackTransaction(connection);
            } catch (SQLException ex) {

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
    
    
    public int migrateEmployeeEducationToESS(long id){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            startTransaction(connection);

            this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_ESS_EDUCATION_DELETE_ALL_EMPLOYEE,
                    param, type);

            int rows = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_ESS_EDUCATION_MIGRATION,
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

    public int migrateEmployeeESSEducationToDefault(long id, Connection connection) throws Exception{
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");

            this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_EDUCATION_DELETE_ALL_EMPLOYEE,
                    param, type);

            int rows = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_ESS_EDUCATION_MIGRATION_REVERSED,
                    param, type);

            return rows;
        }catch(Exception e){
            throw e;
        }
    }//:!

    public int archiveEmployeeESSEducation(long transactionId, Connection connection) throws Exception{
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(transactionId);
            type.add("NUMBER");

            int rows = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_ESS_EDUCATION_MIGRATION_ARCHIVED,
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

            int rows = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_ESS_EDUCATION_INITIATE_TRANSACTION,
                    param, type);

            return rows;
        }catch(Exception e){
            e.printStackTrace();

            throw e;
        }
    }

    public EmployeeEducation getESS_EducationByID(int id){
        Connection connection = null;
        EmployeeEducation records = new EmployeeEducation();
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_ESS_EDUCATION_SELECT_BY_ID,
                    param, type);

            if(rst.next()){
                EmployeeEducation item = new EmployeeEducation();
                item.setId(rst.getInt("edu_code"));
                item.setEmployeeId(rst.getInt("emp_number"));
                item.setInstitution(rst.getString("institution"));
                item.setCourse(rst.getString("course"));
                item.setEducationYear(rst.getLong("edu_year"));
                item.setQualification(rst.getString("edu_qualification"));
                item.setStartDate(rst.getDate("edu_start_date"));
                item.setEndDate(rst.getDate("edu_end_date"));

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

    public List<EmployeeEducation> getESS_EmployeeEducation(long id){
        Connection connection = null;
        List<EmployeeEducation> records = new ArrayList<EmployeeEducation>();
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_ESS_EDUCATION_SELECT_BY_EMPLOYEE,
                    param, type);

            while(rst.next()){
                EmployeeEducation item = new EmployeeEducation();
                item.setId(rst.getInt("edu_code"));
                item.setEmployeeId(rst.getInt("emp_number"));
                item.setInstitution(rst.getString("institution"));
                item.setCourse(rst.getString("course"));
                item.setEducationYear(rst.getLong("edu_year"));
                item.setQualification(rst.getString("edu_qualification"));
                item.setStartDate(rst.getDate("edu_start_date"));
                item.setEndDate(rst.getDate("edu_end_date"));

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

    public int createNewESS_EmployeeEducation(EmployeeEducation employeeEducation){
        Connection connection = null;
        EmployeeEducation record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(employeeEducation.getEmployeeId()); type.add("NUMBER");
            param.add(employeeEducation.getInstitution()); type.add("STRING");
            param.add(employeeEducation.getCourse()); type.add("STRING");
            param.add(employeeEducation.getEducationYear()); type.add("NUMBER");
            param.add(employeeEducation.getQualification()); type.add("STRING");
            param.add(employeeEducation.getStartDate()); type.add("DATE");
            param.add(employeeEducation.getEndDate()); type.add("DATE");
            if(employeeEducation.getTransactionId() == 0){
                param.add(employeeEducation.getTransactionId()); type.add("NUMBER_NULL");
            }else{
                param.add(employeeEducation.getTransactionId()); type.add("NUMBER");
            }
            param.add(employeeEducation.geteStatus()); type.add("STRING");

            System.out.println(">>>Query:" + DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_ESS_EDUCATION_INSERT);
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_ESS_EDUCATION_INSERT,
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

    public int updateESS_EmployeeEducation(EmployeeEducation employeeEducation){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(employeeEducation.getEmployeeId()); type.add("NUMBER");
            param.add(employeeEducation.getInstitution()); type.add("STRING");
            param.add(employeeEducation.getCourse()); type.add("STRING");
            param.add(employeeEducation.getEducationYear()); type.add("NUMBER");
            param.add(employeeEducation.getQualification()); type.add("STRING");
            param.add(employeeEducation.getStartDate()); type.add("DATE");
            param.add(employeeEducation.getEndDate()); type.add("DATE");
            if(employeeEducation.getTransactionId() == 0){
                //param.add(employeeEducation.getTransactionId()); type.add("NUMBER_NULL");
            }else{
                //param.add(employeeEducation.getTransactionId()); type.add("NUMBER");
            }
            //param.add(employeeEducation.geteStatus()); type.add("STRING");
            param.add(employeeEducation.getId()); type.add("NUMBER");

            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_ESS_EDUCATION_UPDATE,
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

    public int deleteESS_EmployeeEducation(EmployeeEducation employeeEducation){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{

            param.add(employeeEducation.getId()); type.add("NUMBER");

            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_ESS_EDUCATION_DELETE,
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
