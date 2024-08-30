package dev.morazzer.cookies.entities.websocket;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class PacketSerializer {

    private ByteArrayInputStream input;
    private ByteArrayOutputStream output;

    public PacketSerializer() {
        this.output = new ByteArrayOutputStream();
    }

    public PacketSerializer(byte[] bytes) {
        this.input = new ByteArrayInputStream(bytes);
    }

    public void writeInt(int number) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (number & 0xFF);
        bytes[1] = (byte) ((number >> 8) & 0xFF);
        bytes[2] = (byte) ((number >> 16) & 0xFF);
        bytes[3] = (byte) ((number >> 24) & 0xFF);
        this.output.writeBytes(bytes);
    }

    public int readInt() throws IOException {
        byte[] bytes = new byte[4];
        this.input.read(bytes);
        return bytes[0] & 0xFF | (bytes[1] & 0xFF) << 8 | (bytes[2] & 0xFF) << 16 | (bytes[3] & 0xFF) << 24;
    }

    public void writeString(String string) {
        this.writeByteArray(string.getBytes(StandardCharsets.UTF_8));
    }

    public String readString() throws IOException {
        return new String(this.readByteArray(), StandardCharsets.UTF_8);
    }

    public byte[] toByteArray() {
        return this.output.toByteArray();
    }

    public void writeLong(long number) {
        this.writeInt((int) (number));
        this.writeInt((int) (number >>> 32));
    }

    public long readLong() throws IOException {
        return ((long) this.readInt() & 0xFFFFFFFFL) | ((long) this.readInt()) << 32;
    }

    public void writeUUID(UUID uuid) {
        this.writeLong(uuid.getMostSignificantBits());
        this.writeLong(uuid.getLeastSignificantBits());
    }

    public UUID readUUID() throws IOException {
        long mostSignificantBits = this.readLong();
        long leastSignificantBits = this.readLong();
        return new UUID(mostSignificantBits, leastSignificantBits);
    }

    public void writeByteArray(byte[] bytes) {
        this.writeInt(bytes.length);
        this.output.writeBytes(bytes);
    }
    public void writeByteArrayWithoutLength(byte[] bytes) {
        this.output.writeBytes(bytes);
    }

    public byte[] readByteArray() throws IOException {
        final int length = this.readInt();
        final byte[] bytes = new byte[length];
        this.input.read(bytes);
        return bytes;
    }

    public void ensureEmpty() {
        if (this.input.available() > 0) {
            throw new IllegalStateException("Buffer not empty after packet deserialization");
        }
    }
}
