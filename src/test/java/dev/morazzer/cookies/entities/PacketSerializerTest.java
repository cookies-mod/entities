package dev.morazzer.cookies.entities;

import dev.morazzer.cookies.entities.websocket.PacketSerializer;
import java.io.IOException;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PacketSerializerTest {

    @Test
    public void intTest() throws IOException {
        final PacketSerializer packetSerializer = new PacketSerializer();
        final int number = (int) System.currentTimeMillis();
        packetSerializer.writeInt(number);
        final PacketSerializer deserializer = new PacketSerializer(packetSerializer.toByteArray());
        Assertions.assertEquals(number, deserializer.readInt());
        deserializer.ensureEmpty();
    }

    @Test
    public void longTest() throws IOException {
        final PacketSerializer packetSerializer = new PacketSerializer();
        final long number = System.currentTimeMillis();
        packetSerializer.writeLong(number);
        final PacketSerializer deserializer = new PacketSerializer(packetSerializer.toByteArray());
        Assertions.assertEquals(number, deserializer.readLong());
        deserializer.ensureEmpty();
    }

    @Test
    public void uuidTest() throws IOException {
        final PacketSerializer packetSerializer = new PacketSerializer();
        final UUID uuid = UUID.randomUUID();
        packetSerializer.writeUUID(uuid);
        final PacketSerializer deserializer = new PacketSerializer(packetSerializer.toByteArray());
        Assertions.assertEquals(uuid, deserializer.readUUID());
        deserializer.ensureEmpty();
    }

    @Test
    public void stringTest() throws IOException {
        final PacketSerializer packetSerializer = new PacketSerializer();
        final String string = UUID.randomUUID().toString();
        packetSerializer.writeString(string);
        final PacketSerializer deserializer = new PacketSerializer(packetSerializer.toByteArray());
        Assertions.assertEquals(string, deserializer.readString());
        deserializer.ensureEmpty();
    }

    @Test
    public void byteArrayTest() throws IOException {
        final PacketSerializer packetSerializer = new PacketSerializer();
        final byte[] byteArray = UUID.randomUUID().toString().getBytes();
        packetSerializer.writeByteArray(byteArray);
        final PacketSerializer deserializer = new PacketSerializer(packetSerializer.toByteArray());
        Assertions.assertArrayEquals(byteArray, deserializer.readByteArray());
    }
}
