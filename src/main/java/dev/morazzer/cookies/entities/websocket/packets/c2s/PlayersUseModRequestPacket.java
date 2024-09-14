package dev.morazzer.cookies.entities.websocket.packets.c2s;

import dev.morazzer.cookies.entities.websocket.Packet;
import dev.morazzer.cookies.entities.websocket.PacketSerializer;
import java.io.IOException;
import java.util.UUID;

/**
 * Request to check if players are using the mod or not.
 */
public class PlayersUseModRequestPacket implements Packet<PlayersUseModRequestPacket> {

    public final UUID[] uuids;

    public PlayersUseModRequestPacket(PacketSerializer packetSerializer) throws IOException {
        final UUID[] uuids = new UUID[packetSerializer.readInt()];
        for (int i = 0; i < uuids.length; i++) {
            uuids[i] = packetSerializer.readUUID();
        }
        this.uuids = uuids;
    }

    public PlayersUseModRequestPacket(UUID... uuids) {
        this.uuids = uuids;
    }

    @Override
    public void serialize(PacketSerializer serializer) {
        serializer.writeInt(uuids.length);
        for (UUID uuid : uuids) {
            serializer.writeUUID(uuid);
        }
    }
}
