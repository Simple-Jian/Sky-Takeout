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

#公共字段的自动填充,如createTime updateUser等字段是公共的,需要设置值,这是可以使用AOP设置
1.自定义注解AutoFil,用于标识需要进行公共字段自动填充的方法
2.自定义切面类,统一拦截加入了@AutoFill注解的方法,通过反射为公共字段赋值

#Redis 一个基于内存的key-value结构数据库
  优点:基于内存存储,读写性能高
      适合存储热点数据(热点商品,资讯,新闻)
      企业应用广泛

      1.下载与安装
      2.启动,在cmd中输入redis-server.exe (配置文件名)redisWindows.config
      3.另外打开一个cmd窗口,输入redis-cli.exe  连接客户端
        keys *查看当前列表是否有数据
        exit退出
        默认客户端会连接本地服务
        连接其它服务 redis-cli.exe -h localhost -p 6379 -a密码 指定即可
        修改密码:配置文件中指定requirepass 密码(已设置为123456)

#Redis中的数据类型
  5种常用数据类型:key-value类型,其中key是字符串类型,value有五种常用的数据类型:
  字符串string
  哈希hash 也叫散列 类似于java中的HashMap结构
  列表list 按照插入顺序排序,可以有重复元素,类似与java中的LinkedList
  集合set 无序集合,没有重复元素,类似于HashSet
  有序集合sorted set/zset 集合中每一个元素关联一个分数(score),根据分数升序排列,没有重复元素

#Redis中的常用命令
  1.字符串操作命令
  SET key value    设置指定key的值
  GET key     获取指定key的值
  SETEX key seconds value  设置指定key的值,并将key的过期时间设置为second秒(例如短信验证码的有效时间)
  SETNX key value    只有key不存在时设置key的值

  2.哈希操作命令(类似HashMap)
    HSET key field value
    HGET key field
    HDEL key field
    HKEYS key 获取哈希表中所有的字段
    HVALS key 获取哈希表中所有的值

  3.列表操作命令(类似LinkedList)
    LPUSH key value1 value2...   从左侧(从头部插入)一个或多个值
    LRANGE key start stop    获取列表指定范围的元素
    RPOP key            移除并获取列表最后一个元素
    LLEN key            获取列表长度

  4.集合set操作命令(String类型的无序集合,类似HashSet)
  SADD key member1 member2  添加一个或多个成员
  SMEMBERS key      返回集合中的所有成员
  SCARD key         获取集合中的成员数
  SINTER key1 key2  返回给定所有集合的交集
  SUNION key1 key2  返回并集
  SREM key member1 member2   删除集合中一个或多个成员

  5.有序集合sorted set/zset常用操作命令(按照分数升序排列)
  ZADD key score1[member1] score2[member2]     向有序集合中添加一个或多个成员
  ZRANGE key start stop [WITHSCORES]     通过索引区间返回有序集合中指定区间内的成员
  ZINCRBY key increment member           有序集合中对指定成员的分数加上增量increment
  ZREM key member1 member2               移除有序集合中的一个或多个成员

  6.Redis通用命令(不分数据类型,都可以使用的命令)
  KEYS pattern      查找所有给定模式pattern的key *为通配符: s*以s开头的keys
  EXISTS key        检查给定key是否存在
  TYPE key         返回key所存储值的类型
  DEL key             该命令用于在key存在时删除key

#在java中操作redis
 redis的java客户端:多种,Jedis  Lettuce
 这里使用spring data redis,它是spring的一部分,对redis底层开发包做了高度封装
 在spring项目中,可以使用Spring Data Redis来简化操作

 操作步骤:
 1.导入Spring Data Redis的坐标
  <!--spring data redis依赖-->
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-data-redis</artifactId>
   </dependency>

 2.配置Redis数据源
 spring:
   redis:
       host: localhost
       port: 6379
       password: 123456
       database: 0       #指定使用哪个数据库,一般有16个,默认为0,这里使用db0,不同数据库之间完全隔离

 3.编写配置类,创建Redis Template对象
@Configuration
@Slf4j
public class RedisConfig {
    //创建一个RedisTemplate对象
    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory){
        log.info("开始创建redis template对象...");
        RedisTemplate redisTemplate = new RedisTemplate();
        //设置redis连接工厂对象
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        //设置redis key的序列化器,使用字符串类型的key序列化器
        redisTemplate.setKeySerializer(new StringRedisSerializer(StandardCharsets.UTF_8));
        return redisTemplate;
    }
}
 4.通过RedisTemplate对象操作Redis
   先获取操作对象
   @AutoWired
   private RedisTemplate redisTemplate;
   ...
    ValueOperations valueOperations = redisTemplate.opsForValue(); //操作字符串类型数据
    HashOperations hashOperations = redisTemplate.opsForHash();   //hash类型
    ListOperations listOperations = redisTemplate.opsForList();   //list类型
    SetOperations setOperations = redisTemplate.opsForSet();      //set类型
    ZSetOperations zSetOperations = redisTemplate.opsForZSet();   //有序集合zset类型
   再进行操作:
   a.string类型
    //set get setex setnx
     ValueOperations valueOperations = redisTemplate.opsForValue();
     valueOperations.set("city","Beijing");
     Object city = valueOperations.get("city");
     System.out.println(city);
     //setex
     valueOperations.set("code",1234,30, TimeUnit.SECONDS);
     //setnx
     System.out.println(valueOperations.setIfAbsent("city", "重庆"));

   b.哈希操作
   HashOperations hashOperations = redisTemplate.opsForHash();
           //  HSET key field value
           hashOperations.put("jql","name","jianqlin");
           hashOperations.put("jql","age",22);
           hashOperations.put("jql","hobby","riding");
           //    HGET key field
           System.out.println(hashOperations.get("jql", "age"));
           //    HDEL key field
           hashOperations.delete("jql","hobby");
           //    HKEYS key 获取哈希表中所有的字段
           Set jql = hashOperations.keys("jql");
           System.out.println(jql);
           //    HVALS key 获取哈希表中所有的值
           List jql1 = hashOperations.values("jql");
           System.out.println(jql1);
    c.其它...
      public void testPublicMethods(){
            //keys pattern
            Set keys = redisTemplate.keys("*");
            System.out.println(keys);
            //exists key
            System.out.println(redisTemplate.hasKey("jql"));
            //type key
            System.out.println(redisTemplate.type("jql"));
            //del key
            System.out.println(redisTemplate.delete("nameee"));
        }

# 店铺营业状态设置(Redis)
  由于店铺营业状态只有一个字段,因此存在redis中比较合适

#HttpClient
 HttpClient是Apache Jakarta Common下的子项目,可以用来提供高效的,最新的,功能丰富的支持HTTP协议的
 客户端编程工具包(即可以在java程序中发送http请求),并且它支持HTTP协议最新的版本和建议

 使用步骤:
 1.导入依赖,这个工具包被阿里云OSS依赖了,因此可以传递过来
   <!--HttpClient工具包-->
   <dependency>
       <groupId>org.apache.httpcomponents</groupId>
       <artifactId>httpclient</artifactId>
   </dependency>

 2.核心API
  HttpClient(接口)
  HttpClients(相当于一个构建器,可以用来创建一个HttpClient对象)
  CloseableHttpClient(实现了HttpClient接口)
  HttpGet
  HttpPost
  使用:1 创建HttpClient对象 2 创建Http请求对象 3 调用HttpClient的execute方法发送请求
   @Test
      public void testGet() throws IOException {
          //创建HttpClient接口实现类CloseableHttpClient对象
          CloseableHttpClient httpClient=HttpClients.createDefault();
          //创建请求对象
          HttpGet httpGet = new HttpGet("http://localhost:8080/user/shop/status");
          //发送请求,接收响应结果
          CloseableHttpResponse res = httpClient.execute(httpGet);

          //获取返回状态码
          System.out.println(res.getStatusLine().getStatusCode());
          HttpEntity entity = res.getEntity();   //响应体对象
          //使用HttpClient的工具类解析响应体对象为字符串
          System.out.println(EntityUtils.toString(entity));

          //关闭资源
          res.close();
          httpClient.close();
      }
      @Test
      public void testPost() throws IOException {
          //创建HttpClient接口实现类CloseableHttpClient对象
          CloseableHttpClient httpClient=HttpClients.createDefault();
          //创建请求对象
          HttpPost httpPost = new HttpPost("http://localhost:8080/admin/employee/login");
          //封装响应体
          //使用fastJSON创建一个Json对象
          JSONObject jsonObject=new JSONObject();
          jsonObject.put("username","admin");
          jsonObject.put("password","123456");

          StringEntity entity = new StringEntity(jsonObject.toString());
          //指定请求的编码方式
          entity.setContentEncoding("UTF-8");
          //指定数据格式
          entity.setContentType("application/json");
          //设置响应体
          httpPost.setEntity(entity);
          //发送请求,接收响应结果
          CloseableHttpResponse res = httpClient.execute(httpPost);

          //获取返回状态码
          System.out.println(res.getStatusLine().getStatusCode());
          HttpEntity resEntity = res.getEntity();   //响应体对象
          //使用HttpClient的工具类解析响应体对象为字符串
          System.out.println("响应数据为:"+EntityUtils.toString(resEntity));

          //关闭资源
          res.close();
          httpClient.close();
      }

#使用Redis缓存菜品
  思路:
  开始--(查询菜品)->后端服务--(检查缓存是否存在)--(No)-->读取数据库-->判断是否添加缓存
                                 |
                                 --(Yes)-->读取缓存

  逻辑:每个分类的菜品保存一份缓存数据
      数据库中菜品数据有变更时清理缓存数据


#Spring Cache
   Spring Cache是一个框架,实现了基于注解的缓存功能,只需要简单地加一个注解,就能实现缓存功能,
   Spring Cache提供了一层抽象,底层可以切换不同的缓存实现,例如EHCache,Caffeine,Redis,
   换言之,这套注解对不同的缓存实现产品是通用的

  1.常用注解
  @EnableCaching 开启缓存注解功能,通常加在启动类上
  @Cacheable   在方法执行前先查询缓存中是否有数据,如果有数据,则直接返回缓存数据,如果没有缓存
  数据,调用方法将方法返回值放到缓存中
  @CachePut 将方法产生的返回值放到缓存中
  @CacheEvict  将一条或多条数据从缓存中删除

  2. 使用步骤:
  a.导入spring cache和Redis相关的maven坐标
   <!--spring data redis依赖-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-redis</artifactId>
    </dependency>
    <!--spring cache的依赖-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-cache</artifactId>
    </dependency>
  b.在启动类上加入@EnableCaching注解...

  3.SpEL(Spring Expression Language),一种spring的表达式语法,可以动态获得数据
  例如:"#user(形参名字).id" "result(引用方法返回值).name"
       "#p0/a0.id/#root.args[0].id"(第一个形参的id属性),

    @CachePut(cacheNames ="userCache", key="#user.id")
      //SpEL(Spring Expression Language)语法,可以动态获取
      //将方法返回值放到缓存中,key的生成规则是:userCache(缓存名)::xxx(user.id)
      其中':'表示一层Redis中的一层目录结构

    /**
         * 使用springCache注解使用缓存查询数据
         * @param id
         * @return
         */
        @Cacheable(cacheNames = "userCache",key="#id")
        @GetMapping
        public User getById(Long id){
            User user = userMapper.getById(id);
            return user;
        }

删除目录下所有:@CacheEvict(cacheNames="userCache" allEntries=true)
/**
     * 当数据库中的数据被删除时,相应缓存数据也要被清理
     * @param id
     */
    @CacheEvict(cacheNames = "userCache",key="#Id")
    @DeleteMapping
    public void deleteById(Long id){
        userMapper.deleteById(id);
    }
      Spring Cache基于代理技术,程序执行时,会进入该方法的代理对象,如果能查到就直接返回,因此可以
  即使在方法中加入断点,如果有缓存也能查询成功,因为首先使用的是代理对象,如果没有缓存,才会使用
  反射调用原方法

  总结:spring cache的使用核心是良好地管理和使用缓存对象的名称


