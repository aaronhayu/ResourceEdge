
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
import org.tenece.hr.data.dao.EquipmentDAO;
import org.tenece.web.data.Equipment;
import org.tenece.web.data.EquipmentAssigned;
import org.tenece.web.data.EquipmentBrand;
import org.tenece.web.data.EquipmentType;

/**
 *
 * @author jeffry.amachree
 */
public class EquipmentService extends BaseService{
    private EquipmentDAO equipmentDAO;
    /** Creates a new instance of PayrollService */
    public EquipmentService() {
    }

    public List<EquipmentBrand> findAllEquipmentBrand(){
        return getEquipmentDAO().getAllBrand();
    }

    public List<EquipmentBrand> findAllEquipmentBrand(String criteria, String searchText){
        return getEquipmentDAO().getAllBrand(criteria, searchText); 
    }

    public List<EquipmentBrand> findAllEquipmentBrandByCompany(String code){
        return getEquipmentDAO().getAllBrandByCompany(code);
    }

    public List<EquipmentBrand> findAllEquipmentBrandByCompany(String code, String criteria, String searchText){
        return getEquipmentDAO().getAllBrandByCompany(code, criteria, searchText);
    }

    public EquipmentBrand findEquipmentBrandByID(long id){
        return getEquipmentDAO().getEquipmentBrandById(id);
    }

    public boolean updateEquipmentBrand(EquipmentBrand equipment, int mode){
        try{
            int saved = 0;
            if(mode == EquipmentService.MODE_INSERT){
                saved = getEquipmentDAO().createNewEquipmentBrand(equipment);
            }else if(mode == EquipmentService.MODE_UPDATE){
                saved = getEquipmentDAO().updateEquipmentBrand(equipment);
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
    public boolean deleteEquipmentBrand(List<Long> ids){
        try{
            int affected = getEquipmentDAO().deleteEquipmentBrand(ids);
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

    public List<EquipmentType> findAllEquipmentType(){
        return getEquipmentDAO().getAllType();
    }

    public List<EquipmentType> findAllEquipmentType(String criteria, String searchText){
        return getEquipmentDAO().getAllType(criteria, searchText);
    }

    public List<EquipmentType> findAllEquipmentTypeByCompany(String code){
        return getEquipmentDAO().getAllTypeByCompany(code);
    }

    public List<EquipmentType> findAllEquipmentTypeByCompany(String code, String criteria, String searchText){
        return getEquipmentDAO().getAllTypeByCompany(code, criteria, searchText);
    }

    public EquipmentType findEquipmentTypeByID(long id){
        return getEquipmentDAO().getEquipmentTypeById(id);
    }

    public boolean updateEquipmentType(EquipmentType equipment, int mode){
        try{
            int saved = 0;
            if(mode == EquipmentService.MODE_INSERT){
                saved = getEquipmentDAO().createNewEquipmentType(equipment);
            }else if(mode == EquipmentService.MODE_UPDATE){
                saved = getEquipmentDAO().updateEquipmentType(equipment);
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
    public boolean deleteEquipmentType(List<Long> ids){
        try{
            int affected = getEquipmentDAO().deleteEquipmentType(ids);
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

    public List<Equipment> findAllEquipment(){
        return getEquipmentDAO().getAllEquipment();
    }

    public List<Equipment> findAllEquipment(String criteria, String searchText){
        return getEquipmentDAO().getAllEquipment(criteria, searchText);
    }

    public List<Equipment> findAllEquipmentByCompany(String code){
        return getEquipmentDAO().getAllEquipmentByCompany(code);
    }

    public List<Equipment> findAllEquipmentByCompany(String code, String criteria, String searchText){
        return getEquipmentDAO().getAllEquipmentByCompany(code, criteria, searchText);
    }

    public Equipment findEquipmentByID(long id){
        return getEquipmentDAO().getEquipmentById(id);
    }
    public Integer checkUsedEquipment(long equipmentId, long employeeId){
        return getEquipmentDAO().checkUsedEquipment(employeeId, employeeId);
    }

    public boolean updateEquipment(Equipment equipment, int mode){
        try{
            int saved = 0;
            if(mode == EquipmentService.MODE_INSERT){
                saved = getEquipmentDAO().createNewEquipment(equipment);
            }else if(mode == EquipmentService.MODE_UPDATE){
                saved = getEquipmentDAO().updateEquipment(equipment);
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
    public boolean deleteEquipment(List<Long> ids){
        try{
            int affected = getEquipmentDAO().deleteEquipment(ids);
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

    public List<EquipmentAssigned> findAllAssignedEquipmentForEmployee(long employeeID){
        return getEquipmentDAO().getAllEquipmentAssignedForEmployee(employeeID);
    }

    public boolean assignEquipments(int employeeId, List<Long> equipmentIds){
        try {
            return getEquipmentDAO().saveEquipmentAssigned(employeeId, equipmentIds);
        } catch (Exception ex) {
            Logger.getLogger(EquipmentService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    

    /**
     * @return the equipmentDAO
     */
    public EquipmentDAO getEquipmentDAO() {
        if(equipmentDAO == null){
            equipmentDAO = new EquipmentDAO();
        }
        return equipmentDAO;
    }

    
}
