<template>
    
    <div class="main_body">
        <div id="NoticeManagement-top">
            <el-space id="NoticeManagement-top-left">
                <span class="icons">标题</span>
                <el-input size="small" v-model="searchForm.title" placeholder="请输入标题"></el-input>
                <span class="icons">作者</span>
                <el-input size="small" v-model="searchForm.author" placeholder="请输入作者"></el-input>
                <el-button style="margin-left: 10px" icon="el-icon-search" circle  size="small" @click="handle(1)"></el-button>
            </el-space>
    
            <el-button
                    style="margin-top: 20px"
                    type="success"
                    @click="Add()">添加</el-button>
        </div>
        
        <div class="tab">
            <el-table
                    :data="NewNotices"
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
                            label="作者"
                            width="180">
                        <template slot-scope="scope">
                            <span  class="iocx">{{ scope.row.author }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column
                            label="来源"
                            width="100">
                        <template slot-scope="scope">
                            <span class="iocx">{{ scope.row.sources }}</span>
                        </template>
                    </el-table-column>
    
                    <el-table-column
                            label="点击量"
                            width="90">
                        <template slot-scope="scope">
                            <span   class="iocx">{{ scope.row.hits }}</span>
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
                            <span style="margin-left: 10px" v-if="scope.row.type === 0 ">新闻</span>
                            <span style="margin-left: 10px" v-if="scope.row.type === 1 ">公告</span>
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
        <el-dialog :append-to-body="true" center  title="编辑" :visible.sync="dialogFormVisible">
            <el-form :model="NewNoticed"  ref="NewNoticed">
                <el-form-item label="题目" :label-width="formLabelWidth" >
                    <el-input  v-model="NewNoticed.title" clearable></el-input>
                </el-form-item>
                <el-form-item label="作者" :label-width="formLabelWidth" >
                    <el-input v-model="NewNoticed.author" clearable></el-input>
                </el-form-item>
                <el-form-item label="来源" :label-width="formLabelWidth">
                    <el-input v-model="NewNoticed.sources"  clearable></el-input>
                </el-form-item>
                <el-form-item label="点击量" :label-width="formLabelWidth">
                    <el-input v-model="NewNoticed.hits"  clearable></el-input>
                </el-form-item>
                <el-form-item label="正文" :label-width="formLabelWidth">
                    <vue-editor v-model="NewNoticed.texts"></vue-editor>
<!--                    <tinymce v-model="NewNoticed.texts" :height="300"></tinymce>-->
                </el-form-item>

                <el-form-item label="类型" :label-width="formLabelWidth">
                    <el-input v-model="NewNoticed.types"  clearable></el-input>
                </el-form-item>
     
            </el-form>
            <div  slot="footer" class="dialog-footer">
                <el-button   @click="Closes()">取 消</el-button>
                <el-button type="primary" @click="Posst()">确 定</el-button>
            </div>
        </el-dialog>
        
        <el-dialog :append-to-body="true" center  @close="Closes1()" title="添加" :visible.sync="dialogFormVisible2">
            <el-form :model="AddNewNoticed"  ref="AddNewNoticed">
                <el-form-item label="题目" :label-width="formLabelWidth" >
                    <el-input  v-model="AddNewNoticed.title" clearable></el-input>
                </el-form-item>
                <el-form-item label="作者" :label-width="formLabelWidth"  >
                    <el-input v-model="AddNewNoticed.author" clearable></el-input>
                </el-form-item>
                <el-form-item label="来源" :label-width="formLabelWidth" >
                    <el-input v-model="AddNewNoticed.sources"  clearable></el-input>
                </el-form-item>
                <el-form-item label="点击量" :label-width="formLabelWidth" >
                    <el-input v-model="AddNewNoticed.hits"  clearable disabled></el-input>
                </el-form-item>
                <el-form-item label="正文" :label-width="formLabelWidth" >
                    <vue-editor v-model="AddNewNoticed.texts"></vue-editor>
                </el-form-item>
                <el-form-item label="类型" :label-width="formLabelWidth">
                    <el-select v-model="AddNewNoticed.types" placeholder="请选择">
                        <el-option
                                v-for="item in options"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                        </el-option>
                    </el-select>
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
        name: "NewNoticePage",
        components: {
            VueEditor,
        },
        data(){
            return{
                searchForm:{
                    title:'',
                    author:''
                },
                value: 0,
                options: [{
                    value: 0,
                    label: '新闻'
                }, {
                    value: 1,
                    label: '公告'
                }],
                
                si:0,
                NewNoticed:{},
                AddNewNoticed:{title:"",author:'',sources:'',hits:0,texts:'',types:0},
                NewNotices:[],
                // fileList:[],
                dialogFormVisible:false,
                dialogFormVisible2:false,
                number:0,
                page:1,
                page2:1,
                formLabelWidth:'120px'
            }
        },
        created(){
            this.NewNoticePage();
        },
        methods:{
            Closes(){
                this.dialogFormVisible = false
            },
            Closes2(){
                this.$refs["AddNewNoticed"].resetFields();
                this.dialogFormVisible2 = false
            },
            Closes1(){
                this.$refs["AddNewNoticed"].resetFields();
            },
            Add(){
                this.dialogFormVisible2 = true
            },
 
            Posst(){
                this.$axios.post('admin/UpdateNewNotice', {
                    title: this.NewNoticed.title,
                    author: this.NewNoticed.author,
                    sources: this.NewNoticed.sources,
                    hits: this.NewNoticed.hits,
                    texts: this.NewNoticed.texts,
                    id: this.NewNoticed.id,
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
                this.$axios.post('admin/AddNewNotice', {
                    title: this.AddNewNoticed.title,
                    author: this.AddNewNoticed.author,
                    sources: this.AddNewNoticed.sources,
                    hits: this.AddNewNoticed.hits,
                    texts: this.AddNewNoticed.texts,
                    types: this.AddNewNoticed.types,
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
                this.NewNoticed = i
                this.$axios.post('public/TextPicture', {
                    outId: i.id,
                    types: 1
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200) {
                        this.fileList = resp.data.data
                    }
                }).catch(resp => {
                })
                this.dialogFormVisible = true
            },
            Deleted(id){
                this.$confirm('此操作删除信息, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$axios.post('admin/DeleteNewNotice', {
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
                this.NewNotices=[]
                if(this.searchForm.author === '' && this.searchForm.title===''){
                    this.$router.go(0)
                }
          
                this.$axios.post('admin/selectNewNotice', {
                    ...this.searchForm,
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    this.number = resp.data.data.length
                    if((page-1)*10+10>resp.data.data.length){
                        for (var i=(page-1)*10;i<resp.data.data.length;i++) {
                            this.NewNotices.push(resp.data.data[i])
                        }
                    }else {
                        for (var i=(page-1)*10;i<(page-1)*10+10;i++) {
                            this.NewNotices.push(resp.data.data[i])
                        }
                    }
                    for (var i=0;i<this.NewNotices.length;i++) {
                        this.NewNotices[i].dates = this.formatDate(new Date(this.NewNotices[i].dates))
                    }
                }).catch(resp => {
                })
            },
            handleCurrentChange(i){
                
                if(this.si === 1){
                    this.handle(i)
                }else {
                    this.NewNotice(i)
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
            NewNotice(page){
                this.$axios.post('public/NewNotices', {
                    NowPage: page
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200) {
                        this.NewNotices = resp.data.data
                        for (var i=0;i<this.NewNotices.length;i++) {
                            this.NewNotices[i].dates = this.formatDate(new Date(this.NewNotices[i].dates))
                        }

                    }
                }).catch(resp => {
                })
            },

            NewNoticePage() {
                this.$axios.post('public/NewNoticeSum', {
                    types: 0
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200) {
                        this.number = resp.data.data
                        this.NewNotice(this.page)
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
</style>