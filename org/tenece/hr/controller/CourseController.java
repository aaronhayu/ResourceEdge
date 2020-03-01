
/*
 * (c) Copyright 2009 The Tenece Professional Services.
 *
 * Created on 27 May 2009, 13:28
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
 * even if Tenece Professional Services has been advised of the possibility of such damages.
 *
 * You acknowledge that Software is not designed, licensed or intended
 * for use in the design, construction, operation or maintenance of
 * any nuclear facility.
 */

package org.tenece.hr.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.tenece.web.common.BaseController;
import org.tenece.web.common.ConfigReader;
import org.tenece.web.common.DateUtility;
import org.tenece.web.common.FormatUtility;
import org.tenece.web.data.Course;
import org.tenece.web.data.CourseApplication;
import org.tenece.web.data.CourseFeedback;
import org.tenece.web.data.Employee;
import org.tenece.web.data.TransactionType;
import org.tenece.web.data.Users;
import org.tenece.web.services.CourseService;
import org.tenece.web.services.EmployeeService;

/**
 *
 * @author jeffry.amachree
 */
public class CourseController extends BaseController{
    
    private CourseService courseService ;
    private EmployeeService employeeService;
    
    /** Creates a new instance of CourseController */
    public CourseController() {
    }
    
    public ModelAndView viewAllCourse(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("course_view");
        
        List<Course> list = null;

        if(this.getUserPrincipal(request).getGroupCompanyUser() == 1){
            list = getCourseService().findAllCourses();
            view.addObject("showCompany", "Y");
        }else{
            Employee employee = this.getEmployeeService().findEmployeeBasicById(getUserPrincipal(request).getEmployeeId());
            list = getCourseService().findAllCoursesByCompany(employee.getCompanyCode());
        }

        view.addObject("result", list);
        return view;
    }
    
    public ModelAndView editCourse(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("course_edit");
        String param = request.getParameter("idx");
        if(param == null){
            return viewAllCourse(request, response);
        }
        long id = Long.parseLong(param);
        Course course = getCourseService().findCourseById(id);
        
        view.addObject("course", course);

        //get company info
        view = getAndAppendCompanyToView(view, request);
        
        return view;
    }
    
    public ModelAndView newCourse(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("course_edit");

        //get company info
        view = getAndAppendCompanyToView(view, request);

        view.addObject("course", new Course());
        return view;
    }
    
    public ModelAndView deleteCourse(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("");
        List<Integer> ids = new ArrayList<Integer>();
        //get mode of request
        String mode = request.getParameter("mode");
        //check if mode is for more than one record
        //get mode, if mode is equals 1, then process for multiple
        if(mode != null && mode.trim().equals("1")){
            String[] args = request.getParameterValues("_chk");
            for(String id : args){
                ids.add(Integer.parseInt(id));
            }
        }else{//then it is zero
            ids.add(Integer.parseInt(request.getParameter("id")));
        }
        getCourseService().deleteCourse(ids);
       //delete
        return viewAllCourse(request, response);
    }
    
    public ModelAndView processCourseRequest(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("message");
        String operation = request.getParameter("txtMode");
        //operation mode: N= New, E=Edit
        //id, course, organizer, course_detail, start_date, end_date,
        //no_of_days, start_time, travel_expenses, allowance, misc_cost, course_venue
        if(operation.trim().equals("N")){
            Course course = new Course();
            String courseName = request.getParameter("txtCourse");
            String organizer = request.getParameter("txtOrganizer");
            String detail = request.getParameter("txtDetail");
            String strStartDate = request.getParameter("txtStartDate");
            String strEndDate = request.getParameter("txtEndDate");
            String strNoOfDays = request.getParameter("txtDays");
            String startTime  = request.getParameter("txtStartTime");
            String strCourseFee = request.getParameter("txtFee");
            String strExpenses = request.getParameter("txtExpenses");
            String strAllowance  = request.getParameter("txtAllowance");
            String strMisc = request.getParameter("txtMisc");
            String strCourseVenue = request.getParameter("txtVenue");
            String companyCode = request.getParameter("cbCompany");
            
            if((!FormatUtility.isNumber(strExpenses)) || (!FormatUtility.isNumber(strAllowance))
                    || (!FormatUtility.isNumber(strMisc)) || (!FormatUtility.isNumber(strNoOfDays))){
                return newCourse(request, response);
            }

            Date _startDate = new Date();
            Date _endDate = new Date();
            try {
                _startDate = DateUtility.getDateFromString(strStartDate, ConfigReader.DATE_FORMAT);
                _endDate = DateUtility.getDateFromString(strEndDate, ConfigReader.DATE_FORMAT);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }

            course.setCourse(courseName);
            course.setOrganizer(organizer);
            course.setCourseDetail(detail);
            course.setStartDate(_startDate);
            course.setEndDate(_endDate);
            course.setNoOfDays(Integer.parseInt(strNoOfDays));
            course.setStartTime(startTime);
            course.setCourseFee(Float.parseFloat(strCourseFee));
            course.setTravelExpenses(Float.parseFloat(strExpenses));
            course.setAllowance(Float.parseFloat(strAllowance));
            course.setMisc_Cost(Float.parseFloat(strMisc));
            course.setCourseVenue(strCourseVenue);
            course.setCompanyCode(companyCode);
            
            boolean saved = getCourseService().updateCourse(course, CourseService.MODE_INSERT);
            if(saved == false){
                view = new ModelAndView("error");
                view.addObject("message", "Can not save course/training information. Please try again later");
                return view;
            }

            createNewAuditTrail(request, "Added new course: " + courseName);
            view.addObject("message", "Record Saved Successfully.");
            
        }else if(operation.trim().equals("E")){
            Course course = new Course();
            String courseName = request.getParameter("txtCourse");
            String organizer = request.getParameter("txtOrganizer");
            String detail = request.getParameter("txtDetail");
            String strStartDate = request.getParameter("txtStartDate");
            String strEndDate = request.getParameter("txtEndDate");
            String strNoOfDays = request.getParameter("txtDays");
            String startTime  = request.getParameter("txtStartTime");
            String strCourseFee = request.getParameter("txtFee");
            String strExpenses = request.getParameter("txtExpenses");
            String strAllowance  = request.getParameter("txtAllowance");
            String strMisc = request.getParameter("txtMisc");
            String strCourseVenue = request.getParameter("txtVenue");
            String companyCode = request.getParameter("cbCompany");
            
            String courseId = request.getParameter("txtId");
            
            if((!FormatUtility.isNumber(strExpenses)) || (!FormatUtility.isNumber(strAllowance))
                    || (!FormatUtility.isNumber(strMisc)) || (!FormatUtility.isNumber(strNoOfDays))){
                return newCourse(request, response);
            }

            Date _startDate = new Date();
            Date _endDate = new Date();
            try {
                _startDate = DateUtility.getDateFromString(strStartDate, ConfigReader.DATE_FORMAT);
                _endDate = DateUtility.getDateFromString(strEndDate, ConfigReader.DATE_FORMAT);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            
            course.setCourse(courseName);
            course.setOrganizer(organizer);
            course.setCourseDetail(detail);
            course.setStartDate(_startDate);
            course.setEndDate(_endDate);
            course.setNoOfDays(Integer.parseInt(strNoOfDays));
            course.setStartTime(startTime);
            course.setCourseFee(Float.parseFloat(strCourseFee));
            course.setTravelExpenses(Float.parseFloat(strExpenses));
            course.setAllowance(Float.parseFloat(strAllowance));
            course.setMisc_Cost(Float.parseFloat(strMisc));
            course.setCourseVenue(strCourseVenue);
            course.setId(Integer.parseInt(courseId));
            course.setCompanyCode(companyCode);
            
            boolean saved = getCourseService().updateCourse(course, CourseService.MODE_UPDATE);
            if(saved == false){
                view = new ModelAndView("error");
                view.addObject("message", "Can not save course/training information. Please try again later");
                return view;
            }

            createNewAuditTrail(request, "Modified course: " + courseName);
            view.addObject("message", "Record Saved Successfully.");
        }
        return view;
    }

    public ModelAndView viewAllCourseApplication(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("course_application_view");

        String cbSearch = request.getParameter("cbSearch");
        String txtSearch = request.getParameter("txtSearch");

        List<CourseApplication> list = null;
        if(cbSearch == null || txtSearch == null){
            //list = getCourseService().findAllCourseApplication();
            if(this.getUserPrincipal(request).getGroupCompanyUser() == 1){
                list = getCourseService().findAllCourseApplication();
                view.addObject("showCompany", "Y");
            }else{
                Employee employee = this.getEmployeeService().findEmployeeBasicById(getUserPrincipal(request).getEmployeeId());
                list = getCourseService().findAllCourseApplicationByCompany(employee.getCompanyCode());
            }
        }else{

            if(this.getUserPrincipal(request).getGroupCompanyUser() == 1){
                list = getCourseService().findAllCourseApplication(cbSearch, txtSearch);
                view.addObject("showCompany", "Y");
            }else{
                Employee employee = this.getEmployeeService().findEmployeeBasicById(getUserPrincipal(request).getEmployeeId());
                list = getCourseService().findAllCourseApplicationByCompany(employee.getCompanyCode(), cbSearch, txtSearch);
            }
        }
        
        view.addObject("result", list);
        return view;
    }

    public ModelAndView editCourseApplication(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("course_application_edit");
        String param = request.getParameter("idx");
        if(param == null){
            return viewAllCourseApplication(request, response);
        }
        long id = Long.parseLong(param);

        CourseApplication courseApp = getCourseService().findCourseApplicationById(id);
        Course course = getCourseService().findCourseById(courseApp.getCourseId());
        //build temp employee data from course application instead of from database
        Employee employee = new Employee();
        employee.setFirstName(courseApp.getEmployeeName());
        employee.setEmployeeNumber(courseApp.getEmployeeId());

        view.addObject("course", course);
        view.addObject("employee", employee);
        view.addObject("courseapp", courseApp);
        return view;
    }

    public ModelAndView newCourseApplication(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("course_application_edit");

        //get Parameter from page
        String cbEmp = request.getParameter("cbEmployee");
        String cbCourse = request.getParameter("cbCourse");

        String companyCode = getActiveEmployeeCompanyCode(request);
        
        if(cbEmp == null){ cbEmp = "";}
        if(cbCourse == null){ cbCourse = ""; }

        //if paramater is for selected course and employee, load course detail.
        if(cbEmp.trim().equals("") || cbCourse.trim().equals("")){

        }else{//parameters are valid... get course detail and employee record
            Course course = getCourseService().findCourseById(Long.parseLong(cbCourse));
            Employee employee = getEmployeeService().findEmployeeBasicById(Long.parseLong(cbEmp));
            view.addObject("course", course);
            view.addObject("employee", employee);
        }


        List<Course> courseList = getCourseService().findAllCoursesByCompany(companyCode);
        List<Employee> employeeList = getEmployeeService().findAllEmployeeForBasicByCompany(companyCode);

        view.addObject("courseList", courseList);
        view.addObject("employeeList", employeeList);
        view.addObject("courseapp", new CourseApplication());

        //get company info
        view = getAndAppendCompanyToView(view, request);
        
        return view;
    }

    public ModelAndView newTransactionCourseApplication(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("transaction/course_application_edit");

        //user is ESS and is applying for training
        Users user = this.getUserPrincipal(request);

        long employeeId = (long)user.getEmployeeId();
        //check if APPROVAL route is already defined for the specific transaction
        String transParentId = "TRAINING";
        if(employeeService.isApprovalRouteDefined(transParentId, employeeId) == 0){
            TransactionType transactionType = employeeService.findTransactionTypeByParentID(transParentId);
            view = new ModelAndView("error");
            view.addObject("message", "No approval route defined for \" " + transactionType.getDescription() + "\"");
            return view;
        }

        //get Parameter from page
        String cbEmp = String.valueOf(user.getEmployeeId());
        String cbCourse = request.getParameter("cbCourse");

        if(cbEmp == null || cbEmp == "0"){ cbEmp = "";}
        if(cbCourse == null){ cbCourse = ""; }

        //if paramater is for selected course and employee, load course detail.
        if(cbEmp.trim().equals("") || cbCourse.trim().equals("")){

        }else{//parameters are valid... get course detail and employee record
            Course course = getCourseService().findCourseById(Long.parseLong(cbCourse));
            view.addObject("course", course);
        }

        String companyCode = getActiveEmployeeCompanyCode(request);

        List<Course> courseList = getCourseService().findAllCoursesByCompany(companyCode);
        Employee employee = getEmployeeService().findEmployeeBasicById(Long.parseLong(cbEmp));

        view.addObject("courseList", courseList);
        view.addObject("employee", employee);
        view.addObject("courseapp", new CourseApplication());
        return view;
    }


    public ModelAndView deleteCourseApplication(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("");
        List<Integer> ids = new ArrayList<Integer>();
        //get mode of request
        String mode = request.getParameter("mode");
        //check if mode is for more than one record
        //get mode, if mode is equals 1, then process for multiple
        if(mode != null && mode.trim().equals("1")){
            String[] args = request.getParameterValues("_chk");
            for(String id : args){
                ids.add(Integer.parseInt(id));
            }
        }else{//then it is zero
            ids.add(Integer.parseInt(request.getParameter("id")));
        }
        getCourseService().deleteCourseApplication(ids);
       //delete
        return viewAllCourseApplication(request, response);
    }

    public long processCourseTransaction(CourseApplication course){
        return getCourseService().initiateTransaction(course);
    }
    
    public ModelAndView processCourseApplicationRequest(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("success");
        String operation = request.getParameter("txtMode");
        String saveType = request.getParameter("txtType");
        
        //check if save type is null
        saveType = saveType == null ? "" : saveType;
        
        //operation mode: N= New, E=Edit
        
        if(operation.trim().equals("N")){
            CourseApplication course = new CourseApplication();
            String courseId = request.getParameter("txtCourse");
            String employeeId = request.getParameter("txtEmp");
            String comment = request.getParameter("txtComments");

            if((!FormatUtility.isNumber(courseId)) || (!FormatUtility.isNumber(employeeId))){
                return newCourseApplication(request, response);
            }


            course.setCourseId(Long.parseLong(courseId));
            course.setEmployeeId(Long.parseLong(employeeId));
            course.setComment(comment);
            course.setDateApplied(new Date());
            course.setTransactionId(0L);
            course.seteStatus("A");

            int role = this.getUserLoginRole(request);

            if(role == 0 && saveType.trim().equals("TRNX")){
                long transactionID = processCourseTransaction(course);
                if(transactionID == 0L){
                    view = new ModelAndView("error");
                    view.addObject("message", "Unable to complete transaction request.");
                    return view;
                }
                long nextApprover = courseService.findNextApprovingOfficer(transactionID);
                Employee supervisor = employeeService.findEmployeeBasicById(nextApprover);
                view = new ModelAndView("success");
                view.addObject("message", "Transaction has been forwarded to " + supervisor.getFullName() + " for approval.");
                return view;
            }else{
                if(role == 0){
                    view = new ModelAndView("error");
                    view.addObject("message", "Unable to complete operation, User is not an administrator.");
                }
                boolean saved = getCourseService().updateCourseApplication(course, CourseService.MODE_INSERT);
                if(saved == false){
                    view = new ModelAndView("error");
                    view.addObject("message", "Can not save course/training information. Please try again later");
                    return view;
                }
            }
            try{
                createNewAuditTrail(request, "New course application '" + courseService.findCourseById(course.getCourseId()));
            }catch(Exception e){}
            view = new ModelAndView("success");
            view.addObject("message", "Record Saved Successfully.");

        }else if(operation.trim().equals("E")){
            CourseApplication course = new CourseApplication();
            String courseId = request.getParameter("txtCourse");
            String employeeId = request.getParameter("txtEmp");
            String comment = request.getParameter("txtComments");

            String applicationId = request.getParameter("txtId");

            if((!FormatUtility.isNumber(courseId)) || (!FormatUtility.isNumber(employeeId))){
                return newCourseApplication(request, response);
            }

            
            course.setCourseId(Long.parseLong(courseId));
            course.setEmployeeId(Long.parseLong(employeeId));
            course.setComment(comment);
            course.setDateApplied(new Date());
            course.setTransactionId(0L);
            course.seteStatus("A");

            course.setId(Integer.parseInt(applicationId));

            boolean saved = getCourseService().updateCourseApplication(course, CourseService.MODE_UPDATE);
            if(saved == false){
                view = new ModelAndView("error");
                view.addObject("message", "Can not process course information. Please try again later");
                return view;
            }

            try{
                createNewAuditTrail(request, "Modified course application '" + courseService.findCourseById(course.getCourseId()));
            }catch(Exception e){}
            view = new ModelAndView("message");
            view.addObject("message", "Record Saved Successfully.");
        }
        return view;
    }

    public ModelAndView editCourseFeedback(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("course_feedback_edit");
        String param = request.getParameter("cbCourse");

        Users userPrincipal = this.getUserPrincipal(request);

        String companyCode = this.getActiveEmployeeCompanyCode(request);

        long employeeId = userPrincipal.getEmployeeId();
        int role = userPrincipal.getAdminUser();
        List<CourseApplication> applications = null;
        if(role == 1){
            applications = courseService.findAllCourseApplicationByCompany(companyCode);
        }else{
            applications = courseService.findAllCourseApplicationForEmployee(employeeId);
        }
        //add to list
        view.addObject("applications", applications);
        if(param == null || param.trim().equals("")){
            //return view
            return view;
        }else{
            long id = Long.parseLong(param);

            CourseApplication courseApp = getCourseService().findCourseApplicationById(id);
            Course course = getCourseService().findCourseById(courseApp.getCourseId());
            
            view.addObject("course", course);
            view.addObject("feedback", new CourseFeedback());
            view.addObject("courseapp", courseApp);
        }
        return view;
    }//:~

    public ModelAndView processCourseFeedbackRequest(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("message");
        
        Users user = this.getUserPrincipal(request);
        //operation mode: N= New, E=Edit

        CourseFeedback course = new CourseFeedback();
        String courseApplicationId = request.getParameter("txtId");
        String employeeId = null;
        if(user.getAdminUser() == 1){
            employeeId = request.getParameter("txtEmp");
        }else{
            employeeId = String.valueOf(user.getEmployeeId());
        }
        String comment = request.getParameter("txtComment");
        
        if((!FormatUtility.isNumber(courseApplicationId)) || (!FormatUtility.isNumber(employeeId))){
            view = new ModelAndView("error");
            view.addObject("message", "Unable to proceed with request");
            return view;
        }


        course.setCourseApplicationId(Long.parseLong(courseApplicationId));
        course.setEmployeeId(Long.parseLong(employeeId));
        course.setComment(comment);
        course.setDateApplied(new Date());

        int saved = getCourseService().updateCourseFeedback(course);
        if(saved != 1){
            view = new ModelAndView("error");
            view.addObject("message", "Can not process course feedback. Please try again later");
            return view;
        }
        try{
            CourseApplication _app = getCourseService().findCourseApplicationById(course.getCourseApplicationId());
            Course _course = getCourseService().findCourseById(_app.getCourseId());
            createNewAuditTrail(request, "New course feedback for " + _course.getCourse());
        }catch(Exception e){}

        view = new ModelAndView("success");
        view.addObject("message", "Record Saved Successfully.");

       return view;
    }

    /**
     * @return the courseService
     */
    public CourseService getCourseService() {
        return courseService;
    }

    /**
     * @param courseService the courseService to set
     */
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    /**
     * @return the employeeService
     */
    public EmployeeService getEmployeeService() {
        return employeeService;
    }

    /**
     * @param employeeService the employeeService to set
     */
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    
}
