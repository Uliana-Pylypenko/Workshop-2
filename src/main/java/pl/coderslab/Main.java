package pl.coderslab;

import pl.coderslab.entity.User;
import pl.coderslab.entity.UserDao;

public class Main {
    public static void main(String[] args) {

    /*User user1 = new User("user1@gmail.com", "user1", "password1");
    User user2 = new User("user2@gmail.com", "user2", "password2");
    User user3 = new User("user3@gmail.com", "user3", "password3");*/

    UserDao userDao = new UserDao();

    //userDao.create(user1);
    //userDao.create(user2);
    //userDao.create(user3);

    User userToUpdate = userDao.read(12);
    userToUpdate.setEmail("test@coderslab.pl");
    userToUpdate.setUserName("test");
    userToUpdate.setPassword("test");
    userDao.update(userToUpdate);

    userDao.delete(13);

    User[] all = userDao.findAll();
    for (User u : all) {
        System.out.println(u.getUserName());
    }




        }
    }
