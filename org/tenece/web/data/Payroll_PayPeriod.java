/*
 * (c) Copyright 2009, 2010 The Tenece Professional Services.
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
 * Tenece Professional Services Limited
 * @author amachree
 */
public class Payroll_PayPeriod extends BaseData{
    private int id;
    private Date startTime;
    private Date endTime;
    private int locked;
    private Date dateCreated;

    public static class PayrollSummary extends BaseData{
        private int totalEmployee;
        private int totalEmployeeWithPolicy;
        private int totalEmployeeWithPayStubs;

        /**
         * @return the totalEmployee
         */
        public int getTotalEmployee() {
            return totalEmployee;
        }

        /**
         * @param totalEmployee the totalEmployee to set
         */
        public void setTotalEmployee(int totalEmployee) {
            this.totalEmployee = totalEmployee;
        }

        /**
         * @return the totalEmployeeWithPolicy
         */
        public int getTotalEmployeeWithPolicy() {
            return totalEmployeeWithPolicy;
        }

        /**
         * @param totalEmployeeWithPolicy the totalEmployeeWithPolicy to set
         */
        public void setTotalEmployeeWithPolicy(int totalEmployeeWithPolicy) {
            this.totalEmployeeWithPolicy = totalEmployeeWithPolicy;
        }

        /**
         * @return the totalEmployeeWithPayStubs
         */
        public int getTotalEmployeeWithPayStubs() {
            return totalEmployeeWithPayStubs;
        }

        /**
         * @param totalEmployeeWithPayStubs the totalEmployeeWithPayStubs to set
         */
        public void setTotalEmployeeWithPayStubs(int totalEmployeeWithPayStubs) {
            this.totalEmployeeWithPayStubs = totalEmployeeWithPayStubs;
        }

    }

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
     * @return the startTime
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the endTime
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * @return the locked
     */
    public int getLocked() {
        return locked;
    }

    /**
     * @param locked the locked to set
     */
    public void setLocked(int locked) {
        this.locked = locked;
    }

    /**
     * @return the dateCreated
     */
    public Date getDateCreated() {
        return dateCreated;
    }

    /**
     * @param dateCreated the dateCreated to set
     */
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public static class PaymentSummary extends EmployeeBank {
        private String firstName;
        private String lastName;
        private double totalEarning;
        private double totalDeduction;
        private double totalOthers;

        private Date startTime;
        private Date endTime;
        
        /**
         * @return the totalEarning
         */
        public double getTotalEarning() {
            return totalEarning;
        }

        /**
         * @param totalEarning the totalEarning to set
         */
        public void setTotalEarning(double totalEarning) {
            this.totalEarning = totalEarning;
        }

        /**
         * @return the totalDeduction
         */
        public double getTotalDeduction() {
            return totalDeduction;
        }

        /**
         * @param totalDeduction the totalDeduction to set
         */
        public void setTotalDeduction(double totalDeduction) {
            this.totalDeduction = totalDeduction;
        }

        /**
         * @return the totalOthers
         */
        public double getTotalOthers() {
            return totalOthers;
        }

        /**
         * @param totalOthers the totalOthers to set
         */
        public void setTotalOthers(double totalOthers) {
            this.totalOthers = totalOthers;
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

        /**
         * @return the startTime
         */
        public Date getStartTime() {
            return startTime;
        }

        /**
         * @param startTime the startTime to set
         */
        public void setStartTime(Date startTime) {
            this.startTime = startTime;
        }

        /**
         * @return the endTime
         */
        public Date getEndTime() {
            return endTime;
        }

        /**
         * @param endTime the endTime to set
         */
        public void setEndTime(Date endTime) {
            this.endTime = endTime;
        }
        
    }
}
