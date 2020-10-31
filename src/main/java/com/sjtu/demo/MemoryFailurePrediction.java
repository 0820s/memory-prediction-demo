package com.sjtu.demo;

import com.sjtu.demo.communication.SocketThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Locale;


@SpringBootApplication
public class MemoryFailurePrediction {
	private static SocketThread socketThread;
	@Autowired
	public void setSocketThread(SocketThread socketThread){
		MemoryFailurePrediction.socketThread=socketThread;
	}

	public static void main(String[] args) {
		SpringApplication.run(MemoryFailurePrediction.class, args);

		try{
			ServerSocket serverSocket=new ServerSocket(9126);
			Socket client = null;
			while(true){
				client = serverSocket.accept();
				//System.out.println( "与客户端连接成功！" );
				socketThread.connect(client);
			}
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}

	@Bean
	public ViewResolver myViewReolver(){
		return new MyViewResolver();
	}

	public static class MyViewResolver implements ViewResolver{

		@Override
		public View resolveViewName(String viewName, Locale locale) throws Exception {
			return null;
		}
	}
}
