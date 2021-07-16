package io.muzoo.ooc.webapp.basic.security;

import io.muzoo.ooc.webapp.basic.config.ConfigProperties;
import io.muzoo.ooc.webapp.basic.config.ConfigurationLoader;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.SQLException;

public class DataBaseConnectionService {

    private static DataBaseConnectionService service;

    public Connection getConnection() {
        try {
            ConfigProperties configProperties = ConfigurationLoader.load();
            if (configProperties == null) {
                throw new RuntimeException("Unable to read the config.properties");
            }
            String jdbcDriver =  configProperties.getDatabaseDriverClassName();
            String jdbcURL =  configProperties.getDatabaseConnectionUrl();
            String username = configProperties.getDatabaseUsername();
            String password = configProperties.getDatabasePassword();
            Class.forName(jdbcDriver);

            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            return connection;

        } catch (SQLException | ClassNotFoundException e) {
            return null;
        }
    }

    public static DataBaseConnectionService getInstance(){
        if(service==null){
            service = new DataBaseConnectionService();
        }
        return service;
    }

//    public static void main(String[] args) {
//        try  {
//            Connection connection = ds.getConnection();
//            String sql = "INSERT INTO tbl_user(username,password,display_name) VALUE(?,?,?);";
//            PreparedStatement ps = connection.prepareStatement(sql);
//            ps.setString(1,"my_username");
//            ps.setString(2,"my_password");
//            ps.setString(3,"my_display_name");
//            ps.executeUpdate();
//            connection.commit();
//        }catch(SQLException e){
//            e.printStackTrace();
//        }
//    }


//    private final HikariDataSource ds;
//
//    private static DataBaseConnectionService service;
//    public DataBaseConnectionService(){
//        ds = new HikariDataSource();
//        ds.setMaximumPoolSize(20);
//
//        ConfigProperties configProperties = ConfigurationLoader.load();
//        if(configProperties == null){
//            throw new RuntimeException("Unable to read config.properties,.");
//        }
//        ds.setDriverClassName( configProperties.getDatabaseDriverClassName());
//        ds.setJdbcUrl(configProperties.getDatabaseConnectionUrl());
//        ds.addDataSourceProperty("user", configProperties.getDatabaseUsername());
//        ds.addDataSourceProperty("password", configProperties.getDatabasePassword());
//        ds.setAutoCommit(false);
//    }
//    public Connection getConnection() throws SQLException{
//        return ds.getConnection();
//    }
}