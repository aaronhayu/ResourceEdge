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
import org.tenece.web.data.EmployeeEmergencyContact;

/**
 *
 * @author jeffry.amachree
 */
public class EmployeeEmergencyContactDAO extends BaseDAO{
    
    /**
     * Creates a new instance of DepartmentDAO
     */
    public EmployeeEmergencyContactDAO() {
    }
    
    public EmployeeEmergencyContact getEmergencyContactById(int id){
        Connection connection = null;
        EmployeeEmergencyContact records = new EmployeeEmergencyContact();
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_EMMERGENCY_CONTACT_SELECT_BY_ID,
                    param, type);
            
            while(rst.next()){
                EmployeeEmergencyContact item = new EmployeeEmergencyContact();
                item.setId(rst.getInt("contact_id"));
                item.setEmployeeId(rst.getInt("emp_number"));
                item.setContactName(rst.getString("contact_name"));
                item.setRelationship(rst.getString("relationship"));
                item.setHomePhone(rst.getString("home_no"));
                item.setMobilePhone(rst.getString("mobile_no"));
                item.setOfficePhone(rst.getString("office_no"));
                
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
    
    public List<EmployeeEmergencyContact> getEmployeeEmergencyContactById(long id){
        Connection connection = null;
        List<EmployeeEmergencyContact> records = new ArrayList<EmployeeEmergencyContact>();
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_EMMERGENCY_CONTACT_SELECT_BY_EMPLOYEE,
                    param, type);
            
            while(rst.next()){
                EmployeeEmergencyContact item = new EmployeeEmergencyContact();
                item.setId(rst.getInt("contact_id"));
                item.setEmployeeId(rst.getInt("emp_number"));
                item.setContactName(rst.getString("contact_name"));
                item.setRelationship(rst.getString("relationship"));
                item.setHomePhone(rst.getString("home_no"));
                item.setMobilePhone(rst.getString("mobile_no"));
                item.setOfficePhone(rst.getString("office_no"));
                
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
    
    public int createNewEmployeeEmergencyContact(EmployeeEmergencyContact EmployeeEmergencyContact){
        Connection connection = null;
        EmployeeEmergencyContact record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(EmployeeEmergencyContact.getEmployeeId()); type.add("NUMBER");
            param.add(EmployeeEmergencyContact.getContactName()); type.add("STRING");
            param.add(EmployeeEmergencyContact.getRelationship()); type.add("STRING");
            param.add(EmployeeEmergencyContact.getHomePhone()); type.add("STRING");
            param.add(EmployeeEmergencyContact.getMobilePhone()); type.add("STRING");
            param.add(EmployeeEmergencyContact.getOfficePhone()); type.add("STRING");
            
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_EMMERGENCY_CONTACT_INSERT,
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
    
    public int updateEmployeeEmergencyContact(EmployeeEmergencyContact EmployeeEmergencyContact){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            System.out.println(">>>>>>>>" + EmployeeEmergencyContact.getContactName());
            param.add(EmployeeEmergencyContact.getEmployeeId()); type.add("NUMBER");
            param.add(EmployeeEmergencyContact.getContactName()); type.add("STRING");
            param.add(EmployeeEmergencyContact.getRelationship()); type.add("STRING");
            param.add(EmployeeEmergencyContact.getHomePhone()); type.add("STRING");
            param.add(EmployeeEmergencyContact.getMobilePhone()); type.add("STRING");
            param.add(EmployeeEmergencyContact.getOfficePhone()); type.add("STRING");
            param.add(EmployeeEmergencyContact.getId()); type.add("NUMBER");
            
            connection = this.getConnection(false);
            System.out.println("Query>>>" + DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_EMMERGENCY_CONTACT_UPDATE);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_EMMERGENCY_CONTACT_UPDATE,
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

    public int deleteEmployeeEmergencyContact(List<Long> ids){
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
                i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_EMMERGENCY_CONTACT_DELETE,
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

    
    public int migrateEmployeeEmergencyContactToESS(long id){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            startTransaction(connection);

            this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_ESS_EMMERGENCY_CONTACT_DELETE_BY_EMPLOYEE,
                    param, type);

            int rows = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_ESS_EMMERGENCY_CONTACT_MIGRATION,
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

    public int migrateEmployeeESSEmergencyContactToDefault(long id, Connection connection) throws Exception{
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");

            this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_EMMERGENCY_CONTACT_DELETE_BY_EMPLOYEE,
                    param, type);

            int rows = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_ESS_EMMERGENCY_CONTACT_MIGRATION_REVERSED,
                    param, type);

            return rows;
        }catch(Exception e){
            throw e;
        }
    }//:!

    public int archiveEmployeeESSEmergencyContact(long transactionId, Connection connection) throws Exception{
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(transactionId);
            type.add("NUMBER");

            int rows = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_ESS_EMMERGENCY_CONTACT_MIGRATION_ARCHIVED,
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

            int rows = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_ESS_EMMERGENCY_CONTACT_INITIATE_TRANSACTION,
                    param, type);

            return rows;
        }catch(Exception e){
            e.printStackTrace();

            throw e;
        }
    }

    public EmployeeEmergencyContact getESS_EmergencyContactByID(long id){
        Connection connection = null;
        EmployeeEmergencyContact records = new EmployeeEmergencyContact();
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_ESS_EMMERGENCY_CONTACT_SELECT_BY_ID,
                    param, type);

            if(rst.next()){
                EmployeeEmergencyContact item = new EmployeeEmergencyContact();
                item.setId(rst.getInt("contact_id"));
                item.setEmployeeId(rst.getInt("emp_number"));
                item.setContactName(rst.getString("contact_name"));
                item.setRelationship(rst.getString("relationship"));
                item.setHomePhone(rst.getString("home_no"));
                item.setMobilePhone(rst.getString("mobile_no"));
                item.setOfficePhone(rst.getString("office_no"));

                records = item;
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

    public List<EmployeeEmergencyContact> getESS_EmployeeEmergencyContacts(long id){
        Connection connection = null;
        List<EmployeeEmergencyContact> records = new ArrayList<EmployeeEmergencyContact>();
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_ESS_EMMERGENCY_CONTACT_SELECT_BY_EMPLOYEE,
                    param, type);

            while(rst.next()){
                EmployeeEmergencyContact item = new EmployeeEmergencyContact();
                item.setId(rst.getInt("contact_id"));
                item.setEmployeeId(rst.getInt("emp_number"));
                item.setContactName(rst.getString("contact_name"));
                item.setRelationship(rst.getString("relationship"));
                item.setHomePhone(rst.getString("home_no"));
                item.setMobilePhone(rst.getString("mobile_no"));
                item.setOfficePhone(rst.getString("office_no"));

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

    public int createNewESS_EmployeeEmergencyContact(EmployeeEmergencyContact contact){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(contact.getEmployeeId()); type.add("NUMBER");
            param.add(contact.getContactName()); type.add("STRING");
            param.add(contact.getRelationship()); type.add("STRING");
            param.add(contact.getHomePhone()); type.add("STRING");
            param.add(contact.getMobilePhone()); type.add("STRING");
            param.add(contact.getOfficePhone()); type.add("STRING");
            if(contact.getTransactionId() == 0){
                param.add(contact.getTransactionId()); type.add("NUMBER_NULL");
            }else{
                param.add(contact.getTransactionId()); type.add("NUMBER");
            }
            param.add(contact.geteStatus()); type.add("STRING");

            System.out.println(">>>Query:" + DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_ESS_EMMERGENCY_CONTACT_INSERT);
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_ESS_EMMERGENCY_CONTACT_INSERT,
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

    public int updateESS_EmployeeEmergencyContact(EmployeeEmergencyContact contact){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(contact.getEmployeeId()); type.add("NUMBER");
            param.add(contact.getContactName()); type.add("STRING");
            param.add(contact.getRelationship()); type.add("STRING");
            param.add(contact.getHomePhone()); type.add("STRING");
            param.add(contact.getMobilePhone()); type.add("STRING");
            param.add(contact.getOfficePhone()); type.add("STRING");
            if(contact.getTransactionId() == 0){
                //param.add(contact.getTransactionId()); type.add("NUMBER_NULL");
            }else{
                //param.add(contact.getTransactionId()); type.add("NUMBER");
            }
            //param.add(employeeEducation.geteStatus()); type.add("STRING");
            param.add(contact.getId()); type.add("NUMBER");

            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_ESS_EMMERGENCY_CONTACT_UPDATE,
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

    public int deleteESS_EmployeeEmergencyContact(EmployeeEmergencyContact contact){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{

            param.add(contact.getId()); type.add("NUMBER");

            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_ESS_EMMERGENCY_CONTACT_DELETE,
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
