package com.peyto.aiengine.example.go;

import com.peyto.aiengine.example.go.model.*;

import java.io.*;
import java.net.Socket;
import java.nio.ByteOrder;

public final class RemoteProcessClient implements Closeable {
    private static final int BUFFER_SIZE_BYTES = 1 << 20;
    private static final ByteOrder PROTOCOL_BYTE_ORDER = ByteOrder.LITTLE_ENDIAN;
    private static final int INTEGER_SIZE_BYTES = Integer.SIZE / Byte.SIZE;
    private static final int LONG_SIZE_BYTES = Long.SIZE / Byte.SIZE;
    public static final int PLAYERS_NUMBER = 2;
    public static final int PROTOCOL_VERSION = 1;

    private final Socket socket;
    private final ObjectOutputStream objectOutputStream;
    private final ObjectInputStream objectInputStreams;

    public RemoteProcessClient(String host, int port) throws IOException {
        socket = new Socket(host, port);
        socket.setSendBufferSize(BUFFER_SIZE_BYTES);
        socket.setReceiveBufferSize(BUFFER_SIZE_BYTES);
        socket.setTcpNoDelay(true);

        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectInputStreams = new ObjectInputStream(socket.getInputStream());
    }

    public void writeToken(String token) throws IOException {
        objectOutputStream.writeObject(MessageType.AUTHENTICATION_TOKEN);
        objectOutputStream.writeObject(token);
        flush();
    }

    public void writeProtocolVersion() throws IOException {
        objectOutputStream.writeObject(MessageType.PROTOCOL_VERSION);
        objectOutputStream.writeObject(new Integer(PROTOCOL_VERSION));
        flush();
    }

    public int readTeamSize() throws IOException, ClassNotFoundException {
        ensureMessageType(readMessageType(), MessageType.TEAM_SIZE);
        return (Integer) objectInputStreams.readObject();
    }

    public GameConstants readGameConstants() throws IOException, ClassNotFoundException {
        ensureMessageType(readMessageType(), MessageType.GAME_CONSTANTS);
        return (GameConstants) objectInputStreams.readObject();
    }

    public PlayerContext readPlayerContext() throws IOException, ClassNotFoundException {
        MessageType messageType = readMessageType();
        if (messageType == MessageType.GAME_OVER) {
            return null;
        }

        ensureMessageType(messageType, MessageType.PLAYER_CONTEXT);
        int playerIndex = (Integer) objectInputStreams.readObject();
        Board board = (Board) objectInputStreams.readObject();

        PlayerContext pc = new PlayerContext(playerIndex, board);
        return pc;
    }

    public void writeMove(Move move) throws IOException {
        objectOutputStream.writeObject(MessageType.MOVES);

        if (move == null) {
            objectOutputStream.writeObject(false);
        } else {
            objectOutputStream.writeObject(true);

            // todo write new turn coordinates
            objectOutputStream.writeObject(move);
        }

        flush();
    }

    @Override
    public void close() throws IOException {
        socket.close();
    }

    private static void ensureMessageType(MessageType actualType, MessageType expectedType) {
        if (actualType != expectedType) {
            throw new IllegalArgumentException(String.format(
                    "Received wrong message [actual=%s, expected=%s].", actualType, expectedType
            ));
        }
    }

    private MessageType readMessageType() throws IOException, ClassNotFoundException {
        return (MessageType) objectInputStreams.readObject();
    }

    private void flush() throws IOException {
        objectOutputStream.flush();
    }

    private enum MessageType {
        UNKNOWN,
        GAME_OVER,
        AUTHENTICATION_TOKEN,
        TEAM_SIZE,
        PROTOCOL_VERSION,
        GAME_CONSTANTS,
        PLAYER_CONTEXT,
        MOVES
    }
}
