package game;

import io.reactivex.subjects.ReplaySubject;

public class Player {

    public static ReplaySubject<Integer> opponentsAlive = ReplaySubject.create();
    public static ReplaySubject<Integer> slotEquipped = ReplaySubject.create();
    public static ReplaySubject<Integer> health = ReplaySubject.create();
    public static ReplaySubject<Integer> armor = ReplaySubject.create();
    public static ReplaySubject<int[]> inventory = ReplaySubject.create();

}
