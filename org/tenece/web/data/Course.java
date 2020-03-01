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

package org.tenece.web.data;

import java.util.Date;

/**
 * This is the Data object to handle Department records.
 * It is also tied to DepartmentDAO for all Database Operation
 * @author jeffry.amachree
 */

public class Course extends BaseData {
    private int id;
    private String course;
    private String courseDetail;
    private String organizer;
    private Date startDate;
    private Date endDate;
    private int noOfDays;
    private String startTime;
    private float courseFee = 0f;
    private float travelExpenses = 0f;
    private float allowance = 0f;
    private float misc_Cost = 0f;
    private String courseVenue;
    /** Creates a new instance of Department */
    public Course() {
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the course
     */
    public String getCourse() {
        return course;
    }

    /**
     * @param course the course to set
     */
    public void setCourse(String course) {
        this.course = course;
    }

    /**
     * @return the courseDetail
     */
    public String getCourseDetail() {
        return courseDetail;
    }

    /**
     * @param courseDetail the courseDetail to set
     */
    public void setCourseDetail(String courseDetail) {
        this.courseDetail = courseDetail;
    }

    /**
     * @return the organizee
     */
    public String getOrganizer() {
        return organizer;
    }

    /**
     * @param organizee the organizee to set
     */
    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    /**
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the noOfDays
     */
    public int getNoOfDays() {
        return noOfDays;
    }

    /**
     * @param noOfDays the noOfDays to set
     */
    public void setNoOfDays(int noOfDays) {
        this.noOfDays = noOfDays;
    }

    /**
     * @return the startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the travelExpenses
     */
    public float getTravelExpenses() {
        return travelExpenses;
    }

    /**
     * @param travelExpenses the travelExpenses to set
     */
    public void setTravelExpenses(float travelExpenses) {
        this.travelExpenses = travelExpenses;
    }

    /**
     * @return the allowance
     */
    public float getAllowance() {
        return allowance;
    }

    /**
     * @param allowance the allowance to set
     */
    public void setAllowance(float allowance) {
        this.allowance = allowance;
    }

    /**
     * @return the misc_Cost
     */
    public float getMisc_Cost() {
        return misc_Cost;
    }

    /**
     * @param misc_Cost the misc_Cost to set
     */
    public void setMisc_Cost(float misc_Cost) {
        this.misc_Cost = misc_Cost;
    }

    /**
     * @return the courseVenue
     */
    public String getCourseVenue() {
        return courseVenue;
    }

    /**
     * @param courseVenue the courseVenue to set
     */
    public void setCourseVenue(String courseVenue) {
        this.courseVenue = courseVenue;
    }

    /**
     * @return the courseFee
     */
    public float getCourseFee() {
        return courseFee;
    }

    /**
     * @param courseFee the courseFee to set
     */
    public void setCourseFee(float courseFee) {
        this.courseFee = courseFee;
    }

}
