
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

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.tenece.web.common.BaseController;
import org.tenece.web.common.FormatUtility;
import org.tenece.web.data.Appraisal;
import org.tenece.web.data.AppraisalCriteria;
import org.tenece.web.data.AppraisalInformation;
import org.tenece.web.data.Course;
import org.tenece.web.data.CourseApplication;
import org.tenece.web.data.Employee;
import org.tenece.web.data.EmployeeBank;
import org.tenece.web.data.EmployeeEducation;
import org.tenece.web.data.EmployeeEmergencyContact;
import org.tenece.web.data.Leave;
import org.tenece.web.data.NoticeBoard;
import org.tenece.web.data.PerformanceTarget;
import org.tenece.web.data.Transaction;
import org.tenece.web.data.TransactionType;
import org.tenece.web.data.Users;
import org.tenece.web.services.AppraisalService;
import org.tenece.web.services.CourseService;
import org.tenece.web.services.EmployeeService;
import org.tenece.web.services.LeaveService;
import org.tenece.web.services.NoticeBoardService;
import org.tenece.web.services.PerformanceTargetService;
import org.tenece.web.services.TransactionService;

/**
 *
 * @author jeffry.amachree
 */
public class InboxController extends BaseController{
    
    private TransactionService transactionService;
    private PerformanceTargetService performanceTargetService;
    private EmployeeService employeeService;
    private CourseService courseService;
    private LeaveService leaveService;
    private AppraisalService appraisalService;
    private NoticeBoardService noticeBoardService;
    
    /** Creates a new instance of DepartmentController */
    public InboxController() {
    }
    
    public ModelAndView viewInbox(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("inbox_view");
        //get user detail from session
        Users user = this.getUserPrincipal(request);

        //instantiate list for results
        List<Transaction> list = new ArrayList<Transaction>();

        //get search criteria
        String searchKey = request.getParameter("cbSearch");
        String searchValue = request.getParameter("txtSearch");

        //check if user requested for a search result
        if(searchKey == null || searchValue == null){
            //use the id to get all transactions for the user
            list = transactionService.findInboxTransactions(user.getEmployeeId());
        }else{
            list = transactionService.findInboxTransactions(user.getEmployeeId(), searchKey, searchValue);
        }
        //add to view
        view.addObject("result", list);
        //return view to UI - (inbox-view)
        return view;
    }

    public ModelAndView viewInboxDetail(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("error");
        //get user detail from session
        Users user = this.getUserPrincipal(request);
        //use the id to get all transactions for the user
        
        String strTrnx = request.getParameter("idx");
        //check if id is a number
        if(!FormatUtility.isNumber(strTrnx)){
            view = new ModelAndView("error");
            return view;
        }
        Transaction trnx = transactionService.findTransactionById(Long.parseLong(strTrnx));
        TransactionType transactionType = transactionService.findTransactionTypeById(trnx.getTransactionType());
        
        if(transactionType.getParentId().equals("KPI")){
            return getKPIDetailView(trnx);
        }else if(transactionType.getParentId().equals("TRAINING")){
            return getCourseDetailedView(trnx);
        }else if(transactionType.getParentId().equals("LEAVE")){
            return getLeaveDetailedView(trnx);
        }else if(transactionType.getParentId().equals("LEAVE-R")){
            return getLeaveResumptionDetailedView(trnx);
        }else if(transactionType.getParentId().equals("TERM")){
            return getTerminationDetailedView(trnx);
        }else if(transactionType.getParentId().equals("ESS-BIO")){
            return getEmployeeBioDetailedView(trnx);
        }else if(transactionType.getParentId().equals("ESS-BENE")){
            return getEmployeeBeneficiariesDetailedView(trnx);
        }else if(transactionType.getParentId().equals("ESS-EDU")){
            return getEmployeeEducationDetailedView(trnx);
        }else if(transactionType.getParentId().equals("ESS-EMER")){
            return getEmployeeEmergencyDetailedView(trnx);
        }else if(transactionType.getParentId().equals("ESS-BANK")){
            return getEmployeeBankDetailedView(trnx);
        }else if(transactionType.getParentId().equals("APPRAISE")){
            return getAppraisalView(trnx, user.getEmployeeId());
        }else if(transactionType.getParentId().equals("NOTE")){
            return getNoticeBoardView(trnx);
        }else{
            view = new ModelAndView("error");
            view.addObject("message", "Unable to process transaction, contact system administrator for support.");
        }

        return view;
    }
    private ModelAndView getEmployeeBioDetailedView(Transaction transaction){
        ModelAndView view = new ModelAndView("transaction/employee_bio_edit");
        //get target
        Employee employee = employeeService.findEmployeeTransactionBioData(transaction.getId());
        Employee initialEmployeeData = employeeService.findEmployeeBasicById(employee.getEmployeeNumber());
        Employee initialEmployeeContact = employeeService.findEmployeeContactById(employee.getEmployeeNumber());

        //ad object to view
        view.addObject("initEmp", initialEmployeeData);
        view.addObject("employee", employee);
        view.addObject("initContact", initialEmployeeContact);

        return view;
    }

    private ModelAndView getNoticeBoardView(Transaction transaction){

        ModelAndView view = new ModelAndView("transaction/noticeboard");
        Employee employee = getEmployeeService().findEmployeeBasicById(transaction.getInitiator());

        NoticeBoard notice = getNoticeBoardService().findNoticeBoardByTransaction(transaction.getId());
        view.addObject("notice", notice);
        view.addObject("employee", employee);

        return view;
    }

    private ModelAndView getEmployeeBankDetailedView(Transaction transaction){
        ModelAndView view = new ModelAndView("transaction/employee_bank_edit");
        //get target
        EmployeeBank bankDetail = employeeService.findESS_EmployeeBankDetailByTransaction(transaction.getId());
        //get old bank record
        EmployeeBank oldBankDetail = employeeService.findEmployeeBankDetail(transaction.getInitiator());
        //get employee record
        Employee employee = employeeService.findEmployeeBasicById(transaction.getInitiator());

        view.addObject("employee", employee);
        view.addObject("bank", bankDetail);
        view.addObject("oldBank", oldBankDetail);

        return view;
    }

    private ModelAndView getEmployeeEmergencyDetailedView(Transaction transaction){
        ModelAndView view = new ModelAndView("transaction/employee_emergency_view");
        //get target
        List<EmployeeEmergencyContact> emergencyList = employeeService.findESS_EmployeeEmergencyContact(transaction.getInitiator());
        //get old/initial record that was saved
        List<EmployeeEmergencyContact> oldEmergencyList = employeeService.findEmployeeEmergencyContacts(transaction.getInitiator());
        //get employee record
        Employee employee = employeeService.findEmployeeBasicById(transaction.getInitiator());
        employee.setTransactionId(transaction.getId());
        //ad object to view
        view.addObject("employee", employee);
        view.addObject("result", emergencyList);
        view.addObject("oldResult", oldEmergencyList);

        return view;
    }

    private ModelAndView getEmployeeEducationDetailedView(Transaction transaction){
        ModelAndView view = new ModelAndView("transaction/employee_education_view");
        //get target
        List<EmployeeEducation> educationList = employeeService.findESS_EmployeeEducation(transaction.getInitiator());
        //get employee record
        Employee employee = employeeService.findEmployeeBasicById(transaction.getInitiator());
        employee.setTransactionId(transaction.getId());
        //ad object to view
        view.addObject("employee", employee);
        view.addObject("result", educationList);

        return view;
    }

    private ModelAndView getEmployeeBeneficiariesDetailedView(Transaction transaction){
        ModelAndView view = new ModelAndView("transaction/employee_beneficiaries_edit");
        //get target

        Employee employee = employeeService.findEmployeeBasicById(transaction.getInitiator());
        employee.setTransactionId(transaction.getId());
        //ad object to view
        view.addObject("employee", employee);

        return view;
    }

    private ModelAndView getCourseDetailedView(Transaction transaction){
        ModelAndView view = new ModelAndView("transaction/course_edit");
        //get target
        CourseApplication courseApplication = courseService.findCourseApplicationByTransaction(transaction.getId());
        Course course = courseService.findCourseById(courseApplication.getCourseId());
        Employee employee = employeeService.findEmployeeBasicById(courseApplication.getEmployeeId());

        //ad object to view
        view.addObject("courseapp", courseApplication);
        view.addObject("employee", employee);
        view.addObject("course", course);

        return view;
    }

    private ModelAndView getLeaveDetailedView(Transaction transaction){
        ModelAndView view = new ModelAndView("transaction/leave_edit");

        //create a blank and default leave  object
        Leave leave = leaveService.findLeaveByTransaction(transaction.getId());

        //get employee data that is required
        Employee employee = employeeService.findEmployeeBasicById(leave.getEmployeeId());
        
        //add objects to view (UI)
        view.addObject("employee", employee);
        view.addObject("leave", leave);
        return view;

    }

    private ModelAndView getLeaveResumptionDetailedView(Transaction transaction){
        ModelAndView view = new ModelAndView("transaction/leave_resumption_edit");

        //create a blank and default leave  object
        Leave.LeaveResumption leaveResumption = leaveService.findLeaveResumptionByTransaction(transaction.getId());

        //get leave resumption data by leave id
        Leave leave = leaveService.findLeaveById(leaveResumption.getLeaveId());
        //get employee data that is required
        //Employee employee = employeeService.findEmployeeBasicById(leave.getEmployeeId());

        //add objects to view (UI)
        view.addObject("leave_resume", leaveResumption);
        view.addObject("leave", leave);
        return view;

    }

    private ModelAndView getKPIDetailView(Transaction transaction){
        ModelAndView view = new ModelAndView("transaction/target_edit");
        //get target
        PerformanceTarget target = performanceTargetService.findPerformanceTargetByTransaction(transaction.getId());
        //get employee
        Employee employee = employeeService.findEmployeeBasicById(transaction.getInitiator());
        //ad object to view
        view.addObject("target", target);
        view.addObject("employee", employee);
        
        return view;
    }

    private ModelAndView getTerminationDetailedView(Transaction transaction){
        ModelAndView view = new ModelAndView("transaction/employee_terminate_edit");

        //create a blank and default leave  object
        Employee.Termination terminate = employeeService.findTerminationbyTransaction(transaction.getId());

        //get employee data that is required
        Employee employee = employeeService.findEmployeeBasicById(terminate.getEmployeeNumber());

        //add objects to view (UI)
        view.addObject("employee", employee);
        view.addObject("terminate", terminate);
        return view;

    }
    private ModelAndView getAppraisalView(Transaction transaction, long supervisor){
        ModelAndView view = new ModelAndView("transaction/appraisal_step1_view");
        System.out.println("transaction.getId() = "+transaction.getId());
        Appraisal appraisal = appraisalService.findAppraisalByTransaction(transaction.getId());
        //get step 1 by appraisal id
        System.out.println("appraisal.getId() = "+appraisal.getId());
        AppraisalInformation.Step1 step1 = appraisalService.findTransactionAppraisalStep1ByID(appraisal.getId());
        
        //get employee data that is required
        Employee initiator = employeeService.findEmployeeBasicById(appraisal.getEmployeeId());
        Employee employee = employeeService.findEmployeeBasicById(supervisor);
        AppraisalCriteria criteria = appraisalService.findAllAppraisalCriteriaByID(step1.getCriteriaId());

        //add objects to view (UI)
        view.addObject("result", step1);
        view.addObject("initiator", initiator);
        view.addObject("employee", employee);
        view.addObject("appraisal", appraisal);
        view.addObject("criteria", criteria);
        view.addObject("transaction", transaction);
        return view;

    }
    /**
     * @return the transactionService
     */
    public TransactionService getTransactionService() {
        return transactionService;
    }

    /**
     * @param transactionService the transactionService to set
     */
    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    /**
     * @return the performanceTargetService
     */
    public PerformanceTargetService getPerformanceTargetService() {
        return performanceTargetService;
    }

    /**
     * @param performanceTargetService the performanceTargetService to set
     */
    public void setPerformanceTargetService(PerformanceTargetService performanceTargetService) {
        this.performanceTargetService = performanceTargetService;
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
     * @return the leaveService
     */
    public LeaveService getLeaveService() {
        return leaveService;
    }

    /**
     * @param leaveService the leaveService to set
     */
    public void setLeaveService(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    /**
     * @return the appraisalService
     */
    public AppraisalService getAppraisalService() {
        return appraisalService;
    }

    /**
     * @param appraisalService the appraisalService to set
     */
    public void setAppraisalService(AppraisalService appraisalService) {
        this.appraisalService = appraisalService;
    }

    /**
     * @return the noticeBoardService
     */
    public NoticeBoardService getNoticeBoardService() {
        return noticeBoardService;
    }

    /**
     * @param noticeBoardService the noticeBoardService to set
     */
    public void setNoticeBoardService(NoticeBoardService noticeBoardService) {
        this.noticeBoardService = noticeBoardService;
    }
    
}
