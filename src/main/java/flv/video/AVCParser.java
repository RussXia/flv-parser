package flv.video;

import static flv.video.VideoConstant.*;

/**
 * User: RuzzZZ
 * Date: 2021/6/29
 * Time: 16:04
 */
public class AVCParser {

    public static boolean isAVCNalu(byte[] data) {
        byte videoHeader = data[0];
        //frame type
        if (videoHeader >> 4 != INTER_FRAME) {
            return false;
        }

        //codec id
        if ((videoHeader & 0x0f) != H264_AVC) {
            return false;
        }

        //avc格式，avc_packet_type为1时，表示AVC NALU
        if ((int) data[1] != AVC_NALU) {
            return false;
        }
        return true;
    }
}
