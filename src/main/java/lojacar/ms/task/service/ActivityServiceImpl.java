package lojacar.ms.task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lojacar.ms.task.dao.ActivityDao;
import lojacar.ms.task.domain.Activity;

@Service
public class ActivityServiceImpl implements IActivityService {

	@Autowired
	private ActivityDao activityDao;
	
	@Override
	public Activity findById(Integer id) {
		return activityDao.findById(id).orElse(null);
	}

	@Override
	public Activity save(Activity activity) {
		return activityDao.save(activity);
	}

}
