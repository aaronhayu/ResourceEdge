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
import java.util.List;

/**
 *
 * @author jeffry.amachree
 */
public class Appraisal  extends BaseData{
    private long id;
    private long employeeId;
    private long transactionId;
    private String eStatus;
    
    /** Creates a new instance of JobTitle */
    public Appraisal() {
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
    public long getEmployeeId() {
        return employeeId;
    }

    /**
     * @param employeeId the employeeId to set
     */
    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
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

    public static class Step1{
        private int employeeId;
        private Date startDate;
        private Date endDate;
        private String reviewer;
        private int criteriaId;
        private long transactionId;
        private int isTransaction;

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
         * @return the reviewer
         */
        public String getReviewer() {
            if(reviewer != null){
                reviewer = reviewer.trim();
            }
            return reviewer;
        }

        /**
         * @param reviewer the reviewer to set
         */
        public void setReviewer(String reviewer) {
            this.reviewer = reviewer;
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
         * @return the isTransaction
         */
        public int getIsTransaction() {
            return isTransaction;
        }

        /**
         * @param isTransaction the isTransaction to set
         */
        public void setIsTransaction(int isTransaction) {
            this.isTransaction = isTransaction;
        }
    }

    public static class Step2{
        private int id;
        private int employeeId;
        private int groupId;
        private int competenceId;
        private int rateIndex;
        private long transactionId;
        private int isTransaction;

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
         * @return the isTransaction
         */
        public int getIsTransaction() {
            return isTransaction;
        }

        /**
         * @param isTransaction the isTransaction to set
         */
        public void setIsTransaction(int isTransaction) {
            this.isTransaction = isTransaction;
        }
    }

    public static class Step2_Statement{
        private int employeeId;
        private int groupId;
        private String statement;
        private long transactionId;
        private int isTransaction;
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
         * @return the isTransaction
         */
        public int getIsTransaction() {
            return isTransaction;
        }

        /**
         * @param isTransaction the isTransaction to set
         */
        public void setIsTransaction(int isTransaction) {
            this.isTransaction = isTransaction;
        }
    }

    public static class Step3{
        private long id;
        private int employeeId;
        private Date dueDate;
        private String goalTitle;
        private String description;
        private String goalResult;
        private int rateIndex;
        private long transactionId;
        private int isTransaction;

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
         * @return the isTransaction
         */
        public int getIsTransaction() {
            return isTransaction;
        }

        /**
         * @param isTransaction the isTransaction to set
         */
        public void setIsTransaction(int isTransaction) {
            this.isTransaction = isTransaction;
        }
    }

    public static class Step4{
        private int employeeId;
        private String statement;
        private String improvement;
        private long transactionId;
        private int isTransaction;
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
         * @return the isTransaction
         */
        public int getIsTransaction() {
            return isTransaction;
        }

        /**
         * @param isTransaction the isTransaction to set
         */
        public void setIsTransaction(int isTransaction) {
            this.isTransaction = isTransaction;
        }
    }

    
    public static class RatingSummary {
        private String section;
        private float rating;
        private String rateText;
        private String ratingLevel;

        /**
         * @return the section
         */
        public String getSection() {
            return section;
        }

        /**
         * @param section the section to set
         */
        public void setSection(String section) {
            this.section = section;
        }

        /**
         * @return the rating
         */
        public float getRating() {
            return rating;
        }

        /**
         * @param rating the rating to set
         */
        public void setRating(float rating) {
            this.rating = rating;
        }

        /**
         * @return the rateText
         */
        public String getRateText() {
            return rateText;
        }

        /**
         * @param rateText the rateText to set
         */
        public void setRateText(String rateText) {
            this.rateText = rateText;
        }
           /**
         * @return the ratingLevel
         */
        public String getRatingLevel() {
            return ratingLevel;
        }

        /**
         * @param ratingLevel the section to set
         */
        public void setRatingLevel(String ratingLevel) {
            this.ratingLevel = ratingLevel;
        }
    }
    public static class step2Competence{
        private int groupId;
        private String groupDescription;
        private List<AppraisalCompetence> competencies;
        private List<EmployeeAppraisalCompetence> emp_competencies;
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
         * @return the groupDescription
         */
        public String getGroupDescription() {
            return groupDescription;
        }

        /**
         * @param groupDescription the groupDescription to set
         */
        public void setGroupDescription(String groupDescription) {
            this.groupDescription = groupDescription;
        }

        /**
         * @return the competencies
         */
        public List<AppraisalCompetence> getCompetencies() {
            return competencies;
        }

        /**
         * @param competencies the competencies to set
         */
        public void setCompetencies(List<AppraisalCompetence> competencies) {
            this.competencies = competencies;
        }
        /**
         * @return the emp_competencies
         */
        public List<EmployeeAppraisalCompetence> getEmp_competencies() {
            return emp_competencies;
        }

        /**
         * @param emp_competencies the emp_competencies to set
         */
        public void setEmp_competencies(List<EmployeeAppraisalCompetence> emp_competencies) {
            this.emp_competencies = emp_competencies;
        }
        
    }
}
