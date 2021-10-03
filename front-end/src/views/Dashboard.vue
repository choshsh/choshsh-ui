<template>
  <div>
    <CCard>
      <CCardBody>
        <CRow>
          <CCol col="5">
            <h4 id="traffic" class="card-title mb-0">정보팀 서버 현황</h4>
            <div class="small text-muted">{{ thisYear }}</div>
          </CCol>
        </CRow>
        <CRow>
          <CCol col="6">
            <CChartLineAdv
              :datas="chartItems['server']"
              :legends="chartLabel['server']"
              style="margin-top: 2%"
            />
          </CCol>
          <CCol col="6">
            <CChartLineAdv
              :datas="chartItems['vm']"
              :legends="chartLabel['vm']"
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
                                  : iAdd.locationName
                              }}
                            </td>
                            <td>
                              <a
                                href="javascript:;"
                                v-c-popover="getPopoverTemplate(i, iAdd)"
                              >
                                {{ iAdd.nickname }}
                              </a>
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
import * as axios from "@/assets/js/axios";
import urls from "@/assets/js/urls";

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
      tmpServer: [],
      tmpVM: [],
      thisYear: new Date().getFullYear(),
    };
  },
  methods: {
    // 차트 물리서버 설정
    async setServerChart(plantParam) {
      let data = await axios.post("/interface/getServerChartYear", {
        plant: plantParam,
      });
      this.chartItems["server"].push(data);
    },
    // 차트 가상서버 설정
    async setVmChart(location) {
      let data = await axios.get("/api/vm/chartYear/" + location);
      this.chartItems["vm"].push(data);
    },
    // 변경사항 데이터 설정
    setList(obj, monthParam) {
      let url = obj === "server" ? "/interface/server/all" : "/api/vm";
      if (obj === "server") {
        this.divParams.forEach(async (o) => {
          let data = await axios.post(url, {
            monthParam: monthParam,
            div: o,
          });
          this.listItems[obj + "_" + o] = data;
          this.listItemsLength[obj + "_" + o] = data.length;
        });
      } else {
        this.divParams.forEach(async (o) => {
          let data = await axios.get(url + "/" + o + "/month/" + monthParam);
          this.listItems[obj + "_" + o] = data;
          this.listItemsLength[obj + "_" + o] = data.length;
        });
      }
    },
    getPopoverTemplate(flag, item) {
      let str = "";
      str +=
        "<table border='1' class='table-outline' style='width:250px; text-align: center;'>";

      if (flag === this.objList[0]) {
        // 물리서버
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
        // VM
        str += "<tr>";
        str += "<th>업무명</th>";
        str += "<td>" + item.nickname + "</td>";
        str += "</tr>";

        str += "<tr>";
        str += "<th>VM명</th>";
        str += "<td>" + item.name + "</td>";
        str += "</tr>";

        str += "<tr>";
        str += "<th>OS</th>";
        str += "<td>" + item.osName + "</td>";
        str += "</tr>";

        str += "<tr>";
        str += "<th>담당자</th>";
        str += "<td>" + item.user + "</td>";
        str += "</tr>";

        str += "<tr>";
        str += "<th>Comment</th>";
        str += "<td>" + item.comment + "</td>";
        str += "</tr>";

        str += "<tr>";
        str += "<th>생성일자</th>";
        str += "<td>" + item.regDate + "</td>";
        str += "</tr>";

        str += "<tr>";
        str += "<th>폐기일자</th>";
        str += "<td>" + (item.delDate ? item.delDate : "-") + "</td>";
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
    this.setServerChart("S");
    this.setServerChart("G");
    this.setVmChart("S");
    this.setVmChart("K");
    this.objList.forEach((o) => {
      this.setList(o, this.selectedList[o]);
    });
  },
};
</script>
