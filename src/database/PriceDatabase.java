package database;

import model.Price;

import java.util.HashMap;
import java.util.Map;

public class PriceDatabase {
    private Map<String, Price> priceDatabase;

    public PriceDatabase() {
        priceDatabase = new HashMap<>();
    }

    public void savePrice(Price price) {
        priceDatabase.put(price.getToolType().getToolType(), price);
    }

    public Price getPrice(String id) {
        return priceDatabase.get(id);
    }

    public void listPrices() {
        for (Price price : priceDatabase.values()) {
            System.out.println(price);
        }
    }
}
