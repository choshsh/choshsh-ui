<template>
  <CCard>
    <CCardBody>
      <table border="1" class="col-md-12 table-outline">
        <tbody>
          <tr>
            <th style="width: 10%">관리번호</th>
            <td style="width: 20%">{{ item.ciNo }}</td>
            <th style="width: 10%">업무명</th>
            <td style="width: 20%">{{ item.nickname }}</td>
            <th style="width: 10%">호스트명</th>
            <td style="width: 30%">{{ item.astNm }}</td>
          </tr>
          <tr>
            <th>관리 부서</th>
            <td>{{ item.mngDeptNm }}</td>
            <th>관리 담당자</th>
            <td>{{ item.mngEmplNm }}</td>
            <th>위치</th>
            <td>{{ item.locationDetail }}</td>
          </tr>
          <tr>
            <th>제조사</th>
            <td>{{ item.serverMakerNm }}</td>
            <th>모델명</th>
            <td>{{ item.ciModel }}</td>
            <th>시리얼 번호</th>
            <td>{{ item.serialNo }}</td>
          </tr>
          <tr>
            <th>구입일자</th>
            <td>{{ item.buyDate }}</td>
            <th>구입금액</th>
            <td>{{ item.buyAmt }}</td>
            <th>유지보수</th>
            <td>{{ item.mngAgree }}</td>
          </tr>
          <tr>
            <th>구입부서</th>
            <td>{{ item.buyDeptNm }}</td>
            <th>구입거래선</th>
            <td>{{ item.buyPartnerNm }}</td>
          </tr>
          <tr>
            <th>OS 계열</th>
            <td>{{ item.serverOsLine }}</td>
            <th>OS 상세</th>
            <td>{{ item.serverOs }}</td>
            <th>DBMS</th>
            <td>{{ item.serverDbms }}</td>
          </tr>
          <tr>
            <th>스펙 요약</th>
            <td colspan="7">{{ item.spec }}</td>
          </tr>
        </tbody>
      </table>
    </CCardBody>
  </CCard>
</template>

<script>
export default {
  name: "ServersInfo",
  props: {
    item: Object,
  },
};
</script>