package dev.morazzer.cookies.entities.websocket.packets;

import dev.morazzer.cookies.entities.websocket.Packet;
import dev.morazzer.cookies.entities.websocket.PacketSerializer;
import java.io.IOException;

/**
 * Used to sync the secrets of a specific room between clients.
 */
public class DungeonUpdateRoomSecrets implements Packet<DungeonUpdateRoomSecrets> {

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
