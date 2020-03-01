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
 * even if Tenece Professional Services has been advised of the possibility of such damages.
 *
 * You acknowledge that Software is not designed, licensed or intended
 * for use in the design, construction, operation or maintenance of
 * any nuclear facility.
 */

package org.tenece.hr.controller;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 *
 * @author jeffry.amachree
 */
public class EmployeeReportController extends MultiActionController{
    
    /** Creates a new instance of EmployeeReportController */
    public EmployeeReportController() {
    }
    
    public ModelAndView viewEmployeeDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        Map model = new HashMap();
        //It is the path of our webapp - is there a better way to do this?
        String reportResourcePath =  getServletContext().getRealPath("/");

        String format = request.getParameter("format");
        //Default format to pdf
        if (StringUtils.hasText(format)){
            if (!(format.equalsIgnoreCase("pdf") || format.equalsIgnoreCase("html")
                || format.equalsIgnoreCase("csv") || format.equalsIgnoreCase("xls"))){
                format = "pdf";
            }
        }else{
            format = "pdf";
        }

        model.put("format", format);
        model.put("WEBDIR", reportResourcePath);
        model.put("dataSource", null);//getUserManager().getUsers(new User()));
        return new ModelAndView("userMultiFormatReport", model);
    }
    
    public void viewReport(HttpServletRequest request, HttpServletResponse response){
//        List results = null;//userManager.getUsers(null);
//        Map parameters = new HashMap();
//        parameters.put("format", "pdf");
//        parameters.put("WEBDIR", request.getSession().getServletContext().getRealPath("/"));
//        try{
//             InputStream is = request.getSession().getServletContext().getResourceAsStream("/WEB-INF/reports/userList.jrxml");
//             JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(results);
//             response.setContentType("application/pdf");
//             response.addHeader("Content-Disposition", "attachment; filename=userList.pdf");
//             JasperDesign jasperDesign = JRXmlLoader.load(is);
//             JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
//             JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ds);
//             JasperExportManager.exportReportToPdfStream(jasperPrint, getResponse().getOutputStream());
//             
//         }
//         catch(Exception e){
//          //log.error(e);
//         }
//         return null;
    }
}
