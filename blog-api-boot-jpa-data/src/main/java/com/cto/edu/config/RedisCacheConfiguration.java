package com.cto.edu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.cache.RedisCacheManager.RedisCacheManagerBuilder;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * redis 自定义缓存管理器
 */
@SpringBootConfiguration
@EnableCaching
@ComponentScan("com.cto.edu")
public class RedisCacheConfiguration extends CachingConfigurerSupport {

	@Autowired
	private RedisConnectionFactory connectionFactory;

	/**
	 * 缓存管理器
	 * @return CacheManager
	 */
	@Bean
	public CacheManager cacheManager() {
		RedisCacheManagerBuilder builder = RedisCacheManagerBuilder.fromConnectionFactory(connectionFactory);
		Set<String> cacheNames = new HashSet<String>() {{
			add("codeNameCache");
		}};
		builder.initialCacheNames(cacheNames);
		return builder.build();

	}

	/**
	 * @description 自定义的缓存key的生成策略</br>
	 *              若想使用这个key</br>
	 *              只需要讲注解上keyGenerator的值设置为customKeyGenerator即可</br>
	 * @return 自定义策略生成的key
	 */
	@Bean
	public KeyGenerator customKeyGenerator() {
		return (o, method, objects) -> {
			StringBuilder sb = new StringBuilder();
			sb.append(o.getClass().getName());
			sb.append(method.getName());
			for (Object obj : objects) {
				sb.append(obj.toString());
			}
			return sb.toString();
		};
	}


}