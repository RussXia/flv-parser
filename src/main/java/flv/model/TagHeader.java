package flv.model;

import lombok.Getter;

import java.nio.ByteBuffer;


@Getter
public class TagHeader {
    public static final int HEAD_LENGTH = 11;

    private byte type;

    private int dataSize;

    private int timestamp;

    private final byte[] streamID = new byte[3];

    private ByteBuffer data;

    public static TagHeader build(ByteBuffer buffer) {
        if (buffer.capacity() != 11) {
            return null;
        }
        TagHeader result = new TagHeader();
        result.data = buffer;
        result.type = buffer.get();

        byte[] dataSize = new byte[3];
        buffer.get(dataSize);
        result.dataSize = (dataSize[0] & 0xff) << 16 | (dataSize[1] & 0xff) << 8 | (dataSize[2] & 0xff);

        byte[] bytes = new byte[4];
        buffer.get(bytes);
        result.timestamp = TagHeader.getTimeStamp(bytes);
        buffer.get(result.streamID);
        return result;
    }

    static int getTimeStamp(byte[] res) {
        return res[2] & 0xff | res[1] & 0xff << 8 | res[0] & 0xff << 16;
    }
}
