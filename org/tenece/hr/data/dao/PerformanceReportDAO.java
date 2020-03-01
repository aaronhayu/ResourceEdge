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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import org.tenece.web.data.PerformanceReport;
import org.tenece.web.data.PerformanceTarget;

/**
 *
 * @author jeffry.amachree
 */
public class PerformanceReportDAO extends BaseDAO{
    
    /**
     * Creates a new instance of DepartmentDAO
     */
    public PerformanceReportDAO() {
    }
    
    /* ************* Performance Target ********** */
    public List<PerformanceReport> getAllPerformanceReports(){
        Connection connection = null;
        List<PerformanceReport> records = new ArrayList<PerformanceReport>();
        try{
            connection = this.getConnection(true);
            ResultSet rst = this.executeQuery(DatabaseQueryLoader.getQueryAssignedConstant().PERFORMANCE_REPORT_SELECT, connection);
            
            while(rst.next()){
                PerformanceReport report = new PerformanceReport();
                report.setId(rst.getInt("idx"));
                report.setTargetId(rst.getInt("target_id"));
                report.setProgress(rst.getString("progress_report"));
                report.setReportDate(new java.util.Date(rst.getDate("report_date").getTime()));
                report.setAmountAcheived(rst.getInt("amount_acheived"));
                report.setStatus(rst.getString("status"));
                report.setNote(rst.getString("note"));
                
                records.add(report);
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
    
    public PerformanceReport getAllPerformanceReportById(int id){
        Connection connection = null;
        PerformanceReport records = new PerformanceReport();
        
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            //set parameter
            param.add(id); type.add("NUMBER");
            
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().PERFORMANCE_REPORT_SELECT_BY_ID, param, type);
            
            if(rst.next()){
                PerformanceReport report = new PerformanceReport();
                report.setId(rst.getInt("idx"));
                report.setTargetId(rst.getInt("target_id"));
                report.setProgress(rst.getString("progress_report"));
                report.setReportDate(new java.util.Date(rst.getDate("report_date").getTime()));
                report.setAmountAcheived(rst.getInt("amount_acheived"));
                report.setStatus(rst.getString("status"));
                report.setNote(rst.getString("note"));
                
                records = report;
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
    
    
    public List<PerformanceReport> getAllPerformanceReportByTarget(int targetId){
        Connection connection = null;
        List<PerformanceReport> records = new ArrayList<PerformanceReport>();
        
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            //set parameter
            param.add(targetId); type.add("NUMBER");
            
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().PERFORMANCE_REPORT_SELECT_BY_TARGET, param, type);
            
            while(rst.next()){
                PerformanceReport report = new PerformanceReport();
                report.setId(rst.getInt("idx"));
                report.setTargetId(rst.getInt("target_id"));
                report.setProgress(rst.getString("progress_report"));
                report.setReportDate(new java.util.Date(rst.getDate("report_date").getTime()));
                report.setAmountAcheived(rst.getInt("amount_acheived"));
                report.setStatus(rst.getString("status"));
                report.setNote(rst.getString("note"));
                
                records.add(report);
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
    
    public int updatePerformanceReport(PerformanceReport report){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            connection = this.getConnection(false);
            //start transaction
            this.startTransaction(connection);
            
            param.add(report.getProgress()); type.add("STRING");
            param.add(report.getReportDate()); type.add("DATE");
            param.add(report.getAmountAcheived()); type.add("NUMBER");
            param.add(report.getStatus()); type.add("STRING");
            param.add(report.getNote()); type.add("STRING");
            param.add(report.getId()); type.add("NUMBER");
            
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().PERFORMANCE_REPORT_UPDATE,
                    param, type);
            
            if(i == 0){ throw new Exception("Unable to save target report"); }
            
            //get status and check if we should finalize the status of target to A
            if(report.getStatus().trim().equalsIgnoreCase("A")){
                //get target
                PerformanceTargetDAO targetDAO = new PerformanceTargetDAO();
                PerformanceTarget target = targetDAO.getPerformanceTargetById(report.getTargetId());
                //set status
                target.setStatus("A");
                targetDAO.updatePerformanceTarget(target, connection);
            }
            
            //commit transaction
            this.commitTransaction(connection);
            return 1;
            
        }catch(Exception e){
            e.printStackTrace();
            try {
                this.rollbackTransaction(connection);
            } catch (SQLException ex) {
                ex.printStackTrace();
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
    
    public int insertPerformanceReport(PerformanceReport report){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            connection = this.getConnection(false);
            //start transaction
            this.startTransaction(connection);
            
            param.add(report.getTargetId()); type.add("NUMBER");
            param.add(report.getProgress()); type.add("STRING");
            param.add(report.getReportDate()); type.add("DATE");
            param.add(report.getAmountAcheived()); type.add("NUMBER");
            param.add(report.getStatus()); type.add("STRING");
            param.add(report.getNote()); type.add("STRING");
            
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().PERFORMANCE_REPORT_INSERT,
                    param, type);
            
            if(i == 0){ throw new Exception("Unable to save target report"); }
            
            //get status and check if we should finalize the status of target to A
            if(report.getStatus().trim().equalsIgnoreCase("A")){
                //get target
                PerformanceTargetDAO targetDAO = new PerformanceTargetDAO();
                PerformanceTarget target = targetDAO.getPerformanceTargetById(report.getTargetId());
                //set status
                target.setStatus("A");
                targetDAO.updatePerformanceTarget(target, connection);
            }
            
            //commit transaction
            this.commitTransaction(connection);
            return 1;
        }catch(Exception e){
            e.printStackTrace();
            try {
                this.rollbackTransaction(connection);
            } catch (SQLException ex) {
                ex.printStackTrace();
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
    
    public int deletePerformanceReport(PerformanceReport report){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(report.getId()); type.add("NUMBER");
            
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().PERFORMANCE_REPORT_DELETE,
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
    
    public int deletePerformanceReports(List<Integer> reports){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            
            
            connection = this.getConnection(false);
            this.startTransaction(connection);
            for(int report : reports){
                param.add(report); type.add("NUMBER");
                int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().PERFORMANCE_REPORT_DELETE,
                        param, type);
            }
            this.commitTransaction(connection);
            return 1;
        }catch(Exception e){
            try {
                this.rollbackTransaction(connection);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
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
}
