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
import org.tenece.web.data.EmployeeWorkExperience;

/**
 *
 * @author jeffry.amachree
 */
public class EmployeeWorkExperienceDAO extends BaseDAO{
    
    /**
     * Creates a new instance of DepartmentDAO
     */
    public EmployeeWorkExperienceDAO() {
    }
    
    public EmployeeWorkExperience getWorkExperienceById(int id){
        Connection connection = null;
        EmployeeWorkExperience record = new EmployeeWorkExperience();
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_WORK_EXPERIENCE_SELECT_BY_ID,
                    param, type);
            
            while(rst.next()){
                EmployeeWorkExperience item = new EmployeeWorkExperience();
                item.setId(rst.getInt("experienceid"));
                item.setEmployeeId(rst.getInt("emp_number"));
                item.setPreviousEmployer(rst.getString("previous_employer"));
                item.setPreviousJobTitle(rst.getString("previous_jobtitle"));
                item.setStartDate(rst.getDate("previous_start_date"));
                item.setEndDate(rst.getDate("previous_end_date"));
                item.setComment(rst.getString("comments"));
                item.setInternalMovement(rst.getInt("internal_movement"));
                
                record = item;
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
    
    public List<EmployeeWorkExperience> getEmployeeWorkExperienceById(int id){
        Connection connection = null;
        List<EmployeeWorkExperience> record = new ArrayList<EmployeeWorkExperience>();
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_WORK_EXPERIENCE_SELECT,
                    param, type);
            
            while(rst.next()){
                EmployeeWorkExperience item = new EmployeeWorkExperience();
                item.setId(rst.getInt("experienceid"));
                item.setEmployeeId(rst.getInt("emp_number"));
                item.setPreviousEmployer(rst.getString("previous_employer"));
                item.setPreviousJobTitle(rst.getString("previous_jobtitle"));
                item.setStartDate(rst.getDate("previous_start_date"));
                item.setEndDate(rst.getDate("previous_end_date"));
                item.setComment(rst.getString("comments"));
                item.setInternalMovement(rst.getInt("internal_movement"));
                
                record.add(item);
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
    
    public int createNewEmployeeWorkExperience(EmployeeWorkExperience EmployeeWorkExperience){
        Connection connection = null;
        EmployeeWorkExperience record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(EmployeeWorkExperience.getEmployeeId()); type.add("NUMBER");
            param.add(EmployeeWorkExperience.getPreviousEmployer()); type.add("STRING");
            param.add(EmployeeWorkExperience.getPreviousJobTitle()); type.add("STRING");
            param.add(EmployeeWorkExperience.getStartDate()); type.add("DATE");
            param.add(EmployeeWorkExperience.getEndDate()); type.add("DATE");
            param.add(EmployeeWorkExperience.getComment()); type.add("STRING");
            param.add(EmployeeWorkExperience.getInternalMovement()); type.add("NUMBER");
            
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_WORK_EXPERIENCE_INSERT,
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
    
    public int updateEmployeeWorkExperience(EmployeeWorkExperience EmployeeWorkExperience){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(EmployeeWorkExperience.getEmployeeId()); type.add("NUMBER");
            param.add(EmployeeWorkExperience.getPreviousEmployer()); type.add("STRING");
            param.add(EmployeeWorkExperience.getPreviousJobTitle()); type.add("STRING");
            param.add(EmployeeWorkExperience.getStartDate()); type.add("DATE");
            param.add(EmployeeWorkExperience.getEndDate()); type.add("DATE");
            param.add(EmployeeWorkExperience.getComment()); type.add("STRING");
            param.add(EmployeeWorkExperience.getInternalMovement()); type.add("NUMBER");
            param.add(EmployeeWorkExperience.getId()); type.add("NUMBER");
            
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_WORK_EXPERIENCE_UPDATE,
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
    
    public int deleteEmployeeWorkExperience(List<Long> ids){

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
                i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_WORK_EXPERIENCE_DELETE_BY_ID,
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
}
