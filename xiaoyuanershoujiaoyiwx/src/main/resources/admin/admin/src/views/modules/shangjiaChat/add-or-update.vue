<template>
    <div class="addEdit-block">
        <el-form class="detail-form-content" ref="ruleForm" :model="ruleForm" :rules="rules" label-width="auto">
            <!-- 详情：不依赖 sessionTable，管理员登录也能完整展示接口返回数据 -->
            <el-row v-if="type === 'info'" :gutter="20">
                <el-col :span="12">
                    <el-form-item label="用户姓名">
                        <el-input :value="ruleForm.yonghuName || '-'" readonly></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="商家名称">
                        <el-input :value="ruleForm.shangjiaName || '-'" readonly></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="24">
                    <el-form-item label="问题内容">
                        <el-input type="textarea" :autosize="{ minRows: 2, maxRows: 8 }" :value="ruleForm.shangjiaChatIssueText || '-'" readonly></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="24">
                    <el-form-item label="回复内容">
                        <el-input type="textarea" :autosize="{ minRows: 2, maxRows: 8 }" :value="ruleForm.shangjiaChatReplyText || '-'" readonly></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="状态">
                        <el-input :value="ruleForm.zhuangtaiValue || '-'" readonly></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="数据类型">
                        <el-input :value="ruleForm.shangjiaChatValue || '-'" readonly></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="提问时间">
                        <el-input :value="displayIssueTime" readonly></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="回复时间">
                        <el-input :value="displayReplyTime" readonly></el-input>
                    </el-form-item>
                </el-col>
            </el-row>

            <!-- 新增/修改 -->
            <el-row v-else>
                <el-col :span="12" v-if="sessionTable != 'yonghu'">
                    <el-form-item class="select" label="提问人(用户)" prop="yonghuId">
                        <el-select v-model="ruleForm.yonghuId" :disabled="ro.yonghuId" filterable placeholder="请选择用户" @change="yonghuChange">
                            <el-option v-for="(item,index) in yonghuOptions" :key="item.id" :label="item.yonghuName" :value="item.id"></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="12" v-if="sessionTable != 'yonghu'">
                    <el-form-item class="input" label="用户姓名" prop="yonghuName">
                        <el-input v-model="yonghuForm.yonghuName" placeholder="用户姓名" clearable readonly></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12" v-if="sessionTable != 'shangjia'">
                    <el-form-item class="select" label="回答人(商家)" prop="shangjiaId">
                        <el-select v-model="ruleForm.shangjiaId" :disabled="ro.shangjiaId" filterable placeholder="请选择商家" @change="shangjiaChange">
                            <el-option v-for="(item,index) in shangjiaOptions" :key="item.id" :label="item.shangjiaName" :value="item.id"></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="12" v-if="sessionTable != 'shangjia'">
                    <el-form-item class="input" label="商家名称" prop="shangjiaName">
                        <el-input v-model="shangjiaForm.shangjiaName" placeholder="商家名称" clearable readonly></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="24">
                    <el-form-item label="问题内容" prop="shangjiaChatIssueText">
                        <el-input type="textarea" :readonly="ro.shangjiaChatIssueText" v-model="ruleForm.shangjiaChatIssueText" placeholder="问题内容"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="24">
                    <el-form-item label="回复内容" prop="shangjiaChatReplyText">
                        <el-input type="textarea" :readonly="ro.shangjiaChatReplyText" v-model="ruleForm.shangjiaChatReplyText" placeholder="回复内容(商家回复)"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12" v-if="zhuangtaiTypesOptions.length">
                    <el-form-item label="状态" prop="zhuangtaiTypes">
                        <el-select v-model="ruleForm.zhuangtaiTypes" placeholder="请选择状态">
                            <el-option v-for="(item,index) in zhuangtaiTypesOptions" :key="item.codeIndex" :label="item.indexName" :value="item.codeIndex"></el-option>
                        </el-select>
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
                    id: null,
                    yonghuId: null,
                    shangjiaId: null,
                    yonghuName: '',
                    shangjiaName: '',
                    shangjiaChatIssueText: '',
                    shangjiaChatReplyText: '',
                    issueTime: '',
                    replyTime: '',
                    insertTime: '',
                    zhuangtaiTypes: null,
                    zhuangtaiValue: '',
                    shangjiaChatTypes: null,
                    shangjiaChatValue: '',
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
        computed: {
            displayIssueTime() {
                return this.fmtTime(this.ruleForm.issueTime || this.ruleForm.insertTime);
            },
            displayReplyTime() {
                return this.fmtTime(this.ruleForm.replyTime);
            },
        },
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
            fmtTime(v) {
                if (v === null || v === undefined || v === '') return '-';
                if (typeof v === 'string') return v;
                if (typeof v === 'number') {
                    const d = new Date(v);
                    return isNaN(d.getTime()) ? '-' : this.formatDate(d);
                }
                if (v instanceof Date) return this.formatDate(v);
                return String(v);
            },
            formatDate(d) {
                const p = n => (n < 10 ? '0' + n : '' + n);
                return d.getFullYear() + '-' + p(d.getMonth() + 1) + '-' + p(d.getDate()) + ' '
                    + p(d.getHours()) + ':' + p(d.getMinutes()) + ':' + p(d.getSeconds());
            },
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
                const nid = id != null && id !== '' ? id : _this.id;
                if (nid === undefined || nid === null || nid === '') {
                    _this.$message.error('记录编号无效');
                    return;
                }
                _this.$http({ url: `shangjiaChat/info/${nid}`, method: 'get' }).then(({ data }) => {
                    if (data && data.code === 0 && data.data) {
                        const row = data.data;
                        const blank = {
                            id: null,
                            yonghuId: null,
                            shangjiaId: null,
                            yonghuName: '',
                            shangjiaName: '',
                            shangjiaChatIssueText: '',
                            shangjiaChatReplyText: '',
                            issueTime: '',
                            replyTime: '',
                            insertTime: '',
                            zhuangtaiTypes: null,
                            zhuangtaiValue: '',
                            shangjiaChatTypes: null,
                            shangjiaChatValue: '',
                        };
                        Object.keys(blank).forEach(k => _this.$set(_this.ruleForm, k, blank[k]));
                        Object.keys(row).forEach(k => _this.$set(_this.ruleForm, k, row[k]));
                        if (row.yonghuId != null) _this.yonghuChange(row.yonghuId);
                        if (row.shangjiaId != null) _this.shangjiaChange(row.shangjiaId);
                    } else {
                        _this.$message.error((data && data.msg) ? data.msg : '加载失败');
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
.btn .el-button { margin-right: 12px; }
</style>
