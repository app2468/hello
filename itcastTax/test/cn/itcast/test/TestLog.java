package cn.itcast.test;

import static org.junit.Assert.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

public class TestLog {

	@Test
	public void test() {
		Log log = LogFactory.getLog(getClass());
		log.debug("这是debug级别日志");
		log.info("这是info级别日志");
		log.warn("这是warn级别日志");
		log.error("这是error级别日志");
		log.fatal("这是fatal级别日志");
	}

}
