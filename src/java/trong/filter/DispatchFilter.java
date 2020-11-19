/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trong.filter;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author DELL
 */
public class DispatchFilter implements Filter {
    
    private static final boolean debug = true;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;
    
    private final String LOGIN_PAGE = "login.html";
    
    public DispatchFilter() {
    }    
    
    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("DispatchFilter:DoBeforeProcessing");
        }
    }    
    
    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("DispatchFilter:DoAfterProcessing");
        }
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        
        String url = LOGIN_PAGE;
       
        
        try {
            int lastIndex = uri.lastIndexOf("/");
            String resource = uri.substring(lastIndex + 1);
            
            boolean notGuest = false;
            boolean isAdmin = false;
            
            HttpSession session = req.getSession(false);
            if(session != null) {
                if(session.getAttribute("FULLNAME") != null) {
                    notGuest = true;
                    if(session.getAttribute("ADMIN") != null) {
                        isAdmin = true;
                    }
                }
            }
            
            if(resource.length() > 0) {
                url = resource.substring(0, 1).toUpperCase() 
                        + resource.substring(1) + "Servlet";
                
                boolean haveParam = false;
                
                Enumeration param = request.getParameterNames();
                while(param.hasMoreElements()) {
                    param.nextElement();
                    haveParam = true;
                }
                
                
                if(!haveParam && !resource.equals("signOut") && !resource.equals("viewCart")) {
                    System.out.println("Here");
                    if(notGuest) {
                        resource = "shoppingPage";
                        if(isAdmin) {
                            resource = "searchPage";
                        }
                    } else {
                        resource = "loginPage";
                    }
                }
                
                if(resource.contains("Page")) {
                   int indexPage = resource.indexOf("Page");
                   resource = resource.substring(0, indexPage);
                   if(resource.equals("login") || resource.equals("invalid")
                           || resource.contains("create")) {
                       if(notGuest) {
                           resource = "shopping";
                           if(isAdmin) {
                               resource = "search";
                           }
                       }
                       url = resource + ".html";
                       if(notGuest || resource.contains("Fail")) {
                           url = resource + ".jsp";
                       }
                   } else {
                       url = resource + ".jsp";
                   }
                }
            } else {
                url = "StartUpServlet"; 
            }
            
            if(url != null) {
                RequestDispatcher rd = req.getRequestDispatcher(url);
                rd.forward(request, response);
            } else if (session != null) {
                res.sendRedirect(req.getContextPath() + "/" + url);
            } else {
                chain.doFilter(request, response);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {        
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {        
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {                
                log("DispatchFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("DispatchFilter()");
        }
        StringBuffer sb = new StringBuffer("DispatchFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }
    
    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);        
        
        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);                
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");                
                pw.print(stackTrace);                
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }
    
    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }
    
    public void log(String msg) {
        filterConfig.getServletContext().log(msg);        
    }
    
}
