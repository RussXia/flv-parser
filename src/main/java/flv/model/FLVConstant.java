package flv.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * User: RuzzZZ
 * Date: 2021/6/29
 * Time: 16:24
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FLVConstant {

    /**
     * 音频
     */
    public static final int FLV_TAG_TYPE_AUDIO = 8;

    /**
     * 视频
     */
    public static final int FLV_TAG_TYPE_VIDEO = 9;

    /**
     * script
     */
    public static final int FLV_TAG_TYPE_SCRIPT = 18;
}
