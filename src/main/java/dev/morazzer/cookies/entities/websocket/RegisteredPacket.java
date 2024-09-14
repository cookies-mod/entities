package dev.morazzer.cookies.entities.websocket;

import java.io.IOException;

/**
 * Represents a packet that has been registered, this contains information like, how to construct said packet, the id
 * of the packet and how to serialize it.
 */
public class RegisteredPacket<T extends Packet<T>> {

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

    /**
     * Serializes an unknown packet, at this point you should be able to safely cast it to the generic packet.
     *
     * @param packet The unknown packet.
     * @return The serialized packet.
     */
    public byte[] serializeUnknown(Packet<?> packet) {
        //noinspection unchecked
        return serialize((T) packet);
    }

    /**
     * Serializes the packet into a byte array.
     *
     * @param packet The packet.
     * @return The serialized packet.
     */
    public byte[] serialize(T packet) {
        PacketSerializer packetSerializer = new PacketSerializer();
        packet.serialize(packetSerializer);
        return packetSerializer.toByteArray();
    }

    /**
     * Deserializes a new packet from the provided serializer.
     *
     * @param packetSerializer The serializer.
     * @return The packet.
     * @throws IOException If any io errors occurred, for more information see packet implementation.
     */
    public T deserialize(PacketSerializer packetSerializer) throws IOException {
        return creator.create(packetSerializer);
    }

    /**
     * Interface to create a packet that also allows for {@link IOException}s.
     * @param <T> The type of the packet.
     */
    @FunctionalInterface
    public interface PacketCreator<T> {
        T create(PacketSerializer packetSerializer) throws IOException;
    }

}
