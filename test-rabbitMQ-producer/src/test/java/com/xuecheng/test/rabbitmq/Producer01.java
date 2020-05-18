package com.xuecheng.test.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer01 {
    public static final String QUEUE="helloworld";
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory=new ConnectionFactory();
        connectionFactory.setHost("49.234.73.81");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("root");
        connectionFactory.setPassword("root");
        connectionFactory.setVirtualHost("/");
        Connection connection=null;
        Channel channel = null;
        try {
            connection = connectionFactory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare(QUEUE,true,false,false,null);
            String mssg="hello world!";
            channel.basicPublish("",QUEUE,null,mssg.getBytes());
            System.out.println("send to");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            channel.close();
            connection.close();
        }
    }
}
