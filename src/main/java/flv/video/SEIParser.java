package flv.video;


import com.google.common.collect.Lists;

import java.nio.ByteBuffer;
import java.util.List;

/**
 * User: RuzzZZ
 * Date: 2021/6/29
 * Time: 10:19
 */
public class SEIParser {

    /**
     * FrameType(FT)  = 1 : Key Frame (for AVC, a seekable frame)
     * = 2 : Inter Frame (for AVC, a non-seekable frame)
     * = 3 : disposable inter frame (H.263 only)
     * = 4 : generated keyframe (reserved for server use only)
     * = 5 : video info/command frame
     */
    private static final int KEY_FRAME = 1;
    private static final int INTER_FRAME = 2;

    /**
     * CodecID(CID)  = 1 : JPEG (currently unused)
     * = 2 : Sorenson H.263
     * = 3 : Screen video
     * = 4 : On2 VP6
     * = 5 : On2 VP6 with alpha channel
     * = 6 : Screen video version 2
     * = 7 : H264/AVC
     */
    private static final int H264_AVC = 7;

    /**
     * AVC package type(AVC PT)= 00 ：AVC Sequence Header
     * = 01 ：AVC NALU
     * = 02 : AVC end of sequence
     */
    private static final int AVC_NALU = 1;

    /**
     * NALU_TYPE_SEI   6      辅助增强信息 (SEI)
     */
    private static final int NALU_TYPE_SEI = 6;

    /**
     * 目前只支持pay_load_type为5的元素的解析
     */
    private static final int SEI_PAY_LOAD_TYPE = 5;


    /**
     * 解析sei
     *
     * @param data video元素信息
     * @return 如果是sei类型，返回sei信息；否则返回null
     */
    public static List<String> parserSEI(byte[] data) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(data.length);
        byteBuffer.put(data);
        byteBuffer.flip();

        byte videoHeader = byteBuffer.get();
        //frame type
        if (videoHeader >> 4 != KEY_FRAME && videoHeader >> 4 != INTER_FRAME) {
            return null;
        }

        //codec id
        if ((videoHeader & 0x0f) != H264_AVC) {
            return null;
        }

        //avc格式，avc_packet_type为1时，表示AVC NALU
        if ((int) byteBuffer.get() != AVC_NALU) {
            return null;
        }
        //jump cts,si24
        byteBuffer.get();
        byteBuffer.get();
        byteBuffer.get();

        return parseSEIInfo(byteBuffer);
    }

    private static List<String> parseSEIInfo(ByteBuffer buffer) {
        List<String> seiList = Lists.newArrayList();
        while (buffer.hasRemaining()) {
            //nalu unit size
            int naluUnitSize = buffer.getInt();
            //sei type
            if ((int) buffer.get() != NALU_TYPE_SEI) {
                return null;
            }
            //pay load type
            if ((int) buffer.get() != SEI_PAY_LOAD_TYPE) {
                return null;
            }
            //pay load size
            int payLoadSize = buffer.get();
            byte[] data = new byte[payLoadSize];
            buffer.get(data);
            seiList.add(new String(data));
            //0x80 结尾
            byte rbspTrailingBits = buffer.get();
        }
        return seiList;
    }

}
