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
 * This is the Data object to handle Department records.
 * It is also tied to DepartmentDAO for all Database Operation
 * @author jeffry.amachree
 */
//id, emp_number, leave_year, leave_type_id, start_date, end_date,
//relief_officer, contact_address, contact_number, reason, transactionid, estatus
public class Leave extends BaseData{
    private long id;
    private int employeeId;
    private int year;
    private long leaveTypeId;
    private Date startDate;
    private Date endDate;
    private int reliefOfficer;
    private String contactAddress;
    private String contactMobile;
    private String reason;
    private long transactionId;
    private String eStatus;

    //transient object
    private String leaveTypeDescription;
    private String employeeName;
    private String reliefOfficerName;
    /** Creates a new instance of Department */
    public Leave() {
    }

    public static class LeaveResumption {
        private long id;
        private long leaveId;
        private Date startDate;
        private Date endDate;
        private String reason;
        private long transactionId;
        private String eStatus;

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
         * @return the leaveId
         */
        public long getLeaveId() {
            return leaveId;
        }

        /**
         * @param leaveId the leaveId to set
         */
        public void setLeaveId(long leaveId) {
            this.leaveId = leaveId;
        }

        /**
         * @return the startDate
         */
        public Date getStartDate() {
            return startDate;
        }

        /**
         * @param startDate the startDate to set
         */
        public void setStartDate(Date startDate) {
            this.startDate = startDate;
        }

        /**
         * @return the endDate
         */
        public Date getEndDate() {
            return endDate;
        }

        /**
         * @param endDate the endDate to set
         */
        public void setEndDate(Date endDate) {
            this.endDate = endDate;
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
    }

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
     * @return the employeeId
     */
    public int getEmployeeId() {
        return employeeId;
    }

    /**
     * @param employeeId the employeeId to set
     */
    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * @return the leaveTypeId
     */
    public long getLeaveTypeId() {
        return leaveTypeId;
    }

    /**
     * @param leaveTypeId the leaveTypeId to set
     */
    public void setLeaveTypeId(long leaveTypeId) {
        this.leaveTypeId = leaveTypeId;
    }

    /**
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the reliefOfficer
     */
    public int getReliefOfficer() {
        return reliefOfficer;
    }

    /**
     * @param reliefOfficer the reliefOfficer to set
     */
    public void setReliefOfficer(int reliefOfficer) {
        this.reliefOfficer = reliefOfficer;
    }

    /**
     * @return the contactAddress
     */
    public String getContactAddress() {
        return contactAddress;
    }

    /**
     * @param contactAddress the contactAddress to set
     */
    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    /**
     * @return the contactMobile
     */
    public String getContactMobile() {
        return contactMobile;
    }

    /**
     * @param contactMobile the contactMobile to set
     */
    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
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
     * @return the leaveTypeDescription
     */
    public String getLeaveTypeDescription() {
        return leaveTypeDescription;
    }

    /**
     * @param leaveTypeDescription the leaveTypeDescription to set
     */
    public void setLeaveTypeDescription(String leaveTypeDescription) {
        this.leaveTypeDescription = leaveTypeDescription;
    }

    /**
     * @return the employeeName
     */
    public String getEmployeeName() {
        return employeeName;
    }

    /**
     * @param employeeName the employeeName to set
     */
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    /**
     * @return the reliefOfficerName
     */
    public String getReliefOfficerName() {
        return reliefOfficerName;
    }

    /**
     * @param reliefOfficerName the reliefOfficerName to set
     */
    public void setReliefOfficerName(String reliefOfficerName) {
        this.reliefOfficerName = reliefOfficerName;
    }

}
