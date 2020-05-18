package com.xuecheng.test.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


public class Consumer01 {

    //队列
    private static final String QUEUE = "helloworld";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory=new ConnectionFactory();
        connectionFactory.setHost("49.234.73.81");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("root");
        connectionFactory.setPassword("root");
        connectionFactory.setVirtualHost("/");
        Connection connection=null;
        connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        DefaultConsumer defaultConsumer=new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String mssg=new String(body,"utf-8");
                System.out.println("receive:"+mssg);
            }
        };

        channel.queueDeclare(QUEUE,true,false,false,null);
        channel.basicConsume(QUEUE,true,defaultConsumer);
    }
}
