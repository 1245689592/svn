package com.cm.common.redis;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.commons.lang3.SerializationUtils;
import com.cm.common.config.ConfigCommon;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.Tuple;
import redis.clients.util.Pool;

/**
 * @author ZhiJD
 *
 */
public class RedisClient {
	
//	private static final Logger logger = LoggerFactory.getLogger(RedisClient.class);
	
	/** Redis线程池 */
	private Pool<?> redisPool = null;
	
	private static Jedis listener;
	
	public RedisClient(String prefix){
		/** 注册模块Redis是否启用集群 */
		boolean isRegShared = ConfigCommon.getBoolean(prefix+".REDIS.ISSHARE", false);
		
		int maxidle = ConfigCommon.getProperties(prefix+".REDIS.POOL.MAXIDLE", -1);
		int maxTotal = ConfigCommon.getProperties(prefix+".REDIS.POOL.MAXTOTAL", -1);
		int minIdle = ConfigCommon.getProperties(prefix+".REDIS.POOL.MINIDLE", -1);
		long maxWait = ConfigCommon.getProperties(prefix+".REDIS.POOL.MAXWAIT", -1)*1000l;
		boolean testonrorrow = ConfigCommon.getBoolean(prefix+".REDIS.POOL.TESTONBORROW",false) ;
		boolean testonreturn = ConfigCommon.getBoolean(prefix+".REDIS.POOL.TESTONRETURN",false) ;
		int timeout = ConfigCommon.getProperties(prefix+".REDIS.POOL.TIMEOUT", 5000);
		
		String password = ConfigCommon.get(prefix+".REDIS.PASSWORD");
		
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(maxTotal);
		config.setMaxWaitMillis(maxWait);
		config.setMinIdle(minIdle);
		config.setMaxIdle(maxidle);
		config.setTestOnBorrow(testonrorrow);
		config.setTestOnReturn(testonreturn);
		if(isRegShared){  //注册模块Redis启用共享线程池
			List<JedisShardInfo> list = new LinkedList<JedisShardInfo>();
			String shareKey  =prefix+".REDIS.SHARE";
			for(Map.Entry<String, String> entry:ConfigCommon.getProperties().entrySet()){   
			    String key = entry.getKey();
				if(key != null && key.toString().startsWith(shareKey)){
					String ipPort = entry.getValue().trim();
					list.add(new JedisShardInfo(ipPort.split(":")[0].trim(), Integer.valueOf(ipPort.split(":")[1].trim())));
				}
			}
			redisPool = new ShardedJedisPool(config, list);
		}else{ //注册模块Redis启用普通线程池
			String ipPort =ConfigCommon.getString(prefix+".REDIS.SINGLE.IP","127.0.0.1:6379").trim();
			redisPool = new JedisPool(config, ipPort.split(":")[0].trim(), Integer.valueOf(ipPort.split(":")[1].trim()),timeout,password);
		}
	}
	
	public Pool<?> getPool(){
		return redisPool;
	}
	
	public void set(String key,String vaule){
		Jedis jedis = null;
		JedisPool pool =null;
		try{
			pool= (JedisPool) redisPool;
			jedis = pool.getResource();
			jedis.set(key,vaule) ;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			jedis.close();
		}
	}
	
	public void set(byte[] key,byte[] vaule){
		Jedis jedis = null;
		JedisPool pool =null;
		try{
			pool= (JedisPool) redisPool;
			jedis = pool.getResource();
			jedis.set(key,vaule) ;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			jedis.close();
		}
	}
	
	
	public void setEx(String key,int seconds,String value){
		Jedis jedis = null;
		JedisPool pool =null;
		try{
			pool= (JedisPool) redisPool;
			jedis = pool.getResource();
			jedis.setex(key, seconds, value);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			jedis.close();
		}
	}
	
	public String get(String key){
		String str=null;
		Jedis jedis = null;
		JedisPool pool =null;
		try{
			pool= (JedisPool) redisPool;
			jedis = pool.getResource();
			str = jedis.get(key);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			jedis.close();
		}
		return str;
	}
	
	public String hset(String mkey,String key,String value){
		String str=null;
		Jedis jedis = null;
		JedisPool pool =null;
		try{
			pool= (JedisPool) redisPool;
			jedis = pool.getResource();
			jedis.hset(mkey, key, value);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			jedis.close();
		}
		return str;
	}
	public String hget(String mkey,String key){
		String str=null;
		Jedis jedis = null;
		JedisPool pool =null;
		try{
			pool= (JedisPool) redisPool;
			jedis = pool.getResource();
			str = jedis.hget(mkey,key);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			jedis.close();
		}
		return str;
	}
	
	public Map<String,String> hgetall(String mkey){
		Map<String,String> map=null;
		Jedis jedis = null;
		JedisPool pool =null;
		try{
			pool= (JedisPool) redisPool;
			jedis = pool.getResource();
			map = jedis.hgetAll(mkey);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			jedis.close();
		}
		return map;
	}
	
	public void hsetall(String mkey,Map<String,Object> params){
		Jedis jedis = null;
		JedisPool pool =null;
		try{
			pool= (JedisPool) redisPool;
			jedis = pool.getResource();
			for(Entry<String,Object> entry:params.entrySet()){
				if(null!=entry.getValue()){
					jedis.hset(mkey, entry.getKey(), entry.getValue().toString());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			jedis.close();
		}
	}
	
	public byte[] get(byte[] key){
		byte[] str=null;
		Jedis jedis = null;
		JedisPool pool =null;
		try{
			pool= (JedisPool) redisPool;
			jedis = pool.getResource();
			str = jedis.get(key);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			jedis.close();
		}
		return str;
	}
	
	public String getAndDel(String key){
		String str=null;
		Jedis jedis = null;
		JedisPool pool =null;
		try{
			pool= (JedisPool) redisPool;
			jedis = pool.getResource();
			str = jedis.get(key);
			jedis.del(key);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			jedis.close();
		}
		return str;
	}
	
	public void del	(String key){
		Jedis jedis = null;
		JedisPool pool =null;
		try{
			pool= (JedisPool) redisPool;
			jedis = pool.getResource();
			jedis.del(key);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			jedis.close();
		}
	}
	
	public void hdel(String mkey,String... keys){
		Jedis jedis = null;
		JedisPool pool =null;
		try{
			pool= (JedisPool) redisPool;
			jedis = pool.getResource();
			jedis.hdel(mkey, keys);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			jedis.close();
		}
	}
	
	public Boolean exists(String key){
		Boolean re =null;
		Jedis jedis = null;
		JedisPool pool =null;
		try{
			pool= (JedisPool) redisPool;
			jedis = pool.getResource();
			re=jedis.exists(key);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			jedis.close();
		}
		return re;
	}
	
	public Set<String> keys(String pattern){
		Set<String> keys =null;
		Jedis jedis = null;
		JedisPool pool =null;
		try{
			pool= (JedisPool) redisPool;
			jedis = pool.getResource();
			keys = jedis.keys(pattern);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			jedis.close();
		}
		return keys;
	}
	
	public boolean expire(String key,int seconds){
		long re =0;
		Jedis jedis = null;
		JedisPool pool =null;
		try{
			pool= (JedisPool) redisPool;
			jedis = pool.getResource();
			re=jedis.expire(key, seconds);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			jedis.close();
		}
		return re==1;
	}
	
	public void setObj(String key,Serializable obj){
		Jedis jedis = null;
		JedisPool pool =null;
		try{
			pool= (JedisPool) redisPool;
			jedis = pool.getResource();
			jedis.set(key.getBytes(), SerializationUtils.serialize(obj));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			jedis.close();
		}
	}
	
	public void zadd(String key,String value,double score){
		Jedis jedis = null;
		JedisPool pool =null;
		try{
			pool= (JedisPool) redisPool;
			jedis = pool.getResource();
			jedis.zadd(key, score, value);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			jedis.close();
		}
	}
	
	public Set<Tuple> rangeByScoreWithScores(String key,double min,double max){
		Jedis jedis = null;
		JedisPool pool =null;
		Set<Tuple> result=null;
		try{
			pool= (JedisPool) redisPool;
			jedis = pool.getResource();
			result = jedis.zrangeByScoreWithScores(key, min, max);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			jedis.close();
		}
		return result;
	}
	
	public Set<String> rangeByScore(String key,double min,double max){
		Jedis jedis = null;
		JedisPool pool =null;
		Set<String> result=null;
		try{
			pool= (JedisPool) redisPool;
			jedis = pool.getResource();
			result = jedis.zrangeByScore(key, min, max);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			jedis.close();
		}
		return result;
	}
	public <T> T getObj(String key,Class<T> clazz){
		Jedis jedis = null;
		JedisPool pool =null;
		byte[] result = null;
		try{
			pool= (JedisPool) redisPool;
			jedis = pool.getResource();
			result = jedis.get(key.getBytes());
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			jedis.close();
		}
		return SerializationUtils.<T>deserialize(result);
	}

	public void del(Set<String> keys) {
		Jedis jedis = null;
		JedisPool pool =null;
		try{
			pool= (JedisPool) redisPool;
			jedis = pool.getResource();
			jedis.del(keys.toArray(new String[]{}));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			jedis.close();
		}
	}
	public void setListener(JedisPubSub pubsub,String pattern) {
		Jedis jedis = null;
		JedisPool pool =null;
		try{
			pool= (JedisPool) redisPool;
			if(null==listener){
				listener= (Jedis)pool.getResource();
			}
			listener.psubscribe(pubsub, pattern);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public Long getZcount(String key,double min,double max){
		Jedis jedis = null;
		JedisPool pool =null;
		Long result=null;
		try{
			pool= (JedisPool) redisPool;
			jedis = pool.getResource();
			result = jedis.zcount(key, min, max);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			jedis.close();
		}
		return result;
	}
	public void sadd(String key,String... members){
		Jedis jedis = null;
		JedisPool pool =null;
		try{
			pool= (JedisPool) redisPool;
			jedis = pool.getResource();
			jedis.sadd(key, members);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			jedis.close();
		}
	}
	public void srem(String key,String... members){
		Jedis jedis = null;
		JedisPool pool =null;
		try{
			pool= (JedisPool) redisPool;
			jedis = pool.getResource();
			jedis.srem(key, members);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			jedis.close();
		}
	}
	public boolean sismember(String key,String member){
		Jedis jedis = null;
		JedisPool pool =null;
		boolean result=false;
		try{
			pool= (JedisPool) redisPool;
			jedis = pool.getResource();
			result=jedis.sismember(key, member);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			jedis.close();
		}
		return result;
	}
	public Set<String> smembers(String key){
		Jedis jedis = null;
		JedisPool pool =null;
		Set<String> results=null;
		try{
			pool= (JedisPool) redisPool;
			jedis = pool.getResource();
			results= jedis.smembers(key);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			jedis.close();
		}
		return results;
	}
}
