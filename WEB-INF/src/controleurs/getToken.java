package controleurs;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import util.DS;
import util.Escape;
import util.JwtManager;

@WebServlet("/users/token")
public class getToken extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html;charset=UTF-8");
        PrintWriter out = res.getWriter();
        
        HttpSession session = req.getSession(true);

        String nom = (String) session.getAttribute("nom");

        if(nom == null){
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        out.println(JwtManager.createJWT());

        
    }

    
}
