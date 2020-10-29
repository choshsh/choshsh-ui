<template>
  <div class="c-app flex-row align-items-center">
    <CContainer>
      <CRow class="justify-content-center">
        <CCol md="8">
          <CCardGroup>
            <CCard class="p-4">
              <CCardBody>
                <CForm @keyup.enter="login()">
                  <h1>Login</h1>
                  <p class="text-muted">Sign In to your account</p>
                  <CInput
                    placeholder="ID"
                    autocomplete="username email"
                    v-model="userId"
                    autofocus
                  >
                    <template #prepend-content>
                      <CIcon name="cil-user" />
                    </template>
                  </CInput>
                  <CInput
                    placeholder="Password"
                    type="password"
                    autocomplete="curent-password"
                    v-model="userPw"
                  >
                    <template #prepend-content>
                      <CIcon name="cil-lock-locked" />
                    </template>
                  </CInput>
                  <CRow>
                    <CCol col="6" class="text-left">
                      <CButton @click="login()" color="primary" class="px-4"
                        >Login</CButton
                      >
                    </CCol>
                    <!--                     <CCol col="6" class="text-right">
                      <CButton color="link" class="px-0">Forgot password?</CButton>
                      <CButton color="link" class="d-lg-none">Register now!</CButton>
                    </CCol>-->
                  </CRow>
                </CForm>
              </CCardBody>
            </CCard>
            <CCard
              color="primary"
              text-color="white"
              class="text-center py-5 d-md-down-none"
              body-wrapper
            >
              <CCardBody>
                <h2>ITSM-View</h2>
                <p>
                  정보팀 SE가 관리하는 리소스들의 현황, 데이터, 차트 등을 확인할
                  수 있습니다.
                </p>
                <!--            <CButton
                  color="light"
                  variant="outline"
                  size="lg"
                >
                  Register Now!
                </CButton>-->
              </CCardBody>
            </CCard>
          </CCardGroup>
        </CCol>
      </CRow>
    </CContainer>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "Login",
  data() {
    return {
      userId: "",
      userPw: "",
    };
  },
  methods: {
    login() {
      axios
        .post("/api/login", {
          userId: this.userId,
          userPw: this.userPw,
        })
        .then((res) => {
          if (res.data.check > 0) {
            sessionStorage.setItem("userId", res.data.getUser.userId);
            sessionStorage.setItem("userName", res.data.getUser.userName);
            sessionStorage.setItem("userRoleCd", res.data.getUser.userRoleCd);
            sessionStorage.setItem("userSeq", res.data.getUser.userSeq);
            this.$router.push("/");
          } else alert("로그인 실패입니다.");
        })
        .catch((e) => console.log(e));
    },
  },
};
</script>
