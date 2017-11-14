package br.com.cinq.spring.data.sample.batch;

import br.com.cinq.spring.data.sample.entity.City;
import br.com.cinq.spring.data.sample.repository.CityRepository;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Listener for the Batch Job
 * 
 * @author vfontoura
 *
 */
@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

	@Autowired
	CityRepository cityRepository;

	@Override
	public void afterJob(JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {

			Iterable<City> results = cityRepository.findAll();
			long count = cityRepository.count();
			System.out.println(count);
			for (City city : results) {
				System.out.println(city.getName());
			}

		}
	}
}