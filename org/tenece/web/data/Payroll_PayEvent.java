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
public class Payroll_PayEvent {
    private long employeeId;
    private int periodId;
    private int accountId;
    private double itemValue;
    private String narration;
    private Date startDate;
    private Date endDate;
    private double amount;
    private double unitPrice;
    private int derived;
    private int quantity;
    private int payable;
    private Date registeredDate;

    private String payableDescription;
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
     * @return the periodId
     */
    public int getPeriodId() {
        return periodId;
    }

    /**
     * @param periodId the periodId to set
     */
    public void setPeriodId(int periodId) {
        this.periodId = periodId;
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
     * @return the narration
     */
    public String getNarration() {
        return narration;
    }

    /**
     * @param narration the narration to set
     */
    public void setNarration(String narration) {
        this.narration = narration;
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
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * @return the unitPrice
     */
    public double getUnitPrice() {
        return unitPrice;
    }

    /**
     * @param unitPrice the unitPrice to set
     */
    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * @return the derived
     */
    public int getDerived() {
        return derived;
    }

    /**
     * @param derived the derived to set
     */
    public void setDerived(int derived) {
        this.derived = derived;
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the payable
     */
    public int getPayable() {
        return payable;
    }

    /**
     * @param payable the payable to set
     */
    public void setPayable(int payable) {
        this.payable = payable;
    }

    /**
     * @return the registeredDate
     */
    public Date getRegisteredDate() {
        return registeredDate;
    }

    /**
     * @param registeredDate the registeredDate to set
     */
    public void setRegisteredDate(Date registeredDate) {
        this.registeredDate = registeredDate;
    }

    /**
     * @return the accountId
     */
    public int getAccountId() {
        return accountId;
    }

    /**
     * @param accountId the accountId to set
     */
    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    /**
     * @return the payableDescription
     */
    public String getPayableDescription() {
        return payableDescription;
    }

    /**
     * @param payableDescription the payableDescription to set
     */
    public void setPayableDescription(String payableDescription) {
        this.payableDescription = payableDescription;
    }

}
