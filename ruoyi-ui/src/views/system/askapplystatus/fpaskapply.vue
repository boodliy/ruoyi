<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="签单起期" prop="startDate">
        <el-date-picker clearable
          v-model="queryParams.startDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择签单起期">
        </el-date-picker>
      </el-form-item>
           <el-form-item label="签单始期" prop="endDate">
        <el-date-picker clearable
          v-model="queryParams.endDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择签单始期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="案件姓名" prop="ajName">
        <el-input
          v-model="queryParams.ajName"
          placeholder="请输入案件姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="案件渠道" prop="ajQudao">
        <el-input
          v-model="queryParams.ajQudao"
          placeholder="请输入案件渠道"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="姓名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
        <!-- <el-form-item label="签单状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择签单状态">
          <el-option
            v-for="item in statusList"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item> -->
      <el-form-item label="探案律师" prop="taUser">
        <el-input
          v-model="queryParams.taUser"
          placeholder="请输入探案律师"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
             <el-form-item label="手机号" prop="phone">
        <el-input
          v-model="queryParams.phone"
          placeholder="请输入手机号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="处理用户" prop="userId">
       <el-select   
        v-model="queryParams.userId"
        filterable
        remote
        reserve-keyword
        placeholder="请输入用户名"
        :remote-method="getUserList"
        :loading="userLoading"
      >

          <el-option
            v-for="item in userOptions"
            :key="item.userId"
            :label="item.userName"
            :value="item.userId"
          />
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:fpaskapplystatus:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:fpaskapplystatus:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:fpaskapplystatus:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:fpaskapplystatus:export']"
        >导出</el-button>
      </el-col>
            <el-col :span="1.5">
        <el-button
          type="info"
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
          v-hasPermi="['system:fpaskapplystatus:import']"
        >导入</el-button>
      </el-col>

      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
      <!-- 用户导入对话框 -->
      <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px">
        <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
        >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">
          将文件拖到此处，或
          <em>点击上传</em>
        </div>
        <div class="el-upload__tip" slot="tip">
          <el-checkbox v-model="upload.updateSupport" />是否更新已经存在的用户数据
          <el-link type="info" style="font-size:12px" @click="importTemplate">下载模板</el-link>
        </div>
        <div class="el-upload__tip" style="color:red" slot="tip">提示：仅允许导入“xls”或“xlsx”格式文件！</div>
        </el-upload>
        <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
        </div>
      </el-dialog>

    <el-table v-loading="loading" :data="askapplystatusList" @selection-change="handleSelectionChange">
      
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="签单流水号" align="center" prop="askNo" /> -->
      <el-table-column label="签单日期" align="center" prop="qdDate" width="180">
        <template slot-scope="scope">
          <span>{{ scope.row.qdDate ? parseTime(scope.row.qdDate, '{y}-{m}-{d} {h}:{i}') : '' }}</span>
        </template>
      </el-table-column>
             <el-table-column label="跟进时间" align="center" prop="gjsj" width="180">
        <template slot-scope="scope">
          <span>{{ scope.row.gjsj ? parseTime(scope.row.gjsj, '{y}-{m}-{d} {h}:{i}') : '' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="案件姓名" align="center" prop="ajName" />
      <el-table-column label="案件渠道" align="center" prop="ajQudao" />
      <el-table-column label="姓名" align="center" prop="name" />
      <el-table-column label="手机号" align="center" prop="phone" />
      <!-- <el-table-column label="签单金额" align="center" prop="qdje" /> -->
      <!-- <el-table-column label="后期提成" align="center" prop="hqtc" />
      <el-table-column label="实收金额" align="center" prop="ssje" /> -->
      <el-table-column label="微信添加" align="center" prop="wxtj" :formatter="formatWxtj" />
      <el-table-column label="跟进状态" align="center" prop="gjzt" :formatter="formatGjzt" />
      <el-table-column label="案件情况" align="center" prop="ajqk" />
      <el-table-column label="当前处理用户" align="center" prop="userName" />
      <!-- <el-table-column label="订单状态" align="center" prop="status"> -->
      <!-- <template #default="scope">
        <el-tag
          :type="getTagType(scope.row.status)"
          effect="plain"
        >
          {{ getStatusLabel(scope.row.status) }}
        </el-tag>
      </template>
    </el-table-column> -->
      <!-- <el-table-column label="探案律师" align="center" prop="taUser" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:askapplystatus:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:askapplystatus:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog
  title="批量分配用户"
  :visible.sync="assignDialogVisible"
  width="400px"
>
  <el-form>
    <el-form-item label="分配用户" required>
      <el-select v-model="assignUserId" placeholder="请选择用户" filterable remote reserve-keyword :remote-method="getUserList" :loading="userLoading">
        <el-option
          v-for="user in userOptions"
          :key="user.userId"
          :label="user.userName"
          :value="user.userId"
        />
      </el-select>
    </el-form-item>
  </el-form>

  <span slot="footer" class="dialog-footer">
    <el-button @click="assignDialogVisible = false">取消</el-button>
    <el-button type="primary" @click="submitAssign">确定</el-button>
  </span>
</el-dialog>
<el-button
  type="primary"
  icon="el-icon-user"
  size="mini"
  @click="openAssignDialog"
>
  批量分配用户
</el-button>
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改【请填写功能名称】对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
         <el-form-item label="签单日期" prop="qdDate">
          <el-date-picker
            clearable
            v-model="form.qdDate"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm"
            format="yyyy-MM-dd HH:mm"
            placeholder="请选择签单日期时间">
          </el-date-picker>
        </el-form-item>
            <el-form-item label="跟进时间" prop="gjsj">
          <el-date-picker
            clearable
            v-model="form.gjsj"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm"
            format="yyyy-MM-dd HH:mm"
            placeholder="请选择签单日期时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="案件姓名" prop="ajName">
          <el-input v-model="form.ajName" placeholder="请输入案件姓名" />
        </el-form-item>
        <el-form-item label="案件渠道" prop="ajQudao">
          <el-input v-model="form.ajQudao" placeholder="请输入案件渠道" />
        </el-form-item>
        <!-- <el-form-item label="签单状态" prop="status">
        <el-select v-model="form.status" placeholder="请选择签单状态" clearable>
          <el-option
            v-for="item in statusList2"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item> -->
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入手机号" />
        </el-form-item>
        <!-- <el-form-item label="签单金额" prop="qdje">
          <el-input v-model="form.qdje" placeholder="请输入签单金额" />
        </el-form-item>
        <el-form-item label="后期提成" prop="hqtc">
          <el-input v-model="form.hqtc" placeholder="请输入后期提成" />
        </el-form-item>
        <el-form-item label="实收金额" prop="ssje">
          <el-input v-model="form.ssje" placeholder="请输入实收金额" />
        </el-form-item>
        <el-form-item label="探案律师" prop="taUser">
          <el-input v-model="form.taUser" placeholder="请输入探案律师" />
        </el-form-item> -->
       <el-form-item label="微信添加" prop="wxtj">
          <el-radio-group v-model="form.wxtj">
            <el-radio 
              v-for="item in isflag"
              :key="item.value"
              :label="item.value"
            >
              {{ item.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="跟进状态" prop="gjzt">
          <el-select v-model="form.gjzt" placeholder="请选择跟进状态" clearable>
            <el-option
              v-for="item in gjztList2"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
       <el-form-item label="案件情况" prop="ajqk">
          <el-input
            type="textarea"
            v-model="form.ajqk"
            :rows="4"  
            placeholder="请输入案件情况"
          />
        </el-form-item>
        <el-form-item label="处理用户" prop="userId">
          <el-select
        v-model="form.userId"
        filterable
        remote
        reserve-keyword
        placeholder="请输入用户名"
        :remote-method="getUserList"
        :loading="userLoading"
      >

          <el-option
            v-for="item in userOptions"
            :key="item.userId"
            :label="item.userName"
            :value="item.userId"
          />
        </el-select>
           </el-form-item>
             <el-form-item label="备注" prop="field101">
              <el-input v-model="form.bz" placeholder="请输入备注" clearable :style="{width: '100%'}">
              </el-input>
            </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { assignUserBatch,listAskapplystatus, getAskapplystatus, delAskapplystatus, addAskapplystatus, updateAskapplystatus ,importTemplate } from "@/api/system/askapplystatus"
import{listUser} from "@/api/system/user"
import { getToken } from "@/utils/auth";
export default {
  name: "Askapplystatus",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 【请填写功能名称】表格数据
      askapplystatusList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        qdDate: null,
        ajName: null,
        ajQudao: null,
        name: null,
        phone: null,
        qdje: null,
        hqtc: null,
        ssje: null,
        status: null,
        taUser: null,
        startDate:null,
        endDate:null
      },
      statusList:[
        {
          value: '',
          label: '全部'
        },
       {
          value: '1',
          label: '签单中'
        }, {
          value: '2',
          label: '签单失败'
        }, {
          value: '3',
          label: '签单成功'
        }
      ],
      statusList2:[
       {
          value: '1',
          label: '签单中'
        }, {
          value: '2',
          label: '签单失败'
        }, {
          value: '3',
          label: '签单成功'
        }
      ],
           isflag:[
        {
          value: '1',
          label: '是'
        },
       {
          value: '2',
          label: '否'
        }
      ],
      gjztList2:[
       {
          value: '1',
          label: '未接通'
        }, {
          value: '2',
          label: '跟进中'
        }, {
          value: '3',
          label: '待成交'
        },
        {
          value: '4',
          label: '已成交'
        },{
          value: '5',
          label: '关机/停机'
        },{
          value: '6',
          label: '空号'
        },{
          value: '7',
          label: '放弃'
        }
      ],
      userOptions: [],      // 下拉用户列表
      userLoading: false,   // 加载状态
      // 表单参数
      form: {},
      // 用户导入参数
      upload: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/system/askapplystatus/importData"
      },
      selectedRows: [],  // 保存选中的行数据
      assignDialogVisible: false,
       assignUserId: null,  // 分配的用户ID
      // 表单校验
      rules: {
      }
    }
  },
  created() {
    this.getList()
    this.getUserList("")
  },
  methods: {
      handleSelectionChange(val) {
          this.selectedRows = val;
        },
        openAssignDialog() {
          if (this.selectedRows.length === 0) {
            this.$message.warning("请先选择至少一条记录");
            return;
          }
          this.assignDialogVisible = true;
        },
        submitAssign() {
          if (!this.assignUserId) {
            this.$message.warning("请选择要分配的用户");
            return;
          }
          console.log("this.assignUserId",this.assignUserId);
          
          // 组装选中行的ID列表
          const ids = this.selectedRows.map(row => row.id || row.askNo); // 这里id字段根据你的数据调整

          // 调接口批量分配用户，示例接口名 assignUserBatch(ids, assignUserId)
          assignUserBatch(ids, this.assignUserId).then(() => {
            this.$message.success("分配成功");
            this.assignDialogVisible = false;
            this.getList(); // 刷新表格
            this.selectedRows = []; // 清空选择
            this.assignUserId = null;
          }).catch(err => {
            this.$message.error("分配失败：" + err.message);
          });
        },
      /** 导入按钮操作 */
      handleImport() {
        this.upload.title = "用户导入";
        this.upload.open = true;
      },
          /** 下载模板操作 */
          importTemplate() {
            // this.download('/system/askapplystatus/importTemplate', {
            // }, `user_template_${new Date().getTime()}.xlsx`)
           // 调用接口，获取返回数据
                importTemplate().then(res => {
                  // 创建blob对象
                  const blob = new Blob([res], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' })
                  const url = window.URL.createObjectURL(blob)
                  const link = document.createElement('a')
                  link.href = url
                  link.setAttribute('download', `导入模板下载${Date.now()}.xlsx`)
                  document.body.appendChild(link)
                  link.click()
                  document.body.removeChild(link)
                  window.URL.revokeObjectURL(url)
                }).catch(err => {
                  console.error('下载失败', err)
                })

          },
      // 文件上传中处理
      handleFileUploadProgress(event, file, fileList) {
        this.upload.isUploading = true;
      },
      // 文件上传成功处理
      handleFileSuccess(response, file, fileList) {
        this.upload.open = false;
        this.upload.isUploading = false;
        this.$refs.upload.clearFiles();
        this.$alert(response.msg, "导入结果", { dangerouslyUseHTMLString: true });
        this.getList();
      },
      // 提交上传文件
      submitFileForm() {
        this.$refs.upload.submit();
      },

  formatWxtj(row) {
    const mapping = { '1': '是', '2': '否' };
    return mapping[row.wxtj] || row.wxtj;
  },
  formatGjzt(row) {
    const mapping = {
      '1': '未接通',
      '2': '跟进中',
      '3': '待成交',
      '4': '已成交',
      '5': '关机/停机',
      '6': '空号',
      '7': '放弃'
    };
    return mapping[row.gjzt] || row.gjzt;
  },
 // 选择处理用户时，同步更新userName
  handleUserChange(userId) {
    const user = this.userOptions.find(u => u.userId === userId);
    this.form.userId = user ? user.userId : null;
  },
     // 模糊搜索用户
     getUserList(query) {
      console.log("调用查询用户");
      
      if (!query) {
        this.userOptions = [];
        return;
      }

        this.userLoading = true;
        listUser({ userName: query }).then(response => {
          this.userOptions = response.rows.map(user => ({
            userId: user.userId,
            userName: user.userName
          }));
          this.userLoading = false;
        });
      },
    
    getStatusLabel(value) {
      const item = this.statusList.find(i => i.value === value);
      return item ? item.label : '未知';
    },
    getTagType(value) {
      switch (value) {
        case '1': return 'warning';     // 签单中
        case '2': return 'danger';      // 签单失败
        case '3': return 'success';     // 签单成功
        default:  return 'info';        // 未知
      }
    },
    /** 查询【请填写功能名称】列表 */
    getList() {
      this.loading = true
      listAskapplystatus(this.queryParams).then(response => {
        this.askapplystatusList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        askNo: null,
        qdDate: null,
        ajName: null,
        ajQudao: null,
        name: null,
        phone: null,
        qdje: null,
        hqtc: null,
        ssje: null,
        status: null,
        taUser: null,
        userId:null,
        userName: null, 
        startDate:null,
        endDate:null
      }
      this.resetForm("form")
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    // 多选框选中数据
    // handleSelectionChange(selection) {
    //   this.ids = selection.map(item => item.askNo)
    //   this.single = selection.length!==1
    //   this.multiple = !selection.length
    // },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加【请填写功能名称】"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const askNo = row.askNo || this.ids
      getAskapplystatus(askNo).then(response => {
        this.form = response.data
        this.getUserList('')
        this.open = true
        this.title = "修改客资管理"
        // 将 userName 填充到 userid 
        var userName =  this.form.userName; 
        this.form.userId =this.form.userName; 
        console.log("111",this.form);
        
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.askNo != null) {
            updateAskapplystatus(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addAskapplystatus(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const askNos = row.askNo || this.ids
      this.$modal.confirm('是否确认删除客资管理编号为"' + askNos + '"的数据项？').then(function() {
        return delAskapplystatus(askNos)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/askapplystatus/export', {
        ...this.queryParams
      }, `askapplystatus_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
