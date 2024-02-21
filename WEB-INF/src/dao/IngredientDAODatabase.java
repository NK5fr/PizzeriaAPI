package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dto.IngredientGet;
import dto.IngredientPost;
import util.DS;

public class IngredientDAODatabase implements DAOIngredient{

    @Override
    public List<IngredientGet> findAll() {
        PreparedStatement ps = null;
        List<IngredientGet> listeIngredients = new ArrayList<>();
        try(Connection con = DS.getConnection()){
            ps = con.prepareStatement("select * from ingredients");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                listeIngredients.add(new IngredientGet(rs.getInt("ino"), rs.getString("inom"), rs.getInt("prix")));
            }
        }catch(Exception e){
            System.out.println(ps);
            System.out.println(e.getMessage());
        }
        return listeIngredients;
    }

    @Override
    public IngredientGet findById(int id) {
        PreparedStatement ps = null;
        IngredientGet ingredient = null;
        try(Connection con = DS.getConnection()){
            ps = con.prepareStatement("select * from ingredients where ino = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return new IngredientGet(rs.getInt("ino"), rs.getString("inom"), rs.getInt("prix"));
        }catch(Exception e){
            System.out.println(ps);
            System.out.println(e.getMessage());
        }
        return ingredient;
    }

    @Override
    public void save(IngredientPost i) {
        PreparedStatement ps = null;
        try(Connection con = DS.getConnection()){
            ps = con.prepareStatement("insert into ingredients(inom, prix) values(?,?)");
            ps.setString(1, i.getNom());
            ps.setInt(2, i.getPrix());
            ps.executeUpdate();
        }catch(Exception e){
            System.out.println(ps);
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        PreparedStatement ps = null;
        try(Connection con = DS.getConnection()){
            ps = con.prepareStatement("delete from ingredients where ino = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
        }catch(Exception e){
            System.out.println(ps);
            System.out.println(e.getMessage());
        }
        
    }

    
    
    
}
