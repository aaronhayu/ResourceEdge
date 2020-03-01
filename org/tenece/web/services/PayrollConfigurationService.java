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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import org.tenece.api.excel.data.ExcelContent;
import org.tenece.hr.data.dao.PayrollDAO;
import org.tenece.hr.data.dao.PayrollConfigurationDAO;
import org.tenece.web.data.Payroll_AccountGroup;
import org.tenece.web.data.Payroll_Attribute;
import org.tenece.web.data.Payroll_InputType;
import org.tenece.web.data.Payroll_PayAccount;
import org.tenece.web.data.Payroll_Policy;
import org.tenece.web.data.Payroll_PayPeriod;
import org.tenece.web.data.Payroll_PayEvent;

/**
 * Tenece Professional Services Limited
 * @author amachree
 */
public class PayrollConfigurationService extends BaseService {

    private PayrollConfigurationDAO payrollConfigurationDAO;
    private PayrollDAO payrollDAO;

    /* ---------- Payroll Attribute ------------- */
    public List<Payroll_Attribute> findAllPayroll_Attributes(){
        return payrollConfigurationDAO.getAllPayrollAttribute();
    }
    public List<Payroll_Attribute> findAllPayroll_Attributes(String searchKey, String searchText){
        return payrollConfigurationDAO.getAllPayrollAttribute(searchKey, searchText);
    }
    public List<Payroll_Attribute> findAllPayroll_AttributesByCompany(String code){
        return payrollConfigurationDAO.getAllPayrollAttributeByCompany(code);
    }
    public List<Payroll_Attribute> findAllPayroll_AttributesByCompany(String code, String searchKey, String searchText){
        return payrollConfigurationDAO.getAllPayrollAttributeByCompany(code, searchKey, searchText);
    }

    public Payroll_Attribute findPayroll_AttributeByID(int attributeId){
        return payrollConfigurationDAO.getPayroll_AttributeById(attributeId);
    }
    public boolean updatePayrollAttribute(Payroll_Attribute attribute, int mode){
        try{
            int saved = 0;
            if(mode == PayrollConfigurationService.MODE_INSERT){
                saved = payrollConfigurationDAO.createNewPayroll_Attribute(attribute);
            }else if(mode == PayrollConfigurationService.MODE_UPDATE){
                saved = payrollConfigurationDAO.updatePayroll_Attribute(attribute);
            }
            if(saved == 0){
                throw new Exception("Unable to update");
            }
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public int deletePayroll_Attribute(int attributeId){
        return payrollConfigurationDAO.deletePayroll_Attribute(attributeId);
    }

    /* ---------- Payroll Policy --------------*/
    public List<Payroll_Policy> findAllPayroll_Policies(){
        return payrollConfigurationDAO.getAllPayroll_Policy();
    }
    public List<Payroll_Policy> findAllPayroll_PoliciesByCompany(String code){
        return payrollConfigurationDAO.getAllPayroll_PolicyByCompany(code);
    }
    public Payroll_Policy findPayroll_PolicyByID(int policyId){
        return payrollConfigurationDAO.getPayroll_PolicyById(policyId);
    }
    public boolean updatePayrollPolicy(Payroll_Policy policy, int mode){
        try{
            int saved = 0;
            if(mode == PayrollConfigurationService.MODE_INSERT){
                saved = payrollConfigurationDAO.createNewPayroll_Policy(policy);
            }else if(mode == PayrollConfigurationService.MODE_UPDATE){
                saved = payrollConfigurationDAO.updatePayroll_Policy(policy);
            }
            if(saved == 0){
                throw new Exception("Unable to update");
            }
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public int deletePayroll_Policy(int policyId){
        return payrollConfigurationDAO.deletePayroll_Policy(policyId);
    }

    /*-------------- Payroll Policy PayItem -------------*/
    public List<Payroll_Policy.PayItem> findAllPayroll_Policy_PayItemByID(int policyId){
        return payrollConfigurationDAO.getPayroll_PolicyPayItemById(policyId);
    }
    public int updatePayroll_PolicyPayItems(List<Payroll_Policy.PayItem> items){
        return payrollConfigurationDAO.createNewPayroll_PolicyPayItems(items);
    }
    public int deletePayroll_PolicyPayItem(int policyId, int accountId){
        return payrollConfigurationDAO.deletePayroll_PolicyPayItem(policyId, accountId);
    }

    /*----------- Payroll Policy Attribute -------*/
    public List<Payroll_Policy.PolicyAttribute> findAllPayroll_PolicyAttributesByPolicy(int policyId){
        return payrollConfigurationDAO.getAllPayroll_PolicyAttributes(policyId);
    }
    public List<Payroll_Policy.PolicyAttribute> findAllPayroll_PolicyAttributesByPolicyAndCompany(int policyId, String code){
        return payrollConfigurationDAO.getAllPayroll_PolicyAttributesByCompany(policyId, code);
    }

    public boolean createNewPayroll_PolicyAttribute(Payroll_Policy.PolicyAttribute record){
        int saved = payrollConfigurationDAO.createNewPayroll_PolicyAttribute(record.getPolicyId(), record.getAttributeId());
        if(saved == 0){ return false; }
        else{ return true; }
    }

    public int deletePayroll_PolicyAttribute(int policyId, int attributeId){
        return payrollConfigurationDAO.deletePayroll_PolicyAttribute(policyId, attributeId);
    }
    /* ---------- Payroll Account Group -------------- */

    public List<Payroll_AccountGroup> findAllPayroll_AccountGroup(){
        return payrollConfigurationDAO.getAllPayroll_AccountGroup();
    }
    public List<Payroll_AccountGroup> findAllPayroll_AccountGroupByCompany(String code){
        return payrollConfigurationDAO.getAllPayroll_AccountGroupByCompany(code);
    }
    public Payroll_AccountGroup findPayroll_AccountGroupByID(int id){
        return payrollConfigurationDAO.getPayroll_AccountGroupById(id);
    }
    public boolean updatePayroll_AccountGroup(Payroll_AccountGroup account, int mode){
        try{
            int saved = 0;
            if(mode == PayrollConfigurationService.MODE_INSERT){
                saved = payrollConfigurationDAO.createNewPayroll_AccountGroup(account);
            }else if(mode == PayrollConfigurationService.MODE_UPDATE){
                saved = payrollConfigurationDAO.updatePayroll_AccountGroup(account);
            }
            if(saved == 0){
                throw new Exception("Unable to update");
            }
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public int deletePayroll_AccountGroup(int id){
        return payrollConfigurationDAO.deletePayroll_AccountGroup(id);
    }

    /* ---------- Payroll Pay Account ------------ */

    public List<Payroll_PayAccount> findAllPayroll__PayAccount(){
        return payrollConfigurationDAO.getAllPayroll_PayAccount();
    }
    public List<Payroll_PayAccount> findAllPayroll__PayAccount(String searchKey, String searchValue){
        return payrollConfigurationDAO.getAllPayroll_PayAccount(searchKey, searchValue);
    }
    public List<Payroll_PayAccount> findAllPayroll__PayAccountByCompany(String code){
        return payrollConfigurationDAO.getAllPayroll_PayAccountByCompany(code);
    }
    public List<Payroll_PayAccount> findAllPayroll__PayAccountByCompany(String code, String searchKey, String searchValue){
        return payrollConfigurationDAO.getAllPayroll_PayAccountByCompany(code, searchKey, searchValue);
    }
    public Payroll_PayAccount findPayroll_PayAccountByID(int id){
        return payrollConfigurationDAO.getPayroll_PayAccountById(id);
    }
    public boolean updatePayroll_PayAccount(Payroll_PayAccount account, int mode){
        try{
            int saved = 0;
            if(mode == PayrollConfigurationService.MODE_INSERT){
                saved = payrollConfigurationDAO.createNewPayroll_PayAccount(account);
            }else if(mode == PayrollConfigurationService.MODE_UPDATE){
                saved = payrollConfigurationDAO.updatePayroll_PayAccount(account);
            }
            if(saved == 0){
                throw new Exception("Unable to update");
            }
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public int deletePayroll_PayAccount(int id){
        return payrollConfigurationDAO.deletePayroll_PayAccount(id);
    }

    /* ---------- Payroll Pay Account Group --------- */

//    public List<Payroll_PayAccount.PayAccountGroup> findAllPayroll_PayAccountGroupByAccountID(int accountid){
//        return payrollConfigurationDAO.getAllPayroll_PayAccountGroups(accountid);
//    }
//
//    public boolean createNewPayroll_PayAccountGroup(Payroll_PayAccount.PayAccountGroup record){
//        int saved = payrollConfigurationDAO.createNewPayroll_PolicyAttribute(record.getAccountId(), record.getGroupId());
//        if(saved == 0){ return false; }
//        else{ return true; }
//    }
//
//    public int deletePayroll_PayAccountGroup(int accountId, int groupId){
//        return payrollConfigurationDAO.deletePayroll_PayAccountGroup(accountId, groupId);
//    }

    /*----------- Payroll Input Type ---------------- */

    public List<Payroll_InputType> findAllPayroll_InputType(){
        return payrollConfigurationDAO.getAllPayroll_InputType();
    }

    public Payroll_InputType findPayroll_InputTypeById(int id){
        return payrollConfigurationDAO.getPayroll_InputTypeById(id);
    }

    /* _________ Pay Period ______________ */
    public List<Payroll_PayPeriod> findAllPayroll_PayPeriod(){
        return payrollConfigurationDAO.getAllPayroll_PayPeriod();
    }
    public List<Payroll_PayPeriod> findAllPayroll_PayPeriodByCompany(String code){
        return payrollConfigurationDAO.getAllPayroll_PayPeriodByCompany(code);
    }
    public Payroll_PayPeriod findPayroll_PayPeriodByID(int id){
        return payrollConfigurationDAO.getPayroll_PayPeriodById(id);
    }
    public Payroll_PayPeriod findActivePayroll_PayPeriod(){
        return payrollConfigurationDAO.getActivePayroll_PayPeriod();
    }
    public Payroll_PayPeriod findActivePayroll_PayPeriodByCompany(String code){
        return payrollConfigurationDAO.getActivePayroll_PayPeriodByCompany(code);
    }
    public Integer countPayroll_PayPeriodByCompany(String code){
        return payrollConfigurationDAO.countPeriodByCompany(code);
    }
    public boolean updatePayroll_PayPeriod(Payroll_PayPeriod period, int mode){
        try{
            int saved = 0;
            if(mode == PayrollConfigurationService.MODE_INSERT){
                saved = payrollConfigurationDAO.createNewPayroll_PayPeriod(period);
            }else if(mode == PayrollConfigurationService.MODE_UPDATE){
                saved = payrollConfigurationDAO.updatePayroll_PayPeriod(period);
            }
            if(saved == 0){
                throw new Exception("Unable to update");
            }
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public Payroll_PayPeriod.PayrollSummary findPayrollSummaryForPeriod (){
        return payrollConfigurationDAO.getPayroll_SummaryForPayPeriod();
    }

    public Payroll_PayPeriod.PayrollSummary findPayrollSummaryForPeriodByCompany(String code){
        return payrollConfigurationDAO.getPayroll_SummaryForPayPeriodByCompany(code);
    }

    public List<ExcelContent> findAllPaySummary_ForActivePeriod(boolean isHeaderRequired){
        return findAllPaySummary_ForActivePeriod(isHeaderRequired, null);
    }
    public List<ExcelContent> findAllPaySummary_ForActivePeriod(boolean isHeaderRequired, String companyCode){
        List<Payroll_PayPeriod.PaymentSummary> payments = new ArrayList<Payroll_PayPeriod.PaymentSummary>();
        if(companyCode == null){
            payments = payrollDAO.getAllPaymentSummary_ForActivePeriod();
        }else{
            payments = payrollDAO.getAllPaymentSummary_ForActivePeriodByCompany(companyCode);
        }

        List<ExcelContent> contents = new ArrayList<ExcelContent>();
        //determine and set header for excel file
        int setHeader = 0;
        if(isHeaderRequired){
            if(setHeader == 0){
                ExcelContent ct = new ExcelContent();
                
                ArrayList<Object> cellContents = new ArrayList<Object>();
                ArrayList<Integer> types = new ArrayList<Integer>();
                cellContents.add("Employee Name"); types.add(ExcelContent.CELL_TYPE_STRING);
                cellContents.add("Bank Name"); types.add(ExcelContent.CELL_TYPE_STRING);
                cellContents.add("Branch Name"); types.add(ExcelContent.CELL_TYPE_STRING);
                cellContents.add("Account Name"); types.add(ExcelContent.CELL_TYPE_STRING);
                cellContents.add("Account Number"); types.add(ExcelContent.CELL_TYPE_STRING);
                cellContents.add("Account Type"); types.add(ExcelContent.CELL_TYPE_STRING);
                cellContents.add("Earnings"); types.add(ExcelContent.CELL_TYPE_STRING);
                cellContents.add("Deductions"); types.add(ExcelContent.CELL_TYPE_STRING);
                cellContents.add("Others"); types.add(ExcelContent.CELL_TYPE_STRING);
                cellContents.add("Net Salary"); types.add(ExcelContent.CELL_TYPE_STRING);
                ct.setRowValues(cellContents);
                ct.setRowValueTypes(types);
                contents.add(ct);
                setHeader = 1;
            }
        }


        for(Payroll_PayPeriod.PaymentSummary payment : payments){

            ExcelContent ct = new ExcelContent();
            ArrayList<Object> cellContents = new ArrayList<Object>();
            ArrayList<Integer> types = new ArrayList<Integer>();
            
            cellContents.add(payment.getFirstName() + " " + payment.getLastName()); types.add(ExcelContent.CELL_TYPE_STRING);
            cellContents.add(payment.getBankName()); types.add(ExcelContent.CELL_TYPE_STRING);
            cellContents.add(payment.getBranch()); types.add(ExcelContent.CELL_TYPE_STRING);
            cellContents.add(payment.getAccountName()); types.add(ExcelContent.CELL_TYPE_STRING);
            cellContents.add(payment.getAccountNumber()); types.add(ExcelContent.CELL_TYPE_STRING);
            cellContents.add(payment.getAccountType()); types.add(ExcelContent.CELL_TYPE_STRING);

            cellContents.add(payment.getTotalEarning()); types.add(ExcelContent.CELL_TYPE_DECIMAL);
            cellContents.add(payment.getTotalDeduction()); types.add(ExcelContent.CELL_TYPE_DECIMAL);
            cellContents.add(payment.getTotalOthers()); types.add(ExcelContent.CELL_TYPE_DECIMAL);
            //DecimalFormat df = new DecimalFormat("#.##");

            cellContents.add(payment.getTotalEarning() + payment.getTotalDeduction()); types.add(ExcelContent.CELL_TYPE_DECIMAL);

            //cellContents.add("19"); types.add(ExcelContent.CELL_TYPE_NUMBER);
            //cellContents.add(new Date()); types.add(ExcelContent.CELL_TYPE_DATE);
            ct.setRowValues(cellContents);
            ct.setRowValueTypes(types);
            contents.add(ct);
        }
        return contents;
    }
    
    /**
     * @return the payrollConfigurationDAO
     */
    public PayrollConfigurationDAO getPayrollConfigurationDAO() {
        return payrollConfigurationDAO;
    }

    /**
     * @param payrollConfigurationDAO the payrollConfigurationDAO to set
     */
    public void setPayrollConfigurationDAO(PayrollConfigurationDAO payrollConfigurationDAO) {
        this.payrollConfigurationDAO = payrollConfigurationDAO;
    }

    /**
     * @return the payrollDAO
     */
    public PayrollDAO getPayrollDAO() {
        return payrollDAO;
    }

    /**
     * @param payrollDAO the payrollDAO to set
     */
    public void setPayrollDAO(PayrollDAO payrollDAO) {
        this.payrollDAO = payrollDAO;
    }
}
