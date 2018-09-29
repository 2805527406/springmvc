package com.demo.shiro;

import java.util.Collection;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import com.demo.redis.RedisUtil;

@Component
public class RedisCache<K,V> implements Cache<K, V>{
	private final String CACHE_FREFIX="cache_";
	@Autowired
	private RedisUtil redisUtil;
	private byte[] getKey(K k) {
		if(k instanceof String) {
			return (CACHE_FREFIX+k).getBytes();
		}
		return SerializationUtils.serialize(k);
	}
	@Override
	public void clear() throws CacheException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public V get(K k) throws CacheException {
		byte[] value = (byte[]) redisUtil.get(new String(getKey(k)));
		if(value != null) {
			return (V) SerializationUtils.deserialize(value);
		}
		return null;
	}

	@Override
	public Set<K> keys() {
		Set<K> keys = redisUtil.hkeys("CACHE_FREFIX"+"*");
		return keys;
	}

	@Override
	public V put(K arg0, V arg1) throws CacheException {
		byte[] key = getKey(arg0);
		byte[] value = (byte[]) redisUtil.get(new String(key));
		redisUtil.set(new String(key), value,600L);
		return arg1;
	}

	@Override
	public V remove(K arg0) throws CacheException {
		byte[] key = getKey(arg0);
		byte[] value = (byte[]) redisUtil.get(new String(key));
		redisUtil.remove(new String(key));
		if(value != null) {
			return (V) SerializationUtils.deserialize(value);
		}
		return null;
	}

	@Override
	public int size() {
		return keys().size();
	}

	@Override
	public Collection<V> values() {
		// TODO Auto-generated method stub
		return null;
	}


}
