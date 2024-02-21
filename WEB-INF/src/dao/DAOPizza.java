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
    public void saveIngredient(Integer i);
    
}