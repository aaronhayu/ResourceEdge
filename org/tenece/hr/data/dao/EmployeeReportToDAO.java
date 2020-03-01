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
import org.tenece.web.data.EmployeeSupervisor;
import org.tenece.web.data.EmployeeSupervisor;
import org.tenece.web.data.EmployeeSupervisor;

/**
 *
 * @author jeffry.amachree
 */
public class EmployeeReportToDAO extends BaseDAO{
    
    /**
     * Creates a new instance of DepartmentDAO
     */
    public EmployeeReportToDAO() {
    }
    
    
    public List<EmployeeSupervisor> getEmployeeSupervisorBy_Sub(int id){
        Connection connection = null;
        List<EmployeeSupervisor> records = new ArrayList<EmployeeSupervisor>();
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_REPORT_TO_SELECT_BY_SUB,
                    param, type);
            
            while(rst.next()){
                EmployeeSupervisor item = new EmployeeSupervisor();
                item.setSubEmployeeId(rst.getInt("sup_emp_number"));
                item.setSupEmployeeId(rst.getInt("sup_emp_number"));
                item.setReportingMode(rst.getInt("reporting_mode"));
                
                records.add(item);
            }
        }catch(Exception e){
            
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return records;
    }
    
    public int createNewEmployeeSupervisor(EmployeeSupervisor EmployeeSupervisor){
        Connection connection = null;
        EmployeeSupervisor record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(EmployeeSupervisor.getSubEmployeeId()); type.add("NUMBER");
            param.add(EmployeeSupervisor.getSupEmployeeId()); type.add("NUMBER");
            param.add(EmployeeSupervisor.getReportingMode()); type.add("NUMBER");
            
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_REPORT_TO_INSERT,
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
    
    public int updateEmployeeSupervisor(EmployeeSupervisor employeeSupervisor, 
            EmployeeSupervisor init_EmployeeSupervisor){
        Connection connection = null;
        Department record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(employeeSupervisor.getSubEmployeeId()); type.add("NUMBER");
            param.add(employeeSupervisor.getSupEmployeeId()); type.add("NUMBER");
            param.add(employeeSupervisor.getReportingMode()); type.add("NUMBER");
            
            param.add(init_EmployeeSupervisor.getSubEmployeeId()); type.add("NUMBER");
            param.add(init_EmployeeSupervisor.getReportingMode()); type.add("NUMBER");
            
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_REPORT_TO_UPDATE_BY_SUB_AND_MODE,
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
    
    public int deleteEmployeeSupervisor(EmployeeSupervisor employeeSupervisor){
        Connection connection = null;
        EmployeeSupervisor record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            
            param.add(employeeSupervisor.getSubEmployeeId()); type.add("NUMBER");
            param.add(employeeSupervisor.getReportingMode()); type.add("NUMBER");
            
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EMPLOYEE_REPORT_TO_DELETE_BY_SUB_AND_MODE,
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
