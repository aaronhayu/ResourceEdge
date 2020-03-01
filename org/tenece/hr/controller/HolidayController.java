
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
import org.tenece.web.data.Employee;
import org.tenece.web.data.Holiday;
import org.tenece.web.services.SetupService;

/**
 *
 * @author jeffry.amachree
 */
public class HolidayController extends BaseController{
    
    private SetupService setupService = new SetupService();
    
    /** Creates a new instance of HolidayController */
    public HolidayController() {
    }
    
    public ModelAndView viewAll(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("holiday_view");
        
        //do search here...
        String cbSearch = request.getParameter("cbSearch");
        String txtSearch = request.getParameter("txtSearch");

        //get list of employee
        List<Holiday> list = null;
        if(cbSearch == null || txtSearch == null){

            if(this.getUserPrincipal(request).getGroupCompanyUser() == 1){
                list = setupService.findAllHolidays();
                view.addObject("showCompany", "Y");
            }else{
                Employee employee = this.getEmployeeService().findEmployeeBasicById(getUserPrincipal(request).getEmployeeId());
                list = setupService.findAllHolidaysByCompany(employee.getCompanyCode());
            }
        }else{

            if(this.getUserPrincipal(request).getGroupCompanyUser() == 1){
                list = setupService.findAllHolidays(cbSearch, txtSearch);
                view.addObject("showCompany", "Y");
            }else{
                Employee employee = this.getEmployeeService().findEmployeeBasicById(getUserPrincipal(request).getEmployeeId());
                list = setupService.findAllHolidaysByCompany(employee.getCompanyCode(), cbSearch, txtSearch);
            }
        }

        view.addObject("result", list);
        return view;
    }
    
    public ModelAndView edit(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("holiday_edit");
        String param = request.getParameter("idx");
        if(param == null){
            return viewAll(request, response);
        }
        int id = Integer.parseInt(param);
        Holiday holiday = setupService.findHolidayById(id);
        view.addObject("holiday", holiday);

        //get company info
        view = getAndAppendCompanyToView(view, request);

        return view;
    }
    
    public ModelAndView newRecord(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("holiday_edit");
        
        //get company info
        view = getAndAppendCompanyToView(view, request);

        view.addObject("holiday", new Holiday());
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
        setupService.deleteHoliday(ids);
       //delete
        return viewAll(request, response);
    }
    
    public ModelAndView processRequest(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("message");
        String operation = request.getParameter("txtMode");
        //operation mode: N= New, E=Edit
        if(operation.trim().equals("N")){
            Holiday holiday = new Holiday();
            String desc = request.getParameter("txtDesc");
            String strDate = request.getParameter("txtDate");
            String holidayLength = request.getParameter("txtLength");
            String recurring = request.getParameter("chkAnnually");
            String companyCode = request.getParameter("cbCompany");
            
            if(recurring == null){
                recurring = "0";
            }
            Date effectiveDate = new Date();
            try {
                effectiveDate = DateUtility.getDateFromString(strDate, ConfigReader.DATE_FORMAT);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            
            holiday.setDescription(desc);
            holiday.setEffectiveDate(effectiveDate);
            holiday.setHoliday_length(Integer.parseInt(holidayLength));
            holiday.setRecurring(Integer.parseInt(recurring));
            holiday.setCompanyCode(companyCode);
            
            boolean saved = setupService.updateHoliday(holiday, setupService.MODE_INSERT);
            if(saved == false){
                view = new ModelAndView("error");
                view.addObject("message", "Error processing Holiday request. Please try again later");
                return view;
            }
            
            view.addObject("message", "Record Saved Successfully.");
            
        }else if(operation.trim().equals("E")){
            Holiday holiday = new Holiday();
            String desc = request.getParameter("txtDesc");
            String strDate = request.getParameter("txtDate");
            String holidayLength = request.getParameter("txtLength");
            String recurring = request.getParameter("chkAnnually");
            String companyCode = request.getParameter("cbCompany");
            
            if(recurring == null){
                recurring = "0";
            }
            Date effectiveDate = new Date();
            try {
                effectiveDate = DateUtility.getDateFromString(strDate, ConfigReader.DATE_FORMAT);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            
            String id = request.getParameter("txtId");
            if(id == null || id.trim().equals("")){
                view = new ModelAndView("error");
                view.addObject("message","Unable to update Holiday");
                return view;
            }
            
            holiday.setId(Integer.parseInt(id));
            holiday.setDescription(desc);
            holiday.setEffectiveDate(effectiveDate);
            holiday.setHoliday_length(Integer.parseInt(holidayLength));
            holiday.setRecurring(Integer.parseInt(recurring));
            holiday.setCompanyCode(companyCode);
            
            boolean saved = setupService.updateHoliday(holiday, setupService.MODE_UPDATE);
            if(saved == false){
                view = new ModelAndView("error");
                view.addObject("message", "Error processing Holiday request. Please try again later");
                return view;
            }
            
            view.addObject("message", "Record Saved Successfully.");
        }
        return view;
    }

    public SetupService getSetupService() {
        return setupService;
    }

    public void setSetupService(SetupService setupService) {
        this.setupService = setupService;
    }
    
}
