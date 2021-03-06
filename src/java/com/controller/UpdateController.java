package com.controller;

import com.login.LoginServlet;
import com.model.UserBean;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UpdateController extends HttpServlet {

    Connection con;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            Class.forName(config.getInitParameter("driver"));
            String username = config.getInitParameter("dbuser");
            String password = config.getInitParameter("password");
            StringBuffer url = new StringBuffer(config.getInitParameter("url"));
            con = DriverManager.getConnection(url.toString(), username, password);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String song = request.getParameter("songtitle");
        String ttl = request.getParameter("newtitle");
        String gnr = request.getParameter("newgenre");
        String yr = request.getParameter("newyear");
        ArrayList<String> title = new ArrayList<String>();
        ArrayList<String> genre = new ArrayList<String>();
        ArrayList<String> year = new ArrayList<String>();
        HttpSession session = request.getSession();
        String qry;
        String result;

        UserBean bean = new UserBean();

        if (bean.checkTtl(con, song.toUpperCase()) == false) {
            request.setAttribute("error", "Song does not exist in your playlist");
            RequestDispatcher rd = request.getRequestDispatcher("error.jsp");
            rd.include(request, response);
        } else {
            if (ttl != null) {
                qry = "UPDATE SONGS SET TITLE = ? WHERE UPPER(TITLE) = ?";
                bean.update(con, ttl, song.toUpperCase(), qry);
                result = (String) session.getAttribute("role");
                ResultSet res = bean.select(con);
                while (res.next()) {
                    title.add(res.getString("title"));
                    genre.add(res.getString("genre"));
                    year.add(res.getString("released"));
                }
                request.setAttribute("title", title);
                request.setAttribute("genre", genre);
                request.setAttribute("year", year);
                request.setAttribute("role", result);
                RequestDispatcher rd = request.getRequestDispatcher("welcome.jsp");
                rd.forward(request, response);
            } else if (gnr != null) {
                qry = "UPDATE SONGS SET GENRE = ? WHERE UPPER(TITLE) = ?";
                bean.update(con, gnr, song.toUpperCase(), qry);
                result = (String) session.getAttribute("role");
                ResultSet res = bean.select(con);
                while (res.next()) {
                    title.add(res.getString("title"));
                    genre.add(res.getString("genre"));
                    year.add(res.getString("released"));
                }
                request.setAttribute("title", title);
                request.setAttribute("genre", genre);
                request.setAttribute("year", year);
                request.setAttribute("role", result);
                RequestDispatcher rd = request.getRequestDispatcher("welcome.jsp");
                rd.forward(request, response);
            } else if (yr != null) {
                qry = "UPDATE SONGS SET RELEASED = ? WHERE UPPER(TITLE) = ?";
                bean.update(con, yr, song.toUpperCase(), qry);
                result = (String) session.getAttribute("role");
                ResultSet res = bean.select(con);
                while (res.next()) {
                    title.add(res.getString("title"));
                    genre.add(res.getString("genre"));
                    year.add(res.getString("released"));
                }
                request.setAttribute("title", title);
                request.setAttribute("genre", genre);
                request.setAttribute("year", year);
                request.setAttribute("role", result);
                RequestDispatcher rd = request.getRequestDispatcher("welcome.jsp");
                rd.forward(request, response);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(UpdateController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(UpdateController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
