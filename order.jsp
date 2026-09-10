<%@page contentType="text/html" pageEncoding="UTF-8"%> 

<%@page import="java.sql.*"%> 

<!DOCTYPE html> 

<html> 

<head> 

    <meta http-equiv="Content-Type" 

          content="text/html; charset=UTF-8"> 

    <title>Order Status & List</title> 

</head> 

<body> 

<% 

    String cName = request.getParameter("name"); 

    String pName = request.getParameter("productname"); 

    String qtyStr = request.getParameter("quantity"); 

    String priceStr = request.getParameter("price"); 

    Connection con = null; 

    PreparedStatement ps = null; 

    Statement stmt = null; 

    ResultSet rs = null; 

    try { 

        // Load MySQL Driver 

        Class.forName("com.mysql.cj.jdbc.Driver"); 

        // Connect to MySQL 

        con = DriverManager.getConnection( 

            "jdbc:mysql://localhost:3306/OrderDB?useSSL=false&allowPublicKeyRetrieval=true", 

            "root", 

            "test@123" 

        ); 

        // Insert order 

        if (cName != null && pName != null && 

            qtyStr != null && priceStr != null) { 

            ps = con.prepareStatement( 

                "INSERT INTO OrderDetails " + 

                "(customer_name, product_name, quantity, total_price) " + 

                "VALUES (?, ?, ?, ?)" 

            ); 

            ps.setString(1, cName); 

            ps.setString(2, pName); 

            ps.setInt(3, Integer.parseInt(qtyStr)); 

            ps.setDouble(4, Double.parseDouble(priceStr)); 

            int i = ps.executeUpdate(); 

            if (i > 0) { 

                out.println( 

                    "<h3 style='color:green;'>Order Placed Successfully!</h3>" 

                ); 

            } 

            ps.close(); 

        } 

%> 

<hr> 

<h2>All Placed Orders</h2> 

<table border="1" cellpadding="5" cellspacing="0"> 

 

<tr> 

    <th>Customer Name</th> 

    <th>Product Name</th> 

    <th>Quantity</th> 

    <th>Price</th> 

</tr> 

<% 

        stmt = con.createStatement(); 

        rs = stmt.executeQuery( 

            "SELECT * FROM OrderDetails" 

        ); 

        while (rs.next()) { 

%> 

<tr> 

    <td> 

        <%=rs.getString("customer_name")%> 

    </td> 

    <td> 

        <%=rs.getString("product_name")%> 

    </td> 

    <td> 

        <%=rs.getInt("quantity")%> 

    </td> 

    <td> 

        Rs <%=rs.getDouble("total_price")%> 

    </td> 

 

</tr> 

<% 

        } 

    } catch (Exception e) { 

        out.println( 

            "<h3 style='color:red;'>Error: " 

            + e.getMessage() 

            + "</h3>" 

        ); 

    } finally { 

        try { 

            if (rs != null) rs.close(); 

        } catch (Exception e) {} 

        try { 

            if (stmt != null) stmt.close(); 

        } catch (Exception e) {} 

        try { 

            if (con != null) con.close(); 

        } catch (Exception e) {} 

    } 

%> 

</table> 

<br> 

<a href="index.html">Place Another Order</a> 

</body> 

</html> 