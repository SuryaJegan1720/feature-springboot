package com.example.demo.service.dao;
import org.springframework.stereotype.Service;
@Service
public class ArithmeticOperations implements Arithmetic {
	@Override
	public int addition(int a, int b) {
		int c=a+b;
		System.out.println("Addition value "+ c);
		return c;
	}
}
