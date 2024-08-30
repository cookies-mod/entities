package dev.morazzer.cookies.entities.websocket;

import java.util.function.Consumer;

public record PacketConsumer<T extends Packet<T>>(Class<T> clazz, Consumer<T> consumer) {}
