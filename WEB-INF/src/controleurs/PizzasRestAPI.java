package controleurs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;


import dao.DAOPizza;
import dao.PizzaDAODatabase;
import dto.IngredientId;
import dto.PizzaGet;
import dto.PizzaPost;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.TokenValidation;

@WebServlet("/pizzas/*")
public class PizzasRestAPI extends restAPI{

    private DAOPizza dao = new PizzaDAODatabase();

    @Override
    public void doPatch(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        String authorization = req.getHeader("Authorization");

        if(!TokenValidation.verifToken(authorization)){
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

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

        PizzaPost p = objectMapper.readValue(data.toString(), PizzaPost.class);

        PizzaGet updated = dao.update(id, p);

        if(updated == null){
            res.sendError(HttpServletResponse.SC_CONFLICT);
            return;
        }
        
        String jsonstring = objectMapper.writeValueAsString(updated);
        out.print(jsonstring);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        
        String authorization = req.getHeader("Authorization");

        if(!TokenValidation.verifToken(authorization)){
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

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
            int pno = Integer.valueOf(splits[1]);
            int ino = Integer.valueOf(splits[2]);
            if(!dao.deleteIngredient(pno, ino)){
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
            List<PizzaGet> l = dao.findAll();
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
        PizzaGet p = dao.findById(id);
        if (p == null) {
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        if(splits.length == 3 && splits[2].equals("prixfinal")){
            out.print("{\"prixfinal\":" + p.prixFinal() + "}");
            return;
        }else if(splits.length == 3){
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        out.print(objectMapper.writeValueAsString(p));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        String authorization = req.getHeader("Authorization");

        if(!TokenValidation.verifToken(authorization)){
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

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
            PizzaPost p = objectMapper.readValue(data.toString(), PizzaPost.class);

            PizzaGet result = dao.save(p);
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

            if(dao.findById(id) == null){
                res.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            IngredientId ii = objectMapper.readValue(data.toString(), IngredientId.class);

            PizzaGet updated = dao.saveIngredient(id, ii);

            if(updated == null){
                res.sendError(HttpServletResponse.SC_CONFLICT);
                return;
            }

            String jsonstring = objectMapper.writeValueAsString(updated);
            out.print(jsonstring);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        String authorization = req.getHeader("Authorization");

        if(!TokenValidation.verifToken(authorization)){
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

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
        
        PizzaPost p = objectMapper.readValue(data.toString(), PizzaPost.class);

        PizzaGet updated = dao.strictUpdate(id, p);

        if(updated == null){
            res.sendError(HttpServletResponse.SC_CONFLICT);
            return;
        }
        
        String jsonstring = objectMapper.writeValueAsString(updated);
        out.print(jsonstring);
    }
    
    
}
