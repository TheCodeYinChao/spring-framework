package cn.demo;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class HelloService {
	public void hello() {
		System.out.println("Hello,Springdemo >>>>>>>");
	}
}