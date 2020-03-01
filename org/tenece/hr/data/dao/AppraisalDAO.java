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

package org.tenece.hr.data.dao;

import org.tenece.hr.db.DatabaseQueryLoader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.tenece.web.data.Appraisal;
import org.tenece.web.data.AppraisalInformation;

/**
 *
 * @author jeffry.amachree
 */
public class AppraisalDAO extends BaseDAO{
    
    /**
     * Creates a new instance of DepartmentDAO
     */
    public AppraisalDAO() {
    }

    public Appraisal getAppraisalByTransaction(long id){
        Connection connection = null;
        Appraisal record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().APPRAISAL_TRANSACTION_SELECT,
                    param, type);

            if(rst.next()){
                Appraisal item = new Appraisal();
                item.setId(rst.getLong("id"));
                item.setEmployeeId(rst.getLong("emp_number"));
                item.setTransactionId(rst.getLong("transactionid"));
                item.seteStatus(rst.getString("estatus"));
                
                record = item;
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

    public AppraisalInformation.Step1 getTransactionAppraisalStep1(long id){
        Connection connection = null;
        AppraisalInformation.Step1 record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().APPRAISAL_STEP1_TRNX_SELECT,
                    param, type);

            if(rst.next()){
                AppraisalInformation.Step1 item = new AppraisalInformation.Step1();
                item.setAppraisalId(rst.getLong("appraisal_id"));
                item.setStartDate(new java.util.Date(rst.getDate("start_period").getTime()));
                item.setEndDate(new java.util.Date(rst.getDate("end_period").getTime()));
                item.setCriteriaId(rst.getInt("appraisal_criteria"));
                item.setSupervisor(rst.getLong("supervisor"));
                item.setAppliedLevel(rst.getInt("applied_level"));
                record = item;

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
    
    public Appraisal.Step1 getAppraisalStep1(int id){
        Connection connection = null;
        Appraisal.Step1 record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().APPRAISAL_STEP1_SELECT,
                    param, type);
            
            if(rst.next()){
                Appraisal.Step1 item = new Appraisal.Step1();
                item.setEmployeeId(rst.getInt("emp_number"));
                item.setStartDate(new java.util.Date(rst.getDate("start_period").getTime()));
                item.setEndDate(new java.util.Date(rst.getDate("end_period").getTime()));
                item.setReviewer(rst.getString("reviewer"));
                item.setCriteriaId(rst.getInt("appraisal_criteria"));
                //item.setTransactionId(rst.getLong("transactionid"));
                item.setIsTransaction(rst.getInt("istransaction"));
                record = item;

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
    
    public int createNewAppraisalStep1(Appraisal.Step1 step1){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(step1.getEmployeeId()); type.add("NUMBER");
            param.add(step1.getStartDate()); type.add("DATE");
            param.add(step1.getEndDate()); type.add("DATE");
            param.add(step1.getReviewer()); type.add("STRING");
            param.add(step1.getCriteriaId()); type.add("NUMBER");
            if(step1.getTransactionId() == 0){
                param.add(step1.getTransactionId()); type.add("NUMBER_NULL");
            }else{
                param.add(step1.getTransactionId()); type.add("NUMBER");
            }
            param.add(step1.getIsTransaction()); type.add("NUMBER");
            
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().APPRAISAL_STEP1_INSERT,
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
    
    public int deleteAppraisalStep1(List<Integer> ids){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{

            for(int id : ids){
                param.add(id); type.add("NUMBER");

                connection = this.getConnection(false);
                int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().APPRAISAL_STEP1_DELETE,
                        param, type);
            }
            return ids.size();
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

    public List<Appraisal.Step2> getAppraisalStep2(int id){
        Connection connection = null;
        List<Appraisal.Step2> record = new ArrayList<Appraisal.Step2>();
        Vector param =new Vector();
        Vector type = new Vector();
        System.out.println("Appraisal Step2 id = "+id);
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().APPRAISAL_STEP2_SELECT,
                    param, type);

            while(rst.next()){
                System.out.println("<<<<<<<<<<<<<<<<<Begin<<<<<<<<<<<<<<<<<<<<<<<<<");
                Appraisal.Step2 item = new Appraisal.Step2();
                item.setEmployeeId(rst.getInt("emp_number"));
                System.out.println("EmployeeId"+ item.getEmployeeId());
                item.setGroupId(rst.getInt("group_id"));
                System.out.println("GroupId"+ item.getGroupId());
                item.setCompetenceId(rst.getInt("competence_id"));
                System.out.println("CompetenceId"+ item.getCompetenceId());
                item.setRateIndex(rst.getInt("rate_index"));
                System.out.println("RateIndex"+ item.getRateIndex());
                item.setIsTransaction(rst.getInt("istransaction"));
                System.out.println("IsTransaction"+ item.getIsTransaction());
                 System.out.println("<<<<<<<<<<<<<<<<<End<<<<<<<<<<<<<<<<<<<<<<<<<");
                record.add(item);
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

    public int createNewAppraisalStep2(Appraisal.Step2 step2){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(step2.getEmployeeId()); type.add("NUMBER");
            param.add(step2.getGroupId()); type.add("NUMBER");
            param.add(step2.getCompetenceId()); type.add("NUMBER");
            param.add(step2.getRateIndex()); type.add("NUMBER");
            if(step2.getTransactionId() == 0){
                param.add(step2.getTransactionId()); type.add("NUMBER_NULL");
            }else{
                param.add(step2.getTransactionId()); type.add("NUMBER");
            }
            param.add(step2.getIsTransaction()); type.add("NUMBER");

            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().APPRAISAL_STEP2_INSERT,
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

    public int createNewAppraisalStep2(List<Appraisal.Step2> step2List, List<Appraisal.Step2_Statement> stmtList){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            connection = this.getConnection(false);
            startTransaction(connection);

            for(Appraisal.Step2 step2 : step2List){
                param =new Vector();
                type = new Vector();
                System.out.println("step2.getEmployeeId() ===============  "+step2.getEmployeeId());
                param.add(step2.getEmployeeId()); type.add("NUMBER");
                param.add(step2.getGroupId()); type.add("NUMBER");
                param.add(step2.getCompetenceId()); type.add("NUMBER");
                param.add(step2.getRateIndex()); type.add("NUMBER");
                if(step2.getTransactionId() == 0){
                    param.add(step2.getTransactionId()); type.add("NUMBER_NULL");
                }else{
                    param.add(step2.getTransactionId()); type.add("NUMBER");
                }
                param.add(step2.getIsTransaction()); type.add("NUMBER");

                int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().APPRAISAL_STEP2_INSERT,
                        param, type);
            }

            //loop thru and save statements
            for(Appraisal.Step2_Statement step2 : stmtList){
                param =new Vector();
                type = new Vector();
                
                param.add(step2.getEmployeeId()); type.add("NUMBER");
                param.add(step2.getGroupId()); type.add("NUMBER");
                param.add(step2.getStatement()); type.add("STRING");
                if(step2.getTransactionId() == 0){
                    param.add(step2.getTransactionId()); type.add("NUMBER_NULL");
                }else{
                    param.add(step2.getTransactionId()); type.add("NUMBER");
                }
                param.add(step2.getIsTransaction()); type.add("NUMBER");

                int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().APPRAISAL_STMT_STEP2_INSERT,
                        param, type);
            }

            commitTransaction(connection);
            return 1;
        }catch(Exception e){
            e.printStackTrace();
            try {
                rollbackTransaction(connection);
            } catch (SQLException ex) {
                Logger.getLogger(AppraisalDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return 0;
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }//:~

    public int deleteAppraisalStep2(List<Integer> ids){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{

            for(int id : ids){
                param.add(id); type.add("NUMBER");

                connection = this.getConnection(false);
                int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().APPRAISAL_STEP2_DELETE,
                        param, type);
            }
            return ids.size();
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

    public List<Appraisal.Step2_Statement> getApproverAppraisalStep2_Statement(long employeeId, long transactionId){
        Connection connection = null;
        List<Appraisal.Step2_Statement> record = new ArrayList<Appraisal.Step2_Statement>();
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(employeeId); type.add("NUMBER");
            param.add(transactionId); type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().APPRAISAL_STMT_STEP2_APPROVE_SELECT,
                    param, type);

            while(rst.next()){
                Appraisal.Step2_Statement item = new Appraisal.Step2_Statement();
                System.out.println("<<<<<<<<<<<<<<<<<<<<Begin<<<<<<<<<<<<<<<<<<<<<<<<");
                item.setEmployeeId(rst.getInt("emp_number"));
                System.out.println("EmployeeId"+item.getEmployeeId());
                item.setGroupId(rst.getInt("group_id"));
                System.out.println("GroupId"+item.getGroupId());
                item.setStatement(rst.getString("statement"));
                System.out.println("Statement"+item.getStatement());
                item.setIsTransaction(rst.getInt("istransaction"));
                System.out.println("IsTransaction"+item.getIsTransaction());
                System.out.println("<<<<<<<<<<<<<<<<<<<<<End<<<<<<<<<<<<<<<<<<<<<<<<");
                record.add(item);
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

    public List<Appraisal.Step2_Statement> getAppraisalStep2_Statement(int id){
        Connection connection = null;
        List<Appraisal.Step2_Statement> record = new ArrayList<Appraisal.Step2_Statement>();
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().APPRAISAL_STMT_STEP2_SELECT,
                    param, type);

            while(rst.next()){
                Appraisal.Step2_Statement item = new Appraisal.Step2_Statement();
                item.setEmployeeId(rst.getInt("emp_number"));
                item.setGroupId(rst.getInt("group_id"));
                item.setStatement(rst.getString("statement"));
                item.setIsTransaction(rst.getInt("istransaction"));
                record.add(item);
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

    public int createNewAppraisalStep2_Statement(Appraisal.Step2_Statement step2){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(step2.getEmployeeId()); type.add("NUMBER");
            param.add(step2.getGroupId()); type.add("NUMBER");
            param.add(step2.getStatement()); type.add("STRING");
            if(step2.getTransactionId() == 0){
                param.add(step2.getTransactionId()); type.add("NUMBER_NULL");
            }else{
                param.add(step2.getTransactionId()); type.add("NUMBER");
            }
            param.add(step2.getIsTransaction()); type.add("NUMBER");

            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().APPRAISAL_STMT_STEP2_INSERT,
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

    public int deleteAppraisalStep2_Statement(List<Integer> ids){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{

            for(int id : ids){
                param.add(id); type.add("NUMBER");

                connection = this.getConnection(false);
                int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().APPRAISAL_STMT_STEP2_DELETE,
                        param, type);
            }
            return ids.size();
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

    public List<Appraisal.Step3> getAllAppraisalStep3(long id, long transactionId){
        Connection connection = null;
        List<Appraisal.Step3> record = new ArrayList<Appraisal.Step3>();
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id); type.add("NUMBER");
            param.add(transactionId); type.add("NUMBER");

            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().APPRAISAL_STEP3_SELECT_BY_EMP_AND_TRANSACTION,
                    param, type);

            while(rst.next()){
                Appraisal.Step3 item = new Appraisal.Step3();
                item.setId(rst.getLong("id"));
                item.setEmployeeId(rst.getInt("emp_number"));
                item.setDueDate(new java.util.Date(rst.getDate("due_date").getTime()));
                item.setGoalTitle(rst.getString("goal_title"));
                item.setDescription(rst.getString("description"));
                item.setGoalResult(rst.getString("goal_result"));
                item.setRateIndex(rst.getInt("rate_index"));
                item.setIsTransaction(rst.getInt("istransaction"));
                record.add(item);
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

    public List<Appraisal.Step3> getAllAppraisalStep3(int id){
        Connection connection = null;
        List<Appraisal.Step3> record = new ArrayList<Appraisal.Step3>();
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().APPRAISAL_STEP3_SELECT,
                    param, type);

            while(rst.next()){
                Appraisal.Step3 item = new Appraisal.Step3();
                item.setId(rst.getLong("id"));
                item.setEmployeeId(rst.getInt("emp_number"));
                item.setDueDate(new java.util.Date(rst.getDate("due_date").getTime()));
                item.setGoalTitle(rst.getString("goal_title"));
                item.setDescription(rst.getString("description"));
                item.setGoalResult(rst.getString("goal_result"));
                item.setRateIndex(rst.getInt("rate_index"));
                item.setIsTransaction(rst.getInt("istransaction"));
                record.add(item);
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

    public Appraisal.Step3 getAppraisalStep3(int id){
        Connection connection = null;
        Appraisal.Step3 record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().APPRAISAL_STEP3_SELECT_BY_ID,
                    param, type);

            while(rst.next()){
                Appraisal.Step3 item = new Appraisal.Step3();
                item.setId(rst.getLong("id"));
                item.setEmployeeId(rst.getInt("emp_number"));
                item.setDueDate(new java.util.Date(rst.getDate("due_date").getTime()));
                item.setGoalTitle(rst.getString("goal_title"));
                item.setDescription(rst.getString("description"));
                item.setGoalResult(rst.getString("goal_result"));
                item.setRateIndex(rst.getInt("rate_index"));
                item.setIsTransaction(rst.getInt("istransaction"));
                record = item;
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

    public int createNewAppraisalStep3(Appraisal.Step3 step3){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(step3.getEmployeeId()); type.add("NUMBER");
            param.add(step3.getDueDate()); type.add("DATE");
            param.add(step3.getGoalTitle()); type.add("STRING");
            param.add(step3.getDescription()); type.add("STRING");
            param.add(step3.getGoalResult()); type.add("STRING");
            param.add(step3.getRateIndex()); type.add("NUMBER");
            if(step3.getTransactionId() == 0){
                param.add(step3.getTransactionId()); type.add("NUMBER_NULL");
            }else{
                param.add(step3.getTransactionId()); type.add("NUMBER");
            }
            param.add(step3.getIsTransaction()); type.add("NUMBER");

            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().APPRAISAL_STEP3_INSERT,
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

    public int updateAppraisalStep3(Appraisal.Step3 step3){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(step3.getEmployeeId()); type.add("NUMBER");
            param.add(step3.getDueDate()); type.add("DATE");
            param.add(step3.getGoalTitle()); type.add("STRING");
            param.add(step3.getDescription()); type.add("STRING");
            param.add(step3.getGoalResult()); type.add("STRING");
            param.add(step3.getRateIndex()); type.add("NUMBER");
            if(step3.getTransactionId() == 0){
                param.add(step3.getTransactionId()); type.add("NUMBER_NULL");
            }else{
                param.add(step3.getTransactionId()); type.add("NUMBER");
            }
            param.add(step3.getIsTransaction()); type.add("NUMBER");
            //set index
            param.add(step3.getId()); type.add("NUMBER");

            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().APPRAISAL_STEP3_UPDATE,
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

    public int deleteAppraisalStep3(List<Integer> ids){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{

            for(int id : ids){
                param.add(id); type.add("NUMBER");

                connection = this.getConnection(false);
                int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().APPRAISAL_STEP3_DELETE,
                        param, type);
            }
            return ids.size();
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


    public Appraisal.Step4 getAppraisalStep4(int id){
        Connection connection = null;
        Appraisal.Step4 record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().APPRAISAL_STEP4_SELECT,
                    param, type);

            while(rst.next()){
                Appraisal.Step4 item = new Appraisal.Step4();
                item.setEmployeeId(rst.getInt("emp_number"));
                item.setStatement(rst.getString("statement"));
                item.setImprovement(rst.getString("improvement"));
                item.setIsTransaction(rst.getInt("istransaction"));
                record = item;
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

    public int createNewAppraisalStep4(Appraisal.Step4 step4){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(step4.getEmployeeId()); type.add("NUMBER");
            param.add(step4.getStatement()); type.add("STRING");
            param.add(step4.getImprovement()); type.add("STRING");
            if(step4.getTransactionId() == 0){
                param.add(step4.getTransactionId()); type.add("NUMBER_NULL");
            }else{
                param.add(step4.getTransactionId()); type.add("NUMBER");
            }
            param.add(step4.getIsTransaction()); type.add("NUMBER");

            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().APPRAISAL_STEP4_INSERT,
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

    public int deleteAppraisalStep4(List<Integer> ids){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{

            for(int id : ids){
                param.add(id); type.add("NUMBER");

                connection = this.getConnection(false);
                int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().APPRAISAL_STEP4_DELETE,
                        param, type);
            }
            return ids.size();
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

    public boolean processAppraisalForEmployee(long employeeId, long nextApprovingOfficer, long transactionId){
        Connection connection = null;
        try{

            connection = this.getConnection(true);
            startTransaction(connection);
            processAppraisalForEmployee(employeeId, nextApprovingOfficer, transactionId, connection);
            commitTransaction(connection);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            try{ rollbackTransaction(connection); }catch(Exception ex){}
            return false;
        }finally{
            try{ closeConnection(connection); }catch(Exception ex){}
        }
    }
    
    public boolean processAppraisalForEmployee(long employeeId, long nextApprovingOfficer, long transactionId, Connection connection){
        
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(employeeId); type.add("NUMBER");
            param.add(transactionId); type.add("NUMBER");
            param.add(nextApprovingOfficer); type.add("NUMBER");
            
            CallableStatement cs = executeProcedure(connection,
                    DatabaseQueryLoader.getQueryAssignedConstant().APPRAISAL_FINALIZER,
                    param, type, true, 4);
            if(cs == null){
                return false;
            }
            int returnValue = cs.getInt(4);
            if(returnValue != 0){
                throw new Exception("Unable to save due to return value greater than zero: " + returnValue);
            }
            // For self Report.....................
             CallableStatement cs_self = executeProcedure(connection,
                    DatabaseQueryLoader.getQueryAssignedConstant().APPRAISAL_FINALIZER_SELF,
                    param, type, true, 4);
            if(cs_self == null){
                return false;
            }
            int returnValueSelf = cs_self.getInt(4);
            if(returnValueSelf != 0){
                throw new Exception("Unable to save due to return value greater than zero: " + returnValueSelf);
            }
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean processApproverAppraisalForEmployee(long employeeId, long transactionId, long supervisor, String status, Connection connection){

        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(employeeId); type.add("NUMBER");
            param.add(transactionId); type.add("NUMBER");
            param.add(supervisor); type.add("NUMBER");
            param.add(status); type.add("STRING");

            CallableStatement cs = executeProcedure(connection, DatabaseQueryLoader.getQueryAssignedConstant().APPRAISAL_APPROVER_FINALIZER,
                    param, type, true, 5);
            if(cs == null){
                return false;
            }
            System.out.println("cs.getInt(5) ===="+cs.getInt(5));
            int returnValue = cs.getInt(5);
            if(returnValue != 0){
                throw new Exception("Unable to save due to return value greater than zero: " + returnValue);
            }
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /* ------------- Appraisal View Based on Appraisal Id --------------------*/
    public AppraisalInformation.Step1 getAppraisalStep1View(long id){
        Connection connection = null;
        AppraisalInformation.Step1 record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().APPRAISAL_STEP1_VIEW,
                    param, type);

            if(rst.next()){
                AppraisalInformation.Step1 item = new AppraisalInformation.Step1();
                item.setAppraisalId(rst.getLong("appraisal_id"));
                item.setStartDate(new java.util.Date(rst.getDate("start_period").getTime()));
                item.setEndDate(new java.util.Date(rst.getDate("end_period").getTime()));
                item.setCriteriaDescription(rst.getString("criteria"));
                item.setFirstName(rst.getString("firstname"));
                item.setLastName(rst.getString("lastname"));
                item.setAppliedLevel(rst.getInt("applied_level"));
                record = item;
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

    public List<AppraisalInformation.Step2> getAppraisalStep2View(long id){
        Connection connection = null;
        List<AppraisalInformation.Step2> record = new ArrayList<AppraisalInformation.Step2>();
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().APPRAISAL_STEP2_VIEW,
                    param, type);

            while(rst.next()){
                AppraisalInformation.Step2 item = new AppraisalInformation.Step2();
                item.setAppraisalId(rst.getLong("appraisal_id"));
                item.setGroupId(rst.getInt("group_id"));
                item.setGroupName(rst.getString("group_name"));
                item.setCompetenceName(rst.getString("comptence_desc"));
                item.setRateIndex(rst.getInt("rate_index"));
                item.setRateDescription(rst.getString("rate_text"));
                item.setFirstName(rst.getString("firstname"));
                item.setLastName(rst.getString("lastname"));
                item.setSupervisor(rst.getLong("supervisor"));
                record.add(item);
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

    public List<AppraisalInformation.Step2_Statement> getAppraisalStep2StatementView(long id){
        Connection connection = null;
        List<AppraisalInformation.Step2_Statement> record = new ArrayList<AppraisalInformation.Step2_Statement>();
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().APPRAISAL_STEP2_STMT_VIEW,
                    param, type);

            while(rst.next()){
                AppraisalInformation.Step2_Statement item = new AppraisalInformation.Step2_Statement();
                item.setAppraisalId(rst.getLong("appraisal_id"));
                item.setGroupId(rst.getInt("group_id"));
                item.setStatement(rst.getString("statement"));
                item.setFirstName(rst.getString("firstname"));
                item.setLastName(rst.getString("lastname"));
                item.setSupervisor(rst.getLong("supervisor"));
                record.add(item);
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

    public List<AppraisalInformation.Step3> getAppraisalStep3View(long id){
        Connection connection = null;
        List<AppraisalInformation.Step3> record = new ArrayList<AppraisalInformation.Step3>();
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().APPRAISAL_STEP3_VIEW,
                    param, type);

            while(rst.next()){
                AppraisalInformation.Step3 item = new AppraisalInformation.Step3();
                item.setAppraisalId(rst.getLong("appraisal_id"));
                item.setDueDate(new java.util.Date(rst.getDate("due_date").getTime()));
                item.setGoalTitle(rst.getString("goal_title"));
                item.setDescription(rst.getString("description"));
                item.setGoalResult(rst.getString("goal_result"));
                item.setRateIndex(rst.getInt("rate_index"));
                item.setRateDescription(rst.getString("rate_text"));
                item.setFirstName(rst.getString("firstname"));
                item.setLastName(rst.getString("lastname"));
                record.add(item);
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

    public List<AppraisalInformation.Step4> getAppraisalStep4View(long id){
        Connection connection = null;
        List<AppraisalInformation.Step4> record = new ArrayList<AppraisalInformation.Step4>();
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().APPRAISAL_STEP4_VIEW,
                    param, type);

            while(rst.next()){
                AppraisalInformation.Step4 item = new AppraisalInformation.Step4();
                item.setAppraisalId(rst.getLong("appraisal_id"));
                item.setStatement(rst.getString("statement"));
                item.setImprovement(rst.getString("improvement"));
                item.setFirstName(rst.getString("firstname"));
                item.setLastName(rst.getString("lastname"));
                record.add(item);
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

    public List<Appraisal.RatingSummary> getRatingSummary(long id){

        Connection connection = null;
        List<Appraisal.RatingSummary> record = new ArrayList<Appraisal.RatingSummary>();
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id); type.add("NUMBER");
            param.add(id); type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().APPRAISAL_RATING_SUMMARY,
                    param, type);

            while(rst.next()){
                Appraisal.RatingSummary item = new Appraisal.RatingSummary();
                item.setSection(rst.getString("description"));
                item.setRating(rst.getFloat("rate_index"));
                item.setRateText("");
                record.add(item);
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

    public List<Appraisal.RatingSummary> getRatingSummary(long employeeId, long transactionId){

        Connection connection = null;
        List<Appraisal.RatingSummary> record = new ArrayList<Appraisal.RatingSummary>();
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(employeeId); type.add("NUMBER");
            param.add(transactionId); type.add("NUMBER");
            param.add(employeeId); type.add("NUMBER");
            param.add(transactionId); type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().APPRAISAL_TRANSACTION_RATING_SUMMARY,
                    param, type);

            while(rst.next()){
                Appraisal.RatingSummary item = new Appraisal.RatingSummary();
                item.setSection(rst.getString("description"));
                item.setRating(rst.getFloat("rate_index"));
                item.setRateText("");
                record.add(item);
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
}
