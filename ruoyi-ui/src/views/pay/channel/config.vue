<template>
  <div class="app-container">
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>基本信息</span>
        <el-button v-if="showEditButton" style="float: right; padding: 3px 0" type="text" @click="editChannel = true">编辑</el-button>
        <el-button v-else style="float: right; padding: 3px 0" type="text" @click="editChannel = false">取消编辑</el-button>

      </div>
      <el-form :model="payChannelForm" :rules="payChannelRules" ref="payChannelRef" size="small" label-width="100px" class="card-form">
        <el-col :span="8">
          <el-form-item label="通道名称" prop="name">
            <el-input v-model="payChannelForm.name" style="width: 260px" :disabled="disableEdit"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="通道代号" prop="code">
            <el-input v-model="payChannelForm.code" style="width: 260px" :disabled="disableEdit"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="通道状态" prop="status">
            <el-select v-model="payChannelForm.status" placeholder="系统内置" clearable  style="width: 260px" :disabled="disableEdit">
              <el-option
                v-for="dict in dict.type['sys_normal_disable']"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="日限额" prop="dailyLimit">
            <el-input v-model="payChannelForm.dailyLimit" style="width: 260px" :disabled="disableEdit"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="月限额" prop="monthLimit">
            <el-input v-model="payChannelForm.monthLimit" style="width: 260px" :disabled="disableEdit"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="24" v-if="editChannel">
          <el-form-item>
            <el-button type="primary" size="small" @click="submitPayChannel">提交</el-button>
          </el-form-item>
        </el-col>
      </el-form>
    </el-card>

    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>支付通道</span>
        <el-button style="float: right; padding: 3px 0" type="text" @click="handleAddChannelConfig">添加通道</el-button>
      </div>
      <el-card class="box-card-item"
               :body-style="{ padding: '0px' }"
               v-for="config in payChannelConfigs"
               :key="config.id">
        <img src="https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png" class="image" alt="">
        <div style="padding: 14px;">
          <span>{{config.name}}</span>
          <div class="bottom clearfix">
            <el-button type="text" class="button">操作按钮</el-button>
          </div>
        </div>
      </el-card>
    </el-card>

    <!-- 添加或修改支付通道 -->
    <el-dialog :title="channelConfigTitle" :visible.sync="channelConfigDialogOpen" width="840px" append-to-body>
      <el-form ref="channelConfigRef" :model="channelConfigForm" :rules="channelConfigRules" label-width="120px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="支付类型" prop="channelType" >
              <el-input v-model="channelConfigForm.channelType" placeholder="请选择支付类型" class="form-item" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="通道状态" prop="status">
              <el-input v-model="channelConfigForm.status" placeholder="请选择通道状态" class="form-item" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="费率" prop="costRate">
              <el-input v-model="channelConfigForm.costRate" placeholder="请输入费率" class="form-item" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="手续费" prop="costFee">
              <el-input v-model="channelConfigForm.costFee" placeholder="请输入手续费" class="form-item" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="最大手续费" prop="maxFee">
              <el-input v-model="channelConfigForm.maxFee" placeholder="请输入最大手续费" class="form-item" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="最低手续费" prop="minFee">
              <el-input v-model="channelConfigForm.minFee" placeholder="请输入最低手续费" class="form-item" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="最大费率" prop="maxRate">
              <el-input v-model="channelConfigForm.maxRate" placeholder="请输入最大费率" class="form-item" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="最小费率" prop="minRate">
              <el-input v-model="channelConfigForm.minRate" placeholder="请输入最低费率" class="form-item" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="日限额" prop="dailyLimit">
              <el-input v-model="channelConfigForm.dailyLimit" placeholder="请输入日限额" class="form-item" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="日订单限额" prop="monthLimit">
              <el-input v-model="channelConfigForm.dailyOrderLimit" placeholder="请输入日订单限额" class="form-item" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="单笔最低限额" prop="singleMinLimit">
              <el-input v-model="channelConfigForm.singleMinLimit" placeholder="请输入单笔最低限额" class="form-item" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="单笔最大限额" prop="singleMaxLimit">
              <el-input v-model="channelConfigForm.singleMaxLimit" placeholder="请输入单笔最大限额" class="form-item" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="交易时间">
              <el-time-picker
                is-range
                v-model="dateRange"
                range-separator="-"
                value-format="HH:mm:ss"
                start-placeholder="开始时间"
                end-placeholder="结束时间"
                class="form-item"
              ></el-time-picker>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitChannelConfig">确 定</el-button>
        <el-button @click="cancelChannelConfig">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {addPayChannelConfig, getPayChannel, updatePayChannel} from "@/api/pay/channel";

export default {
  name: "channelConfig",
  dicts: ['sys_normal_disable'],
  data() {
    return {
      payChannelId: undefined,
      // 支付通道
      editChannel: false,
      payChannelForm: {
        payChannelId: undefined,
        name: undefined,
        code: undefined,
        status: 0,
        dailyLimit: 0,
        monthLimit: 0
      },
      payChannelConfigs: [],
      payChannelRules: {
        name: [
          {required: true, message: "渠道名称不能为空", trigger: "blur"}
        ],
        code: [
          {required: true, message: "渠道编码不能为空", trigger: "blur"}
        ]
      },
      // 支付类型配置
      dateRange: [],
      channelConfigDialogOpen: false,
      channelConfigTitle: "",
      channelConfigForm: {},
      channelConfigRules: {}
    }
  },
  created() {
    this.payChannelId = this.$route.query.channelId
    this.getPayChannel()
  },
  computed: {
    showEditButton() {
      return !this.editChannel
    },
    disableEdit() {
      return !this.editChannel
    }
  },
  methods: {
    /**
     * 获取支付通道
     */
    getPayChannel() {
      getPayChannel({id: this.payChannelId}).then(response => {
        this.payChannelForm = response.data
        this.payChannelForm.status = response.data.status.toString()
        this.payChannelConfigs = response.data.configs
      })
    },
    /**
     * 提交通道配置
     */
    submitPayChannel() {
      this.$refs.payChannelRef.validate(valid => {
        if (valid) {
          updatePayChannel(this.payChannelForm).then(() => {
            this.$modal.msgSuccess("修改成功");
            this.getPayChannel()
          })
        }
      })
    },

    /** 新增按钮操作 */
    handleAddChannelConfig() {
      this.resetChannelConfig();
      this.channelConfigDialogOpen = true;
      this.channelConfigTitle = "添加支付配置";
    },
    /** 新增按钮操作 */
    cancelChannelConfig() {
      this.channelConfigDialogOpen = false;
      this.resetChannelConfig();
    },
    // 表单重置
    resetChannelConfig() {
      this.dateRange = ['00:00:00', '23:59:59']
      this.channelConfigForm = {
        channelConfigId: undefined,
        channelType: undefined,
        status: "0",
        costRate: 0.00,
        costFee: 0,
        maxRate: 0.00,
        minRate: 0.00,
        maxFee: 0,
        minFee: 0,
        dailyOrderLimit: 0,
        dailyLimit: 0,
        singleMinLimit: 0,
        singleMaxLimit: 0,
      };
      this.resetForm("form");
    },
    /**
     * 提交支付类型配置
     */
    submitChannelConfig() {
      this.$refs.channelConfigRef.validate(valid => {
        if (valid) {
          this.channelConfigForm.payChannelId = this.payChannelId
          this.channelConfigForm.startTime = this.dateRange[0]
          this.channelConfigForm.endTime = this.dateRange[1]
          addPayChannelConfig(this.channelConfigForm).then(() => {
            this.channelConfigDialogOpen = false
            this.$modal.msgSuccess("修改成功");
            this.getPayChannel()
          })
        }
      })
    }
  }
}
</script>

<style scoped lang="scss">
.box-card {
  margin-bottom: 30px;
  padding: 0 10px 20px 10px;
}
.box-card-item {
  width: 200px;
  height: 240px;
  margin: 8px 20px;
  display: inline-block;
}
.image {
  width: 100%;
  height: 140px;
  display: block;
}

.form-item {
  width: 200px;
}
</style>
