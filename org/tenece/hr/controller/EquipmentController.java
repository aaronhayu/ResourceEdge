
/*
 * (c) Copyright 2009 The Tenece Professional Services.
 *
 * Created on 27 May 2009, 13:28
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

package org.tenece.hr.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.tenece.web.common.BaseController;
import org.tenece.web.data.Employee;
import org.tenece.web.data.Equipment;
import org.tenece.web.data.EquipmentAssigned;
import org.tenece.web.data.EquipmentBrand;
import org.tenece.web.data.EquipmentType;
import org.tenece.web.data.Users;
import org.tenece.web.services.EmployeeService;
import org.tenece.web.services.EquipmentService;

/**
 *
 * @author jeffry.amachree
 */
public class EquipmentController extends BaseController{
    
    private EquipmentService equipmentService;
    private EmployeeService employeeService;
    
    /** Creates a new instance of DepartmentController */
    public EquipmentController() {
    }
    
    public ModelAndView viewAllEquipmentBrand(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("equipment_brand_view");

        //do search here...
        String cbSearch = request.getParameter("cbSearch");
        String txtSearch = request.getParameter("txtSearch");

        List<EquipmentBrand> list = null;
        if(cbSearch == null || txtSearch == null){

            if(this.getUserPrincipal(request).getGroupCompanyUser() == 1){
                list = equipmentService.findAllEquipmentBrand();
                view.addObject("showCompany", "Y");
            }else{
                Employee employee = this.getEmployeeService().findEmployeeBasicById(getUserPrincipal(request).getEmployeeId());
                list = equipmentService.findAllEquipmentBrandByCompany(employee.getCompanyCode());
            }
        }else{

            if(this.getUserPrincipal(request).getGroupCompanyUser() == 1){
                list = equipmentService.findAllEquipmentBrand(cbSearch, txtSearch);
                view.addObject("showCompany", "Y");
            }else{
                Employee employee = this.getEmployeeService().findEmployeeBasicById(getUserPrincipal(request).getEmployeeId());
                list = equipmentService.findAllEquipmentBrandByCompany(employee.getCompanyCode(), cbSearch, txtSearch);
            }
        }

        view.addObject("result", list);
        return view;
    }
    
    public ModelAndView editEquipmentBrand(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("equipment_brand_edit");
        String param = request.getParameter("idx");
        
        if(param == null){
            return viewAllEquipmentBrand(request, response);
        }
        int id = Integer.parseInt(param);
        EquipmentBrand equip = equipmentService.findEquipmentBrandByID(id);

        //get company info
        view = getAndAppendCompanyToView(view, request);
        
        view.addObject("equipment", equip);
        return view;
    }
    
    public ModelAndView newEquipmentBrand(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("equipment_brand_edit");

        //get company info
        view = getAndAppendCompanyToView(view, request);
        
        view.addObject("equipment", new EquipmentBrand());
        return view;
    }
    
    public ModelAndView deleteEquipmentBrand(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("");
        List<Long> ids = new ArrayList<Long>();
        //get mode of request
        String mode = request.getParameter("mode");
        //check if mode is for more than one record
        //get mode, if mode is equals 1, then process for multiple
        if(mode != null && mode.trim().equals("1")){
            String[] args = request.getParameterValues("_chk");
            for(String id : args){
                ids.add(Long.parseLong(id));
            }
        }else{//then it is zero
            ids.add(Long.parseLong(request.getParameter("id")));
        }
        equipmentService.deleteEquipmentBrand(ids);
       //delete
        return viewAllEquipmentBrand(request, response);
    }
    
    public ModelAndView processEquipmentBrandRequest(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("message");
        String operation = request.getParameter("txtMode");
        //operation mode: N= New, E=Edit
        if(operation.trim().equals("N")){
            EquipmentBrand equip = new EquipmentBrand();
            String code = request.getParameter("txtCode");
            String description = request.getParameter("txtDescription");
            String chkUsed = request.getParameter("chkUsed");
            String companyCode = request.getParameter("cbCompany");
            if(chkUsed == null){
                chkUsed = "0";
            }
            
            equip.setCode(code);
            equip.setDescription(description);
            equip.setActive(Integer.parseInt(chkUsed));
            equip.setCompanyCode(companyCode);
            
            boolean saved = equipmentService.updateEquipmentBrand(equip, equipmentService.MODE_INSERT);
            if(saved == false){
                view = new ModelAndView("error");
                view.addObject("message", "Error processing equipment brand request. Please try again later");
                return view;
            }

            view.addObject("message", "Record Saved Successfully.");
            
        }else if(operation.trim().equals("E")){
            EquipmentBrand equip = new EquipmentBrand();
            String code = request.getParameter("txtCode");
            String description = request.getParameter("txtDescription");
            String chkUsed = request.getParameter("chkUsed");
            String companyCode = request.getParameter("cbCompany");
            if(chkUsed == null){
                chkUsed = "0";
            }
            
            String txtId = request.getParameter("txtId");
            
            if(txtId == null || txtId.trim().equals("")){
                view = new ModelAndView("error");
                view.addObject("message","Unable to complete update.");
                return view;
            }
            equip.setId(Long.parseLong(txtId));
            equip.setCode(code);
            equip.setDescription(description);
            equip.setActive(Integer.parseInt(chkUsed));
            equip.setCompanyCode(companyCode);

            boolean saved = equipmentService.updateEquipmentBrand(equip, equipmentService.MODE_UPDATE);
            if(saved == false){
                view = new ModelAndView("error");
                view.addObject("message", "Error processing equipment brand request. Please try again later");
                return view;
            }

            view.addObject("message", "Record Saved Successfully.");
        }
        return view;
    }

    /* ============= Equipment Type ==================== */
    public ModelAndView viewAllEquipmentType(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("equipment_type_view");

        String cbSearch = request.getParameter("cbSearch");
        String txtSearch = request.getParameter("txtSearch");

        String companyCode = this.getActiveEmployeeCompanyCode(request);

        List<EquipmentType> list = null;
        if(cbSearch == null || txtSearch == null){

            if(this.getUserPrincipal(request).getGroupCompanyUser() == 1){
                list = equipmentService.findAllEquipmentType();
                view.addObject("showCompany", "Y");
            }else{
                Employee employee = this.getEmployeeService().findEmployeeBasicById(getUserPrincipal(request).getEmployeeId());
                list = equipmentService.findAllEquipmentTypeByCompany(employee.getCompanyCode());
            }
        }else{

            if(this.getUserPrincipal(request).getGroupCompanyUser() == 1){
                list = equipmentService.findAllEquipmentType(cbSearch, txtSearch);
                view.addObject("showCompany", "Y");
            }else{
                Employee employee = this.getEmployeeService().findEmployeeBasicById(getUserPrincipal(request).getEmployeeId());
                list = equipmentService.findAllEquipmentTypeByCompany(employee.getCompanyCode(), cbSearch, txtSearch);
            }
        }

        view.addObject("result", list);
        return view;
    }

    public ModelAndView editEquipmentType(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("equipment_type_edit");
        String param = request.getParameter("idx");
        if(param == null){
            return viewAllEquipmentType(request, response);
        }
        long id = Long.parseLong(param);
        EquipmentType equip = equipmentService.findEquipmentTypeByID(id);

        //get company info
        view = getAndAppendCompanyToView(view, request);
        
        view.addObject("equipment", equip);
        return view;
    }

    public ModelAndView newEquipmentType(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("equipment_type_edit");

        //get company info
        view = getAndAppendCompanyToView(view, request);

        view.addObject("equipment", new EquipmentType());
        return view;
    }

    public ModelAndView deleteEquipmentType(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("");
        List<Long> ids = new ArrayList<Long>();
        //get mode of request
        String mode = request.getParameter("mode");
        //check if mode is for more than one record
        //get mode, if mode is equals 1, then process for multiple
        if(mode != null && mode.trim().equals("1")){
            String[] args = request.getParameterValues("_chk");
            for(String id : args){
                ids.add(Long.parseLong(id));
            }
        }else{//then it is zero
            ids.add(Long.parseLong(request.getParameter("id")));
        }
        equipmentService.deleteEquipmentType(ids);
       //delete
        return viewAllEquipmentType(request, response);
    }

    public ModelAndView processEquipmentTypeRequest(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("message");
        String operation = request.getParameter("txtMode");
        //operation mode: N= New, E=Edit
        if(operation.trim().equals("N")){
            EquipmentType equip = new EquipmentType();
            String code = request.getParameter("txtCode");
            String description = request.getParameter("txtDescription");
            String companyCode = request.getParameter("cbCompany");
            
            equip.setCode(code);
            equip.setDescription(description);
            equip.setCompanyCode(companyCode);

            boolean saved = equipmentService.updateEquipmentType(equip, equipmentService.MODE_INSERT);
            if(saved == false){
                view = new ModelAndView("error");
                view.addObject("message", "Error processing equipment type request. Please try again later");
                return view;
            }

            view.addObject("message", "Record Saved Successfully.");

        }else if(operation.trim().equals("E")){
            EquipmentType equip = new EquipmentType();
            String code = request.getParameter("txtCode");
            String description = request.getParameter("txtDescription");
            String companyCode = request.getParameter("cbCompany");
            
            String txtId = request.getParameter("txtId");

            if(txtId == null || txtId.trim().equals("")){
                view = new ModelAndView("error");
                view.addObject("message","Unable to complete update.");
                return view;
            }
            equip.setId(Long.parseLong(txtId));
            equip.setCode(code);
            equip.setDescription(description);
            equip.setCompanyCode(companyCode);

            boolean saved = equipmentService.updateEquipmentType(equip, equipmentService.MODE_UPDATE);
            if(saved == false){
                view = new ModelAndView("error");
                view.addObject("message", "Error processing equipment type request. Please try again later");
                return view;
            }
            
            view.addObject("message", "Record Saved Successfully.");
        }
        return view;
    }

    /* ========== Equipment =====================*/
    public ModelAndView viewAllEquipment(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("equipment_view");

        String cbSearch = request.getParameter("cbSearch");
        String txtSearch = request.getParameter("txtSearch");

        List<Equipment> list = null;
        if(cbSearch == null || txtSearch == null){

            if(this.getUserPrincipal(request).getGroupCompanyUser() == 1){
                list = equipmentService.findAllEquipment();
                view.addObject("showCompany", "Y");
            }else{
                Employee employee = this.getEmployeeService().findEmployeeBasicById(getUserPrincipal(request).getEmployeeId());
                list = equipmentService.findAllEquipmentByCompany(employee.getCompanyCode());
            }
        }else{

            if(this.getUserPrincipal(request).getGroupCompanyUser() == 1){
                list = equipmentService.findAllEquipment(cbSearch, txtSearch);
                view.addObject("showCompany", "Y");
            }else{
                Employee employee = this.getEmployeeService().findEmployeeBasicById(getUserPrincipal(request).getEmployeeId());
                list = equipmentService.findAllEquipmentByCompany(employee.getCompanyCode(), cbSearch, txtSearch);
            }
        }

        view.addObject("result", list);
        return view;
    }

    public ModelAndView editEquipment(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("equipment_edit");
        String param = request.getParameter("idx");
        if(param == null){
            return viewAllEquipment(request, response);
        }

        //get object for edit
        long id = Long.parseLong(param);
        Equipment equip = equipmentService.findEquipmentByID(id);

        String companyCode = equip.getCompanyCode();

        List<EquipmentBrand> brandList = equipmentService.findAllEquipmentBrandByCompany(companyCode);
        List<EquipmentType> typeList = equipmentService.findAllEquipmentTypeByCompany(companyCode);
        //add lists to view instance
        view.addObject("brandList", brandList);
        view.addObject("typeList", typeList);

        //get company info
        view = getAndAppendCompanyToView(view, request);

        view.addObject("equipment", equip);
        return view;
    }

    public ModelAndView newEquipment(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("equipment_edit");

        //get company info
        view = getAndAppendCompanyToView(view, request);

        String companyCode = request.getParameter("cbCompany");
        if(companyCode == null) {
            companyCode = "";
        }

        if(this.getUserPrincipal(request).getGroupCompanyUser() == 0){
            Employee employee = employeeService.findEmployeeBasicById(getUserPrincipal(request).getEmployeeId());
            companyCode = employee.getCompanyCode();
        }
        
        //get list of brand and  type
        List<EquipmentBrand> brandList = equipmentService.findAllEquipmentBrandByCompany(companyCode);
        List<EquipmentType> typeList = equipmentService.findAllEquipmentTypeByCompany(companyCode);

        view.addObject("brandList", brandList);
        view.addObject("typeList", typeList);

        Equipment equipment = new Equipment();
        equipment.setCompanyCode(companyCode);

        view.addObject("equipment", equipment);
        return view;
    }

    public ModelAndView deleteEquipment(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("");
        List<Long> ids = new ArrayList<Long>();
        //get mode of request
        String mode = request.getParameter("mode");
        //check if mode is for more than one record
        //get mode, if mode is equals 1, then process for multiple
        if(mode != null && mode.trim().equals("1")){
            String[] args = request.getParameterValues("_chk");
            for(String id : args){
                ids.add(Long.parseLong(id));
            }
        }else{//then it is zero
            ids.add(Long.parseLong(request.getParameter("id")));
        }
        equipmentService.deleteEquipment(ids);
       //delete
        return viewAllEquipment(request, response);
    }

    public ModelAndView processEquipmentRequest(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("message");
        String operation = request.getParameter("txtMode");
        //operation mode: N= New, E=Edit
        
        if(operation.trim().equals("N")){
            Equipment equip = new Equipment();
            String brand = request.getParameter("cbBrand");
            String type = request.getParameter("cbType");
            String product = request.getParameter("txtProduct");
            String serial = request.getParameter("txtSerial");
            String companyCode = request.getParameter("cbCompany");

            equip.setBrandID(Long.parseLong(brand));
            equip.setTypeID(Long.parseLong(type));
            equip.setProductName(product);
            equip.setSerialNumber(serial);
            equip.setCompanyCode(companyCode);

            boolean saved = equipmentService.updateEquipment(equip, equipmentService.MODE_INSERT);
            if(saved == false){
                view = new ModelAndView("error");
                view.addObject("message", "Error processing equipment request. Please try again later");
                return view;
            }

            view.addObject("message", "Record Saved Successfully.");

        }else if(operation.trim().equals("E")){
            Equipment equip = new Equipment();
            String brand = request.getParameter("cbBrand");
            String type = request.getParameter("cbType");
            String product = request.getParameter("txtProduct");
            String serial = request.getParameter("txtSerial");
            String companyCode = request.getParameter("cbCompany");

            String txtId = request.getParameter("txtId");

            if(txtId == null || txtId.trim().equals("")){
                view = new ModelAndView("error");
                view.addObject("message","Unable to complete update.");
                return view;
            }
            equip.setId(Long.parseLong(txtId));
            equip.setBrandID(Long.parseLong(brand));
            equip.setTypeID(Long.parseLong(type));
            equip.setProductName(product);
            equip.setSerialNumber(serial);
            equip.setCompanyCode(companyCode);

            boolean saved = equipmentService.updateEquipment(equip, equipmentService.MODE_UPDATE);
            if(saved == false){
                view = new ModelAndView("error");
                view.addObject("message", "Error processing equipment request. Please try again later");
                return view;
            }
            
            view.addObject("message", "Record Saved Successfully.");
        }
        return view;
    }

    public ModelAndView viewAllEmployeeForEquipment(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("equipment_employee_view");

        String companyCode = this.getActiveEmployeeCompanyCode(request);
        //do search here...
        String cbSearch = request.getParameter("cbSearch");
        String txtSearch = request.getParameter("txtSearch");

        //get list of employee
        List<Employee> employees = null;
        if(cbSearch == null || txtSearch == null){
            employees = getEmployeeService().findAllEmployeeForBasicByCompany(companyCode);
        }else{
            employees = getEmployeeService().findAllEmployeeForBasicByCompany(companyCode, cbSearch, txtSearch);
        }
        view.addObject("result", employees);
        return view;
    }

    public ModelAndView editAllEmployeeForEquipment(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("equipment_assign_edit");

        String companyCode = this.getActiveEmployeeCompanyCode(request);

        String idx = request.getParameter("idx");
        if(idx == null){
            view = new ModelAndView("error_mini");
            view.addObject("message","Unable to process request");
            return view;
        }
        Employee employee = employeeService.findEmployeeBasicById(Long.parseLong(idx));
        List<Equipment> list = equipmentService.findAllEquipmentByCompany(companyCode);

        //get all equipments for user
        List<EquipmentAssigned> assignedEquipments = equipmentService.findAllAssignedEquipmentForEmployee(Long.parseLong(idx));

        view.addObject("employee", employee);
        view.addObject("equipmentList", list);
        view.addObject("assigned", assignedEquipments);
        return view;
    }

    public ModelAndView preloadEmployeeForEquipment(HttpServletRequest request, HttpServletResponse response, long employeeId){
        ModelAndView view = new ModelAndView("equipment_assign_edit");

        String companyCode = this.getActiveEmployeeCompanyCode(request);

        Employee employee = employeeService.findEmployeeBasicById(employeeId);
        List<Equipment> list = equipmentService.findAllEquipmentByCompany(companyCode);

        //get all equipments for user
        List<EquipmentAssigned> assignedEquipments = equipmentService.findAllAssignedEquipmentForEmployee(employeeId);

        view.addObject("employee", employee);
        view.addObject("equipmentList", list);
        view.addObject("assigned", assignedEquipments);
        return view;
    }

    public ModelAndView equipmentEnquiryforEmployee(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("employee/equipment_assign_enquiry");

        Users user = this.getUserPrincipal(request);
        long employeeId = user.getEmployeeId();

        String companyCode = this.getActiveEmployeeCompanyCode(request);

        Employee employee = employeeService.findEmployeeBasicById(employeeId);
        List<Equipment> list = equipmentService.findAllEquipmentByCompany(companyCode);

        //get all equipments for user
        List<EquipmentAssigned> assignedEquipments = equipmentService.findAllAssignedEquipmentForEmployee(employeeId);

        view.addObject("employee", employee);
        view.addObject("equipmentList", list);
        view.addObject("assigned", assignedEquipments);
        return view;
    }

    public ModelAndView processEquipmentAssignedRequest(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("success_mini");
        //String operation = request.getParameter("txtMode");
        //operation mode: N= New, E=Edit

        String txtEmp = request.getParameter("txtEmp");
        String[] equipments = request.getParameterValues("chkEquipment");

        //convert value to int
        int employeeId = Integer.parseInt(txtEmp);

        //convert value to list of type long
        List<Long> equipmentIds = new ArrayList<Long>();
        for(String equip : equipments){
            //check usage of equipment by another employee
            int count = equipmentService.checkUsedEquipment(Long.parseLong(equip), employeeId);
            if(count >= 1){
                view = preloadEmployeeForEquipment(request, response, employeeId);
                view.addObject("message", "One of the specified equipment is being used.");
                view.addObject("equipUsed", equip);
                return view;
            }
            equipmentIds.add(Long.parseLong(equip));
        }

        boolean saved = equipmentService.assignEquipments(employeeId, equipmentIds);
        if(saved == false){
            view = new ModelAndView("error");
            view.addObject("message", "Error assigning equipment. Please try again later");
            return view;
        }

        view.addObject("message", "Record Saved Successfully.");


        return view;
    }

    /**
     * @return the equipmentService
     */
    public EquipmentService getEquipmentService() {
        return equipmentService;
    }

    /**
     * @param equipmentService the equipmentService to set
     */
    public void setEquipmentService(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    /**
     * @return the employeeService
     */
    public EmployeeService getEmployeeService() {
        return employeeService;
    }

    /**
     * @param employeeService the employeeService to set
     */
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    
}
