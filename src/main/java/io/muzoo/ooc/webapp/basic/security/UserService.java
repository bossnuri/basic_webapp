package io.muzoo.ooc.webapp.basic.security;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class UserService {

    public void setDataBaseConnectionService(DataBaseConnectionService dataBaseConnectionService) {
        this.dataBaseConnectionService = dataBaseConnectionService;
    }

    private static final String INSET_USER_SQL = "INSERT INTO tbl_user(username,password,display_name) VALUE(?,?,?);";
    private static final String SELECT_USER_SQL = "SELECT * FROM tbl_user WHERE username = ?;";
    private DataBaseConnectionService dataBaseConnectionService;

    public void createUser(String username,String password,String displayName) throws UserServiceException {
        try{
            Connection connection = dataBaseConnectionService.getConnection();
            PreparedStatement ps = connection.prepareStatement(INSET_USER_SQL);
            ps.setString(1,username);
            ps.setString(2,BCrypt.hashpw(password, BCrypt.gensalt()));
            ps.setString(3,displayName);
            ps.executeUpdate();
            connection.commit();
        }
        catch(SQLIntegrityConstraintViolationException e){
            throw new UsernameNotUniqueException(String.format("Username %s has already been taken" ,username));
        }
        catch(SQLException throwables) {
            throw new UserServiceException(throwables.getMessage());
        }
    }
    public User findByUsername(String username){
        try{
            Connection connection = dataBaseConnectionService.getConnection();
            PreparedStatement ps = connection.prepareStatement(SELECT_USER_SQL);
            ps.setString(1,username);
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            return new User(
                    resultSet.getLong("id"),
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    resultSet.getString("displayName")
            );
        }
        catch(SQLException throwables) {
            return null;
        }
    }

    public static void main(String[] args) {
        UserService userService = new UserService();
        userService.setDataBaseConnectionService(new DataBaseConnectionService());
        User user = userService.findByUsername("gigadot");
        System.out.println(user.getUsername());
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
