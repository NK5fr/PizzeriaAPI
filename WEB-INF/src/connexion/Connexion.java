package connexion;

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

@WebServlet("/connexion")
public class Connexion extends HttpServlet{

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html;charset=UTF-8");
        PrintWriter out = res.getWriter();
        
        String nom = Escape.escape(req.getParameter("nom"));
        String mdp = req.getParameter("mdp");

        boolean ok = false;

        PreparedStatement ps = null;
        try(Connection con = DS.getConnection()){
            ps = con.prepareStatement("select * from clients where nom = ? and mdp = md5(?)");
            ps.setString(1, nom);
            ps.setString(2, mdp);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                HttpSession session = req.getSession(true);
                session.setAttribute("nom", nom);
                ok = true;
            }
        }catch(Exception e){
            System.out.println(ps);
            System.out.println(e.getMessage());
        }

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Connexion</title><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
        out.println("<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH\" crossorigin=\"anonymous\">");
        out.println("</head>");
        out.println("<body>");
        if(ok){
            out.println("<div class=\"alert alert-primary w-50 mx-auto my-5 text-center\" role=\"alert\">");
            out.println("Connexion valide");
        }else{
            out.println("<div class=\"alert alert-warning w-50 mx-auto my-5 text-center\" role=\"alert\">");
            out.println("Erreur de connexion");
        }
        out.println("<p></p>");
        out.println("<a href='login.html'>retour</a>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
    
    
}
