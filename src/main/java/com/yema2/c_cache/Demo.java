package com.yema2.c_cache;

import com.commons.domain.Customer;
import com.commons.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

//测试一级缓存
public class Demo {

	@Test
	//证明一级缓存存在
	public void fun1(){
		//1 获得session
		Session session = HibernateUtils.openSession();
		//2 控制事务
		Transaction tx = session.beginTransaction();
		//3执行操作
		
		Customer c1 = (Customer) session.get(Customer.class, 1l);
		Customer c2 = (Customer) session.get(Customer.class, 1l);
		Customer c3 = (Customer) session.get(Customer.class, 1l);
		Customer c4 = (Customer) session.get(Customer.class, 1l);
		Customer c5 = (Customer) session.get(Customer.class, 1l);
		/*
			查询了5次，只发送了一次sql，说明后面的结果都是从缓存中取的，没有查数据库。
		 */
		System.out.println(c3==c5);//true
		//4提交事务.关闭资源
		tx.commit();
		session.close();// 游离|托管 状态, 有id , 没有关联
		
		
	}
	
	@Test
	//
	public void fun2(){
		//1 获得session
		Session session = HibernateUtils.openSession();
		//2 控制事务
		Transaction tx = session.beginTransaction();
		//3执行操作
		
		Customer c1 = (Customer) session.get(Customer.class, 1l);
		
		c1.setCust_name("哈哈");
		c1.setCust_name("传智播客");//只执行了一次upate语句，说明在事务提交后才同步数据库
		/*
			hibernate在从数据库查询出ResultSet后吗，会将结果存两份，一份在缓存中、一份在快照中。
			最后将缓存中的数据返回给程序，程序在两次修改后提交。此时hibernate会比对缓存和快照的数据，并将最终缓存中修改的结果同步到数据库
		*/
		
		//4提交事务.关闭资源
		tx.commit();
		session.close();// 游离|托管 状态, 有id , 没有关联
		
		
	}
	
	@Test
	//持久化状态对象其实就是放入session缓存中的对象
	public void fun3(){
		//1 获得session
		Session session = HibernateUtils.openSession();
		//2 控制事务
		Transaction tx = session.beginTransaction();
		//3执行操作
		
		Customer c1 = new Customer();

		c1.setCust_id(1l);//托管|游离

		session.update(c1);//c1被放入session缓存了。执行update语句
		
		
		Customer c2 = (Customer) session.get(Customer.class, 1l);//这里不会执行查询语句了
		
		//4提交事务.关闭资源
		tx.commit();
		session.close();// 游离|托管 状态, 有id , 没有关联
		
		
	}
	
}
