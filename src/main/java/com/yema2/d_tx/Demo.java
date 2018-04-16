package com.yema2.d_tx;

import com.commons.utils.HibernateUtils;
import org.hibernate.Session;
import org.junit.Test;


//测试getCurrentSession
public class Demo {

	@Test
	//返回同一个与线程绑定的session
	public void fun1(){
		Session session1 = HibernateUtils.getCurrentSession();
		Session session2 = HibernateUtils.getCurrentSession();
		
		System.out.println(session1==session2);//true
	}
	
	@Test
	//返回不同的session
	public void fun2(){
		Session session1 = HibernateUtils.openSession();
		Session session2 = HibernateUtils.openSession();
		
		System.out.println(session1==session2);//false
	}
	
	
}
