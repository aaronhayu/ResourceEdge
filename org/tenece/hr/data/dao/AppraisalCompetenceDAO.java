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
import org.tenece.web.data.AppraisalCompetence;
import org.tenece.web.data.EmployeeAppraisalCompetence;

/**
 *
 * @author jeffry.amachree
 */
public class AppraisalCompetenceDAO extends BaseDAO{
    
    /**
     * Creates a new instance of DepartmentDAO
     */
    public AppraisalCompetenceDAO() {
    }
    
    /* ************* AppraisalCompetence ********** */
    public List<AppraisalCompetence> getAllAppraisalCompetences(){
        Connection connection = null;
        List<AppraisalCompetence> records = new ArrayList<AppraisalCompetence>();
        try{
            connection = this.getConnection(true);
            ResultSet rst = this.executeQuery(DatabaseQueryLoader.getQueryAssignedConstant().APPRAISAL_COMPETENCE_SELECT, connection);
            
            while(rst.next()){
                AppraisalCompetence item = new AppraisalCompetence();
                item.setId(rst.getInt("id"));
                item.setDescription(rst.getString("description"));
                item.setStatement(rst.getString("statement"));
                item.setGroupId(rst.getInt("group_id"));
                
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
    
    public AppraisalCompetence getAppraisalCompetenceById(int id){
        Connection connection = null;
        AppraisalCompetence record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().APPRAISAL_COMPETENCE_SELECT_BY_ID,
                    param, type);
            
            while(rst.next()){
                AppraisalCompetence item = new AppraisalCompetence();
                item.setId(rst.getInt("id"));
                item.setDescription(rst.getString("description"));
                item.setStatement(rst.getString("statement"));
                item.setGroupId(rst.getInt("group_id"));
                
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
    
             public EmployeeAppraisalCompetence getEmployeeAppraisalCompetenceByID(int id){
        Connection connection = null;
        EmployeeAppraisalCompetence record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_APPRAISAL_COMPETENCE_SELECT_BY_ID,
                    param, type);
            
            while(rst.next()){
                EmployeeAppraisalCompetence item = new EmployeeAppraisalCompetence();
                item.setId(rst.getInt("id"));
                item.setDescription(rst.getString("description"));
                item.setStatement(rst.getString("statement"));
                item.setEmployeeId(rst.getInt("emp_number"));
                item.setEmployeeName(rst.getString("firstname") + " "+ rst.getString("lastname"));
                item.setGroupId(rst.getInt("group_id"));
                
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
public List<AppraisalCompetence> getAppraisalCompetenceByGroup(int id){
        Connection connection = null;
        List<AppraisalCompetence> records = new ArrayList<AppraisalCompetence>();
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);

            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().APPRAISAL_COMPETENCE_SELECT_BY_GROUP,
                    param, type);
            
            while(rst.next()){
                AppraisalCompetence item = new AppraisalCompetence();
                item.setId(rst.getInt("id"));
                item.setDescription(rst.getString("description"));
                item.setStatement(rst.getString("statement"));
                item.setGroupId(rst.getInt("group_id"));
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
    
    public List<EmployeeAppraisalCompetence> getEmployeeAppraisalCompetenceByGroup(int id){
        Connection connection = null;
        List<EmployeeAppraisalCompetence> records = new ArrayList<EmployeeAppraisalCompetence>();
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);

            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_APPRAISAL_COMPETENCE_SELECT_BY_GROUP,
                    param, type);
            
            while(rst.next()){
                EmployeeAppraisalCompetence item = new EmployeeAppraisalCompetence();
                item.setId(rst.getInt("id"));
                item.setDescription(rst.getString("description"));
                item.setStatement(rst.getString("statement"));
                item.setEmployeeId(rst.getInt("emp_number"));
                item.setEmployeeName(rst.getString("firstname") + " "+ rst.getString("lastname"));
                item.setGroupId(rst.getInt("group_id"));

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
     public List<AppraisalCompetence> getEmployeeAppraisalCompetenceByGroupAndEmployID(int id, int employID){
        Connection connection = null;
        List<AppraisalCompetence> records = new ArrayList<AppraisalCompetence>();
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            param.add(employID);
            type.add("NUMBER");
            connection = this.getConnection(true);

            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_APPRAISAL_COMPETENCE_SELECT_BY_GROUP_AND_BY_EMP_ID,
                    param, type);
            
            while(rst.next()){
                EmployeeAppraisalCompetence item = new EmployeeAppraisalCompetence();
                item.setId(rst.getInt("id"));
                item.setDescription(rst.getString("description"));
                item.setStatement(rst.getString("statement"));
                item.setEmployeeId(rst.getInt("emp_number"));
                item.setGroupId(rst.getInt("group_id"));

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
    public int createNewAppraisalCompetence(AppraisalCompetence appraisalCompetence){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(appraisalCompetence.getDescription()); type.add("STRING");
            param.add(appraisalCompetence.getStatement()); type.add("STRING");
            param.add(appraisalCompetence.getGroupId()); type.add("NUMBER");
            
            System.out.println("QUERY:" + DatabaseQueryLoader.getQueryAssignedConstant().APPRAISAL_COMPETENCE_INSERT);
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().APPRAISAL_COMPETENCE_INSERT,
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
    public int createEmployeeNewAppraisalCompetence(EmployeeAppraisalCompetence appraisalCompetence){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(appraisalCompetence.getDescription()); type.add("STRING");
            param.add(appraisalCompetence.getStatement()); type.add("STRING");
            param.add(appraisalCompetence.getGroupId()); type.add("NUMBER");
            param.add(appraisalCompetence.getEmployeeId()); type.add("NUMBER");
            //System.out.println("QUERY:" + DatabaseQueryLoader.getQueryAssignedConstant().APPRAISAL_COMPETENCE_INSERT);
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_APPRAISAL_COMPETENCE_INSERT,
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
    public int updateAppraisalCompetence(AppraisalCompetence appraisalCompetence){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(appraisalCompetence.getDescription()); type.add("STRING");
            param.add(appraisalCompetence.getStatement()); type.add("STRING");
            param.add(appraisalCompetence.getGroupId()); type.add("NUMBER");
            param.add(appraisalCompetence.getId()); type.add("NUMBER");
            
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().APPRAISAL_COMPETENCE_UPDATE,
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
    public int updateEmployeeAppraisalCompetence(EmployeeAppraisalCompetence appraisalCompetence){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(appraisalCompetence.getDescription()); type.add("STRING");
            param.add(appraisalCompetence.getStatement()); type.add("STRING");
            param.add(appraisalCompetence.getGroupId()); type.add("NUMBER");
            param.add(appraisalCompetence.getEmployeeId()); type.add("NUMBER");
            param.add(appraisalCompetence.getId()); type.add("NUMBER");
            
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_APPRAISAL_COMPETENCE_UPDATE,
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
    public int deleteAppraisalCompetence(List<Integer> ids){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{

            for(int id : ids){
                param.add(id); type.add("NUMBER");

                connection = this.getConnection(false);
                int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().APPRAISAL_COMPETENCE_DELETE,
                        param, type);
            }
            return ids.size();
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
