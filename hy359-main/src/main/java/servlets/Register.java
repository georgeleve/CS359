/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servlets;

import database.tables.EditSimpleUserTable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mainClasses.JSONConverter;
import mainClasses.User;

/**
 *
 * @author leven
 */
public class Register extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Register</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Register at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        processRequest(request, response);
    }

    public String getJSONFromAjax(BufferedReader reader) throws IOException {
        StringBuilder buffer = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        String data = buffer.toString();
        return data;
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String newUser = this.getJSONFromAjax(request.getReader());
        System.out.println(newUser);  // Print the request you get from the client
        
        
        System.out.println("lalalalalalalaaalalalalalalalaal");
        
        EditSimpleUserTable userTable = new EditSimpleUserTable();
      //  EditDoctorTable doctorTable = new EditDoctorTable(); 

        try {
            userTable.addSimpleUserFromJSON(newUser);
            

            // Metatroph JSON se User kai vazoume extra metablhtes
            /*JSONConverter jc = new JSONConverter();
            User p = jc.jsonToUser(request.getReader());
            p.setValues();
            String JsonString = jc.userToJSON(p);
            
            // Ti theloume na epistrecoyme san format
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            
            // Apagoreyoume na syndethei kapoios xrhsths an yparxei to username
            if (Resources.registeredUsers.containsKey(p.getUsername())) {
            response.setStatus(409);
            Gson gson = new Gson();
            JsonObject jo = new JsonObject();
            jo.addProperty("error", "Username Already Taken");
            response.getWriter().write(jo.toString());
            
            // Allios metatrepoume to JSON se User kai to epistrefoume gia na poume oti eixe epityxia
            } else {
            Resources.registeredUsers.put(p.getUsername(), p);
            response.setStatus(200);
            response.getWriter().write(JsonString);
            }
             */
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
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