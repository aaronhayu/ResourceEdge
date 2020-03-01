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
import org.tenece.web.data.Department;
import org.tenece.web.data.Skill;
import org.tenece.web.data.Skill;

/**
 *
 * @author jeffry.amachree
 */
public class SkillDAO extends BaseDAO{
    
    /**
     * Creates a new instance of DepartmentDAO
     */
    public SkillDAO() {
    }
    
    /* ************* Skills ********** */
    public List<Skill> getAllSkills(){
        return getAllSkills(null, null);
    }
    public List<Skill> getAllSkills(String criteria, String searchText){
        Connection connection = null;
        List<Skill> records = new ArrayList<Skill>();
        try{
            connection = this.getConnection(true);
            ResultSet rst = null;
            if(criteria == null || searchText == null){
                rst = this.executeQuery(DatabaseQueryLoader.getQueryAssignedConstant().SKILL_SELECT, connection);
            }else{
                String query = "";

                if(criteria.equalsIgnoreCase("CODE")){
                    query = DatabaseQueryLoader.getQueryAssignedConstant().SKILL_SELECT_SEARCH_BY_CODE;
                }else if(criteria.equalsIgnoreCase("NAME")){
                    query = DatabaseQueryLoader.getQueryAssignedConstant().SKILL_SELECT_SEARCH_BY_NAME;
                }else if(criteria.equalsIgnoreCase("DESC")){
                    query = DatabaseQueryLoader.getQueryAssignedConstant().SKILL_SELECT_SEARCH_BY_DESC;
                }

                query = query.replaceAll("_SEARCH_", searchText);

                rst = this.executeQuery(query, connection);
            }

            while(rst.next()){
                Skill item = new Skill();
                item.setId(rst.getInt("skill_id"));
                item.setSkillCode(rst.getString("skill_code"));
                item.setSkillName(rst.getString("skill_name"));
                item.setSkillDescription(rst.getString("skill_description"));
                item.setCompanyCode(rst.getString("company_code"));
                
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

    public List<Skill> getAllSkillsByCompany(String code){
        return getAllSkillsByCompany(code, null, null);
    }
    public List<Skill> getAllSkillsByCompany(String code, String criteria, String searchText){
        Connection connection = null;
        List<Skill> records = new ArrayList<Skill>();

        Vector param =new Vector();
        Vector type = new Vector();

        try{
            param.add(code); type.add("STRING");

            connection = this.getConnection(true);
            ResultSet rst = null;
            if(criteria == null || searchText == null){
                rst = this.executeQuery(DatabaseQueryLoader.getQueryAssignedConstant().SKILL_SELECT, connection);
            }else{
                String query = "";

                if(criteria.equalsIgnoreCase("CODE")){
                    query = DatabaseQueryLoader.getQueryAssignedConstant().SKILL_SELECT_BY_COMPANY_SEARCH_BY_CODE;
                }else if(criteria.equalsIgnoreCase("NAME")){
                    query = DatabaseQueryLoader.getQueryAssignedConstant().SKILL_SELECT_BY_COMPANY_SEARCH_BY_NAME;
                }else if(criteria.equalsIgnoreCase("DESC")){
                    query = DatabaseQueryLoader.getQueryAssignedConstant().SKILL_SELECT_BY_COMPANY_SEARCH_BY_DESC;
                }

                query = query.replaceAll("_SEARCH_", searchText);

                rst = this.executeParameterizedQuery(connection, query, param, type);
            }

            while(rst.next()){
                Skill item = new Skill();
                item.setId(rst.getInt("skill_id"));
                item.setSkillCode(rst.getString("skill_code"));
                item.setSkillName(rst.getString("skill_name"));
                item.setSkillDescription(rst.getString("skill_description"));
                item.setCompanyCode(rst.getString("company_code"));

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

    public Skill getSkillById(int id){
        Connection connection = null;
        Skill record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().SKILL_SELECT_BY_ID,
                    param, type);
            
            while(rst.next()){
                Skill item = new Skill();
                item.setId(rst.getInt("skill_id"));
                item.setSkillCode(rst.getString("skill_code"));
                item.setSkillName(rst.getString("skill_name"));
                item.setSkillDescription(rst.getString("skill_description"));
                item.setCompanyCode(rst.getString("company_code"));
                
                record = item;
            }
        }catch(Exception e){
            
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return record;
    }
    
    public int createNewSkill(Skill Skill){
        Connection connection = null;
        Skill record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(Skill.getSkillCode()); type.add("STRING");
            param.add(Skill.getSkillName()); type.add("STRING");
            param.add(Skill.getSkillDescription()); type.add("STRING");
            param.add(Skill.getCompanyCode()); type.add("STRING");
            
            System.out.println("QUERY:" + DatabaseQueryLoader.getQueryAssignedConstant().SKILL_INSERT);
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().SKILL_INSERT,
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
    
    public int updateSkill(Skill Skill){
        Connection connection = null;
        Department record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(Skill.getSkillCode()); type.add("STRING");
            param.add(Skill.getSkillName()); type.add("STRING");
            param.add(Skill.getSkillDescription()); type.add("STRING");
            param.add(Skill.getCompanyCode()); type.add("STRING");
            param.add(Skill.getId()); type.add("NUMBER");
            
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().SKILL_UPDATE,
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
    
    public int deleteSkill(Skill Skill){
        Connection connection = null;
        Skill record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            
            param.add(Skill.getId()); type.add("NUMBER");
            
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().SKILL_DELETE,
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
}
