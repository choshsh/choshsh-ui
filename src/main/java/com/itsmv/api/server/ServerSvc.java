package com.itsmv.api.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itsmv.api.util.ExcelUtil;

@Service
public class ServerSvc {

    @Autowired
    private ServerRepo serverRepo;

    List<ServerEntity> list() {
        List<ServerEntity> list = new ArrayList<>();
        Iterable<ServerEntity> it = serverRepo.findAll();
        it.forEach(o -> {
            o.setDeptNm(o.getDeptEntity().getDeptNm());
            o.setLocationNm(o.getLocationEntity().getLocationNm());
            o.setOprNm(o.getOprEntity().getOprNm());
            o.setUsageNm(o.getUsageEntity().getUsageNm());
            o.setOsNm(o.getOsEntity().getOsNm());
            o.setOsDetail(o.getOsEntity().getOsDetail());
            list.add(o);
        });
        return list;
    }

    @SuppressWarnings("unchecked")
    void excel(HttpServletRequest req, HttpServletResponse res) throws Exception {
        String[][] dKey = {{"id", "관리번호"}, {"locationNm", "사업장"}, {"serverNm", "업무명"}, {"hostname", "호스트명"},
                {"oprNm", "운영 상태"}, {"usageNm", "용도"}, {"ipAddr", "서비스 IP"}, {"osNm", "OS계열"},
                {"osDetail", "OS상세"}, {"makerNm", "제조사"}, {"makerModelNm", "모델명"}, {"serial", "시리얼번호"},
                {"spec", "스펙"}, {"warranty", "유지보수"}, {"locationDetail", "설치장소"}, {"deptNm", "관리 부서"},
                {"mngEmplNm", "관리 담당자"}, {"buyDate", "설치일자"}};
        String[] shArr = {"Seoul", "Gumi"};

        ExcelUtil exu = new ExcelUtil("물리서버_리스트");
        for (String s : shArr) {
            exu.addSheet(s);
            /* 헤더 */
            exu.addRow();
            for (int i = 0; i < dKey.length; i++) {
                exu.addCell(i, dKey[i][1]);
            }
            exu.setFilter();
            exu.getSh().createFreezePane(5, 1);
        }

        /* 바디 */
        ObjectMapper mapper = new ObjectMapper();
        for (ServerEntity o : this.list()) {
            exu.setSh(exu.getWb().getSheet(o.getLocationEntity().getLocationNm()));
            exu.addRow();
            HashMap<String, Object> m = mapper.convertValue(o, HashMap.class);
            for (int i = 0; i < dKey.length; i++) {
                exu.addCell(i, m.get(dKey[i][0]) == null ? "-" : m.get(dKey[i][0]).toString());
            }
        }
        /* 너비 조정 */
        IntStream.range(0, shArr.length).forEach(i -> {
            exu.setWidth(exu.getWb().getSheetAt(i).getRow(0), i);
        });
        /* 1행 필터 생성 */
        exu.setFilter();

        /* 다운로드 */
        exu.download(req, res);
    }
}