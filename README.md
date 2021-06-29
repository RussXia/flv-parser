# Flv Parser
flv文件由两部分组成：FLVHeader + Body,

其中Body又由一个个tag组成，其中tag分为三种：1.script、video、audio等。

每个tag由：previousTagSize + Tag Header + data 三部分组成 
![flv协议内容](https://raw.githubusercontent.com/RussXia/flv-parser/master/doc/pic/flv-format.jpg)

## 简介
+ 支持`HTTP FLV`动态拉流分析
+ 不需要分析全部内容，如只想解析第一个video tag，迭代`FLVTagIterator`拿到`Tag Header`的type是9(视频)的即可。
+ 解析结果为 `FLVTag`，内包含`previousTagSize`、`header`、`data`
+ 构造 `FLVTagIterator`时，会先拉取最前面的9个字节，判断当前文件是否为flv文件

## 解析sei
![video类型数据](https://raw.githubusercontent.com/RussXia/flv-parser/master/doc/pic/video-data.png)

| Field           | Type   | Comment                                                      |
| --------------- | ------ | ------------------------------------------------------------ |
| AVC packet 类型 | UI8    | 0-AVC序列头;1：AVC NALU单元; 2：AVC序列结束。低级别avc不需要。 |
| CTS             | SI24   | 如果 AVC packet 类型是 1，则为 cts 偏移(时间)，为 0 则为 0   |
| 数据            | UI8[n] | 如果AVC packet类型是0，则是解码器配置，sps，pps。 如果是1，则是nalu单元，可以是多个 |

解析video data中的SEI,就是指，解析frame_type为1/2，codec_id为7；
avc packet为1时，NALU_TYPE为6时，此时此数据即为sei数据。

| NALU_TYPE          | 0x06 表示sei类型                          |
| ------------------ | ----------------------------------------- |
| SEI Payload Type   | 0x05 表示遵循user_data_unregistered()语法 |
| SEI Payload Size   | 长度，等于uuid_size + content_size        |
| SEI Content        | SEI内容                                   |
| RBSP trailing bits | NAL unit的结尾默认以0x80结尾              |