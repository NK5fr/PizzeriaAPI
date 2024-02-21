package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dto.IngredientGet;
import dto.IngredientPost;
import dto.PizzaGet;
import dto.PizzaPost;
import util.DS;

public class PizzaDAODatabase implements DAOPizza{

    @Override
    public void delete(int id) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public List<PizzaGet> findAll() {
        PreparedStatement pizza = null;
        PreparedStatement ingredients = null;
        List<PizzaGet> listePizzas = new ArrayList<>();
        try(Connection con = DS.getConnection()){
            pizza = con.prepareStatement("select * from pizzas");
            ResultSet rsp = pizza.executeQuery();
            while(rsp.next()){
                List<IngredientGet> listeIngredients = new ArrayList<>();
                ingredients = con.prepareStatement("select ino, inom, prix from compose join ingredients using(ino) where pno = ?");
                ingredients.setInt(1, rsp.getInt("pno"));
                ResultSet rsi = ingredients.executeQuery();
                while(rsi.next()){
                    listeIngredients.add(new IngredientGet(rsi.getInt("ino"), rsi.getString("inom"), rsi.getInt("prix")));
                }
                PizzaGet pg = new PizzaGet(rsp.getInt("pno"), rsp.getString("pnom"), rsp.getString("pate"), rsp.getInt("prixBase"), listeIngredients);
                listePizzas.add(pg);
            }
        }catch(Exception e){
            System.out.println(pizza);
            System.out.println(ingredients);
            System.out.println(e.getMessage());
        }
        return listePizzas;
    }

    @Override
    public PizzaGet findById(int id) {
        PreparedStatement pizza = null;
        PreparedStatement ingredients = null;
        PizzaGet pizzaGet = null;
        try(Connection con = DS.getConnection()){
            pizza = con.prepareStatement("select * from pizzas where pno = ?");
            pizza.setInt(1, id);
            ResultSet rsp = pizza.executeQuery();
            rsp.next();
            List<IngredientGet> listeIngredients = new ArrayList<>();
            ingredients = con.prepareStatement("select ino, inom, prix from compose join ingredients using(ino) where pno = ?");
            ingredients.setInt(1, id);
            ResultSet rsi = ingredients.executeQuery();
            while(rsi.next()){
                listeIngredients.add(new IngredientGet(rsi.getInt("ino"), rsi.getString("inom"), rsi.getInt("prix")));
            }
            pizzaGet = new PizzaGet(rsp.getInt("pno"), rsp.getString("pnom"), rsp.getString("pate"), rsp.getInt("prixBase"), listeIngredients);
            return pizzaGet;
        }catch(Exception e){
            System.out.println(pizza);
            System.out.println(ingredients);
            System.out.println(e.getMessage());
        }
        return pizzaGet;
    }

    @Override
    public void save(PizzaPost p) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void saveIngredient(int id, int i) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveIngredient'");
    }

    @Override
    public void deleteIngredient(int id, int i) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteIngredient'");
    }

    

    
    
    
}
