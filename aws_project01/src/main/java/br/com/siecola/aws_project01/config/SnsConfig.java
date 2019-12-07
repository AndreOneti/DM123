package br.com.siecola.aws_project01.config;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.Topic;
import org.springframework.context.annotation.Bean;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;


@Configuration
public class SnsConfig {
    @Value("${aws.region}")
    private String awsRegion;

    @Value("${aws.sns.topic.product.events.arn}")
    private String productEventsTopic;

    @Bean
    public AmazonSNS snsClient() {
        return AmazonSNSClientBuilder.standard()
                .withRegion(awsRegion)
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .build();
    }

    @Bean(name = "productEventsTopic")
    public Topic snsProductEventsTopic() {
        return new Topic().withTopicArn(productEventsTopic);
    }
}
