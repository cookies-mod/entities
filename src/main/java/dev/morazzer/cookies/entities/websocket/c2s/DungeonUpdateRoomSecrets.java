package dev.morazzer.cookies.entities.websocket.c2s;

import dev.morazzer.cookies.entities.websocket.CrossServer;
import dev.morazzer.cookies.entities.websocket.Packet;
import dev.morazzer.cookies.entities.websocket.PacketSerializer;
import java.io.IOException;

public class DungeonUpdateRoomSecrets implements Packet<DungeonUpdateRoomSecrets>, CrossServer {

    public int roomMapX;
    public int roomMapY;
    public int collectedSecrets;
    public int maxSecrets;

    public DungeonUpdateRoomSecrets(PacketSerializer serializer) throws IOException {
        this.roomMapX = serializer.readInt();
        this.roomMapY = serializer.readInt();
        this.collectedSecrets = serializer.readInt();
        this.maxSecrets = serializer.readInt();
    }

    public DungeonUpdateRoomSecrets(int roomMapX, int roomMapY, int collectedSecrets, int maxSecrets) {
        this.roomMapX = roomMapX;
        this.roomMapY = roomMapY;
        this.collectedSecrets = collectedSecrets;
        this.maxSecrets = maxSecrets;
    }

    @Override
    public void serialize(PacketSerializer serializer) {
        serializer.writeInt(roomMapX);
        serializer.writeInt(roomMapY);
        serializer.writeInt(collectedSecrets);
        serializer.writeInt(maxSecrets);
    }
}
