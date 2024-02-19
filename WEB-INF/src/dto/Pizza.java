package dto;

import java.util.List;

public class Pizza {
    
    private String nom;
    private String pate;
    private int prixBase;
    private List<IngredientGet> ingredients;
    
    public Pizza() {
    }

    public Pizza(String nom, String pate, int prixBase, List<IngredientGet> ingredients) {
        this.nom = nom;
        this.pate = pate;
        this.prixBase = prixBase;
        this.ingredients = ingredients;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPate() {
        return pate;
    }

    public void setPate(String pate) {
        this.pate = pate;
    }

    public int getPrixBase() {
        return prixBase;
    }

    public void setPrixBase(int prixBase) {
        this.prixBase = prixBase;
    }

    public List<IngredientGet> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientGet> ingredients) {
        this.ingredients = ingredients;
    }

    
}
