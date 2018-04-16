package com.yema3.a_oneTomany;

import com.commons.domain.Customer;
import com.commons.domain.LinkMan;
import com.commons.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

//一对多|多对一关系操作
public class Demo {
	@Test
	//保存客户 以及客户 下的联系人
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
		
		//表达多对一,联系人属于哪个客户
		lm1.setCustomer(c);
		lm2.setCustomer(c);
		
		
		session.save(c);
//		session.save(lm1);//设置级联，就可以简化操作，无需保存这两个
//		session.save(lm2);
		
		//-------------------------------------------------
		//4提交事务
		tx.commit();
		//5关闭资源
		session.close();
	}
	
	@Test
	//为客户增加联系人
	public void fun2(){
		//1 获得session
		Session session = HibernateUtils.openSession();
		//2 开启事务
		Transaction tx = session.beginTransaction();
		//-------------------------------------------------
		//3操作
		//1> 获得要操作的客户对象
		Customer c = (Customer) session.get(Customer.class,1l);
		//2> 创建联系人
		LinkMan lm1 = new LinkMan();
		lm1.setLkm_name("郝强勇");
		//3> 将联系人添加到客户,将客户设置到联系人中
		c.getLinkMens().add(lm1);
		lm1.setCustomer(c);
		//4> 执行保存
		session.save(lm1);
		//-------------------------------------------------
		//4提交事务
		tx.commit();
		//5关闭资源
		session.close();
	}
	
	@Test
	//为客户删除联系人
	public void fun3(){
		//1 获得session
		Session session = HibernateUtils.openSession();
		//2 开启事务
		Transaction tx = session.beginTransaction();
		//-------------------------------------------------
		//3操作
		//1> 获得要操作的客户对象
		Customer c = (Customer) session.get(Customer.class,1l);
		//2> 获得要移除的联系人
		LinkMan lm = (LinkMan) session.get(LinkMan.class, 3l);
		//3> 将联系人从客户集合中移除
		c.getLinkMens().remove(lm);//只是移除了外键关系，联系人数据并没有删掉
		lm.setCustomer(null);
		//-------------------------------------------------
		//4提交事务
		tx.commit();
		//5关闭资源
		session.close();
	}
		
}
