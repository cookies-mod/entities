package dev.morazzer.cookies.entities.websocket;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * Helper used for serialization/deserialization of packets.
 */
public class PacketSerializer {

    private ByteArrayInputStream input;
    private ByteArrayOutputStream output;

    /**
     * Creates a write only packet serializer.
     */
    public PacketSerializer() {
        this.output = new ByteArrayOutputStream();
    }

    /**
     * Creates a read only packet serializer.
     *
     * @param bytes The bytes to be read.
     */
    public PacketSerializer(byte[] bytes) {
        this.input = new ByteArrayInputStream(bytes);
    }

    /**
     * Writes the int to the output.
     */
    public void writeInt(int number) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (number & 0xFF);
        bytes[1] = (byte) ((number >> 8) & 0xFF);
        bytes[2] = (byte) ((number >> 16) & 0xFF);
        bytes[3] = (byte) ((number >> 24) & 0xFF);
        this.output.writeBytes(bytes);
    }

    /**
     * Reads an int from the input.
     *
     * @throws IOException May be thrown, for more information see {@link ByteArrayInputStream#read(byte[])}.
     */
    public int readInt() throws IOException {
        byte[] bytes = this.input.readNBytes(4);
        return bytes[0] & 0xFF | (bytes[1] & 0xFF) << 8 | (bytes[2] & 0xFF) << 16 | (bytes[3] & 0xFF) << 24;
    }

    /**
     * Writes the string to the output in form of a byte[] and prefixes said array with its length.
     *
     * @param string The string to write.
     */
    public void writeString(String string) {
        this.writeByteArray(string.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Reads a string from the input stream.
     *
     * @throws IOException May be thrown, for more information see {@link ByteArrayInputStream#read(byte[])}.
     */
    public String readString() throws IOException {
        return new String(this.readByteArray(), StandardCharsets.UTF_8);
    }

    /**
     * @return The output as a byte array.
     */
    public byte[] toByteArray() {
        return this.output.toByteArray();
    }

    /**
     * Writes the long as two integers to the output.
     *
     * @param number The long to write.
     */
    public void writeLong(long number) {
        this.writeInt((int) (number));
        this.writeInt((int) (number >>> 32));
    }

    /**
     * Reads a long from the input stream.
     *
     * @throws IOException May be thrown, for more information see {@link ByteArrayInputStream#read(byte[])}.
     */
    public long readLong() throws IOException {
        return ((long) this.readInt() & 0xFFFFFFFFL) | ((long) this.readInt()) << 32;
    }

    /**
     * Writes an uuid as two longs to the output.
     *
     * @param uuid The uuid to write.
     */
    public void writeUUID(UUID uuid) {
        this.writeLong(uuid.getMostSignificantBits());
        this.writeLong(uuid.getLeastSignificantBits());
    }

    /**
     * Reads an uuid from the input stream.
     *
     * @throws IOException May be thrown, for more information see {@link ByteArrayInputStream#read(byte[])}.
     */
    public UUID readUUID() throws IOException {
        long mostSignificantBits = this.readLong();
        long leastSignificantBits = this.readLong();
        return new UUID(mostSignificantBits, leastSignificantBits);
    }

    /**
     * Writes a byte array to the output stream and prefixes said array with its length.
     *
     * @param bytes The byte array to write.
     */
    public void writeByteArray(byte[] bytes) {
        this.writeInt(bytes.length);
        this.output.writeBytes(bytes);
    }

    /**
     * Writes a byte array to the output stream without prefixing it with its length.
     *
     * @param bytes The byte array to write.
     */
    public void writeByteArrayWithoutLength(byte[] bytes) {
        this.output.writeBytes(bytes);
    }

    /**
     * Reads a byte array that was prefixed with its own length.
     *
     * @throws IOException May be thrown, for more information see {@link ByteArrayInputStream#read(byte[])}.
     */
    public byte[] readByteArray() throws IOException {
        final int length = this.readInt();
        final byte[] bytes = new byte[length];
        this.input.read(bytes);
        return bytes;
    }

    /**
     * Ensures that the input stream has no remaining bytes, if any are found an exception is thrown.
     */
    public void ensureEmpty() {
        if (this.input.available() > 0) {
            throw new IllegalStateException("Buffer not empty after packet deserialization");
        }
    }

    /**
     * Writes a single byte to the output stream.
     *
     * @param b The byte to write.
     */
    public void writeByte(byte b) {
        this.output.write(b);
    }

    /**
     * Reads a single byte from the input stream.
     *
     * @throws IOException May be thrown, for more information see {@link ByteArrayInputStream#readNBytes(int)}.
     */
    public byte readByte() throws IOException {
        return this.input.readNBytes(1)[0];
    }

    /**
     * Writes a boolean in form of a byte to the output stream.
     *
     * @param bool The boolean to write.
     */
    public void writeBoolean(boolean bool) {
        this.writeByte((byte) (bool ? 1 : 0));
    }

    /**
     * Reads a boolean from the input stream.
     *
     * @throws IOException May be thrown, for more information see {@link ByteArrayInputStream#readNBytes(int)}.
     */
    public boolean readBoolean() throws IOException {
        return readByte() == 1;
    }
}
