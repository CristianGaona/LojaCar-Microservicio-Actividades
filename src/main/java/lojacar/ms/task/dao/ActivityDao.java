package lojacar.ms.task.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import lojacar.ms.task.domain.Activity;

@Repository
public interface ActivityDao extends CrudRepository<Activity, Integer>{

}
