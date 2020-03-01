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

/**
 * Tenece Professional Services Limited
 * @author amachree
 */
public class Payroll_PayAccount extends BaseData{
    private int id;
    private String description;
    private String formula;
    private int calculationSequence;
    private int inputType;
    private String eStatus;
    private int groupId;
    //transient object
    private String inputTypeDescription;
    private transient double itemValue;

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
     * @return the formula
     */
    public String getFormula() {
        return formula;
    }

    /**
     * @param formula the formula to set
     */
    public void setFormula(String formula) {
        this.formula = formula;
    }

    /**
     * @return the calculationSequence
     */
    public int getCalculationSequence() {
        return calculationSequence;
    }

    /**
     * @param calculationSequence the calculationSequence to set
     */
    public void setCalculationSequence(int calculationSequence) {
        this.calculationSequence = calculationSequence;
    }

    /**
     * @return the inputType
     */
    public int getInputType() {
        return inputType;
    }

    /**
     * @param inputType the inputType to set
     */
    public void setInputType(int inputType) {
        this.inputType = inputType;
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
     * @return the inputTypeDescription
     */
    public String getInputTypeDescription() {
        return inputTypeDescription;
    }

    /**
     * @param inputTypeDescription the inputTypeDescription to set
     */
    public void setInputTypeDescription(String inputTypeDescription) {
        this.inputTypeDescription = inputTypeDescription;
    }

    /**
     * @return the itemValue
     */
    public double getItemValue() {
        return itemValue;
    }

    /**
     * @param itemValue the itemValue to set
     */
    public void setItemValue(double itemValue) {
        this.itemValue = itemValue;
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

    public static class PayAccountGroup{
        private int accountId;
        private int groupId;

        /**
         * @return the accoountId
         */
        public int getAccountId() {
            return accountId;
        }

        /**
         * @param accountId the accoountId to set
         */
        public void setAccountId(int accountId) {
            this.accountId = accountId;
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
    }
}
