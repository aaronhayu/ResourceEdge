
/* * (c) Copyright 2008 Tenece Professional Services.
 *
 * ============================================================
 * Project Info:  http://www.tenece.com
 * Project Lead:  Kenneth Onyema (info@tenece.com);
 * ============================================================
 *
 *
 * Licensed under the Tenece Professional Services;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.tenece.com
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
package org.tenece.hr.taghandler;

import com.google.common.collect.Multimap;
import java.util.Collections;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.tenece.web.data.Employee;
import org.tenece.web.data.MenuData;
import org.tenece.web.data.MenuSubMenuInfo;
import org.tenece.web.data.Users;
import org.tenece.web.services.EmployeeService;
import org.tenece.web.services.UserService;

/**
 *
 * @author kenneth Onyema
 */
public class MenuTagHandler extends TagSupport {

    private UserService userService = new UserService();
    private EmployeeService employeeService = new EmployeeService();
    String typeOfMode = "";

    public void setTypeOfMode(String name) {
        this.typeOfMode = typeOfMode;
    }

    public String getTypeOfMode() {
        return typeOfMode;
    }

    /**
     * @see javax.servlet.jsp.tagext.Tag#doStartTag()
     */
    @Override
    public final int doStartTag() throws JspException {
        try {
            return EVAL_BODY_INCLUDE;
        } catch (IllegalArgumentException e) {
            throw new JspException(e.getMessage());
        }
    }

    /**
     * @see javax.servlet.jsp.tagext.Tag#doEndTag()
     */
    @Override
    public final int doEndTag() throws JspException {
        buildMenu();
        return EVAL_PAGE;
    }

    /**
     * This Method is responsible for building User Menu based on the Selected
     * Role on a Business Unit
     */
    private void buildMenu() {
        try {


            HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();

            Users user = this.getUserPrincipal(request);
            //get session
            HttpSession session = request.getSession();
            String viewAdminMenu = (String) session.getAttribute("menuMode");
            System.out.println("viewAdminMenu===="+viewAdminMenu);
            if (viewAdminMenu.equals(null)) {
                viewAdminMenu = "E";
            }
            String menuToShow = "";
            int userRole = 0; //default .. self service
            int isSuperAdmin = 0; //default... not a super admin
            try {

                userRole = user.getAdminUser();
                isSuperAdmin = user.getSuperAdmin();
            } catch (Exception e) {
            }
            //if role is ESS (0) then show only ESS - (No Option to change menu)
            if (userRole == 0 && isSuperAdmin == 0) {
                menuToShow = "ESS";
            } else if (userRole == 1 && isSuperAdmin == 1) {
                menuToShow = "ADMIN_GRP";
            } else if (viewAdminMenu.trim().equals("A") && isSuperAdmin == 1 && userRole == 0) {
                menuToShow = "ADMIN";
            } else if (viewAdminMenu.trim().equals("E") && isSuperAdmin == 1 && userRole == 0) {
                menuToShow = "ESS";
            } else {
                menuToShow = "ESS";
            }
            StringBuilder tempBuilder = new StringBuilder();
            tempBuilder.append("\n").append("<hr width=\"").append("100%").append("\"").append(" noshade=\"").append("noshade").append("\">");
            tempBuilder.append("\n").append("<div class=\"").append("glossymenu").append("\"").append(" style=\"").append("width:100%").append("\">");

            System.out.println("menuToShow ===" + menuToShow);
            MenuSubMenuInfo menuSubmenuInfo = getUserService().getMenu(menuToShow);
            List<MenuData> menuList = menuSubmenuInfo.getMenuList();
            Multimap<String, MenuData> subMenuMap = menuSubmenuInfo.getSubMenuMap();

            for (MenuData menu : menuList) {

                if (menu.getDivClass().trim().equals("menuitem")) {
                    tempBuilder.append("<a class=\"").append(menu.getDivClass()).append("\"").append(" target=\"").append(menu.getTarget()).append("\"").append(" href=\"").append(menu.getURL()).append("\">").append(menu.getMenuTitle()).append("</a>");

                } else if (menu.getDivClass().trim().equals("submenuheader")) {
                    tempBuilder.append("<a class=\"").append("menuitem ").append(menu.getDivClass()).append("\"").append("\n").append("href=\"#").append("\">").append(menu.getMenuTitle()).append("</a>");
                    tempBuilder.append("\n").append("<div class=\"").append("submenu").append("\">");
                    tempBuilder.append("\n\t").append("<ul>");
                    // get the submenu 
                    List<MenuData> subMenuList = (List<MenuData>) subMenuMap.get(menu.getMenuTitle());
                    Collections.sort(subMenuList);
                    for (MenuData submenu : subMenuList) {

                        tempBuilder.append("\n").append("<li> <a target=\"").append(submenu.getTarget()).append("\"").append("href=\"").append(submenu.getURL()).append("\">").append(submenu.getSubmenutitle()).append("</a></li>");

                    }
                    tempBuilder.append("\n\t").append("</ul>");
                    tempBuilder.append("\n").append("</div>");
                }
            }

            //  }
            tempBuilder.append("\n").append("</div>");
            tempBuilder.append("\n").append("<br/>");
            // tempBuilder.append("\n").append("<p>Last Login Date:<br/>").append(lastLoginDate).append("</p>");
            tempBuilder.append("\n").append("<br/>");
            pageContext.getOut().write(tempBuilder.toString());
            pageContext.getOut().flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Users getUserPrincipal(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Users userPrincipal = null;
        try {
            userPrincipal = (Users) session.getAttribute("userPrincipal");
        } catch (Exception e) {
            return null;
        }
        return userPrincipal;
    }

    /**
     * @return the userService
     */
    private UserService getUserService() {
        return userService;
    }

    /**
     * @param userService the userService to set
     */
    private void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * @return the userService
     */
    private EmployeeService getEmployeeService() {
        return employeeService;
    }

    /**
     * @param employeeService the employeeService to set
     */
    private void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
}
