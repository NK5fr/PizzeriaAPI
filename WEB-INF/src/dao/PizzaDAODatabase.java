package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.IngredientGet;
import dto.IngredientId;
import dto.IngredientPost;
import dto.PizzaGet;
import dto.PizzaId;
import dto.PizzaPost;
import util.DS;

public class PizzaDAODatabase implements DAOPizza{

    @Override
    public List<PizzaGet> findAll() {
        PreparedStatement ps = null;
        List<PizzaGet> listePizzas = new ArrayList<>();
        try(Connection con = DS.getConnection()){
            ps = con.prepareStatement("select * from pizzas");
            ResultSet rsp = ps.executeQuery();
            while(rsp.next()){
                List<IngredientGet> listeIngredients = new ArrayList<>();
                ps = con.prepareStatement("select ino, inom, prix from compose join ingredients using(ino) where pno = ?");
                ps.setInt(1, rsp.getInt("pno"));
                ResultSet rsi = ps.executeQuery();
                while(rsi.next()){
                    listeIngredients.add(new IngredientGet(rsi.getInt("ino"), rsi.getString("inom"), rsi.getInt("prix")));
                }
                PizzaGet pg = new PizzaGet(rsp.getInt("pno"), rsp.getString("pnom"), rsp.getString("pate"), rsp.getInt("prixBase"), listeIngredients);
                listePizzas.add(pg);
            }
        }catch(Exception e){
            System.out.println(ps);
            System.out.println(e.getMessage());
        }
        return listePizzas;
    }

    @Override
    public PizzaGet findById(int id) {
        PreparedStatement ps = null;
        PizzaGet pizzaGet = null;
        try(Connection con = DS.getConnection()){
            ps = con.prepareStatement("select * from pizzas where pno = ?");
            ps.setInt(1, id);
            ResultSet rsp = ps.executeQuery();
            rsp.next();
            List<IngredientGet> listeIngredients = new ArrayList<>();
            ps = con.prepareStatement("select ino, inom, prix from compose join ingredients using(ino) where pno = ?");
            ps.setInt(1, id);
            ResultSet rsi = ps.executeQuery();
            while(rsi.next()){
                listeIngredients.add(new IngredientGet(rsi.getInt("ino"), rsi.getString("inom"), rsi.getInt("prix")));
            }
            pizzaGet = new PizzaGet(rsp.getInt("pno"), rsp.getString("pnom"), rsp.getString("pate"), rsp.getInt("prixBase"), listeIngredients);
            return pizzaGet;
        }catch(Exception e){
            System.out.println(ps);
            System.out.println(e.getMessage());
        }
        return pizzaGet;
    }

    @Override
    public boolean save(PizzaPost p) {
        PreparedStatement ps = null;
        try(Connection con = DS.getConnection()){
            ps = con.prepareStatement("insert into pizzas(pnom, pate, prixbase) values(?,?,?)");
            ps.setString(1, p.getNom());
            ps.setString(2, p.getPate());
            ps.setInt(3, p.getPrixBase());
            ps.executeUpdate();
            PizzaGet pi = findByName(p.getNom());
            for(IngredientId ii : p.getIngredients()){
                ps = con.prepareStatement("insert into compose(pno, ino) values(?,?)");
                ps.setInt(1, pi.getId());
                ps.setInt(2, ii.getId());
                ps.executeUpdate();
            }
        }catch(Exception e){
            System.out.println(ps);
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean saveIngredient(int id, IngredientId i) {
        PreparedStatement ps = null;
        try(Connection con = DS.getConnection()){
            ps = con.prepareStatement("insert into compose(pno, ino) values(?,?)");
            ps.setInt(1, id);
            ps.setInt(2, i.getId());
            ps.executeUpdate();
        }catch(Exception e){
            System.out.println(ps);
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(int id) {
        PreparedStatement ps = null;
        try(Connection con = DS.getConnection()){
            ps = con.prepareStatement("delete from pizzas where pno = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
        }catch(Exception e){
            System.out.println(ps);
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteIngredient(int id, int ino) {
        PreparedStatement ps = null;
        try(Connection con = DS.getConnection()){
            ps = con.prepareStatement("delete from compose where pno = ? and ino = ?");
            ps.setInt(1, id);
            ps.setInt(2, ino);
            ps.executeUpdate();
        }catch(Exception e){
            System.out.println(ps);
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean update(int id, PizzaPost p) {
        PreparedStatement ps = null;
        PizzaGet actual = findById(id);
        try(Connection con = DS.getConnection()){
            ps = con.prepareStatement("update pizzas set pnom = ?, pate = ?, prixbase = ? where pno = ?");

            if(p.getNom() != null){
                ps.setString(1, p.getNom());
            }else{
                ps.setString(1, actual.getNom());
            }

            if(p.getPate() != null){
                ps.setString(2, p.getPate());
            }else{
                ps.setString(2, actual.getPate());
            }

            if(p.getPrixBase() != 0){
                ps.setInt(3, p.getPrixBase());
            }else{
                ps.setInt(3, actual.getPrixBase());
            }

            ps.setInt(4, id);
            ps.executeUpdate();


            if(p.getIngredients() != null){
                ps = con.prepareStatement("delete from compose where pno = ?");
                ps.setInt(1, id);
                ps.executeUpdate();
                for(IngredientId ii : p.getIngredients()){
                    ps = con.prepareStatement("insert into compose(pno, ino) values(?,?)");
                    ps.setInt(1, id);
                    ps.setInt(2, ii.getId());
                    ps.executeUpdate();
                }
            }
        }catch(Exception e){
            System.out.println(ps);
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public PizzaGet findByName(String name) {
        PreparedStatement ps = null;
        PizzaGet pizza = null;
        try(Connection con = DS.getConnection()){
            ps = con.prepareStatement("select pno from pizzas where pnom = ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            rs.next();
            pizza = findById(rs.getInt("pno"));
        }catch(Exception e){
            System.out.println(ps);
            System.out.println(e.getMessage());
        }
        return pizza;
    }

    

    
    
    
}
