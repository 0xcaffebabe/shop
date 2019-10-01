# shop
 
 - 服务注册中心
 - 服务网关
 
 ## 商品服务
 
 需要注意的是，我们的item是一个微服务，那么将来肯定会有其它系统需要来调用服务中提供的接口，因此肯定也会使用到接口中关联的实体类
 我们会在ly-item中创建两个子工程：
 
 - item-interface：主要是对外暴露的接口及相关实体类
 - item-service：所有业务逻辑及内部使用接口
 
 ### 商品详情页
 我们已知的条件是传递来的spu的id，我们需要根据spu的id查询到下面的数据：
 
 - spu信息
 - spu的详情
 - spu下的所有sku
 - 品牌
 - 商品三级分类
 - 商品规格参数、规格参数组
 
#### 静态化
静态化是指把动态生成的HTML页面变为静态内容保存，以后用户的请求到来，直接访问静态页面，不再经过服务的渲染。

而静态的HTML页面可以部署在nginx中，从而大大提高并发能力，减小tomcat压力。
 
 ## 上传服务
 
- controller
     - 请求方式：上传肯定是POST
     - 请求路径：/upload/image
     - 请求参数：文件，参数名是file，SpringMVC会封装为一个接口：MultipleFile
     - 返回结果：上传成功后得到的文件的url路径

- service
    - 校验文件大小
    - 校验文件的媒体类型
    - 校验文件的内容
- 绕过网关

### 分布式文件系统

- 传统文件系统管理的文件就存储在本机。
- 分布式文件系统管理的文件存储在很多机器，这些机器通过网络连接，要被统一管理。无论是上传或者访问文件，都需要通过管理中心来访问

### 商品规格数据结构

- SPU：Standard Product Unit （标准产品单位） ，一组具有共同属性的商品集
- SKU：Stock Keeping Unit（库存量单位），SPU商品集因具体特性不同而细分的每个商品

## 搜索服务

搜索系统需要优化的点：

- 查询规格参数部分可以添加缓存
- 聚合计算interval变化频率极低，所以可以设计为定时任务计算（周期为天），然后缓存起来。
- elasticsearch本身有查询缓存，可以不进行优化
- 商品图片应该采用缩略图，减少流量，提高页面加载速度
- 图片采用延迟加载
- 图片还可以采用CDN服务器
- sku信息应该在页面异步加载，而不是放到索引库

## 短信服务

通过消息队列异步进行发送

## 授权中心

![](https://gitee.com/caffebabee/leyou/raw/master/day17-%E6%8E%88%E6%9D%83%E4%B8%AD%E5%BF%83/assets/1527300483893.png)

![](https://gitee.com/caffebabee/leyou/raw/master/day17-%E6%8E%88%E6%9D%83%E4%B8%AD%E5%BF%83/assets/1527312464328.png)

## 购物车服务

![](https://gitee.com/caffebabee/leyou/raw/master/day18-%E8%B4%AD%E7%89%A9%E8%BD%A6/assets/1527585343248.png)
首先不同用户应该有独立的购物车，因此购物车应该以用户的作为key来存储，Value是用户的所有购物车信息。这样看来基本的k-v结构就可以了。
但是，我们对购物车中的商品进行增、删、改操作，基本都需要根据商品id进行判断，为了方便后期处理，我们的购物车也应该是k-v结构，key是商品id，value才是这个商品的购物车信息。

## 订单服务

- 雪花算法

![](https://gitee.com/caffebabee/leyou/raw/master/day19-%E4%B8%8B%E5%8D%95/assets/1528729105237.png)

- 微信支付

![](https://pay.weixin.qq.com/wiki/doc/api/img/chapter6_5_1.png)





