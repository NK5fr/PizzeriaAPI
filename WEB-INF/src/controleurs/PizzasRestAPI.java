package controleurs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;


import dao.DAOPizza;
import dao.PizzaDAODatabase;
import dto.IngredientId;
import dto.PizzaFinalPrice;
import dto.PizzaGet;
import dto.PizzaId;
import dto.PizzaPost;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/pizzas/*")
public class PizzasRestAPI extends restAPI{

    private DAOPizza dao = new PizzaDAODatabase();

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
        PizzaPost p = objectMapper.readValue(data.toString(), PizzaPost.class);

        if(!dao.update(id, p)){
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        
        PizzaGet updated = dao.findById(id);
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
            PizzaFinalPrice in = new PizzaFinalPrice(p.prixFinal());
            out.print(objectMapper.writeValueAsString(in));
            return;
        }else if(splits.length == 3){
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        out.print(objectMapper.writeValueAsString(p));
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
            PizzaPost p = objectMapper.readValue(data.toString(), PizzaPost.class);
            if(!dao.save(p)){
                res.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            PizzaGet last = dao.findByName(p.getNom());
            String jsonstring = objectMapper.writeValueAsString(last);
            out.print(jsonstring);
        }else{
            String[] splits = info.split("/");
            if (splits.length != 2) {
                res.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            int id = Integer.valueOf(splits[1]);
            IngredientId ii = objectMapper.readValue(data.toString(), IngredientId.class);
            if(!dao.saveIngredient(id, ii)){
                res.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            PizzaGet updated = dao.findById(id);
            String jsonstring = objectMapper.writeValueAsString(updated);
            out.print(jsonstring);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }
    
    
}
