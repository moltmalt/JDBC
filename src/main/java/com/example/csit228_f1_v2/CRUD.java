package com.example.csit228_f1_v2;
import java.sql.*;

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
            System.out.println("SQL Exception HERE");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Class Exception");
            e.printStackTrace();
        }
        return c;
    }

    public boolean insertData(String username, String password){

        boolean inserted = false;

        // Check if the username already exists
        if (usernameExists(username)) {
            System.out.println("Username already exists!");
            return false;
        }

        try(Connection c = getConnection();
            PreparedStatement statement = c.prepareStatement(
                    "INSERT INTO tblusers (username, password) VALUES (?, ?)"
            )){
            statement.setString(1, username);
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

    // Method to check if username already exists
    public boolean usernameExists(String username) {
        boolean exists = false;

        try(Connection c = getConnection();
            PreparedStatement statement = c.prepareStatement(
                    "SELECT COUNT(*) FROM tblusers WHERE username = ?"
            )){
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                int count = rs.getInt(1);
                exists = (count > 0);
            }

        }catch (SQLException e){
            System.out.println("Exception in usernameExists");
            e.printStackTrace();
        }

        return exists;
    }


    public boolean readData(String username, String password) {
        boolean check = false;
        String dbusername, dbPassword;
        try (Connection c = getConnection();
             PreparedStatement statement = c.prepareStatement(
                     "SELECT * FROM tblusers"
             )) {
            ResultSet present = statement.executeQuery();

            while(present.next()){
                if(present.getString("username").equals(username) &&
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

    public boolean readDataUsername(String username) {
        boolean check = false;
        String dbusername, dbPassword;
        try (Connection c = getConnection();
             PreparedStatement statement = c.prepareStatement(
                     "SELECT * FROM tblusers"
             )) {
            ResultSet present = statement.executeQuery();

            while(present.next()){
                if(present.getString("username").equals(username) && present.getString("username") != null){
                    check = true;
                }
            }

        } catch (SQLException e) {
            System.out.println("Exception in InsertData");
            e.printStackTrace();
        }

        return check;
    }

    public boolean readDataPassword(String password) {
        boolean check = false;
        try (Connection c = getConnection();
             PreparedStatement statement = c.prepareStatement(
                     "SELECT * FROM tblusers"
             )) {
            ResultSet present = statement.executeQuery();

            while(present.next()){
                if(present.getString("password").equals(password) && present.getString("password") != null){
                    check = true;
                }
            }

        } catch (SQLException e) {
            System.out.println("Exception in InsertData");
            e.printStackTrace();
        }

        return check;
    }

    public boolean updateData(String username, String newPassword) {
        boolean updated = false;
        try (Connection c = getConnection();
             PreparedStatement statement = c.prepareStatement(
                     "UPDATE tblusers SET password = ? WHERE username = ? "
             )) {

            statement.setString(1, newPassword);
            statement.setString(2, username);
            int rowsUpdated = statement.executeUpdate();
            System.out.println("Rows updated: " + rowsUpdated);
            ResultSet res = statement.getResultSet();
            if(rowsUpdated != 0) updated = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updated;
    }



    public boolean deleteData(String username){
        boolean deleted = false;
        try(Connection c = getConnection();
            PreparedStatement statement = c.prepareStatement(
                    "DELETE FROM tblusers where username = ?"
            )){

            statement.setString(1, username);
            int rowsDeleted = statement.executeUpdate();
            System.out.println("Rows deleted: " + rowsDeleted);
            if(rowsDeleted != 0) deleted = true;
        }catch (SQLException e){
            System.out.println("Exception in DeleteData");
            e.printStackTrace();
        }
        return deleted;
    }

//    public void createCat(){
//        if (usernameExists(username)) {
//            System.out.println("Username already exists!");
//            return false;
//        }
//
//        try(Connection c = getConnection();
//            PreparedStatement statement = c.prepareStatement(
//                    "INSERT INTO tblusers (username, password) VALUES (?, ?)"
//            )){
//            statement.setString(1, username);
//            statement.setString(2, password);
//            int num = statement.executeUpdate();
//            System.out.println("Rows inserted: " + num);
//            if(num != 0) inserted = true;
//
//        }catch (SQLException e){
//            System.out.println("Exception in InsertData");
//            e.printStackTrace();
//        }
//
//        return inserted;
    }

