package com.example.lab11jee.services;
import com.example.lab11jee.repository.Student;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.*;

@Stateless
public class Service {
    @Resource(name = "messageQueue")
    private Queue messageQueue;

    @Resource
    private ConnectionFactory connectionFactory;

    public String sendJmsMessage(Student student) {
        try (final Connection connection = connectionFactory.createConnection();
             final Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
             final MessageProducer producer = session.createProducer(messageQueue)) {
            connection.start();
            final Message jmsMessage = session.createTextMessage(String.valueOf(student)); //session.createObjectMessage(student);
            producer.send(jmsMessage);
            return "successfully sent";
        } catch (final Exception e) {
            throw new RuntimeException("Caught exception from JMS when sending a message", e);
        }
    }

    public String getMessage() {
        try (final Connection connection = connectionFactory.createConnection();
             final Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
             final MessageConsumer messageConsumer = session.createConsumer(messageQueue)) {
            connection.start();

            final Message jmsMessage = messageConsumer.receive(1000); //final ObjectMessage jmsMessage = (ObjectMessage) messageConsumer.receive(1000);
            if (jmsMessage == null) {
                return "Empty message!";//return new Student();
            }
            else {
                TextMessage message = (TextMessage) jmsMessage;//Student student = (Student) jmsMessage;
                return message.getText();
            }
        } catch (final Exception e) {
            throw new RuntimeException("Caught exception from JMS when receiving a message", e);
        }
    }

    //@Transactional
    //public List<String> getAll() throws JMSException{
    //    Connection connection = null;
      //  Session session = null;
        //MessageConsumer messageConsumer = null;
        //List<String> messages = new ArrayList<>();
        //try {
        //    connection = connectionFactory.createConnection();
         //   connection.start();

        //    session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //    messageConsumer = session.createConsumer(messageQueue);
         //   TextMessage message;
        //    while ((message = (TextMessage) messageConsumer.receive(1000)) != null){
         //       messages.add(message.getText());
         //   }
         //   return messages;

      //  } finally {
      //      if (messageConsumer != null) messageConsumer.close();
      //      if (session != null) session.close();
      //      if (connection != null) connection.close();
        //}
    //}
}
