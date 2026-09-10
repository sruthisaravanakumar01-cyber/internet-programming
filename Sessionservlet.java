import java.io.IOException; 

import java.io.PrintWriter; 

import javax.servlet.ServletContext; 

import javax.servlet.ServletException; 

import javax.servlet.annotation.WebServlet; 

import javax.servlet.http.HttpServlet; 

import javax.servlet.http.HttpServletRequest; 

import javax.servlet.http.HttpServletResponse; 

 

@WebServlet("/SessionServlet") 

public class SessionServlet extends HttpServlet { 

 

    @Override 

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 

            throws ServletException, IOException { 

 

        response.setContentType("text/html"); 

        try (PrintWriter out = response.getWriter()) { 

            String name = request.getParameter("name"); 

            String course = request.getParameter("course"); 

             

            ServletContext context = getServletContext(); 

             

            Integer count = (Integer) context.getAttribute("visitorCount"); 

             

            if (count == null) { 

                count = 0; 

            } 

           

            count++; 

            context.setAttribute("visitorCount", count); 

             

            out.println("<html>"); 

            out.println("<head>"); 

            out.println("<title>Session Servlet</title>"); 

            out.println("</head>"); 

            out.println("<body>"); 

            

            out.println("<h2>Session Tracking</h2>"); 

             

            out.println("<p>Welcome " + name + "</p>"); 

            out.println("<p>Course: " + course + "</p>"); 

            

            out.println("<a href='VisitorServlet?name=" + name 

                    + "&course=" + course + "'>"); 

          

            out.println("<h3>Click here to go to the next page</h3>");

                       

            out.println("</a>"); 

          

            out.println("</body>"); 

            out.println("</html>"); 

        } 

    } 

} 