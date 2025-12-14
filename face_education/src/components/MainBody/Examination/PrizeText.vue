<template>
    <div class="main_body">
        <HeadTitle></HeadTitle>
        <div class="head_picture"></div>
        <div class="content row">
            <div class="sub_left">
                <h2 class="title_left flag1">有奖竞答</h2>
                <ul class="flag2 sub_menu">
                    <li>
                        <a  title="知识竞答" href="/ExaminationPage" >知识竞答</a>
                    </li>
                    
                    <li>
                        <a  title="奖品兑换" href="/PrizePage" class="chs">奖品兑换</a>
                    </li>
                
                </ul>
            </div>
            <div class="sub_right">
                <div class="all">
                    <div class="changeTitle">
                        <span>商品信息</span>
                    </div>
                    
                    <div class="aleft">
                        <div class="good_right">
                            <img :src="Good.picture">
                        </div>
                        <div class="good_left">
                            <h1>{{Good.tradeName}}</h1>
                            <p class="next"><span>所需积分：</span><i>{{Good.points}}</i></p>
                            <p><span>商品介绍：</span><span  class="ql-editor" v-html="Good.introduce"></span></p>
    
                            <el-button type="primary" round @click="dialogFormVisible = true" class="change">立即兑换</el-button>
                        </div>
    
                        <el-dialog title="奖品兑换" :visible.sync="dialogFormVisible" @close="closed" :append-to-body="true">
                            <el-form :model="Order" :rules="rules" ref="Order">
                                <el-form-item label="联系姓名" :label-width="formLabelWidth" prop="userName">
                                    <el-input v-model="Order.userName" clearable></el-input>
                                </el-form-item>
                                <el-form-item label="手机号码" :label-width="formLabelWidth" prop="phone">
                                    <el-input v-model="Order.phone" clearable></el-input>
                                </el-form-item>
                                <el-form-item label="收件地址" :label-width="formLabelWidth" prop="address">
                                    <el-input v-model="Order.address" clearable></el-input>
                                </el-form-item>
                                <el-form-item label="备注" :label-width="formLabelWidth" prop="remark">
                                    <el-input v-model="Order.remark" clearable></el-input>
                                </el-form-item>
                            </el-form>
                            <div slot="footer" class="dialog-footer">
                                <el-button @click="offIt()">取 消</el-button>
                                <el-button type="primary" @click="submitForm()">确 定</el-button>
                            </div>
                        </el-dialog>
                    </div>
                </div>
                <div class="bottoms"></div>
            
            </div>
        </div>
    </div>
</template>

<script>
    import HeadTitle from "@/components/MainBody/HeadTitle";
    export default {
        name: "PrizeText",
        components: {HeadTitle},
        data(){
            return{
                Good:{},
                dialogFormVisible: false,
                Order: {
                    userName: '',
                    phone: '',
                    address: '',
                    remark: '',
                },
                people:{},
                formLabelWidth: '120px',
                rules: {
                    userName: [
                        { required: true, message: '联系人不能为空'},
                    ],
                    address: [
                        { required: true, message: '联系地址不能为空'},
                    ],
                    phone: [
                        { required: true, message: '手机不能为空'},
                        {
                            validator: (rule, value, callback) => {
                                const reg = /^1[(34578)][0-9]\d{8}$/;
                                if (reg.test(value)) {
                                    callback();
                                } else {
                                    return callback(new Error("手机号码格式错误"));
                                }
                            },
                        }
                    ],
                }
            }
        },
        created(){
            this.GetGood();
            this.GetUser();
        },

        methods: {
            GetUser(){
                this.$axios.post('public/MyInformation', {
                    token: this.$store.state.Authorization,
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200) {
                        this.people = resp.data.data
                    }
                }).catch(resp => {
                })
            },
            closed() {
                this.$refs["Order"].resetFields();
            },
            offIt(){
                this.$refs['Order'].resetFields();
                this.dialogFormVisible = false
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
            GetGood(){
                this.$axios.post('public/AnGoods', {
                    id: this.$route.query.id,
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200) {
                        this.Good = resp.data.data
                    }
                }).catch(resp => {
                })
            },
            submitForm() {
                this.$refs['Order'].validate((valid) => {
                    if (valid) {
                        this.dialogFormVisible = false
                        if(this.people.integral >= this.Good.points){
                            this.$axios.post('public/GoodsAdd', {
                                userName: this.Order.userName,
                                commodityId: this.Good.id,
                                phone: this.Order.phone,
                                address: this.Order.address,
                                remark: this.Order.remark,
                                dates: this.formatDate(new Date()),
                                points: this.Good.points,
                                token: this.$store.state.Authorization
                            }, {
                                headers: {'Authorization': this.$store.state.Authorization}
                            }).then(resp => {
                                if (resp.status === 200) {
                                    this.$message({
                                        message: "兑换成功",
                                        type: 'success'
                                    });
                                }
                            }).catch(resp => {
                            })
                        }else {
                            this.$message({
                                message: "积分不足",
                                type: 'success'
                            });
                        }
                        
                        
                    }
                });
            },

        }
    }
</script>

<style scoped>
    @import '../../editor.css';
    .main_body{
        margin: 0;
        padding: 0;
        height: 100%;
        width: 100%;
    }
    .head_picture{
        height: 250px;
        width: 100%;
        background: url("http://rcqmzsxxw.com/rchema/xuexiwang/img/public/head_bg.png");
        /*background: url("https://api.ixiaowai.cn/gqapi/gqapi.php");*/
        background-size: cover;
    }
    .changeTitle {
        width: 80%;
        height: 36px;
        line-height: 36px;
        padding: 0 18px;
        background: #f5f5f5;
        color: #0d74b1;
        font-size: 15px;

    }
    .changeTitle a {
        color: #0d74b1;
        font-size: 15px;
        float: right;
    }
    .sub_left {
        width: 230px;
        float: left;
        position: relative;
        z-index: 3;
        margin-top: -80px;
        background: url("https://www.cqut.edu.cn/images/sub_left_bg.png") top center no-repeat;
        padding: 0 0 65px;
        background-size: 100% auto;
    }
    .title_left {
        line-height: 50px;
        padding: 15px 10px 15px 40px;
        color: #fff;
        position: relative;
        font-weight: normal;
    }
    .flag1 {
        font-size: 30px;
    }
    .sub_menu {
        margin: 0 0 0 20px;
        background: #fdf4e1;
        height: 199px;
    }
    .content {
        position: relative;
        z-index: 2;
    }

    .row {
        width: 1500px;
        margin: 0 auto;
    }
    .flag2 {
        font-size: 18px;
    }
    .sub_menu > li > a {
        display: block;
        line-height: 36px;
        color: #333;
        padding: 17px 0 16px 30px;
        position: relative;
        border: 1px solid #ededed;
        border-top: 0;
    }
    .sub_right {
        margin: 0 0 0 230px;
        background: #FFFFFF;
    }

    .page_title h3 {
        padding: 25px 0;
        color: #333;
        text-align: center;
    }

    .chs{
        background: #f0ad4e;
        color: #fff;
        border-color: #F0AD4E;
    }

    .bottoms{
        height: 20px;
    }

    .card_img  img{
        height: 80%;
        width: 80%;
        padding: 20px 20px;
    }

    .card_list_li span{
        font-size: 18px;
        font-weight: bold;
        color: #363636;
        margin-top: 12px;
    }
    .all{
        margin: 30px 30px;
    }
    .good_right{
        float: left;
        width: 240px;
        height: 240px;
    }
    .good_right img{
        width: 100%;
        height: 100%;
    }
    .good_left{
        float: left;
        margin-left: 30px;
        width: 60%;
        height: 60%;
    }
    .good_left h1{
        margin-top: 10px;
    }
    .aleft{
        margin-top: 20px;
    }
    .next{
        margin-top: 30px;
        font-size: 19px;
    }
    .good_left p {
        font-size: 19px;
        color: #565656;
        line-height: 30px;
    }
    
    .change{
        margin-top: 10px;
    }
</style>