package com.demo.redis;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.SerializationUtils;

public class RedisSessionDao extends AbstractSessionDAO{

	@Autowired
	private RedisUtil redisUtil;
	private final String SHIRO_SESSION_PREFIX="resession_";
	
	private byte[] getKey(String key) {
		return (SHIRO_SESSION_PREFIX + key).getBytes();
	}
	
	private void saveSession(Session session) {
		if(session != null && session.getId() != null) {
			Serializable sessionId = generateSessionId(session);
			byte[] key = getKey(session.getId().toString());
			byte[] value = SerializationUtils.serialize(session);
			redisUtil.set(new String(key), value,600L);
		}
	}
	@Override
	public void update(Session session) throws UnknownSessionException {
		saveSession(session);
	}

	@Override
	public void delete(Session session) {
		if(session == null || session.getId() == null) {
			return;
		}
		byte[] key  = getKey(session.getId().toString());
		redisUtil.remove(new String(key));
	}

	@Override
	public Collection<Session> getActiveSessions() {
		Set<byte[]> keys = redisUtil.hkeys(SHIRO_SESSION_PREFIX+"*");
		Set<Session> sessions = new HashSet<>();
		if(CollectionUtils.isEmpty(keys)) {
			return sessions;
		}
		for(byte[] key:keys) {
			Session session  = (Session) SerializationUtils.deserialize((byte[]) redisUtil.get(new String(key)));
			sessions.add(session);
		}
		return sessions;
	}

	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = generateSessionId(session);
		assignSessionId(session, sessionId);
		byte[] key = getKey(session.getId().toString());
		byte[] value = SerializationUtils.serialize(session);
		redisUtil.set(new String(key), value,600L);
		return sessionId;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		if(sessionId == null) {
			return null;
		}
		byte[] key = getKey(sessionId.toString());
		final String obj = new String(key);
		byte[] value = (byte[]) redisUtil.get(obj);
		return (Session) SerializationUtils.deserialize(value);
	}

}
