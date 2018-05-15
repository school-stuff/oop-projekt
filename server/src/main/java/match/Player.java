package match;

import models.MatchModel;
import services.QueryHandler;
import shared.match.location.Location;

public class Player {
        private QueryHandler playerSocket;

        public Player(QueryHandler playerSocket) {
            this.playerSocket = playerSocket;

            playerSocket.locationRequest().subscribe(data -> {
                updatePlayerLocation((Location.UserLocation) data);
            });

            playerSocket.createWatchQuery("matchLocation").subscribe(data -> {
                playerSocket.sendData("watchUpdate", "matchLocation", data);
            });
        }

        public QueryHandler getPlayerSocket() {
            return playerSocket;
        }

        public void updatePlayerLocation(Location.UserLocation location) {
            if (MatchModel.canGoTo(location)) {
                playerSocket.updateLocation(location);
            }
        }

        public void sendOpponentLocation(Location.UserLocation location) {
            playerSocket.sendData("watchUpdate", "opponentLocation", location);
        }

    }
