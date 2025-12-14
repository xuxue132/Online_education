<template>
    <div class="main_body">
        <div id="NoticeManagement-top">
            <el-space id="NoticeManagement-top-left">
                <span class="icons">商品名</span>
                <el-input size="small" v-model="searchForm.title" placeholder="请输入商品名"></el-input>
                <el-button style="margin-left: 10px" icon="el-icon-search" circle  size="small" @click="handle(1)"></el-button>
            </el-space>
            
            <el-button
                    style="margin-top: 20px"
                    type="success"
                    @click="Add()">添加</el-button>
        </div>
        
        <div class="tab">
            <el-table
                    :data="Honor"
                    style="width: 100%">
                
                <el-table-column
                        label="日期"
                        width="180">
                    <template slot-scope="scope">
                        <i class="el-icon-time"></i>
                        <span style="margin-left: 10px"  class="iocx">{{ scope.row.dates }}</span>
                    </template>
                </el-table-column>
                <el-table-column
                        label="标题"
                        width="180">
                    <template slot-scope="scope">
                        <span style="margin-left: 10px"  class="iocx">{{ scope.row.title }}</span>
                    </template>
                </el-table-column>
                <el-table-column
                        label="正文"
                        width="180">
                    <template slot-scope="scope"  >
                        <span style="margin-left: 10px" class="iocx">{{ scope.row.texts }}</span>
                    </template>
                </el-table-column>
                <el-table-column label="操作">
                    
                    <template slot-scope="scope">
                        <el-button
                                size="mini"
                                @click="Edits(scope.row)">编辑</el-button>
                        <el-button
                                size="mini"
                                type="danger"
                                @click="Deleted(scope.row.id)">删除</el-button>
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
        <el-dialog :append-to-body="true" center @close="Closes()"  title="编辑" :visible.sync="dialogFormVisible">
            <el-form :model="Honored" :rules="rules" ref="Honored">
                <el-form-item label="标题" :label-width="formLabelWidth" prop="title" >
                    <el-input v-model="Honored.title" clearable></el-input>
                </el-form-item>
                
                <el-form-item label="正文" :label-width="formLabelWidth" prop="texts">
                    <vue-editor v-model="Honored.texts"></vue-editor>
                </el-form-item>
                <el-form-item label="封面图片" :label-width="formLabelWidth" prop="picture">
                    <el-upload
                            class="avatar-uploader"
                            action="http://localhost:8700/admin/HonorUpload"
                            :headers="{'Authorization': this.$store.state.Authorization}"
                            :data = "{id: Honored.id}"
                            :show-file-list="false"
                            :on-success="handleAvatarSuccess"
                            :before-upload="beforeAvatarUpload"
                    >
                        <img v-if="imageUrl" :src="imageUrl" class="avatar">
                        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                    </el-upload>
                </el-form-item>
            </el-form>
            <div  slot="footer" class="dialog-footer">
                <el-button   @click="Closes()">取 消</el-button>
                <el-button type="primary" @click="Change()">确 定</el-button>
            </div>
        </el-dialog>
        <el-dialog :append-to-body="true" center  title="添加" @close="closed()" :visible.sync="dialogFormVisible2">
            <el-form :model="Honors" :rules="rules2" ref="Honors">
                <el-form-item label="标题" :label-width="formLabelWidth" prop="title" >
                    <el-input v-model="Honors.title" clearable></el-input>
                </el-form-item>
                
                <el-form-item label="正文" :label-width="formLabelWidth" prop="texts">
                    <vue-editor v-model="Honors.texts"></vue-editor>
                </el-form-item>
            </el-form>
            <div  slot="footer" class="dialog-footer">
                <el-button   @click="closed()">取 消</el-button>
                <el-button type="primary" @click="Posts()">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import {VueEditor} from "vue2-editor";

    export default {
        name: "honor",
        components: {
            VueEditor
        },
        data(){
            return{
                id:'',
                searchForm:{
                    title:''
                },
                si:0,
                imageUrl:'',
                Honor:[],
                Honored:{},
                Honors:{},
                number:0,
                page:1,
                page2:1,
                formLabelWidth: '120px',
                dialogFormVisible:false,
                dialogFormVisible2:false,
                rules: {
                    title: [
                        { required: true, message: '不能为空'},
                    ],
                    texts: [
                        { required: true, message: '不能为空'},
                    ],
                },
                rules2: {
                    title: [
                        { required: true, message: '不能为空'},
                    ],
                    texts: [
                        { required: true, message: '不能为空'},
                    ],
                },
            }
        },
        created(){
            this.HonorNumber()

        },
        methods:{
            Posts(){
                this.$refs['Honors'].validate((valid) => {
                    if(valid){
                        this.$axios.post('admin/AddHonor', {
                            title: this.Honors.title,
                            texts: this.Honors.texts,
                        }, {
                            headers: {'Authorization': this.$store.state.Authorization}
                        }).then(resp => {
                            if (resp.status === 200) {
                                this.$message({
                                    message: "添加成功",
                                    type: 'success'
                                });
                                this.$router.go(0)
                            }
                        }).catch(resp => {
                        })
                        this.dialogFormVisible2 = false
                    }
                })

            },
            Add(){
                this.dialogFormVisible2 = true
            },

            handleAvatarSuccess(res, file) {
                this.GetNew()
            },
            GetNew(){
                this.$axios.post('public/AnHonor', {
                    id: this.Honored.id,
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200) {
                        this.imageUrl = resp.data.data.images;
                    }
                }).catch(resp => {
                })
            },
            beforeAvatarUpload(file) {
                const isJPG = file.type === 'image/jpeg'
                const isJPG2 = file.type === 'image/png'
                const isLt2M = file.size / 1024 / 1024 < 2;

                if (!(isJPG || isJPG2)) {
                    this.$message.error('上传头像图片只能是 JPG/PNG 格式!');
                }
                if (!isLt2M) {
                    this.$message.error('上传头像图片大小不能超过 2MB!');
                }

                return (isJPG || isJPG2) && isLt2M;
            },
            HonorNumber(){
                this.$axios.post('public/AllHonorSum', {
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
                this.Honor=[]
                if(this.searchForm.title===''){
                    this.$router.go(0)
                }
                this.$axios.post('admin/selectHonor', {
                    ...this.searchForm,
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    this.number = resp.data.data.length
                    if((page-1)*10+10>resp.data.data.length){
                        for (var i=(page-1)*10;i<resp.data.data.length;i++) {
                            this.Honor.push(resp.data.data[i])
                        }
                    }else {
                        for (var i=(page-1)*10;i<(page-1)*10+10;i++) {
                            this.Honor.push(resp.data.data[i])
                        }
                    }
                }).catch(resp => {
                })

            },
            closed() {
                this.$refs["Honors"].resetFields();
                this.dialogFormVisible2 = false
            },
            Closes(){
                this.$refs["Honored"].resetFields();
                this.dialogFormVisible = false
            },
            Edits(i){
                this.dialogFormVisible = true
                this.imageUrl = i.images
                this.Honored = i
            },
            Change(){
                this.$refs['Honored'].validate((valid) => {
                    if (valid) {
                        this.$axios.post('admin/ChangeHonor', {
                            ...this.Honored,
                            id: this.Honored.id
                        }, {
                            headers: {'Authorization': this.$store.state.Authorization}
                        }).then(resp => {
                            if (resp.status === 200) {
                                this.$message({
                                    message: "修改成功",
                                    type: 'success'
                                });
                                this.$router.go(0)
                            }
                        }).catch(resp => {
                        })
                        this.dialogFormVisible = false
                    }
                })
            },
            inits(page){
                this.$axios.post('public/AllHonor', {
                    NowPage: page
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200) {
                        this.Honor = resp.data.data
                        for (var i=0;i<this.Honor.length;i++) {
                            this.Honor[i].dates = this.formatDate(new Date(this.Honor[i].dates))
                        }
                    }
                }).catch(resp => {
                })
            },

            Deleted(id){
                this.$confirm('此操作删除信息, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$axios.post('admin/DeleteHonor', {
                        id: id
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
        padding: 30px 10px;
        width: 97%;
    }
    .iocx{
        overflow:hidden;
        text-overflow:ellipsis;
        white-space:nowrap;
        word-break:keep-all;
    }
    .avatar-uploader .el-upload {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }
    .avatar-uploader .el-upload:hover {
        border-color: #409EFF;
    }
    .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 178px;
        height: 178px;
        line-height: 178px;
        text-align: center;
    }
    .avatar {
        width: 178px;
        height: 178px;
        display: block;
    }
</style>