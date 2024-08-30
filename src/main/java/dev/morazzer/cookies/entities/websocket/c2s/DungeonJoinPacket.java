package dev.morazzer.cookies.entities.websocket.c2s;

import dev.morazzer.cookies.entities.websocket.CrossServer;
import dev.morazzer.cookies.entities.websocket.Packet;
import dev.morazzer.cookies.entities.websocket.PacketSerializer;
import java.io.IOException;
import java.util.UUID;

public class DungeonJoinPacket implements Packet<DungeonJoinPacket> {

    private final String server;
    private final UUID partyLeader;

    public DungeonJoinPacket(PacketSerializer buffer) throws IOException {
        this.server = buffer.readString();
        this.partyLeader = buffer.readUUID();
    }

    public DungeonJoinPacket(String server, UUID partyLeader) {
        this.server = server;
        this.partyLeader = partyLeader;
    }

    @Override
    public void serialize(PacketSerializer packetSerializer) {
        packetSerializer.writeString(this.server);
        packetSerializer.writeUUID(this.partyLeader);
    }

    public String getServer() {
        return server;
    }

    public UUID getPartyLeader() {
        return partyLeader;
    }
}
