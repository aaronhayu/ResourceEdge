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

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import org.tenece.hr.db.EntityProcessor;
import org.tenece.web.common.ConfigReader;
import org.tenece.web.common.DateUtility;
import org.tenece.web.data.ControlData;
import org.tenece.web.filter.ApplicationFilter;

/**
 *
 * @author jeffry.amachree
 */
public abstract class BaseDAO implements Serializable {
	private EntityProcessor entityProcessor;
	
	private void init(){
		setEntityProcessor(EntityProcessor.getInstance());
	}

	private EntityProcessor getEntityProcessor() {
		if(entityProcessor == null){
			init();
		}
		return entityProcessor;
	}

	private void setEntityProcessor(EntityProcessor entityProcessor) {
		this.entityProcessor = entityProcessor;
	}

	public Connection getConnection(boolean readOnly) throws Exception {
		Connection connection = getEntityProcessor().acquireConnection();
		connection.setReadOnly(readOnly);
		return connection;
	}
        
        public void closeConnection(Connection connection){
            try{
                connection.close();
                connection = null;
            }catch(Exception e){
                
            }
        }

        public ResultSet executeReportQuery(String query, Connection connection) throws Exception{
            Statement stmt = connection.createStatement();
            return stmt.executeQuery(query);
        }//:

        public ResultSet executeQuery(String query, Connection connection) throws Exception{
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            return stmt.executeQuery(query);
        }//:
        
        public int executeUpdate(Connection connection, String query) throws Exception{
            Statement stmt = connection.createStatement();
            return stmt.executeUpdate(query);
        }
        
        public ResultSet executeParameterizedQuery(Connection connection, String query, Vector parameters, Vector type) throws Exception {
            PreparedStatement pstmt = connection.prepareStatement(query);//, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            for(int i = 0; i < parameters.size(); i++){
                String paramValue = String.valueOf(parameters.elementAt(i));
                String paramType = String.valueOf(type.elementAt(i));
                if(paramType.trim().equalsIgnoreCase("STRING")){
                    pstmt.setString(i + 1, paramValue);
                }else if(paramType.trim().equalsIgnoreCase("NUMBER")){
                    pstmt.setLong(i + 1, Long.parseLong(paramValue));
                }else if(paramType.trim().equalsIgnoreCase("STRING_NULL")){
                    pstmt.setNull(i + 1, Types.VARCHAR);
                }else if(paramType.trim().equalsIgnoreCase("NUMBER_NULL")){
                    pstmt.setNull(i + 1, Types.NUMERIC);
                }else if(paramType.trim().equalsIgnoreCase("BYTES")){
                    pstmt.setBytes(i + 1, (byte[])parameters.elementAt(i));
                }else if(paramType.trim().equalsIgnoreCase("DATE")){
                    Date paramDate = (Date)parameters.elementAt(i);
                    java.sql.Date dt = null;
                    if(paramDate == null){
                        dt = null;
                    }else{
                        dt = new java.sql.Date(paramDate.getTime());
                    }
                    pstmt.setDate(i + 1, dt);
                }else{
                    pstmt.setString(i + 1, paramValue);
                }
            }
            return pstmt.executeQuery();
        }
        
        public int executeParameterizedUpdate(Connection connection, String query, Vector parameters, Vector type) throws Exception {
            PreparedStatement pstmt = connection.prepareStatement(query); 
            for(int i = 0; i < parameters.size(); i++){
                String paramValue = String.valueOf(parameters.elementAt(i));
                String paramType = String.valueOf(type.elementAt(i));
                if(paramType.trim().equalsIgnoreCase("STRING")){
                    pstmt.setString(i + 1, paramValue);
                }else if(paramType.trim().equalsIgnoreCase("NUMBER")){
                    pstmt.setLong(i + 1, Long.parseLong(paramValue));
                }else if(paramType.trim().equalsIgnoreCase("VARCHAR_NULL")){
                    pstmt.setNull(i + 1, Types.VARCHAR);
                }else if(paramType.trim().equalsIgnoreCase("NUMBER_NULL")){
                    pstmt.setNull(i + 1, Types.NUMERIC);
                }else if(paramType.trim().equalsIgnoreCase("AMOUNT")){
                    pstmt.setDouble(i + 1, Double.parseDouble(paramValue));
                }else if(paramType.trim().equalsIgnoreCase("BYTES")){
                    pstmt.setBytes(i + 1, (byte[])parameters.elementAt(i));
                }else if(paramType.trim().equalsIgnoreCase("DATE")){

//                    System.out.println((Date)parameters.elementAt(i) + "Database type to use when deciding on date format: " +
//                            ConfigReader.getDatabaseConfig().getProperty(
//                            ApplicationFilter.APPLICATION_DBTYPE));
                    
                    if(ConfigReader.getDatabaseConfig().getProperty(
                            ApplicationFilter.APPLICATION_DBTYPE).equals("MSSQL")){

                        Date paramDate = (Date)parameters.elementAt(i);
                        String strDate = DateUtility.getDateAsString(paramDate, "dd/MM/yyyy HH:mm:ss");
                        //System.out.println(".....converted date: " + strDate);
                        pstmt.setTimestamp(i + 1, new Timestamp(paramDate.getTime()));

                    }else{
                        Date paramDate = (Date)parameters.elementAt(i);
                        java.sql.Date dt = null;
                        if(paramDate == null){
                            dt = null;
                        }else{
                            dt = new java.sql.Date(paramDate.getTime());
                        }
                        pstmt.setDate(i + 1, dt);
                    }
                }else{
                    pstmt.setString(i + 1, paramValue);
                }
            }
            return pstmt.executeUpdate();
        }
        
        public void startTransaction(Connection connection) throws SQLException{
            connection.setAutoCommit(false);
        }
        public void commitTransaction(Connection connection) throws SQLException{
            connection.commit();
        }
        public void rollbackTransaction(Connection connection) throws SQLException{
            connection.rollback();
        }

        /**
         * The Procedure name should be passed as parameter. In a situation there is need to pass parameters
         * simple add it to the name like myparam(?)
         * @param connection
         * @param procedure
         * @param parameters
         * @param type
         * @return
         */
        public CallableStatement executeProcedure(Connection connection, String procedure, Vector parameters, Vector type){
            return executeProcedure(connection, procedure, parameters, type, false, 0);
        }
        public CallableStatement executeProcedure(Connection connection, String procedure, Vector parameters, Vector type, boolean useOutputParameter, int position){
            CallableStatement cs = null;
            try{
                // Call a procedure with no parameters
                if(parameters.size() == 0){
                    cs = connection.prepareCall("{call " + procedure +"}");
                    System.out.println("{call " + procedure +"}");
                }else{
                    // Call a procedure with one IN parameters
                    cs = connection.prepareCall("{call " + procedure + "}");
                    // Set the value for the IN parameter
                    System.out.println("************** " + parameters.size() +"**************************");
                    for(int i = 0; i < parameters.size(); i++){
                        String paramValue = String.valueOf(parameters.elementAt(i));
                        String paramType = String.valueOf(type.elementAt(i));
                        if(paramType.trim().equalsIgnoreCase("STRING")){
                            cs.setString(i + 1, paramValue);
                        }else if(paramType.trim().equalsIgnoreCase("NUMBER")){
                            cs.setLong(i + 1, Long.parseLong(paramValue));
                           
                        }else if(paramType.trim().equalsIgnoreCase("AMOUNT")){
                            cs.setDouble(i + 1, Double.parseDouble(paramValue));
                        }else if(paramType.trim().equalsIgnoreCase("BYTES")){
                            cs.setBytes(i + 1, (byte[])parameters.elementAt(i));
                        }else if(paramType.trim().equalsIgnoreCase("DATE")){

//                            System.out.println("Database type to use when deciding on date format: " +
//                                    ConfigReader.getDatabaseConfig().getProperty(
//                                    ApplicationFilter.APPLICATION_DBTYPE));

                            if(ConfigReader.getDatabaseConfig().getProperty(
                                    ApplicationFilter.APPLICATION_DBTYPE).equals("MSSQL")){

                                Date paramDate = (Date)parameters.elementAt(i);
                                String strDate = DateUtility.getDateAsString(paramDate, "dd/MM/yyyy HH:mm:ss");
                                cs.setTimestamp(i + 1, new Timestamp(paramDate.getTime()));

                            }else{
                                Date paramDate = (Date)parameters.elementAt(i);
                                java.sql.Date dt = null;
                                if(paramDate == null){
                                    dt = null;
                                }else{
                                    dt = new java.sql.Date(paramDate.getTime());
                                }
                                cs.setDate(i + 1, dt);
                            }
                        }else{
                            cs.setString(i + 1, paramValue);
                        }
                    }
                }
                if(useOutputParameter){
                    cs.registerOutParameter(position, Types.INTEGER);
                }
                // Execute the stored procedure
                cs.execute();
                return cs;
            }catch(Exception e){
                e.printStackTrace();
                return null;
            }
        }


        public List<ControlData> getControlData(String query){
            Connection connection = null;
            List<ControlData> control = new ArrayList<ControlData>();
            Vector param =new Vector();
            Vector type = new Vector();
            try{
                //param.add(id);
                //type.add("NUMBER");

                connection = this.getConnection(true);
                ResultSet rst = this.executeParameterizedQuery(connection, query,
                        param, type);

                while(rst.next()){
                    ControlData data = new ControlData();
                    data.setId(rst.getString(1));
                    //dept.setCode(rst.getString("managerid"));
                    data.setDescription(rst.getString(2));
                    
                    control.add(data);
                }
            }catch(Exception e){

            }

            return control;
        }
}