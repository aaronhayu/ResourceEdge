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

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.tenece.hr.db.DatabaseQueryLoader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import org.tenece.web.data.Department;
import org.tenece.web.data.Lock;
import org.tenece.web.data.MenuData;
import org.tenece.web.data.MenuSubMenuInfo;
import org.tenece.web.data.Users;

/**
 *
 * @author jeffry.amachree
 */
public class UserDAO extends BaseDAO{
    
    /**
     * Creates a new instance of DepartmentDAO
     */
    public UserDAO() {
    }
    
    /* ************* Userss ********** */
    public List<Users> getAllUsers(){
        Connection connection = null;
        List<Users> records = new ArrayList<Users>();
        try{
            connection = this.getConnection(true);
            ResultSet rst = this.executeQuery(DatabaseQueryLoader.getQueryAssignedConstant().USER_SELECT, connection);
            
            while(rst.next()){
                Users item = new Users();
                item.setId(rst.getInt("user_id"));
                item.setEmployeeId(rst.getInt("emp_number"));
                item.setUserName(rst.getString("user_name"));
                item.setPassword(rst.getString("password"));
                item.setAdminUser(rst.getInt("admin_user"));
                item.setSuperAdmin(rst.getInt("superadmin"));
                item.setNumberLogins(rst.getInt("numlogins"));
                item.setActive(rst.getString("active"));
                
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

    public List<Users> getAllUsersByCompany(String code){
        Connection connection = null;
        List<Users> records = new ArrayList<Users>();

        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(code); type.add("STRING");
            
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, 
                    DatabaseQueryLoader.getQueryAssignedConstant().USER_SELECT_BY_COMPANY, param, type);

            while(rst.next()){
                Users item = new Users();
                item.setId(rst.getInt("user_id"));
                item.setEmployeeId(rst.getInt("emp_number"));
                item.setUserName(rst.getString("user_name"));
                item.setPassword(rst.getString("password"));
                item.setAdminUser(rst.getInt("admin_user"));
                item.setSuperAdmin(rst.getInt("superadmin"));
                item.setNumberLogins(rst.getInt("numlogins"));
                item.setActive(rst.getString("active"));

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

    public Users getUsersById(int id){
        Connection connection = null;
        Users record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().USER_SELECT_BY_ID,
                    param, type);
            
            while(rst.next()){
                Users item = new Users();
                item.setId(rst.getInt("user_id"));
                item.setEmployeeId(rst.getInt("emp_number"));
                item.setUserName(rst.getString("user_name"));
                item.setPassword(rst.getString("password"));
                item.setAdminUser(rst.getInt("admin_user"));
                item.setSuperAdmin(rst.getInt("superadmin"));
                item.setNumberLogins(rst.getInt("numlogins"));
                item.setActive(rst.getString("active"));
                
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
    
    /* ************* User Locks ********** */
    public List<Users> getAllLockedUsers(){
        Connection connection = null;
        List<Users> records = new ArrayList<Users>();
        try{
            connection = this.getConnection(true);
            ResultSet rst = this.executeQuery(DatabaseQueryLoader.getQueryAssignedConstant().USER_SELECT_BY_LOCKED_STATUS, connection);

            while(rst.next()){
                Users item = new Users();
                item.setId(rst.getInt("user_id"));
                item.setEmployeeId(rst.getInt("emp_number"));
                item.setUserName(rst.getString("user_name"));
                item.setPassword(rst.getString("password"));
                item.setAdminUser(rst.getInt("admin_user"));
                item.setSuperAdmin(rst.getInt("superadmin"));
                item.setNumberLogins(rst.getInt("numlogins"));
                item.setActive(rst.getString("active"));

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

    public Lock getUserLockById(int id){
        Connection connection = null;
        Lock record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().USER_LOCK_SELECT_BY_ID,
                    param, type);

            while(rst.next()){
                Lock item = new Lock();
                item.setId(rst.getInt("user_id"));
                item.setDateLocked(new Date(rst.getDate("datelock").getTime()));
                item.setReason(rst.getString("reasonlock"));
                item.setLockedBy(rst.getString("lockedby"));
                item.setActive(rst.getString("active"));

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

    public int createNewUsers(Users Users){
        Connection connection = null;
        Users record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(Users.getEmployeeId()); type.add("NUMBER");
            param.add(Users.getUserName()); type.add("STRING");
            param.add(Users.getPassword()); type.add("STRING");
            param.add(Users.getAdminUser()); type.add("NUMBER");
            param.add(Users.getSuperAdmin()); type.add("NUMBER");
            param.add(Users.getNumberLogins()); type.add("NUMBER");
            param.add(Users.getActive()); type.add("STRING");
            param.add(Users.getDateUpdated()); type.add("DATE");
            param.add(Users.getDateSignup()); type.add("DATE");
            param.add(Users.getLastLoginDate()); type.add("DATE");
            
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().USER_INSERT,
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
    
    public int updateUsers(Users Users){
        Connection connection = null;
        Department record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(Users.getEmployeeId()); type.add("NUMBER");
            param.add(Users.getUserName()); type.add("STRING");
            //param.add(Users.getPassword()); type.add("STRING");
            param.add(Users.getAdminUser()); type.add("NUMBER");
            param.add(Users.getSuperAdmin()); type.add("NUMBER");
            //param.add(Users.getNumberLogins()); type.add("NUMBER");
            param.add(Users.getActive()); type.add("STRING");
            param.add(new Date()); type.add("DATE");
            param.add(Users.getId()); type.add("NUMBER");
            
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().USER_UPDATE,
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

    public int updateUsersLoginTries(Users Users){
        Connection connection = null;
        Department record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(Users.getNumberLogins()); type.add("NUMBER");
            param.add(Users.getId()); type.add("NUMBER");

            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().USER_UPDATE_LOGIN,
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

    public int updateUserPassword(long userId, String password){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(password); type.add("STRING");
            param.add(new Date()); type.add("DATE");
            param.add(userId); type.add("NUMBER");
            
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().USER_UPDATE_PASSWORD,
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
    
    public int deleteUsers(Users Users){
        Connection connection = null;
        Users record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(Users.getId()); type.add("NUMBER");
            
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().USER_DELETE,
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

    public int lockUser(Lock lock){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(lock.getUserId()); type.add("NUMBER");
            param.add(lock.getDateLocked()); type.add("DATE");
            param.add(lock.getReason()); type.add("STRING");
            param.add(lock.getLockedBy()); type.add("STRING");
            param.add(lock.getActive()); type.add("STRING");

            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().USER_LOCK_INSERT,
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

    public int deactivateLock(Lock lock){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            connection = this.getConnection(false);
            startTransaction(connection);

            //param.add(lock.getActive()); type.add("STRING");
            param.add(lock.getUserId()); type.add("NUMBER");

            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().USER_LOCK_DEACTIVATE,
                    param, type);
                    
            i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().USER_RESET_LOGIN_TRIES,
                    param, type);

            commitTransaction(connection);
            return i;
        }catch(Exception e){
            e.printStackTrace();
            try{
                rollbackTransaction(connection);
            }catch(Exception er){}
            
            return 0;
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }//:~
    
    public Users getUserByUserNameAndPassword(Users Users){
        Connection connection = null;
        Users record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(Users.getUserName()); type.add("STRING");
            param.add(Users.getPassword()); type.add("STRING");
            
            connection = this.getConnection(false);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().USER_SELECT_BY_UID_AND_PASSWORD,
                    param, type);

            if(rst.next()){
                Users item = new Users();
                item.setId(rst.getInt("user_id"));
                item.setEmployeeId(rst.getInt("emp_number"));
                item.setUserName(rst.getString("user_name"));
                item.setPassword(rst.getString("password"));
                item.setAdminUser(rst.getInt("admin_user"));
                item.setSuperAdmin(rst.getInt("superadmin"));
                item.setNumberLogins(rst.getInt("numlogins"));
                item.setActive(rst.getString("active"));
                
                record = item;
            }
            return record;
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

    public Users getUserByUserNameAndEmployeeID(String userName, long employeeID){
        Connection connection = null;
        Users record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(userName); type.add("STRING");
            param.add(employeeID); type.add("NUMBER");

            connection = this.getConnection(false);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().USER_SELECT_BY_UID_AND_EMP_ID,
                    param, type);

            if(rst.next()){
                Users item = new Users();
                item.setPassword(rst.getString("password"));
                item.setId(rst.getInt("user_id"));

                record = item;
            }
            return record;
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

    public Users getUserByUserName(Users Users){
        Connection connection = null;
        Users record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(Users.getUserName()); type.add("STRING");

            connection = this.getConnection(false);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().USER_SELECT_BY_UID,
                    param, type);

            if(rst.next()){
                Users item = new Users();
                item.setId(rst.getInt("user_id"));
                item.setEmployeeId(rst.getInt("emp_number"));
                item.setUserName(rst.getString("user_name"));
                item.setPassword(rst.getString("password"));
                item.setAdminUser(rst.getInt("admin_user"));
                item.setSuperAdmin(rst.getInt("superadmin"));
                item.setNumberLogins(rst.getInt("numlogins"));
                item.setActive(rst.getString("active"));

                record = item;
            }
            return record;
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
    
     public MenuSubMenuInfo getMenu(String menuToShow){
        List<MenuData> records = new ArrayList<MenuData>();
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        MenuSubMenuInfo menuSubMenuInfo = new MenuSubMenuInfo();
        Multimap<String, MenuData> multiMap = ArrayListMultimap.create();
        try{

            param.add(menuToShow); type.add("STRING");
            connection = this.getConnection(false);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().MENU_SELECT_BY_USERID,
                    param, type);
           while(rst.next()){
                MenuData item = new MenuData();
                item.setMenuTitle(rst.getString("MenuTitle"));
                item.setDivClass(rst.getString("class"));
                item.setURL(rst.getString("URL"));
                item.setSubmenutitle(rst.getString("submenutitle"));
                item.setTarget(rst.getString("Location"));
                item.setOrderCode(rst.getInt("OrderCode"));
                if(item.getDivClass().trim().equals("submenu"))
                {
                   multiMap.put(item.getMenuTitle(),item);

                }
                else
                {
                    records.add(item);
                }

            }
            menuSubMenuInfo.setMenuList(records);
            menuSubMenuInfo.setSubMenuMap(multiMap);
            return menuSubMenuInfo;
        }catch(Exception e){
            e.printStackTrace();
            return new MenuSubMenuInfo();
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }//:~
}
