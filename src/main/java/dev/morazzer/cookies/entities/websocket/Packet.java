package dev.morazzer.cookies.entities.websocket;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * A packet that can be sent between server and client.
 * @param <T> The type of the packet.
 */
public interface Packet<T extends Packet<T>> {
    List<PacketConsumer<?>> consumers = new ArrayList<>();

    /**
     * Serializes the packet into a byte stream.
     * @param serializer The serializer.
     */
    void serialize(PacketSerializer serializer);

    /**
     * Registers a packet listener.
     * @param clazz The packet to listen to.
     * @param consumer The listener.
     * @param <T> The type of the packet.
     */
    static <T extends Packet<T>> void onReceive(Class<T> clazz, Consumer<T> consumer) {
        consumers.add(new PacketConsumer<>(clazz, consumer));
    }

    /**
     * Called when a packet is received.
     * @param receive The packet that was received.
     * @param <T> The type of the packet.
     */
    static <T extends Packet<T>> void receive(Packet<T> receive) {
        for (PacketConsumer<?> consumer : consumers) {
            if (consumer.clazz().isInstance(receive)) {
                invoke(consumer.consumer(), receive);
            }
        }
    }

    /**
     * Invokes the packet listener for the packet.
     * @param consumer The listener.
     * @param packet The packet.
     * @param <T> The type of the packet.
     */
    private static <T extends Packet<T>>  void invoke(Consumer<T> consumer, Packet<?> packet) {
        //noinspection unchecked
        consumer.accept((T) packet);
    }

}
