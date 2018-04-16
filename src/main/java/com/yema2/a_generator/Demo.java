package com.yema2.a_generator;

import com.commons.domain.Customer;
import com.commons.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

//测试主键生成策略
public class Demo {

	@Test
	//保存客户
	public void fun1(){
		//1 获得session
		Session session = HibernateUtils.openSession();
		//2 控制事务
		Transaction tx = session.beginTransaction();
		//3执行操作
		Customer c = new Customer();
		c.setCust_name("联想");
		
		session.save(c);
		
		//4提交事务.关闭资源
		tx.commit();
		session.close();
		
		
	}
}
