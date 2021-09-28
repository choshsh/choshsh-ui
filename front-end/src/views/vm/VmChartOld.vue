<template>
  <div style="margin-right:10%; margin-left:10%">
    <CCardGroup columns class="card-columns cols-1">
      <CCard>
        <CCardHeader>VM - 용도</CCardHeader>
        <CCardBody>
          <CChartBarAdvMulti
            :labels="labels.main"
            :label="labels.sub"
            :datas="[datasParam.main['op'], datasParam.main['dev']]"
          />
        </CCardBody>
      </CCard>
      <CCard>
        <CCardHeader>VM - 용도2</CCardHeader>
        <CCardBody>
          <CChartDoughnutAdv :labels="labels.sub" :datas="datasParam.sub" />
        </CCardBody>
      </CCard>
      <CCard>
        <CCardHeader>VM - OS</CCardHeader>
        <CCardBody>
          <CChartDoughnutAdv :labels="labels.mainOs" :datas="datasParam.mainOs" />
        </CCardBody>
      </CCard>
    </CCardGroup>

    <CCard>
      <CCardHeader>
        <h4>
          <strong>리소스 사용률</strong>
        </h4>
      </CCardHeader>
      <CCardBody>
        <CTabs variant="pills" :vertical="vertical" fade>
          <CTab v-for="obj01 in lineObjList01" v-bind:key="obj01">
            <template slot="title">{{obj01}}</template>
            <CCard v-for="obj02 in lineObjList02" v-bind:key="obj02">
              <CCardHeader>
                <h5>{{obj02}} 장비</h5>
              </CCardHeader>
              <CCardBody class="ml-5 mr-5">
                <CCardGroup columns class="card-columns cols-2">
                  <CCard>
                    <CCardHeader>CPU, Memory 사용률 (%)</CCardHeader>
                    <CCardBody>
                      <CChartBarAdvMulti
                        :label="['CPU', '메모리']"
                        :labels="hostParam[obj01][obj02]['nm']"
                        :datas="[hostParam[obj01][obj02]['cpu'], hostParam[obj01][obj02]['memory']]"
                      />
                    </CCardBody>
                  </CCard>
                  <CCard>
                    <CCardHeader>Datastore 사용률 (%)</CCardHeader>
                    <CCardBody>
                      <CChartBarAdv
                        :color="'#41B883'"
                        :labels="dsParam[obj01][obj02]['nm']"
                        :datas="dsParam[obj01][obj02]['storage']"
                      />
                    </CCardBody>
                  </CCard>
                </CCardGroup>
              </CCardBody>
            </CCard>
          </CTab>
        </CTabs>
      </CCardBody>
    </CCard>

    <CCardGroup columns class="card-columns cols-2">
      <CCard>
        <CCardHeader>VM - Windows</CCardHeader>
        <CCardBody>
          <CChartBarAdv :color="color" :labels="labels.NT" :datas="datasParam.NT" />
        </CCardBody>
      </CCard>
      <CCard>
        <CCardHeader>VM - Linux</CCardHeader>
        <CCardBody>
          <CChartBarAdv :color="'#DD1B16'" :labels="labels.LINUX" :datas="datasParam.LINUX" />
        </CCardBody>
      </CCard>
    </CCardGroup>
  </div>
</template>

<script>
import CChartBarAdv from "../base/CChartBarAdv.vue";
import CChartBarAdvMulti from "../base/CChartBarAdvMulti.vue";
import CChartDoughnutAdv from "../base/CChartDoughnutAdv";
import CChartLineAdv from "../base/CChartLineAdv.vue";
import axios from "axios";

export default {
  name: "Charts",
  components: {
    CChartBarAdv,
    CChartDoughnutAdv,
    CChartLineAdv,
    CChartBarAdvMulti
  },
  data() {
    return {
      color: "#007bff",
      labels: {
        main: ["본사", "구미"],
        sub: ["운영", "개발"],
        mainOs: ["Windows", "Linux"],
        NT: [],
        LINUX: []
      },
      vertical: { vertical: true, navs: "col-md-1", content: "col-md-11" },
      datasParam: { NT: [], LINUX: [], main: { op: [], dev: [] } },
      lineObjList01: ["본사", "구미"],
      lineObjList02: ["HP", "DELL"],
      dsParam: {
        본사: {
          HP: {
            nm: [],
            storage: []
          },
          DELL: {
            nm: [],
            storage: []
          }
        },
        구미: {
          HP: {
            nm: [],
            storage: []
          },
          DELL: {
            nm: [],
            storage: []
          }
        }
      },
      hostParam: {
        본사: {
          HP: {
            nm: [],
            cpu: [],
            memory: []
          },
          DELL: {
            nm: [],
            cpu: [],
            memory: []
          }
        },
        구미: {
          HP: {
            nm: [],
            cpu: [],
            memory: []
          },
          DELL: {
            nm: [],
            cpu: [],
            memory: []
          }
        }
      },
      vmChartKey: 0
    };
  },
  methods: {
    getData() {
      axios
        .post("/api/vms/getVmChart", { 1: 1 })
        .then(res => {
          this.setData(res.data);
        })
        .catch(e => console.log(e));

      axios
        .post("/api/vms/getVmChartHostDatastore", { 1: 1 })
        .then(res => {
          this.setDataDs(res.data.getVmDatastore);
          this.setDataHost(res.data.getVmHost);
        })
        .catch(e => console.log(e));
    },
    setData(data) {
      data.getCountPerUsageDetailTotal.map(o => {
        this.labels[o.VM_OS_LINE].push(o.VM_OS_DETAIL);
        this.datasParam[o.VM_OS_LINE].push(o.CNT);
      });
      // 전체 용도
      this.datasParam["main"]["op"].push(data.getVmChartMonthly.sopCnt);
      this.datasParam["main"]["op"].push(data.getVmChartMonthly.gopCnt);
      this.datasParam["main"]["dev"].push(data.getVmChartMonthly.sdevCnt);
      this.datasParam["main"]["dev"].push(data.getVmChartMonthly.gdevCnt);
      // 전체 용도 - 도넛
      this.datasParam["sub"] = [
        data.getVmChartMonthly.sopCnt + data.getVmChartMonthly.gopCnt,
        data.getVmChartMonthly.sdevCnt + data.getVmChartMonthly.gdevCnt
      ];
      // 전체 OS
      this.datasParam["mainOs"] = [
        data.getVmChartMonthly.ntCnt,
        data.getVmChartMonthly.linuxCnt
      ];

      this.vmChartKey++;
    },
    setDataDs(data) {
      data.forEach(o => {
        this.dsParam[o.vmClusterLocationNm][o.vmClusterMaker]["nm"].push(
          o.dsNm
        );
        this.dsParam[o.vmClusterLocationNm][o.vmClusterMaker]["storage"].push(
          o.usageRate
        );
      });
    },
    setDataHost(data) {
      data.forEach(o => {
        this.hostParam[o.vmClusterLocationNm][o.vmClusterMaker]["nm"].push(
          o.hostNm
        );
        this.hostParam[o.vmClusterLocationNm][o.vmClusterMaker]["cpu"].push(
          o.cpuRate
        );
        this.hostParam[o.vmClusterLocationNm][o.vmClusterMaker]["memory"].push(
          o.memoryRate
        );
      });
    }
  },
  created() {
    this.getData();
  }
};
</script>
