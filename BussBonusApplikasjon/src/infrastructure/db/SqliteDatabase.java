package infrastructure.db;

import core.port.UserRepository;
import core.port.RouteRepository;
import core.port.TripRepository;

import core.domain.User;
import core.domain.Admin;
import core.domain.EndUser;
import core.domain.Route;
import core.domain.Trip;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Main SQLite database class.
 * Implements all repository ports.
 * Handles table creation and SQL queries.
 */
public class SqliteDatabase implements UserRepository, RouteRepository, TripRepository {

    private final SQLiteDatabaseConnection connection;

    // Constructor: inject the connection helper
    public SqliteDatabase(SQLiteDatabaseConnection connection) {
        this.connection = connection;
    }

    /**
     * Creates all necessary tables if they do not already exist.
     * Call this once at application startup.
     */
    public void initialize() {
        try (Connection conn = connection.getConnection();
             Statement stmt = conn.createStatement()) {

            // Create table for users
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS users (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    first_name TEXT NOT NULL,
                    last_name TEXT NOT NULL,
                    email TEXT NOT NULL UNIQUE,
                    user_type TEXT NOT NULL,
                    created_at TEXT NOT NULL
                );
            """);

            // Create table for routes
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS routes (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    start_stop TEXT NOT NULL,
                    end_stop TEXT NOT NULL,
                    time TEXT NOT NULL,
                    delayed INTEGER NOT NULL
                );
            """);

            // Create table for trips
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS trips (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    user_id INTEGER NOT NULL,
                    route_id INTEGER NOT NULL,
                    departure_time TEXT NOT NULL,
                    arrival_time TEXT NOT NULL,
                    bonus_trip INTEGER NOT NULL,
                    created_at TEXT NOT NULL
                );
            """);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ----------------------------------------------------------------------
    // USER REPOSITORY IMPLEMENTATION
    // ----------------------------------------------------------------------

    @Override
    public Optional<Route> findRouteById(int id) {
        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM routes WHERE id = ?")) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) return Optional.empty();

            return Optional.of(mapRoute(rs));

        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }



    @Override
    public Optional<Trip> findTripById(int tripId) {
        return Optional.empty();
    }

    @Override
    public Trip saveTrip(Trip trip) {
        return null;
    }

    @Override
    public List<Trip> findTripByUserId(int userId) {
        return List.of();
    }

    @Override
    public List<Trip> findAllTrips() {
        return List.of();
    }

    @Override
    public List<Route> searchByStops(String startBusStop, String endBusStop) {
        return List.of();
    }

    @Override
    public Route saveRoute(Route route) {
        return null;
    }

    @Override
    public Optional<User> findUserById(int id) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE email = ?")) {

            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) return Optional.empty();

            return Optional.of(mapUser(rs));

        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public User saveUser(User user) {
        return user.getUserId() == 0 ? insertUser(user) : updateUser(user);
    }

    @Override
    public List<User> findAllUsers() {
        return List.of();
    }

    private User insertUser(User user) {
        String sql = """
            INSERT INTO users (first_name, last_name, email, user_type, created_at)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getUserType());
            stmt.setString(5, user.getCreatedAt().toString());

            stmt.executeUpdate();
            ResultSet keys = stmt.getGeneratedKeys();

            if (keys.next()) {
                int id = keys.getInt(1);
                return copyUserWithId(user, id);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    private User updateUser(User user) {
        String sql = """
            UPDATE users
            SET first_name = ?, last_name = ?, email = ?, user_type = ?
            WHERE id = ?
        """;

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getUserType());
            stmt.setInt(5, user.getUserId());

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public List<Route> findAllRoutes() {
        List<Route> list = new ArrayList<>();

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM routes");
             ResultSet rs = stmt.executeQuery()) {

            /*id INTEGER PRIMARY KEY AUTOINCREMENT,
                    start_stop TEXT NOT NULL,
                    end_stop TEXT NOT NULL,
                    time TEXT NOT NULL,
                    delayed INTEGER NOT NULL*/

            while (rs.next()) {
                list.add(mapRoute(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    private Route mapRoute(ResultSet rs) throws Exception{
        int id = rs.getInt("id");
        String start_stop = rs.getString("start_stop");
        String end_stop = rs.getString("end_stop");
        LocalDateTime time = LocalDateTime.parse(rs.getString("time"));
        int isDelayedInteger = rs.getInt("isDelayed");

        boolean isDelayed = false;

        if (isDelayedInteger == 1){
            isDelayed = true;
        }

        return new Route(id, start_stop, end_stop, time, isDelayed);

    }




    @Override
    public void deleteUserById(int id) {
        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM users WHERE id = ?")) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private User mapUser(ResultSet rs) throws Exception {
        int id = rs.getInt("id");
        String first_name = rs.getString("first_name");
        String last_name = rs.getString("last_name");
        String email = rs.getString("email");
        String userType = rs.getString("user_type");
        LocalDateTime createdAt = LocalDateTime.parse(rs.getString("created_at"));

        return "ADMIN".equalsIgnoreCase(userType)
                ? new Admin(id, first_name, last_name, email, userType, createdAt)
                : new EndUser(id, first_name, last_name, email, userType, createdAt);
    }

    private User copyUserWithId(User user, int id) {
        return user instanceof Admin
                ? new Admin(id, user.getFirstName(), user.getLastName(), user.getEmail(), user.getUserType(),  user.getCreatedAt())
                : new EndUser(id, user.getFirstName(), user.getLastName(), user.getEmail(), user.getUserType(), user.getCreatedAt());
    }

}
