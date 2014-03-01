package jszczepankiewicz.storm.test1.jmsclient;

import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by urwisy on 01.03.14.
 */
public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);
    private static JmxReporter reporter;

    public static void main(String... args) {

        LOG.info("Client application started");

        ApplicationContext context =
                new ClassPathXmlApplicationContext("beans.xml");

        MetricRegistry registry = context.getBean(MetricRegistry.class);
        reporter = JmxReporter.forRegistry(registry).build();
        reporter.start();

        MessageClient client = context.getBean(MessageClient.class);
        client.sendAndReceiveMessages();

    }

}
