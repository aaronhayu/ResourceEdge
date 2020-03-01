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

package org.tenece.hr.data.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import org.tenece.hr.db.DatabaseQueryLoader;
import org.tenece.web.data.Payroll_AccountGroup;
import org.tenece.web.data.Payroll_Attribute;
import org.tenece.web.data.Payroll_InputType;
import org.tenece.web.data.Payroll_PayAccount;
import org.tenece.web.data.Payroll_Policy;
import org.tenece.web.data.Payroll_PayPeriod;
import org.tenece.web.data.Scheduler;

/**
 * Tenece Professional Services Limited
 * @author amachree
 */
public class PayrollConfigurationDAO extends BaseDAO {

    /* --------- Payroll Attribute ----------------- */
    public List<Payroll_Attribute> getAllPayrollAttribute(){
        return getAllPayrollAttribute(null, null);
    }
    public List<Payroll_Attribute> getAllPayrollAttribute(String searchKey, String searchValue){
        Connection connection = null;
        List<Payroll_Attribute> records = new ArrayList<Payroll_Attribute>();
        try{
            connection = this.getConnection(true);
            ResultSet rst = null;
            //check if user requested for a search result
            if(searchKey == null || searchValue == null){
                rst = this.executeQuery(DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_ATTRIBUTE_SELECT, connection);
            }else{
                String query = "";
                if(searchKey.trim().equals("CODE")){
                    query = DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_ATTRIBUTE_SELECT_SEARCH_BY_CODE;
                    query = query.replaceAll("_SEARCH_", searchValue);
                }else if(searchKey.trim().equals("DESC")){
                    query = DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_ATTRIBUTE_SELECT_SEARCH_BY_DESC;
                    query = query.replaceAll("_SEARCH_", searchValue);
                }
                rst = this.executeQuery(query, connection);
            }
            while(rst.next()){
                Payroll_Attribute attribute = new Payroll_Attribute();
                attribute.setId(rst.getInt("attributeid"));
                attribute.setCode(rst.getString("code"));
                attribute.setDescription(rst.getString("description"));
                attribute.setCompanyCode(rst.getString("company_code"));

                records.add(attribute);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return records;
    }

    public List<Payroll_Attribute> getAllPayrollAttributeByCompany(String code){
        return getAllPayrollAttributeByCompany(code, null, null);
    }
    public List<Payroll_Attribute> getAllPayrollAttributeByCompany(String code, String searchKey, String searchValue){
        Connection connection = null;
        List<Payroll_Attribute> records = new ArrayList<Payroll_Attribute>();

        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(code); type.add("STRING");
            
            connection = this.getConnection(true);
            ResultSet rst = null;
            //check if user requested for a search result
            if(searchKey == null || searchValue == null){
                rst = this.executeParameterizedQuery(connection, 
                        DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_ATTRIBUTE_SELECT_BY_COMPANY, param, type);
            }else{
                String query = "";
                if(searchKey.trim().equals("CODE")){
                    query = DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_ATTRIBUTE_SELECT_BY_COMPANY_SEARCH_BY_CODE;
                    query = query.replaceAll("_SEARCH_", searchValue);
                }else if(searchKey.trim().equals("DESC")){
                    query = DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_ATTRIBUTE_SELECT_BY_COMPANY_SEARCH_BY_DESC;
                    query = query.replaceAll("_SEARCH_", searchValue);
                }
                rst = this.executeParameterizedQuery(connection, query, param, type);
            }
            while(rst.next()){
                Payroll_Attribute attribute = new Payroll_Attribute();
                attribute.setId(rst.getInt("attributeid"));
                attribute.setCode(rst.getString("code"));
                attribute.setDescription(rst.getString("description"));
                attribute.setCompanyCode(rst.getString("company_code"));

                records.add(attribute);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return records;
    }

    public Payroll_Attribute getPayroll_AttributeById(int id){
        Connection connection = null;
        Payroll_Attribute record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_ATTRIBUTE_SELECT_BY_ID,
                    param, type);

            while(rst.next()){
                Payroll_Attribute attribute = new Payroll_Attribute();
                attribute.setId(rst.getInt("attributeid"));
                attribute.setCode(rst.getString("code"));
                attribute.setDescription(rst.getString("description"));
                attribute.setCompanyCode(rst.getString("company_code"));
                record = attribute;
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return record;
    }

    public int createNewPayroll_Attribute(Payroll_Attribute attribute){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            //get connection
            connection = this.getConnection(false);
            param.add(attribute.getCode()); type.add("STRING");
            param.add(attribute.getDescription()); type.add("STRING");
            param.add(attribute.getCompanyCode()); type.add("STRING");

            System.out.println("QUERY:" + DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_ATTRIBUTE_INSERT);

            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_ATTRIBUTE_INSERT,
                    param, type);

            return i;
        }catch(Exception e){
            e.printStackTrace();

            //return zero- no row updated
            return 0;

        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }//:~

    public int updatePayroll_Attribute(Payroll_Attribute attribute){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(attribute.getCode()); type.add("STRING");
            param.add(attribute.getDescription()); type.add("STRING");
            param.add(attribute.getCompanyCode()); type.add("STRING");
            param.add(attribute.getId()); type.add("NUMBER");

            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_ATTRIBUTE_UPDATE,
                    param, type);

            return i;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }//:~

    public int deletePayroll_Attribute(int attributeId){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(attributeId); type.add("NUMBER");

            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_ATTRIBUTE_DELETE,
                    param, type);

            return i;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }//:~


    /* --------- Payroll Policy ----------------- */
    public List<Payroll_Policy> getAllPayroll_Policy(){
        Connection connection = null;
        List<Payroll_Policy> records = new ArrayList<Payroll_Policy>();
        try{
            connection = this.getConnection(true);
            ResultSet rst = null;
            rst = this.executeQuery(DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_POLICY_SELECT, connection);

            while(rst.next()){
                Payroll_Policy policy = new Payroll_Policy();
                policy.setId(rst.getInt("policyid"));
                policy.setDescription(rst.getString("description"));

                records.add(policy);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return records;
    }

    public List<Payroll_Policy> getAllPayroll_PolicyByCompany(String code){
        Connection connection = null;
        List<Payroll_Policy> records = new ArrayList<Payroll_Policy>();

        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(code); type.add("STRING");

            connection = this.getConnection(true);
            ResultSet rst = null;
            rst = this.executeParameterizedQuery(connection,
                    DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_POLICY_SELECT_BY_COMPANY, param, type);

            while(rst.next()){
                Payroll_Policy policy = new Payroll_Policy();
                policy.setId(rst.getInt("policyid"));
                policy.setDescription(rst.getString("description"));
                policy.setCompanyCode(rst.getString("company_code"));

                records.add(policy);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return records;
    }

    public Payroll_Policy getPayroll_PolicyById(int id){
        Connection connection = null;
        Payroll_Policy record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_POLICY_SELECT_BY_ID,
                    param, type);

            while(rst.next()){
                Payroll_Policy policy = new Payroll_Policy();
                policy.setId(rst.getInt("policyid"));
                policy.setDescription(rst.getString("description"));
                policy.setCompanyCode(rst.getString("company_code"));
                record = policy;
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return record;
    }

    public int createNewPayroll_Policy(Payroll_Policy policy){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            //get connection
            connection = this.getConnection(false);

            param.add(policy.getDescription()); type.add("STRING");
            param.add(policy.getCompanyCode()); type.add("STRING");

            System.out.println("QUERY:" + DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_POLICY_INSERT);

            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_POLICY_INSERT,
                    param, type);

            return i;
        }catch(Exception e){
            e.printStackTrace();

            //return zero- no row updated
            return 0;

        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }//:~

    public int updatePayroll_Policy(Payroll_Policy policy){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(policy.getDescription()); type.add("STRING");
            param.add(policy.getCompanyCode()); type.add("STRING");
            param.add(policy.getId()); type.add("NUMBER");

            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_POLICY_UPDATE,
                    param, type);

            return i;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }//:~

    public int deletePayroll_Policy(int policyId){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(policyId); type.add("NUMBER");

            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_POLICY_DELETE,
                    param, type);

            return i;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }//:~

    /* -------- Payroll Policy Attribute -------- */
    public List<Payroll_Policy.PolicyAttribute> getAllPayroll_PolicyAttributes(int policyId){
        Connection connection = null;
        List<Payroll_Policy.PolicyAttribute> records = new ArrayList<Payroll_Policy.PolicyAttribute>();
        try{
            Vector param =new Vector();
            Vector type = new Vector();
            //set policy id as parameter
            param.add(policyId); type.add("NUMBER");

            connection = this.getConnection(true);
            ResultSet rst = null;
            rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_POLICY_ATTRIBUTE_SELECT_BY_POLICY,
                    param, type);

            while(rst.next()){
                Payroll_Policy.PolicyAttribute policyAttribute = new Payroll_Policy.PolicyAttribute();
                policyAttribute.setPolicyId(rst.getInt("policyid"));
                policyAttribute.setAttributeId(rst.getInt("attributeid"));
                //instantiate attribute object and populate
                Payroll_Attribute attribute = new Payroll_Attribute();
                attribute.setCode(rst.getString("code"));
                attribute.setDescription(rst.getString("description"));
                attribute.setCompanyCode(rst.getString("company_code"));
                //add attribute to policy
                policyAttribute.setAttribute(attribute);
                
                records.add(policyAttribute);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return records;
    }

    public List<Payroll_Policy.PolicyAttribute> getAllPayroll_PolicyAttributesByCompany(int policyId, String code){
        Connection connection = null;
        List<Payroll_Policy.PolicyAttribute> records = new ArrayList<Payroll_Policy.PolicyAttribute>();
        try{
            Vector param =new Vector();
            Vector type = new Vector();
            //set policy id as parameter
            param.add(policyId); type.add("NUMBER");
            param.add(code); type.add("STRING");

            connection = this.getConnection(true);
            ResultSet rst = null;
            rst = this.executeParameterizedQuery(connection, 
                    DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_POLICY_ATTRIBUTE_SELECT_BY_POLICY_AND_COMPANY,
                    param, type);

            while(rst.next()){
                Payroll_Policy.PolicyAttribute policyAttribute = new Payroll_Policy.PolicyAttribute();
                policyAttribute.setPolicyId(rst.getInt("policyid"));
                policyAttribute.setAttributeId(rst.getInt("attributeid"));
                //instantiate attribute object and populate
                Payroll_Attribute attribute = new Payroll_Attribute();
                attribute.setCode(rst.getString("code"));
                attribute.setDescription(rst.getString("description"));
                attribute.setCompanyCode(rst.getString("company_code"));
                //add attribute to policy
                policyAttribute.setAttribute(attribute);

                records.add(policyAttribute);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return records;
    }

    public int createNewPayroll_PolicyAttribute(int policyId, int attributeId){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(policyId); type.add("NUMBER");
            param.add(attributeId); type.add("NUMBER");

            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_POLICY_ATTRIBUTE_INSERT,
                    param, type);

            return i;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }//:~

    public int deletePayroll_PolicyAttribute(int policyId, int attributeId){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(policyId); type.add("NUMBER");
            param.add(attributeId); type.add("NUMBER");

            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_POLICY_ATTRIBUTE_DELETE_BY_POLICY_AND_ATTRIBUTE,
                    param, type);

            return i;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }//:~

    /* --------------- Payroll Policy pay Items ---------------- */
    public List<Payroll_Policy.PayItem> getPayroll_PolicyPayItemById(int id){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        List<Payroll_Policy.PayItem> items = new ArrayList<Payroll_Policy.PayItem>();

        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_POLICY_PAYITEM_SELECT,
                    param, type);

            while(rst.next()){
                Payroll_Policy.PayItem item = new Payroll_Policy.PayItem();
                item.setPolicyId(rst.getInt("policyid"));
                item.setAccountId(rst.getInt("accountid"));
                item.setAmount(rst.getDouble("amount"));
                item.setFromPeriodId(rst.getInt("fromperiodid"));
                item.setToPeriodId(rst.getInt("toperiodid"));

                items.add(item);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return items;
    }

    public int createNewPayroll_PolicyPayItems(List<Payroll_Policy.PayItem> items){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            connection = this.getConnection(false);
            //start transaction
            startTransaction(connection);

            //delete all pending record
            param.add(items.get(0).getPolicyId()); type.add("NUMBER");
            int del = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_POLICY_PAYITEM_DELETE_ALL,
                        param, type);

            //initiate the new record
            int counter = 0;
            for(Payroll_Policy.PayItem item : items){
                //re-initialize vactors
                param = new Vector();
                type = new Vector();
                //set parameters in the appropriate order
                param.add(item.getPolicyId()); type.add("NUMBER");
                param.add(item.getAccountId()); type.add("NUMBER");
                param.add(item.getAmount()); type.add("AMOUNT");
                param.add(item.getFromPeriodId()); type.add("NUMBER");
                param.add(item.getToPeriodId()); type.add("NUMBER_NULL");

                int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_POLICY_PAYITEM_INSERT,
                        param, type);
                counter = counter + i;
            }
            if(counter != items.size()){
                throw new Exception("Incorrect number of updates");
            }
            commitTransaction(connection);
            return counter;
        }catch(Exception e){
            e.printStackTrace();
            try{ rollbackTransaction(connection); }catch(Exception re){}
            return 0;
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }//:~

    public int deletePayroll_PolicyPayItem(int policyId, int accountId){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(policyId); type.add("NUMBER");
            param.add(accountId); type.add("NUMBER");

            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_POLICY_PAYITEM_DELETE,
                    param, type);

            return i;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }//:~

    /* --------- Payroll Pay Account ----------------- */
    public List<Payroll_PayAccount> getAllPayroll_PayAccount(){
        return getAllPayroll_PayAccount(null, null);
    }
    public List<Payroll_PayAccount> getAllPayroll_PayAccount(String searchKey, String searchValue){
        Connection connection = null;
        List<Payroll_PayAccount> records = new ArrayList<Payroll_PayAccount>();
        try{
            connection = this.getConnection(true);
            ResultSet rst = null;
            if(searchKey == null || searchValue == null){
                rst = this.executeQuery(DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_PAY_ACCOUNT_SELECT, connection);
            }else{
                String query = "";
                if(searchKey.trim().equals("DESC")){
                    query = DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_PAY_ACCOUNT_SELECT_SEARCH_BY_DESC;
                    query = query.replaceAll("_SEARCH_", searchValue);
                }
                rst = this.executeQuery(query, connection);
            }

            while(rst.next()){
                Payroll_PayAccount account = new Payroll_PayAccount();
                account.setId(rst.getInt("accountid"));
                account.setDescription(rst.getString("description"));
                account.setFormula(rst.getString("formula"));
                account.setCalculationSequence(rst.getInt("calcseq"));
                account.setInputType(rst.getInt("inputtype"));
                account.setGroupId(rst.getInt("groupid"));
                account.setCompanyCode(rst.getString("company_code"));
                records.add(account);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return records;
    }

    public List<Payroll_PayAccount> getAllPayroll_PayAccountByCompany(String code){
        return getAllPayroll_PayAccountByCompany(code, null, null);
    }
    public List<Payroll_PayAccount> getAllPayroll_PayAccountByCompany(String code, String searchKey, String searchValue){
        Connection connection = null;
        List<Payroll_PayAccount> records = new ArrayList<Payroll_PayAccount>();

        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(code); type.add("STRING");
            
            connection = this.getConnection(true);
            ResultSet rst = null;
            if(searchKey == null || searchValue == null){
                rst = this.executeParameterizedQuery(connection, 
                        DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_PAY_ACCOUNT_SELECT_BY_COMPANY, param, type);
            }else{
                String query = "";
                if(searchKey.trim().equals("DESC")){
                    query = DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_PAY_ACCOUNT_SELECT_BY_COMPANY_SEARCH_BY_DESC;
                    query = query.replaceAll("_SEARCH_", searchValue);
                }
                rst = this.executeParameterizedQuery(connection, query, param, type);
            }

            while(rst.next()){
                Payroll_PayAccount account = new Payroll_PayAccount();
                account.setId(rst.getInt("accountid"));
                account.setDescription(rst.getString("description"));
                account.setFormula(rst.getString("formula"));
                account.setCalculationSequence(rst.getInt("calcseq"));
                account.setInputType(rst.getInt("inputtype"));
                account.setGroupId(rst.getInt("groupid"));
                account.setCompanyCode(rst.getString("company_code"));
                records.add(account);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return records;
    }

    public Payroll_PayAccount getPayroll_PayAccountById(int id){
        Connection connection = null;
        Payroll_PayAccount record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_PAY_ACCOUNT_SELECT_BY_ID,
                    param, type);

            while(rst.next()){
                Payroll_PayAccount account = new Payroll_PayAccount();
                account.setId(rst.getInt("accountid"));
                account.setDescription(rst.getString("description"));
                account.setFormula(rst.getString("formula"));
                account.setCalculationSequence(rst.getInt("calcseq"));
                account.setInputType(rst.getInt("inputtype"));
                account.setGroupId(rst.getInt("groupid"));
                account.setCompanyCode(rst.getString("company_code"));
                
                record = account;
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return record;
    }

    public int createNewPayroll_PayAccount(Payroll_PayAccount account){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            //get connection
            connection = this.getConnection(false);

            param.add(account.getDescription()); type.add("STRING");
            param.add(account.getFormula()); type.add("STRING");
            param.add(account.getCalculationSequence()); type.add("NUMBER");
            param.add(account.getInputType()); type.add("NUMBER");
            param.add(account.getGroupId()); type.add("NUMBER");
            param.add(account.getCompanyCode()); type.add("STRING");

            System.out.println("QUERY:" + DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_PAY_ACCOUNT_INSERT);

            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_PAY_ACCOUNT_INSERT,
                    param, type);

            return i;
        }catch(Exception e){
            e.printStackTrace();

            //return zero- no row updated
            return 0;

        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }//:~

    public int updatePayroll_PayAccount(Payroll_PayAccount account){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(account.getDescription()); type.add("STRING");
            param.add(account.getFormula()); type.add("STRING");
            param.add(account.getCalculationSequence()); type.add("NUMBER");
            param.add(account.getInputType()); type.add("NUMBER");
            param.add(account.getGroupId()); type.add("NUMBER");
            param.add(account.getCompanyCode()); type.add("STRING");

            param.add(account.getId()); type.add("NUMBER");

            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_PAY_ACCOUNT_UPDATE,
                    param, type);

            return i;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }//:~

    public int deletePayroll_PayAccount(int accountId){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            //check if account has been used in payevent
            
            param.add(accountId); type.add("NUMBER");
            
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_PAY_ACCOUNT_DELETE,
                    param, type);

            return i;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }//:~


    /* --------- Payroll Account Group ----------------- */
    public List<Payroll_AccountGroup> getAllPayroll_AccountGroup(){
        Connection connection = null;
        List<Payroll_AccountGroup> records = new ArrayList<Payroll_AccountGroup>();
        try{
            connection = this.getConnection(true);
            ResultSet rst = null;
            rst = this.executeQuery(DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_ACCOUNT_GROUP_SELECT, connection);

            while(rst.next()){
                Payroll_AccountGroup accountGroup = new Payroll_AccountGroup();
                accountGroup.setId(rst.getInt("groupid"));
                accountGroup.setName(rst.getString("name"));
                accountGroup.setDescription(rst.getString("description"));
                accountGroup.setReportShow(rst.getInt("report"));
                accountGroup.setSelectorShow(rst.getInt("selector"));
                accountGroup.setCompanyCode(rst.getString("company_code"));

                records.add(accountGroup);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return records;
    }

    public List<Payroll_AccountGroup> getAllPayroll_AccountGroupByCompany(String code){
        Connection connection = null;
        List<Payroll_AccountGroup> records = new ArrayList<Payroll_AccountGroup>();

        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(code); type.add("STRING");
            connection = this.getConnection(true);
            ResultSet rst = null;
            rst = this.executeParameterizedQuery(connection, 
                    DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_ACCOUNT_GROUP_SELECT_BY_COMPANY, param, type);

            while(rst.next()){
                Payroll_AccountGroup accountGroup = new Payroll_AccountGroup();
                accountGroup.setId(rst.getInt("groupid"));
                accountGroup.setName(rst.getString("name"));
                accountGroup.setDescription(rst.getString("description"));
                accountGroup.setReportShow(rst.getInt("report"));
                accountGroup.setSelectorShow(rst.getInt("selector"));
                accountGroup.setCompanyCode(rst.getString("company_code"));

                records.add(accountGroup);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return records;
    }

    public Payroll_AccountGroup getPayroll_AccountGroupById(int id){
        Connection connection = null;
        Payroll_AccountGroup record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_ACCOUNT_GROUP_SELECT_BY_ID,
                    param, type);

            while(rst.next()){
                Payroll_AccountGroup accountGroup = new Payroll_AccountGroup();
                accountGroup.setId(rst.getInt("groupid"));
                accountGroup.setName(rst.getString("name"));
                accountGroup.setDescription(rst.getString("description"));
                accountGroup.setReportShow(rst.getInt("report"));
                accountGroup.setSelectorShow(rst.getInt("selector"));

                record = accountGroup;
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return record;
    }

    public int createNewPayroll_AccountGroup(Payroll_AccountGroup accountGroup){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            //get connection
            connection = this.getConnection(false);

            param.add(accountGroup.getName()); type.add("STRING");
            param.add(accountGroup.getDescription()); type.add("STRING");
            param.add(accountGroup.getReportShow()); type.add("NUMBER");
            param.add(accountGroup.getSelectorShow()); type.add("NUMBER");
            param.add(accountGroup.getCompanyCode()); type.add("STRING");

            System.out.println("QUERY:" + DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_ACCOUNT_GROUP_INSERT);

            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_ACCOUNT_GROUP_INSERT,
                    param, type);

            return i;
        }catch(Exception e){
            e.printStackTrace();

            //return zero- no row updated
            return 0;

        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }//:~

    public int updatePayroll_AccountGroup(Payroll_AccountGroup accountGroup){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(accountGroup.getName()); type.add("STRING");
            param.add(accountGroup.getDescription()); type.add("STRING");
            param.add(accountGroup.getReportShow()); type.add("NUMBER");
            param.add(accountGroup.getSelectorShow()); type.add("NUMBER");
            param.add(accountGroup.getCompanyCode()); type.add("STRING");

            param.add(accountGroup.getId()); type.add("NUMBER");

            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_ACCOUNT_GROUP_UPDATE,
                    param, type);

            return i;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }//:~

    public int deletePayroll_AccountGroup(int accountGroupId){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            //get ids of earnings and deductions

            param.add(accountGroupId); type.add("NUMBER");

            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_ACCOUNT_GROUP_DELETE,
                    param, type);

            return i;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }//:~


    /* -------- Payroll Pay Account Group -------- */
//    public List<Payroll_PayAccount.PayAccountGroup> getAllPayroll_PayAccountGroups(int accountId){
//        Connection connection = null;
//        List<Payroll_PayAccount.PayAccountGroup> records = new ArrayList<Payroll_PayAccount.PayAccountGroup>();
//        try{
//            Vector param =new Vector();
//            Vector type = new Vector();
//            //set policy id as parameter
//            param.add(accountId); type.add("NUMBER");
//
//            connection = this.getConnection(true);
//            ResultSet rst = null;
//            rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_PAY_ACCOUNT_GROUP_SELECT_BY_ACCOUNT,
//                    param, type);
//
//            while(rst.next()){
//                Payroll_PayAccount.PayAccountGroup accountGroup = new Payroll_PayAccount.PayAccountGroup();
//                accountGroup.setAccountId(rst.getInt("accountid"));
//                accountGroup.setGroupId(rst.getInt("groupid"));
//
//                records.add(accountGroup);
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//        }finally{
//            try {
//                connection.close();
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
//        }
//        return records;
//    }
//
//    public int createNewPayroll_PayAccountGroup(int accountId, int groupId){
//        Connection connection = null;
//        Vector param =new Vector();
//        Vector type = new Vector();
//        try{
//            param.add(accountId); type.add("NUMBER");
//            param.add(groupId); type.add("NUMBER");
//
//            connection = this.getConnection(false);
//            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_PAY_ACCOUNT_GROUP_INSERT,
//                    param, type);
//
//            return i;
//        }catch(Exception e){
//            e.printStackTrace();
//            return 0;
//        }finally{
//            try {
//                connection.close();
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
//        }
//    }//:~
//
//    public int deletePayroll_PayAccountGroup(int accountId, int groupId){
//        Connection connection = null;
//        Vector param =new Vector();
//        Vector type = new Vector();
//        try{
//            param.add(accountId); type.add("NUMBER");
//            param.add(groupId); type.add("NUMBER");
//
//            connection = this.getConnection(false);
//            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_PAY_ACCOUNT_GROUP_DELETE_BY_ACCOUNT_AND_GROUP,
//                    param, type);
//
//            return i;
//        }catch(Exception e){
//            e.printStackTrace();
//            return 0;
//        }finally{
//            try {
//                connection.close();
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
//        }
//    }//:~

    /* ***************** Payroll INPUT TYPE ********************** */
    public List<Payroll_InputType> getAllPayroll_InputType(){
        Connection connection = null;
        List<Payroll_InputType> records = new ArrayList<Payroll_InputType>();
        try{
            connection = this.getConnection(true);
            ResultSet rst = null;
            rst = this.executeQuery(DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_INPUT_TYPE_SELECT, connection);

            while(rst.next()){
                Payroll_InputType inputType = new Payroll_InputType();
                inputType.setId(rst.getInt("idx"));
                inputType.setDescription(rst.getString("description"));
                inputType.setFormula(rst.getString("formula"));

                records.add(inputType);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return records;
    }

    public Payroll_InputType getPayroll_InputTypeById(int id){
        Connection connection = null;
        Payroll_InputType record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_INPUT_TYPE_SELECT_BY_ID,
                    param, type);

            while(rst.next()){
                Payroll_InputType inputType = new Payroll_InputType();
                inputType.setId(rst.getInt("idx"));
                inputType.setDescription(rst.getString("description"));
                inputType.setFormula(rst.getString("formula"));

                record = inputType;
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return record;
    }

    // -------------------- PAY PERIOD -------------------
    public List<Payroll_PayPeriod> getAllPayroll_PayPeriod(){
        Connection connection = null;
        List<Payroll_PayPeriod> records = new ArrayList<Payroll_PayPeriod>();
        try{
            connection = this.getConnection(true);
            ResultSet rst = null;
            rst = this.executeQuery(DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_PAY_PERIOD_SELECT, connection);

            while(rst.next()){
                Payroll_PayPeriod period = new Payroll_PayPeriod();
                period.setId(rst.getInt("periodid"));
                period.setStartTime(new Date(rst.getDate("starttime").getTime()));
                period.setEndTime(new Date(rst.getDate("endtime").getTime()));
                period.setLocked(rst.getInt("locked"));
                period.setCompanyCode(rst.getString("company_code"));

                records.add(period);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return records;
    }

    public List<Payroll_PayPeriod> getAllPayroll_PayPeriodByCompany(String code){
        Connection connection = null;
        List<Payroll_PayPeriod> records = new ArrayList<Payroll_PayPeriod>();

        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(code); type.add("STRING");

            connection = this.getConnection(true);
            ResultSet rst = null;
            rst = this.executeParameterizedQuery(connection, 
                    DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_PAY_PERIOD_SELECT_BY_COMPANY, param, type);

            while(rst.next()){
                Payroll_PayPeriod period = new Payroll_PayPeriod();
                period.setId(rst.getInt("periodid"));
                period.setStartTime(new Date(rst.getDate("starttime").getTime()));
                period.setEndTime(new Date(rst.getDate("endtime").getTime()));
                period.setLocked(rst.getInt("locked"));
                period.setCompanyCode(rst.getString("company_code"));

                records.add(period);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return records;
    }

    public Payroll_PayPeriod getPayroll_PayPeriodById(int id){
        Connection connection = null;
        Payroll_PayPeriod record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_PAY_PERIOD_SELECT_BY_ID,
                    param, type);

            while(rst.next()){
                Payroll_PayPeriod period = new Payroll_PayPeriod();
                period.setId(rst.getInt("periodid"));
                period.setStartTime(new Date(rst.getDate("starttime").getTime()));
                period.setEndTime(new Date(rst.getDate("endtime").getTime()));
                period.setLocked(rst.getInt("locked"));
                period.setCompanyCode(rst.getString("company_code"));

                record = period;
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return record;
    }

    public Payroll_PayPeriod getActivePayroll_PayPeriod(){
        Connection connection = null;
        Payroll_PayPeriod records = new Payroll_PayPeriod();
        try{
            connection = this.getConnection(true);
            ResultSet rst = null;
            rst = this.executeQuery(DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_PAY_PERIOD_SELECT_ACTIVE, connection);

            while(rst.next()){
                Payroll_PayPeriod period = new Payroll_PayPeriod();
                period.setId(rst.getInt("periodid"));
                period.setStartTime(new Date(rst.getDate("starttime").getTime()));
                period.setEndTime(new Date(rst.getDate("endtime").getTime()));
                period.setLocked(rst.getInt("locked"));
                period.setCompanyCode(rst.getString("company_code"));

                records = period;
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return records;
    }

    public Payroll_PayPeriod getActivePayroll_PayPeriodByCompany(String code){
        Connection connection = null;
        Payroll_PayPeriod records = new Payroll_PayPeriod();

        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(code); type.add("STRING");
            
            connection = this.getConnection(true);
            ResultSet rst = null;
            rst = this.executeParameterizedQuery(connection,
                    DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_PAY_PERIOD_SELECT_BY_COMPANY_ACTIVE, param, type);

            while(rst.next()){
                Payroll_PayPeriod period = new Payroll_PayPeriod();
                period.setId(rst.getInt("periodid"));
                period.setStartTime(new Date(rst.getDate("starttime").getTime()));
                period.setEndTime(new Date(rst.getDate("endtime").getTime()));
                period.setLocked(rst.getInt("locked"));
                period.setCompanyCode(rst.getString("company_code"));

                records = period;
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return records;
    }

    public Payroll_PayPeriod.PayrollSummary getPayroll_SummaryForPayPeriod(){
        Connection connection = null;
        Payroll_PayPeriod.PayrollSummary _summary = new Payroll_PayPeriod.PayrollSummary();
        try{
            connection = this.getConnection(true);
            ResultSet rst = null;
            rst = this.executeQuery(DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_EMPLOYEE_SUMMARY_SELECT, connection);

            if(rst.next()){
                Payroll_PayPeriod.PayrollSummary summary = new Payroll_PayPeriod.PayrollSummary();
                summary.setTotalEmployeeWithPolicy(rst.getInt("policyCount"));
                summary.setTotalEmployee(rst.getInt("employeeCount"));
                summary.setTotalEmployeeWithPayStubs(rst.getInt("totalEmployeeStub"));

                _summary = summary;
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return _summary;
    }

    public Payroll_PayPeriod.PayrollSummary getPayroll_SummaryForPayPeriodByCompany(String code){
        Connection connection = null;
        Payroll_PayPeriod.PayrollSummary _summary = new Payroll_PayPeriod.PayrollSummary();

        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(code); type.add("STRING");
            param.add(code); type.add("STRING");
            param.add(code); type.add("STRING");

            connection = this.getConnection(true);
            ResultSet rst = null;
            rst = this.executeParameterizedQuery(connection,
                    DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_EMPLOYEE_SUMMARY_SELECT_BY_COMPANY, param, type);

            if(rst.next()){
                Payroll_PayPeriod.PayrollSummary summary = new Payroll_PayPeriod.PayrollSummary();
                summary.setTotalEmployeeWithPolicy(rst.getInt("policyCount"));
                summary.setTotalEmployee(rst.getInt("employeeCount"));
                summary.setTotalEmployeeWithPayStubs(rst.getInt("totalEmployeeStub"));
                summary.setCompanyCode(code);

                _summary = summary;
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return _summary;
    }

    public Integer countPeriodByCompany(String companyCode){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            //get connection
            connection = this.getConnection(false);

            boolean initialPeriod = false;
            param.add(companyCode); type.add("STRING");

            //check if company do have existing period otherwise just save period
            ResultSet rstCount = this.executeParameterizedQuery(connection,
                   DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_PAY_PERIOD_COUNT_BY_COMPANY , param, type);
            if(rstCount.next()){
                return rstCount.getInt(1);
            }else{
                return 0;
            }
        }catch(Exception e){
            e.printStackTrace();
            //return zero- no row updated
            return 0;
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    public int createNewPayroll_PayPeriod(Payroll_PayPeriod period){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            //get connection
            connection = this.getConnection(false);

            //start transaction
            startTransaction(connection);

            //get company code from period
            String companyCode = period.getCompanyCode();

            boolean initialPeriod = false;
            param.add(companyCode); type.add("STRING");

            //check if company do have existing period otherwise just save period
            ResultSet rstCount = this.executeParameterizedQuery(connection,
                   DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_PAY_PERIOD_COUNT_BY_COMPANY , param, type);
            if(rstCount.next()){
                if(rstCount.getInt(1) <= 0){
                    initialPeriod = false;
                }else{
                    initialPeriod = true; //it is greater than zero
                }
            }else{
                initialPeriod = false;
            }

            param = new Vector();
            type = new Vector();

            if(initialPeriod == true){
                //call procedure to move all records to archive for payevent
                int updated = this.executeUpdate(connection,
                        DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_PAYEVENT_ARCHIVED);

                //confirm if the number of update is valid...
                if(updated <= 0){
                    throw new Exception("Unable to move payevent to archive");
                }
                //remove all records in payevent table - ready to accept new records
                updated = this.executeUpdate(connection,
                        DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_PAYEVENT_TRUNCATE);

                //lock the active period-
                updated = this.executeUpdate(connection,
                        DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_PAY_PERIOD_LOCKED);
                //check if it was locked successfully
                if(updated <= 0){
                    throw new Exception("Unable to lock pay period. Please try again.");
                }
            }
            //start process of saving new period
            param.add(period.getStartTime()); type.add("DATE");
            param.add(period.getEndTime()); type.add("DATE");
            param.add(0); type.add("NUMBER");
            
            param.add(period.getDateCreated()); type.add("DATE");
            param.add(period.getCompanyCode()); type.add("STRING");

            System.out.println("QUERY:" + DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_PAY_PERIOD_INSERT);

            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_PAY_PERIOD_INSERT,
                    param, type);

            commitTransaction(connection);
            return i;
        }catch(Exception e){
            e.printStackTrace();
            try{ rollbackTransaction(connection); }catch(Exception er){}
            //return zero- no row updated
            return 0;

        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }//:~

    public int updatePayroll_PayPeriod(Payroll_PayPeriod period){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(period.getStartTime()); type.add("DATE");
            param.add(period.getEndTime()); type.add("DATE");
            param.add(period.getLocked()); type.add("NUMBER");

            param.add(period.getCompanyCode()); type.add("STRING");
            param.add(period.getId()); type.add("NUMBER");

            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().PAYROLL_PAY_PERIOD_UPDATE,
                    param, type);

            return i;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }//:~

    public int createScheduler(String code){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(code); type.add("STRING");
            param.add(new Date()); type.add("DATE");

            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection,
                    DatabaseQueryLoader.getQueryAssignedConstant().SCHEDULER_INSERT,
                    param, type);

            return i;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public int updateSchedulerStatus(String code){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(code); type.add("STRING");

            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection,
                    DatabaseQueryLoader.getQueryAssignedConstant().SCHEDULER_UPDATE_BY_CODE,
                    param, type);

            return i;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public int deleteScheduler(String code){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(code); type.add("STRING");

            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection,
                    DatabaseQueryLoader.getQueryAssignedConstant().SCHEDULER_DELETE_BY_CODE,
                    param, type);

            return i;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public Scheduler getSchedulerByCode(String code){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{

            param.add(code); type.add("STRING");

            connection = this.getConnection(false);
            ResultSet rst = this.executeParameterizedQuery(connection,
                    DatabaseQueryLoader.getQueryAssignedConstant().SCHEDULER_SELECT_BY_CODE,
                    param, type);

            Scheduler scheduler = new Scheduler();
            if(rst.next()){
                scheduler.setCode(code);
                scheduler.setStartTime(new Date(rst.getDate("start_time").getTime()));
                scheduler.setStatus(rst.getString("status"));
            }
            return scheduler;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
