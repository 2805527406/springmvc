package com.demo.redis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

/**
 * redis cache util
 * 
 */
@Component
public class RedisUtil {
	@Resource(name="redisTemplate")
	private RedisTemplate<Serializable, Object> redisTemplate;
	
	public void setRedisTemplate(
			RedisTemplate<Serializable, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	
	private Logger logger = Logger.getLogger(RedisUtil.class);

	/**
	 * ����ɾ����Ӧ��value
	 * 
	 * @param keys
	 */
	public void remove(final String... keys) {
		for (String key : keys) {
			remove(key);
		}
	}

	/**
	 * ����ɾ��key
	 * 
	 * @param pattern
	 */
	public void removePattern(final String pattern) {
		Set<Serializable> keys = redisTemplate.keys(pattern);
		if (keys.size() > 0)
			redisTemplate.delete(keys);
	}

	/**
	 * ɾ����Ӧ��value
	 * 
	 * @param key
	 */
	public void remove(final String key) {
		if (exists(key)) {
			redisTemplate.delete(key);
		}
	}

	/**
	 * �жϻ������Ƿ��ж�Ӧ��value
	 * 
	 * @param key
	 * @return
	 */
	public boolean exists(final String key) {
		return redisTemplate.hasKey(key);
	}

	/**
	 * ��ȡ����
	 * 
	 * @param key
	 * @return
	 */
	public Object get(final String key) {
		Object result = null;
		ValueOperations<Serializable, Object> operations = redisTemplate
				.opsForValue();
		result = operations.get(key);
		return result;
	}

	/**
	 * д�뻺��
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(final String key, Object value) {
		boolean result = false;
		try {
			ValueOperations<Serializable, Object> operations = redisTemplate
					.opsForValue();
			operations.set(key, value);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * д�뻺��
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(final String key, Object value, Long expireTime) {
		boolean result = false;
		try {
			ValueOperations<Serializable, Object> operations = redisTemplate
					.opsForValue();
			operations.set(key, value);
			redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * ��listβ�����
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public long rpush(final String key, Object value) {
		ListOperations<Serializable, Object> operations = redisTemplate.opsForList();
		long size = operations.rightPush(key, value);
		return size;
	}
	
	/**
	 * ��listͷ�����
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public long lpush(final String key, Object value) {
		ListOperations<Serializable, Object> operations = redisTemplate.opsForList();
		long size = operations.leftPush(key, value);
		return size;
	}
	
	/**
	 * ��ȡlist��length
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public long lsize(final String key) {
		ListOperations<Serializable, Object> operations = redisTemplate.opsForList();
		long size = operations.size(key);
		return size;
	}
	
	public List lrange(final String key,int start,int end){
		ListOperations<Serializable, Object> operations = redisTemplate.opsForList();
		List<Object> list = new ArrayList<Object>();
		list = operations.range(key, start, end);
		return list;
	}
	/**
	 * ��ϣ��Ĳ���
	 * @param key
	 * @param hashKey
	 * @param value
	 */
	public void hset(final String key,final String hashKey,Object value){
		HashOperations<Serializable,Serializable, Object> operations = redisTemplate.opsForHash();
		operations.put(key, hashKey, value);
	}
	
	public Object hget(final String key,final String hashKey){
		HashOperations<Serializable,Serializable, Object> operations = redisTemplate.opsForHash();
		Object result = null;
		result = operations.get(key, hashKey);
		return result;
	}
	
	public long hincr(final String key,final String hashKey,long delta){
		HashOperations<Serializable,Serializable, Object> operations = redisTemplate.opsForHash();
		long size = operations.increment(key, hashKey, delta);
		return size;
	}
	
	public boolean hexist(final String key,final String hashKey){
		HashOperations<Serializable,Serializable, Object> operations = redisTemplate.opsForHash();
		Boolean flag = operations.hasKey(key, hashKey);
		return flag;
	}
	
	public void hdelete(final String key,final String hashKey){
		HashOperations<Serializable,Serializable, Object> operations = redisTemplate.opsForHash();
		operations.delete(key, hashKey);
	}
	
	public long hsize(final String key){
		HashOperations<Serializable,Serializable, Object> operations = redisTemplate.opsForHash();
		return operations.size(key);
	}
	
	public Set hkeys(final String key){
		HashOperations<Serializable,Serializable, Object> operations = redisTemplate.opsForHash();
		return operations.keys(key);
	}
	
	public void sAdd(final String key, Object value) {
		SetOperations<Serializable, Object> operations = redisTemplate.opsForSet();
		operations.add(key, value);
	}
	
	public Boolean sisMember(final String key, Object value) {
		SetOperations<Serializable, Object> operations = redisTemplate.opsForSet();
		Boolean flag = operations.isMember(key, value);
		return flag;
	}

}
