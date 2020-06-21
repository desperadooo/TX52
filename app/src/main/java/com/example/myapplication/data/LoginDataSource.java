package com.example.myapplication.data;

import com.example.myapplication.data.model.LoggedInUser;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.security.auth.login.LoginException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {
    String s = "";
    public Result<LoggedInUser> login(String username, String password) {

        try {
            Thread t1=new Thread(){
                public void run(){
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.1.47/tx52?useUnicode=true&serverTimezone=UTC","root","");
                        Statement stmt = conn.createStatement();
                        ResultSet rs = stmt.executeQuery("Select * from user where email='"+username+"';");
                        while(rs.next()){
                            s=rs.getString("password");
                            break;
                        }
                    } catch (ClassNotFoundException | SQLException e) {
                        e.printStackTrace();
                    }
                    }};
            t1.start();
            t1.join();
            if(s.equals(password)){

                LoggedInUser fakeUser =
                        new LoggedInUser(
                                username,
                                username.split("@")[0]);
                return new Result.Success<>(fakeUser);
            }
            return new Result.Error(new LoginException(""));
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }

    }

    public void logout() {
        // TODO: revoke authentication
    }
}
