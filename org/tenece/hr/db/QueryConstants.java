
/*
 * (c) Copyright 2009 The Tenece Professional Services.
 *
 * Created on 24 May 2009, 22:11
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

package org.tenece.hr.db;

/**
 *
 * @author jeffry.amachree
 */
public class QueryConstants {

    public QueryConstants(){
    
    }
    public String TEST_QUERY = "select 'mssql' ";

    public  String ORDER_BY_1 = " order by 1";
    public  String ORDER_BY_2 = " order by 2";
    public  String ORDER_BY_3 = " order by 3";
    public  String ORDER_BY_4 = " order by 4";
    public  String ORDER_BY_5 = " order by 5";

    public  String COUNTRY_SELECT = "select code, description from country order by 1 ";
    public  String RELATIONSHIP_SELECT = "select code, description from relationship order by 1";

    public  String EMAIL_CONTENT_SELECT_BY_CODE = "select code, subject, message_body, sender_email, sender_name from email_messages where code=?";

    public  String COMPANY_SELECT_ALL = "select code, company_name, legal_name, address1, address2, city, state, country, phone, fax, email, website, logo, logo_name, leave_limit, daily_working_hours, use_default_workflow from company ";
    public  String COMPANY_SELECT_BY_CODE = "select code, company_name, legal_name, address1, address2, city, state, country, phone, fax, email, website, logo, logo_name, leave_limit, daily_working_hours, use_default_workflow from company where code=? ";
    public  String COMPANY_INSERT = "insert into company (code, company_name, legal_name, address1, address2, city, state, country, phone, fax, email, website, logo, logo_name, leave_limit, daily_working_hours, use_default_workflow) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    public  String COMPANY_UPDATE_WITH_LOGO = "update company set company_name=?, legal_name=?, address1=?, address2=?, city=?, state=?, country=?, phone=?, fax=?, email=?, website=?, logo=?, logo_name=?, leave_limit=?, daily_working_hours=?, use_default_workflow=? where code=? ";
    public  String COMPANY_UPDATE = "update company set company_name=?, legal_name=?, address1=?, address2=?, city=?, state=?, country=?, phone=?, fax=?, email=?, website=?, leave_limit=?, daily_working_hours=?, use_default_workflow=? where code=? ";

    public  String ANNUAL_CALENDAR_SELECT = "select annual_id, annual_code, annual_description, start_date, end_date, status, company_code from annual_calendar ";
    public  String ANNUAL_CALENDAR_SELECT_BY_COMPANY = "select annual_id, annual_code, annual_description, start_date, end_date, status, company_code from annual_calendar where company_code=? ";
    public  String ANNUAL_CALENDAR_SELECT_BY_ID = "select annual_id, annual_code, annual_description, start_date, end_date, status, company_code from annual_calendar where annual_id=? ";
    public  String ANNUAL_CALENDAR_INSERT = "insert into annual_calendar (annual_code, annual_description, start_date, end_date, status, company_code) values(?,?,?,?,?,?) ";
    public  String ANNUAL_CALENDAR_UPDATE = "update annual_calendar set annual_code=?, annual_description=?, start_date=?, end_date=?, status=?, company_code=? where annual_id=? ";
    public  String ANNUAL_CALENDAR_UPDATE_ALL_OUTSTANDING = "update annual_calendar set status='A' where status not in ('A') ";
    
    /* ----------- USERS ----------- */
    public  String USER_SELECT = "select user_id, emp_number, user_name, password, admin_user, superadmin, numlogins, active, dateupdated from users  ";
    public  String USER_SELECT_BY_COMPANY = "select u.user_id, u.emp_number, u.user_name, u.password, u.admin_user, u.superadmin, u.numlogins, u.active, u.dateupdated from users u, employee e where u.emp_number = e.emp_number and e.company_code=?  ";
    public  String USER_SELECT_BY_ID = "select user_id, emp_number, user_name, password, admin_user, superadmin, numlogins, active, dateupdated from users where user_id=? ";
    public  String USER_SELECT_BY_UID_AND_PASSWORD = "select user_id, emp_number, user_name, password, admin_user, superadmin, numlogins, active, dateupdated from users where user_name=? and password=? " +
            " and user_id not in (select user_id from locks where active='A') ";
    public  String USER_SELECT_BY_UID_AND_EMP_ID = "select password, user_id from users where user_name=? and " +
            " emp_number in (select emp_number from employee where emp_id = ?) ";
    
    public  String USER_SELECT_BY_UID = "select user_id, emp_number, user_name, password, admin_user, superadmin, numlogins, active, dateupdated from users where user_name=? ";
    public  String USER_INSERT = "insert into users (emp_number, user_name, password, admin_user, superadmin, numlogins, active, dateupdated, datesignup, lastlogindate, loginip) values(?,?,?,?,?,?,?,?,?,?, ' ')";
    public  String USER_UPDATE = "update users set emp_number=?, user_name=?, admin_user=?, superadmin=?, active=?, dateupdated=? where user_id=?";
    public  String USER_UPDATE_PASSWORD = "update users set password=?, dateupdated=? where user_id=?";
    public  String USER_UPDATE_LOGIN = "update users set numlogins=? where user_id = ? ";
    public  String USER_DELETE = "delete from users where user_id = ? ";

    public  String USER_SELECT_BY_LOCKED_STATUS = "select user_id, emp_number, user_name, password, admin_user, superadmin, numlogins, active, dateupdated from users where user_id in (select user_id from locks where active='A')  ";
    public  String USER_LOCK_SELECT_BY_ID = "select user_id, datelock, reasonlock, lockedby, active from locks  where user_id =? ";
    public  String USER_LOCK_INSERT = "insert into locks (user_id, datelock, reasonlock, lockedby, active) values(?,?,?,?,?) ";
    public  String USER_LOCK_DEACTIVATE = "update locks set active='D' where user_id=? ";
    public  String USER_RESET_LOGIN_TRIES = "update users set numlogins=0 where user_id=?";

    public  String USER_UPDATE_PASSWORD_FOR_USER_DEACTIVATION = "update users set password='', dateupdated=? where emp_number=?";
    
    
    /* -----------Department ---------*/
    public  String DEPARTMENT_SELECT = " select deptid, managerid, deptname, location, deptdesc, workdesc, parent_id, company_code from department ";
    public  String DEPARTMENT_SELECT_SEARCH_BY_NAME = " select deptid, managerid, deptname, location, deptdesc, workdesc, parent_id, company_code from department where deptname like '%_SEARCH_%' ";
    public  String DEPARTMENT_SELECT_SEARCH_BY_LOCATION = " select deptid, managerid, deptname, location, deptdesc, workdesc, parent_id, company_code from department where location like '%_SEARCH_%' ";

    public  String DEPARTMENT_SELECT_BY_COMPANY = " select deptid, managerid, deptname, location, deptdesc, workdesc, parent_id, company_code from department where company_code=? ";
    public  String DEPARTMENT_SELECT_BY_COMPANY_SEARCH_BY_NAME = " select deptid, managerid, deptname, location, deptdesc, workdesc, parent_id, company_code from department where company_code=? and deptname like '%_SEARCH_%' ";
    public  String DEPARTMENT_SELECT_BY_COMPANY_SEARCH_BY_LOCATION = " select deptid, managerid, deptname, location, deptdesc, workdesc, parent_id, company_code from department where company_code=? and location like '%_SEARCH_%' ";

    public  String DEPARTMENT_SELECT_BY_ID = " select deptid, managerid, deptname, location, deptdesc, workdesc, parent_id, company_code from department where deptid = ? ";
    public  String DEPARTMENT_INSERT = " insert into department (managerid, deptname, location, deptdesc, workdesc, parent_id, company_code) values (?,?,?,?,?,?,?)";
    public  String DEPARTMENT_UPDATE = " update department set managerid=?, deptname=?, location=?, deptdesc=?, workdesc=?, parent_id=?, company_code=? where deptid=? ";
    public  String DEPARTMENT_DELETE = "delete from department where deptid = ? ";
    
    /* ---------- HOLIDAY -----------*/
    public  String HOLIDAY_SELECT = "select holiday_id, description, effective_date, recurring, holiday_length, company_code from holidays ";
    public  String HOLIDAY_SELECT_SEARCH_BY_DESC = "select holiday_id, description, effective_date, recurring, holiday_length, company_code from holidays where description like '%_SEARCH_%' ";
    public  String HOLIDAY_SELECT_SEARCH_BY_LENGTH = "select holiday_id, description, effective_date, recurring, holiday_length, company_code from holidays where holiday_length = '_SEARCH_' ";

    public  String HOLIDAY_SELECT_BY_COMPANY = "select holiday_id, description, effective_date, recurring, holiday_length, company_code from holidays where company_code=? ";
    public  String HOLIDAY_SELECT_BY_COMPANY_SEARCH_BY_DESC = "select holiday_id, description, effective_date, recurring, holiday_length, company_code from holidays where description like '%_SEARCH_%' and company_code=? ";
    public  String HOLIDAY_SELECT_BY_COMPANY_SEARCH_BY_LENGTH = "select holiday_id, description, effective_date, recurring, holiday_length, company_code from holidays where holiday_length = '_SEARCH_' and company_code=? ";

    public  String HOLIDAY_SELECT_BY_ID = "select holiday_id, description, effective_date, recurring, holiday_length, company_code from holidays where holiday_id = ? ";
    public  String HOLIDAY_UPDATE = "update holidays set description=?, effective_date=?, recurring=?, holiday_length=?, company_code=? where holiday_id = ? ";
    public  String HOLIDAY_INSERT = "insert into holidays (description, effective_date, recurring, holiday_length, company_code) values(?,?,?,?,?) ";
    public  String HOLIDAY_DELETE = "delete from holidays where holiday_id=? ";
    
    /* ---------- Job Title ----------*/
    public  String JOBTITLE_SELECT = "select jobid, jobtitle, jobdesc, company_code from jobtitle ";
    public  String JOBTITLE_SELECT_BY_COMPANY = "select jobid, jobtitle, jobdesc, company_code from jobtitle where company_code=? ";
    public  String JOBTITLE_SELECT_BY_ID = "select jobid, jobtitle, jobdesc, company_code from jobtitle where jobid=?";
    public  String JOBTITLE_UPDATE = "update jobtitle set jobtitle=?, jobdesc=?, company_code=? where jobid=? ";
    public  String JOBTITLE_INSERT = "insert into jobtitle(jobtitle, jobdesc, company_code) values(?,?,?) ";
    public  String JOBTITLE_DELETE = "delete from jobtitle where jobid = ? ";
    
    /* ---------- Skills -------------*/
    public  String SKILL_SELECT = "select skill_id, skill_code, skill_name, skill_description, company_code from skill ";
    public  String SKILL_SELECT_SEARCH_BY_CODE = "select skill_id, skill_code, skill_name, skill_description, company_code from skill where skill_code like '%_SEARCH_%'";
    public  String SKILL_SELECT_SEARCH_BY_NAME = "select skill_id, skill_code, skill_name, skill_description, company_code from skill where skill_name like '%_SEARCH_%'";
    public  String SKILL_SELECT_SEARCH_BY_DESC = "select skill_id, skill_code, skill_name, skill_description, company_code from skill where skill_description like '%_SEARCH_%' ";

    public  String SKILL_SELECT_BY_COMPANY = "select skill_id, skill_code, skill_name, skill_description, company_code from skill where company_code=? ";
    public  String SKILL_SELECT_BY_COMPANY_SEARCH_BY_CODE = "select skill_id, skill_code, skill_name, skill_description, company_code from skill where company_code=? and skill_code like '%_SEARCH_%'";
    public  String SKILL_SELECT_BY_COMPANY_SEARCH_BY_NAME = "select skill_id, skill_code, skill_name, skill_description, company_code from skill where company_code=? and skill_name like '%_SEARCH_%'";
    public  String SKILL_SELECT_BY_COMPANY_SEARCH_BY_DESC = "select skill_id, skill_code, skill_name, skill_description, company_code from skill where company_code=? and skill_description like '%_SEARCH_%' ";

    public  String SKILL_SELECT_BY_ID = "select skill_id, skill_code, skill_name, skill_description, company_code from skill where skill_id=? ";
    public  String SKILL_UPDATE = "update skill set skill_code=?, skill_name=?, skill_description=?, company_code=? where skill_id=? ";
    public  String SKILL_INSERT = "insert into skill (skill_code, skill_name, skill_description, company_code) values(?,?,?,?) ";
    public  String SKILL_DELETE = "delete from skill where skill_id = ? ";
    
    /* --------- SALARY GRADE ----------- */
    public  String SALARYGRADE_SELECT = "select salaryid, code, description, note, company_code from salarygrade ";
    public  String SALARYGRADE_SELECT_BY_COMPANY = "select salaryid, code, description, note, company_code from salarygrade where company_code=? ";
    public  String SALARYGRADE_SELECT_BY_ID = "select salaryid, code, description, note, company_code from salarygrade where salaryid=? ";
    public  String SALARYGRADE_UPDATE = "update salarygrade set code=?, description=?, note=?, company_code=? where salaryid=? ";
    public  String SALARYGRADE_INSERT = "insert into salarygrade (code, description, note, company_code) values(?,?,?,?) ";
    public  String SALARYGRADE_DELETE = "delete from salarygrade where salaryid=? ";
    
    /* -------- Employee Category --------- */
    public  String EMPLOYEE_CATEGORY_SELECT = "select catid, catname, catdesc, note, company_code from employee_category ";
    public  String EMPLOYEE_CATEGORY_SELECT_BY_COMPANY = "select catid, catname, catdesc, note, company_code from employee_category where company_code=? ";
    public  String EMPLOYEE_CATEGORY_SELECT_BY_ID = "select catid, catname, catdesc, note, company_code from employee_category where catid = ? ";
    public  String EMPLOYEE_CATEGORY_INSERT = "insert into employee_category (catname, catdesc, note, company_code) values(?,?,?,?) ";
    public  String EMPLOYEE_CATEGORY_UPDATE = "update employee_category set catname=?, catdesc=?, note=?, company_code=? where catid=? ";
    public  String EMPLOYEE_CATEGORY_DELETE = "delete from employee_category where catid=? ";
    
    /* ------- EMPLOYEE TYPE -------------- */
    public  String EMPLOYEE_TYPE_SELECT = "select typeid, typename, typedesc, note, company_code from employeetype";
    public  String EMPLOYEE_TYPE_SELECT_BY_COMPANY = "select typeid, typename, typedesc, note, company_code from employeetype where company_code=?";
    public  String EMPLOYEE_TYPE_SELECT_BY_ID = "select typeid, typename, typedesc, note, company_code from employeetype where typeid=?";
    public  String EMPLOYEE_TYPE_INSERT = "insert into employeetype (typename, typedesc, note, company_code) values(?,?,?,?) ";
    public  String EMPLOYEE_TYPE_UPDATE = "update employeetype set typename=?, typedesc=?, note=?, company_code=?  where typeid=? ";
    public  String EMPLOYEE_TYPE_DELETE = "delete from employeetype where typeid=? ";
    
    /* ========= PAY FREQUENCY ============ */
    public  String PAY_FREQUENCY_SELECT = "select id, description from pay_frequency order by 1";
    
    /* --------- EMPLOYEE PICTURE -------- */
    public  String EMPLOYEE_PICTURE_SELECT = " select picid, pic_type, filename, filesize, filetype, picture from employee_picture where emp_number = ? ";
    public  String EMPLOYEE_PICTURE_UPDATE = " update employee_picture set emp_number=?, pic_type=?, filename=?, filesize=?, filetype=?, picture=? where picid=? ";
    public  String EMPLOYEE_PICTURE_DELETE = " delete from employee_picture where emp_number = ? ";
    public  String EMPLOYEE_PICTURE_INSERT = " insert into employee_picture (emp_number, pic_type, filename, filesize, filetype, picture) values(?,?,?,?,?,?)";
    
    /* --------- EMPLOYEE LOCKS ------------ */
    public  String LOCKS_SELECT = "select lockid, emp_number, datelock, reasonlock, lockedby, active from locks ";
    public  String LOCKS_SELECT_BY_ID = "select lockid, emp_number, datelock, reasonlock, lockedby, active from locks where emp_number=? ";
    public  String LOCKS_INSERT = "insert into locks (lockid, emp_number, datelock, reasonlock, lockedby, active) values(?,?,?,?,?,?) ";
    public  String LOCKS_UPDATE = "update locks set emp_number=?, datelock=?, reasonlock=?, lockedby=?, active=? where lockid=? ";
    public  String LOCKS_DELETE = "delete from locks where lockid=? ";
    
    /* ---------- EMPLOYEE SALARY ----------- */
    public   String EMPLOYEESALARY_SELECT = "select salaryid, salarygradeid, emp_number, basicsalary, payment_frequency from employeesalary ";
    public   String EMPLOYEESALARY_SELECT_BY_ID = "select salaryid, salarygradeid, emp_number, basicsalary, payment_frequency from employeesalary where salaryid=? ";
    public   String EMPLOYEESALARY_SELECT_BY_EMPLOYEE = "select salaryid, salarygradeid, emp_number, basicsalary, payment_frequency from employeesalary where emp_number=? ";
    public   String EMPLOYEESALARY_INSERT = "insert into employeesalary (salarygradeid, emp_number, basicsalary, payment_frequency) values(?,?,?,?) ";
    public   String EMPLOYEESALARY_UPDATE = "update employeesalary set salarygradeid=?, emp_number=?, basicsalary=?, payment_frequency=? where salaryid=? ";
    public   String EMPLOYEESALARY_DELETE = "delete from employeesalary where salaryid=? ";
    
    /* --------- EMPLOYEE CHILDREN ------------ */
    public  String EMPLOYEE_CHILDREN_SELECT_BY_EMPLOYEE = "select child_id, emp_number, seqno, birth_name, date_of_birth, placeofbirth, presentaddress, remarks from employee_children where emp_number=? ";
    public  String EMPLOYEE_CHILDREN_SELECT_BY_ID = "select child_id, emp_number, seqno, birth_name, date_of_birth, placeofbirth, presentaddress, remarks from employee_children where child_id=? ";
    public  String EMPLOYEE_CHILDREN_UPDATE = " update employee_children set emp_number=?, seqno=?, birth_name=?, date_of_birth=?, placeofbirth=?, presentaddress=?, remarks=? where child_id=? ";
    public  String EMPLOYEE_CHILDREN_INSERT = "insert into employee_children (emp_number, seqno, birth_name, date_of_birth, placeofbirth, presentaddress, remarks) values(?,?,?,?,?,?,?) ";
    public  String EMPLOYEE_CHILDREN_DELETE = "delete from employee_children where child_id=? ";
    public  String EMPLOYEE_CHILDREN_DELETE_ALL_EMPLOYEE = "delete from employee_children where emp_number=? ";

    /* --------- EMPLOYEE SPOUSE ------------ */
    public  String EMPLOYEE_SPOUSE_SELECT_BY_EMPLOYEE = "select spouse_id, emp_number, fullname, date_of_birth, placeofbirth, presentaddress from employee_spouse where emp_number=? ";
    public  String EMPLOYEE_SPOUSE_SELECT_BY_ID = "select spouse_id, emp_number, fullname, date_of_birth, placeofbirth, presentaddress from employee_spouse where spouse_id=? ";
    public  String EMPLOYEE_SPOUSE_UPDATE = " update employee_spouse set emp_number=?, fullname=?, date_of_birth=?, placeofbirth=?, presentaddress=? where spouse_id=? ";
    public  String EMPLOYEE_SPOUSE_INSERT = "insert into employee_spouse (emp_number, fullname, date_of_birth, placeofbirth, presentaddress) values(?,?,?,?,?) ";
    public  String EMPLOYEE_SPOUSE_DELETE = "delete from employee_spouse where spouse_id=? ";
    public  String EMPLOYEE_SPOUSE_DELETE_ALL_EMPLOYEE = "delete from employee_spouse where emp_number=? ";


    /* --------- EMPLOYEE DEPENDENTS --------- */
    public  String EMPLOYEE_DEPENDENTS_SELECT_BY_EMPLOYEE = "select dependent_id, emp_number, seqno, fullname, relationship from employee_dependents where emp_number=? ";
    public  String EMPLOYEE_DEPENDENTS_SELECT_BY_ID = "select dependent_id, emp_number, seqno, fullname, relationship from employee_dependents where dependent_id=? ";
    public  String EMPLOYEE_DEPENDENTS_INSERT = "insert into employee_dependents (emp_number, seqno, fullname, relationship) values(?,?,?,?) ";
    public  String EMPLOYEE_DEPENDENTS_UPDATE = "update employee_dependents set emp_number=?, seqno=?, fullname=?, relationship=? where dependent_id=? ";
    public  String EMPLOYEE_DEPENDENTS_DELETE = "delete from employee_dependents where dependent_id=? ";
    public  String EMPLOYEE_DEPENDENTS_DELETE_ALL_BY_EMPLOYEE = "delete from employee_dependents where emp_number=? ";
    
    /* --------- EMPLOYEE EDUCATION -------------- */
    public  String EMPLOYEE_EDUCATION_SELECT_BY_EMPLOYEE = "select edu_code, emp_number, institution, course, edu_year, edu_qualification, edu_start_date, edu_end_date from employee_education where emp_number =? ";
    public  String EMPLOYEE_EDUCATION_SELECT_BY_ID = "select edu_code, emp_number, institution, course, edu_year, edu_qualification, edu_start_date, edu_end_date from employee_education where edu_code =? ";
    public  String EMPLOYEE_EDUCATION_INSERT = "insert into employee_education (emp_number, institution, course, edu_year, edu_qualification, edu_start_date, edu_end_date) values (?,?,?,?,?,?,?) ";
    public  String EMPLOYEE_EDUCATION_UPDATE = "update employee_education set emp_number=?, institution=?, course=?, edu_year=?, edu_qualification=?, edu_start_date=?, edu_end_date=? where edu_code=? ";
    public  String EMPLOYEE_EDUCATION_DELETE = "delete from employee_education where edu_code = ? ";
    public  String EMPLOYEE_EDUCATION_DELETE_ALL_EMPLOYEE = "delete from employee_education where emp_number = ? ";
    
    /*---------- EMPLOYEE EMMERGENCY CONTACT ------------ */
    public  String EMPLOYEE_EMMERGENCY_CONTACT_SELECT_BY_EMPLOYEE = "select contact_id, emp_number, contact_name, relationship, home_no, mobile_no, office_no from employee_emergency_contacts where emp_number=? ";
    public  String EMPLOYEE_EMMERGENCY_CONTACT_SELECT_BY_ID = "select contact_id, emp_number, contact_name, relationship, home_no, mobile_no, office_no from employee_emergency_contacts where contact_id=? ";
    public  String EMPLOYEE_EMMERGENCY_CONTACT_INSERT = "insert into employee_emergency_contacts (emp_number, contact_name, relationship, home_no, mobile_no, office_no) values(?,?,?,?,?,?) ";
    public  String EMPLOYEE_EMMERGENCY_CONTACT_UPDATE = "update employee_emergency_contacts set emp_number=?, contact_name=?, relationship=?, home_no=?, mobile_no=?, office_no=? where contact_id=? ";
    public  String EMPLOYEE_EMMERGENCY_CONTACT_DELETE = "delete from employee_emergency_contacts where contact_id=? ";
    public  String EMPLOYEE_EMMERGENCY_CONTACT_DELETE_BY_EMPLOYEE = "delete from employee_emergency_contacts where emp_number=? ";
    
    /* --------- EMPLOYEE REPORT TO --------------------- */
    public  String EMPLOYEE_REPORT_TO_SELECT_BY_SUB = "select sub_emp_number, sup_emp_number, reporting_mode from employee_reportto where sub_emp_number = ? ";
    public  String EMPLOYEE_REPORT_TO_SELECT_BY_SUP = "select sub_emp_number, sup_emp_number, reporting_mode from employee_reportto where sup_emp_number = ? ";
    public  String EMPLOYEE_REPORT_TO_INSERT = "insert into employee_reportto (sub_emp_number, sup_emp_number, reporting_mode) values(?,?,?) ";
    public  String EMPLOYEE_REPORT_TO_UPDATE_BY_SUB_AND_MODE = "update employee_reportto set sub_emp_number=?, sup_emp_number=?, reporting_mode=? where sub_emp_number=? and reporting_mode=?  ";
    public  String EMPLOYEE_REPORT_TO_DELETE_BY_SUB_AND_MODE = "delete from employee_reportto where sub_emp_number=? and reporting_mode=?  ";
    public  String EMPLOYEE_REPORT_TO_DELETE_BY_SUB = "delete from employee_reportto where sub_emp_number=?  ";
    
    /*---------- EMPLOYEE SKILLS -------------------------*/
    public  String EMPLOYEE_SKILLS_SELECT_BY_EMPLOYEE = "select e.id, e.emp_number, e.skill_id, e.years_of_exp, e.comments, skill_name as skillname from employee_skill e, skill s where s.skill_id = e.skill_id and emp_number = ? ";
    public  String EMPLOYEE_SKILLS_SELECT_BY_ID = "select id, emp_number, skill_id, years_of_exp, comments from employee_skill where id = ? ";
    public  String EMPLOYEE_SKILLS_UPDATE = "update employee_skill set emp_number=?, skill_id=?, years_of_exp=?, comments=? where id = ? ";
    public  String EMPLOYEE_SKILLS_INSERT = "insert into employee_skill (emp_number, skill_id, years_of_exp, comments) values(?,?,?,?) ";
    public  String EMPLOYEE_SKILLS_DELETE_BY_ID = "delete from employee_skill where id = ? ";
    public  String EMPLOYEE_SKILLS_DELETE_BY_EMPLOYEE = "delete from employee_skill where emp_number = ? ";
    
    /*---------- EMPLOYEE WORK EXPERIENCE ---------------- */
    public  String EMPLOYEE_WORK_EXPERIENCE_SELECT = "select experienceid, emp_number, previous_employer, previous_jobtitle, previous_start_date, previous_end_date, comments, internal_movement from employee_work_experience where emp_number=? ";
    public  String EMPLOYEE_WORK_EXPERIENCE_SELECT_BY_ID = "select experienceid, emp_number, previous_employer, previous_jobtitle, previous_start_date, previous_end_date, comments, internal_movement from employee_work_experience where experienceid=? ";
    public  String EMPLOYEE_WORK_EXPERIENCE_INSERT = "insert into employee_work_experience (emp_number, previous_employer, previous_jobtitle, previous_start_date, previous_end_date, comments, internal_movement) values(?,?,?,?,?,?,?) ";
    public  String EMPLOYEE_WORK_EXPERIENCE_UPDATE = "update employee_work_experience set emp_number=?, previous_employer=?, previous_jobtitle=?, previous_start_date=?, previous_end_date=?, comments=?, internal_movement=?  where experienceid=? ";
    public  String EMPLOYEE_WORK_EXPERIENCE_DELETE_BY_ID = "delete from employee_work_experience where experienceid=? ";
    public  String EMPLOYEE_WORK_EXPERIENCE_DELETE_BY_EMPLOYEE = "delete from employee_work_experience where emp_number=? ";
    
    /*---------- EMPLOYEE BANK DETAIL --------------------- */
//    public  String EMPLOYEE_BANK_DETAIL_SELECT = "select id, emp_number, bank_name, bank_branch, account_name, account_number, account_type from employee_bankdetail where emp_number=? ";
//    public  String EMPLOYEE_BANK_DETAIL_INSERT = "insert into employee_bankdetail (emp_number, bank_name, bank_branch, account_name, account_number, account_type) values(?,?,?,?,?,?) ";
//    public  String EMPLOYEE_BANK_DETAIL_UPDATE = "update employee_bankdetail set bank_name=?, bank_branch=?, account_name=?, account_number=?, account_type=? where emp_number=? ";
    /* ----------- EMPLOYEE --------------------------- */
    public  String EMPLOYEE_SELECT_ALL_UNCONFIRMED = "select emp_number, emp_id, deptid, jobtitleid, employeetypeid, categoryid, salutation, lastname, firstname, dob, gender, marital, active, employment_date, confirmation_date, confirmed, employment_status, email, salarygradeid, company_code from employee where confirmed = 0 and active=1 and employment_status='A'  ";
    public  String EMPLOYEE_SELECT_ALL_UNCONFIRMED_SEARCH_FNAME = "select emp_number, emp_id, deptid, jobtitleid, employeetypeid, categoryid, salutation, lastname, firstname, dob, gender, marital, active, employment_date, confirmation_date, confirmed, employment_status, email, salarygradeid, company_code from employee where firstname like '%_SEARCH_%' and confirmed = 0 and active=1 and employment_status='A'  ";
    public  String EMPLOYEE_SELECT_ALL_UNCONFIRMED_SEARCH_LNAME = "select emp_number, emp_id, deptid, jobtitleid, employeetypeid, categoryid, salutation, lastname, firstname, dob, gender, marital, active, employment_date, confirmation_date, confirmed, employment_status, email, salarygradeid, company_code from employee where lastname like '%_SEARCH_%' and confirmed = 0 and active=1 and employment_status='A'  ";
    public  String EMPLOYEE_SELECT_ALL_UNCONFIRMED_SEARCH_ID = "select emp_number, emp_id, deptid, jobtitleid, employeetypeid, categoryid, salutation, lastname, firstname, dob, gender, marital, active, employment_date, confirmation_date, confirmed, employment_status, email, salarygradeid, company_code from employee where emp_id = '_SEARCH_' and confirmed = 0 and active=1 and employment_status='A'  ";

    public  String EMPLOYEE_SELECT_ALL_UNCONFIRMED_BY_COMPANY = "select emp_number, emp_id, deptid, jobtitleid, employeetypeid, categoryid, salutation, lastname, firstname, dob, gender, marital, active, employment_date, confirmation_date, confirmed, employment_status, email, salarygradeid, company_code from employee where confirmed = 0 and active=1 and employment_status='A' and company_code=? ";
    public  String EMPLOYEE_SELECT_ALL_UNCONFIRMED_BY_COMPANY_SEARCH_FNAME = "select emp_number, emp_id, deptid, jobtitleid, employeetypeid, categoryid, salutation, lastname, firstname, dob, gender, marital, active, employment_date, confirmation_date, confirmed, employment_status, email, salarygradeid, company_code from employee where firstname like '%_SEARCH_%' and confirmed = 0 and active=1 and employment_status='A' and company_code=? ";
    public  String EMPLOYEE_SELECT_ALL_UNCONFIRMED_BY_COMPANY_SEARCH_LNAME = "select emp_number, emp_id, deptid, jobtitleid, employeetypeid, categoryid, salutation, lastname, firstname, dob, gender, marital, active, employment_date, confirmation_date, confirmed, employment_status, email, salarygradeid, company_code from employee where lastname like '%_SEARCH_%' and confirmed = 0 and active=1 and employment_status='A' and company_code=? ";
    public  String EMPLOYEE_SELECT_ALL_UNCONFIRMED_BY_COMPANY_SEARCH_ID = "select emp_number, emp_id, deptid, jobtitleid, employeetypeid, categoryid, salutation, lastname, firstname, dob, gender, marital, active, employment_date, confirmation_date, confirmed, employment_status, email, salarygradeid, company_code from employee where emp_id = '_SEARCH_' and confirmed = 0 and active=1 and employment_status='A' and company_code=? ";

    public  String EMPLOYEE_UPDATE_UNCONFIRMED = "update employee set confirmation_date=?, confirmed=? where emp_number = ?  and active=1 and employment_status='A'  ";

    public  String EMPLOYEE_SELECT = "select * from employee where active=1 and employment_status='A' ";
    public  String EMPLOYEE_SELECT_FOR_ID = "select emp_number, emp_id, deptid, jobtitleid, employeetypeid, categoryid, salutation, lastname, firstname, dob, gender, marital, active, employment_date, confirmation_date, confirmed, employment_status, email, salarygradeid, company_code from employee where emp_id=? and active=1 and employment_status='A'  ";
    public  String EMPLOYEE_SELECT_FOR_ID_AND_COMPANY = "select emp_number, emp_id, deptid, jobtitleid, employeetypeid, categoryid, salutation, lastname, firstname, dob, gender, marital, active, employment_date, confirmation_date, confirmed, employment_status, email, salarygradeid, company_code from employee where emp_id=? and active=1 and employment_status='A' and company_code=? ";

    public  String EMPLOYEE_SELECT_ALL_FOR_BASIC = "select emp_number, emp_id, deptid, jobtitleid, employeetypeid, categoryid, salutation, lastname, firstname, dob, gender, marital, active, employment_date, confirmation_date, confirmed, employment_status, email, salarygradeid, company_code from employee where active=1 and employment_status='A'  ";
    public  String EMPLOYEE_SELECT_ALL_FOR_BASIC_SEARCH_FNAME = "select emp_number, emp_id, deptid, jobtitleid, employeetypeid, categoryid, salutation, lastname, firstname, dob, gender, marital, active, employment_date, confirmation_date, confirmed, employment_status, email,salarygradeid, company_code from employee where firstname like '%_SEARCH_%' and active=1 and employment_status='A' ";
    public  String EMPLOYEE_SELECT_ALL_FOR_BASIC_SEARCH_LNAME = "select emp_number, emp_id, deptid, jobtitleid, employeetypeid, categoryid, salutation, lastname, firstname, dob, gender, marital, active, employment_date, confirmation_date, confirmed, employment_status, email, salarygradeid, company_code from employee where lastname like '%_SEARCH_%' and active=1 and employment_status='A' ";
    public  String EMPLOYEE_SELECT_ALL_FOR_BASIC_SEARCH_ID = "select emp_number, emp_id, deptid, jobtitleid, employeetypeid, categoryid, salutation, lastname, firstname, dob, gender, marital, active, employment_date, confirmation_date, confirmed, employment_status, email, salarygradeid, company_code from employee where emp_id = '_SEARCH_' and active=1 and employment_status='A' ";
    public  String EMPLOYEE_SELECT_ALL_FOR_BASIC_SEARCH_COMPANY = "select emp_number, emp_id, deptid, jobtitleid, employeetypeid, categoryid, salutation, lastname, firstname, dob, gender, marital, active, employment_date, confirmation_date, confirmed, employment_status, email, salarygradeid, company_code from employee where company_code = '_SEARCH_' and active=1 and employment_status='A' ";

    public  String EMPLOYEE_SELECT_ALL_FOR_BASIC_BY_COMPANY = "select emp_number, emp_id, deptid, jobtitleid, employeetypeid, categoryid, salutation, lastname, firstname, dob, gender, marital, active, employment_date, confirmation_date, confirmed, employment_status, email, salarygradeid, company_code from employee where active=1 and employment_status='A' and company_code=?  ";
    public  String EMPLOYEE_SELECT_ALL_FOR_BASIC_BY_COMPANY_SEARCH_FNAME = "select emp_number, emp_id, deptid, jobtitleid, employeetypeid, categoryid, salutation, lastname, firstname, dob, gender, marital, active, employment_date, confirmation_date, confirmed, employment_status, email,salarygradeid, company_code from employee where firstname like '%_SEARCH_%' and active=1 and employment_status='A'  and company_code=? ";
    public  String EMPLOYEE_SELECT_ALL_FOR_BASIC_BY_COMPANY_SEARCH_LNAME = "select emp_number, emp_id, deptid, jobtitleid, employeetypeid, categoryid, salutation, lastname, firstname, dob, gender, marital, active, employment_date, confirmation_date, confirmed, employment_status, email, salarygradeid, company_code from employee where lastname like '%_SEARCH_%' and active=1 and employment_status='A' and company_code=? ";
    public  String EMPLOYEE_SELECT_ALL_FOR_BASIC_BY_COMPANY_SEARCH_ID = "select emp_number, emp_id, deptid, jobtitleid, employeetypeid, categoryid, salutation, lastname, firstname, dob, gender, marital, active, employment_date, confirmation_date, confirmed, employment_status, email, salarygradeid, company_code from employee where emp_id = '_SEARCH_' and active=1 and employment_status='A' and company_code=? ";
    public  String EMPLOYEE_SELECT_ALL_FOR_BASIC_BY_COMPANY_SEARCH_COMPANY = "select emp_number, emp_id, deptid, jobtitleid, employeetypeid, categoryid, salutation, lastname, firstname, dob, gender, marital, active, employment_date, confirmation_date, confirmed, employment_status, email, salarygradeid, company_code from employee where company_code = '_SEARCH_' and active=1 and employment_status='A' and company_code=? ";

    public  String EMPLOYEE_SELECT_BASIC = "select emp_number, emp_id, deptid, jobtitleid, employeetypeid, categoryid, salutation, lastname, firstname, dob, gender, marital, active, employment_date, confirmation_date, confirmed, employment_status, email, salarygradeid, company_code from employee where emp_number=? and active=1 and employment_status='A'";

    public  String EMPLOYEE_SELECT_CONTACT = "select address1, address2, city, state_of_address, zipcode, country, email, homephone, officephone, cellphone from employee where emp_number=?  and active=1 and employment_status='A' ";
    public  String EMPLOYEE_DELETE = "update employee set active=0 where emp_number = ? ";
    public  String EMPLOYEE_UPDATE_BASIC = "update employee set emp_id=?, deptid=?, jobtitleid=?, employeetypeid=?, categoryid=?, salutation=?, lastname=?, firstname=?, dob=?, gender=?, marital=?, active=?, employment_date=?, email=?, salarygradeid=? where emp_number = ? and active=1 ";
    public  String EMPLOYEE_UPDATE_CONTACTS = "update employee set address1=?, address2=?, city=?, state_of_address=?, zipcode=?, country=?, homephone=?, officephone=?, cellphone=?  where emp_number = ? and active=1 ";
    public  String EMPLOYEE_INSERT_BASIC = "insert into employee(emp_id, deptid, jobtitleid, employeetypeid, categoryid, salutation, lastname, firstname, dob, gender, marital, active, employment_date, confirmation_date, confirmed, employment_status, email, salarygradeid, company_code) values(?,?,?,?,?,?,?,?,?,?,?,1,?,getDate(),0,'A', ?,?,?) ";
    public  String EMPLOYEE_INSERT_CONTACTS = "insert into employee(address1, address2, city, state_of_address, zipcode, country, homephone, officephone, cellphone) values(?,?,?,?,?,?,?,?,?) ";
    
    public  String EMPLOYEE_UPDATE_EMPLOYMENT_STATUS = "update employee set employment_status=?, active=? where emp_number = ? ";

    /* -------------- EMPLOYEE SELF-SERVICE REQUEST/APPLICATION -------------------------*/
    public  String EMPLOYEE_BIO_TRANSACTION_SELECT_BY_TRANSACTION = "select emp_number, salutation, lastname, firstname, dob, gender, marital, address1, address2, city, state_of_address, zipcode, country, email, homephone, officephone, cellphone from e_employee where transactionid=?";
    public  String EMPLOYEE_BIO_TRANSACTION_INSERT = "insert into e_employee (emp_number, salutation, lastname, firstname, dob, gender, marital, address1, address2, city, state_of_address, zipcode, country, email, homephone, officephone, cellphone, transactionid, estatus) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
    public  String EMPLOYEE_BIO_TRANSACTION_UPDATE = "update employee set salutation=?, lastname=?, firstname=?, dob=?, gender=?, marital=?, address1=?, address2=?, city=?, state_of_address=?, zipcode=?, country=?, email=?, homephone=?, officephone=?, cellphone=? where emp_number=? ";
    public  String EMPLOYEE_BIO_TRANSACTION_UPDATE_STATUS = "update e_employee set estatus=? where transactionid=? ";

    /* --------- EMPLOYEE ESS CHILDREN --------- */
    public  String EMPLOYEE_ESS_CHILDREN_SELECT_BY_EMPLOYEE = "select emp_number, seqno, birth_name, date_of_birth, placeofbirth, presentaddress, remarks from e_employee_children where emp_number=? ";
    public  String EMPLOYEE_ESS_CHILDREN_SELECT_BY_SEQUENCE = "select emp_number, seqno, birth_name, date_of_birth, placeofbirth, presentaddress, remarks from e_employee_children where seqno=? ";
    public  String EMPLOYEE_ESS_CHILDREN_UPDATE = " update e_employee_children set emp_number=?, seqno=?, birth_name=?, date_of_birth=?, transactionid=?, estatus=?, placeofbirth=?, presentaddress=?, remarks=? where seqno=? ";
    public  String EMPLOYEE_ESS_CHILDREN_INSERT = "insert into e_employee_children (emp_number, seqno, birth_name, date_of_birth, transactionid, estatus, placeofbirth, presentaddress, remarks) values(?,?,?,?,?,?, ?,?, ?) ";
    public  String EMPLOYEE_ESS_CHILDREN_DELETE = "delete from e_employee_children where seqno=? ";
    public  String EMPLOYEE_ESS_CHILDREN_DELETE_ALL_EMPLOYEE = "delete from e_employee_children where emp_number=? ";
    
    //move next linesto procedure later
    public  String EMPLOYEE_ESS_CHILDREN_MIGRATION = "insert into e_employee_children (emp_number, seqno, birth_name, date_of_birth, transactionid, estatus, placeofbirth, presentaddress, remarks) select emp_number, seqno, birth_name, date_of_birth, null, 'M', placeofbirth, presentaddress, remarks from employee_children where emp_number=? ";
    public  String EMPLOYEE_ESS_DEPENDENTS_MIGRATION = "insert into e_employee_dependents (emp_number, seqno, fullname, relationship, transactionid, estatus) select emp_number, seqno, fullname, relationship, null, 'M' from employee_dependents where emp_number=? ";

    public  String EMPLOYEE_ESS_CHILDREN_MIGRATION_REVERSED = "insert into employee_children (emp_number, seqno, birth_name, date_of_birth, placeofbirth, presentaddress, remarks) select emp_number, seqno, birth_name, date_of_birth, placeofbirth, presentaddress, remarks from e_employee_children where emp_number=? and estatus='P' ";
    public  String EMPLOYEE_ESS_DEPENDENTS_MIGRATION_REVERSED = "insert into employee_dependents (emp_number, seqno, fullname, relationship) select emp_number, seqno, fullname, relationship from e_employee_dependents where emp_number=? and estatus ='P' ";

    public  String EMPLOYEE_ESS_CHILDREN_MIGRATION_ARCHIVED = "insert into archive_employee_children (emp_number, seqno, birth_name, date_of_birth, transactionid, estatus, placeofbirth, presentaddress, remarks) select emp_number, seqno, birth_name, date_of_birth, transactionid, estatus, placeofbirth, presentaddress, remarks from e_employee_children where transactionid=? ";
    public  String EMPLOYEE_ESS_DEPENDENTS_MIGRATION_ARCHIVED = "insert into archive_employee_dependents (emp_number, seqno, fullname, relationship, transactionid, estatus) select emp_number, seqno, fullname, relationship, transactionid, estatus from e_employee_dependents where transactionid=? ";

    public  String EMPLOYEE_ESS_CHILDREN_INITIATE_TRANSACTION = "update e_employee_children set transactionid=?, estatus=? where emp_number=? and estatus=? ";
    public  String EMPLOYEE_ESS_DEPENDENT_INITIATE_TRANSACTION = "update e_employee_dependents set transactionid=?, estatus=? where emp_number=? and estatus=? ";

    /* --------- EMPLOYEE ESS DEPENDENTS --------- */
    public  String EMPLOYEE_ESS_DEPENDENTS_SELECT_BY_EMPLOYEE = "select emp_number, seqno, fullname, relationship from e_employee_dependents where emp_number=? ";
    public  String EMPLOYEE_ESS_DEPENDENTS_SELECT_BY_SEQUENCE = "select emp_number, seqno, fullname, relationship from e_employee_dependents where seqno=? ";
    public  String EMPLOYEE_ESS_DEPENDENTS_INSERT = "insert into e_employee_dependents (emp_number, seqno, fullname, relationship, transactionid, estatus) values(?,?,?,?,?,?) ";
    public  String EMPLOYEE_ESS_DEPENDENTS_UPDATE = "update e_employee_dependents set emp_number=?, seqno=?, fullname=?, relationship=? where seqno=? ";
    public  String EMPLOYEE_ESS_DEPENDENTS_DELETE = "delete from e_employee_dependents where seqno=? ";
    public  String EMPLOYEE_ESS_DEPENDENTS_DELETE_ALL_BY_EMPLOYEE = "delete from e_employee_dependents where emp_number=? ";

    /* --------- EMPLOYEE ESS EDUCATION -------------- */
    public  String EMPLOYEE_ESS_EDUCATION_MIGRATION = "insert into e_employee_education (emp_number, institution, course, edu_year, edu_qualification, edu_start_date, edu_end_date, transactionid, estatus) select emp_number, institution, course, edu_year, edu_qualification, edu_start_date, edu_end_date, null, 'M' from employee_education where emp_number=? ";
    public  String EMPLOYEE_ESS_EDUCATION_MIGRATION_REVERSED = "insert into employee_education (emp_number, institution, course, edu_year, edu_qualification, edu_start_date, edu_end_date) select emp_number, institution, course, edu_year, edu_qualification, edu_start_date, edu_end_date from e_employee_education where emp_number=? and estatus='P' ";
    public  String EMPLOYEE_ESS_EDUCATION_MIGRATION_ARCHIVED = "insert into archive_employee_education (emp_number, institution, course, edu_year, edu_qualification, edu_start_date, edu_end_date, transactionid, estatus) select emp_number, institution, course, edu_year, edu_qualification, edu_start_date, edu_end_date, transactionid, estatus from e_employee_education where transactionid=? ";
    public  String EMPLOYEE_ESS_EDUCATION_INITIATE_TRANSACTION = "update e_employee_education set transactionid=?, estatus=? where emp_number=? and estatus=? ";


    public  String EMPLOYEE_ESS_EDUCATION_SELECT_BY_EMPLOYEE = "select edu_code, emp_number, institution, course, edu_year, edu_qualification, edu_start_date, edu_end_date from e_employee_education where emp_number =? ";
    public  String EMPLOYEE_ESS_EDUCATION_SELECT_BY_ID = "select edu_code, emp_number, institution, course, edu_year, edu_qualification, edu_start_date, edu_end_date from e_employee_education where edu_code =? ";
    public  String EMPLOYEE_ESS_EDUCATION_INSERT = "insert into e_employee_education (emp_number, institution, course, edu_year, edu_qualification, edu_start_date, edu_end_date, transactionid, estatus) values (?,?,?,?,?,?,?,?,?) ";
    public  String EMPLOYEE_ESS_EDUCATION_UPDATE = "update e_employee_education set emp_number=?, institution=?, course=?, edu_year=?, edu_qualification=?, edu_start_date=?, edu_end_date=? where edu_code=? ";
    public  String EMPLOYEE_ESS_EDUCATION_DELETE = "delete from e_employee_education where edu_code = ? ";
    public  String EMPLOYEE_ESS_EDUCATION_DELETE_ALL_EMPLOYEE = "delete from e_employee_education where emp_number = ? ";

    /*---------- EMPLOYEE ESS EMMERGENCY CONTACT ------------ */
    public  String EMPLOYEE_ESS_EMMERGENCY_CONTACT_MIGRATION = "insert into e_employee_emergency_contacts (emp_number, contact_name, relationship, home_no, mobile_no, office_no, transactionid, estatus) select emp_number, contact_name, relationship, home_no, mobile_no, office_no, null, 'M' from employee_emergency_contacts where emp_number=? ";
    public  String EMPLOYEE_ESS_EMMERGENCY_CONTACT_MIGRATION_REVERSED = "insert into employee_emergency_contacts (emp_number, contact_name, relationship, home_no, mobile_no, office_no) select emp_number, contact_name, relationship, home_no, mobile_no, office_no from e_employee_emergency_contacts where emp_number=? and estatus='P' ";
    public  String EMPLOYEE_ESS_EMMERGENCY_CONTACT_MIGRATION_ARCHIVED = "insert into archive_employee_emergency_contacts (emp_number, contact_name, relationship, home_no, mobile_no, office_no, transactionid, estatus) select emp_number, contact_name, relationship, home_no, mobile_no, office_no, transactionid, estatus from e_employee_emergency_contacts where transactionid=? ";
    public  String EMPLOYEE_ESS_EMMERGENCY_CONTACT_INITIATE_TRANSACTION = "update e_employee_emergency_contacts set transactionid=?, estatus=? where emp_number=? and estatus=? ";

    public  String EMPLOYEE_ESS_EMMERGENCY_CONTACT_SELECT_BY_EMPLOYEE = "select contact_id, emp_number, contact_name, relationship, home_no, mobile_no, office_no from e_employee_emergency_contacts where emp_number=? ";
    public  String EMPLOYEE_ESS_EMMERGENCY_CONTACT_SELECT_BY_ID = "select contact_id, emp_number, contact_name, relationship, home_no, mobile_no, office_no from e_employee_emergency_contacts where contact_id=? ";
    public  String EMPLOYEE_ESS_EMMERGENCY_CONTACT_INSERT = "insert into e_employee_emergency_contacts (emp_number, contact_name, relationship, home_no, mobile_no, office_no, transactionid, estatus) values(?,?,?,?,?,?,?,?) ";
    public  String EMPLOYEE_ESS_EMMERGENCY_CONTACT_UPDATE = "update e_employee_emergency_contacts set emp_number=?, contact_name=?, relationship=?, home_no=?, mobile_no=?, office_no=? where contact_id=? ";
    public  String EMPLOYEE_ESS_EMMERGENCY_CONTACT_DELETE = "delete from e_employee_emergency_contacts where contact_id=? ";
    public  String EMPLOYEE_ESS_EMMERGENCY_CONTACT_DELETE_BY_EMPLOYEE = "delete from e_employee_emergency_contacts where emp_number=? ";

    /*---------- EMPLOYEE ESS BANK DETAIL --------------------- */
    public  String EMPLOYEE_ESS_BANK_DETAIL_SELECT = "select emp_number, bank_name, bank_branch, account_name, account_number, account_type, transactionid, estatus from e_employee_bankdetail where transactionid=? ";
    public  String EMPLOYEE_ESS_BANK_DETAIL_INSERT = "insert into e_employee_bankdetail (emp_number, bank_name, bank_branch, account_name, account_number, account_type, transactionid, estatus) values(?,?,?,?,?,?,?,?) ";
    public  String EMPLOYEE_ESS_BANK_DETAIL_UPDATE = "update e_employee_bankdetail set bank_name=?, bank_branch=?, account_name=?, account_number=?, account_type=?, transactionid=?, estatus=? where emp_number=? ";
    public  String EMPLOYEE_ESS_BANK_DETAIL_UPDATE_STATUS_BY_TRANSACTION = "update e_employee_bankdetail set estatus=? where transactionid=? ";
    public  String EMPLOYEE_ESS_BANK_DETAIL_MIGRATE_FROM_ESS = "insert into payroll_employee_bankdetail (emp_number, bank_name, bank_branch, account_name, account_number, account_type) select emp_number, bank_name, bank_branch, account_name, account_number, account_type from e_employee_bankdetail where transactionid=? ";
    public  String EMPLOYEE_ESS_BANK_DETAIL_MIGRATE_UPDATE_FROM_ESS = " update payroll_employee_bankdetail set bank_name=?, bank_branch=?, account_name=?, account_number=?, account_type=?  where emp_number=? ";
    //public  String EMPLOYEE_BANK_DETAIL_INSERT = "insert into payroll_employee_bankdetail (emp_number, bank_name, bank_branch, account_name, account_number, account_type, policyid) values(?,?,?,?,?,?,?) ";

    /* ------------- EMPLOYEE TIME employee_attendance ------------- */
    public  String EMPLOYEE_ATTENDANCE_SELECT = "select a.idx, a.employee_id, action_date, action_type, action_time, a.device, a.punch_error, a.custom1, a.custom2, e.firstname, e.lastname from employee_attendance a, employee e where a.employee_id = e.emp_number order by convert(varchar(10), action_date, 112) + ' ' + action_time asc ";
    public  String EMPLOYEE_ATTENDANCE_SELECT_BY_EMPLOYEE = "select a.idx, a.employee_id, action_date, action_type, action_time, a.device, a.punch_error, a.custom1, a.custom2, e.firstname, e.lastname from employee_attendance a, employee e where a.employee_id = e.emp_number and employee_id = ? order by convert(varchar(10), action_date, 112) + ' ' + action_time asc  ";
    public  String EMPLOYEE_ATTENDANCE_SELECT_BY_ID = "select idx, employee_id, action_date, action_type, action_time, device, punch_error, custom1, custom2 from employee_attendance where idx = ? ";
    public  String EMPLOYEE_ATTENDANCE_INSERT = "insert into employee_attendance (employee_id, action_date, action_type, action_time, device, punch_error, custom1, custom2) select isNull((select emp_number from employee where emp_id=? and company_code=?),0), ?,?,?,?,?,?,?";
    public  String EMPLOYEE_ATTENDANCE_UPDATE = "update employee_attendance set employee_id=?, action_date=?, action_type=?, action_time=?, device=?, punch_error=?, custom1=?, custom2=? where idx=? ";
    public  String EMPLOYEE_ATTENDANCE_DELETE = "delete from employee_attendance where idx=? ";

    /* ----------------- EMPLOYEE TERMINATION ------------------------- */
    public  String EMPLOYEE_SELECT_ALL_TERMINATED = "select emp_number, emp_id, deptid, jobtitleid, employeetypeid, categoryid, salutation, lastname, firstname, dob, gender, marital, active, employment_date, confirmation_date, confirmed, employment_status, company_code from employee where employment_status in ('RESIGN','TERMINATE') ";
    public  String EMPLOYEE_SELECT_ALL_TERMINATED_BY_COMPANY = "select emp_number, emp_id, deptid, jobtitleid, employeetypeid, categoryid, salutation, lastname, firstname, dob, gender, marital, active, employment_date, confirmation_date, confirmed, employment_status, company_code from employee where employment_status in ('RESIGN','TERMINATE') and company_code=? ";
    public  String EMPLOYEE_TERMINATION_SELECT = " select t.id, t.emp_number, t.operation, t.reason, t.effective_date, t.effective_period, t.transactionid, t.estatus, e.firstname, e.lastname, company_code from employee_termination t, employee e where e.emp_number = t.emp_number ";
    public  String EMPLOYEE_TERMINATION_SELECT_BY_COMPANY = " select t.id, t.emp_number, t.operation, t.reason, t.effective_date, t.effective_period, t.transactionid, t.estatus, e.firstname, e.lastname, company_code from employee_termination t, employee e where e.emp_number = t.emp_number and company_code=? ";
    public  String EMPLOYEE_TERMINATION_SELECT_BY_ID = " select t.id, t.emp_number, t.operation, t.reason, t.effective_date, t.effective_period, t.transactionid, t.estatus, e.firstname, e.lastname from employee_termination t, employee e where e.emp_number = t.emp_number and id=? ";
    public  String EMPLOYEE_TERMINATION_SELECT_BY_TRANSACTION = " select t.id, t.emp_number, t.operation, t.reason, t.effective_date, t.effective_period, t.transactionid, t.estatus, e.firstname, e.lastname from employee_termination t, employee e where e.emp_number = t.emp_number and transactionid=? ";
    public  String EMPLOYEE_TERMINATION_INSERT = " insert into employee_termination (emp_number, operation, reason, effective_date, effective_period, transactionid, estatus) values(?,?,?,?,?,?,?) ";
    public  String EMPLOYEE_TERMINATION_UPDATE = " update employee_termination set emp_number=?, operation=?, reason=?, effective_date=?, effective_period=? where id=? ";
    public  String EMPLOYEE_TERMINATION_TRANSACTION_UPDATE = " update employee_termination set estatus=? where transactionid=? ";
    public  String EMPLOYEE_TERMINATION_DELETE = " delete from employee_termination where id=? ";

    /*  ------------- REPORT QUERY -------------------- */
    public  String REPORT_CODE_SELECT = "select * from report_template where code = ? ";
    
    /* ------------- PERFORMANCE TARGET (performance_target) ------------ */
    public  String PERFORMANCE_TARGET_SELECT = "select idx, employee_id, assignment, target_weight, target_amount, start_date, end_date, note, status, transactionid, estatus from performance_target where estatus='A' order by 1, 2 ";
    public  String PERFORMANCE_TARGET_SELECT_BY_ID = "select idx, employee_id, assignment, target_weight, target_amount, start_date, end_date, note, status, transactionid, estatus from performance_target where estatus='A' and idx=? ";
    public  String PERFORMANCE_TARGET_SELECT_BY_TRANSACTION = "select idx, employee_id, assignment, target_weight, target_amount, start_date, end_date, note, status, transactionid, estatus from performance_target where transactionid=? ";
    public  String PERFORMANCE_TARGET_SELECT_BY_EMPLOYEE = "select idx, employee_id, assignment, target_weight, target_amount, start_date, end_date, note, status, transactionid, estatus from performance_target where estatus='A' and employee_id=? ";
    public  String PERFORMANCE_TARGET_UPDATE = "update performance_target set employee_id=?, assignment=?, target_weight=?, target_amount=?, start_date=?, end_date=?, note=?, status=?, transactionid=?, estatus=? where idx=? ";
    public  String PERFORMANCE_TARGET_INSERT = "insert into performance_target (employee_id, assignment, target_weight, target_amount, start_date, end_date, note, status, transactionid, estatus) values(?,?,?,?,?,?,?,?,?,?) ";
    public  String PERFORMANCE_TARGET_DELETE = "delete from performance_target where idx=? ";

    public  String PERFORMANCE_TARGET_TRANX_SELECT = "select idx, employee_id, assignment, target_weight, target_amount, start_date, end_date, note, status, transactionid, estatus from performance_target where estatus='A' order by 1, 2 ";

    /* ------------- PERFORMANCE REPORT (Performance_report) --------------- */
    public  String PERFORMANCE_REPORT_SELECT = "select idx, target_id, progress_report, report_date, amount_acheived, status, note from performance_report ";
    public  String PERFORMANCE_REPORT_SELECT_BY_ID = "select idx, target_id, progress_report, report_date, amount_acheived, status, note from performance_report where idx=? ";
    public  String PERFORMANCE_REPORT_SELECT_BY_TARGET = "select idx, target_id, progress_report, report_date, amount_acheived, status, note from performance_report where target_id=? order by report_date desc ";
    public  String PERFORMANCE_REPORT_UPDATE = "update performance_report set progress_report=?, report_date=?, amount_acheived=?, status=?, note=? where idx=? ";
    public  String PERFORMANCE_REPORT_INSERT = "insert into performance_report (target_id, progress_report, report_date, amount_acheived, status, note) values(?,?,?,?,?,?) ";
    public  String PERFORMANCE_REPORT_DELETE = "delete from performance_report where idx=? ";

    /* ------------ PERFORMANCE APPRAISAL CRITERIA -------------------------- */
    /*  Appraisal Criteria */
    public  String APPRAISAL_CRITERIA_SELECT = "select id, description, note, company_code from appraisal_criteria ";
    public  String APPRAISAL_CRITERIA_SELECT_BY_ID = "select id, description, note, company_code from appraisal_criteria where id=?";
    public  String APPRAISAL_CRITERIA_UPDATE = "update appraisal_criteria set description=?, note=? company_code=?, where id=? ";
    public  String APPRAISAL_CRITERIA_INSERT = "insert into appraisal_criteria(description, note, company_code) values(?,?,?) ";
    public  String APPRAISAL_CRITERIA_DELETE = "delete from appraisal_criteria where jobid = ? ";
    public  String APPRAISAL_CRITERIA_SELECT_BY_COMPANY = "select id, description, note, company_code from appraisal_criteria where company_code=? ";

    /*  Appraisal Group */
    public  String APPRAISAL_GROUP_SELECT = "select id, description, note, criteria_id from appraisal_group ";
    public  String APPRAISAL_GROUP_SELECT_BY_ID = "select id, description, note, criteria_id, for_job_specific from appraisal_group where id=?";
    public  String APPRAISAL_GROUP_SELECT_BY_CRITERIA = "select id, description, note, criteria_id,for_job_specific from appraisal_group where criteria_id=?";
    public  String APPRAISAL_GROUP_UPDATE = "update appraisal_group set description=?, note=?, criteria_id=?, for_job_specific=? where id=? ";
    public  String APPRAISAL_GROUP_INSERT = "insert into appraisal_group(description, note, criteria_id,for_job_specific) values(?,?,?,?) ";
    public  String APPRAISAL_GROUP_DELETE = "delete from appraisal_group where id = ? ";

    /*  Appraisal Competencies */
    public  String APPRAISAL_COMPETENCE_SELECT = "select id, description, statement, group_id from appraisal_competence ";
    public  String APPRAISAL_COMPETENCE_SELECT_BY_ID = "select id, description, statement, group_id from appraisal_competence where id=?";
    public  String EMPLOYEE_APPRAISAL_COMPETENCE_SELECT_BY_ID = "select t1.id, t1.description, t1.statement, t1.group_id,t1.emp_number, t2.firstname, t2.lastname  from job_specific_appraisal_competence t1, employee t2 where t1.id=? and t1.emp_number=t2.emp_number";
    public  String APPRAISAL_COMPETENCE_SELECT_BY_GROUP = "select id, description, statement, group_id from appraisal_competence where group_id=?";
    public  String EMPLOYEE_APPRAISAL_COMPETENCE_SELECT_BY_GROUP = "select t1.id, t1.description, t1.statement, t1.group_id, t1.emp_number,t2.firstname, t2.lastname from job_specific_appraisal_competence t1, employee t2 where group_id=? and t1.emp_number=t2.emp_number";
    public  String EMPLOYEE_APPRAISAL_COMPETENCE_SELECT_BY_GROUP_AND_BY_EMP_ID = "select t1.id, t1.description, t1.statement, t1.group_id, t1.emp_number from job_specific_appraisal_competence t1, employee t2 where group_id=? and t1.emp_number=? and t1.emp_number=t2.emp_number";
    public  String APPRAISAL_COMPETENCE_UPDATE = "update appraisal_competence set description=?, statement=?, group_id=? where id=? ";
    public  String EMPLOYEE_APPRAISAL_COMPETENCE_UPDATE = "update job_specific_appraisal_competence set description=?, statement=?, group_id=?,emp_number=?  where id=? ";
    public  String APPRAISAL_COMPETENCE_INSERT = "insert into appraisal_competence(description, statement, group_id) values(?,?,?) ";
    public  String EMPLOYEE_APPRAISAL_COMPETENCE_INSERT = "insert into job_specific_appraisal_competence(description, statement, group_id, emp_number) values(?,?,?,?) ";

    public  String APPRAISAL_COMPETENCE_DELETE = "delete from appraisal_competence where id = ? ";

    /* Appraisal Rating Language */
    public  String APPRAISAL_RATING_SELECT = "select rate_index, rate_text from appraisal_rating_language ";
    public  String APPRAISAL_RATING_SELECT_BY_ID = "select rate_index, rate_text from appraisal_rating_language where rate_index=?";
    public  String APPRAISAL_RATING_UPDATE = "update appraisal_rating_language set rate_index=?, rate_text=? where rate_index=? ";
    public  String APPRAISAL_RATING_INSERT = "insert into appraisal_rating_language(rate_index, rate_text) values(?,?) ";
    public  String APPRAISAL_RATING_DELETE = "delete from appraisal_rating_language where rate_index = ? ";

    /* ============== Appraisal Transaction Authorization ================== */
    public  String APPRAISAL_TRANSACTION_SELECT = "select id, emp_number, transactionid, estatus from appraisal_step where transactionid = ? ";

    public  String APPRAISAL_STEP1_TRNX_SELECT = "select appraisal_id, start_period, end_period, appraisal_criteria, supervisor, applied_level from appraisal_step1 where appraisal_id=? ";
    public  String APPRAISAL_STEP2_TRNX_SELECT = "select appraisal_id, group_id, competence_id, rate_index, transactionid, istransaction, supervisor, applied_level from appraisal_step2 where appraisal_id=? order by applied_level, 4 ";
    public  String APPRAISAL_STMT_STEP2_TRNX_SELECT = "select appraisal_id, group_id, statement, supervisor, applied_level from appraisal_stmt_step2 where appraisal_id=? ";
    public  String APPRAISAL_STEP3_TRNX_SELECT = "select appraisal_id, emp_number, due_date, goal_title, description, goal_result, rate_index, supervisor, applied_level from appraisal_step3 where appraisal_id=? order by applied_level, due_date ";
    public  String APPRAISAL_STEP4_TRNX_SELECT = "select appraisal_id, statement, improvement, supervisor, applied_level from appraisal_step4 where appraisal_id=? ";

    public  String APPRAISAL_STMT_STEP2_APPROVE_SELECT = "select emp_number, group_id, statement, transactionid, istransaction from e_appraisal_stmt_step2 where emp_number=? and transactionid=? ";
    
    public  String APPRAISAL_FINALIZER = " prcSaveAppraisal(?,?,?,?) ";
    public  String APPRAISAL_FINALIZER_SELF = " prcSaveSelfAppraisal(?,?,?,?) ";
    /**
     * This   instance is for the procedure
     * prcApproveAppraisal (@employeeId numeric(20,0), @transactionid numeric(20,0), @supervisor numeric(20,0), @actionStatus varchar(1))
     */
    public  String APPRAISAL_APPROVER_FINALIZER = " prcApproveAppraisal(?,?,?,?,?) ";

    /* ============== END ============= */
    /* ============ APPRAISAL VIEW WHILE IN TRANSACTION =================== */
    public  String APPRAISAL_STEP1_VIEW = "select a.appraisal_id, a.start_period, a.end_period, c.description as criteria, e.firstname, e.lastname, a.applied_level " +
            "from appraisal_step1 a join appraisal_criteria c on a.appraisal_criteria = c.id join employee e on e.emp_number = a.supervisor " +
            "where a.applied_level = 0 and a.appraisal_id=? order by a.applied_level ";
    public  String APPRAISAL_STEP2_VIEW = "select a.appraisal_id, a.group_id, g.description as group_name, c.description as comptence_desc, " +
            " r.rate_index as rate_index, r.rate_text as rate_text, e.firstname, e.lastname, a.supervisor " +
            " from appraisal_step2 a join appraisal_group g on g.id = a.group_id join appraisal_competence c on g.id = c.group_id and a.competence_id = c.id " +
            " join appraisal_rating_language r on r.rate_index = a.rate_index join employee e on e.emp_number = a.supervisor " +
            " where a.appraisal_id=? order by applied_level";
    public  String APPRAISAL_STEP2_STMT_VIEW = "select a.appraisal_id, a.group_id, a.statement, e.firstname, e.lastname, a.supervisor from appraisal_stmt_step2 a join employee e on a.supervisor = e.emp_number " +
            " where a.appraisal_id=? order by applied_level  ";
    public  String APPRAISAL_STEP3_VIEW = "select a.appraisal_id, a.due_date, a.goal_title, a.description, a.goal_result, r.rate_index as rate_index, " +
            " r.rate_text as rate_text, e.firstname, e.lastname from appraisal_step3 a join appraisal_rating_language r on a.rate_index = r.rate_index " +
            " join employee e on e.emp_number = a.supervisor where a.appraisal_id=? order by applied_level";
    public  String APPRAISAL_STEP4_VIEW = "select a.appraisal_id, a.statement, a.improvement, e.firstname, e.lastname from appraisal_step4 a " +
            " join employee e on a.supervisor = e.emp_number where a.appraisal_id=? order by applied_level ";

    /* ============END ================= */

    public  String APPRAISAL_STEP1_SELECT = "select emp_number, start_period, end_period, reviewer, appraisal_criteria, transactionid, istransaction from e_appraisal_step1 where emp_number=? ";
    public  String APPRAISAL_STEP1_INSERT = "insert into e_appraisal_step1 (emp_number, start_period, end_period, reviewer, appraisal_criteria, transactionid, istransaction) values(?,?,?,?,?,?,?) ";
    public  String APPRAISAL_STEP1_DELETE = "delete from e_appraisal_step1 where emp_number=? ";

    public  String APPRAISAL_STEP2_SELECT = "select emp_number, group_id, competence_id, rate_index, transactionid, istransaction from e_appraisal_step2 where emp_number=? order by 4 ";
    public  String APPRAISAL_STEP2_INSERT = "insert into e_appraisal_step2 (emp_number, group_id, competence_id, rate_index, transactionid, istransaction) values(?,?,?,?,?,?) ";
    public  String APPRAISAL_STEP2_DELETE = "delete from e_appraisal_step2 where emp_number=? ";// and transactionid is null ";

    public  String APPRAISAL_STMT_STEP2_SELECT = "select emp_number, group_id, statement, transactionid, istransaction from e_appraisal_stmt_step2 where emp_number=? ";
    public  String APPRAISAL_STMT_STEP2_INSERT = "insert into e_appraisal_stmt_step2 (emp_number, group_id, statement, transactionid, istransaction) values(?,?,?,?,?) ";
    public  String APPRAISAL_STMT_STEP2_DELETE = "delete from e_appraisal_stmt_step2 where emp_number=?";// and transactionid is null";

    public  String APPRAISAL_STEP3_SELECT = "select id, emp_number, due_date, goal_title, description, goal_result, rate_index, transactionid, istransaction from e_appraisal_step3 where emp_number=? order by 1 ";
    public  String APPRAISAL_STEP3_SELECT_BY_EMP_AND_TRANSACTION = "select id, emp_number, due_date, goal_title, description, goal_result, rate_index, transactionid, istransaction from e_appraisal_step3 where emp_number=? and transactionid=? order by 1 ";
    public  String APPRAISAL_STEP3_SELECT_BY_ID = "select id, emp_number, due_date, goal_title, description, goal_result, rate_index, transactionid, istransaction from e_appraisal_step3 where id=? ";
    public  String APPRAISAL_STEP3_INSERT = "insert into e_appraisal_step3 (emp_number, due_date, goal_title, description, goal_result, rate_index, transactionid, istransaction) values(?,?,?,?,?,?,?,?) ";
    public  String APPRAISAL_STEP3_UPDATE = "update e_appraisal_step3 set emp_number=?, due_date=?, goal_title=?, description=?, goal_result=?, rate_index=?, transactionid=?, istransaction=? where id=? ";
    public  String APPRAISAL_STEP3_DELETE = "delete from e_appraisal_step3 where emp_number=? ";//and transactionid is null ";

    public  String APPRAISAL_STEP4_SELECT = "select emp_number, statement, improvement, transactionid, istransaction from e_appraisal_step4 where emp_number=? ";
    public  String APPRAISAL_STEP4_INSERT = "insert into e_appraisal_step4 (emp_number, statement, improvement, transactionid, istransaction) values(?,?,?,?,?) ";
    public  String APPRAISAL_STEP4_DELETE = "delete from e_appraisal_step4 where emp_number=? ";//and transactionid is null ";

    public  String APPRAISAL_RATING_SUMMARY = "select g.description, (sum(rate_index) + 0.0)/count(group_id) as rate_index  from e_appraisal_step2 a, appraisal_group g " +
            "where a.group_id = g.id and emp_number=? group by g.description union select a.goal_title, a.rate_index from e_appraisal_step3 a where emp_number=? ";

    public  String APPRAISAL_TRANSACTION_RATING_SUMMARY = "select g.description, (sum(rate_index) + 0.0)/count(group_id) as rate_index  from e_appraisal_step2 a, appraisal_group g " +
            "where a.group_id = g.id and emp_number=? and transactionid=? group by g.description union select a.goal_title, a.rate_index from e_appraisal_step3 a where emp_number=? and transactionid=? ";

    
    /* -------------- TRANSACTION OPERATIONS ----------------------------- */
    public  String TRANS_TYPE_SELECT = "select id, description, approval_url, parentid from transactiontype order by id ";
    public  String TRANS_TYPE_SELECT_BY_ID = "select id, description, approval_url, parentid from transactiontype where id=? ";
    public  String TRANS_TYPE_SELECT_BY_PARENT = "select id, description, approval_url, parentid from transactiontype where parentid=? ";

    /* -------------- APPROVAL ROUTE -------------------------- */
    public String APPROVAL_ROUTE_GENERATION_FOR_EMPLOYEE = " stp_generate_workflow_for_employee(?,?) ";

    public  String APPROVAL_ROUTE_GENERATED_SELECT_BY_EMP = "SELECT approval_level, emp_number, approver_firstname, approver_lastname from vw_approvalroute_generated where emp_number=? order by approval_level asc";
    public  String APPROVAL_ROUTE_SELECT_BY_EMP = "select id, approval_level, emp_number, transactiontypeid, authorizer from approvalroute where emp_number=? order by approval_level ";
    public  String APPROVAL_ROUTE_INSERT = " insert into approvalroute (approval_level, emp_number, transactiontypeid, authorizer) values (?,?,?,?) ";
    public  String APPROVAL_ROUTE_DELETE = "delete from approvalroute where emp_number=?";
    public  String APPROVAL_NEXT_LEVEL_FOR_TRANSACTION = "select authorizer from approvalroute where approval_level = (select count(action_by) from transaction_audit where transaction_id=?)" +
            " and transactiontypeid=? and emp_number=?  ";
    public  String APPROVAL_NEXT_LEVEL_BY_DEFAULT = " select authorizer from approvalroute_generated where approval_level = (select count(action_by) from transaction_audit where transaction_id=?) and emp_number=? and isdepartment=0" +
            " union " +
            " select d.managerid from approvalroute_generated a, department d where a.authorizer_group = d.deptid and approval_level = (select count(action_by) from transaction_audit where transaction_id=?) and emp_number=? and isdepartment=1  ";

    public  String APPROVAL_NEXT_PENDING_LEVEL_FOR_TRANSACTION = "select action_by from transaction_audit where transaction_id=? and action_status='P' ";

    public  String APPROVAL_FIRST_LEVEL_FOR_TRANSACTION = "select authorizer from approvalroute where approval_level = 1 " +
            " and transactiontypeid=? and emp_number=?  ";
    public  String APPROVAL_FIRST_LEVEL_BY_DEFAULT = "select authorizer from approvalroute_generated where approval_level = 1 " +
            " and emp_number=? and isdepartment=0 " +
            " union " +
            " select d.managerid From approvalroute_generated a, department d where a.authorizer_group = d.deptid and a.approval_level=1 and a.emp_number=? and a.isdepartment=1 ";
    
    public  String APPROVAL_CONFIRM_NEXT_LEVEL_AVAILABILITY = "select ((select max(approval_level) + 1 from approvalroute where transactiontypeid=? and emp_number=?) - " +
            "(select count(action_by) as counted from transaction_audit where transaction_id=? and status='A'))";

    public  String APPROVAL_CONFIRM_NEXT_LEVEL_AVAILABILITY_BY_DEFAULT = "select ((select max(approval_level) + 1 from approvalroute_generated where emp_number=?) - " +
            "(select count(action_by) as counted from transaction_audit where transaction_id=? and status='A'))";

    public  String APPROVAL_ROUTE_IS_DEFINED_FOR_TRANSACTION = "select max(approval_level) from approvalroute a, transactiontype t where t.id = a.transactiontypeid and t.parentid=? and emp_number=?";
    public  String APPROVAL_ROUTE_IS_DEFINED_FOR_DEFAULT_TRANSACTION = "select max(approval_level) from approvalroute_generated a, transactiontype t where t.id = a.transactiontypeid and t.parentid=? and emp_number=? ";

    /* ------------ TRANSACTION AND TRANS AUDIT --------------------------- */
    public  String TRANSACTION_SELECT_BY_EMPLOYEE = "select id, description, status, batch_number, transaction_ref, transactiontypeid, initiator_id from transaction_table where initiator_id=?";
    public  String TRANSACTION_SELECT_EMPLOYEE_MAX_ID = "select max(id) from transaction_table where initiator_id=?";
    public  String TRANSACTION_SELECT_BY_ID = "select id, description, status, batch_number, transaction_ref, transactiontypeid, initiator_id from transaction_table where id=?";
    public  String TRANSACTION_UPDATE_STATUS = "update transaction_table set status=? where id=?";
    public  String TRANSACTION_UPDATE_FINALIZED = "update transaction_table set status='A' where id=?";
    public  String TRANSACTION_INSERT = "insert into transaction_table (description, status, batch_number, transaction_ref, transactiontypeid, initiator_id) values(?,?,?,?,?,?) ";

    public  String TRANSACTION_STATUS_SELECT = "select id, status, description, module, initiated_date, initiator_id from vw_transaction_status where initiator_id = ? order by initiated_date desc";
    public  String TRANSACTION_STATUS_SELECT_SEARCH_BY_NAME = "select id, status, description, module, initiated_date, initiator_id from vw_transaction_status where description like '%_SEARCH_%' and initiator_id = ? order by initiated_date desc ";
    public  String TRANSACTION_STATUS_SELECT_SEARCH_BY_STATUS = "select id, status, description, module, initiated_date, initiator_id from vw_transaction_status where status='_SEARCH_' and initiator_id = ? order by initiated_date desc ";

    public  String TRANSACTION_INBOX_SELECT = "select t.id, t.description, t.status, t.batch_number, t.transaction_ref, t.transactiontypeid, t.initiator_id, e.firstname, e.lastname, r.description as transtypedesc " +
            " from transaction_table t, employee e, transactiontype r where t.initiator_id = e.emp_number and t.transactiontypeid = r.id" +
            " and t.id in (select transaction_id from transaction_audit where action_status='P' and action_by=?) ";
    public  String TRANSACTION_INBOX_SELECT_SEARCH_INITIATOR = "select t.id, t.description, t.status, t.batch_number, t.transaction_ref, t.transactiontypeid, t.initiator_id, e.firstname, e.lastname, r.description as transtypedesc " +
            " from transaction_table t, employee e, transactiontype r where t.initiator_id = e.emp_number and t.transactiontypeid = r.id" +
            " and t.id in (select transaction_id from transaction_audit where action_status='P' and action_by=?) " +
            " and e.firstname + ' ' + e.lastname like '%_SEARCH_%' ";
    
    public  String TRANSACTION_INBOX_SELECT_SEARCH_TRANSTYPE = "select t.id, t.description, t.status, t.batch_number, t.transaction_ref, t.transactiontypeid, t.initiator_id, e.firstname, e.lastname, r.description as transtypedesc " +
            " from transaction_table t, employee e, transactiontype r where t.initiator_id = e.emp_number and t.transactiontypeid = r.id" +
            " and t.id in (select transaction_id from transaction_audit where action_status='P' and action_by=?) " +
            " and r.description like '%_SEARCH_%' ";

    public  String TRANSACTION_AUDIT_SELECT_BY_TRANSACTION = "select status, action_status, action_date, transaction_id, action_by, e.firstname, e.lastname from transaction_audit a, employee e where e.emp_number=a.action_by and transaction_id=? order by action_date asc ";
    public  String TRANSACTION_AUDIT_SELECT_BY_STATUS_AND_TRANSACTION = "select status, action_status, action_date, transaction_id, action_by from transaction_audit where status=? and transaction_id=? ";
    public  String TRANSACTION_AUDIT_SELECT_PENDING_AUTHORIZER = "select status, action_status, action_date, transaction_id, action_by from transaction_audit where status='P' and transaction_id=?";
    public  String TRANSACTION_AUDIT_INSERT = "insert into transaction_audit (status, action_status, action_date, transaction_id, action_by) values(?,?,?,?,?) ";
    public  String TRANSACTION_AUDIT_UPDATE_BY_TRANSACTION_AND_ACTIONBY = "update transaction_audit set status=?, action_status=?, action_date=? where transaction_id=? and action_by=? ";

    /* --------------- COURSE TRAINING AND FEEDBACK ------------------------- */
    public  String COURSE_SELECT = "select id, course, organizer, course_detail, start_date, end_date, no_of_days, start_time, travel_expenses, allowance, misc_cost, course_venue, course_fee, company_code from course order by start_date desc ";
    public  String COURSE_SELECT_BY_COMPANY = "select id, course, organizer, course_detail, start_date, end_date, no_of_days, start_time, travel_expenses, allowance, misc_cost, course_venue, course_fee, company_code from course where company_code=? order by start_date desc ";
    public  String COURSE_SELECT_BY_ID = "select id, course, organizer, course_detail, start_date, end_date, no_of_days, start_time, travel_expenses, allowance, misc_cost, course_venue, course_fee, company_code from course where id=? ";
    public  String COURSE_INSERT = "insert into course (course, organizer, course_detail, start_date, end_date, no_of_days, start_time, travel_expenses, allowance, misc_cost, course_venue, course_fee, company_code) values (?,?,?,?,?,?,?,?,?,?,?,?,?) ";
    public  String COURSE_UPDATE = "update course set course=?, organizer=?, course_detail=?, start_date=?, end_date=?, no_of_days=?, start_time=?, travel_expenses=?, allowance=?, misc_cost=?, course_venue=?, course_fee=?, company_code=? where id=? ";
    public  String COURSE_DELETE = "delete from course where id=? ";

    public  String COURSE_APPLICATION_SELECT = "select a.id, course_id, a.emp_number, a.date_applied, a.comment, a.transactionid, a.estatus, c.course, e.firstname + ' ' + e.lastname as empname, e.company_code from course_application a, course c, employee e  where a.estatus='A' and a.course_id = c.id and e.emp_number = a.emp_number and c.company_code = e.company_code ";
    public  String COURSE_APPLICATION_SELECT_SEARCH_BY_APPLICANT = "select a.id, course_id, a.emp_number, a.date_applied, a.comment, a.transactionid, a.estatus, c.course, e.firstname + ' ' + e.lastname as empname, e.company_code from course_application a, course c, employee e  where a.estatus='A' and a.course_id = c.id and e.emp_number = a.emp_number  and e.firstname + ' ' + e.lastname like '%_SEARCH_%' and c.company_code = e.company_code ";
    public  String COURSE_APPLICATION_SELECT_SEARCH_BY_COURSE = "select a.id, course_id, a.emp_number, a.date_applied, a.comment, a.transactionid, a.estatus, c.course, e.firstname + ' ' + e.lastname as empname, e.company_code from course_application a, course c, employee e  where a.estatus='A' and a.course_id = c.id and e.emp_number = a.emp_number and c.course like '%_SEARCH_%' and c.company_code = e.company_code ";

    public  String COURSE_APPLICATION_SELECT_BY_COMPANY = "select a.id, course_id, a.emp_number, a.date_applied, a.comment, a.transactionid, a.estatus, c.course, e.firstname + ' ' + e.lastname as empname, e.company_code from course_application a, course c, employee e  where a.estatus='A' and a.course_id = c.id and e.emp_number = a.emp_number and e.company_code=? and c.company_code = e.company_code ";
    public  String COURSE_APPLICATION_SELECT_BY_COMPANY_SEARCH_BY_APPLICANT = "select a.id, course_id, a.emp_number, a.date_applied, a.comment, a.transactionid, a.estatus, c.course, e.firstname + ' ' + e.lastname as empname, e.company_code from course_application a, course c, employee e  where a.estatus='A' and a.course_id = c.id and e.emp_number = a.emp_number  and e.firstname + ' ' + e.lastname like '%_SEARCH_%' and e.company_code=? and c.company_code = e.company_code ";
    public  String COURSE_APPLICATION_SELECT_BY_COMPANY_SEARCH_BY_COURSE = "select a.id, course_id, a.emp_number, a.date_applied, a.comment, a.transactionid, a.estatus, c.course, e.firstname + ' ' + e.lastname as empname, e.company_code from course_application a, course c, employee e  where a.estatus='A' and a.course_id = c.id and e.emp_number = a.emp_number and c.course like '%_SEARCH_%' and e.company_code=? and c.company_code = e.company_code ";

    public  String COURSE_APPLICATION_SELECT_BY_ID = "select a.id, course_id, a.emp_number, a.date_applied, a.comment, a.transactionid, a.estatus, c.course, e.firstname + ' ' + e.lastname as empname from course_application a, course c, employee e  where a.estatus='A' and a.course_id = c.id and e.emp_number = a.emp_number and a.id=? ";
    public  String COURSE_APPLICATION_SELECT_BY_EMPLOYEE = "select a.id, course_id, a.emp_number, a.date_applied, a.comment, a.transactionid, a.estatus, c.course, e.firstname + ' ' + e.lastname as empname from course_application a, course c, employee e  where a.estatus='A' and a.course_id = c.id and e.emp_number = a.emp_number and a.emp_number=? and a.id not in (select course_application_id from course_application_feedback) ";
    public  String COURSE_APPLICATION_SELECT_BY_TRANSACTION = "select a.id, course_id, a.emp_number, a.date_applied, a.comment, a.transactionid, a.estatus, c.course, e.firstname + ' ' + e.lastname as empname from course_application a, course c, employee e  where a.course_id = c.id and e.emp_number = a.emp_number and a.transactionid=? ";
    public  String COURSE_APPLICATION_INSERT = "insert into course_application (course_id, emp_number, date_applied, comment, transactionid, estatus) values (?,?,?,?,?,?) ";
    public  String COURSE_APPLICATION_UPDATE = "update course_application set course_id=?, emp_number=?, date_applied=?, comment=?, transactionid=? where id=? ";
    public  String COURSE_APPLICATION_UPDATE_STATUS_BY_TRANSACTION = "update course_application set estatus=? where transactionid=? ";
    public  String COURSE_APPLICATION_DELETE = "delete from course_application where id=? ";

    public   String COURSE_FEEDBACK_INSERT = "insert into course_application_feedback (course_application_id, emp_number, date_applied, comment) values(?,?,?,?) ";
    public   String COURSE_FEEDBACK_SELECT_BY_COURSE_APPLICATION = "select course_application_id, emp_number, date_applied, comment from course_application_feedback where course_application_id=? ";
    public   String COURSE_FEEDBACK_UPDATE = "update course_application_feedback set emp_number=?, date_applied=?, comment=? where course_application_id=? ";


    /* --------------- LEAVE SETUP AND APPLICATION ----------------------------- */
    public  String LEAVE_TYPE_SELECT = "select id, description, guide, paid_leave, company_code from leave_type ";
    public  String LEAVE_TYPE_SELECT_BY_COMPANY = "select id, description, guide, paid_leave, company_code from leave_type where company_code=? ";
    public  String LEAVE_TYPE_SELECT_BY_ID = "select id, description, guide, paid_leave, company_code from leave_type where id=?";
    public  String LEAVE_TYPE_UPDATE = "update leave_type set description=?, guide=?, paid_leave=?, company_code=? where id=? ";
    public  String LEAVE_TYPE_INSERT = "insert into leave_type(description, guide, paid_leave, company_code) values(?,?,?,?) ";
    public  String LEAVE_TYPE_DELETE = "delete from leave_type where id = ? ";

    public  String LEAVE_SELECT = "select l.id, l.emp_number, l.leave_year, l.leave_type_id, l.start_date, l.end_date, l.relief_officer, l.contact_address, l.contact_number, l.reason, l.transactionid, l.estatus, t.description as leavetypedesc, e.firstname + ' ' + e.lastname as emp_name, r.firstname + ' ' + r.lastname as reliefofficer_name, e.company_code from leave l, employee e, leave_type t, employee r where t.id = l.leave_type_id and e.emp_number = l.emp_number and r.emp_number = l.relief_officer and annual_id in (select annual_id from annual_calendar where status='P') and t.company_code = e.company_code ";
    public  String LEAVE_SELECT_SEARCH_BY_APPLICANT = "select l.id, l.emp_number, l.leave_year, l.leave_type_id, l.start_date, l.end_date, l.relief_officer, l.contact_address, l.contact_number, l.reason, l.transactionid, l.estatus, t.description as leavetypedesc, e.firstname + ' ' + e.lastname as emp_name, r.firstname + ' ' + r.lastname as reliefofficer_name, e.company_code from leave l, employee e, leave_type t, employee r where t.id = l.leave_type_id and e.emp_number = l.emp_number and r.emp_number = l.relief_officer and e.firstname + ' ' + e.lastname like '%_SEARCH_%'  and annual_id in (select annual_id from annual_calendar where status='P')  and t.company_code = e.company_code ";
    public  String LEAVE_SELECT_SEARCH_BY_LEAVETYPE = "select l.id, l.emp_number, l.leave_year, l.leave_type_id, l.start_date, l.end_date, l.relief_officer, l.contact_address, l.contact_number, l.reason, l.transactionid, l.estatus, t.description as leavetypedesc, e.firstname + ' ' + e.lastname as emp_name, r.firstname + ' ' + r.lastname as reliefofficer_name, e.company_code from leave l, employee e, leave_type t, employee r where t.id = l.leave_type_id and e.emp_number = l.emp_number and r.emp_number = l.relief_officer and t.description like '%_SEARCH_%'  and annual_id in (select annual_id from annual_calendar where status='P') and t.company_code = e.company_code ";

    public  String LEAVE_SELECT_BY_COMPANY = "select l.id, l.emp_number, l.leave_year, l.leave_type_id, l.start_date, l.end_date, l.relief_officer, l.contact_address, l.contact_number, l.reason, l.transactionid, l.estatus, t.description as leavetypedesc, e.firstname + ' ' + e.lastname as emp_name, r.firstname + ' ' + r.lastname as reliefofficer_name, e.company_code from leave l, employee e, leave_type t, employee r where t.id = l.leave_type_id and e.emp_number = l.emp_number and r.emp_number = l.relief_officer and annual_id in (select annual_id from annual_calendar where status='P') and t.company_code = e.company_code and e.company_code=? ";
    public  String LEAVE_SELECT_BY_COMPANY_SEARCH_BY_APPLICANT = "select l.id, l.emp_number, l.leave_year, l.leave_type_id, l.start_date, l.end_date, l.relief_officer, l.contact_address, l.contact_number, l.reason, l.transactionid, l.estatus, t.description as leavetypedesc, e.firstname + ' ' + e.lastname as emp_name, r.firstname + ' ' + r.lastname as reliefofficer_name, e.company_code from leave l, employee e, leave_type t, employee r where t.id = l.leave_type_id and e.emp_number = l.emp_number and r.emp_number = l.relief_officer and e.firstname + ' ' + e.lastname like '%_SEARCH_%'  and annual_id in (select annual_id from annual_calendar where status='P') and t.company_code = e.company_code and e.company_code=? ";
    public  String LEAVE_SELECT_BY_COMPANY_SEARCH_BY_LEAVETYPE = "select l.id, l.emp_number, l.leave_year, l.leave_type_id, l.start_date, l.end_date, l.relief_officer, l.contact_address, l.contact_number, l.reason, l.transactionid, l.estatus, t.description as leavetypedesc, e.firstname + ' ' + e.lastname as emp_name, r.firstname + ' ' + r.lastname as reliefofficer_name, e.company_code from leave l, employee e, leave_type t, employee r where t.id = l.leave_type_id and e.emp_number = l.emp_number and r.emp_number = l.relief_officer and t.description like '%_SEARCH_%'  and annual_id in (select annual_id from annual_calendar where status='P') and t.company_code = e.company_code and e.company_code=? ";

    public  String LEAVE_SELECT_BY_ID = "select l.id, l.emp_number, l.leave_year, l.leave_type_id, l.start_date, l.end_date, l.relief_officer, l.contact_address, l.contact_number, l.reason, l.transactionid, l.estatus, t.description as leavetypedesc, e.firstname + ' ' + e.lastname as emp_name, r.firstname + ' ' + r.lastname as reliefofficer_name from leave l, employee e, leave_type t, employee r where t.id = l.leave_type_id and e.emp_number = l.emp_number and r.emp_number = l.relief_officer and l.id=?  and annual_id in (select annual_id from annual_calendar where status='P')";
    public  String LEAVE_SELECT_BY_EMPLOYEE = "select l.id, l.emp_number, l.leave_year, l.leave_type_id, l.start_date, l.end_date, l.relief_officer, l.contact_address, l.contact_number, l.reason, l.transactionid, l.estatus, t.description as leavetypedesc, e.firstname + ' ' + e.lastname as emp_name, r.firstname + ' ' + r.lastname as reliefofficer_name from leave l, employee e, leave_type t, employee r where t.id = l.leave_type_id and e.emp_number = l.emp_number and r.emp_number = l.relief_officer and l.id not in (select leave_id from leave_resumption) and l.emp_number=?  and annual_id in (select annual_id from annual_calendar where status='P') ";
    public  String LEAVE_SELECT_BY_TRANSACTION = "select l.id, l.emp_number, l.leave_year, l.leave_type_id, l.start_date, l.end_date, l.relief_officer, l.contact_address, l.contact_number, l.reason, l.transactionid, l.estatus, t.description as leavetypedesc, e.firstname + ' ' + e.lastname as emp_name, r.firstname + ' ' + r.lastname as reliefofficer_name from leave l, employee e, leave_type t, employee r where t.id = l.leave_type_id and e.emp_number = l.emp_number and r.emp_number = l.relief_officer and l.transactionid=?  and annual_id in (select annual_id from annual_calendar where status='P')";
    public  String LEAVE_UPDATE = "update leave set emp_number=?, leave_year=?, leave_type_id=?, start_date=?, end_date=?, relief_officer=?, contact_address=?, contact_number=?, reason=?, transactionid=?, estatus=? where id=? ";
    public  String LEAVE_UPDATE_STATUS_BY_TRANSACTION = "update leave set estatus=? where transactionid=? ";
    public  String LEAVE_INSERT = "insert into leave(emp_number, leave_year, leave_type_id, start_date, end_date, relief_officer, contact_address, contact_number, reason, transactionid, estatus, annual_id) select ?,?,?,?,?,?,?,?,?,?,?, (select annual_id from annual_calendar where status='P') ";
    public  String LEAVE_DELETE = "delete from leave where id = ? ";
    public  String LEAVE_COUNT_FOR_EMPLOYEE = "select isNull(sum(datediff(dd, start_date, end_date)),0) from leave where estatus='A' and emp_number=?  and annual_id in (select annual_id from annual_calendar where status='P') ";
    public  String LEAVE_COUNT_PENDING_FOR_EMPLOYEE = "select isNull(count(emp_number),0) from leave where estatus not in ('A') and emp_number=?  and annual_id in (select annual_id from annual_calendar where status='P') ";

    public  String LEAVE_RESUMPTION_SELECT = "select r.id, r.leave_id, r.start_date, r.end_date, r.reason, r.transactionid, r.estatus from leave_resumption r, leave l where r.leave_id = l.id ";
    public  String LEAVE_RESUMPTION_SELECT_BY_LEAVE_ID = "select r.id, r.leave_id, r.start_date, r.end_date, r.reason, r.transactionid, r.estatus from leave_resumption r, leave l where r.leave_id = l.id and r.leave_id=? and r.estatus='A' ";
    public  String LEAVE_RESUMPTION_SELECT_BY_TRANSACTION = "select r.id, r.leave_id, r.start_date, r.end_date, r.reason, r.transactionid, r.estatus from leave_resumption r, leave l where r.leave_id = l.id and r.transactionid=?";
    public  String LEAVE_RESUMPTION_UPDATE = "update leave_resumption set leave_id=?, start_date=?, end_date=?, reason=? where id=? ";
    public  String LEAVE__RESUMPTION_UPDATE_STATUS_BY_TRANSACTION = "update leave_resumption set estatus=? where transactionid=? ";
    public  String LEAVE_RESUMPTION_INSERT = "insert into leave_resumption(leave_id, start_date, end_date, reason, transactionid, estatus) values(?,?,?,?,?,?) ";
    public  String LEAVE_RESUMPTION_DELETE = "delete from leave_resumption where id = ? ";

    /* ----------------- EQUIPMENTS ----------------------------- */
    public  String EQUIPMENT_BRAND_SELECT = "select id, code, description, active, company_code from equipment_brand ";
    public  String EQUIPMENT_BRAND_SELECT_SEARCH_BY_DESC = "select id, code, description, active, company_code from equipment_brand where description like '%_SEARCH_%' ";
    public  String EQUIPMENT_BRAND_SELECT_SEARCH_BY_CODE = "select id, code, description, active, company_code from equipment_brand where code like '%_SEARCH_%' ";
    
    public  String EQUIPMENT_BRAND_SELECT_BY_COMPANY = "select id, code, description, active, company_code from equipment_brand where company_code=? ";
    public  String EQUIPMENT_BRAND_SELECT_BY_COMPANY_SEARCH_BY_DESC = "select id, code, description, active, company_code from equipment_brand where description like '%_SEARCH_%' and company_code=? ";
    public  String EQUIPMENT_BRAND_SELECT_BY_COMPANY_SEARCH_BY_CODE = "select id, code, description, active, company_code from equipment_brand where code like '%_SEARCH_%' and company_code=? ";

    public  String EQUIPMENT_BRAND_SELECT_BY_ID = "select id, code, description, active, company_code from equipment_brand where id=? ";
    public  String EQUIPMENT_BRAND_INSERT = "insert into equipment_brand (code, description, active, company_code) values(?,?,?,?)  ";
    public  String EQUIPMENT_BRAND_UPDATE = "update equipment_brand set code=?, description=?, active=?, company_code=? where id=? ";
    public  String EQUIPMENT_BRAND_DELETE = "delete from equipment_brand where id=? ";

    public  String EQUIPMENT_TYPE_SELECT = "select id, code, description, company_code from equipment_type ";
    public  String EQUIPMENT_TYPE_SELECT_SEARCH_BY_DESC = "select id, code, description, company_code from equipment_type where description like '%_SEARCH_%' ";
    public  String EQUIPMENT_TYPE_SELECT_SEARCH_BY_CODE = "select id, code, description, company_code from equipment_type where code like '%_SEARCH_%' ";

    public  String EQUIPMENT_TYPE_SELECT_BY_COMPANY = "select id, code, description, company_code from equipment_type where company_code=? ";
    public  String EQUIPMENT_TYPE_SELECT_BY_COMPANY_SEARCH_BY_DESC = "select id, code, description, company_code from equipment_type where description like '%_SEARCH_%' and company_code=? ";
    public  String EQUIPMENT_TYPE_SELECT_BY_COMPANY_SEARCH_BY_CODE = "select id, code, description, company_code from equipment_type where code like '%_SEARCH_%' and company_code=? ";

    public  String EQUIPMENT_TYPE_SELECT_BY_ID = "select id, code, description, company_code from equipment_type where id=? ";
    public  String EQUIPMENT_TYPE_INSERT = "insert into equipment_type (code, description, company_code) values(?,?,?)  ";
    public  String EQUIPMENT_TYPE_UPDATE = "update equipment_type set code=?, description=?, company_code=? where id=? ";
    public  String EQUIPMENT_TYPE_DELETE = "delete from equipment_type where id=? ";

    public  String EQUIPMENT_SELECT = "select e.id, e.brand_id, e.type_id, e.product_name, e.serial_number, b.code as brand_code, t.code as type_code, e.company_code from equipment e, equipment_brand b, equipment_type t where e.brand_id = b.id and e.type_id = t.id  ";
    public  String EQUIPMENT_SELECT_SEARCH_BY_BRAND = "select e.id, e.brand_id, e.type_id, e.product_name, e.serial_number, b.code as brand_code, t.code as type_code, e.company_code from equipment e, equipment_brand b, equipment_type t where e.brand_id = b.id and e.type_id = t.id and b.code like '%_SEARCH_%' ";
    public  String EQUIPMENT_SELECT_SEARCH_BY_TYPE = "select e.id, e.brand_id, e.type_id, e.product_name, e.serial_number, b.code as brand_code, t.code as type_code, e.company_code from equipment e, equipment_brand b, equipment_type t where e.brand_id = b.id and e.type_id = t.id and t.code like '%_SEARCH_%' ";
    public  String EQUIPMENT_SELECT_SEARCH_BY_NAME = "select e.id, e.brand_id, e.type_id, e.product_name, e.serial_number, b.code as brand_code, t.code as type_code, e.company_code from equipment e, equipment_brand b, equipment_type t where e.brand_id = b.id and e.type_id = t.id and e.product_name like '%_SEARCH_%' ";
    public  String EQUIPMENT_SELECT_SEARCH_BY_SERIAL = "select e.id, e.brand_id, e.type_id, e.product_name, e.serial_number, b.code as brand_code, t.code as type_code, e.company_code from equipment e, equipment_brand b, equipment_type t where e.brand_id = b.id and e.type_id = t.id and e.serial_number like '%_SEARCH_%' ";

    public  String EQUIPMENT_SELECT_BY_COMPANY = "select e.id, e.brand_id, e.type_id, e.product_name, e.serial_number, b.code as brand_code, t.code as type_code, company_code from equipment e, equipment_brand b, equipment_type t where e.brand_id = b.id and e.type_id = t.id and  company_code=? ";
    public  String EQUIPMENT_SELECT_BY_COMPANY_SEARCH_BY_BRAND = "select e.id, e.brand_id, e.type_id, e.product_name, e.serial_number, b.code as brand_code, t.code as type_code, company_code from equipment e, equipment_brand b, equipment_type t where e.brand_id = b.id and e.type_id = t.id and b.code like '%_SEARCH_%'  and  company_code=? ";
    public  String EQUIPMENT_SELECT_BY_COMPANY_SEARCH_BY_TYPE = "select e.id, e.brand_id, e.type_id, e.product_name, e.serial_number, b.code as brand_code, t.code as type_code, company_code from equipment e, equipment_brand b, equipment_type t where e.brand_id = b.id and e.type_id = t.id and t.code like '%_SEARCH_%' and  company_code=? ";
    public  String EQUIPMENT_SELECT_BY_COMPANY_SEARCH_BY_NAME = "select e.id, e.brand_id, e.type_id, e.product_name, e.serial_number, b.code as brand_code, t.code as type_code, company_code from equipment e, equipment_brand b, equipment_type t where e.brand_id = b.id and e.type_id = t.id and e.product_name like '%_SEARCH_%' and  company_code=? ";
    public  String EQUIPMENT_SELECT_BY_COMPANY_SEARCH_BY_SERIAL = "select e.id, e.brand_id, e.type_id, e.product_name, e.serial_number, b.code as brand_code, t.code as type_code, company_code from equipment e, equipment_brand b, equipment_type t where e.brand_id = b.id and e.type_id = t.id and e.serial_number like '%_SEARCH_%' and  company_code=? ";

    public  String EQUIPMENT_SELECT_BY_ID = "select e.id, e.brand_id, e.type_id, e.product_name, e.serial_number, b.code as brand_code, t.code as type_code, e.company_code from equipment e, equipment_brand b, equipment_type t where e.brand_id = b.id and e.type_id = t.id  and e.id=? ";
    public  String EQUIPMENT_INSERT = "insert into equipment (brand_id, type_id, product_name, serial_number,company_code) values(?,?,?,?,?)  ";
    public  String EQUIPMENT_UPDATE = "update equipment set brand_id=?, type_id=?, product_name=?, serial_number=?, company_code=? where id=? ";
    public  String EQUIPMENT_DELETE = "delete from equipment where id=? ";

    public  String EQUIPMENT_ASSIGNED_SELECT_BY_EMPLOYEE = "select emp_number, equipment_id from equipment_assigned where emp_number=? ";
    public  String EQUIPMENT_ASSIGNED_INSERT = "insert into equipment_assigned (emp_number, equipment_id) values(?,?)  ";
    public  String EQUIPMENT_ASSIGNED_DELETE = "delete from equipment_assigned where emp_number=? ";

    public  String EQUIPMENT_ASSIGN_CHECK_USED = "select count(emp_number) from equipment_assigned where emp_number not in (?) and equipment_id=?";
    /* --------------- AUDIT MTRAIL ---------------------- */
    public  String AUDIT_TRAIL_SELECT = "select emp_number, access_date, operation, machine_identity from audit_trail ";
    public  String AUDIT_TRAIL_SELECT_BY_EMP = "select emp_number, access_date, operation, machine_identity from audit_trail where emp_number=? ";
    public  String AUDIT_TRAIL_SELECT_BY_DATE = "select emp_number, access_date, operation, machine_identity from audit_trail where access_date between ? and ? ";
    public  String AUDIT_TRAIL_INSERT = "insert into audit_trail (emp_number, access_date, operation, machine_identity) values(?,?,?,?) ";
    

    /* --------------- REPORT TEMPLATE (report_template) ---------------------- */
    public  String REPORT_TEMPLATE_SELECT_BY_CODE = "select code, control, control_caption, control_value, init_procedures, custom1 from report_template where code=? ";
    public  String REPORT_SELECT_BY_ID = "select id, title, report_template, report_query, report_procedure, report_file, subreports_query from report where id=? ";

    /* --------------- PROJECT UTILIZATION ---------------------- */
    public  String PROJECT_GROUP_SELECT = "select id, code, description, company_code from projectgroup";
    public  String PROJECT_GROUP_SELECT_BY_COMPANY = "select id, code, description, company_code from projectgroup where company_code=?";
    public  String PROJECT_GROUP_SELECT_BY_ID = "select id, code, description, company_code from projectgroup where id=?";
    public  String PROJECT_GROUP_INSERT = "insert into projectgroup (code, description, company_code) values(?,?,?) ";
    public  String PROJECT_GROUP_UPDATE = "update projectgroup set code=?, description=?, company_code=? where id=?";
    public  String PROJECT_GROUP_DELETE = "delete from projectgroup  where id=?";

    public  String PROJECT_SELECT = "select p.id, project_group_id, billable, p.code, p.description, g.description as project_group, p.company_code  from project p, projectgroup g where p.project_group_id=g.id ";
    public  String PROJECT_SELECT_BY_COMPANY = "select p.id, project_group_id, billable, p.code, p.description, g.description as project_group, p.company_code from project p, projectgroup g where p.project_group_id=g.id and p.company_code=? ";
    public  String PROJECT_SELECT_BY_ID = "select p.id, project_group_id, billable, p.code, p.description, g.description as project_group, p.company_code from project p, projectgroup g where p.project_group_id=g.id and p.id=?";
    public  String PROJECT_INSERT = "insert into project (project_group_id, billable, code, description, company_code) values(?,?,?,?,?) ";
    public  String PROJECT_UPDATE = "update project set project_group_id=?, billable=?, code=?, description=?, company_code=? where id=?";
    public  String PROJECT_DELETE = "delete from project  where id=? ";

    public  String PROJECT_UTILIZATION_SELECT = "select u.id, u.project_id, u.active_date, u.utilized_hour, p.description as project_description, u.emp_number, estatus, e.firstname, e.lastname from project_utilization u, project p, employee e where u.project_id=p.id and e.emp_number=u.emp_number and u.emp_number=? and estatus='P' ";
    public  String PROJECT_UTILIZATION_SELECT_BY_ID = "select u.id, u.project_id, u.active_date, u.utilized_hour, p.description as project_description, u.emp_number, estatus, e.firstname, e.lastname from project_utilization u, project p, employee e where u.project_id=p.id and e.emp_number=u.emp_number and u.id=? and u.emp_number=? and estatus='P' ";
    public  String PROJECT_UTILIZATION_INSERT = "insert into project_utilization (project_id, active_date, utilized_hour, emp_number, estatus) values(?,?,?,?,?) ";
    public  String PROJECT_UTILIZATION_UPDATE = "update project_utilization set project_id=?, active_date=?, utilized_hour=?, emp_number=?, estatus=? where id=? and estatus='P' ";
    public  String PROJECT_UTILIZATION_DELETE = "delete from project_utilization  where id=? and estatus='P' ";

    public  String PROJECT_UTILIZATION_CHECKED_DATE_SELECT = "select count(id) from project_utilization u where estatus='P' and active_date=? and project_id=? and emp_number=? ";
    public  String PROJECT_UTILIZATION_CHECKED_HOURS_SELECT = "select sum(utilized_hour) from project_utilization u where estatus='P' and active_date=?  and emp_number=? ";

    public  String PROJECT_UTILIZATION_PENDING_SELECT = "select u.project_id, p.description, sum(utilized_hour) as total_hours, (select min(z.active_date) from project_utilization z where z.project_id = u.project_id and z.estatus=u.estatus) as startDate, (select max(z.active_date) from project_utilization z where z.project_id = u.project_id and z.estatus=u.estatus) as endDate from project_utilization u, project p where u.project_id = p.id and estatus=? group by u.project_id, p.description, u.estatus";
    public  String PROJECT_UTILIZATION_PENDING_SELECT_BY_COMPANY = "select u.project_id, p.description, sum(utilized_hour) as total_hours, (select min(z.active_date) from project_utilization z where z.project_id = u.project_id and z.estatus=u.estatus) as startDate, (select max(z.active_date) from project_utilization z where z.project_id = u.project_id and z.estatus=u.estatus) as endDate from project_utilization u, project p where u.project_id = p.id and estatus=? and p.company_code=? group by u.project_id, p.description, u.estatus";
    public  String PROJECT_UTILIZATION_CLOSED_UPDATE = "update project_utilization set estatus='A' where estatus='P' and project_id=? ";


    /* --------------- PAYROLL SPECIFICS TABLE QUERIES ------------------------ */

    /* ****** PAYROLL EMPLOYEE BANK DETAIL ****** */
    public  String EMPLOYEE_BANK_DETAIL_ALL_ACTIVE_SELECT = "select b.id, b.emp_number, bank_name, bank_branch, account_name, account_number, account_type, policyid from payroll_employee_bankdetail b, employee e where e.emp_number=b.emp_number and employment_status='A' ";
    public  String EMPLOYEE_BANK_DETAIL_ALL_ACTIVE_SELECT_BY_COMPANY = "select b.id, b.emp_number, bank_name, bank_branch, account_name, account_number, account_type, policyid from payroll_employee_bankdetail b, employee e where e.emp_number=b.emp_number and employment_status='A' and e.company_code=? ";
    public  String EMPLOYEE_BANK_DETAIL_SELECT = "select id, emp_number, bank_name, bank_branch, account_name, account_number, account_type, policyid from payroll_employee_bankdetail where emp_number=? ";
    public  String EMPLOYEE_BANK_DETAIL_INSERT = "insert into payroll_employee_bankdetail (emp_number, bank_name, bank_branch, account_name, account_number, account_type, policyid) values(?,?,?,?,?,?,?) ";
    public  String EMPLOYEE_BANK_DETAIL_UPDATE = "update payroll_employee_bankdetail set bank_name=?, bank_branch=?, account_name=?, account_number=?, account_type=?, policyid where emp_number=? ";
    
    /* Input type for accounts */
    public String PAYROLL_INPUT_TYPE_SELECT = "select idx, description, formula from payroll_input_type order by idx";
    public String PAYROLL_INPUT_TYPE_SELECT_BY_ID = "select idx, description, formula from payroll_input_type where idx=?";

    /* *** payroll Pay Account *** */
    public String PAYROLL_PAY_ACCOUNT_SELECT = "select accountid, description, formula, calcseq, inputtype, groupid, company_code from payroll_payaccount where estatus='A' order by accountid ";
    public String PAYROLL_PAY_ACCOUNT_SELECT_SEARCH_BY_DESC = "select accountid, description, formula, calcseq, inputtype, groupid, company_code from payroll_payaccount where estatus='A' and description like '%_SEARCH_%' order by accountid ";

    public String PAYROLL_PAY_ACCOUNT_SELECT_BY_COMPANY = "select accountid, description, formula, calcseq, inputtype, groupid, company_code from payroll_payaccount where estatus='A' and company_code=? order by accountid ";
    public String PAYROLL_PAY_ACCOUNT_SELECT_BY_COMPANY_SEARCH_BY_DESC = "select accountid, description, formula, calcseq, inputtype, groupid, company_code from payroll_payaccount where estatus='A' and company_code=? and description like '%_SEARCH_%' order by accountid ";

    public String PAYROLL_PAY_ACCOUNT_SELECT_BY_ID = "select accountid, description, formula, calcseq, inputtype, groupid, company_code from payroll_payaccount where accountid=? and estatus='A' ";
    public String PAYROLL_PAY_ACCOUNT_INSERT = "insert into payroll_payaccount (description, formula, calcseq, inputtype, estatus, groupid, company_code) values(?,?,?,?,'A',?,?) ";
    public String PAYROLL_PAY_ACCOUNT_UPDATE = "update payroll_payaccount set description=?, formula=?, calcseq=?, inputtype=?, groupid=?, company_code=? where accountid=? and estatus='A' ";
    public String PAYROLL_PAY_ACCOUNT_DELETE = "update payroll_payaccount set estatus='D' where accountid=? and estatus='A' ";

    /* ***** Payroll Attributes ****** */
    public String PAYROLL_ATTRIBUTE_SELECT = "select attributeid, code, description, company_code from payroll_attribute where estatus='A' order by attributeid ";
    public String PAYROLL_ATTRIBUTE_SELECT_SEARCH_BY_CODE = "select attributeid, code, description, company_code from payroll_attribute where estatus='A' and code like '%_SEARCH_%' order by attributeid ";
    public String PAYROLL_ATTRIBUTE_SELECT_SEARCH_BY_DESC = "select attributeid, code, description, company_code from payroll_attribute where estatus='A' and description like '%_SEARCH_%' order by attributeid ";

    public String PAYROLL_ATTRIBUTE_SELECT_BY_COMPANY = "select attributeid, code, description, company_code from payroll_attribute where estatus='A' and company_code=? order by attributeid ";
    public String PAYROLL_ATTRIBUTE_SELECT_BY_COMPANY_SEARCH_BY_CODE = "select attributeid, code, description, company_code from payroll_attribute where estatus='A' and company_code=? and code like '%_SEARCH_%' order by attributeid ";
    public String PAYROLL_ATTRIBUTE_SELECT_BY_COMPANY_SEARCH_BY_DESC = "select attributeid, code, description, company_code from payroll_attribute where estatus='A' and company_code=? and description like '%_SEARCH_%' order by attributeid ";

    public String PAYROLL_ATTRIBUTE_SELECT_BY_ID = "select attributeid, code, description, company_code from payroll_attribute where attributeid=? and estatus='A' ";
    public String PAYROLL_ATTRIBUTE_INSERT = "insert into payroll_attribute (code, description, estatus, company_code) values(?, ?,'A',?) ";
    public String PAYROLL_ATTRIBUTE_UPDATE = "update payroll_attribute set code=?, description=?, company_code=? where attributeid=? and estatus='A' ";
    public String PAYROLL_ATTRIBUTE_DELETE = "update payroll_attribute set estatus='D' where attributeid=? and estatus='A' ";

    public String PAYROLL_POLICY_SELECT = "select policyid, description, company_code from payroll_policy where estatus='A' order by policyid ";
    public String PAYROLL_POLICY_SELECT_BY_COMPANY = "select policyid, description, company_code from payroll_policy where estatus='A' and company_code=? order by policyid ";
    public String PAYROLL_POLICY_SELECT_BY_ID = "select policyid, description, company_code from payroll_policy where policyid=? and estatus='A' ";
    public String PAYROLL_POLICY_INSERT = "insert into payroll_policy (description, estatus, company_code) values(?,'A',?) ";
    public String PAYROLL_POLICY_UPDATE = "update payroll_policy set description=?, company_code=? where policyid=? and estatus='A' ";
    public String PAYROLL_POLICY_DELETE = "update payroll_policy set estatus='D' where policyid=? and estatus='A' ";

    public String PAYROLL_POLICY_ATTRIBUTE_SELECT = "SELECT policyid, attributeid from payroll_policy_attribute ";
    public String PAYROLL_POLICY_ATTRIBUTE_SELECT_BY_POLICY = "SELECT p.policyid, p.attributeid, a.code, a.description, z.company_code from payroll_policy_attribute p, payroll_attribute a, payroll_policy z where z.policyid=p.policyid and p.attributeid=a.attributeid and policyid=?";
    public String PAYROLL_POLICY_ATTRIBUTE_SELECT_BY_POLICY_AND_COMPANY = "SELECT p.policyid, p.attributeid, a.code, a.description, z.company_code from payroll_policy_attribute p, payroll_attribute a, payroll_policy z where z.policyid=p.policyid and p.attributeid=a.attributeid and p.policyid=? and z.company_code=?";
    public String PAYROLL_POLICY_ATTRIBUTE_INSERT = "insert into payroll_policy_attribute (policyid, attributeid) values(?,?)";
    public String PAYROLL_POLICY_ATTRIBUTE_DELETE_BY_POLICY_AND_ATTRIBUTE = "delete from payroll_policy_attribute where policyid=? and attributeid=?";

    public String PAYROLL_POLICY_PAYITEM_SELECT = "select policyid, i.accountid, amount, fromperiodid, toperiodid from payroll_policy_payitem i, payroll_payaccount a where a.accountid=i.accountid and policyid=? order by calcseq";
    public String PAYROLL_POLICY_PAYITEM_INSERT = "insert into payroll_policy_payitem (policyid, accountid, amount, fromperiodid, toperiodid) values(?,?,?,?,?) ";
    public String PAYROLL_POLICY_PAYITEM_DELETE = "delete from payroll_policy_payitem where policyid=? and accountid=? ";
    public String PAYROLL_POLICY_PAYITEM_DELETE_ALL = "delete from payroll_policy_payitem where policyid=? ";

    /* ******* Payroll AccountGroup ******* */
    public String PAYROLL_ACCOUNT_GROUP_SELECT = "select groupid, name, description, report, selector, company_code from payroll_accountgroup where estatus='A' ";
    public String PAYROLL_ACCOUNT_GROUP_SELECT_BY_COMPANY = "select groupid, name, description, report, selector, company_code from payroll_accountgroup where estatus='A' and company_code=? ";
    public String PAYROLL_ACCOUNT_GROUP_SELECT_BY_ID = "select groupid, name, description, report, selector, company_code from payroll_accountgroup where groupid=? and estatus='A' ";
    public String PAYROLL_ACCOUNT_GROUP_INSERT = "insert into payroll_accountgroup (name, description, report, selector, estatus, company_code) values(?,?,?,?,'A',?) ";
    public String PAYROLL_ACCOUNT_GROUP_UPDATE = "update payroll_accountgroup set name=?, description=?, report=?, selector=?, company_code=? where groupid=? and estatus='A' ";
    public String PAYROLL_ACCOUNT_GROUP_DELETE = "update payroll_accountgroup set estatus='D' where groupid=? and estatus='A' and name not in ('earning', 'deduction') ";

    /* ******** Payroll Pay Account Group ****** */
    /*
    public String PAYROLL_PAY_ACCOUNT_GROUP_SELECT = "SELECT groupid, accountid from payroll_payaccount_group ";
    public String PAYROLL_PAY_ACCOUNT_GROUP_SELECT_BY_ACCOUNT = "SELECT groupid, accountid from payroll_payaccount_group where accountid=?";
    public String PAYROLL_PAY_ACCOUNT_GROUP_INSERT = "insert into payroll_payaccount_group (groupid, accountid) values(?,?)";
    public String PAYROLL_PAY_ACCOUNT_GROUP_DELETE_BY_ACCOUNT_AND_GROUP = "delete from payroll_payaccount_group where groupid=? and accountid=?";
    */
    /* ******** Payroll Pay Period *********** */
    public String PAYROLL_PAY_PERIOD_SELECT = "select periodid, starttime, endtime, locked, company_code from payroll_payperiod ";
    public String PAYROLL_PAY_PERIOD_SELECT_ACTIVE = "select periodid, starttime, endtime, locked, company_code from payroll_payperiod where locked=0 ";

    public String PAYROLL_PAY_PERIOD_SELECT_BY_COMPANY = "select periodid, starttime, endtime, locked, company_code from payroll_payperiod where company_code=? ";
    public String PAYROLL_PAY_PERIOD_SELECT_BY_COMPANY_ACTIVE = "select periodid, starttime, endtime, locked, company_code from payroll_payperiod where locked=0 and company_code=? ";
    
    public String PAYROLL_PAY_PERIOD_COUNT_BY_COMPANY = "select count(periodid) from payroll_payperiod where company_code=? ";

    public String PAYROLL_PAY_PERIOD_SELECT_BY_ID = "select periodid, starttime, endtime, locked, company_code from payroll_payperiod where periodid=?  ";
    public String PAYROLL_PAY_PERIOD_INSERT = "insert into payroll_payperiod (starttime, endtime, locked, date_created, company_code) values(?,?,?,?,?) ";
    public String PAYROLL_PAY_PERIOD_UPDATE = "update payroll_payperiod set starttime=?, endtime=?, locked=?, company_code=? where periodid=? and locked=0 ";
    public String PAYROLL_PAY_PERIOD_LOCKED = "update payroll_payperiod set locked=1 where locked=0 ";

    /* ************** Payroll (Employee Payitem and Attribute) ******************** */
    public String PAYROLL_EMPLOYEE_PAYITEM_SELECT = "select emp_number, i.accountid, item_value, fromperiodid, toperiodid from payroll_employee_payitem i, payroll_payaccount a where a.accountid=i.accountid and emp_number=? order by a.calcseq ";
    public String PAYROLL_EMPLOYEE_PAYITEM_INSERT = "insert into payroll_employee_payitem (emp_number, accountid, item_value, fromperiodid, toperiodid) values(?,?,?,?,?) ";
    public String PAYROLL_EMPLOYEE_PAYITEM_DELETE = "delete from payroll_employee_payitem where emp_number=? and accountid=? ";
    public String PAYROLL_EMPLOYEE_PAYITEM_DELETE_ALL = "delete from payroll_employee_payitem where emp_number=? ";

    public String PAYROLL_EMPLOYEE_ATTRIBUTE_SELECT = "select emp_number, attributeid, value_amount, fromperiodid, toperiodid from payroll_employee_attribute where emp_number=? ";
    public String PAYROLL_EMPLOYEE_ATTRIBUTE_INSERT = "insert into payroll_employee_attribute (emp_number, attributeid, value_amount, fromperiodid, toperiodid) values(?,?,?,?,?) ";
    public String PAYROLL_EMPLOYEE_ATTRIBUTE_DELETE = "delete from payroll_employee_attribute where emp_number=? and attributeid=? ";
    public String PAYROLL_EMPLOYEE_ATTRIBUTE_DELETE_ALL = "delete from payroll_employee_attribute where emp_number=? ";
    public String PAYROLL_EMPLOYEE_ATTRIBUTE_SETTINGS = "select v.attributeid, v.value_amount, v.fromperiodid, v.emp_number, a.description as attributename from vw_payroll_employee_attribute_setting v, payroll_attribute a where a.attributeid = v.attributeid and emp_number=? ";

    /* _______________ Payroll summaryReports ________________ */
    public String PAYROLL_EMPLOYEE_SUMMARY_SELECT = "select " +
            " (select count(b.emp_number) from payroll_employee_bankdetail b, employee e where b.emp_number = e.emp_number and e.employment_status='A' and b.policyid is not null) as policyCount," +
            " (select count(emp_number) from employee e where e.employment_status='A') as employeeCount," +
            " (select count(distinct p.emp_number) from payroll_payevent p, employee e where p.emp_number = e.emp_number and e.employment_status='A') as totalEmployeeStub ";

    public String PAYROLL_EMPLOYEE_SUMMARY_SELECT_BY_COMPANY = "select " +
            " (select count(b.emp_number) from payroll_employee_bankdetail b, employee e where b.emp_number = e.emp_number and e.employment_status='A' and b.policyid is not null and e.company_code=?) as policyCount," +
            " (select count(emp_number) from employee e where e.employment_status='A' and e.company_code=?) as employeeCount," +
            " (select count(distinct p.emp_number) from payroll_payevent p, employee e where p.emp_number = e.emp_number and e.employment_status='A' and e.company_code=?) as totalEmployeeStub ";

    
    /* _______________ Payroll Calculation and event Queries ______________ */
    public String PAYROLL_EMPLOYEE_PAYEVENT_DELETE_ALL = "delete from payroll_payevent where emp_number=? ";
    public String PAYROLL_PAYEVENT_INITIAL_ACCOUNT_AND_AMOUNT_SELECT = "select pp.accountid, amount, a.description, a.groupid from payroll_policy_payitem pp join payroll_payaccount a on a.accountid=pp.accountid where policyid=? and pp.fromperiodid<=? and (pp.toperiodid>? or pp.toperiodid is null) " +
            " union select pp.accountid, item_value, a.description, a.groupid from payroll_employee_payitem pp join payroll_payaccount a on a.accountid=pp.accountid where emp_number=? and pp.fromperiodid<=? and (pp.toperiodid>? or pp.toperiodid is null)";
    public String PAYROLL_PAYEVENT_INITIAL_INSERT = "insert into payroll_payevent (emp_number, periodid, accountid, derived, item_value, regtime, narrative ,payable) values(?,?,?,?,?,?,?,?) ";
    public String PAYROLL_PAYEVENT_ACCOUNT_SELECT = "select pe.accountid, formula, item_value, calcseq, inputtype from payroll_payevent pe join payroll_payaccount a on a.accountid=pe.accountid where pe.emp_number=? and pe.periodid=? order by calcseq";
    //update payevent after calculation
    public String PAYROLL_PAYEVENT_CALCULATION_UPDATE = "update payroll_payevent set amount=?, unit_price=?, quantity=? where accountid=? and emp_number=? and periodid=? ";
    
    public String PAYROLL_PAYEVENT_ATTRIBUTE_SELECT_BY_CODE_PERIOD_AND_EMPLOYEE = "select value_amount, a.description from payroll_employee_attribute ea join payroll_attribute a on a.attributeid=ea.attributeid where emp_number=? and a.code=? and fromperiodid = (select max(fromperiodid) from payroll_employee_attribute ea2 where ea2.emp_number=ea.emp_number and ea2.attributeid=ea.attributeid and ea2.fromperiodid<=?)";
    //query to get all payevents for an employee excluding the group with none/default/na
    public String PAYROLL_PAYEVENT_SELECT_BY_EMPLOYEE_AND_PERIOD = "select emp_number, periodid, e.accountid, item_value, narrative, amount, unit_price, derived, quantity, payable, g.name from payroll_payevent e, payroll_accountgroup g, payroll_payaccount a where a.accountid=e.accountid and g.groupid = e.payable and emp_number =? and periodid=? and g.name not in ('none','default','n/a') order by a.calcseq ";
    public String PAYROLL_PAYEVENT_SELECT_BY_EMPLOYEE_AND_PERIOD_FROM_ARCHIVE = "select emp_number, periodid, e.accountid, item_value, narrative, amount, unit_price, derived, quantity, payable, g.name from payroll_payevent_archive e, payroll_accountgroup g, payroll_payaccount a where a.accountid=e.accountid and g.groupid = e.payable and emp_number =? and periodid=? and g.name not in ('none','default','n/a') order by a.calcseq ";
    
    public String PAYROLL_PAYEVENT_SUMMARY_SELECT_BY_ACTIVE_PERIOD = "select b.id, b.emp_number, b.bank_name, b.bank_branch, b.account_name, b.account_number, b.account_type, z.firstname, z.lastname, " +
            " (select sum(amount) from payroll_payevent e, payroll_accountgroup g where e.payable = g.groupid and g.name='earning' and b.emp_number = e.emp_number) as totalearning, " +
            " (select sum(amount) from payroll_payevent e, payroll_accountgroup g where e.payable = g.groupid and g.name='deduction' and b.emp_number = e.emp_number) as totaldeduction, " +
            " (select sum(amount) from payroll_payevent e, payroll_accountgroup g where e.payable = g.groupid and g.name not in ('earning','deduction','none','n/a','default') and b.emp_number = e.emp_number) as totalothers " +
            " from payroll_employee_bankdetail b, employee z where z.emp_number = b.emp_number ";

    public String PAYROLL_PAYEVENT_SUMMARY_SELECT_BY_ACTIVE_PERIOD_AND_COMPANY = "select b.id, b.emp_number, b.bank_name, b.bank_branch, b.account_name, b.account_number, b.account_type, z.firstname, z.lastname, " +
            " (select sum(amount) from payroll_payevent e, payroll_accountgroup g where e.payable = g.groupid and g.name='earning' and b.emp_number = e.emp_number) as totalearning, " +
            " (select sum(amount) from payroll_payevent e, payroll_accountgroup g where e.payable = g.groupid and g.name='deduction' and b.emp_number = e.emp_number) as totaldeduction, " +
            " (select sum(amount) from payroll_payevent e, payroll_accountgroup g where e.payable = g.groupid and g.name not in ('earning','deduction','none','n/a','default') and b.emp_number = e.emp_number) as totalothers " +
            " from payroll_employee_bankdetail b, employee z where z.emp_number = b.emp_number and z.company_code=? ";

    //public  String APPRAISAL_FINALIZER = " prcSaveAppraisal(?,?) ";
    public String PAYROLL_PAYEVENT_SUMMARY_FOR_ALL_PERIODS = " STP_PAYROLL_EMPLOYEE_PAY_SLIP_HISTORY(?) ";

    public String PAYROLL_PAYEVENT_ARCHIVED = "insert into payroll_payevent_archive (emp_number, periodid, accountid, item_value, narrative, amount, unit_price, derived, quantity, payable, starttime, endtime, regtime) " +
            " select emp_number, periodid, accountid, item_value, narrative, amount, unit_price, derived, quantity, payable, starttime, endtime, regtime from payroll_payevent ";
    public String PAYROLL_PAYEVENT_TRUNCATE = "delete from payroll_payevent ";

    /* =============== promotion history ================== */
    public String PROMOTION_HISTORY_SELECT_ALL = "select idx, emp_number, jobid, salarygradeid, categoryid, reason, effective_date, estatus from employee_promotion_history order by idx asc, emp_number asc ";
    public String PROMOTION_HISTORY_SELECT_BY_EMPLOYEE = "select idx, emp_number, jobid, salarygradeid, categoryid, reason, effective_date, estatus from employee_promotion_history where emp_number=? order by idx asc ";
    public String PROMOTION_HISTORY_SELECT_LAST_BY_EMPLOYEE = "select idx, emp_number, jobid, salarygradeid, categoryid, reason, effective_date, estatus from employee_promotion_history where emp_number=? and idx = (select max(idx) from employee_promotion_history where emp_number=?) order by idx asc ";
    public String PROMOTION_HISTORY_UPDATE = "update employee_promotion_history set emp_number=?, jobid=?, salarygradeid=?, categoryid=?, reason=?, effective_date=? where idx=?";
    public String PROMOTION_HISTORY_INSERT = "insert into employee_promotion_history (emp_number, jobid, salarygradeid, categoryid, reason, effective_date, estatus) values (?,?,?,?,?,?,?)";
    public  String EMPLOYEE_PROMOTION_UPDATE_BASIC = "update employee set jobtitleid=?, categoryid=?, salarygradeid=? where emp_number = ? and active=1 ";

    /* =============== Scheduler checker =================== */
    public String SCHEDULER_INSERT = "insert into payroll_scheduler_log (code, start_time, status) values (?,?,'P')";
    public String SCHEDULER_SELECT = "select code, start_time, status from payroll_scheduler_log where status='P' order by start_time desc ";
    public String SCHEDULER_SELECT_BY_CODE = "select code, start_time, status from payroll_scheduler_log where code=?";
    public String SCHEDULER_UPDATE_BY_CODE = "update payroll_scheduler_log set status='A' where code=? ";
    public String SCHEDULER_DELETE_BY_CODE = "delete payroll_scheduler_log where code=? ";


    /* =============== Dashboard queries ==================== */
    public String DASHBOARD_EMPLOYEE_COUNT_BY_DEPT = "select d.deptname as description, count(e.emp_number) as counted from employee e right outer join department d on e.deptid = d.deptid group by d.deptname";
    public String DASHBOARD_EMPLOYEE_COUNT_BY_LOCATION = "select d.location as description, count(e.emp_number) as counted from employee e right outer join department d on e.deptid = d.deptid group by d.location";
    public String DASHBOARD_EMPLOYEE_COUNT_BY_CATEGORY = "select d.catname as description, count(e.emp_number) as counted from employee e right outer join employee_category d on e.categoryid = d.catid group by d.catname";

    public String DASHBOARD_RETENTION_RATE = "select (((count(emp_number) - (select count(emp_number) from employee where employment_status not in ('A'))) + 0.00)/count(emp_number))*100 from employee e where employment_date between ? and ?";
    public String DASHBOARD_PROMOTION_RATE = "select (((select count(distinct p.emp_number) from employee_promotion_history p, employee e where e.emp_number = p.emp_number and e.employment_status='A') + 0.00)" +
            " / (select count(z.emp_number) from employee z where z.employment_status = 'A')) * 100.00";


    public String NOTICE_BOARD_ACTIVE_SELECT_BY_EMPLOYEE = "select notice_id, subject, message, emp_number, create_date, expire_date, viewers, department, status from notice_board where emp_number = ? and status='P' and estatus='A' ";
    public String NOTICE_BOARD_ACTIVE_SELECT_BY_EMPLOYEE_SEARCH_BY_SUBJECT = "select notice_id, subject, message, emp_number, create_date, expire_date, viewers, department, status from notice_board where emp_number = ? and status='P' and subject like '%_SEARCH_%' and estatus='A' ";
    public String NOTICE_BOARD_ACTIVE_SELECT_BY_EMPLOYEE_SEARCH_BY_MESSAGE = "select notice_id, subject, message, emp_number, create_date, expire_date, viewers, department, status from notice_board where emp_number = ? and status='P' and message like '%_SEARCH_%' and estatus='A' ";
    public String NOTICE_BOARD_ACTIVE_SELECT_BY_ID = "select notice_id, subject, message, emp_number, create_date, expire_date, viewers, department, status from notice_board where notice_id = ? and status='P' ";
    public String NOTICE_BOARD_ACTIVE_SELECT_BY_TRANSACTION = "select notice_id, subject, message, emp_number, create_date, expire_date, viewers, department, status, transactionid, estatus, case viewers when ltrim('') then 'N/A' else (select firstname + ' ' + lastname from employee where emp_number=viewers) end as viewerName, case department when -1 then 'All' when ltrim('') then 'N/A' else (select deptname from department where deptid=department) end as departmentName  from notice_board where transactionid=? ";
    public String NOTICE_BOARD_INSERT = "insert into notice_board (subject, message, emp_number, create_date, expire_date, viewers, department, status, transactionid, estatus) values (?,?,?,?,?,?,?,'P',?,?) ";
    public String NOTICE_BOARD_UPDATE = "update notice_board set subject=?, message=?, expire_date=?, viewers=?, department=? where notice_id=? and emp_number=?";
    public String NOTICE_BOARD_STATUS_UPDATE = "update notice_board set estatus=? where transactionid=?";
    public String NOTICE_BOARD_DELETE = "delete notice_board where notice_id=? ";
    public String NOTICE_BOARD_INBOX_SELECT = "select notice_id, subject, message, emp_number, create_date, expire_date, viewers, department, status from notice_board where status='P' and ((viewers = ?) or (department=?)) and estatus not in ('P','R') " +
            " union " +
            " select notice_id, subject, message, emp_number, create_date, expire_date, viewers, department, status from notice_board where department='-1' and status='P' and estatus not in ('P','R') ";
    public String NOTICE_BOARD_INBOX_SELECT_SEARCH_BY_SUBJECT = "select notice_id, subject, message, emp_number, create_date, expire_date, viewers, department, status from notice_board where status='P' and ((viewers = ?) or (department=?))  and subject like '%_SEARCH_%' and estatus not in ('P','R') " +
            " union " +
            " select notice_id, subject, message, emp_number, create_date, expire_date, viewers, department, status from notice_board where department='-1' and status='P'  and subject like '%_SEARCH_%' and estatus not in ('P','R') ";
    public String NOTICE_BOARD_INBOX_SELECT_SEARCH_BY_MESSAGE = "select notice_id, subject, message, emp_number, create_date, expire_date, viewers, department, status from notice_board where status='P' and ((viewers = ?) or (department=?))  and message like '%_SEARCH_%' and estatus not in ('P','R') " +
            " union " +
            " select notice_id, subject, message, emp_number, create_date, expire_date, viewers, department, status from notice_board where department='-1' and status='P'  and message like '%_SEARCH_%' and estatus not in ('P','R')";

  // Menu
   public  String MENU_SELECT_BY_USERID = "select MenuTitle,class,URL,submenutitle,[Target] as Location,OrderCode from MENU  where  MenuCat=? order by OrderCode";

 public  String TRANSITION_INITIATOR_INFO = "select t1.action_date,(t2.firstname + ' ' +t2.lastname) as employee_name from transaction_audit t1,employee  t2 "+
         " where t1.transaction_id in (select id from transaction_table where transaction_ref=?) "+
         " and t1.action_by in (select initiator_id from transaction_table where initiator_id=?) "+
         " and t2.emp_number =t1.action_by";

}



