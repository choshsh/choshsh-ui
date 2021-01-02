<template>
  <div>
    <CRow>
      <CCol sm="6">
        <CCard>
          <CCardHeader><strong>Navigation Menu</strong></CCardHeader>
          <CCardBody>
            <table border="0" class="col-md-12">
              <tbody>
                <tr>
                  <th>
                    Name
                  </th>
                  <th>Path</th>
                  <th>Icon</th>
                  <th style="width:5%;"></th>
                </tr>
                <tr v-for="(item, index) in nav" v-bind:key="index">
                  <td>
                    <CInput v-model="item.name" />
                  </td>
                  <td>
                    <CInput v-model="item.to" />
                  </td>
                  <td>
                    <CInput v-model="item.icon" />
                  </td>
                  <td
                    class="align-top text-center"
                    v-if="Boolean(index > 0 || nav.length > 1)"
                  >
                    <button class="btn btn-danger" @click="remove(nav, index)">
                      <CIcon name="cil-minus" size="sm" />
                    </button>
                  </td>
                </tr>
                <tr>
                  <td colspan="5" class="align-top text-center">
                    <button class="btn btn-success mb-2" @click="add(nav)">
                      <CIcon name="cil-plus" size="sm" />
                    </button>
                  </td>
                </tr>
              </tbody>
            </table>
            <div class="text-center row-fluid mb-1 mt-4">
              <button
                id="saveBtn"
                type="button"
                class="btn btn-primary"
                @click="save(nav, 'nav')"
              >
                저장
              </button>
            </div>
          </CCardBody>
        </CCard>
      </CCol>
      <CCol sm="6">
        <CCard>
          <CCardHeader><strong>Header Menu</strong></CCardHeader>
          <CCardBody>
            <table border="0" class="col-md-12">
              <tbody>
                <tr>
                  <th>Name</th>
                  <th>URL</th>
                  <th style="width:5%;"></th>
                </tr>
                <tr v-for="(item, index) in header" v-bind:key="index">
                  <td>
                    <CInput v-model="item.name" />
                  </td>
                  <td>
                    <CInput
                      v-model="item.url"
                      placeholder="http(s)://www.choshsh.com"
                    />
                  </td>
                  <td
                    class="align-top text-center"
                    v-if="Boolean(index > 0 || header.length > 1)"
                  >
                    <button
                      class="btn btn-danger"
                      @click="remove(header, index)"
                    >
                      <CIcon name="cil-minus" size="sm" />
                    </button>
                  </td>
                </tr>
                <tr>
                  <td colspan="5" class="align-top text-center">
                    <button class="btn btn-success mb-2" @click="add(header)">
                      <CIcon name="cil-plus" size="sm" />
                    </button>
                  </td>
                </tr>
              </tbody>
            </table>
            <div class="text-center row-fluid mb-1 mt-4">
              <button
                id="saveBtn"
                type="button"
                class="btn btn-primary"
                @click="save(header, 'header')"
              >
                저장
              </button>
            </div>
          </CCardBody>
        </CCard>
        <CCard>
          <CCardHeader><strong>Monitoring Menu</strong></CCardHeader>
          <CCardBody>
            <table border="0" class="col-md-12">
              <tbody>
                <tr>
                  <th>Name</th>
                  <th>Height</th>
                  <th>URL</th>
                  <th style="width:5%;"></th>
                </tr>
                <tr v-for="(item, index) in monitoring" v-bind:key="index">
                  <td>
                    <CInput v-model="item.name" />
                  </td>
                  <td>
                    <CInput v-model="item.height" placeholder="px" />
                  </td>
                  <td>
                    <CInput
                      v-model="item.url"
                      placeholder="http(s)://www.choshsh.com"
                    />
                  </td>
                  <td
                    class="align-top text-center"
                    v-if="Boolean(index > 0 || header.length > 1)"
                  >
                    <button
                      class="btn btn-danger"
                      @click="remove(monitoring, index)"
                    >
                      <CIcon name="cil-minus" size="sm" />
                    </button>
                  </td>
                </tr>
                <tr>
                  <td colspan="5" class="align-top text-center">
                    <button
                      class="btn btn-success mb-2"
                      @click="add(monitoring)"
                    >
                      <CIcon name="cil-plus" size="sm" />
                    </button>
                  </td>
                </tr>
              </tbody>
            </table>
            <div class="text-center row-fluid mb-1 mt-4">
              <button
                id="saveBtn"
                type="button"
                class="btn btn-primary"
                @click="save(monitoring, 'monitoring')"
              >
                저장
              </button>
            </div>
          </CCardBody>
        </CCard>
      </CCol>
    </CRow>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "Settings",
  data() {
    return {
      nav: Array,
      header: Array,
      monitoring: Array,
    };
  },
  methods: {
    getNav() {
      axios
        .get("/api/nav")
        .then((res) => {
          this.nav = res.data;
        })
        .catch((e) => console.log(e));
    },
    getHeaer() {
      axios
        .get("/api/header")
        .then((res) => {
          this.header = res.data;
        })
        .catch((e) => console.log(e));
    },
    getMonitoring() {
      axios
        .get("/api/monitoring")
        .then((res) => {
          this.monitoring = res.data;
          console.log(this.data);
        })
        .catch((e) => console.log(e));
    },
    add(array) {
      var keys = Object.keys(array[array.length - 1]);
      var newItem = new Object();

      keys.forEach((el) => {
        newItem[el] = "";
      });

      array.push(newItem);
    },
    remove(array, index) {
      array.splice(index, 1);
    },
    save(array, entity) {
      var url;
      var param;

      if (entity == "nav") {
        url = "/api/navs";
        param = array.map((el) => {
          el._name = "CSidebarNavItem";
          return el;
        });
      } else if (entity == "header") {
        url = "/api/headers";
        param = array;
      } else if (entity == "monitoring") {
        url = "/api/monitorings";
        param = array;
      }

      axios
        .post(url, param)
        .then((res) => {
          res.status == 200 ? this.onSuccess(entity) : alert("Fail");
        })
        .catch((e) => console.log(e));
    },
    onSuccess(entity) {
      alert("Success");
      this.$emit("on-save-setting", entity);
    },
  },
  created() {
    this.getNav();
    this.getHeaer();
    this.getMonitoring();
  },
};
</script>
