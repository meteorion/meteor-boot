<template>
  <div class="app-container">
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>基本信息</span>
        <el-button style="float: right; padding: 3px 0" type="text">编辑</el-button>
      </div>
      <el-form :model="channelConfigForm" :rules="channelConfigRules" ref="channelConfigRule" size="small" label-width="100px" class="card-form">
        <el-col :span="8">
          <el-form-item label="通道名称" prop="name">
            <el-input v-model="channelConfigForm.name" style="width: 260px"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="通道代号" prop="code">
            <el-input v-model="channelConfigForm.code" style="width: 260px"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="通道状态" prop="status">
            <el-input v-model="channelConfigForm.status" style="width: 260px"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="日限额" prop="status">
            <el-input v-model="channelConfigForm.status" style="width: 260px"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="月限额" prop="status">
            <el-input v-model="channelConfigForm.status" style="width: 260px"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item>
            <el-button type="primary" size="small" @click="submitPayChannel">提交</el-button>
            <el-button size="small" @click="resetChannelConfigForm">重置</el-button>
          </el-form-item>
        </el-col>
      </el-form>
    </el-card>

    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>支付通道</span>
        <el-button style="float: right; padding: 3px 0" type="text">添加通道</el-button>
      </div>
      <el-card class="box-card-item" :body-style="{ padding: '0px' }">
        <img src="https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png" class="image">
        <div style="padding: 14px;">
          <span>好吃的汉堡</span>
          <div class="bottom clearfix">
            <time class="time">{{ currentDate }}</time>
            <el-button type="text" class="button">操作按钮</el-button>
          </div>
        </div>
      </el-card>
      <el-card class="box-card-item" :body-style="{ padding: '0px' }">
        <img src="https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png" class="image">
        <div style="padding: 14px;">
          <span>好吃的汉堡</span>
          <div class="bottom clearfix">
            <time class="time">{{ currentDate }}</time>
            <el-button type="text" class="button">操作按钮</el-button>
          </div>
        </div>
      </el-card>
      <el-card class="box-card-item" :body-style="{ padding: '0px' }">
        <img src="https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png" class="image">
        <div style="padding: 14px;">
          <span>好吃的汉堡</span>
          <div class="bottom clearfix">
            <time class="time">{{ currentDate }}</time>
            <el-button type="text" class="button">操作按钮</el-button>
          </div>
        </div>
      </el-card>
      <el-card class="box-card-item" :body-style="{ padding: '0px' }">
        <img src="https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png" class="image">
        <div style="padding: 14px;">
          <span>好吃的汉堡</span>
          <div class="bottom clearfix">
            <time class="time">{{ currentDate }}</time>
            <el-button type="text" class="button">操作按钮</el-button>
          </div>
        </div>
      </el-card>
      <el-card class="box-card-item" :body-style="{ padding: '0px' }">
        <img src="https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png" class="image">
        <div style="padding: 14px;">
          <span>好吃的汉堡</span>
          <div class="bottom clearfix">
            <time class="time">{{ currentDate }}</time>
            <el-button type="text" class="button">操作按钮</el-button>
          </div>
        </div>
      </el-card>
      <el-card class="box-card-item" :body-style="{ padding: '0px' }">
        <img src="https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png" class="image">
        <div style="padding: 14px;">
          <span>好吃的汉堡</span>
          <div class="bottom clearfix">
            <time class="time">{{ currentDate }}</time>
            <el-button type="text" class="button">操作按钮</el-button>
          </div>
        </div>
      </el-card>
      <el-card class="box-card-item" :body-style="{ padding: '0px' }">
        <img src="https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png" class="image">
        <div style="padding: 14px;">
          <span>好吃的汉堡</span>
          <div class="bottom clearfix">
            <time class="time">{{ currentDate }}</time>
            <el-button type="text" class="button">操作按钮</el-button>
          </div>
        </div>
      </el-card>
      <el-card class="box-card-item" :body-style="{ padding: '0px' }">
        <img src="https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png" class="image">
        <div style="padding: 14px;">
          <span>好吃的汉堡</span>
          <div class="bottom clearfix">
            <time class="time">{{ currentDate }}</time>
            <el-button type="text" class="button">操作按钮</el-button>
          </div>
        </div>
      </el-card>
      <el-card class="box-card-item" :body-style="{ padding: '0px' }">
        <img src="https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png" class="image">
        <div style="padding: 14px;">
          <span>好吃的汉堡</span>
          <div class="bottom clearfix">
            <time class="time">{{ currentDate }}</time>
            <el-button type="text" class="button">操作按钮</el-button>
          </div>
        </div>
      </el-card>
    </el-card>
  </div>
</template>

<script>
import {getPayChannel} from "@/api/pay/channel";

export default {
  name: "channelConfig",
  data() {
    return {
      channelConfigForm: {

      },
      channelConfigRules: [

      ]
    }
  },
  created() {
    this.getPayChannel(this.$route.query.channelId)
  },
  methods: {
    /**
     * 获取支付通道
     */
    getPayChannel(id) {
      getPayChannel({id: id}).then(response => {
        console.log(response)
      })
    },
    /** 重置按钮操作 */
    resetChannelConfigForm() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /**
     * 提交通道配置
     */
    submitPayChannel() {

    }
  }
}
</script>

<style scoped lang="scss">
.box-card {
  margin-bottom: 30px;
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
</style>
