<template>
  <div :key="chartKey"  style="margin-right:10%; margin-left:10%">
    <CCard v-for="o in objGroup" v-bind:key="o.cd">
      <CCardHeader>
        <h4>
          <strong>{{o.nm}}</strong>
        </h4>
      </CCardHeader>
      <CCardBody>
        <CCardGroup columns class="card-columns cols-1">
          <CCard>
            <CCardHeader>장비 상태</CCardHeader>
            <CCardBody>
              <CChartBarAdv :color="color" :labels="labelsOpr" :datas="datasParam[o.cd + '_opr']" />
            </CCardBody>
          </CCard>
          <CCard>
            <CCardHeader>용도</CCardHeader>
            <CCardBody>
              <CChartBarAdv :color="color" :labels="labelsUsg" :datas="datasParam[o.cd + '_usg']" />
            </CCardBody>
          </CCard>
          <CCard>
            <CCardHeader>OS</CCardHeader>
            <CCardBody>
              <CChartDoughnutAdv :labels="labelsOs" :datas="datasParam[o.cd + '_os']" />
            </CCardBody>
          </CCard>
        </CCardGroup>
      </CCardBody>
    </CCard>
  </div>
</template>

<script>
import CChartBarAdv from "../base/CChartBarAdv.vue";
import CChartDoughnutAdv from "../base/CChartDoughnutAdv";
import axios from "axios";

export default {
  name: "Charts",
  components: {
    CChartBarAdv,
    CChartDoughnutAdv
  },
  data() {
    return {
      color: "#007bff",
      labelsOs: ["Windows", "Linux", "Storage", "null"],
      labelsOpr: ["지급사용", "보관", "폐기대상"],
      labelsUsg: ["운영", "개발", "유휴", "기타"],
      objGroup: [
        { nm: "본사", cd: "S0" },
        { nm: "3공장", cd: "G1" },
        { nm: "1공장", cd: "G2" },
        { nm: "2공장", cd: "G6" },
        { nm: "4공장", cd: "G3" },
        { nm: "군산", cd: "K1" }
      ],
      datasParam: {},
      chartKey: 0
    };
  },
  methods: {
    getData() {
      axios
        .post("/api/getServerChartThisMonth", { 1: 1 })
        .then(res => {
          this.setData(res.data);
        })
        .catch(e => console.log(e));
    },
    setData(data) {
      data.map(item => {
        this.datasParam[item.plant + "_os"] = [
          item.ntCnt,
          item.linuxCnt,
          item.stgCnt,
          item.udfCnt
        ];
        this.datasParam[item.plant + "_opr"] = [
          item.useCnt,
          item.keepCnt,
          item.dstCnt
        ];
        this.datasParam[item.plant + "_usg"] = [
          item.opCnt,
          item.devCnt,
          item.idleCnt,
          item.etcCnt
        ];
      });
      this.chartKey++;
    }
  },
  created: function() {
    this.getData();
  }
};
</script>
