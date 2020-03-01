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
import org.tenece.web.data.Course;
import org.tenece.web.data.CourseApplication;
import org.tenece.web.data.CourseFeedback;

/**
 *
 * @author jeffry.amachree
 */
public class CourseDAO extends BaseDAO{
    
    /**
     * Creates a new instance of CourseDAO
     */
    public CourseDAO() {
    }
    
    /* ************* Course ********** */
    public List<Course> getAllCourses(){
        Connection connection = null;
        List<Course> records = new ArrayList<Course>();
        try{
            connection = this.getConnection(true);
            ResultSet rst = this.executeQuery(DatabaseQueryLoader.getQueryAssignedConstant().COURSE_SELECT, connection);
            
            while(rst.next()){
                Course course = new Course();
                course.setId(rst.getInt("id"));
                course.setCourse(rst.getString("course"));
                course.setOrganizer(rst.getString("organizer"));
                course.setCourseDetail(rst.getString("course_detail"));
                course.setStartDate(new Date(rst.getDate("start_date").getTime()));
                course.setEndDate(new Date(rst.getDate("end_date").getTime()));
                course.setNoOfDays(rst.getInt("no_of_days"));
                course.setStartTime(rst.getString("start_time"));
                course.setTravelExpenses(rst.getFloat("travel_expenses"));
                course.setAllowance(rst.getFloat("allowance"));
                course.setMisc_Cost(rst.getFloat("allowance"));
                course.setCourseVenue(rst.getString("course_venue"));
                course.setCourseFee(rst.getFloat("course_fee"));
                course.setCompanyCode(rst.getString("company_code"));
                
                records.add(course);
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

    public List<Course> getAllCoursesByCompany(String code){
        Connection connection = null;
        List<Course> records = new ArrayList<Course>();

        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(code); type.add("STRING");

            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection,
                    DatabaseQueryLoader.getQueryAssignedConstant().COURSE_SELECT_BY_COMPANY, param, type);

            while(rst.next()){
                Course course = new Course();
                course.setId(rst.getInt("id"));
                course.setCourse(rst.getString("course"));
                course.setOrganizer(rst.getString("organizer"));
                course.setCourseDetail(rst.getString("course_detail"));
                course.setStartDate(new Date(rst.getDate("start_date").getTime()));
                course.setEndDate(new Date(rst.getDate("end_date").getTime()));
                course.setNoOfDays(rst.getInt("no_of_days"));
                course.setStartTime(rst.getString("start_time"));
                course.setTravelExpenses(rst.getFloat("travel_expenses"));
                course.setAllowance(rst.getFloat("allowance"));
                course.setMisc_Cost(rst.getFloat("allowance"));
                course.setCourseVenue(rst.getString("course_venue"));
                course.setCourseFee(rst.getFloat("course_fee"));
                course.setCompanyCode(rst.getString("company_code"));

                records.add(course);
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

    public Course getCourseById(long id){
        Connection connection = null;
        try{
            connection = this.getConnection(true);
            return getCourseById(id, connection);
        }catch(Exception e){
            return null;
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    public Course getCourseById(long id, Connection connection){
        
        Course record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().COURSE_SELECT_BY_ID,
                    param, type);
            
            while(rst.next()){
                Course course = new Course();
                course.setId(rst.getInt("id"));
                course.setCourse(rst.getString("course"));
                course.setOrganizer(rst.getString("organizer"));
                course.setCourseDetail(rst.getString("course_detail"));
                course.setStartDate(new Date(rst.getDate("start_date").getTime()));
                course.setEndDate(new Date(rst.getDate("end_date").getTime()));
                course.setNoOfDays(rst.getInt("no_of_days"));
                course.setStartTime(rst.getString("start_time"));
                course.setTravelExpenses(rst.getFloat("travel_expenses"));
                course.setAllowance(rst.getFloat("allowance"));
                course.setMisc_Cost(rst.getFloat("allowance"));
                course.setCourseVenue(rst.getString("course_venue"));
                course.setCourseFee(rst.getFloat("course_fee"));
                course.setCompanyCode(rst.getString("company_code"));
                
                record = course;
            }
        }catch(Exception e){
            return null;
        }
        return record;
    }
    
    public int createNewCourse(Course course){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(course.getCourse()); type.add("STRING");
            param.add(course.getOrganizer()); type.add("STRING");
            param.add(course.getCourseDetail()); type.add("STRING");
            param.add(course.getStartDate()); type.add("DATE");
            param.add(course.getEndDate()); type.add("DATE");

            param.add(course.getNoOfDays()); type.add("NUMBER");
            param.add(course.getStartTime()); type.add("STRING");
            param.add(course.getTravelExpenses()); type.add("AMOUNT");
            param.add(course.getAllowance()); type.add("AMOUNT");
            param.add(course.getMisc_Cost()); type.add("AMOUNT");
            param.add(course.getCourseVenue()); type.add("STRING");
            param.add(course.getCourseFee()); type.add("AMOUNT");
            param.add(course.getCompanyCode()); type.add("STRING");
            
            System.out.println("QUERY:" + DatabaseQueryLoader.getQueryAssignedConstant().COURSE_INSERT);
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().COURSE_INSERT,
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
    
    public int updateCourse(Course course){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(course.getCourse()); type.add("STRING");
            param.add(course.getOrganizer()); type.add("STRING");
            param.add(course.getCourseDetail()); type.add("STRING");
            param.add(course.getStartDate()); type.add("DATE");
            param.add(course.getEndDate()); type.add("DATE");

            param.add(course.getNoOfDays()); type.add("NUMBER");
            param.add(course.getStartTime()); type.add("STRING");
            param.add(course.getTravelExpenses()); type.add("AMOUNT");
            param.add(course.getAllowance()); type.add("AMOUNT");
            param.add(course.getMisc_Cost()); type.add("AMOUNT");
            param.add(course.getCourseVenue()); type.add("STRING");
            param.add(course.getCourseFee()); type.add("AMOUNT");
            param.add(course.getCompanyCode()); type.add("STRING");

            param.add(course.getId()); type.add("NUMBER");
            
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().COURSE_UPDATE,
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
    
    public int deleteCourse(Course course){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(course.getId()); type.add("NUMBER");
            
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().COURSE_DELETE,
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
    
    public int deleteCourses(List<Integer> courses){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            
            
            connection = this.getConnection(false);
            this.startTransaction(connection);
            for(int course : courses){
                param.add(course); type.add("NUMBER");
                int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().COURSE_DELETE,
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

    /* ************* Course Application ********** */
    public List<CourseApplication> getAllCourseApplication(){
        return getAllCourseApplication(null, null);
    }
    public List<CourseApplication> getAllCourseApplication(String criteria, String searchText){
        Connection connection = null;
        List<CourseApplication> records = new ArrayList<CourseApplication>();
        try{
            connection = this.getConnection(true);
            ResultSet rst = null;
            if(criteria == null || searchText == null){
                rst = this.executeQuery(DatabaseQueryLoader.getQueryAssignedConstant().COURSE_APPLICATION_SELECT, connection);
            }else{
                String query = "";
                //param.add(searchText); type.add("STRING");

                if(criteria.equalsIgnoreCase("NAME")){
                    query = DatabaseQueryLoader.getQueryAssignedConstant().COURSE_APPLICATION_SELECT_SEARCH_BY_APPLICANT;
                }else if(criteria.equalsIgnoreCase("COURSE")){
                    query = DatabaseQueryLoader.getQueryAssignedConstant().COURSE_APPLICATION_SELECT_SEARCH_BY_COURSE;
                }
                query = query.replaceAll("_SEARCH_", searchText);

                rst = this.executeQuery(query, connection);
            }

            while(rst.next()){
                CourseApplication course = new CourseApplication();
                course.setId(rst.getInt("id"));
                course.setCourseId(rst.getLong("course_id"));
                course.setEmployeeId(rst.getInt("emp_number"));
                course.setDateApplied(new Date(rst.getDate("date_applied").getTime()));
                course.setComment(rst.getString("comment"));
                course.setTransactionId(rst.getLong("transactionid"));
                course.seteStatus(rst.getString("estatus"));
                course.setCourseDescription(rst.getString("course"));
                course.setEmployeeName(rst.getString("empname"));
                course.setCompanyCode(rst.getString("company_code"));

                records.add(course);
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

    public List<CourseApplication> getAllCourseApplicationByCompany(String code){
        return getAllCourseApplicationByCompany(code, null, null);
    }
    public List<CourseApplication> getAllCourseApplicationByCompany(String code, String criteria, String searchText){
        Connection connection = null;
        List<CourseApplication> records = new ArrayList<CourseApplication>();
        Vector param = new Vector();
        Vector type = new Vector();
        try{
            param.add(code); type.add("STRING");

            connection = this.getConnection(true);
            ResultSet rst = null;
            if(criteria == null || searchText == null){
                rst = this.executeParameterizedQuery(connection, 
                        DatabaseQueryLoader.getQueryAssignedConstant().COURSE_APPLICATION_SELECT_BY_COMPANY, param, type);
            }else{
                String query = "";
                //param.add(searchText); type.add("STRING");

                if(criteria.equalsIgnoreCase("NAME")){
                    query = DatabaseQueryLoader.getQueryAssignedConstant().COURSE_APPLICATION_SELECT_BY_COMPANY_SEARCH_BY_APPLICANT;
                }else if(criteria.equalsIgnoreCase("COURSE")){
                    query = DatabaseQueryLoader.getQueryAssignedConstant().COURSE_APPLICATION_SELECT_BY_COMPANY_SEARCH_BY_COURSE;
                }
                query = query.replaceAll("_SEARCH_", searchText);

                rst = this.executeQuery(query, connection);
            }

            while(rst.next()){
                CourseApplication course = new CourseApplication();
                course.setId(rst.getInt("id"));
                course.setCourseId(rst.getLong("course_id"));
                course.setEmployeeId(rst.getInt("emp_number"));
                course.setDateApplied(new Date(rst.getDate("date_applied").getTime()));
                course.setComment(rst.getString("comment"));
                course.setTransactionId(rst.getLong("transactionid"));
                course.seteStatus(rst.getString("estatus"));
                course.setCourseDescription(rst.getString("course"));
                course.setEmployeeName(rst.getString("empname"));
                course.setCompanyCode(rst.getString("company_code"));

                records.add(course);
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

    public List<CourseApplication> getAllCourseApplicationForEmployee(long id){
        Connection connection = null;
        List<CourseApplication> records = new ArrayList<CourseApplication>();

        Vector param = new Vector();
        Vector type = new Vector();

        try{
            param.add(id); type.add("NUMBER");
            
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().COURSE_APPLICATION_SELECT_BY_EMPLOYEE, param, type);

            while(rst.next()){
                CourseApplication course = new CourseApplication();
                course.setId(rst.getInt("id"));
                course.setCourseId(rst.getLong("course_id"));
                course.setEmployeeId(rst.getInt("emp_number"));
                course.setDateApplied(new Date(rst.getDate("date_applied").getTime()));
                course.setComment(rst.getString("comment"));
                course.setTransactionId(rst.getLong("transactionid"));
                course.seteStatus(rst.getString("estatus"));
                course.setCourseDescription(rst.getString("course"));
                course.setEmployeeName(rst.getString("empname"));

                records.add(course);
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

    public CourseApplication getCourseApplicationById(long id){
        Connection connection = null;
        try{
            connection = this.getConnection(true);
            return getCourseApplicationById(id, connection);
        }catch(Exception e){
            return null;
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public CourseApplication getCourseApplicationByTransaction(long id){
        Connection connection = null;
        try{
            connection = this.getConnection(true);
            return getCourseApplicationByTransaction(id, connection);
        }catch(Exception e){
            return null;
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public CourseApplication getCourseApplicationById(long id, Connection connection){
        return getCourseApplicationById(id, connection, DatabaseQueryLoader.getQueryAssignedConstant().COURSE_APPLICATION_SELECT_BY_ID);
    }

    public CourseApplication getCourseApplicationByTransaction(long id, Connection connection){
        return getCourseApplicationById(id, connection, DatabaseQueryLoader.getQueryAssignedConstant().COURSE_APPLICATION_SELECT_BY_TRANSACTION);
    }

    public CourseApplication getCourseApplicationById(long id, Connection connection, String query){

        CourseApplication record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            ResultSet rst = this.executeParameterizedQuery(connection, query,
                    param, type);

            while(rst.next()){
                CourseApplication course = new CourseApplication();
                course.setId(rst.getInt("id"));
                course.setCourseId(rst.getLong("course_id"));
                course.setEmployeeId(rst.getInt("emp_number"));
                course.setDateApplied(new Date(rst.getDate("date_applied").getTime()));
                course.setComment(rst.getString("comment"));
                course.setTransactionId(rst.getLong("transactionid"));
                course.seteStatus(rst.getString("estatus"));
                course.setCourseDescription(rst.getString("course"));
                course.setEmployeeName(rst.getString("empname"));

                record = course;
            }
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
        return record;
    }

    public int createNewCourseApplication(CourseApplication course){
        Connection connection = null;
        try{

            connection = this.getConnection(false);
            return createNewCourseApplication(course, connection);
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
    public int createNewCourseApplication(CourseApplication course, Connection connection) throws Exception{
        
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(course.getCourseId()); type.add("NUMBER");
            param.add(course.getEmployeeId()); type.add("NUMBER");
            param.add(course.getDateApplied()); type.add("DATE");
            param.add(course.getComment()); type.add("STRING");
            param.add(course.getTransactionId()); type.add("NUMBER");
            param.add(course.geteStatus()); type.add("STRING");

            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().COURSE_APPLICATION_INSERT,
                    param, type);

            return i;
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }//:~

    public int updateCourseApplicationTransactionStatus(CourseApplication course, Connection connection){
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(course.geteStatus()); type.add("STRING");

            param.add(course.getTransactionId()); type.add("NUMBER");

            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().COURSE_APPLICATION_UPDATE_STATUS_BY_TRANSACTION,
                    param, type);

            return i;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }//:~

    public int updateCourseApplication(CourseApplication course){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(course.getCourseId()); type.add("NUMBER");
            param.add(course.getEmployeeId()); type.add("NUMBER");
            param.add(course.getDateApplied()); type.add("DATE");
            param.add(course.getComment()); type.add("STRING");
            param.add(course.getTransactionId()); type.add("NUMBER");

            param.add(course.getId()); type.add("NUMBER");

            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().COURSE_APPLICATION_UPDATE,
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

    public int deleteCourseApplication(CourseApplication course){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(course.getId()); type.add("NUMBER");

            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().COURSE_APPLICATION_DELETE,
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

    public int deleteCourseApplications(List<Integer> courses){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{


            connection = this.getConnection(false);
            this.startTransaction(connection);
            for(int course : courses){
                param.add(course); type.add("NUMBER");
                int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().COURSE_APPLICATION_DELETE,
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

    /* =========== COURSE FEEDBACK ============================== */
    public CourseFeedback getCourseFeedbackByApplication(long id){
        Connection connection = null;
        CourseFeedback record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");

            connection = this.getConnection(true);

            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().COURSE_FEEDBACK_SELECT_BY_COURSE_APPLICATION,
                    param, type);

            while(rst.next()){
                CourseFeedback course = new CourseFeedback();
                course.setCourseApplicationId(rst.getLong("course_application_id"));
                course.setEmployeeId(rst.getLong("emp_number"));
                course.setDateApplied(new Date(rst.getDate("date_applied").getTime()));
                course.setComment(rst.getString("comment"));

                record = course;
            }
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return record;
    }

    public int createNewCourseFeedback(CourseFeedback course) throws Exception{
        Connection connection = null;
        try{
            connection = this.getConnection(true);
            return createNewCourseFeedback(course, connection);
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
    public int createNewCourseFeedback(CourseFeedback course, Connection connection) throws Exception{

        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(course.getCourseApplicationId()); type.add("NUMBER");
            param.add(course.getEmployeeId()); type.add("NUMBER");
            param.add(course.getDateApplied()); type.add("DATE");
            param.add(course.getComment()); type.add("STRING");

            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().COURSE_FEEDBACK_INSERT,
                    param, type);

            return i;
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }//:~

}
