import java.util.Arrays;
import java.util.Date;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.maps.model.GeocodingResult;

import vn.hiworld.com.chloe.util.GeoCoding;
import vn.hiworld.com.chloe.util.GoogleGeoCodeResponse;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.DateBuilder.evenMinuteDate;
public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
//		SchedulerFactory sf = new StdSchedulerFactory();   
//		Scheduler sched = sf.getScheduler();
//		JobDetail job = JobBuilder.newJob(HelloJob2.class)
//				.withIdentity("job1", "group1")
//				.usingJobData("jobSays", "Hello World!")
//			    .usingJobData("myFloatValue", 3.141f)
//				.build();
//		Date runTime = evenMinuteDate(new Date()); 
//		Trigger trigger = newTrigger()
//				.withIdentity("trigger1", "group1")
//				.startAt(runTime)
//				.withSchedule(simpleSchedule()
//			            .withIntervalInSeconds(1)
//			            .repeatForever())  
//				.build();
//		sched.scheduleJob(job, trigger);
//		sched.start();
//		Thread.sleep(2L * 1000L);
//		sched.shutdown(true);
		GeoCoding geo = GeoCoding.createGeoCoding();
//		GoogleGeoCodeResponse result = gson.fromJson(jsonCoord(URLEncoder.encode("67 hang bai, ha noi", "UTF-8"));GoogleGeoCodeResponse.class);
		GoogleGeoCodeResponse result = GoogleGeoCodeResponse.parse(geo.search("67 hang bai, ha noi"));
//		parse.parse(Arrays.toString(result));
		
		System.out.println(result
				.toJson()
				);
	}
	

}
