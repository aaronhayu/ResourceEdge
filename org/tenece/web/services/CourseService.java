
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
import org.tenece.hr.data.dao.CourseDAO;
import org.tenece.web.data.Course;
import org.tenece.web.data.CourseApplication;
import org.tenece.web.data.CourseFeedback;

/**
 *
 * @author jeffry.amachree
 */
public class CourseService extends BaseService{

    private CourseDAO courseDAO = null;
    /** Creates a new instance of PayrollService */
    public CourseService() {
    }

    public List<Course> findAllCourses(){
        return getCourseDAO().getAllCourses();
    }

    public List<Course> findAllCoursesByCompany(String code){
        return getCourseDAO().getAllCoursesByCompany(code);
    }

    public Course findCourseById(long id){
        return getCourseDAO().getCourseById(id);
    }

    public boolean updateCourse(Course course, int mode){
        try{
            int affected = 0;
            if(mode == CourseService.MODE_INSERT){
                affected = getCourseDAO().createNewCourse(course);
            }else if(mode == CourseService.MODE_UPDATE){
                affected = getCourseDAO().updateCourse(course);
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

    public boolean deleteCourse(List<Integer> ids){
        try{
            int affected = getCourseDAO().deleteCourses(ids);
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

    public List<CourseApplication> findAllCourseApplication(){
        return getCourseDAO().getAllCourseApplication();
    }

    public List<CourseApplication> findAllCourseApplicationByCompany(String code){
        return getCourseDAO().getAllCourseApplicationByCompany(code);
    }

    public List<CourseApplication> findAllCourseApplication(String searchKey, String searchText){
        return getCourseDAO().getAllCourseApplication(searchKey, searchText);
    }

    public List<CourseApplication> findAllCourseApplicationByCompany(String code, String searchKey, String searchText){
        return getCourseDAO().getAllCourseApplicationByCompany(code, searchKey, searchText);
    }

    public List<CourseApplication> findAllCourseApplicationForEmployee(long id){
        return getCourseDAO().getAllCourseApplicationForEmployee(id);
    }

    public CourseApplication findCourseApplicationById(long id){
        return getCourseDAO().getCourseApplicationById(id);
    }

    public CourseApplication findCourseApplicationByTransaction(long id){
        return getCourseDAO().getCourseApplicationByTransaction(id);
    }

    public long initiateTransaction(CourseApplication course){
        //get initiator, this isn our current user
        return this.initiateTransaction(course, "Application for Training", (int)course.getEmployeeId(), "TRAINING");
    }

    public boolean updateCourseApplication(CourseApplication course, int mode){
        try{
            int affected = 0;
            if(mode == CourseService.MODE_INSERT){
                affected = getCourseDAO().createNewCourseApplication(course);
            }else if(mode == CourseService.MODE_UPDATE){
                affected = getCourseDAO().updateCourseApplication(course);
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

    public boolean deleteCourseApplication(List<Integer> ids){
        try{
            int affected = getCourseDAO().deleteCourseApplications(ids);
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

    public CourseFeedback findCourseFeedbackByApplication(long id){
        return getCourseDAO().getCourseFeedbackByApplication(id);
    }

    public int updateCourseFeedback(CourseFeedback feedback) {
        try{
            return getCourseDAO().createNewCourseFeedback(feedback);
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * @return the courseDAO
     */
    public CourseDAO getCourseDAO() {
        if(courseDAO == null){
            courseDAO = new CourseDAO();
        }
        return courseDAO;
    }
    
}
