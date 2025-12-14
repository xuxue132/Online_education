<template>
    <div class="main_body">
        <div class="head_top">
                <div class="head_top_next">
                    <img src="http://eucation.i8329.cn/learning.jpg">
                    <div class="top_right">
                        <div class="right_one" style="align-items: center;">
                            <a href="/MyInformation" class="userMobileName">{{this.user.username}}</a>
                            <a  style="float: left;font-size: 20px;color: #868686;margin-top: 1px">你好  |</a>
                        </div>
                        <div class="right_one">
                            <el-link :underline="false" class="outThisUser" @click="LoginOut()">退出</el-link>
                        </div>
                        
                        <div class="right_three">
                            <el-link :underline="false" class="outThisUser" @click="HomePage()">首页</el-link>
                        </div>
                    </div>
                </div>
        </div>
        
        <div class="head_min">
            <div class="head_min_left">
                <img  @click="dialogFormVisible = true" :src="this.user.picture" alt="">
            </div>
            <div class="head_min_right">
                    <div>{{this.user.username}}  的个人中心</div>
                    <a>当前积分 {{this.user.integral}}</a>
            </div>
        </div>
        <div class="tab_text deleteBorder" >
                <el-tabs  class="deleteBorder" v-model="activeName" >
                    <el-tab-pane label="个人信息" name="first" >
                    <MyInf></MyInf>
                    
                    </el-tab-pane>
                    <el-tab-pane label="兑换记录" name="second">
                    <MyPrize></MyPrize>
                    </el-tab-pane>
    
                </el-tabs>
        </div>
        <el-dialog width="700px"  title=":设置头像" :visible.sync="dialogFormVisible" center>
            <el-upload
                    width="200px"
                    style="margin-left: 130px;height: 200px;"
                    action="http://localhost:8700/public/upload"
                    :headers="{'Authorization': this.$store.state.Authorization}"
                    list-type="picture-card"
                    :auto-upload="false"
                    :limit="1"
                    :data = "{telephone:this.user.telephone}"
                    ref="upload"
                    drag
            >
                <i slot="default" class="el-icon-plus" ></i>
                <div slot="file" slot-scope="{file}">
                        <img
                                
                                class="el-upload-list__item-thumbnail"
                                :src="file.url" alt=""
                        >
                        <span class="el-upload-list__item-actions">

                            <span
                                    v-if="!disabled"
                                    class="el-upload-list__item-delete"
                                    @click="handleRemove(file)"
                            >
                              <i class="el-icon-delete"></i>
                            </span>
                     </span>
                </div>
                
            </el-upload>
            <el-dialog :visible.sync="dialogVisible">
                <img width="100%" :src="dialogImageUrl" alt="">
            </el-dialog>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false;this.$refs.upload.clearFiles()">取 消</el-button>
                <el-button type="primary" @click="submitUpload()">确 定</el-button>
            </div>
           
        </el-dialog>
    
    </div>
</template>

<script>
    import MyInf from "@/components/MainBody/MyInf";
    import MyPrize from "@/components/MainBody/MyPrize";
    export default {
        name: "MyInformation",
        components: {MyPrize, MyInf},
        data() {
            return {
                dialogFormVisible: false,
                dialogImageUrl: '',
                dialogVisible: false,
                disabled: false,
                imageUrl: '',
                activeName: 'first',
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
                    integral: "",
                    picture:"",
                },
            };
        },
        created(){
          this.getUser()
        },
        methods: {
            handleRemove(file) {
                this.$refs.upload.clearFiles()//清空上传列表
            },
            submitUpload() {
                this.$refs.upload.submit();
                this.$refs.upload.clearFiles()//清空上传列表
                this.dialogFormVisible = false
                this.$router.go(0);
            },
    
            LoginOut() {
                this.$router.push("/login");
            },
            HomePage(){
                this.$router.push("/FirstPage")
            },
            getUser() {
                this.$axios.post('public/MyInformation', {
                    token: this.$store.state.Authorization
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200) {
                        this.user = resp.data.data
                    }
                }).catch(resp => {
                })
            },
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
    .head_top{
        background: #fff;
        box-shadow: 0 3px 0 0 #dfdfdf;
        margin: 10px 0;
        padding: 0;
        width: 100%;
        height: 105px;
    }
    .head_top_next{
        width: 80%;
        margin: 0 auto;
        height: 95px;
        justify-content: space-between;
        align-items: center;
        
    }
    .head_top_next img{
        float: left;
        max-height: 100%;
        max-width: 100%;
        display: block;
        margin: auto;
    }
    
    .top_right{
        align-items: center;
        height: 75px;
        line-height: 95px;
        color: #868686;
        font-size: 22px;
        float: right;
        margin-right: 78px;
    }
    .right_one{
        margin-right: 11px;
        font-size: 22px;
        float: left;
        color: #666666;
    }
    .right_one a{
        margin-left: 11px;
        color: #666666;
    }
    .userMobileName{
        font-size: 22px;
        float: left;
        color: #666666;
        
        
    }
    .outThisUser{
        font-size: 22px;
        color: #666666;
    }
    .right_three{
        height: 75px;
        font-size: 22px;
        line-height: 95px;
        float: left;
        color: #666666;
    }
    .right_three a{
        color: #666666;
        line-height: 95px;
    }
    a span{
        display: inline-block
    }
    .head_min{

        width: 100%;
        height: 120px;
        margin-top: 50px;
    }
    .head_min_left{
        width: 120px;
        height: 100%;
        float: left;
        margin-left: 20%;
    }
    .head_min_left img{
        width: 108px;
        height: 108px;
        border-radius: 50%;
    }
    .head_min_right{
        display: inline;
        float: left;
        margin-top: 11px;
        margin-left: 20px;
        line-height: 40px;
    }
    .head_min_right div{
        font-size: 20px;
        color: #000000;
    }
    .deleteBorder .el-tabs__nav-wrap::after{
        height: 0 !important;
    }
    .tab_text{
        font-size: 22px !important;
        width: 60%;
        height: 50px;
        margin: 18px auto;
    }
    /*.avatar-uploader .el-upload {*/
    /*    border: 1px dashed #d9d9d9;*/
    /*    border-radius: 6px;*/
    /*    cursor: pointer;*/
    /*    position: relative;*/
    /*    overflow: hidden;*/
    /*}*/
    /*.avatar-uploader .el-upload:hover {*/
    /*    border-color: #409EFF;*/
    /*}*/

 

</style>