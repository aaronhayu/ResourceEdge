package org.tenece.hr.test;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import org.tenece.hr.data.dao.DepartmentDAO;
import org.tenece.hr.data.dao.EmployeeDAO;
import org.tenece.hr.data.dao.HolidaysDAO;
import org.tenece.hr.data.dao.SkillDAO;
import org.tenece.hr.security.SecurityEncoderImpl;
import org.tenece.web.common.ConfigReader;
import org.tenece.web.data.Department;
import org.tenece.web.data.Employee;
import org.tenece.web.data.Holiday;
import org.tenece.web.data.Skill;
import org.tenece.web.filter.ApplicationFilter;

/*
 * (c) Copyright 2009 The Tenece Professional Services.
 *
 * Created on 25 May 2009, 22:01
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

/**
 *
 * @author jeffry.amachree
 */
public class DAOTest {
    ConfigReader reader = null;
    /** Creates a new instance of DAOTest */
    public DAOTest() {
        Properties prop = new Properties();
        prop.setProperty(ApplicationFilter.APPLICATION_DATABASE_DRIVER, "com.mysql.jdbc.Driver");
        prop.setProperty(ApplicationFilter.APPLICATION_DATABASE_PASSWORD,  "");
        prop.setProperty(ApplicationFilter.APPLICATION_DATABASE_USERNAME, "root");
        prop.setProperty(ApplicationFilter.APPLICATION_DBTYPE,  "MySQL");
        prop.setProperty(ApplicationFilter.APPLICATION_DATABASE_URL, "jdbc:mysql://localhost:3306/test");
        reader = new ConfigReader();
        reader.setDatabaseConfig(prop);
    }
    
    public void testDepartment(){
        
        DepartmentDAO setup = new DepartmentDAO();
        //insert
        Department dept = new Department();
        dept.setDepartmentName("Marketing 1"); dept.setLocation("Lagos");
        dept.setManagerId(1); dept.setShortDescription("Marketing Software ");
        dept.setWorkDescription("To market software to customers");
        dept.setId(2);
        setup.updateDepartment(dept);
        //setup.createNewDepartment(dept);
        
        //select
        List<Department> depts = setup.getAllDepartments();
        System.out.println("Length of Data:" + depts.size());
    }
    
    public void testHoliday(){
        HolidaysDAO dao = new HolidaysDAO();
        Holiday hols = new Holiday();
        System.out.println("----------------------");
        hols.setDescription("Chrismas Break"); hols.setEffectiveDate(new Date());
        hols.setHoliday_length(1); hols.setRecurring(1);
        
        int i = dao.createNewHoliday(hols);
        System.out.println("Inserted..:" + i);
    }
    
    public void testSkill(){
        SkillDAO dao = new SkillDAO();
        Skill skill = new Skill();
        skill.setSkillDescription("Java Programming Language");
        skill.setSkillCode("JAVA");
        skill.setSkillName("Java Developer");
        
        dao.createNewSkill(skill);
    }
    
    public void testEmployee(){
        EmployeeDAO dao = new EmployeeDAO();
        
        Employee emp = new Employee();
        emp.setDepartmentId(1);
        emp.setCategoryId(1);
        emp.setEmployeeTypeId(1);
        emp.setJobTitleId(2);
        emp.setFirstName("Boma");
        emp.setLastName("Amachree");
        emp.setSalutation("Mr");
        emp.setDateOfBirth(new Date());
        emp.setGender("M");
        emp.setMaritalStatus("M");
        emp.setActive(1);
        
        dao.createEmployee_WithBasic(emp);
    }
    
    public static void main(String[] args){
        //DAOTest test = new DAOTest();
        //test.testEmployee();
        try{
            
            System.out.println("xtremehr:" + SecurityEncoderImpl.encryptPasswordWithAES("xtremehr"));
            System.out.println("tenece4321:" + SecurityEncoderImpl.encryptPasswordWithAES("tenece4321"));
            System.out.println("james:" + SecurityEncoderImpl.decryptPasswordWithAES("Wmt2NUdxQ2owOVA5MnNIN3hkZkpCQXpGM3duRndlVUoxOW4zeWVmS0cwMD0NCg=="));

        }catch(Exception e){
            e.printStackTrace();
        }


    }
}
