package com.pmp.nwms.web.rest.servlet.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user1 on 12/24/2017.
 */
public class CommandTransmitter {

    private String command;
    private List<SimagooKeyValue> simagooKeyValues =new ArrayList<>();

    public CommandTransmitter() {
    }

    public CommandTransmitter(String command, List<SimagooKeyValue> simagooKeyValues) {
        this.command = command;
        this.simagooKeyValues = simagooKeyValues;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public List<SimagooKeyValue> getSimagooKeyValues() {
        return simagooKeyValues;
    }

    public void setSimagooKeyValues(List<SimagooKeyValue> simagooKeyValues) {
        this.simagooKeyValues = simagooKeyValues;
    }
}
