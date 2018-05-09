package match;

import models.MatchModel;
import services.QueryHandler;
import shared.match.location.Location;

public class Player {
        private QueryHandler playerSocket;

        public Player(QueryHandler playerSocket) {
            this.playerSocket = playerSocket;
        }

        public QueryHandler getPlayerSocket() {
            return playerSocket;
        }

        public void sendPlayerLocation(Location.UserLocation location) {
            if (MatchModel.canGoTo()) {
                playerSocket.sendData("watchUpdate", "matchLocation", location);
            }
        }

        public void subscribeToLocationRequests() {
            playerSocket.getPlayerLocation().subscribe(data -> {
                Location.UserLocation location = (Location.UserLocation) data;
                sendPlayerLocation(location);
            });
        }
    }
