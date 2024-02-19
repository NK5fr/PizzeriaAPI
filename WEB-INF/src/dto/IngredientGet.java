package dto;

public class IngredientGet {

    private int id;
    private String nom;
    private int prix;

    

    public IngredientGet(int id, String nom, int prix) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
    }


    public IngredientGet() {
    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getPrix() {
        return prix;
    }
    public void setPrix(int prix) {
        this.prix = prix;
    }


    public String getNom() {
        return nom;
    }


    public void setNom(String nom) {
        this.nom = nom;
    }

}