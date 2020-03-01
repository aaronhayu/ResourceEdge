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
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import org.tenece.web.common.DateUtility;
import org.tenece.web.data.Employee;

/**
 *
 * @author jeffry.amachree
 */
public class EmployeeDAO extends BaseDAO {
    
    /** Creates a new instance of EmployeeDAO */
    public EmployeeDAO() {
    }

    public List<Employee> getAllEmployeeBasic(){
        return getAllEmployeeBasic(null, null);
    }
    public List<Employee> getAllEmployeeBasic(String criteria, String searchText){
        Connection connection = null;
        List<Employee> records = new ArrayList<Employee>();
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            connection = this.getConnection(true);

            ResultSet rst = null;
            if(criteria == null || searchText == null){
                rst = this.executeParameterizedQuery(connection,
                        DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_SELECT_ALL_FOR_BASIC,
                        param, type);
            }else{
                String query = "";
                //param.add(searchText); type.add("STRING");
                
                if(criteria.equalsIgnoreCase("FN")){
                    query = DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_SELECT_ALL_FOR_BASIC_SEARCH_FNAME;
                }else if(criteria.equalsIgnoreCase("LN")){
                    query = DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_SELECT_ALL_FOR_BASIC_SEARCH_LNAME;
                }else if(criteria.equalsIgnoreCase("ID")){
                    query = DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_SELECT_ALL_FOR_BASIC_SEARCH_ID;
                }else if(criteria.equalsIgnoreCase("COMPANY")){
                    query = DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_SELECT_ALL_FOR_BASIC_SEARCH_COMPANY;
                }
                query = query.replaceAll("_SEARCH_", searchText);

                System.out.println("One search Query...:" + query);
                
                rst = this.executeParameterizedQuery(connection, query,
                        param, type);
            }
            
            while(rst.next()){
                Employee item = new Employee();
                item.setEmployeeNumber(rst.getLong("emp_number"));
                item.setEmployeeID(rst.getLong("emp_id"));
                item.setDepartmentId(rst.getInt("deptid"));
                item.setJobTitleId(rst.getInt("jobtitleid"));
                item.setEmployeeTypeId(rst.getInt("employeetypeid"));
                item.setCategoryId(rst.getInt("categoryid"));
                item.setSalutation(rst.getString("salutation"));
                
                item.setLastName(rst.getString("lastname"));
                item.setFirstName(rst.getString("firstname"));
                item.setDateOfBirth(rst.getDate("dob"));
                
                item.setGender(rst.getString("gender"));
                item.setMaritalStatus(rst.getString("marital"));
                item.setActive(rst.getInt("active"));

                item.setEmploymentDate(new Date(rst.getDate("employment_date").getTime()));
                item.setConfirmationDate(new Date(rst.getDate("confirmation_date").getTime()));
                item.setConfirmationStatus(rst.getInt("confirmed"));
                item.setEmploymentStatus(rst.getString("employment_status"));
                item.setEmail(rst.getString("email"));
                item.setSalaryGradeId(rst.getInt("salarygradeid"));
                item.setCompanyCode(rst.getString("company_code"));
                records.add(item);
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
        return records;
    }

    public List<Employee> getAllEmployeeBasicByCompany(String code){
        return getAllEmployeeBasicByCompany(code, null, null);
    }
    public List<Employee> getAllEmployeeBasicByCompany(String code, String criteria, String searchText){
        Connection connection = null;
        List<Employee> records = new ArrayList<Employee>();
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            connection = this.getConnection(true);

            param.add(code); type.add("STRING");

            ResultSet rst = null;
            if(criteria == null || searchText == null){
                rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_SELECT_ALL_FOR_BASIC_BY_COMPANY,
                        param, type);
            }else{
                String query = "";
                //param.add(searchText); type.add("STRING");

                if(criteria.equalsIgnoreCase("FN")){
                    query = DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_SELECT_ALL_FOR_BASIC_BY_COMPANY_SEARCH_FNAME;
                }else if(criteria.equalsIgnoreCase("LN")){
                    query = DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_SELECT_ALL_FOR_BASIC_BY_COMPANY_SEARCH_LNAME;
                }else if(criteria.equalsIgnoreCase("ID")){
                    query = DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_SELECT_ALL_FOR_BASIC_BY_COMPANY_SEARCH_ID;
                }else if(criteria.equalsIgnoreCase("COMPANY")){
                    query = DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_SELECT_ALL_FOR_BASIC_BY_COMPANY_SEARCH_COMPANY;
                }
                query = query.replaceAll("_SEARCH_", searchText);

                System.out.println("One search Query...:" + query);

                rst = this.executeParameterizedQuery(connection, query,
                        param, type);
            }

            while(rst.next()){
                Employee item = new Employee();
                item.setEmployeeNumber(rst.getLong("emp_number"));
                item.setEmployeeID(rst.getLong("emp_id"));
                item.setDepartmentId(rst.getInt("deptid"));
                item.setJobTitleId(rst.getInt("jobtitleid"));
                item.setEmployeeTypeId(rst.getInt("employeetypeid"));
                item.setCategoryId(rst.getInt("categoryid"));
                item.setSalutation(rst.getString("salutation"));

                item.setLastName(rst.getString("lastname"));
                item.setFirstName(rst.getString("firstname"));
                item.setDateOfBirth(rst.getDate("dob"));

                item.setGender(rst.getString("gender"));
                item.setMaritalStatus(rst.getString("marital"));
                item.setActive(rst.getInt("active"));

                item.setEmploymentDate(new Date(rst.getDate("employment_date").getTime()));
                item.setConfirmationDate(new Date(rst.getDate("confirmation_date").getTime()));
                item.setConfirmationStatus(rst.getInt("confirmed"));
                item.setEmploymentStatus(rst.getString("employment_status"));
                item.setEmail(rst.getString("email"));
                item.setSalaryGradeId(rst.getInt("salarygradeid"));
                item.setCompanyCode(rst.getString("company_code"));
                item.setEmail(rst.getString("company_code"));

                records.add(item);
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
        return records;
    }

    public List<Employee> getAllUnconfirmedEmployee(String criteria, String searchText){
        Connection connection = null;
        List<Employee> records = new ArrayList<Employee>();
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            connection = this.getConnection(true);

            ResultSet rst = null;
            if(criteria == null || searchText == null){
                rst = this.executeParameterizedQuery(connection,
                        DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_SELECT_ALL_UNCONFIRMED,
                        param, type);
            }else{
                String query = "";
                //param.add(searchText); type.add("STRING");

                if(criteria.equalsIgnoreCase("FN")){
                    query = DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_SELECT_ALL_UNCONFIRMED_SEARCH_FNAME;
                }else if(criteria.equalsIgnoreCase("LN")){
                    query = DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_SELECT_ALL_UNCONFIRMED_SEARCH_LNAME;
                }else if(criteria.equalsIgnoreCase("ID")){
                    query = DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_SELECT_ALL_UNCONFIRMED_SEARCH_ID;
                }
                query = query.replaceAll("_SEARCH_", searchText);

                //System.out.println("Onw search Query...:" + query);

                rst = this.executeParameterizedQuery(connection, query,
                        param, type);
            }

            while(rst.next()){
                Employee item = new Employee();
                item.setEmployeeNumber(rst.getLong("emp_number"));
                item.setEmployeeID(rst.getLong("emp_id"));
                item.setDepartmentId(rst.getInt("deptid"));
                item.setJobTitleId(rst.getInt("jobtitleid"));
                item.setEmployeeTypeId(rst.getInt("employeetypeid"));
                item.setCategoryId(rst.getInt("categoryid"));
                item.setSalutation(rst.getString("salutation"));

                item.setLastName(rst.getString("lastname"));
                item.setFirstName(rst.getString("firstname"));
                item.setDateOfBirth(rst.getDate("dob"));

                item.setGender(rst.getString("gender"));
                item.setMaritalStatus(rst.getString("marital"));
                item.setActive(rst.getInt("active"));

                item.setEmploymentDate(new Date(rst.getDate("employment_date").getTime()));
                item.setConfirmationDate(new Date(rst.getDate("confirmation_date").getTime()));
                item.setConfirmationStatus(rst.getInt("confirmed"));
                item.setEmploymentStatus(rst.getString("employment_status"));
                item.setEmail(rst.getString("email"));
                item.setSalaryGradeId(rst.getInt("salarygradeid"));
                item.setCompanyCode(rst.getString("company_code"));

                records.add(item);
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
        return records;
    }

    public List<Employee> getAllUnconfirmedEmployeeByCompany(String code, String criteria, String searchText){
        Connection connection = null;
        List<Employee> records = new ArrayList<Employee>();
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(code); type.add("STRING");
            
            connection = this.getConnection(true);

            ResultSet rst = null;
            if(criteria == null || searchText == null){
                rst = this.executeParameterizedQuery(connection, 
                        DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_SELECT_ALL_UNCONFIRMED_BY_COMPANY,
                        param, type);
            }else{
                String query = "";
                //param.add(searchText); type.add("STRING");

                if(criteria.equalsIgnoreCase("FN")){
                    query = DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_SELECT_ALL_UNCONFIRMED_BY_COMPANY_SEARCH_FNAME;
                }else if(criteria.equalsIgnoreCase("LN")){
                    query = DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_SELECT_ALL_UNCONFIRMED_BY_COMPANY_SEARCH_LNAME;
                }else if(criteria.equalsIgnoreCase("ID")){
                    query = DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_SELECT_ALL_UNCONFIRMED_BY_COMPANY_SEARCH_ID;
                }
                query = query.replaceAll("_SEARCH_", searchText);

                //System.out.println("Onw search Query...:" + query);

                rst = this.executeParameterizedQuery(connection, query,
                        param, type);
            }

            while(rst.next()){
                Employee item = new Employee();
                item.setEmployeeNumber(rst.getLong("emp_number"));
                item.setEmployeeID(rst.getLong("emp_id"));
                item.setDepartmentId(rst.getInt("deptid"));
                item.setJobTitleId(rst.getInt("jobtitleid"));
                item.setEmployeeTypeId(rst.getInt("employeetypeid"));
                item.setCategoryId(rst.getInt("categoryid"));
                item.setSalutation(rst.getString("salutation"));

                item.setLastName(rst.getString("lastname"));
                item.setFirstName(rst.getString("firstname"));
                item.setDateOfBirth(rst.getDate("dob"));

                item.setGender(rst.getString("gender"));
                item.setMaritalStatus(rst.getString("marital"));
                item.setActive(rst.getInt("active"));

                item.setEmploymentDate(new Date(rst.getDate("employment_date").getTime()));
                item.setConfirmationDate(new Date(rst.getDate("confirmation_date").getTime()));
                item.setConfirmationStatus(rst.getInt("confirmed"));
                item.setEmploymentStatus(rst.getString("employment_status"));
                item.setEmail(rst.getString("email"));
                item.setSalaryGradeId(rst.getInt("salarygradeid"));
                item.setCompanyCode(rst.getString("company_code"));

                records.add(item);
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
        return records;
    }

    public List<Employee> getAllTerminatedEmployee(){
        Connection connection = null;
        List<Employee> records = new ArrayList<Employee>();
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            connection = this.getConnection(true);

            ResultSet rst = null;
            rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_SELECT_ALL_TERMINATED,
                        param, type); 


            while(rst.next()){
                Employee item = new Employee();
                item.setEmployeeNumber(rst.getLong("emp_number"));
                item.setEmployeeID(rst.getLong("emp_id"));
                item.setDepartmentId(rst.getInt("deptid"));
                item.setJobTitleId(rst.getInt("jobtitleid"));
                item.setEmployeeTypeId(rst.getInt("employeetypeid"));
                item.setCategoryId(rst.getInt("categoryid"));
                item.setSalutation(rst.getString("salutation"));

                item.setLastName(rst.getString("lastname"));
                item.setFirstName(rst.getString("firstname"));
                item.setDateOfBirth(rst.getDate("dob"));

                item.setGender(rst.getString("gender"));
                item.setMaritalStatus(rst.getString("marital"));
                item.setActive(rst.getInt("active"));

                item.setEmploymentDate(new Date(rst.getDate("employment_date").getTime()));
                item.setConfirmationDate(new Date(rst.getDate("confirmation_date").getTime()));
                item.setConfirmationStatus(rst.getInt("confirmed"));
                item.setEmploymentStatus(rst.getString("employment_status"));
                item.setCompanyCode(rst.getString("company_code"));

                records.add(item);
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
        return records;
    }

    public List<Employee> getAllTerminatedEmployeeByCompany(String code){
        Connection connection = null;
        List<Employee> records = new ArrayList<Employee>();
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(code); type.add("STRING");

            connection = this.getConnection(true);

            ResultSet rst = null;
            rst = this.executeParameterizedQuery(connection, 
                    DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_SELECT_ALL_TERMINATED_BY_COMPANY,
                        param, type);


            while(rst.next()){
                Employee item = new Employee();
                item.setEmployeeNumber(rst.getLong("emp_number"));
                item.setEmployeeID(rst.getLong("emp_id"));
                item.setDepartmentId(rst.getInt("deptid"));
                item.setJobTitleId(rst.getInt("jobtitleid"));
                item.setEmployeeTypeId(rst.getInt("employeetypeid"));
                item.setCategoryId(rst.getInt("categoryid"));
                item.setSalutation(rst.getString("salutation"));

                item.setLastName(rst.getString("lastname"));
                item.setFirstName(rst.getString("firstname"));
                item.setDateOfBirth(rst.getDate("dob"));

                item.setGender(rst.getString("gender"));
                item.setMaritalStatus(rst.getString("marital"));
                item.setActive(rst.getInt("active"));

                item.setEmploymentDate(new Date(rst.getDate("employment_date").getTime()));
                item.setConfirmationDate(new Date(rst.getDate("confirmation_date").getTime()));
                item.setConfirmationStatus(rst.getInt("confirmed"));
                item.setEmploymentStatus(rst.getString("employment_status"));
                item.setCompanyCode(rst.getString("company_code"));

                records.add(item);
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
        return records;
    }

    public Employee getEmployeeBasicDataById(long id){
        Connection connection = null;
        Employee record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection,
                    DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_SELECT_BASIC,
                    param, type);
            
            while(rst.next()){
                Employee item = new Employee();
                item.setEmployeeNumber(id);
                item.setEmployeeID(rst.getLong("emp_id"));
                item.setDepartmentId(rst.getInt("deptid"));
                item.setJobTitleId(rst.getInt("jobtitleid"));
                item.setEmployeeTypeId(rst.getInt("employeetypeid"));
                item.setCategoryId(rst.getInt("categoryid"));
                item.setSalutation(rst.getString("salutation"));
                
                item.setLastName(rst.getString("lastname"));
                item.setFirstName(rst.getString("firstname"));
                item.setDateOfBirth(new java.util.Date(rst.getDate("dob").getTime()));
                
                item.setGender(rst.getString("gender"));
                item.setMaritalStatus(rst.getString("marital"));
                item.setActive(rst.getInt("active"));
                
                item.setEmploymentDate(new Date(rst.getDate("employment_date").getTime()));
                item.setConfirmationDate(new Date(rst.getDate("confirmation_date").getTime()));
                item.setConfirmationStatus(rst.getInt("confirmed"));
                item.setEmploymentStatus(rst.getString("employment_status"));
                item.setEmail(rst.getString("email"));
                item.setSalaryGradeId(rst.getInt("salarygradeid"));
                item.setCompanyCode(rst.getString("company_code"));
                record = item;
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
    public Hashtable getTransactionInitiator(long transactionRef, long initiatorId){
        Connection connection = null;
        Hashtable keys = new Hashtable();
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(transactionRef);
            type.add("NUMBER");
            param.add(initiatorId);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection,
                    DatabaseQueryLoader.getQueryAssignedConstant().TRANSITION_INITIATOR_INFO,
                    param, type);
            
            while(rst.next()){
                  keys.put("EMAIL_TRANSACTION_DATE", DateUtility.getDateAsString(rst.getDate("action_date"),"dd/MM/yyyy"));
                  keys.put("EMAIL_TRANSACTION_INITIATED_BY", rst.getString("employee_name"));
                
            }
        }catch(Exception e){
            
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return keys;
    }
    
    public Employee getEmployeeBasicDataByEmpNumber(int id){
        Connection connection = null;
        Employee record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection,
                    DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_SELECT_FOR_ID,
                    param, type);
            
            while(rst.next()){
                Employee item = new Employee();
                item.setEmployeeNumber(rst.getLong("emp_number"));
                item.setEmployeeID(rst.getLong("emp_id"));
                item.setDepartmentId(rst.getInt("deptid"));
                item.setJobTitleId(rst.getInt("jobtitleid"));
                item.setEmployeeTypeId(rst.getInt("employeetypeid"));
                item.setCategoryId(rst.getInt("categoryid"));
                item.setSalutation(rst.getString("salutation"));
                
                item.setLastName(rst.getString("lastname"));
                item.setFirstName(rst.getString("firstname"));
                item.setDateOfBirth(new java.util.Date(rst.getDate("dob").getTime()));
                
                item.setGender(rst.getString("gender"));
                item.setMaritalStatus(rst.getString("marital"));
                item.setActive(rst.getInt("active"));

                item.setEmploymentDate(new Date(rst.getDate("employment_date").getTime()));
                item.setConfirmationDate(new Date(rst.getDate("confirmation_date").getTime()));
                item.setConfirmationStatus(rst.getInt("confirmed"));
                item.setEmploymentStatus(rst.getString("employment_status"));
                item.setEmail(rst.getString("email"));
                item.setSalaryGradeId(rst.getInt("salarygradeid"));
                item.setCompanyCode(rst.getString("company_code"));
                
                record = item;
            }
        }catch(Exception e){
            record = null;
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

    public Employee getEmployeeBasicDataByEmpNumberAndCompany(int id, String companyCode){
        Connection connection = null;
        Employee record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id); type.add("NUMBER");
            param.add(companyCode); type.add("STRING");

            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection,
                    DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_SELECT_FOR_ID_AND_COMPANY,
                    param, type);

            while(rst.next()){
                Employee item = new Employee();
                item.setEmployeeNumber(rst.getLong("emp_number"));
                item.setEmployeeID(rst.getLong("emp_id"));
                item.setDepartmentId(rst.getInt("deptid"));
                item.setJobTitleId(rst.getInt("jobtitleid"));
                item.setEmployeeTypeId(rst.getInt("employeetypeid"));
                item.setCategoryId(rst.getInt("categoryid"));
                item.setSalutation(rst.getString("salutation"));

                item.setLastName(rst.getString("lastname"));
                item.setFirstName(rst.getString("firstname"));
                item.setDateOfBirth(new java.util.Date(rst.getDate("dob").getTime()));

                item.setGender(rst.getString("gender"));
                item.setMaritalStatus(rst.getString("marital"));
                item.setActive(rst.getInt("active"));

                item.setEmploymentDate(new Date(rst.getDate("employment_date").getTime()));
                item.setConfirmationDate(new Date(rst.getDate("confirmation_date").getTime()));
                item.setConfirmationStatus(rst.getInt("confirmed"));
                item.setEmploymentStatus(rst.getString("employment_status"));
                item.setEmail(rst.getString("email"));
                item.setSalaryGradeId(rst.getInt("salarygradeid"));
                item.setCompanyCode(rst.getString("company_code"));

                record = item;
            }
        }catch(Exception e){
            record = null;
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

    public Employee getEmployeeContactDataById(long id){
        Connection connection = null;
        Employee record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_SELECT_CONTACT,
                    param, type);
            
            while(rst.next()){
                Employee item = new Employee();
                item.setEmployeeNumber(id);
                item.setAddress1(rst.getString("address1"));
                item.setAddress2(rst.getString("address2"));
                item.setCity(rst.getString("city"));
                item.setState(rst.getString("state_of_address"));
                item.setZipCode(rst.getString("zipcode"));
                
                item.setCountry(rst.getString("country"));
                item.setEmail(rst.getString("email"));
                item.setHomePhone(rst.getString("homephone"));
                
                item.setOfficePhone(rst.getString("officephone"));
                item.setCellPhone(rst.getString("cellphone"));
                
                record = item;
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
    
    public int createEmployee_WithBasic(Employee employee){
        Connection connection = null;
        Employee record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        
        try{
            param.add(employee.getEmployeeID()); type.add("LONG");
            param.add(employee.getDepartmentId()); type.add("NUMBER");
            param.add(employee.getJobTitleId()); type.add("NUMBER");
            param.add(employee.getEmployeeTypeId()); type.add("NUMBER");
            param.add(employee.getCategoryId()); type.add("NUMBER");
            param.add(employee.getSalutation()); type.add("STRING");
            param.add(employee.getLastName()); type.add("STRING");
            param.add(employee.getFirstName()); type.add("STRING");
            param.add(employee.getDateOfBirth()); type.add("DATE");
            param.add(employee.getGender()); type.add("STRING");
            param.add(employee.getMaritalStatus()); type.add("STRING");
            //param.add(employee.getActive()); type.add("NUMBER");
            param.add(employee.getEmploymentDate()); type.add("DATE");
            param.add(employee.getEmail()); type.add("STRING");
            param.add(employee.getSalaryGradeId()); type.add("NUMBER");
            param.add(employee.getCompanyCode()); type.add("STRING");
            
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection,
                    DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_INSERT_BASIC,
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
    public int createEmployee_Contact(Employee employee){
        Connection connection = null;
        Employee record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        //address1, address2, city, state_of_address, zipcode, country, email, homephone, officephone, cellphone
        try{
            param.add(employee.getAddress1()); type.add("STRING");
            param.add(employee.getAddress2()); type.add("STRING");
            param.add(employee.getCity()); type.add("STRING");
            param.add(employee.getState()); type.add("STRING");
            param.add(employee.getZipCode()); type.add("STRING");
            param.add(employee.getCountry()); type.add("STRING");
            //param.add(employee.getEmail()); type.add("STRING");
            param.add(employee.getHomePhone()); type.add("STRING");
            param.add(employee.getOfficePhone()); type.add("STRING");
            param.add(employee.getCellPhone()); type.add("STRING");
            
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_INSERT_CONTACTS,
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

    public int deactivateEmployee(List<Long> terminate){
        Connection connection = null;
        Vector param =new Vector(); 
        Vector type = new Vector();
        try{

            connection = this.getConnection(false);
            startTransaction(connection);
            for(long id : terminate){
                param =new Vector();
                type = new Vector();
                //set parameter
                param.add(id); type.add("NUMBER");
                //execute query
                int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_DELETE,
                        param, type);

                if(i == 0){
                    throw new Exception("Unable to deactivate employee");
                }

                //USER_UPDATE_PASSWORD_FOR_USER_DEACTIVATION
                param =new Vector();
                type = new Vector();
                //set parameter
                param.add(new Date()); type.add("DATE");
                param.add(id); type.add("NUMBER");
                i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().USER_UPDATE_PASSWORD_FOR_USER_DEACTIVATION,
                        param, type);
                
            }

            commitTransaction(connection);
            return terminate.size();
        }catch(Exception e){
            try{ rollbackTransaction(connection); }catch(Exception er){}
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

    public int updateEmployeeBasic(Employee employee){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(employee.getEmployeeID()); type.add("LONG");
            param.add(employee.getDepartmentId()); type.add("NUMBER");
            param.add(employee.getJobTitleId()); type.add("NUMBER");
            param.add(employee.getEmployeeTypeId()); type.add("NUMBER");
            param.add(employee.getCategoryId()); type.add("NUMBER");
            param.add(employee.getSalutation()); type.add("STRING");
            param.add(employee.getLastName()); type.add("STRING");
            param.add(employee.getFirstName()); type.add("STRING");
            param.add(employee.getDateOfBirth()); type.add("DATE");
            param.add(employee.getGender()); type.add("STRING");
            param.add(employee.getMaritalStatus()); type.add("STRING");
            param.add(employee.getActive()); type.add("NUMBER");
            param.add(employee.getEmploymentDate()); type.add("DATE");
            param.add(employee.getEmail()); type.add("STRING");
            param.add(employee.getSalaryGradeId()); type.add("NUMBER");
            param.add(employee.getEmployeeNumber()); type.add("NUMBER");

            System.out.println("Query to update:" + DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_UPDATE_BASIC);

            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_UPDATE_BASIC,
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

    public int updateUnconfirmedEmployeeStatus(Employee employee){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{

            param.add(employee.getConfirmationDate()); type.add("DATE");
            param.add(employee.getConfirmationStatus()); type.add("NUMBER");
            param.add(employee.getEmployeeNumber()); type.add("NUMBER");

            System.out.println("Query to update:" + DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_UPDATE_UNCONFIRMED);

            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_UPDATE_UNCONFIRMED,
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
    
    public int updateEmployeeContact(Employee employee){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(employee.getAddress1()); type.add("STRING");
            param.add(employee.getAddress2()); type.add("STRING");
            param.add(employee.getCity()); type.add("STRING");
            param.add(employee.getState()); type.add("STRING");
            param.add(employee.getZipCode()); type.add("STRING");
            param.add(employee.getCountry()); type.add("STRING");
            //param.add(employee.getEmail()); type.add("STRING");
            param.add(employee.getHomePhone()); type.add("STRING");
            param.add(employee.getOfficePhone()); type.add("STRING");
            param.add(employee.getCellPhone()); type.add("STRING");
            param.add(employee.getEmployeeNumber()); type.add("NUMBER");
            
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_UPDATE_CONTACTS,
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

    public List<Employee.Termination> getListOfTermination(){
        Connection connection = null;
        List<Employee.Termination> records = new ArrayList<Employee.Termination>();
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            connection = this.getConnection(true);

            ResultSet rst = null;
            rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_TERMINATION_SELECT,
                        param, type);
            
            while(rst.next()){
                Employee.Termination item = new Employee.Termination();
                item.setId(rst.getLong("id"));
                item.setEmployeeNumber(rst.getLong("emp_number"));
                item.setOperation(rst.getString("operation"));
                item.setReason(rst.getString("reason"));
                item.setEffectiveDate(new Date(rst.getDate("effective_date").getTime()));
                item.setPeriod(rst.getString("effective_period"));
                item.setTransactionId(rst.getLong("transactionid"));
                item.seteStatus(rst.getString("estatus"));

                item.setFirstName(rst.getString("firstname"));
                item.setLastName(rst.getString("lastname"));

                records.add(item);
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
        return records;
    }

    public List<Employee.Termination> getListOfTerminationByCompany(String code){
        Connection connection = null;
        List<Employee.Termination> records = new ArrayList<Employee.Termination>();
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            connection = this.getConnection(true);

            param.add(code); type.add("STRING");

            ResultSet rst = null;
            rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_TERMINATION_SELECT_BY_COMPANY,
                        param, type);

            while(rst.next()){
                Employee.Termination item = new Employee.Termination();
                item.setId(rst.getLong("id"));
                item.setEmployeeNumber(rst.getLong("emp_number"));
                item.setOperation(rst.getString("operation"));
                item.setReason(rst.getString("reason"));
                item.setEffectiveDate(new Date(rst.getDate("effective_date").getTime()));
                item.setPeriod(rst.getString("effective_period"));
                item.setTransactionId(rst.getLong("transactionid"));
                item.seteStatus(rst.getString("estatus"));

                item.setFirstName(rst.getString("firstname"));
                item.setLastName(rst.getString("lastname"));

                records.add(item);
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
        return records;
    }

    public Employee.Termination getTerminationById(long id){
        Connection connection = null;
        try{
            connection = this.getConnection(true);
            return getTerminationById(id, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_TERMINATION_SELECT_BY_ID, connection);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public Employee.Termination getTerminationByTransaction(long id){
        Connection connection = null;
        try{
            connection = this.getConnection(true);
            return getTerminationById(id, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_TERMINATION_SELECT_BY_TRANSACTION, connection);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public Employee.Termination getTerminationByTransaction(long id, Connection connection){
        return getTerminationById(id, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_TERMINATION_SELECT_BY_TRANSACTION, connection);
    }

    public Employee.Termination getTerminationById(long id, String query, Connection connection){
        Employee.Termination records = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            
            //set parameter
            param.add(id); type.add("NUMBER");
            //init resultset and execute query
            ResultSet rst = null;
            rst = this.executeParameterizedQuery(connection, query,
                        param, type);

            if(rst.next()){
                Employee.Termination item = new Employee.Termination();
                item.setId(rst.getLong("id"));
                item.setEmployeeNumber(rst.getLong("emp_number"));
                item.setOperation(rst.getString("operation"));
                item.setReason(rst.getString("reason"));
                item.setEffectiveDate(new Date(rst.getDate("effective_date").getTime()));
                item.setPeriod(rst.getString("effective_period"));
                item.setTransactionId(rst.getLong("transactionid"));
                item.seteStatus(rst.getString("estatus"));

                item.setFirstName(rst.getString("firstname"));
                item.setLastName(rst.getString("lastname"));

                records = item;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return records;
    }//:~



    public int updateTermination(Employee.Termination terminate){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            connection = this.getConnection(false);
            startTransaction(connection);

            param.add(terminate.getEmployeeNumber()); type.add("NUMBER");
            param.add(terminate.getOperation()); type.add("STRING");
            param.add(terminate.getReason()); type.add("STRING");
            param.add(terminate.getEffectiveDate()); type.add("DATE");
            param.add(terminate.getPeriod()); type.add("STRING");

            param.add(terminate.getId()); type.add("NUMBER");

            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_TERMINATION_UPDATE,
                    param, type);

            param = new Vector(); type = new Vector();
            param.add(terminate.getOperation()); type.add("STRING");
            param.add(terminate.getEmployeeNumber()); type.add("NUMBER");

            i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_UPDATE_EMPLOYMENT_STATUS, param, type);
            commitTransaction(connection);

            return i;
        }catch(Exception e){
            e.printStackTrace();
            try{ rollbackTransaction(connection); }catch(Exception ex){}
            return 0;
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }//:~

    public int updateTerminationForTransaction(Employee.Termination terminate){
        Connection connection = null;
        try{
            connection = this.getConnection(false);
            startTransaction(connection);

            //process info
            int i = updateTerminationForTransaction(terminate, connection);

            commitTransaction(connection);

            return i;
        }catch(Exception e){
            e.printStackTrace();
            try{ rollbackTransaction(connection); }catch(Exception ex){}
            return 0;
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }
    public int updateTerminationForTransaction(Employee.Termination terminate, Connection connection) throws Exception{
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            
            param.add(terminate.geteStatus()); type.add("STRING");
            param.add(terminate.getTransactionId()); type.add("NUMBER");

            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_TERMINATION_TRANSACTION_UPDATE,
                    param, type);

            if(!terminate.geteStatus().trim().equals("R")){
                param = new Vector(); type = new Vector();
                param.add(terminate.getOperation()); type.add("STRING");
                param.add(terminate.getEmployeeNumber()); type.add("NUMBER");

                i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_UPDATE_EMPLOYMENT_STATUS, param, type);
            }
            return i;
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }//:~

    public int createNewTermination(Employee.Termination terminate){
        Connection connection = null;
        try{
            connection = this.getConnection(false);

            int rows =  this.createNewTermination(terminate, connection, 0);
            if(rows == 0){
                throw new Exception("Unable to save data");
            }
            commitTransaction(connection);
            return rows;
        }catch(Exception e){
            try{ rollbackTransaction(connection); }catch(Exception ex){}
            return 0;
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                //ex.printStackTrace();
            }
        }
    }
    /**
     *
     * @param terminate
     * @param connection
     * @param mode 0=for update of employee record 1=non-update
     * @return
     */
    public int createNewTermination(Employee.Termination terminate, Connection connection, int mode){
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            startTransaction(connection);
            
            param.add(terminate.getEmployeeNumber()); type.add("NUMBER");
            param.add(terminate.getOperation()); type.add("STRING");
            param.add(terminate.getReason()); type.add("STRING");
            param.add(terminate.getEffectiveDate()); type.add("DATE");
            param.add(terminate.getPeriod()); type.add("STRING");
            if(terminate.getTransactionId() == 0){
                param.add(terminate.getTransactionId()); type.add("NUMBER_NULL");
            }else{
                param.add(terminate.getTransactionId()); type.add("NUMBER");
            }
            param.add(terminate.geteStatus()); type.add("STRING");

            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_TERMINATION_INSERT,
                    param, type);

            if(mode == 1){
                param = new Vector(); type = new Vector();
                param.add(terminate.getOperation()); type.add("STRING");
                param.add(0); type.add("NUMBER");
                param.add(terminate.getEmployeeNumber()); type.add("NUMBER");

                i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_UPDATE_EMPLOYMENT_STATUS, param, type);
            }

            return i;

        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }//:~

    public int deleteTerminations(List<Long> terminate){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            
            connection = this.getConnection(false);
            startTransaction(connection);
            for(long id : terminate){
                param =new Vector();
                type = new Vector();
                //set parameter
                param.add(id); type.add("NUMBER");
                //execute query
                int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_TERMINATION_DELETE,
                        param, type);
            }
            commitTransaction(connection);
            return terminate.size();
        }catch(Exception e){
            try{ rollbackTransaction(connection); }catch(Exception er){}
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

    public Employee getEmployeeTransactionBioDataById(long id){
        
        Connection connection = null;
        Employee record = null;
        try{
            connection = this.getConnection(true);
            record = getEmployeeTransactionBioDataById(id, connection);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return record;
    }
    public Employee getEmployeeTransactionBioDataById(long id, Connection connection){
        
        Employee record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_BIO_TRANSACTION_SELECT_BY_TRANSACTION,
                    param, type);

            if(rst.next()){
                Employee item = new Employee();
                item.setTransactionId(id);
                item.setEmployeeNumber(rst.getLong("emp_number"));
                item.setSalutation(rst.getString("salutation"));

                item.setLastName(rst.getString("lastname"));
                item.setFirstName(rst.getString("firstname"));
                item.setDateOfBirth(new java.util.Date(rst.getDate("dob").getTime()));

                item.setGender(rst.getString("gender"));
                item.setMaritalStatus(rst.getString("marital"));

                item.setAddress1(rst.getString("address1"));
                item.setAddress2(rst.getString("address2"));
                item.setCity(rst.getString("city"));
                item.setState(rst.getString("state_of_address"));
                item.setZipCode(rst.getString("zipcode"));

                item.setCountry(rst.getString("country"));
                item.setEmail(rst.getString("email"));
                item.setHomePhone(rst.getString("homephone"));

                item.setOfficePhone(rst.getString("officephone"));
                item.setCellPhone(rst.getString("cellphone"));

                record = item;
            }
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
        return record;
    }

    public int updateEmployeeTransactionBioData(Employee employee, Connection connection){
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(employee.getSalutation()); type.add("STRING");
            param.add(employee.getLastName()); type.add("STRING");
            param.add(employee.getFirstName()); type.add("STRING");
            param.add(employee.getDateOfBirth()); type.add("DATE");
            param.add(employee.getGender()); type.add("STRING");
            param.add(employee.getMaritalStatus()); type.add("STRING");

            param.add(employee.getAddress1()); type.add("STRING");
            param.add(employee.getAddress2()); type.add("STRING");
            param.add(employee.getCity()); type.add("STRING");
            param.add(employee.getState()); type.add("STRING");
            param.add(employee.getZipCode()); type.add("STRING");
            param.add(employee.getCountry()); type.add("STRING");
            param.add(employee.getEmail()); type.add("STRING");
            param.add(employee.getHomePhone()); type.add("STRING");
            param.add(employee.getOfficePhone()); type.add("STRING");
            param.add(employee.getCellPhone()); type.add("STRING");
            //id to use for update
            param.add(employee.getEmployeeNumber()); type.add("NUMBER");

            System.out.println("Query to update:" + DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_BIO_TRANSACTION_UPDATE);

            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_BIO_TRANSACTION_UPDATE,
                    param, type);

            return i;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }//:~

    public int createEmployeeTransactionBioData(Employee employee){
        Connection connection = null;
        try{
            connection = this.getConnection(false);
            //pass connection to method
            return createEmployeeTransactionBioData(employee, connection);
            
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
    }

    public int updateEmployeeBioDataStatus(Employee employee, Connection connection){
        Vector param =new Vector();
        Vector type = new Vector();
        try{

            param.add(employee.geteStatus()); type.add("STRING");
            param.add(employee.getTransactionId()); type.add("NUMBER");

            System.out.println("Query to update:" + DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_BIO_TRANSACTION_UPDATE_STATUS);

            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_BIO_TRANSACTION_UPDATE_STATUS,
                    param, type);

            return i;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public int createEmployeeTransactionBioData(Employee employee, Connection connection){
        Vector param =new Vector();
        Vector type = new Vector();
        try{

            param.add(employee.getEmployeeNumber()); type.add("NUMBER");
            param.add(employee.getSalutation()); type.add("STRING");
            param.add(employee.getLastName()); type.add("STRING");
            param.add(employee.getFirstName()); type.add("STRING");
            param.add(employee.getDateOfBirth()); type.add("DATE");
            param.add(employee.getGender()); type.add("STRING");
            param.add(employee.getMaritalStatus()); type.add("STRING");

            param.add(employee.getAddress1()); type.add("STRING");
            param.add(employee.getAddress2()); type.add("STRING");
            param.add(employee.getCity()); type.add("STRING");
            param.add(employee.getState()); type.add("STRING");
            param.add(employee.getZipCode()); type.add("STRING");
            param.add(employee.getCountry()); type.add("STRING");
            param.add(employee.getEmail()); type.add("STRING");
            param.add(employee.getHomePhone()); type.add("STRING");
            param.add(employee.getOfficePhone()); type.add("STRING");
            param.add(employee.getCellPhone()); type.add("STRING");
            param.add(employee.getTransactionId()); type.add("NUMBER");
            param.add(employee.geteStatus()); type.add("STRING");

            System.out.println("Query to update:" + DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_BIO_TRANSACTION_INSERT);

            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_BIO_TRANSACTION_INSERT,
                    param, type);

            return i;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }//:~


    /* __________ Promotion ______________ */
    public List<Employee.Promotion> getAllPromotionHistory(){
        Connection connection = null;
        List<Employee.Promotion> records = new ArrayList<Employee.Promotion>();
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            connection = this.getConnection(true);

            ResultSet rst = null;
            rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().PROMOTION_HISTORY_SELECT_ALL,
                        param, type);

            while(rst.next()){
                Employee.Promotion item = new Employee.Promotion();
                item.setId(rst.getInt("idx"));
                item.setEmployeeNumber(rst.getLong("emp_number"));
                item.setJobTitleId(rst.getInt("jobid"));
                item.setSalaryGradeId(rst.getInt("salarygradeid"));
                item.setCategoryId(rst.getInt("categoryid"));
                item.setReason(rst.getString("reason"));
                item.setEffectiveDate(new Date(rst.getDate("effective_date").getTime()));
                item.seteStatus(rst.getString("estatus"));

                records.add(item);
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
        return records;
    }

    public List<Employee.Promotion> getAllPromotionHistoryByEmployee(long employeeId){
        Connection connection = null;
        List<Employee.Promotion> records = new ArrayList<Employee.Promotion>();
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            connection = this.getConnection(true);
            param.add(employeeId); type.add("NUMBER");

            ResultSet rst = null;
            rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().PROMOTION_HISTORY_SELECT_BY_EMPLOYEE,
                        param, type);

            while(rst.next()){
                Employee.Promotion item = new Employee.Promotion();
                item.setId(rst.getInt("idx"));
                item.setEmployeeNumber(rst.getLong("emp_number"));
                item.setJobTitleId(rst.getInt("jobid"));
                item.setSalaryGradeId(rst.getInt("salarygradeid"));
                item.setCategoryId(rst.getInt("categoryid"));
                item.setReason(rst.getString("reason"));
                item.setEffectiveDate(new Date(rst.getDate("effective_date").getTime()));
                item.seteStatus(rst.getString("estatus"));

                records.add(item);
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
        return records;
    }

    public Employee.Promotion getLastPromotionHistoryByEmployee(long employeeId){
        Connection connection = null;
        Employee.Promotion records = new Employee.Promotion();
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            connection = this.getConnection(true);
            param.add(employeeId); type.add("NUMBER");
            param.add(employeeId); type.add("NUMBER");
            
            ResultSet rst = null;
            rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().PROMOTION_HISTORY_SELECT_LAST_BY_EMPLOYEE,
                        param, type);

            while(rst.next()){
                Employee.Promotion item = new Employee.Promotion();
                item.setId(rst.getInt("idx"));
                item.setEmployeeNumber(rst.getLong("emp_number"));
                item.setJobTitleId(rst.getInt("jobid"));
                item.setSalaryGradeId(rst.getInt("salarygradeid"));
                item.setCategoryId(rst.getInt("categoryid"));
                item.setReason(rst.getString("reason"));
                item.setEffectiveDate(new Date(rst.getDate("effective_date").getTime()));
                item.seteStatus(rst.getString("estatus"));

                records = item;
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
        return records;
    }

    public int updateEmployeePromotion(Employee.Promotion promotion){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(promotion.getEmployeeID()); type.add("LONG");
            param.add(promotion.getJobTitleId()); type.add("NUMBER");
            param.add(promotion.getSalaryGradeId()); type.add("NUMBER");
            param.add(promotion.getCategoryId()); type.add("NUMBER");
            param.add(promotion.getReason()); type.add("STRING");
            param.add(promotion.getEffectiveDate()); type.add("DATE");
            param.add(promotion.getId()); type.add("NUMBER");

            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().PROMOTION_HISTORY_UPDATE,
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

    public int createEmployeePromotion(Employee.Promotion promotion){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            Employee employee = this.getEmployeeBasicDataById(promotion.getEmployeeNumber());
            connection = this.getConnection(false);
            //start transaction
            startTransaction(connection);
            //save new promotion detail
            param.add(promotion.getJobTitleId()); type.add("NUMBER");
            param.add(promotion.getCategoryId()); type.add("NUMBER");
            param.add(promotion.getSalaryGradeId()); type.add("NUMBER");
            param.add(promotion.getEmployeeNumber()); type.add("LONG");
            //EMPLOYEE_PROMOTION_UPDATE_BASIC
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_PROMOTION_UPDATE_BASIC,
                    param, type);
            if(i == 0) throw new Exception("Can not update employee information.");

            //save previous promotion record
            param = new Vector(); type = new Vector();
            param.add(promotion.getEmployeeNumber()); type.add("LONG");
            param.add(employee.getJobTitleId()); type.add("NUMBER");
            param.add(employee.getSalaryGradeId()); type.add("NUMBER");
            param.add(employee.getCategoryId()); type.add("NUMBER");
            param.add(promotion.getReason()); type.add("STRING");
            param.add(promotion.getEffectiveDate()); type.add("DATE");
            param.add(promotion.geteStatus()); type.add("STRING");

            i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().PROMOTION_HISTORY_INSERT,
                    param, type);
            if(i == 0) throw new Exception("Can not save history for employee promotion information.");
            //
            commitTransaction(connection);
            return i;
        }catch(Exception e){
            e.printStackTrace();
            try{ rollbackTransaction(connection); }catch(Exception er){}
            return 0;
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }//:~
}
