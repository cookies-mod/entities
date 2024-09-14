package dev.morazzer.cookies.entities.websocket.packets.s2c;

import dev.morazzer.cookies.entities.websocket.Packet;
import dev.morazzer.cookies.entities.websocket.PacketSerializer;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Response that informs about if players use the mod or not.
 */
public class PlayersUseModResponsePacket implements Packet<PlayersUseModResponsePacket> {

    public final Map<UUID, Boolean> map;

    public PlayersUseModResponsePacket(Map<UUID, Boolean> booleans) {
        this.map = Collections.unmodifiableMap(booleans);
    }

    public PlayersUseModResponsePacket(PacketSerializer serializer) throws IOException {
        final int i1 = serializer.readInt();
        final HashMap<UUID, Boolean> map = new HashMap<>(i1);
        for (int i = 0; i < i1; i++) {
            map.put(serializer.readUUID(), serializer.readBoolean());
        }
        this.map = Collections.unmodifiableMap(map);
    }

    @Override
    public void serialize(PacketSerializer serializer) {
        final Set<Map.Entry<UUID, Boolean>> entries = this.map.entrySet();
        serializer.writeInt(entries.size());
        for (Map.Entry<UUID, Boolean> entry : entries) {
            serializer.writeUUID(entry.getKey());
            serializer.writeBoolean(entry.getValue() != null && entry.getValue());
        }
    }
}
