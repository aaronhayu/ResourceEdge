
/*
 * (c) Copyright 2009 The Tenece Professional Services.
 *
 * Created on 01 January 2010, 22:11
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


import com.octo.captcha.service.image.ImageCaptchaService;
import java.io.ByteArrayOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.tenece.web.common.BaseController;
/**
 *
 * @author amachree
 */
public class CaptchaController extends BaseController {

    /*
     *
     Usage:
  boolean validCaptcha = false;
  try {
   validCaptcha = captchaService.validateResponseForID(sessionId, registrationForm.getCaptcha());
  }
  catch (CaptchaServiceException e) {
   //should not happen, may be thrown if the id is not valid
   logger.warn("validateCaptcha()", e);
  }

     */
    private ImageCaptchaService captchaService;

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response){

        try{
        
            //byte[] captchaChallengeAsJpeg = null;

            System.out.println("In==================JCaptchaController");

            ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();

            String captchaId = request.getSession(true).getId();

            java.awt.image.BufferedImage bufferedImg = this.getCaptchaService(
                    ).getImageChallengeForID(captchaId, request.getLocale());


            response.setHeader("Cache-Control", "no-store");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0L);
            response.setContentType("image/jpeg");

            response.setHeader("Content-Disposition", "inline; filename=captcha_image.jpeg");
            javax.servlet.ServletOutputStream out = response.getOutputStream();

            javax.imageio.ImageIO.write(bufferedImg, "png", out);
            out.flush();
            out.close();
            return null;
        }catch(Exception ex){
            return null;
        }
    }

    public void gtenerateCaptcha(){
        
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

    public void afterPropertiesSet()
    throws Exception
    {
        if (this.captchaService == null)
          throw new RuntimeException("ResourceEdge Image captcha service wasn`t set!");
    }
}
