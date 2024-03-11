package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dto.CommandeGet;
import dto.CommandePost;
import dto.IngredientGet;
import dto.IngredientId;
import dto.PizzaGet;
import dto.PizzaId;
import dto.PizzaQte;
import util.DS;

public class CommandeDAODatabase implements DAOCommande{

    @Override
    public boolean delete(int id) {
        PreparedStatement ps = null;
        try(Connection con = DS.getConnection()){
            ps = con.prepareStatement("delete from commandes where cno = ?");
            ps.setInt(1, id);
            int nbAffectedLines = ps.executeUpdate();
            if (nbAffectedLines == 0) {
                throw new Exception("Aucune ligne supprimée");
            }
        }catch(Exception e){
            System.out.println(ps);
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean deletePizza(int cno, int pno) {
        PreparedStatement ps = null;
        try(Connection con = DS.getConnection()){
            ps = con.prepareStatement("update contient set qte = qte - 1 where cno = ? and pno = ? returning qte");
            ps.setInt(1, cno);
            ps.setInt(2, pno);
            ResultSet rs = ps.executeQuery();
            rs.next();
            if(rs.getInt("qte") == 0){
                ps = con.prepareStatement("delete from contient where cno = ? and pno = ?");
                ps.setInt(1, cno);
                ps.setInt(2, pno);
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
    public List<CommandeGet> findAll() {
        PreparedStatement ps = null;
        List<CommandeGet> listeCommandes = new ArrayList<>();
        try(Connection con = DS.getConnection()){
            ps = con.prepareStatement("select * from commandes");
            ResultSet rsc = ps.executeQuery();
            while(rsc.next()){
                List<PizzaQte> listePizzas = new ArrayList<>();
                ps = con.prepareStatement("select pno, qte from contient where cno = ?");
                ps.setInt(1, rsc.getInt("cno"));
                ResultSet rsp = ps.executeQuery();
                while(rsp.next()){
                    PizzaGet p = new PizzaDAODatabase().findById(rsp.getInt("pno"));
                    listePizzas.add(PizzaQte.valueOf(p, rsp.getInt("qte")));
                }
                CommandeGet cg = new CommandeGet(rsc.getInt("cno"), rsc.getString("cnom"), rsc.getDate("date"), listePizzas);
                listeCommandes.add(cg);
            }
        }catch(Exception e){
            System.out.println(ps);
            System.out.println(e.getMessage());
        }
        return listeCommandes;
    }

    @Override
    public CommandeGet findById(int id) {
        PreparedStatement ps = null;
        CommandeGet cg = null;
        try(Connection con = DS.getConnection()){
            ps = con.prepareStatement("select * from commandes where cno = ?");
            ps.setInt(1, id);
            ResultSet rsc = ps.executeQuery();
            rsc.next();
            List<PizzaQte> listePizzas = new ArrayList<>();
            ps = con.prepareStatement("select pno, qte from contient where cno = ?");
            ps.setInt(1, id);
            ResultSet rsp = ps.executeQuery();
            while(rsp.next()){
                PizzaGet p = new PizzaDAODatabase().findById(rsp.getInt("pno"));
                listePizzas.add(PizzaQte.valueOf(p, rsp.getInt("qte")));
            }
            cg = new CommandeGet(rsc.getInt("cno"), rsc.getString("cnom"), rsc.getDate("date"), listePizzas);
        }catch(Exception e){
            System.out.println(ps);
            System.out.println(e.getMessage());
        }
        return cg;
    }

    @Override
    public CommandeGet save(CommandePost c) {
        PreparedStatement ps = null;
        CommandeGet result = null;
        Connection con = null;
        try{
            con = DS.getConnection();
            con.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            con.setAutoCommit(false);
            ps = con.prepareStatement("insert into commandes(cnom, date) values(?,?) returning cno");
            ps.setString(1, c.getClient());
            ps.setDate(2, Date.valueOf(LocalDate.now()));
            ResultSet rs = ps.executeQuery();
            rs.next();
            if (c.getPizzas() == null) c.setPizzas(new ArrayList<>());
            for(PizzaId pi : c.getPizzas()){
                ps = con.prepareStatement("insert into contient(cno, pno, qte) values(?,?,?)");
                ps.setInt(1, rs.getInt("cno"));
                ps.setInt(2, pi.getId());
                ps.setInt(3, pi.getQte());
                ps.executeUpdate();
            }
            con.commit();
            result = findById(rs.getInt("cno"));
        }catch(Exception e){
            System.out.println(ps);
            System.out.println(e.getMessage());
            try {
                con.rollback();
            } catch (Exception e1) {
                System.out.println(e1.getMessage());
            }
        }finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return result ;
    }

    @Override
    public CommandeGet savePizza(int id, PizzaId i) {
        PreparedStatement ps = null;
        CommandeGet result = null;
        try(Connection con = DS.getConnection()){
            ps = con.prepareStatement("update contient set qte = qte + ? where cno = ? and pno = ? returning qte");
            ps.setInt(1, i.getQte());
            ps.setInt(2, id);
            ps.setInt(3, i.getId());
            ResultSet rs = ps.executeQuery();
            if(!rs.next()){
                ps = con.prepareStatement("insert into contient values(?,?,?)");
                ps.setInt(1, id);
                ps.setInt(2, i.getId());
                ps.setInt(3, i.getQte());
                ps.executeUpdate();
            }
            result = findById(id);
        }catch(Exception e){
            System.out.println(ps);
            System.out.println(e.getMessage());
        }
        return result;
    }

    @Override
    public CommandeGet strictUpdate(int id, CommandePost c) {
        PreparedStatement ps = null;
        Connection con = null;
        CommandeGet result = null;
        try{
            con = DS.getConnection();
            con.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            con.setAutoCommit(false);
            ps = con.prepareStatement("update commandes set cnom = ? where cno = ?");

            ps.setString(1, c.getClient());
            ps.setInt(2, id);
            ps.executeUpdate();

            ps = con.prepareStatement("delete from contient where cno = ?");
            ps.setInt(1, id);
            ps.executeUpdate();

            if(c.getPizzas() != null){
                for(PizzaId pi : c.getPizzas()){
                    ps = con.prepareStatement("insert into contient(cno, pno, qte) values(?,?,?)");
                    ps.setInt(1, id);
                    ps.setInt(2, pi.getId());
                    ps.setInt(3, pi.getQte());
                    ps.executeUpdate();
                }
            }
            con.commit();
            result = findById(id);
        }catch(Exception e){
            System.out.println(ps);
            System.out.println(e.getMessage());
            try {
                con.rollback();
            } catch (Exception e1) {
                System.out.println(e1.getMessage());
            }
        }finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return result;
    }

    @Override
    public CommandeGet update(int id, CommandePost c) {
        PreparedStatement ps = null;
        CommandeGet actual = findById(id);
        Connection con = null;
        CommandeGet result = null;
        try{
            con = DS.getConnection();
            con.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            con.setAutoCommit(false);
            ps = con.prepareStatement("update commandes set cnom = ? where cno = ?");

            if(c.getClient() != null){
                ps.setString(1, c.getClient());
            }else{
                ps.setString(1, actual.getClient());
            }

            ps.setInt(2, id);
            ps.executeUpdate();


            if(c.getPizzas() != null){
                ps = con.prepareStatement("delete from contient where cno = ?");
                ps.setInt(1, id);
                ps.executeUpdate();
                for(PizzaId pi : c.getPizzas()){
                    ps = con.prepareStatement("insert into contient(cno, pno, qte) values(?,?,?)");
                    ps.setInt(1, id);
                    ps.setInt(2, pi.getId());
                    ps.setInt(3, pi.getQte());
                    ps.executeUpdate();
                }
            }
            con.commit();
            result = findById(id);
        }catch(Exception e){
            System.out.println(ps);
            System.out.println(e.getMessage());
            try {
                con.rollback();
            } catch (Exception e1) {
                System.out.println(e1.getMessage());
            }
        }finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return result;
    }

    
}