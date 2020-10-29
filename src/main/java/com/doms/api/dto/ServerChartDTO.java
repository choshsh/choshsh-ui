package com.doms.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ServerChartDTO {
	private String serverChartDate;
	private String plant;
	private String locationNm;
	private int totalCnt;
	// 장비 운영상태 별 구분
	private int useCnt; // 지급사용
	private int dstCnt; // 폐기대상
	private int keepCnt; // 보관
	// 용도 별 구분
	private int opCnt; // 운영
	private int devCnt; // 개발
	private int idleCnt; // 유휴
	private int etcCnt; // 나머지
	// os 별 구분
	private int ntCnt; // nt
	private int linuxCnt; // linux
	private int stgCnt; // storage
	private int udfCnt; // undefined

	// 날짜
	private int year; // undefined
	private int month; // undefined
}
