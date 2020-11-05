package com.restdeveloper.app.ws.shared;

public final class WebSocketMessageConstants {
    public static final String PAIRED_WITH_NEW_CHANNEL = "NEWSUB:";
    public static final String POLL_ENDED = "ENDSUB";
    public static final String POLL_UPDATE = "UPDATE";
    public static final String NOT_PAIRED = "NOT_PAIRED";
    public static final String NOT_REGISTERED = "NOT_REGISTERED";

    public static final char SEPARATOR = ((char) 007);

    private WebSocketMessageConstants() {
    }
}
