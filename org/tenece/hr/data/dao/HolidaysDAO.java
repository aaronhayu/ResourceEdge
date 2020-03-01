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
import org.tenece.web.data.Department;
import org.tenece.web.data.Holiday;

/**
 *
 * @author jeffry.amachree
 */
public class HolidaysDAO extends BaseDAO{
    
    /**
     * Creates a new instance of DepartmentDAO
     */
    public HolidaysDAO() {
    }
    
    /* ************* Holidays ********** */
    public List<Holiday> getAllHolidays(){ 
        return getAllHolidays(null, null);
    }

    public List<Holiday> getAllHolidays(String criteria, String searchText){
        Connection connection = null;
        List<Holiday> records = new ArrayList<Holiday>();
        try{
            connection = this.getConnection(true);
            ResultSet rst = null;
            if(criteria == null || searchText == null){
                rst = this.executeQuery(DatabaseQueryLoader.getQueryAssignedConstant().HOLIDAY_SELECT, connection);
            }else{
                String query = "";

                if(criteria.equalsIgnoreCase("DESC")){
                    query = DatabaseQueryLoader.getQueryAssignedConstant().HOLIDAY_SELECT_SEARCH_BY_DESC;
                }else if(criteria.equalsIgnoreCase("LENGTH")){ 
                    query = DatabaseQueryLoader.getQueryAssignedConstant().HOLIDAY_SELECT_SEARCH_BY_LENGTH;
                }
                
                query = query.replaceAll("_SEARCH_", searchText);

                rst = this.executeQuery(query, connection);
            }
            
            while(rst.next()){
                Holiday item = new Holiday();
                item.setId(rst.getInt("holiday_id"));
                item.setDescription(rst.getString("description"));
                item.setEffectiveDate(new java.util.Date(rst.getDate("effective_date").getTime()));
                item.setRecurring(rst.getInt("recurring"));
                item.setHoliday_length(rst.getInt("holiday_length"));
                item.setCompanyCode(rst.getString("company_code"));
                
                records.add(item);
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

    public List<Holiday> getAllHolidaysByCompany(String code){
        return getAllHolidaysByCompany(code, null, null);
    }

    public List<Holiday> getAllHolidaysByCompany(String code, String criteria, String searchText){
        Connection connection = null;
        List<Holiday> records = new ArrayList<Holiday>();

        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(code); type.add("STRING");

            connection = this.getConnection(true);
            ResultSet rst = null;
            if(criteria == null || searchText == null){
                rst = this.executeParameterizedQuery(connection,
                        DatabaseQueryLoader.getQueryAssignedConstant().HOLIDAY_SELECT_BY_COMPANY, param, type);
            }else{
                String query = "";

                if(criteria.equalsIgnoreCase("DESC")){
                    query = DatabaseQueryLoader.getQueryAssignedConstant().HOLIDAY_SELECT_BY_COMPANY_SEARCH_BY_DESC;
                }else if(criteria.equalsIgnoreCase("LENGTH")){
                    query = DatabaseQueryLoader.getQueryAssignedConstant().HOLIDAY_SELECT_BY_COMPANY_SEARCH_BY_LENGTH;
                }

                query = query.replaceAll("_SEARCH_", searchText);

                rst = this.executeParameterizedQuery(connection, query, param, type);
            }

            while(rst.next()){
                Holiday item = new Holiday();
                item.setId(rst.getInt("holiday_id"));
                item.setDescription(rst.getString("description"));
                item.setEffectiveDate(new java.util.Date(rst.getDate("effective_date").getTime()));
                item.setRecurring(rst.getInt("recurring"));
                item.setHoliday_length(rst.getInt("holiday_length"));
                item.setCompanyCode(rst.getString("company_code"));

                records.add(item);
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

    public Holiday getHolidayById(int id){
        Connection connection = null;
        Holiday record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().HOLIDAY_SELECT_BY_ID,
                    param, type);
            
            while(rst.next()){
                Holiday item = new Holiday();
                item.setId(rst.getInt("holiday_id"));
                item.setDescription(rst.getString("description"));
                item.setEffectiveDate(new java.util.Date(rst.getDate("effective_date").getTime()));
                item.setRecurring(rst.getInt("recurring"));
                item.setHoliday_length(rst.getInt("holiday_length"));
                item.setCompanyCode(rst.getString("company_code"));
                
                record = item;
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
    
    public int createNewHoliday(Holiday holiday){
        Connection connection = null;
        Holiday record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(holiday.getDescription()); type.add("STRING");
            param.add(holiday.getEffectiveDate()); type.add("DATE");
            param.add(holiday.getRecurring()); type.add("NUMBER");
            param.add(holiday.getHoliday_length()); type.add("NUMBER");
            param.add(holiday.getCompanyCode()); type.add("STRING");
            
            System.out.println("QUERY:" + DatabaseQueryLoader.getQueryAssignedConstant().HOLIDAY_INSERT);
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().HOLIDAY_INSERT,
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
    
    public int updateHoliday(Holiday holiday){
        Connection connection = null;
        Department record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(holiday.getDescription()); type.add("STRING");
            param.add(holiday.getEffectiveDate()); type.add("DATE");
            param.add(holiday.getRecurring()); type.add("NUMBER");
            param.add(holiday.getHoliday_length()); type.add("NUMBER");
            param.add(holiday.getCompanyCode()); type.add("STRING");
            
            param.add(holiday.getId()); type.add("NUMBER");
            
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().HOLIDAY_UPDATE,
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
    
    public int deleteHoliday(Holiday holiday){
        Connection connection = null;
        Holiday record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            
            param.add(holiday.getId()); type.add("NUMBER");
            
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().HOLIDAY_DELETE,
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
    
    public int deleteHoliday(List<Integer> ids){
        Connection connection = null;
        Holiday record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            
            for(int id : ids){
                connection = this.getConnection(false);
                
                this.startTransaction(connection);
                param =new Vector();
                type = new Vector();
                param.add(id); type.add("NUMBER");

                int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().HOLIDAY_DELETE,
                        param, type);
                
                this.commitTransaction(connection);
            }
            return ids.size();
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
