/*
 * (c) Copyright 2009 The Tenece Professional Services.
 *
 * Created on June 17, 2009, 3:40 PM
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

package org.tenece.hr.controller;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.tenece.web.common.BaseController;
import org.tenece.web.common.ConfigReader;
import org.tenece.web.common.DateUtility;
import org.tenece.web.data.Employee;
import org.tenece.web.data.EmployeeAttendance;
import org.tenece.web.data.FileUpload;
import org.tenece.web.data.Users;
import org.tenece.web.services.EmployeeService;
import org.tenece.web.services.TimesheetService;

/**
 *
 * @author jeffry.amachree
 */
public class EmployeeAttendanceController extends BaseController {
    private TimesheetService timesheetService = new TimesheetService();
    private EmployeeService employeeService = new EmployeeService();
    
    /** Creates a new instance of EmployeeAttendanceController */
    public EmployeeAttendanceController() {
    }
    
    public ModelAndView viewAll(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("attendance_view");
        
        List<EmployeeAttendance> list = timesheetService.findAllAttendance();
        view.addObject("result", list);
        return view;
    }
    
    public ModelAndView viewAllForEmployee(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("attendance_emp_view");
        
        String idx = request.getParameter("idx");
        
        List<EmployeeAttendance> list = timesheetService.findAllEmployeeAttendance(Integer.parseInt(idx));
        view.addObject("result", list);
        return view;
    }
    
    public ModelAndView edit(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("attendance_edit");

        String companyCode = this.getActiveEmployeeCompanyCode(request);

        //get id of attendance- not employee id
        String idx = request.getParameter("idx");
        if(idx == null){
            return viewAll(request, response);
        }
        
        List<Employee> employees = employeeService.findAllEmployeeForBasicByCompany(companyCode);
        view.addObject("employees", employees);
        
        EmployeeAttendance attendance = timesheetService.findAttendance(Integer.parseInt(idx));
        view.addObject("attendance", attendance);
        return view;
    }
    
    public ModelAndView newRecord(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("attendance_edit");

        String companyCode = this.getActiveEmployeeCompanyCode(request);

        List<Employee> employees = employeeService.findAllEmployeeForBasicByCompany(companyCode);
        view.addObject("employees", employees);
        
        view.addObject("attendance", new EmployeeAttendance());
        return view;
    }
    
    public ModelAndView upload(HttpServletRequest request, HttpServletResponse response) throws Exception{
        ModelAndView view = new ModelAndView("attendance_upload");
        
        return view;
    }
    
    public ModelAndView processUploadRequest(HttpServletRequest request, HttpServletResponse response) throws Exception{
        
        ModelAndView view = new ModelAndView("attendance_upload_view");
        
        String companyCode = this.getActiveEmployeeCompanyCode(request);

        Users user = this.getUserPrincipal(request);

        if (!(request instanceof MultipartHttpServletRequest)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Expected multipart request");
            return null;
        }
        String deviceId = request.getParameter("cbDevice");
        String fileType = request.getParameter("cbFileType");
        
        System.out.println("-------1------------");
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("txtFile");
        
        FileUpload fileUpload = new FileUpload();
        fileUpload.setFileName(file.getName());
        try {
            fileUpload.setBytes(file.getBytes());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
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
        try{
            List<EmployeeAttendance> attendanceList = timesheetService.saveUploadedAttendanceFile(fileUpload, fileType, deviceId, companyCode);
            HttpSession session = request.getSession(false);
            String strId = user.getEmployeeId() + "_" + DateUtility.getDateAsString(new Date(), "ddMMyyyyHHmmss");
            session.setAttribute(strId, attendanceList);

            view.addObject("list", attendanceList);
            view.addObject("idx", strId);
        }catch(Exception e){
            view = upload(request, response);
            view.addObject("message", e.getMessage());
        }
        
        return view;
    }
    //
    public ModelAndView confirmUploadRequest(HttpServletRequest request, HttpServletResponse response) throws Exception{
        ModelAndView view = new ModelAndView("success");
        //get session index
        String strId = request.getParameter("txtHIdx");
        
        String companyCode = this.getActiveEmployeeCompanyCode(request);

        //use index to get value from session
        HttpSession session = request.getSession(false);

        List<EmployeeAttendance> attendanceList = (List<EmployeeAttendance>)session.getAttribute(strId);
        //check if value is null
        if(attendanceList == null){
            view = new ModelAndView("error");
            view.addObject("message","Unable to confirm request. Ensure that the uploaded file is valid.");
            return view;
        }
        //remove value from session
        session.removeAttribute(strId);

        //save bulk
        boolean saved = timesheetService.saveBulkEmployeeAttendance(attendanceList, companyCode);
        if(saved == false){
            view = new ModelAndView("error");
            view.addObject("message","Error while saving employee attendance. Please try again later");
            return view;
        }else{
            view.addObject("message","Uploaded attendance successfully confirmed.");
        }

        return view;
    }
    public ModelAndView delete(HttpServletRequest request, HttpServletResponse response){
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
        timesheetService.deleteEmployeeAttendance(ids);
       //delete
        return viewAll(request, response);
    }

    public ModelAndView processRequest(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("message");
        String operation = request.getParameter("txtMode");
        //operation mode: N= New, E=Edit
        if(operation.trim().equals("N")){
            EmployeeAttendance attendance = new EmployeeAttendance();
            String employeeId = request.getParameter("cbEmp");
            String strDate = request.getParameter("txtDate");
            String type = request.getParameter("cbType");
            String time = request.getParameter("txtTime");
            String device = request.getParameter("txtDevice");
            String punchError = "0";
            String custom1 = "";
            String custom2 = "";
            
            Date actionDate = new Date();
            try {
                actionDate = DateUtility.getDateFromString(strDate, ConfigReader.DATE_FORMAT);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            
            if(employeeId != null && (!employeeId.equals(""))){
                attendance.setEmployeeId(Integer.parseInt(employeeId));
            }
            attendance.setActionDate(actionDate);
            attendance.setActionTime(time);
            attendance.setActionType(type);
            attendance.setCustom1(custom1);
            attendance.setCustom2(custom2);
            attendance.setPunchError(punchError);
            attendance.setDevice(device);
            
            boolean saved = timesheetService.updateEmployeeAttendance(attendance, timesheetService.MODE_INSERT);
            if(saved == false){
                view = new ModelAndView("error");
                view.addObject("message", "Error processing request. Please try again later");
                return view;
            }
            
            view.addObject("message", "Record Saved Successfully.");
            
        }else if(operation.trim().equals("E")){
            EmployeeAttendance attendance = new EmployeeAttendance();
            String employeeId = request.getParameter("cbManager");
            String strDate = request.getParameter("txtDate");
            String type = request.getParameter("txtType");
            String time = request.getParameter("txtTime");
            String device = request.getParameter("txtDevice");
            String punchError = "0";
            String custom1 = "";
            String custom2 = "";
            
            String attId = request.getParameter("txtId");
            
            Date actionDate = new Date();
            try {
                actionDate = DateUtility.getDateFromString(strDate, ConfigReader.DATE_FORMAT);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            
            if(attId == null || attId.trim().equals("")){
                view = new ModelAndView("error");
                view.addObject("message","Unable to complete update.");
                return view;
            }
            
            if(employeeId != null && (!employeeId.equals(""))){
                attendance.setEmployeeId(Integer.parseInt(employeeId));
            }
            attendance.setActionDate(actionDate);
            attendance.setActionTime(time);
            attendance.setActionType(type);
            attendance.setCustom1(custom1);
            attendance.setCustom2(custom2);
            attendance.setPunchError(punchError);
            attendance.setDevice(device);
            attendance.setId(Integer.parseInt(attId));
            
            boolean saved = timesheetService.updateEmployeeAttendance(attendance, timesheetService.MODE_UPDATE);
            if(saved == false){
                view = new ModelAndView("error");
                view.addObject("message", "Error processing request. Please try again later");
                return view;
            }
            
            view.addObject("message", "Record Saved Successfully.");
        }
        return view;
    }
    
    public TimesheetService getTimesheetService() {
        return timesheetService;
    }

    public void setTimesheetService(TimesheetService timesheetService) {
        this.timesheetService = timesheetService;
    }

    public EmployeeService getEmployeeService() {
        return employeeService;
    }

    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    
    
}
