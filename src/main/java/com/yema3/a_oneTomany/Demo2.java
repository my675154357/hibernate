package com.yema3.a_oneTomany;

import com.commons.domain.Customer;
import com.commons.domain.LinkMan;
import com.commons.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

//测试级联操作
public class Demo2 {
	@Test
	//保存客户 以及客户 下的联系人
	//cascade:save-update Customer配置中
	public void fun1(){
		//1 获得session
		Session session = HibernateUtils.openSession();
		//2 开启事务
		Transaction tx = session.beginTransaction();
		//-------------------------------------------------
		//3操作
		Customer c = new Customer();
		c.setCust_name("传智播客");
		
		LinkMan lm1 = new LinkMan();
		lm1.setLkm_name("黎活明");
		
		LinkMan lm2 = new LinkMan();
		lm2.setLkm_name("刘悦东");
		
		//表达一对多,客户下有多个联系人
		c.getLinkMens().add(lm1);
		c.getLinkMens().add(lm2);
		
		//表达对对对,联系人属于哪个客户
		lm1.setCustomer(c);
		lm2.setCustomer(c);
		
		
		session.save(c);
//		session.save(lm1);
//		session.save(lm2);
		
		//-------------------------------------------------
		//4提交事务
		tx.commit();
		//5关闭资源
		session.close();
	}
	
	@Test
	//测试删除客户时,级联删除客户下的联系人
	//cascade:delete Customer配置中
	public void fun2(){
		//1 获得session
		Session session = HibernateUtils.openSession();
		//2 开启事务
		Transaction tx = session.beginTransaction();
		//-------------------------------------------------
		//3操作
		//1> 获得要操作的客户对象
		Customer c = (Customer) session.get(Customer.class,1l);
		//2>调用delete删除客户
		session.delete(c);
		//-------------------------------------------------
		//4提交事务
		tx.commit();
		//5关闭资源
		session.close();
	}
	
	@Test
	//保存联系人以及联系人对应的客户
	//cascade:save-update LinkMan配置中
	public void fun3(){
		//1 获得session
		Session session = HibernateUtils.openSession();
		//2 开启事务
		Transaction tx = session.beginTransaction();
		//-------------------------------------------------
		//3操作
		Customer c = new Customer();
		c.setCust_name("北大青鸟");
		
		LinkMan lm1 = new LinkMan();
		lm1.setLkm_name("刘总");
		
		//表达一对多,客户下有多个联系人
		c.getLinkMens().add(lm1);
		
		//表达对对对,联系人属于哪个客户
		lm1.setCustomer(c);
		
		
		session.save(lm1);
		
		//-------------------------------------------------
		//4提交事务
		tx.commit();
		//5关闭资源
		session.close();
	}
		
}
