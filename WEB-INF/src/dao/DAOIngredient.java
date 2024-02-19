package dao;

import java.util.List;

import dto.IngredientGet;
import dto.IngredientPost;

public interface DAOIngredient {

    public List<IngredientGet> findAll();
    public IngredientGet findById(int id);
    public void save(IngredientPost i);
    public void delete(int id);

}