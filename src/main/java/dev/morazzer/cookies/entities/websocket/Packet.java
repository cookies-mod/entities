package dev.morazzer.cookies.entities.websocket;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public interface Packet<T extends Packet<T>> {
    List<PacketConsumer<?>> consumers = new ArrayList<>();

    void serialize(PacketSerializer serializer);
    static <T extends Packet<T>> void onReceive(Class<T> clazz, Consumer<T> consumer) {
        consumers.add(new PacketConsumer<>(clazz, consumer));
    }

    static <T extends Packet<T>> void receive(Packet<T> receive) {
        for (PacketConsumer<?> consumer : consumers) {
            if (consumer.clazz().isInstance(receive)) {
                invoke(consumer.consumer(), receive);
            }
        }
    }

    private static <T extends Packet<T>>  void invoke(Consumer<T> consumer, Packet<?> packet) {
        //noinspection unchecked
        consumer.accept((T) packet);
    }

}
