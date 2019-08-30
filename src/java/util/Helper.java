/* 
 * The MIT License
 *
 * Copyright 2019 crumbl3d.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * Helper functions used at random places...
 * @author crumbl3d
 */
public class Helper {
    
    public static final String SEVERITY_INFO = "INFO";
    public static final String SEVERITY_WARN = "WARN";
    public static final String SEVERITY_ERROR = "ERROR";
    public static final String SEVERITY_FATAL = "FATAL";
    
    
    public static void showMessage(String severity, String summary, String detail) {
        FacesMessage.Severity messageSeverity = FacesMessage.SEVERITY_INFO;
        if (severity != null) {
            if (SEVERITY_FATAL.equalsIgnoreCase(severity)) {
                messageSeverity = FacesMessage.SEVERITY_FATAL;
            } else if (SEVERITY_ERROR.equalsIgnoreCase(severity)) {
                messageSeverity = FacesMessage.SEVERITY_ERROR;
            } else if (SEVERITY_WARN.equalsIgnoreCase(severity)) {
                messageSeverity = FacesMessage.SEVERITY_WARN;
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(messageSeverity, summary, detail));
    }

    public static void showFatal(String summary, String detail) {
        showMessage(SEVERITY_FATAL, summary, detail);
    }
    
    public static void showError(String summary, String detail) {
        showMessage(SEVERITY_ERROR, summary, detail);
    }
    
    public static void showWarning(String summary, String detail) {
        showMessage(SEVERITY_WARN, summary, detail);
    }
        
    public static void showInfo(String summary, String detail) {
        showMessage(SEVERITY_INFO, summary, detail);
    }
    
    public static boolean checkValid(Object object, String message) {
        if (object == null || (object instanceof String && ((String) object).isEmpty())) {
            if (message != null && !message.isEmpty()) {
                showError(null, message);
            }
            return false;
        }
        return true;
    }
    
    public static boolean checkValid(Object object) {
        return checkValid(object, null);
    }

    public static Object getObject(String name) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        return facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, name);
    }
}