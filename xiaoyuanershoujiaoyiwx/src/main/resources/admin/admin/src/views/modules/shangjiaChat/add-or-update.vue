<template>
    <div class="addEdit-block">
        <el-form class="detail-form-content" ref="ruleForm" :model="ruleForm" :rules="rules" label-width="auto">
            <el-row>
                <el-col :span="12" v-if="sessionTable != 'yonghu'">
                    <el-form-item class="select" v-if="type!='info'" label="提问人(用户)" prop="yonghuId">
                        <el-select v-model="ruleForm.yonghuId" :disabled="ro.yonghuId" filterable placeholder="请选择用户" @change="yonghuChange">
                            <el-option v-for="(item,index) in yonghuOptions" :key="item.id" :label="item.yonghuName" :value="item.id"></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="12" v-if="sessionTable != 'yonghu'">
                    <el-form-item class="input" v-if="type!='info'" label="用户姓名" prop="yonghuName">
                        <el-input v-model="yonghuForm.yonghuName" placeholder="用户姓名" clearable readonly></el-input>
                    </el-form-item>
                    <el-form-item v-else-if="type=='info'" label="用户姓名" prop="yonghuName">
                        <el-input v-model="ruleForm.yonghuName" placeholder="用户姓名" readonly></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12" v-if="sessionTable != 'shangjia'">
                    <el-form-item class="select" v-if="type!='info'" label="回答人(商家)" prop="shangjiaId">
                        <el-select v-model="ruleForm.shangjiaId" :disabled="ro.shangjiaId" filterable placeholder="请选择商家" @change="shangjiaChange">
                            <el-option v-for="(item,index) in shangjiaOptions" :key="item.id" :label="item.shangjiaName" :value="item.id"></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="12" v-if="sessionTable != 'shangjia'">
                    <el-form-item class="input" v-if="type!='info'" label="商家名称" prop="shangjiaName">
                        <el-input v-model="shangjiaForm.shangjiaName" placeholder="商家名称" clearable readonly></el-input>
                    </el-form-item>
                    <el-form-item v-else-if="type=='info'" label="商家名称" prop="shangjiaName">
                        <el-input v-model="ruleForm.shangjiaName" placeholder="商家名称" readonly></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="24">
                    <el-form-item v-if="type!='info'" label="问题内容" prop="shangjiaChatIssueText">
                        <el-input type="textarea" :readonly="ro.shangjiaChatIssueText" v-model="ruleForm.shangjiaChatIssueText" placeholder="问题内容"></el-input>
                    </el-form-item>
                    <el-form-item v-else-if="type=='info'" label="问题内容" prop="shangjiaChatIssueText">
                        <span v-html="ruleForm.shangjiaChatIssueText || '-'"></span>
                    </el-form-item>
                </el-col>
                <el-col :span="24">
                    <el-form-item v-if="type!='info'" label="回复内容" prop="shangjiaChatReplyText">
                        <el-input type="textarea" :readonly="ro.shangjiaChatReplyText" v-model="ruleForm.shangjiaChatReplyText" placeholder="回复内容(商家回复)"></el-input>
                    </el-form-item>
                    <el-form-item v-else-if="type=='info'" label="回复内容" prop="shangjiaChatReplyText">
                        <span v-html="ruleForm.shangjiaChatReplyText || '-'"></span>
                    </el-form-item>
                </el-col>
                <el-col :span="12" v-if="type!='info' && zhuangtaiTypesOptions.length">
                    <el-form-item label="状态" prop="zhuangtaiTypes">
                        <el-select v-model="ruleForm.zhuangtaiTypes" placeholder="请选择状态">
                            <el-option v-for="(item,index) in zhuangtaiTypesOptions" :key="item.codeIndex" :label="item.indexName" :value="item.codeIndex"></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="12" v-if="type=='info' && ruleForm.zhuangtaiValue">
                    <el-form-item label="状态" prop="zhuangtaiValue">
                        <el-input v-model="ruleForm.zhuangtaiValue" readonly></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12" v-if="type=='info'">
                    <el-form-item label="提问时间" prop="issueTime">
                        <el-input v-model="ruleForm.issueTime" readonly></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12" v-if="type=='info'">
                    <el-form-item label="回复时间" prop="replyTime">
                        <el-input v-model="ruleForm.replyTime" readonly></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-form-item class="btn">
                <el-button v-if="type!='info'" type="primary" class="btn-success" @click="onSubmit">提交</el-button>
                <el-button v-if="type!='info'" class="btn-close" @click="back()">取消</el-button>
                <el-button v-if="type=='info'" class="btn-close" @click="back()">返回</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>
<script>
    import styleJs from "../../../utils/style.js";

    export default {
        data() {
            return {
                id: '',
                type: '',
                sessionTable: "",
                role: "",
                userId: "",
                yonghuForm: {},
                shangjiaForm: {},
                ro: {
                    yonghuId: false,
                    shangjiaId: false,
                    shangjiaChatIssueText: false,
                    shangjiaChatReplyText: false,
                },
                ruleForm: {
                    yonghuId: '',
                    shangjiaId: '',
                    shangjiaChatIssueText: '',
                    shangjiaChatReplyText: '',
                    issueTime: '',
                    replyTime: '',
                    zhuangtaiTypes: null,
                },
                yonghuOptions: [],
                shangjiaOptions: [],
                zhuangtaiTypesOptions: [],
                rules: {
                    yonghuId: [{ required: true, message: '请选择用户', trigger: 'blur' }],
                    shangjiaId: [{ required: true, message: '请选择商家', trigger: 'blur' }],
                    shangjiaChatIssueText: [{ required: true, message: '问题内容不能为空', trigger: 'blur' }],
                }
            };
        },
        props: ["parent"],
        created() {
            this.sessionTable = this.$storage.get("sessionTable");
            this.role = this.$storage.get("role");
            this.userId = this.$storage.get("userId");
            if (this.role != "管理员") {
                this.ro.yonghuId = true;
                this.ro.shangjiaId = true;
                this.ro.shangjiaChatIssueText = true;
            }
            this.$http({ url: `yonghu/page?page=1&limit=100`, method: "get" }).then(({ data }) => {
                if (data && data.code === 0) this.yonghuOptions = data.data.list;
            });
            this.$http({ url: `shangjia/page?page=1&limit=100`, method: "get" }).then(({ data }) => {
                if (data && data.code === 0) this.shangjiaOptions = data.data.list;
            });
            this.$http({ url: `dictionary/page?dicCode=zhuangtai_types&page=1&limit=100`, method: "get" }).then(({ data }) => {
                if (data && data.code === 0) this.zhuangtaiTypesOptions = data.data.list;
            });
        },
        mounted() {},
        methods: {
            init(id, type) {
                if (id) { this.id = id; this.type = type; }
                if (this.type == 'info' || this.type == 'else') this.info(id);
            },
            yonghuChange(id) {
                this.$http({ url: `yonghu/info/` + id, method: "get" }).then(({ data }) => {
                    if (data && data.code === 0) this.yonghuForm = data.data;
                });
            },
            shangjiaChange(id) {
                this.$http({ url: `shangjia/info/` + id, method: "get" }).then(({ data }) => {
                    if (data && data.code === 0) this.shangjiaForm = data.data;
                });
            },
            info(id) {
                let _this = this;
                _this.$http({ url: `shangjiaChat/info/${id}`, method: 'get' }).then(({ data }) => {
                    if (data && data.code === 0) {
                        _this.ruleForm = data.data;
                        _this.yonghuChange(data.data.yonghuId);
                        _this.shangjiaChange(data.data.shangjiaId);
                    } else {
                        _this.$message.error(data.msg);
                    }
                });
            },
            onSubmit() {
                this.$refs["ruleForm"].validate(valid => {
                    if (valid) {
                        this.$http({
                            url: `shangjiaChat/${!this.ruleForm.id ? "save" : "update"}`,
                            method: "post",
                            data: this.ruleForm
                        }).then(({ data }) => {
                            if (data && data.code === 0) {
                                this.$message({ message: "操作成功", type: "success", duration: 1500, onClose: () => {
                                    this.parent.showFlag = true;
                                    this.parent.addOrUpdateFlag = false;
                                    this.parent.search();
                                    this.parent.contentStyleChange();
                                }});
                            } else {
                                this.$message.error(data.msg);
                            }
                        });
                    }
                });
            },
            back() {
                this.parent.showFlag = true;
                this.parent.addOrUpdateFlag = false;
                this.parent.contentStyleChange();
            },
        }
    };
</script>
<style lang="scss" scoped>
.addEdit-block { margin: -10px; }
.detail-form-content { padding: 12px; background-color: transparent; }
.btn .el-button { padding: 0; }
</style>
