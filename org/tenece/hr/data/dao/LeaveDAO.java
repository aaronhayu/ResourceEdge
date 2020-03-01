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
import org.tenece.web.data.Leave;
import org.tenece.web.data.LeaveType;

/**
 *
 * @author jeffry.amachree
 */
public class LeaveDAO extends BaseDAO{
    
    /**
     * Creates a new instance of LeaveTypeDAO
     */
    public LeaveDAO() {
    }
    
    /* ************* LeaveType ********** */
    
    public List<LeaveType> getAllLeaveType(){
        Connection connection = null;
        List<LeaveType> records = new ArrayList<LeaveType>();
        try{
            connection = this.getConnection(true);
            ResultSet rst = this.executeQuery(DatabaseQueryLoader.getQueryAssignedConstant().LEAVE_TYPE_SELECT, connection);
           
            while(rst.next()){
                LeaveType type = new LeaveType();
                type.setId(rst.getInt("id"));
                type.setDescription(rst.getString("description"));
                type.setGuide(rst.getString("guide"));
                type.setPaidLeave(rst.getInt("paid_leave"));
                type.setCompanyCode(rst.getString("company_code"));
                
                records.add(type);
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

    public List<LeaveType> getAllLeaveTypeByCompany(String code){
        Connection connection = null;
        List<LeaveType> records = new ArrayList<LeaveType>();
        Vector param = new Vector();
        Vector types = new Vector();

        try{
            param.add(code); types.add("STRING");

            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection,
                    DatabaseQueryLoader.getQueryAssignedConstant().LEAVE_TYPE_SELECT_BY_COMPANY, param, types);

            while(rst.next()){
                LeaveType type = new LeaveType();
                type.setId(rst.getInt("id"));
                type.setDescription(rst.getString("description"));
                type.setGuide(rst.getString("guide"));
                type.setPaidLeave(rst.getInt("paid_leave"));
                type.setCompanyCode(rst.getString("company_code"));

                records.add(type);
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

    public LeaveType getLeaveTypeById(int id){
        Connection connection = null;
        LeaveType record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().LEAVE_TYPE_SELECT_BY_ID,
                    param, type);
            
            if(rst.next()){
                LeaveType leaveTtype = new LeaveType();
                leaveTtype.setId(rst.getInt("id"));
                leaveTtype.setDescription(rst.getString("description"));
                leaveTtype.setGuide(rst.getString("guide"));
                leaveTtype.setPaidLeave(rst.getInt("paid_leave"));
                leaveTtype.setCompanyCode(rst.getString("company_code"));
                
                record = leaveTtype;
            }
        }catch(Exception e){
            
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return record;
    }
    
    public int createNewLeaveType(LeaveType leaveType){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(leaveType.getDescription()); type.add("STRING");
            param.add(leaveType.getGuide()); type.add("STRING");
            param.add(leaveType.getPaidLeave()); type.add("NUMBER");
            param.add(leaveType.getCompanyCode()); type.add("STRING");
            
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().LEAVE_TYPE_INSERT,
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
    
    public int updateLeaveType(LeaveType leaveType){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(leaveType.getDescription()); type.add("STRING");
            param.add(leaveType.getGuide()); type.add("STRING");
            param.add(leaveType.getPaidLeave()); type.add("NUMBER");
            param.add(leaveType.getCompanyCode()); type.add("STRING");
            param.add(leaveType.getId()); type.add("NUMBER");
            
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().LEAVE_TYPE_UPDATE,
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
    
    public int deleteLeaveType(LeaveType leaveType){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(leaveType.getId()); type.add("NUMBER");
            
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().LEAVE_TYPE_DELETE,
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
    
    public int deleteLeaveTypes(List<Integer> types){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            
            
            connection = this.getConnection(false);
            this.startTransaction(connection);
            for(int leaveType : types){
                param.add(leaveType); type.add("NUMBER");
                int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().LEAVE_TYPE_DELETE,
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

    /* ===== LEAVE APPLICATION ===== */
    public List<Leave> getAllLeaveApplication(){
        return getAllLeaveApplication(null, null);
    }
    public List<Leave> getAllLeaveApplication(String criteria, String searchText){
        Connection connection = null;
        List<Leave> records = new ArrayList<Leave>();
        try{
            connection = this.getConnection(true);
            ResultSet rst = null;
            if(criteria == null || searchText == null){
                rst = this.executeQuery(DatabaseQueryLoader.getQueryAssignedConstant().LEAVE_SELECT, connection);
            }else{
                String query = "";
                //param.add(searchText); type.add("STRING");

                if(criteria.equalsIgnoreCase("NAME")){ 
                    query = DatabaseQueryLoader.getQueryAssignedConstant().LEAVE_SELECT_SEARCH_BY_APPLICANT;
                }else if(criteria.equalsIgnoreCase("ID")){
                    query = DatabaseQueryLoader.getQueryAssignedConstant().LEAVE_SELECT_SEARCH_BY_LEAVETYPE;
                }
                query = query.replaceAll("_SEARCH_", searchText);

                rst = this.executeQuery(query, connection);
            }
            

            while(rst.next()){
                Leave leave = new Leave();
                leave.setId(rst.getInt("id"));
                leave.setEmployeeId(rst.getInt("emp_number"));
                leave.setYear(rst.getInt("leave_year"));
                leave.setLeaveTypeId(rst.getInt("leave_type_id"));
                leave.setStartDate(new Date(rst.getDate("start_date").getTime()));
                leave.setEndDate(new Date(rst.getDate("end_date").getTime()));
                leave.setReliefOfficer(rst.getInt("relief_officer"));
                leave.setContactAddress(rst.getString("contact_address"));
                leave.setContactMobile(rst.getString("contact_number"));
                leave.setReason(rst.getString("reason"));
                leave.setTransactionId(rst.getLong("transactionid"));
                leave.seteStatus(rst.getString("estatus"));
                leave.setLeaveTypeDescription(rst.getString("leavetypedesc"));
                leave.setEmployeeName(rst.getString("emp_name"));
                leave.setReliefOfficerName(rst.getString("reliefofficer_name"));
                leave.setCompanyCode(rst.getString("company_code"));

                records.add(leave);
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

    public List<Leave> getAllLeaveApplicationByCompany(String code){
        return getAllLeaveApplicationByCompany(code, null, null);
    }
    public List<Leave> getAllLeaveApplicationByCompany(String code, String criteria, String searchText){
        Connection connection = null;
        List<Leave> records = new ArrayList<Leave>();

        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(code); type.add("STRING");

            connection = this.getConnection(true);
            ResultSet rst = null;
            if(criteria == null || searchText == null){
                rst = this.executeParameterizedQuery(connection,
                        DatabaseQueryLoader.getQueryAssignedConstant().LEAVE_SELECT_BY_COMPANY, param, type);
            }else{
                String query = "";
                //param.add(searchText); type.add("STRING");

                if(criteria.equalsIgnoreCase("NAME")){
                    query = DatabaseQueryLoader.getQueryAssignedConstant().LEAVE_SELECT_BY_COMPANY_SEARCH_BY_APPLICANT;
                }else if(criteria.equalsIgnoreCase("ID")){
                    query = DatabaseQueryLoader.getQueryAssignedConstant().LEAVE_SELECT_BY_COMPANY_SEARCH_BY_LEAVETYPE;
                }
                query = query.replaceAll("_SEARCH_", searchText);

                rst = this.executeParameterizedQuery(connection, query, param, type);
            }


            while(rst.next()){
                Leave leave = new Leave();
                leave.setId(rst.getInt("id"));
                leave.setEmployeeId(rst.getInt("emp_number"));
                leave.setYear(rst.getInt("leave_year"));
                leave.setLeaveTypeId(rst.getInt("leave_type_id"));
                leave.setStartDate(new Date(rst.getDate("start_date").getTime()));
                leave.setEndDate(new Date(rst.getDate("end_date").getTime()));
                leave.setReliefOfficer(rst.getInt("relief_officer"));
                leave.setContactAddress(rst.getString("contact_address"));
                leave.setContactMobile(rst.getString("contact_number"));
                leave.setReason(rst.getString("reason"));
                leave.setTransactionId(rst.getLong("transactionid"));
                leave.seteStatus(rst.getString("estatus"));
                leave.setLeaveTypeDescription(rst.getString("leavetypedesc"));
                leave.setEmployeeName(rst.getString("emp_name"));
                leave.setReliefOfficerName(rst.getString("reliefofficer_name"));
                leave.setCompanyCode(rst.getString("company_code"));

                records.add(leave);
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

    public Leave getLeaveById(long id){
        Connection connection = null;
        try{
            connection = this.getConnection(true);
            return getLeaveById(id, connection, DatabaseQueryLoader.getQueryAssignedConstant().LEAVE_SELECT_BY_ID);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    /**
     *
     * @param id This is teh ID of the transaction
     * @return
     */
    public Leave getLeaveByTransactionId(long id){
        Connection connection = null;
        try{
            connection = this.getConnection(true);
            return getLeaveById(id, connection, DatabaseQueryLoader.getQueryAssignedConstant().LEAVE_SELECT_BY_TRANSACTION);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Leave getLastLeaveForEmployee(long id){
        Connection connection = null;
        try{
            connection = this.getConnection(true);
            return getLeaveById(id, connection, DatabaseQueryLoader.getQueryAssignedConstant().LEAVE_SELECT_BY_EMPLOYEE);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * This will be called when processing a transaction
     * @param id
     * @param connection
     * @return
     */
    public Leave getLeaveByTransactionId(long id, Connection connection){
        return getLeaveById(id, connection, DatabaseQueryLoader.getQueryAssignedConstant().LEAVE_SELECT_BY_TRANSACTION);
    }

    public Leave getLeaveById(long id, Connection connection, String query){
        
        Leave record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            //execute query
            ResultSet rst = this.executeParameterizedQuery(connection, query,
                    param, type);

            if(rst.next()){
                Leave leave = new Leave();
                leave.setId(rst.getInt("id"));
                leave.setEmployeeId(rst.getInt("emp_number"));
                leave.setYear(rst.getInt("leave_year"));
                leave.setLeaveTypeId(rst.getInt("leave_type_id"));
                leave.setStartDate(new Date(rst.getDate("start_date").getTime()));
                leave.setEndDate(new Date(rst.getDate("end_date").getTime()));
                leave.setReliefOfficer(rst.getInt("relief_officer"));
                leave.setContactAddress(rst.getString("contact_address"));
                leave.setContactMobile(rst.getString("contact_number"));
                leave.setReason(rst.getString("reason"));
                leave.setTransactionId(rst.getLong("transactionid"));
                leave.seteStatus(rst.getString("estatus"));
                leave.setLeaveTypeDescription(rst.getString("leavetypedesc"));
                leave.setEmployeeName(rst.getString("emp_name"));
                leave.setReliefOfficerName(rst.getString("reliefofficer_name"));

                record = leave;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return record;
    }

    public Integer getSumOfLeaveByEmployee(long employeeId){
        int counted = 0;
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(employeeId); type.add("NUMBER");
            //execute query
            connection = this.getConnection(false);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().LEAVE_COUNT_FOR_EMPLOYEE,
                    param, type);

            if(rst.next()){
                counted = rst.getInt(1);

            }
        }catch(Exception e){
            e.printStackTrace();
            return counted;
        }
        return counted;
    }

    public Integer getCountOfPendingLeavesByEmployee(long employeeId){
        int counted = 0;
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(employeeId); type.add("NUMBER");
            //execute query
            connection = this.getConnection(false);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().LEAVE_COUNT_PENDING_FOR_EMPLOYEE,
                    param, type);
            
            if(rst.next()){
                counted = rst.getInt(1);

            }
        }catch(Exception e){
            e.printStackTrace();
            return counted;
        }
        return counted;
    }

    public int createNewLeave(Leave leave){
        Connection connection = null;
        try{
            connection = this.getConnection(false);
            return createNewLeave(leave, connection);
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
    }
    public int createNewLeave(Leave leave, Connection connection){
        
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            
            param.add(leave.getEmployeeId()); type.add("NUMBER");
            param.add(leave.getYear()); type.add("NUMBER");
            param.add(leave.getLeaveTypeId()); type.add("NUMBER");
            param.add(leave.getStartDate()); type.add("DATE");
            param.add(leave.getEndDate()); type.add("DATE");
            param.add(leave.getReliefOfficer()); type.add("NUMBER");
            param.add(leave.getContactAddress()); type.add("STRING");
            param.add(leave.getContactMobile()); type.add("STRING");
            param.add(leave.getReason()); type.add("STRING");
            if(leave.getTransactionId() == 0){
                param.add(leave.getTransactionId()); type.add("NUMBER_NULL");
            }else{
                param.add(leave.getTransactionId()); type.add("NUMBER");
            }
            param.add(leave.geteStatus()); type.add("STRING");

            //execute query
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().LEAVE_INSERT,
                    param, type);

            return i;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }//:~

    public int updateLeave(Leave leave){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(leave.getEmployeeId()); type.add("NUMBER");
            param.add(leave.getYear()); type.add("NUMBER");
            param.add(leave.getLeaveTypeId()); type.add("NUMBER");
            param.add(leave.getStartDate()); type.add("DATE");
            param.add(leave.getEndDate()); type.add("DATE");
            param.add(leave.getReliefOfficer()); type.add("NUMBER");
            param.add(leave.getContactAddress()); type.add("STRING");
            param.add(leave.getContactMobile()); type.add("STRING");
            param.add(leave.getReason()); type.add("STRING");
            if(leave.getTransactionId() == 0){
                param.add(leave.getTransactionId()); type.add("NUMBER_NULL");
            }else{
                param.add(leave.getTransactionId()); type.add("NUMBER");
            }
            param.add(leave.geteStatus()); type.add("STRING");
            param.add(leave.getId()); type.add("NUMBER");

            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().LEAVE_UPDATE,
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

    public int updateLeaveStatus(Leave leave){
        Connection connection = null;
        try{
            connection = this.getConnection(false);
            return updateLeaveStatus(leave, connection);
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
    }
    public int updateLeaveStatus(Leave leave, Connection connection){
        Vector param =new Vector();
        Vector type = new Vector();
        try{

            param.add(leave.geteStatus()); type.add("STRING");
            param.add(leave.getTransactionId()); type.add("NUMBER");

            //execute query
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().LEAVE_UPDATE_STATUS_BY_TRANSACTION,
                    param, type);

            return i;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }//:~

    public int deleteLeave(Leave leave){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(leave.getId()); type.add("NUMBER");

            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().LEAVE_DELETE,
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

    public int deleteLeave(List<Integer> types){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{

            connection = this.getConnection(false);
            this.startTransaction(connection);
            for(int leave : types){
                param.add(leave); type.add("NUMBER");
                int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().LEAVE_DELETE,
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

    /* ================= Leave Resumption ========================= */
    /* ===== LEAVE APPLICATION ===== */
    public List<Leave.LeaveResumption> getAllLeaveResumption(){
        Connection connection = null;
        List<Leave.LeaveResumption> records = new ArrayList<Leave.LeaveResumption>();
        try{
            connection = this.getConnection(true);
            ResultSet rst = this.executeQuery(DatabaseQueryLoader.getQueryAssignedConstant().LEAVE_RESUMPTION_SELECT, connection);

            while(rst.next()){
                Leave.LeaveResumption leave = new Leave.LeaveResumption();
                leave.setId(rst.getInt("id"));
                leave.setStartDate(new Date(rst.getDate("start_date").getTime()));
                leave.setEndDate(new Date(rst.getDate("end_date").getTime()));
                leave.setReason(rst.getString("reason"));
                leave.setTransactionId(rst.getLong("transactionid"));
                leave.seteStatus(rst.getString("estatus"));

                records.add(leave);
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

    public Leave.LeaveResumption getLeaveResumptionById(long id){
        Connection connection = null;
        try{
            connection = this.getConnection(true);
            return getLeaveResumptionById(id, connection, DatabaseQueryLoader.getQueryAssignedConstant().LEAVE_RESUMPTION_SELECT_BY_LEAVE_ID);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    /**
     *
     * @param id This is teh ID of the transaction
     * @return
     */
    public Leave.LeaveResumption getLeaveResumptionByTransactionId(long id){
        Connection connection = null;
        try{
            connection = this.getConnection(true);
            return getLeaveResumptionById(id, connection, DatabaseQueryLoader.getQueryAssignedConstant().LEAVE_RESUMPTION_SELECT_BY_TRANSACTION);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This will be called when processing a transaction
     * @param id
     * @param connection
     * @return
     */
    public Leave.LeaveResumption getLeaveResumptionByTransactionId(long id, Connection connection){
        return getLeaveResumptionById(id, connection, DatabaseQueryLoader.getQueryAssignedConstant().LEAVE_RESUMPTION_SELECT_BY_TRANSACTION);
    }

    public Leave.LeaveResumption getLeaveResumptionById(long id, Connection connection, String query){

        Leave.LeaveResumption record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            //execute query
            ResultSet rst = this.executeParameterizedQuery(connection, query,
                    param, type);

            if(rst.next()){
                Leave.LeaveResumption leave = new Leave.LeaveResumption();
                leave.setId(rst.getInt("id"));
                leave.setLeaveId(rst.getLong("leave_id"));
                leave.setStartDate(new Date(rst.getDate("start_date").getTime()));
                leave.setEndDate(new Date(rst.getDate("end_date").getTime()));
                leave.setReason(rst.getString("reason"));
                leave.setTransactionId(rst.getLong("transactionid"));
                leave.seteStatus(rst.getString("estatus"));

                record = leave;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return record;
    }

    public int createNewLeaveResumption(Leave.LeaveResumption leave){
        Connection connection = null;
        try{
            connection = this.getConnection(false);
            return createNewLeaveResumption(leave, connection);
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
    }
    public int createNewLeaveResumption(Leave.LeaveResumption leave, Connection connection){

        Vector param =new Vector();
        Vector type = new Vector();
        try{

            param.add(leave.getLeaveId()); type.add("NUMBER");
            param.add(leave.getStartDate()); type.add("DATE");
            param.add(leave.getEndDate()); type.add("DATE");
            param.add(leave.getReason()); type.add("STRING");
            if(leave.getTransactionId() == 0){
                param.add(leave.getTransactionId()); type.add("NUMBER_NULL");
            }else{
                param.add(leave.getTransactionId()); type.add("NUMBER");
            }
            param.add(leave.geteStatus()); type.add("STRING");

            //execute query
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().LEAVE_RESUMPTION_INSERT,
                    param, type);

            return i;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }//:~

    public int updateLeaveResumption(Leave.LeaveResumption leave){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(leave.getLeaveId()); type.add("NUMBER");
            param.add(leave.getStartDate()); type.add("DATE");
            param.add(leave.getEndDate()); type.add("DATE");
            param.add(leave.getReason()); type.add("STRING");
            if(leave.getTransactionId() == 0){
                param.add(leave.getTransactionId()); type.add("NUMBER_NULL");
            }else{
                param.add(leave.getTransactionId()); type.add("NUMBER");
            }
            param.add(leave.geteStatus()); type.add("STRING");
            param.add(leave.getId()); type.add("NUMBER");

            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().LEAVE_RESUMPTION_UPDATE,
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

    public int updateLeaveResumptionStatus(Leave.LeaveResumption leave){
        Connection connection = null;
        try{
            connection = this.getConnection(false);
            return updateLeaveResumptionStatus(leave, connection);
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
    }
    public int updateLeaveResumptionStatus(Leave.LeaveResumption leave, Connection connection){
        Vector param =new Vector();
        Vector type = new Vector();
        try{

            param.add(leave.geteStatus()); type.add("STRING");
            param.add(leave.getTransactionId()); type.add("NUMBER");

            //execute query
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().LEAVE__RESUMPTION_UPDATE_STATUS_BY_TRANSACTION,
                    param, type);

            return i;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }//:~

    public int deleteLeaveResumption(Leave.LeaveResumption leave){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(leave.getId()); type.add("NUMBER");

            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().LEAVE_RESUMPTION_DELETE,
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

    public int deleteLeaveResumption(List<Integer> types){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{

            connection = this.getConnection(false);
            this.startTransaction(connection);
            for(int leave : types){
                param.add(leave); type.add("NUMBER");
                int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().LEAVE_RESUMPTION_DELETE,
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
