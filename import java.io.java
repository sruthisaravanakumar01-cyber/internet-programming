import java.io.IOException; 

import java.io.PrintWriter; 

import java.sql.Connection; 

import java.sql.DriverManager; 

import java.sql.PreparedStatement; 

import java.sql.ResultSet; 

import java.sql.SQLException; 

 

import javax.servlet.ServletException; 

import javax.servlet.annotation.WebServlet; 

import javax.servlet.http.HttpServlet; 

import javax.servlet.http.HttpServletRequest; 

import javax.servlet.http.HttpServletResponse; 

 

@WebServlet("/BookTicketServlet") 

public class BookTicketServlet extends HttpServlet { 

 

    // Database details 

    private static final String URL = 

            "jdbc:mysql://localhost:3306/moviebooking"; 

 

    private static final String USER = "root"; 

 

    // CHANGE THIS to your MySQL root password 

    private static final String PASSWORD = "test@123"; 

 

    @Override 

    protected void doPost(HttpServletRequest request, 

                          HttpServletResponse response) 

            throws ServletException, IOException { 

 

        response.setContentType("text/html;charset=UTF-8"); 

 

        PrintWriter out = response.getWriter(); 

 

        // Get data from index.html 

        String customerName = 

                request.getParameter("customer_name"); 

 

        String movieName = 

                request.getParameter("movie_name"); 

 

        String showDate = 

                request.getParameter("show_date"); 

 

        String showTime = 

                request.getParameter("show_time"); 

 

        int seats = 

                Integer.parseInt(request.getParameter("seats")); 

 

        double ticketPrice = 

                Double.parseDouble(request.getParameter("ticket_price")); 

 

        // Calculate total amount 

        double totalAmount = seats * ticketPrice; 

 

        Connection con = null; 

        PreparedStatement insertStatement = null; 

        PreparedStatement selectStatement = null; 

        ResultSet rs = null; 

 

        try { 

 

            // Load MySQL JDBC Driver 

            Class.forName("com.mysql.cj.jdbc.Driver"); 

 

            // Connect to MySQL database 

            con = DriverManager.getConnection( 

                    URL, 

                    USER, 

                    PASSWORD 

            ); 

 

             

            String insertSQL = 

                    "INSERT INTO tickets " 

                    + "(customer_name, movie_name, show_date, " 

                    + "show_time, seats, ticket_price, total_amount) " 

                    + "VALUES (?, ?, ?, ?, ?, ?, ?)"; 

 

            insertStatement = 

                    con.prepareStatement(insertSQL); 

 

            insertStatement.setString(1, customerName); 

            insertStatement.setString(2, movieName); 

            insertStatement.setString(3, showDate); 

            insertStatement.setString(4, showTime); 

            insertStatement.setInt(5, seats); 

            insertStatement.setDouble(6, ticketPrice); 

            insertStatement.setDouble(7, totalAmount); 

 

            insertStatement.executeUpdate(); 

 

 

             

 

            String selectSQL = 

                    "SELECT * FROM tickets"; 

 

            selectStatement = 

                    con.prepareStatement(selectSQL); 

 

            rs = selectStatement.executeQuery(); 

 

 

 

            out.println("<!DOCTYPE html>"); 

            out.println("<html>"); 

            out.println("<head>"); 

 

            out.println("<title>Movie Ticket Details</title>"); 

 

            out.println("<style>"); 

 

            out.println("body {"); 

            out.println("font-family: Arial;"); 

            out.println("background-color: #f2f2f2;"); 

            out.println("}"); 

 

            out.println(".container {"); 

            out.println("width: 90%;"); 

            out.println("margin: 40px auto;"); 

            out.println("background-color: white;"); 

            out.println("padding: 20px;"); 

            out.println("box-shadow: 0 0 10px gray;"); 

            out.println("}"); 

 

            out.println("h1 {"); 

            out.println("text-align: center;"); 

            out.println("color: green;"); 

            out.println("}"); 

 

            out.println("table {"); 

            out.println("width: 100%;"); 

            out.println("border-collapse: collapse;"); 

            out.println("}"); 

 

            out.println("th {"); 

            out.println("background-color: #333;"); 

            out.println("color: white;"); 

            out.println("padding: 10px;"); 

            out.println("}"); 

 

            out.println("td {"); 

            out.println("padding: 10px;"); 

            out.println("text-align: center;"); 

            out.println("border: 1px solid #ddd;"); 

            out.println("}"); 

 

            out.println("</style>"); 

 

            out.println("</head>"); 

 

            out.println("<body>"); 

 

            out.println("<div class='container'>"); 

 

            out.println("<h1>Ticket Booking Successful!</h1>"); 

 

            out.println("<h2>All Ticket Booking Details</h2>"); 

 

            out.println("<table>"); 

 

            out.println("<tr>"); 

            out.println("<th>ID</th>"); 

            out.println("<th>Customer Name</th>"); 

            out.println("<th>Movie Name</th>"); 

            out.println("<th>Show Date</th>"); 

            out.println("<th>Show Time</th>"); 

            out.println("<th>Seats</th>"); 

            out.println("<th>Ticket Price</th>"); 

            out.println("<th>Total Amount</th>"); 

            out.println("</tr>"); 

 

 

            // Display database records 

            while (rs.next()) { 

 

                out.println("<tr>"); 

 

                out.println("<td>" 

                        + rs.getInt("id") 

                        + "</td>"); 

 

                out.println("<td>" 

                        + rs.getString("customer_name") 

                        + "</td>"); 

 

                out.println("<td>" 

                        + rs.getString("movie_name") 

                        + "</td>"); 

 

                out.println("<td>" 

                        + rs.getDate("show_date") 

                        + "</td>"); 

 

                out.println("<td>" 

                        + rs.getString("show_time") 

                        + "</td>"); 

 

                out.println("<td>" 

                        + rs.getInt("seats") 

                        + "</td>"); 

 

                out.println("<td>" 

                        + rs.getDouble("ticket_price") 

                        + "</td>"); 

 

                out.println("<td>" 

                        + rs.getDouble("total_amount") 

                        + "</td>"); 

 

                out.println("</tr>"); 

            } 

 

            out.println("</table>"); 

 

            out.println("</div>"); 

 

            out.println("</body>"); 

 

            out.println("</html>"); 

 

        } catch (ClassNotFoundException e) { 

 

            out.println("<h2>JDBC Driver Error</h2>"); 

            out.println("<p>" + e.getMessage() + "</p>"); 

 

        } catch (SQLException e) { 

 

            out.println("<h2>Database Error</h2>"); 

            out.println("<p>" + e.getMessage() + "</p>"); 

 

        } catch (Exception e) { 

 

            out.println("<h2>Error</h2>"); 

            out.println("<p>" + e.getMessage() + "</p>"); 

 

        } finally { 

 

            // Close database resources 

            try { 

                if (rs != null) { 

                    Rs. Close(); 

                } 

 

                if (insertStatement != null) { 

                    insertStatement.close(); 

                } 

 

                if (selectStatement != null) { 

                    selectStatement.close(); 

                } 

 

                if (con != null) { 

                    con.close(); 

                } 

 

            } catch (SQLException e) { 

                e.printStackTrace(); 

            } 

        } 

    } 

} 

 

 

 

 

 

 