package flv.parser;

import flv.model.FLVHeader;
import flv.model.FLVTag;
import flv.model.TagHeader;
import lombok.SneakyThrows;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Iterator;

/**
 * User: RuzzZZ
 * Date: 2021/6/28
 * Time: 19:53
 */
public class FLVTagIterator implements Iterator<FLVTag> {

    private final BufferedInputStream bis;

    private FLVTag currentTag;

    private byte[] data;

    public FLVTagIterator(BufferedInputStream bis) throws IOException {
        this.bis = bis;
        //check flv header
        FLVHeader flvHeader = FLVHeaderParser.parseHeader(bis);
        if (flvHeader == null || !flvHeader.isFlv()) {
            throw new IllegalArgumentException("illegal flv file");
        }
    }

    @SneakyThrows
    @Override
    public boolean hasNext() {
        //previous tag size
        byte[] previous = new byte[4];
        int i = bis.read(previous);
        if (i == -1) {
            return false;
        }
        int previousSize = previous[3] & 0xFF | (previous[2] & 0xFF) << 8 | (previous[1] & 0xFF) << 16 | (previous[0] & 0xFF) << 24;

        //header
        byte[] headerBytes = new byte[11];
        i = bis.read(headerBytes);
        if (i == -1) {
            return false;
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(11);
        byteBuffer.put(headerBytes);
        byteBuffer.flip();
        TagHeader tagHeader = TagHeader.build(byteBuffer);
        if (tagHeader == null) {
            return false;
        }
        //data
        int dataSize = tagHeader.getDataSize();
        byte[] data = new byte[dataSize];
        i = bis.read(data);
        if (i == -1) {
            return false;
        }

        //build & return
        this.currentTag = FLVTag.builder()
                .header(tagHeader)
                .data(data)
                .previousTagSize(previousSize)
                .build();
        return true;
    }

    @Override
    public FLVTag next() {
        return currentTag;
    }
}
