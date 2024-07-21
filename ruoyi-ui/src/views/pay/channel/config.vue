<template>
  <div class="app-container">
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>基本信息</span>
        <el-button v-if="showEditButton" style="float: right; padding: 3px 0" type="text" @click="editChannel = true">
          编辑
        </el-button>
        <el-button v-else style="float: right; padding: 3px 0" type="text" @click="editChannel = false">取消编辑
        </el-button>

      </div>
      <el-form :model="payChannel" :rules="payChannelRules" ref="payChannelRef" size="small" label-width="100px"
               class="card-form">
        <el-col :span="8">
          <el-form-item label="通道名称" prop="name">
            <el-input v-model="payChannel.name" style="width: 260px" :disabled="disableEdit"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="通道代号" prop="code">
            <el-input v-model="payChannel.code" style="width: 260px" :disabled="disableEdit"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="通道状态" prop="status">
            <el-select v-model="payChannel.status" placeholder="系统内置" clearable style="width: 260px"
                       :disabled="disableEdit">
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
            <el-input v-model="payChannel.dailyLimit" style="width: 260px" :disabled="disableEdit"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="月限额" prop="monthLimit">
            <el-input v-model="payChannel.monthLimit" style="width: 260px" :disabled="disableEdit"></el-input>
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
        <img src="https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png"
             class="image" alt="">
        <div style="padding: 14px;">
          <span>{{ config.channelTypeName }}</span>
          <el-breadcrumb separator="/" class="channel-config-item-bc">
            <el-breadcrumb-item>{{ config.costRate }}%</el-breadcrumb-item>
            <el-breadcrumb-item>{{ config.costFee }}元</el-breadcrumb-item>
          </el-breadcrumb>
          <div class="bottom clearfix">
            <el-button type="text" class="button" @click="handleEditChannelConfig(config)">详情</el-button>
            <el-button type="text" class="button" @click="handleEditClientConfig(config)">参数设置</el-button>
            <el-button type="text" class="button" disabled>支付测试</el-button>
          </div>
        </div>
      </el-card>
    </el-card>

    <!-- 添加或修改支付通道 -->
    <el-dialog :title="channelConfigTitle" :visible.sync="channelConfigDialogOpen" width="840px" append-to-body>
      <el-form ref="channelConfigRef" :model="channelConfig" :rules="channelConfigRules" label-width="120px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="支付类型" prop="channelType">
              <el-select v-model="channelConfig.channelType" placeholder="请选择支付类型" clearable class="form-item">
                <el-option
                  v-for="dict in dict.type['pay_channel_type']"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="通道状态" prop="status">
              <el-select v-model="channelConfig.status" placeholder="请选择通道状态" clearable class="form-item">
                <el-option
                  v-for="dict in dict.type['sys_normal_disable']"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="费率" prop="costRate">
              <el-input v-model="channelConfig.costRate" placeholder="请输入费率" class="form-item" >
                <template v-slot:append>%</template>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="手续费" prop="costFee">
              <el-input type="number" min="0" v-model="channelConfig.costFee" placeholder="请输入手续费" class="form-item" >
                <template v-slot:append>元</template>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="最大手续费" prop="maxFee">
              <el-input type="number" min="0" v-model="channelConfig.maxFee" placeholder="请输入最大手续费" class="form-item" >
                <template v-slot:append>元</template>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="最低手续费" prop="minFee">
              <el-input type="number" min="0" v-model="channelConfig.minFee" placeholder="请输入最低手续费" class="form-item">
                <template v-slot:append>元</template>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="最大费率" prop="maxRate">
              <el-input v-model="channelConfig.maxRate" placeholder="请输入最大费率" class="form-item">
                <template v-slot:append>%</template>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="最小费率" prop="minRate">
              <el-input v-model="channelConfig.minRate" placeholder="请输入最低费率" class="form-item">
                <template v-slot:append>%</template>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="日限额" prop="dailyLimit">
              <el-input type="number" min="0" v-model="channelConfig.dailyLimit" placeholder="请输入日限额" class="form-item">
                <template v-slot:append>元</template>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="日订单限额" prop="monthLimit">
              <el-input type="number" min="0" v-model="channelConfig.dailyOrderLimit" placeholder="请输入日订单限额" class="form-item">
                <template v-slot:append>笔</template>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="单笔最低限额" prop="singleMinLimit">
              <el-input type="number" min="0" v-model="channelConfig.singleMinLimit" placeholder="请输入单笔最低限额" class="form-item">
                <template v-slot:append>元</template>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="单笔最大限额" prop="singleMaxLimit">
              <el-input type="number" min="0" v-model="channelConfig.singleMaxLimit" placeholder="请输入单笔最大限额" class="form-item">
                <template v-slot:append>元</template>
              </el-input>
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

    <!-- 添加或修改支付客户端配置 -->
    <el-dialog :title="clientConfigTitle" :visible.sync="clientConfigDialogOpen" width="900px" append-to-body>
      <el-form ref="clientConfigRef" :model="clientConfig" :rules="clientConfigRules" label-width="120px">
        <el-form-item label="支付类型" prop="channelType">
          <el-select v-model="clientConfig.channelType" placeholder="请选择支付类型" clearable class="form-item" disabled>
            <el-option
              v-for="dict in dict.type['pay_channel_type']"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-row :gutter="20">
          <el-col :span="18">
            <el-form-item label="服务地址" prop="serviceUrl">
              <el-input v-model="clientConfig.serviceUrl" style="width: 380px" placeholder="请输入服务地址" clearable></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="运用id" prop="appId">
              <el-input v-model="clientConfig.appId" class="form-item" placeholder="请输入运用id" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="代理编号" prop="agentId">
              <el-input v-model="clientConfig.agentId" class="form-item" placeholder="请输入代理id" clearable></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="公钥" prop="publicKey">
          <el-input type="textarea" v-model="clientConfig.publicKey" :autosize="{ minRows: 2}" style="width: 680px"></el-input>
        </el-form-item>
        <el-form-item label="私钥" prop="privateKey">
          <el-input type="textarea" v-model="clientConfig.privateKey" :autosize="{ minRows: 3}" style="width: 680px"></el-input>
        </el-form-item>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="签名类型" prop="signType">
              <el-input v-model="clientConfig.signType" class="form-item" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="签名秘钥" prop="signKey">
              <el-input v-model="clientConfig.signKey" class="form-item" placeholder="请输入签名秘钥" clearable></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="其他配置" prop="metedata">
          <el-input type="textarea" v-model="clientConfig.metedata" :autosize="{ minRows: 6}" style="width: 680px"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitClientConfig">确 定</el-button>
        <el-button @click="cancelClientConfig">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  addPayChannelConfig,
  editPayCclientConfig,
  getPayChannel,
  updatePayChannel,
  updatePayChannelConfig
} from "@/api/pay/channel";
import {selectDictLabel} from "@/utils/ruoyi";

export default {
  name: "channelConfig",
  dicts: ['sys_normal_disable', 'pay_channel_type'],
  data() {
    return {
      payChannelId: undefined,
      // 支付通道
      editChannel: false,
      payChannel: {
        payChannelId: undefined,
        name: undefined,
        code: undefined,
        status: 0,
        dailyLimit: 0,
        monthLimit: 0
      },
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
      channelConfig: {},
      channelConfigRules: {
        channelType: [
          {required: true, message: "支付类型不能为空", trigger: "blur"}
        ],
        code: [
          {required: true, message: "渠道编码不能为空", trigger: "blur"}
        ],
        costRate: [
          {required: true, message: "成本费率不能为空", trigger: "blur"},
        ],
        costFee: [
          {required: true, message: "成本手续费不能为空", trigger: "blur"},
        ],
        minFee: [
          {required: true, message: "最低手续费不能为空", trigger: "blur"},
        ],
        maxFee: [
          {required: true, message: "最大手续费不能为空", trigger: "blur"},
        ]
      },
      // 客户端配置
      clientConfigTitle: "",
      clientConfigDialogOpen: false,
      clientConfig: {},
      clientConfigRules: {

      }
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
    },
    payChannelConfigs() {
      if (this.payChannel.configs) {
        return this.payChannel.configs.map(channelConfig => ({
          ...channelConfig,
          costRate: this.mulpitly100(channelConfig.costRate),
          minRate: this.mulpitly100(channelConfig.minRate),
          maxRate: this.mulpitly100(channelConfig.maxRate),
          channelTypeName: selectDictLabel(this.dict.type['pay_channel_type'], channelConfig.channelType)
        }));
      }
      return [];
    },
  },
  methods: {
    /**
     * 获取支付通道
     */
    getPayChannel() {
      getPayChannel({id: this.payChannelId}).then(response => {
        this.payChannel = response.data
        this.payChannel.status = response.data.status.toString()
      })
    },
    /**
     * 提交通道配置
     */
    submitPayChannel() {
      this.$refs.payChannelRef.validate(valid => {
        if (valid) {
          updatePayChannel(this.payChannel).then(() => {
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
    /** 取消新增按钮操作 */
    cancelChannelConfig() {
      this.channelConfigDialogOpen = false;
      this.resetChannelConfig();
    },
    /**
     * 修改支付配置
     *
     * @param item 支付配置
     */
    handleEditChannelConfig(item) {
      this.channelConfig = item
      this.channelConfig.status = item.status.toString()
      this.dateRange = [item.startTime, item.endTime]
      this.channelConfigDialogOpen = true;
      this.channelConfigTitle = "修改支付配置";
    },
    // 表单重置
    resetChannelConfig() {
      this.dateRange = ['00:00:00', '23:59:59']
      this.channelConfig = {
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
      this.resetForm("channelConfig");
    },
    /**
     * 提交支付类型配置
     */
    submitChannelConfig() {
      this.$refs.channelConfigRef.validate(valid => {
        if (valid) {
          const data = {...this.channelConfig};
          data.payChannelId = this.payChannelId
          data.startTime = this.dateRange[0]
          data.endTime = this.dateRange[1]
          data.costRate = this.divide100(data.costRate)
          data.minRate = this.divide100(data.minRate)
          data.maxRate = this.divide100(data.maxRate)

          if (data.channelConfigId) {
            updatePayChannelConfig(data).then(() => {
              this.channelConfigDialogOpen = false
              this.$modal.msgSuccess("修改成功");
              this.getPayChannel()
            })
          } else {
            addPayChannelConfig(data).then(() => {
              this.channelConfigDialogOpen = false
              this.$modal.msgSuccess("新增成功");
              this.getPayChannel()
            })
          }
        }
      })
    },
    /**
     * 乘以一百
     */
    mulpitly100(value) {
      return value ? value * 100 : value
    },
    /**
     * 除以一百
     */
    divide100(value) {
      return value ? value / 100 : value
    },

    /** 通道客户端编辑按钮操作 */
    handleEditClientConfig(channelConfig) {
      this.resetClientConfig();
      this.clientConfig.payChannelId = this.payChannelId
      if (channelConfig.clientConfig) {
        this.clientConfig = channelConfig.clientConfig
      } else {
        this.clientConfig.channelConfigId = channelConfig.channelConfigId
        this.clientConfig.channelType = channelConfig.channelType
      }
      this.clientConfigDialogOpen = true;
      this.clientConfigTitle = "编辑客户端配置";
    },
    /** 取消新增按钮操作 */
    cancelClientConfig() {
      this.clientConfigDialogOpen = false;
      this.resetClientConfig();
    },
    submitClientConfig() {
      this.$refs.clientConfigRef.validate(valid => {
          if (valid) {
            editPayCclientConfig(this.clientConfig).then(() => {
              this.clientConfigDialogOpen = false
              this.$modal.msgSuccess("新增成功");
              this.getPayChannel()
            })
          }
        }
      )
    },
    // 表单重置
    resetClientConfig() {
      this.clientConfig = {
        serviceUrl: undefined,
        appId: undefined,
        agentId: undefined,
        publicKey: undefined,
        privateKey: undefined,
        signType: undefined,
        signKey: undefined,
        metedata: undefined,
      };
      this.resetForm("clientConfig");
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
  width: 240px;
}

.channel-config-item-bc {
  margin-top: 8px;
}
</style>
