package jszczepankiewicz.storm.test1.jmsclient;

import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import static com.codahale.metrics.MetricRegistry.name;

/**
 * Created by urwisy on 01.03.14.
 */
@Component
public class MessageClient {

    private static final Logger LOG = LoggerFactory.getLogger(MessageClient.class);

    private final Meter requests;

    private JmsTemplate jmsTemplate;

    @Autowired
    public MessageClient(MetricRegistry metrics, JmsTemplate jmsTemplate){
        requests = metrics.meter(name(MessageClient.class, "clientJMSRequests"));
        this.jmsTemplate = jmsTemplate;
    }

    public void sendAndReceiveMessages(){

        LOG.info("Starting sending messages...");

        for(int i = 0; i<1000; i++){
            requests.mark();

            jmsTemplate.convertAndSend("Message content: " + i);

            if(i%10000==0){
                LOG.info("Sent {} messages", i);
            }
        }
    }
}
