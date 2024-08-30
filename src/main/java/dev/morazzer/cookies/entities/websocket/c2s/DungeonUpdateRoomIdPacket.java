package dev.morazzer.cookies.entities.websocket.c2s;

import dev.morazzer.cookies.entities.websocket.CrossServer;
import dev.morazzer.cookies.entities.websocket.Packet;
import dev.morazzer.cookies.entities.websocket.PacketSerializer;
import java.io.IOException;

public class DungeonUpdateRoomIdPacket implements Packet<DungeonUpdateRoomIdPacket>, CrossServer {

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
