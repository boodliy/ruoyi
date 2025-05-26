<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="签单日期" prop="qdDate">
        <el-date-picker clearable
          v-model="queryParams.qdDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择签单日期">
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
         <el-form-item label="手机号" prop="phone">
        <el-input
          v-model="queryParams.phone"
          placeholder="请输入手机号"
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
      <!-- <el-form-item label="探案律师" prop="taUser">
        <el-input
          v-model="queryParams.taUser"
          placeholder="请输入探案律师"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
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
          v-hasPermi="['system:askapplystatus:add']"
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
          v-hasPermi="['system:askapplystatus:query']"
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
          v-hasPermi="['system:askapplystatus:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:askapplystatus:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="askapplystatusList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="签单流水号" align="center" prop="askNo" /> -->
      <el-table-column label="入库时间" align="center" prop="qdDate" width="180">
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
     <el-table-column label="微信添加" align="center" prop="wxtj" :formatter="formatWxtj" />
      <el-table-column label="跟进状态" align="center" prop="gjzt" :formatter="formatGjzt" />
            <el-table-column label="案件情况" align="center" prop="ajqk" />

      <!-- <el-table-column label="签单金额" align="center" prop="qdje" /> -->
      <!-- <el-table-column label="后期提成" align="center" prop="hqtc" />
      <el-table-column label="实收金额" align="center" prop="ssje" />
      <el-table-column label="订单状态" align="center" prop="status">
      <template #default="scope">
        <el-tag
          :type="getTagType(scope.row.status)"
          effect="plain"
        >
          {{ getStatusLabel(scope.row.status) }}
        </el-tag>
      </template>
    </el-table-column>
      <el-table-column label="探案律师" align="center" prop="taUser" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:askapplystatus:query']"
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
        <!-- <el-form-item label="后期提成" prop="hqtc">
          <el-input v-model="form.hqtc" placeholder="请输入后期提成" />
        </el-form-item>
        <el-form-item label="实收金额" prop="ssje">
          <el-input v-model="form.ssje" placeholder="请输入实收金额" />
        </el-form-item>
        <el-form-item label="探案律师" prop="taUser">
          <el-input v-model="form.taUser" placeholder="请输入探案律师" />
        </el-form-item> -->
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
import { listAskapplystatus, getAskapplystatus, delAskapplystatus, addAskapplystatus, updateAskapplystatus } from "@/api/system/askapplystatus"

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
        gjsj:null,
        ajName: null,
        ajQudao: null,
        name: null,
        phone: null,
        qdje: null,
        hqtc: null,
        ssje: null,
        status: null,
        taUser: null,
        wxtj:null,
        gjzt:null,
        ajqk:null,
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
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    
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
        gjsj:null,
        ajName: null,
        ajQudao: null,
        name: null,
        phone: null,
        qdje: null,
        hqtc: null,
        ssje: null,
        status: null,
        taUser: null,
        wxtj:null,
        gjzt:null,
        ajqk:null,
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
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.askNo)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
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
        this.open = true
        this.title = "修改【请填写功能名称】"
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
      this.$modal.confirm('是否确认删除【请填写功能名称】编号为"' + askNos + '"的数据项？').then(function() {
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
