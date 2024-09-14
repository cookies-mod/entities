package dev.morazzer.cookies.entities.websocket.packets;

import dev.morazzer.cookies.entities.websocket.Packet;
import dev.morazzer.cookies.entities.websocket.PacketSerializer;

/**
 * Empty packet to disconnect the client.
 */
public class DisconnectPacket implements Packet<DisconnectPacket> {

    public DisconnectPacket() {

    }

    public DisconnectPacket(PacketSerializer serializer) {

    }

    @Override
    public void serialize(PacketSerializer serializer) {

    }
}
