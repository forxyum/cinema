package hu.alkfejl.dao;

import hu.alkfejl.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CinemaDAOImp implements CinemaDAO {
    private static final String CONN_STR = "jdbc:sqlite:cinema.db";
    private static final String CREATE_MOVIE = "CREATE TABLE IF NOT EXISTS Movie(" +
            "id integer primary key autoincrement," +
            "title text," +
            "length integer," +
            "rating text," +
            "director text," +
            "description text," +
            "cover text);";
    private static final String CREATE_ROOM = "CREATE TABLE IF NOT EXISTS Room(" +
            "id integer primary key autoincrement," +
            "rows integer not null," +
            "columns integer not null);";
    private static final String CREATE_SCREENING = "CREATE TABLE IF NOT EXISTS Screening(" +
            "id integer primary key autoincrement," +
            "movieId integer not null," +
            "datetime text not null," +
            "room integer not null," +
            "foreign key(movieId) references Movie(id) ON DELETE CASCADE ON UPDATE CASCADE," +
            "foreign key (room) references Room(id)ON DELETE CASCADE ON UPDATE CASCADE);";
    private static final String CREATE_ACTOR = "CREATE TABLE IF NOT EXISTS Actor(" +
            "movieId not null," +
            "name text not null," +
            "primary key(movieId,name));";
    private static final String CREATE_RESERVATION = "CREATE TABLE IF NOT EXISTS Reservation(" +
            "id integer primary key autoincrement," +
            "screeningId integer not null," +
            "username text not null," +
            "foreign key(screeningId) references Screening(id) ON DELETE CASCADE ON UPDATE CASCADE," +
            "foreign key(username) references  User(username) ON DELETE CASCADE  ON UPDATE CASCADE);";
    private static final String CREATE_SEAT = "CREATE TABLE IF NOT EXISTS Seat(" +
            "resId integer not null," +
            "number integer not null," +
            "primary key(resId,number)," +
            "foreign key(resId) references Reservation(id) ON DELETE CASCADE ON UPDATE CASCADE);";
    private static final String CREATE_USER = "CREATE TABLE IF NOT EXISTS User(" +
            "username text primary key," +
            "password text not null" +
            ");";
    private static final String FOREIGN_KEYS_ON = "PRAGMA foreign_keys = ON;";
    private static final String INSERT_MOVIE = "INSERT INTO Movie VALUES(null,?,?,?,?,?,?);";
    private static final String DELETE_MOVIE = "DELETE FROM Movie where id = ?;";
    private static final String UPDATE_MOVIE = "UPDATE Movie SET title=?,length=?,rating=?,director=?,description=?,cover=? where id =?;";
    private static final String SELECT_ALL_MOVIES = "SELECT * FROM Movie;";
    private static final String SELECT_ALL_SCREENINGS = "SELECT id, movieId, date(datetime), time(datetime),room FROM Screening;";
    private static final String INSERT_SCREENING = "INSERT INTO Screening VALUES(null,?,?,?);";
    private static final String DELETE_SCREENING = "DELETE FROM Screening WHERE id=?;";
    private static final String UPDATE_SCREENING  = "UPDATE Screening SET movieId=?,datetime=?,room=? WHERE id=?";
    private static final String INSERT_ROOM = "INSERT INTO Room VALUES(null,?,?)";
    private static final String DELETE_ROOM = "DELETE FROM Room WHERE id=?;";
    private static final String UPDATE_ROOM = "UPDATE Room SET rows = ?,columns = ? WHERE id=?;";
    private static final String SELECT_ALL_ROOMS = "SELECT * FROM Room;";
    private static final String INSERT_RESERVATION = "INSERT INTO Reservation VALUES (null,?,?)";
    private static final String DELETE_RESERVATION = "DELETE FROM Reservation where id=?;";
    private static final String UPDATE_RESERVATION = "UPDATE Reservation SET screeningId = ?, username = ? WHERE id=?;";
    private static final String SELECT_ALL_RESERVATIONS = "SELECT * FROM Reservation;";
    private static final String INSERT_ACTOR = "INSERT INTO Actor VALUES(?,?);";
    private static final String LAST_MOVIE_ID = "SELECT seq FROM sqlite_sequence WHERE name=\"Movie\";";
    private static final String LAST_RESERVATION_ID = "SELECT seq FROM sqlite_sequence WHERE name=\"Reservation\";";
    private static final String SELECT_ACTORS_BY_MOVIE_ID = "SELECT name FROM Actor WHERE movieId=?;";
    private static final String DELETE_ACTOR = "DELETE FROM Actor WHERE movieId=? AND name=?;";
    private static final String MOVIE_TITLE_BY_RES_ID = "SELECT title FROM Movie,Reservation,Screening WHERE Screening.id=screeningId AND Movie.id=movieId AND Reservation.id=?;";
    private static final String SCREENING_DATETIME_BY_RES_ID = "SELECT datetime FROM Reservation,Screening WHERE Screening.id=screeningId AND Reservation.id =?;";
    private static final String SEATS_BY_RES_ID = "SELECT number FROM Seat,Reservation WHERE Reservation.id=resId AND Reservation.id=?;";
    private static final String ROOM_BY_RES_ID ="SELECT room FROM Reservation,Screening WHERE Reservation.screeningId=Screening.id AND Reservation.id=?;";
    private static final String SCREENING_BY_MOVIE_ID = "SELECT * FROM Screening WHERE movieId=?;";
    private static final String SEATS_BY_SCREENING_ID = "SELECT number FROM Seat,Reservation,Screening WHERE screeningId=Screening.id AND Reservation.id=resId AND screeningId=?;";
    private static final String DIMENSIONS_BY_SCREENING_ID = "SELECT rows, columns FROM Room, Screening WHERE Screening.room=Room.id AND Screening.id =?;";
    private static final String SELECT_ALL_USERNAMES = "SELECT username FROM User;";
    private static final String INSERT_SEAT = "INSERT INTO Seat VALUES(?,?);";
    private static final String DELETE_SEAT = "DELETE FROM Seat WHERE resId=? AND number=?;";
    private static final String MOVIE_TITLE_BY_SCREENING_ID = "SELECT title FROM Movie,Screening WHERE Movie.id = Screening.movieId AND Screening.id=?;";

    public CinemaDAOImp() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        initTables();
    }

    public void initTables() {
        try (Connection conn = DriverManager.getConnection(CONN_STR); Statement st = conn.createStatement()) {
            st.executeUpdate(FOREIGN_KEYS_ON);
            st.executeUpdate(CREATE_MOVIE);
            st.executeUpdate(CREATE_ROOM);
            st.executeUpdate(CREATE_ACTOR);
            st.executeUpdate(CREATE_SCREENING);
            st.executeUpdate(CREATE_RESERVATION);
            st.executeUpdate(CREATE_SEAT);
            st.executeUpdate(CREATE_USER);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean addMovie(Movie m) {
        try (Connection conn = DriverManager.getConnection(CONN_STR); PreparedStatement ps = conn.prepareStatement(INSERT_MOVIE)) {
            conn.createStatement().executeUpdate(FOREIGN_KEYS_ON);
            ps.setString(1, m.getTitle());
            ps.setInt(2, m.getLength());
            ps.setString(3, m.getRating());
            ps.setString(4, m.getDirector());
            ps.setString(5, m.getDescription());
            ps.setString(6, m.getCover());
            int res = ps.executeUpdate();
            if (res == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteMovie(Movie m) {
        try (Connection conn = DriverManager.getConnection(CONN_STR); PreparedStatement ps = conn.prepareStatement(DELETE_MOVIE)) {
            conn.createStatement().executeUpdate(FOREIGN_KEYS_ON);
            ps.setInt(1, m.getId());
            int res = ps.executeUpdate();
            if (res == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateMovie(int id, Movie m) {
        try (Connection conn = DriverManager.getConnection(CONN_STR); PreparedStatement ps = conn.prepareStatement(UPDATE_MOVIE)) {
            conn.createStatement().executeUpdate(FOREIGN_KEYS_ON);
            ps.setString(1, m.getTitle());
            ps.setInt(2, m.getLength());
            ps.setString(3, m.getRating());
            ps.setString(4, m.getDirector());
            ps.setString(5, m.getDescription());
            ps.setString(6, m.getCover());
            ps.setInt(7, id);
            int res = ps.executeUpdate();
            if (res == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    //TODO: TEST IT MOFO
    @Override
    public List<Movie> advSearchMovies(String title, Integer length, String rating, String director) {
        List<Movie> res = new ArrayList<Movie>();
        String advSearch = "SELECT * FROM Movie WHERE ";
        String options = "";
        if (title != null)
            options += "title = " + title + ",";
        if (length != null)
            options += "length = " + length + ",";
        if (rating != null)
            options += "rating = " + rating + ",";
        if (director != null)
            options += "director = " + director + ",";
        if (options.length() == 0) {
            return res;
        }
        else {
            advSearch += options.substring(0, options.length() - 1);
        }
        try (Connection conn = DriverManager.getConnection(CONN_STR);
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(advSearch)
        ) {

            while (rs.next()) {
                Movie m = new Movie(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7)
                );
                res.add(m);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    @Override
    public List<Movie> listAllMovies() {
        List<Movie> res = new ArrayList<Movie>();
        try (Connection conn = DriverManager.getConnection(CONN_STR);
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(SELECT_ALL_MOVIES)
        ) {
            while (rs.next()) {
                Movie m = new Movie(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7)
                );
                res.add(m);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    @Override
    public Integer getLastMovieId() {
        try (Connection conn = DriverManager.getConnection(CONN_STR);
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(LAST_MOVIE_ID)
        ) {
                return rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Screening> listScreenings(int movieId) {
        List<Screening> res = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(CONN_STR);
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(SELECT_ALL_SCREENINGS)
        ) {
            while (rs.next()) {
                Screening s = new Screening(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5)
                );
                res.add(s);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    @Override
    public boolean addScreening(Screening s) {
        try (Connection conn = DriverManager.getConnection(CONN_STR); PreparedStatement ps = conn.prepareStatement(INSERT_SCREENING)) {
            conn.createStatement().executeUpdate(FOREIGN_KEYS_ON);
            ps.setInt(1, s.getMovieId());
            ps.setString(2, s.getDate() + "T" + s.getTime());
            ps.setInt(3, s.getRoom());
            int res = ps.executeUpdate();
            if (res == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteScreening(Screening s) {
        try (Connection conn = DriverManager.getConnection(CONN_STR);
             PreparedStatement ps = conn.prepareStatement(DELETE_SCREENING)) {
            conn.createStatement().executeUpdate(FOREIGN_KEYS_ON);
            ps.setInt(1, s.getId());
            int res = ps.executeUpdate();
            if (res == 1) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateScreening(int id, Screening s) {
        try (Connection conn = DriverManager.getConnection(CONN_STR); PreparedStatement ps = conn.prepareStatement(UPDATE_SCREENING)) {
            conn.createStatement().executeUpdate(FOREIGN_KEYS_ON);
            ps.setInt(1, s.getMovieId());
            ps.setString(2, s.getDate() + "T" + s.getTime());
            ps.setInt(3,s.getRoom());
            ps.setInt(4,id);
            int res = ps.executeUpdate();
            if (res == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Screening> listAllScreenings() {
        List<Screening> res = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(CONN_STR);
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(SELECT_ALL_SCREENINGS)
        ) {
            while (rs.next()) {
                Screening s = new Screening(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4).substring(0,5),
                        rs.getInt(5)
                );
                res.add(s);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    @Override
    public boolean addRoom(Room r) {
        try (Connection conn = DriverManager.getConnection(CONN_STR); PreparedStatement ps = conn.prepareStatement(INSERT_ROOM)) {
            ps.setInt(1, r.getRows());
            ps.setInt(2, r.getColumns());
            int res = ps.executeUpdate();
            if (res == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteRoom(Room r) {
        try (Connection conn = DriverManager.getConnection(CONN_STR);
             PreparedStatement ps = conn.prepareStatement(DELETE_ROOM)) {
            conn.createStatement().executeUpdate(FOREIGN_KEYS_ON);
            ps.setInt(1, r.getId());
            int res = ps.executeUpdate();
            if (res == 1) {
                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateRoom(int id, Room r) {
        try (Connection conn = DriverManager.getConnection(CONN_STR); PreparedStatement ps = conn.prepareStatement(UPDATE_ROOM)) {
            conn.createStatement().executeUpdate(FOREIGN_KEYS_ON);
            ps.setInt(1, r.getRows());
            ps.setInt(2, r.getColumns());
            ps.setInt(3,id);
            int res = ps.executeUpdate();
            if (res == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Room> listAllRooms() {
        List<Room> res = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(CONN_STR);
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(SELECT_ALL_ROOMS)
        ) {
            while (rs.next()) {
                Room r = new Room(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3)
                );
                res.add(r);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    @Override
    public boolean addReservation(Reservation r) {
        try (Connection conn = DriverManager.getConnection(CONN_STR); PreparedStatement ps = conn.prepareStatement(INSERT_RESERVATION)) {
            conn.createStatement().executeUpdate(FOREIGN_KEYS_ON);
            ps.setInt(1, r.getScreeningId());
            ps.setString(2, r.getUsername());
            int res = ps.executeUpdate();
            if (res == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteReservation(Reservation r) {
        try (Connection conn = DriverManager.getConnection(CONN_STR);
             PreparedStatement ps = conn.prepareStatement(DELETE_RESERVATION)) {
            conn.createStatement().executeUpdate(FOREIGN_KEYS_ON);
            ps.setInt(1, r.getId());
            int res = ps.executeUpdate();
            if (res == 1) {
                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateReservation(int id, Reservation r) {
        try (Connection conn = DriverManager.getConnection(CONN_STR); PreparedStatement ps = conn.prepareStatement(UPDATE_RESERVATION)) {
            conn.createStatement().executeUpdate(FOREIGN_KEYS_ON);
            ps.setInt(1, r.getScreeningId());
            ps.setString(2, r.getUsername());
            int res = ps.executeUpdate();
            if (res == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Integer getLastReservationId() {
        try (Connection conn = DriverManager.getConnection(CONN_STR);
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(LAST_RESERVATION_ID)
        ) {
            return rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Reservation> listAllReservations() {
        List<Reservation> res = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(CONN_STR);
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(SELECT_ALL_RESERVATIONS)
        ) {
            while (rs.next()) {
                Reservation r = new Reservation(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3)
                );
                res.add(r);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    @Override
    public List<Reservation> advSearchReservations(String username, Integer screeningId) {
        List<Reservation> res = new ArrayList<>();
        String advSearch = "SELECT * FROM Reservation WHERE ";
        String options = "";
        if (username != null)
            options += "username = " + username + ",";
        if (screeningId != null)
            options += "screeningId = " + screeningId + ",";
        if (options.length() == 0) {
            return res;
        }
        advSearch += options.substring(0, options.length() - 1);
        try (Connection conn = DriverManager.getConnection(CONN_STR);
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(advSearch)
        ) {

            while (rs.next()) {
                Reservation r = new Reservation(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3)
                );
                res.add(r);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    @Override
    public boolean addActor(Actor a) {
        try (Connection conn = DriverManager.getConnection(CONN_STR); PreparedStatement ps = conn.prepareStatement(INSERT_ACTOR)) {
            ps.setInt(1, a.getMovieId());
            ps.setString(2, a.getName());
            int res = ps.executeUpdate();
            if (res == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteActor(Actor a) {
        try (Connection conn = DriverManager.getConnection(CONN_STR);
             PreparedStatement ps = conn.prepareStatement(DELETE_ACTOR)) {
            ps.setInt(1, a.getMovieId());
            ps.setString(2,a.getName());
            int res = ps.executeUpdate();
            if (res == 1) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<User> listAllUsers() {
        //TODO: body
        return null;
    }

    @Override
    public List<String> listAllUsernames() {
        List<String> res = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(CONN_STR);
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(SELECT_ALL_USERNAMES)
        ) {
            while (rs.next()) {
                res.add(rs.getString(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }


    @Override
    public List<String> listActorNamesOfMovie(Integer movieId){
        List<String> res = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(CONN_STR);
             PreparedStatement ps = conn.prepareStatement(SELECT_ACTORS_BY_MOVIE_ID)
        ) {
            ps.setInt(1,movieId);
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    res.add(rs.getString(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public String getMovieTitleByReservationId(Integer reservationId) {
        String res = null;
        try (Connection conn = DriverManager.getConnection(CONN_STR);
             PreparedStatement ps = conn.prepareStatement(MOVIE_TITLE_BY_RES_ID)
        ) {
            ps.setInt(1,reservationId);
            try(ResultSet rs = ps.executeQuery()){
                res = rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public String getMovieTitleByScreeningId(Integer screeningId) {
        String res = null;
        try (Connection conn = DriverManager.getConnection(CONN_STR);
             PreparedStatement ps = conn.prepareStatement(MOVIE_TITLE_BY_SCREENING_ID)
        ) {
            ps.setInt(1,screeningId);
            try(ResultSet rs = ps.executeQuery()){
                res = rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }


    @Override
    public String getScreeningDateTimeByReservationId(Integer reservationId) {
        String res = null;
        try (Connection conn = DriverManager.getConnection(CONN_STR);
             PreparedStatement ps = conn.prepareStatement(SCREENING_DATETIME_BY_RES_ID)
        ) {
            ps.setInt(1,reservationId);
            try(ResultSet rs = ps.executeQuery()){
                res = rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public List<Integer> getSeatsByReservationId(Integer reservationId) {
        List<Integer> res = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(CONN_STR);
             PreparedStatement ps = conn.prepareStatement(SEATS_BY_RES_ID)
        ) {
            ps.setInt(1,reservationId);
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()) {
                    res.add(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public Integer getRoomByReservationId(Integer reservationId) {
        Integer res = null;
        try (Connection conn = DriverManager.getConnection(CONN_STR);
             PreparedStatement ps = conn.prepareStatement(ROOM_BY_RES_ID);
        ) {
            ps.setInt(1,reservationId);
            try(ResultSet rs = ps.executeQuery()){
                res = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public List<Screening> getScreeningsByMovieId(Integer movieId) {
        List<Screening> res = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(CONN_STR);
             PreparedStatement ps = conn.prepareStatement(SCREENING_BY_MOVIE_ID)
        ) {
            ps.setInt(1,movieId);
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()) {
                    res.add(new Screening(
                            rs.getInt(1),
                            rs.getInt(2),
                            rs.getString(3).split("T")[0],
                            rs.getString(3).split("T")[1],
                            rs.getInt(4)
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public List<Integer> getSeatsByScreeningId(Integer screeningId) {
        List<Integer> res = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(CONN_STR);
             PreparedStatement ps = conn.prepareStatement(SEATS_BY_SCREENING_ID)
        ) {
            ps.setInt(1,screeningId);
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()) {
                    res.add(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public List<Integer> getRoomDimensionsByScreeningId(Integer screeningId) {
        List<Integer> res = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(CONN_STR);
             PreparedStatement ps = conn.prepareStatement(DIMENSIONS_BY_SCREENING_ID)
        ) {
            ps.setInt(1,screeningId);
            try(ResultSet rs = ps.executeQuery()){
                    res.add(rs.getInt(1));
                    res.add(rs.getInt(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public boolean addSeat(Seat s) {
        try (Connection conn = DriverManager.getConnection(CONN_STR); PreparedStatement ps = conn.prepareStatement(INSERT_SEAT)) {
            ps.setInt(1, s.getResId());
            ps.setInt(2, s.getNumber());
            int res = ps.executeUpdate();
            if (res == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteSeat(Seat s) {
        try (Connection conn = DriverManager.getConnection(CONN_STR);
             PreparedStatement ps = conn.prepareStatement(DELETE_SEAT)) {
            ps.setInt(1, s.getResId());
            ps.setInt(2,s.getNumber());
            int res = ps.executeUpdate();
            if (res == 1) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
