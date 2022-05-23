package ru.spbstu.hsai.userservice.server.manager;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import ru.spbstu.hsai.userservice.server.entity.User;

public class UserManager {

    private UserDao userDao;
    
    
    //TODO add logging
    public User createUser(String name, String email) {
        String id = UUID.randomUUID().toString();
        User userToCreate = new User();
        userToCreate.setId(id);
        userToCreate.setEmail(email);
        userToCreate.setCreationDate(new Date().getTime());
        System.out.println();
        return userDao.createUser(userToCreate);
    }
    
    public List<User> getUsers(){
        return userDao.getUsers();
    }

}
