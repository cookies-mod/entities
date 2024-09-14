package dev.morazzer.cookies.entities.websocket;

import java.util.function.Consumer;

/**
 * A consumer with additional information about the packet.
 * @param clazz The class of the packet.
 * @param consumer The consumer.
 * @param <T> The type of the packet.
 */
public record PacketConsumer<T extends Packet<T>>(Class<T> clazz, Consumer<T> consumer) {}
