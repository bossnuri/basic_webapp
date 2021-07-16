package io.muzoo.ooc.webapp.basic.security;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserService {

    public UserService() {

    }

    public void setDataBaseConnectionService(DataBaseConnectionService dataBaseConnectionService) {
        this.dataBaseConnectionService = dataBaseConnectionService;
    }

    private static UserService service = new UserService();


    public static UserService getInstance() {
        if (service == null) {
            service = new UserService();
            service.setDataBaseConnectionService(DataBaseConnectionService.getInstance());
        }
        return service;
    }

    private static final String INSET_USER_SQL = "INSERT INTO tbl_user(username,password,display_name) VALUE(?,?,?);";
    private static final String SELECT_USER_SQL = "SELECT * FROM tbl_user WHERE username = ?;";
    private static final String SELECT_ALL_USERS_SQL = "SELECT * FROM tbl_user;";
    private static final String DELETE_USER_SQL = "DELETE FROM tbl_user WHERE username = ?;";
    private static final String UPDATE_USER_SQL = "UPDATE tbl_user SET display_name = ? WHERE username = ?;";
    private static final String UPDATE_USER_PASSWORD_SQL = "UPDATE tbl_user SET password = ? WHERE username = ?;";
    private DataBaseConnectionService dataBaseConnectionService = new DataBaseConnectionService();

    public void createUser(String username, String password, String displayName) throws UserServiceException {
        try(Connection connection = dataBaseConnectionService.getConnection();
            PreparedStatement ps = connection.prepareStatement(INSET_USER_SQL);) {

            ps.setString(1, username);
            ps.setString(2, BCrypt.hashpw(password, BCrypt.gensalt()));
            ps.setString(3, displayName);
            ps.executeUpdate();
            connection.commit();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new UsernameNotUniqueException(String.format("Username %s has already been taken", username));
        } catch (SQLException throwables) {
            throw new UserServiceException(throwables.getMessage());
        }
    }

    public User findByUsername(String username) {
        try(Connection connection = dataBaseConnectionService.getConnection();
            PreparedStatement ps = connection.prepareStatement(SELECT_USER_SQL);) {

            ps.setString(1, username);
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            return new User(
                    resultSet.getLong("id"),
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    resultSet.getString("display_name")
            );
        } catch (SQLException e) {
            return null;
        }
    }

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try ( Connection connection = dataBaseConnectionService.getConnection();
              PreparedStatement ps = connection.prepareStatement(SELECT_ALL_USERS_SQL);){

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                users.add(new User(
                        resultSet.getLong("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("display_name")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return users;
    }

    public boolean deleteUserByName(String username) {
        try (
                Connection connection = dataBaseConnectionService.getConnection();
                PreparedStatement ps = connection.prepareStatement(DELETE_USER_SQL);
        ) {
            ps.setString(1, username);
            ps.executeUpdate();
            return true;
        } catch (SQLException throwables) {
            return false;
        }
    }

    public void updateUserByUsername(String username, String displayName) throws UserServiceException {
        try {
            Connection connection = dataBaseConnectionService.getConnection();
            PreparedStatement ps = connection.prepareStatement(UPDATE_USER_SQL);
            ps.setString(1, displayName);
            ps.setString(2, username);
            ps.executeUpdate();
            connection.setAutoCommit(false);
            connection.commit();
        } catch (SQLException throwables) {
            throw new UserServiceException(throwables.getMessage());
        }
    }
    public void changePassword(String username, String newPassword) throws UserServiceException {
        try {
            Connection connection = dataBaseConnectionService.getConnection();
            PreparedStatement ps = connection.prepareStatement(UPDATE_USER_PASSWORD_SQL);

            ps.setString(1, BCrypt.hashpw(newPassword, BCrypt.gensalt()));
            ps.setString(2, username);

            ps.executeUpdate();

            connection.setAutoCommit(false);
            connection.commit();
        } catch (SQLException throwables) {
            throw new UserServiceException(throwables.getMessage());
        }
    }


    public static void main(String[] args) throws UserServiceException {
        UserService userService = new UserService();
        userService.setDataBaseConnectionService(new DataBaseConnectionService());
        userService.createUser("bossnuri","boss112233","Boss");
    }

//    private Map<String, User> users = new HashMap<>();
//
//    {
//        users.put("gigadot", new User("gigadot", "12345"));
//        users.put("admin", new User("admin", "12345"));
//    }
//

//
//    public boolean checkIfUserExists(String username) {
//        return users.containsKey(username);
//    }


}
