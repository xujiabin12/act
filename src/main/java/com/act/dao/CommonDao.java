package com.act.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

@Repository
public class CommonDao {
	
	@PersistenceContext(unitName="ueUnit")
	private EntityManager em;
	
	
	
	//查询对象
	public <T> T queryObject(Class<T> c,String key)throws Exception{
		return (T) em.find(c, key);
	}
	
	//删除对象
	public <T> void remove(Class<T> c,String key)throws Exception{
		em.remove(em.find(c, key));
	}
	
	//删除对象
	public <T> void remove(Object obj)throws Exception{
		em.remove(obj);
	}
	
	//更新对象
	public <T> void update(T t)throws Exception{
		em.merge(t);
	}
	
	//增加
	public <T> void add(Object t)throws Exception{
		em.persist(t);
	}
	
	public void executeUpdate(String sql,Map<String,Object> map)throws Exception{
		Query q = em.createQuery(sql);
		if(map != null){
			for(Map.Entry<String, Object> en : map.entrySet()){
				q.setParameter(en.getKey(), en.getValue());
			}
		}
		q.executeUpdate();
	}
	
	
	
	
	
	//查询集合
	@SuppressWarnings("unchecked")
	public <T> List<T> queryList(String sql,Map<String,Object> map)throws Exception{
		Query q = em.createQuery(sql);
		if(map != null){
			for(Map.Entry<String, Object> en : map.entrySet()){
				q.setParameter(en.getKey(), en.getValue());
			}
		}
		return q.getResultList();
	}
	
	//统计查询
	public int queryCount(String sql,Map<String,Object> map){
		Query q = em.createQuery(sql);
		if(map != null){
			for(Map.Entry<String, Object> en : map.entrySet()){
				q.setParameter(en.getKey(), en.getValue());
			}
		}
		return q.getResultList().size();
	}
	
	//统计查询
		public int queryCountBySql(String sql,Map<String,Object> map){
			Query q = em.createNativeQuery(sql);
			if(map != null){
				for(Map.Entry<String, Object> en : map.entrySet()){
					q.setParameter(en.getKey(), en.getValue());
				}
			}
			return q.getResultList().size();
		}
	
	//查询集合
		@SuppressWarnings("unchecked")
		public List<Object[]> queryListBySql(String sql,Map<String,Object> map)throws Exception{
			Query q = em.createNativeQuery(sql);
			if(map != null){
				for(Map.Entry<String, Object> en : map.entrySet()){
					q.setParameter(en.getKey(), en.getValue());
				}
			}
			return q.getResultList();
		}
	
	//查询集合
	@SuppressWarnings("rawtypes")
	public List queryListObj(String sql,Map<String,Object> map)throws Exception{
		Query q = em.createQuery(sql);
		if(map != null){
			for(Map.Entry<String, Object> en : map.entrySet()){
				q.setParameter(en.getKey(), en.getValue());
			}
		}
		return q.getResultList();
	}
	
	//分页查询集合
	@SuppressWarnings("unchecked")
	public <T> List<T> queryListByPage(String sql,Map<String,Object> map,int pageNo,int pageSize){
		Query q = em.createQuery(sql);
		if(map != null){
			for(Map.Entry<String, Object> en : map.entrySet()){
				q.setParameter(en.getKey(), en.getValue());
			}
		}
		q.setFirstResult(pageNo);//起始行数，从多少条开始
		q.setMaxResults(pageSize);//向后获取多少条记录
		return q.getResultList();
	}
	
	
	//分页查询集合
		@SuppressWarnings({ "unchecked" })
		public <T> List<T> queryListSqlByPage(String sql,Map<String,Object> map,int pageNo,int pageSize,String c){
			Query q = em.createNativeQuery(sql,c);
			if(map != null){
				for(Map.Entry<String, Object> en : map.entrySet()){
					q.setParameter(en.getKey(), en.getValue());
				}
			}
			q.setFirstResult(pageNo);//起始行数，从多少条开始
			q.setMaxResults(pageSize);//向后获取多少条记录
			return q.getResultList();
		}
		
		
		//分页查询集合
		public List<Object[]> queryListObjSqlByPage(String sql,Map<String,Object> map,int pageNo,int pageSize){
			Query q = em.createNativeQuery(sql);
			if(map != null){
				for(Map.Entry<String, Object> en : map.entrySet()){
					q.setParameter(en.getKey(), en.getValue());
				}
			}
			q.setFirstResult(pageNo);//起始行数，从多少条开始
			q.setMaxResults(pageSize);//向后获取多少条记录
			return q.getResultList();
		}
	
	
	//分页查询集合
	@SuppressWarnings("rawtypes")
	public List queryListObjByPage(String sql,Map<String,Object> map,int pageNo,int pageSize){
		Query q = em.createQuery(sql);
		if(map != null){
			for(Map.Entry<String, Object> en : map.entrySet()){
				q.setParameter(en.getKey(), en.getValue());
			}
		}
		q.setFirstResult(pageNo);//起始行数，从多少条开始
		q.setMaxResults(pageSize);//向后获取多少条记录
		return q.getResultList();
	}

}
