package code.problems1101_1200;

import java.util.Calendar;

/**
 * @author chengzhe yan
 * @description : 给你一个日期，请你设计一个算法来判断它是对应一周中的哪一天
 * @date 2022/1/3 4:26 下午
 */
public class Problem1185 {


	public String dayOfTheWeek(int day, int month, int year) {
		String[] week = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
		int[] monthDays = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30};
		// 当年份是 400 的倍数或者是 4的倍数且不是 100 的倍数时，该年会在二月份多出一天。
		int days = 365 * (year - 1971) + (year - 1969) / 4;
		for (int i = 0; i < month - 1; i++) {
			days += monthDays[i];
		}
		boolean isLeapYear = (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) && month >= 3;
		if (isLeapYear) {
			days += 1;
		}
		days += day;
		return week[(days + 3) % 7];
	}

	public String dayOfTheWeek1(int day, int month, int year) {
		String[] week = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, day);
		int i = calendar.get(Calendar.DAY_OF_WEEK);
		return week[i - 1];
	}

	public static void main(String[] args) {
		String s = new Problem1185().dayOfTheWeek(3, 1, 2022);
		System.out.println(s);
	}
}
