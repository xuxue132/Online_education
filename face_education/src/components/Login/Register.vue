<template>
  <!--  选择注册-->
  <div id="register"
       style="position: fixed;
                    overflow: hidden;
                    top: 0;
                    left: 0;
                    overflow-y: auto;
                    background-size: 100% 100%;
                    height: 100%;
                    width: 100%;
                    background-image: url('https://www.ketangpai.com/Public/Home/img/reg-bg-01.jpg');">

    <el-dialog :visible="view === 1" :show-close="false" width="480px" :modal="false"
               top="35px" destroy-on-close>
      <div>
        <div style="display: flex;padding: 30px 0 0 30px;">
          <img @click="$router.push('/login')" alt="" src="https://www.ketangpai.com/Public/Home/img/returnReg.png">
          <span style="line-height: 40px;font-size: 32px;color: #3B3D45;font-weight: 400;margin-left: 22px">用户注册</span>
        </div>
        <div style="margin-top: 25px;padding: 0 40px;height: 473px;">
          <el-form ref="UserForm" :model="UserForm" :rules="rules">
            <el-form-item prop="telephone">
              <el-input placeholder="手机" v-model="UserForm.telephone" clearable></el-input>
            </el-form-item>
            <el-form-item prop="password">
              <el-input placeholder="密码" type="password" v-model="UserForm.password" clearable show-password></el-input>
            </el-form-item>
            <el-form-item prop="checkPassword">
              <el-input placeholder="再次输入密码" type="password" v-model="UserForm.checkPassword" clearable show-password></el-input>
            </el-form-item>
            <el-form-item prop="username">
              <el-input placeholder="姓名" v-model="UserForm.username" clearable></el-input>
            </el-form-item>
            <el-form-item prop="answer">
              <div  style="display: flex;">
                <el-input placeholder="验证码" v-model="UserForm.answer" clearable></el-input>
                <div style="padding-left: 10px">
                  <ValidCode :info="{code}" @validCode="validCode()"></ValidCode>
                </div>
              </div>
            </el-form-item>
            <el-form-item class="button">
              <el-button @click="UserRegister" type="primary"
                         style="margin-top: 18px;
                                            width: 100%;
                                            height: 48px;
                                            border-radius: 3px;
                                            color: #fff;
                                            font-size: 16px;">
                注册
              </el-button>
            </el-form-item>
          </el-form>
          <div @click="$router.push('/login')"
               style="cursor: pointer;margin: 18px 0 0 288px">
            <span style="color: darkgrey;">已有账号？</span>
            <span style="color: #32BAF0;">去登陆</span>
          </div>
        </div>
      </div>
    </el-dialog>

  </div>
</template>

<script>
  import ValidCode from "@/components/Login/ValidCode";
  export default {
    name: 'Register',
    components: {ValidCode},
    data() {
      return {
        view:1,
        UserForm: {
          telephone: '',
          password: '',
          checkPassword:'',
          username: '',
          school: '',
          answer: '',
        },
        code:'',
        answerPlus:'',
        rules: {
          telephone: [
            {required: true, message: '手机不能为空'},
            {validator: (rule, value, callback) => {
                const phoneReg = /^1[(34578)][0-9]\d{8}$/
                // const mailboxReg = /^([a-zA-Z]|[0-9])(\w|\\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
                if (phoneReg.test(value) || mailboxReg.test(value)) {
                  callback();
                } else {
                  callback(new Error('手机格式不对'));
                }
              }, trigger: 'change'}
          ],
          password: [
            {required: true, message: '密码不能为空'},
            {validator: (rule, value, callback) => {
                if (value.length < 6 || value.length > 12) {
                  callback(new Error('密码太弱，请注意您的账户安全'))
                } else {
                  callback();
                }
              }, trigger: 'change'}
          ],
          checkPassword:[
            {validator: (rule, value, callback) => {
                if (value === '') {
                  callback(new Error('再次输入密码不能为空'));
                } else if (value !== this.UserForm.password ) {
                  callback(new Error('两次密码不一致'));
                } else {
                  callback();
                }
              }, trigger: 'change'}
          ],
          username: [
            {required: true, message: '姓名不能为空'}
          ],
          
          answer: [
            {required: true, message: '验证码不能为空'}
          ]
        },
      }
    },
    methods: {
      validCode(){
        this.$axios.get('/validCode').then(resp => {
          if (resp.status === 200){
            this.code = resp.data.code
            this.answerPlus = resp.data.answerPlus
          }
        }).catch(reason => {
        })
      },
      UserRegister() {
        this.$refs['UserForm'].validate((valid) => {
          if (valid) {
            this.$axios.post('/register', {
              telephone: this.UserForm.telephone,
              password: this.UserForm.password,
              username: this.UserForm.username,
              answer:this.UserForm.answer,
              answerPlus:this.answerPlus
            }).then(resp => {
              if (resp.status === 200) {
                this.$message({
                  showClose: true,
                  center: true,
                  offset: this.$store.state.tip,
                  message: resp.data.message
                })
                  this.$router.push('/login');
              }
            })
          }
        });
      },
    },
  }
</script>

<style>
  #register .el-dialog__header, .el-dialog__body {
    padding: 0 !important;
  }
  
  #register .el-tabs__nav-scroll {
    height: 35px !important;
    padding-top: 47px;
    padding-left: 41px;
    padding-bottom: 58px;
    border: none;
    font-size: 20px;
  }
  
  #register a {
    display: block;
    width: 330px;
    font-size: 16px;
    color: #fff;
    height: 64px;
    line-height: 64px;
    text-align: center;
    background: #32BAF0;
    border-radius: 4px;
    position: relative;
    left: 70px;
    bottom: 67px;
    margin:3px 0 0 20px;
    cursor: pointer;
  }
  
  #register .el-input__inner {
    margin-bottom: 12px;
    height: 48px;
    line-height: 48px;
  }
  
  #register .el-form-item{
    margin-bottom: 0;
  }
  
  #register .el-form-item__error {
    margin-top: -16px;
    left: auto;
    right: 0;
  }
  
  #register .el-input{
    font-size: 16px!important;
  }
</style>
