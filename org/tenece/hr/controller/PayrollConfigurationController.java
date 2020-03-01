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

package org.tenece.hr.controller;

import java.io.FileOutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.tenece.web.common.BaseController;
import org.tenece.web.data.Payroll_AccountGroup;
import org.tenece.web.data.Payroll_Attribute;
import org.tenece.web.data.Payroll_PayAccount;
import org.tenece.web.data.Payroll_Policy;
import org.tenece.web.data.Payroll_PayPeriod;
import org.tenece.web.services.PayrollConfigurationService;
import org.tenece.hr.controller.view.WidgetListExcelView;

import org.tenece.web.common.ConfigReader;
import org.tenece.web.common.DateUtility;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.tenece.api.excel.ExcelWriter;
import org.tenece.api.excel.data.ExcelContent;

/**
 * Tenece Professional Services Limited
 * @author amachree
 */
public class PayrollConfigurationController extends BaseController{
    private PayrollConfigurationService payrollConfigurationService;

    public ModelAndView viewAllPayroll_Attribute(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("payroll/payroll_attribute_view");

        //do search here...
        String cbSearch = request.getParameter("cbSearch");
        String txtSearch = request.getParameter("txtSearch");

        String companyCode = this.getActiveEmployeeCompanyCode(request);

        //get list of employee
        List<Payroll_Attribute> list = null;
        if(cbSearch == null || txtSearch == null){
            list = payrollConfigurationService.findAllPayroll_AttributesByCompany(companyCode);
        }else{
            list = payrollConfigurationService.findAllPayroll_AttributesByCompany(companyCode, cbSearch, txtSearch);
        }

        view.addObject("result", list);
        return view;
    }

    public ModelAndView editPayroll_Attribute(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("payroll/payroll_attribute_edit");
        String param = request.getParameter("idx");
        if(param == null){
            return viewAllPayroll_Attribute(request, response);
        }
        int id = Integer.parseInt(param);
        Payroll_Attribute attribute = payrollConfigurationService.findPayroll_AttributeByID(id);

        //get company info
        view = getAndAppendCompanyToView(view, request);
        
        view.addObject("attribute", attribute);
        return view;
    }

    public ModelAndView newPayrollAttributeRecord(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("payroll/payroll_attribute_edit");

        view.addObject("attribute", new Payroll_Attribute());

        //get company info
        view = getAndAppendCompanyToView(view, request);
        
        return view;
    }

    public ModelAndView deletePayroll_Attribute(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("");
        List<Integer> ids = new ArrayList<Integer>();
        //get mode of request
        String mode = request.getParameter("mode");
        //check if mode is for more than one record
        //get mode, if mode is equals 1, then process for multiple
        if(mode != null && mode.trim().equals("1")){
            String[] args = request.getParameterValues("_chk");
            for(String id : args){
                ids.add(Integer.parseInt(id));
            }
        }else{//then it is zero
            ids.add(Integer.parseInt(request.getParameter("id")));
        }
        for(int id : ids){
            payrollConfigurationService.deletePayroll_Attribute(id);
        }
        
       //delete
        return viewAllPayroll_Attribute(request, response);
    }

    public ModelAndView processPayrollAttributeRequest(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("success");
        String operation = request.getParameter("txtMode");
        //operation mode: N= New, E=Edit
        if(operation.trim().equals("N")){
            Payroll_Attribute attribute = new Payroll_Attribute();
            String desc = request.getParameter("txtDesc");
            String code = request.getParameter("txtCode");
            String companyCode = request.getParameter("cbCompany");

            attribute.setDescription(desc);
            attribute.setCode(code.toLowerCase());
            attribute.setCompanyCode(companyCode);

            boolean saved = payrollConfigurationService.updatePayrollAttribute(attribute, PayrollConfigurationService.MODE_INSERT);
            if(saved == false){
                view = new ModelAndView("error");
                view.addObject("message", "Can not save payroll attribute description. Please try again later");
                return view;
            }

            view = viewAllPayroll_Attribute(request, response);
            view.addObject("message", "Record Saved Successfully.");

        }else if(operation.trim().equals("E")){
            Payroll_Attribute attribute = new Payroll_Attribute();
            String desc = request.getParameter("txtDesc");
            String code = request.getParameter("txtCode");
            String companyCode = request.getParameter("cbCompany");

            String strId = request.getParameter("txtId");

            if(strId == null || strId.trim().equals("")){
                view = new ModelAndView("error");
                view.addObject("message","Unable to perform update. Please try again later.");
                return view;
            }
            attribute.setId(Integer.parseInt(strId));
            attribute.setDescription(desc);
            attribute.setCode(code);
            attribute.setCompanyCode(companyCode);
            
            boolean saved = payrollConfigurationService.updatePayrollAttribute(attribute, PayrollConfigurationService.MODE_UPDATE);
            if(saved == false){
                view = new ModelAndView("error");
                view.addObject("message", "Can not save payroll attribute information. Please try again later");
                return view;
            }

            view = viewAllPayroll_Attribute(request, response);
            view.addObject("message", "Record Saved Successfully.");
        }
        return view;
    }


    /* =================== POLICY ========================*/
    public ModelAndView viewAllPayroll_Policy(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("payroll/payroll_policy_view");

        //do search here...
        String cbSearch = request.getParameter("cbSearch");
        String txtSearch = request.getParameter("txtSearch");

        String companyCode = this.getActiveEmployeeCompanyCode(request);

        //get list of employee
        List<Payroll_Policy> list = null;
        if(cbSearch == null || txtSearch == null){
            list = payrollConfigurationService.findAllPayroll_PoliciesByCompany(companyCode);
        }else{
            //list = setupService.findAllDepartments(cbSearch, txtSearch);
        }

        view.addObject("result", list);
        return view;
    }

    public ModelAndView editPayroll_Policy(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("payroll/payroll_policy_edit");
        String param = request.getParameter("idx");
        if(param == null){
            return viewAllPayroll_Policy(request, response);
        }
        int id = Integer.parseInt(param);
//        Payroll_Policy policy = payrollConfigurationService.findPayroll_PolicyByID(id);
//
//        view.addObject("policy", policy);
        view = editPayroll_Policy(id, request);
        return view;
    }
    public ModelAndView editPayroll_Policy(int policyId, HttpServletRequest request){
        ModelAndView view = new ModelAndView("payroll/payroll_policy_edit");
        
        Payroll_Policy policy = payrollConfigurationService.findPayroll_PolicyByID(policyId);

        //get company info
        view = getAndAppendCompanyToView(view, request);

        view.addObject("policy", policy);
        return view;
    }

    public ModelAndView newPayrollPolicyRecord(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("payroll/payroll_policy_edit");

        //get company info
        view = getAndAppendCompanyToView(view, request);

        view.addObject("policy", new Payroll_Policy());
        return view;
    }

    public ModelAndView deletePayroll_Policy(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("");
        List<Integer> ids = new ArrayList<Integer>();
        //get mode of request
        String mode = request.getParameter("mode");
        //check if mode is for more than one record
        //get mode, if mode is equals 1, then process for multiple
        if(mode != null && mode.trim().equals("1")){
            String[] args = request.getParameterValues("_chk");
            for(String id : args){
                ids.add(Integer.parseInt(id));
            }
        }else{//then it is zero
            ids.add(Integer.parseInt(request.getParameter("id")));
        }
        for(int id : ids){
            payrollConfigurationService.deletePayroll_Policy(id);
        }
       //delete
        return viewAllPayroll_Policy(request, response);
    }
    public ModelAndView previewPayroll_Policy(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("payroll/payroll_policy_setting");

        try{
            String param = request.getParameter("idx");
            if(param == null){
                param = "0";
            }
            //check if the value is integer
            int id = Integer.parseInt(param);
            view.addObject("idx", id);

        }catch(Exception er){
            return viewAllPayroll_Policy(request, response);
        }
        //return the preview view
        return view;
    }
    //editPayItemsPayroll_Policy
    public ModelAndView editPayItemsPayroll_Policy(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("payroll/payroll_policy_payitem_edit");

        String param = request.getParameter("idx");
        if(param == null){
            param = "0";
        }
        int id = Integer.parseInt(param);

        //get  active company code
        String companyCode = this.getActiveEmployeeCompanyCode(request);

        view.addObject("policyid", param);
        view.addObject("items", payrollConfigurationService.findAllPayroll_Policy_PayItemByID(id));
        view.addObject("newItem", new Payroll_Policy.PayItem());
        view.addObject("accountList", payrollConfigurationService.findAllPayroll__PayAccountByCompany(companyCode));
        return view;
        
    }

    public ModelAndView editPayItemsPayroll_Policy(int id, HttpServletRequest request){
        ModelAndView view = new ModelAndView("payroll/payroll_policy_payitem_edit");

        //get  active company code
        String companyCode = this.getActiveEmployeeCompanyCode(request);

        view.addObject("policyid", String.valueOf(id));
        view.addObject("items", payrollConfigurationService.findAllPayroll_Policy_PayItemByID(id));
        view.addObject("newItem", new Payroll_Policy.PayItem());
        view.addObject("accountList", payrollConfigurationService.findAllPayroll__PayAccountByCompany(companyCode));
        return view;

    }

    public ModelAndView editAttributesPayroll_Policy(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("payroll/payroll_policy_attribute_edit");

        String param = request.getParameter("idx");
        if(param == null){
            param = "0";
        }
        //get active company info
        String companyCode = this.getActiveEmployeeCompanyCode(request);

        //check if the value is integer
        int id = Integer.parseInt(param);
        view.addObject("policyId", id);
        view.addObject("result", payrollConfigurationService.findAllPayroll_PolicyAttributesByPolicyAndCompany(id, companyCode));
        view.addObject("attributeList", payrollConfigurationService.findAllPayroll_AttributesByCompany(companyCode));
        return view;
    }
    public ModelAndView editAttributesPayroll_Policy(int id, HttpServletRequest request){
        ModelAndView view = new ModelAndView("payroll/payroll_policy_attribute_edit");

        //get active company info
        String companyCode = this.getActiveEmployeeCompanyCode(request);

        view.addObject("policyId", id);
        view.addObject("result", payrollConfigurationService.findAllPayroll_PolicyAttributesByPolicyAndCompany(id, companyCode));
        view.addObject("attributeList", payrollConfigurationService.findAllPayroll_AttributesByCompany(companyCode));
        return view;
    }

    public ModelAndView saveAttributesPayroll_Policy(HttpServletRequest request, HttpServletResponse response){
        String strAttribute = request.getParameter("cbAttribute");
        String strPolicyId = request.getParameter("txtId");

        if(strAttribute == null || strAttribute.trim().equals("") ||strAttribute.trim().equals("0")){
            ModelAndView view = editAttributesPayroll_Policy(request, response);
            view.addObject("message", "System Error Saving Attribute. Ensure you select one attribute before saving.");
            return view;
        }
        Payroll_Policy.PolicyAttribute policyAttribute = new Payroll_Policy.PolicyAttribute();
        policyAttribute.setAttributeId(Integer.parseInt(strAttribute));
        policyAttribute.setPolicyId(Integer.parseInt(strPolicyId));
        boolean saved = payrollConfigurationService.createNewPayroll_PolicyAttribute(policyAttribute);

        ModelAndView view = editAttributesPayroll_Policy(Integer.parseInt(strPolicyId), request);
        if(saved == true){
            return view;
        }else{
            view.addObject("message", "System Error Saving Policy Attribute.");
            return view;
        }

    }

    public ModelAndView deleteAttributesPayroll_Policy(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("");
        List<Integer> ids = new ArrayList<Integer>();
        String strPolicyId = "";
        //get policy id
        strPolicyId = request.getParameter("pidx");
        try{
            //get mode of request
            String mode = request.getParameter("mode");

            if(strPolicyId == null || strPolicyId.trim().equals("")){
                view = editAttributesPayroll_Policy(request, response);
                view.addObject("message", "Unable to locate policy based on indentifier.");
                return view;
            }
            //check if mode is for more than one record
            //get mode, if mode is equals 1, then process for multiple
            if(mode != null && mode.trim().equals("1")){
                String[] args = request.getParameterValues("_chk");
                for(String id : args){
                    ids.add(Integer.parseInt(id));
                }
            }else{//then it is zero
                String id = request.getParameter("id");
                ids.add(Integer.parseInt(id));
            }
            for(int id : ids){
                payrollConfigurationService.deletePayroll_PolicyAttribute(Integer.parseInt(strPolicyId), id);
            }
           //after delete, reload list back
            return editAttributesPayroll_Policy(Integer.parseInt(strPolicyId), request);
        }catch(Exception e){
            view = editAttributesPayroll_Policy(Integer.parseInt(strPolicyId), request);
            view.addObject("message", "Unable to delete. System Error occured.");
            return view;
        }
    }

    public ModelAndView savePayItemsPayroll_Policy(HttpServletRequest request, HttpServletResponse response){
        //get old records
        String[] accounts = request.getParameterValues("cbAccount");
        String[] amounts = request.getParameterValues("txtAmount");
        String strPolicyId = request.getParameter("txtId");

        //check again for bug
        if(strPolicyId == null || strPolicyId.trim().equals("")){
            return editPayItemsPayroll_Policy(Integer.parseInt(strPolicyId), request);
        }

        //get current period
        Payroll_PayPeriod period = payrollConfigurationService.findActivePayroll_PayPeriod();
        
        List<Payroll_Policy.PayItem> items = new ArrayList<Payroll_Policy.PayItem>();
        //check if there was any previous record
        if(accounts != null){
            for(int i = 0; i < accounts.length; i++){

                Payroll_Policy.PayItem item = new Payroll_Policy.PayItem();
                item.setAccountId(Integer.parseInt(accounts[i]));
                item.setAmount(Double.parseDouble(amounts[i]));
                item.setPolicyId(Integer.parseInt(strPolicyId));
                item.setFromPeriodId(period.getId());
                items.add(item);
            }
        }//
        //check if new record was specified
        String newAccount = request.getParameter("cbNewAccount");
        String newAmount = request.getParameter("txtNewAmount");

        if((newAccount.trim().equals("") || newAccount.trim().equals("0"))
                && (newAmount.trim().equals("") || newAmount.trim().equals("0"))){
            //nothing was selected
        }else{
            Payroll_Policy.PayItem item = new Payroll_Policy.PayItem();
            item.setAccountId(Integer.parseInt(newAccount));
            item.setAmount(Double.parseDouble(newAmount));
            item.setPolicyId(Integer.parseInt(strPolicyId));
            item.setFromPeriodId(period.getId());
            items.add(item);
        }

        int saved = payrollConfigurationService.updatePayroll_PolicyPayItems(items);
        if(saved > 0){
            ModelAndView view = editPayItemsPayroll_Policy(Integer.parseInt(strPolicyId), request);
            view.addObject("message", "Policy pay items saved successfully.");
            return view;
        }else{
            ModelAndView view = editPayItemsPayroll_Policy(Integer.parseInt(strPolicyId), request);
            view.addObject("message", "Unable to save pay items for policy.");
            return view;
        }

    }

    public ModelAndView deletePayItemsPayroll_Policy(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("");
        List<Integer> ids = new ArrayList<Integer>();
        String strPolicyId = "";
        //get policy id
        strPolicyId = request.getParameter("pidx");
        try{
            //get mode of request
            String mode = request.getParameter("mode");

            if(strPolicyId == null || strPolicyId.trim().equals("")){
                view = editPayItemsPayroll_Policy(request, response);
                view.addObject("message", "Unable to locate policy based on indentifier.");
                return view;
            }
            //check if mode is for more than one record
            //get mode, if mode is equals 1, then process for multiple
            if(mode != null && mode.trim().equals("1")){
                String[] args = request.getParameterValues("_chk");
                for(String id : args){
                    ids.add(Integer.parseInt(id));
                }
            }else{//then it is zero
                String id = request.getParameter("id");
                ids.add(Integer.parseInt(id));
            }
            for(int id : ids){
                payrollConfigurationService.deletePayroll_PolicyPayItem(Integer.parseInt(strPolicyId), id);
            }
           //after delete, reload list back
            return editPayItemsPayroll_Policy(Integer.parseInt(strPolicyId), request);
        }catch(Exception e){
            view = editPayItemsPayroll_Policy(Integer.parseInt(strPolicyId), request);
            view.addObject("message", "Unable to delete. System Error occured.");
            return view;
        }
    }

    public ModelAndView processPayrollPolicyRequest(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("success");
        String operation = request.getParameter("txtMode");
        //operation mode: N= New, E=Edit
        if(operation.trim().equals("N")){
            Payroll_Policy policy = new Payroll_Policy();
            String desc = request.getParameter("txtDesc");
            String companyCode = request.getParameter("cbCompany");

            policy.setDescription(desc);
            policy.setCompanyCode(companyCode);

            boolean saved = payrollConfigurationService.updatePayrollPolicy(policy, PayrollConfigurationService.MODE_INSERT);
            if(saved == false){
                view = new ModelAndView("error");
                view.addObject("message", "Can not save payroll policy description. Please try again later");
                return view;
            }

            view = viewAllPayroll_Policy(request, response);
            view.addObject("message", "Record Saved Successfully.");

        }else if(operation.trim().equals("E")){
            Payroll_Policy policy = new Payroll_Policy();
            String desc = request.getParameter("txtDesc");
            String companyCode = request.getParameter("cbCompany");

            String strId = request.getParameter("txtId");

            if(strId == null || strId.trim().equals("")){
                view = new ModelAndView("error");
                view.addObject("message","Unable to perform update. Please try again later.");
                return view;
            }
            policy.setId(Integer.parseInt(strId));
            policy.setDescription(desc);
            policy.setCompanyCode(companyCode);

            boolean saved = payrollConfigurationService.updatePayrollPolicy(policy, PayrollConfigurationService.MODE_UPDATE);
            if(saved == false){
                view = new ModelAndView("error");
                view.addObject("message", "Can not save payroll policy information. Please try again later");
                return view;
            }

            view = editPayroll_Policy(policy.getId(), request);
            view.addObject("message", "Policy Saved Successfully.");
        }
        return view;
    }

    /* =================== Account Group ========================*/
    public ModelAndView viewAllPayroll_AccountGroup(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("payroll/payroll_accountgroup_view");

        //do search here...
        String cbSearch = request.getParameter("cbSearch");
        String txtSearch = request.getParameter("txtSearch");

        String companyCode = this.getActiveEmployeeCompanyCode(request);

        //get list of employee
        List<Payroll_AccountGroup> list = null;
        if(cbSearch == null || txtSearch == null){
            list = payrollConfigurationService.findAllPayroll_AccountGroupByCompany(companyCode);
        }else{
            //list = setupService.findAllDepartments(cbSearch, txtSearch);
        }

        view.addObject("result", list);
        return view;
    }

    public ModelAndView editPayroll_AccountGroup(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("payroll/payroll_accountgroup_edit");
        String param = request.getParameter("idx");
        if(param == null){
            return viewAllPayroll_AccountGroup(request, response);
        }
        //get company info
        view = getAndAppendCompanyToView(view, request);
        
        int id = Integer.parseInt(param);
        Payroll_AccountGroup group = payrollConfigurationService.findPayroll_AccountGroupByID(id);

        view.addObject("group", group);
        return view;
    }

    public ModelAndView newPayrollAccountGroupRecord(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("payroll/payroll_accountgroup_edit");

        //get company info
        view = getAndAppendCompanyToView(view, request);
        
        view.addObject("group", new Payroll_AccountGroup());
        return view;
    }

    public ModelAndView deletePayroll_AccountGroup(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("");
        List<Integer> ids = new ArrayList<Integer>();
        //get mode of request
        String mode = request.getParameter("mode");
        //check if mode is for more than one record
        //get mode, if mode is equals 1, then process for multiple
        if(mode != null && mode.trim().equals("1")){
            String[] args = request.getParameterValues("_chk");
            for(String id : args){
                ids.add(Integer.parseInt(id));
            }
        }else{//then it is zero
            ids.add(Integer.parseInt(request.getParameter("id")));
        }
        for(int id : ids){
            payrollConfigurationService.deletePayroll_AccountGroup(id);
        }
       //delete
        return viewAllPayroll_AccountGroup(request, response);
    }

    public ModelAndView processPayrollAccountGroupRequest(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("success");
        String operation = request.getParameter("txtMode");

        //operation mode: N= New, E=Edit
        if(operation.trim().equals("N")){
            Payroll_AccountGroup group = new Payroll_AccountGroup();
            String name = request.getParameter("txtName");
            String desc = request.getParameter("txtDesc");
            String strShowInReport = request.getParameter("chkReport");
            String strShowInSelector = request.getParameter("chkSelector");
            String companyCode = request.getParameter("cbCompany");

            if(strShowInReport == null){ strShowInReport = "0"; }
            if(strShowInSelector == null){ strShowInSelector = "0"; }

            group.setName(name.toLowerCase());
            group.setDescription(desc);
            group.setReportShow(Integer.parseInt(strShowInReport));
            group.setSelectorShow(Integer.parseInt(strShowInSelector));
            group.setCompanyCode(companyCode);

            boolean saved = payrollConfigurationService.updatePayroll_AccountGroup(group, PayrollConfigurationService.MODE_INSERT);
            if(saved == false){
                view = new ModelAndView("error");
                view.addObject("message", "Can not save payroll policy description. Please try again later");
                return view;
            }

            view = viewAllPayroll_AccountGroup(request, response);
            view.addObject("message", "Record Saved Successfully.");

        }else if(operation.trim().equals("E")){
            Payroll_AccountGroup group = new Payroll_AccountGroup();
            String name = request.getParameter("txtName");
            String desc = request.getParameter("txtDesc");
            String strShowInReport = request.getParameter("chkReport");
            String strShowInSelector = request.getParameter("chkSelector");
            String companyCode = request.getParameter("cbCompany");

            if(strShowInReport == null){ strShowInReport = "0"; }
            if(strShowInSelector == null){ strShowInSelector = "0"; }

            String strId = request.getParameter("txtId");

            if(strId == null || strId.trim().equals("")){
                view = new ModelAndView("error");
                view.addObject("message","Unable to perform update. Please try again later.");
                return view;
            }
            group.setId(Integer.parseInt(strId));
            group.setName(name);
            group.setDescription(desc);
            group.setReportShow(Integer.parseInt(strShowInReport));
            group.setSelectorShow(Integer.parseInt(strShowInSelector));
            group.setCompanyCode(companyCode);

            boolean saved = payrollConfigurationService.updatePayroll_AccountGroup(group, PayrollConfigurationService.MODE_UPDATE);
            if(saved == false){
                view = new ModelAndView("error");
                view.addObject("message", "Can not save payroll policy information. Please try again later");
                return view;
            }

            view = viewAllPayroll_AccountGroup(request, response);
            view.addObject("message", "Record Saved Successfully.");
        }
        return view;
    }


    /* =================== Pay Account ========================*/
    public ModelAndView viewAllPayroll_PayAccount(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("payroll/payroll_payaccount_view");

        //do search here...
        String cbSearch = request.getParameter("cbSearch");
        String txtSearch = request.getParameter("txtSearch");

        String companyCode = this.getActiveEmployeeCompanyCode(request);

        //get list of employee
        List<Payroll_PayAccount> list = null;
        if(cbSearch == null || txtSearch == null){
            list = payrollConfigurationService.findAllPayroll__PayAccountByCompany(companyCode);
        }else{
            list = payrollConfigurationService.findAllPayroll__PayAccountByCompany(companyCode, cbSearch, txtSearch);
            //list = setupService.findAllDepartments(cbSearch, txtSearch);
        }

        view.addObject("result", list);
        return view;
    }

    public ModelAndView editPayroll_PayAccount(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("payroll/payroll_payaccount_edit");
        String param = request.getParameter("idx");
        if(param == null){
            return viewAllPayroll_PayAccount(request, response);
        }

        String companyCode = this.getActiveEmployeeCompanyCode(request);
        view = this.getAndAppendCompanyToView(view, request);

        int id = Integer.parseInt(param);
        Payroll_PayAccount group = payrollConfigurationService.findPayroll_PayAccountByID(id);
        view.addObject("account", group);
        view.addObject("inputTypeList", payrollConfigurationService.findAllPayroll_InputType());
        view.addObject("groupList", payrollConfigurationService.findAllPayroll_AccountGroupByCompany(companyCode));

        return view;
    }

    public ModelAndView newPayrollPayAccountRecord(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("payroll/payroll_payaccount_edit");

        String companyCode = this.getActiveEmployeeCompanyCode(request);
        view = this.getAndAppendCompanyToView(view, request);

        view.addObject("account", new Payroll_PayAccount());
        view.addObject("inputTypeList", payrollConfigurationService.findAllPayroll_InputType());
        view.addObject("groupList", payrollConfigurationService.findAllPayroll_AccountGroupByCompany(companyCode));
        return view;
    }

    public ModelAndView deletePayroll_PayAccount(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("");
        List<Integer> ids = new ArrayList<Integer>();
        //get mode of request
        String mode = request.getParameter("mode");
        //check if mode is for more than one record
        //get mode, if mode is equals 1, then process for multiple
        if(mode != null && mode.trim().equals("1")){
            String[] args = request.getParameterValues("_chk");
            for(String id : args){
                ids.add(Integer.parseInt(id));
            }
        }else{//then it is zero
            ids.add(Integer.parseInt(request.getParameter("id")));
        }
        for(int id : ids){
            payrollConfigurationService.deletePayroll_PayAccount(id);
        }
       //delete
        return viewAllPayroll_PayAccount(request, response);
    }

    public ModelAndView processPayrollPayAccountRequest(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("success");
        String operation = request.getParameter("txtMode");

        //operation mode: N= New, E=Edit
        if(operation.trim().equals("N")){
            Payroll_PayAccount account = new Payroll_PayAccount();
            String formula = request.getParameter("txtFormula");
            String desc = request.getParameter("txtDesc");
            String strInputType = request.getParameter("cbInput");
            String strSeq = request.getParameter("txtSeq");
            String companyCode = request.getParameter("cbCompany");

            String strGroupId = request.getParameter("cbGroup");

            account.setFormula(formula.toLowerCase());
            account.setDescription(desc);
            account.setInputType(Integer.parseInt(strInputType));
            account.setCalculationSequence(Integer.parseInt(strSeq));
            account.setGroupId(Integer.parseInt(strGroupId));
            account.setCompanyCode(companyCode);

            boolean saved = payrollConfigurationService.updatePayroll_PayAccount(account, PayrollConfigurationService.MODE_INSERT);
            if(saved == false){
                view = new ModelAndView("error");
                view.addObject("message", "Can not save payroll policy description. Please try again later");
                return view;
            }

            view = viewAllPayroll_PayAccount(request, response);
            view.addObject("message", "Record Saved Successfully.");

        }else if(operation.trim().equals("E")){
            Payroll_PayAccount account = new Payroll_PayAccount();
            String formula = request.getParameter("txtFormula");
            String desc = request.getParameter("txtDesc");
            String strInputType = request.getParameter("cbInput");
            String strSeq = request.getParameter("txtSeq");
            String strGroupId = request.getParameter("cbGroup");
            String companyCode = request.getParameter("cbCompany");

            String strId = request.getParameter("txtId");

            if(strId == null || strId.trim().equals("")){
                view = new ModelAndView("error");
                view.addObject("message","Unable to perform update. Please try again later.");
                return view;
            }
            account.setId(Integer.parseInt(strId));
            account.setFormula(formula);
            account.setDescription(desc);
            account.setInputType(Integer.parseInt(strInputType));
            account.setCalculationSequence(Integer.parseInt(strSeq));
            account.setGroupId(Integer.parseInt(strGroupId));
            account.setCompanyCode(companyCode);

            boolean saved = payrollConfigurationService.updatePayroll_PayAccount(account, PayrollConfigurationService.MODE_UPDATE);
            if(saved == false){
                view = new ModelAndView("error");
                view.addObject("message", "Can not save payroll policy information. Please try again later");
                return view;
            }

            view = viewAllPayroll_PayAccount(request, response);
            view.addObject("message", "Record Saved Successfully.");
        }
        return view;
    }

    /* ____________________ Payroll PayPeriod ____________________________*/
    public ModelAndView viewAllPayroll_PayPeriod(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("payroll/payroll_payperiod_view");

        //do search here...
        String cbSearch = request.getParameter("cbSearch");
        String txtSearch = request.getParameter("txtSearch");

        String companyCode = this.getActiveEmployeeCompanyCode(request);
        
        //get list of employee
        List<Payroll_PayPeriod> list = null;
        if(cbSearch == null || txtSearch == null){
            list = payrollConfigurationService.findAllPayroll_PayPeriodByCompany(companyCode);
        }else{
            //list = setupService.findAllDepartments(cbSearch, txtSearch);
        }

        view.addObject("result", list);
        view.addObject("companyCode", companyCode);
        return view;
    }

    public ModelAndView editPayroll_PayPeriod(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("payroll/payroll_payperiod_edit");
        String param = request.getParameter("idx");
        if(param == null){
            return viewAllPayroll_Attribute(request, response);
        }
        int id = Integer.parseInt(param);
        Payroll_PayPeriod period = payrollConfigurationService.findPayroll_PayPeriodByID(id);

        view = this.getAndAppendCompanyToView(view, request);
        view.addObject("period", period);
        return view;
    }

    public ModelAndView newPayrollPayPeriodRecord(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("payroll/payroll_payperiod_new");

        String companyCode = this.getActiveEmployeeCompanyCode(request);

        int numberOfPeriods = payrollConfigurationService.countPayroll_PayPeriodByCompany(companyCode);

        Payroll_PayPeriod activePeriod = payrollConfigurationService.findActivePayroll_PayPeriodByCompany(companyCode);
        //check and suggest a start and end time for the next period
        Payroll_PayPeriod period = new Payroll_PayPeriod();
        Calendar cal = Calendar.getInstance();
        if(activePeriod.getStartTime() == null){
            cal.setTime(new Date());
        }else{
            cal.setTime(activePeriod.getStartTime());
            cal.add(Calendar.MONTH, 1);
        }
        period.setStartTime(cal.getTime());

        //suggest end time
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        period.setEndTime(cal.getTime());

        view = this.getAndAppendCompanyToView(view, request);
        
        view.addObject("period", period);
        view.addObject("activePeriod", activePeriod);
        view.addObject("periodCount", numberOfPeriods);
        return view;
    }

    public ModelAndView editActivePayroll_PayPeriod(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("payroll/payroll_payperiod_active");

        String companyCode = this.getActiveEmployeeCompanyCode(request);
        //get the number of employee with valid policy and total number of employees on the system

        Payroll_PayPeriod period = payrollConfigurationService.findActivePayroll_PayPeriodByCompany(companyCode);
        view.addObject("period", period);
        view.addObject("summary", payrollConfigurationService.findPayrollSummaryForPeriodByCompany(companyCode));

        return view;
    }

    public ModelAndView processPayrollPayPeriodRequest(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("success");
        String operation = request.getParameter("txtMode");
        //operation mode: N= New, E=Edit
        if(operation.trim().equals("N")){
            Payroll_PayPeriod period = new Payroll_PayPeriod();
            String strStartTime = request.getParameter("txtStartDate");
            String strEndTime = request.getParameter("txtEndDate");
            String companyCode = request.getParameter("cbCompany");

            Date startDate = new Date();
            try {
                startDate = DateUtility.getDateFromString(strStartTime, ConfigReader.DATE_FORMAT);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            Date endDate = new Date();
            try {
                endDate = DateUtility.getDateFromString(strEndTime, ConfigReader.DATE_FORMAT);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }

            period.setStartTime(startDate);
            period.setEndTime(endDate);
            period.setLocked(-1);
            period.setDateCreated(new Date());
            period.setCompanyCode(companyCode);

            boolean saved = payrollConfigurationService.updatePayroll_PayPeriod(period, PayrollConfigurationService.MODE_INSERT);
            if(saved == false){
                view = new ModelAndView("error");
                view.addObject("message", "Can not save payroll pay period. Please try again later");
                return view;
            }

            view = new ModelAndView("payroll/payroll_payperiod_calculation_msg");
            view.addObject("message", "New Payroll Pay Period Successfully Created.");

        }else if(operation.trim().equals("E")){
            Payroll_PayPeriod period = new Payroll_PayPeriod();
            String strStartTime = request.getParameter("txtStartDate");
            String strEndTime = request.getParameter("txtEndDate");
            String companyCode = request.getParameter("cbCompany");

            String strId = request.getParameter("txtId");

            if(strId == null || strId.trim().equals("")){
                view = new ModelAndView("error");
                view.addObject("message","Unable to perform update. Please try again later.");
                return view;
            }

            Date startDate = new Date();
            try {
                startDate = DateUtility.getDateFromString(strStartTime, ConfigReader.DATE_FORMAT);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            Date endDate = new Date();
            try {
                endDate = DateUtility.getDateFromString(strEndTime, ConfigReader.DATE_FORMAT);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }

            period.setStartTime(startDate);
            period.setEndTime(endDate);
            period.setLocked(-1);
            period.setDateCreated(new Date());
            period.setCompanyCode(companyCode);
            
            period.setId(Integer.parseInt(strId));
            
            boolean saved = payrollConfigurationService.updatePayroll_PayPeriod(period, PayrollConfigurationService.MODE_UPDATE);
            if(saved == false){
                view = new ModelAndView("error");
                view.addObject("message", "Can not save payroll pay period information. Please try again later");
                return view;
            }

            view = viewAllPayroll_PayPeriod(request, response);
            view.addObject("message", "Record Saved Successfully.");
        }
        return view;
    }

    public ModelAndView downloadPeriod_PayStubSummary(HttpServletRequest request, HttpServletResponse response){
        try{
            Map model = new HashMap();

            String companyCode = this.getActiveEmployeeCompanyCode(request);

            //set header for excel file
            List<ExcelContent> contents = payrollConfigurationService.findAllPaySummary_ForActivePeriod(true, companyCode);
            //instantiate excel writer
            ExcelWriter r = new ExcelWriter();
            //get the name of the excel file
            String fileName = DateUtility.getDateAsString(new Date(), "yyyyMMddHHmmss") + ".xls";
            //set the name of the excel file
            model.put(WidgetListExcelView.WIDGET_EXCEL_FILE_NAME, fileName);
            //generate and set the workbook
            model.put(WidgetListExcelView.WIDGET_EXCEL_KEY, r.generateExcelWorkBook(new FileOutputStream(fileName), contents, true));
            //initiate our widget - This will prompt the user to download or load excel
            return new ModelAndView(new WidgetListExcelView(), model);

        }catch(Exception er){
            er.printStackTrace();
            ModelAndView view = new ModelAndView("error");
            
            return null;
        }
    }
    /**
     * @return the payrollConfigurationService
     */
    public PayrollConfigurationService getPayrollConfigurationService() {
        return payrollConfigurationService;
    }

    /**
     * @param payrollConfigurationService the payrollConfigurationService to set
     */
    public void setPayrollConfigurationService(PayrollConfigurationService payrollConfigurationService) {
        this.payrollConfigurationService = payrollConfigurationService;
    }

}
