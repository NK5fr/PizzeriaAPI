package dto;

public class IngredientPost {
    
    private String nom;
    private int prix;
    
    public IngredientPost(String nom, int prix) {
        this.nom = nom;
        this.prix = prix;
    }

    public IngredientPost() {
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    
}
