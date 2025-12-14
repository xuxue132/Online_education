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
                    @click="Add()">试题添加</el-button>
        </div>
        <div style="margin-top: 20px"  class="tab">
            <el-table
                    :data="Exams"
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
                        label="试题号"
                        width="180">
                    <template slot-scope="scope" >
                        <span style="margin-left: 10px" class="iocx">{{ scope.row.id }}</span>
                    </template>
                </el-table-column>
                <el-table-column
                        label="标题"
                        width="180">
                    <template slot-scope="scope" >
                        <span style="margin-left: 10px" class="iocx">{{ scope.row.title }}</span>
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
                        <el-button
                                size="mini"
                                type="success"
                                @click="AddTitleIds(scope.row.id)">添加题目</el-button>
                        <el-button
                                v-if="scope.row.effect === 1"
                                size="mini"
                                type="success"
                                @click="ChangeEffect(scope.row.id)">生效</el-button>
                        <el-button
                                disabled
                                v-if="scope.row.effect === 0"
                                size="mini"
                                type="success"
                                >已生效</el-button>
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
                <span class="icons">试题号</span>
                <el-input size="small" v-model="searchForm1.idPaper" placeholder="请输入试题号"></el-input>
                <el-button style="margin-left: 10px" icon="el-icon-search" circle  size="small" @click="handle1(1)"></el-button>
            </el-space>
          
        </div>
        <div style="margin-top: 20px" class="tab">
            <el-table
                    :data="ExamsTitles"
                    style="width: 100%">
                <el-table-column
                        label="试题号"
                        width="180">
                    <template slot-scope="scope">
                        <span style="margin-left: 10px"  class="iocx">{{ scope.row.idPaper }}</span>
                    </template>
                </el-table-column>
                <el-table-column
                        label="题号"
                        width="180">
                    <template slot-scope="scope">
                        <span style="margin-left: 10px"  class="iocx">{{ scope.row.id }}</span>
                    </template>
                </el-table-column>
                <el-table-column
                        label="题目"
                        width="180">
                    <template slot-scope="scope">
                        <span style="margin-left: 10px"  class="iocx">{{ scope.row.title }}</span>
                    </template>
                </el-table-column>
                <el-table-column
                        label="正确答案"
                        width="180">
                    <template slot-scope="scope" >
                        <span style="margin-left: 10px" class="iocx">{{ scope.row.rightKey }}</span>
                    </template>
                </el-table-column>
               
                <el-table-column label="操作">
                    
                    <template slot-scope="scope">
                        <el-button
                                size="mini"
                                @click="Edits1(scope.row)">编辑</el-button>
                        <el-button
                                size="mini"
                                type="danger"
                                @click="Deleted1(scope.row.id)">删除</el-button>
                        <el-button
                                size="mini"
                                type="success"
                                @click="AddOptions(scope.row.idPaper,scope.row.id)">添加选项</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <div>
            <el-pagination
                    style="margin-left: 40%"
                    @current-change="handleCurrentChange2"
                    background
                    layout="prev, pager, next"
                    :total="this.number1">
            </el-pagination>
        </div>
    
        <div class="NoticeManagement-top">
            <el-space class="NoticeManagement-top-left">
                <span class="icons">题号</span>
                <el-input size="small" v-model="searchForm2.title_id" placeholder="请输入题号"></el-input>
                <el-button style="margin-left: 10px" icon="el-icon-search" circle  size="small" @click="handle2(1)"></el-button>
            </el-space>
    
        </div>
        <div style="margin-top: 20px" class="tab">
            <el-table
                    :data="ExamsQuestions"
                    style="width: 100%">
                <el-table-column
                        label="试题号"
                        width="180">
                    <template slot-scope="scope">
                        <span style="margin-left: 10px"  class="iocx">{{ scope.row.paperId }}</span>
                    </template>
                </el-table-column>
                <el-table-column
                        label="题号"
                        width="180">
                    <template slot-scope="scope" >
                        <span style="margin-left: 10px" class="iocx">{{ scope.row.titleId }}</span>
                    </template>
                </el-table-column>
                <el-table-column
                        label="选项"
                        width="180">
                    <template slot-scope="scope">
                        <span style="margin-left: 10px"  class="iocx">{{ scope.row.optionS }}</span>
                    </template>
                </el-table-column>
                <el-table-column
                        label="选项号"
                        width="180">
                    <template slot-scope="scope">
                        <span style="margin-left: 10px"  class="iocx">{{ scope.row.optionValue }}</span>
                    </template>
                </el-table-column>
               
                <el-table-column label="操作">
                    
                    <template slot-scope="scope">
                        <el-button
                                size="mini"
                                @click="Edits2(scope.row)">编辑</el-button>

                    </template>
                </el-table-column>
            </el-table>
        </div>
        <div>
            <el-pagination
                    style="margin-left: 40%"
                    @current-change="handleCurrentChange3"
                    background
                    layout="prev, pager, next"
                    :total="this.number2">
            </el-pagination>
        </div>
    
        <el-dialog :append-to-body="true" center   title="编辑" :visible.sync="dialogFormVisible">
            <el-form :model="Exam">
                <el-form-item label="题目" :label-width="formLabelWidth" >
                    <el-input  v-model="Exam.title" clearable></el-input>
                </el-form-item>
            </el-form>
            <div  slot="footer" class="dialog-footer">
                <el-button   @click="Closes()">取 消</el-button>
                <el-button type="primary" @click="ChangePaper()">确 定</el-button>
            </div>
        </el-dialog>
        <el-dialog :append-to-body="true" center   title="编辑" :visible.sync="dialogFormVisible1">
            <el-form :model="ExamsTitle"  :rules="rules" ref="ExamsTitle">
                <el-form-item label="题目" :label-width="formLabelWidth" prop="title">
                    <el-input  v-model="ExamsTitle.title" clearable></el-input>
                </el-form-item>
                <el-form-item label="正确答案" :label-width="formLabelWidth" prop="rightKey">
                    <el-select v-model="ExamsTitle.rightKey" placeholder="请选择">
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
                <el-button   @click="Closes1()">取 消</el-button>
                <el-button type="primary" @click="ChangeTitle()">确 定</el-button>
            </div>
        </el-dialog>
        <el-dialog :append-to-body="true" center   title="编辑" :visible.sync="dialogFormVisible2">
            <el-form :model="ExamsQuestion"  :rules="rules2" ref="ExamsQuestion">
                <el-form-item label="选项" :label-width="formLabelWidth" prop="optionS">
                    <el-input  v-model="ExamsQuestion.optionS" clearable></el-input>
                </el-form-item>
            </el-form>
            <div  slot="footer" class="dialog-footer">
                <el-button   @click="Closes2()">取 消</el-button>
                <el-button type="primary" @click="ChangeQuestion()">确 定</el-button>
            </div>
        </el-dialog>
        <el-dialog :append-to-body="true" center @close="Closes3()"  title="添加试题" :visible.sync="dialogFormVisible3">
            <el-form :model="Examed"  :rules="rules2" ref="Examed">
                <el-form-item label="试题标题" :label-width="Examed" prop="title">
                    <el-input  v-model="Examed.title" clearable></el-input>
                </el-form-item>
            </el-form>

            <div  slot="footer" class="dialog-footer">
                <el-button   @click="Closes3()">取 消</el-button>
                <el-button type="primary" @click="AddPaper()">确 定</el-button>
            </div>
        </el-dialog>
        
        <el-dialog :append-to-body="true" center @close="Closes4()"   title="添加试题题目" :visible.sync="dialogFormVisible4">
            <el-form :model="ExamsTitled"  :rules="rules3" ref="ExamsTitled">
                <el-form-item label="试题题目" :label-width="formLabelWidth" prop="title">
                    <el-input  v-model="ExamsTitled.title" clearable></el-input>
                </el-form-item>
                <el-form-item label="正确答案" :label-width="formLabelWidth" prop="rightKey">
                    <el-select v-model="ExamsTitled.rightKey" placeholder="请选择">
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
                <el-button   @click="Closes4()">取 消</el-button>
                <el-button type="primary" @click="AddTitle()">确 定</el-button>
            </div>
        </el-dialog>
        <el-dialog :append-to-body="true" center @close="Closes5()"   title="添加试题选项" :visible.sync="dialogFormVisible5">
            <el-form   :model="ExamsQuestioned" ref="ExamsQuestioned">
                <el-form-item label="选项A" :label-width="formLabelWidth">
                    <el-input  v-model="ExamsQuestioned[0].OptionS" clearable></el-input>
                </el-form-item>

                <el-form-item label="选项B" :label-width="formLabelWidth">
                    <el-input  v-model="ExamsQuestioned[1].OptionS" clearable></el-input>
                </el-form-item>

                <el-form-item label="选项C" :label-width="formLabelWidth">
                    <el-input  v-model="ExamsQuestioned[2].OptionS" clearable></el-input>
                </el-form-item>

                <el-form-item label="选项D" :label-width="formLabelWidth">
                    <el-input  v-model="ExamsQuestioned[3].OptionS" clearable></el-input>
                </el-form-item>

            </el-form>

            <div  slot="footer" class="dialog-footer">
                <el-button   @click="Closes5()">取 消</el-button>
                <el-button type="primary" @click="AddOption()">确 定</el-button>
            </div>
        </el-dialog>

    </div>
</template>

<script>
    export default {
        name: "Examination",
        data(){
            return{
                id:'',
                searchForm:{
                    title:'',
                },
                searchForm1:{
                    idPaper:'',
                },
                searchForm2:{
                    title_id:'',
                },
                options: [{
                    value: 'A',
                    label: 'A'
                }, {
                    value: 'B',
                    label: 'B'
                } ,{
                    value: 'C',
                    label: 'C'
                }, {
                    value: 'D',
                    label: 'D'
                }
                ],
                idPaper:0,
                ids:0,
                AddTitleId:0,
                si1:0,
                si2:0,
                si3:0,
                Exam:{title:''},
                ExamsQuestion:{optionS:''},
                ExamsTitle:{title:'',rightKey:''},
                
                Examed:{title:''},
                ExamsQuestioned:[
                    {OptionS:''},
                    {OptionS:''},
                    {OptionS:''},
                    {OptionS:''},
                ],
                ExamsTitled:{title:'',rightKey:''},
                
                Exams:[],
                ExamsQuestions:[],
                ExamsTitles:[],
                number:0,
                number1:0,
                number2:0,
                page:1,
                page2:1,
                formLabelWidth: '120px',
                dialogFormVisible:false,
                dialogFormVisible1:false,
                dialogFormVisible2:false,
                dialogFormVisible3:false,
                dialogFormVisible4:false,
                dialogFormVisible5:false,
                form:{
                    password:'',
                    NewPassword:'',
                    NewPassword1:''
                },
                checks:[],
                rules: {
                    rightKey: [
                        { required: true, message: '不能为空'},
                    ],
                    title: [
                        { required: true, message: '不能为空'},
                    ],
                },
                rules2: {
                    title: [
                        { required: true, message: '不能为空'},
                    ],
                },
            
                rules3: {
                    rightKey: [
                        { required: true, message: '不能为空'},
                    ],
                        title: [
                        { required: true, message: '不能为空'},
                    ],
                },
            }
        },
        created(){
            this.ExamNumber()
        },
        methods:{
            AddOptions(idPaper,id){
                this.idPaper =idPaper
                this.ids = id
                this.$axios.post('public/PaperOptionsPlus', {
                    title_id: id
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if(resp.data.data.length>0){
                        this.$message({
                            message: "选项已足够，无须添加",
                            type: 'success'
                        });
                    }else {
                        this.dialogFormVisible5 = true
                    }
                }).catch(resp => {
                })
                
            },
            AddTitleIds(i){
                this.dialogFormVisible4=true
                this.AddTitleId = i
            } ,
            AddPaper(){
                this.$refs['Examed'].validate((valid) => {
                    if(valid){
                        this.$axios.post('admin/AddPaper', {
                            title: this.Examed.title
                        }, {
                            headers: {'Authorization': this.$store.state.Authorization}
                        }).then(resp => {
                            if (resp.status === 200) {
                                this.$message({
                                    message: "添加成功",
                                    type: 'success'
                                });
                            }
                            this.$router.go(0)
                        }).catch(resp => {
                        })
                        this.dialogFormVisible1 = false
                    }

                })
              
            },
            AddTitle(){
                this.$refs['ExamsTitled'].validate((valid) => {
                    if(valid){
                        
                        this.$axios.post('admin/AddTitle', {
                            id: this.AddTitleId,
                            title: this.ExamsTitled.title,
                            rightKey: this.ExamsTitled.rightKey
                        }, {
                            headers: {'Authorization': this.$store.state.Authorization}
                        }).then(resp => {
                            if (resp.status === 200) {
                                this.$message({
                                    message: "添加成功",
                                    type: 'success'
                                });
                            }
                            this.$router.go(0)
                        }).catch(resp => {
                        })
                        this.dialogFormVisible1 = false
                    }

                })
              
            },
            AddOption(){
                const OptionValued = ['A','B','C','D']
                    
                        for(let k=0; k<this.ExamsQuestioned.length; k++){
                            this.ExamsQuestioned[k].idPaper = this.idPaper
                            this.ExamsQuestioned[k].ids = this.ids
                            this.ExamsQuestioned[k].OptionValue = OptionValued[k]
                        }
                        for(let p=0; p<this.ExamsQuestioned.length; p++){
                            this.$axios.post('admin/AddOption', {
                                idPaper: this.ExamsQuestioned[p].idPaper,
                                idTitle: this.ExamsQuestioned[p].ids,
                                optionS: this.ExamsQuestioned[p].OptionS,
                                OptionValue: this.ExamsQuestioned[p].OptionValue
                            }, {
                                headers: {'Authorization': this.$store.state.Authorization}
                            }).then(resp => {

                            }).catch(resp => {
                            })
                        }
                            this.$message({
                                message: "添加成功",
                                type: 'success'
                            });
                        this.dialogFormVisible1 = false
                        this.$router.go(0)
            },
            Add(){
                this.dialogFormVisible3 = true
            },
            ExamNumber(){
                this.$axios.post('public/PaperSum', {
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200) {
                        this.number = resp.data.data
                        this.inits(this.page)
                    }
                }).catch(resp => {
                })
                this.$axios.post('admin/AllTitleSum', {
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200) {
                        this.number1 = resp.data.data
                        this.inits1(this.page)
                    }
                }).catch(resp => {
                })
                this.$axios.post('admin/AllQuestionSum', {
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200) {
                        this.number2 = resp.data.data
                        this.inits2(this.page)
                    }
                }).catch(resp => {
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
            handleCurrentChange2(i){
                if(this.si2 === 1){
                    // this.page2 = i
                    this.handle1(i)
                }else {
                    this.inits1(i)
                }
            },
            handleCurrentChange3(i){
                    this.inits2(i)
            },

            handle(page){
                this.si1 = 1
                this.Exams=[]
                if(this.searchForm.title === ''){
                    this.$router.go(0)
                }
                this.$axios.post('admin/selectPaper', {
                    ...this.searchForm,
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    this.number = resp.data.data.length
                    if((page-1)*10+10>resp.data.data.length){
                        for (var i=(page-1)*10;i<resp.data.data.length;i++) {
                            this.Exams.push(resp.data.data[i])
                        }
                    }else {
                        for (var i=(page-1)*10;i<(page-1)*10+10;i++) {
                            this.Exams.push(resp.data.data[i])
                        }
                    }
                    for (var i=0;i<this.Exams.length;i++) {
                        this.Exams[i].dates = this.formatDate(new Date(this.Exams[i].dates))
                    }
                }).catch(resp => {
                })

            },
            
            handle1(page){
                this.si2 = 1
                this.ExamsTitles=[]
                if(this.searchForm1.idPaper === '' ){
                    this.$router.go(0)
                }
                this.$axios.post('admin/selectTitle', {
                    ...this.searchForm1,
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    this.number1 = resp.data.data.length
                    if((page-1)*10+10>resp.data.data.length){
                        for (var i=(page-1)*10;i<resp.data.data.length;i++) {
                            this.ExamsTitles.push(resp.data.data[i])
                        }
                    }else {
                        for (var i=(page-1)*10;i<(page-1)*10+10;i++) {
                            this.ExamsTitles.push(resp.data.data[i])
                        }
                    }
                   
                }).catch(resp => {
                })

            },
            
            handle2(page){
                this.si3 = 1
                this.ExamsQuestions=[]
                if(this.searchForm2.title_id === '' ){
                    this.$router.go(0)
                }
            this.$axios.post('admin/selectQuestions', {
                    ...this.searchForm2,
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    this.number2 = resp.data.data.length
                    if((page-1)*10+10>resp.data.data.length){
                        for (var i=(page-1)*10;i<resp.data.data.length;i++) {
                            this.ExamsQuestions.push(resp.data.data[i])
                        }
                    }else {
                        for (var i=(page-1)*10;i<(page-1)*10+10;i++) {
                            this.ExamsQuestions.push(resp.data.data[i])
                        }
                    }
                   
                }).catch(resp => {
                })

            },
            
            
            closed() {
                this.$refs["form"].resetFields();
            },
            Closes(){
                this.dialogFormVisible = false
            },
            Closes1(){
                this.dialogFormVisible1 = false
            },
            Closes2(){
                this.dialogFormVisible2 = false
            },
            Closes3(){
                this.$refs["Examed"].resetFields();
                this.dialogFormVisible3 = false
            },
            Closes4(){
                this.$refs["ExamsTitled"].resetFields();
                this.dialogFormVisible4 = false
            },
            Closes5(){
                this.$refs["ExamsQuestioned"].resetFields();
                this.dialogFormVisible5 = false
            },
            Edits(i){
                this.Exam = i
                this.dialogFormVisible = true
            },
            Edits1(i){
                this.ExamsTitle = i
                this.dialogFormVisible1 = true
            },
            Edits2(i){
                this.ExamsQuestion = i
                this.dialogFormVisible2 = true
            },
            ChangePaper(){
                this.$axios.post('admin/UpdatePaper', {
                    title: this.Exam.title,
                    id: this.Exam.id
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
            },
            ChangeTitle(){
                this.$refs['ExamsTitle'].validate((valid) => {
                    if(valid){
                        this.$axios.post('admin/UpdateTitle', {
                            title: this.ExamsTitle.title,
                            rightKey: this.ExamsTitle.rightKey,
                            id: this.ExamsTitle.id
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
                        this.dialogFormVisible1 = false
                    }
                    
                })
                
            },
            ChangeQuestion(){
                this.$refs['ExamsQuestion'].validate((valid) => {
                    if(valid){
                        this.$axios.post('admin/UpdateQuestion', {
                            optionS: this.ExamsQuestion.optionS,
                            id: this.ExamsQuestion.id
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
                        this.dialogFormVisible2 = false
                    }
                    
                    
                })
                
            },

            inits(page){
                this.$axios.post('public/AllPaper', {
                    NowPage: page
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200) {
                        this.Exams = resp.data.data
                        for (var i=0;i<this.Exams.length;i++) {
                            this.Exams[i].dates = this.formatDate(new Date(this.Exams[i].dates))
                        }
                    }
                }).catch(resp => {
                })
            },
            inits1(page){
                this.$axios.post('admin/AllTitle', {
                    NowPage: page
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200) {
                        this.ExamsTitles = resp.data.data
                    }
                }).catch(resp => {
                })
            },
            inits2(page){
                this.$axios.post('admin/AllQuestion', {
                    NowPage: page
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200) {
                        this.ExamsQuestions = resp.data.data
                     
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

                    this.$axios.post('admin/DeletePaper', {
                        id: id
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
                    this.$axios.post('admin/DeleteTiTle', {
                        id: id
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
            },

            ChangeEffect(id){
                    this.$axios.post('admin/ChangeEffect', {
                        id: id
                    }, {
                        headers: {'Authorization': this.$store.state.Authorization}
                    }).then(resp => {
                        this.$message({
                            message: '生效成功',
                            type: 'success'
                        });
                        this.$router.go(0)
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