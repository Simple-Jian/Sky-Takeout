一
nginx反向代理的好处:
1.提高访问速度,对同一个请求,可以缓存
2.进行负载均衡:就是把大量请求按照我们指定的方式均匀分配给集群中的每一台服务器
3.保证后端服务安全,隐藏后端服务地址,可以用Nginx走内网连接后端,外界无法直接访问后端服务地址,
进一步保障安全

nginx反向代理和负载均衡配置方式
在nginx.config文件中配置...

负载均衡策略:
轮询:默认方式,轮流访问
weight:权重方式,默认为1
ip_hash:根据ip分配方式,这样每个访客可以固定访问一个后端服务
least_conn:根据最少连接的方式,把请求优先分配给连接少的后端服务
url_hash:根据url分配方式,相同Url会被分配到同一个后端服务
fair:根据响应时间方式,响应时间短的服务将会被优先分配

#md5加密方式,使用一定算法单向加密,即无法通过结果反推初始值,达到加密敏感信息的目的,此处用来加密
密码

#YAPI网站,导入,打开接口文档

#Swagger  帮助后端生成接口文档和在线接口测试
      Knife4j是为java MVC框架集成Swagger生成API文档的增强解决方案
     使用:1导入坐标
      <dependency>
          <groupId>com.github.xiaoymin</groupId>
          <artifactId>knife4j-spring-boot-starter</artifactId>
          <version>${knife4j}</version>
      </dependency>
       2.配置
       3.设置静态资源映射,否则接口文档页面无法访问
       4.访问localhost:8080/doc.html,即可进行测试

   常用注解:@Api,用在类上,例如Controller,表示对类的说明
           @ApiModel,用在类上,例如Entity,DTO,VO
           @ApiModelProperty,用在属性上,描述属性信息
           @ApiOperation,用在方法上,例如Controller方法,说明方法的用途,作用

#ThreadLocal并不是一个线程,而是Thread的局部变量
 ThreadLocal为每个线程提供单独一份存储空间,具有线程隔离的效果,只有在线程内才能获取到对应的值,
 线程外则不能访问.每一条请求都是一个单独的线程,拥有不同的id

 常用方法:
 public void set(T value)   设置当前线程局部变量的值
 public T get()             返回当前线程所对应的线程局部变量的值
 public void remove         移除当前线程的线程局部变量

通过这个方法可以在拦截器中解析令牌含有的处理人信息,将其设置在局部变量中