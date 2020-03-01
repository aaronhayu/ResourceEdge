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
import org.tenece.web.data.EmployeeChildren;
import org.tenece.web.data.EmployeeChildren;

/**
 *
 * @author jeffry.amachree
 */
public class EmployeeChildrenDAO extends BaseDAO{
    
    /**
     * Creates a new instance of DepartmentDAO
     */
    public EmployeeChildrenDAO() {
    }
    
    public EmployeeChildren getEmployeeChildById(int id){
        Connection connection = null;
        EmployeeChildren child = new EmployeeChildren();
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_CHILDREN_SELECT_BY_ID,
                    param, type);
            
            while(rst.next()){
                EmployeeChildren item = new EmployeeChildren();
                item.setId(rst.getInt("child_id"));
                item.setEmployeeId(rst.getInt("emp_number"));
                item.setSequence(rst.getInt("seqno"));
                item.setBirthName(rst.getString("birth_name"));
                item.setDate_Of_Birth(rst.getDate("date_of_birth"));
                item.setPlaceOfBirth(rst.getString("placeofbirth"));
                item.setPresentAddress(rst.getString("presentaddress"));
                item.setRemarks(rst.getString("remarks"));
                
                child = item;
            }
        }catch(Exception e){
            
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return child;
    }
    
    public List<EmployeeChildren> getEmployeeChildrenById(int id){
        Connection connection = null;
        List<EmployeeChildren> records = new ArrayList<EmployeeChildren>();
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_CHILDREN_SELECT_BY_EMPLOYEE,
                    param, type);
            
            while(rst.next()){
                EmployeeChildren item = new EmployeeChildren();
                item.setId(rst.getInt("child_id"));
                item.setEmployeeId(rst.getInt("emp_number"));
                item.setSequence(rst.getInt("seqno"));
                item.setBirthName(rst.getString("birth_name"));
                item.setDate_Of_Birth(rst.getDate("date_of_birth"));

                item.setPlaceOfBirth(rst.getString("placeofbirth"));
                item.setPresentAddress(rst.getString("presentaddress"));
                item.setRemarks(rst.getString("remarks"));
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
    
    public int createNewEmployeeChildren(EmployeeChildren EmployeeChildren){
        Connection connection = null;
        EmployeeChildren record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(EmployeeChildren.getEmployeeId()); type.add("NUMBER");
            param.add(EmployeeChildren.getSequence()); type.add("NUMBER");
            param.add(EmployeeChildren.getBirthName()); type.add("STRING");
            param.add(EmployeeChildren.getDate_Of_Birth()); type.add("DATE");
            param.add(EmployeeChildren.getPlaceOfBirth()); type.add("STRING");
            param.add(EmployeeChildren.getPresentAddress()); type.add("STRING");
            param.add(EmployeeChildren.getRemarks()); type.add("STRING");
            
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_CHILDREN_INSERT,
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
    
    public int updateEmployeeChildren(EmployeeChildren EmployeeChildren){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(EmployeeChildren.getEmployeeId()); type.add("NUMBER");
            param.add(EmployeeChildren.getSequence()); type.add("NUMBER");
            param.add(EmployeeChildren.getBirthName()); type.add("STRING");
            param.add(EmployeeChildren.getDate_Of_Birth()); type.add("DATE");
            param.add(EmployeeChildren.getPlaceOfBirth()); type.add("STRING");
            param.add(EmployeeChildren.getPresentAddress()); type.add("STRING");
            param.add(EmployeeChildren.getRemarks()); type.add("STRING");
            param.add(EmployeeChildren.getId()); type.add("NUMBER");
            
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_CHILDREN_UPDATE,
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
    
    public int deleteEmployeeChildren(List<Long> ids){
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
                i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_CHILDREN_DELETE,
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


    public int migrateEmployeeChildrenToESS(long id){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            startTransaction(connection);

            this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_ESS_CHILDREN_DELETE_ALL_EMPLOYEE,
                    param, type);

            int rows = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_ESS_CHILDREN_MIGRATION,
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

    public int migrateEmployeeESSChildrenToDefault(long id, Connection connection) throws Exception{
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");

            this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_CHILDREN_DELETE_ALL_EMPLOYEE,
                    param, type);

            int rows = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_ESS_CHILDREN_MIGRATION_REVERSED,
                    param, type);

            return rows;
        }catch(Exception e){
            throw e;
        }
    }//:!

    public int archiveEmployeeESSChildren(long transactionId, Connection connection) throws Exception{
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(transactionId);
            type.add("NUMBER");

            int rows = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_ESS_CHILDREN_MIGRATION_ARCHIVED,
                    param, type);

            return rows;
        }catch(Exception e){
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
            
            int rows = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_ESS_CHILDREN_INITIATE_TRANSACTION,
                    param, type);

            return rows;
        }catch(Exception e){
            e.printStackTrace();

            throw e;
        }
    }

    public EmployeeChildren getESS_EmployeeChildBySequence(int id){
        Connection connection = null;
        EmployeeChildren child = new EmployeeChildren();
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_ESS_CHILDREN_SELECT_BY_SEQUENCE,
                    param, type);

            while(rst.next()){
                EmployeeChildren item = new EmployeeChildren();
                item.setEmployeeId(rst.getInt("emp_number"));
                item.setSequence(rst.getInt("seqno"));
                item.setBirthName(rst.getString("birth_name"));
                item.setDate_Of_Birth(rst.getDate("date_of_birth"));

                child = item;
            }
        }catch(Exception e){

        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return child;
    }

    public List<EmployeeChildren> getESS_EmployeeChildren(int id){
        Connection connection = null;
        List<EmployeeChildren> records = new ArrayList<EmployeeChildren>();
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_ESS_CHILDREN_SELECT_BY_EMPLOYEE,
                    param, type);

            while(rst.next()){
                EmployeeChildren item = new EmployeeChildren();
                item.setEmployeeId(rst.getInt("emp_number"));
                item.setSequence(rst.getInt("seqno"));
                item.setBirthName(rst.getString("birth_name"));
                item.setDate_Of_Birth(rst.getDate("date_of_birth"));
                item.setPlaceOfBirth(rst.getString("placeofbirth"));
                item.setPresentAddress(rst.getString("presentaddress"));
                item.setRemarks(rst.getString("remarks"));

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

    public int createNewESSEmployeeChild(EmployeeChildren employeeChildren){
        Connection connection = null;
        EmployeeChildren record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(employeeChildren.getEmployeeId()); type.add("NUMBER");
            param.add(employeeChildren.getSequence()); type.add("NUMBER");
            param.add(employeeChildren.getBirthName()); type.add("STRING");
            param.add(employeeChildren.getDate_Of_Birth()); type.add("DATE");
            if(employeeChildren.getTransactionId() == 0){
                param.add(employeeChildren.getTransactionId()); type.add("NUMBER_NULL");
            }else{
                param.add(employeeChildren.getTransactionId()); type.add("NUMBER");
            }
            param.add(employeeChildren.geteStatus()); type.add("STRING");

            param.add(employeeChildren.getPlaceOfBirth()); type.add("STRING");
            param.add(employeeChildren.getPresentAddress()); type.add("STRING");
            param.add(employeeChildren.getRemarks()); type.add("STRING");

            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_ESS_CHILDREN_INSERT,
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

    public int updateESS_EmployeeChild(EmployeeChildren employeeChildren){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(employeeChildren.getEmployeeId()); type.add("NUMBER");
            param.add(employeeChildren.getSequence()); type.add("NUMBER");
            param.add(employeeChildren.getBirthName()); type.add("STRING");
            param.add(employeeChildren.getDate_Of_Birth()); type.add("DATE");
            if(employeeChildren.getTransactionId() == 0){
                param.add(employeeChildren.getTransactionId()); type.add("NUMBER_NULL");
            }else{
                param.add(employeeChildren.getTransactionId()); type.add("NUMBER");
            }
            param.add(employeeChildren.geteStatus()); type.add("STRING");
            param.add(employeeChildren.getPlaceOfBirth()); type.add("STRING");
            param.add(employeeChildren.getPresentAddress()); type.add("STRING");
            param.add(employeeChildren.getRemarks()); type.add("STRING");
            
            param.add(employeeChildren.getSequence()); type.add("NUMBER");
            
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_ESS_CHILDREN_UPDATE,
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

    public int deleteESS_EmployeeChild(EmployeeChildren EmployeeChildren){
        Connection connection = null;
        EmployeeChildren record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{

            param.add(EmployeeChildren.getSequence()); type.add("NUMBER");

            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_ESS_CHILDREN_DELETE,
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
