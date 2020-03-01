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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.tenece.hr.data.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import org.tenece.hr.db.DatabaseQueryLoader;
import java.util.List;
import java.util.Vector;
import org.tenece.web.data.ControlData;
import org.tenece.web.data.ScheduleLog;

/**
 *
 * @author jeffry.amachree
 */
public class ApplicationDAO extends BaseDAO{

    /**
     * This method select all countries in the database
     * @return List of Country stored in the database
     */
    public List<ControlData> getCountryList(){
        return getControlData(DatabaseQueryLoader.getQueryAssignedConstant().COUNTRY_SELECT);
    }

    public List<ControlData> getRelationshipList(){
        return getControlData(DatabaseQueryLoader.getQueryAssignedConstant().RELATIONSHIP_SELECT);
    }

    public List<ScheduleLog> getAllActiveScheduledLog(){
        Connection connection = null;
        List<ScheduleLog> records = new ArrayList<ScheduleLog>();
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            connection = this.getConnection(true);

            ResultSet rst = null;
            rst = this.executeQuery(DatabaseQueryLoader.getQueryAssignedConstant().SCHEDULER_SELECT,
                        connection);

            while(rst.next()){
                ScheduleLog item = new ScheduleLog();
                item.setCode(rst.getString("code"));
                item.setStartTime(new Date(rst.getDate("start_time").getTime()));
                item.setStatus(rst.getString("status"));

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
}
