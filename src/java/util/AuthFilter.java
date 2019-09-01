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

import controllers.GameController;
import entities.Korisnik;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(filterName = "AuthFilter", urlPatterns = { "*.xhtml" })
public class AuthFilter implements Filter {

    public AuthFilter() {}

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest reqt = (HttpServletRequest) request;
            HttpServletResponse resp = (HttpServletResponse) response;
            HttpSession ses = reqt.getSession(false);
            String reqURI = reqt.getRequestURI();
            Korisnik k = null;
            if (ses != null && ses.getAttribute("user") != null) {
                k = (Korisnik) ses.getAttribute("user");
            }
            String currentGame = null;
            if (ses != null && ses.getAttribute("currentGame") != null) {
                currentGame = (String) ses.getAttribute("currentGame");
            }
            if (reqURI.contains("/index.xhtml")
                    || reqURI.contains("javax.faces.resource")
                    || reqURI.contains("/public/")
                    || reqURI.contains("/users/") && k != null && reqURI.contains(k.getVrsta())
                    || reqURI.contains("/games/") && k != null && k.getVrsta().equals("takmicar") && currentGame != null) {
                chain.doFilter(request, response);
            } else if (k != null) {
                resp.sendRedirect(reqt.getContextPath() + "/faces/users/" + k.getVrsta() + ".xhtml");
            } else {
                resp.sendRedirect(reqt.getContextPath() + "/faces/index.xhtml");
            }
        } catch (IOException | ServletException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void destroy() {}
}