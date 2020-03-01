
/*
 * (c) Copyright 2009 The Tenece Professional Services.
 *
 * Created on 20 July 2009, 10:45
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

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import org.tenece.api.excel.XLSReader;
import org.tenece.hr.constant.AppraisalLevelConstant;
import org.tenece.hr.data.dao.AppraisalCompetenceDAO;
import org.tenece.hr.data.dao.AppraisalCriteriaDAO;
import org.tenece.hr.data.dao.AppraisalDAO;
import org.tenece.hr.data.dao.AppraisalGroupDAO;
import org.tenece.hr.data.dao.AppraisalRatingLangaugeDAO;
import org.tenece.hr.data.dao.EmployeeDAO;
import org.tenece.hr.exception.RecordExistException;
import org.tenece.hr.exception.UnknownException;
import org.tenece.web.data.Appraisal;
import org.tenece.web.data.AppraisalCompetence;
import org.tenece.web.data.AppraisalCriteria;
import org.tenece.web.data.AppraisalGroup;
import org.tenece.web.data.AppraisalInformation;
import org.tenece.web.data.AppraisalRatingLanguage;
import org.tenece.web.data.Employee;
import org.tenece.web.data.EmployeeAppraisalCompetence;
import org.tenece.web.data.FileUpload;

/**
 *
 * @author jeffry.amachree
 */
public class AppraisalService extends BaseService {

    private AppraisalDAO appraisalDAO;
    private AppraisalCriteriaDAO appraisalCriteriaDAO;
    private AppraisalGroupDAO appraisalGroupDAO;
    private AppraisalCompetenceDAO appraisalCompetenceDAO;
    private AppraisalRatingLangaugeDAO appraisalRatingLangaugeDAO;
    private EmployeeDAO employeeDAO;

    /** Creates a new instance of PayrollService */
    public AppraisalService() {
    }

    /* ----------- Criteria ---------------- */
    /**
     * 
     * @return List of all Appraisal Criteria
     */
    public List<AppraisalCriteria> findAllAppraisalCriteria() {
        try {
            return getAppraisalCriteriaDAO().getAllAppraisalCriteria();
        } catch (Exception e) {
            return new ArrayList<AppraisalCriteria>();
        }
    }

    public List<AppraisalCriteria> findAllAppraisalCriteriaByCompany(String code) {
        try {
            return getAppraisalCriteriaDAO().getAllAppraisalCriteriaByCompany(code);
        } catch (Exception e) {
            return new ArrayList<AppraisalCriteria>();
        }
    }

    /**
     *
     * @param id
     * @return
     */
    public AppraisalCriteria findAllAppraisalCriteriaByID(int id) {
        try {
            return getAppraisalCriteriaDAO().getAppraisalCriteriaById(id);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     *
     * @param criteria
     * @param mode
     * @return
     */
    public boolean updateAppraisalCriteria(AppraisalCriteria criteria, int mode) {
        try {
            if (this.MODE_INSERT == mode) {
                getAppraisalCriteriaDAO().createNewAppraisalCriteria(criteria);
            } else if (this.MODE_UPDATE == mode) {
                getAppraisalCriteriaDAO().updateAppraisalCriteria(criteria);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     *
     * @param ids
     * @return
     */
    public boolean deleteAppraisalCriteria(List<Integer> ids) {
        try {
            getAppraisalCriteriaDAO().deleteAppraisalCriteria(ids);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    /* ----------- Group ---------------- */
    /**
     *
     * @return List of all Appraisal Group
     */
    public List<AppraisalGroup> findAllAppraisalGroup() {
        try {
            return getAppraisalGroupDAO().getAllAppraisalGroups();
        } catch (Exception e) {
            return new ArrayList<AppraisalGroup>();
        }
    }

    /**
     *
     * @param id
     * @return
     */
    public AppraisalGroup findAllAppraisalGroupByID(int id) {
        try {
            return getAppraisalGroupDAO().getAppraisalGroupById(id);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     *
     * @param id
     * @return
     */
    public List<AppraisalGroup> findAllAppraisalGroupByCriteria(int id) {
        try {
            return getAppraisalGroupDAO().getAppraisalGroupByCriteria(id);
        } catch (Exception e) {
            return new ArrayList<AppraisalGroup>();
        }
    }

    /**
     *
     * @param criteria
     * @param mode
     * @return
     */
    public boolean updateAppraisalGroup(AppraisalGroup group, int mode) {
        try {
            if (this.MODE_INSERT == mode) {
                getAppraisalGroupDAO().createNewAppraisalGroup(group);
            } else if (this.MODE_UPDATE == mode) {
                getAppraisalGroupDAO().updateAppraisalGroup(group);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     *
     * @param ids
     * @return
     */
    public boolean deleteAppraisalGroup(List<Integer> ids) {
        try {
            getAppraisalGroupDAO().deleteAppraisalGroup(ids);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    /* ----------- Competence ---------------- */
    /**
     *
     * @return List of all Appraisal Group
     */
    public List<AppraisalCompetence> findAllAppraisalCompetence() {
        try {
            return getAppraisalCompetenceDAO().getAllAppraisalCompetences();
        } catch (Exception e) {
            return new ArrayList<AppraisalCompetence>();
        }
    }

    /**
     *
     * @param id
     * @return
     */
    public AppraisalCompetence findAllAppraisalCompetenceByID(int id) {
        try {
            return getAppraisalCompetenceDAO().getAppraisalCompetenceById(id);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     *
     * @param id
     * @return
     */
    public EmployeeAppraisalCompetence findEmployeeAppraisalCompetenceByID(int id) {
        try {
            return getAppraisalCompetenceDAO().getEmployeeAppraisalCompetenceByID(id);
        } catch (Exception e) {
            return null;
        }
    }

    public List<AppraisalCompetence> findAllAppraisalCompetenceByGroup(int id) {
        try {
            return getAppraisalCompetenceDAO().getAppraisalCompetenceByGroup(id);
        } catch (Exception e) {
            return new ArrayList<AppraisalCompetence>();
        }
    }

    public List<EmployeeAppraisalCompetence> findEmployeeAllAppraisalCompetenceByGroup(int id) {
        try {
            return getAppraisalCompetenceDAO().getEmployeeAppraisalCompetenceByGroup(id);
        } catch (Exception e) {
            return new ArrayList<EmployeeAppraisalCompetence>();
        }
    }

    public List<AppraisalCompetence> findEmployeeAllAppraisalCompetenceByGroupAndEmployId(int id, int employId) {
        try {
            return getAppraisalCompetenceDAO().getEmployeeAppraisalCompetenceByGroupAndEmployID(id, employId);
        } catch (Exception e) {
            return new ArrayList<AppraisalCompetence>();
        }
    }

    /**
     *
     * @param criteria
     * @param mode
     * @return
     */
    public boolean updateAppraisalCompetence(AppraisalCompetence competence, int mode) {
        try {
            if (this.MODE_INSERT == mode) {
                getAppraisalCompetenceDAO().createNewAppraisalCompetence(competence);
            } else if (this.MODE_UPDATE == mode) {
                getAppraisalCompetenceDAO().updateAppraisalCompetence(competence);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     *
     * @param criteria
     * @param mode
     * @return
     */
    public boolean updateEmployeeAppraisalCompetence(EmployeeAppraisalCompetence competence, int mode) {
        try {
            if (this.MODE_INSERT == mode) {
                getAppraisalCompetenceDAO().createEmployeeNewAppraisalCompetence(competence);
            } else if (this.MODE_UPDATE == mode) {
                getAppraisalCompetenceDAO().updateEmployeeAppraisalCompetence(competence);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     *
     * @param ids
     * @return
     */
    public boolean deleteAppraisalCompetence(List<Integer> ids) {
        try {
            getAppraisalCompetenceDAO().deleteAppraisalCompetence(ids);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /* ----------- Rating language ---------------- */
    /**
     *
     * @return List of all Appraisal Criteria
     */
    public List<AppraisalRatingLanguage> findAllAppraisalRating() {
        try {
            return getAppraisalRatingLangaugeDAO().getAllAppraisalRating();
        } catch (Exception e) {
            return new ArrayList<AppraisalRatingLanguage>();
        }
    }

    /**
     *
     * @param id
     * @return
     */
    public AppraisalRatingLanguage findAppraisalRatingByID(int id) {
        try {
            return getAppraisalRatingLangaugeDAO().getAppraisalRatingById(id);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     *
     * @param criteria
     * @param mode
     * @return
     */
    public boolean updateAppraisalRating(AppraisalRatingLanguage rating, int mode) {
        try {
            if (this.MODE_INSERT == mode) {
                getAppraisalRatingLangaugeDAO().createNewAppraisalRating(rating);
            } else if (this.MODE_UPDATE == mode) {
                getAppraisalRatingLangaugeDAO().updateAppraisalRating(rating);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     *
     * @param ids
     * @return
     */
    public boolean deleteAppraisalRating(List<Integer> ids) {
        try {
            getAppraisalRatingLangaugeDAO().deleteAppraisalRating(ids);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    /* *********************** APPRAISAL TRNX ************************* */

    public Appraisal findAppraisalByTransaction(long id) {
        try {
            return getAppraisalDAO().getAppraisalByTransaction(id);
        } catch (Exception e) {
            return null;
        }
    }

    public AppraisalInformation.Step1 findTransactionAppraisalStep1ByID(long id) {
        try {
            return getAppraisalDAO().getTransactionAppraisalStep1(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
//    public List<Appraisal.Step1> findAllTransactionAppraisalStep1ByID(long id){
//        try{
//            return getAppraisalDAO().getAppraisalByTransaction(id);
//        }catch(Exception e){
//            return null;
//        }
//    }
    /* *********************** APPRAISAL STEPS ************************ */

    public Appraisal.Step1 findAppraisalStep1ByID(int id) {
        try {
            return getAppraisalDAO().getAppraisalStep1(id);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean updateAppraisalStep1(Appraisal.Step1 step1, int mode) {
        try {
            if (this.MODE_INSERT == mode) {
                getAppraisalDAO().createNewAppraisalStep1(step1);
            } else if (this.MODE_UPDATE == mode) {
                List<Integer> ids = new ArrayList<Integer>();
                ids.add(step1.getEmployeeId());
                getAppraisalDAO().deleteAppraisalStep1(ids);
                getAppraisalDAO().createNewAppraisalStep1(step1);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteAppraisalSteps(int id) {
        try {
            List<Integer> ids = new ArrayList<Integer>();
            ids.add(id);
            appraisalDAO.deleteAppraisalStep1(ids);
            appraisalDAO.deleteAppraisalStep2(ids);
            appraisalDAO.deleteAppraisalStep2_Statement(ids);
            appraisalDAO.deleteAppraisalStep3(ids);
            appraisalDAO.deleteAppraisalStep4(ids);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Appraisal.Step2> findAppraisalStep2ByID(int id) {
        try {
            return getAppraisalDAO().getAppraisalStep2(id);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean updateAppraisalStep2(List<Appraisal.Step2> step2List, List<Appraisal.Step2_Statement> stmtList, int employeeId, int mode) {
        try {
            if (this.MODE_INSERT == mode) {
                List<Integer> ids = new ArrayList<Integer>();
                ids.add(employeeId);

                //delete records before add new ones
                getAppraisalDAO().deleteAppraisalStep2(ids);
                getAppraisalDAO().deleteAppraisalStep2_Statement(ids);

                getAppraisalDAO().createNewAppraisalStep2(step2List, stmtList);
            } else if (this.MODE_UPDATE == mode) {
                List<Integer> ids = new ArrayList<Integer>();
                ids.add(employeeId);
                //delete records before add new ones
                getAppraisalDAO().deleteAppraisalStep2(ids);
                getAppraisalDAO().deleteAppraisalStep2_Statement(ids);

                getAppraisalDAO().createNewAppraisalStep2(step2List, stmtList);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Appraisal.Step2_Statement> findAppraisalStep2StatementByID(int id) {
        try {
            return getAppraisalDAO().getAppraisalStep2_Statement(id);
        } catch (Exception e) {
            return new ArrayList<Appraisal.Step2_Statement>();
        }
    }//:

    public List<Appraisal.Step2_Statement> findAppraisalStep2StatementByID(long employeeId, long transactionId) {
        try {
            return getAppraisalDAO().getApproverAppraisalStep2_Statement(employeeId, transactionId);
        } catch (Exception e) {
            return new ArrayList<Appraisal.Step2_Statement>();
        }
    }//:

    public boolean updateAppraisalStep2Statement(Appraisal.Step2_Statement step2, int mode) {
        try {
            if (this.MODE_INSERT == mode) {
                getAppraisalDAO().createNewAppraisalStep2_Statement(step2);
            } else if (this.MODE_UPDATE == mode) {
                List<Integer> ids = new ArrayList<Integer>();
                ids.add(step2.getEmployeeId());
                getAppraisalDAO().deleteAppraisalStep2(ids);
                getAppraisalDAO().createNewAppraisalStep2_Statement(step2);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Appraisal.step2Competence> generateStep2Competencies(int employeeId) {

        try {
            List<Appraisal.step2Competence> competencies = new ArrayList<Appraisal.step2Competence>();

            Appraisal.Step1 step1 = this.findAppraisalStep1ByID(employeeId);

            List<AppraisalGroup> groups = this.findAllAppraisalGroupByCriteria(step1.getCriteriaId());

            for (AppraisalGroup group : groups) {
                Appraisal.step2Competence competence = new Appraisal.step2Competence();
                competence.setGroupId(group.getId());
                competence.setGroupDescription(group.getDescription());
                List<AppraisalCompetence> competenceList = new ArrayList<AppraisalCompetence>();
                //loop thru competence and add as list
                if (group.getForJobSpecific() == 1) {

                    competenceList = this.findEmployeeAllAppraisalCompetenceByGroupAndEmployId(group.getId(), employeeId);

                } else {
                    competenceList = this.findAllAppraisalCompetenceByGroup(group.getId());

                }
                competence.setCompetencies(competenceList);
                competencies.add(competence);
            }
            return competencies;

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<Appraisal.step2Competence>();
        }
    }//:~

    public List<Appraisal.step2Competence> generateTransactionStep2Competencies(long appraisalId,long txtInitiatorId) {

        try {
            List<Appraisal.step2Competence> competencies = new ArrayList<Appraisal.step2Competence>();

            AppraisalInformation.Step1 step1 = this.findTransactionAppraisalStep1ByID(appraisalId);

            List<AppraisalGroup> groups = this.findAllAppraisalGroupByCriteria(step1.getCriteriaId());

            for (AppraisalGroup group : groups) {
                Appraisal.step2Competence competence = new Appraisal.step2Competence();
                competence.setGroupId(group.getId());
                competence.setGroupDescription(group.getDescription());

                //loop thru competence and add as list
                List<AppraisalCompetence> competenceList = new ArrayList<AppraisalCompetence>();
                 if (group.getForJobSpecific() == 1) {

                    competenceList = this.findEmployeeAllAppraisalCompetenceByGroupAndEmployId(group.getId(), (int)txtInitiatorId);

                } else {
                    competenceList = this.findAllAppraisalCompetenceByGroup(group.getId());

                }
                competence.setCompetencies(competenceList);

                //add to main list
                competencies.add(competence);
            }

            return competencies;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<Appraisal.step2Competence>();
        }
    }//:~

    public List<Appraisal.Step3> findAllAppraisalStep3ByID(int id) {
        try {
            return getAppraisalDAO().getAllAppraisalStep3(id);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Appraisal.Step3> findAllAppraisalStep3ByID(long id, long transactionId) {
        try {
            return getAppraisalDAO().getAllAppraisalStep3(id, transactionId);
        } catch (Exception e) {
            return null;
        }
    }

    public Appraisal.Step3 findAppraisalStep3(int id) {
        try {
            return getAppraisalDAO().getAppraisalStep3(id);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean updateAppraisalStep3(Appraisal.Step3 step3, int mode) {
        try {
            if (this.MODE_INSERT == mode) {
                getAppraisalDAO().createNewAppraisalStep3(step3);
            } else if (this.MODE_UPDATE == mode) {
                getAppraisalDAO().updateAppraisalStep3(step3);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Appraisal.Step4 findAppraisalStep4(int id) {
        try {
            return getAppraisalDAO().getAppraisalStep4(id);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean updateAppraisalStep4(Appraisal.Step4 step4, int mode) {
        try {
            List<Integer> ids = new ArrayList<Integer>();
            ids.add(step4.getEmployeeId());

            getAppraisalDAO().deleteAppraisalStep4(ids);
            getAppraisalDAO().createNewAppraisalStep4(step4);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public long processTransaction(Object object, String description, long initiator, String parentId) {
        return this.initiateTransaction(object, description, initiator, parentId);
    }

    public long updateAppraisalStep5(int employeeId) {
        Employee employee = getEmployeeDAO().getEmployeeBasicDataById(employeeId);
        try {
            return processTransaction(employee, "New Performance Appraisal", employeeId, "APPRAISE");
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
        //return getAppraisalDAO().processAppraisalForEmployee(employeeId);
    }

    public AppraisalInformation.Step1 findApproveAppraisalStep1View(long appraisalId) {
        return getAppraisalDAO().getAppraisalStep1View(appraisalId);
    }

    public List<AppraisalInformation.Step2> findApproveAppraisalStep2View(long appraisalId) {
        return getAppraisalDAO().getAppraisalStep2View(appraisalId);
    }

    public List<AppraisalInformation.Step2_Statement> findApproveAppraisalStep2StatementView(long appraisalId) {
        return getAppraisalDAO().getAppraisalStep2StatementView(appraisalId);
    }

    public List<AppraisalInformation.Step3> findApproveAppraisalStep3View(long appraisalId) {
        return getAppraisalDAO().getAppraisalStep3View(appraisalId);
    }

    public List<AppraisalInformation.Step4> findApproveAppraisalStep4View(long appraisalId) {
        return getAppraisalDAO().getAppraisalStep4View(appraisalId);
    }

    public Hashtable<String, Object> generateRatingSummary(int id) {
        return generateRatingSummary((long) id, null);
    }

    public Hashtable<String, Object> generateRatingSummary(long id, Long transactionId) {
        Hashtable holder = new Hashtable();
        try {
            //create a list to hold rate summary
            List<Appraisal.RatingSummary> summary = new ArrayList<Appraisal.RatingSummary>();
            List<Appraisal.RatingSummary> tmpSummary = null;
            //get summary from DAO
            if (transactionId == null) {
                tmpSummary = getAppraisalDAO().getRatingSummary(id);
            } else {
                tmpSummary = getAppraisalDAO().getRatingSummary(id, transactionId);
            }
            //instance to use to hold vital info
            float totalRating = 0f;
            String totalRateText = "";

            //navigate thru each and set the rate_text
            for (Appraisal.RatingSummary rating : tmpSummary) {
                //get rateIndex
                long roundedRate = Math.round(Math.ceil(rating.getRating()));
                AppraisalRatingLanguage rateInst = this.findAppraisalRatingByID((int) roundedRate);
                rating.setRateText(rateInst.getText());
                //add to total rating
                totalRating += rating.getRating();

                summary.add(rating);
            }
            Appraisal.RatingSummary ratingTotal = new Appraisal.RatingSummary();
            float rateAverage = totalRating / tmpSummary.size();
            DecimalFormat df = new DecimalFormat("#.00");
            String rateAverageFormated = df.format(rateAverage);
            Float floatrateAvg = Float.parseFloat(rateAverageFormated);
            //long roundedRate = Math.round(Math.ceil(rateAverage));
            //AppraisalRatingLanguage rateInst = this.findAppraisalRatingByID((int)roundedRate);

            ratingTotal.setSection("");
            ratingTotal.setRating(floatrateAvg);
            if (floatrateAvg < 2) {
                ratingTotal.setRatingLevel(AppraisalLevelConstant.POOR);
            } else if (floatrateAvg >= 2.00 && floatrateAvg <= 2.49) {
                ratingTotal.setRatingLevel(AppraisalLevelConstant.FAIR);
            } else if (floatrateAvg >= 2.50 && floatrateAvg <= 3.49) {
                ratingTotal.setRatingLevel(AppraisalLevelConstant.SATISFACTORY);
            } else if (floatrateAvg >= 3.50 && floatrateAvg <= 4.49) {
                ratingTotal.setRatingLevel(AppraisalLevelConstant.GOOD);
            } else {
                ratingTotal.setRatingLevel(AppraisalLevelConstant.OUTSTANDING);
            }
            //ratingTotal.setRateText(rateInst.getText());

            //System.out.println("total>>>>>>>>>"+"Grand Rate Text: " + rateInst.getText());


            holder.put("summary", summary);
            holder.put("total", ratingTotal);
            return holder;
        } catch (Exception e) {
            e.printStackTrace();
            return holder;
        }
    }
public List<EmployeeAppraisalCompetence> saveUploadedUserlistFile(FileUpload fileUpload, String fileType,String criteriaText,String groupText,String employeeName) throws IllegalStateException, Exception{
        //get device type
        List<EmployeeAppraisalCompetence> userList = null;
        if(fileType.trim().equalsIgnoreCase("csv")){ //default
            userList = getDefaultTemplateForUsersList(fileUpload,criteriaText, groupText, employeeName);
        }else if(fileType.trim().equalsIgnoreCase("xls")){
            try {
                userList = getXLSTemplateForUsersList(fileUpload,criteriaText, groupText, employeeName);
            } catch (ParseException ex) {
                ex.printStackTrace();
                throw new Exception("Invalid numeric value in XLS file");
            } catch (IOException ex) {
                ex.printStackTrace();
                throw new Exception("Unable to read file, ensure the file is a valid Microsoft OLE file.");
            }catch (RecordExistException ex) {
                ex.printStackTrace();
                throw new Exception("Unable to process file.<br/>"+ex.getMessage());
            }catch (UnknownException ex) {
                ex.printStackTrace();
                throw new Exception("Unable to process file.<br/>"+ex.getMessage());
            }catch (NumberFormatException ex) {
                ex.printStackTrace();
                throw new Exception("Unable to process file, Invalid Numeric value specified in file.<br/>"+ex.getMessage());
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new Exception("Invalid Microsoft OLE file. Ensure the file is a valid Excel File");
            }
        }

        return userList;
    }
 public List<EmployeeAppraisalCompetence> getDefaultTemplateForUsersList(FileUpload fileUpload,String criteriaText,String groupText,String employeeName){
        try{
            List<EmployeeAppraisalCompetence> AppCompList = new ArrayList<EmployeeAppraisalCompetence>();
            //get file content
            String fileContent = new String(fileUpload.getBytes());
            //get all rows as array based on new line
            String[] rows = fileContent.split("\n");

            for(String row : rows){
                //create a dummy object
                EmployeeAppraisalCompetence appraisalCompetence = new EmployeeAppraisalCompetence();
                String[] columns = row.split(",");

                String serialNumber = columns[0];
                String strDescription = columns[1].trim();
                String strStatement = columns[2].trim();
                
                appraisalCompetence.setDescription(strDescription);
                appraisalCompetence.setStatement(strStatement);
                appraisalCompetence.setCompetenceText(criteriaText);
                appraisalCompetence.setGroupText(groupText);
                appraisalCompetence.setEmployeeName(employeeName);
                AppCompList.add(appraisalCompetence);
            }
            return AppCompList;
        }catch(Exception e){
            e.printStackTrace();
            return new ArrayList<EmployeeAppraisalCompetence>();
        }
    }
public List<EmployeeAppraisalCompetence> getXLSTemplateForUsersList(FileUpload fileUpload,String criteriaText,String groupText,String employeeName) throws ParseException, IOException, Exception{

        List<EmployeeAppraisalCompetence> userList = new ArrayList<EmployeeAppraisalCompetence>();

        //get xlsfile content into List array
        File xlsFile = new File(fileUpload.getAbsolutePath());
        XLSReader xlsReader = new XLSReader();
        List<List> records = xlsReader.convertXLSToList(xlsFile, 0);
        if(records.size() == 0){
            throw new IllegalStateException("No record in XLS file");
        }

        for(int a = 0; a < records.size(); a++){
            EmployeeAppraisalCompetence appCom = new EmployeeAppraisalCompetence();
            List<Object> colValue = records.get(a);
            String statement = null;
            String description = null;
            System.out.println(">>>>>>>:" + colValue.size());
            if(a > 0){
                try{
                    for(int i = 0; i < colValue.size(); i++){
                        if((i + 1) == 1){//position 3 -SaffID
                            int sn = (int) Math.round(Double.parseDouble(String.valueOf(colValue.get(i)).trim()));
                            appCom.setSerialNumber(sn);

                        }else if((i + 1) == 3){//position 3 -StaffID
                            statement =  String.valueOf(colValue.get(i)).trim();
                            appCom.setStatement(String.valueOf(statement));
                        }else if((i + 1) == 2){
                            description =  String.valueOf(colValue.get(i)).trim();
                            appCom.setDescription(String.valueOf(description));
                        }
                        appCom.setCompetenceText(criteriaText);
                        appCom.setGroupText(groupText);
                        appCom.setEmployeeName(employeeName);
                    }
                }catch(NumberFormatException er){
                    System.out.println("***Error processing record-:" + colValue);
                    throw new NumberFormatException(colValue.toString());
                }
            userList.add(appCom);
            }//:
        }
        return userList;
    }
    /*------------ getter for all DAOs ---------------------------- */
    /**
     * @return the appraisalCriteriaDAO
     */
    public AppraisalCriteriaDAO getAppraisalCriteriaDAO() {
        if (appraisalCriteriaDAO == null) {
            appraisalCriteriaDAO = new AppraisalCriteriaDAO();
        }
        return appraisalCriteriaDAO;
    }

    /**
     * @return the appraisalGroupDAO
     */
    public AppraisalGroupDAO getAppraisalGroupDAO() {
        if (appraisalGroupDAO == null) {
            appraisalGroupDAO = new AppraisalGroupDAO();
        }
        return appraisalGroupDAO;
    }

    /**
     * @return the appraisalCompetenceDAO
     */
    public AppraisalCompetenceDAO getAppraisalCompetenceDAO() {
        if (appraisalCompetenceDAO == null) {
            appraisalCompetenceDAO = new AppraisalCompetenceDAO();
        }
        return appraisalCompetenceDAO;
    }

    /**
     * @return the appraisalRatingLangaugeDAO
     */
    public AppraisalRatingLangaugeDAO getAppraisalRatingLangaugeDAO() {
        if (appraisalRatingLangaugeDAO == null) {
            appraisalRatingLangaugeDAO = new AppraisalRatingLangaugeDAO();
        }
        return appraisalRatingLangaugeDAO;
    }

    /**
     * @return the appraisalDAO
     */
    public AppraisalDAO getAppraisalDAO() {
        if (appraisalDAO == null) {
            appraisalDAO = new AppraisalDAO();
        }
        return appraisalDAO;
    }

    /**
     * @return the employeeDAO
     */
    public EmployeeDAO getEmployeeDAO() {
        if (employeeDAO == null) {
            employeeDAO = new EmployeeDAO();
        }
        return employeeDAO;
    }
}
