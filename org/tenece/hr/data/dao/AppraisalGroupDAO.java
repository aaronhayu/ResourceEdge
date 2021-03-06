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
import org.tenece.web.data.AppraisalGroup;
import org.tenece.web.data.AppraisalGroup;

/**
 *
 * @author jeffry.amachree
 */
public class AppraisalGroupDAO extends BaseDAO{
    
    /**
     * Creates a new instance of DepartmentDAO
     */
    public AppraisalGroupDAO() {
    }
    
    /* ************* AppraisalGroup ********** */
    public List<AppraisalGroup> getAllAppraisalGroups(){
        Connection connection = null;
        List<AppraisalGroup> records = new ArrayList<AppraisalGroup>();
        try{
            connection = this.getConnection(true);
            ResultSet rst = this.executeQuery(DatabaseQueryLoader.getQueryAssignedConstant().APPRAISAL_GROUP_SELECT, connection);
            
            while(rst.next()){
                AppraisalGroup item = new AppraisalGroup();
                item.setId(rst.getInt("id"));
                item.setDescription(rst.getString("description"));
                item.setNote(rst.getString("note"));
                item.setCriteriaId(rst.getInt("criteria_id"));
                
                records.add(item);
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
    
    public AppraisalGroup getAppraisalGroupById(int id){
        Connection connection = null;
        AppraisalGroup record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().APPRAISAL_GROUP_SELECT_BY_ID,
                    param, type);
            
            while(rst.next()){
                AppraisalGroup item = new AppraisalGroup();
                item.setId(rst.getInt("id"));
                item.setDescription(rst.getString("description"));
                item.setNote(rst.getString("note"));
                item.setCriteriaId(rst.getInt("criteria_id"));
                item.setForJobSpecific(rst.getInt("for_job_specific"));
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

    public List<AppraisalGroup> getAppraisalGroupByCriteria(int id){
        Connection connection = null;
        List<AppraisalGroup> records = new ArrayList<AppraisalGroup>();
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().APPRAISAL_GROUP_SELECT_BY_CRITERIA,
                    param, type);

            while(rst.next()){
                AppraisalGroup item = new AppraisalGroup();
                item.setId(rst.getInt("id"));
                item.setDescription(rst.getString("description"));
                item.setNote(rst.getString("note"));
                item.setCriteriaId(rst.getInt("criteria_id"));
                item.setForJobSpecific(rst.getInt("for_job_specific"));
                records.add(item);
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
    
    public int createNewAppraisalGroup(AppraisalGroup appraisalGroup){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(appraisalGroup.getDescription()); type.add("STRING");
            param.add(appraisalGroup.getNote()); type.add("STRING");
            param.add(appraisalGroup.getCriteriaId()); type.add("NUMBER");
            param.add(appraisalGroup.getForJobSpecific()); type.add("NUMBER");
            System.out.println("QUERY:" + DatabaseQueryLoader.getQueryAssignedConstant().APPRAISAL_GROUP_INSERT);
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().APPRAISAL_GROUP_INSERT,
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
    
    public int updateAppraisalGroup(AppraisalGroup appraisalGroup){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(appraisalGroup.getDescription()); type.add("STRING");
            param.add(appraisalGroup.getNote()); type.add("STRING");
            param.add(appraisalGroup.getCriteriaId()); type.add("NUMBER");
            param.add(appraisalGroup.getForJobSpecific()); type.add("NUMBER");
            param.add(appraisalGroup.getId()); type.add("NUMBER");
            
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().APPRAISAL_GROUP_UPDATE,
                    param, type);
            
            return i;
        }catch(Exception e){            System.out.print("appraisalGroup.getForJobSpecific() ==="+appraisalGroup.getForJobSpecific());

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
    
    public int deleteAppraisalGroup(List<Integer> ids){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{

            for(int id : ids){
                param.add(id); type.add("NUMBER");

                connection = this.getConnection(false);
                int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().APPRAISAL_GROUP_DELETE,
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
}
