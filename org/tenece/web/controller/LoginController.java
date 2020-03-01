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

import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.ImageCaptchaService;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.tenece.web.common.BaseController;
import org.tenece.web.data.Key;
import org.tenece.web.data.Users;
import org.tenece.web.services.LoginService;

/**
 * StrategieX Solutions, Nigeria
 * @author strategiex
 */
public class LoginController extends BaseController {
    private LoginService loginService;
    private ImageCaptchaService captchaService;

    /** Creates a new instance of LoginController */
    public LoginController() {
    }
    
    public LoginService getLoginService() {
        return loginService;
    }

    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }
    
    public ModelAndView showLogin(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("login");
        
        //getServletContext().getRealPath("/");
        //System.out.println(">>>>>>>>>>>>>>" + request.getSession().getServletContext().getRealPath("/WEB-INF/jsp/index.jsp"));
        
        //System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>" + request.getSession().getServletContext().getResourcePaths("/WEB-INF"));
        //System.out.println("*****Real Path:" + request.getSession().getServletContext().getRealPath("/"));
        //get session and kill all values
        HttpSession session = request.getSession(true);
        session.setAttribute("userPrincipal", null);
        
        return view;
    }
    
    public ModelAndView doLogin(HttpServletRequest request, HttpServletResponse response) throws Exception{
        //get all parameters from login page
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String locale = request.getParameter("locale");

        //get parameter to check if captcha is needed
        String useCaptcha = request.getParameter("j_ct");
         System.out.println("useCaptcha >>>>>>" + useCaptcha);
        if(useCaptcha != null && useCaptcha.equals("1")){

            String captchaText = request.getParameter("txtCaptcha");
            ModelAndView view = null;

            boolean validCaptcha = false;
            String sessionId = request.getSession().getId();
            try {
                validCaptcha = getCaptchaService().validateResponseForID(sessionId, captchaText);
            }
            catch (CaptchaServiceException e) {
                //should not happen, may be thrown if the id is not valid
                view = showLogin(request, response);
                view.addObject("j_ct", "1");
                view.addObject("message", "Unable to proceed with request, Code verification Error.");
                return view;
            }

            if(validCaptcha == false){
                view = showLogin(request, response);
                view.addObject("j_ct", "1");
                view.addObject("message", "Unable to proceed with request, Invalid verification code. Ensure you enter the exact text displayed on the image.");
                return view;
            }
        }

        //validate user...
        Users user = null;
        user = loginService.validateUser(userName, password);
        //check if user is valid
        if(user == null){//user is invalid
            ModelAndView view = null;
            view = showLogin(request, response);
            view.addObject("j_ct", "1");
            view.addObject("message", loginService.getErrorMessage());
            return view;
        }else{//valid user
            
            Users userPrincipal = (Users)user;
            //get session object
            HttpSession session = request.getSession(true);
            //assign values to session as attribute
            session.setAttribute("userPrincipal", userPrincipal);
            session.setAttribute("userlang", locale);

            //create audit
            try{
                createNewAuditTrail(request, Key.LOGIN_OPERATION);
            }catch(Exception e){
                //e.printStackTrace();
            }
            
            //build locale object
            Locale defLocale = new Locale(locale);
            //set local in session and in jstl config
            Config.set(session, Config.FMT_LOCALE, defLocale);
            
        }
        return new ModelAndView(new RedirectView("default_home.hd?"));
        
    }
    
    public ModelAndView welcomeUser (HttpServletRequest request, HttpServletResponse response) throws Exception{
        ModelAndView view = new ModelAndView("welcome");
        
        return view;
    }

    public ModelAndView logoutUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView view = new ModelAndView("logout");
        
        HttpSession session = request.getSession();
        if(session != null){
            session.invalidate();
            session = null;
        }
        return view;
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
