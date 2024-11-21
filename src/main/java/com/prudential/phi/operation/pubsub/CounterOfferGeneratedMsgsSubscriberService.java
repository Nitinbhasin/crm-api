package com.prudential.phi.operation.pubsub;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.PubsubMessage;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CounterOfferGeneratedMsgsSubscriberService {

	@Value("${crm.counterOfferGeneratedMsg.subscriptionId}")
	private String CRM_CO_GENERATED_MSG_SUB_ID;
	
	@Value("${crm.projectId}")
	private String CRM_PROJECT_ID;
	
	
	@PostConstruct
	public void init() {
		subscribeAsync(CRM_PROJECT_ID, CRM_CO_GENERATED_MSG_SUB_ID);
	}

	
	public void subscribeAsync(String projectId, String subscriptionId) {
		ProjectSubscriptionName subscriptionName = ProjectSubscriptionName.of(projectId, subscriptionId);

		// Instantiate an asynchronous message receiver.
		MessageReceiver receiver = (PubsubMessage message, AckReplyConsumer consumer) -> {
			// Handle incoming message, then ack the received message.
			log.info("-----------Message Received for  {} subscription --------- ",subscriptionId);
			log.info("Id: " + message.getMessageId());
			log.info("Data: " + message.getData().toStringUtf8());
			log.info("Attributes :" + message.getAttributesMap());			
			consumer.ack();
		};

		Subscriber subscriber = null;
		try {
			subscriber = Subscriber.newBuilder(subscriptionName, receiver).build();
			// Start the subscriber.
			subscriber.startAsync().awaitRunning();
			log.info("Listening for messages on {}:", subscriptionName.toString());
		} catch (Exception exception) {
			subscriber.stopAsync();
		}
	}
	
}
