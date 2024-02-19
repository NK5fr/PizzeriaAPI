package util;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DS {
    
    public Connection connection() {
        try{
            Properties p = new Properties();
            InputStream in = getClass().getResourceAsStream("../../res/config.prop");
            p.load(in);
            in.close();
            Class.forName(p.getProperty("driver"));
            String url = p.getProperty("url");
            String nom = p.getProperty("login");
            String mdp = p.getProperty("password");
            return DriverManager.getConnection(url, nom, mdp);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static Connection getConnection(){
        return new DS().connection();
    }
    
    
}
