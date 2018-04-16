package com.yema4.e_fetchsize;

import java.util.List;

import com.commons.domain.Customer;
import com.commons.utils.HibernateUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import org.junit.Test;

//抓取数量
//Customer配置文件中这个标签上<set name="linkMens" inverse="true" cascade="save-update" lazy="true" fetch="select" batch-size="3">
public class Demo {
	
	@Test
	public void fun1(){
		Session session = HibernateUtils.openSession();
		Transaction tx = session.beginTransaction();
		//----------------------------------------------------
		
		String hql = "from Customer ";
		Query query = session.createQuery(hql);
		List<Customer> list = query.list();
		
		for(Customer c:list){
			//如果没配置batch-size，那么这里每次抓取一个LinkMan，循环一次，查询一次。一个Customer如果关联了100个LinkMan，这里就要执行100次查询LinkMan
			//配置了batch-size后，根据配置的数量n，第一次循环会查询n个LinkMan，然后的n-1次循环都不会发送sql再查询了。经过一次次循环，直到上次抓取的结果都用完了，
			//会再去抓取n个LinkMan
			System.out.println(c.getLinkMens());
		}
		
		//----------------------------------------------------
		tx.commit();
		session.close();
		
	}

}
