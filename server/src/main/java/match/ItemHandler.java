package match;

import battleFieldMap.Maps;
import services.MapService;
import shared.match.item.RenderItem;

import java.util.*;

public class ItemHandler {
    private int halfOfRenderedSquare = 5;
    private int numberOfItems = 50;
    private Set<Integer> itemIdAvailable = new HashSet<>(Arrays.asList(1, 2, 3, 4));

    private Map<Integer, List<Item>> itemsPerRow = new HashMap<>();

    public ItemHandler() {
        generateAmountOfItems();
    }

    public Set<RenderItem.ItemData> getItemsToRender(int x, int y) {
        int firstRenderedRow = getFirstRenderedIndex(y);
        int lastRenderedRow = firstRenderedRow + 10;
        int firstRenderedColumn = getFirstRenderedIndex(x);
        int lastRenderedColumn = firstRenderedColumn + 10;

        Set<RenderItem.ItemData> itemData = new HashSet<>();
        for (int i = firstRenderedColumn; i < lastRenderedColumn + 1; i++) {
            if (!itemsPerRow.containsKey(i)) continue;
            for (Item item : itemsPerRow.get(i)) {
                if (item.getY() >= firstRenderedRow && item.getY() <= lastRenderedRow) {
                    itemData.add(RenderItem.ItemData.newBuilder().
                            setX(item.getX() - firstRenderedColumn).
                            setY(item.getY() - firstRenderedRow).
                            setId(item.getId()).build());
                }
            }
        }
        return itemData;
    }

    private int getFirstRenderedIndex(int y) {
        int firstRenderedRow = y - halfOfRenderedSquare;


        if(y <= halfOfRenderedSquare) {
            firstRenderedRow = 0;
        }

        if (y > 49 - halfOfRenderedSquare) {
            firstRenderedRow = 49 - 10;
        }
        return firstRenderedRow;
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
        int x = (int) Math.round(Math.random() * (Maps.map[0].length - 2));
        int y = (int) Math.round(Math.random() * (Maps.map.length - 2));
        while (!itemIdAvailable.contains(itemType)) {
            itemType = (int) Math.round(Math.random() * itemIdAvailable.size());
        }
        while (!MapService.canGoToLocation(x, y)) {
            x = (int) Math.round(Math.random() * Maps.map[0].length);
            y = (int) Math.round(Math.random() * Maps.map.length);
        }
        return new Item(x, y, itemType);
    }
}
