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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.tenece.web.services;

import java.util.Hashtable;
import java.util.Vector;
import org.tenece.hr.data.dao.AppraisalDAO;
import org.tenece.hr.data.dao.CompanyDAO;
import org.tenece.hr.data.dao.CourseDAO;
import org.tenece.hr.data.dao.EmployeeBankDetailDAO;
import org.tenece.hr.data.dao.EmployeeChildrenDAO;
import org.tenece.hr.data.dao.EmployeeDAO;
import org.tenece.hr.data.dao.EmployeeDependentDAO;
import org.tenece.hr.data.dao.EmployeeEducationDAO;
import org.tenece.hr.data.dao.EmployeeEmergencyContactDAO;
import org.tenece.hr.data.dao.LeaveDAO;
import org.tenece.hr.data.dao.PerformanceTargetDAO;
import org.tenece.hr.data.dao.TransactionDAO;
import org.tenece.web.common.ConfigReader;
import org.tenece.web.data.EmailMessage;
import org.tenece.web.data.Employee;
import org.tenece.web.data.Users;
import org.tenece.web.mail.EmailImpl;

/**
 *
 * @author jeffry.amachree
 */
public class MailService extends BaseService {

    private PerformanceTargetDAO targetDAO = null;
    private CourseDAO courseDAO = null;
    private LeaveDAO leaveDAO = null;
    private TransactionDAO transactionDAO = null;
    private EmployeeDAO employeeDAO = null;
    private EmployeeChildrenDAO employeeChildrenDAO = null;
    private EmployeeDependentDAO employeeDependentDAO = null;
    private EmployeeEducationDAO employeeEducationDAO = null;
    private EmployeeEmergencyContactDAO employeeEmergencyContactDAO = null;
    private EmployeeBankDetailDAO employeeBankDetailDAO = null;
    private AppraisalDAO appraisalDAO = null;
    private CompanyDAO companyDAO = null;

    public int sendPasswordRecall(long employeeID, String password, String companyLogo){
        try{
            Employee employee = getEmployeeDAO().getEmployeeBasicDataByEmpNumber((int)employeeID);
            Hashtable keys = new Hashtable();
            keys.put("EMAIL_PASSWORD", password);
            keys.put("EMAIL_NAME", employee.getFullName());
            keys.put("COMPANY_LOGO", companyLogo);

            int sent = this.sendEmail("PWD_RECALL", employee.getEmployeeNumber(), keys);

            System.out.println("completed mail sent: " + sent);
            return sent;
        }catch(Exception e){
            return 0;
        }
    }

    public int sendChangePassword(Users _user, String newPwd2, String companyLogo){
        try{
            Employee employee = getEmployeeDAO().getEmployeeBasicDataById(_user.getEmployeeId());
            Hashtable keys = new Hashtable();
            keys.put("EMAIL_PASSWORD", newPwd2);
            keys.put("EMAIL_NAME", employee.getFullName());
            keys.put("COMPANY_LOGO", companyLogo);

            int sent = this.sendEmail("CHANGE_PWD", _user.getEmployeeId(), keys);
            System.out.println("mail sent: " + sent);
            return sent;
        }catch(Exception e){
            return 0;
        }
    }

    public int sendEmail(String code, long receivingEmployeeId, Hashtable keyHolders){
        try{
            //get email template from config
            String standardEmailMessage = ConfigReader.loadStandardEmailTemplate();
            //get email based on code
            EmailMessage message = getCompanyDAO().getEmailMessageByCode(code);
            Employee employee = getEmployeeDAO().getEmployeeBasicDataById(receivingEmployeeId);

            String finalMessage = standardEmailMessage.replaceAll("VOLMACHT_MESSAGE", message.getMessage());
            
            String messageBody = EmailImpl.formatEmailMessage(finalMessage, keyHolders);
            //System.out.println(employee.getEmail() + "\nEmail:\n" + finalMessage);

            return EmailImpl.sendEmail(employee.getEmail(), message.getSenderEmail(), "", "", message.getSubject(),
                    messageBody, new Vector());

        }catch(Exception e){
            //e.printStackTrace();
            return 0;
        }
    }

    /**
     * @return the targetDAO
     */
    public PerformanceTargetDAO getTargetDAO() {
        if(targetDAO == null){
            targetDAO = new PerformanceTargetDAO();
        }
        return targetDAO;
    }

    /**
     * @return the transactionDAO
     */
    public TransactionDAO getTransactionDAO() {
        if(transactionDAO == null){
            transactionDAO = new TransactionDAO();
        }
        return transactionDAO;
    }

    /**
     * @return the courseDAO
     */
    public CourseDAO getCourseDAO() {
        if(courseDAO == null){
            courseDAO = new CourseDAO();
        }
        return courseDAO;
    }

    /**
     * @return the leaveDAO
     */
    public LeaveDAO getLeaveDAO() {
        if(leaveDAO == null){
            leaveDAO = new LeaveDAO();
        }
        return leaveDAO;
    }

    /**
     * @return the employeeDAO
     */
    public EmployeeDAO getEmployeeDAO() {
        if(employeeDAO == null){
            employeeDAO = new EmployeeDAO();
        }
        return employeeDAO;
    }

    /**
     * @return the employeeChildrenDAO
     */
    public EmployeeChildrenDAO getEmployeeChildrenDAO() {
        if(employeeChildrenDAO == null){
            employeeChildrenDAO = new EmployeeChildrenDAO();
        }
        return employeeChildrenDAO;
    }

    /**
     * @return the employeeDependentDAO
     */
    public EmployeeDependentDAO getEmployeeDependentDAO() {
        if(employeeDependentDAO == null){
            employeeDependentDAO = new EmployeeDependentDAO();
        }
        return employeeDependentDAO;
    }

    /**
     * @return the employeeEducationDAO
     */
    public EmployeeEducationDAO getEmployeeEducationDAO() {
        if(employeeEducationDAO == null){
            employeeEducationDAO = new EmployeeEducationDAO();
        }
        return employeeEducationDAO;
    }

    /**
     * @return the employeeEmergencyContactDAO
     */
    public EmployeeEmergencyContactDAO getEmployeeEmergencyContactDAO() {
        if(employeeEmergencyContactDAO == null){
            employeeEmergencyContactDAO = new EmployeeEmergencyContactDAO();
        }
        return employeeEmergencyContactDAO;
    }

    /**
     * @return the employeeBankDetailDAO
     */
    public EmployeeBankDetailDAO getEmployeeBankDetailDAO() {
        if(employeeBankDetailDAO == null){
            employeeBankDetailDAO = new EmployeeBankDetailDAO();
        }
        return employeeBankDetailDAO;
    }

    /**
     * @return the appraisalDAO
     */
    public AppraisalDAO getAppraisalDAO() {
        if(appraisalDAO == null){
            appraisalDAO = new AppraisalDAO();
        }
        return appraisalDAO;
    }

    /**
     * @return the companyDAO
     */
    public CompanyDAO getCompanyDAO() {
        if(companyDAO == null){
            companyDAO = new CompanyDAO();
        }
        return companyDAO;
    }
}
