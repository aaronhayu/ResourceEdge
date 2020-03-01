
/*
 * (c) Copyright 2009 The Tenece Professional Services.
 *
 * Created on 27 May 2009, 12:45
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

package org.tenece.web.services;

import org.tenece.hr.data.dao.UserDAO;
import org.tenece.web.data.Users;
import java.util.List;
import java.util.ArrayList;
import org.tenece.web.data.Lock;
import org.tenece.web.data.MenuSubMenuInfo;

/**
 *
 * @author jeffry.amachree
 */
public class UserService extends BaseService{
    
    private UserDAO userDAO = null;

    /** Creates a new instance of PayrollService */
    public UserService() {
    }

    public boolean updateUsers(Users user, int mode){
        try{
            int saved = 0;
            if(this.MODE_INSERT == mode){
                saved = getUserDAO().createNewUsers(user);
            }else if(this.MODE_UPDATE == mode){
                saved = getUserDAO().updateUsers(user);
            }
            if(saved > 0)
                return true;
            else
                return false;
        }catch(Exception e){
            return false;
        }
    }

    public List<Users> findAllUsers(){
        try{
            return getUserDAO().getAllUsers();
        }catch(Exception e){
            return new ArrayList<Users>();
        }
    }
    public List<Users> findAllUsersByCompany(String code){
        try{
            return getUserDAO().getAllUsersByCompany(code);
        }catch(Exception e){
            return new ArrayList<Users>();
        }
    }

    public List<Users> findAllLockedUsers(){
        try{
            return getUserDAO().getAllLockedUsers();
        }catch(Exception e){
            return new ArrayList<Users>();
        }
    }

    public Users findUsersById(int id){
        try{
            return getUserDAO().getUsersById(id);
        }catch(Exception e){
            return null;
        }
    }

    public Lock findLockById(int id){
        try{
            return getUserDAO().getUserLockById(id);
        }catch(Exception e){
            return null;
        }
    }

    public int deactivateLockOnUser(Lock lock){
        try{
            return getUserDAO().deactivateLock(lock);
        }catch(Exception e){
            return 0;
        }
    }

    public boolean deleteUsers(int id){
        try{
            Users user = new Users();
            user.setId(id);

            getUserDAO().deleteUsers(user);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public Users findUserByUserNameAndEmployeeID(String userName, long employeeId){
        return getUserDAO().getUserByUserNameAndEmployeeID(userName, employeeId);
    }
    
    /**
     * @return the userDAO
     */
    public UserDAO getUserDAO() {
        if(userDAO == null){
            userDAO = new UserDAO();
        }
        return userDAO;
    }
     public MenuSubMenuInfo getMenu(String menuToShow){
       MenuSubMenuInfo  menuSubmenuInfo  = new MenuSubMenuInfo();
         try
         {
             
            menuSubmenuInfo = getUserDAO().getMenu(menuToShow);
          }catch(Exception e){
            return new MenuSubMenuInfo();
          }
      
         return menuSubmenuInfo;
    }
}
