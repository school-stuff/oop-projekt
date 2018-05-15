package services;

import battleFieldMap.Maps;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class MapService {
    private static Set<Integer> canGoValues = new HashSet<>(Collections.singletonList(0));
    private static Set<Integer> canNotGoValues = new HashSet<>(Arrays.asList(-1, 1, 2));

    public static boolean canGoToLocation(int x, int y) {
        return canGoValues.contains(Maps.map[y][x]);
    }
}
