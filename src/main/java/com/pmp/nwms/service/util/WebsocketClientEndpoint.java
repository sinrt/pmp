package com.pmp.nwms.service.util;

import java.net.URI;
import java.util.Timer;
import java.util.TimerTask;
import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

/**
 * ChatServer Client
 *
 * @author Jiji_Sasidharan
 */
@ClientEndpoint
public class WebsocketClientEndpoint {

    private static boolean opened = false;
    Session userSession = null;
    private MessageHandler messageHandler;

    public WebsocketClientEndpoint() {

        try {

            URI uri = URI.create("ws://192.168.1.14:8443/call");
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, uri);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    /**
     * Callback hook for Connection open events.
     *
     * @param userSession the userSession which is opened.
     */
    @OnOpen
    public void onOpen(Session userSession) {
        opened = true;
        System.out.println("opening websocket");
        this.userSession = userSession;
    }

    /**
     * Callback hook for Connection close events.
     *
     * @param userSession the userSession which is getting closed.
     * @param reason      the reason for connection close
     */
    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        System.out.println("closing websocket");
        this.userSession = null;
    }

    /**
     * Callback hook for Message Events. This method will be invoked when a client send a message.
     *
     * @param message The text message
     */
    @OnMessage
    public void onMessage(String message) {
        if (this.messageHandler != null) {
            this.messageHandler.handleMessage(message);
        }
    }

    /**
     * register message handler
     *
     * @param msgHandler
     */
    public void addMessageHandler(MessageHandler msgHandler) {
        this.messageHandler = msgHandler;
    }

    /**
     * Send a message.
     *
     * @param message
     */
    public void sendMessage(String message) {

        if (!isOpened()) {

            Timer timer = new Timer();

            timer.schedule(new TimerTask() {
                @Override
                public void run() {

                    if (isOpened()) {

                        userSession.getAsyncRemote().sendText(message);
                        timer.cancel();
                    }
                }
            }, 0, 1000);
        } else {
            this.userSession.getAsyncRemote().sendText(message);
        }


    }

    /**
     * Message handler.
     *
     * @author Jiji_Sasidharan
     */
    public static interface MessageHandler {

        public void handleMessage(String message);
    }

    public static boolean isOpened() {
        return opened;
    }


}
