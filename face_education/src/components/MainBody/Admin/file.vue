<template>
    <div class="main_body">
        <div class="NoticeManagement-top">
            <el-space class="NoticeManagement-top-left">
                <span class="icons">标题</span>
                <el-input size="small" v-model="searchForm.title" placeholder="请输入标题"></el-input>
                <el-button style="margin-left: 10px" icon="el-icon-search" circle  size="small" @click="handle(1)"></el-button>
            </el-space>
            <el-button
                    style="margin-top: 20px"
                    type="success"
                    @click="Add()">添加文件信息</el-button>
        </div>
        <div style="margin-top: 20px"  class="tab">
            <el-table
                    :data="FileInformation"
                    style="width: 100%">
    
                <el-table-column
                        label="文件号"
                        width="180">
                    <template slot-scope="scope" >
                        <span style="margin-left: 10px" class="iocx">{{ scope.row.id }}</span>
                    </template>
                </el-table-column>
                <el-table-column
                        label="日期"
                        width="180">
                    <template slot-scope="scope">
                        <i class="el-icon-time"></i>
                        <span style="margin-left: 10px"   class="iocx">{{ scope.row.dates }}</span>
                    </template>
                </el-table-column>
                <el-table-column
                        label="标题"
                        width="180">
                    <template slot-scope="scope">
                        <span style="margin-left: 10px"  class="iocx">{{ scope.row.title }}</span>
                    </template>
                </el-table-column>
            
                <el-table-column label="操作">
                
                    <template slot-scope="scope">
                        <el-button
                                size="mini"
                                @click="Edits(scope.row)">编辑</el-button>
                        <el-button
                                size="mini"
                                type="success"
                                @click="AddFile(scope.row.id)">添加文件</el-button>
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
        
        
        <div class="NoticeManagement-top">
            <el-space class="NoticeManagement-top-left">
                <span class="icons">文件号</span>
                <el-input size="small" v-model="searchForm1.fileId" placeholder="请输入文件号"></el-input>
                <el-button style="margin-left: 10px" icon="el-icon-search" circle  size="small" @click="handle1(1)"></el-button>
            </el-space>
 
        </div>
        <div style="margin-top: 20px"  class="tab">
            <el-table
                    :data="File"
                    style="width: 100%">
                <el-table-column
                        label="文件号"
                        width="180">
                    <template slot-scope="scope" >
                        <span style="margin-left: 10px"  class="iocx">{{ scope.row.fileId }}</span>
                    </template>
                </el-table-column>
                <el-table-column
                        label="文件名"
                        width="180">
                    <template slot-scope="scope"  >
                        <span style="margin-left: 10px" class="iocx">{{ scope.row.filename }}</span>
                    </template>
                </el-table-column>
            
                <el-table-column label="操作">
                
                    <template slot-scope="scope">
                        <el-button
                                size="mini"
                                type="danger"
                                @click="Deleted1(scope.row.id)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <div>
            <el-pagination
                    style="margin-left: 40%"
                    @current-change="handleCurrentChange1"
                    background
                    layout="prev, pager, next"
                    :total="this.number1">
            </el-pagination>
        </div>
    
        <el-dialog :append-to-body="true" center @close="Closes()"  title="添加文件信息" :visible.sync="dialogFormVisible">
            <el-form :model="FileInformations" :rules="rules" ref="FileInformations">
                <el-form-item label="标题" :label-width="formLabelWidth" prop="title">
                    <el-input  v-model="FileInformations.title" clearable></el-input>
                </el-form-item>
            </el-form>
            <div  slot="footer" class="dialog-footer">
                <el-button   @click="Closes()">取 消</el-button>
                <el-button type="primary" @click="Posts()">确 定</el-button>
            </div>
        </el-dialog>
        <el-dialog :append-to-body="true" center  title="编辑文件信息" :visible.sync="dialogFormVisible1">
            <el-form :model="FileInformationed" ref="FileInformationed">
                <el-form-item label="标题" :label-width="formLabelWidth" prop="title">
                    <el-input  v-model="FileInformationed.title" clearable></el-input>
                </el-form-item>
            </el-form>
            <div  slot="footer" class="dialog-footer">
                <el-button   @click="Closes1()">取 消</el-button>
                <el-button type="primary" @click="Change()">确 定</el-button>
            </div>
        </el-dialog>
        <el-dialog :append-to-body="true" center @close="Closes2()"  title="添加文件" :visible.sync="dialogFormVisible2">
            <el-form :model="Files" ref="Files">
                <el-upload
                    class="upload-demo"
                    action="http://localhost:8700/admin/FileUpload"
                    :before-remove="beforeRemove"
                    :headers="{'Authorization': this.$store.state.Authorization}"
                    :data = "{id:this.id}"
                    :auto-upload="false"
                    ref="upload"
                    multiple
                    >
                    <el-button size="small" type="primary">点击上传</el-button>
                </el-upload>
            </el-form>
            <div  slot="footer" class="dialog-footer">
                <el-button   @click="Closes2()">取 消</el-button>
                <el-button type="primary" @click="Uploads()">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "file",
        data(){
            return{
                id:0,
                searchForm:{
                    title:'',
                },
                searchForm1:{
                    fileId:'',
                },
                formLabelWidth:'120px',
                dialogFormVisible:false,
                dialogFormVisible1:false,
                dialogFormVisible2:false,
                rules: {
                    title: [
                        { required: true, message: '不能为空'},
                    ],
                },
                FileInformation:{},
                File:{},
                
                FileInformations:[],
                Files:[],
                
                FileInformationed:{},
                Filed:{},
                si1:0,
                si2:0,
                number:0,
                number1:0,
                page:1,
                page1:1,
            }
        },
        created(){
            this.FileInformationNumber()
        },
        methods:{
            Add(){
                this.dialogFormVisible = true
            },
            Deleted(id){
                this.$confirm('此操作删除信息, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$axios.post('admin/DeleteFileName', {
                        id: id
                    }, {
                        headers: {'Authorization': this.$store.state.Authorization}
                    }).then(resp => {
                        this.$message({
                            message: "删除成功",
                            type: 'success'
                        });
                        this.$router.go(0)
                    }).catch(resp => {
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            Deleted1(id){
                this.$confirm('此操作删除信息, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$axios.post('admin/DeleteFile', {
                        id: id
                    }, {
                        headers: {'Authorization': this.$store.state.Authorization}
                    }).then(resp => {
                        this.$message({
                            message: "删除成功",
                            type: 'success'
                        });
                        this.$router.go(0)
                    }).catch(resp => {
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            AddFile(id){
                this.id = id
                this.dialogFormVisible2 = true
            },
            Edits(i){
               this.FileInformationed = i
               this.dialogFormVisible1 = true
            },
            Closes(){
                this.$refs["FileInformations"].resetFields();
                this.dialogFormVisible = false
            },
            Closes1(){
                this.dialogFormVisible1 = false
            },
            Closes2(){
                this.$refs.upload.clearFiles()//清空上传列表
                this.dialogFormVisible2 = false
            },
            Uploads(){
                this.$refs.upload.submit();
                this.$refs.upload.clearFiles()//清空上传列表
                this.dialogFormVisible2 = false
            },
            beforeRemove(file, fileList) {
                return this.$confirm(`确定移除 ${ file.name }？`);
            },
            Change(){
                this.$axios.post('admin/UpdateFileInformations', {
                    title: this.FileInformationed.title,
                    id: this.FileInformationed.id,
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    this.$message({
                        message: "添加成功",
                        type: 'success'
                    });
                    this.$router.go(0)
                }).catch(resp => {
                })

                this.dialogFormVisible1 = false
            },
            Posts(){
                this.$refs['FileInformations'].validate((valid) => {
                  if(valid){
                      this.$axios.post('admin/AddFileInformations', {
                          title: this.FileInformations.title,
                      }, {
                          headers: {'Authorization': this.$store.state.Authorization}
                      }).then(resp => {
                          this.$message({
                              message: "添加成功",
                              type: 'success'
                          });
                          this.$router.go(0)
                      }).catch(resp => {
                      })

                      this.dialogFormVisible = false
                  }
                })
                
                
            },
            handleCurrentChange(i){
                if(this.si1 === 1){
                    // this.page2 = i
                    this.handle(i)
                }else {
                    this.inits(i)
                }
            },
            handleCurrentChange1(i){
                if(this.si2 === 1){
                    // this.page2 = i
                    this.handle1(i)
                }else {
                    this.inits1(i)
                }
            },
            FileInformationNumber(){
                this.$axios.post('public/SumFileInformation', {
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200) {
                        this.number = resp.data.data
                        this.inits(this.page)
                    }
                }).catch(resp => {
                })
                
                this.$axios.post('public/SumFile', {
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200) {
                        this.number1 = resp.data.data
                        this.inits1(this.page)
                    }
                }).catch(resp => {
                })
               
            },
            inits(page){
                this.$axios.post('public/AllFileInformation', {
                    NowPage: page
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200) {
                        this.FileInformation = resp.data.data
                        for (var i=0;i<this.FileInformation.length;i++) {
                            this.FileInformation[i].dates = this.formatDate(new Date(this.FileInformation[i].dates))
                        }
                    }
                }).catch(resp => {
                })
            },
            inits1(page){
                this.$axios.post('public/AllFile', {
                    NowPage: page
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200) {
                        this.File = resp.data.data
                    }
                }).catch(resp => {
                })
            },
            handle(page){
                this.si1 = 1
                this.FileInformation=[]
                if(this.searchForm.title === ''){
                    this.$router.go(0)
                }
                this.$axios.post('admin/selectFileInformation', {
                    ...this.searchForm,
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    this.number = resp.data.data.length
                    if((page-1)*10+10>resp.data.data.length){
                        for (var i=(page-1)*10;i<resp.data.data.length;i++) {
                            this.FileInformation.push(resp.data.data[i])
                        }
                    }else {
                        for (var i=(page-1)*10;i<(page-1)*10+10;i++) {
                            this.FileInformation.push(resp.data.data[i])
                        }
                    }
                    for (var i=0;i<this.FileInformation.length;i++) {
                        this.FileInformation[i].dates = this.formatDate(new Date(this.FileInformation[i].dates))
                    }
                }).catch(resp => {
                })

            },
            handle1(page){
                this.si2 = 1
                this.File=[]
                if(this.searchForm1.fileId === ''){
                    this.$router.go(0)
                }
                this.$axios.post('public/TheFile', {
                    ...this.searchForm1,
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    this.number1 = resp.data.data.length
                    if((page-1)*10+10>resp.data.data.length){
                        for (var i=(page-1)*10;i<resp.data.data.length;i++) {
                            this.File.push(resp.data.data[i])
                        }
                    }else {
                        for (var i=(page-1)*10;i<(page-1)*10+10;i++) {
                            this.File.push(resp.data.data[i])
                        }
                    }
                }).catch(resp => {
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
    .NoticeManagement-top{
        width: 30%;
        height: 50px;
        padding: 20px 20px;
    }
    .NoticeManagement-top-left{
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