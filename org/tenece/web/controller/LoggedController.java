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

package org.tenece.web.controller;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.tenece.web.common.BaseController;
import org.tenece.web.data.Employee;
import org.tenece.web.data.EmployeePicture;
import org.tenece.web.data.Users;
import org.tenece.web.services.EmployeeService;

/**
 *
 * @author strategiex
 */
public class LoggedController extends BaseController{
    private EmployeeService employeeService;
    private Users user;
    
    /** Creates a new instance of Login */
    public LoggedController() {
    }
    
    public ModelAndView loadHeader(HttpServletRequest request, HttpServletResponse response) throws Exception{
        ModelAndView view = new ModelAndView("header_user");
        System.out.println("getting header");

        Users user = this.getUserPrincipal(request);
         try
         {
             
         
        //get session
        HttpSession session  = request.getSession();
        String typeOfMode = (String)session.getAttribute("menuMode");
        if(user.getSuperAdmin() == 1 && user.getAdminUser() == 0){
            view.addObject("displayMenuOption", "Y");
            if(typeOfMode == null || typeOfMode.trim().equals("E")){
                view.addObject("menuOption", "E");
            }else{
                view.addObject("menuOption", "A");
            }
        }else{
            view.addObject("displayMenuOption", "N");
        }
         }catch(Exception ex)
         {
           ex.printStackTrace();
         }
        Employee employee = getEmployeeService().findEmployeeBasicById((long)user.getEmployeeId());

        if(employee == null && user.getEmployeeId() <= 0){
            //no employer
            employee = new Employee();
            employee.setFirstName("System Administrator");
            employee.setLastName("");
        }

        //get employee picture when loading data
        
        try{
            EmployeePicture picture = employeeService.getEmployeePicture(user.getEmployeeId());
            String filePath = request.getSession().getServletContext().getRealPath("./upload/ess_" + String.valueOf(employee.getEmployeeID()) + "_" + picture.getFileName());
            File file = new File(filePath);
            DataOutputStream dOut = new DataOutputStream(new FileOutputStream(file));
            dOut.write(picture.getPicture());
            dOut.flush();
            dOut.close();

            //System.out.println("Path for image(ess)...." + file.getAbsolutePath());

            view.addObject("emp_picture", "./upload/ess_" + String.valueOf(employee.getEmployeeID()) + "_" + picture.getFileName());
        }catch(Exception e){
            view.addObject("emp_picture", "./images/default_employee_image.gif");
            //e.printStackTrace();
        }

        String strCompanyLogo = this.stampCompanyLogoURL(request);
        if(strCompanyLogo.trim().equals("")){
            strCompanyLogo = "./images/strategiex.gif";
        }
        view.addObject("COMPANY_LOGO", strCompanyLogo);

        view.addObject("employee", employee);
        return view;
    }

    public ModelAndView home(HttpServletRequest request, HttpServletResponse response) throws Exception{
        ModelAndView view = new ModelAndView("home");
        HttpSession session = request.getSession();
        String viewAdminMenu = (String) session.getAttribute("menuMode");
        System.out.println("viewAdminMenu = "+viewAdminMenu);
        System.out.print("home");
        return view;
    }
    public ModelAndView footer(HttpServletRequest request, HttpServletResponse response) throws Exception{
        ModelAndView view = new ModelAndView("footer");
        
        return view;
    }
    public ModelAndView reloadMenu(HttpServletRequest request, HttpServletResponse response) throws Exception{
        //get session (the current session)
        HttpSession session = request.getSession();
        //get parameter pass to this URL
        String type = request.getParameter("type");
        //create a variable to hold the final value to set
        String value = "";
        if(type == null){
            value = "E";
        }else if(type.trim().equals("A")){
            value = "A";
        }else if(type.trim().equals("E")){
            value = "E";
        }
        System.out.println("value========"+value);
        
        session.setAttribute("menuMode", value);
        
        return home(request, response);
    }
public ModelAndView loadMenu(HttpServletRequest request, HttpServletResponse response) throws Exception{
       ModelAndView view = new ModelAndView("menuContent");
        //get session (the current session)
        HttpSession session = request.getSession();
        //get parameter pass to this URL
        String type = request.getParameter("type");
        //create a variable to hold the final value to set
        String MenuXXXXX = (String) session.getAttribute("menuMode");
        if(MenuXXXXX == "E" || MenuXXXXX == null )
        {
            String value = "";
            if(type == null){
                value = "E";
            }else if(type.trim().equals("A")){
                value = "A";
            }else if(type.trim().equals("E")){
                value = "E";
            }
            session.setAttribute("menuMode", value);
            //System.out.println()
            view.addObject("menuMode", value);
        }
        return view;
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
