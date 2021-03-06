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

package org.tenece.web.services;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.tenece.hr.data.dao.CompanyDAO;
import org.tenece.hr.data.dao.ReportDAO;
import org.tenece.hr.data.dao.UserDAO;
import org.tenece.web.data.Report;
import org.tenece.web.data.ReportTemplate;


/**
 * Tenece Professional Services, Nigeria
 * @author Jeffry Amachree
 */
public class ReportService extends BaseService{
    private UserDAO userDAO = null;
    private CompanyDAO companyDAO = null;
    private ReportDAO reportDAO = null;
    /** Creates a new instance of LoginService */
    public ReportService() {
    }

    public Report findReportByID(String id) throws Exception{
        return getReportDAO().getReportById(id);
    }

    public ReportTemplate findReportTemplateByCode(String code){
        return getReportDAO().getReportTemplateByCode(code);
    }

    public List<Object> getTemplateData(String captions, String controls, String controlValues){

        List<Object> templateParams = new ArrayList<Object>();

        String[] controlSplit = controls.split(";");
        
        String[] valueSplit = controlValues.split(";");
        String[] captionSplit = captions.split(";");

        for(int i = 0; i < controlSplit.length; i++){
            Hashtable<String, Object> params = new Hashtable<String, Object>();
            //get value and see if it has query to pick list from
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>Begin>>>>>>>>>>>>>>>>>>>>>>>>>>");
            if(controlSplit[i].equalsIgnoreCase("s") || controlSplit[i].equalsIgnoreCase("n")){
                params.put(controlSplit[i], reportDAO.getControlData(valueSplit[i]));
            }else{
                params.put(controlSplit[i], "");
            }
            System.out.println("controlSplit["+i+"] =========" +controlSplit[i]);
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>Begin>>>>>>>>>>>>>>>>>>>>>>>>>>");
            
            params.put("caption", captionSplit[i]);
            templateParams.add(params);
        }

        return templateParams;
    }

    private JasperPrint generateReportPrint(String reportFile, Map parameters, String query) throws Exception{
        ResultSet rst = reportDAO.executeReportQuery(query, reportDAO.getConnection(true));

        JasperPrint jasperPrint = JasperFillManager.fillReport(
                reportFile, parameters, new JRResultSetDataSource(rst));

        return jasperPrint;
    }
    
    public JasperPrint fillReport(String directory, String reportFile, Map parameters, String query) throws Exception{
        //get resultSet and use it to build Jasper ResultSet
        try{
            //create a new connection that will be used for sub reports
            Connection connection = reportDAO.getConnection(true);
            ResultSet rst = reportDAO.executeReportQuery(query, connection);

            System.out.println("Subreport Directory..." + directory + File.separator);
            parameters.put("REPORT_CONNECTION", connection);
            parameters.put("SUBREPORT_DIR", directory + File.separator);
            parameters.put("HR_CONNECTION", connection);

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    reportFile, parameters, new JRResultSetDataSource(rst));

            return jasperPrint;
        }catch(JRException e){
            //throws JRException
            e.printStackTrace();
            System.out.println("******** Simulated Connection **********");
            try{
                return generateReportPrint(reportFile, parameters, query);
            }catch(Exception ex){
                ex.printStackTrace();
                throw ex;
            }
        }catch(Exception e){
            throw e;
        }

        
    }
/*
    public JasperPrint fillReportWithConnection(String reportFile, Map parameters){
        //get resultSet and use it to build Jasper ResultSet
        JasperPrint jasperPrint = JasperFillManager.fillReport(
                    reportFile, parameters, new EmployeeDAO().getConnection(true));

        return jasperPrint;
    }
*/
    private UserDAO getUserDAO() {
        if(userDAO == null){
            userDAO = new UserDAO();
        }
        return userDAO;
    }
    

    /**
     * @return the companyDAO
     */
//    private CompanyDAO getCompanyDAO() {
//        if(companyDAO == null){
//            companyDAO = new CompanyDAO();
//        }
//        return companyDAO;
//    }

    /**
     * @return the reportDAO
     */
    private ReportDAO getReportDAO() {
        if(reportDAO == null){
            reportDAO = new ReportDAO();
        }
        return reportDAO;
    }
}
