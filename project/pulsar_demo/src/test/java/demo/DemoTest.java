package demo;


import org.junit.Test;

import java.time.Clock;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author chengzhe yan
 * @description
 * @date 2021/12/28 12:49 上午
 */
public class DemoTest {

	@Test
	public void testClock(){
		/*
			java 8 新增的时钟类
		 */
		Clock clock = Clock.systemDefaultZone();
		System.out.println(clock);
	}

	@Test
	public void testRandom(){
		/*
			多线程下高效生成随机数
		 */
		int i = ThreadLocalRandom.current().nextInt(12);
		System.out.println(i);
	}

}