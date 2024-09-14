package dev.morazzer.cookies.entities.websocket;

import dev.morazzer.cookies.entities.websocket.packets.DisconnectPacket;
import dev.morazzer.cookies.entities.websocket.packets.DungeonLeavePacket;
import dev.morazzer.cookies.entities.websocket.packets.DungeonMimicKilledPacket;
import dev.morazzer.cookies.entities.websocket.packets.DungeonSyncPlayerLocation;
import dev.morazzer.cookies.entities.websocket.packets.DungeonJoinPacket;
import dev.morazzer.cookies.entities.websocket.packets.DungeonUpdateRoomIdPacket;
import dev.morazzer.cookies.entities.websocket.packets.HandshakePacket;
import dev.morazzer.cookies.entities.websocket.packets.TestServerPacket;
import dev.morazzer.cookies.entities.websocket.packets.DungeonUpdateRoomSecrets;
import dev.morazzer.cookies.entities.websocket.packets.WrongProtocolVersionPacket;
import dev.morazzer.cookies.entities.websocket.packets.c2s.PlayersUseModRequestPacket;
import dev.morazzer.cookies.entities.websocket.packets.s2c.PlayersUseModResponsePacket;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

/**
 * Holder of all packets, this basically defines the packet ids.
 */
public enum Side {

    PACKETS(
        register(HandshakePacket.class, HandshakePacket::new),
        register(DisconnectPacket.class, DisconnectPacket::new),
        register(WrongProtocolVersionPacket.class, WrongProtocolVersionPacket::new),
        register(TestServerPacket.class, TestServerPacket::new),
        register(DungeonJoinPacket.class, DungeonJoinPacket::new),
        register(DungeonSyncPlayerLocation.class, DungeonSyncPlayerLocation::new),
        register(DungeonUpdateRoomSecrets.class, DungeonUpdateRoomSecrets::new),
        register(DungeonUpdateRoomIdPacket.class, DungeonUpdateRoomIdPacket::new),
        register(DungeonMimicKilledPacket.class, DungeonMimicKilledPacket::new),
        register(DungeonLeavePacket.class, DungeonLeavePacket::new),
        register(PlayersUseModResponsePacket.class, PlayersUseModResponsePacket::new),
        register(PlayersUseModRequestPacket.class, PlayersUseModRequestPacket::new));
    private final RegisteredPacket<?>[] register;
    public static final Side[] VALUES = values();

    /**
     * Creates the packet registry.
     *
     * @param register All registered packets.
     */
    @SafeVarargs
    Side(Function<Integer, RegisteredPacket<?>>... register) {
        Set<Class<?>> registeredPackets = new HashSet<>();
        this.register = new RegisteredPacket<?>[register.length];
        for (int i = 0; i < register.length; i++) {
            final RegisteredPacket<?> apply = register[i].apply(i);
            if (registeredPackets.contains(apply.getClazz())) {
                throw new IllegalStateException("Duplicate packet registered for " + apply.getClazz());
            }
            registeredPackets.add(apply.getClazz());
            this.register[i] = apply;
        }
    }

    /**
     * Serializes an unknown packet.
     *
     * @param unknown The packet to serialize.
     * @return The serialized packet.
     */
    public byte[] serializeUnknown(Packet<?> unknown) {
        try {
            final RegisteredPacket<?> registeredPacket = findForUnknown(unknown);
            PacketSerializer packetSerializer = new PacketSerializer();
            final byte[] serialize = registeredPacket.serializeUnknown(unknown);
            //packetSerializer.writeInt(serialize.length + 1);
            packetSerializer.writeInt(registeredPacket.getId());
            packetSerializer.writeByteArrayWithoutLength(serialize);
            return packetSerializer.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error while serializing packet " + unknown.getClass().getSimpleName(), e);
        }
    }

    /**
     * Serializes a packet.
     *
     * @param packet The packet to serialize.
     * @param <T>    The type of the packet.
     * @return The serialized packet.
     */
    public <T extends Packet<T>> byte[] serialize(T packet) {
        return serializeUnknown(packet);
    }

    /**
     * Deserializes a packet from a byte[].
     *
     * @param packet The serialized packet.
     * @param <T>    The type of the packet.
     * @return The packet.
     * @throws IOException If any io error occurred, for more information see packet implementation.
     */
    public <T extends Packet<T>> T deserialize(byte[] packet) throws IOException {
        final PacketSerializer packetSerializer = new PacketSerializer(packet);
        final int id = packetSerializer.readInt();
        final RegisteredPacket<T> deserializer = this.findFor(id);
        if (deserializer == null) {
            throw new RuntimeException("Unknown packet id " + id);
        }
        try {
            T createdPacket = deserializer.deserialize(packetSerializer);
            packetSerializer.ensureEmpty();
            return createdPacket;
        } catch (Exception e) {
            throw new RuntimeException("Error while deserializing packet " + deserializer.getClazz().getSimpleName(), e);
        }
    }

    /**
     * Deserializes a packet and immediately invokes the listeners associated with said packet.
     *
     * @param packet The serialized packet.
     * @param <T>    The type of the packet.
     * @throws IOException If any io error occurred, for more information see packet implementation.
     */
    public <T extends Packet<T>> void deserializeAndSend(byte[] packet) throws IOException {
        Packet.receive(this.<T>deserialize(packet));
    }

    /**
     * Finds the registered packet based on the packet id.
     * @param id The packet id.
     * @return The registered packet.
     * @param <T> The type of the packet.
     */
    private <T extends Packet<T>> RegisteredPacket<T> findFor(int id) {
        for (RegisteredPacket<?> registeredPacket : this.register) {
            if (registeredPacket.getId() == id) {
                //noinspection unchecked
                return (RegisteredPacket<T>) registeredPacket;
            }
        }
        return null;
    }

    /**
     * Finds the registered packet for the unknown given packet.
     * @param packet The packet.
     * @return The registered packet.
     */
    private RegisteredPacket<?> findForUnknown(Packet<?> packet) {
        for (RegisteredPacket<?> registeredPacket : register) {
            if (registeredPacket.getClazz().isInstance(packet)) {
                return registeredPacket;
            }
        }
        return null;
    }

    /**
     * Creates a registered packet provider that can later be invoked with the packet id.
     * @param tClass The class of the packet.
     * @param packetCreator The creator of the packet.
     * @return The registered packet provider.
     * @param <T> The type of the packet.
     */
    private static <T extends Packet<T>> Function<Integer, RegisteredPacket<?>> register(
        Class<T> tClass, RegisteredPacket.PacketCreator<T> packetCreator) {
        return id -> new RegisteredPacket<>(tClass, packetCreator, id);
    }

}
