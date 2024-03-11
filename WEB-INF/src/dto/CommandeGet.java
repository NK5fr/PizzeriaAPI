package dto;

import java.sql.Date;
import java.util.List;

public class CommandeGet {
    
    private int id;
    private String client;
    private Date date;
    private List<PizzaQte> pizzas;

    public CommandeGet() {
    }
    
    public CommandeGet(int id, String client, Date date, List<PizzaQte> pizzas) {
        this.id = id;
        this.client = client;
        this.date = date;
        this.pizzas = pizzas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<PizzaQte> getPizzas() {
        return pizzas;
    }

    public void setPizzas(List<PizzaQte> pizzas) {
        this.pizzas = pizzas;
    }

    public int prixFinal(){
        int total = 0;
        for(PizzaQte p : pizzas){
            total += p.prixFinal();
        }
        return total;
    }
}
