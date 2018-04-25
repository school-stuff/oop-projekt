package services;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DatabaseService {
    private static DatabaseService ourInstance = new DatabaseService();

    public static DatabaseService getInstance() {
        return ourInstance;
    }

    private Connection connection;

    private DatabaseService() {
        Map<String, String> env = new HashMap<>();
        try (Scanner scanner = new Scanner(new File("./server/src/.env"), "UTF-8")) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.length() < 1) {
                    continue;
                }
                String[] lines = line.split("=");
                env.put(lines[0].trim(), lines[1].trim());
            }
            connection = DriverManager.getConnection(
                    env.get("DATABASE_URL"),
                    env.get("DATABASE_USER"),
                    env.get("DATABASE_PASSWORD"));
        } catch (Exception e) {
            // TODO: error handling
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
