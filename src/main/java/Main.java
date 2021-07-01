import com.github.kevinsawicki.http.HttpRequest;
import flv.model.FLVTag;
import flv.parser.FLVTagIterator;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * User: RuzzZZ
 * Date: 2021/6/28
 * Time: 19:41
 */
public class Main {

    public static void main(String[] args) throws IOException {
        //String urlStr = "http://1251952132.vod2.myqcloud.com/0ebf8881vodcq1251952132/069285f23701925919166709792/f0.flv";
        //URL url = new URL(urlStr);
        //InputStream is = url.openStream();

        // FIXME: 2021/6/29 会因为stream.read()返回-1而提前结束，猜测http链接提前断开了？
        InputStream is = HttpRequest.get("http://1251952132.vod2.myqcloud.com/0ebf8881vodcq1251952132/069285f23701925919166709792/f0.flv")
                .readTimeout(10000).connectTimeout(10000)
                .stream();

        //File file = new File("/Users/xmly/Downloads/demo-2.flv");
        //FileInputStream is = new FileInputStream(file);

        //OkHttpClient client = new OkHttpClient();
        //Request request = new Request.Builder()
        //        .url("http://1251952132.vod2.myqcloud.com/0ebf8881vodcq1251952132/069285f23701925919166709792/f0.flv")
        //        .build();
        //InputStream is = client.newCall(request).execute().body().byteStream();

        //解析tag
        FLVTagIterator flvTagIterator = new FLVTagIterator(is);
        int count = 0;
        while (flvTagIterator.hasNext()) {
            count++;
            FLVTag tag = flvTagIterator.next();
            System.out.println("timestamp:" + tag.getHeader().getTimestamp() + ",previous tag size:" + tag.getPreviousTagSize());
            ////提取里面的视频信息
            //if (Objects.equals((int) tag.getHeader().getType(), FLV_TAG_TYPE_VIDEO)) {
            //    count++;
            //    //System.out.println(new String(tag.getData()));
            //    if (SEIParser.hasSEIInfo(tag.getData())) {
            //        System.out.println(SEIParser.parserSEI(tag.getData()));
            //    }
            //}
        }
        IOUtils.closeQuietly(is);
        System.out.println("total count :" + count);
    }
}
