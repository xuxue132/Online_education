<template>
    <div class="main_body">
        <div id="NoticeManagement-top">
            <el-space id="NoticeManagement-top-left">
                <span class="icons">电话</span>
                <el-input size="small" v-model="searchForm.telephone" placeholder="请输入用户电话"></el-input>
                <span class="icons">姓名</span>
                <el-input size="small" v-model="searchForm.userName" placeholder="请输入用户姓名"></el-input>
                <el-button style="margin-left: 10px" icon="el-icon-search" circle  size="small" @click="handle(1)"></el-button>
            </el-space>
        </div>
        
        <div class="tab">
            <el-table
                    :data="user"
                    style="width: 100%">
                <el-table-column type="expand">
                    <template slot-scope="props">
                        <el-form label-position="left" inline class="demo-table-expand">
                            <el-form-item label="用户名字">
                                <span>{{ props.row.username }}</span>
                            </el-form-item>
                            <el-form-item label="用户电话">
                                <span>{{ props.row.telephone }}</span>
                            </el-form-item>
                            <el-form-item label="用户性别">
                                <span>{{ props.row.sex }}</span>
                            </el-form-item>
                            <el-form-item label="用户生日">
                                <span>{{ props.row.birthday }}</span>
                            </el-form-item>
                            <el-form-item label="用户邮箱">
                                <span>{{ props.row.email }}</span>
                            </el-form-item>
                            <el-form-item label="用户社区">
                                <span>{{ props.row.area }}</span>
                            </el-form-item>
                            <el-form-item label="用户学历">
                                <span>{{ props.row.background }}</span>
                            </el-form-item>
                            <el-form-item label="用户工作">
                                <span>{{ props.row.job }}</span>
                            </el-form-item>
                            <el-form-item label="用户爱好">
                                <span>{{ props.row.hobby }}</span>
                            </el-form-item>
                            <el-form-item label="用户积分">
                                <span>{{ props.row.integral }}</span>
                            </el-form-item>
                            <el-form-item label="创建时间">
                                <span>{{ props.row.createTime }}</span>
                            </el-form-item>
                            <el-form-item label="用户角色">
                                <span>{{ props.row.roleName }}</span>
                            </el-form-item>
                        </el-form>
                    </template>
                </el-table-column>
                <el-table-column
                        label="日期"
                        width="180">
                    <template slot-scope="scope">
                        <i class="el-icon-time"></i>
                        <span style="margin-left: 10px"  class="iocx">{{ scope.row.createTime }}</span>
                    </template>
                </el-table-column>
                <el-table-column
                        label="电话"
                        width="180">
                    <template slot-scope="scope">
                        <span style="margin-left: 10px"  class="iocx">{{ scope.row.telephone }}</span>
                    </template>
                </el-table-column>
                <el-table-column
                        label="邮箱"
                        width="180">
                    <template slot-scope="scope"  class="iocx">
                        <span style="margin-left: 10px">{{ scope.row.email }}</span>
                    </template>
                </el-table-column>
                <el-table-column
                        label="姓名"
                        width="180"  class="iocx">
                    <template slot-scope="scope">
                        <el-popover trigger="hover" placement="top">
                            <p>姓名: {{ scope.row.username }}</p>
                            <p>住址: {{ scope.row.area }}</p>
                            <div slot="reference" class="name-wrapper">
                                <el-tag size="medium">{{ scope.row.username }}</el-tag>
                            </div>
                        </el-popover>
                    </template>
                </el-table-column>
                <el-table-column
                        label="积分"
                        width="180">
                    <template slot-scope="scope">
                        <span style="margin-left: 10px">{{ scope.row.integral }}</span>
                    </template>
                </el-table-column>
                <el-table-column
                        label="角色"
                        width="180">
                    <template slot-scope="scope">
                        <span style="margin-left: 10px">{{ scope.row.roleName }}</span>
                    </template>
                </el-table-column>
                <el-table-column label="操作">
                    
                    <template slot-scope="scope">
                        <el-button
                                size="mini"
                                @click="Edits(scope.row.telephone)">编辑</el-button>
                        <el-button
                                size="mini"
                                type="danger"
                                v-if="scope.row.deleted === 0"
                                @click="Deleted(scope.row.telephone)">封禁</el-button>
                        <el-button
                                size="mini"
                                type="primary"
                                v-if="scope.row.deleted === 1"
                                @click="UnDeleted(scope.row.telephone)">解封</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <div>
            <el-pagination
                    style="margin-left: 40%"
                    @current-change="handleCurrentChange"
                    background
                    layout="prev, pager, next"
                    :total="this.number">
            </el-pagination>
        </div>
        <el-dialog :append-to-body="true" center @close="closed" title="修改密码" :visible.sync="dialogFormVisible">
            <el-form :model="form" :rules="rules" ref="form">

                <el-form-item label="新密码" :label-width="formLabelWidth"  prop="NewPassword">
                    <el-input type="password" v-model="form.NewPassword" clearable></el-input>
                </el-form-item>
            
                <el-form-item label="确认密码" :label-width="formLabelWidth" prop="NewPassword1">
                    <el-input type="password" v-model="form.NewPassword1"  clearable></el-input>
                </el-form-item>
            </el-form>
            <div  slot="footer" class="dialog-footer">
                <el-button   @click="Closes()">取 消</el-button>
                <el-button type="primary" @click="ChangePassword()">确 定</el-button>
            </div>
        </el-dialog>

    </div>
</template>

<script>
    export default {
        name: "UserPage",
        data(){
            return{
                telephone:'',
                searchForm:{
                    telephone:'',
                    userName:''
                },
                si:0,
                user:[],
                number:0,
                page:1,
                page2:1,
                formLabelWidth: '120px',
                dialogFormVisible:false,
                form:{
                    NewPassword:'',
                    NewPassword1:''
                },
                rules: {

                    NewPassword1:[
                        { required: true, message: '不能为空'},
                        {validator: (rule, value, callback) => {
                                if (value === '' || value===null) {
                                    callback(new Error('再次输入密码不能为空'));
                                } else if (value !== this.form.NewPassword) {
                                    callback(new Error('两次密码不一致'));
                                } else {
                                    callback();
                                }
                            }, trigger: 'change'}
                    ],
                    NewPassword: [
                        { required: true, message: '不能为空'},
                    ],
                  
                }
            }
        },
        created(){
            this.UserNumber()
            
        },
        methods:{
            UserNumber(){
                this.$axios.post('admin/UserSum', {
                    types: 1
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200) {
                        this.number = resp.data.data
                        this.inits(this.page)
                    }
                }).catch(resp => {
                })
            },
            handleCurrentChange(i){

                if(this.si === 1){
                    // this.page2 = i
                    this.handle(i)
                }else {
                    this.inits(i)
                }
            },
            handle(page){
                this.si = 1
                this.user=[]
                if(this.searchForm.userName === '' && this.searchForm.telephone===''){
                    this.$router.go(0)
                }
                this.$axios.post('admin/selectUser', {
                    ...this.searchForm,
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    this.number = resp.data.data.length
                    if((page-1)*10+10>resp.data.data.length){
                        for (var i=(page-1)*10;i<resp.data.data.length;i++) {
                            this.user.push(resp.data.data[i])
                        }
                    }else {
                        for (var i=(page-1)*10;i<(page-1)*10+10;i++) {
                            this.user.push(resp.data.data[i])
                        }
                    }
                    for (var i=0;i<this.user.length;i++) {
                        this.user[i].createTime = this.formatDate(new Date(this.user[i].createTime))
                    }
                }).catch(resp => {
                })
                
            },
            closed() {
                this.$refs["form"].resetFields();
            },
            Closes(){
                this.$refs["form"].resetFields();
                this.dialogFormVisible = false
            },
            Edits(telephone){
                this.dialogFormVisible = true
                this.telephone = telephone;
            },
            ChangePassword(){
                this.$refs['form'].validate((valid) => {
                    if (valid) {
                        this.$axios.post('admin/ChangePasswordPlus', {
                            ...this.form,
                            telephone: this.telephone
                        }, {
                            headers: {'Authorization': this.$store.state.Authorization}
                        }).then(resp => {
                            if (resp.status === 200) {
                                this.$message({
                                    message: '修改成功',
                                    type: 'success'
                                });
                            }
                        }).catch(resp => {
                        })
                        this.dialogFormVisible = false
                    }
                })
            },
            inits(page){
                this.$axios.post('admin/AllInformation', {
                    NowPage: page
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200) {
                        this.user = resp.data.data
                        for (var i=0;i<this.user.length;i++) {
                            this.user[i].createTime = this.formatDate(new Date(this.user[i].createTime))
                        }
                    }
                }).catch(resp => {
                })
            },
           
            Deleted(telephone){
                this.$confirm('此操作删除信息, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$axios.post('admin/DeleteUser', {
                        telephone: telephone
                    }, {
                        headers: {'Authorization': this.$store.state.Authorization}
                    }).then(resp => {
                        if (resp.status === 200) {
                            this.$message({
                                message: "删除成功",
                                type: 'success'
                            });
                            this.$router.go(0)
                        }
                    }).catch(resp => {
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
                
                
            },
            UnDeleted(telephone){
                this.$axios.post('admin/UnDeleteUser', {
                    telephone: telephone
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200) {
                        this.$message({
                            message: resp.data.message,
                            type: 'success'
                        });
                        this.$router.go(0)
                    }
                }).catch(resp => {
                })
                // this.$router.go(0)
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
                return year + "-" + month + "-" + day;
            },
        }
    }
</script>

<style scoped>
    .main_body{
        margin: 0;
        padding: 0;
        height: 100%;
        width: 90%;
    }
    #NoticeManagement-top{
        width: 30%;
        height: 50px;
        padding: 20px 20px;
    }
    #NoticeManagement-top-left{
        display: flex;
        justify-content: flex-start;
    }
    .icons{
        width: 120px;
        text-align: center;
        font-size: 16px;
        line-height: 30px
    }
    .demo-table-expand {
        font-size: 0;
    }
    .demo-table-expand label {
        width: 90px;
        color: #99a9bf;
    }
    .demo-table-expand .el-form-item {
        margin-right: 0;
        margin-bottom: 0;
        width: 50%;
    }
    .tab{
        padding: 10px 10px;
        width: 97%;
    }
    .iocx{
        overflow:hidden;
        text-overflow:ellipsis;
        white-space:nowrap;
        word-break:keep-all;
    }
</style>