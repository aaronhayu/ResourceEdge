
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

import java.util.Date;
import java.util.List;
import org.tenece.hr.data.dao.ProjectDAO;
import org.tenece.web.data.Project;
import org.tenece.web.data.ProjectGroup;
import org.tenece.web.data.ProjectUtilization;

/**
 *
 * @author jeffry.amachree
 */
public class ProjectService extends BaseService{
    private ProjectDAO projectDAO;
    /** Creates a new instance of PayrollService */
    public ProjectService() {
    }

    public List<ProjectGroup> findAllProjectGroup(){
        return getProjectDAO().getAllProjectGroups();
    }

    public List<ProjectGroup> findAllProjectGroupByCompany(String code){
        return getProjectDAO().getAllProjectGroupsByCompany(code);
    }

    public ProjectGroup findProjectGroupById(long id){
        return getProjectDAO().getProjectGroupById(id);
    }

    public boolean updateProjectGroup(ProjectGroup project, int mode){
        try{
            int affected = 0;
            if(mode == ProjectService.MODE_INSERT){
                affected = getProjectDAO().createNewProjectGroup(project);
            }else if(mode == ProjectService.MODE_UPDATE){
                affected = getProjectDAO().updateProjectGroup(project);
            }
            if(affected <= 0){
                return false;
            }
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteProjectGroup(List<Long> ids){
        try{
            int affected = getProjectDAO().deleteProjectGroups(ids);
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

    public List<Project> findAllProject(){
        return getProjectDAO().getAllProject();
    }
    public List<Project> findAllProjectByCompany(String code){
        return getProjectDAO().getAllProjectByCompany(code);
    }

    public Project findProjectById(long id){
        return getProjectDAO().getProjectById(id);
    }

    public boolean updateProject(Project project, int mode){
        try{
            int affected = 0;
            if(mode == ProjectService.MODE_INSERT){
                affected = getProjectDAO().createNewProject(project);
            }else if(mode == ProjectService.MODE_UPDATE){
                affected = getProjectDAO().updateProject(project);
            }
            if(affected <= 0){
                return false;
            }
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteProject(List<Long> ids){
        try{
            int affected = getProjectDAO().deleteProjects(ids);
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

    /* ******** Project utilization ***************** */
    public List<ProjectUtilization> findAllProjectUtilization(long employeeId){
        return getProjectDAO().getAllProjectUtilization(employeeId);
    }

    public ProjectUtilization findProjectUtilizationById(long id, long employeeId){
        return getProjectDAO().getProjectUtilizationById(id, employeeId);
    }

    public boolean updateProjectUtilization(ProjectUtilization project, int mode){
        try{
            int affected = 0;
            if(mode == ProjectService.MODE_INSERT){
                affected = getProjectDAO().createNewProjectUtilization(project);
            }else if(mode == ProjectService.MODE_UPDATE){
                affected = getProjectDAO().updateProjectUtilization(project);
            }
            if(affected <= 0){
                return false;
            }
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteProjectUtilization(List<Long> ids){
        try{
            int affected = getProjectDAO().deleteProjectUtilizations(ids);
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

    public int checkProjectByDate(Date date, long projectId, long employeeId){
        return getProjectDAO().checkProjectByDate(date, projectId, employeeId);
        
    }

    public int checkTotalHoursByDate(Date date, long employeeId){
        return getProjectDAO().checkDailyHours(date, employeeId);
    }

    public List<ProjectUtilization> findAllPendingProjectUtilizationSummary(String status){
        return getProjectDAO().getAllPendingProjectUtilizationSummary(status);
    } 
    public List<ProjectUtilization> findAllPendingProjectUtilizationSummaryByCompany(String status, String companyCode){
        return getProjectDAO().getAllPendingProjectUtilizationSummaryByCompany(status, companyCode);
    }

    public int approveProjectUtilizations(List<Long> ids){
        return getProjectDAO().approveProjectUtilizations(ids);
    }

    /**
     * @return the projectDAO
     */
    public ProjectDAO getProjectDAO() {
        if(projectDAO == null){
            projectDAO = new ProjectDAO();
        }
        return projectDAO;
    }
}
