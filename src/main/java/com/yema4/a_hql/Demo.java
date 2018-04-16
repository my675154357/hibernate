package com.yema4.a_hql;

import java.util.List;

import com.commons.utils.HibernateUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;


//学习HQL语法
public class Demo {
	
	//基本语法
	@Test
	public void fun1(){
		Session session = HibernateUtils.openSession();
		Transaction tx = session.beginTransaction();
		//----------------------------------------------------
		String hql = " from com.commons.domain.Customer";//完整写法
		String hql2 = " from Customer"; //简单写法
		//String hql3 = " from java.lang.Object";//查所有表。开发没用，还可能内存溢出
		
		Query query = session.createQuery(hql2);
		
		List list = query.list();
		
		System.out.println(list);
		//----------------------------------------------------
		tx.commit();
		session.close();
		
	}
	
	
	@Test
	//排序
	public void fun2(){
		Session session = HibernateUtils.openSession();
		Transaction tx = session.beginTransaction();
		//----------------------------------------------------
		String hql1 = " from com.commons.domain.Customer order by cust_id asc ";//完整写法
		String hql2 = " from com.commons.domain.Customer order by cust_id desc ";//完整写法
		
		Query query = session.createQuery(hql2);
		
		List list = query.list();
		
		System.out.println(list);
		//----------------------------------------------------
		tx.commit();
		session.close();
		
	}

	@Test
	//条件查询
	public void fun3(){
		Session session = HibernateUtils.openSession();
		Transaction tx = session.beginTransaction();
		//----------------------------------------------------
		String hql1 = " from com.commons.domain.Customer where cust_id = ?";//完整写法
		String hql2 = " from com.commons.domain.Customer where cust_id = :id ";//完整写法
		
		Query query = session.createQuery(hql2);
		
//		query.setParameter(0, 2l);
		query.setParameter("id", 2l);
		
		
		List list = query.list();
		
		System.out.println(list);
		//----------------------------------------------------
		tx.commit();
		session.close();
		
	}
	
	@Test
	//分页查询
	public void fun4(){
		Session session = HibernateUtils.openSession();
		Transaction tx = session.beginTransaction();
		//----------------------------------------------------
		String hql1 = " from com.commons.domain.Customer";//完整写法
		
		Query query = session.createQuery(hql1);
		
		//limit ?,?
		// (当前页数-1)*每页条数
		query.setFirstResult(0);
		query.setMaxResults(2);
		
		List list = query.list();
		
		System.out.println(list);
		//----------------------------------------------------
		tx.commit();
		session.close();
		
	}
	
	@Test
	//统计查询
	//count	计数
	//sum 	求和
	//avg	平均数
	//max
	//min
	public void fun5(){
		Session session = HibernateUtils.openSession();
		Transaction tx = session.beginTransaction();
		//----------------------------------------------------
		String hql1 = " select count(*) from Customer";//完整写法
		String hql2 = " select sum(cust_id) from Customer";//完整写法
		String hql3 = " select avg(cust_id) from Customer";//完整写法
		String hql4 = " select max(cust_id) from Customer";//完整写法
		String hql5 = " select min(cust_id) from Customer";//完整写法
		
		Query query = session.createQuery(hql5);
		
		Number number  = (Number) query.uniqueResult();
		
		System.out.println(number);
		//----------------------------------------------------
		tx.commit();
		session.close();
		
	}
	
	
	@Test
	//投影查询。一个物体的投影，只能看到物体的一部分，是无法看到物体的全部。所以投影查询，就是不完全查询，也就是只查询部分列
	public void fun6(){
		Session session = HibernateUtils.openSession();
		Transaction tx = session.beginTransaction();
		//----------------------------------------------------
		String hql1 = " select cust_name from Customer";//返回List<Object[]>，不能封装到对象中
		String hql2 = " select cust_name,cust_id from com.commons.domain.Customer";//返回List<Object[]>，不能封装到对象中
		String hql3 = " select new Customer(cust_id,cust_name) from com.commons.domain.Customer";//这样写的前提是实体类中要有对应的构造方法
		
		Query query = session.createQuery(hql3);
		
		List list = query.list();
		
		System.out.println(list);
		
		//----------------------------------------------------
		tx.commit();
		session.close();
		
	}
}
