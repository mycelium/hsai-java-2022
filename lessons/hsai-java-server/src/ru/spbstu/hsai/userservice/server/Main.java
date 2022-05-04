package ru.spbstu.hsai.userservice.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

import ru.spbstu.hsai.userservice.common.dto.CreateUserRequest;
import ru.spbstu.hsai.userservice.common.dto.CreateUserResponse;
import ru.spbstu.hsai.userservice.common.dto.GeneralResponse;
import ru.spbstu.hsai.userservice.common.dto.GetUsersRequest;
import ru.spbstu.hsai.userservice.common.dto.GetUsersResponse;
import ru.spbstu.hsai.userservice.common.entity.User;

public class Main {

    private static List<User> users = new ArrayList<>();

    public static void main(String[] args) {
        boolean isAlive = true;
        int port = 8888;

        ExecutorService pool = Executors.newFixedThreadPool(4);

        //        users = getUsers();
        //
        //        new Thread(() -> {
        //            if (!users.isEmpty()) {
        //                persistUsers();
        //            }
        //            try {
        //                Thread.currentThread().sleep(5000);
        //            } catch (InterruptedException e) {
        //                e.printStackTrace();
        //            }
        //        }).start();
        
        
        //TODO check if database exists - do not drop
//        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:users.db")) {
//            Statement statement = connection.createStatement();
//            statement.setQueryTimeout(10);
//            statement.executeUpdate("drop table if exists users");
//            statement.executeUpdate("create table users (id string, login string, name string, email string)");
//        } catch (SQLException e1) {
//            System.err.println(e1.getMessage());
//        }

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (isAlive) {
                Socket client = serverSocket.accept();
                //Client request
                pool.execute(() -> {
                    try {
                        ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
                        Object request = ois.readObject();

                        if (request instanceof CreateUserRequest) {
                            CreateUserRequest userRequest = (CreateUserRequest) request;
                            String userId = createUser(userRequest).getId();
                            GeneralResponse<CreateUserResponse> response = new GeneralResponse<>();
                            CreateUserResponse userResponse = new CreateUserResponse();
                            userResponse.setUserId(userId);
                            response.setCode(0);
                            response.setMessage("Succcess");
                            response.setData(userResponse);
                            //Server response
                            ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
                            oos.writeObject(response);
                            oos.flush();
                        } else if (request instanceof GetUsersRequest) {
                            GeneralResponse<GetUsersResponse> response = new GeneralResponse<>();
                            GetUsersResponse resp = new GetUsersResponse();
                            List<User> persistedUsers = getUsers();
                            resp.setUsers(persistedUsers);
                            response.setData(resp);
                            response.setCode(0);
                            response.setMessage("Success");
                            ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
                            oos.writeObject(response);
                            oos.flush();
                        }
                    } catch (java.io.IOException e) {
                        System.err.println(e.getMessage());
                    } catch (ClassNotFoundException e) {
                        System.err.println(e.getMessage());
                    }
                });
            }
        } catch (java.net.SocketException e2) {
            System.err.println(e2.getMessage());
        } catch (java.io.IOException e) {
            System.err.println(e.getMessage());
        }

    }

    private static User createUser(CreateUserRequest userRequest) {
        System.out.println(Thread.currentThread().getName() + " : " + userRequest.getName());
        String id = UUID.randomUUID().toString();
        User user = new User(id, userRequest.getLogin(), userRequest.getEmail(), userRequest.getName());
        persistUser(user);
        return user;
    }

    private static void persistUser(User user) {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:users.db")) {
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(10);
            statement.executeUpdate(String.format("insert into users (id, login, name, email) values ('%s', '%s', '%s', '%s')", user.getId(),
                    user.getLogin(), user.getName(), user.getEmail()));
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    private static synchronized void persistUsers() {
        Gson gson = new Gson();
        String jsonUsers = gson.toJson(users);
        try {
            Files.writeString(Paths.get("users.json"), jsonUsers, StandardOpenOption.CREATE);
        } catch (IOException e) {
            System.err.println("Can't persist users");
            System.err.println(e.getMessage());
        }
    }

    private static synchronized List<User> getUsers() {
        //        Gson gson = new Gson();
        //        try {
        //            User[] users = gson.fromJson(Files.readString(Paths.get("users.json")), User[].class);
        //            return Arrays.asList(users);
        //        } catch (JsonSyntaxException | IOException e) {
        //            e.printStackTrace();
        //        }
        //        return new LinkedList<>();
        List<User> result = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:users.db")) {
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(10);
            ResultSet rs = statement.executeQuery("select id, login, name, email from users");
            while (rs.next()) {
                String id = rs.getString("id");
                String login = rs.getString("login");
                String name  = rs.getString("name");
                String email = rs.getString("email");
                User user = new User(id, login, email, name);
                result.add(user);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return result;
    }
}
