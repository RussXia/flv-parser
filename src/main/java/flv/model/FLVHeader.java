package flv.model;


import lombok.Getter;

import java.nio.ByteBuffer;

@Getter
public class FLVHeader {
    private static final byte[] FLV = {'F', 'L', 'V'};

    private final boolean isFlv;

    private final byte version;

    private final byte flags;

    private final int headerSize;


    public FLVHeader(ByteBuffer byteBuffer) {
        byte[] bytes = new byte[3];
        byteBuffer.get(bytes);
        isFlv = isFlv(bytes);
        version = byteBuffer.get();
        flags = byteBuffer.get();
        headerSize = byteBuffer.getInt();
    }

    public static boolean isFlv(byte[] bytes) {
        return bytes[0] == FLV[0] && bytes[1] == FLV[1] && bytes[2] == FLV[2];
    }

}
