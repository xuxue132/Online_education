<template>
    <div class="hole">
        <div class="bg1"></div>
        <div class="gyl">
            在线教育系统
            <div class="gy2">社区在线教育信息管理平台</div>
        </div>
        <div class="bg">
            <div class="wel">管理员登录</div>
            <div class="user">
                <div class="users" style="">手机号</div>
                <input type="text" name="管理员" value="" v-model="telephoneForm.telephone" />
            </div>
            <div class="password">
                <div class="users">密&nbsp;&nbsp;&nbsp;&nbsp;码</div>
                <input class="" type="password" name="密码" value="" v-model="telephoneForm.password" />
            </div>
            <div class="rem">
                <input type="checkbox" name="" id="" value=""/>
                <div id="reb">
                    记住密码
                </div>
            </div>
            <div class="fg">
                <div style="font-size: 11px;margin-top: 11px;">
                    <a style="font-size: 11px;">忘记密码？</a>
                </div>
            </div>
            <input class="btn" type="submit" name="登录" value="登录" @click="login()"/>
        </div>
    </div>
</template>

<script>
    import { mapMutations } from "vuex";
    export default {
        name: "AdminLogin",
        data() {
            return {
                fixStyle: '',
                telephoneForm: {
                    telephone: "",
                    password: "",
                    checked: false
                },
            }
        },
        methods: {
            ...mapMutations(["setToken"]),
         
            login() {
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
                            this.$router.push("/AdminPage");
                        }
                    })
                    .catch(resp => {
                        console.log(resp);
                    });
                
                
            }
        },
        
    }
</script>

<style>
   
    .ivu-spin-fullscreen .ivu-spin-fullscreen-wrapper {
        background-color: rgba(0, 0, 0, 0.37);
    }

    * {
        font-family: "微软雅黑";
        font-size: 16px;
        border: 0;
        padding: 0;
        margin: 0;
        color: #666;
        box-sizing: border-box;
        -moz-box-sizing: border-box;
        -webkit-box-sizing: border-box;
    }


    html body{
        width: 100%;
        height: 100%;
    }
    .hole {
        height: 100%;
        width: 100%;
        background-image: url("https://file-ehall.cqut.edu.cn/imageserver/100005/ump/images/2021/07/09/1625817649670edWyTBN.png");
        /*background-size: cover;*/
        overflow: hidden;
        top: 0;
        left: 0;
        overflow-y: auto;
        background-size: 100% 100%;
      
    }

    .bg1 {
        width: 100%;
        height: 237px;
        background: #656571;
        opacity: 0.6;
        position: absolute;
        left: 0;
        top: 0px;
        right: 0px;
        bottom: 0px;
        margin: auto;
    }

    .gyl {
        width: 510px;
        height: 237px;
        color: #FFFFFF;
        font-size: 62px;
        position: absolute;
        left: 15%;
        top: 11%;
        text-align: center;
        bottom: 0px;
        margin: auto;
    }

    .gy2 {
        color: #fff;
        margin-left: 6px;
        font-size: 21px;
        text-align: center;
        margin-top: 32px;
    }
    .bg {
        width: 375px;
        height: 337px;
        background: #fff;
        position: relative;
        position: absolute;
        left: 56%;
        top: 10px;
        right: 0px;
        bottom: 0px;
        margin: auto;
        z-index: 1;
        box-shadow: 0px 0px 50px rgba(0, 0, 0, 0.4);
        border-radius: 5px;
    }

    .password {
        position: absolute;
        top: 9.5rem;
        right: 2.5rem;
        display: flex;
    }

    .btn {
        position: absolute;
        top: 16rem;
        right: 2.5rem;
        border: none;
        color: #fff;
        width: 18.825rem;
        text-align: center;
        background-color: #347ebc;
        text-indent: 0rem;
    }

    .img {
        width: 100%;
        position: absolute;
        left: 0;
        right: 0;
        margin: 0 auto;
        z-index: -1;
    }

    .wel {
        width: 375px;
        height: 35px;
        color: #347ebc;
        font-size: 1.5rem;
        position: absolute;
        top: 2rem;
        text-align: center;
    }

    input {
        border-bottom: 0.0625rem solid #347ebc;
        height: 2.5625rem;
        width: 15.8125rem;
        text-indent: 3.125rem;
        outline: none;
    }

    .user {
        position: absolute;
        top: 6rem;
        right: 2.5rem;
        display: flex;
    
    }

    .rem {
        position: absolute;
        top: 12.5rem;
        right: 17.2rem;
        border: none;
        color: #323333;
    }

    .rem input {
        width: 0.8125rem;
    }

    .fg {
        position: absolute;
        top: 12.5rem;
        right: 2.5rem;
        border: none;
        color: #323333;
    }

    .users {
        border-bottom: 1px solid #347ebc;
        height: 41px;
        line-height: 41px;
    }

    #reb {
        margin-top: -33px;
        margin-left: 20px;
        font-size: 11px;
    }

    @media (min-width: 1201px) and (max-width: 1500px) {
        .gyl {
            width: 420px;
            font-size: 60px;
            top: 16%;
        }
    }

    @media (min-width: 800px) and (max-width: 1200px) {
        .bg {
            width: 355px;
            height: 419px;
        }
        .gyl {
            font-size: 50px;
        }
        .wel {
            width: 355px;
        }
        .user {
            top: 6rem;
            right: 3.5rem;
        }
        .password {
            top: 10rem;
            right: 3.5rem;
        }
        input {
        
            width: 12rem;
        }
        .rem {
            top: 11rem;
            right: 14.3rem;
        }
        .fg {
            top: 11rem;
            right: 3.5rem;
        }
        .btn {
            top: 14rem;
            width: 15rem;
        }
    }
</style>


