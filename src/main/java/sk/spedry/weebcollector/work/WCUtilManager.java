package sk.spedry.weebcollector.work;

import sk.spedry.weebcollector.app.controllers.choiceboxitems.CodeTable;
import sk.spedry.weebcollector.app.controllers.util.WCMServer;

public class WCUtilManager {

    public CodeTable wcmServerToCodeTable(WCMServer wcmServer) {
        return new CodeTable(
                wcmServer.getId(),
                wcmServer.getServerName(),
                wcmServer.getServeChannelName()
        );
    }
}
