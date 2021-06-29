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
+ 解析video tag中的SEI


| Field           | Type   | Comment                                                      |
| --------------- | ------ | ------------------------------------------------------------ |
| AVC packet 类型 | UI8    | 0-AVC序列头;1：AVC NALU单元; 2：AVC序列结束。低级别avc不需要。 |
| CTS             | SI24   | 如果 AVC packet 类型是 1，则为 cts 偏移(见下面的解释)，为 0 则为 0 |
| 数据            | UI8[n] | 如果AVC packet类型是0，则是解码器配置，sps，pps。 如果是1，则是nalu单元，可以是多个 |

