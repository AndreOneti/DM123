package br.com.siecola.aws_project02.service;

import org.slf4j.Logger;

import java.io.IOException;
import javax.jms.TextMessage;
import javax.jms.JMSException;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.siecola.aws_project02.model.Envelope;
import br.com.siecola.aws_project02.model.SnsMessage;
import br.com.siecola.aws_project02.model.ProductEvent;

@Service
public class ProductEventConsumer {
    private static final Logger log = LoggerFactory.getLogger(ProductEventConsumer.class);

    private ObjectMapper objectMapper;

    @Autowired
    public ProductEventConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @JmsListener(destination = "${aws.sqs.queue.product.events.name}")
    public void receiveProductEvent(TextMessage textMessage)
            throws JMSException, IOException {

        SnsMessage snsMessage = objectMapper.readValue(textMessage.getText(), SnsMessage.class);

        Envelope envelope = objectMapper.readValue(snsMessage.getMessage(), Envelope.class);

        ProductEvent productEvent = objectMapper.readValue(envelope.getData(), ProductEvent.class);

        log.info("Product event received - Event: {} - ProductId: {} - " + "MessageId: {}",
                envelope.getEventType(), productEvent.getProductId(), snsMessage.getMessageId());
    }
}
