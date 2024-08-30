package dev.morazzer.cookies.entities.websocket;

import dev.morazzer.cookies.entities.websocket.c2s.DungeonLeavePacket;
import dev.morazzer.cookies.entities.websocket.c2s.DungeonMimicKilledPacket;
import dev.morazzer.cookies.entities.websocket.c2s.DungeonSyncPlayerLocation;
import dev.morazzer.cookies.entities.websocket.c2s.DungeonJoinPacket;
import dev.morazzer.cookies.entities.websocket.c2s.DungeonUpdateRoomIdPacket;
import dev.morazzer.cookies.entities.websocket.c2s.TestServerPacket;
import dev.morazzer.cookies.entities.websocket.c2s.DungeonUpdateRoomSecrets;
import java.io.IOException;
import java.util.function.Function;

public enum Side {

    PACKETS(
        register(TestServerPacket.class, TestServerPacket::new),
        register(DungeonJoinPacket.class, DungeonJoinPacket::new),
        register(DungeonSyncPlayerLocation.class, DungeonSyncPlayerLocation::new),
        register(DungeonUpdateRoomSecrets.class, DungeonUpdateRoomSecrets::new),
        register(DungeonUpdateRoomIdPacket.class, DungeonUpdateRoomIdPacket::new),
        register(DungeonMimicKilledPacket.class, DungeonMimicKilledPacket::new),
        register(DungeonLeavePacket.class, DungeonLeavePacket::new));
    private final RegisteredPacket<?>[] register;
    public static final Side[] VALUES = values();

    @SafeVarargs
    Side(Function<Integer, RegisteredPacket<?>>... register) {
        this.register = new RegisteredPacket<?>[register.length];
        for (int i = 0; i < register.length; i++) {
            this.register[i] = register[i].apply(i);
        }
    }

    public byte[] serializeUnknown(Packet<?> unknown) throws IOException {
        final RegisteredPacket<?> registeredPacket = findForUnknown(unknown);
        PacketSerializer packetSerializer = new PacketSerializer();
        final byte[] serialize = registeredPacket.serializeUnknown(unknown);
        packetSerializer.writeInt(registeredPacket.getId());
        packetSerializer.writeByteArrayWithoutLength(serialize);
        return packetSerializer.toByteArray();
    }

    public <T extends Packet<T>> byte[] serialize(T packet) throws IOException {
        return serializeUnknown(packet);
    }

    public <T extends Packet<T>> T deserialize(byte[] packet) throws IOException {
        final PacketSerializer packetSerializer = new PacketSerializer(packet);
        final int id = packetSerializer.readInt();
        final RegisteredPacket<T> deserializer = this.findFor(id);
        T createdPacket = deserializer.deserialize(packetSerializer);
        packetSerializer.ensureEmpty();
        return createdPacket;
    }

    public <T extends Packet<T>> void deserializeAndSend(byte[] packet) throws IOException {
        Packet.receive(this.<T>deserialize(packet));
    }

    private <T extends Packet<T>> RegisteredPacket<T> findFor(int id) {
        for (RegisteredPacket<?> registeredPacket : this.register) {
            if (registeredPacket.getId() == id) {
                return (RegisteredPacket<T>) registeredPacket;
            }
        }
        return null;
    }
    private RegisteredPacket<?> findForUnknown(Packet<?> packet) {
        for (RegisteredPacket<?> registeredPacket : register) {
            if (registeredPacket.getClazz().isInstance(packet)) {
                return registeredPacket;
            }
        }
        return null;
    }
    private <T extends Packet<T>> RegisteredPacket<T> findFor(T packet) {
        for (RegisteredPacket<?> registeredPacket : register) {
            if (registeredPacket.getClazz().isInstance(packet)) {
                return (RegisteredPacket<T>) registeredPacket;
            }
        }
        return null;
    }

    private static <T extends Packet<T>> Function<Integer, RegisteredPacket<?>> register(
        Class<T> tClass, RegisteredPacket.PacketCreator<T> packetCreator) {
        return id -> new RegisteredPacket<>(tClass, packetCreator, id);
    }

}
