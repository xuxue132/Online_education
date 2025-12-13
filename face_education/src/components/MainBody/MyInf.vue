<template>
    <div class="main_body">
        <div class="head_tit">
            <a class="head_tit_button" style="margin-left: 25px;line-height: 50px">基础信息</a>
            <el-button class="head_tit_button" type="text" @click="ChangeIds()" style="color: #4285f4;float: right;margin-right: 25px" v-if="this.ids===1">编辑</el-button>
            <el-button type="primary" class="head_tit_button" @click="UpdateUser()" style="float: right;margin-right: 25px" v-if="this.ids===2">保存</el-button>
            <el-button class="head_tit_button" @click="recoverIds()" style="float: right;margin-right: 15px" v-if="ids===2">取消</el-button>
            
        </div>
        <div class="head_tab" v-if="ids===1">
            <ul  class="form-card">
                <li  class="list">
                    <label  class="label-title">姓名</label>
                    <p  class="info-txt" v-if="this.user.username"> {{this.user.username}}</p>
                    <p  class="info-txt" v-if="this.user.username===null">
                        <span  class="info">
                        <i  class="el-icon-warning font_color_warning">
                        </i> 未完善 </span>
                    </p>
                </li>
                <li  class="list">
                    <label  class="label-title">电话</label>
                    <p  class="info-txt" v-if="this.user.telephone!==null"> {{this.user.telephone}}</p>
                    <p  class="info-txt" v-if="this.user.telephone===null">
                        <span  class="info">
                        <i  class="el-icon-warning font_color_warning">
                        </i> 未完善 </span>
                    </p>
                </li>
                <li  class="list">
                    <label  class="label-title">性别</label>
                    <p  class="info-txt" v-if="this.user.sex!==null"> {{this.user.sex}}</p>
                    <p  class="info-txt" v-if="this.user.sex===null">
                        <span  class="info">
                        <i  class="el-icon-warning font_color_warning">
                        </i> 未完善 </span>
                    </p>
                </li>
                <li  class="list">
                    <label  class="label-title">生日</label>
                    <p  class="info-txt" v-if="this.user.birthday!==null"> {{this.user.birthday}}</p>
                    <p  class="info-txt" v-if="this.user.birthday===null">
                        <span  class="info">
                        <i  class="el-icon-warning font_color_warning">
                        </i> 未完善 </span>
                    </p>
                </li>

                <li  class="list">
                    <label  class="label-title">社区</label>
                    <p  class="info-txt" v-if="this.user.area!==null"> {{this.user.area}}</p>
                    <p  class="info-txt" v-if="this.user.area===null">
                        <span  class="info">
                        <i  class="el-icon-warning font_color_warning">
                        </i> 未完善 </span>
                    </p>
                </li>
                <li  class="list">
                    <label  class="label-title">学历</label>
                    <p  class="info-txt" v-if="this.user.background!==null"> {{this.user.background}}</p>
                    <p  class="info-txt" v-if="this.user.background===null">
                        <span  class="info">
                        <i  class="el-icon-warning font_color_warning">
                        </i> 未完善 </span>
                    </p>
                </li>
                <li  class="list">
                    <label  class="label-title">工作</label>
                    <p  class="info-txt" v-if="this.user.job!==null"> {{this.user.job}}</p>
                    <p  class="info-txt" v-if="this.user.job===null">
                        <span  class="info">
                        <i  class="el-icon-warning font_color_warning">
                        </i> 未完善 </span>
                    </p>
                </li>
                <li  class="list">
                    <label  class="label-title">爱好</label>
                    <p  class="info-txt" v-if="this.user.hobby!==null"> {{this.user.hobby}}</p>
                    <p  class="info-txt" v-if="this.user.hobby===null">
                        <span  class="info">
                        <i  class="el-icon-warning font_color_warning">
                        </i> 未完善 </span>
                    </p>
                </li>
            </ul>
        </div>
        
        <div class="head_tab" v-if="ids===2">
            <ul  class="form-card">
                <li  >
                    <label  class="label-title">姓名</label>
                    <el-input  class="list" v-model="user.username" maxlength="10" show-word-limit></el-input>
                </li>
                <li  >
                    <label  class="label-title">电话</label>
                    <el-input  class="list" v-model="user.telephone" :disabled="true"></el-input>
                </li>
                <li >
                    <label  class="label-title">性别</label>
                    <el-select  class="list" v-model="user.sex" placeholder="请选择">
                        <el-option
                                v-for="item in options"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                        </el-option>
                    </el-select>
                </li>
                <li >
                    <label  class="label-title">生日</label>
                    <el-col :span="11" class="list">
                        <el-date-picker type="date" placeholder="选择日期" v-model="user.birthday" style="width: 100%;"></el-date-picker>
                    </el-col>
                </li>
                
                <li >
                    <label  class="label-title">社区</label>
                    <el-select class="list" v-model="user.area" placeholder="请选择社区">
                        <el-option
                                v-for="item in options2"
                                :key="item.label"
                                :label="item.label"
                                :value="item.label">
                        </el-option>
                    </el-select>
                </li>
                <li >
                    <label  class="label-title">学历</label>
                    <el-select  class="list" v-model="user.background" clearable placeholder="请选择">
                        <el-option
                                v-for="item in options3"
                                :key="item.label"
                                :label="item.label"
                                :value="item.label">
                        </el-option>
                    </el-select>
                </li>
                <li >
                    <label  class="label-title">工作</label>
                    <el-input class="list" v-model="user.job" maxlength="30" show-word-limit></el-input>
                </li>
                <li >
                    <label  class="label-title">爱好</label>
                    <el-input type="textarea" class="list" v-model="user.hobby" maxlength="40" show-word-limit></el-input>
                </li>
              
            </ul>
        </div>
    
        <div class="head_tit">
            <a class="head_tit_button" style="margin-left: 25px;line-height: 50px">账号设置</a>
        </div>
    
        <div class="head_tab" >
            <ul  class="form-card">
                <li  class="list">
                    <label  class="label-title">角色</label>
                    <p  class="info-txt" v-if="this.user.username"> {{this.user.roleName}}</p>
                </li>
                <li  class="list">
                    <label  class="label-title">电话</label>
                    <p  class="info-txt" v-if="this.user.telephone!==null"> {{this.user.telephone}}</p>
                    <el-button class="head_tit_button" type="text" @click="dialogFormVisible = true" style="font-size: 14px;color: #4285f4;float: right;margin-right: 35px">更换手机号</el-button>
                    <el-button class="head_tit_button" type="text" @click="dialogFormVisible1 = true" style="font-size: 14px;color: #4285f4;float: right;margin-right: 35px">修改密码</el-button>
                </li>
                
            </ul>
        </div>
    
    
        <div class="head_tit">
            <a class="head_tit_button" style="margin-left: 25px;line-height: 50px">第三方账号绑定</a>
        </div>
        <div class="head_tab">
            <ul  class="form-card">
                <li  class="list">
                    <label  class="label-title">邮箱绑定</label>
                    <p  class="info-txt" v-if="this.user.email"> {{this.user.email}}</p>
                    <p  class="info-txt" v-if="this.user.email===null">
                            <span  class="info">
                            <i  class="el-icon-warning font_color_warning">
                            </i> 未完善 </span>
                    </p>
                    <el-button v-if="this.user.email===null || this.user.email==='' " class="head_tit_button" type="text" @click="dialogFormVisible2 = true" style="font-size: 14px;color: #4285f4;float: right;margin-right: 35px">立即绑定</el-button>
                    <el-button v-if="this.user.email" class="head_tit_button" type="text" @click="dialogFormVisible2 = true" style="font-size: 14px;color: #4285f4;float: right;margin-right: 35px">更换绑定</el-button>
                </li>
            </ul>
        </div>
        
        <el-dialog :append-to-body="true" center title="修改手机号" @close="close1" :visible.sync="dialogFormVisible">
            <el-form :model="form" :rules="rules" ref="form">
                <el-form-item label="原手机号" :label-width="formLabelWidth" >
                    <p>{{user.telephone}}</p>
                </el-form-item>
                <el-form-item label="新手机号" :label-width="formLabelWidth" prop="NewTelephone">
                    <el-input v-model="form.NewTelephone" autocomplete="off"></el-input>
                </el-form-item>
    
                <el-form-item label="密码" :label-width="formLabelWidth" prop="password">
                    <el-input type="password" v-model="form.password" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div  slot="footer" class="dialog-footer">
                <el-button   @click="close1();dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="ChangePhone()">确 定</el-button>
            </div>
        </el-dialog>
        <el-dialog center title="修改邮箱" :visible.sync="dialogFormVisible2" @close="close2" :append-to-body="true">
            <el-form :model="form2" :rules="rules1" ref="form2">
                <el-form-item v-if="this.user.email" label="原邮箱号" :label-width="formLabelWidth">
                    <p>{{user.email}}</p>
                </el-form-item>
                <el-form-item label="新邮箱号" :label-width="formLabelWidth" prop="NewEmail">
                    <el-input v-model="form2.NewEmail" autocomplete="off"></el-input>
                </el-form-item>
    
                <el-form-item label="密码" :label-width="formLabelWidth" prop="password1">
                    <el-input type="password" v-model="form2.password1" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div  slot="footer" class="dialog-footer">
                <el-button   @click="close2();dialogFormVisible2 = false">取 消</el-button>
                <el-button type="primary" @click="ChangeEmail()">确 定</el-button>
            </div>
        </el-dialog>
        <el-dialog :append-to-body="true" center title="修改密码" @close="close3" :visible.sync="dialogFormVisible1">
            <el-form :model="form3" :rules="rules2" ref="form3">
                <el-form-item label="原密码" :label-width="formLabelWidth" prop="password">
                    <el-input type="password" v-model="form3.password" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="新密码" :label-width="formLabelWidth" prop="NewPassword">
                    <el-input type="password" v-model="form3.NewPassword" autocomplete="off"></el-input>
                </el-form-item>
            
                <el-form-item label="确认密码" :label-width="formLabelWidth" prop="NewPassword1">
                    <el-input type="password" v-model="form3.NewPassword1" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div  slot="footer" class="dialog-footer">
                <el-button   @click="close3();dialogFormVisible1 = false">取 消</el-button>
                <el-button type="primary" @click="ChangePassword()">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "MyInf",
        data() {
            return {
                ids: 1,
                dialogFormVisible:false,
                dialogFormVisible1:false,
                dialogFormVisible2:false,
                user: {
                    username: "",
                    telephone: "",
                    sex: "",
                    birthday: "",
                    email: "",
                    area: "",
                    background: "",
                    job: "",
                    hobby: "",
                    integral: 0,
                    roleName:'',
                },
                form3:{
                    password:'',
                    NewPassword:'',
                    NewPassword1:''
                },
                options: [
                    {
                    value: '男',
                    label: '男'
                }, {
                    value: '女',
                    label: '女'
                }],
                options2: [
                    {
                    value: '安富街道',
                    label: '安富街道'
                }, {
                    value: '昌源街道',
                    label: '昌源街道'
                }, {
                    value: '昌州街道',
                    label: '昌州街道'
                }, {
                    value: '峰高街道',
                    label: '峰高街道'
                }, {
                    value: '古昌街道',
                    label: '古昌街道'
                }, {
                    value: '龙馨街道',
                    label: '龙馨街道'
                }, {
                    value: '观胜街',
                    label: '观胜街'
                }, {
                    value: '广顺街道',
                    label: '广顺街道'
                }, {
                    value: '何包街道',
                    label: '何包街道'
                }, {
                    value: '龙泉街道',
                    label: '龙泉街道'
                }
                
                ],
                options3: [
                    {
                    value: '专科',
                    label: '专科'
                }, {
                    value: '本科',
                    label: '本科'
                }, {
                    value: '研究生',
                    label: '研究生'
                }, {
                    value: '高中',
                    label: '高中'
                }, {
                    value: '初中',
                    label: '初中'
                }, {
                    value: '小学',
                    label: '小学'
                }, {
                    value: '其他',
                    label: '其他'
                }
                ],
                form: {
                    NewTelephone: '',
                    password:'',
                },
                form2: {
                    NewEmail: '',
                    password1:'',
                },
                formLabelWidth: '120px',
                rules: {
                    NewTelephone: [
                        { required: true, message: '不能为空'},
                    ],

                    password: [
                        { required: true, message: '不能为空'},
                    ],
                },
                rules1: {
                    NewEmail: [
                        { required: true, message: '不能为空'},
                    ],

                    password1: [
                        { required: true, message: '不能为空'},
                    ],
                },
                rules2: {
                    password: [
                        { required: true, message: '不能为空'},
                    ],

                    NewPassword: [
                        { required: true, message: '不能为空'},
                    ],
                    NewPassword1: [
                        { required: true, message: '不能为空'},
                    ],
                }
            };
        },
        methods: {
            close1(){
                this.$refs["form"].resetFields()
            },
            close2(){
                this.$refs["form2"].resetFields()
            },
            close3(){
                this.$refs["form3"].resetFields()
            },
            ChangeIds() {
                this.ids=2;
            },
            recoverIds() {
                this.ids=1;
            },
            ChangePassword(){
                this.$refs['form3'].validate((valid) => {
                    if (valid) {
                        this.$axios.post('public/ChangePassword', {
                            ...this.form3,
                            telephone: this.user.telephone
                        }, {
                            headers: {'Authorization': this.$store.state.Authorization}
                        }).then(resp => {
                            this.$message({
                                message: resp.data.message,
                                type: 'success'
                            });
                            if (resp.data.code === 200) {
                                this.dialogFormVisible1 = false
                            }
                        }).catch(resp => {
                        })
                    }
                })
            },
            //格式化时间
            formatDate(date) {
                if (typeof date === 'string'){
                    return date
                }

                let year = date.getFullYear();
                let month = date.getMonth()+1;
                let day = date.getDate();
                if (month < 10) {
                    month = "0" + month;
                }
                if (day < 10) {
                    day = "0" + day;
                }
                return year + "/" + month + "/" + day;
            },
            ChangePhone() {
                this.$refs['form'].validate((valid) => {
                    if (valid) {
                        this.$axios.post('public/ChangePhone', {
                            ...this.form,
                            telephone: this.user.telephone
                        }, {
                            headers: {'Authorization': this.$store.state.Authorization}
                        }).then(resp => {
                            this.$message({
                                message: resp.data.message,
                                type: 'success'
                            });
                            if (resp.data.code === 200) {
                                this.dialogFormVisible = false
                                this.$router.push("/login");
                            }
                        }).catch(resp => {
                        })
                    }
                })
            },
            ChangeEmail() {
                this.$refs['form2'].validate((valid) => {
                    if (valid) {
                        this.$axios.post('public/ChangeEmail', {
                            ...this.form2,
                            ...this.user
                        }, {
                            headers: {'Authorization': this.$store.state.Authorization}
                        }).then(resp => {
                            this.$message({
                                message: resp.data.message,
                                type: 'success'
                            });
                            if (resp.data.code === 200) {

                                this.dialogFormVisible2 = false
                                this.$router.push("/login");
                            }
                        }).catch(resp => {
                        })
                    }
                })
               
                
            },
            getUser() {
                this.$axios.post('public/MyInformation', {
                    token: this.$store.state.Authorization
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200) {
                        this.user = resp.data.data
                        this.user.birthday =  this.formatDate(new Date ( this.user.birthday ))
                    }
                }).catch(resp => {
                })
            },
            
            UpdateUser(){
                this.$axios.post('public/UpdateMyInformation', {
                    ...this.user,
                    birthday: this.formatDate(new Date (this.user.birthday))
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200) {
                        this.ids=1
                        this.$router.go(0);
                        this.user.birthday =  this.formatDate(new Date ( this.user.birthday ))
                    }
                }).catch(resp => {
                })
                
            },
        },
        created(){
            
            this.getUser();
        }
    }
</script>

<style scoped>
    .main_body{
        width: 60%;
        background: #fffdf6;
    }
    .head_tit{
        height: 50px;
       
    }
    .head_tab{
        width: 95%;
        margin:10px auto;
    }
    .head_tit_button{
        font-size: 15px;
        height: 30px;
        width: 50px;
        /*line-height:50px !important;*/
        margin-top: 10px;
        padding: 0 0 !important;
    }
    .form-card {
        background: #fff;
        border: 1px solid #dadce0;
        border-radius: 4px;
        box-shadow: inset 4px 0 0 0 #4285f4;
    }
    .form-card li {
        width: 100%;
        display: flex;
        align-items: center;
        padding: 16px 0;
        border-top: 1px solid #dadce0;
        margin-left: 4px;
    }
    .list{
        width: 300px;
        margin-left: 34px;
    }
    .label-title {
        padding: 0 24px;

        text-align: left;
    }
    .info-txt {
        flex: 1;
        color: #5f6368;
        padding-right: 24px;
        margin-left: 40px;
    }
    .info {
        color: #74787c;
    }
    .info i {
        margin-right: 8px;
        font-size: 16px;
    }
    .font_color_warning {
        color: #ff941f;
    }
</style>