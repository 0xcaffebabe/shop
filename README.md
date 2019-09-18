# shop
 
 - 服务注册中心
 - 服务网关
 
 ## 商品服务
 
 需要注意的是，我们的item是一个微服务，那么将来肯定会有其它系统需要来调用服务中提供的接口，因此肯定也会使用到接口中关联的实体类
 我们会在ly-item中创建两个子工程：
 
 - item-interface：主要是对外暴露的接口及相关实体类
 - item-service：所有业务逻辑及内部使用接口
 
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
