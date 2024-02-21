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
        PreparedStatement ps = null;
        List<PizzaGet> listePizzas = new ArrayList<>();
        try(Connection con = DS.getConnection()){
            ps = con.prepareStatement("select * from pizzas join compose using(pno) join ingredients using(ino)");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                listePizzas.add(null);
            }
        }catch(Exception e){
            System.out.println(ps);
            System.out.println(e.getMessage());
        }
        return listePizzas;
    }

    @Override
    public PizzaGet findById(int id) {
        // TODO Auto-generated method stub
        return null;
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

    @Override
    public int finalPrice(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'finalPrice'");
    }

    

    
    
    
}
