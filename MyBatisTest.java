package com.slc.test;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.slc.dao.StudentDao;
import com.slc.entity.Student;

public class MyBatisTest {
	
	private SqlSession getSqlSessionFactory() { 
		String resource = "mybatis-config.xml";
		InputStream inputStream = null;
		try {
			inputStream = Resources.getResourceAsStream(resource);
		} catch (IOException e) {
			e.printStackTrace();
		}
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		return sqlSessionFactory.openSession();
	}
	
	@Test
	public void test1() {
		SqlSession session = getSqlSessionFactory();
		try {
			StudentDao mapper = session.getMapper(StudentDao.class);
			Student student = mapper.getStudentById(2);
			System.out.println(student);
		}finally {
			session.close();
		}
	}
	
	@Test
	public void test2() {
		SqlSession session = getSqlSessionFactory();
		
		try {
			Student student = new Student();
			
			student.setName("刘畅");
			student.setAge(24);
			student.setCountry("中国");
			
			StudentDao mapper = session.getMapper(StudentDao.class);
			
			mapper.addStudent(student);
			
			session.commit();
			System.out.println(student.getId());
			
		}finally {
			session.close();
		}
	}

}
