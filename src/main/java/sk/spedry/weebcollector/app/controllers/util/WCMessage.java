package sk.spedry.weebcollector.app.controllers.util;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;

public class WCMessage {
    @Getter
    @Setter
    private String messageId;
    @Getter
    @Setter
    private String messageBody;

    public <T> WCMessage(String messageId, T object) {
        this.messageId = messageId;
        this.messageBody = new Gson().toJson(object);
    }

    public WCMessage(String messageId) {
        this.messageId = messageId;
    }
}
