<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button type="success" icon="el-icon-plus" size="mini" @click="handleAdd">新增</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="payChannelList" >
      <el-table-column label="通道id" align="center" prop="payChannelId" />
      <el-table-column label="通道名称" align="center" prop="name" />
      <el-table-column label="通道代号" align="center" prop="code" />
      <el-table-column label="通道状态" align="center" prop="status" >
        <template v-slot:default="scope">
          <dict-tag :options="dict.type['sys_normal_disable']" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template v-slot:default="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['pay:channel:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['pay:channel:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

  </div>
</template>

<script>
import {listPayChannel} from "@/api/pay/channel";

export default {
  name: "payChannel",
  dicts: ['sys_normal_disable'],
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
      // 参数表格数据
      payChannelList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        configName: [
          {required: true, message: "参数名称不能为空", trigger: "blur"}
        ],
        configKey: [
          {required: true, message: "参数键名不能为空", trigger: "blur"}
        ],
        configValue: [
          {required: true, message: "参数键值不能为空", trigger: "blur"}
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询参数列表 */
    getList() {
      this.loading = true;
      listPayChannel(this.queryParams).then(response => {
          this.payChannelList = response.data;
          this.loading = false;
        }
      );
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加参数";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.$router.push({ path: '/pay/channel/config', query: { channelId: row['payChannelId'] } })
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const configIds = row.configId || this.ids;
      this.$modal.confirm('是否确认删除参数编号为"' + configIds + '"的数据项？').then(function() {
        return null;
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    }
  }
}
</script>
