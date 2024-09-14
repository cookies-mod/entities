package dev.morazzer.cookies.entities.websocket.packets;

import dev.morazzer.cookies.entities.websocket.Packet;
import dev.morazzer.cookies.entities.websocket.PacketSerializer;

/**
 * Informs the backend about a dungeon leave.
 */
public class DungeonLeavePacket implements Packet<DungeonLeavePacket> {

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
