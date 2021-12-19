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
    @Getter
    @Setter
    private String additionalData;

    public <T> WCMessage(String messageId, T object) {
        this.messageId = messageId;
        this.messageBody = new Gson().toJson(object);
    }

    public <T, F> WCMessage(String messageId, T object, F additionalData) {
        this.messageId = messageId;
        this.messageBody = new Gson().toJson(object);
        this.additionalData = new Gson().toJson(additionalData);
    }

    public WCMessage(String messageId) {
        this.messageId = messageId;
    }
}
