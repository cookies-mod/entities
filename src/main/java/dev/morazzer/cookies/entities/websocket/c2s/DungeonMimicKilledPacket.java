package dev.morazzer.cookies.entities.websocket.c2s;

import dev.morazzer.cookies.entities.websocket.CrossServer;
import dev.morazzer.cookies.entities.websocket.Packet;
import dev.morazzer.cookies.entities.websocket.PacketSerializer;

public class DungeonMimicKilledPacket implements Packet<DungeonMimicKilledPacket>, CrossServer {

    public DungeonMimicKilledPacket(PacketSerializer serializer) {
        // EMPTY PACKET
    }

    public DungeonMimicKilledPacket() {
        // EMPTY PACKET
    }

    @Override
    public void serialize(PacketSerializer serializer) {
        // EMPTY PACKET
    }
}
