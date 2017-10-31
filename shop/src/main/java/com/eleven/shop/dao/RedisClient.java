package com.eleven.shop.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
@Component
public class RedisClient {
	@Resource
	public RedisTemplate<String, Object> redisTemplate;

	@Resource
	protected RedisTemplate<Serializable, Serializable> redisTemplateSerializable;

	/**
	 * 缓存基本的对象，integer，string、实体类等
	 */

	public void setCacheObject(String key, Object value) {
		redisTemplate.opsForValue().set(key, value);
		
		
	}
	

	/**
	 * 获得缓存的基本对象
	 * 
	 * @param key
	 * @return
	 */
	public Object getCacheObject(String key) {
		return redisTemplate.opsForValue().get(key);
	}

	/**
	 * 缓存list数据
	 * 
	 * @param key
	 * @param dataList
	 * @return
	 */
	public Object setCacheList(String key, List<Object> dataList) {
		ListOperations<String, Object> listOperations = redisTemplate.opsForList();
		if (dataList != null) {
			int size = dataList.size();
			for (int i = 0; i < size; i++) {
				listOperations.rightPush(key, dataList.get(i));
			}
		}
		return listOperations;
	}

	/**
	 * 获得缓存的list对象
	 * 
	 * @param key
	 * @return
	 */
	public List<Object> getCacheList(String key) {
		List<Object> dataList = new ArrayList<Object>();
		ListOperations<String, Object> listOperations = redisTemplate.opsForList();
		long size = listOperations.size(key);
		for (int i = 0; i < size; i++) {
			dataList.add(listOperations.leftPop(key));
		}

		return dataList;
	}

	/**
	 * 获取list范围内的值
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Object> rangeGetList(String key, int start, int end) {
		ListOperations<String, Object> listOperations = redisTemplate.opsForList();
		return listOperations.range(key, start, end);
	}

	/**
	 * 获取list的长度
	 * 
	 * @param key
	 * @return
	 */
	public Long listSize(String key) {
		return redisTemplate.opsForList().size(key);
	}

	public void listSet(String key, int index, Object object) {
		redisTemplate.opsForList().set(key, index, object);
	}

	/**
	 * 向list头部追加记录
	 * 
	 * @param key
	 * @param obj
	 * @return
	 */
	public long leftPush(String key, Object obj) {
		return redisTemplate.opsForList().leftPush(key, obj);
		
	}

	/**
	 * 向list尾部追加记录
	 * 
	 * @param key
	 * @param object
	 * @return
	 */
	public long rightPush(String key, Object object) {
		return redisTemplate.opsForList().rightPush(key, object);
	}

	/**
	 * 截取list
	 * 
	 * @param key
	 * @param start
	 * @param end
	 */
	public void trim(String key, int start, int end) {
		redisTemplate.opsForList().trim(key, start, end);
	}

	/**
	 * 删除list集合中count条记录值尾
	 * 
	 * @param key
	 * @param count
	 * @param object
	 * @return
	 */
	public long listRemove(String key, long count, Object value) {
		return redisTemplate.opsForList().remove(key, count, value);
	}

	/**
	 * 缓存set数据
	 * 
	 * @param key
	 * @param dateSet
	 * @return
	 */
	public BoundSetOperations<String, Object> setCacheSet(String key, Set<Object> dateSet) {
		BoundSetOperations<String, Object> setOperations = redisTemplate.boundSetOps(key);
		Iterator<Object> iterator = dateSet.iterator();
		while (iterator.hasNext()) {
			setOperations.add(iterator.next());
		}
		return setOperations;
	}

	public Set<Object> getCacheSet(String key) {
		Set<Object> dataSet = new HashSet<Object>();
		BoundSetOperations<String, Object> operations = redisTemplate.boundSetOps(key);
		long size = operations.size();
		for (int i = 0; i < size; i++) {
			dataSet.add(operations.pop());
		}
		return dataSet;
	}

	/**
	 * 缓存map
	 * 
	 * @param key
	 * @param dataMap
	 * @return
	 */
	public int setCacheMap(String key, Map<String, Object> dataMap) {
		if (dataMap != null) {
			HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
			for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
				if (hashOperations != null) {
					hashOperations.put(key, entry.getKey(), entry.getValue());
				} else {
					return 0;
				}
			}
		} else {
			return 0;
		}
		return dataMap.size();
	}

	/**
	 * 获取缓存中的key
	 * 
	 * @param key
	 * @return
	 */
	public Map<Object, Object> getCacheMap(String key) {
		Map<Object, Object> map = redisTemplate.opsForHash().entries(key);
		return map;

	}

	/**
	 * 删除缓存中的map
	 * 
	 * @param key
	 * @return
	 */
	public long deleteMap(String key) {
		// redisTemplate.setEnableTransactionSupport(true);
		return redisTemplate.opsForHash().delete(key);
	}

	/**
	 * 设置过期时间
	 * 
	 * @param key
	 * @param time
	 * @param timeUnit
	 * @return
	 */
	public boolean expire(String key, long time, TimeUnit timeUnit) {
		return redisTemplate.expire(key, time, timeUnit);
	}

	public long increment(String key, long step) {
		return redisTemplate.opsForValue().increment(key, step);
	}

	public long del(final byte[] key) {
		return (Long) redisTemplateSerializable.execute(new RedisCallback<Object>() {

			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				// TODO Auto-generated method stub
				return connection.del(key);
			}
		});
	}

	public <T> byte[] get(final byte[] key) {
		return (byte[]) redisTemplateSerializable.execute(new RedisCallback<byte[]>() {

			public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
				// TODO Auto-generated method stub
				return connection.get(key);
			}
		});
	}
	
	public void set(final byte[] key,final byte[] value,final long liveTime){
		redisTemplateSerializable.execute(new RedisCallback<Boolean>() {

			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				// TODO Auto-generated method stub
				boolean isSet=connection.set(key, value);
				if(liveTime>0){
					isSet=connection.expire(key, liveTime);
				}
				return isSet;
			}
		});
	}
}
