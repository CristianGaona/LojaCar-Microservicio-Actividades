package lojacar.ms.task.producer;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lojacar.ms.task.domain.Activity;
import lojacar.ms.task.service.IActivityService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class ActivityEventProducer {

	String topic = "activity-events";
	private Logger log = LoggerFactory.getLogger(ActivityEventProducer.class);
	
	@Autowired
	KafkaTemplate<Integer, String> kafkaTemplate;
	
	@Autowired
	ObjectMapper objetMapper;
	
	@Autowired
	private IActivityService service;
	
	public ListenableFuture<SendResult<Integer, String>> sendActivity(Activity activityEvent) throws JsonProcessingException{
		
		Integer key = activityEvent.getId();
		String value = objetMapper.writeValueAsString(activityEvent);
		
		ProducerRecord<Integer, String> producerRecord = buildProducerRecord(key,value,topic);
		
		ListenableFuture<SendResult<Integer, String>> listenableFuture = kafkaTemplate.send(producerRecord);
		
		listenableFuture.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>(){

			@Override
			public void onSuccess(SendResult<Integer, String> result) {
				handleSuccess(key,value,result);
				
			}

			@Override
			public void onFailure(Throwable ex) {
				handleFailure(key,value,ex);
				
			}
			
		});
		
		return listenableFuture;
		
	}
	
	private ProducerRecord<Integer, String> buildProducerRecord(Integer key, String value, String topic){
		List<Header> recordHeaders = Stream.of(new RecordHeader("activity-event-source", "scanner".getBytes()))
                .collect(Collectors.toList());
		return new ProducerRecord<>(topic, null, key, value, recordHeaders);

	}
	
	private void handleFailure(Integer key, String value, Throwable ex) {
		log.error("Error Sending the Message and the execpition is {}", ex.getMessage());
		try {
			throw ex;
		} catch (Throwable throwable) {
			log.error("Error in OnFailure : {}", throwable.getMessage());
		}
	}

	private void handleSuccess(Integer key, String value, SendResult<Integer, String> result) {
    
		log.info("Message Sent SuccessFully for the key : {} and the value is {} , partition is {}",
				key , value , result.getRecordMetadata().partition());
		
	}
	
}
