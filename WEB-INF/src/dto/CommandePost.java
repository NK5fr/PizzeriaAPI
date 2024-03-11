package dto;

import java.sql.Date;
import java.util.List;

public class CommandePost {
    
    private String client;
    private List<PizzaId> pizzas;

    public CommandePost() {
    }
    
    public CommandePost(String client, List<PizzaId> pizzas) {
        this.client = client;
        this.pizzas = pizzas;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public List<PizzaId> getPizzas() {
        return pizzas;
    }

    public void setPizzas(List<PizzaId> pizzas) {
        this.pizzas = pizzas;
    }

    
}
