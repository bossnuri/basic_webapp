package io.muzoo.ooc.webapp.basic.security;

import com.zaxxer.hikari.HikariDataSource;
import io.muzoo.ooc.webapp.basic.config.ConfigProperties;
import io.muzoo.ooc.webapp.basic.config.ConfigurationLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataBaseConnectionService {
    private final HikariDataSource ds;
    public DataBaseConnectionService(){
        ds = new HikariDataSource();
        ds.setMaximumPoolSize(20);

        ConfigProperties configProperties = ConfigurationLoader.load();
        if(configProperties == null){
            throw new RuntimeException("Unable to read config.properties,.");
        }
        ds.setDriverClassName( configProperties.getDatabaseDriverClassName());
        ds.setJdbcUrl(configProperties.getDatabaseConnectionUrl());
        ds.addDataSourceProperty("user", configProperties.getDatabaseUsername());
        ds.addDataSourceProperty("password", configProperties.getDatabasePassword());
        ds.setAutoCommit(false);
    }
    public Connection getConnection() throws SQLException{
        return ds.getConnection();
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
}