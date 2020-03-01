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
public class AppraisalInformation extends BaseData {
    private String description;
    private String statement;
    private String firstName;
    private String lastName;
    
    /** Creates a new instance of JobTitle */
    public AppraisalInformation() {
    }

    
    public static class Step1 extends AppraisalInformation{
        private long appraisalId;
        private long supervisor;
        private int appliedLevel;
        private Date startDate;
        private Date endDate;
        private int criteriaId;

        //transient
        private String criteriaDescription;

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
         * @return the criteriaId
         */
        public int getCriteriaId() {
            return criteriaId;
        }

        /**
         * @param criteriaId the criteriaId to set
         */
        public void setCriteriaId(int criteriaId) {
            this.criteriaId = criteriaId;
        }

        /**
         * @return the appraisalId
         */
        public long getAppraisalId() {
            return appraisalId;
        }

        /**
         * @param appraisalId the appraisalId to set
         */
        public void setAppraisalId(long appraisalId) {
            this.appraisalId = appraisalId;
        }

        /**
         * @return the supervisor
         */
        public long getSupervisor() {
            return supervisor;
        }

        /**
         * @param supervisor the supervisor to set
         */
        public void setSupervisor(long supervisor) {
            this.supervisor = supervisor;
        }

        /**
         * @return the appliedLevel
         */
        public int getAppliedLevel() {
            return appliedLevel;
        }

        /**
         * @param appliedLevel the appliedLevel to set
         */
        public void setAppliedLevel(int appliedLevel) {
            this.appliedLevel = appliedLevel;
        }

        /**
         * @return the criteriaDescription
         */
        public String getCriteriaDescription() {
            return criteriaDescription;
        }

        /**
         * @param criteriaDescription the criteriaDescription to set
         */
        public void setCriteriaDescription(String criteriaDescription) {
            this.criteriaDescription = criteriaDescription;
        }

    }

    public static class Step2 extends AppraisalInformation{
        private long appraisalId;
        private long supervisor;
        private int appliedLevel;
        private int groupId;
        private int competenceId;
        private int rateIndex;

        //transient
        private String groupName;
        private String competenceName;
        private String rateDescription;
        /**
         * @return the groupId
         */
        public int getGroupId() {
            return groupId;
        }

        /**
         * @param groupId the groupId to set
         */
        public void setGroupId(int groupId) {
            this.groupId = groupId;
        }

        /**
         * @return the competenceId
         */
        public int getCompetenceId() {
            return competenceId;
        }

        /**
         * @param competenceId the competenceId to set
         */
        public void setCompetenceId(int competenceId) {
            this.competenceId = competenceId;
        }

        /**
         * @return the rateIndex
         */
        public int getRateIndex() {
            return rateIndex;
        }

        /**
         * @param rateIndex the rateIndex to set
         */
        public void setRateIndex(int rateIndex) {
            this.rateIndex = rateIndex;
        }

        /**
         * @return the appraisalId
         */
        public long getAppraisalId() {
            return appraisalId;
        }

        /**
         * @param appraisalId the appraisalId to set
         */
        public void setAppraisalId(long appraisalId) {
            this.appraisalId = appraisalId;
        }

        /**
         * @return the supervisor
         */
        public long getSupervisor() {
            return supervisor;
        }

        /**
         * @param supervisor the supervisor to set
         */
        public void setSupervisor(long supervisor) {
            this.supervisor = supervisor;
        }

        /**
         * @return the appliedLevel
         */
        public int getAppliedLevel() {
            return appliedLevel;
        }

        /**
         * @param appliedLevel the appliedLevel to set
         */
        public void setAppliedLevel(int appliedLevel) {
            this.appliedLevel = appliedLevel;
        }

        /**
         * @return the groupName
         */
        public String getGroupName() {
            return groupName;
        }

        /**
         * @param groupName the groupName to set
         */
        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        /**
         * @return the competenceName
         */
        public String getCompetenceName() {
            return competenceName;
        }

        /**
         * @param competenceName the competenceName to set
         */
        public void setCompetenceName(String competenceName) {
            this.competenceName = competenceName;
        }

        /**
         * @return the rateDescription
         */
        public String getRateDescription() {
            return rateDescription;
        }

        /**
         * @param rateDescription the rateDescription to set
         */
        public void setRateDescription(String rateDescription) {
            this.rateDescription = rateDescription;
        }
    }

    public static class Step2_Statement extends AppraisalInformation{

        private long appraisalId;
        private long supervisor;
        private int appliedLevel;
        private int groupId;
        private String statement;

        /**
         * @return the groupId
         */
        public int getGroupId() {
            return groupId;
        }

        /**
         * @param groupId the groupId to set
         */
        public void setGroupId(int groupId) {
            this.groupId = groupId;
        }

        /**
         * @return the statement
         */
        public String getStatement() {
            return statement;
        }

        /**
         * @param statement the statement to set
         */
        public void setStatement(String statement) {
            this.statement = statement;
        }

        /**
         * @return the appraisalId
         */
        public long getAppraisalId() {
            return appraisalId;
        }

        /**
         * @param appraisalId the appraisalId to set
         */
        public void setAppraisalId(long appraisalId) {
            this.appraisalId = appraisalId;
        }

        /**
         * @return the supervisor
         */
        public long getSupervisor() {
            return supervisor;
        }

        /**
         * @param supervisor the supervisor to set
         */
        public void setSupervisor(long supervisor) {
            this.supervisor = supervisor;
        }

        /**
         * @return the appliedLevel
         */
        public int getAppliedLevel() {
            return appliedLevel;
        }

        /**
         * @param appliedLevel the appliedLevel to set
         */
        public void setAppliedLevel(int appliedLevel) {
            this.appliedLevel = appliedLevel;
        }
    }

    public static class Step3 extends AppraisalInformation{
        private long appraisalId;
        private long supervisor;
        private int appliedLevel;
        private Date dueDate;
        private String goalTitle;
        private String description;
        private String goalResult;
        private int rateIndex;

        //transient
        private String rateDescription;

        /**
         * @return the dueDate
         */
        public Date getDueDate() {
            return dueDate;
        }

        /**
         * @param dueDate the dueDate to set
         */
        public void setDueDate(Date dueDate) {
            this.dueDate = dueDate;
        }

        /**
         * @return the goalTitle
         */
        public String getGoalTitle() {
            return goalTitle;
        }

        /**
         * @param goalTitle the goalTitle to set
         */
        public void setGoalTitle(String goalTitle) {
            this.goalTitle = goalTitle;
        }

        /**
         * @return the description
         */
        public String getDescription() {
            return description;
        }

        /**
         * @param description the description to set
         */
        public void setDescription(String description) {
            this.description = description;
        }

        /**
         * @return the goalResult
         */
        public String getGoalResult() {
            return goalResult;
        }

        /**
         * @param goalResult the goalResult to set
         */
        public void setGoalResult(String goalResult) {
            this.goalResult = goalResult;
        }

        /**
         * @return the rateIndex
         */
        public int getRateIndex() {
            return rateIndex;
        }

        /**
         * @param rateIndex the rateIndex to set
         */
        public void setRateIndex(int rateIndex) {
            this.rateIndex = rateIndex;
        }

        /**
         * @return the appraisalId
         */
        public long getAppraisalId() {
            return appraisalId;
        }

        /**
         * @param appraisalId the appraisalId to set
         */
        public void setAppraisalId(long appraisalId) {
            this.appraisalId = appraisalId;
        }

        /**
         * @return the supervisor
         */
        public long getSupervisor() {
            return supervisor;
        }

        /**
         * @param supervisor the supervisor to set
         */
        public void setSupervisor(long supervisor) {
            this.supervisor = supervisor;
        }

        /**
         * @return the appliedLevel
         */
        public int getAppliedLevel() {
            return appliedLevel;
        }

        /**
         * @param appliedLevel the appliedLevel to set
         */
        public void setAppliedLevel(int appliedLevel) {
            this.appliedLevel = appliedLevel;
        }

        /**
         * @return the rateDescription
         */
        public String getRateDescription() {
            return rateDescription;
        }

        /**
         * @param rateDescription the rateDescription to set
         */
        public void setRateDescription(String rateDescription) {
            this.rateDescription = rateDescription;
        }
    }

    public static class Step4 extends AppraisalInformation{
        private long appraisalId;
        private long supervisor;
        private int appliedLevel;
        private String statement;
        private String improvement;
        
        /**
         * @return the statement
         */
        public String getStatement() {
            return statement;
        }

        /**
         * @param statement the statement to set
         */
        public void setStatement(String statement) {
            this.statement = statement;
        }

        /**
         * @return the improvement
         */
        public String getImprovement() {
            return improvement;
        }

        /**
         * @param improvement the improvement to set
         */
        public void setImprovement(String improvement) {
            this.improvement = improvement;
        }

        /**
         * @return the appraisalId
         */
        public long getAppraisalId() {
            return appraisalId;
        }

        /**
         * @param appraisalId the appraisalId to set
         */
        public void setAppraisalId(long appraisalId) {
            this.appraisalId = appraisalId;
        }

        /**
         * @return the supervisor
         */
        public long getSupervisor() {
            return supervisor;
        }

        /**
         * @param supervisor the supervisor to set
         */
        public void setSupervisor(long supervisor) {
            this.supervisor = supervisor;
        }

        /**
         * @return the appliedLevel
         */
        public int getAppliedLevel() {
            return appliedLevel;
        }

        /**
         * @param appliedLevel the appliedLevel to set
         */
        public void setAppliedLevel(int appliedLevel) {
            this.appliedLevel = appliedLevel;
        }
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the statement
     */
    public String getStatement() {
        return statement;
    }

    /**
     * @param statement the statement to set
     */
    public void setStatement(String statement) {
        this.statement = statement;
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
        if(firstName != null){
            firstName = firstName.trim();
        }
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
        if(lastName != null){
            lastName = lastName.trim();
        }
        this.lastName = lastName;
    }

    
}
