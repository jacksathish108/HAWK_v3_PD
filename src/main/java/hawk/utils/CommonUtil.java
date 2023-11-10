package hawk.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonUtil {
	static Logger logger = LoggerFactory.getLogger(CommonUtil.class);

	public static Timestamp stringToTimeStamp(String date) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			return new Timestamp((dateFormat.parse(date)).getTime());
		} catch (Exception e) {
			logger.error("stringToTimeStamp method called..." + e.getMessage());
		}
		return null;
	}

	public static String timeStampToYYMMDD(Timestamp timestamp) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			return (dateFormat.format(timestamp));
		} catch (Exception e) {
			logger.error("stringToTimeStamp method called..." + e.getMessage());
		}
		return null;
	}

	public static int calculateDueAmount(int packegePrice, int discount, int paidAmount) {
		int payable = 0;
		try {
			payable = packegePrice - (discount + paidAmount);

		} catch (Exception e) {
			logger.error("stringToTimeStamp method called..." + e.getMessage());
		}
		return payable;
	}

	public static Timestamp getExpiryDate(Timestamp fromDate, long duration) {
		Timestamp expiryDate = null;
		try {
			expiryDate = Timestamp.valueOf(fromDate.toLocalDateTime().plusDays(duration));

		} catch (Exception e) {
			logger.error("stringToTimeStamp method called..." + e.getMessage());
		}
		return expiryDate;
	}

	public static Timestamp getDuration(String type, Long duration,Long extension) {
		Timestamp expiryDate=new Timestamp(System.currentTimeMillis());
		try {
			Calendar calendar = Calendar.getInstance();
			if (type.toUpperCase().contains("DAY")) {
				calendar.add(Calendar.DATE, duration.intValue());
				calendar.add(Calendar.DATE, extension.intValue());
			}
			else if (type.toUpperCase().contains("MONTH")) {
				calendar.add(Calendar.MONTH, duration.intValue());
				calendar.add(Calendar.DATE, extension.intValue());
			}
			expiryDate=new Timestamp(calendar.getTimeInMillis());
		} catch (Exception e) {
			logger.error("stringToTimeStamp method called..." + e.getMessage());
		}
		return expiryDate;
	}
	public static boolean isStringNumeric(String number)
	{
		boolean isNumeric;
		String regex = "-?\\d+(\\.\\d+)?";		
		if(number == null)
			isNumeric = false;
		else if(number.matches(regex))
			isNumeric = true;
		else
			isNumeric = false;		
		return isNumeric;
	}
}