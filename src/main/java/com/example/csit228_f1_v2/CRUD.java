package com.example.csit228_f1_v2;

import java.sql.*;
import java.util.ArrayList;

public class CRUD {
    public static final String URL = "jdbc:mysql://localhost:3306/dbmolt";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "";
    static Connection getConnection(){
        Connection c = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection(URL,USERNAME, PASSWORD);
            System.out.println("DB Connection [SUCCESS]");
        }catch (SQLException e){
            System.out.println("SQL Exception");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Class Exception");
            e.printStackTrace();
        }
        return c;
    }

    public boolean insertData(String name, String password){

        boolean inserted = false;

        try(Connection c = getConnection();
            PreparedStatement statement = c.prepareStatement(
                    "INSERT INTO users (name, password) VALUES (?, ?)"
            )){
            statement.setString(1, name);
            statement.setString(2, password);
            int num = statement.executeUpdate();
            System.out.println("Rows inserted: " + num);
            if(num != 0) inserted = true;

        }catch (SQLException e){
            System.out.println("Exception in InsertData");
            e.printStackTrace();
        }

        return inserted;
    }

    public boolean readData(String name, String password) {
        boolean check = false;
        String dbName, dbPassword;
        try (Connection c = getConnection();
             PreparedStatement statement = c.prepareStatement(
                     "SELECT * FROM users"
             )) {
            ResultSet present = statement.executeQuery();

            while(present.next()){
                if(present.getString("name").equals(name) &&
                        present.getString("password").equals(password) && present.getString("password") != null){
                    check = true;
                }
            }

        } catch (SQLException e) {
            System.out.println("Exception in InsertData");
            e.printStackTrace();
        }

        return check;
    }

    public boolean readData(String name) {
        boolean check = false;
        String dbName, dbPassword;
        try (Connection c = getConnection();
             PreparedStatement statement = c.prepareStatement(
                     "SELECT * FROM users"
             )) {
            ResultSet present = statement.executeQuery();

            while(present.next()){
                if(present.getString("name").equals(name) && present.getString("name") != null){
                    check = true;
                }
            }

        } catch (SQLException e) {
            System.out.println("Exception in InsertData");
            e.printStackTrace();
        }

        return check;
    }

    public boolean updateData(String name, String newPassword) {
        boolean updated = false;
        try (Connection c = getConnection();
             PreparedStatement statement = c.prepareStatement(
                     "UPDATE users SET password = ? WHERE name = ? "
             )) {

            statement.setString(1, newPassword);
            statement.setString(2, name);
            int rowsUpdated = statement.executeUpdate();
            System.out.println("Rows updated: " + rowsUpdated);
            ResultSet res = statement.getResultSet();
            if(rowsUpdated != 0) updated = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updated;
    }



    public boolean deleteData(String name, String password){
        boolean deleted = false;
        try(Connection c = getConnection();
            PreparedStatement statement = c.prepareStatement(
                    "DELETE FROM users where name = ? and password = ?"
            )){

            statement.setString(1, name);
            statement.setString(2, password);
            int rowsDeleted = statement.executeUpdate();
            System.out.println("Rows deleted: " + rowsDeleted);
            if(rowsDeleted != 0) deleted = true;
        }catch (SQLException e){
            System.out.println("Exception in DeleteData");
            e.printStackTrace();
        }
        return deleted;
    }
}
