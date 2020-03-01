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

import org.tenece.hr.db.DatabaseQueryLoader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import org.tenece.web.data.Equipment;
import org.tenece.web.data.EquipmentAssigned;
import org.tenece.web.data.EquipmentBrand;
import org.tenece.web.data.EquipmentType;

/**
 *
 * @author jeffry.amachree
 */
public class EquipmentDAO extends BaseDAO{
    
    /**
     * Creates a new instance of DepartmentDAO
     */
    public EquipmentDAO() {
    }
    
    /* ************* EquipmentBrand ********** */
    public List<EquipmentBrand> getAllBrand(){
        return getAllBrand(null, null);
    }
    public List<EquipmentBrand> getAllBrand(String criteria, String searchText){
        Connection connection = null;
        List<EquipmentBrand> records = new ArrayList<EquipmentBrand>();
        try{
            connection = this.getConnection(true);

            ResultSet rst = null;
            if(criteria == null || searchText == null){
                rst = this.executeQuery(DatabaseQueryLoader.getQueryAssignedConstant().EQUIPMENT_BRAND_SELECT, connection);
            }else{
                String query = "";
                
                if(criteria.equalsIgnoreCase("DESC")){
                    query = DatabaseQueryLoader.getQueryAssignedConstant().EQUIPMENT_BRAND_SELECT_SEARCH_BY_DESC;
                }else if(criteria.equalsIgnoreCase("CODE")){
                    query = DatabaseQueryLoader.getQueryAssignedConstant().EQUIPMENT_BRAND_SELECT_SEARCH_BY_CODE;
                }
                query = query.replaceAll("_SEARCH_", searchText);

                rst = this.executeQuery(query, connection);
            }
            
            while(rst.next()){
                EquipmentBrand item = new EquipmentBrand();
                item.setId(rst.getInt("id"));
                item.setCode(rst.getString("code"));
                item.setDescription(rst.getString("description"));
                item.setActive(rst.getInt("active"));
                item.setCompanyCode(rst.getString("company_code"));
                
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

    public List<EquipmentBrand> getAllBrandByCompany(String code){
        return getAllBrandByCompany(code, null, null);
    }
    public List<EquipmentBrand> getAllBrandByCompany(String code, String criteria, String searchText){
        Connection connection = null;
        List<EquipmentBrand> records = new ArrayList<EquipmentBrand>();

        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(code); type.add("STRING");

            connection = this.getConnection(true);

            ResultSet rst = null;
            if(criteria == null || searchText == null){
                rst = this.executeParameterizedQuery(connection,
                        DatabaseQueryLoader.getQueryAssignedConstant().EQUIPMENT_BRAND_SELECT_BY_COMPANY, param, type);
            }else{
                String query = "";

                if(criteria.equalsIgnoreCase("DESC")){
                    query = DatabaseQueryLoader.getQueryAssignedConstant().EQUIPMENT_BRAND_SELECT_BY_COMPANY_SEARCH_BY_DESC;
                }else if(criteria.equalsIgnoreCase("CODE")){
                    query = DatabaseQueryLoader.getQueryAssignedConstant().EQUIPMENT_BRAND_SELECT_BY_COMPANY_SEARCH_BY_CODE;
                }
                query = query.replaceAll("_SEARCH_", searchText);

                rst = this.executeParameterizedQuery(connection, query, param, type);
            }

            while(rst.next()){
                EquipmentBrand item = new EquipmentBrand();
                item.setId(rst.getInt("id"));
                item.setCode(rst.getString("code"));
                item.setDescription(rst.getString("description"));
                item.setActive(rst.getInt("active"));
                item.setCompanyCode(rst.getString("company_code"));

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

    public EquipmentBrand getEquipmentBrandById(long id){
        Connection connection = null;
        EquipmentBrand record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().EQUIPMENT_BRAND_SELECT_BY_ID,
                    param, type);
            
            while(rst.next()){
                EquipmentBrand item = new EquipmentBrand();
                item.setId(rst.getInt("id"));
                item.setCode(rst.getString("code"));
                item.setDescription(rst.getString("description"));
                item.setActive(rst.getInt("active"));
                item.setCompanyCode(rst.getString("company_code"));
                
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
    
    public int createNewEquipmentBrand(EquipmentBrand equipmentBrand){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{

            param.add(equipmentBrand.getCode()); type.add("STRING");
            param.add(equipmentBrand.getDescription()); type.add("STRING");
            param.add(equipmentBrand.getActive()); type.add("NUMBER");
            param.add(equipmentBrand.getCompanyCode()); type.add("STRING");
            
            System.out.println("QUERY:" + DatabaseQueryLoader.getQueryAssignedConstant().EQUIPMENT_BRAND_INSERT);
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EQUIPMENT_BRAND_INSERT,
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
    
    public int updateEquipmentBrand(EquipmentBrand equipmentBrand){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(equipmentBrand.getCode()); type.add("STRING");
            param.add(equipmentBrand.getDescription()); type.add("STRING");
            param.add(equipmentBrand.getActive()); type.add("NUMBER");
            param.add(equipmentBrand.getCompanyCode()); type.add("STRING");
            param.add(equipmentBrand.getId()); type.add("NUMBER");
            
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EQUIPMENT_BRAND_UPDATE,
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

    public int deleteEquipmentBrand(List<Long> equip){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{


            connection = this.getConnection(false);
            this.startTransaction(connection);
            for(long idx : equip){
                param.add(idx); type.add("NUMBER");
                int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EQUIPMENT_BRAND_DELETE,
                        param, type);
            }
            this.commitTransaction(connection);
            return 1;
        }catch(Exception e){
            try {
                this.rollbackTransaction(connection);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
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

    public int deleteEquipmentType(List<Long> equip){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{


            connection = this.getConnection(false);
            this.startTransaction(connection);
            for(long idx : equip){
                param.add(idx); type.add("NUMBER");
                int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EQUIPMENT_TYPE_DELETE,
                        param, type);
            }
            this.commitTransaction(connection);
            return 1;
        }catch(Exception e){
            try {
                this.rollbackTransaction(connection);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
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

    public int deleteEquipmentBrand(EquipmentBrand equipmentBrand){
        Connection connection = null;
        EquipmentBrand record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            
            param.add(equipmentBrand.getId()); type.add("NUMBER");
            
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EQUIPMENT_BRAND_DELETE,
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

    public List<EquipmentType> getAllType(){
        return getAllType(null, null);
    }
    public List<EquipmentType> getAllType(String criteria, String searchText){
        Connection connection = null;
        List<EquipmentType> records = new ArrayList<EquipmentType>();
        try{
            connection = this.getConnection(true);
            ResultSet rst = null;

            if(criteria == null || searchText == null){
                rst = this.executeQuery(DatabaseQueryLoader.getQueryAssignedConstant().EQUIPMENT_TYPE_SELECT, connection);
            }else{
                String query = "";

                if(criteria.equalsIgnoreCase("DESC")){
                    query = DatabaseQueryLoader.getQueryAssignedConstant().EQUIPMENT_TYPE_SELECT_SEARCH_BY_DESC;
                }else if(criteria.equalsIgnoreCase("CODE")){
                    query = DatabaseQueryLoader.getQueryAssignedConstant().EQUIPMENT_TYPE_SELECT_SEARCH_BY_CODE;
                }
                query = query.replaceAll("_SEARCH_", searchText);

                rst = this.executeQuery(query, connection);
            }

            while(rst.next()){
                EquipmentType item = new EquipmentType();
                item.setId(rst.getInt("id"));
                item.setCode(rst.getString("code"));
                item.setDescription(rst.getString("description"));
                item.setCompanyCode(rst.getString("company_code"));

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

    public List<EquipmentType> getAllTypeByCompany(String code){
        return getAllTypeByCompany(code, null, null);
    }
    public List<EquipmentType> getAllTypeByCompany(String code, String criteria, String searchText){
        Connection connection = null;
        List<EquipmentType> records = new ArrayList<EquipmentType>();

        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(code); type.add("STRING");
            
            connection = this.getConnection(true);
            ResultSet rst = null;

            if(criteria == null || searchText == null){
                rst = this.executeParameterizedQuery(connection, 
                        DatabaseQueryLoader.getQueryAssignedConstant().EQUIPMENT_TYPE_SELECT_BY_COMPANY, param, type);
            }else{
                String query = "";

                if(criteria.equalsIgnoreCase("DESC")){
                    query = DatabaseQueryLoader.getQueryAssignedConstant().EQUIPMENT_TYPE_SELECT_BY_COMPANY_SEARCH_BY_DESC;
                }else if(criteria.equalsIgnoreCase("CODE")){
                    query = DatabaseQueryLoader.getQueryAssignedConstant().EQUIPMENT_TYPE_SELECT_BY_COMPANY_SEARCH_BY_CODE;
                }
                query = query.replaceAll("_SEARCH_", searchText);

                rst = this.executeParameterizedQuery(connection, query, param, type);
            }

            while(rst.next()){
                EquipmentType item = new EquipmentType();
                item.setId(rst.getInt("id"));
                item.setCode(rst.getString("code"));
                item.setDescription(rst.getString("description"));
                item.setCompanyCode(rst.getString("company_code"));

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

    public EquipmentType getEquipmentTypeById(long id){
        Connection connection = null;
        EquipmentType record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().EQUIPMENT_TYPE_SELECT_BY_ID,
                    param, type);

            while(rst.next()){
                EquipmentType item = new EquipmentType();
                item.setId(rst.getInt("id"));
                item.setCode(rst.getString("code"));
                item.setDescription(rst.getString("description"));
                item.setCompanyCode(rst.getString("company_code"));

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

    public int createNewEquipmentType(EquipmentType equipmentType){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{

            param.add(equipmentType.getCode()); type.add("STRING");
            param.add(equipmentType.getDescription()); type.add("STRING");
            param.add(equipmentType.getCompanyCode()); type.add("STRING");

            System.out.println("QUERY:" + DatabaseQueryLoader.getQueryAssignedConstant().EQUIPMENT_TYPE_INSERT);
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EQUIPMENT_TYPE_INSERT,
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

    public int updateEquipmentType(EquipmentType equipmentType){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(equipmentType.getCode()); type.add("STRING");
            param.add(equipmentType.getDescription()); type.add("STRING");
            param.add(equipmentType.getCompanyCode()); type.add("STRING");
            param.add(equipmentType.getId()); type.add("NUMBER");

            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EQUIPMENT_TYPE_UPDATE,
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

    public int deleteEquipmentType(EquipmentType equipmentType){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{

            param.add(equipmentType.getId()); type.add("NUMBER");

            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EQUIPMENT_TYPE_DELETE,
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

    public List<Equipment> getAllEquipment(){
        return getAllEquipment(null, null);
    }
    public List<Equipment> getAllEquipment(String criteria, String searchText){
        Connection connection = null;
        List<Equipment> records = new ArrayList<Equipment>();
        try{
            connection = this.getConnection(true);
            ResultSet rst = null;
            if(criteria == null || searchText == null){
                rst = this.executeQuery(DatabaseQueryLoader.getQueryAssignedConstant().EQUIPMENT_SELECT, connection);
            }else{
                String query = "";
                
                if(criteria.equalsIgnoreCase("BRAND")){
                    query = DatabaseQueryLoader.getQueryAssignedConstant().EQUIPMENT_SELECT_SEARCH_BY_BRAND;
                }else if(criteria.equalsIgnoreCase("TYPE")){
                    query = DatabaseQueryLoader.getQueryAssignedConstant().EQUIPMENT_SELECT_SEARCH_BY_TYPE;
                }else if(criteria.equalsIgnoreCase("NAME")){
                    query = DatabaseQueryLoader.getQueryAssignedConstant().EQUIPMENT_SELECT_SEARCH_BY_NAME;
                }else if(criteria.equalsIgnoreCase("SERIAL")){
                    query = DatabaseQueryLoader.getQueryAssignedConstant().EQUIPMENT_SELECT_SEARCH_BY_SERIAL;
                }
                query = query.replaceAll("_SEARCH_", searchText);
                
                rst = this.executeQuery(query, connection);
            }
            
            
            while(rst.next()){
                Equipment item = new Equipment();
                item.setId(rst.getInt("id"));
                item.setBrandID(rst.getLong("brand_id"));
                item.setTypeID(rst.getLong("type_id"));
                item.setProductName(rst.getString("product_name"));
                item.setSerialNumber(rst.getString("serial_number"));
                item.setBrandCode(rst.getString("brand_code"));
                item.setTypeCode(rst.getString("type_code"));
                item.setCompanyCode(rst.getString("company_code"));

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

    public List<Equipment> getAllEquipmentByCompany(String code){
        return getAllEquipmentByCompany(code, null, null);
    }
    public List<Equipment> getAllEquipmentByCompany(String code, String criteria, String searchText){
        Connection connection = null;
        List<Equipment> records = new ArrayList<Equipment>();
        
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(code); type.add("STRING");
            
            connection = this.getConnection(true);
            ResultSet rst = null;
            if(criteria == null || searchText == null){
                rst = this.executeParameterizedQuery(connection, 
                        DatabaseQueryLoader.getQueryAssignedConstant().EQUIPMENT_SELECT_BY_COMPANY, param, type);
            }else{
                String query = "";

                if(criteria.equalsIgnoreCase("BRAND")){
                    query = DatabaseQueryLoader.getQueryAssignedConstant().EQUIPMENT_SELECT_BY_COMPANY_SEARCH_BY_BRAND;
                }else if(criteria.equalsIgnoreCase("TYPE")){
                    query = DatabaseQueryLoader.getQueryAssignedConstant().EQUIPMENT_SELECT_BY_COMPANY_SEARCH_BY_TYPE;
                }else if(criteria.equalsIgnoreCase("NAME")){
                    query = DatabaseQueryLoader.getQueryAssignedConstant().EQUIPMENT_SELECT_BY_COMPANY_SEARCH_BY_NAME;
                }else if(criteria.equalsIgnoreCase("SERIAL")){
                    query = DatabaseQueryLoader.getQueryAssignedConstant().EQUIPMENT_SELECT_BY_COMPANY_SEARCH_BY_SERIAL;
                }
                query = query.replaceAll("_SEARCH_", searchText);

                rst = this.executeParameterizedQuery(connection, query, param, type);
            }


            while(rst.next()){
                Equipment item = new Equipment();
                item.setId(rst.getInt("id"));
                item.setBrandID(rst.getLong("brand_id"));
                item.setTypeID(rst.getLong("type_id"));
                item.setProductName(rst.getString("product_name"));
                item.setSerialNumber(rst.getString("serial_number"));
                item.setBrandCode(rst.getString("brand_code"));
                item.setTypeCode(rst.getString("type_code"));
                item.setCompanyCode(rst.getString("company_code"));

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
    
    public Equipment getEquipmentById(long id){
        Connection connection = null;
        Equipment record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(id);
            type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().EQUIPMENT_SELECT_BY_ID,
                    param, type);

            while(rst.next()){
                Equipment item = new Equipment();
                item.setId(rst.getInt("id"));
                item.setBrandID(rst.getLong("brand_id"));
                item.setTypeID(rst.getLong("type_id"));
                item.setProductName(rst.getString("product_name"));
                item.setSerialNumber(rst.getString("serial_number"));
                item.setBrandCode(rst.getString("brand_code"));
                item.setTypeCode(rst.getString("type_code"));
                item.setCompanyCode(rst.getString("company_code"));

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

    public Integer checkUsedEquipment(long id, long employeeId){
        Connection connection = null;
        int counted = 0;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(employeeId); type.add("NUMBER");
            param.add(id); type.add("NUMBER");
            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().EQUIPMENT_ASSIGN_CHECK_USED,
                    param, type);

            while(rst.next()){

                counted = rst.getInt(1);
            }
        }catch(Exception e){
            e.printStackTrace();
            counted = 0;
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return counted;
    }

    public int createNewEquipment(Equipment equipment){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{

            param.add(equipment.getBrandID()); type.add("NUMBER");
            param.add(equipment.getTypeID()); type.add("NUMBER");
            param.add(equipment.getProductName()); type.add("STRING");
            param.add(equipment.getSerialNumber()); type.add("STRING");
            param.add(equipment.getCompanyCode()); type.add("STRING");

            System.out.println("QUERY:" + DatabaseQueryLoader.getQueryAssignedConstant().EQUIPMENT_INSERT);
            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EQUIPMENT_INSERT,
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

    public int updateEquipment(Equipment equipment){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{
            param.add(equipment.getBrandID()); type.add("NUMBER");
            param.add(equipment.getTypeID()); type.add("NUMBER");
            param.add(equipment.getProductName()); type.add("STRING");
            param.add(equipment.getSerialNumber()); type.add("STRING");
            param.add(equipment.getCompanyCode()); type.add("STRING");
            param.add(equipment.getId()); type.add("NUMBER");

            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EQUIPMENT_UPDATE,
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

    public int deleteEquipment(List<Long> equip){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{


            connection = this.getConnection(false);
            this.startTransaction(connection);
            for(long idx : equip){
                param.add(idx); type.add("NUMBER");
                int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EQUIPMENT_DELETE,
                        param, type);
            }
            this.commitTransaction(connection);
            return 1;
        }catch(Exception e){
            try {
                this.rollbackTransaction(connection);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
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

    public int deleteEquipment(Equipment equipment){
        Connection connection = null;
        Equipment record = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{

            param.add(equipment.getId()); type.add("NUMBER");

            connection = this.getConnection(false);
            int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EQUIPMENT_DELETE,
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

    public List<EquipmentAssigned> getAllEquipmentAssignedForEmployee(long employeeID){
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        List<EquipmentAssigned> records = new ArrayList<EquipmentAssigned>();
        try{
            param.add(employeeID); type.add("NUMBER");

            connection = this.getConnection(true);
            ResultSet rst = this.executeParameterizedQuery(connection, DatabaseQueryLoader.getQueryAssignedConstant().EQUIPMENT_ASSIGNED_SELECT_BY_EMPLOYEE, param, type);

            while(rst.next()){
                EquipmentAssigned item = new EquipmentAssigned();
                item.setEmployeeId(rst.getInt("emp_number"));
                item.setEquipmentId(rst.getLong("equipment_id"));

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

    public boolean saveEquipmentAssigned(int employeeId, List<Long> equipmentIds) throws Exception{
        Connection connection = null;
        Vector param =new Vector();
        Vector type = new Vector();
        try{

            connection = this.getConnection(false);
            this.startTransaction(connection);

            //set value to use for delete
            param.add(employeeId); type.add("NUMBER");
            //remove initial record for employee
            int rows = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EQUIPMENT_ASSIGNED_DELETE, param, type);

            
            for(long idx : equipmentIds){
                //re-initialize variable for parameter
                param = new Vector(); type = new Vector();

                param.add(employeeId); type.add("NUMBER");
                param.add(idx); type.add("NUMBER");
                int i = this.executeParameterizedUpdate(connection, DatabaseQueryLoader.getQueryAssignedConstant().EQUIPMENT_ASSIGNED_INSERT,
                        param, type);

                if(i == 0){
                    throw new Exception("Unable to complete operation");
                }
            }
            this.commitTransaction(connection);
            return true;
        }catch(Exception e){
            try {
                this.rollbackTransaction(connection);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
