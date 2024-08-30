package dev.morazzer.cookies.entities.websocket;

import java.io.IOException;
import java.util.function.Function;

public class RegisteredPacket<T extends Packet> {

    private final Class<T> clazz;
    private final PacketCreator<T> creator;
    private final int id;

    public RegisteredPacket(Class<T> tClass, PacketCreator<T> creator, int id) {
        this.clazz = tClass;
        this.creator = creator;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public byte[] serializeUnknown(Packet<?> packet) throws IOException {
        //noinspection unchecked
        return serialize((T) packet);
    }

    public byte[] serialize(T packet) throws IOException {
        PacketSerializer packetSerializer = new PacketSerializer();
        packet.serialize(packetSerializer);
        return packetSerializer.toByteArray();
    }

    public T deserialize(PacketSerializer packetSerializer) throws IOException {
        return creator.create(packetSerializer);
    }

    public interface PacketCreator<T> {
        T create(PacketSerializer packetSerializer) throws IOException;
    }

}
