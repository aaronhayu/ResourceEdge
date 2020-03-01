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

package org.tenece.hr.data.dao;

import org.tenece.hr.db.DatabaseQueryLoader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import org.tenece.web.data.Company;
import org.tenece.web.data.EmailMessage;

/**
 *
 * @author jeffry.amachree
 */
public class CompanyDAO extends BaseDAO{
    
    /** Creates a new instance of CompanyDAO */
    public CompanyDAO() {
    }

    public List<Company> getAllCompany(){
        Connection connection = null;
        List<Company> record = new ArrayList<Company>();
        Vector param =new Vector();
        Vector type = new Vector();
        try{

            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, 
                    DatabaseQueryLoader.getQueryAssignedConstant().COMPANY_SELECT_ALL,
                    param, type);

            while(rst.next()){
                Company company = new Company();
                company.setCode(rst.getString("code"));
                company.setName(rst.getString("company_name"));
                company.setLegalName(rst.getString("legal_name"));
                company.setAddress1(rst.getString("address1"));
                company.setAddress2(rst.getString("address2"));
                company.setCity(rst.getString("city"));
                company.setState(rst.getString("state"));
                company.setCountry(rst.getString("country"));
                company.setPhone(rst.getString("phone"));
                company.setFax(rst.getString("fax"));
                company.setEmail(rst.getString("email"));
                company.setWebsite(rst.getString("website"));
                company.setLogo(rst.getBytes("logo"));
                company.setLogoName(rst.getString("logo_name"));
                company.setLeaveLimit(rst.getInt("leave_limit"));
                company.setDailyWorkingHours(rst.getInt("daily_working_hours"));
                company.setUseDefaultWorkflow(rst.getInt("use_default_workflow"));

                record.add(company);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return record;
    }

    public Company getCompany(String code){
        Connection connection = null;
        Company record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{

            param.add(code); type.add("STRING");
            
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection,
                    DatabaseQueryLoader.getQueryAssignedConstant().COMPANY_SELECT_BY_CODE,
                    param, type);

            if(rst.next()){
                Company company = new Company();
                company.setCode(rst.getString("code"));
                company.setName(rst.getString("company_name"));
                company.setLegalName(rst.getString("legal_name"));
                company.setAddress1(rst.getString("address1"));
                company.setAddress2(rst.getString("address2"));
                company.setCity(rst.getString("city"));
                company.setState(rst.getString("state"));
                company.setCountry(rst.getString("country"));
                company.setPhone(rst.getString("phone"));
                company.setFax(rst.getString("fax"));
                company.setEmail(rst.getString("email"));
                company.setWebsite(rst.getString("website"));
                company.setLogo(rst.getBytes("logo"));
                company.setLogoName(rst.getString("logo_name"));
                company.setLeaveLimit(rst.getInt("leave_limit"));
                company.setDailyWorkingHours(rst.getInt("daily_working_hours"));
                company.setUseDefaultWorkflow(rst.getInt("use_default_workflow"));

                record = company;
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return record;
    }

    public int createNewCompany(Company company){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(company.getCode()); type.add("STRING");
            param.add(company.getName()); type.add("STRING");
            param.add(company.getLegalName()); type.add("STRING");

            param.add(company.getAddress1()); type.add("STRING");
            param.add(company.getAddress2()); type.add("STRING");
            param.add(company.getCity()); type.add("STRING");
            param.add(company.getState()); type.add("STRING");
            param.add(company.getCountry()); type.add("STRING");
            param.add(company.getPhone()); type.add("STRING");
            param.add(company.getFax()); type.add("STRING");

            param.add(company.getEmail()); type.add("STRING");
            param.add(company.getWebsite()); type.add("STRING");
            param.add(company.getLogo()); type.add("BYTES");
            param.add(company.getLogoName()); type.add("STRING");

            param.add(company.getLeaveLimit()); type.add("NUMBER");
            param.add(company.getDailyWorkingHours()); type.add("NUMBER");

            param.add(company.getUseDefaultWorkflow()); type.add("NUMBER");

            System.out.println("QUERY:" + DatabaseQueryLoader.getQueryAssignedConstant().COMPANY_INSERT);
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().COMPANY_INSERT,
                    param, type);

            return i;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }//:~

    public int updateCompany(Company company, boolean changeLogo){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            String query = "";

            
            param.add(company.getName()); type.add("STRING");
            param.add(company.getLegalName()); type.add("STRING");

            param.add(company.getAddress1()); type.add("STRING");
            param.add(company.getAddress2()); type.add("STRING");
            param.add(company.getCity()); type.add("STRING");
            param.add(company.getState()); type.add("STRING");
            param.add(company.getCountry()); type.add("STRING");
            param.add(company.getPhone()); type.add("STRING");
            param.add(company.getFax()); type.add("STRING");

            param.add(company.getEmail()); type.add("STRING");
            param.add(company.getWebsite()); type.add("STRING");

            if(changeLogo == true){
                param.add(company.getLogo()); type.add("BYTES");
                param.add(company.getLogoName()); type.add("STRING");
                query = DatabaseQueryLoader.getQueryAssignedConstant().COMPANY_UPDATE_WITH_LOGO;
            }else{
                query = DatabaseQueryLoader.getQueryAssignedConstant().COMPANY_UPDATE;
            }

            param.add(company.getLeaveLimit()); type.add("NUMBER");
            param.add(company.getDailyWorkingHours()); type.add("NUMBER");
            param.add(company.getUseDefaultWorkflow()); type.add("NUMBER");

            param.add(company.getCode()); type.add("STRING");
            
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, query,
                    param, type);
            
            return i;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }//:~


    public EmailMessage getEmailMessageByCode(String code){
        Connection connection = null;
        EmailMessage record = new EmailMessage();
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(code);
            type.add("STRING");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMAIL_CONTENT_SELECT_BY_CODE,
                    param, type);

            while(rst.next()){
                EmailMessage email = new EmailMessage();
                email.setCode(rst.getString("code"));
                email.setSubject(rst.getString("subject"));
                email.setMessage(rst.getString("message_body"));
                email.setSenderEmail(rst.getString("sender_email"));
                email.setSenderName(rst.getString("sender_name"));
                record = email;
            }
        }catch(Exception e){

        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return record;
    }
}
