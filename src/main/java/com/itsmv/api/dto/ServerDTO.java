package com.itsmv.api.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ServerDTO {
	protected String ciNo;
	protected String ciModel;
	protected String ciItem;
	protected String ciFirstChar;
	protected String buyDate;
	protected String buyType;
	protected String warranty;
	protected String serialNo;
	protected String astNm;
	protected String nickname;
	protected String svcCode;
	protected String makerCd;
	protected String buyPartner;
	protected String buyPartnerNm;
	protected String useGbn;
	protected String usage;
	protected String perGbn;
	protected String oprGbn;
	protected String spec;
	protected String location;
	protected String mngEmpl;
	protected String mngDept;
	protected String useEmpl;
	protected String useDept;
	protected String oprStDate;
	protected String curStDate;
	protected String buyDept;
	protected String buyDeptNm;
	protected String depreGbn;
	protected int buyAmt;
	protected int deperAmt;
	protected int deperJan;
	protected String cost;
	protected int depreYear;
	protected String ordNo;
	protected String ordSeq;
	protected String astNumber;
	protected String strategGbn;
	protected String strategNo;
	protected String mngAgree;
	protected String insDate;
	protected String insEmpl;
	protected String updDate;
	protected String updEmpl;
	protected String scrapDate;
	protected String regDate;
	protected String rentFrom;
	protected String rentTo;
	protected String comCd;
	protected String oprNm;
	protected String mngEmplNm;
	protected String mngDeptNm;
	protected String useEmplNm;
	protected String serverIp;
	protected String serverDbms;
	protected String serverPdsk;
	protected String serverMacAddr;
	protected String serverOs;
	protected String serverOsLine;
	protected String serverUsage;
	protected String serverMakerNm;
	protected String locationNm;
	protected String locationDetail;
	protected String plant;
	protected List<ServerChangeDTO> changeInfo;
}
