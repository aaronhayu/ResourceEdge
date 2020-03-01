
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

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.tenece.api.excel.XLSReader;
import org.tenece.hr.data.dao.EmployeeAttendanceDAO;
import org.tenece.hr.data.dao.EmployeeDAO;
import org.tenece.web.common.DateUtility;
import org.tenece.web.data.Employee;
import org.tenece.web.data.EmployeeAttendance;
import org.tenece.web.data.FileUpload;

/**
 *
 * @author jeffry.amachree
 */
public class TimesheetService extends BaseService{
    private EmployeeAttendanceDAO employeeAttendanceDAO;
    private EmployeeDAO employeeDAO;
    /** Creates a new instance of TimesheetService */
    public TimesheetService() {
    }
    
    public List<EmployeeAttendance> findAllAttendance(){
        return getEmployeeAttendanceDAO().getAllAttendance();
    }
    
    public List<EmployeeAttendance> findAllEmployeeAttendance(int employeeId){
        return getEmployeeAttendanceDAO().getAllEmployeeAttendance(employeeId);
    }
    
    public EmployeeAttendance findAttendance(int id){
        List<EmployeeAttendance> attendance = getEmployeeAttendanceDAO().getAttendance(id);
        if(attendance.size() > 0){
            return attendance.get(0);
        }else{
            return new EmployeeAttendance();
        }
    }
    
    public boolean updateEmployeeAttendance(EmployeeAttendance attendance, int mode){
        if(this.MODE_INSERT == mode){
            getEmployeeAttendanceDAO().createNewAttendance(attendance);
        }else if(this.MODE_UPDATE == mode){
            getEmployeeAttendanceDAO().updateAttendance(attendance);
        }
        return true;
    }

    public boolean deleteEmployeeAttendance(int id){
        try{
            getEmployeeAttendanceDAO().deleteAttendance(id);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    
    public boolean deleteEmployeeAttendance(List<Integer> id){
        try{
            getEmployeeAttendanceDAO().deleteAttendance(id);
            return true;
        }catch(Exception e){
            return false;
        }
    }
 
    public List<EmployeeAttendance> saveUploadedAttendanceFile(FileUpload fileUpload, String fileType, String device, String companyCode) throws IllegalStateException, Exception{

        //get device type
        List<EmployeeAttendance> attendanceList = null;
        if(Integer.parseInt(device) == 0){ //default
            attendanceList = getDefaultTemplateAttendance(fileUpload, companyCode);
        }else if(Integer.parseInt(device) == 1){
            try {
                attendanceList = getIGuardTemplateAttendance(fileUpload, companyCode);
            } catch (ParseException ex) {
                Logger.getLogger(TimesheetService.class.getName()).log(Level.SEVERE, null, ex);
                throw new Exception("Invalid numeric value in XLS file");
            } catch (IOException ex) {
                Logger.getLogger(TimesheetService.class.getName()).log(Level.SEVERE, null, ex);
                throw new Exception("Unable to read file, ensure the file is a valid Microsoft OLE file.");
            } catch (Exception ex) {
                Logger.getLogger(TimesheetService.class.getName()).log(Level.SEVERE, null, ex);
                throw new Exception("Invalid Microsoft OLE file. Ensure the file is a valid Excel File");
            }
        }
        
        return attendanceList;
    }

    public List<EmployeeAttendance> getIGuardTemplateAttendance(FileUpload fileUpload, String companyCode) throws ParseException, IOException, Exception{
        
        List<EmployeeAttendance> attendanceList = new ArrayList<EmployeeAttendance>();

        //get xlsfile content into List array
        File xlsFile = new File(fileUpload.getAbsolutePath());
        XLSReader xlsReader = new XLSReader();
        List<List> records = xlsReader.convertXLSToList(xlsFile, 0);
        if(records.size() == 0){
            throw new IllegalStateException("No record in XLS file");
        }

        for(int a = 0; a < records.size(); a++){
            EmployeeAttendance attendance = new EmployeeAttendance();
            List<Object> colValue = records.get(a);
            
            System.out.println(">>>>>>>:" + colValue.size());
            if(a > 0){
                for(int i = 0; i < colValue.size(); i++){
                    if((i + 1) == 2){//position 2
                        attendance.setEmployeeId((int) Math.round(Double.parseDouble(String.valueOf(colValue.get(i)).trim())));
                        //validate employee ID
                        try{
                            Employee employee = getEmployeeDAO().getEmployeeBasicDataByEmpNumberAndCompany(attendance.getEmployeeId(), companyCode);
                            if(employee.getEmployeeNumber() <= 0){
                                throw new Exception("Invalid Employee");
                            }
                            attendance.setEmployeeName(employee.getFullName());
                            attendance.setErr(0);
                        }catch(Exception err){
                            attendance.setErr(1);
                        }

                    }else if((i + 1) == 6){
                        attendance.setActionDate((Date) colValue.get(i));
                    }else if((i + 1) == 7){
                        attendance.setActionTime((String)colValue.get(i));
                    }else if((i + 1) == 8){
                        attendance.setDevice(((String) colValue.get(i)).trim());
                    }else if((i + 1) == 9){
                        String actionType = (String)colValue.get(i);
                        if(actionType.trim().equalsIgnoreCase("IN")){
                            attendance.setActionType("1");
                        }else if(actionType.trim().equalsIgnoreCase("OUT")){
                            attendance.setActionType("0");
                        }
                    }//:
                }

            attendanceList.add(attendance);
            }//:
        }
        return attendanceList;
    }

    public List<EmployeeAttendance> getDefaultTemplateAttendance(FileUpload fileUpload, String companyCode){
        try{
            List<EmployeeAttendance> attendanceList = new ArrayList<EmployeeAttendance>();
            //get file content
            String fileContent = new String(fileUpload.getBytes());
            //get all rows as array based on new line
            String[] rows = fileContent.split("\n");

            for(String row : rows){
                //create a dummy object
                EmployeeAttendance attendance = new EmployeeAttendance();
                String[] columns = row.split(",");

                String empNumber = columns[0];
                String strDate = columns[1].trim();
                String actionType = columns[2].trim();
                String actionTime = columns[3].trim();
                String device = columns[4].trim();

                //get empID from empNumber
                Employee emp = getEmployeeDAO().getEmployeeBasicDataByEmpNumberAndCompany(Integer.parseInt(empNumber), companyCode);
                if(emp == null){ throw new Exception("Employee is Invalid"); }

                Date actionDate = DateUtility.getDateFromString(strDate, "MMddyyyy");

                attendance.setEmployeeId(
                        Integer.parseInt(String.valueOf(emp.getEmployeeNumber())));
                attendance.setActionDate(actionDate);
                attendance.setActionTime(actionTime);
                attendance.setActionType(actionType);
                attendance.setDevice(device);

                attendanceList.add(attendance);
            }
            return attendanceList;
        }catch(Exception e){
            e.printStackTrace();
            return new ArrayList<EmployeeAttendance>();
        }
    }

    public boolean saveBulkEmployeeAttendance(List<EmployeeAttendance> attendanceList, String companyCode){
        try{
            //process list
            int saveCount = getEmployeeAttendanceDAO().createBulkAttendance(attendanceList, companyCode);
            if(saveCount == 0){
                throw new Exception("Unable to save bulk attendance");
            }
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    
    public EmployeeAttendanceDAO getEmployeeAttendanceDAO() {
        if(employeeAttendanceDAO == null){
            employeeAttendanceDAO = new EmployeeAttendanceDAO();
        }
        return employeeAttendanceDAO;
    }

    public EmployeeDAO getEmployeeDAO() {
         if(employeeDAO == null){
            employeeDAO = new EmployeeDAO();
        }
        return employeeDAO;
    }

}
