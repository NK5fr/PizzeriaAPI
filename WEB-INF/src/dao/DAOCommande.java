package dao;

import java.util.List;

import dto.CommandeGet;
import dto.CommandePost;
import dto.PizzaGet;
import dto.PizzaId;

public interface DAOCommande {


    public List<CommandeGet> findAll();
    public CommandeGet findById(int id);
    public CommandeGet save(CommandePost c);
    public boolean delete(int id);
    public CommandeGet savePizza(int id, PizzaId i);
    public boolean deletePizza(int cno, int pno);
    public CommandeGet update(int id, CommandePost c);
    public CommandeGet strictUpdate(int id, CommandePost c);
}
