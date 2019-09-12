<template>
  <div class="root-container addNewCard-container">
    <el-form
      :model="ruleForm"
      :rules="rules"
      ref="ruleForm"
      label-width="120px"
      class="addNewCard-form"
      label-position="left"
      :show-message="true"
    >
      <el-form-item label="创建人" prop="createdBy" :hide-required-asterisk="true">
        <el-input v-model="ruleForm.createdBy" placeholder="请输入创建人姓名"></el-input>
      </el-form-item>
      <el-form-item label="卡劵标题" prop="ticketTitle" :hide-required-asterisk="true">
        <el-input v-model="ruleForm.ticketTitle" placeholder="请输入内容"></el-input>
      </el-form-item>
      <el-form-item label="减免规则" prop="ticketRule" :hide-required-asterisk="true">
        <el-input-number
          :min="0"
          size="medium"
          v-model="ruleForm.ticketRule[0]"
          placeholder="订单满足金额"
        ></el-input-number>
        <el-input-number :min="0" size="medium" v-model="ruleForm.ticketRule[1]" placeholder="折扣价格"></el-input-number>
      </el-form-item>
      <el-form-item label="卡劵折扣时间" prop="datetimeValue" :hide-required-asterisk="true">
        <el-col :span="11">
          <el-date-picker
            value-format="timestamp"
            v-model="ruleForm.datetimeValue"
            type="datetimerange"
            :picker-options="timePickerOptions"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
          ></el-date-picker>
        </el-col>
      </el-form-item>
      <el-form-item label="发布启用" prop="isEnable" :hide-required-asterisk="true">
        <el-switch v-model="ruleForm.isEnable"></el-switch>
      </el-form-item>
      <el-form-item label="卡劵面值" prop="preferentialFee" :hide-required-asterisk="true">
        <el-input-number
          size="medium"
          :precision="2"
          :step="0.1"
          :min="0"
          v-model="ruleForm.preferentialFee"
        ></el-input-number>
      </el-form-item>
      <el-form-item label="卡劵数量" prop="ticketNum" required :hide-required-asterisk="true">
        <el-input-number size="medium" v-model="ruleForm.ticketNum"></el-input-number>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')">立即创建</el-button>
        <el-button @click="resetForm('ruleForm')">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { examineItem, alertInfo } from '../../assets/js/common'
import cardModel from './../../Http/cardManage/index'
export default {
  name: 'addNewCard',
  data () {
    return {
      timePickerOptions: {
        shortcuts: [
          {
            text: '最近一周',
            onClick (picker) {
              const end = new Date()
              const start = new Date()
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
              picker.$emit('pick', [start, end])
            }
          },
          {
            text: '最近一个月',
            onClick (picker) {
              const end = new Date()
              const start = new Date()
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
              picker.$emit('pick', [start, end])
            }
          },
          {
            text: '最近三个月',
            onClick (picker) {
              const end = new Date()
              const start = new Date()
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
              picker.$emit('pick', [start, end])
            }
          }
        ]
      },
      ruleForm: {
        createdBy: '',
        title: '',
        ticketRule: [],
        isEnable: false,
        datetimeValue: null,
        preferentialFee: 0,
        ticketNum: 0
      },
      rules: {
        createdBy: [
          { required: true, message: '请输入创建人名称', trigger: 'blur' },
          { min: 2, max: 8, message: '请输入合法姓名', trigger: 'blur' }
        ],
        ticketTitle: [
          {
            required: true,
            message: '请选择卡劵标题',
            trigger: 'blur'
          }
        ],
        datetimeValue: [
          {
            type: 'array',
            required: true,
            message: '请选择开启时间和结束时间',
            trigger: 'change'
          }
        ],
        ticketRule: [
          {
            type: 'array',
            required: true,
            message: '请填写卡劵满减规则',
            trigger: 'change'
          }
        ],
        isEnable: [
          {
            type: 'boolean',
            required: true,
            message: '请选择是否开启卡劵',
            trigger: 'change'
          }
        ],
        preferentialFee: [
          {
            type: 'number',
            required: true,
            message: '请输入卡劵面值',
            trigger: 'change'
          }
        ],
        ticketNum: [
          {
            required: true,
            type: 'number',
            message: '请填写卡劵数量',
            trigger: 'blur'
          }
        ]
      }
    }
  },
  methods: {
    // 表单数据效验
    formInfoVerify () {
      // 对应的错误信息表
      for (var key in this.rules) {
        if (examineItem.call(this['ruleForm'], key)) {
          alertInfo.call(this, {
            type: 'error',
            cont: this.rules[key][0].message
          })
          return false
        }
      }
      // 判断卡劵规则是否填写并符合规则
      if (this.ruleForm.ticketRule[0] > 0 && this.ruleForm.ticketRule[1] > 0) {
        if (this.ruleForm.ticketRule[0] <= this.ruleForm.ticketRule[1]) {
          return alertInfo.call(this, {
            type: 'error',
            cont: '订单满减金额必须大于卡劵金额'
          })
        }
        // 验证成功，返回true并发送验证请求
        return true
      }
      return alertInfo.call(this, {
        type: 'error',
        cont: this.rules.ticketRule[0].message
      })
    },
    // 组织个人信息
    assignSendInfo () {
      return {
        createdBy: this.ruleForm.createdBy,
        ticketTitle: this.ruleForm.ticketTitle,
        startTime: this.ruleForm.datetimeValue[0],
        endTime: this.ruleForm.datetimeValue[1],
        preferentialFee: this.ruleForm.preferentialFee,
        ticketStatus: this.ruleForm.ticketStatus === true ? 'Y' : 'N',
        ticketNum: this.ruleForm.ticketNum,
        ticketRule: this.ruleForm.ticketRule.toString()
      }
    },
    // 表单提交验证
    submitForm () {
      if (!this.formInfoVerify()) {
        return false
      }
      let cardInfo = this.assignSendInfo()
      cardModel.addCustomerCard(cardInfo, ({ data: { data } }) => {
        this.$confirm('添加成功', '提示', {
          confirmButtonText: '返回卡劵页面',
          cancelButtonText: '继续添加',
          type: 'success',
          lockScroll: false
        })
          .then(() => {
            this.$router.go(-1)
          })
          .catch(() => {})
      })
      // 执行添加操作
    },
    resetForm (formName) {
      this.$refs[formName].resetFields()
    }
  },
  created () {}
}
</script>

<style lang="scss" scoped>
.addNewCard-container {
  padding: 50px 0;
  padding-left: 30px;
  background-color: white;
  .addNewCard-form {
    width: 60%;
  }
}
</style>
