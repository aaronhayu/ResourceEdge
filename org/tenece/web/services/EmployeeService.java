
/*
 * (c) Copyright 2009 The Tenece Professional Services.
 *
 * Created on 27 May 2009, 10:25
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
import org.tenece.hr.data.dao.EmployeeDependentDAO;
import org.tenece.hr.data.dao.EmployeeBankDetailDAO;
import org.tenece.hr.data.dao.EmployeeChildrenDAO;
import org.tenece.hr.data.dao.EmployeeDAO;
import org.tenece.hr.data.dao.EmployeeEducationDAO;
import org.tenece.hr.data.dao.EmployeeEmergencyContactDAO;
import org.tenece.hr.data.dao.EmployeePictureDAO;
import org.tenece.hr.data.dao.EmployeeReportToDAO;
import org.tenece.hr.data.dao.EmployeeSalaryDAO;
import org.tenece.hr.data.dao.EmployeeSkillDAO;
import org.tenece.hr.data.dao.EmployeeSpouseDAO;
import org.tenece.hr.data.dao.EmployeeWorkExperienceDAO;
import org.tenece.hr.data.dao.LocksDAO;
import org.tenece.web.data.Employee;
import org.tenece.web.data.EmployeeBank;
import org.tenece.web.data.EmployeeChildren;
import org.tenece.web.data.EmployeeDependent;
import org.tenece.web.data.EmployeeEducation;
import org.tenece.web.data.EmployeeEmergencyContact;
import org.tenece.web.data.EmployeePicture;
import org.tenece.web.data.EmployeeSalary;
import org.tenece.web.data.EmployeeSkill;
import org.tenece.web.data.EmployeeSpouse;
import org.tenece.web.data.EmployeeSupervisor;
import org.tenece.web.data.EmployeeWorkExperience;
import org.tenece.web.data.FileUpload;
import org.tenece.web.data.Lock;

/**
 *
 * @author jeffry.amachree
 */
public class EmployeeService extends BaseService {
    private EmployeeDAO employeeDAO = new EmployeeDAO();
    private EmployeePictureDAO employeePictureDAO = null;
    private LocksDAO lockDAO = null;
    private EmployeeSalaryDAO employeesalaryDAO = null;
    private EmployeeChildrenDAO employeeChildrenDAO = null;
    private EmployeeDependentDAO employeeDependentDAO = null;
    private EmployeeEducationDAO employeeEducationDAO = null;
    private EmployeeEmergencyContactDAO employeeEmergencyContact = null;
    private EmployeeReportToDAO employeeReportToDAO = null;
    private EmployeeSkillDAO employeeSkillDAO = null;
    private EmployeeWorkExperienceDAO employeeWorkExperienceDAO = null;
    private EmployeeBankDetailDAO employeeBankDetailDAO = null;
    private EmployeeSpouseDAO employeeSpouseDAO = null;
    
    /** Creates a new instance of EmployeeService */
    public EmployeeService() {
    }
    
    public boolean updateEmployeeBasic(Employee employee, int mode){
        try{
            int saved = 0;
            if(this.MODE_INSERT == mode){
                saved = getEmployeeDAO().createEmployee_WithBasic(employee);
            }else if(this.MODE_UPDATE == mode){
                saved = getEmployeeDAO().updateEmployeeBasic(employee);
            }
            if(saved == 0){
                throw new Exception("Unable to save employee");
            }
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    public boolean updateEmployeeContact(Employee employee, int mode){
        try{
            if(this.MODE_INSERT == mode){
                getEmployeeDAO().createEmployee_Contact(employee);
            }else if(this.MODE_UPDATE == mode){
                getEmployeeDAO().updateEmployeeContact(employee);
            }
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public List<Employee> findAllUnconfirmedEmployee(String criteria, String searchText){
        try{
            return getEmployeeDAO().getAllUnconfirmedEmployee(criteria, searchText);
        }catch(Exception e){
            e.printStackTrace();
            return new ArrayList<Employee>();
        }
    }
    public List<Employee> findAllUnconfirmedEmployeeByCompany(String code, String criteria, String searchText){
        try{
            return getEmployeeDAO().getAllUnconfirmedEmployeeByCompany(code, criteria, searchText);
        }catch(Exception e){
            e.printStackTrace();
            return new ArrayList<Employee>();
        }
    }
    public List<Employee> findAllEmployeeForBasic(){
        try{
            return getEmployeeDAO().getAllEmployeeBasic();
        }catch(Exception e){
            e.printStackTrace();
            return new ArrayList<Employee>();
        }
    }

    public List<Employee> findAllEmployeeForBasic(String criteria, String searchText){
        try{
            return getEmployeeDAO().getAllEmployeeBasic(criteria, searchText);
        }catch(Exception e){
            e.printStackTrace();
            return new ArrayList<Employee>();
        }
    }

    public List<Employee> findAllEmployeeForBasicByCompany(String code){
        try{
            return getEmployeeDAO().getAllEmployeeBasicByCompany(code);
        }catch(Exception e){
            e.printStackTrace();
            return new ArrayList<Employee>();
        }
    }

    public List<Employee> findAllEmployeeForBasicByCompany(String code, String criteria, String searchText){
        try{
            return getEmployeeDAO().getAllEmployeeBasicByCompany(code, criteria, searchText);
        }catch(Exception e){
            e.printStackTrace();
            return new ArrayList<Employee>();
        }
    }

    public Employee findEmployeeBasicById(long id){
        try{
            return getEmployeeDAO().getEmployeeBasicDataById(id);
        }catch(Exception e){
            return null;
        }
    }
    public Employee findEmployeeBasicByEmpNumber(int id){
        try{
            return getEmployeeDAO().getEmployeeBasicDataByEmpNumber(id);
        }catch(Exception e){
            return null;
        }
    }

    public Employee findEmployeeContactById(long id){
        try{
            return getEmployeeDAO().getEmployeeContactDataById(id);
        }catch(Exception e){
            return null;
        }
    }
    
    public boolean deActivateEmployee(List<Long> ids){
        try{
            int rows = getEmployeeDAO().deactivateEmployee(ids);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public int updateUnconfirmedEmployeeStatus(Employee employee){
        return getEmployeeDAO().updateUnconfirmedEmployeeStatus(employee);
    }
    
    /* ------------- Employee picture ----------------- */
    public boolean updateEmployeePicture(EmployeePicture employeePicture, int mode){
        try{
            if(this.MODE_INSERT == mode){
                getEmployeePictureDAO().createNewEmployeePicture(employeePicture);
            }else if(this.MODE_UPDATE == mode){
                getEmployeePictureDAO().updateEmployeePicture(employeePicture);
            }
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    public EmployeePicture findEmployeePicture(int id){
        try{
            return getEmployeePictureDAO().getEmployeePictureByEmployee(id);
        }catch(Exception e){
            return null;
        }
    }
    
    public boolean deleteEmployeePicture(int id){
        try{
            EmployeePicture employeePicture = new EmployeePicture();
            employeePicture.setEmployeeNumber(id);
            getEmployeePictureDAO().deleteEmployeePicture(employeePicture);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    /* ------------- Employee Locks ------------------- */
    public boolean updateEmployeeLocks(Lock lock, int mode){
        try{
            if(this.MODE_INSERT == mode){
                getLockDAO().createNewLock(lock);
            }else{
                getLockDAO().updateLock(lock);
            }
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    public Lock findEmployeeLock(int id){
        try{
            return getLockDAO().getLockById(id);
        }catch(Exception e){
            return null;
        }
    }
    
    public boolean deleteEmployeeLock(int id){
        try{
            Lock lock = new Lock();
            lock.setId(id);
            
            getLockDAO().deleteLock(lock);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    /* ------------------ EMPLOYEE SALARY -------------- */
    
    public boolean updateEmployeeSalary(EmployeeSalary employeeSalary, int mode){
        try{
            if(this.MODE_INSERT == mode){
                getEmployeesalaryDAO().createNewEmployeeSalary(employeeSalary);
            }else if(this.MODE_UPDATE == mode){
                getEmployeesalaryDAO().updateEmployeeSalary(employeeSalary);
            }
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    public EmployeeSalary findEmployeeSalary(int id){
        try{
            return getEmployeesalaryDAO().getEmployeeSalaryById(id);
        }catch(Exception e){
            return null;
        }
    }
    
    public boolean deleteEmployeeSalary(int id){
        try{
            EmployeeSalary employeeSalary = new EmployeeSalary();
            employeeSalary.setId(id);
            getEmployeesalaryDAO().deleteEmployeeSalary(employeeSalary);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    public boolean updateEmployeeChildren(EmployeeChildren employeeChildren, int mode){
        try{
            if(this.MODE_INSERT == mode){
                getEmployeeChildrenDAO().createNewEmployeeChildren(employeeChildren);
            }else if(this.MODE_UPDATE == mode){
                getEmployeeChildrenDAO().updateEmployeeChildren(employeeChildren);
            }
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    public List<EmployeeChildren> findEmployeeChildren(int id){
        try{
            List<EmployeeChildren> employeeChildren = getEmployeeChildrenDAO().getEmployeeChildrenById(id);
            if(employeeChildren == null){ employeeChildren = new ArrayList<EmployeeChildren>(); }
            return employeeChildren;
        }catch(Exception e){
            return new ArrayList<EmployeeChildren>();
        }
    }

    public EmployeeChildren findEmployeeChild(int id){
        try{
            EmployeeChildren employeeChild = getEmployeeChildrenDAO().getEmployeeChildById(id);
            
            return employeeChild;
        }catch(Exception e){
            return new EmployeeChildren();
        }
    }
    
    
    public boolean deleteEmployeeChild(List<Long> ids){
        try{
            int affected = getEmployeeChildrenDAO().deleteEmployeeChildren(ids);
            if(affected > 0){
                return true;
            }else{
                return false;
            }
        }catch(Exception e){
            e.printStackTrace();
            return true;
        }
    }

    /* --------------- EMPLOYEE SPOUSE -------------------- */
    public boolean updateEmployeeSpouse(EmployeeSpouse employeeSpouse, int mode){
        try{
            if(this.MODE_INSERT == mode){
                getEmployeeSpouseDAO().createNewEmployeeSpouse(employeeSpouse);
            }else if(this.MODE_UPDATE == mode){
                getEmployeeSpouseDAO().updateEmployeeSpouse(employeeSpouse);
            }
            return true;
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("-----" + e.getMessage());
            return false;
        }
    }

    public List<EmployeeSpouse> findAllEmployeeSpouse(int id){
        try{
            List<EmployeeSpouse> employeeSpouse = getEmployeeSpouseDAO().getAllEmployeeSpouseById(id);
            if(employeeSpouse == null){ employeeSpouse = new ArrayList<EmployeeSpouse>(); }
            return employeeSpouse;
        }catch(Exception e){
            return new ArrayList<EmployeeSpouse>();
        }
    }

    public EmployeeSpouse findEmployeeSpouse(int id){
        try{
            EmployeeSpouse employeeSpouse = getEmployeeSpouseDAO().getEmployeeSpouseById(id);

            return employeeSpouse;
        }catch(Exception e){
            return new EmployeeSpouse();
        }
    }


    public boolean deleteEmployeeSpouse(List<Long> ids){
        try{
            int affected = getEmployeeSpouseDAO().deleteEmployeeSpouse(ids);
            if(affected > 0){
                return true;
            }else{
                return false;
            }
        }catch(Exception e){
            e.printStackTrace();
            return true;
        }
    }

    /* ------------- EMPLOYEE DEPENDENTS ---------------- */
    public boolean updateEmployeeDependent(EmployeeDependent dependent, int mode){
        try{
            if(this.MODE_INSERT == mode){
                getEmployeeDependentDAO().createNewEmployeeDependent(dependent);
            }else if(this.MODE_UPDATE  == mode){
                getEmployeeDependentDAO().updateEmployeeDependent(dependent);
            }
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    
    public List<EmployeeDependent> findEmployeeDependents(int id){
        try{
            return getEmployeeDependentDAO().getEmployeeDependentById(id);
        }catch(Exception e){
            return new ArrayList<EmployeeDependent>();
        }
    }
    
    public EmployeeDependent findEmployeeDependent(int id){
        try{
            return getEmployeeDependentDAO().getDependentById(id);
        }catch(Exception e){
            return new EmployeeDependent();
        }
    }

    public boolean deleteEmployeeDependent(List<Long> ids){
        try{
            int affected = getEmployeeDependentDAO().deleteEmployeeDependent(ids);
            if(affected > 0){
                return true;
            }else{
                return false;
            }
        }catch(Exception e){
            e.printStackTrace();
            return true;
        }
    }
    
    /* ---------------- EMPLOYEE EDUCATION ------------- */
    public boolean updateEmployeeEducation(EmployeeEducation employeeEducation, int mode){
        try{
            if(this.MODE_INSERT == mode){
                getEmployeeEducationDAO().createNewEmployeeEducation(employeeEducation);
            }else if(this.MODE_UPDATE == mode){
                getEmployeeEducationDAO().updateEmployeeEducation(employeeEducation);
            }
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    public List<EmployeeEducation> findEmployeeEducation(int id){
        try{
            return getEmployeeEducationDAO().getEmployeeEducationById(id);
        }catch(Exception e){
            return new ArrayList<EmployeeEducation>();
        }
    }
    
    public EmployeeEducation findEducationByID(int id){
        try{
            return getEmployeeEducationDAO().getEducationById(id);
        }catch(Exception e){
            return new EmployeeEducation();
        }
    }
    
    public boolean deleteEmployeeEducation(List<Long> ids){
        try{
            int affected = getEmployeeEducationDAO().deleteEmployeeEducation(ids);
            if(affected > 0){
                return true;
            }else{
                return false;
            }
        }catch(Exception e){
            e.printStackTrace();
            return true;
        }
    }
    
    /* ------------- Employee Emergency -------------- */
    public boolean updateEmployeeEmergencyContact(EmployeeEmergencyContact contact, int mode){
        try{
            if(this.MODE_INSERT == mode){
                getEmployeeEmergencyContact().createNewEmployeeEmergencyContact(contact);
            }else if(this.MODE_UPDATE == mode){
                getEmployeeEmergencyContact().updateEmployeeEmergencyContact(contact);
            }
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    public List<EmployeeEmergencyContact> findEmployeeEmergencyContacts(long id){
        try{
            return getEmployeeEmergencyContact().getEmployeeEmergencyContactById(id);
        }catch(Exception e){
            return new ArrayList<EmployeeEmergencyContact>();
        }
    }
    
    public EmployeeEmergencyContact findEmergencyContacts(int id){
        try{
            return getEmployeeEmergencyContact().getEmergencyContactById(id);
        }catch(Exception e){
            return new EmployeeEmergencyContact();
        }
    }

    public boolean deleteEmployeeEmmergencyContact(List<Long> ids){
        try{
            int affected = getEmployeeEmergencyContact().deleteEmployeeEmergencyContact(ids);
            if(affected > 0){
                return true;
            }else{
                return false;
            }
        }catch(Exception e){
            e.printStackTrace();
            return true;
        }
    }
    
    /* ----------------- Employee Report To ---------------- */
    public boolean updateEmployeeReportTo(EmployeeSupervisor supervisor, int mode){
        return updateEmployeeReportTo(supervisor, null, mode);
    }
    public boolean updateEmployeeReportTo(EmployeeSupervisor supervisor, EmployeeSupervisor initial, int mode){
        try{
            if(this.MODE_INSERT == mode){
                getEmployeeReportToDAO().createNewEmployeeSupervisor(supervisor);
            }else if(this.MODE_UPDATE == mode){
                getEmployeeReportToDAO().updateEmployeeSupervisor(supervisor, initial);
            }
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    public List<EmployeeSupervisor> findEmployeeSupervisors(int id){
        try{
            return getEmployeeReportToDAO().getEmployeeSupervisorBy_Sub(id);
        }catch(Exception e){
            return new ArrayList<EmployeeSupervisor>();
        }
    }
    
    public EmployeeSupervisor findEmployeeSupervisorsBySub(int id){
        try{
            return getEmployeeReportToDAO().getEmployeeSupervisorBy_Sub(id).get(0);
        }catch(Exception e){
            return new EmployeeSupervisor();
        }
    }
    
    public boolean deleteEmployeeSupervisor(int id, int mode){
        try{
            EmployeeSupervisor supervisor = new EmployeeSupervisor();
            supervisor.setSubEmployeeId(id);
            supervisor.setReportingMode(mode);
            getEmployeeReportToDAO().deleteEmployeeSupervisor(supervisor);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    /* ----------------- Employee Skills ------------------ */
    public boolean updateEmployeeSkills(EmployeeSkill skill, int mode){
        try{
            if(this.MODE_INSERT == mode){
                getEmployeeSkillDAO().createNewEmployeeSkill(skill);
            }else if(this.MODE_UPDATE == mode){
                getEmployeeSkillDAO().updateEmployeeSkill(skill);
            }
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    public List<EmployeeSkill> findEmployeeSkills(int id){
        try{
            return getEmployeeSkillDAO().getEmployeeSkillById(id);
        }catch(Exception e){
            return new ArrayList<EmployeeSkill>();
        }
    }
    
    public EmployeeSkill findEmployeeSkillByID(int id){
        try{
            return getEmployeeSkillDAO().getSkillById(id);
        }catch(Exception e){
            return new EmployeeSkill();
        }
    }
    
    public boolean deleteEmployeeSkill(List<Long> ids){

        try{
            int affected = getEmployeeSkillDAO().deleteEmployeeSkill(ids);
            if(affected > 0){
                return true;
            }else{
                return false;
            }
        }catch(Exception e){
            e.printStackTrace();
            return true;
        }
    }
    
    /* ----------------- Employee Work Experience --------------- */
    public boolean updateEmployeeWorkExperience(EmployeeWorkExperience experience, int mode){
        try{
            if(this.MODE_INSERT == mode){
                getEmployeeWorkExperienceDAO().createNewEmployeeWorkExperience(experience);
            }else if(this.MODE_UPDATE == mode){
                getEmployeeWorkExperienceDAO().updateEmployeeWorkExperience(experience);
            }
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    public List<EmployeeWorkExperience> findEmployeeWorkExperience(int id){
        try{
            return getEmployeeWorkExperienceDAO().getEmployeeWorkExperienceById(id);
        }catch(Exception e){
            return new ArrayList<EmployeeWorkExperience>();
        }
    }
    
    public EmployeeWorkExperience findEmployeeWorkExperienceByID(int id){
        try{
            return getEmployeeWorkExperienceDAO().getWorkExperienceById(id);
        }catch(Exception e){
            return new EmployeeWorkExperience();
        }
    }
    
    public boolean deleteEmployeeWorkExperience(List<Long> ids){
//        try{
//            EmployeeWorkExperience experience = new EmployeeWorkExperience();
//            experience.setId(id);
//            getEmployeeWorkExperienceDAO().deleteEmployeeWorkExperience(experience);
//            return true;
//        }catch(Exception e){
//            return false;
//        }
        try{
            int affected = getEmployeeWorkExperienceDAO().deleteEmployeeWorkExperience(ids);
            if(affected > 0){
                return true;
            }else{
                return false;
            }
        }catch(Exception e){
            e.printStackTrace();
            return true;
        }
    }
    
    /* ----------------- Employee Bank Detail --------------- */
    public boolean updateEmployeeBankDetail(EmployeeBank bank, int mode){
        try{
            if(this.MODE_INSERT == mode){
                getEmployeeBankDetailDAO().createNewEmployeeBankDetail(bank);
            }else if(this.MODE_UPDATE == mode){
                getEmployeeBankDetailDAO().updateEmployeeBankDetail(bank);
            }
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    public EmployeeBank findEmployeeBankDetail(long id){
        try{
            return getEmployeeBankDetailDAO().getEmployeeBankDetailById(id);
        }catch(Exception e){
            return new EmployeeBank();
        }
    }

    public List<EmployeeBank> findAllActiveEmployeeBankDetail(){
        try{
            return getEmployeeBankDetailDAO().getAllValidEmployeeBankDetail();
        }catch(Exception e){
            return new ArrayList<EmployeeBank>();
        }
    }

    public List<EmployeeBank> findAllActiveEmployeeBankDetailByCompany(String code){
        try{
            return getEmployeeBankDetailDAO().getAllValidEmployeeBankDetailByCompany(code);
        }catch(Exception e){
            return new ArrayList<EmployeeBank>();
        }
    }

    /* ---------- EMPLOYEE PICTURE -------------------- */
    public EmployeePicture getEmployeePicture(int employeeId){
        return getEmployeePictureDAO().getEmployeePictureByEmployee(employeeId);
    }
    
    public boolean saveUploadedPicture(FileUpload fileUpload, int employeeId){
        try{
            EmployeePicture picture = new EmployeePicture();
            EmployeePicture tmpPicture = new EmployeePicture();
            
            tmpPicture.setEmployeeNumber(employeeId);
            //tmpPicture.setId();
            //delete the current one and save a new picture
            getEmployeePictureDAO().deleteEmployeePicture(tmpPicture);
            
            picture.setEmployeeNumber(employeeId);
            picture.setFileName(fileUpload.getOrginalFileName());
            picture.setFileSize(String.valueOf(fileUpload.getSize()));
            picture.setPicture(fileUpload.getBytes());
            picture.setFileType(fileUpload.getContentType());
            picture.setPictureType(fileUpload.getOrginalFileName().substring(fileUpload.getOrginalFileName().indexOf(".")));
            getEmployeePictureDAO().createNewEmployeePicture(picture);
            
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /* ============ EMPLOYEE TERMINATION ==========================*/
    public List<Employee> findAllTerminatedEmployee(){
        try{
            return getEmployeeDAO().getAllTerminatedEmployee();
        }catch(Exception e){
            e.printStackTrace();
            return new ArrayList<Employee>();
        }
    }

    public List<Employee.Termination> findAllTermination(){
            return getEmployeeDAO().getListOfTermination();
    }

    public List<Employee.Termination> findAllTerminationByCompany(String code){
            return getEmployeeDAO().getListOfTerminationByCompany(code);
    }

    public Employee.Termination findTerminationbyId(long id){
        return getEmployeeDAO().getTerminationById(id);
    }

    public Employee.Termination findTerminationbyTransaction(long id){
        return getEmployeeDAO().getTerminationByTransaction(id);
    }
    
    public boolean updateTermination(Employee.Termination termination, int mode){
        try{
            int rows = 0;
            if(mode == EmployeeService.MODE_INSERT){
                rows = getEmployeeDAO().createNewTermination(termination);
            }else if(mode == EmployeeService.MODE_UPDATE){
                rows = getEmployeeDAO().updateTermination(termination);
            }
            return true;
        }catch(Exception e){
            return false;
        }
    }
    

    public int updateStatusByTransaction(Employee.Termination termination){
        return getEmployeeDAO().updateTerminationForTransaction(termination);
    }

    public int deleteTermination(List<Long> ids){
        return getEmployeeDAO().deleteTerminations(ids);
    }

    public Employee findEmployeeTransactionBioData(long transactionId){
        return getEmployeeDAO().getEmployeeTransactionBioDataById(transactionId);
    }

    public boolean updateEmployeeTransactionBioData(Employee employee, int mode){
        int saved = 0;
        if(mode == EmployeeService.MODE_INSERT){
            saved = getEmployeeDAO().createEmployeeTransactionBioData(employee);
        }
        if(saved == 0){
            return false;
        }else{
            return true;
        }
    }

    public int migrateEmployeeESSChildren(long employeeId){
        return getEmployeeChildrenDAO().migrateEmployeeChildrenToESS(employeeId);

    }

    public boolean updateESS_EmployeeChildren(EmployeeChildren employeeChildren, int mode){
        try{
            if(this.MODE_INSERT == mode){
                getEmployeeChildrenDAO().createNewESSEmployeeChild(employeeChildren);
            }else if(this.MODE_UPDATE == mode){
                getEmployeeChildrenDAO().updateESS_EmployeeChild(employeeChildren);
            }
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public List<EmployeeChildren> findESS_EmployeeChildren(int id){
        try{
            List<EmployeeChildren> employeeChildren = getEmployeeChildrenDAO().getESS_EmployeeChildren(id);
            if(employeeChildren == null){ employeeChildren = new ArrayList<EmployeeChildren>(); }
            return employeeChildren;
        }catch(Exception e){
            return new ArrayList<EmployeeChildren>();
        }
    }

    public EmployeeChildren findESS_EmployeeChild(int id){
        try{
            EmployeeChildren employeeChild = getEmployeeChildrenDAO().getESS_EmployeeChildBySequence(id);

            return employeeChild;
        }catch(Exception e){
            return new EmployeeChildren();
        }
    }

    public boolean deleteESS_EmployeeChild(int id){
        try{
            EmployeeChildren employeeChild = new EmployeeChildren();
            employeeChild.setSequence(id);
            getEmployeeChildrenDAO().deleteESS_EmployeeChild(employeeChild);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public int migrateEmployeeESSDependents(long employeeId){
        return getEmployeeDependentDAO().migrateEmployeeDependentsToESS(employeeId);

    }

    public boolean updateESS_EmployeeDependent(EmployeeDependent employeeDependent, int mode){
        try{
            if(this.MODE_INSERT == mode){
                getEmployeeDependentDAO().createNewESS_EmployeeDependent(employeeDependent);
            }else if(this.MODE_UPDATE == mode){
                getEmployeeDependentDAO().updateESS_EmployeeDependent(employeeDependent);
            }
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public List<EmployeeDependent> findESS_EmployeeDependent(int id){
        try{
            List<EmployeeDependent> employeeDependent = getEmployeeDependentDAO().getESS_EmployeeDependents(id);
            if(employeeDependent == null){ employeeDependent = new ArrayList<EmployeeDependent>(); }
            return employeeDependent;
        }catch(Exception e){
            return new ArrayList<EmployeeDependent>();
        }
    }

    public EmployeeDependent findESS_EmployeeDependentBySequence(int id){
        try{
            EmployeeDependent employeeDependent = getEmployeeDependentDAO().getESS_DependentBySequence(id);

            return employeeDependent;
        }catch(Exception e){
            return new EmployeeDependent();
        }
    }

    public boolean deleteESS_EmployeeDependent(int id){
        try{
            EmployeeDependent employeeDependent = new EmployeeDependent();
            employeeDependent.setSequence(id);
            getEmployeeDependentDAO().deleteESS_EmployeeDependent(employeeDependent);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public int migrateEmployeeESSEducation(long employeeId){
        return getEmployeeEducationDAO().migrateEmployeeEducationToESS(employeeId);

    }

    public boolean updateESS_EmployeeEducation(EmployeeEducation employeeEducation, int mode){
        try{
            if(this.MODE_INSERT == mode){
                getEmployeeEducationDAO().createNewESS_EmployeeEducation(employeeEducation);
            }else if(this.MODE_UPDATE == mode){
                getEmployeeEducationDAO().updateESS_EmployeeEducation(employeeEducation);
            }
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public List<EmployeeEducation> findESS_EmployeeEducation(long id){
        try{
            List<EmployeeEducation> employeeEducation = getEmployeeEducationDAO().getESS_EmployeeEducation(id);
            if(employeeEducation == null){ employeeEducation = new ArrayList<EmployeeEducation>(); }
            return employeeEducation;
        }catch(Exception e){
            return new ArrayList<EmployeeEducation>();
        }
    }

    public EmployeeEducation findESS_EmployeeEducationByID(int id){
        try{
            EmployeeEducation employeeEducation = getEmployeeEducationDAO().getESS_EducationByID(id);

            return employeeEducation;
        }catch(Exception e){
            return new EmployeeEducation();
        }
    }

    public boolean deleteESS_EmployeeEducation(int id){
        try{
            EmployeeEducation employeeEducation = new EmployeeEducation();
            employeeEducation.setId(id);
            getEmployeeEducationDAO().deleteESS_EmployeeEducation(employeeEducation);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public int migrateEmployeeESSEmergencyContact(long employeeId){
        return getEmployeeEmergencyContact().migrateEmployeeEmergencyContactToESS(employeeId);

    }

    public boolean updateESS_EmployeeEmergencyContact(EmployeeEmergencyContact contact, int mode){
        try{
            if(this.MODE_INSERT == mode){
                getEmployeeEmergencyContact().createNewESS_EmployeeEmergencyContact(contact);
            }else if(this.MODE_UPDATE == mode){
                getEmployeeEmergencyContact().updateESS_EmployeeEmergencyContact(contact);
            }
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public List<EmployeeEmergencyContact> findESS_EmployeeEmergencyContact(long id){
        try{
            List<EmployeeEmergencyContact> contacts = getEmployeeEmergencyContact().getESS_EmployeeEmergencyContacts(id);
            if(contacts == null){ contacts = new ArrayList<EmployeeEmergencyContact>(); }
            return contacts;
        }catch(Exception e){
            return new ArrayList<EmployeeEmergencyContact>();
        }
    }

    public EmployeeEmergencyContact findESS_EmployeeEmergencyContactByID(long id){
        try{
            EmployeeEmergencyContact contact = getEmployeeEmergencyContact().getESS_EmergencyContactByID(id);
            return contact;
        }catch(Exception e){
            return new EmployeeEmergencyContact();
        }
    }

    public boolean deleteESS_EmployeeEmergencyContact(int id){
        try{
            EmployeeEmergencyContact contact = new EmployeeEmergencyContact();
            contact.setId(id);
            getEmployeeEmergencyContact().deleteESS_EmployeeEmergencyContact(contact);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    public EmployeeBank findESS_EmployeeBankDetailByTransaction(long id){
        try{
            EmployeeBank bank = getEmployeeBankDetailDAO().getESS_EmployeeBankDetailByTransaction(id);
            return bank;
        }catch(Exception e){
            return new EmployeeBank();
        }
    }

    public List<Employee.Promotion> findAllPromotionHistory(){
        return getEmployeeDAO().getAllPromotionHistory();
    }
    
    public List<Employee.Promotion> findAllEmployeePromotionHistory(long employeeId){
        return getEmployeeDAO().getAllPromotionHistoryByEmployee(employeeId);
    }

    public Employee.Promotion findLastEmployeePromotionHistory(long employeeId){
        return getEmployeeDAO().getLastPromotionHistoryByEmployee(employeeId);
    }

    public boolean updateEmployeePromotionHistory(Employee.Promotion promotion, int mode){
        try{
            int saved = 0;
            if(this.MODE_INSERT == mode){
                saved = getEmployeeDAO().createEmployeePromotion(promotion);
            }else if(this.MODE_UPDATE == mode){
                saved = getEmployeeDAO().updateEmployeePromotion(promotion);
            }
            if(saved == 0) return false;
            else return true;
        }catch(Exception e){
            return false;
        }
    }

    /**
     * @return the employeeDAO
     */
    public EmployeeDAO getEmployeeDAO() {
        return employeeDAO;
    }

    /**
     * @param employeeDAO the employeeDAO to set
     */
    public void setEmployeeDAO(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    /**
     * @return the employeePictureDAO
     */
    public EmployeePictureDAO getEmployeePictureDAO() {
        return employeePictureDAO;
    }

    /**
     * @param employeePictureDAO the employeePictureDAO to set
     */
    public void setEmployeePictureDAO(EmployeePictureDAO employeePictureDAO) {
        this.employeePictureDAO = employeePictureDAO;
    }

    /**
     * @return the lockDAO
     */
    public LocksDAO getLockDAO() {
        return lockDAO;
    }

    /**
     * @param lockDAO the lockDAO to set
     */
    public void setLockDAO(LocksDAO lockDAO) {
        this.lockDAO = lockDAO;
    }

    /**
     * @return the employeesalaryDAO
     */
    public EmployeeSalaryDAO getEmployeesalaryDAO() {
        return employeesalaryDAO;
    }

    /**
     * @param employeesalaryDAO the employeesalaryDAO to set
     */
    public void setEmployeesalaryDAO(EmployeeSalaryDAO employeesalaryDAO) {
        this.employeesalaryDAO = employeesalaryDAO;
    }

    /**
     * @return the employeeChildrenDAO
     */
    public EmployeeChildrenDAO getEmployeeChildrenDAO() {
        return employeeChildrenDAO;
    }

    /**
     * @param employeeChildrenDAO the employeeChildrenDAO to set
     */
    public void setEmployeeChildrenDAO(EmployeeChildrenDAO employeeChildrenDAO) {
        this.employeeChildrenDAO = employeeChildrenDAO;
    }

    /**
     * @return the employeeDependentDAO
     */
    public EmployeeDependentDAO getEmployeeDependentDAO() {
        return employeeDependentDAO;
    }

    /**
     * @param employeeDependentDAO the employeeDependentDAO to set
     */
    public void setEmployeeDependentDAO(EmployeeDependentDAO employeeDependentDAO) {
        this.employeeDependentDAO = employeeDependentDAO;
    }

    /**
     * @return the employeeEducationDAO
     */
    public EmployeeEducationDAO getEmployeeEducationDAO() {
        return employeeEducationDAO;
    }

    /**
     * @param employeeEducationDAO the employeeEducationDAO to set
     */
    public void setEmployeeEducationDAO(EmployeeEducationDAO employeeEducationDAO) {
        this.employeeEducationDAO = employeeEducationDAO;
    }

    /**
     * @return the employeeEmergencyContact
     */
    public EmployeeEmergencyContactDAO getEmployeeEmergencyContact() {
        return employeeEmergencyContact;
    }

    /**
     * @param employeeEmergencyContact the employeeEmergencyContact to set
     */
    public void setEmployeeEmergencyContact(EmployeeEmergencyContactDAO employeeEmergencyContact) {
        this.employeeEmergencyContact = employeeEmergencyContact;
    }

    /**
     * @return the employeeReportToDAO
     */
    public EmployeeReportToDAO getEmployeeReportToDAO() {
        return employeeReportToDAO;
    }

    /**
     * @param employeeReportToDAO the employeeReportToDAO to set
     */
    public void setEmployeeReportToDAO(EmployeeReportToDAO employeeReportToDAO) {
        this.employeeReportToDAO = employeeReportToDAO;
    }

    /**
     * @return the employeeSkillDAO
     */
    public EmployeeSkillDAO getEmployeeSkillDAO() {
        return employeeSkillDAO;
    }

    /**
     * @param employeeSkillDAO the employeeSkillDAO to set
     */
    public void setEmployeeSkillDAO(EmployeeSkillDAO employeeSkillDAO) {
        this.employeeSkillDAO = employeeSkillDAO;
    }

    /**
     * @return the employeeWorkExperienceDAO
     */
    public EmployeeWorkExperienceDAO getEmployeeWorkExperienceDAO() {
        return employeeWorkExperienceDAO;
    }

    /**
     * @param employeeWorkExperienceDAO the employeeWorkExperienceDAO to set
     */
    public void setEmployeeWorkExperienceDAO(EmployeeWorkExperienceDAO employeeWorkExperienceDAO) {
        this.employeeWorkExperienceDAO = employeeWorkExperienceDAO;
    }

    /**
     * @return the employeeBankDetailDAO
     */
    public EmployeeBankDetailDAO getEmployeeBankDetailDAO() {
        return employeeBankDetailDAO;
    }

    /**
     * @param employeeBankDetailDAO the employeeBankDetailDAO to set
     */
    public void setEmployeeBankDetailDAO(EmployeeBankDetailDAO employeeBankDetailDAO) {
        this.employeeBankDetailDAO = employeeBankDetailDAO;
    }

    /**
     * @return the employeeSpouseDAO
     */
    public EmployeeSpouseDAO getEmployeeSpouseDAO() {
        return employeeSpouseDAO;
    }

    /**
     * @param employeeSpouseDAO the employeeSpouseDAO to set
     */
    public void setEmployeeSpouseDAO(EmployeeSpouseDAO employeeSpouseDAO) {
        this.employeeSpouseDAO = employeeSpouseDAO;
    }


}
