package dao;

import java.util.List;

import dto.IngredientGet;
import dto.IngredientPost;
import dto.PizzaGet;
import dto.PizzaPost;

public interface DAOPizza {

    public List<PizzaGet> findAll();
    public PizzaGet findById(int id);
    public void save(PizzaPost p);
    public void delete(int id);
    public void saveIngredient(int id, int i);
    public void deleteIngredient(int id, int i);
    public int finalPrice(int id);
}