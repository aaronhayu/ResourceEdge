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
import org.tenece.web.data.EmployeePicture;
import org.tenece.web.data.EmployeePicture;

/**
 *
 * @author jeffry.amachree
 */
public class EmployeePictureDAO extends BaseDAO{
    
    /**
     * Creates a new instance of DepartmentDAO
     */
    public EmployeePictureDAO() {
    }
    
    /* ************* EmployeePictures ********** */
    public List<EmployeePicture> getAllEmployeePictures(){
        Connection connection = null;
        List<EmployeePicture> records = new ArrayList<EmployeePicture>();
        try{
            connection = this.getConnection(true);
            ResultSet rst = this.executeQuery(DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_PICTURE_SELECT, connection);
            
            while(rst.next()){
                EmployeePicture item = new EmployeePicture();
                item.setId(rst.getInt("picid"));
                item.setEmployeeNumber(rst.getInt("emp_number"));
                item.setPictureType(rst.getString("pic_type"));
                item.setFileName(rst.getString("filename"));
                item.setFileSize(rst.getString("filesize"));
                item.setFileType(rst.getString("filetype"));
                item.setPicture(rst.getBytes("picture"));
                
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
    
    public EmployeePicture getEmployeePictureByEmployee(int id){
        Connection connection = null;
        EmployeePicture record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_PICTURE_SELECT,
                    param, type);
            
            while(rst.next()){
                EmployeePicture item = new EmployeePicture();
                item.setId(rst.getInt("picid"));
                item.setEmployeeNumber(id);
                item.setPictureType(rst.getString("pic_type"));
                item.setFileName(rst.getString("filename"));
                item.setFileSize(rst.getString("filesize"));
                item.setFileType(rst.getString("filetype"));
                item.setPicture(rst.getBytes("picture"));
                
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
    
    public int createNewEmployeePicture(EmployeePicture EmployeePicture){
        Connection connection = null;
        EmployeePicture record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(EmployeePicture.getEmployeeNumber()); type.add("NUMBER");
            param.add(EmployeePicture.getPictureType()); type.add("STRING");
            param.add(EmployeePicture.getFileName()); type.add("STRING");
            param.add(EmployeePicture.getFileSize()); type.add("STRING");
            param.add(EmployeePicture.getFileType()); type.add("STRING");
            param.add(EmployeePicture.getPicture()); type.add("BYTES");
            
            System.out.println("QUERY:" + DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_PICTURE_INSERT);
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_PICTURE_INSERT,
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
    
    public int updateEmployeePicture(EmployeePicture EmployeePicture){
        Connection connection = null;
        Department record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(EmployeePicture.getEmployeeNumber()); type.add("NUMBER");
            param.add(EmployeePicture.getPictureType()); type.add("STRING");
            param.add(EmployeePicture.getFileName()); type.add("STRING");
            param.add(EmployeePicture.getFileSize()); type.add("STRING");
            param.add(EmployeePicture.getFileType()); type.add("STRING");
            param.add(EmployeePicture.getPicture()); type.add("BYTES");
            param.add(EmployeePicture.getId()); type.add("NUMBER");
            
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_PICTURE_UPDATE,
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
    
    public int deleteEmployeePicture(EmployeePicture EmployeePicture){
        Connection connection = null;
        EmployeePicture record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            
            param.add(EmployeePicture.getEmployeeNumber()); type.add("NUMBER");
            
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_PICTURE_DELETE,
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
