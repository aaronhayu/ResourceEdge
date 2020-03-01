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

package org.tenece.web.data;

import java.util.Date;

/**
 *
 * @author jeffry.amachree
 */
public class Employee extends BaseData {
    private Long employeeNumber;
    private int departmentId;
    private int jobTitleId;
    private int employeeTypeId;
    private int categoryId;
    
    private String salutation;
    private String firstName;
    private String lastName;
    
    private Date dateOfBirth;
    private String gender;
    private String maritalStatus;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zipCode;
    
    private String country;
    private String email;
    private String homePhone;
    private String officePhone;
    private String cellPhone;
    private int active = 1;
    //staff ID
    private Long employeeID;

    private Date employmentDate;
    private Date confirmationDate;
    private String employmentStatus;
    private int confirmationStatus;

    private int salaryGradeId;
    //for transaction tables only
    private long transactionId;
    private String eStatus;
    private long nextApprovingOfficer;
    /** Creates a new instance of Employee */
    public Employee() {
    }

    /**
     * @return the nextApprovingOfficer
     */
    public long getNextApprovingOfficer() {
        return nextApprovingOfficer;
    }

    /**
     * @param nextApprovingOfficer the nextApprovingOfficer to set
     */
    public void setNextApprovingOfficer(long nextApprovingOfficer) {
        this.nextApprovingOfficer = nextApprovingOfficer;
    }

    
    public static class Promotion extends Employee{
        private int id;
        private String reason;
        private Date effectiveDate;

        private String categoryName;
        private String jobName;
        private String salaryGradeName;

        /**
         * @return the id
         */
        public int getId() {
            return id;
        }

        /**
         * @param id the id to set
         */
        public void setId(int id) {
            this.id = id;
        }

        /**
         * @return the reason
         */
        public String getReason() {
            return reason;
        }

        /**
         * @param reason the reason to set
         */
        public void setReason(String reason) {
            this.reason = reason;
        }

        /**
         * @return the effectiveDate
         */
        public Date getEffectiveDate() {
            return effectiveDate;
        }

        /**
         * @param effectiveDate the effectiveDate to set
         */
        public void setEffectiveDate(Date effectiveDate) {
            this.effectiveDate = effectiveDate;
        }

        /**
         * @return the categoryName
         */
        public String getCategoryName() {
            return categoryName;
        }

        /**
         * @param categoryName the categoryName to set
         */
        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        /**
         * @return the jobName
         */
        public String getJobName() {
            return jobName;
        }

        /**
         * @param jobName the jobName to set
         */
        public void setJobName(String jobName) {
            this.jobName = jobName;
        }

        /**
         * @return the salaryGradeName
         */
        public String getSalaryGradeName() {
            return salaryGradeName;
        }

        /**
         * @param salaryGradeName the salaryGradeName to set
         */
        public void setSalaryGradeName(String salaryGradeName) {
            this.salaryGradeName = salaryGradeName;
        }
    }

    public Long getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(Long employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public int getJobTitleId() {
        return jobTitleId;
    }

    public void setJobTitleId(int jobTitleId) {
        this.jobTitleId = jobTitleId;
    }

    public int getEmployeeTypeId() {
        return employeeTypeId;
    }

    public void setEmployeeTypeId(int employeeTypeId) {
        this.employeeTypeId = employeeTypeId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if(firstName != null){
            firstName = firstName.trim();
        }
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if(lastName != null){
            lastName = lastName.trim();
        }
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public Long getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Long employeeID) {
        this.employeeID = employeeID;
    }

    /**
     * @return the fullName
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getActiveDescription(){
            return "Active";
    }

    /**
     * @return the employmentDate
     */
    public Date getEmploymentDate() {
        return employmentDate;
    }

    /**
     * @param employmentDate the employmentDate to set
     */
    public void setEmploymentDate(Date employmentDate) {
        this.employmentDate = employmentDate;
    }

    /**
     * @return the confirmationDate
     */
    public Date getConfirmationDate() {
        return confirmationDate;
    }

    /**
     * @param confirmationDate the confirmationDate to set
     */
    public void setConfirmationDate(Date confirmationDate) {
        this.confirmationDate = confirmationDate;
    }

    /**
     * @return the employmentStatus
     */
    public String getEmploymentStatus() {
        return employmentStatus;
    }

    /**
     * @param employmentStatus the employmentStatus to set
     */
    public void setEmploymentStatus(String employmentStatus) {
        this.employmentStatus = employmentStatus;
    }

    /**
     * @return the confirmationStatus
     */
    public int getConfirmationStatus() {
        return confirmationStatus;
    }

    /**
     * @param confirmationStatus the confirmationStatus to set
     */
    public void setConfirmationStatus(int confirmationStatus) {
        this.confirmationStatus = confirmationStatus;
    }

    /**
     * @return the transactionId
     */
    public long getTransactionId() {
        return transactionId;
    }

    /**
     * @param transactionId the transactionId to set
     */
    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    /**
     * @return the eStatus
     */
    public String geteStatus() {
        return eStatus;
    }

    /**
     * @param eStatus the eStatus to set
     */
    public void seteStatus(String eStatus) {
        this.eStatus = eStatus;
    }

    /**
     * @return the salaryGradeId
     */
    public int getSalaryGradeId() {
        return salaryGradeId;
    }

    /**
     * @param salaryGradeId the salaryGradeId to set
     */
    public void setSalaryGradeId(int salaryGradeId) {
        this.salaryGradeId = salaryGradeId;
    }


    public static class Termination {
        private long id;
        private long employeeNumber;
        private String operation;
        private String reason;
        private Date effectiveDate;
        private String period;
        private long transactionId;
        private String eStatus;

        //transient object
        private String firstName;
        private String lastName;

        /**
         * @return the id
         */
        public long getId() {
            return id;
        }

        /**
         * @param id the id to set
         */
        public void setId(long id) {
            this.id = id;
        }

        /**
         * @return the employeeNumber
         */
        public long getEmployeeNumber() {
            return employeeNumber;
        }

        /**
         * @param employeeNumber the employeeNumber to set
         */
        public void setEmployeeNumber(long employeeNumber) {
            this.employeeNumber = employeeNumber;
        }

        /**
         * @return the operation
         */
        public String getOperation() {
            return operation;
        }

        /**
         * @param operation the operation to set
         */
        public void setOperation(String operation) {
            this.operation = operation;
        }

        /**
         * @return the reason
         */
        public String getReason() {
            return reason;
        }

        /**
         * @param reason the reason to set
         */
        public void setReason(String reason) {
            this.reason = reason;
        }

        /**
         * @return the effectiveDate
         */
        public Date getEffectiveDate() {
            return effectiveDate;
        }

        /**
         * @param effectiveDate the effectiveDate to set
         */
        public void setEffectiveDate(Date effectiveDate) {
            this.effectiveDate = effectiveDate;
        }

        /**
         * @return the period
         */
        public String getPeriod() {
            return period;
        }

        /**
         * @param period the period to set
         */
        public void setPeriod(String period) {
            this.period = period;
        }

        /**
         * @return the transactionId
         */
        public long getTransactionId() {
            return transactionId;
        }

        /**
         * @param transactionId the transactionId to set
         */
        public void setTransactionId(long transactionId) {
            this.transactionId = transactionId;
        }

        /**
         * @return the eStatus
         */
        public String geteStatus() {
            return eStatus;
        }

        /**
         * @param eStatus the eStatus to set
         */
        public void seteStatus(String eStatus) {
            this.eStatus = eStatus;
        }

        /**
         * @return the firstName
         */
        public String getFirstName() {
            return firstName;
        }

        /**
         * @param firstName the firstName to set
         */
        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        /**
         * @return the lastName
         */
        public String getLastName() {
            return lastName;
        }

        /**
         * @param lastName the lastName to set
         */
        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
    }
}
