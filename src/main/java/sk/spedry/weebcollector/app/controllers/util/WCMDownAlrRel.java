package sk.spedry.weebcollector.app.controllers.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class WCMDownAlrRel {
    @Getter
    private boolean download;
    @Getter
    private int alreadyReleasedEp;
}
