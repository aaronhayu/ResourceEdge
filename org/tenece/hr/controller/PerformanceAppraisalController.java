
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

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.tenece.web.common.BaseController;
import org.tenece.web.common.ConfigReader;
import org.tenece.web.common.DateUtility;
import org.tenece.web.data.Appraisal;
import org.tenece.web.data.AppraisalCompetence;
import org.tenece.web.data.AppraisalCriteria;
import org.tenece.web.data.AppraisalGroup;
import org.tenece.web.data.AppraisalInformation;
import org.tenece.web.data.AppraisalRatingLanguage;
import org.tenece.web.data.Employee;
import org.tenece.web.data.EmployeeAppraisalCompetence;
import org.tenece.web.data.FileUpload;
import org.tenece.web.data.Transaction;
import org.tenece.web.data.Users;
import org.tenece.web.services.AppraisalService;
import org.tenece.web.services.EmployeeService;
import org.tenece.web.services.SetupService;
import org.tenece.web.services.TransactionService;

/**
 *
 * @author jeffry.amachree
 */
public class PerformanceAppraisalController extends BaseController{
    
    private SetupService setupService = new SetupService();
    private AppraisalService appraisalService = new AppraisalService();
    private EmployeeService employeeService = new EmployeeService();
    private TransactionService transactionService;
    
    /** Creates a new instance of jobTitleController */
    public PerformanceAppraisalController() {
    }
    
    public ModelAndView viewAllCriteria(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("appraisal_criteria_view");
        
        List<AppraisalCriteria> list = null;
        if(this.getUserPrincipal(request).getGroupCompanyUser() == 1){
            list = appraisalService.findAllAppraisalCriteria();
            view.addObject("showCompany", "Y");
        }else{
            Employee employee = this.getEmployeeService().findEmployeeBasicById(getUserPrincipal(request).getEmployeeId());
            list = appraisalService.findAllAppraisalCriteriaByCompany(employee.getCompanyCode());
        }

        view.addObject("result", list);
        return view;
    }
    
    public ModelAndView editCriteria(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("appraisal_criteria_edit");
        String param = request.getParameter("idx");
        if(param == null){
            return viewAllCriteria(request, response);
        }
        int id = Integer.parseInt(param);
        AppraisalCriteria appraisalCriteria = appraisalService.findAllAppraisalCriteriaByID(id);
        view.addObject("record", appraisalCriteria);

        //get company info
        view = getAndAppendCompanyToView(view, request);

        return view;
    }
    
    public ModelAndView newCriteriaRecord(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("appraisal_criteria_edit");
        
        view.addObject("record", new AppraisalCriteria());

        //get company info
        view = getAndAppendCompanyToView(view, request);

        return view;
    }
    
    public ModelAndView deleteCriteria(HttpServletRequest request, HttpServletResponse response){
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
        appraisalService.deleteAppraisalCriteria(ids);
        
       //delete
        return viewAllCriteria(request, response);
    }

    /* ----------- Group ---------- */
    public ModelAndView viewAllGroupByCompetence(HttpServletRequest request, HttpServletResponse response, long criteriaId){
        ModelAndView view = new ModelAndView("appraisal_group_view");

        view.addObject("criteria", appraisalService.findAllAppraisalCriteriaByID((int)criteriaId));
        view.addObject("result", appraisalService.findAllAppraisalGroupByCriteria((int)criteriaId));
        return view;
    }

    public ModelAndView viewAllGroup(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("appraisal_group_view");

        String idx = request.getParameter("criteriaId");
        if(idx == null || idx.equals("")){
            return viewAllCriteria(request, response);
        }
        view.addObject("criteria", appraisalService.findAllAppraisalCriteriaByID(Integer.parseInt(idx)));
        view.addObject("result", appraisalService.findAllAppraisalGroupByCriteria(Integer.parseInt(idx)));
        return view;
    }

    public ModelAndView editGroup(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("appraisal_group_edit");
        String param = request.getParameter("idx");
        String strCriteriaID = request.getParameter("criteriaId");

        if(param == null || strCriteriaID == null){
            return viewAllGroup(request, response);
        }
        int id = Integer.parseInt(param);
        int criteriaID = Integer.parseInt(strCriteriaID);

        AppraisalGroup appraisalGroup = appraisalService.findAllAppraisalGroupByID(id);
        AppraisalCriteria criteria = appraisalService.findAllAppraisalCriteriaByID(criteriaID);

        view.addObject("record", appraisalGroup);
        view.addObject("criteria", criteria);
        return view;
    }

    public ModelAndView newGroupRecord(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("appraisal_group_edit");

        String strCriteriaID = request.getParameter("criteriaId");
        if(strCriteriaID == null){
            return viewAllGroup(request, response);
        }
        int criteriaID = Integer.parseInt(strCriteriaID);
        AppraisalCriteria criteria = appraisalService.findAllAppraisalCriteriaByID(criteriaID);

        view.addObject("criteria", criteria);
        view.addObject("record", new AppraisalGroup());
        return view;
    }

    public ModelAndView deleteGroup(HttpServletRequest request, HttpServletResponse response){
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
        appraisalService.deleteAppraisalGroup(ids);

       //delete
        return viewAllGroup(request, response);
    }

    /* ----------- Factor ---------- */
    private ModelAndView viewAllFactor(HttpServletRequest request, HttpServletResponse response, int groupId){
        ModelAndView view = new ModelAndView("appraisal_factor_view");

        AppraisalGroup  appraisalGroup = appraisalService.findAllAppraisalGroupByID(groupId);
        view.addObject("group", appraisalGroup);
        if(appraisalGroup.getForJobSpecific() == 1)
        {
            view.addObject("result", appraisalService.findEmployeeAllAppraisalCompetenceByGroup(groupId));
        }else
        {
            view.addObject("result", appraisalService.findAllAppraisalCompetenceByGroup(groupId));
        }
        return view;
    }

    public ModelAndView viewAllFactor(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("appraisal_factor_view");

        String idx = request.getParameter("groupid");
        if(idx == null || idx.equals("")){
            return viewAllGroup(request, response);
        }
        AppraisalGroup  appraisalGroup = appraisalService.findAllAppraisalGroupByID(Integer.parseInt(idx));
        
        view.addObject("group", appraisalGroup);
        if(appraisalGroup.getForJobSpecific() == 1)
        {
            view.addObject("result", appraisalService.findEmployeeAllAppraisalCompetenceByGroup(Integer.parseInt(idx)));
        }else
        {
            view.addObject("result", appraisalService.findAllAppraisalCompetenceByGroup(Integer.parseInt(idx)));
        }
        return view;
    }

    public ModelAndView editFactor(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("appraisal_factor_edit");
        String param = request.getParameter("idx");
        String strGroupID = request.getParameter("groupid");
        
        if(param == null || strGroupID == null){
            return viewAllFactor(request, response);
        }
        int id = Integer.parseInt(param);
        int groupID = Integer.parseInt(strGroupID);

        AppraisalGroup group = appraisalService.findAllAppraisalGroupByID(groupID);
        
        if(group.getForJobSpecific()== 1)
        {
            List<Employee> employeeList = employeeService.findAllEmployeeForBasic();
            
            view.addObject("employeeList", employeeList);
            EmployeeAppraisalCompetence appraisalCompetence = appraisalService.findEmployeeAppraisalCompetenceByID(id);
            view.addObject("record", appraisalCompetence);
        }
        else
        {
             AppraisalCompetence appraisalCompetence = appraisalService.findAllAppraisalCompetenceByID(id);
             view.addObject("record", appraisalCompetence);
        }
       
        view.addObject("group", group);
        
        return view;
    }

    public ModelAndView newFactorRecord(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("appraisal_factor_edit");

        String strGroupID = request.getParameter("groupid");
        if(strGroupID == null){
            return viewAllFactor(request, response);
        }
        int groupID = Integer.parseInt(strGroupID);
        AppraisalGroup group = appraisalService.findAllAppraisalGroupByID(groupID);

        
        if(group.getForJobSpecific()== 1)
        {
            List<Employee> employeeList = employeeService.findAllEmployeeForBasic();
            view.addObject("employeeList", employeeList);
            view.addObject("record", new EmployeeAppraisalCompetence());
        }
        else
        {
           view.addObject("record", new AppraisalCompetence());
        }
        view.addObject("group", group);
        return view;
    }

    public ModelAndView deleteFactor(HttpServletRequest request, HttpServletResponse response){
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
        appraisalService.deleteAppraisalGroup(ids);

       //delete
        return viewAllFactor(request, response);
    }

    /* ----- Rating Language ---------- */
    public ModelAndView viewAllRatingLanguage(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("appraisal_rate_view");

        view.addObject("result", appraisalService.findAllAppraisalRating());
        return view;
    }

    public ModelAndView editRatingLanguage(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("appraisal_rate_edit");
        String param = request.getParameter("idx");
        if(param == null){
            return viewAllRatingLanguage(request, response);
        }
        int id = Integer.parseInt(param);
        AppraisalRatingLanguage rating = appraisalService.findAppraisalRatingByID(id);
        view.addObject("record", rating);
        return view;
    }

    public ModelAndView newRatingLanguageRecord(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("appraisal_rate_edit");

        view.addObject("record", new AppraisalRatingLanguage());
        return view;
    }

    public ModelAndView deleteRatinglanguage(HttpServletRequest request, HttpServletResponse response){
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
        appraisalService.deleteAppraisalRating(ids);
        
       //delete
        return viewAllRatingLanguage(request, response);
    }

    /* ------------ INIT SCREEN WHEN STARTING APPRAISAL -------------------- */
    public ModelAndView viewEmployeesForAppraisal(HttpServletRequest request, HttpServletResponse response){
        //appraisal_employee_view
        ModelAndView view = new ModelAndView("appraisal_employee_view");

        String companyCode = this.getActiveEmployeeCompanyCode(request);
        
        //do search here...
        String cbSearch = request.getParameter("cbSearch");
        String txtSearch = request.getParameter("txtSearch");

        //get list of employee
        List<Employee> employees = null;
        if(cbSearch == null || txtSearch == null){
            employees = getEmployeeService().findAllEmployeeForBasicByCompany(companyCode);
        }else{
            employees = getEmployeeService().findAllEmployeeForBasicByCompany(companyCode, cbSearch, txtSearch);
        }
        
        view.addObject("result", employees);

        return view;
        
    }
    /* *************** APPRAISAL STEP1 *********************** */
    public ModelAndView viewAppraisalStep1(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("appraisal_step1_view");

        String companyCode = this.getActiveEmployeeCompanyCode(request);

        String strMode = request.getParameter("mode");
        //String strId = request.getParameter("idx");
        //int employeeId = Integer.parseInt(strId);
        Users user = this.getUserPrincipal(request);
        long employeeId = (long)user.getEmployeeId();


        //if mode is set to Y; then delete all record
        if(strMode != null && strMode.trim().equals("Y")){
            //delete all records
            //appraisalService.deleteAppraisalSteps((int)employeeId);
        }

        //get employee info...
        Employee employee = employeeService.findEmployeeBasicById(employeeId);

        view.addObject("criteria", appraisalService.findAllAppraisalCriteriaByCompany(companyCode));
        view.addObject("employee", employee);
        view.addObject("result", appraisalService.findAppraisalStep1ByID((int)employeeId));
        return view;
    }

    public ModelAndView viewAppraisalStep2(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("appraisal_step2_view");

        String strMode = request.getParameter("mode");
        //String strId = request.getParameter("idx");
        //int employeeId = Integer.parseInt(strId);
        Users user = this.getUserPrincipal(request);
        long employeeId = (long)user.getEmployeeId();

        //get employee info..
        Employee employee = employeeService.findEmployeeBasicById(employeeId);
        
        //get step 1 info... this will enable us get some data based on appraisal criteria
        Appraisal.Step1 step1 = appraisalService.findAppraisalStep1ByID((int)employeeId);

        //generate the List of objects to create competence on the page
        List<Appraisal.step2Competence> competencies = appraisalService.generateStep2Competencies((int)employeeId);
        List<Appraisal.Step2_Statement> statements = appraisalService.findAppraisalStep2StatementByID((int)employeeId);

        //set object in view
        view.addObject("rating", appraisalService.findAllAppraisalRating());
        view.addObject("employee", employee);
        view.addObject("result", appraisalService.findAppraisalStep2ByID((int)employeeId));
        view.addObject("competencies", competencies);
        view.addObject("statements", statements);
        return view;
    }

    public ModelAndView viewAppraisalStep3(HttpServletRequest request, HttpServletResponse response){
        //String strId = request.getParameter("idx");
        //int employeeId = Integer.parseInt(strId);
        Users user = this.getUserPrincipal(request);
        long employeeId = (long)user.getEmployeeId();

        //get parameter to verify if record is for update
        String strIndex = request.getParameter("cbGoal");
        ModelAndView view = viewAppraisalStep3(request, response, String.valueOf(employeeId), strIndex);
        return view;
    }

    private ModelAndView viewAppraisalStep3(HttpServletRequest request, HttpServletResponse response,
            String strEmployeeId, String strCbGoal){
        ModelAndView view = new ModelAndView("appraisal_step3_view");

        int employeeId = Integer.parseInt(strEmployeeId);

        //get parameter to verify if record is for update
        String strIndex = strCbGoal;
        Appraisal.Step3 step3 = new Appraisal.Step3();
        if(strIndex != null && (!strIndex.trim().equals(""))){
            step3 = appraisalService.findAppraisalStep3(Integer.parseInt(strIndex));
        }
        view.addObject("step3", step3);

        //get employee info...
        Employee employee = employeeService.findEmployeeBasicById(employeeId);
        
        view.addObject("rating", appraisalService.findAllAppraisalRating());
        view.addObject("employee", employee);
        view.addObject("result", appraisalService.findAllAppraisalStep3ByID(employeeId));
        
        return view;
    }

    public ModelAndView viewAppraisalStep4(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("appraisal_step4_view");

        //String strId = request.getParameter("idx");
        //int employeeId = Integer.parseInt(strId);
        Users user = this.getUserPrincipal(request);
        long employeeId = (long)user.getEmployeeId();

        //get employee info...
        Employee employee = employeeService.findEmployeeBasicById(employeeId);

        Hashtable<String, Object> summaryHolder = appraisalService.generateRatingSummary((int)employeeId);
        List<Appraisal.RatingSummary> summary = (List<Appraisal.RatingSummary>) summaryHolder.get("summary");
        Appraisal.RatingSummary total = (Appraisal.RatingSummary) summaryHolder.get("total");

        //calculate summary and add as a list to the view (RatingSummary)
        view.addObject("summary", summary);
        view.addObject("total", total);
        
        view.addObject("employee", employee);
        view.addObject("result", appraisalService.findAppraisalStep4((int)employeeId));
        return view;
    }

    public ModelAndView viewAppraisalStep5(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("appraisal_step5_view");

        //String strId = request.getParameter("idx");
        //int employeeId = Integer.parseInt(strId);
        Users user = this.getUserPrincipal(request);
        long employeeId = (long)user.getEmployeeId();

        //get employee info...
        Employee employee = employeeService.findEmployeeBasicById(employeeId);

        view.addObject("employee", employee);
        return view;
    }



    public ModelAndView processCriteriaRequest(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("message");
        String operation = request.getParameter("txtMode");
        //operation mode: N= New, E=Edit
        if(operation.trim().equals("N")){
            AppraisalCriteria appraisalCriteria = new AppraisalCriteria();
            String desc = request.getParameter("txtDesc");
            String note = request.getParameter("txtNote");
            String companyCode = request.getParameter("cbCompany");
            
            appraisalCriteria.setDescription(desc);
            appraisalCriteria.setNote(note);
            appraisalCriteria.setCompanyCode(companyCode);

            appraisalService.updateAppraisalCriteria(appraisalCriteria, appraisalService.MODE_INSERT);
            
            view.addObject("message", "Record Saved Successfully.");
            
        }else if(operation.trim().equals("E")){
            AppraisalCriteria appraisalCriteria = new AppraisalCriteria();
            String desc = request.getParameter("txtDesc");
            String note = request.getParameter("txtNote");
            String companyCode = request.getParameter("cbCompany");
            
            String id = request.getParameter("txtId");
            if(id == null || id.trim().equals("")){
                view = new ModelAndView("error");
                view.addObject("message","Unable to update Criteria information.");
                return view;
            }
            
            appraisalCriteria.setId(Integer.parseInt(id));
            appraisalCriteria.setDescription(desc);
            appraisalCriteria.setNote(note);
            appraisalCriteria.setCompanyCode(companyCode);

            appraisalService.updateAppraisalCriteria(appraisalCriteria, appraisalService.MODE_UPDATE);
            
            view.addObject("message", "Record Saved Successfully.");
        }
        return view;
    }

    public ModelAndView processGroupRequest(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("message");
        String operation = request.getParameter("txtMode");
        //operation mode: N= New, E=Edit
        if(operation.trim().equals("N")){
            AppraisalGroup appraisalGroup = new AppraisalGroup();
            String desc = request.getParameter("txtDesc");
            String note = request.getParameter("txtNote");
            String criteriaId = request.getParameter("cbCriteria");
            String chkJobSpecific = request.getParameter("chkJobSpecific");
            
            if(chkJobSpecific == null){
                chkJobSpecific = "0";
            }
            else{chkJobSpecific ="1";}
            appraisalGroup.setForJobSpecific(Integer.parseInt(chkJobSpecific)); 
            appraisalGroup.setDescription(desc);
            appraisalGroup.setNote(note);
            appraisalGroup.setCriteriaId(Integer.parseInt(criteriaId));

            appraisalService.updateAppraisalGroup(appraisalGroup, appraisalService.MODE_INSERT);

            view = viewAllGroupByCompetence(request, response, Long.parseLong(criteriaId));
            view.addObject("message", "Record Saved Successfully.");

        }else if(operation.trim().equals("E")){
            AppraisalGroup appraisalGroup = new AppraisalGroup();
            String desc = request.getParameter("txtDesc");
            String note = request.getParameter("txtNote");
            String criteriaId = request.getParameter("cbCriteria");
            String chkJobSpecific = request.getParameter("chkJobSpecific");
            String id = request.getParameter("txtId");
            if(id == null || id.trim().equals("")){
                view = new ModelAndView("error");
                view.addObject("message","Unable to update Criteria Group information.");
                return view;
            }
            if(chkJobSpecific == null){
                chkJobSpecific = "0";
            }
            

            appraisalGroup.setForJobSpecific(Integer.parseInt(chkJobSpecific)); 
            appraisalGroup.setId(Integer.parseInt(id));
            appraisalGroup.setDescription(desc);
            appraisalGroup.setNote(note);
            appraisalGroup.setCriteriaId(Integer.parseInt(criteriaId));

            appraisalService.updateAppraisalGroup(appraisalGroup, appraisalService.MODE_UPDATE);

            view = viewAllGroupByCompetence(request, response, Long.parseLong(criteriaId));
            view.addObject("message", "Record Saved Successfully.");
        }
        return view;
    }

    public ModelAndView processFactorRequest(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("message");
        String operation = request.getParameter("txtMode");
        //operation mode: N= New, E=Edit
        if(operation.trim().equals("N")){
            
            String desc = request.getParameter("txtDesc");
            String note = request.getParameter("txtStatement");
            String groupId = request.getParameter("cbGroup");
            String employeeId = request.getParameter("cbEmployee");
            if(employeeId == null)
            {
                
                AppraisalCompetence appraisalCompetence = new AppraisalCompetence();
                appraisalCompetence.setDescription(desc);
                appraisalCompetence.setStatement(note);
                appraisalCompetence.setGroupId(Integer.parseInt(groupId));

                appraisalService.updateAppraisalCompetence(appraisalCompetence, appraisalService.MODE_INSERT);
            }else
            {
               
                EmployeeAppraisalCompetence appraisalCompetence = new EmployeeAppraisalCompetence();
                appraisalCompetence.setDescription(desc);
                appraisalCompetence.setStatement(note);
                appraisalCompetence.setGroupId(Integer.parseInt(groupId));
                appraisalCompetence.setEmployeeId(Integer.parseInt(employeeId));
                appraisalService.updateEmployeeAppraisalCompetence(appraisalCompetence, appraisalService.MODE_INSERT);
            }
            
            

            view = viewAllFactor(request, response, Integer.parseInt(groupId));
            view.addObject("message", "Record Saved Successfully.");
            
        }else if(operation.trim().equals("E")){
            AppraisalCompetence appraisalCompetence = new AppraisalCompetence();
            String desc = request.getParameter("txtDesc");
            String note = request.getParameter("txtStatement");
            String groupId = request.getParameter("cbGroup");

            String id = request.getParameter("txtId");
            if(id == null || id.trim().equals("")){
                view = new ModelAndView("error");
                view.addObject("message","Unable to update Criteria Competence information.");
                return view;
            }

            appraisalCompetence.setId(Integer.parseInt(id));
            appraisalCompetence.setDescription(desc);
            appraisalCompetence.setStatement(note);
            appraisalCompetence.setGroupId(Integer.parseInt(groupId));

            appraisalService.updateAppraisalCompetence(appraisalCompetence, appraisalService.MODE_UPDATE);

            view = viewAllFactor(request, response, Integer.parseInt(groupId));
            view.addObject("message", "Record Saved Successfully.");
        }
        return view;
    }

    public ModelAndView processRatingLanguageRequest(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("message");
        String operation = request.getParameter("txtMode");
        //operation mode: N= New, E=Edit
        if(operation.trim().equals("N")){
            AppraisalRatingLanguage rating = new AppraisalRatingLanguage();
            String index = request.getParameter("txtIndex");
            String text = request.getParameter("txtText");

            rating.setIndex(Integer.parseInt(index));
            rating.setText(text);

            appraisalService.updateAppraisalRating(rating, appraisalService.MODE_INSERT);

            view.addObject("message", "Record Saved Successfully.");

        }else if(operation.trim().equals("E")){
            AppraisalRatingLanguage rating = new AppraisalRatingLanguage();
            String index = request.getParameter("txtIndex");
            String text = request.getParameter("txtText");

            String id = request.getParameter("txtId");
            if(id == null || id.trim().equals("")){
                view = new ModelAndView("error");
                view.addObject("message","Unable to update rating language.");
                return view;
            }

            rating.setIndex(Integer.parseInt(id));
            rating.setText(text);
            rating.setInitIndex(Integer.parseInt(id));

            appraisalService.updateAppraisalRating(rating, appraisalService.MODE_UPDATE);

            view.addObject("message", "Record Saved Successfully.");
        }
        return view;
    }

    public ModelAndView processStep1Request(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("message");
        String operation = request.getParameter("txtMode");
        
        Appraisal.Step1 step = new Appraisal.Step1();
        String index = request.getParameter("txtEnp");
        String startDate = request.getParameter("txtSDate");
        String endDate = request.getParameter("txtEDate");
        String reviewer = request.getParameter("txtReviewer");
        String criteria = request.getParameter("cbCriteria");

        Date _startDate = new Date();
        Date _endDate = new Date();
        try {
            _startDate = DateUtility.getDateFromString(startDate, ConfigReader.DATE_FORMAT);
            _endDate = DateUtility.getDateFromString(endDate, ConfigReader.DATE_FORMAT);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        step.setEmployeeId(Integer.parseInt(index));
        step.setStartDate(_startDate);
        step.setEndDate(_endDate);
        step.setReviewer(reviewer);
        step.setCriteriaId(Integer.parseInt(criteria));

        appraisalService.updateAppraisalStep1(step, appraisalService.MODE_UPDATE);

        view = viewAppraisalStep2(request, response);
        view.addObject("message", "Record Saved Successfully.");

        return view;
    }

    public ModelAndView processStep2Request(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("message");
        String operation = request.getParameter("txtMode");

        List<Appraisal.Step2> step2List = new ArrayList<Appraisal.Step2>();
        String employeeId = request.getParameter("txtEmp");
        //create Hash Set to hold group IDs
        Set<Integer> groupSet = new HashSet<Integer>();

        Enumeration params = request.getParameterNames();
        while(params.hasMoreElements()){
            String parameterName = String.valueOf(params.nextElement());

            if(parameterName.startsWith("rb")){
                String rateIndex = request.getParameter(parameterName);
                String tmpValue = parameterName;
                tmpValue = tmpValue.substring(2);
                String[] splitted = tmpValue.split("-");

                System.out.println(splitted[0] + "***********************" + splitted[1] + ">>>>>" + rateIndex);

                Appraisal.Step2 step = new Appraisal.Step2();
                step.setEmployeeId(Integer.parseInt(employeeId));
                step.setGroupId(Integer.parseInt(splitted[0]));
                step.setCompetenceId(Integer.parseInt(splitted[1]));
                step.setRateIndex(Integer.parseInt(rateIndex));
                step2List.add(step);

                //add group id to set
                groupSet.add(Integer.parseInt(splitted[0]));
                
            }

        }

        List<Appraisal.Step2_Statement> stmtList = new ArrayList<Appraisal.Step2_Statement>();
        for(int grpId : groupSet){
            String strStatement = request.getParameter("txtStmt" + String.valueOf(grpId));
            Appraisal.Step2_Statement appStmt = new Appraisal.Step2_Statement();
            appStmt.setEmployeeId(Integer.parseInt(employeeId));
            appStmt.setGroupId(grpId);
            appStmt.setStatement(strStatement);

            stmtList.add(appStmt);
        }

        //save step 2 records
        appraisalService.updateAppraisalStep2(step2List, stmtList, Integer.parseInt(employeeId)  , appraisalService.MODE_UPDATE);

        //set message for final output
        view = viewAppraisalStep3(request, response);
        view.addObject("message", "Record Saved Successfully.");

        return view;
    }

    public ModelAndView processStep3Request(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("message");
        String operation = request.getParameter("txtMode");

        String saveMode = request.getParameter("sv");
        if(saveMode == null){ saveMode = ""; }

        if(saveMode.trim().equals("next")){
            view = viewAppraisalStep4(request, response);
            return view;
        }

        //operation mode: N= New, E=Edit
        if(operation.trim().equals("N")){
            Appraisal.Step3 step = new Appraisal.Step3();
            String empId = request.getParameter("txtEmp");
            String dueDate = request.getParameter("txtDate");
            String title = request.getParameter("txtTitle");
            String desc = request.getParameter("txtDesc");
            String result = request.getParameter("txtResult");
            String rateIndex = request.getParameter("rbIndex");

            Date _startDate = new Date();
            try {
                _startDate = DateUtility.getDateFromString(dueDate, ConfigReader.DATE_FORMAT);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }

            step.setEmployeeId(Integer.parseInt(empId));
            step.setDueDate(_startDate);
            step.setDescription(desc);
            step.setGoalTitle(title);
            step.setGoalResult(result);
            step.setRateIndex(Integer.parseInt(rateIndex));

            appraisalService.updateAppraisalStep3(step, appraisalService.MODE_INSERT);

            view.addObject("message", "Record Saved Successfully.");
            if(!saveMode.trim().equals("next")){
                view = viewAppraisalStep3(request, response, empId, null);
            }else{
                view = viewAppraisalStep4(request, response);
            }
            return view;
        }else{
            Appraisal.Step3 step = new Appraisal.Step3();
            String empId = request.getParameter("txtEmp");
            String dueDate = request.getParameter("txtDate");
            String title = request.getParameter("txtTitle");
            String desc = request.getParameter("txtDesc");
            String result = request.getParameter("txtResult");
            String rateIndex = request.getParameter("rbIndex");
            System.out.println("desc======"+desc);
            String  strId = request.getParameter("txtId");

            Date _startDate = new Date();
            try {
                _startDate = DateUtility.getDateFromString(dueDate, ConfigReader.DATE_FORMAT);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }

            step.setEmployeeId(Integer.parseInt(empId));
            step.setDueDate(_startDate);
            step.setDescription(desc);
            step.setGoalTitle(title);
            step.setGoalResult(result);
            step.setRateIndex(Integer.parseInt(rateIndex));
            step.setId(Long.parseLong(strId));

            appraisalService.updateAppraisalStep3(step, appraisalService.MODE_UPDATE);

            
            if(!saveMode.trim().equals("next")){
                view = viewAppraisalStep3(request, response, empId, null);
            }else{
                view = viewAppraisalStep4(request, response);
            }

            view.addObject("message", "Record Saved Successfully.");
            return view;
        }
    }

    public ModelAndView processStep4Request(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("message");
        String operation = request.getParameter("txtMode");

        Appraisal.Step4 step = new Appraisal.Step4();
        String index = request.getParameter("txtEmp");
        //String strStmt = request.getParameter("txtStatement");
        String strSmt = request.getParameter("txtStatement");
        String strImprovement = request.getParameter("txtImprovement");
        //String strImprovement = request.getParameter("txtImprovement");
        System.out.println("strStmt ===="+strSmt);
        System.out.println("strImprovement ===="+strImprovement);
        step.setEmployeeId(Integer.parseInt(index));
        step.setStatement(strSmt);
        step.setImprovement(strImprovement);

        appraisalService.updateAppraisalStep4(step, appraisalService.MODE_UPDATE);

        view = viewAppraisalStep5(request, response);
        view.addObject("message", "Record Saved Successfully.");

        return view;
    }

    public ModelAndView processStep5Request(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("message");

        String index = request.getParameter("txtEmp");

        //close up appraisal process
        
        System.out.println("EmployeeId = "+index);
        long transactionID = appraisalService.updateAppraisalStep5(Integer.parseInt(index));
        if(transactionID == 0L){
            view = new ModelAndView("error");
            view.addObject("message", "Unable to complete transaction request.");
            return view;
        }
        long nextApprover = appraisalService.findNextApprovingOfficer(transactionID);
        Employee supervisor = employeeService.findEmployeeBasicById(nextApprover);
        view = new ModelAndView("success");
        view.addObject("message", "Transaction has been forwarded to " + supervisor.getFullName() + " for approval.");

        return view;
    }
    
    public ModelAndView viewTransactionStep1(HttpServletRequest request, HttpServletResponse response){
        String trnxId = request.getParameter("txtTranx");

        Transaction transaction = transactionService.findTransactionById(Long.parseLong(trnxId));
        Users user = this.getUserPrincipal(request);
        
        return getAppraisalView(transaction, user.getEmployeeId());
    }
    private ModelAndView getAppraisalView(Transaction transaction, long supervisor){
        ModelAndView view = new ModelAndView("transaction/appraisal_step1_view");

        Appraisal appraisal = appraisalService.findAppraisalByTransaction(transaction.getId());
        //get step 1 by appraisal id
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

    public ModelAndView processTransactionStep1Request(HttpServletRequest request, HttpServletResponse response){
        return viewTransactionStep2(request, response);
    }
    public ModelAndView viewTransactionStep2(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("transaction/appraisal_step2_view");

        Users user = this.getUserPrincipal(request);
        long employeeId = (long)user.getEmployeeId();

        String trnxId = request.getParameter("txtTranx");
        Transaction transaction = transactionService.findTransactionById(Long.parseLong(trnxId));

        Appraisal appraisal = appraisalService.findAppraisalByTransaction(transaction.getId());
        //get step 1 info... this will enable us get some data based on appraisal criteria
        AppraisalInformation.Step1 step1 = appraisalService.findTransactionAppraisalStep1ByID(appraisal.getId());

        //get employee info..
        Employee initiator = employeeService.findEmployeeBasicById(transaction.getInitiator());

        //generate the List of objects to create competence on the page
        List<Appraisal.step2Competence> competencies = appraisalService.generateTransactionStep2Competencies(appraisal.getId(),transaction.getInitiator());
        List<Appraisal.Step2_Statement> statements = appraisalService.findAppraisalStep2StatementByID(initiator.getEmployeeID(), appraisal.getTransactionId());

        //set object in view
        view.addObject("rating", appraisalService.findAllAppraisalRating());
        view.addObject("employee", initiator);  
        view.addObject("result", appraisalService.findAppraisalStep2ByID(initiator.getEmployeeNumber().intValue()));
        view.addObject("competencies", competencies);
        view.addObject("statements", statements);
        view.addObject("transaction", transaction);
        return view;
    }

    public ModelAndView processTransactionStep2Request(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("message");

        List<Appraisal.Step2> step2List = new ArrayList<Appraisal.Step2>();

        //Users user = this.getUserPrincipal(request);
       
       // long employeeId = (long)user.getEmployeeId();
        String EmployId = request.getParameter("txtEmp"); 
        
        int employeeId = Integer.parseInt(EmployId);
        //get transaction
        String trnxId = request.getParameter("txtTranx");
        Transaction transaction = transactionService.findTransactionById(Long.parseLong(trnxId));

        //create Hash Set to hold group IDs
        Set<Integer> groupSet = new HashSet<Integer>();

        Enumeration params = request.getParameterNames();
        while(params.hasMoreElements()){
            String parameterName = String.valueOf(params.nextElement());
            
            if(parameterName.startsWith("rb")){
                String rateIndex = request.getParameter(parameterName);
                String tmpValue = parameterName;
                tmpValue = tmpValue.substring(2);
                String[] splitted = tmpValue.split("-");

              

                Appraisal.Step2 step = new Appraisal.Step2();
                step.setEmployeeId((int)employeeId);
                step.setGroupId(Integer.parseInt(splitted[0]));
                step.setCompetenceId(Integer.parseInt(splitted[1]));
                step.setRateIndex(Integer.parseInt(rateIndex));
                step.setTransactionId(transaction.getId());
                step.setIsTransaction(1);
                step2List.add(step);

                //add group id to set
                groupSet.add(Integer.parseInt(splitted[0]));

            }

        }

        List<Appraisal.Step2_Statement> stmtList = new ArrayList<Appraisal.Step2_Statement>();
        for(int grpId : groupSet){
            String strStatement = request.getParameter("txtStmt" + String.valueOf(grpId));
            Appraisal.Step2_Statement appStmt = new Appraisal.Step2_Statement();
           
            appStmt.setEmployeeId((int)employeeId);
            appStmt.setGroupId(grpId);
            appStmt.setStatement(strStatement);
            appStmt.setTransactionId(transaction.getId());
            appStmt.setIsTransaction(1);
            stmtList.add(appStmt);
        }

        //save step 2 records
        appraisalService.updateAppraisalStep2(step2List, stmtList, (int)employeeId  , appraisalService.MODE_UPDATE);

        //set message for final output
        view = viewTransactionStep3(request, response);
        view.addObject("message", "Record Saved Successfully.");

        return view;
    }

    public ModelAndView viewTransactionStep3(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("transaction/appraisal_step3_view");

        //Users user = this.getUserPrincipal(request);
        //long employeeId = (long)user.getEmployeeId();
        String EmployId = request.getParameter("txtEmp"); 
       
        long employeeId = Long.parseLong(EmployId);
        
        String trnxId = request.getParameter("txtTranx");

        //get parameter to verify if record is for update
        String strIndex = request.getParameter("cbGoal");
        Transaction transaction = transactionService.findTransactionById(Long.parseLong(trnxId));

        return viewTransactionStep3(request, response, employeeId, strIndex, transaction);
    }

    private ModelAndView viewTransactionStep3(HttpServletRequest request, HttpServletResponse response,
            long employeeId, String strCbGoal, Transaction transaction){
        ModelAndView view = new ModelAndView("transaction/appraisal_step3_view");

        //int employeeId = Integer.parseInt(strEmployeeId);

        //get parameter to verify if record is for update
        String strIndex = strCbGoal;
        Appraisal.Step3 step3 = new Appraisal.Step3();
        if(strIndex != null && (!strIndex.trim().equals(""))){
            step3 = appraisalService.findAppraisalStep3(Integer.parseInt(strIndex));
        }
        view.addObject("step3", step3);

        //get employee info...
        Employee initiator = employeeService.findEmployeeBasicById(transaction.getInitiator());

        view.addObject("rating", appraisalService.findAllAppraisalRating());
        view.addObject("employee", initiator);
        view.addObject("result", appraisalService.findAllAppraisalStep3ByID((int)employeeId, transaction.getId()));
        view.addObject("transaction", transaction);

        return view;
    }

    public ModelAndView processTransactionStep3Request(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("message");
        String operation = request.getParameter("txtMode");

        String saveMode = request.getParameter("sv");
        if(saveMode == null){ saveMode = ""; }

        //Users user = this.getUserPrincipal(request);
       // long employeeId = (long)user.getEmployeeId();
        String EmployId = request.getParameter("txtEmp"); 
        int employeeId = Integer.parseInt(EmployId);
        
        //get transaction
        String trnxId = request.getParameter("txtTranx");
        Transaction transaction = transactionService.findTransactionById(Long.parseLong(trnxId));

        //CONFIRM IF USER REQUEST IS NEXT LEVEL
        if(saveMode.trim().equals("next")){
            request.setAttribute("txtEmp",EmployId);
                    
            view = viewTransactionStep4(request, response);
        }

        //operation mode: N= New, E=Edit
        if(operation.trim().equals("N")){
            Appraisal.Step3 step = new Appraisal.Step3();
            String empId = request.getParameter("txtEmp");
            String dueDate = request.getParameter("txtDate");
            String title = request.getParameter("txtTitle");
            String desc = request.getParameter("txtDesc");
            String result = request.getParameter("txtResult");
            String rateIndex = request.getParameter("rbIndex");

            Date _startDate = new Date();
            try {
                _startDate = DateUtility.getDateFromString(dueDate, ConfigReader.DATE_FORMAT);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }

            step.setEmployeeId((int)employeeId);
            step.setDueDate(_startDate);
            step.setDescription(desc);
            step.setGoalTitle(title);
            step.setGoalResult(result);
            step.setRateIndex(Integer.parseInt(rateIndex));
            step.setTransactionId(transaction.getId());
            step.setIsTransaction(1);

            appraisalService.updateAppraisalStep3(step, appraisalService.MODE_INSERT);

            view.addObject("message", "Record Saved Successfully.");
            if(!saveMode.trim().equals("next")){
                view = viewTransactionStep3(request, response, transaction.getInitiator(), null, transaction);
            }else{
                request.setAttribute("txtEmp",EmployId);
                view = viewTransactionStep4(request, response);
            }
            return view;
        }else{
            Appraisal.Step3 step = new Appraisal.Step3();
            String empId = request.getParameter("txtEmp");
          
            String dueDate = request.getParameter("txtDate");
            String title = request.getParameter("txtTitle");
            String desc = request.getParameter("txtDesc");
            String result = request.getParameter("txtResult");
            String rateIndex = request.getParameter("rbIndex");

            String  strId = request.getParameter("txtId");

            Date _startDate = new Date();
            try {
                _startDate = DateUtility.getDateFromString(dueDate, ConfigReader.DATE_FORMAT);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }

            step.setEmployeeId((int)employeeId);
            step.setDueDate(_startDate);
            step.setDescription(desc);
            step.setGoalTitle(title);
            step.setGoalResult(result);
            step.setRateIndex(Integer.parseInt(rateIndex));
            step.setTransactionId(transaction.getId());
            step.setIsTransaction(1);
            step.setId(Long.parseLong(strId));

            appraisalService.updateAppraisalStep3(step, appraisalService.MODE_UPDATE);


            if(!saveMode.trim().equals("next")){
                view = viewTransactionStep3(request, response, transaction.getInitiator(), null, transaction);
            }else{
                request.setAttribute("txtEmp",EmployId);
                view = viewTransactionStep4(request, response);
            }

            view.addObject("message", "Record Saved Successfully.");
            return view;
        }
    }

    public ModelAndView viewTransactionStep4(HttpServletRequest request, HttpServletResponse response){
            ModelAndView view = new ModelAndView("transaction/appraisal_step4_view");

            //Users user = this.getUserPrincipal(request);
            //long employeeId = (long)user.getEmployeeId();
         
            //get transaction id to use for operation
            String trnxId = request.getParameter("txtTranx");
           

            Transaction transaction = transactionService.findTransactionById(Long.parseLong(trnxId));
            //get employee info...
            Employee initiator = employeeService.findEmployeeBasicById(transaction.getInitiator());

            Hashtable<String, Object> summaryHolder = appraisalService.generateRatingSummary(initiator.getEmployeeNumber(), transaction.getId());
            List<Appraisal.RatingSummary> summary = (List<Appraisal.RatingSummary>) summaryHolder.get("summary");
            Appraisal.RatingSummary total = (Appraisal.RatingSummary) summaryHolder.get("total");

            //calculate summary and add as a list to the view (RatingSummary)
            view.addObject("summary", summary);
            view.addObject("total", total);
            System.out.println("initiator.getEmployeeNumber()>>>>>>>>>> "+initiator.getEmployeeNumber());
            view.addObject("employee", initiator);
            view.addObject("result", appraisalService.findAppraisalStep4(initiator.getEmployeeNumber().intValue()));
            return view;
    }

    public ModelAndView processTransactionStep4Request(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("message");

        //get User's information
        //Users user = this.getUserPrincipal(request);
        //long employeeId = (long)user.getEmployeeId();
        String EmployId = request.getParameter("txtEmp"); 
        int employeeId = Integer.parseInt(EmployId);

        //get transaction
        String trnxId = request.getParameter("txtTranx");
        Transaction transaction = transactionService.findTransactionById(Long.parseLong(trnxId));

        Appraisal.Step4 step = new Appraisal.Step4();
        //String index = request.getParameter("txtEmp");
        String strStmt = request.getParameter("txtStatement");
        String strImprovement = request.getParameter("txtImprovement");

        step.setEmployeeId((int)employeeId);
        step.setStatement(strStmt);
        step.setImprovement(strImprovement);
        step.setTransactionId(transaction.getId());
        step.setIsTransaction(1);

        appraisalService.updateAppraisalStep4(step, appraisalService.MODE_UPDATE);

        view = viewTransactionStep5(request, response);
        view.addObject("message", "Record Saved Successfully.");

        return view;
    }

    public ModelAndView viewTransactionStep5(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("transaction/appraisal_step5_view");

        //String strId = request.getParameter("idx");
        //int employeeId = Integer.parseInt(strId);
        Users user = this.getUserPrincipal(request);
        long employeeId = (long)user.getEmployeeId();


        //get transaction id to use for operation
        String trnxId = request.getParameter("txtTranx");
        Transaction transaction = transactionService.findTransactionById(Long.parseLong(trnxId));
        //get employee info...
        Employee initiator = employeeService.findEmployeeBasicById(transaction.getInitiator());
        view.addObject("transaction", transaction);
        view.addObject("employee", initiator);
        return view;
    }

    public ModelAndView processTransactionStep5Request(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("message");

        //get User's information
        Users user = this.getUserPrincipal(request);
        long employeeId = (long)user.getEmployeeId();
        
      

        //get transaction
        String trnxId = request.getParameter("txtTranx");
        
        Transaction transaction = transactionService.findTransactionById(Long.parseLong(trnxId));

        view = approveAppraisalTransaction(request, transaction, user);

        return view;
    }

    public ModelAndView approveTransactionRequest(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("message");

        //get User's information
        Users user = this.getUserPrincipal(request);
        long employeeId = (long)user.getEmployeeId();


        //get transaction
        String trnxId = request.getParameter("txtTranx");
        Transaction transaction = transactionService.findTransactionById(Long.parseLong(trnxId));
        
        view = approveAppraisalTransaction(request, transaction, user);

        return view;
    }

    public ModelAndView approveAppraisalTransaction(HttpServletRequest request, Transaction transaction, Users supervisor){
        ModelAndView view = new ModelAndView("message");
        //get the exact action
        String action = request.getParameter("txtAction");
        if(action == null){
            view = new ModelAndView("error");
            view.addObject("message", "Unable to determine the action to take on transaction");
            return view;
        }
        //confirm the action
        if((!action.trim().equals("A")) && (!action.trim().equals("R"))){
            view = new ModelAndView("error");
            view.addObject("message", "Unable to determine the exact action to take on transaction");
            return view;
        }

        //set the new action that will determine the route to use
        transaction.setStatus(action);
        //call appropriate method
        try{
            boolean saved = transactionService.processTransaction(transaction, supervisor.getEmployeeId());
            if(saved == false){
                throw new Exception("Unable to complete transaction.");
            }
            long nextAuthorizer = transactionService.findNextApprovingOfficer(transaction.getId());
            if(nextAuthorizer == 0){
                if(transaction.getStatus().trim().equals("R")){
                    view.addObject("message", "Appraisal Transaction Successfully Rejected.");
                }else{
                    view.addObject("message", "Appraisal Transaction Successfully Approved.");
                }
            }else{
                Employee nextSupervisor = employeeService.findEmployeeBasicById(nextAuthorizer);
                view.addObject("message", "Transaction has been forwarded to " + nextSupervisor.getFullName() + " for approval.");
            }
            return view;

        }catch(Exception e){
            e.printStackTrace();
            view = new ModelAndView("error");
            view.addObject("message", "Error Processing Transaction. " + e.getMessage());
            return view;
        }
        
    }

    public ModelAndView viewAuthorizedAppraisalSteps(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("transaction/appraisal_step_view");

        //String strId = request.getParameter("idx");
        //int employeeId = Integer.parseInt(strId);
        Users user = this.getUserPrincipal(request);
        long employeeId = (long)user.getEmployeeId();


        //get transaction id to use for operation
        String trnxId = request.getParameter("txtTranx");
        Transaction transaction = transactionService.findTransactionById(Long.parseLong(trnxId));
        Appraisal appraisal = appraisalService.findAppraisalByTransaction(transaction.getId());

        //get steps based on appraisal id
        AppraisalInformation.Step1 step1 = appraisalService.findApproveAppraisalStep1View(appraisal.getId());
        List<AppraisalInformation.Step2> step2 = appraisalService.findApproveAppraisalStep2View(appraisal.getId());
        List<AppraisalInformation.Step2_Statement> step2_Statement = appraisalService.findApproveAppraisalStep2StatementView(appraisal.getId());
        List<AppraisalInformation.Step3> step3 = appraisalService.findApproveAppraisalStep3View(appraisal.getId());
        List<AppraisalInformation.Step4> step4 = appraisalService.findApproveAppraisalStep4View(appraisal.getId());

        //get list of step2 supervisors
        List<Long> step2Supervisors = new ArrayList<Long>();
        for(AppraisalInformation.Step2 step2Info : step2){
            if(!step2Supervisors.contains(step2Info.getSupervisor())){
                step2Supervisors.add(step2Info.getSupervisor());
            }
        }

        //set steps in view
        view.addObject("step1", step1);
        view.addObject("step2", step2);
        view.addObject("step2_stmt", step2_Statement);
        view.addObject("supervisors", step2Supervisors);

        view.addObject("step3", step3);
        view.addObject("step4", step4);

        
        return view;
    }

         public ModelAndView processUploadRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

        ModelAndView view = new ModelAndView("appraisal_competence_upload_view");
        //get user information
        Users user = this.getUserPrincipal(request);

        if (!(request instanceof MultipartHttpServletRequest)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Expected multipart request");
            return null;
        }
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        String fileType = request.getParameter("cbFileType");
        String criteriaId = request.getParameter("cbCriteria");
        String criteriaText = request.getParameter("criteriaText");
        String groupId = request.getParameter("cbGroup");
        String groupText = request.getParameter("groupText");
        String employeeId = request.getParameter("cbEmployee");
        String employeeName = request.getParameter("employeeName");
        System.out.println("employeeId="+employeeId);
        System.out.println("employeeName="+employeeName);
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("txtFile");

        org.tenece.web.data.FileUpload fileUpload = new FileUpload();
        fileUpload.setFileName(file.getName());
        try {
            fileUpload.setBytes(file.getBytes());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
        fileUpload.setSize(file.getSize());
        fileUpload.setContentType(file.getContentType());
        fileUpload.setOrginalFileName(file.getOriginalFilename());

        //save file in archive
        File destination = new File("./" + file.getOriginalFilename());
        fileUpload.setAbsolutePath(destination.getAbsolutePath());
        try {
            file.transferTo(destination);
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        //process file
        try {
            System.out.println("CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC");
            List<EmployeeAppraisalCompetence> applist = appraisalService.saveUploadedUserlistFile(fileUpload, fileType, criteriaText,groupText,employeeName);
            System.out.println("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
            HttpSession session = request.getSession(false);
            String strId = user.getEmployeeId() + "_" + org.tenece.web.common.DateUtility.getDateAsString(new Date(), "ddMMyyyyHHmmss");
            System.out.println("EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
            session.setAttribute(strId, applist);

            System.out.println("Number of uploaded records: " + applist.size());
            view.addObject("result", applist);
            view.addObject("idx", strId);
            view.addObject("criteriaId", criteriaId);
            view.addObject("groupId", groupId);
            view.addObject("employeeId", employeeId);

        } catch (Exception e) {
            view = viewAppraisalUpload(request, response);
            view.addObject("message", e.getMessage());
        }

        return view;
    }

   

    public ModelAndView viewAppraisalUpload(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("upload_appraisal_competence");
        view.addObject("cbCriteriaList", appraisalService.findAllAppraisalCriteria());
        return view;
    }

    public SetupService getSetupService() {
        return setupService;
    }

    public void setSetupService(SetupService setupService) {
        this.setupService = setupService;
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
    
}
