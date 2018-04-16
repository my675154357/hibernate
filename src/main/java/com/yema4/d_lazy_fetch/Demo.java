package com.yema4.d_lazy_fetch;

import java.util.List;
import java.util.Set;

import com.commons.domain.Customer;
import com.commons.domain.LinkMan;
import com.commons.utils.HibernateUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import org.junit.Test;

//关联级别 延迟加载 & 抓取策略    从Customer关联加载LinkMan，主 → 从
//下面fetch和lazy着两个属性都是Customer配置文件 <set name="linkMens" inverse="true" cascade="save-update" lazy="true" fetch="select">这个标签上的
//fetch有三个值：select join subselect          lazy有三个值：true false extra
public class Demo {
	
	//集合级别的关联
	//fetch:select 单表查询。每次只查询一个表
	//lazy:true 使用时才加载集合数据.
	@Test
	public void fun1(){
		Session session = HibernateUtils.openSession();
		Transaction tx = session.beginTransaction();
		//----------------------------------------------------
		
		Customer c = (Customer) session.get(Customer.class, 1l);//这里发送sql只查询了Customer对象
		
		Set<LinkMan> linkMens = c.getLinkMens();//获得集合属性不会查询

		System.out.println(linkMens.size());//这里使用的时候才发送sql查询了linkMens对象
		
		//----------------------------------------------------
		tx.commit();
		session.close();
		
	}
	
	//集合级别的关联
		//fetch:select 单表查询
		//lazy:false 立即记载集合数据
		@Test
		public void fun2(){
			Session session = HibernateUtils.openSession();
			Transaction tx = session.beginTransaction();
			//----------------------------------------------------
			
			Customer c = (Customer) session.get(Customer.class, 1l);//这里发送sql立即加载，把Customer和linkMens对象都查出来了
			
			Set<LinkMan> linkMens = c.getLinkMens();//关联级别
			
			System.out.println(linkMens);
			
			//----------------------------------------------------
			tx.commit();
			session.close();
			
		}
		//集合级别的关联
		//fetch:select 单表查询
		//lazy:extra 极其懒惰.与懒加载效果基本一致. 如果只获得集合的size.只查询集合的size(count语句)
		@Test
		public void fun3(){
			Session session = HibernateUtils.openSession();
			Transaction tx = session.beginTransaction();
			//----------------------------------------------------
			
			Customer c = (Customer) session.get(Customer.class, 1l);//立即返回Customer对象
			
			Set<LinkMan> linkMens = c.getLinkMens();//未发送sql，返回代理对象
			
			System.out.println(linkMens.size());//发送sql，只查询LinkMan对象的count
			
			System.out.println(linkMens);//发送sql查询LinkMan对象
			
			//----------------------------------------------------
			tx.commit();
			session.close();
			
		}
		//集合级别的关联
		//fetch:join	多表查询 customer left outer join linkman
		//lazy:true|false|extra 失效.立即加载.
		@Test
		public void fun4(){
			Session session = HibernateUtils.openSession();
			Transaction tx = session.beginTransaction();
			//----------------------------------------------------
			
			Customer c = (Customer) session.get(Customer.class, 1l);
			
			Set<LinkMan> linkMens = c.getLinkMens();//关联级别
			
			System.out.println(linkMens.size());
			
			System.out.println(linkMens);
			
			//----------------------------------------------------
			tx.commit();
			session.close();
			
		}
		
		@Test
		//fetch: subselect 子查询
		//lazy: true 懒加载
		public void fun5(){
			Session session = HibernateUtils.openSession();
			Transaction tx = session.beginTransaction();
			//----------------------------------------------------
				
			String  hql = "from Customer";
			
			Query query = session.createQuery(hql);//查询Customer
			
			List<Customer> list = query.list();
			
			for(Customer c:list){
				System.out.println(c);
				System.out.println(c.getLinkMens().size());//使用子查询 in(select...)查询LinkMen
				System.out.println(c.getLinkMens());
			}
			
			//----------------------------------------------------
			tx.commit();
			session.close();
			
		}
		@Test
		//fetch: subselect 子查询
		//lazy: false 立即加载
		public void fun6(){
			Session session = HibernateUtils.openSession();
			Transaction tx = session.beginTransaction();
			//----------------------------------------------------
				
			String  hql = "from Customer";
			
			Query query = session.createQuery(hql);//立即使用子查询，查询了Customer和LinkMen
			
			List<Customer> list = query.list();
			
			for(Customer c:list){
				System.out.println(c);
				System.out.println(c.getLinkMens().size());
				System.out.println(c.getLinkMens());
			}
			
			//----------------------------------------------------
			tx.commit();
			session.close();
			
		}
		@Test
		//fetch: subselect 子查询
		//lazy: extra 极其懒惰
		public void fun7(){
			Session session = HibernateUtils.openSession();
			Transaction tx = session.beginTransaction();
			//----------------------------------------------------
				
			String  hql = "from Customer";
			
			Query query = session.createQuery(hql);//查询Customer
			
			List<Customer> list = query.list();
			
			for(Customer c:list){
				System.out.println(c);
				System.out.println(c.getLinkMens().size());//查询LinkMen的count
				System.out.println(c.getLinkMens());//查询LinkMen的信息
			}
			
			//----------------------------------------------------
			tx.commit();
			session.close();
			
		}
	
}
