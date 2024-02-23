package dao;

import java.util.List;
import dto.IngredientId;
import dto.PizzaGet;
import dto.PizzaId;
import dto.PizzaPost;

public interface DAOPizza {

    public List<PizzaGet> findAll();
    public PizzaGet findById(int id);
    public PizzaGet save(PizzaPost p);
    public boolean delete(int id);
    public boolean saveIngredient(int id, IngredientId i);
    public boolean deleteIngredient(int pno, int ino);
    public boolean update(int id, PizzaPost p);
    public boolean strictUpdate(int id, PizzaPost p);
}