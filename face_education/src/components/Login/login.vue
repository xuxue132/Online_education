<template>
  <!--  登录-->
  <div
    id="login"
    style="position: fixed;
                overflow: hidden;
                top: 0;
                left: 0;
                overflow-y: auto;
                background-size: 100% 100%;
                height: 100%;
                width: 100%;
                background-image: url('https://file-ehall.cqut.edu.cn/imageserver/100005/ump/images/2021/07/09/1625817649670edWyTBN.png')"
  >
    <!--        普通登录-->
    <el-dialog
      :visible="view !== '2'"
      :show-close="false"
      :modal-append-to-body="false"
      top="70px"
      width="480px"
      style="margin: 0.8rem auto 0.64rem;"
    >
      <div
        slot="title"
        style="background: #FFF;
                        width: 480px;
                        box-shadow: 0 3px 5px rgba(0,0,0,.2);"
      >
        <el-tabs :value="view">
          <el-tab-pane name="0" label="手机登录">
            <el-form
              ref="telephoneForm"
              :model="telephoneForm"
              :rules="rules"
              style="padding: 0 30px 60px;"
            >
              <el-form-item
                prop="telephone"
                style="position: relative;
                                                margin-bottom: 24px;
                                                height: 64px;
                                                line-height: 64px;
                                                border-radius: 4px;
                                                border: 1px solid rgba(226,230,237,1);
                                                box-sizing: border-box;
                                                transition: border .3s;"
              >
                <el-input
                  @keyup.enter.native="login('telephoneForm')"
                  placeholder="手机号/邮箱"
                  clearable
                  v-model="telephoneForm.telephone"
                ></el-input>
              </el-form-item>
              <el-form-item
                prop="password"
                style="position: relative;
                                        margin-bottom: 24px;
                                        height: 64px;
                                        border-radius: 4px;
                                        border: 1px solid rgba(226,230,237,1);
                                        box-sizing: border-box;
                                        transition: border .3s;"
              >
                <el-input
                  @keyup.enter.native="login('telephoneForm')"
                  placeholder="密码"
                  clearable
                  show-password
                  v-model="telephoneForm.password"
                ></el-input>
              </el-form-item>
              <el-form-item style="margin-bottom: 0;">
                <div
                  style="padding: 20px 0;
                                                font-size: 12px;
                                                height: 24px;
                                                width: 100%;
                                                line-height: 24px;
                                                display: flex;
                                                justify-content: space-between;
                                                align-items: center"
                >
                  <el-checkbox
                    class="rememberPassword"
                    type="checkBox"
                    v-model="telephoneForm.checked"
                    c
                  >
                    <span style="font-size: 12px;margin-left: -4px;"
                      >下次自动登录</span
                    >
                  </el-checkbox>
                  
                </div>
              </el-form-item>
              <el-form-item
                style="display: block;
                                                height: 50px;
                                                line-height: 50px;
                                                background: #32BAF0;
                                                text-align: center;
                                                border-radius: 3px;
                                                color: #fff;
                                                font-size: 16px;
                                                margin: 25px 0 0 0;"
              >
                <el-button
                  @click="login('telephoneForm')"
                  style="width: 100%;line-height: 50px;padding: 0;font-size: 16px"
                  type="primary"
                  >登录
                </el-button>
              </el-form-item>
              <el-form-item
                style="padding: 4px 0;
                                                text-align: right;
                                                margin: 0;"
              >
                <router-link to="/register">
                  <span class="str1" style="color:grey">还没有账号? </span>
                  <span class="str2" style="color:blue">去注册</span>
                </router-link>
              </el-form-item>
            </el-form>

          </el-tab-pane>
          
        </el-tabs>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { mapMutations } from "vuex";

export default {
  name: "Login",
  data() {
    return {
      view: "0",
      telephoneForm: {
        telephone: "",
        password: "",
        checked: false
      },
      phoneForm: {
        telephone: "",
        verification: "",
        checked: false
      },
      rules: {
        password: [{ required: true, message: "密码不能为空" }],
        telephone: [
          { required: true, message: "手机不能为空" },
        ],
        verification: [{ required: true, message: "验证码不能为空" }]
      }
    };
  },
  created() {
    localStorage.clear();
    this.$store.commit("init");
  },
  methods: {
    ...mapMutations(["setToken"]),
    login(form) {
      this.$refs[form].validate(valid => {
        if (valid) {
          this.$axios
            .post("/login", {
              telephone: this.telephoneForm.telephone,
              email: this.telephoneForm.telephone,
              password: this.telephoneForm.password
            })
            .then(resp => {
                if (resp.data.code === 400) {
                  this.$message({
                    type: 'warning',
                    message: resp.data.message
                  });
                }else {
                  this.$message({
                    message: resp.data.message,
                    type: 'success'
                  });
                  this.setToken({ Authorization: resp.data.data.token });
                  this.$router.push("/FirstPage");
                }
            })
            .catch(resp => {
              console.log(resp);
            });
        }
      });
    }
  }
};
</script>

<style>
#login .el-dialog__header,
.el-dialog__body {
  padding: 0 !important;
}

#login .el-tabs__nav-scroll {
  height: 35px !important;
  padding-top: 47px;
  padding-left: 41px;
  padding-bottom: 58px;
  border: none;
  font-size: 20px;
}

#login .el-tabs__header {
  padding: 0;
  position: relative;
  line-height: 0 !important;
  margin: 0 !important;
  border: 0 !important;
  outline: none !important;
}

#login .el-tabs__nav-wrap::after,
.el-tabs__active-bar {
  height: 0 !important;
}

#login .el-input__inner {
  height: 64px !important;
}

#login .el-form-item__content {
  height: 100% !important;
}

#login .el-tabs__item {
  margin-top: -3px;
  font-size: 16px;
  padding-right: 10px;
}

#login .el-tabs__item:hover {
  color: rgba(59, 61, 69, 1) !important;
}

#login .el-tabs__item.is-active {
  font-size: 26px;
  font-weight: 500;
  color: rgba(59, 61, 69, 1) !important;
  padding-right: 10px;
}

#login .el-form-item__error {
  left: auto;
  right: 0;
}

#verification .el-form-item__error {
  left: 90px;
}

#login .el-input {
  font-size: 16px !important;
}

.login-logo{
  height: 80px;
  width: 100%;
  position: relative;
  color: #fff;
  font-size: .32rem;
  padding-left: 0.24rem;
  margin-left: 0.24rem;
  vertical-align: middle;
}

</style>
