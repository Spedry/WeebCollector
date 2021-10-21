package sk.spedry.weebcollector.clienthandler;

import com.google.gson.Gson;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sk.spedry.weebcollector.app.controllers.util.WCMessage;
import sk.spedry.weebcollector.work.WCWorkPlace;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.LinkedBlockingDeque;

public class ClientMessageHandler implements Runnable {

    private final Logger logger = LogManager.getLogger(this.getClass());

    private final String hostname;
    private final int port;

    {
        hostname = "localhost";
        port = 50000;
    }
    @Getter
    private final Socket socket;
    @Getter
    //TODO
    // CHANGE TO FINAL AFTER IT'S DONE
    private PrintWriter out;
    private LinkedBlockingDeque<WCMessage> wcMsgQueue;

    private final WCWorkPlace wcWork;

    public ClientMessageHandler(){
        logger.trace("Creating client message handler");
        this.socket = initSocket();
        if (socket != null) {
            this.out = initPrintWriter();
            this.wcMsgQueue = initMsgQueue();
        }
        wcWork = new WCWorkPlace();
    }

    private Socket initSocket() {
        try {
            return new Socket(hostname, port);
        } catch (UnknownHostException e) {
            logger.error("Error during creating socket: unknown host", e);
        } catch (IOException e) {
            logger.error("Error during creating socket", e);
        }
        return null;
    }

    private PrintWriter initPrintWriter() {
        try {
            return new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);
        } catch (IOException e) {
            logger.error("Error when accessing socket socket: getOutputStream", e);
        }
        return null;
    }

    private LinkedBlockingDeque<WCMessage> initMsgQueue() {
        try {
            ClientMessageReceiver messageReceiver = new ClientMessageReceiver(socket);
            Thread clientsSideReceiver = new Thread(messageReceiver);
            LinkedBlockingDeque<WCMessage> wcMsgQueue = messageReceiver.getMsgQueue();
            clientsSideReceiver.setDaemon(true);
            clientsSideReceiver.start();
            return wcMsgQueue;
        } catch (Exception e) {
            logger.error("Error when initializing msgQueue", e);
        }
        return null;
    }

    @Override
    public void run() {
        loop: while (true) {
            try {
                WCMessage msg = wcMsgQueue.take();
                logger.debug("Received id: " + msg.getMessageId());
                switch (msg.getMessageId()) {
                    case "addNewAnimeEntry":
                    case "getAnimeList":
                        wcWork.addNewAnimeEntry(msg);
                        break;
                    case "setSetup":
                        wcWork.setSetup(msg);
                        break;

                    default:
                        throw new Exception("Unknown id: " + msg.getMessageId() + ": " + msg.getMessageBody());
                } // SWITCH


            } catch (Exception e) {
                logger.error(e);
            }
        }
    }
}
