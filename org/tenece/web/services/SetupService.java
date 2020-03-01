
/*
 * (c) Copyright 2009 The Tenece Professional Services.
 *
 * Created on 27 May 2009, 08:51
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

package org.tenece.web.services;

import java.util.ArrayList;
import java.util.List;
import org.tenece.hr.data.dao.ApplicationDAO;
import org.tenece.hr.data.dao.DepartmentDAO;
import org.tenece.hr.data.dao.EmployeeCategoryDAO;
import org.tenece.hr.data.dao.EmployeeTypeDAO;
import org.tenece.hr.data.dao.HolidaysDAO;
import org.tenece.hr.data.dao.JobTitleDAO;
import org.tenece.hr.data.dao.PayFrequencyDAO;
import org.tenece.hr.data.dao.SkillDAO;
import org.tenece.hr.data.dao.SalaryGradeDAO;
import org.tenece.web.data.ControlData;
import org.tenece.web.data.Department;
import org.tenece.web.data.EmployeeCategory;
import org.tenece.web.data.EmployeeType;
import org.tenece.web.data.Holiday;
import org.tenece.web.data.JobTitle;
import org.tenece.web.data.PayFrequency;
import org.tenece.web.data.SalaryGrade;
import org.tenece.web.data.Skill;

/**
 *
 * @author jeffry.amachree
 */
public class SetupService extends BaseService{
    private DepartmentDAO departmentDAO = null;
    private HolidaysDAO holidayDAO = null;
    private JobTitleDAO jobTitleDAO = null;
    private SkillDAO skillDAO = null;
    private SalaryGradeDAO salaryGradeDAO = null;
    private EmployeeCategoryDAO employeeCategoryDAO = null;
    private EmployeeTypeDAO employeeTypeDAO = null;
    private PayFrequencyDAO payFrequencyDAO = null;
    private ApplicationDAO applicationDAO = null;
    /** Creates a new instance of SetupService */
    public SetupService() {
    }
    
    /* ------------ DEPARTMENT -------------- */
    /**
     * This methos is used to update/create a new department
     * The Paramaters required are : Department and Mode (1 or 0)
     */
    public boolean updateDepartment(Department department, int mode){
        try{
            int rows = 0;
            if(this.MODE_INSERT == mode){
                rows = getDepartmentDAO().createNewDepartment(department);
            }else if(this.MODE_UPDATE == mode){
                rows = getDepartmentDAO().updateDepartment(department);
            }
            if(rows == 0){ return false; }
            return true;
        }catch(Exception e){
            return false;
        }
    }//: updateDepartment
    
    public List<Department> findAllDepartments(){
        try{
            return getDepartmentDAO().getAllDepartments();
        }catch(Exception e){
            e.printStackTrace();
            return new ArrayList<Department>();
        }
    }
    public List<Department> findAllDepartments(String criteria, String searchText){
        try{
            return getDepartmentDAO().getAllDepartments(criteria, searchText); 
        }catch(Exception e){
            e.printStackTrace();
            return new ArrayList<Department>();
        }
    }

    public List<Department> findAllDepartmentsByCompany(String code){
        try{
            return getDepartmentDAO().getAllDepartmentsByCompany(code);
        }catch(Exception e){
            e.printStackTrace();
            return new ArrayList<Department>();
        }
    }
    public List<Department> findAllDepartmentsByCompany(String code, String criteria, String searchText){
        try{
            return getDepartmentDAO().getAllDepartmentsByCompany(code, criteria, searchText);
        }catch(Exception e){
            e.printStackTrace();
            return new ArrayList<Department>();
        }
    }

    public Department findDepartmentByID(int id){
        try{
            return getDepartmentDAO().getDepartmentById(id);
        }catch(Exception e){
            return null;
        }
    }
    
    public boolean deleteDepartment(int id){
        try{
            Department dept = new Department();
            dept.setId(id);
            getDepartmentDAO().deleteDepartment(dept);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    public boolean deleteDepartment(List<Integer> ids){
        try{
            getDepartmentDAO().deleteDepartments(ids);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    /* ------------------ HOLIDAY ------------------ */
    public boolean updateHoliday(Holiday holiday, int mode){
        try{
            if(this.MODE_INSERT == mode){
                getHolidaysDAO().createNewHoliday(holiday);
            }else if(this.MODE_UPDATE == mode){
                getHolidaysDAO().updateHoliday(holiday);
            }
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    public List<Holiday> findAllHolidays(){
        try{
            return getHolidaysDAO().getAllHolidays();
        }catch(Exception e){
            return new ArrayList<Holiday>();
        }
    }

    public List<Holiday> findAllHolidays(String criteria, String searchText){
        try{
            return getHolidaysDAO().getAllHolidays(criteria, searchText);
        }catch(Exception e){
            return new ArrayList<Holiday>();
        }
    }

    public List<Holiday> findAllHolidaysByCompany(String code){
        try{
            return getHolidaysDAO().getAllHolidaysByCompany(code);
        }catch(Exception e){
            return new ArrayList<Holiday>();
        }
    }

    public List<Holiday> findAllHolidaysByCompany(String code, String criteria, String searchText){
        try{
            return getHolidaysDAO().getAllHolidaysByCompany(code, criteria, searchText);
        }catch(Exception e){
            return new ArrayList<Holiday>();
        }
    }
    
    public Holiday findHolidayById(int id){
        try{
            return getHolidaysDAO().getHolidayById(id);
        }catch(Exception e){
            return null;
        }
    }
    
    public boolean deleteHoliday(int id){
        try{
            Holiday holiday = new Holiday();
            holiday.setId(id);
            getHolidaysDAO().deleteHoliday(holiday);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    public boolean deleteHoliday(List<Integer> ids){
        try{
            getHolidaysDAO().deleteHoliday(ids);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    /* ------------ JOB TITLE --------------------- */
    public boolean updateJobTitle(JobTitle jobTitle, int mode){
        try{
            int row = 0;
            if(this.MODE_INSERT == mode){
                row = getJobTitleDAO().createNewJobTitle(jobTitle);
            }else if(this.MODE_UPDATE == mode){
                row = getJobTitleDAO().updateJobTitle(jobTitle);
            }
            if(row == 0){
                return false;
            }
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    public List<JobTitle> findAllJobTitles(){
        try{
            return getJobTitleDAO().getAllJobTitles();
        }catch(Exception e){
            return new ArrayList<JobTitle>();
        }
    }

    public List<JobTitle> findAllJobTitlesByCompany(String code){
        try{
            return getJobTitleDAO().getAllJobTitlesByCompany(code);
        }catch(Exception e){
            return new ArrayList<JobTitle>();
        }
    }
    
    public JobTitle findJobTitleById(int id){
        try{
            return getJobTitleDAO().getJobTitleById(id);
        }catch(Exception e){
            return null;
        }
    }
    
    public boolean deleteJobTitle(int id){
        try{
            JobTitle jobTitle = new JobTitle();
            jobTitle.setId(id);
            getJobTitleDAO().deleteJobTitle(jobTitle);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    /* ---------------- SKILLS -------------------- */
    public boolean updateSkill(Skill skill, int mode){
        try{
            if(this.MODE_INSERT == mode){
                getSkillDAO().createNewSkill(skill);
            }else if(this.MODE_UPDATE == mode){
                getSkillDAO().updateSkill(skill);
            }
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    public List<Skill> findAllSkills(){
        try{
            return getSkillDAO().getAllSkills();
        }catch(Exception e){
            return new ArrayList<Skill>();
        }
    }

    public List<Skill> findAllSkills(String criteria, String searchText){
        try{
            return getSkillDAO().getAllSkills(criteria, searchText);
        }catch(Exception e){
            return new ArrayList<Skill>();
        }
    }

    public List<Skill> findAllSkillsByCompany(String code){
        try{
            return getSkillDAO().getAllSkillsByCompany(code);
        }catch(Exception e){
            return new ArrayList<Skill>();
        }
    }

    public List<Skill> findAllSkillsByCompany(String code, String criteria, String searchText){
        try{
            return getSkillDAO().getAllSkillsByCompany(code, criteria, searchText);
        }catch(Exception e){
            return new ArrayList<Skill>();
        }
    }
    
    public Skill findSkillById(int id){
        try{
            return getSkillDAO().getSkillById(id);
        }catch(Exception e){
            return null;
        }
    }
    
    public boolean deleteSkill(int id){
        try{
            Skill skill = new Skill();
            skill.setId(id);
            getSkillDAO().deleteSkill(skill);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    /*----------------- SALARY GRADE ---------------------- */
    public boolean updateSalaryGrade(SalaryGrade salaryGrade, int mode){
        try{
            int row = 0;
            if(this.MODE_INSERT == mode){
                row = getSalaryGradeDAO().createNewSalaryGrade(salaryGrade);
            }else if(this.MODE_UPDATE == mode){
                row = getSalaryGradeDAO().updateSalaryGrade(salaryGrade);
            }
            if(row == 0){
                return false;
            }
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    public List<SalaryGrade> findAllSalaryGrade(){
        try{
            return getSalaryGradeDAO().getAllSalaryGrade();
        }catch(Exception e){
            return new ArrayList<SalaryGrade>();
        }
    }

    public List<SalaryGrade> findAllSalaryGradeByCompany(String code){
        try{
            return getSalaryGradeDAO().getAllSalaryGradeByCompany(code);
        }catch(Exception e){
            return new ArrayList<SalaryGrade>();
        }
    }
    
    public SalaryGrade findSalaryGradeById(int id){
        try{
            return getSalaryGradeDAO().getSalaryGradeById(id);
        }catch(Exception e){
            return null;
        }
    }
    
    public boolean deleteSalaryGrade(int id){
        try{
            SalaryGrade salaryGrade = new SalaryGrade();
            salaryGrade.setId(id);
            
            getSalaryGradeDAO().deleteSalaryGrade(salaryGrade);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    /* ------------- EMPLOYEE CATEGORY --------------------*/
    public boolean updateEmployeeCategory(EmployeeCategory employeeCategory, int mode){
        try{
            if(this.MODE_INSERT == mode){
                getEmployeeCategoryDAO().createNewEmployeeCategory(employeeCategory);
            }else if(this.MODE_UPDATE == mode){
                getEmployeeCategoryDAO().updateEmployeeCategory(employeeCategory);
            }
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public List<EmployeeCategory> findAllEmployeeCategory(){
        try{
            return getEmployeeCategoryDAO().getAllEmployeeCategorys();
        }catch(Exception e){
            return new ArrayList<EmployeeCategory>();
        }
    }

    public List<EmployeeCategory> findAllEmployeeCategoryByCompany(String code){
        try{
            return getEmployeeCategoryDAO().getAllEmployeeCategoryByCompany(code);
        }catch(Exception e){
            return new ArrayList<EmployeeCategory>();
        }
    }
    
    public EmployeeCategory findEmployeeCategoryById(int id){
        try{
            return getEmployeeCategoryDAO().getEmployeeCategoryById(id);
        }catch(Exception e){
            return null;
        }
    }
    
    public boolean deleteEmployeeCategory(int id){
        try{
            EmployeeCategory employeeCategory = new EmployeeCategory();
            employeeCategory.setId(id);
            getEmployeeCategoryDAO().deleteEmployeeCategory(employeeCategory);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    /* ------------- Employee Type ---------------------- */
    public boolean updateEmployeeType(EmployeeType employeeType, int mode){
        try{
            if(this.MODE_INSERT == mode){
                getEmployeeTypeDAO().createNewEmployeeType(employeeType);
            }else if(this.MODE_UPDATE == mode){
                getEmployeeTypeDAO().updateEmployeeType(employeeType);
            }
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    public List<EmployeeType> findAllEmployeeTypes(){
        try{
            return getEmployeeTypeDAO().getAllEmployeeTypes();
        }catch(Exception e){
            return new ArrayList<EmployeeType>();
        }
    }

    public List<EmployeeType> findAllEmployeeTypesByCompany(String code){
        try{
            return getEmployeeTypeDAO().getAllEmployeeTypesByCompany(code);
        }catch(Exception e){
            return new ArrayList<EmployeeType>();
        }
    }

    public EmployeeType findEmployeeTypeById(int id){
        try{
            return getEmployeeTypeDAO().getEmployeeTypeById(id);
        }catch(Exception e){
            return null;
        }
    }
    
    public boolean deleteEmployeeType(int id){
        try{
            EmployeeType employeeType = new EmployeeType();
            employeeType.setId(id);
            
            getEmployeeTypeDAO().deleteEmployeeType(employeeType);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    public List<PayFrequency> findAllPayFrequencies(){
        try{
            return getpayFrequencyDAO().getAllPayFrequencies();
        }catch(Exception e){
            return new ArrayList<PayFrequency>();
        }
    }

    public List<ControlData> findAllCountries(){
        try{
            return getApplicationDAO().getCountryList();
        }catch(Exception e){
            return new ArrayList<ControlData>();
        }
    }

    public List<ControlData> findAllRelationships(){
        try{
            return getApplicationDAO().getRelationshipList();
        }catch(Exception e){
            return new ArrayList<ControlData>();
        }
    }
    
    public DepartmentDAO getDepartmentDAO() {
        if(departmentDAO == null){
            departmentDAO = new DepartmentDAO();
        }
        return departmentDAO;
    }

    public HolidaysDAO getHolidaysDAO() {
        if(holidayDAO == null){
            holidayDAO = new HolidaysDAO();
        }
        return holidayDAO;
    }

    public JobTitleDAO getJobTitleDAO() {
        if(jobTitleDAO == null){
            jobTitleDAO = new JobTitleDAO();
        }
        return jobTitleDAO;
    }

    public SkillDAO getSkillDAO() {
        if(skillDAO == null){
            skillDAO = new SkillDAO();
        }
        return skillDAO;
    }

    public SalaryGradeDAO getSalaryGradeDAO() {
        if(salaryGradeDAO == null){
            salaryGradeDAO = new SalaryGradeDAO();
        }
        return salaryGradeDAO;
    }

    public EmployeeCategoryDAO getEmployeeCategoryDAO() {
        if(employeeCategoryDAO == null){
            employeeCategoryDAO = new EmployeeCategoryDAO();
        }
        return employeeCategoryDAO;
    }

    public EmployeeTypeDAO getEmployeeTypeDAO() {
        if(employeeTypeDAO == null){
            employeeTypeDAO = new EmployeeTypeDAO();
        }
        return employeeTypeDAO;
    }
    
    public PayFrequencyDAO getpayFrequencyDAO() {
        if(payFrequencyDAO == null){
            payFrequencyDAO = new PayFrequencyDAO();
        }
        return payFrequencyDAO;
    }

    /**
     * @return the applicationDAO
     */
    public ApplicationDAO getApplicationDAO() {

        if(applicationDAO == null){
            applicationDAO = new ApplicationDAO();
        }
        return applicationDAO;
    }
}
