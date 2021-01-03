<template>
  <CRow>
    <CCol col="12">
      <CCard>
        <CCardHeader>
          <strong>SE 월간회의 </strong>
          <small>Form</small>
          <div class="small text-muted mt-1" v-if="Boolean(userName)">
            등록자: {{ userName }}ㅤㅤㅤ등록 일시: {{ regDt }}
          </div>
          <div class="small text-muted" v-if="Boolean(modUserName)">
            수정자: {{ modUserName }}ㅤㅤㅤ수정 일시: {{ modDt }}
          </div>
        </CCardHeader>
        <CCardBody>
          <form id="boardForm">
            <CRow>
              <CCol sm="12">
                <CInput label="제목" v-model="title" placeholder="Enter 제목" />
              </CCol>
            </CRow>
            <CRow>
              <CCol sm="12">
                <CInput
                  label="키워드"
                  v-model="keyword"
                  placeholder="Enter 키워드"
                />
              </CCol>
            </CRow>
            <CRow>
              <CCol sm="12" :key="ckKey">
                <CKeditor ref="ClassicEditor" :dataParam="this.content" />
              </CCol>
            </CRow>
            <div class="text-center row-fluid mb-1 mt-3">
              <button
                id="saveBtn"
                type="button"
                class="btn btn-primary"
                @click="save()"
              >
                저장
              </button>
              <button
                id="cancelBtn"
                type="button"
                class="btn btn-secondary ml-4"
                @click="cancel()"
              >
                취소
              </button>
            </div>
          </form>
        </CCardBody>
      </CCard>
    </CCol>
  </CRow>
</template>

<script>
import axios from "axios";
import CKeditor from "../base/CKEditor.vue";

export default {
  name: "SeMonthConfForm",
  components: {
    CKeditor,
  },
  data() {
    return {
      bdSeqParam: this.$route.params.bdSeq,
      bdCdParam: this.$route.params.bdCd,
      bdSeq: 0,
      title: "",
      content: "",
      keyword: "",
      userSeq: sessionStorage.getItem("userSeq"),
      regDt: "",
      modDt: "",
      userName: "",
      modUserName: "",
      bdNm: "",
      validateArr: [],
      ckKey: 0,
    };
  },
  methods: {
    save() {
      this.content = this.$refs.ClassicEditor.getData();
      const form = {
        bdSeq: this.bdSeq,
        title: this.title,
        content: this.content,
        keyword: this.keyword,
        userSeq: this.userSeq,
        bdCd: this.bdCdParam,
      };
      if (this.validateCheck(form)) {
        axios
          .post("/api/boardForm/boardSave", form)
          .then((res) => {
            if (res.data > 0) {
              alert("저장 성공");
              this.$router.push("seMonthConf");
            } else {
              alert("저장 실패");
            }
          })
          .catch((e) => console.log(e));
      } else {
        alert("필수 값을 입력해주세요.");
      }
    },
    validateCheck(form) {
      let check = true;
      this.validateArr.forEach((s) => {
        if (!form[s]) {
          check = false;
        }
      });
      return check;
    },
    setData() {
      if (this.bdCdParam) {
        axios
          .post("/api/board/getInfo", {
            bdSeq: this.bdSeqParam,
            bdCd: this.bdCdParam,
          })
          .then((res) => {
            this.bdSeq = res.data.bdSeq;
            this.title = res.data.title;
            this.content = res.data.content;
            this.keyword = res.data.keyword;
            this.regDt = res.data.regDt;
            this.modDt = res.data.modDt;
            this.userName = res.data.userName;
            this.modUserName = res.data.modUserName;
            this.bdCd = res.data.bdCd;
            this.bdNm = res.data.bdNm;
            this.ckKey++;
          })
          .catch((e) => console.log(e));
      }
    },
    cancel() {
      this.$router.push("seMonthConf");
    },
  }, 
  created: function () {
    this.setData();
  },
};
</script>