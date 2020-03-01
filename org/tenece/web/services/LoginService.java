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

package org.tenece.web.services;

import java.util.Date;
import org.tenece.hr.data.dao.UserDAO;
import org.tenece.hr.security.SecurityEncoderImpl;
import org.tenece.web.data.Lock;
import org.tenece.web.data.Users;


/**
 * Tenece Professional Services, Nigeria
 * @author Jeffry Amachree
 */
public class LoginService extends BaseService{
    private UserDAO userDAO = null;
    /** Creates a new instance of LoginService */
    public LoginService() {
    }

    public int updatePassword (long userId, String password){
        return getUserDAO().updateUserPassword(userId, password);
    }
    public Users validateUser(String uid, String pwd){
        
        try {
            Users user = new Users();

            //set properties to use for validation
            user.setUserName(uid);
            //user.setPassword(pwd);

            //validate user name against database
            //this will be used to ensure that the user name exist on the system
            user = getUserDAO().getUserByUserName(user);
            if(user != null){
                //if it exist, check if account is locked
                long userID = user.getId();
                long loginCount = user.getNumberLogins();
                if (loginCount >= 3) {
                    throw new IllegalAccessException("User account is locked. Contact System Administrator for Assistance.");
                }

                //get password
                String userPassword = user.getPassword();
                //decrypt password
                String decryptedPassword = SecurityEncoderImpl.decryptPasswordWithAES(userPassword);
                //compare with the password sent via request
                if(!pwd.trim().equals(decryptedPassword)){

                    //if numlogin is 3 or greater, lock user
                    if((loginCount + 1) >= 3){
                        //lock user
                        Lock lock = new Lock();
                        lock.setActive("A");
                        lock.setDateLocked(new Date());
                        lock.setLockedBy("0");
                        lock.setReason("User Login Tries Exceed Limit.");
                        lock.setUserId(userID);

                        getUserDAO().lockUser(lock);

                        //increase lock
                        user.setNumberLogins(user.getNumberLogins() + 1);
                        getUserDAO().updateUsersLoginTries(user);
                        //thorw exception with appropriate message
                        if(true){
                            throw new IllegalAccessException("User account is locked. There was an attempt to access this account.");
                        }
                    }else{
                        //increase login tries
                        user.setNumberLogins(user.getNumberLogins() + 1);
                        getUserDAO().updateUsersLoginTries(user);
                        throw new Exception("Invalid User Name and Password Combination");
                    }
                }else{
                    //reset login tries on successful login
                    user.setNumberLogins(0);
                    getUserDAO().updateUsersLoginTries(user);
                }

            }else{
                
                throw new Exception("Invalid User Name and Password Combination");
            }
            //return the user instance returned
            return user;
        }catch(IllegalAccessException ie){
            this.setErrorMessage(ie.getMessage());
            return null;
        }catch (Exception ex) {
            ex.printStackTrace();
            this.setErrorMessage("Invalid User Name and Password Combination ");
            return null;
        }
        
    }

    public UserDAO getUserDAO() {
        if(userDAO == null){
            userDAO = new UserDAO();
        }
        return userDAO;
    }
}
