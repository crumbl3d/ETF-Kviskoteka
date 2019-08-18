/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import entities.Korisnik;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Session Utility class with a convenient method to get Session object,
 * current user and etc.
 *
 * @author crumbl3d
 */
public class SessionUtil {

    public static HttpSession getSession() {
        return (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
    }

    public static HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance()
                .getExternalContext().getRequest();
    }

    public static Korisnik getCurrentUser() {
        HttpSession session = getSession();
        if (session != null) {
            return (Korisnik) session.getAttribute("user");
        }
        return null;
    }

    public static boolean setCurrentUser(Korisnik k) {
        HttpSession session = getSession();
        if (session != null) {
            if (k == null) {
                session.removeAttribute("user");
            } else {
                session.setAttribute("user", k);
            }
            return true;
        }
        return false;
    }
}