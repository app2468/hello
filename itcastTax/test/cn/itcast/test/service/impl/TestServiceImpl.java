package cn.itcast.test.service.impl;

import java.io.Serializable;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.itcast.test.dao.TestDao;
import cn.itcast.test.entity.Person;
import cn.itcast.test.service.TestService;

@Service("testService")
public class TestServiceImpl implements TestService {
 
	@Resource
	TestDao testDao;
	
	@Override
	public void say() {
		System.out.println("service saying hi...");

	}

	@Override
	public void save(Person person) {
		testDao.save(person);
		//int tem=1/0;
	}

	@Override
	public Person findPersonById(Serializable id) {
		//save(new Person("test"));
		return testDao.findPersonById(id);
	}

}
