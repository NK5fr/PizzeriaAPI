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
        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
        
        String nom = Escape.escape(req.getParameter("nom"));
        String mdp = req.getParameter("mdp");
        System.out.println(nom + " " + mdp);

        PreparedStatement ps = null;
        try (Connection con = DS.getConnection()) {
            ps = con.prepareStatement("select * from clients where cnom = ? and mdp = md5(?)");
            ps.setString(1, nom);
            ps.setString(2, mdp);
            ResultSet rs = ps.executeQuery();
            System.out.println(nom + " " + mdp);
            if(rs.next()){
                String token = JwtManager.createJWT();
                out.print(token);
                res.addHeader("Token", token);
            }else{
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } catch (Exception e) {
            System.out.println(ps);
            System.out.println(e.getMessage());
        }

        
    }

    
}
