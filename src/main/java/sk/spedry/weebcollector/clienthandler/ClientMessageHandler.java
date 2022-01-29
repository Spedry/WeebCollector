package sk.spedry.weebcollector.clienthandler;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sk.spedry.weebcollector.app.controllers.util.WCMessage;
import sk.spedry.weebcollector.properties.Configuration;
import sk.spedry.weebcollector.work.WCWorkPlace;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.LinkedBlockingDeque;

public class ClientMessageHandler implements Runnable {

    private final Logger logger = LogManager.getLogger(this.getClass());

    private final String serverIpAddress;
    private final int port;

    {
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

        serverIpAddress = new Configuration().getProperty("serverIpAddress");
        if (serverIpAddress == null) {
            logger.error("Firs insert your server ip address into config.properties");
        }
        this.socket = initSocket();
        if (socket != null) {
            this.out = initPrintWriter();
            this.wcMsgQueue = initMsgQueue();
        }
        wcWork = new WCWorkPlace();
    }

    private Socket initSocket() {
        try {
            return new Socket(serverIpAddress, port);
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
        if (socket != null)
            loop: while (true) {
                try {
                    WCMessage wcMessage = wcMsgQueue.take();
                    if (!wcMessage.getMessageId().equals("setProgress"))
                        logger.debug("Received id: " + wcMessage.getMessageId());
                    switch (wcMessage.getMessageId()) {
                        case "getAnimeList":
                        case "editAnimeEntry":
                        case "addNewAnimeEntry":
                            wcWork.addNewAnimeEntry(wcMessage);
                            break;
                        case "getSetup":
                        case "setSetup":
                            wcWork.setSetup(wcMessage);
                            break;
                        case "setProgress":
                            wcWork.setDownloadingAnimeProgressBar(wcMessage);
                            break;
                        case "setDownloadingAnimeName":
                            wcWork.setDownloadingAnimeName(wcMessage);
                            break;
                        case "setAnimeToOpen":
                            wcWork.setAnimeToOpen(wcMessage);
                            break;


                        case "turningOff":
                            logger.debug(wcMessage.getMessageBody());
                            break loop;

                        default:
                            throw new Exception("Unknown id: " + wcMessage.getMessageId() + ": " + wcMessage.getMessageBody());
                    } // SWITCH


                } catch (Exception e) {
                    logger.error(e);
                }
            }
        else
            logger.warn("Socket is null");
    }
}
