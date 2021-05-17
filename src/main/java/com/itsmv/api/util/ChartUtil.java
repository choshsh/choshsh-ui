package com.itsmv.api.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class ChartUtil {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private Calendar cal = Calendar.getInstance();
	private int yearNow;
	private int monthNow;

	public ChartUtil() {
		this.yearNow = cal.get(Calendar.YEAR);
		this.monthNow = cal.get(Calendar.MONTH) + 1;
	}

	public List<Integer> dataPerYear(List<Integer> param) {
		List<Integer> result = new ArrayList<>();
		List<Integer> listYear = new ArrayList<>();
		List<Integer> listMonth = new ArrayList<>();
		param.forEach(i -> listYear.add(Integer.parseInt(i.toString().substring(0, 4))));
		/* 이번 년 이전 cnt */
		int defaultCnt = listYear.indexOf(this.yearNow);
		if (defaultCnt < 0) {
			/* 결과값 */
			for (int i = 1; i < 13; i++) {
				if (!(i > this.monthNow)) {
					result.add(listYear.size());
				}
			}
		} else {
			/* 이번 년 배열 */
			param.subList(defaultCnt, listYear.size()).forEach(i -> listMonth.add(Integer.parseInt(i.toString().substring(4, 6))));
			/* 결과값 */
			for (int i = 1; i < 13; i++) {
				if (!(i > this.monthNow)) {
					result.add(0);
					int addAcc = listMonth.lastIndexOf(i) >= 0 ? listMonth.lastIndexOf(i) - listMonth.indexOf(i) + 1
							: 0;
					result.set(i - 1, defaultCnt += addAcc);
				}

			}
		}
		return result;
	}

	public List<Integer> delDataPerYear(List<Integer> param) {
		List<Integer> result = new ArrayList<>();
		List<Integer> listYear = new ArrayList<>();
		List<Integer> listMonth = new ArrayList<>();
		param.forEach(i -> listYear.add(Integer.parseInt(i.toString().substring(0, 4))));
		/* 이번 년 이전 cnt */
		int defaultCnt = listYear.indexOf(this.yearNow);
		if (defaultCnt < 0) {
			/* 결과값 */
			for (int i = 1; i < 13; i++) {
				if (!(i > this.monthNow)) {
					result.add(listYear.size());
				}

			}
		} else {
			/* 이번 년 배열 */
			param.subList(defaultCnt, listYear.size()).forEach(i -> listMonth.add(Integer.parseInt(i.toString().substring(4, 6))));
			/* 결과값 */
			for (int i = 1; i < 13; i++) {
				if (!(i > this.monthNow)) {
					result.add(0);
					int delAcc = listMonth.lastIndexOf(i) >= 0 ? listMonth.lastIndexOf(i) - listMonth.indexOf(i) + 1
							: 0;
					result.set(i - 1, defaultCnt += delAcc);
				}

			}
		}
		return result;
	}

}
