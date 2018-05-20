package match;

import battleFieldMap.Maps;
import services.MapService;
import shared.match.item.RenderItem;

import java.util.*;

public class ItemHandler {
    private int numberOfItems = 15;
    private Set<Integer> itemIdAvailable = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5));
    private Map<Integer, List<Item>> itemsPerRow = new HashMap<>();

    public ItemHandler() {
        generateAmountOfItems();
    }

    public Set<RenderItem.ItemData> getItemsToRender(int x, int y) {
        Set<RenderItem.ItemData> itemData = new HashSet<>();
        for (int i = x - 5; i < x + 5; i++) {
            for (Item item : itemsPerRow.get(i)) {
                if (item.getY() > y - 5 && item.getY() < y + 5) {
                    itemData.add(RenderItem.ItemData.newBuilder().setX(item.getX()).setY(item.getY()).setId(item.getId()).build());
                }
            }
        }
        return itemData;
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
        while (!itemIdAvailable.contains(itemType)) {
            itemType = (int) Math.round(Math.random()* itemIdAvailable.size());
        }
        while (!MapService.canGoToLocation(x, y)){
            x = (int) Math.round(Math.random() * Maps.map[0].length);
            y = (int) Math.round(Math.random() * Maps.map.length);
        }
        return new Item(x, y, itemType);
    }

}
