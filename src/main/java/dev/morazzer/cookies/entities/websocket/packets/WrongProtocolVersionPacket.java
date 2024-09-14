package dev.morazzer.cookies.entities.websocket.packets;

import dev.morazzer.cookies.entities.websocket.Packet;
import dev.morazzer.cookies.entities.websocket.PacketSerializer;
import java.io.IOException;

/**
 * Send when the client has a wrong protocol version, this will also inform about the current version, normally this shouldn't happen.
 */
public class WrongProtocolVersionPacket implements Packet<WrongProtocolVersionPacket> {

    public final int serverVersion;
    public final int clientVersion;
    public final boolean clientOutdated;

    public WrongProtocolVersionPacket(int serverVersion, int clientVersion, boolean clientOutdated) {
        this.serverVersion = serverVersion;
        this.clientVersion = clientVersion;
        this.clientOutdated = clientOutdated;
    }

    public WrongProtocolVersionPacket(PacketSerializer serializer) throws IOException {
        this.serverVersion = serializer.readInt();
        this.clientVersion = serializer.readInt();
        this.clientOutdated = serializer.readBoolean();
    }

    @Override
    public void serialize(PacketSerializer serializer) {
        serializer.writeInt(serverVersion);
        serializer.writeInt(clientVersion);
        serializer.writeBoolean(clientOutdated);
    }
}
