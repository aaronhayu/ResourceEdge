/*
 * (c) Copyright 2010 The Tenece Professional Services.
 *
 * Created on 6 February 2009, 09:57
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
 * even if Tenece Professional Services has been advised of the possibility of such damages.
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
import org.tenece.web.data.Chart;

/**
 * Tenece Professional Services Limited
 * @author amachree
 */
public class DashBoardDAO extends BaseDAO{

    public List<Chart> getEmployeeChartCount(String viewType){

        Connection connection = null;
        List<Chart> records = new ArrayList<Chart>();
        
        try{
            connection = this.getConnection(true);

            ResultSet rst = null;
            if(viewType.trim().equalsIgnoreCase("DEPT")){
                rst = this.executeQuery(DatabaseQueryLoader.getQueryAssignedConstant().DASHBOARD_EMPLOYEE_COUNT_BY_DEPT,
                            connection);
            }else if(viewType.trim().equalsIgnoreCase("LOCATION")){
                rst = this.executeQuery(DatabaseQueryLoader.getQueryAssignedConstant().DASHBOARD_EMPLOYEE_COUNT_BY_LOCATION,
                            connection);
            }else if(viewType.trim().equalsIgnoreCase("CATEGORY")){
                rst = this.executeQuery(DatabaseQueryLoader.getQueryAssignedConstant().DASHBOARD_EMPLOYEE_COUNT_BY_CATEGORY,
                            connection);
            }

            while(rst.next()){
                Chart item = new Chart();
                item.setKey(rst.getString("description"));
                item.setdValue(rst.getDouble("counted"));

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

    public double getRetentionRate(Date startDate, Date endDate){
        Connection connection = null;
        Vector param = new Vector();
        Vector type = new Vector();

        try{
            connection = this.getConnection(true);
            startTransaction(connection);

            //set values in parameter
            param.add(startDate); type.add("DATE");
            param.add(endDate); type.add("DATE");

            ResultSet rst = this.executeParameterizedQuery(connection,
                    DatabaseQueryLoader.getQueryAssignedConstant().DASHBOARD_RETENTION_RATE,
                    param, type);
            if(rst.next()){
                Double percentage = rst.getDouble(1);
                if(percentage == null){
                    percentage = 0d;
                }
                return percentage;
            }else{
                return 0;
            }
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {

            }
        }
    }

    public double getPromotionRate(){
        Connection connection = null;

        try{
            connection = this.getConnection(true);
            startTransaction(connection);

            ResultSet rst = this.executeQuery(DatabaseQueryLoader.getQueryAssignedConstant().DASHBOARD_PROMOTION_RATE,
                    connection);
            if(rst.next()){
                Double percentage = rst.getDouble(1);
                if(percentage == null){
                    percentage = 0d;
                }
                return percentage;
            }else{
                return 0;
            }
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {

            }
        }
    }
    
}
