package test.spring;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * 
 * TODO : Change to testNG
 *
 */
public class TestRunner {
	public static void main(String[] args) {
		
		Result result = JUnitCore.runClasses(PersonRestfulTest.class, UserRestfulTest.class);
		
		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}
		System.out.println(result.wasSuccessful());
		
	}
}
