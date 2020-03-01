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
import org.tenece.web.data.AnnualCalendar;
import org.tenece.web.data.Department;

/**
 *
 * @author jeffry.amachree
 */
public class AnnualCalendarDAO extends BaseDAO{
    
    /**
     * Creates a new instance of DepartmentDAO
     */
    public AnnualCalendarDAO() {
    }
    
    /* ************* Annual calendar ********** */
    
    public List<AnnualCalendar> getAllAnnualcalendar(){
        Connection connection = null;
        List<AnnualCalendar> records = new ArrayList<AnnualCalendar>();
        try{
            connection = this.getConnection(true);
            ResultSet rst = null;
            rst = this.executeQuery(DatabaseQueryLoader.getQueryAssignedConstant().ANNUAL_CALENDAR_SELECT, connection);
            
            while(rst.next()){
                AnnualCalendar calendar = new AnnualCalendar();
                calendar.setId(rst.getInt("annual_id"));
                calendar.setCode(rst.getString("annual_code"));
                calendar.setDescription(rst.getString("annual_description"));
                calendar.setStartDate(new Date(rst.getDate("start_date").getTime()));
                try{
                    calendar.setEndDate(new Date(rst.getDate("end_date").getTime()));
                }catch(Exception e){
                    calendar.setEndDate(null);
                }
                calendar.setStatus(rst.getString("status"));
                calendar.setCompanyCode(rst.getString("company_code"));
                
                records.add(calendar);
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

    public List<AnnualCalendar> getAllAnnualcalendarByCompany(String companyCode){
        Connection connection = null;
        List<AnnualCalendar> records = new ArrayList<AnnualCalendar>();
        Vector param =new Vector();
        Vector type = new Vector();

        try{
            param.add(companyCode); type.add("STRING");
            
            connection = this.getConnection(true);
            ResultSet rst = null;
            rst = this.executeParameterizedQuery(connection,
                    DatabaseQueryLoader.getQueryAssignedConstant().ANNUAL_CALENDAR_SELECT_BY_COMPANY, param, type);

            while(rst.next()){
                AnnualCalendar calendar = new AnnualCalendar();
                calendar.setId(rst.getInt("annual_id"));
                calendar.setCode(rst.getString("annual_code"));
                calendar.setDescription(rst.getString("annual_description"));
                calendar.setStartDate(new Date(rst.getDate("start_date").getTime()));
                try{
                    calendar.setEndDate(new Date(rst.getDate("end_date").getTime()));
                }catch(Exception e){
                    calendar.setEndDate(null);
                }
                calendar.setStatus(rst.getString("status"));
                calendar.setCompanyCode(rst.getString("company_code"));

                records.add(calendar);
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

    public AnnualCalendar getAnnualCalendarById(int id){
        Connection connection = null;
        AnnualCalendar record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection,
                    DatabaseQueryLoader.getQueryAssignedConstant().ANNUAL_CALENDAR_SELECT_BY_ID,
                    param, type);
            
            while(rst.next()){
                AnnualCalendar calendar = new AnnualCalendar();
                calendar.setId(rst.getInt("annual_id"));
                calendar.setCode(rst.getString("annual_code"));
                calendar.setDescription(rst.getString("annual_description"));
                calendar.setStartDate(new Date(rst.getDate("start_date").getTime()));
                try{
                    calendar.setEndDate(new Date(rst.getDate("end_date").getTime()));
                }catch(Exception e){
                    calendar.setEndDate(null);
                }
                calendar.setStatus(rst.getString("status"));
                calendar.setCompanyCode(rst.getString("company_code"));

                record = calendar;
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
        return record;
    }
    
    public int createNewAnnualCalendar(AnnualCalendar calendar){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            //get connection
            connection = this.getConnection(false);

            //start transaction
            startTransaction(connection);
            //update other calendar setting existing
            int delRows = this.executeUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().ANNUAL_CALENDAR_UPDATE_ALL_OUTSTANDING);

            param.add(calendar.getCode()); type.add("STRING");
            param.add(calendar.getDescription()); type.add("STRING");
            param.add(calendar.getStartDate()); type.add("DATE");
            param.add(calendar.getEndDate()); type.add("VARCHAR_NULL");
            param.add(calendar.getStatus()); type.add("STRING");
            param.add(calendar.getCompanyCode()); type.add("STRING");
            
            System.out.println("QUERY:" + DatabaseQueryLoader.getQueryAssignedConstant().ANNUAL_CALENDAR_INSERT);
            
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().ANNUAL_CALENDAR_INSERT,
                    param, type);
            //commit transsction
            commitTransaction(connection);
            return i;
        }catch(Exception e){
            e.printStackTrace();
            //rollback transaction
            try{
                rollbackTransaction(connection);
            }catch(Exception er){}
            //return zero- no row updated
            return 0;
            
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }//:~
    
    public int updateAnnualCalendar(AnnualCalendar calendar){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(calendar.getCode()); type.add("STRING");
            param.add(calendar.getDescription()); type.add("STRING");
            param.add(calendar.getStartDate()); type.add("DATE");
            param.add(calendar.getEndDate()); type.add("VARCHAR_NULL");
            param.add(calendar.getStatus()); type.add("STRING");
            param.add(calendar.getCompanyCode()); type.add("STRING");
            
            param.add(calendar.getId()); type.add("NUMBER");
            
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().ANNUAL_CALENDAR_UPDATE,
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
    
    public int updatePendingAnnualCalendars(){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().ANNUAL_CALENDAR_UPDATE_ALL_OUTSTANDING,
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
