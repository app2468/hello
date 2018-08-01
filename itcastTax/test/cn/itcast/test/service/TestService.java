package cn.itcast.test.service;

import java.io.Serializable;

import cn.itcast.test.entity.Person;

public interface TestService {
	
	public abstract void say();
	//新增用户
	public void save(Person person);
    
	public Person findPersonById(Serializable id);
}
