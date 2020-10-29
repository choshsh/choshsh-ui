<template>
  <div>
    <CCard>
      <CCardBody>
        <CRow>
          <CCol col="5">
            <h4 id="traffic" class="card-title mb-0">정보팀 서버 현황</h4>
            <div class="small text-muted">2020</div>
          </CCol>
        </CRow>
        <CRow>
          <CCol col="6" v-for="i in this.objList" v-bind:key="i">
            <CChartLineAdv
              :datas="chartItems[i]"
              :label="chartLabel[i]"
              style="margin-top: 2%"
            />
          </CCol>
        </CRow>
        <CRow>
          <CCol col="6" v-for="i in this.objList" v-bind:key="i">
            <CCard style="margin-top: 50px">
              <CCardHeader>
                <slot name="header">
                  <select
                    @change="setList(i, selectedList[i])"
                    v-model="selectedList[i]"
                    style="width: 10%"
                  >
                    <option
                      v-for="option in options"
                      v-bind:value="option"
                      v-bind:key="option"
                    >
                      {{ option }}
                    </option>
                  </select>
                  월 {{ i.toUpperCase() }} 변경사항
                </slot>
              </CCardHeader>
              <CCardBody>
                <CRow>
                  <CCol col="6" v-for="div in divParams" v-bind:key="div">
                    <CCard :key="keyList[i]">
                      <CCardHeader class="font-weight-bold">
                        <slot name="header"
                          >{{ Boolean(div === "add") ? "신규" : "폐기" }}
                          {{ i.toUpperCase() }}:
                          {{ listItemsLength[i + "_" + div] }}건</slot
                        >
                      </CCardHeader>
                      <CCardBody>
                        <table border="0" class="col-md-12 table-xs">
                          <tr class="font-weight-bold">
                            <td>{{ "사업장" }}</td>
                            <td>업무명</td>
                          </tr>
                          <tr
                            v-for="iAdd in listItems[i + '_' + div]"
                            v-bind:key="iAdd.i"
                          >
                            <td>
                              {{
                                Boolean(i === "server")
                                  ? Boolean(iAdd.locationNm === "본사(서울)")
                                    ? "본사"
                                    : iAdd.locationNm
                                  : iAdd.vmClusterLocationNm
                              }}
                            </td>
                            <td>
                              <CIcon
                                name="cil-info"
                                class="mr-1 mb-1"
                                v-c-popover="getPopoverTemplate(i, iAdd)"
                                style="cursor: pointer"
                              />{{
                                Boolean(i === "server")
                                  ? iAdd.nickname
                                  : iAdd.vmNickname
                              }}
                            </td>
                          </tr>
                        </table>
                      </CCardBody>
                    </CCard>
                  </CCol>
                </CRow>
              </CCardBody>
            </CCard>
          </CCol>
        </CRow>
      </CCardBody>
    </CCard>
  </div>
</template>

<script>
import CChartLineAdv from "./base/CChartLineAdv.vue";
import axios from "axios";

export default {
  name: "Dashboard",
  components: {
    CChartLineAdv,
  },
  data() {
    return {
      objList: ["server", "vm"],
      chartItems: {
        server: [],
        vm: [],
      },
      listItems: {},
      listItemsLength: {},
      divParams: ["add", "remove"],
      chartLabel: {
        server: ["본사-물리서버", "구미-물리서버"],
        vm: ["본사-VM", "구미-VM"],
      },
      options: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12],
      selected: new Date().getMonth() + 1,
      selectedList: {
        server: new Date().getMonth() + 1,
        vm: new Date().getMonth() + 1,
      },
      keyList: {
        server: 0,
        vm: 0,
      },
    };
  },
  methods: {
    setServerChart(plantParam, init) {
      axios
        .post("/api/getServerChartYear", { plant: plantParam })
        .then((res) => {
          this.chartItems[this.objList[0]].push(res.data);
          if (init > 0) this.setServerChart("G");
        })
        .catch((e) => console.log(e));
    },
    setVmChart(vmClusterLocationNmParam, init) {
      axios
        .post("/api/getVmChartYear", {
          vmClusterLocationNm: vmClusterLocationNmParam,
        })
        .then((res) => {
          this.chartItems[this.objList[1]].push(res.data);
          if (init > 0) this.setVmChart("구미");
        })
        .catch((e) => console.log(e));
    },
    setList(obj, monthParam) {
      const url = obj === "server" ? "/api/serversAll" : "/api/vmsAll";
      this.divParams.forEach((o) => {
        axios
          .post(url, {
            monthParam: monthParam,
            div: o,
          })
          .then((res) => {
            this.listItems[obj + "_" + o] = res.data;
            this.listItemsLength[obj + "_" + o] = res.data.length;
            this.keyList[obj]++;
          })
          .catch((e) => console.log(e));
      });
    },
    getPopoverTemplate(flag, item) {
      let str = "";
      str +=
        "<table border='1' class='table-outline' style='width:250px; text-align: center;'>";

      if (flag === this.objList[0]) {
        str += "<tr>";
        str += "<th>관리번호</th>";
        str += "<td>" + item.ciNo + "</td>";
        str += "</tr>";

        str += "<tr>";
        str += "<th>업무명</th>";
        str += "<td>" + item.nickname + "</td>";
        str += "</tr>";

        str += "<tr>";
        str += "<th>호스트명</th>";
        str += "<td>" + item.astNm + "</td>";
        str += "</tr>";

        str += "<tr>";
        str += "<th>제조사</th>";
        str += "<td>" + item.serverMakerNm + "</td>";
        str += "</tr>";

        str += "<tr>";
        str += "<th>모델명</th>";
        str += "<td>" + item.ciModel + "</td>";
        str += "</tr>";

        str += "<tr>";
        str += "<th>OS</th>";
        str += "<td>" + item.serverOs + "</td>";
        str += "</tr>";

        str += "<tr>";
        str += "<th>관리부서</th>";
        str += "<td>" + item.mngDeptNm + "</td>";
        str += "</tr>";

        str += "<tr>";
        str += "<th>담당자</th>";
        str += "<td>" + item.mngEmplNm + "</td>";
        str += "</tr>";

        str += "<tr>";
        str += "<th>구입일자</th>";
        str += "<td>" + item.buyDate + "</td>";
        str += "</tr>";

        str += "<tr>";
        str += "<th>폐기일자</th>";
        str += "<td>" + (item.scrapDate ? item.scrapDate : "-") + "</td>";
        str += "</tr>";
      } else {
        str += "<tr>";
        str += "<th>업무명</th>";
        str += "<td>" + item.vmNickname + "</td>";
        str += "</tr>";

        str += "<tr>";
        str += "<th>VM명</th>";
        str += "<td>" + item.vmNm + "</td>";
        str += "</tr>";

        str += "<tr>";
        str += "<th>DNS</th>";
        str += "<td>" + item.vmHostname + "</td>";
        str += "</tr>";

        str += "<tr>";
        str += "<th>OS</th>";
        str += "<td>" + item.vmOsDetail + "</td>";
        str += "</tr>";

        str += "<tr>";
        str += "<th>담당자</th>";
        str += "<td>" + item.vmUseEmpl + "</td>";
        str += "</tr>";

        str += "<tr>";
        str += "<th>Comment</th>";
        str += "<td>" + item.vmComment + "</td>";
        str += "</tr>";

        str += "<tr>";
        str += "<th>생성일자</th>";
        str += "<td>" + item.vmRegDate.substr(0, 10) + "</td>";
        str += "</tr>";

        str += "<tr>";
        str += "<th>폐기일자</th>";
        str += "<td>" + (item.vmDelDate ? item.vmDelDate.substr(0, 10) : "-") + "</td>";
        str += "</tr>";
      }

      str += "</table>";

      const obj = {
        header: "Info",
        content: "",
        placement: "top-end",
      };

      obj["content"] = str;

      return obj;
    },
  },
  created() {
    this.setServerChart("S", 1);
    this.setVmChart("본사", 1);
    this.objList.forEach((o) => {
      this.setList(o, this.selectedList[o]);
    });
  },
};
</script>
