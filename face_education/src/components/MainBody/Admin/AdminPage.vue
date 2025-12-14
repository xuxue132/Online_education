<template>
    <div class="main_body">
        <div style="background: rgb(64, 72, 90);height: 100px;width: 100%">
            <div style="color: #FFFFFF;width: 450px;font-size: 38px;margin: auto;line-height: 90px">后台信息管理系统</div>
            
        </div>
        <div  class="left">
            <el-col :span="12" style="width: 200px">
                <el-menu
                        default-active="1"
                        class="el-menu-vertical-demo"
                        @open="handleOpen"
                        @close="handleClose"
                        background-color="#545c64"
                        text-color="#fff"
                        active-text-color="#ffd04b">
                    <el-menu-item index="1" class="items" @click="$router.push('/UserPage')">
                        <i class="el-icon-menu"></i>
                        <span slot="title" style="font-size: 20px" >用户管理</span>
                    </el-menu-item>
                    <el-menu-item index="2" class="items" @click="$router.push('/NewNoticePage')">
                        <i class="el-icon-menu"></i>
                        <span slot="title" style="font-size: 20px">新闻公告管理</span>
                    </el-menu-item>
                    <el-menu-item index="3" class="items" @click="$router.push('/CharacterPage')">
                        <i class="el-icon-document"></i>
                        <span slot="title" style="font-size: 20px">特色项目管理</span>
                    </el-menu-item>
                    <el-menu-item index="4" class="items" @click="$router.push('/Examination')">
                        <i class="el-icon-setting"></i>
                        <span slot="title" style="font-size: 20px">答题管理</span>
                    </el-menu-item>
                    <el-menu-item index="5" class="items" @click="$router.push('/prize')">
                        <i class="el-icon-setting"></i>
                        <span slot="title" style="font-size: 20px">奖品管理</span>
                    </el-menu-item>
                    <el-menu-item index="6" class="items" @click="$router.push('/Book')">
                        <i class="el-icon-setting"></i>
                        <span slot="title" style="font-size: 20px">阅读打卡管理</span>
                    </el-menu-item>
                    <el-menu-item index="7" class="items" @click="$router.push('/honor')">
                        <i class="el-icon-setting" ></i>
                        <span slot="title" style="font-size: 20px">荣誉管理</span>
                    </el-menu-item>
                    <el-menu-item index="8" class="items" @click="$router.push('/file')">
                        <i class="el-icon-setting"></i>
                        <span slot="title" style="font-size: 20px">文件中心管理</span>
                    </el-menu-item>
                </el-menu>
            </el-col>
        </div>
        <div class="right1">
            <div class="right_body" >
                <router-view />
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "AdminPage",
        data(){
            return{
            
            
            }
        },
        methods: {
            ToUserPage(){
                this.$router.push({
                    path: '/UserPage',
                    

                })
            },
            ToUserPage2(){
                this.$router.push({
                    path: '/FirstPage',
                    

                })
            },
            handleOpen(key, keyPath) {
                console.log(key, keyPath);
            },
            handleClose(key, keyPath) {
                console.log(key, keyPath);
            }
        }
    }
</script>

<style scoped>
    .main_body{
        margin: 0;
        padding: 0;
        height: 100%;
        width: 100%;
    }
    .items{
        height: 94px;
    }
    .right1{
        float: left;
        width: 88%;
        height: 100%
    }
    .left{
        float: left;
    }
    .right_body{
        height: 100%;
        width: 100%;
    }
</style>