<template>
    <div class="main_body">
        <div id="NoticeManagement-top">
            <el-space id="NoticeManagement-top-left">
                <span class="icons">标题</span>
                <el-input size="small" v-model="searchForm.title" placeholder="请输入标题"></el-input>
                <span class="icons">社区</span>
                <el-input size="small" v-model="searchForm.area" placeholder="请输入作者"></el-input>
                <el-button style="margin-left: 10px" icon="el-icon-search" circle  size="small" @click="handle(1)"></el-button>
            </el-space>
            
            <el-button
                    style="margin-top: 20px"
                    type="success"
                    @click="Add()">添加</el-button>
        </div>
        
        <div class="tab">
            <el-table
                    :data="CharacterProjects"
                    style="width: 100%">
                
                <el-table-column
                        label="发布日期"
                        width="180">
                    <template slot-scope="scope">
                        <i class="el-icon-time"></i>
                        <span   class="iocx">{{ scope.row.dates }}</span>
                    </template>
                </el-table-column>
                <el-table-column
                        label="题目"
                        width="180">
                    <template slot-scope="scope">
                        <span  class="iocx">{{ scope.row.title }}</span>
                    </template>
                </el-table-column>
                <el-table-column
                        label="社区"
                        width="180">
                    <template slot-scope="scope">
                            <span  class="iocx">{{ scope.row.area }}</span>
                    </template>
                </el-table-column>

                <el-table-column
                        label="正文"
                        width="380">
                    <template slot-scope="scope">
                        <span  class="iocx">{{ scope.row.texts }}</span>
                    </template>
                </el-table-column>
                
                <el-table-column
                        label="类型"
                        width="80">
                    <template slot-scope="scope">
                        <span  class="iocx">{{ scope.row.types }}</span>
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
            <el-form :model="CharacterProjected"  ref="CharacterProjected">
                <el-form-item label="题目" :label-width="formLabelWidth" >
                    <el-input  v-model="CharacterProjected.title" clearable></el-input>
                </el-form-item>
                <el-form-item label="地区" :label-width="formLabelWidth" >
                    <el-input v-model="CharacterProjected.area" clearable></el-input>
                </el-form-item>

                <el-form-item label="正文" :label-width="formLabelWidth">
                    <vue-editor v-model="CharacterProjected.texts"></vue-editor>
                </el-form-item>
                <el-form-item label="图片" :label-width="formLabelWidth" prop="4">
                    <el-upload
                            class="avatar-uploader"
                            action="http://localhost:8700/admin/UpdateUpload"
                            :headers="{'Authorization': this.$store.state.Authorization}"
                            :data = "{id:this.CharacterProjected.id}"
                            :show-file-list="false"
                            :on-success="handleAvatarSuccess"
                            :before-upload="beforeAvatarUpload"
                    >
                        <img v-if="imageUrl" :src="imageUrl" class="avatar">
                        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                    </el-upload>
                </el-form-item>
                
                <el-form-item label="类型" :label-width="formLabelWidth">
                    <el-input v-model="CharacterProjected.types"  clearable></el-input>
                </el-form-item>
            
            </el-form>
            <div  slot="footer" class="dialog-footer">
                <el-button   @click="Closes()">取 消</el-button>
                <el-button type="primary" @click="Posst()">确 定</el-button>
            </div>
        </el-dialog>



        <el-dialog :append-to-body="true" center  @close="Closes1()" title="添加" :visible.sync="dialogFormVisible2">
            <el-form :model="CharacterProject"  ref="CharacterProject">
                <el-form-item label="题目" :label-width="formLabelWidth" >
                    <el-input  v-model="CharacterProject.title" clearable></el-input>
                </el-form-item>
                <el-form-item label="地区" :label-width="formLabelWidth"  >
                    <el-input v-model="CharacterProject.area" clearable></el-input>
                </el-form-item>
                <el-form-item label="正文" :label-width="formLabelWidth" >
                    <vue-editor v-model="CharacterProject.texts"></vue-editor>
                </el-form-item>
                <el-form-item label="类型" :label-width="formLabelWidth">
                    <el-input v-model="CharacterProject.types" clearable></el-input>
                </el-form-item>
            
            </el-form>
            <div  slot="footer" class="dialog-footer">
                <el-button   @click="Closes2()">取 消</el-button>
                <el-button type="primary" @click="Posst2()">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import {VueEditor} from "vue2-editor";

    export default {
        name: "CharacterPage",
        components: {
            VueEditor
        },
        data(){
            return{
                searchForm:{
                    title:'',
                    area:''
                },
                imageUrl: '',
                si:0,
                CharacterProjected:{},
                CharacterProject:{title:"",area:'',texts:'',types:''},
                CharacterProjects:[],
                fileList:{name:'封面',url:""},
                dialogFormVisible:false,
                dialogFormVisible2:false,
                number:0,
                page:1,
                page2:1,
                formLabelWidth:'120px'
            }
        },
        created(){
            this.NewCharacter();
        },
        methods:{
            Closes(){
                this.NewCharacter()
                this.dialogFormVisible = false
            },
            Closes2(){
                this.$refs["CharacterProject"].resetFields();
                this.dialogFormVisible2 = false
            },
            Closes1(){
                this.$refs["CharacterProject"].resetFields();
            },
            Add(){
                this.dialogFormVisible2 = true
            },
            handleAvatarSuccess(res, file) {
                this.GetNew()
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
            GetNew(){
                this.$axios.post('public/AnCharacterProject', {
                    id: this.CharacterProjected.id,
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200) {
                        this.imageUrl = resp.data.data.images;
                    }
                }).catch(resp => {
                })
            },
            Posst(){
                this.$axios.post('admin/UpdateCharacterProject', {
                    title: this.CharacterProjected.title,
                    area: this.CharacterProjected.area,
                    texts: this.CharacterProjected.texts,
                    types: this.CharacterProjected.types,
                    id: this.CharacterProjected.id,
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200) {
                        this.$message({
                            message: "修改成功",
                            type: 'success'
                        });
                    }
                }).catch(resp => {
                })
                this.dialogFormVisible = false
                // this.$refs.upload.submit();
            },
            Posst2(){
                this.$axios.post('admin/AddCharacterProject', {
                    title: this.CharacterProject.title,
                    area: this.CharacterProject.area,
                    texts: this.CharacterProject.texts,
                    types: this.CharacterProject.types,
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
            },
            Edits(i){
                this.CharacterProjected = i
                this.imageUrl = i.images
                this.dialogFormVisible = true
            },
            Deleted(id){
                this.$confirm('此操作删除信息, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$axios.post('admin/DeleteCharacterProject', {
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
            handle(page){
                this.si = 1
                this.CharacterProjects=[]
                if(this.searchForm.area === '' && this.searchForm.title===''){
                    this.$router.go(0)
                }

                this.$axios.post('admin/selectCharacterProject', {
                    ...this.searchForm,
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    this.number = resp.data.data.length
                    if((page-1)*10+10>resp.data.data.length){
                        for (var i=(page-1)*10;i<resp.data.data.length;i++) {
                            this.CharacterProjects.push(resp.data.data[i])
                        }
                    }else {
                        for (var i=(page-1)*10;i<(page-1)*10+10;i++) {
                            this.CharacterProjects.push(resp.data.data[i])
                        }
                    }
                    for (var i=0;i<this.CharacterProjects.length;i++) {
                        this.CharacterProjects[i].dates = this.formatDate(new Date(this.CharacterProjects[i].dates))
                    }
                }).catch(resp => {
                })
            },
            handleCurrentChange(i){

                if(this.si === 1){
                    this.handle(i)
                }else {
                    this.Character(i)
                }
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
            Character(page){
                this.$axios.post('public/CharacterProject', {
                    NowPage: page
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200) {
                        this.CharacterProjects = resp.data.data
                        for (var i=0;i<this.CharacterProjects.length;i++) {
                            this.CharacterProjects[i].dates = this.formatDate(new Date(this.CharacterProjects[i].dates))
                        }

                    }
                }).catch(resp => {
                })
            },

            NewCharacter() {
                this.$axios.post('public/CharacterProjectNumber', {
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200) {
                        this.number = resp.data.data
                        this.Character(this.page)
                    }
                }).catch(resp => {
                })

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
        padding: 23px 10px;
        width: 100%;
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