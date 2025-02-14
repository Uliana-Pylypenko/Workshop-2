package pl.coderslab.entity;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.DbUtil;

import java.sql.*;
import java.util.Arrays;
import java.util.Scanner;

public class UserDao {
    private static final String CREATE_USER_QUERY =
            "INSERT INTO users(username, email, password) VALUES (?, ?, ?)";

    public static User create(User user) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(CREATE_USER_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, hashPassword(user.getPassword()));
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1); // IT WAS LONG HERE
                user.setId(id);
            }
            System.out.println("User with id " + user.getId() + " successfully created");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public static final String READ_USER_QUERY = "SELECT * FROM users WHERE id = ?";

    public static User read(int userId) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(READ_USER_QUERY);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(userId);
                user.setUserName(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                return user;
            } else {
                System.out.println("User with id " + userId + " not found");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static final String UPDATE_USER_QUERY = "UPDATE users SET username = ?, email = ?, password = ? WHERE id = ?";

    public static void update(User user) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(UPDATE_USER_QUERY);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, hashPassword(user.getPassword()));
            statement.setInt(4, user.getId());
            statement.executeUpdate();
            System.out.println("User with id " + user.getId() + " successfully updated");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static final String DELETE_QUERY = "DELETE FROM users where id = ?";
    public static void delete(int userId) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(DELETE_QUERY);

            // add confirmation
            // create function to get all indexes in the database and check if useId is in there

            if (checkId(userId)) {
                try (Scanner scanner = new Scanner(System.in)) {
                    System.out.println("Delete user with id " + userId + "? (y/n)");
                    String input = scanner.nextLine();
                    while (!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n")) {
                        System.out.println("Option not found.");
                        input = scanner.nextLine();
                    }
                    if (input.equalsIgnoreCase("y")) {
                        statement.setInt(1, userId);
                        statement.executeUpdate();
                        System.out.println("User with id " + userId + " successfully deleted");
                    } else {
                        System.out.println("User with id " + userId + " is still in the table");
                    }
                }
            } else {
                System.out.println("User with id " + userId + " not found");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static User[] addToArray(User u, User[] users) {
        User[] tmpUsers = Arrays.copyOf(users, users.length + 1);
        tmpUsers[users.length] = u;
        return tmpUsers;
    }

    public static User[] findAll() {
        User[] allUsers = new User[0];
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM users");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
                user.setUserName(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                allUsers = addToArray(user, allUsers);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allUsers;
    }

    public static boolean checkId(int userId) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("SELECT id FROM users");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                if (id == userId) {
                    return true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }




    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }


}