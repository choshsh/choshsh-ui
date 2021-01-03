<template>
  <CCard>
    <CCardBody>
      <table border="1" class="col-md-12 table-outline">
        <tbody>
          <tr>
            <th style="width: 10%">관리번호</th>
            <td style="width: 20%">{{ item.id }}</td>
            <th style="width: 10%">업무명</th>
            <td style="width: 20%">{{ item.serverNm }}</td>
            <th style="width: 10%">호스트명</th>
            <td style="width: 30%">{{ item.hostname }}</td>
          </tr>
          <tr>
            <th>관리 부서</th>
            <td>{{ item.deptNm }}</td>
            <th>관리 담당자</th>
            <td>{{ item.mngEmplNm }}</td>
            <th>위치</th>
            <td>{{ item.locationDetail }}</td>
          </tr>
          <tr>
            <th>제조사</th>
            <td>{{ item.makerNm }}</td>
            <th>모델명</th>
            <td>{{ item.makerModelNm }}</td>
            <th>시리얼 번호</th>
            <td>{{ item.serial }}</td>
          </tr>
          <tr>
            <th>구입일자</th>
            <td>{{ dateFormatter(item.buyDate) }}</td>
            <th>구입금액</th>
            <td>{{ item.price }}</td>
            <th>보증기간</th>
            <td>{{ item.warranty + "년" }}</td>
          </tr>
          <tr>
            <th>OS 계열</th>
            <td>{{ item.osNm }}</td>
            <th>OS 상세</th>
            <td colspan="3">{{ item.osDetail }}</td>
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
import moment from "moment";

export default {
  name: "ServersInfo",
  props: {
    item: Object,
  },
  methods: {
    dateFormatter(date) {
      return moment(date).format("YYYY-MM-DD");
    },
  },
};
</script>
