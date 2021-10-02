package sk.spedry.weebcollector.clienthandler;

import com.google.gson.Gson;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.PrintWriter;

public class ClientMessageSender {

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Getter
    private final PrintWriter out;

    public ClientMessageSender(PrintWriter out) {
        this.out = out;
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public <T> void sendMessage(T object) {
        try {
            String message = new Gson().toJson(object);
            if (message == null) {
                throw new Exception("Couldn't transform object into json text");
            }
            out.println(message);
        } catch (Exception e) {
            logger.error(e);
        }
    }
}
