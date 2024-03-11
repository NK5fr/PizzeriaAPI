package controleurs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.CommandeDAODatabase;
import dao.DAOCommande;
import dto.CommandeGet;
import dto.CommandePost;
import dto.IngredientId;
import dto.PizzaGet;
import dto.PizzaId;
import dto.PizzaPost;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/commandes/*")
public class CommandesRestAPI extends restAPI{

    private DAOCommande dao = new CommandeDAODatabase();

    @Override
    public void doPatch(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();

        StringBuilder data = new StringBuilder();
        BufferedReader reader = req.getReader();

        String line;
        while ((line = reader.readLine()) != null) {
            data.append(line);
        }
        
        
        String info = req.getPathInfo();

        if (info == null || info.equals("/")) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String[] splits = info.split("/");

        if (splits.length != 2) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        int id = Integer.valueOf(splits[1]);

        if(dao.findById(id) == null){
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        
        CommandePost c = objectMapper.readValue(data.toString(), CommandePost.class);

        CommandeGet updated = dao.update(id, c);

        if(updated == null){
            res.sendError(HttpServletResponse.SC_CONFLICT);
            return;
        }
        
        String jsonstring = objectMapper.writeValueAsString(updated);
        out.print(jsonstring);
        
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        String info = req.getPathInfo();

        if (info == null || info.equals("/")) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        
        String[] splits = info.split("/");

        if (splits.length != 2 && splits.length != 3) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        if(splits.length == 2){
            int id = Integer.valueOf(splits[1]);
            if(!dao.delete(id)){
                res.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }else{
            int cno = Integer.valueOf(splits[1]);
            int pno = Integer.valueOf(splits[2]);
            if(!dao.deletePizza(cno, pno)){
                res.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();


        String info = req.getPathInfo();
        if (info == null || info.equals("/")) {
            List<CommandeGet> l = dao.findAll();
            String jsonstring = objectMapper.writeValueAsString(l);
            out.print(jsonstring);
            return;
        }

        String[] splits = info.split("/");

        if (splits.length != 2 && splits.length != 3) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        int id = Integer.parseInt(splits[1]);
        CommandeGet c = dao.findById(id);
        if (c == null) {
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        if(splits.length == 3 && splits[2].equals("prixfinal")){
            out.print("{\"prixFinal\":" + c.prixFinal() + "}");
            return;
        }else if(splits.length == 3){
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        out.print(objectMapper.writeValueAsString(c));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();

        StringBuilder data = new StringBuilder();
        BufferedReader reader = req.getReader();

        String line;
        while ((line = reader.readLine()) != null) {
            data.append(line);
        }
        
        
        String info = req.getPathInfo();

        if (info == null || info.equals("/")) {
            CommandePost cp = objectMapper.readValue(data.toString(), CommandePost.class);

            CommandeGet result = dao.save(cp);
            if(result == null){
                res.sendError(HttpServletResponse.SC_CONFLICT);
                return;
            }
            String jsonstring = objectMapper.writeValueAsString(result);
            out.print(jsonstring);
        }else{
            String[] splits = info.split("/");
            if (splits.length != 2) {
                res.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            int id = Integer.valueOf(splits[1]);
            PizzaId pi = objectMapper.readValue(data.toString(), PizzaId.class);

            CommandeGet updated = dao.savePizza(id, pi);

            if(updated == null){
                res.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            String jsonstring = objectMapper.writeValueAsString(updated);
            out.print(jsonstring);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();

        StringBuilder data = new StringBuilder();
        BufferedReader reader = req.getReader();

        String line;
        while ((line = reader.readLine()) != null) {
            data.append(line);
        }
        
        
        String info = req.getPathInfo();

        if (info == null || info.equals("/")) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String[] splits = info.split("/");

        if (splits.length != 2) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        int id = Integer.valueOf(splits[1]);

        if(dao.findById(id) == null){
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        
        CommandePost c = objectMapper.readValue(data.toString(), CommandePost.class);

        CommandeGet updated = dao.strictUpdate(id, c);

        if(updated == null){
            res.sendError(HttpServletResponse.SC_CONFLICT);
            return;
        }
        
        String jsonstring = objectMapper.writeValueAsString(updated);
        out.print(jsonstring);
    }
    
    
}
