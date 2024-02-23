package dao;

import java.util.List;
import dto.IngredientId;
import dto.PizzaGet;
import dto.PizzaId;
import dto.PizzaPost;

public interface DAOPizza {

    public List<PizzaGet> findAll();
    public PizzaGet findById(int id);
    public PizzaGet findByName(String name);
    public boolean save(PizzaPost p);
    public boolean delete(int id);
    public boolean saveIngredient(int id, IngredientId i);
    public boolean deleteIngredient(int pno, int ino);
    public boolean update(int id, PizzaPost p);
}