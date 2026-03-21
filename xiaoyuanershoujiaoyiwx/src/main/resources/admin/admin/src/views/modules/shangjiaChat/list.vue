<template>
    <div class="main-content">
        <div v-if="showFlag">
            <el-form :inline="true" :model="searchForm" class="form-content">
                <el-row :gutter="20" class="slt" :style="{justifyContent:contents.searchBoxPosition=='1'?'flex-start':contents.searchBoxPosition=='2'?'center':'flex-end'}">
                    <el-form-item :label="contents.inputTitle == 1 ? '提问人' : ''">
                        <el-input prefix-icon="el-icon-search" v-model="searchForm.yonghuName" placeholder="提问人(用户姓名)" clearable></el-input>
                    </el-form-item>
                    <el-form-item :label="contents.inputTitle == 1 ? '回答人' : ''">
                        <el-input prefix-icon="el-icon-search" v-model="searchForm.shangjiaName" placeholder="回答人(商家名称)" clearable></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="success" @click="search()">查询<i class="el-icon-search el-icon--right"/></el-button>
                    </el-form-item>
                </el-row>
                <el-row class="ad" :style="{justifyContent:contents.btnAdAllBoxPosition=='1'?'flex-start':contents.btnAdAllBoxPosition=='2'?'center':'flex-end'}">
                    <el-form-item>
                        <el-button v-if="isAuth('shangjiaChat','新增')" type="success" icon="el-icon-plus" @click="addOrUpdateHandler()">新增</el-button>
                        <el-button v-if="isAuth('shangjiaChat','删除')" :disabled="dataListSelections.length <= 0" type="danger" icon="el-icon-delete" @click="deleteHandler()">删除</el-button>
                    </el-form-item>
                </el-row>
            </el-form>
            <div class="table-content">
                <el-table class="tables" :size="contents.tableSize" :show-header="contents.tableShowHeader"
                          :header-row-style="headerRowStyle" :header-cell-style="headerCellStyle"
                          :border="contents.tableBorder" :fit="contents.tableFit" :stripe="contents.tableStripe"
                          :row-style="rowStyle" :cell-style="cellStyle"
                          :style="{width: '100%',fontSize:contents.tableContentFontSize,color:contents.tableContentFontColor}"
                          v-if="isAuth('shangjiaChat','查看')" :data="dataList" v-loading="dataListLoading"
                          @selection-change="selectionChangeHandler">
                    <el-table-column v-if="contents.tableSelection" type="selection" header-align="center" align="center" width="50"></el-table-column>
                    <el-table-column label="索引" v-if="contents.tableIndex" type="index" width="50" />
                    <el-table-column :sortable="contents.tableSortable" :align="contents.tableAlign" prop="yonghuName" header-align="center" label="提问人">
                        <template slot-scope="scope">{{scope.row.yonghuName}}</template>
                    </el-table-column>
                    <el-table-column :sortable="contents.tableSortable" :align="contents.tableAlign" prop="shangjiaName" header-align="center" label="回答人(商家)">
                        <template slot-scope="scope">{{scope.row.shangjiaName}}</template>
                    </el-table-column>
                    <el-table-column :sortable="contents.tableSortable" :align="contents.tableAlign" prop="shangjiaChatIssueText" header-align="center" label="问题" min-width="120">
                        <template slot-scope="scope">
                            <span v-if="scope.row.shangjiaChatIssueText && scope.row.shangjiaChatIssueText.length>15">{{scope.row.shangjiaChatIssueText.slice(0,15)}}...</span>
                            <span v-else>{{scope.row.shangjiaChatIssueText}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column :sortable="contents.tableSortable" :align="contents.tableAlign" prop="shangjiaChatReplyText" header-align="center" label="回复" min-width="120">
                        <template slot-scope="scope">
                            <span v-if="scope.row.shangjiaChatReplyText && scope.row.shangjiaChatReplyText.length>15">{{scope.row.shangjiaChatReplyText.slice(0,15)}}...</span>
                            <span v-else>{{scope.row.shangjiaChatReplyText || '-'}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column :sortable="contents.tableSortable" :align="contents.tableAlign" prop="issueTime" header-align="center" label="提问时间" width="160">
                        <template slot-scope="scope">{{scope.row.issueTime || scope.row.insertTime}}</template>
                    </el-table-column>
                    <el-table-column :sortable="contents.tableSortable" :align="contents.tableAlign" prop="replyTime" header-align="center" label="回复时间" width="160">
                        <template slot-scope="scope">{{scope.row.replyTime || '-'}}</template>
                    </el-table-column>
                    <el-table-column :sortable="contents.tableSortable" :align="contents.tableAlign" prop="zhuangtaiValue" header-align="center" label="状态" width="90">
                        <template slot-scope="scope">{{scope.row.zhuangtaiValue || '-'}}</template>
                    </el-table-column>
                    <el-table-column width="260" :align="contents.tableAlign" header-align="center" label="操作">
                        <template slot-scope="scope">
                            <el-button v-if="isAuth('shangjiaChat','查看')" type="success" icon="el-icon-tickets" size="mini" @click="addOrUpdateHandler(scope.row.id,'info')">详情</el-button>
                            <el-button v-if="isAuth('shangjiaChat','修改')" type="primary" icon="el-icon-edit" size="mini" @click="addOrUpdateHandler(scope.row.id)">回复/修改</el-button>
                            <el-button v-if="isAuth('shangjiaChat','删除')" type="danger" icon="el-icon-delete" size="mini" @click="deleteHandler(scope.row.id)">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <el-pagination
                    clsss="pages"
                    :layout="layouts"
                    @size-change="sizeChangeHandle"
                    @current-change="currentChangeHandle"
                    :current-page="pageIndex"
                    :page-sizes="[10, 20, 50, 100]"
                    :page-size="Number(contents.pageEachNum)"
                    :total="totalPage"
                    :small="contents.pageStyle"
                    class="pagination-content"
                    :background="contents.pageBtnBG"
                    :style="{textAlign:contents.pagePosition==1?'left':contents.pagePosition==2?'center':'right'}"
                ></el-pagination>
            </div>
        </div>
        <add-or-update v-if="addOrUpdateFlag" :parent="this" ref="addOrUpdate"></add-or-update>
    </div>
</template>
<script>
    import AddOrUpdate from "./add-or-update";
    import styleJs from "../../../utils/style.js";

    export default {
        data() {
            return {
                searchForm: {},
                dataList: [],
                pageIndex: 1,
                pageSize: 10,
                totalPage: 0,
                dataListLoading: false,
                dataListSelections: [],
                showFlag: true,
                addOrUpdateFlag: false,
                contents: null,
                layouts: '',
            };
        },
        created() {
            this.contents = styleJs.listStyle();
            this.init();
            this.getDataList();
            this.contentStyleChange();
        },
        mounted() {},
        components: { AddOrUpdate },
        methods: {
            contentStyleChange() {
                this.contentSearchStyleChange();
                this.contentBtnAdAllStyleChange();
                this.contentSearchBtnStyleChange();
                this.contentTableBtnStyleChange();
                this.contentPageStyleChange();
            },
            contentSearchStyleChange() {
                this.$nextTick(() => {
                    document.querySelectorAll('.form-content .slt .el-input__inner').forEach(el => {
                        el.style.height = this.contents.inputHeight;
                        el.style.color = this.contents.inputFontColor;
                        el.style.fontSize = this.contents.inputFontSize;
                        el.style.borderWidth = this.contents.inputBorderWidth;
                        el.style.borderStyle = this.contents.inputBorderStyle;
                        el.style.borderColor = this.contents.inputBorderColor;
                        el.style.borderRadius = this.contents.inputBorderRadius;
                        el.style.backgroundColor = this.contents.inputBgColor;
                    });
                });
            },
            contentSearchBtnStyleChange() {
                this.$nextTick(() => {
                    document.querySelectorAll('.form-content .slt .el-button--success').forEach(el => {
                        el.style.height = this.contents.searchBtnHeight;
                        el.style.color = this.contents.searchBtnFontColor;
                        el.style.fontSize = this.contents.searchBtnFontSize;
                        el.style.backgroundColor = this.contents.searchBtnBgColor;
                    });
                });
            },
            contentBtnAdAllStyleChange() {
                this.$nextTick(() => {
                    document.querySelectorAll('.form-content .ad .el-button--success').forEach(el => {
                        el.style.height = this.contents.btnAdAllHeight;
                        el.style.color = this.contents.btnAdAllAddFontColor;
                        el.style.fontSize = this.contents.btnAdAllFontSize;
                        el.style.backgroundColor = this.contents.btnAdAllAddBgColor;
                    });
                    document.querySelectorAll('.form-content .ad .el-button--danger').forEach(el => {
                        el.style.height = this.contents.btnAdAllHeight;
                        el.style.color = this.contents.btnAdAllDelFontColor;
                        el.style.fontSize = this.contents.btnAdAllFontSize;
                        el.style.backgroundColor = this.contents.btnAdAllDelBgColor;
                    });
                });
            },
            rowStyle({ row, rowIndex }) {
                if (rowIndex % 2 == 1 && this.contents.tableStripe) return { color: this.contents.tableStripeFontColor };
                return '';
            },
            cellStyle({ row, rowIndex }) {
                if (rowIndex % 2 == 1 && this.contents.tableStripe) return { backgroundColor: this.contents.tableStripeBgColor };
                return '';
            },
            headerRowStyle() { return { color: this.contents.tableHeaderFontColor }; },
            headerCellStyle() { return { backgroundColor: this.contents.tableHeaderBgColor }; },
            contentTableBtnStyleChange() {},
            contentPageStyleChange() {
                let arr = [];
                if (this.contents.pageTotal) arr.push('total');
                if (this.contents.pageSizes) arr.push('sizes');
                if (this.contents.pagePrevNext) { arr.push('prev'); if (this.contents.pagePager) arr.push('pager'); arr.push('next'); }
                if (this.contents.pageJumper) arr.push('jumper');
                this.layouts = arr.join();
                this.contents.pageEachNum = 10;
            },
            init() {},
            search() {
                this.pageIndex = 1;
                this.getDataList();
            },
            getDataList() {
                this.dataListLoading = true;
                let params = { page: this.pageIndex, limit: this.pageSize, sort: 'id' };
                if (this.searchForm.yonghuName) params['yonghuName'] = '%' + this.searchForm.yonghuName + '%';
                if (this.searchForm.shangjiaName) params['shangjiaName'] = '%' + this.searchForm.shangjiaName + '%';

                this.$http({ url: "shangjiaChat/page", method: "get", params: params }).then(({ data }) => {
                    if (data && data.code === 0) {
                        this.dataList = data.data.list;
                        this.totalPage = data.data.total;
                    } else {
                        this.dataList = [];
                        this.totalPage = 0;
                    }
                    this.dataListLoading = false;
                });
            },
            sizeChangeHandle(val) {
                this.pageSize = val;
                this.pageIndex = 1;
                this.getDataList();
            },
            currentChangeHandle(val) {
                this.pageIndex = val;
                this.getDataList();
            },
            selectionChangeHandler(val) { this.dataListSelections = val; },
            addOrUpdateHandler(id, type) {
                this.showFlag = false;
                this.addOrUpdateFlag = true;
                if (type != 'info') type = 'else';
                this.$nextTick(() => { this.$refs.addOrUpdate.init(id, type); });
            },
            deleteHandler(id) {
                var ids = id ? [Number(id)] : this.dataListSelections.map(item => Number(item.id));
                this.$confirm(`确定进行[${id ? "删除" : "批量删除"}]操作?`, "提示", { confirmButtonText: "确定", cancelButtonText: "取消", type: "warning" }).then(() => {
                    this.$http({ url: "shangjiaChat/delete", method: "post", data: ids }).then(({ data }) => {
                        if (data && data.code === 0) {
                            this.$message({ message: "操作成功", type: "success", duration: 1500, onClose: () => { this.search(); } });
                        } else {
                            this.$message.error(data.msg);
                        }
                    });
                });
            },
        }
    };
</script>
<style lang="scss" scoped>
.slt { margin: 0 !important; display: flex; }
.ad { margin: 0 !important; display: flex; }
.pages ::v-deep el-pagination__sizes ::v-deep el-input__inner { height: 22px; line-height: 22px; }
.el-button+.el-button { margin: 0; }
.tables ::v-deep .el-button--success { height: 35px; color: #fff; font-size: 14px; border-radius: 10px 0 10px 0; background-color: var(--publicMainColor); }
.tables ::v-deep .el-button--primary { height: 35px; color: #fff; font-size: 14px; border-radius: 10px 0 10px 0; background-color: var(--publicMainColor); }
.tables ::v-deep .el-button--danger { height: 35px; color: #fff; font-size: 14px; border-radius: 10px 0 10px 0; background-color: var(--publicMainColor); }
.tables ::v-deep .el-button { margin: 4px; }
.form-content { background: transparent; }
.table-content { background: transparent; }
.tables ::v-deep .el-table__body tr { background-color: #f5f5f5 !important; color: rgba(0,1,2,1) !important; }
.tables ::v-deep .el-table__body tr.el-table__row--striped td { background: transparent; }
.tables ::v-deep .el-table__body tr.el-table__row--striped { background-color: #F5F7FA !important; color: rgba(0,0,0,1) !important; }
.tables ::v-deep .el-table__body tr:hover>td { background-color: var(--publicSubColor) !important; color: #333 !important; }
</style>
