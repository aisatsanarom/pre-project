package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }
    @Override
    public void createUsersTable() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS User (`id` INT NOT NULL AUTO_INCREMENT,`name` VARCHAR(45) NOT NULL,`lastName` VARCHAR(45) NOT NULL,`age` INT NOT NULL, PRIMARY KEY (`id`));");
            System.out.println("Table was created");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void dropUsersTable() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS User");
            System.out.println("Table was dropped");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO User (name, lastName, age) VALUES (?,?,?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.execute();
            System.out.println("User was saved");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM User WHERE id=?");
            preparedStatement.setInt(1, (int) id);
            preparedStatement.executeUpdate();
            System.out.println("User was deleted");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM User");
            ResultSet res = preparedStatement.executeQuery();
            while (res.next()) {
                User user = new User(res.getString("name"), res.getString("LastName"),res.getByte("age"));
                user.setId(res.getLong("id"));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Here is the users");
        return userList;
    }
    @Override
    public void cleanUsersTable() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM User");
            System.out.println("All users were deleted from table");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

