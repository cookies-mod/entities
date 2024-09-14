package dev.morazzer.cookies.entities;

import dev.morazzer.cookies.entities.websocket.PacketSerializer;
import dev.morazzer.cookies.entities.websocket.packets.s2c.PlayersUseModResponsePacket;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PlayersUseModResponsePacketTest {

    @Test
    public void test() throws IOException {
        final Map<UUID, Boolean> map = new HashMap<>();
        map.put(UUID.randomUUID(), true);
        map.put(UUID.randomUUID(), false);
        map.put(UUID.randomUUID(), false);
        map.put(UUID.randomUUID(), true);
        PlayersUseModResponsePacket origin = new PlayersUseModResponsePacket(map);
        final PacketSerializer packetSerializer = new PacketSerializer();
        origin.serialize(packetSerializer);
        final PacketSerializer packetSerializer1 = new PacketSerializer(packetSerializer.toByteArray());
        PlayersUseModResponsePacket copy = new PlayersUseModResponsePacket(packetSerializer1);

        Assertions.assertEquals(origin.map.size(), copy.map.size());
        List<UUID> uuid = map.keySet().stream().toList();
        for (UUID uuid1 : uuid) {
            Assertions.assertTrue(copy.map.containsKey(uuid1));
            Assertions.assertEquals(origin.map.get(uuid1), copy.map.get(uuid1));
        }
        packetSerializer1.ensureEmpty();
    }

}
