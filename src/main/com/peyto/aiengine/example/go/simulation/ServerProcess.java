package com.peyto.aiengine.example.go.simulation;

import com.peyto.aiengine.example.go.GoStrategy;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerProcess {

    private static final int BUFFER_SIZE_BYTES = 1 << 20;

    private final int clientsExpected;
    private final ServerSocket serverSocket;
    private final List<RpcCaller> clients = new ArrayList<>();

    public ServerProcess(int port, int clientsExpected) throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.setReceiveBufferSize(BUFFER_SIZE_BYTES);
        this.clientsExpected = clientsExpected;
    }

    public void waitClientsToConnect() {
        int clientsConnected = 0;
        while (clientsConnected < clientsExpected) {
            try {
                Socket socket = serverSocket.accept();
                clients.add(new RpcCaller(socket));
                clientsConnected++;
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
            }
        }
        System.out.println("INFO: clients connected");
    }

    public GoStrategy getClient(int i) {
        return clients.get(i);
    }
}
