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
                    v-model="user.userId"
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
                    v-model="user.userPw"
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
                <h2>CHOSHSH</h2>
                <p>
                  안녕하세요, 조상현입니다.
                </p>
              </CCardBody>
            </CCard>
          </CCardGroup>
        </CCol>
      </CRow>
    </CContainer>
  </div>
</template>

<script>
import * as axios from "@/assets/js/axios";
import urls from "@/assets/js/urls";

export default {
  name: "Login",
  data() {
    return {
      user: {
        userId: "",
        userPw: "",
      },
    };
  },
  methods: {
    async login() {
      let user = await axios.post(urls.auth.login, this.user);
      if (user.id) {
        sessionStorage.setItem("id", user.id);
        sessionStorage.setItem("userId", user.userId);
        sessionStorage.setItem("userName", user.userName);
        sessionStorage.setItem("role", user.role);
        this.$router.push("/");
      } else {
        alert("로그인 실패");
      }
    },
  },
};
</script>
