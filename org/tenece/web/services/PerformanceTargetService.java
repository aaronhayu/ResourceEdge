
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
import org.tenece.hr.data.dao.PerformanceReportDAO;
import org.tenece.hr.data.dao.PerformanceTargetDAO;
import org.tenece.hr.data.dao.TransactionDAO;
import org.tenece.hr.data.dao.TransactionProcessDAO;
import org.tenece.web.data.PerformanceReport;
import org.tenece.web.data.PerformanceTarget;
import org.tenece.web.data.Transaction;

/**
 *
 * @author jeffry.amachree
 */
public class PerformanceTargetService extends BaseService{
    private PerformanceReportDAO performanceReportDAO;
    private PerformanceTargetDAO performanceTargetDAO;
    private TransactionProcessDAO transactionProcessDAO;
    private TransactionDAO transactionDAO;
    /** Creates a new instance of PayrollService */
    public PerformanceTargetService() {
    }

    public List<PerformanceReport> findAllPerformanceReports(){
        return getPerformanceReportDAO().getAllPerformanceReports();
    }
    
    public List<PerformanceReport> findPerformanceReportByTarget(int targetId){
        return getPerformanceReportDAO().getAllPerformanceReportByTarget(targetId);
    }
    
    public PerformanceReport findPerformanceReportById(int id){
        return getPerformanceReportDAO().getAllPerformanceReportById(id);
    }
    
    public boolean updatePerformanceReport(PerformanceReport report, int mode){
        if(this.MODE_INSERT == mode){
            getPerformanceReportDAO().insertPerformanceReport(report);
        }else if(this.MODE_UPDATE == mode){
            getPerformanceReportDAO().updatePerformanceReport(report);
        }
        return true;
    }

    
    public boolean deletePerformanceReport(List<Integer> ids){
        getPerformanceReportDAO().deletePerformanceReports(ids);
        return true;
    }
    
    public List<PerformanceTarget> findAllPerformanceTargets(){
        return getPerformanceTargetDAO().getAllPerformanceTargets();
    }
    
    public List<PerformanceTarget> findPerformanceTargetForEmployee(long employeeId){
        return getPerformanceTargetDAO().getAllPerformanceTargetForEmployee(employeeId);
    }
    
    public PerformanceTarget findPerformanceTargetById(int id){
        return getPerformanceTargetDAO().getPerformanceTargetById(id);
    }
    public PerformanceTarget findPerformanceTargetByTransaction(long id){
        return getPerformanceTargetDAO().getPerformanceTargetByTransaction(id);
    }
    
    public boolean updatePerformanceTarget(PerformanceTarget target, int mode){
        if(this.MODE_INSERT == mode){
            getPerformanceTargetDAO().insertPerformanceTarget(target);
        }else if(this.MODE_UPDATE == mode){
            getPerformanceTargetDAO().updatePerformanceTarget(target);
        }
        return true;
    }

//    public long updatePerformanceTargetTransaction(PerformanceTarget target, int mode){
//        if(this.MODE_INSERT == mode){
//            Transaction transaction  = new Transaction();
//            transaction.setDescription("Performance Target");
//            transaction.setBatchNumber(0);
//            transaction.setInitiator(target.getEmployeeId());
//            transaction.setStatus("P");
//            transaction.setTransactionReference(0);
//            transaction.setTransactionType(getTransactionDAO().getTransactionTypeByParent("KPI").getId());
//            long transactionID = getTransactionProcessDAO().saveTransaction(transaction, target, "KPI");
//            return transactionID;
//        }else{
//            return 0L;
//        }
//
//    }
    
    public boolean deletePerformanceTargets(List<Integer> ids){
        getPerformanceTargetDAO().deletePerformanceTargets(ids);
        return true;
    }
    
    public PerformanceReportDAO getPerformanceReportDAO() {
        if(performanceReportDAO == null){
            performanceReportDAO = new PerformanceReportDAO();
        }
        return performanceReportDAO;
    }

    public PerformanceTargetDAO getPerformanceTargetDAO() {
        if(performanceTargetDAO == null){
            performanceTargetDAO = new PerformanceTargetDAO();
        }
        return performanceTargetDAO;
    }

    /**
     * @return the transactionProcessDAO
     */
    public TransactionProcessDAO getTransactionProcessDAO() {
        if(transactionProcessDAO == null){
            transactionProcessDAO = new TransactionProcessDAO();
        }
        return transactionProcessDAO;
    }

    /**
     * @return the transactionDAO
     */
    public TransactionDAO getTransactionDAO() {
        if(transactionDAO == null){
            transactionDAO = new TransactionDAO();
        }
        return transactionDAO;
    }

    
}
