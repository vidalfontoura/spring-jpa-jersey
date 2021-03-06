package br.com.cinq.spring.data.sample.batch;

import br.com.cinq.spring.data.sample.entity.City;
import br.com.cinq.spring.data.sample.repository.CityRepository;

import java.io.File;
import java.net.URL;
import java.util.Date;

import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * BatchConfiguration for reading a csv file and insert the data in the database
 * The path for the csv file should be configured using the "batch.file
 * configuration.
 */
@Configuration
@EnableBatchProcessing
@Import({ BatchScheduler.class })
public class BatchConfiguration {
    
    private static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(BatchConfiguration.class);

    private static final String FILE_ABSENT = "The provided batch file name {} is absent, skipping Job";

    private static final String JOB_STARTED = "Job started at : {}";

    private static final String JOB_FINISHED = "Job finished with status : {}";

    @Value("${batch.file}")
    private String batchFileConfig;
    
	@Autowired
	private SimpleJobLauncher jobLauncher;

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private CityRepository cityRepository;

    @Scheduled(cron = "${batch.cron-expression}")
	public void perform() throws Exception {

        URL resourceURL = this.getClass().getClassLoader().getResource(batchFileConfig);

        if (resourceURL != null) {
            LOGGER.info(JOB_STARTED, new Date());
            JobParameters param =
                new JobParametersBuilder().addString("importCitiesFromCSVFile",
                    String.valueOf(System.currentTimeMillis())).toJobParameters();

            JobExecution execution = jobLauncher.run(importCities(), param);
            
            File file = new ClassPathResource(batchFileConfig).getFile();
            file.delete();

            LOGGER.info(JOB_FINISHED, execution.getStatus());
        }

        LOGGER.info(FILE_ABSENT, batchFileConfig);

	}

	public FlatFileItemReader<CityDTO> reader() {
		FlatFileItemReader<CityDTO> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource(batchFileConfig));
		reader.setLineMapper(new DefaultLineMapper<CityDTO>() {
			{
				setLineTokenizer(new DelimitedLineTokenizer() {
					{
						setNames(new String[] { "city", "country" });
					}
				});
				setFieldSetMapper(new BeanWrapperFieldSetMapper<CityDTO>() {
					{
						setTargetType(CityDTO.class);
					}
				});
			}
		});
		return reader;
	}

	public CityItemProcessor processor() {
		return new CityItemProcessor();
	}

	public RepositoryItemWriter<City> writer() throws Exception {

		RepositoryItemWriter<City> itemWriter = new RepositoryItemWriter<>();
		itemWriter.setRepository(cityRepository);
		itemWriter.setMethodName("save");
		itemWriter.afterPropertiesSet();
		return itemWriter;
	}

	public Job importCities() throws Exception {

        return jobBuilderFactory.get("importCitiesFromCSVFile").incrementer(new RunIdIncrementer())
				.flow(step1()).end().build();
	}

	public Step step1() throws Exception {
		return stepBuilderFactory.get("step1").<CityDTO, City>chunk(10).reader(reader()).processor(processor())
				.writer(writer()).build();
	}



}