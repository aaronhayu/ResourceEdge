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

package org.tenece.web.services;

import java.util.List;
import org.tenece.hr.data.dao.EmployeePayrollDAO;
import org.tenece.web.data.Employee_Payroll;

/**
 * Tenece Professional Services Limited
 * @author amachree
 */
public class EmployeePayrollService extends BaseService {
    private EmployeePayrollDAO employeePayrollDAO;

    /*-------------- Payroll Policy PayItem -------------*/
    public List<Employee_Payroll.PayItem> findAllPayroll_PayItemByID(long employeeId){
        return employeePayrollDAO.getPayroll_PayItemById(employeeId);
    }
    public int updatePayroll_PayItems(List<Employee_Payroll.PayItem> items){
        return employeePayrollDAO.createNewPayroll_PayItems(items);
    }
    public int deletePayroll_PayItem(long employeeId, int accountId){
        return employeePayrollDAO.deletePayroll_PayItem(employeeId, accountId);
    }

    /*-------------- Payroll EMPLOYEE ATTRIBUTE -------------*/
    public List<Employee_Payroll.Attribute> findAllPayroll_AttributeByID(long employeeId){
        return employeePayrollDAO.getPayroll_AttributeById(employeeId);
    }
    public int updatePayroll_Attributes(List<Employee_Payroll.Attribute> attributes){
        return employeePayrollDAO.createNewPayroll_Attributes(attributes);
    }
    public int deletePayroll_Attribute(long employeeId, int attributeId){
        return employeePayrollDAO.deletePayroll_Attribute(employeeId, attributeId);
    }

    /**
     * @return the employeePayrollDAO
     */
    public EmployeePayrollDAO getEmployeePayrollDAO() {
        return employeePayrollDAO;
    }

    /**
     * @param employeePayrollDAO the employeePayrollDAO to set
     */
    public void setEmployeePayrollDAO(EmployeePayrollDAO employeePayrollDAO) {
        this.employeePayrollDAO = employeePayrollDAO;
    }
}
