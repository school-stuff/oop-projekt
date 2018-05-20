package match;

import com.google.protobuf.AbstractMessage;
import services.QueryHandler;

public class Flower {
    private int[] lifeCycle = {10, 4, 4};
    private int stageCount = 0;
    private int lifeCycleCount = lifeCycle[0];
    private QueryHandler queryHandler;

    public Flower(QueryHandler queryHandler) {
        this.queryHandler = queryHandler;
    }

    public void grow() {
        lifeCycleCount--;
        if (lifeCycleCount == 0) {
            stageCount++;
            if (stageCount > 2) stageCount = 0;
            sendCycleChange(shared.match.plant.Flower.FlowerCycleChange.newBuilder().setStage(stageCount).build());
            lifeCycleCount = lifeCycle[stageCount];
        }
    }

    private void sendCycleChange(AbstractMessage data) {
        queryHandler.sendData("watchUpdate", "flowerCycle", data);
    }
}
