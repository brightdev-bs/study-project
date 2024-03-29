package org.example;

import org.example.calculator.HttpResponse;
import org.example.calculator.domain.Calculator;
import org.example.calculator.domain.PositiveNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CustomWebApplicationServer {

    private final int port;

    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    private static final Logger logger = LoggerFactory.getLogger(CustomWebApplicationServer.class);

    public CustomWebApplicationServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("[CustomWebApplicationServer] started {} port ", port);

            Socket clientScoket;
            logger.info("[CustomWebapplicationServer] waiting for client");

            while((clientScoket = serverSocket.accept()) != null) {
                logger.info("[CustomWebapplicationServer] client connected");
                executorService.execute(new ClientRequestHandler(clientScoket));

            }
        }
    }
}
