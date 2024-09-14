package dev.morazzer.cookies.entities.websocket.packets;

import dev.morazzer.cookies.entities.websocket.Packet;
import dev.morazzer.cookies.entities.websocket.PacketSerializer;
import java.io.IOException;

/**
 * Used to sync the map state between clients.
 */
public class DungeonUpdateRoomIdPacket implements Packet<DungeonUpdateRoomIdPacket> {

    public int roomMapX;
    public int roomMapY;
    public String roomIdentifier;

    public DungeonUpdateRoomIdPacket(PacketSerializer serializer) throws IOException {
        this.roomMapX = serializer.readInt();
        this.roomMapY = serializer.readInt();
        this.roomIdentifier = serializer.readString();
    }

    public DungeonUpdateRoomIdPacket(int roomMapX, int roomMapY, String roomIdentifier) {
        this.roomMapX = roomMapX;
        this.roomMapY = roomMapY;
        this.roomIdentifier = roomIdentifier;
    }

    @Override
    public void serialize(PacketSerializer serializer) {
        serializer.writeInt(this.roomMapX);
        serializer.writeInt(this.roomMapY);
        serializer.writeString(this.roomIdentifier);
    }
}
