package dev.morazzer.cookies.entities.websocket.c2s;

import dev.morazzer.cookies.entities.websocket.CrossServer;
import dev.morazzer.cookies.entities.websocket.Packet;
import dev.morazzer.cookies.entities.websocket.PacketSerializer;

public class DungeonLeavePacket implements Packet<DungeonLeavePacket>, CrossServer {

    public DungeonLeavePacket(PacketSerializer serializer) {
        // EMPTY PACKET
    }

    public DungeonLeavePacket() {
        // EMPTY PACKET
    }

    @Override
    public void serialize(PacketSerializer serializer) {
        // EMPTY PACKET
    }
}
