package dev.morazzer.cookies.entities.websocket.packets;

import dev.morazzer.cookies.entities.websocket.Packet;
import dev.morazzer.cookies.entities.websocket.PacketSerializer;
import java.io.IOException;

/**
 * Test packet to debug api functionality, sending this will cause a disconnect in prod.
 */
public class TestServerPacket implements Packet<TestServerPacket> {

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
