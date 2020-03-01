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
import org.tenece.web.data.EmployeeSkill;
import org.tenece.web.data.EmployeeSkill;

/**
 *
 * @author jeffry.amachree
 */
public class EmployeeSkillDAO extends BaseDAO{
    
    /**
     * Creates a new instance of DepartmentDAO
     */
    public EmployeeSkillDAO() {
    }
    
    public EmployeeSkill getSkillById(int id){
        Connection connection = null;
        EmployeeSkill record = new EmployeeSkill();
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_SKILLS_SELECT_BY_ID,
                    param, type);
            
            while(rst.next()){
                EmployeeSkill item = new EmployeeSkill();
                item.setId(rst.getInt("id"));
                item.setEmployeeId(rst.getInt("emp_number"));
                item.setSkillId(rst.getInt("skill_id"));
                item.setYearsOfExperience(rst.getInt("years_of_exp"));
                item.setComment(rst.getString("comments"));
                
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
    
    public List<EmployeeSkill> getEmployeeSkillById(int id){
        Connection connection = null;
        List<EmployeeSkill> record = new ArrayList<EmployeeSkill>();
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_SKILLS_SELECT_BY_EMPLOYEE,
                    param, type);
            
            while(rst.next()){
                EmployeeSkill item = new EmployeeSkill();
                item.setId(rst.getInt("id"));
                item.setEmployeeId(rst.getInt("emp_number"));
                item.setSkillId(rst.getInt("skill_id"));
                item.setYearsOfExperience(rst.getInt("years_of_exp"));
                item.setComment(rst.getString("comments"));
                item.setSkillName(rst.getString("skillname"));
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
    
    public int createNewEmployeeSkill(EmployeeSkill EmployeeSkill){
        Connection connection = null;
        EmployeeSkill record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(EmployeeSkill.getEmployeeId()); type.add("NUMBER");
            param.add(EmployeeSkill.getSkillId()); type.add("NUMBER");
            param.add(EmployeeSkill.getYearsOfExperience()); type.add("NUMBER");
            param.add(EmployeeSkill.getComment()); type.add("STRING");
            
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_SKILLS_INSERT,
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
    
    public int updateEmployeeSkill(EmployeeSkill EmployeeSkill){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(EmployeeSkill.getEmployeeId()); type.add("NUMBER");
            param.add(EmployeeSkill.getSkillId()); type.add("NUMBER");
            param.add(EmployeeSkill.getYearsOfExperience()); type.add("NUMBER");
            param.add(EmployeeSkill.getComment()); type.add("STRING");
            param.add(EmployeeSkill.getId()); type.add("NUMBER");
            
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_SKILLS_UPDATE,
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
    
    public int deleteEmployeeSkill(List<Long> ids){

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
                i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_SKILLS_DELETE_BY_ID,
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
