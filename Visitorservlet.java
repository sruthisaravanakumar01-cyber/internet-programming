import java.io.IOException; 

import java.io.PrintWriter; 

import javax.servlet.ServletContext; 

import javax.servlet.ServletException; 

import javax.servlet.annotation.WebServlet; 

import javax.servlet.http.HttpServlet; 

import javax.servlet.http.HttpServletRequest; 

import javax.servlet.http.HttpServletResponse; 

@WebServlet("/VisitorServlet") 

public class VisitorServlet extends HttpServlet { 

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 

            throws ServletException, IOException { 

        response.setContentType("text/html"); 

        PrintWriter out = response.getWriter(); 

        String name = request.getParameter("name"); 

        String course = request.getParameter("course"); 

        ServletContext context = getServletContext(); 

        Integer count = (Integer) context.getAttribute("visitorCount"); 

        if (count == null) { 

            count = 0; 

        } 

        out.println("<html>"); 

        out.println("<head>"); 

        out.println("<title>Visitor Details</title>"); 

        out.println("</head>"); 

        out.println("<body>"); 

        out.println("<h2>Visitor Details</h2>"); 

        out.println("<p>Name: " + name + "</p>"); 

        out.println("<p>Course: " + course + "</p>"); 

        out.println("<h3>Number of Visitors: " + count + "</h3>"); 

  

        out.println("</body>"); 

        out.println("</html>"); 

        out.close(); 

    } 

} 