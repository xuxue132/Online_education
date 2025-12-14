<template>
    <div class="main_body">
        <div id="NoticeManagement-top">
            <el-space id="NoticeManagement-top-left">
                <span class="icons">商品名</span>
                <el-input size="small" v-model="searchForm.name" placeholder="请输入标题名"></el-input>
                <el-button style="margin-left: 10px" icon="el-icon-search" circle  size="small" @click="handle(1)"></el-button>
            </el-space>
    
            <el-button
                    style="margin-top: 20px"
                    type="success"
                    @click="Add()">添加</el-button>
        </div>
        
        <div class="tab">
            <el-table
                    :data="commodity"
                    style="width: 100%">

                <el-table-column
                        label="商品号"
                        width="180">
                    <template slot-scope="scope">
                        <span style="margin-left: 10px"  class="iocx">{{ scope.row.id }}</span>
                    </template>
                </el-table-column>
                <el-table-column
                        label="商品名"
                        width="180">
                    <template slot-scope="scope">
                        <span style="margin-left: 10px"  class="iocx">{{ scope.row.tradeName }}</span>
                    </template>
                </el-table-column>
                <el-table-column
                        label="积分"
                        width="180">
                    <template slot-scope="scope">
                        <span style="margin-left: 10px"  class="iocx">{{ scope.row.points }}</span>
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
        
        <div class="tab">
            <el-table
                    :data="OrderForm"
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
                        label="商品号"
                        width="180">
                    <template slot-scope="scope">
                        <span style="margin-left: 10px"  class="iocx">{{ scope.row.commodityId }}</span>
                    </template>
                </el-table-column>
                <el-table-column
                        label="联系人名"
                        width="180">
                    <template slot-scope="scope">
                        <span style="margin-left: 10px"  class="iocx">{{ scope.row.userName }}</span>
                    </template>
                </el-table-column>
                <el-table-column
                        label="联系电话"
                        width="180">
                    <template slot-scope="scope">
                        <span style="margin-left: 10px"  class="iocx">{{ scope.row.telephone }}</span>
                    </template>
                </el-table-column>
                <el-table-column
                        label="绑定电话"
                        width="180">
                    <template slot-scope="scope">
                        <span style="margin-left: 10px"  class="iocx">{{ scope.row.phone }}</span>
                    </template>
                </el-table-column>
                <el-table-column
                        label="收货地址"
                        width="180">
                    <template slot-scope="scope">
                        <span style="margin-left: 10px"  class="iocx">{{ scope.row.address }}</span>
                    </template>
                </el-table-column>
                <el-table-column
                        label="备注"
                        width="180">
                    <template slot-scope="scope">
                        <span style="margin-left: 10px"  class="iocx">{{ scope.row.remark }}</span>
                    </template>
                </el-table-column>
                <el-table-column label="操作">
                    
                    <template slot-scope="scope">
                        <el-button
                                size="mini"
                                type="danger"
                                v-if="scope.row.states === 0"
                                @click="UpdateStatus(scope.row.id)">发货</el-button>
                        <el-button
                                size="mini"
                                type="success"
                                v-if="scope.row.states === 1"
                                disabled>已发货</el-button>
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
                    :total="this.number1">
            </el-pagination>
        </div>
    
        
        
        
        
        <el-dialog :append-to-body="true" center @close="Closes()"  title="编辑" :visible.sync="dialogFormVisible">
            <el-form :model="commodityed" :rules="rules" ref="commodityed">
                <el-form-item label="商品名" :label-width="formLabelWidth" prop="tradeName" >
                    <el-input v-model="commodityed.tradeName" clearable></el-input>
                </el-form-item>
                <el-form-item label="商品积分" :label-width="formLabelWidth"  prop="points">
                    <el-input v-model="commodityed.points" clearable></el-input>
                </el-form-item>
                
                <el-form-item label="商品介绍" :label-width="formLabelWidth" prop="introduce">
                    <vue-editor v-model="commodityed.introduce"></vue-editor>
                </el-form-item>
                <el-form-item label="封面图片" :label-width="formLabelWidth" prop="picture">
                    <el-upload
                            class="avatar-uploader"
                            action="http://localhost:8700/admin/CommodityUpload"
                            :headers="{'Authorization': this.$store.state.Authorization}"
                            :data = "{id: commodityed.id}"
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
            <el-form :model="commoditys" :rules="rules2" ref="commoditys">
                <el-form-item label="商品名" :label-width="formLabelWidth" prop="tradeName" >
                    <el-input v-model="commoditys.tradeName" clearable></el-input>
                </el-form-item>
                <el-form-item label="商品积分" :label-width="formLabelWidth"  prop="points">
                    <el-input v-model="commoditys.points" clearable></el-input>
                </el-form-item>
            
                <el-form-item label="商品介绍" :label-width="formLabelWidth" prop="introduce">
                    <vue-editor v-model="commoditys.introduce"></vue-editor>
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
        name: "prize",
        components: {
            VueEditor
        },
        data(){
            return{
                id:'',
                searchForm:{
                    name:''
                },
                si:0,
                imageUrl:'',
                commodity:[],
                commodityed:{},
                commoditys:{},
                OrderForm:[],
                number:0,
                number1:0,
                page:1,
                page2:1,
                formLabelWidth: '120px',
                dialogFormVisible:false,
                dialogFormVisible2:false,
                rules: {
                    tradeName: [
                        { required: true, message: '不能为空'},
                    ],
                    points: [
                        {  pattern: /^-?\d+\.?\d*$/, message: '请输入正确数据', trigger: 'blur'  },
                    ],
                    introduce: [
                        { required: true, message: '不能为空'},
                    ],
                    picture: [
                        { required: true, message: '不能为空'},
                    ],
                },
                rules2: {
                    tradeName: [
                        { required: true, message: '不能为空'},
                    ],
                    points: [
                        {  pattern: /^-?\d+\.?\d*$/, message: '请输入正确数据', trigger: 'blur'  },
                    ],
                    introduce: [
                        { required: true, message: '不能为空'},
                    ],
                }
            }
        },
        created(){
            this.CommodityNumber()
            this.OrderFormNumber()
        },
        methods:{
            OrderFormNumber(){
                this.$axios.post('admin/OrderSum', {
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200) {
                        this.number1 = resp.data.data
                        this.OrderForms(this.page)
                    }
                }).catch(resp => {
                })
            },
            OrderForms(i){
                this.$axios.post('admin/SelectAll', {
                    NowPage:i
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200) {
                        this.OrderForm = resp.data.data
                        for (var i=0;i<this.OrderForm.length;i++) {
                            this.OrderForm[i].dates = this.formatDate(new Date(this.OrderForm[i].dates))
                        }
                    }
                }).catch(resp => {
                })
            },
            UpdateStatus(id){
                this.$axios.post('admin/updateStates', {
                    id:id,
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200) {
                        this.$message({
                            message: "成功",
                            type: 'success'
                        });
                        this.$router.go(0)
                    }
                }).catch(resp => {
                })
            },
            
            Posts(){
                
                this.$refs['commoditys'].validate((valid) => {
                   if(valid){
                       this.$axios.post('admin/AddCommodity', {
                           tradeName: this.commoditys.tradeName,
                           points: this.commoditys.points,
                           introduce: this.commoditys.introduce,
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
                this.$axios.post('public/AnGoods', {
                    id: this.commodityed.id,
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
            CommodityNumber(){
                this.$axios.post('public/GoodsNumber', {
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
                this.commodity=[]
                if(this.searchForm.name===''){
                    this.$router.go(0)
                }
                this.$axios.post('admin/selectCommodity', {
                    ...this.searchForm,
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    this.number = resp.data.data.length
                    if((page-1)*10+10>resp.data.data.length){
                        for (var i=(page-1)*10;i<resp.data.data.length;i++) {
                            this.commodity.push(resp.data.data[i])
                        }
                    }else {
                        for (var i=(page-1)*10;i<(page-1)*10+10;i++) {
                            this.commodity.push(resp.data.data[i])
                        }
                    }
                }).catch(resp => {
                })

            },
            closed() {
                this.$refs["commoditys"].resetFields();
                this.dialogFormVisible2 = false
            },
            Closes(){
                this.$refs["commodityed"].resetFields();
                this.dialogFormVisible = false
            },
            Edits(i){
                this.dialogFormVisible = true
                this.imageUrl = i.picture
                this.commodityed = i
                // this.id = i.id;
            },
            Change(){
                this.$refs['commodityed'].validate((valid) => {
                    if (valid) {
                        this.$axios.post('admin/ChangeCommodity', {
                            ...this.commodityed,
                            id: this.commodityed.id
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
                this.$axios.post('public/Goods', {
                    NowPage: page
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200) {
                        this.commodity = resp.data.data
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
                    this.$axios.post('admin/DeleteCommodity', {
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