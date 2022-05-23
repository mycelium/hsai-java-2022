package ru.spbstu.hsai.userservice.server.manager;

import java.util.List;

import ru.spbstu.hsai.userservice.server.entity.User;

public interface UserDao {
    
    public List<User> getUsers();
    public User getUser(String id);
    public void deleteUser(String id);
    public User createUser(User user);
    
}
