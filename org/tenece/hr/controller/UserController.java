
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

import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.ImageCaptchaService;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.tenece.hr.security.SecurityEncoderImpl;
import org.tenece.web.common.BaseController;
import org.tenece.web.common.RandomGenerator;
import org.tenece.web.data.EmailMessage;
import org.tenece.web.data.Employee;
import org.tenece.web.data.Lock;
import org.tenece.web.data.Users;
import org.tenece.web.services.EmployeeService;
import org.tenece.web.services.LoginService;
import org.tenece.web.services.MailService;
import org.tenece.web.services.UserService;

/**
 *
 * @author jeffry.amachree
 */
public class UserController extends BaseController{
    
    private UserService userService = new UserService();
    private EmployeeService employeeService = new EmployeeService();
    private LoginService loginService;
    private MailService mailService;
    private ImageCaptchaService captchaService;

    /** Creates a new instance of userController */
    public UserController() {
    }

    public ModelAndView viewPasswordRecall(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("password_recall");
        return view;
    }

    public ModelAndView savePasswordRecall(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("success_plain");
        String txtEmpNo = request.getParameter("txtNo");
        String txtUID = request.getParameter("txtName");
        String captchaText = request.getParameter("txtCaptcha");

        boolean validCaptcha = false;
        String sessionId = request.getSession().getId();
        try {
            validCaptcha = getCaptchaService().validateResponseForID(sessionId, captchaText);
        }
        catch (CaptchaServiceException e) {
            //should not happen, may be thrown if the id is not valid
            view = viewPasswordRecall(request, response);
            view.addObject("message", "Unable to proceed with request, Code verification Error.");
            return view;
        }
        
        if(validCaptcha == false){
            view = viewPasswordRecall(request, response);
            view.addObject("message", "Unable to proceed with request, Invalid verification code. Ensure you enter the exact text displayed on the image.");
            return view;
        }

        if(txtEmpNo == null || txtEmpNo.trim().equals("")){
            view = viewPasswordRecall(request, response);
            view.addObject("message", "Unable to proceed with request, ensure that employee number is specified");
            return view;
        }
        //check if number is valid digit
        try{
            Long.parseLong(txtEmpNo);
        }catch(NumberFormatException e){
            view = viewPasswordRecall(request, response);
            view.addObject("message", "Invalid Employee number, Only numbers are allowed.");
            return view;
        }
        if(txtUID == null || txtUID.trim().equals("")){
            view = viewPasswordRecall(request, response);
            view.addObject("message", "To retrieve password, user name must be specified.");
            return view;
        }

        //use service to access data based on the combination
        Users user = this.userService.findUserByUserNameAndEmployeeID(txtUID, Long.parseLong(txtEmpNo));
        //if valid user, decrypt password
        if(user == null){
            view = viewPasswordRecall(request, response);
            view.addObject("message", "Invalid User Name and Employee Number combination. Ensure that the user name is assigned to the employee with the number specified (" + txtEmpNo + ")");
            return view;
        }
        try {

            String decryptPassword = SecurityEncoderImpl.decryptPasswordWithAES(user.getPassword());
            //send mail to recipient
            Employee employee = employeeService.findEmployeeBasicByEmpNumber(Integer.parseInt(txtEmpNo));

            String companyLogo = this.stampCompanyLogoURL(request, employee.getCompanyCode());
            int sent = mailService.sendPasswordRecall(Long.parseLong(txtEmpNo), decryptPassword, companyLogo);
            System.out.println(">>>>>>>>>>Sent Mail:" + sent);
        } catch (Exception ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            view = viewPasswordRecall(request, response);
            view.addObject("message", "System error while processing password, contact your system administrator for assistance.");
            return view;
        }
        
        view.addObject("message","Operation was successful, password has been sent to the registered email address.");
        return view;
    }

    public ModelAndView viewAllLockAccounts(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("users_locked_view");

        view.addObject("result", userService.findAllLockedUsers());
        return view;
    }

    public ModelAndView viewAll(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("users_view");
        
        List<Users> list = null;
        
        if(this.getUserPrincipal(request).getGroupCompanyUser() == 1){
            list = userService.findAllUsers();
            view.addObject("showCompany", "Y");
        }else{
            Employee employee = this.getEmployeeService().findEmployeeBasicById(getUserPrincipal(request).getEmployeeId());
            list = userService.findAllUsersByCompany(employee.getCompanyCode());
        }

        view.addObject("result", list);
        return view;
    }

    
    public ModelAndView edit(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("users_edit");
        String param = request.getParameter("idx");
        if(param == null){
            return viewAll(request, response);
        }
        int id = Integer.parseInt(param);
        Users user = userService.findUsersById(id);
        view.addObject("showAll", "Y");
        view.addObject("user", user);
        view.addObject("employee", employeeService.findEmployeeBasicById(user.getEmployeeId()));
        return view;
    }

    public ModelAndView editLock(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("users_locked_edit");
        String param = request.getParameter("idx");
        if(param == null){
            return viewAll(request, response);
        }
        int id = Integer.parseInt(param);
        Users user = userService.findUsersById(id);
        view.addObject("user", user);
        view.addObject("employee", employeeService.findEmployeeBasicById(user.getEmployeeId()));
        view.addObject("lock", getUserService().findLockById(id));
        return view;
    }
    
    public ModelAndView newRecord(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("users_edit");

        String cbCompany = request.getParameter("cbCompany");
        if(cbCompany == null){ cbCompany = ""; }

        if(this.getUserPrincipal(request).getGroupCompanyUser() == 1){
            //leave the setting the way it is...
        }else{
            Employee employee = this.getEmployeeService().findEmployeeBasicById(getUserPrincipal(request).getEmployeeId());
            cbCompany = employee.getCompanyCode();
        }
        
        //get parameter to know which employee was selected
        String strEmpID = request.getParameter("cbEmp");
        Employee employee = new Employee();
        if(strEmpID != null && (!strEmpID.trim().equals(""))){
            if(Integer.parseInt(strEmpID) > 0){
                employee = employeeService.findEmployeeBasicById(Long.parseLong(strEmpID));

                Users _user = new Users();
                _user.setEmployeeId(Integer.parseInt(strEmpID));
                _user.setUserName(String.valueOf(employee.getEmployeeID()));
                _user.setPassword(RandomGenerator.getRandomValue(7));
                view.addObject("user", _user);
                view.addObject("showAll", "Y");
            }else{
                view.addObject("showAll", "N");
            }
        }else{
            view.addObject("showAll", "N");
        }

        view.addObject("employeeList", employeeService.findAllEmployeeForBasicByCompany(cbCompany));
        view.addObject("companyCode", cbCompany);
        view = getAndAppendCompanyToView(view, request);
        return view;
    }

    public ModelAndView changePassword(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("change_password");

        Users user = this.getUserPrincipal(request);
        view.addObject("user", user);
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
        for(int i : ids){
            userService.deleteUsers(i);
        }
        
       //delete
        return viewAll(request, response);
    }

     public ModelAndView processChangePasswordRequest(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("message");

        Users user = this.getUserPrincipal(request);
        //get parameters
        String pwd1 = request.getParameter("txtPwd");
        String newPwd = request.getParameter("txtPwd2");
        String newPwd2 = request.getParameter("txtPwd3");

        if(newPwd == null || newPwd2 == null || pwd1 == null
                || newPwd.trim().equals("")){
            view = new ModelAndView("error");
            view.addObject("message", "Invalid Data, Try again.");
            return view;
        }
        //confirm if the two new pwd is correct
        if(!newPwd.equals(newPwd2)){
            view = new ModelAndView("error");
            view.addObject("message", "New Passwords does not match.");
            return view;
        }

        //confirm if old password is valid
        Users _user = loginService.validateUser(user.getUserName(), pwd1);
        if(_user == null){
            view = new ModelAndView("error");
            view.addObject("message", "Current/Active password is invalid. User account will be locked after several attempt");
            return view;
        }

        //encrypt
        String strPwd = "";
        try {
            strPwd = SecurityEncoderImpl.encryptPasswordWithAES(newPwd2);
        } catch (Exception ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }

        int row = loginService.updatePassword(_user.getId(), strPwd);
        if(row == 0){
            view = new ModelAndView("error");
            view.addObject("message", "Unable to change user login information");
            return view;
        }
        //send mail
        try{
            int sent = mailService.sendChangePassword(_user, newPwd2, stampCompanyLogoURL(request));
        }catch(Exception e){}
        
        view.addObject("message", "Password was changed successfully.");
        return view;
     }
     
     public ModelAndView processRequest(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("message");
        String operation = request.getParameter("txtMode");
        //operation mode: N= New, E=Edit
        if(operation.trim().equals("N")){
            Users user = new Users();
            String empId = request.getParameter("txtEmp");
            String uid = request.getParameter("txtUserName");
            String role = request.getParameter("cbRole");
            String superAdmin = request.getParameter("chkSuper");
            String status = request.getParameter("cbStatus");
            String pwd = String.valueOf(request.getParameter("txtPwd"));
            String pwd2 = String.valueOf(request.getParameter("txtPwd2"));

            //invalid password
            if((!pwd.trim().equals(pwd2)) || pwd.trim().equals("null")){
                return newRecord(request, response);
            }

            String strPwd = "";
            try {
                strPwd = SecurityEncoderImpl.encryptPasswordWithAES(pwd);
            } catch (Exception ex) {
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            }

            if(superAdmin == null){
                superAdmin = "0";
            }
            user.setEmployeeId(Integer.parseInt(empId));
            user.setUserName(uid);
            user.setPassword(strPwd);
            user.setNumberLogins(0);
            user.setActive(status);
            user.setAdminUser(Integer.parseInt(role));
            user.setSuperAdmin(Integer.parseInt(superAdmin));
            user.setDateUpdated(new Date());
            user.setDateSignup(new Date());
            user.setLastLoginDate(new Date());

            boolean saved = userService.updateUsers(user, userService.MODE_INSERT);
            if(saved == false){
                view = new ModelAndView("error");
                view.addObject("message", "Error processing user setup request. Please try again later");
                return view;
            }

            //send mail
            try{
                Employee employee = getEmployeeService().findEmployeeBasicById(user.getEmployeeId());
                Hashtable keys = new Hashtable();
                keys.put("EMAIL_USERNAME", user.getUserName());
                keys.put("EMAIL_NAME", employee.getFullName());
                keys.put("EMAIL_PASSWORD", pwd);

                int sent = mailService.sendEmail("SIGNIN", user.getEmployeeId(), keys);
                System.out.println("mail sent: " + sent);
            }catch(Exception e){}

            view = new ModelAndView("success");
            view.addObject("message", "Record Saved Successfully.");
            
        }else if(operation.trim().equals("E")){
            Users user = new Users();
            String empId = request.getParameter("txtEmp");
            String uid = request.getParameter("txtUserName");
            String role = request.getParameter("cbRole");
            String superAdmin = request.getParameter("chkSuper");
            String status = request.getParameter("cbStatus");

            String id = request.getParameter("txtId");
            if(id == null || id.trim().equals("")){
                view = new ModelAndView("error");
                view.addObject("message","Unable to update Job Title information.");
                return view;
            }

            if(superAdmin == null){
                superAdmin = "0";
            }
            user.setId(Integer.parseInt(id));
            user.setEmployeeId(Integer.parseInt(empId));
            user.setUserName(uid);
            user.setNumberLogins(0);
            user.setActive(status);
            user.setAdminUser(Integer.parseInt(role));
            user.setSuperAdmin(Integer.parseInt(superAdmin));
            user.setDateUpdated(new Date());
            user.setDateSignup(new Date());
            user.setLastLoginDate(new Date());
            
            boolean saved = userService.updateUsers(user, userService.MODE_UPDATE);
            if(saved == false){
                view = new ModelAndView("error");
                view.addObject("message", "Error processing user setup request. Please try again later");
                return view;
            }
            
            view = new ModelAndView("success");
            view.addObject("message", "Record Saved Successfully.");
        }
        return view;
    }

     public ModelAndView processUnlockRequest(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("message");
        //user id
        String id = request.getParameter("txtId");

        if(id == null || id.trim().equals("")){
            view = new ModelAndView("error");
        }
        Lock lock = new Lock();
        lock.setActive("D");
        lock.setUserId(Long.parseLong(id));

        int saved = userService.deactivateLockOnUser(lock);
        if(saved == 0){
            view = new ModelAndView("error");
            view.addObject("message", "Error Unlocking user account. Please try again later");
            return view;
        }

        String chkMail = request.getParameter("chkNotify");
        if(chkMail != null && chkMail.trim().equals("1")){
            //send mail
            try{

//                Employee employee = getEmployeeService().findEmployeeBasicById(user.getEmployeeId());
//                Hashtable keys = new Hashtable();
//                keys.put("EMAIL_USERNAME", user.getUserName());
//                keys.put("EMAIL_NAME", employee.getFullName());
//                keys.put("EMAIL_PASSWORD", pwd);
//
//                int sent = sendEmail("SIGNIN", user.getEmployeeId(), keys);
//                System.out.println("mail sent: " + sent);
            }catch(Exception e){}
        }


        view.addObject("message", "Record Saved Successfully.");

        return view;
    }

    /**
     * @return the userService
     */
    public UserService getUserService() {
        return userService;
    }

    /**
     * @param userService the userService to set
     */
    public void setUserService(UserService userService) {
        this.userService = userService;
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
     * @return the loginService
     */
    public LoginService getLoginService() {
        return loginService;
    }

    /**
     * @param loginService the loginService to set
     */
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    /**
     * @return the mailService
     */
    public MailService getMailService() {
        return mailService;
    }

    /**
     * @param mailService the mailService to set
     */
    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    /**
     * @return the captchaService
     */
    public ImageCaptchaService getCaptchaService() {
        return captchaService;
    }

    /**
     * @param captchaService the captchaService to set
     */
    public void setCaptchaService(ImageCaptchaService captchaService) {
        this.captchaService = captchaService;
    }
    
}
