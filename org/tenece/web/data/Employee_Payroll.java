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
public class Employee_Payroll extends BaseData {

    public static class Attribute{

        private long employeeId;
        private int attributeId;
        private int fromPeriodId;
        private int toPeriodId;
        private double value_Amount;
        private String attributeName;

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
         * @return the attributeId
         */
        public int getAttributeId() {
            return attributeId;
        }

        /**
         * @param attributeId the attributeId to set
         */
        public void setAttributeId(int attributeId) {
            this.attributeId = attributeId;
        }

        /**
         * @return the fromPeriodId
         */
        public int getFromPeriodId() {
            return fromPeriodId;
        }

        /**
         * @param fromPeriodId the fromPeriodId to set
         */
        public void setFromPeriodId(int fromPeriodId) {
            this.fromPeriodId = fromPeriodId;
        }

        /**
         * @return the toPeriodId
         */
        public int getToPeriodId() {
            return toPeriodId;
        }

        /**
         * @param toPeriodId the toPeriodId to set
         */
        public void setToPeriodId(int toPeriodId) {
            this.toPeriodId = toPeriodId;
        }

        /**
         * @return the value_Amount
         */
        public double getValue_Amount() {
            return value_Amount;
        }

        /**
         * @param value_Amount the value_Amount to set
         */
        public void setValue_Amount(double value_Amount) {
            this.value_Amount = value_Amount;
        }

        /**
         * @return the attributeName
         */
        public String getAttributeName() {
            return attributeName;
        }

        /**
         * @param attributeName the attributeName to set
         */
        public void setAttributeName(String attributeName) {
            this.attributeName = attributeName;
        }

    }
    
    public static class PayItem{
        
        private long employeeId;
        private int accountId;
        private int fromPeriodId;
        private int toPeriodId;
        private double itemValue;

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
         * @return the fromPeriodId
         */
        public int getFromPeriodId() {
            return fromPeriodId;
        }

        /**
         * @param fromPeriodId the fromPeriodId to set
         */
        public void setFromPeriodId(int fromPeriodId) {
            this.fromPeriodId = fromPeriodId;
        }

        /**
         * @return the toPeriodId
         */
        public int getToPeriodId() {
            return toPeriodId;
        }

        /**
         * @param toPeriodId the toPeriodId to set
         */
        public void setToPeriodId(int toPeriodId) {
            this.toPeriodId = toPeriodId;
        }

        /**
         * @return the itemvalue
         */
        public double getItemValue() {
            return itemValue;
        }

        /**
         * @param itemvalue the itemvalue to set
         */
        public void setItemValue(double itemValue) {
            this.itemValue = itemValue;
        }
        
    }
}
