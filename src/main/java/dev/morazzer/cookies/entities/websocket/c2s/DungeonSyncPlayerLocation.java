package dev.morazzer.cookies.entities.websocket.c2s;

import dev.morazzer.cookies.entities.websocket.CrossServer;
import dev.morazzer.cookies.entities.websocket.Packet;
import dev.morazzer.cookies.entities.websocket.PacketSerializer;
import java.io.IOException;

public class DungeonSyncPlayerLocation implements Packet<DungeonSyncPlayerLocation>, CrossServer {

    public String username;
    public int x;
    public int y;
    public int rotation;
    public long timestamp;

    public DungeonSyncPlayerLocation(PacketSerializer packetSerializer) throws IOException {
        this.username = packetSerializer.readString();
        this.x = packetSerializer.readInt();
        this.y = packetSerializer.readInt();
        this.rotation = packetSerializer.readInt();
        this.timestamp = packetSerializer.readLong();
    }

    public DungeonSyncPlayerLocation(String username, int x, int y, int rotation) {
        this.username = username;
        this.x = x;
        this.y = y;
        this.rotation = rotation;
        this.timestamp = System.currentTimeMillis();
    }

    @Override
    public void serialize(PacketSerializer serializer) {
        serializer.writeString(username);
        serializer.writeInt(x);
        serializer.writeInt(y);
        serializer.writeInt(rotation);
        serializer.writeLong(timestamp);
    }
}
