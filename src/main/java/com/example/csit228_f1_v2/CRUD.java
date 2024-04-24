package com.example.csit228_f1_v2;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CRUD {
    public static final String URL = "jdbc:mysql://localhost:3306/dbmolt";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "";

    static Connection getConnection() {
        Connection c = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return c;
    }

    public int getUserID(String username) {
        String query = "SELECT userID FROM tblusers WHERE username = ?";
        try (Connection c = getConnection();
             PreparedStatement statement = c.prepareStatement(query)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("userID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }


    public void createTable1() {
        String query = "CREATE TABLE IF NOT EXISTS tblusers (" +
                "userID INT PRIMARY KEY AUTO_INCREMENT," +
                "username VARCHAR(255) NOT NULL," +
                "password VARCHAR(255) NOT NULL)";
        try (Connection c = getConnection()) {
            Statement statement = c.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTable2() {
        String query = "CREATE TABLE IF NOT EXISTS tblcats (" +
                "catID INT PRIMARY KEY AUTO_INCREMENT," +
                "userID INT," + // Added userID as foreign key
                "catName VARCHAR(255) NOT NULL," +
                "catImage VARCHAR(255) NOT NULL," +
                "FOREIGN KEY (userID) REFERENCES tblusers(userID))";

        try (Connection c = getConnection()) {
            Statement statement = c.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean insertData(String username, String password) {
        boolean inserted = false;
        Connection connection = null;

        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            if (usernameExists(connection, username)) {
                return false;
            }

            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO tblusers (username, password) VALUES (?, ?)"
            )) {
                statement.setString(1, username);
                statement.setString(2, password);
                int num = statement.executeUpdate();
                if (num != 0) inserted = true;
            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            } catch (SQLException closeEx) {
                closeEx.printStackTrace();
            }
        }

        return inserted;
    }

    public boolean usernameExists(Connection connection, String username) {
        boolean exists = false;

        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT COUNT(*) FROM tblusers WHERE username = ?"
        )) {
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                exists = (count > 0);
            }
        } catch (SQLException e) {
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

            while (present.next()) {
                if (present.getString("username").equals(username) &&
                        present.getString("password").equals(password) && present.getString("password") != null) {
                    check = true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return check;
    }
    public boolean usernameExists(String username) {
        boolean exists = false;

        try (Connection c = getConnection();
             PreparedStatement statement = c.prepareStatement(
                     "SELECT COUNT(*) FROM tblusers WHERE username = ?"
             )) {
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                exists = (count > 0);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exists;
    }


    public boolean readDataUsername(String username) {
        boolean check = false;
        String dbusername, dbPassword;
        try (Connection c = getConnection();
             PreparedStatement statement = c.prepareStatement(
                     "SELECT * FROM tblusers"
             )) {
            ResultSet present = statement.executeQuery();

            while (present.next()) {
                if (present.getString("username").equals(username) && present.getString("username") != null) {
                    check = true;
                }
            }

        } catch (SQLException e) {
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

            while (present.next()) {
                if (present.getString("password").equals(password) && present.getString("password") != null) {
                    check = true;
                }
            }

        } catch (SQLException e) {
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
            ResultSet res = statement.getResultSet();
            if (rowsUpdated != 0) updated = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updated;
    }


    public boolean deleteData(String username) {
        boolean deleted = false;
        try (Connection c = getConnection();
             PreparedStatement statement = c.prepareStatement(
                     "DELETE FROM tblusers where username = ?"
             )) {

            statement.setString(1, username);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted != 0) deleted = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deleted;
    }

    public boolean catExists(String catName) {
        boolean exists = false;

        try (Connection c = getConnection();
             PreparedStatement statement = c.prepareStatement(
                     "SELECT COUNT(*) FROM tblcats WHERE catName = ?"
             )) {
            statement.setString(1, catName);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                exists = (count > 0);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exists;
    }

    public boolean createCat(String catName, String catImage, int sessionID) {
        if (catExists(catName)) {
            return false;
        }

        boolean inserted = false;

        try (Connection c = getConnection();
             PreparedStatement statement = c.prepareStatement(
                     "INSERT INTO tblcats (userID, catName, catImage) VALUES (?, ?, ?)"
             )) {
            statement.setString(1, Integer.toString(sessionID));
            statement.setString(2, catName);
            statement.setString(3, catImage);
            int num = statement.executeUpdate();
            if (num != 0) inserted = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return inserted;
    }

    public List<Map<String, String>> getCatData(int sessionID) {
        List<Map<String, String>> catData = new ArrayList<>();

        try (Connection c = getConnection();
             PreparedStatement statement = c.prepareStatement(
                     "SELECT catName, catImage FROM tblcats WHERE userID = ?"
             )) {
            statement.setInt(1, sessionID);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Map<String, String> catRow = new HashMap<>();
                catRow.put("catName", rs.getString("catName"));
                catRow.put("catImage", rs.getString("catImage"));
                catData.add(catRow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return catData;
    }

    public boolean updateCatData(String oldCatName) {
        try (Connection c = getConnection();
             PreparedStatement statement = c.prepareStatement(
                     "UPDATE tblcats SET catName = ? WHERE catName = ?"
             )) {
            statement.setString(1, "Jay");
            statement.setString(2, oldCatName);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    public void deleteCatRow(Map<String, String> catRow) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM tblcats WHERE catName = ? LIMIT 1")) {

            statement.setString(1, catRow.get("catName"));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
