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
import dto.IngredientName;
import dto.IngredientPost;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
            IngredientName in = new IngredientName(i.getNom());
            out.print(objectMapper.writeValueAsString(in));
            return;
        }else if(splits.length == 3){
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        out.print(objectMapper.writeValueAsString(i));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
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

        if(dao.save(i)){
            IngredientId ii = dao.findHigherId();
            IngredientGet last = dao.findById(ii.getId());
            out.print(objectMapper.writeValueAsString(last));
        }else{
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

        
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

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
    public void doPatch(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }

    
    
    
}
