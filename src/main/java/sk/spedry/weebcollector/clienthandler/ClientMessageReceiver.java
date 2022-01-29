package sk.spedry.weebcollector.clienthandler;

import com.google.gson.Gson;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sk.spedry.weebcollector.app.controllers.util.WCMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.LinkedBlockingDeque;

public class ClientMessageReceiver extends ClientSocketCloser implements Runnable{

    private final Logger logger = LogManager.getLogger(this.getClass());

    private BufferedReader br;
    @Getter
    private LinkedBlockingDeque<WCMessage> msgQueue;
    public ClientMessageReceiver(Socket socket) {
        super(socket);
        try {
            this.br = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            this.msgQueue = new LinkedBlockingDeque<WCMessage>();
        } catch (IOException e) {
            logger.error("Get input stream", e);
        }
    }

    @Override
    public void run() {
        try {
            String data;
            logger.debug("Starting receiving messages");
            while ((data = br.readLine()) != null) {
                msgQueue.add(new Gson().fromJson(data, WCMessage.class));
            }
            logger.debug("Receiving messages ended");

            closeSocket();
            logger.info("Was socket connected: " + getSocket().isConnected());
            logger.info("Was socket closed: " + getSocket().isClosed());

        } catch (IOException e) {
            logger.error("Read line", e);
        }
    }
}
