package dto;

import java.util.List;

public class PizzaQte extends PizzaGet{
    
    private int qte;
    
    public PizzaQte() {
    }
    
    public PizzaQte(int id, String nom, String pate, int prixBase, List<IngredientGet> ingredients, int qte) {
        super(id, nom, pate, prixBase, ingredients);
        this.qte = qte;
    }
    
    public int getQte() {
        return qte;
    }
    public void setQte(int qte) {
        this.qte = qte;
    }

    public static PizzaQte valueOf(PizzaGet p, int qte){
        return new PizzaQte(p.getId(), p.getNom(), p.getPate(), p.getPrixBase(), p.getIngredients(), qte);
    }

    @Override
    public int prixFinal(){
        return super.prixFinal() * qte;
    }
}
