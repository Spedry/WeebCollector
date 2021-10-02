package sk.spedry.weebcollector.clienthandler;

import lombok.Getter;

import java.io.IOException;
import java.net.Socket;

public class ClientSocketCloser {
    @Getter
    private final Socket socket;

    public ClientSocketCloser(Socket socket) {
        this.socket = socket;
    }

    public void shutDownSocket() throws IOException {
        socket.shutdownOutput();
    }

    public void closeSocket() throws IOException {
        socket.close();
    }

}
