/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tenece.hr.controller.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tenece.web.data.AppraisalGroup;
import org.tenece.web.data.Employee;
import org.tenece.web.services.AppraisalService;
import org.tenece.web.services.EmployeeService;

/**
 *
 * @author kenneth
 */
@Controller
public class Jsoncontroller {

    private AppraisalService appraisalService = new AppraisalService();
    private EmployeeService employeeService = new EmployeeService();
    
    @RequestMapping(value = "/getCriteriaGroup.hd", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public @ResponseBody
    List<AppraisalGroup> doGetCriteriaGroup(HttpSession session, @RequestParam String id, HttpServletRequest request, HttpServletResponse response) {
         System.out.println("here===="+id);
        // return new ResponseEntity<AppraisalGroup>();
        List<AppraisalGroup> grouplist = appraisalService.findAllAppraisalGroupByCriteria(Integer.parseInt(id));
            System.out.println("grouplist===="+grouplist.size());
        String json = null;

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json; charset=utf-8");
        return  grouplist;
    }
      @RequestMapping(value = "/getGroup.hd", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public @ResponseBody
    List<Employee> doGroup(HttpSession session, @RequestParam String id, HttpServletRequest request, HttpServletResponse response) {
         System.out.println("here===="+id);
        // return new ResponseEntity<AppraisalGroup>();
        AppraisalGroup group = appraisalService.findAllAppraisalGroupByID(Integer.parseInt(id));
        List<Employee> emplist = new ArrayList<Employee>();
        if(group.getForJobSpecific()>0)
        {
           emplist = employeeService.findAllEmployeeForBasic();
        }
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json; charset=utf-8");
        return  emplist;
    }
    
}
