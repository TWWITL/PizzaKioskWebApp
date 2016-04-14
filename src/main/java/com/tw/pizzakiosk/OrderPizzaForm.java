package com.tw.pizzakiosk;

import java.util.List;

public class OrderPizzaForm {
    private String size;
    private String crustType;
    private List<String> toppings;

    public OrderPizzaForm() {
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCrustType() {
        return crustType;
    }

    public void setCrustType(String crustType) {
        this.crustType = crustType;
    }

    public List<String> getToppings() {
        return toppings;
    }

    public void setToppings(List<String> toppings) {
        this.toppings = toppings;
    }
}
