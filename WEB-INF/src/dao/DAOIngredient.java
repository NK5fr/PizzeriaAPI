package dao;

import java.util.List;

import dto.IngredientGet;
import dto.IngredientId;
import dto.IngredientPost;

public interface DAOIngredient {

    public List<IngredientGet> findAll();
    public IngredientGet findById(int id);
    public IngredientGet save(IngredientPost i);
    public boolean delete(int id);
    public IngredientGet update(int id, IngredientPost i);
    public IngredientGet strictUpdate(int id, IngredientPost i);

}