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


public class SqliteDatabase implements UserRepository, RouteRepository, TripRepository {

    private final SQLiteDatabaseConnection connection;

    // Constructor
    public SqliteDatabase(SQLiteDatabaseConnection connection) {
        this.connection = connection;
    }


    public void initialize() {
        try (Connection conn = connection.getConnection();
             Statement stmt = conn.createStatement()) {

            // users table
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

            // routes table
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS routes (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    start_stop TEXT NOT NULL,
                    end_stop TEXT NOT NULL,
                    time TEXT NOT NULL,
                    delayed INTEGER NOT NULL
                );
            """);

            // trips table
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


    @Override
    public Optional<User> findUserById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) return Optional.empty();
                return Optional.of(mapUser(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) return Optional.empty();
                return Optional.of(mapUser(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public User saveUser(User user) {
        // insert if id == 0, otherwise update
        return user.getUserId() == 0 ? insertUser(user) : updateUser(user);
    }

    private User insertUser(User user) {
        String sql = """
            INSERT INTO users (first_name, last_name, email, user_type, created_at)
            VALUES (?, ?, ?, ?, ?)
            """;

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // set columns
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getUserType());
            stmt.setString(5, user.getCreatedAt().toString()); // ISO format

            stmt.executeUpdate();

            // read generated id
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    int id = keys.getInt(1);
                    return copyUserWithId(user, id);
                }
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
            stmt.setInt(5, user.getUserId()); // use getId()

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public List<User> findAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(mapUser(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public void deleteUserById(int id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private User mapUser(ResultSet rs) throws Exception {
        int id = rs.getInt("id");
        String first = rs.getString("first_name");
        String last = rs.getString("last_name");
        String email = rs.getString("email");
        String userType = rs.getString("user_type");
        LocalDateTime createdAt = LocalDateTime.parse(rs.getString("created_at"));

        // choose subclass based on user_type
        if ("ADMIN".equalsIgnoreCase(userType)) {
            return new Admin(id, first, last, email, userType, createdAt);
        } else {
            return new EndUser(id, first, last, email, userType, createdAt);
        }
    }

    // create a new domain user with assigned id
    private User copyUserWithId(User user, int id) {
        if (user instanceof Admin) {
            return new Admin(id, user.getFirstName(), user.getLastName(), user.getEmail(), user.getUserType(), user.getCreatedAt());
        } else {
            return new EndUser(id, user.getFirstName(), user.getLastName(), user.getEmail(), user.getUserType(), user.getCreatedAt());
        }
    }



    @Override
    public Optional<Route> findRouteById(int routeId) {
        String sql = "SELECT * FROM routes WHERE id = ?";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, routeId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) return Optional.empty();
                return Optional.of(mapRoute(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<Route> searchByStops(String startBusStop, String endBusStop) {
        String sql = "SELECT * FROM routes WHERE start_stop = ? AND end_stop = ?";
        List<Route> list = new ArrayList<>();

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, startBusStop);
            stmt.setString(2, endBusStop);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRoute(rs));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public Route saveRoute(Route route) {
        return route.getRouteId() == 0 ? insertRoute(route) : updateRoute(route);
    }

    private Route insertRoute(Route route) {
        String sql = """
            INSERT INTO routes (start_stop, end_stop, time, delayed)
            VALUES (?, ?, ?, ?)
            """;

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, route.getStartBusStop());
            stmt.setString(2, route.getEndBusStop());
            stmt.setString(3, route.getTime().toString()); // ISO
            stmt.setInt(4, route.isDelayed() ? 1 : 0);

            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    int id = keys.getInt(1);
                    return new Route(id, route.getStartBusStop(), route.getEndBusStop(), route.getTime(), route.isDelayed());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return route;
    }

    private Route updateRoute(Route route) {
        String sql = """
            UPDATE routes
            SET start_stop = ?, end_stop = ?, time = ?, delayed = ?
            WHERE id = ?
            """;

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, route.getStartBusStop());
            stmt.setString(2, route.getEndBusStop());
            stmt.setString(3, route.getTime().toString());
            stmt.setInt(4, route.isDelayed() ? 1 : 0);
            stmt.setInt(5, route.getRouteId());

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return route;
    }

    @Override
    public List<Route> findAllRoutes() {
        List<Route> list = new ArrayList<>();
        String sql = "SELECT * FROM routes";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(mapRoute(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    private Route mapRoute(ResultSet rs) throws Exception {
        int id = rs.getInt("id");
        String start = rs.getString("start_stop");
        String end = rs.getString("end_stop");


        LocalDateTime time = LocalDateTime.parse(rs.getString("time"));


        int delayedInt = rs.getInt("delayed");
        boolean delayed = delayedInt == 1;

        return new Route(id, start, end, time, delayed);
    }



    @Override
    public Optional<Trip> findTripById(int tripId) {
        String sql = "SELECT * FROM trips WHERE id = ?";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, tripId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) return Optional.empty();
                return Optional.of(mapTrip(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Trip saveTrip(Trip trip) {
        return trip.getTripId() == 0 ? insertTrip(trip) : updateTrip(trip);
    }

    private Trip insertTrip(Trip trip) {
        String sql = """
            INSERT INTO trips (user_id, route_id, departure_time, arrival_time, bonus_trip, created_at)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, trip.getUserId());
            stmt.setInt(2, trip.getRouteId());
            stmt.setString(3, trip.getDepartureTime().toString());
            stmt.setString(4, trip.getArrivalTime().toString());
            stmt.setInt(5, trip.isBonusTrip() ? 1 : 0);
            stmt.setString(6, trip.getCreatedAt().toString());

            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    int id = keys.getInt(1);
                    return new Trip(id, trip.getUserId(), trip.getRouteId(), trip.getDepartureTime(), trip.getArrivalTime(), trip.isBonusTrip(), trip.getCreatedAt());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return trip;
    }

    private Trip updateTrip(Trip trip) {
        String sql = """
            UPDATE trips
            SET user_id = ?, route_id = ?, departure_time = ?, arrival_time = ?, bonus_trip = ?
            WHERE id = ?
            """;

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, trip.getUserId());
            stmt.setInt(2, trip.getRouteId());
            stmt.setString(3, trip.getDepartureTime().toString());
            stmt.setString(4, trip.getArrivalTime().toString());
            stmt.setInt(5, trip.isBonusTrip() ? 1 : 0);
            stmt.setInt(6, trip.getTripId());

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return trip;
    }

    @Override
    public List<Trip> findTripByUserId(int userId) {
        List<Trip> list = new ArrayList<>();
        String sql = "SELECT * FROM trips WHERE user_id = ?";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapTrip(rs));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<Trip> findAllTrips() {
        List<Trip> list = new ArrayList<>();
        String sql = "SELECT * FROM trips";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(mapTrip(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    private Trip mapTrip(ResultSet rs) throws Exception {
        int id = rs.getInt("id");
        int userId = rs.getInt("user_id");
        int routeId = rs.getInt("route_id");
        LocalDateTime departure = LocalDateTime.parse(rs.getString("departure_time"));
        LocalDateTime arrival = LocalDateTime.parse(rs.getString("arrival_time"));
        boolean bonus = rs.getInt("bonus_trip") == 1;
        LocalDateTime createdAt = LocalDateTime.parse(rs.getString("created_at"));

        return new Trip(id, userId, routeId, departure, arrival, bonus, createdAt);
    }

}
