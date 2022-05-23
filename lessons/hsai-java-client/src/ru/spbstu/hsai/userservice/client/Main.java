package ru.spbstu.hsai.userservice.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ru.spbstu.hsai.userservice.common.dto.CreateUserRequest;
import ru.spbstu.hsai.userservice.common.dto.CreateUserResponse;
import ru.spbstu.hsai.userservice.common.dto.GeneralResponse;
import ru.spbstu.hsai.userservice.common.dto.GetUsersRequest;
import ru.spbstu.hsai.userservice.common.dto.GetUsersResponse;

public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int port = 8888;

        List<CompletableFuture> futures =  Stream.generate(() -> {
            return CompletableFuture.runAsync(() -> {
                try (Socket clientSocket = new Socket("127.0.0.1", port)) {
                    clientSocket.setKeepAlive(true);

                    CreateUserRequest userRequest = new CreateUserRequest();
                    userRequest.setEmail("test@test.test")
                            .setLogin("test.test")
                            .setName("Test Testov");
                    //Client request
                    ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
                    oos.writeObject(userRequest);
                    oos.flush();

                    //Server response
                    ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
                    GeneralResponse<CreateUserResponse> result = (GeneralResponse<CreateUserResponse>) ois.readObject();
                    System.out.println(result.getMessage());
                    System.out.println(result.getData().getUserId());

                } catch (IOException e) {
                    System.err.println(e.getMessage());
                } catch (ClassNotFoundException e) {
                    System.err.println(e.getMessage());
                }
            });
        }).limit(10).collect(Collectors.toList());
        
        CompletableFuture future = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        
        future.get();
        
        try (Socket clientSocket = new Socket("127.0.0.1", port)) {
            clientSocket.setKeepAlive(true);
            GetUsersRequest request = new GetUsersRequest();
            ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
            oos.writeObject(request);
            oos.flush();

            ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
            GeneralResponse<GetUsersResponse> response = (GeneralResponse<GetUsersResponse>) ois.readObject();

            response.getData().getUsers().forEach(user -> System.out.println(user.getId()));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }

    }
}
