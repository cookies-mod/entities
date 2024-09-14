package dev.morazzer.cookies.entities;


import dev.morazzer.cookies.entities.websocket.PacketSerializer;
import dev.morazzer.cookies.entities.websocket.packets.c2s.PlayersUseModRequestPacket;
import dev.morazzer.cookies.entities.websocket.packets.s2c.PlayersUseModResponsePacket;
import java.io.IOException;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PlayerUseModRequestPacketTest {

    @Test
    public void test() throws IOException {
        final PlayersUseModRequestPacket origin =
            new PlayersUseModRequestPacket(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());
        final PacketSerializer packetSerializer = new PacketSerializer();
        origin.serialize(packetSerializer);

        final PlayersUseModRequestPacket copy =
            new PlayersUseModRequestPacket(new PacketSerializer(packetSerializer.toByteArray()));

        Assertions.assertEquals(origin.uuids.length, copy.uuids.length);
        for (int i = 0; i < origin.uuids.length; i++) {
            Assertions.assertEquals(origin.uuids[i], copy.uuids[i]);
        }
    }

}
