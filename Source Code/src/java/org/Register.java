package org;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "Register", urlPatterns = {"/register"})
public class Register extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rst = null;
    private int i = 0;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userId = request.getParameter("reg_id");
        HttpSession session = request.getSession(true);

        try {
            con = connection.dbConnection.makeConnection();
            String query = "DELETE FROM users WHERE userid=?";
            pst = con.prepareStatement(query);
            pst.setString(1, userId);
            i = pst.executeUpdate();

            if (i > 0) {
                session.setAttribute("MSG", "User has been successfully deleted !!");
            } else {
                session.setAttribute("MSG", "User has not been deleted !!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        response.sendRedirect("userlist.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(true);

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String mobile = request.getParameter("mobile");
        String dob = request.getParameter("dob");
        String gender = request.getParameter("gender");

        try {
            con = connection.dbConnection.makeConnection();
            if (con != null) {
                String userid = UUID.randomUUID().toString(); // Generate unique user ID
                String sqlquery = "INSERT INTO users(userid,name,email,mobile,dob,gender,created) VALUES(?,?,?,?,?,?, NOW())";
                pst = con.prepareStatement(sqlquery);
                pst.setString(1, userid);
                pst.setString(2, name);
                pst.setString(3, email);
                pst.setString(4, mobile);
                pst.setString(5, dob);
                pst.setString(6, gender);
                i = pst.executeUpdate();

                if (i > 0) {
                    session.setAttribute("MSG", "User has been successfully registered !!");
                    response.sendRedirect("keyexchange.jsp");
                } else {
                    session.setAttribute("MSG", "Your data has not been registered.");
                    response.sendRedirect("register.jsp");
                }
            } else {
                session.setAttribute("MSG", "Database connection failed. Your data has not been registered.");
                response.sendRedirect("register.jsp");
            }
        } catch (SQLException e) {
    e.printStackTrace(); // Print stack trace for debugging
    session.setAttribute("MSG", "Database error occurred. Your data has not been registered.");
    response.sendRedirect("register.jsp");
} finally {
            try {
                if (pst != null) {
                    pst.close();
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
