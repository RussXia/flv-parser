package flv.video;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * User: RuzzZZ
 * Date: 2021/6/29
 * Time: 16:05
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VideoConstant {


    /**
     * FrameType(FT)  = 1 : Key Frame (for AVC, a seekable frame)
     * = 2 : Inter Frame (for AVC, a non-seekable frame)
     * = 3 : disposable inter frame (H.263 only)
     * = 4 : generated keyframe (reserved for server use only)
     * = 5 : video info/command frame
     */
    public static final int INTER_FRAME = 2;

    /**
     * CodecID(CID)  = 1 : JPEG (currently unused)
     * = 2 : Sorenson H.263
     * = 3 : Screen video
     * = 4 : On2 VP6
     * = 5 : On2 VP6 with alpha channel
     * = 6 : Screen video version 2
     * = 7 : H264/AVC
     */
    public static final int H264_AVC = 7;

    /**
     * AVC package type(AVC PT)= 00 ：AVC Sequence Header
     * = 01 ：AVC NALU
     * = 02 : AVC end of sequence
     */
    public static final int AVC_NALU = 1;

    /**
     * NALU_TYPE_SEI   6      辅助增强信息 (SEI)
     */
    public static final int NALU_TYPE_SEI = 6;

    /**
     * 目前只支持pay_load_type为5的元素的解析
     */
    public static final int SEI_PAY_LOAD_TYPE = 5;
}
