
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

import java.util.List;
import org.tenece.hr.data.dao.EmployeeDAO;
import org.tenece.hr.data.dao.LeaveDAO;
import org.tenece.web.data.Leave;
import org.tenece.web.data.LeaveType;

/**
 *
 * @author jeffry.amachree
 */
public class LeaveService extends BaseService{

    private EmployeeDAO employeeDAO;
    private LeaveDAO leaveDAO;
    
    /** Creates a new instance of PayrollService */
    public LeaveService() {
    }

    public List<LeaveType> findAllLeaveType(){
        return getLeaveDAO().getAllLeaveType();
    }
    public List<LeaveType> findAllLeaveTypeByCompany(String code){
        return getLeaveDAO().getAllLeaveTypeByCompany(code);
    }

    public LeaveType findLeaveTypeById(int id){
        return getLeaveDAO().getLeaveTypeById(id);
    }

    public boolean updateLeaveType(LeaveType leaveType, int mode){
        try{
            int updated = 0;
            if(mode == LeaveService.MODE_INSERT){
                updated = getLeaveDAO().createNewLeaveType(leaveType);
            }else if(mode == LeaveService.MODE_UPDATE){
                updated = getLeaveDAO().updateLeaveType(leaveType);
            }
            if(updated == 0){
                return false;
            }
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteLeaveType(List<Integer> ids){
        try{
            int deleted = getLeaveDAO().deleteLeaveTypes(ids);
            return true;
        }catch(Exception e){
            return false;
        }
    }


    /*==================== Leave ========================*/
    public List<Leave> findAllLeave(){
        return getLeaveDAO().getAllLeaveApplication();
    }
    public List<Leave> findAllLeave(String criteria, String searchText){
        return getLeaveDAO().getAllLeaveApplication(criteria, searchText); 
    }

    public List<Leave> findAllLeaveByCompany(String code){
        return getLeaveDAO().getAllLeaveApplicationByCompany(code);
    }
    public List<Leave> findAllLeaveByCompany(String code, String criteria, String searchText){
        return getLeaveDAO().getAllLeaveApplicationByCompany(code, criteria, searchText);
    }

    public Leave findLeaveById(long id){
        return getLeaveDAO().getLeaveById(id);
    }

    public Leave findLeaveByTransaction(long id){
        return getLeaveDAO().getLeaveByTransactionId(id);
    }

    public Leave findLastLeaveForEmployee(long id){
        return getLeaveDAO().getLastLeaveForEmployee(id);
    }//:!

    public int countNumberOfLeaveUsed(long employeeId){
        //get the number of leave used ...
        return getLeaveDAO().getSumOfLeaveByEmployee(employeeId);
    }//:~
    public int countNumberOfPendingLeave(long employeeId){
        //get the number of leave used ...
        return getLeaveDAO().getCountOfPendingLeavesByEmployee(employeeId);
    }//:~
    public boolean updateLeave(Leave leave, int mode){
        try{
            int updated = 0;
            if(mode == LeaveService.MODE_INSERT){
                updated = getLeaveDAO().createNewLeave(leave);
            }else if(mode == LeaveService.MODE_UPDATE){
                updated = getLeaveDAO().updateLeave(leave);
            }
            if(updated == 0){
                return false;
            }
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteLeave(List<Integer> ids){
        try{
            int deleted = getLeaveDAO().deleteLeave(ids);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    /*==================== Leave Resumption ========================*/
    public List<Leave.LeaveResumption> findAllLeaveResumption(){
        return getLeaveDAO().getAllLeaveResumption();
    }

    public Leave.LeaveResumption findLeaveResumptionById(long id){
        return getLeaveDAO().getLeaveResumptionById(id);
    }

    public Leave.LeaveResumption findLeaveResumptionByTransaction(long id){
        return getLeaveDAO().getLeaveResumptionByTransactionId(id);
    }

    public boolean updateLeaveResumption(Leave.LeaveResumption leave, int mode){
        try{
            int updated = 0;
            Leave.LeaveResumption tmpObject = getLeaveDAO().getLeaveResumptionById(leave.getLeaveId());
            if(tmpObject == null){
                mode = LeaveService.MODE_INSERT;
            }else{
                mode = LeaveService.MODE_UPDATE;
            }

            if(mode == LeaveService.MODE_INSERT){
                updated = getLeaveDAO().createNewLeaveResumption(leave);
            }else if(mode == LeaveService.MODE_UPDATE){
                updated = getLeaveDAO().updateLeaveResumption(leave);
            }
            if(updated == 0){
                return false;
            }
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteLeaveResumption(List<Integer> ids){
        try{
            int deleted = getLeaveDAO().deleteLeaveResumption(ids);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    /**
     * @return the employeeDAO
     */
    public EmployeeDAO getEmployeeDAO() {
        if(employeeDAO == null){
            employeeDAO = new EmployeeDAO();
        }
        return employeeDAO;
    }
    
    /**
     * @return the leaveDAO
     */
    public LeaveDAO getLeaveDAO() {
        if(leaveDAO == null){
            leaveDAO = new LeaveDAO();
        }
        return leaveDAO;
    }

}
