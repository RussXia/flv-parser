package flv;


import lombok.Builder;
import lombok.Getter;

/**
 * User: RuzzZZ
 * Date: 2021/6/28
 * Time: 19:41
 */
@Getter
@Builder
public class FLVTag {

    private final TagHeader header;

    private final byte[] data;

    private final int previousTagSize;
}
