package controleurs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.DAOIngredient;
import dao.IngredientDAODatabase;
import dto.IngredientGet;
import dto.IngredientId;
import dto.IngredientPost;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.TokenValidation;

@WebServlet("/ingredients/*")
public class IngredientRestAPI extends restAPI{

    private DAOIngredient dao = new IngredientDAODatabase();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();


        String info = req.getPathInfo();
        if (info == null || info.equals("/")) {
            List<IngredientGet> l = dao.findAll();
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
        IngredientGet i = dao.findById(id);
        if (i == null) {
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        if(splits.length == 3 && splits[2].equals("name")){
            out.print("{\"nom\":\"" + i.getNom() + "\"}");
            return;
        }else if(splits.length == 3){
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        out.print(objectMapper.writeValueAsString(i));
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

        String info = req.getPathInfo();
        
        if(info != null && !info.equals("/")){
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String line;
        while ((line = reader.readLine()) != null) {
            data.append(line);
        }

        IngredientPost i = objectMapper.readValue(data.toString(), IngredientPost.class);

        IngredientGet result = dao.save(i);
        if(result == null){
            res.sendError(HttpServletResponse.SC_CONFLICT);
        }else{
            out.print(objectMapper.writeValueAsString(result));
        }

        
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

        if (splits.length != 2) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        int id = Integer.parseInt(splits[1]);
        
        if(!dao.delete(id)){
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
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

        IngredientPost i = objectMapper.readValue(data.toString(), IngredientPost.class);

        IngredientGet updated = dao.strictUpdate(id, i);

        if(updated == null){
            res.sendError(HttpServletResponse.SC_CONFLICT);
            return;
        }
        

        String jsonstring = objectMapper.writeValueAsString(updated);
        out.print(jsonstring);
    }

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

        IngredientPost i = objectMapper.readValue(data.toString(), IngredientPost.class);

        IngredientGet updated = dao.update(id, i);

        if(updated == null){
            res.sendError(HttpServletResponse.SC_CONFLICT);
            return;
        }
        
        String jsonstring = objectMapper.writeValueAsString(updated);
        out.print(jsonstring);
    }

    
    
    
}
