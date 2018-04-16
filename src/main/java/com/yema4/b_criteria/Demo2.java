package com.yema4.b_criteria;

import java.util.List;

import com.commons.domain.Customer;
import com.commons.utils.HibernateUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;

//学习离线Criteria
public class Demo2 {

	@Test
	public void fun1(){
		//Service/web层
		DetachedCriteria dc  = DetachedCriteria.forClass(Customer.class);
		
		dc.add(Restrictions.idEq(1l));//拼装条件(全部与普通Criteria一致)
		
		//----------------------------------------------------
		Session session = HibernateUtils.openSession();
		Transaction tx = session.beginTransaction();
		//----------------------------------------------------
		Criteria c = dc.getExecutableCriteria(session);
		
		List list = c.list();
		
		System.out.println(list);
		//----------------------------------------------------
		tx.commit();
		session.close();
		
	}
	
}
