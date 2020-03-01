/*
 * (c) Copyright 2010 The Tenece Professional Services.
 *
 * Created on 6 February 2009, 09:57
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
import org.tenece.web.data.NoticeBoard;
import org.tenece.web.services.EmployeeService;
import org.tenece.web.services.NoticeBoardService;
import org.tenece.web.services.SetupService;

/**
 * Tenece Professional Services Limited
 * @author amachree
 */
public class NoticeBoardController extends BaseController{
    private NoticeBoardService noticeBoardService;
    private SetupService setupService;
    private EmployeeService employeeService;

    public ModelAndView viewAllNoticeBoardByEmployee(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("noticeboard_view");

        List<NoticeBoard> list = new ArrayList<NoticeBoard>();
        long employeeId = this.getUserPrincipal(request).getEmployeeId();

        String cbSearch = request.getParameter("cbSearch");
        String txtSearch = request.getParameter("txtSearch");
        if(cbSearch == null || txtSearch == null){
            list = getNoticeBoardService().findAllNoticeBoard(employeeId);
        }else{
            list = getNoticeBoardService().findAllNoticeBoard(employeeId, cbSearch, txtSearch);
        }

        view.addObject("result", list);
        return view;
    }

    public ModelAndView editNoticeBoard(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("noticeboard_edit");
        String param = request.getParameter("idx");
        if(param == null){
            return viewAllNoticeBoardByEmployee(request, response);
        }
        int id = Integer.parseInt(param);
        NoticeBoard notice = getNoticeBoardService().findNoticeBoardById(id);
        view.addObject("notice", notice);
        
        List<Department> deptList = getSetupService().findAllDepartments();
        Department dept = new Department();
        dept.setId(-1); dept.setDepartmentName("All");
        deptList.add(dept);
        view.addObject("departmentList", deptList);
        view.addObject("employeeList", getEmployeeService().findAllEmployeeForBasic());
        
        return view;
    }

    public ModelAndView newNoticeBoard(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("noticeboard_edit");

        view.addObject("notice", new NoticeBoard());

        List<Department> deptList = getSetupService().findAllDepartments();
        System.out.println(">>>>>:" + deptList);
        Department dept = new Department();
        dept.setId(-1); dept.setDepartmentName("All");
        deptList.add(dept);
        view.addObject("departmentList", deptList);
        view.addObject("employeeList", getEmployeeService().findAllEmployeeForBasic());
        return view;
    }

    public ModelAndView newTransactionNoticeBoard(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("noticeboard_edit");

        view.addObject("notice", new NoticeBoard());

        List<Department> deptList = getSetupService().findAllDepartments();
        System.out.println(">>>>>:" + deptList);
        Department dept = new Department();
        dept.setId(-1); dept.setDepartmentName("All");
        deptList.add(dept);
        view.addObject("departmentList", deptList);
        view.addObject("employeeList", getEmployeeService().findAllEmployeeForBasic());
        view.addObject("TRANX", "Y");
        return view;
    }

    //
    public ModelAndView deleteNoticeBoard(HttpServletRequest request, HttpServletResponse response){
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
        for(int i : ids){
            //getNoticeBoardService().deleteNoticeBoard(i);
        }

       //delete
        return viewAllNoticeBoardByEmployee(request, response);
    }

    public long processTransaction(Object object, String description, long initiator, String transTypeParentID){
        return getNoticeBoardService().initiateTransaction(object, description, initiator, transTypeParentID);
    }

    public ModelAndView processNoticeBoardRequest(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("message");
        String operation = request.getParameter("txtMode");
        String tranxMode = request.getParameter("TRANX");

        long employeeId = this.getUserPrincipal(request).getEmployeeId();
        //operation mode: N= New, E=Edit
        if(operation.trim().equals("N")){
            NoticeBoard notice = new NoticeBoard();
            String subject = request.getParameter("txtSubject");
            String message = request.getParameter("txtMessage");
            //String strStartDate = request.getParameter("txtStartDate");
            String strEndDate = request.getParameter("txtEndDate");
            String strViewers = request.getParameter("cbViewer");
            String strDept = request.getParameter("cbDept");

            System.out.println(">>>>>:" + message);

            //Date _startDate = new Date();
            Date _endDate = new Date();
            try {
                //_startDate = DateUtility.getDateFromString(strStartDate, ConfigReader.DATE_FORMAT);
                _endDate = DateUtility.getDateFromString(strEndDate, ConfigReader.DATE_FORMAT);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }

            notice.setSubject(subject);
            notice.setMessage(message);
            notice.setCreateDate(new Date());
            notice.setExpireDate(_endDate);
            notice.setEmployeeId(employeeId);
            notice.setViewers(strViewers);
            notice.setDepartment(strDept);

            if(tranxMode == null || tranxMode.trim().equals("")){
                boolean saved = getNoticeBoardService().updateNoticeBoard(notice, NoticeBoardService.MODE_INSERT);
                if(saved == false){
                    view = new ModelAndView("error");
                    view.addObject("message", "Error processing Notice Board request. Please try again later");
                    return view;
                }
            }else if(tranxMode.trim().equals("Y")){
                
                long transactionID = processTransaction(notice, "Notice Board Request", employeeId, "NOTE");
                if(transactionID == 0L){
                    view = new ModelAndView("error");
                    view.addObject("message", "Unable to complete transaction request.");
                    return view;
                }
                long nextApprover = employeeService.findNextApprovingOfficer(transactionID);
                Employee supervisor = employeeService.findEmployeeBasicById(nextApprover);
                view = new ModelAndView("success");
                view.addObject("message", "Transaction has been forwarded to " + supervisor.getFullName() + " for approval.");
                return view;
            }

            view.addObject("message", "Record Saved Successfully.");

        }else if(operation.trim().equals("E")){
            NoticeBoard notice = new NoticeBoard();
            String subject = request.getParameter("txtSubject");
            String message = request.getParameter("txtMessage");
            //String strStartDate = request.getParameter("txtStartDate");
            String strEndDate = request.getParameter("txtEndDate");
            String strViewers = request.getParameter("cbViewer");
            String strDept = request.getParameter("cbDept");

            String id = request.getParameter("txtId");
            if(id == null || id.trim().equals("")){
                view = new ModelAndView("error");
                view.addObject("message","Unable to update Job Title information.");
                return view;
            }

            //Date _startDate = new Date();
            Date _endDate = new Date();
            try {
                //s_startDate = DateUtility.getDateFromString(strStartDate, ConfigReader.DATE_FORMAT);
                _endDate = DateUtility.getDateFromString(strEndDate, ConfigReader.DATE_FORMAT);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }

            notice.setSubject(subject);
            notice.setMessage(message);
            //notice.setCreateDate(_startDate);
            notice.setExpireDate(_endDate);
            notice.setEmployeeId(employeeId);
            notice.setViewers(strViewers);
            notice.setDepartment(strDept);
            notice.setId(Long.parseLong(id));

            boolean saved = getNoticeBoardService().updateNoticeBoard(notice, NoticeBoardService.MODE_UPDATE);
            if(saved == false){
                view = new ModelAndView("error");
                view.addObject("message", "Error processing Notice Board Modification. Please try again later");
                return view;
            }

            view.addObject("message", "Record Saved Successfully.");
        }
        return view;
    }

    public ModelAndView viewNoticeBoardInbox(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("noticeboard_inbox");

        List<NoticeBoard> list = new ArrayList<NoticeBoard>();
        long employeeId = this.getUserPrincipal(request).getEmployeeId();
        Employee employee = getEmployeeService().findEmployeeBasicById(employeeId);

        String cbSearch = request.getParameter("cbSearch");
        String txtSearch = request.getParameter("txtSearch");
        if(cbSearch == null || txtSearch == null){
            list = getNoticeBoardService().findNoticeBoardInboxByEmployee(employeeId, employee.getDepartmentId());
        }else{
            list = getNoticeBoardService().findNoticeBoardInboxByEmployee(employeeId, employee.getDepartmentId(), cbSearch, txtSearch);
        }

        view.addObject("result", list);
        return view;
    }

    public ModelAndView viewNoticeBoard(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("noticeboard");
        String param = request.getParameter("idx");
        if(param == null){
            return viewNoticeBoardInbox(request, response);
        }
        int id = Integer.parseInt(param);
        NoticeBoard notice = getNoticeBoardService().findNoticeBoardById(id);
        view.addObject("notice", notice);

        return view;
    }

    public ModelAndView previewNoticeBoard(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("noticeboard_preview");
        String param = request.getParameter("idx");
        if(param == null){
            return viewNoticeBoardInbox(request, response);
        }
        int id = Integer.parseInt(param);
        NoticeBoard notice = getNoticeBoardService().findNoticeBoardById(id);
        view.addObject("notice", notice);

        return view;
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

    /**
     * @return the setupService
     */
    public SetupService getSetupService() {
        return setupService;
    }

    /**
     * @param setupService the setupService to set
     */
    public void setSetupService(SetupService setupService) {
        this.setupService = setupService;
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
