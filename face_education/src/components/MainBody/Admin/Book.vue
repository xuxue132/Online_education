<template>
    <div class="main_body">
        <div class="NoticeManagement-top">
            <el-space class="NoticeManagement-top-left">
                <span class="icons">名</span>
                <el-input size="small" v-model="searchForm.name_book" placeholder="名"></el-input>
                <el-button style="margin-left: 10px" icon="el-icon-search" circle  size="small" @click="handle(1)"></el-button>
            </el-space>
            <el-button
                    style="margin-top: 20px"
                    type="success"
                    @click="Add()">添加书籍文章</el-button>
        </div>
        <div style="margin-top: 20px"  class="tab">
            <el-table
                    :data="Library"
                    style="width: 100%">
                
                <el-table-column
                        label="书籍文章号"
                        width="180">
                    <template slot-scope="scope" >
                        <span style="margin-left: 10px" class="iocx">{{ scope.row.idBook }}</span>
                    </template>
                </el-table-column>
                <el-table-column
                        label="书籍文章名"
                        width="180">
                    <template slot-scope="scope">
                        <span style="margin-left: 10px"   class="iocx">{{ scope.row.nameBook }}</span>
                    </template>
                </el-table-column>
                <el-table-column
                        label="时间"
                        width="180">
                    <template slot-scope="scope">
                        <span style="margin-left: 10px"  class="iocx">{{ scope.row.timeBook }}</span>
                    </template>
                </el-table-column>
                <el-table-column
                        label="作者"
                        width="180">
                    <template slot-scope="scope">
                        <span style="margin-left: 10px"  class="iocx">{{ scope.row.author }}</span>
                    </template>
                </el-table-column>
                <el-table-column
                        label="介绍"
                        width="180">
                    <template slot-scope="scope">
                        <span style="margin-left: 10px"  class="iocx">{{ scope.row.introduce }}</span>
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
                                @click="AddBookContents(scope.row.idBook)">添加目录文章</el-button>
                        <el-button
                                size="mini"
                                type="danger"
                                @click="Deleted(scope.row.idBook)">删除</el-button>
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
                <span class="icons">标号</span>
                <el-input size="small" v-model="searchForm1.id_book" placeholder="标号"></el-input>
                <el-button style="margin-left: 10px" icon="el-icon-search" circle  size="small" @click="handle1(1)"></el-button>
            </el-space>
        
        </div>
        <div style="margin-top: 20px"  class="tab">
            <el-table
                    :data="BookContent"
                    style="width: 100%">
                <el-table-column
                        label="标号"
                        width="180">
                    <template slot-scope="scope" >
                        <span style="margin-left: 10px"  class="iocx">{{ scope.row.idBook }}</span>
                    </template>
                </el-table-column>
                <el-table-column
                        label="目录"
                        width="180">
                    <template slot-scope="scope"  >
                        <span style="margin-left: 10px" class="iocx">{{ scope.row.catalog}}</span>
                    </template>
                </el-table-column>
                <el-table-column
                        label="正文"
                        width="180">
                    <template slot-scope="scope"  >
                        <span style="margin-left: 10px" class="iocx">{{ scope.row.content }}</span>
                    </template>
                </el-table-column>
                
                <el-table-column label="操作">
                    
                    <template slot-scope="scope">
                        <el-button
                                size="mini"
                                type="danger"
                                @click="Deleted1(scope.row.id)">删除</el-button>
                        <el-button
                                size="mini"
                                @click="Edits1(scope.row)">编辑</el-button>
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
        <div style="height: 100px"></div>
        
        <el-dialog :append-to-body="true" center @close="Closes()"  title="添加书籍" :visible.sync="dialogFormVisible">
            <el-form :model="Librarys" :rules="rules" ref="Librarys">
                <el-form-item label="名" :label-width="formLabelWidth" prop="nameBook">
                    <el-input  v-model="Librarys.nameBook" clearable></el-input>
                </el-form-item>
                <el-form-item label="时间" :label-width="formLabelWidth" prop="timeBook">
                    <el-input  v-model="Librarys.timeBook" clearable></el-input>
                </el-form-item>
                <el-form-item label="作者" :label-width="formLabelWidth" prop="author">
                    <el-input  v-model="Librarys.author" clearable></el-input>
                </el-form-item>
                <el-form-item label="介绍" :label-width="formLabelWidth" prop="introduce">
                    <el-input  v-model="Librarys.introduce" clearable></el-input>
                </el-form-item>
            </el-form>
            <div  slot="footer" class="dialog-footer">
                <el-button   @click="Closes()">取 消</el-button>
                <el-button type="primary" @click="Posts()">确 定</el-button>
            </div>
        </el-dialog>
        <el-dialog :append-to-body="true" center  title="编辑文件信息" @close="Closes1" :visible.sync="dialogFormVisible1">
            <el-form :model="Libraryed" :rules="rules" ref="Libraryed">
                <el-form-item label="名" :label-width="formLabelWidth" prop="nameBook">
                    <el-input  v-model="Libraryed.nameBook" clearable></el-input>
                </el-form-item>
                <el-form-item label="时间" :label-width="formLabelWidth" prop="timeBook">
                    <el-input  v-model="Libraryed.timeBook" clearable></el-input>
                </el-form-item>
                <el-form-item label="作者" :label-width="formLabelWidth" prop="author">
                    <el-input  v-model="Libraryed.author" clearable></el-input>
                </el-form-item>
                <el-form-item label="介绍" :label-width="formLabelWidth" prop="introduce">
                    <el-input  v-model="Libraryed.introduce" clearable></el-input>
                </el-form-item>
                <el-form-item label="封面图片" :label-width="formLabelWidth" prop="picture">
                    <el-upload
                            class="avatar-uploader"
                            action="http://localhost:8700/admin/UpdateLibraryPicture"
                            :headers="{'Authorization': this.$store.state.Authorization}"
                            :data = "{id: Libraryed.idBook}"
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
                <el-button   @click="Closes1()">取 消</el-button>
                <el-button type="primary" @click="Change()">确 定</el-button>
            </div>
        </el-dialog>
        <el-dialog :append-to-body="true" center @close="Closes2()"  title="添加目录文章" :visible.sync="dialogFormVisible2">
            <el-form :model="BookContents" :rules="rules1" ref="BookContents">
                <el-form-item label="目录章节名" :label-width="formLabelWidth" prop="catalog">
                    <el-input  v-model="BookContents.catalog" clearable></el-input>
                </el-form-item>
                <el-form-item label="时间" :label-width="formLabelWidth" prop="content">
                    <vue-editor v-model="BookContents.content"></vue-editor>
                </el-form-item>
            </el-form>
            <div  slot="footer" class="dialog-footer">
                <el-button   @click="Closes2()">取 消</el-button>
                <el-button type="primary" @click="AddBookContent()">确 定</el-button>
            </div>
        </el-dialog>
        <el-dialog :append-to-body="true" center @close="Closes3()"  title="编辑目录文章" :visible.sync="dialogFormVisible3">
            <el-form :model="BookContented" :rules="rules1" ref="BookContented">
                <el-form-item label="目录章节名" :label-width="formLabelWidth" prop="catalog">
                    <el-input  v-model="BookContented.catalog" clearable></el-input>
                </el-form-item>
                <el-form-item label="时间" :label-width="formLabelWidth" prop="content">
                    <vue-editor v-model="BookContented.content"></vue-editor>
                </el-form-item>
            </el-form>
            <div  slot="footer" class="dialog-footer">
                <el-button   @click="Closes3()">取 消</el-button>
                <el-button type="primary" @click="ChangeBookContent()">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import {VueEditor} from "vue2-editor";

    export default {
        name: "Book",
        components: {
            VueEditor
        },
        data(){
            return{
                id:0,
                searchForm:{
                    name_book:'',
                },
                searchForm1:{
                    id_book:'',
                },
                formLabelWidth:'120px',
                dialogFormVisible:false,
                dialogFormVisible1:false,
                dialogFormVisible2:false,
                dialogFormVisible3:false,
                rules: {
                    introduce: [
                        { required: true, message: '不能为空'},
                    ],
                    nameBook: [
                        { required: true, message: '不能为空'},
                    ],
                    author: [
                        { required: true, message: '不能为空'},
                    ],
                    timeBook: [
                        { required: true, message: '不能为空'},
                    ],
                },
                rules1: {
                    content: [
                        { required: true, message: '不能为空'},
                    ],
                    catalog: [
                        { required: true, message: '不能为空'},
                    ],
                   
                },
                Library:[],
                BookContent:[],

                Librarys: {},
                BookContents:{},

                Libraryed: {},
                BookContented: {},

                imageUrl:'',
                si1:0,
                si2:0,
                number:0,
                number1:0,
                page:1,
                page1:1,
            }
        },
        created(){
            this.LibraryNumber()
        },
        methods:{
            Add(){
                this.dialogFormVisible = true
            },
            handleAvatarSuccess(res, file) {
                this.GetNew()
            },
            GetNew(){
                this.$axios.post('public/OneBook', {
                    id_book: this.Libraryed.idBook,
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200) {
                        this.imageUrl = resp.data.data.picture;
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
            Deleted(id){
                this.$confirm('此操作删除信息, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$axios.post('admin/DeleteLibrary', {
                        id_book: id
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
                    this.$axios.post('admin/DeleteBookContent', {
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
            AddBookContents(id){
                this.id = id
                this.dialogFormVisible2 = true
            },
            Edits(i){
                this.Libraryed = i
                this.imageUrl = i.picture
                this.dialogFormVisible1 = true
            },
            Edits1(i){
                this.BookContented = i
                this.dialogFormVisible3 = true
            },
            Closes(){
                this.$refs["Librarys"].resetFields();
                this.dialogFormVisible = false
            },
            Closes1(){
                this.$refs["Libraryed"].resetFields();
                this.dialogFormVisible1 = false
            },
            Closes2(){
                this.$refs["BookContents"].resetFields();
                this.dialogFormVisible2 = false
            },
            Closes3(){
                this.$refs["BookContented"].resetFields();
                this.dialogFormVisible3 = false
            },
            AddBookContent(){
                this.$refs['BookContents'].validate((valid) => {
                    if (valid) {
                        this.$axios.post('admin/AddBookContents', {
                            catalog: this.BookContents.catalog,
                            content: this.BookContents.content,
                            idBook: this.id,
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
                        this.dialogFormVisible2 = false
                    }
                })
                
            },
            ChangeBookContent(){
                this.$refs['BookContented'].validate((valid) => {
                    if (valid) {
                        this.$axios.post('admin/ChangeBookContents', {
                            catalog: this.BookContented.catalog,
                            content: this.BookContented.content,
                            id:this.BookContented.id
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
                        this.dialogFormVisible2 = false
                    }
                })
                
            },
           
            Change(){
                
                this.$refs['Libraryed'].validate((valid) => {
                    if (valid) {
                        this.$axios.post('admin/UpdateLibrary', {
                            nameBook: this.Libraryed.nameBook,
                            timeBook: this.Libraryed.timeBook,
                            author: this.Libraryed.author,
                            introduce: this.Libraryed.introduce,
                            idBook: this.Libraryed.idBook,
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
                    }
                })
               
            },
            Posts(){
                this.$refs['Librarys'].validate((valid) => {
                    if(valid){
                        this.$axios.post('admin/AddLibrary', {
                            ...this.Librarys,
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
            LibraryNumber(){
                this.$axios.post('public/AllBookSum', {
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200) {
                        this.number = resp.data.data
                        this.inits(this.page)
                    }
                }).catch(resp => {
                })

                this.$axios.post('public/AllBookContentSum', {
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
                this.$axios.post('public/AllBook', {
                    NowPage: page
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200) {
                        this.Library = resp.data.data
                    }
                }).catch(resp => {
                })
            },
            inits1(page){
                this.$axios.post('public/AllBookContent', {
                    NowPage: page
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200) {
                        
                        this.BookContent = resp.data.data
                    }
                }).catch(resp => {
                })
            },
            handle(page){
                this.si1 = 1
                this.Library=[]
                if(this.searchForm.name_book === ''){
                    this.$router.go(0)
                }
                this.$axios.post('admin/selectLibrary', {
                    ...this.searchForm,
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    this.number = resp.data.data.length
                    if((page-1)*10+10>resp.data.data.length){
                        for (var i=(page-1)*10;i<resp.data.data.length;i++) {
                            this.Library.push(resp.data.data[i])
                        }
                    }else {
                        for (var i=(page-1)*10;i<(page-1)*10+10;i++) {
                            this.Library.push(resp.data.data[i])
                        }
                    }
                }).catch(resp => {
                })

            },
            handle1(page){
                this.si2 = 1
                this.BookContent=[]
                if(this.searchForm1.id_book === ''){
                    this.$router.go(0)
                }
                this.$axios.post('public/TheBook', {
                    ...this.searchForm1,
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    this.number1 = resp.data.data.length
                    if((page-1)*10+10>resp.data.data.length){
                        for (var i=(page-1)*10;i<resp.data.data.length;i++) {
                            this.BookContent.push(resp.data.data[i])
                        }
                    }else {
                        for (var i=(page-1)*10;i<(page-1)*10+10;i++) {
                            this.BookContent.push(resp.data.data[i])
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