package dev.morazzer.cookies.entities.websocket.packets;

import dev.morazzer.cookies.entities.misc.BackendVersion;
import dev.morazzer.cookies.entities.websocket.Packet;
import dev.morazzer.cookies.entities.websocket.PacketSerializer;
import java.io.IOException;

/**
 * Used to exchange current packet version, if not equal the client will be disconnected.
 *
 */
public class HandshakePacket implements Packet<HandshakePacket> {

    public final int packetVersion;

    public HandshakePacket() {
        this.packetVersion = BackendVersion.CURRENT_PACKET_VERSION;
    }

    public HandshakePacket(PacketSerializer packetSerializer) throws IOException {
        this.packetVersion = packetSerializer.readInt();
    }

    @Override
    public void serialize(PacketSerializer serializer) {
        serializer.writeInt(this.packetVersion);
    }
}
