import flv.FLVTag;
import parser.FLVTagIterator;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

/**
 * User: RuzzZZ
 * Date: 2021/6/28
 * Time: 19:41
 */
public class DemoMain {

    public static void main(String[] args) throws IOException {
        String urlStr = "http://1251952132.vod2.myqcloud.com/0ebf8881vodcq1251952132/069285f23701925919166709792/f0.flv";
        URL url = new URL(urlStr);
        BufferedInputStream bis = new BufferedInputStream(url.openStream());
        //解析tag
        FLVTagIterator flvTagIterator = new FLVTagIterator(bis);
        while (flvTagIterator.hasNext()) {
            FLVTag tag = flvTagIterator.next();
            //提取里面的视频信息
            if (Objects.equals(tag.getHeader().getType(), (byte)9)) {
                System.out.println(new String(tag.getData()));
            }
        }
    }
}
