package br.com.siecola.aws_project01.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.amazonaws.services.s3.AmazonS3;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import br.com.siecola.aws_project01.repository.InvoiceRepository;

@Service
public class InvoiceConsumer {
    private static final Logger log = LoggerFactory.getLogger(InvoiceConsumer.class);
    private ObjectMapper objectMapper;
    private InvoiceRepository invoiceRepository;
    private AmazonS3 amazonS3;

    @Autowired
    public InvoiceConsumer(ObjectMapper objectMapper, InvoiceRepository invoiceRepository, AmazonS3 amazonS3) {
        this.objectMapper = objectMapper;
        this.invoiceRepository = invoiceRepository;
        this.amazonS3 = amazonS3;
    }
}