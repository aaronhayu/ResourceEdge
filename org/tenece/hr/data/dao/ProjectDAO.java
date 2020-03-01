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
import org.tenece.web.data.Project;
import org.tenece.web.data.ProjectGroup;
import org.tenece.web.data.ProjectUtilization;

/**
 *
 * @author jeffry.amachree
 */
public class ProjectDAO extends BaseDAO{
    
    /**
     * Creates a new instance of DepartmentDAO
     */
    public ProjectDAO() {
    }
    
    /* ************* Project Group ********** */
    public List<ProjectGroup> getAllProjectGroups(){
        Connection connection = null;
        List<ProjectGroup> records = new ArrayList<ProjectGroup>();
        try{
            connection = this.getConnection(true);
            ResultSet rst = this.executeQuery(
                    DatabaseQueryLoader.getQueryAssignedConstant().PROJECT_GROUP_SELECT, connection);
            
            while(rst.next()){
                ProjectGroup prj = new ProjectGroup();
                prj.setId(rst.getLong("id"));
                prj.setCode(rst.getString("code"));
                prj.setDescription(rst.getString("description"));
                prj.setCompanyCode(rst.getString("company_code"));

                records.add(prj);
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

    public List<ProjectGroup> getAllProjectGroupsByCompany(String code){
        Connection connection = null;
        List<ProjectGroup> records = new ArrayList<ProjectGroup>();

        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(code); type.add("STRING");
            
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection,
                    DatabaseQueryLoader.getQueryAssignedConstant().PROJECT_GROUP_SELECT_BY_COMPANY, param, type);

            while(rst.next()){
                ProjectGroup prj = new ProjectGroup();
                prj.setId(rst.getLong("id"));
                prj.setCode(rst.getString("code"));
                prj.setDescription(rst.getString("description"));
                prj.setCompanyCode(rst.getString("company_code"));

                records.add(prj);
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

    public ProjectGroup getProjectGroupById(long id){
        Connection connection = null;
        ProjectGroup record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().PROJECT_GROUP_SELECT_BY_ID,
                    param, type);
            
            if(rst.next()){
                ProjectGroup prj = new ProjectGroup();
                prj.setId(rst.getLong("id"));
                prj.setCode(rst.getString("code"));
                prj.setDescription(rst.getString("description"));
                prj.setCompanyCode(rst.getString("company_code"));

                record = prj;
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
    
    public int createNewProjectGroup(ProjectGroup projectGroup){
        Connection connection = null;
        ProjectGroup record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(projectGroup.getCode()); type.add("STRING");
            param.add(projectGroup.getDescription()); type.add("STRING");
            param.add(projectGroup.getCompanyCode()); type.add("STRING");
            
            System.out.println("QUERY:" + DatabaseQueryLoader.getQueryAssignedConstant().PROJECT_GROUP_INSERT);
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().PROJECT_GROUP_INSERT,
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
    
    public int updateProjectGroup(ProjectGroup projectGroup){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(projectGroup.getCode()); type.add("STRING");
            param.add(projectGroup.getDescription()); type.add("STRING");
            param.add(projectGroup.getCompanyCode()); type.add("STRING");

            param.add(projectGroup.getId()); type.add("NUMBER");
            
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().PROJECT_GROUP_UPDATE,
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
    
    public int deleteProjectGroups(List<Long> ids){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            
            
            connection = this.getConnection(false);
            this.startTransaction(connection);
            for(long id : ids){
                param.add(id); type.add("NUMBER");
                int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().PROJECT_GROUP_DELETE,
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

    //********** Project ****************************
    public List<Project> getAllProject(){
        Connection connection = null;
        List<Project> records = new ArrayList<Project>();
        try{
            connection = this.getConnection(true);
            ResultSet rst = this.executeQuery(DatabaseQueryLoader.getQueryAssignedConstant().PROJECT_SELECT, connection);

            while(rst.next()){
                Project prj = new Project();
                prj.setId(rst.getLong("id"));
                prj.setProjectGroupId(rst.getLong("project_group_id"));
                prj.setBillable(rst.getInt("billable"));
                prj.setCode(rst.getString("code"));
                prj.setDescription(rst.getString("description"));
                prj.setProjectGroupDescription(rst.getString("project_group"));
                prj.setCompanyCode(rst.getString("company_code"));

                records.add(prj);
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

    public List<Project> getAllProjectByCompany(String code){
        Connection connection = null;
        List<Project> records = new ArrayList<Project>();

        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(code); type.add("STRING");
            
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection,
                    DatabaseQueryLoader.getQueryAssignedConstant().PROJECT_SELECT_BY_COMPANY, param, type);

            while(rst.next()){
                Project prj = new Project();
                prj.setId(rst.getLong("id"));
                prj.setProjectGroupId(rst.getLong("project_group_id"));
                prj.setBillable(rst.getInt("billable"));
                prj.setCode(rst.getString("code"));
                prj.setDescription(rst.getString("description"));
                prj.setProjectGroupDescription(rst.getString("project_group"));
                prj.setCompanyCode(rst.getString("company_code"));

                records.add(prj);
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

    public Project getProjectById(long id){
        Connection connection = null;
        Project record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().PROJECT_SELECT_BY_ID,
                    param, type);

            if(rst.next()){
                Project prj = new Project();
                prj.setId(rst.getLong("id"));
                prj.setProjectGroupId(rst.getLong("project_group_id"));
                prj.setBillable(rst.getInt("billable"));
                prj.setCode(rst.getString("code"));
                prj.setDescription(rst.getString("description"));
                prj.setProjectGroupDescription(rst.getString("project_group"));
                prj.setCompanyCode(rst.getString("company_code"));

                record = prj;
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

    public int createNewProject(Project project){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(project.getProjectGroupId()); type.add("NUMBER");
            param.add(project.getBillable()); type.add("NUMBER");
            param.add(project.getCode()); type.add("STRING");
            param.add(project.getDescription()); type.add("STRING");
            param.add(project.getCompanyCode()); type.add("STRING");

            System.out.println("QUERY:" + DatabaseQueryLoader.getQueryAssignedConstant().PROJECT_INSERT);
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().PROJECT_INSERT,
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

    public int updateProject(Project project){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(project.getProjectGroupId()); type.add("NUMBER");
            param.add(project.getBillable()); type.add("NUMBER");
            param.add(project.getCode()); type.add("STRING");
            param.add(project.getDescription()); type.add("STRING");
            param.add(project.getCompanyCode()); type.add("STRING");

            param.add(project.getId()); type.add("NUMBER");

            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().PROJECT_UPDATE,
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

    public int deleteProjects(List<Long> ids){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{

            connection = this.getConnection(false);
            this.startTransaction(connection);
            for(long id : ids){
                param.add(id); type.add("NUMBER");
                int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().PROJECT_DELETE,
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

    /* ********** PROJECT UTILIZATION ***********************/
    public List<ProjectUtilization> getAllProjectUtilization(long employeeId){
        Connection connection = null;
        List<ProjectUtilization> records = new ArrayList<ProjectUtilization>();
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(employeeId); type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().PROJECT_UTILIZATION_SELECT, param, type);

            while(rst.next()){
                ProjectUtilization prj = new ProjectUtilization();
                prj.setId(rst.getLong("id"));
                prj.setProjectId(rst.getLong("project_id"));
                prj.setActiveDate(new java.util.Date(rst.getDate("active_date").getTime()));
                prj.setHours(rst.getInt("utilized_hour"));
                prj.setProjectDescription(rst.getString("project_description"));
                prj.setEmployeeId(rst.getLong("emp_number"));
                prj.seteStatus(rst.getString("estatus"));
                prj.setEmployeeName(rst.getString("firstname") + " " + rst.getString("lastname"));

                records.add(prj);
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

    public ProjectUtilization getProjectUtilizationById(long id, long employeeId){
        Connection connection = null;
        ProjectUtilization record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id); type.add("NUMBER");
            param.add(employeeId); type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().PROJECT_UTILIZATION_SELECT_BY_ID,
                    param, type);

            if(rst.next()){
                ProjectUtilization prj = new ProjectUtilization();
                prj.setId(rst.getLong("id"));
                prj.setProjectId(rst.getLong("project_id"));
                prj.setActiveDate(new java.util.Date(rst.getDate("active_date").getTime()));
                prj.setHours(rst.getInt("utilized_hour"));
                prj.setProjectDescription(rst.getString("project_description"));
                prj.setEmployeeId(rst.getLong("emp_number"));
                prj.seteStatus(rst.getString("estatus"));
                prj.setEmployeeName(rst.getString("firstname") + " " + rst.getString("lastname"));
                record = prj;
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

    public int createNewProjectUtilization(ProjectUtilization project){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(project.getProjectId()); type.add("NUMBER");
            param.add(project.getActiveDate()); type.add("DATE");
            param.add(project.getHours()); type.add("NUMBER");
            param.add(project.getEmployeeId()); type.add("NUMBER");
            param.add(project.geteStatus()); type.add("STRING");

            System.out.println("QUERY:" + DatabaseQueryLoader.getQueryAssignedConstant().PROJECT_UTILIZATION_INSERT);
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().PROJECT_UTILIZATION_INSERT,
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

    public int updateProjectUtilization(ProjectUtilization project){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(project.getProjectId()); type.add("NUMBER");
            param.add(project.getActiveDate()); type.add("DATE");
            param.add(project.getHours()); type.add("NUMBER");
            param.add(project.getEmployeeId()); type.add("NUMBER");
            param.add(project.geteStatus()); type.add("STRING");

            param.add(project.getId()); type.add("NUMBER");

            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().PROJECT_UTILIZATION_UPDATE,
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

    public int deleteProjectUtilizations(List<Long> ids){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{

            connection = this.getConnection(false);
            this.startTransaction(connection);
            for(long id : ids){
                param.add(id); type.add("NUMBER");
                int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().PROJECT_UTILIZATION_DELETE,
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

    public Integer checkProjectByDate(Date activeDate, long projectId, long employeeId){
        Connection connection = null;
        int rowsFound = 0;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(activeDate); type.add("DATE");
            param.add(projectId); type.add("NUMBER");
            param.add(employeeId); type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().PROJECT_UTILIZATION_CHECKED_DATE_SELECT,
                    param, type);

            if(rst.next()){
                rowsFound = rst.getInt(1);
            }
        }catch(Exception e){
            rowsFound = 0;
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return rowsFound;
    }

    public Integer checkDailyHours(Date activeDate, long employeeId){
        Connection connection = null;
        int rowsFound = 0;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(activeDate); type.add("DATE");
            param.add(employeeId); type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().PROJECT_UTILIZATION_CHECKED_HOURS_SELECT,
                    param, type);

            if(rst.next()){
                rowsFound = rst.getInt(1);
            }
        }catch(Exception e){
            rowsFound = 0;
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return rowsFound;
    }

    public List<ProjectUtilization> getAllPendingProjectUtilizationSummary(String status){
        Connection connection = null;
        List<ProjectUtilization> records = new ArrayList<ProjectUtilization>();
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(status); type.add("STRING");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().PROJECT_UTILIZATION_PENDING_SELECT, param, type);

            while(rst.next()){
                ProjectUtilization prj = new ProjectUtilization();
                
                prj.setProjectId(rst.getLong("project_id"));
                prj.setProjectDescription(rst.getString("description"));
                prj.setHours(rst.getInt("total_hours"));
                prj.setStartDate(new java.util.Date(rst.getDate("startDate").getTime()));
                prj.setEndDate(new java.util.Date(rst.getDate("endDate").getTime()));

                records.add(prj);
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

    public List<ProjectUtilization> getAllPendingProjectUtilizationSummaryByCompany(String status, String companyCode){
        Connection connection = null;
        List<ProjectUtilization> records = new ArrayList<ProjectUtilization>();
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(status); type.add("STRING");
            param.add(companyCode); type.add("STRING");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection,
                    DatabaseQueryLoader.getQueryAssignedConstant().PROJECT_UTILIZATION_PENDING_SELECT_BY_COMPANY, param, type);

            while(rst.next()){
                ProjectUtilization prj = new ProjectUtilization();

                prj.setProjectId(rst.getLong("project_id"));
                prj.setProjectDescription(rst.getString("description"));
                prj.setHours(rst.getInt("total_hours"));
                prj.setStartDate(new java.util.Date(rst.getDate("startDate").getTime()));
                prj.setEndDate(new java.util.Date(rst.getDate("endDate").getTime()));

                records.add(prj);
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


    public int approveProjectUtilizations(List<Long> ids){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{

            connection = this.getConnection(false);
            this.startTransaction(connection);

            for(long id : ids){
                //re-initialize
                param =new Vector();
                type = new Vector();

                param.add(id); type.add("NUMBER");
                int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().PROJECT_UTILIZATION_CLOSED_UPDATE,
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
