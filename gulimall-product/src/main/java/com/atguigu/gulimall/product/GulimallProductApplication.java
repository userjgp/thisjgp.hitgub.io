package com.atguigu.gulimall.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * 1、整合MyBatis-Plus
 *      1）、导入依赖
 *      <dependency>
 *             <groupId>com.baomidou</groupId>
 *             <artifactId>mybatis-plus-boot-starter</artifactId>
 *             <version>3.2.0</version>
 *      </dependency>
 *      2）、配置
 *          1、配置数据源；
 *              1）、导入数据库的驱动。https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-versions.html
 *              2）、在application.yml配置数据源相关信息
 *          2、配置MyBatis-Plus；
 *              1）、使用@MapperScan
 *              2）、告诉MyBatis-Plus，sql映射文件位置
 *
 * 2、逻辑删除
 *  1）、配置全局的逻辑删除规则（省略）
 *  2）、配置逻辑删除的组件Bean（省略）
 *  3）、给Bean加上逻辑删除注解@TableLogic
 *
 * 3、JSR303
 *   1）、给Bean添加校验注解:javax.validation.constraints，并定义自己的message提示
 *   2)、开启校验功能@Valid
 *      效果：校验错误以后会有默认的响应；
 *   3）、给校验的bean后紧跟一个BindingResult，就可以获取到校验的结果
 *   4）、分组校验（多场景的复杂校验）
 *         1)、	@NotBlank(message = "品牌名必须提交",groups = {AddGroup.class,UpdateGroup.class})
 *          给校验注解标注什么情况需要进行校验
 *         2）、@Validated({AddGroup.class})
 *         3)、默认没有指定分组的校验注解@NotBlank，在分组校验情况@Validated({AddGroup.class})下不生效，只会在@Validated生效；
 *
 *   5）、自定义校验
 *      1）、编写一个自定义的校验注解
 *      2）、编写一个自定义的校验器 ConstraintValidator
 *      3）、关联自定义的校验器和自定义的校验注解
 *      @Documented
 * @Constraint(validatedBy = { ListValueConstraintValidator.class【可以指定多个不同的校验器，适配不同类型的校验】 })
 * @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
 * @Retention(RUNTIME)
 * public @interface ListValue {
 *
 * 4、统一的异常处理
 * @ControllerAdvice
 *  1）、编写异常处理类，使用@ControllerAdvice。
 *  2）、使用@ExceptionHandler标注方法可以处理的异常。
 *
 *  5.模板引擎
 *  1）.thymeleaf-starter:关闭缓存
 *  2).静态资源放在static文件夹下
 *  3）。页面放在templates，直接访问
 *  springboot访问时自动找index
 *  6.整合redis
 *  引入data-redis-starter
 *  配置host等信息
 *  使用stringboot配置好的stringredistemplate来操作redis，存放数据key，数据值value
 *
 *
 *  7.整合redisson作为分布式锁等功能框架
 *  引入依赖
 *  配置redisson
 *  myredissonconfig给容器中配置一个redissonclient实例即可
 *  参照文档使用
 *  8.引入springcache简化缓存开发
 *  引入依赖  spring-boot-start-cache
 *  写配置
 *      自动配置
 *          cacheautoconfigration会导入RediscacheConfigration
 *          自动配好了缓存管理器
 *      配置使用redis作为缓存
 *   测试使用
 * @cacheable:触发将数据保存到缓存
 * @cacheevict：触发将数据从缓存删除
 * @cacheput：不影响方法执行更新缓存
 * @caching：组合以上多个操作
 * @cacheconfig：在类级别共享缓存的相同配置
 * 开启缓存 启动类上@Enablecache
 * 只需要使用注解
 *
 * 原理
 *   cacheautoconfigration会导入RediscacheConfigration -》
 *   自动配置了Rediscachemanager 会初始化所有的缓存-》每个缓存决定使用什么配置
 *   -》RediscacheConfigration有就用已有的，否则用默认配置-》想改缓存配置，只需要给容器放一个RediscacheConfigration
 *   就会应用到当前RediscacheConfigration管理的所有缓存分区中
 *
 *
 *   spring-cache的不足
 *   读模式：
 *   缓存穿透：查询一个空数据，解决：缓存空数据
 *   缓存击穿：大并发进来同时查询一个正好过期的数据 解决：加锁？
 *   缓存雪崩：大量的key同时过期 解决：加随机时间
 *
 *   写模式：（缓存与数据库一致）
 *   读写加锁
 *   引入canal，感知mysql的更新去更新数据库
 *   读多写多：直接去数据库查询
 *
 *   总结：
 *   常规数据（读多写少，及时性一致性不高的数据）：完全可以用spring-cache 写模式（配置一个随机失效时间）
 *   特殊数据：特殊处理
 *
 *
 */

@EnableRedisHttpSession     //开启springsession
@EnableCaching
@EnableFeignClients(basePackages = "com.atguigu.gulimall.product.feign")
@EnableDiscoveryClient
@SpringBootApplication
@EnableTransactionManagement
public class GulimallProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallProductApplication.class, args);
    }

}
