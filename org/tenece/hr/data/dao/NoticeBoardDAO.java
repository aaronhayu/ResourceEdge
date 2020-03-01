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
import java.util.List;
import java.util.Vector;
import org.tenece.hr.db.DatabaseQueryLoader;
import org.tenece.web.data.NoticeBoard;

/**
 * Tenece Professional Services Limited
 * @author amachree
 */
public class NoticeBoardDAO extends BaseDAO {
    /* ************* NoticeBoard ********** */
    public List<NoticeBoard> getAllNoticeBoardByEmployee(long employeeId){
        return getAllNoticeBoardByEmployee(employeeId, null, null);
    }

    public List<NoticeBoard> getAllNoticeBoardByEmployee(long employeeId, String criteria, String searchText){
        Connection connection = null;
        List<NoticeBoard> records = new ArrayList<NoticeBoard>();
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(employeeId); type.add("NUMBER");

            connection = this.getConnection(true);
            ResultSet rst = null;
            if(criteria == null || searchText == null){
                rst = this.executeParameterizedQuery(connection,
                        DatabaseQueryLoader.getQueryAssignedConstant().NOTICE_BOARD_ACTIVE_SELECT_BY_EMPLOYEE, param, type);
            }else{
                String query = "";

                if(criteria.equalsIgnoreCase("SUBJECT")){
                    query = DatabaseQueryLoader.getQueryAssignedConstant().NOTICE_BOARD_ACTIVE_SELECT_BY_EMPLOYEE_SEARCH_BY_SUBJECT;
                }else if(criteria.equalsIgnoreCase("MESSAGE")){
                    query = DatabaseQueryLoader.getQueryAssignedConstant().NOTICE_BOARD_ACTIVE_SELECT_BY_EMPLOYEE_SEARCH_BY_MESSAGE;
                }

                query = query.replaceAll("_SEARCH_", searchText);

                rst = this.executeParameterizedQuery(connection, query, param, type);
            }

            while(rst.next()){
                NoticeBoard item = new NoticeBoard();
                item.setId(rst.getInt("notice_id"));
                item.setSubject(rst.getString("subject"));
                item.setMessage(rst.getString("message"));
                item.setEmployeeId(rst.getLong("emp_number"));
                item.setCreateDate(new java.util.Date(rst.getDate("create_date").getTime()));
                item.setExpireDate(new java.util.Date(rst.getDate("expire_date").getTime()));
                item.setViewers(rst.getString("viewers"));
                item.setDepartment(rst.getString("department"));
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

    public NoticeBoard getNoticeBoardById(long id){
        Connection connection = null;
        NoticeBoard record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().NOTICE_BOARD_ACTIVE_SELECT_BY_ID,
                    param, type);

            while(rst.next()){
                NoticeBoard item = new NoticeBoard();
                item.setId(rst.getInt("notice_id"));
                item.setSubject(rst.getString("subject"));
                item.setMessage(rst.getString("message"));
                item.setEmployeeId(rst.getLong("emp_number"));
                item.setCreateDate(new java.util.Date(rst.getDate("create_date").getTime()));
                item.setExpireDate(new java.util.Date(rst.getDate("expire_date").getTime()));
                item.setViewers(rst.getString("viewers"));
                item.setDepartment(rst.getString("department"));
                item.setStatus(rst.getString("status"));
                
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

    public NoticeBoard getNoticeBoardByTransaction(long transactionId){
        Connection connection = null;

        try{
            connection = this.getConnection(false);
            return getNoticeBoardByTransaction(transactionId, connection);
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
    }//:~

    public NoticeBoard getNoticeBoardByTransaction(long id, Connection connection){
        NoticeBoard record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            ResultSet rst = this.executeParameterizedQuery(connection,
                    DatabaseQueryLoader.getQueryAssignedConstant().NOTICE_BOARD_ACTIVE_SELECT_BY_TRANSACTION,
                    param, type);

            while(rst.next()){
                NoticeBoard item = new NoticeBoard();
                item.setId(rst.getInt("notice_id"));
                item.setSubject(rst.getString("subject"));
                item.setMessage(rst.getString("message"));
                item.setEmployeeId(rst.getLong("emp_number"));
                item.setCreateDate(new java.util.Date(rst.getDate("create_date").getTime()));
                item.setExpireDate(new java.util.Date(rst.getDate("expire_date").getTime()));
                item.setStatus(rst.getString("status"));
                item.setTransactionId(rst.getLong("transactionid"));
                item.seteStatus(rst.getString("estatus"));
                item.setViewers(rst.getString("viewerName"));
                item.setDepartment(rst.getString("departmentName"));

                record = item;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return record;
    }

    public int createNoticeBoard(NoticeBoard notice){
        Connection connection = null;

        try{
            connection = this.getConnection(false);
            return createNoticeBoard(notice, connection);
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

    public int createNoticeBoard(NoticeBoard notice, Connection connection){
        NoticeBoard record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(notice.getSubject()); type.add("STRING");
            param.add(notice.getMessage()); type.add("STRING");
            param.add(notice.getEmployeeId()); type.add("NUMBER");
            param.add(notice.getCreateDate()); type.add("DATE");
            param.add(notice.getExpireDate()); type.add("DATE");
            param.add(notice.getViewers()); type.add("STRING");
            param.add(notice.getDepartment()); type.add("STRING");
            if(notice.getTransactionId() == 0){
                param.add(notice.getTransactionId()); type.add("NUMBER_NULL");
            }else{
                param.add(notice.getTransactionId()); type.add("NUMBER");
            }

            if(notice.geteStatus().trim().equals("")){
                param.add(notice.geteStatus()); type.add("VARCHAR_NULL");
            }else{
                param.add(notice.geteStatus()); type.add("STRING");
            }
            int i = this.executeParameterizedUpdate(connection,
                    DatabaseQueryLoader.getQueryAssignedConstant().NOTICE_BOARD_INSERT,
                    param, type);

            return i;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }//:~

    public int updateNoticeBoard(NoticeBoard notice){
        Connection connection = null;
        NoticeBoard record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(notice.getSubject()); type.add("STRING");
            param.add(notice.getMessage()); type.add("STRING");
            
            param.add(notice.getExpireDate()); type.add("DATE");
            param.add(notice.getViewers()); type.add("STRING");
            param.add(notice.getDepartment()); type.add("STRING");

            param.add(notice.getId()); type.add("NUMBER");
            param.add(notice.getEmployeeId()); type.add("NUMBER");

            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().NOTICE_BOARD_UPDATE,
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

    public int updateNoticeBoardStatus(NoticeBoard notice, Connection connection){
        NoticeBoard record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(notice.geteStatus()); type.add("STRING");
            param.add(notice.getTransactionId()); type.add("NUMBER");

            int i = this.executeParameterizedUpdate(connection,
                    DatabaseQueryLoader.getQueryAssignedConstant().NOTICE_BOARD_STATUS_UPDATE,
                    param, type);

            return i;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }//:~

    /* ************* NoticeBoard Inbox********** */
    public List<NoticeBoard> getAllNoticeBoardInboxByEmployee(long employeeId, int departmentId){
        return getAllNoticeBoardInboxByEmployee(employeeId, departmentId, null, null);
    }

    public List<NoticeBoard> getAllNoticeBoardInboxByEmployee(long employeeId, int departmentId, String criteria, String searchText){
        Connection connection = null;
        List<NoticeBoard> records = new ArrayList<NoticeBoard>();
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(String.valueOf(employeeId)); type.add("STRING");
            param.add(String.valueOf(departmentId)); type.add("STRING");

            connection = this.getConnection(true);
            ResultSet rst = null;
            if(criteria == null || searchText == null){
                rst = this.executeParameterizedQuery(connection,
                        DatabaseQueryLoader.getQueryAssignedConstant().NOTICE_BOARD_INBOX_SELECT, param, type);
            }else{
                String query = "";

                if(criteria.equalsIgnoreCase("SUBJECT")){
                    query = DatabaseQueryLoader.getQueryAssignedConstant().NOTICE_BOARD_INBOX_SELECT_SEARCH_BY_SUBJECT;
                }else if(criteria.equalsIgnoreCase("MESSAGE")){
                    query = DatabaseQueryLoader.getQueryAssignedConstant().NOTICE_BOARD_INBOX_SELECT_SEARCH_BY_MESSAGE;
                }

                query = query.replaceAll("_SEARCH_", searchText);

                rst = this.executeParameterizedQuery(connection, query, param, type);
            }

            while(rst.next()){
                NoticeBoard item = new NoticeBoard();
                item.setId(rst.getInt("notice_id"));
                item.setSubject(rst.getString("subject"));
                item.setMessage(rst.getString("message"));
                item.setEmployeeId(rst.getLong("emp_number"));
                item.setCreateDate(new java.util.Date(rst.getDate("create_date").getTime()));
                item.setExpireDate(new java.util.Date(rst.getDate("expire_date").getTime()));
                item.setViewers(rst.getString("viewers"));
                item.setDepartment(rst.getString("department"));
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
