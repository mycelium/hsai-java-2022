package ru.spbstu.hsai.userservice.server.dao;

import java.util.List;

import ru.spbstu.hsai.userservice.server.entity.User;
import ru.spbstu.hsai.userservice.server.manager.UserDao;

public class UserDaoSQLiteImpl implements UserDao {
    
    private String connectionString = "jdbc:sqlite:users.db";

    @Override
    public List<User> getUsers() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public User getUser(String id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteUser(String id) {
        // TODO Auto-generated method stub

    }

    @Override
    public User createUser(User user) {
        // TODO Auto-generated method stub
        return null;
    }

}
