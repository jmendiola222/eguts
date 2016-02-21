package helpers;

import org.joda.time.DateTime
import org.joda.time.Duration


class DateUtils {

	static def overrideDate = null
	
	static def now(){
		if (overrideDate){
			overrideDate;
		}else{
			new Date();
		}
	}
	
	static def DateTime nowAsDateTime() {
		if (overrideDate){
			new DateTime(overrideDate);
		}else{
			new DateTime();
		}
	}

	static def int minutesSince(Date start){
		return dateDiffInMinutes(start, now())
	}

	static def int dateDiffInMinutes(Date start, Date end){
		def startD = new DateTime(start)
		def endD = new DateTime(end)
		Duration duration = new Duration(startD, endD);
		return duration.getStandardMinutes();
	}
}
