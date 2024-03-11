package dto;

public class PizzaId {
    
    private int id;
    private int qte;

    public PizzaId() {
    }

    public PizzaId(int id, int qte) {
        this.id = id;
        this.qte = qte;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    

}
