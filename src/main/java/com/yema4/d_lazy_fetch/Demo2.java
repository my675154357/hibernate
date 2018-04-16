package com.yema4.d_lazy_fetch;

import com.commons.domain.Customer;
import com.commons.domain.LinkMan;
import com.commons.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

//关联级别 延迟加载 & 抓取策略   从LinkMan关联加载Customer，从 → 主
//下面fetch和lazy着两个属性都是Linkman配置文件 <many-to-one name="customer" column="lkm_cust_id" class="Customer"/>这个标签上的
//fetch有两个值：select join          lazy有三个值：proxy false no-proxy
public class Demo2 {
	
	@Test
	//fetch:select	单表查询
	//lazy:proxy（默认）
		//customer-true 懒加载
	public void fun1(){
		Session session = HibernateUtils.openSession();
		Transaction tx = session.beginTransaction();
		//----------------------------------------------------
		
		LinkMan lm = (LinkMan) session.get(LinkMan.class, 1l);//单表查LinkMan
		
		Customer customer = lm.getCustomer();
		
		System.out.println(customer);//单表查Customer
		
		//----------------------------------------------------
		tx.commit();
		session.close();
		
	}
	@Test
	//fetch:join	多表
	//lazy: 失效  
	public void fun3(){
		Session session = HibernateUtils.openSession();
		Transaction tx = session.beginTransaction();
		//----------------------------------------------------
		
		LinkMan lm = (LinkMan) session.get(LinkMan.class, 1l);//连接查询两张表
		
		Customer customer = lm.getCustomer();
		
		System.out.println(customer);
		
		//----------------------------------------------------
		tx.commit();
		session.close();
		
	}
	@Test
	//fetch:select	单表查询
	//lazy:proxy  
		//customer-false 立即加载
	public void fun2(){
		Session session = HibernateUtils.openSession();
		Transaction tx = session.beginTransaction();
		//----------------------------------------------------
		
		LinkMan lm = (LinkMan) session.get(LinkMan.class, 3l);//直接执行两个单表查询LinkMan和Customer
		
		Customer customer = lm.getCustomer();
		
		System.out.println(customer);
		
		//----------------------------------------------------
		tx.commit();
		session.close();
		
	}
}
