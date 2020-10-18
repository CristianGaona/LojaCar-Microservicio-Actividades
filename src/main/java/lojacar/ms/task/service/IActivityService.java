package lojacar.ms.task.service;

import lojacar.ms.task.domain.Activity;

public interface IActivityService {

	public Activity findById(Integer id);
	public Activity save( Activity activity);
}
