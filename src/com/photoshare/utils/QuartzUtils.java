package com.photoshare.utils;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class QuartzUtils {
	public static final String JOB_PACKAGE_NAME = "com.june.quartz.jobs";

	public static final String KEY_DOT = ".";

	public static final String SPECIAL_CHARACTER_INCRE = "/";

	public static final String SPECIAL_CHARACTER_AND = ",";

	public static final String SPECIAL_CHARACTER_WILD_CARD = "*";

	public static final String SPECIAL_CHARACTER_WILD_CARD_CN = "ÿ";

	public static final String SPECIAL_CHARACTER_NONDETERMINE = "?";

	public static final String SPECIAL_CHARACTER_WEEKDAY = "W";

	public static final String SPECIAL_CHARACTER_LAST = "L";

	public static final String SPECIAL_CHARACTER_LAST_CN = "���";

	public static final String SPECIAL_CHARACTER_DAY = "#";

	public static final String MSG_START_TIME_AFTER_SCHEDULE_DATE = "��ʼʱ�����ڵ���ʱ��";

	public static final String MSG_NO_DAY_WEEK_OF_MONTH_IN_THIS_MONTH = "�����û����һ��";

	public static final String MSG_NO_WEEK_OF_MONTH_IN_THIS_MONTH = "�����ֻ������";

	public static final String MSG_NO_JOB_ID_SELECTED = "��ѡ��һ������";

	public static final String MSG_NO_START_TIME = "��ѡ��ִ��ʱ��";

	public static final String MSG_NO_START_YEAR = "��ѡ��ִ�����";

	public static final String MSG_NO_START_MONTH = "��ѡ��ִ���·�";

	public static final String MSG_NO_START_WEEK_OF_MONTH = "��ѡ��ڼ���ִ��";

	public static final String MSG_NO_START_DAY_OF_WEEK = "��ѡ�����ڼ�ִ��";

	public static final String MSG_NO_NEXT_FIRE_TIME = "����ʱ�����ô��󣬸����񲻻ᱻ����";

	public static final String MSG_NEXT_FIRE_TIME = "�´�ִ��ʱ�䣺\n";

	public static final String MSG_NEXT_FIRE_TIME_LATER_THAN_END_TIME = "�������ʱ������ִ��ʱ��";

	public static final String MSG_NO_TRIGGER_PLUG_IN = "û�����õ���ʱ��";

	public static final String MSG_NO_END_TIME_PLUG_IN = "û�����ý���ʱ��";

	public static final String MSG_NO_SELECT_JOBS = "��ѡ������";

	public static boolean isJobKey(String key) {
		if (key == null)
			return false;
		if ("".equals(key))
			return false;
		if (key.length() < 35)
			return false;
		if (key.startsWith("job"))
			return true;
		return false;
	}

	public static boolean isBlank(String str) {
		if (str == null)
			return true;
		if ("".equals(str))
			return true;
		if ("��".equals(str)) {
			return true;
		}
		return false;
	}

	public static boolean before(Date date1, Date date2) {
		if (date1 == null || date2 == null)
			return true;
		return date1.before(date2);
	}

	public static boolean after(Date date1, Date date2) {
		return date1.after(date2);
	}

	public static int change(String date) {
		int retVal = 0;
		if ("������".equals(date) || "һ��".equals(date) || "��һ��".equals(date)) {
			retVal = 1;
		} else if ("����һ".equals(date) || "����".equals(date)
				|| "�ڶ���".equals(date)) {
			retVal = 2;
		} else if ("���ڶ�".equals(date) || "����".equals(date)
				|| "������".equals(date)) {
			retVal = 3;
		} else if ("������".equals(date) || "����".equals(date)
				|| "������".equals(date)) {
			retVal = 4;
		} else if ("������".equals(date) || "����".equals(date)
				|| "������".equals(date)) {
			retVal = 5;
		} else if ("������".equals(date) || "����".equals(date)
				|| "������".equals(date)) {
			retVal = 6;
		} else if ("������".equals(date) || "����".equals(date)
				|| "������".equals(date)) {
			retVal = 7;
		}

		if ("����".equals(date)) {
			retVal = 8;
		} else if ("����".equals(date)) {
			retVal = 9;
		} else if ("ʮ��".equals(date)) {
			retVal = 10;
		} else if ("ʮһ��".equals(date)) {
			retVal = 11;
		} else if ("ʮ����".equals(date)) {
			retVal = 12;
		}

		return retVal;
	}

	public static boolean isLastDayOfWeek(String date) {
		if ("���һ��".equals(date)) {
			return true;
		}
		return false;
	}

	public static boolean isLastDayOfMonth(String date) {
		if ("���һ��".equals(date)) {
			return true;
		}
		return false;
	}

	public static Date getDate(int year, int month, int weekOfMonth,
			int dayOfWeek, int hour, int minute, int second) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.WEEK_OF_MONTH, weekOfMonth);
		calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);
		return calendar.getTime();
	}

	public static Date getDate(int year, int month, int day, int hour,
			int minute, int second) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);
		return calendar.getTime();
	}

	public static Date getDate(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		return calendar.getTime();
	}

	public static Date getLastDayOfMonth(int year, int month, int hour,
			int minute, int second) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		int value = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, value);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);
		return cal.getTime();
	}

	public static Date getLastDayOfWeek(int year, int month, int dayOfWeek,
			int hour, int minute, int second) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		int value = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, value);
		int lastDay = cal.getTime().getDate();
		int lastDayOfWeek = cal.getTime().getDay();
		int back = lastDayOfWeek - dayOfWeek;
		if (back < 0) {
			back += 7;
		}
		lastDay -= back;
		cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, lastDay);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);
		return cal.getTime();
	}

	public static int getFirstDayOfWeekInMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	public static String format(Date date) {
		String retVal = "";
		if (date != null) {
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd-a-hh-mm-ss-E");
			retVal = format.format(date);
		}
		return retVal;
	}

	public static String quartzFormat(Date date) {
		String retVal = "";
		if (date != null) {
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd!HH:mm:ss");
			retVal = format.format(date);
			retVal = retVal.replaceAll("!", "T");
		}
		return retVal;
	}

	public static int getWeeksOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		return cal.getActualMaximum(Calendar.WEEK_OF_MONTH);
	}

	public static boolean isMonthValid(String month) {
		if (!isBlank(month)) {
			int m = Integer.parseInt(month);
			if (m >= 1 && m <= 12) {
				return true;
			}
		}
		return false;
	}

	public static boolean isDayValid(String day) {
		if (!isBlank(day)) {
			int m = Integer.parseInt(day);
			if (m >= 1 && m <= 12) {
				return true;
			}
		}
		return false;
	}

	public static boolean isSecondValid(int second) {
		if (second <= 0)
			return false;
		return true;
	}

	public static boolean isRepeatCountValid(int repeatCnt) {
		if (repeatCnt < 0)
			return false;
		return true;
	}

	public static boolean isPast(Date date) {
		if (date == null)
			return true;
		if (date.before(new Date()))
			return true;
		return false;
	}

	public static boolean checkUrl(String urlStr) {
		try {
			new URL(urlStr);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isUrl(String url) {
		if (url == null)
			return false;
		if ("".equals(url))
			return false;
		String regEx = "^(http|https|ftp)//://([a-zA-Z0-9//.//-]+(//:[a-zA-"
				+ "Z0-9//.&%//$//-]+)*@)?((25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{"
				+ "2}|[1-9]{1}[0-9]{1}|[1-9])//.(25[0-5]|2[0-4][0-9]|[0-1]{1}"
				+ "[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)//.(25[0-5]|2[0-4][0-9]|"
				+ "[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)//.(25[0-5]|2[0-"
				+ "4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])|([a-zA-Z0"
				+ "-9//-]+//.)*[a-zA-Z0-9//-]+//.[a-zA-Z]{2,4})(//:[0-9]+)?(/"
				+ "[^/][a-zA-Z0-9//.//,//?//'///////+&%//$//=~_//-@]*)*$";
		Pattern p = Pattern.compile(regEx);
		Matcher matcher = p.matcher(url);
		return matcher.matches();
	}

	public static boolean isCharacterWildCard(String str) {
		if (str == null)
			return false;
		if (SPECIAL_CHARACTER_WILD_CARD.equals(str) || "0/1".equals(str))
			return true;
		return false;
	}

	public static boolean isCharacterLast(String str) {
		if (str == null)
			return false;
		if (SPECIAL_CHARACTER_LAST.equals(str))
			return true;
		return false;
	}

	public static boolean isCharacterIncre(String str) {
		if (str == null)
			return false;
		if (SPECIAL_CHARACTER_INCRE.equals(str))
			return true;
		return false;
	}

	public static boolean isCharacterNondetermine(String str) {
		if (str == null)
			return false;
		if (SPECIAL_CHARACTER_NONDETERMINE.equals(str))
			return true;
		return false;
	}

	public static boolean isCharacterDay(String str) {
		if (str == null)
			return false;
		if (SPECIAL_CHARACTER_DAY.equals(str))
			return true;
		return false;
	}

	public static String formattedNow() {
		return format(new Date());
	}

}
