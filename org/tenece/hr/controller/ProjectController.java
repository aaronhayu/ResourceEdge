
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
import org.tenece.web.data.Department;
import org.tenece.web.data.Employee;
import org.tenece.web.data.Project;
import org.tenece.web.data.ProjectGroup;
import org.tenece.web.data.ProjectUtilization;
import org.tenece.web.data.Users;
import org.tenece.web.services.EmployeeService;
import org.tenece.web.services.ProjectService;
import org.tenece.web.services.SetupService;

/**
 *
 * @author jeffry.amachree
 */
public class ProjectController extends BaseController{
    
    private ProjectService projectService = new ProjectService();
    private EmployeeService employeeService = new EmployeeService();
    
    /** Creates a new instance of DepartmentController */
    public ProjectController() {
    }
    
    public ModelAndView viewAllProjectGroup(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("project_group_view");
        
        List<ProjectGroup> list = null;

        if(this.getUserPrincipal(request).getGroupCompanyUser() == 1){
           list =  projectService.findAllProjectGroup();
            view.addObject("showCompany", "Y");
        }else{
            Employee employee = this.getEmployeeService().findEmployeeBasicById(getUserPrincipal(request).getEmployeeId());
            list = projectService.findAllProjectGroupByCompany(employee.getCompanyCode());
        }

        view.addObject("result", list);
        return view;
    }
    
    public ModelAndView editProjectGroup(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("project_group_edit");
        String param = request.getParameter("idx");
        if(param == null){
            return viewAllProjectGroup(request, response);
        }
        int id = Integer.parseInt(param);
        ProjectGroup project = projectService.findProjectGroupById(id);
        
        view.addObject("project", project);

        //get company info
        view = getAndAppendCompanyToView(view, request);
        
        return view;
    }
    
    public ModelAndView newProjectGroup(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("project_group_edit");
        
        view.addObject("project", new ProjectGroup());

        //get company info
        view = getAndAppendCompanyToView(view, request);

        return view;
    }
    
    public ModelAndView deleteProjectGroup(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("");
        List<Long> ids = new ArrayList<Long>();
        //get mode of request
        String mode = request.getParameter("mode");
        //check if mode is for more than one record
        //get mode, if mode is equals 1, then process for multiple
        if(mode != null && mode.trim().equals("1")){
            String[] args = request.getParameterValues("_chk");
            for(String id : args){
                ids.add(Long.parseLong(id));
            }
        }else{//then it is zero
            ids.add(Long.parseLong(request.getParameter("id")));
        }
        projectService.deleteProjectGroup(ids);
       //delete
        return viewAllProjectGroup(request, response);
    }
    
    public ModelAndView processProjectGroupRequest(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("message");
        String operation = request.getParameter("txtMode");
        //operation mode: N= New, E=Edit
        if(operation.trim().equals("N")){
            ProjectGroup project = new ProjectGroup();
            String code = request.getParameter("txtCode");
            String desc = request.getParameter("txtDesc");
            String companyCode = request.getParameter("cbCompany");
            
            project.setCode(code);
            project.setDescription(desc);
            project.setCompanyCode(companyCode);
            
            boolean saved = projectService.updateProjectGroup(project, ProjectService.MODE_INSERT);
            if(saved == false){
                view = new ModelAndView("error");
                view.addObject("message", "Error processing project group request. Please try again later");
                return view;
            }

            view.addObject("message", "Record Saved Successfully.");
            
        }else if(operation.trim().equals("E")){
            ProjectGroup project = new ProjectGroup();
            String code = request.getParameter("txtCode");
            String desc = request.getParameter("txtDesc");
            String companyCode = request.getParameter("cbCompany");
            
            String projectId = request.getParameter("txtId");
            
            if(projectId == null || projectId.trim().equals("")){
                view = new ModelAndView("error");
                view.addObject("message","Unable to complete update.");
                return view;
            }
            project.setId(Long.parseLong(projectId));
            project.setCode(code);
            project.setDescription(desc);
            project.setCompanyCode(companyCode);
            
            boolean saved = projectService.updateProjectGroup(project, projectService.MODE_UPDATE);
            if(saved == false){
                view = new ModelAndView("error");
                view.addObject("message", "Error processing project group request. Please try again later");
                return view;
            }
            
            view.addObject("message", "Record Saved Successfully.");
        }
        return view;
    }

    public ModelAndView viewAllProject(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("project_view");

        List<Project> list = null;//projectService.findAllProject();

        if(this.getUserPrincipal(request).getGroupCompanyUser() == 1){
            list = projectService.findAllProject();
            view.addObject("showCompany", "Y");
        }else{
            Employee employee = this.getEmployeeService().findEmployeeBasicById(getUserPrincipal(request).getEmployeeId());
            list = projectService.findAllProjectByCompany(employee.getCompanyCode());
        }

        view.addObject("result", list);
        return view;
    }

    public ModelAndView editProject(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("project_edit");
        String param = request.getParameter("idx");

        String sentCompanyCode = "";
        if(this.getUserPrincipal(request).getGroupCompanyUser() == 1){
            sentCompanyCode = request.getParameter("cbCompany");
            sentCompanyCode = sentCompanyCode == null ? "" : sentCompanyCode;
        }else{
            Employee employee = this.getEmployeeService().findEmployeeBasicById(getUserPrincipal(request).getEmployeeId());
            sentCompanyCode = employee.getCompanyCode();
        }

        if(param == null){
            return viewAllProject(request, response);
        }
        int id = Integer.parseInt(param);
        Project project = projectService.findProjectById(id);
        //set company code to the selected value
        if(!sentCompanyCode.trim().equals("")){
            project.setCompanyCode(sentCompanyCode);
        }else{
            sentCompanyCode = project.getCompanyCode();
        }

        view.addObject("project", project);
        view.addObject("projectGroupList", projectService.findAllProjectGroupByCompany(sentCompanyCode));

        //get company info
        view = getAndAppendCompanyToView(view, request);

        return view;
    }

    public ModelAndView newProject(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("project_edit");

        String sentCompanyCode = "";
        if(this.getUserPrincipal(request).getGroupCompanyUser() == 1){
            sentCompanyCode = request.getParameter("cbCompany");
            sentCompanyCode = sentCompanyCode == null ? "" : sentCompanyCode;
        }else{
            Employee employee = this.getEmployeeService().findEmployeeBasicById(getUserPrincipal(request).getEmployeeId());
            sentCompanyCode = employee.getCompanyCode();
        }

        Project project = new Project();
        project.setCompanyCode(sentCompanyCode);
        
        view.addObject("project", project);
        view.addObject("projectGroupList", projectService.findAllProjectGroupByCompany(sentCompanyCode));

        //get company info
        view = getAndAppendCompanyToView(view, request);

        return view;
    }

    public ModelAndView deleteProject(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("");
        List<Long> ids = new ArrayList<Long>();
        //get mode of request
        String mode = request.getParameter("mode");
        //check if mode is for more than one record
        //get mode, if mode is equals 1, then process for multiple
        if(mode != null && mode.trim().equals("1")){
            String[] args = request.getParameterValues("_chk");
            for(String id : args){
                ids.add(Long.parseLong(id));
            }
        }else{//then it is zero
            ids.add(Long.parseLong(request.getParameter("id")));
        }
        projectService.deleteProject(ids);
       //delete
        return viewAllProject(request, response);
    }

    public ModelAndView processProjectRequest(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("message");
        String operation = request.getParameter("txtMode");
        //operation mode: N= New, E=Edit
        if(operation.trim().equals("N")){
            Project project = new Project();
            String cbProject = request.getParameter("cbProject");
            String chkBillable = request.getParameter("chkBillable");
            String code = request.getParameter("txtCode");
            String desc = request.getParameter("txtDesc");
            String companyCode = request.getParameter("cbCompany");
            //check if checkedbox was selected
            if(chkBillable == null || chkBillable.trim().equals("")){
                chkBillable = "0";
            }
            
            project.setCode(code);
            project.setDescription(desc);
            project.setProjectGroupId(Long.parseLong(cbProject));
            project.setBillable(Integer.parseInt(chkBillable));
            project.setCompanyCode(companyCode);

            boolean saved = projectService.updateProject(project, ProjectService.MODE_INSERT);
            if(saved == false){
                view = new ModelAndView("error");
                view.addObject("message", "Error processing project defination request. Please try again later");
                return view;
            }
            
            view.addObject("message", "Record Saved Successfully.");

        }else if(operation.trim().equals("E")){
            Project project = new Project();
            String cbProject = request.getParameter("cbProject");
            String chkBillable = request.getParameter("chkBillable");
            String code = request.getParameter("txtCode");
            String desc = request.getParameter("txtDesc");
            String companyCode = request.getParameter("cbCompany");
            //check if checkedbox was selected
            if(chkBillable == null || chkBillable.trim().equals("")){
                chkBillable = "0";
            }

            String projectId = request.getParameter("txtId");

            if(projectId == null || projectId.trim().equals("")){
                view = new ModelAndView("error");
                view.addObject("message","Unable to complete update.");
                return view;
            }
            project.setId(Long.parseLong(projectId));
            project.setCode(code);
            project.setDescription(desc);
            project.setProjectGroupId(Long.parseLong(cbProject));
            project.setBillable(Integer.parseInt(chkBillable));
            project.setCompanyCode(companyCode);

            boolean saved = projectService.updateProject(project, projectService.MODE_UPDATE);
            if(saved == false){
                view = new ModelAndView("error");
                view.addObject("message", "Error processing project defination request. Please try again later");
                return view;
            }

            view.addObject("message", "Record Saved Successfully.");
        }
        return view;
    }

    public ModelAndView viewAllProjectUtilization(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("project_util_view");

        //get user profile
        Users user = this.getUserPrincipal(request);
        if(user == null){
            view = new ModelAndView("logout");
            return view;
        }
        //get all pending for the current user
        List<ProjectUtilization> list = projectService.findAllProjectUtilization(user.getEmployeeId());
        view.addObject("result", list);
        return view;
    }

    public ModelAndView editProjectUtilization(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("project_util_edit");
        Users user = this.getUserPrincipal(request);
        if(user == null){
            view = new ModelAndView("logout");
            return view;
        }
        String param = request.getParameter("idx");
        if(param == null){
            return viewAllProject(request, response);
        }
        int id = Integer.parseInt(param);
        ProjectUtilization projectUtil = projectService.findProjectUtilizationById(id, user.getEmployeeId());

        //find project by ID and set in a list
        Project project = projectService.findProjectById(projectUtil.getProjectId());
        List<Project> projectList = new ArrayList<Project>();
        projectList.add(project);

        view.addObject("project", projectUtil);
        view.addObject("projectList", projectList);
        view.addObject("employee", employeeService.findEmployeeBasicById(user.getEmployeeId()));
        return view;
    }

    public ModelAndView newProjectUtilization(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("project_util_edit");
        //get user profile
        Users user = this.getUserPrincipal(request);
        if(user == null){
            view = new ModelAndView("logout");
            return view;
        }

        String companyCode = this.getActiveEmployeeCompanyCode(request);

        view.addObject("project", new ProjectUtilization());
        view.addObject("projectList", projectService.findAllProjectByCompany(companyCode));
        view.addObject("employee", employeeService.findEmployeeBasicById(user.getEmployeeId()));
        return view;
    }

    public ModelAndView deleteProjectUtilization(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("");
        List<Long> ids = new ArrayList<Long>();
        //get mode of request
        String mode = request.getParameter("mode");
        //check if mode is for more than one record
        //get mode, if mode is equals 1, then process for multiple
        if(mode != null && mode.trim().equals("1")){
            String[] args = request.getParameterValues("_chk");

            for(String id : args){
                System.out.println(">>>>>>>>>>:" + Long.parseLong(id) + "'");
                long _id = Long.parseLong(id);
                ids.add(_id);
            }
        }else{//then it is zero
            ids.add(Long.parseLong(request.getParameter("id")));
        }
        projectService.deleteProjectUtilization(ids);
       //delete
        return viewAllProjectUtilization(request, response);
    }

    public ModelAndView closeAllProjectUtilization(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("project_close_view");

        String companyCode = this.getActiveEmployeeCompanyCode(request);

        List<ProjectUtilization> list = projectService.findAllPendingProjectUtilizationSummaryByCompany("P", companyCode);
        view.addObject("result", list);
        return view;
    }
    
    public ModelAndView processCloseProjectUtilization(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("success");

        List<Long> ids = new ArrayList<Long>();
        String[] args = request.getParameterValues("_chk");

        if(args == null){
            view = closeAllProjectUtilization(request, response);
            view.addObject("message","Please select atleast one project to approve.");
            return view;
        }
        for(String id : args){
            System.out.println(">>>>>>>>>>:" + Long.parseLong(id) + "'");
            long _id = Long.parseLong(id);
            ids.add(_id);
        }
        int affected = projectService.approveProjectUtilizations(ids);

        view = closeAllProjectUtilization(request, response);
        if(affected == 0){
            view.addObject("message","Unable to approve selected projects utilization.");
        }else{
            view.addObject("message","Operation saved successfully.");
        }
        return view;
    }

    public ModelAndView processProjectUtilizationRequest(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("message");
        String operation = request.getParameter("txtMode");
        Users user = this.getUserPrincipal(request);
        if(user == null){
            view = new ModelAndView("logout");
            return view;
        }
        //operation mode: N= New, E=Edit
        if(operation.trim().equals("N")){
            ProjectUtilization project = new ProjectUtilization();
            String cbProject = request.getParameter("cbProject");
            String hours = request.getParameter("txtHours");
            String strDate = request.getParameter("txtDate");

            Date effectiveDate = new Date();
            try {
                effectiveDate = DateUtility.getDateFromString(strDate, ConfigReader.DATE_FORMAT);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }

            project.setProjectId(Long.parseLong(cbProject));
            project.setEmployeeId(user.getEmployeeId());
            project.setHours(Integer.parseInt(hours));
            project.setActiveDate(effectiveDate);
            project.seteStatus("P");

            //check if the project exist for the selected date
            int counted = projectService.checkProjectByDate(effectiveDate, Long.parseLong(cbProject), user.getEmployeeId());
            if(counted > 0){
                view = new ModelAndView("error");
                try{
                    view.addObject("message", "Project already exist for the specified date '" + DateUtility.getDateAsString(effectiveDate, "dd/MM/yyyy") + "'");
                }catch(Exception er){}
                return view;
            }
            int totalHours = projectService.checkTotalHoursByDate(effectiveDate, project.getEmployeeId());
            if((totalHours + Integer.parseInt(hours)) > 24){
                view = new ModelAndView("error");
                try{
                    view.addObject("message", "Total number of utilized time exceed 24 Hours for the specified date '" + DateUtility.getDateAsString(effectiveDate, "dd/MM/yyyy") + "'");
                }catch(Exception er){}
                return view;
            }

            boolean saved = projectService.updateProjectUtilization(project, ProjectService.MODE_INSERT);
            if(saved == false){
                view = new ModelAndView("error");
                view.addObject("message", "Error processing project utilization request. Please try again later");
                return view;
            }

            view.addObject("message", "Record Saved Successfully.");

        }else if(operation.trim().equals("E")){
            ProjectUtilization project = new ProjectUtilization();
            String cbProject = request.getParameter("cbProject");
            String hours = request.getParameter("txtHours");
            String strDate = request.getParameter("txtDate");

            String oldHours = request.getParameter("txtHHours");
            if(oldHours == null || oldHours.trim().equals("")){
                oldHours = "0";
            }
            int iHours = Integer.parseInt(oldHours);

            Date effectiveDate = new Date();
            try {
                effectiveDate = DateUtility.getDateFromString(strDate, ConfigReader.DATE_FORMAT);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }

            String projectId = request.getParameter("txtId");

            if(projectId == null || projectId.trim().equals("")){
                view = new ModelAndView("error");
                view.addObject("message","Unable to complete update.");
                return view;
            }
            project.setId(Long.parseLong(projectId));
            project.setProjectId(Long.parseLong(cbProject));
            project.setEmployeeId(user.getEmployeeId());
            project.setHours(Integer.parseInt(hours));
            project.setActiveDate(effectiveDate);
            project.seteStatus("P");

            int totalHours = projectService.checkTotalHoursByDate(effectiveDate, project.getEmployeeId());
            if(((totalHours - iHours) + Integer.parseInt(hours)) > 24){
                view = new ModelAndView("error");
                try{
                    view.addObject("message", "Total number of utilized time exceed 24 Hours for the specified date '" + DateUtility.getDateAsString(effectiveDate, "dd/MM/yyyy") + "'");
                }catch(Exception er){}
                return view;
            }

            boolean saved = projectService.updateProjectUtilization(project, ProjectService.MODE_UPDATE);
            if(saved == false){
                view = new ModelAndView("error");
                view.addObject("message", "Error processing project utilization request. Please try again later");
                return view;
            }
            
            view.addObject("message", "Record Saved Successfully.");
        }
        return view;
    }

    /**
     * @return the projectService
     */
    public ProjectService getProjectService() {
        return projectService;
    }

    /**
     * @param projectService the projectService to set
     */
    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
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
