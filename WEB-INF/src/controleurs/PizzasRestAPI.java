package controleurs;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.DAOIngredient;
import dao.DAOPizza;
import dao.IngredientDAODatabase;
import dao.PizzaDAODatabase;
import dto.IngredientGet;
import dto.IngredientName;
import dto.PizzaGet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/pizzas/*")
public class PizzasRestAPI extends restAPI{

    private DAOPizza dao = new PizzaDAODatabase();

    @Override
    public void doPatch(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
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
        PizzaGet i = dao.findById(id);
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
        res.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }
    
    
}
