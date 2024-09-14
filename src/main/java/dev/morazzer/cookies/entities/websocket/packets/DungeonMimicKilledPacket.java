package dev.morazzer.cookies.entities.websocket.packets;

import dev.morazzer.cookies.entities.websocket.Packet;
import dev.morazzer.cookies.entities.websocket.PacketSerializer;

/**
 * Informs about the unaliving of mimic.
 */
public class DungeonMimicKilledPacket implements Packet<DungeonMimicKilledPacket> {

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
