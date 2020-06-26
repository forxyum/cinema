package hu.alkfejl.dao;

import at.favre.lib.crypto.bcrypt.BCrypt;
import hu.alkfejl.config.DBConfig;
import hu.alkfejl.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImp implements UserDAO {
    private static final String CONN_STR = DBConfig.DB_CONN_STR;
    private static final String CREATE_USER = "CREATE TABLE IF NOT EXISTS User(" +
            "username text primary key," +
            "password text not null" +
            ");";
    private static final String SELECT_ALL_USERNAMES = "SELECT username FROM User;";
    private static final String SELECT_ALL_USERS = "SELECT * FROM User";
    private static final String REGISTER = "INSERT INTO User VALUES (?,?);";
    private static final String LOGIN = "SELECT * FROM User WHERE username=?;";

    public UserDAOImp() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        init();
    }

    private void init() {
        try (Connection conn = DriverManager.getConnection(CONN_STR); Statement st = conn.createStatement()) {
            st.executeUpdate(CREATE_USER);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> listAllUsers() {
        List<User> res = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(CONN_STR);
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(SELECT_ALL_USERNAMES)
        ) {
            while (rs.next()) {
                res.add(new User(
                        rs.getString(1),
                        rs.getString(2)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
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
    public void register(User user) {
        try (Connection conn = DriverManager.getConnection(CONN_STR); PreparedStatement ps = conn.prepareStatement(REGISTER)) {
            String newPass = BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray());
            user.setPassword(newPass);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User login(String username, String password) {
        try (Connection conn = DriverManager.getConnection(CONN_STR); PreparedStatement ps = conn.prepareStatement(LOGIN)) {
            ps.setString(1,username);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                String hashed = rs.getString("password");

                BCrypt.Result res = BCrypt.verifyer().verify(password.toCharArray(),hashed);
                if(res.verified){
                    User user = new User(
                            rs.getString("username"),
                            rs.getString("password")
                    );
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
