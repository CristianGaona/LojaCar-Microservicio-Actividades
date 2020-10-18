package lojacar.ms.task.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import lojacar.ms.task.domain.Activity;
import lojacar.ms.task.producer.ActivityEventProducer;
import lojacar.ms.task.service.IActivityService;
@RestController
public class ActivityEventController {

	private Logger log = LoggerFactory.getLogger(ActivityEventController.class);
	
	@Autowired
	ActivityEventProducer activityProducer;
	
	@Autowired
	private IActivityService activityService;
	
	@PostMapping("/api/v1/activityevent")
	public ResponseEntity<Activity> postActivityEvent (@RequestBody Activity activityEvent) throws JsonProcessingException{
		log.info("antes de tranSql");
		Activity tranSql = activityService.save(activityEvent);
		log.info("despues de tranSql");
		log.info("antes  de sendDepositEvent");
		activityProducer.sendActivity(tranSql);
		log.info("despues  de sendDepositEvent");
		return  ResponseEntity.status(HttpStatus.CREATED).body(tranSql);
				}
}
