package flv.video;


import com.google.common.collect.Lists;

import java.nio.ByteBuffer;
import java.util.List;

import static flv.video.VideoConstant.NALU_TYPE_SEI;
import static flv.video.VideoConstant.SEI_PAY_LOAD_TYPE;

/**
 * User: RuzzZZ
 * Date: 2021/6/29
 * Time: 10:19
 */
public class SEIParser {


    /**
     * 判断是否含有sei信息
     *
     * @param data flv中的video tag信息
     * @return 是否含有sei信息
     */
    public static boolean hasSEIInfo(byte[] data) {
        if (!AVCParser.isAVCNalu(data)) {
            return false;
        }
        ByteBuffer byteBuffer = ByteBuffer.wrap(data);

        //跳过avc_nalu的判断
        byteBuffer.position(5);

        while (byteBuffer.hasRemaining()) {
            //nalu unit size
            int naluUnitSize = byteBuffer.getInt();
            byte[] unit = new byte[naluUnitSize];
            byteBuffer.get(unit);
            ByteBuffer unitBuffer = ByteBuffer.wrap(unit);

            //sei type
            if ((int) unitBuffer.get() != NALU_TYPE_SEI) {
                continue;
            }
            //pay load type
            if ((int) unitBuffer.get() != SEI_PAY_LOAD_TYPE) {
                continue;
            }
            //pay load size
            return true;
        }
        return false;
    }


    /**
     * 解析sei
     *
     * @param data video元素信息
     * @return 如果是sei类型，返回sei信息；否则返回null
     */
    public static List<String> parserSEI(byte[] data) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(data);

        //跳过avc_nalu的判断
        byteBuffer.position(5);

        //解析nalu_unit数组
        List<String> seiList = Lists.newArrayList();
        while (byteBuffer.hasRemaining()) {
            //nalu unit size
            int naluUnitSize = byteBuffer.getInt();
            byte[] unit = new byte[naluUnitSize];
            byteBuffer.get(unit);
            ByteBuffer unitBuffer = ByteBuffer.wrap(unit);

            //sei type
            if ((int) unitBuffer.get() != NALU_TYPE_SEI) {
                return null;
            }
            //pay load type
            if ((int) unitBuffer.get() != SEI_PAY_LOAD_TYPE) {
                return null;
            }
            //pay load size
            int payLoadSize = unitBuffer.get();
            byte[] seiContent = new byte[payLoadSize];
            unitBuffer.get(seiContent);
            seiList.add(new String(seiContent));
            //0x80 结尾
            byte rbspTrailingBits = unitBuffer.get();
        }
        return seiList;
    }
}
