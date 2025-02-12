package pl.coderslab;

import pl.coderslab.entity.User;
import pl.coderslab.entity.UserDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {

        User user = new User();
        user.setUserName("admin5");
        user.setPassword("admin5");
        user.setEmail("admin5@coderslab.pl");


        UserDao.create(user);
        System.out.println(user.getId());


        }
    }
