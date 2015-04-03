package mymdb.client;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

/**
 * Created by root on 15-4-3.
 */
public class MyMDBClient {

    public static void main(String[] args) {
        final Properties env = new Properties();
        env.put(Context.INITIAL_CONTEXT_FACTORY, org.jboss.naming.remote.client.InitialContextFactory.class.getName());
        env.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
        env.put(Context.SECURITY_PRINCIPAL, "xiaoming");
        env.put(Context.SECURITY_CREDENTIALS, "123");
        env.put("jboss.naming.client.ejb.context", true);

        InitialContext context=null;
        try {
            context = new InitialContext(env);
        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        TopicConnectionFactory factory=null;
        try {
            factory =  (TopicConnectionFactory) context.lookup("jms/RemoteConnectionFactory");
        } catch (NamingException e) {
            e.printStackTrace();
        }
        TopicConnection connection=null;
        try {
            connection = factory.createTopicConnection("xiaoqiang","123");
        } catch (JMSException e) {
            e.printStackTrace();
        }
        TopicSession session=null;
        try {
            session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        Topic topic=null;
        try {
            topic = (Topic) context.lookup("jms/topic/testTopic");
        } catch (NamingException e) {
            e.printStackTrace();
        }

        TopicPublisher publisher = null;

        try {
            publisher = session.createPublisher(topic);
        } catch (JMSException e) {
            e.printStackTrace();
        }

        TextMessage msg = null;
        try {
            msg = session.createTextMessage();
        } catch (JMSException e) {
            e.printStackTrace();
        }

        try {
            msg.setText("xiaoming nihao");
            publisher.publish(msg);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
