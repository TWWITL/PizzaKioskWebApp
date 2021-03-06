package com.tw.pizzakiosk.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tw.pizzakiosk.crusts.Crust;
import com.tw.pizzakiosk.crusts.ThickCrust;
import com.tw.pizzakiosk.crusts.ThinCrust;
import com.tw.pizzakiosk.sizes.Size;
import com.tw.pizzakiosk.toppings.Topping;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class DataSource {
    static DataSource dataSource = new DataSource();
    public static DataSource instance() {
        return dataSource;
    }

    private  List<Crust> crustList;
    private  final Map<String, Topping> toppingMap ;
    private  final Map<String, Size> sizeMap;

    private DataSource() {
        byte[] jsonData = null;

        this.sizeMap = new HashMap<>();
        this.toppingMap = new HashMap<>();

        try {
            jsonData = Files.readAllBytes(Paths.get("config.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode rootNode = null;
        try {
            rootNode = objectMapper.readTree(jsonData);
        } catch (IOException e) {
            e.printStackTrace();
        }

        loadSizes(objectMapper, rootNode);
        loadToppings(objectMapper, rootNode);
        loadCrustList();
    }

    private void loadCrustList() {
        crustList = new ArrayList<>();
        crustList.add(new ThickCrust());
        crustList.add(new ThinCrust());

    }

    private void loadToppings(ObjectMapper objectMapper, JsonNode rootNode) {
        Iterator<JsonNode> elements;
        JsonNode obj;

        JsonNode toppings = rootNode.path("toppings");
        elements = toppings.elements();

        while (elements.hasNext()) {
            obj = elements.next();

            try {
                Topping topping = objectMapper.treeToValue(obj, Topping.class);
                this.toppingMap.put(topping.getDesc(), topping);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    public  void loadSizes(ObjectMapper objectMapper, JsonNode rootNode) {
        JsonNode sizes = rootNode.path("sizes");
        Iterator<JsonNode> elements = sizes.elements();
        JsonNode obj;

        while (elements.hasNext()) {
            obj = elements.next();

            try {
                Size size = objectMapper.treeToValue(obj, Size.class);
                this.sizeMap.put(size.getDesc(), size);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    public Map<String, Size> getSizeMap() {
        return this.sizeMap;
    }

    public Map<String, Topping> getToppingMap() {
        return this.toppingMap;
    }

    public List<Crust> getCrustsList() { return this.crustList; }
}
