# Spring boot cache



## 一. 相关注解

- @EnableCaching : 开关注解,在项目启动类或某个配置类上使用此注解后，则表示允许使用注解的方式进行缓存操作
- @Cacheable : 在执行目标方法前，如果从缓存中查询到了数据，那么直接返回缓存中的数据；如果从 缓存中没有查询到数据，那么执行目标方法，目标方法执行完毕之后，判断unless的结果，若unless的结果为true，那么不缓存方法的返回值；若unless的结果为false，那么缓存方法的返回值。
- @CacheEvict : 可用于类或方法上；在执行完目标方法后，清除缓存中对应key的数据(如果缓存中有对应key的数据缓存的话)
- @CachePut : 在目标方法执行完毕之后，判断unless的结果，若unless的结果为true，那么不缓存方法的返回值；若unless的结果为false，那么缓存方法的返回值。
- @Caching : 
- @CacheConfig : 配置一些公共属