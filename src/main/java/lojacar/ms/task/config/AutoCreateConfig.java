package lojacar.ms.task.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.config.TopicBuilder;

public class AutoCreateConfig {

	public NewTopic activityEvent() {
		return TopicBuilder.name("activity-events")
				.partitions(3)
				.replicas(1)
				.build();
	}
}
