
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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.tenece.hr.data.dao.AnnualCalendarDAO;
import org.tenece.hr.data.dao.EquipmentDAO;
import org.tenece.web.data.AnnualCalendar;
import org.tenece.web.data.Equipment;
import org.tenece.web.data.EquipmentAssigned;
import org.tenece.web.data.EquipmentBrand;
import org.tenece.web.data.EquipmentType;

/**
 *
 * @author jeffry.amachree
 */

public class AnnualCalendarService extends BaseService{
    private AnnualCalendarDAO annualCalendarDAO;
    /** Creates a new instance of PayrollService */
    public AnnualCalendarService() {
    }

    public List<AnnualCalendar> findAllAnnualCalendar(){
        return getAnnualCalendarDAO().getAllAnnualcalendar();
    }

    public List<AnnualCalendar> findAllAnnualCalendarByCompany(String code){
        return getAnnualCalendarDAO().getAllAnnualcalendarByCompany(code);
    }

    public AnnualCalendar findAnnualCalendarByID(int id){
        return getAnnualCalendarDAO().getAnnualCalendarById(id);
    }

    public boolean updateAnnualCalendar(AnnualCalendar calendar, int mode){
        try{
            int saved = 0;
            if(mode == AnnualCalendarService.MODE_INSERT){
                saved = getAnnualCalendarDAO().createNewAnnualCalendar(calendar);
            }else if(mode == AnnualCalendarService.MODE_UPDATE){
                saved = getAnnualCalendarDAO().updateAnnualCalendar(calendar);
            }
            if(saved == 0){
                throw new Exception("Unable to update");
            }
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public boolean closeAllOutstandingCalendar(){
        try{
            int affected = getAnnualCalendarDAO().updatePendingAnnualCalendars();
            if(affected > 0){
                return true;
            }else{
                return false;
            }
        }catch(Exception e){
            e.printStackTrace();
            return true;
        }
    }

    /**
     * @return the annualCalendarDAO
     */
    public AnnualCalendarDAO getAnnualCalendarDAO() {
        
        return annualCalendarDAO;
    }

    /**
     * @param annualCalendarDAO the annualCalendarDAO to set
     */
    public void setAnnualCalendarDAO(AnnualCalendarDAO annualCalendarDAO) {
        this.annualCalendarDAO = annualCalendarDAO;
    }

    
}
