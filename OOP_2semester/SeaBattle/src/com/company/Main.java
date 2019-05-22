package com.company;

import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;

public class Main {
    public static void main(String[] args) {
        MutablePicoContainer pico = new DefaultPicoContainer();
        pico.addComponent(RandomBotPlayer.class);
        //pico.addComponent(CleverBotPlayer.class);
        //pico.addComponent(RealPlayer.class);
        pico.addComponent(GameServer.class);
        GameServer server = (GameServer) pico.getComponent(GameServer.class);
        server.Game(0);  //type of output means whose field will not be shown
    }
}
