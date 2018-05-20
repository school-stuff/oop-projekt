package match;

import battleFieldMap.Maps;
import services.MapService;

import java.util.*;

public class ItemHandler {
    private int numberOfItems = 15;
    private Set<Integer> itemIdAwailable = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5));
    private Map<Integer, List<Item>> itemsPerRow = new HashMap<>();

    public ItemHandler() {
        generateAmountOfItems();
        System.out.println(itemsPerRow);
    }

    private void generateAmountOfItems() {
        for (int i = 0; i < numberOfItems; i++) {
            Item item = generateRandomItem();
            if (!itemsPerRow.containsKey(item.getX())) {
                itemsPerRow.put(item.getX(), new ArrayList<>());
                itemsPerRow.get(item.getX()).add(item);
            }
        }
    }

    private Item generateRandomItem() {
        int itemType = 0;
        int x = (int) Math.round(Math.random() * (Maps.map[0].length - 1));
        int y = (int) Math.round(Math.random() * (Maps.map.length - 1));
        while (!itemIdAwailable.contains(itemType)) {
            itemType = (int) Math.round(Math.random()*itemIdAwailable.size());
        }
        while (!MapService.canGoToLocation(x, y)){
            x = (int) Math.round(Math.random() * Maps.map[0].length);
            y = (int) Math.round(Math.random() * Maps.map.length);
        }
        return new Item(x, y, itemType);
    }
}
