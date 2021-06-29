package flv.parser;

import flv.model.FLVHeader;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * User: RuzzZZ
 * Date: 2021/6/28
 * Time: 19:55
 */
public class FLVHeaderParser {

    private static final byte[] _9_byte = new byte[9];

    public static FLVHeader parseHeader(BufferedInputStream bis) throws IOException {
        int i = bis.read(_9_byte);
        if (i == -1) {
            return null;
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(_9_byte.length);
        byteBuffer.put(_9_byte);
        byteBuffer.flip();
        return new FLVHeader(byteBuffer);
    }
}
