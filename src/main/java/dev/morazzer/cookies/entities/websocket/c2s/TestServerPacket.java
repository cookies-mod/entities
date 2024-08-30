package dev.morazzer.cookies.entities.websocket.c2s;

import dev.morazzer.cookies.entities.websocket.CrossServer;
import dev.morazzer.cookies.entities.websocket.Packet;
import dev.morazzer.cookies.entities.websocket.PacketSerializer;
import java.awt.TextArea;
import java.io.IOException;

public class TestServerPacket implements Packet<TestServerPacket>, CrossServer {

    public final String text;

    public TestServerPacket(PacketSerializer serializer) throws IOException {
        this.text = serializer.readString();
    }

    public TestServerPacket(String text) {
        this.text = text;
    }

    @Override
    public void serialize(PacketSerializer serializer) {
        serializer.writeString(text);
    }
}
