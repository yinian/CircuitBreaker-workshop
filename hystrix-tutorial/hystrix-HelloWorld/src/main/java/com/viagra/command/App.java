package com.viagra.command;

import com.viagra.command.HelloCommand;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

public class App {

	@Test
	public void testSync(){
		HelloCommand command = new HelloCommand();
		// 同步模式
		String result = command.execute();
		System.out.println(result);
	}

	@Test
	public void testAsync(){
		HelloCommand command = new HelloCommand();
		// 异步模式
		try {
			String asyncResult = command.queue().get();
			System.out.println(asyncResult);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		HelloCommand command = new HelloCommand();
		// 同步模式
		String result = command.execute();
		System.out.println(result);

		// 异步模式
		try {
			String asyncResult = command.queue().get();
			System.out.println(asyncResult);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
}
