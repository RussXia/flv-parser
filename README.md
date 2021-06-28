# Flv Parser

## 简介
+ 支持`HTTP FLV`动态拉流分析
+ 解析结果为 `FLVTag`，内包含`previousTagSize`、`header`、`data`
+ 构造 `FLVTagIterator`时，会先拉取最前面的9个字节，判断当前文件是否为flv文件