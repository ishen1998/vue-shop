<template>
  <div class="root-container">
    <Headline title="用户管理" label="活动新增/编辑" />
    <div class="from-group clearfix">
      <div class="form-item">
        <span>活动名称：</span>
        <el-input placeholder="请输入内容" class="form-input" v-model="activityName"></el-input>
      </div>
      <div class="form-item">
        <span>开启时间：</span>
        <el-date-picker
          class="form-datetime"
          v-model="startTime"
          type="datetime"
          placeholder="选择日期时间"
          size
        ></el-date-picker>
      </div>
      <div class="form-item">
        <span>结束时间：</span>
        <el-date-picker
          class="form-datetime"
          v-model="endTime"
          type="datetime"
          placeholder="选择日期时间"
        ></el-date-picker>
      </div>
      <div class="form-item">
        <span>活动封面</span>
        <el-upload
          action="http://192.168.3.88:8089/shop/uploadFile"
          list-type="picture-card"
          :file-list="fileList"
          :auto-upload="true"
          :limit="1"
          name="file"
          :on-success="upLoad"
        >
          <i slot="default" class="el-icon-plus"></i>
          <div slot="file" style="width: 100%;height: 100%;" slot-scope="{file}">
            <img
              class="el-upload-list__item-thumbnail"
              style="width: 100%;height: 100%;"
              :src="file.url"
              alt
            />
            <span class="el-upload-list__item-actions">
              <span
                v-if="!disabled"
                class="el-upload-list__item-delete"
                @click="handleDownload(file)"
              >
                <i class="el-icon-download"></i>
              </span>
              <span
                v-if="!disabled"
                class="el-upload-list__item-delete"
                @click="handleRemove(file)"
              >
                <i class="el-icon-delete"></i>
              </span>
            </span>
          </div>
        </el-upload>
        <el-dialog :visible.sync="dialogVisible">
          <img width="100%" height="100%" :src="dialogImageUrl" alt />
        </el-dialog>
        <div class="toast">
          <p>限上传一张缩略图</p>
          <p>图片尺寸最佳为180X180像素</p>
        </div>
      </div>
      <div class="form-item">
        <span>活动规则：</span>
        <textarea v-model="activityRule"></textarea>
      </div>
    </div>
    <button type="button" @click="activitySubmit" class="add-button">添加</button>
    <listPanel class="grap" title="活动商品">
      <div>
        <el-table
          :data="tableData"
          :style="{ width: '100%' }"
          header-align="center"
          :row-style="{height: '164px'}"
          class="table-container"
        >
          <el-table-column align="center" prop="itemId" label="编号"></el-table-column>
          <el-table-column align="center" prop="itemName" label="名称"></el-table-column>
          <el-table-column align="center" label="缩略图">
            <template slot-scope="scope">
              <img style="width: 100%;height: 100%;" :src="scope.row.coverImg" alt />
            </template>
          </el-table-column>
          <el-table-column align="center" prop="price" label="价格"></el-table-column>
          <el-table-column align="center" prop="avatar" label="规格属性"></el-table-column>
          <el-table-column align="center" prop="categoryId" label="分类"></el-table-column>
          <el-table-column align="center" label="操作">
            <template slot-scope="scope">
              <div class="opeartion-container">
                <el-tag
                  type="danger"
                  @click="changStatus(scope.row.itemId, scope.row.isShow, 'Y', scope.$index)"
                >上架</el-tag>
                <el-tag
                  type="success"
                  @click="changStatus(scope.row.itemId, scope.row.isShow, 'N', scope.$index)"
                >下架</el-tag>
                <el-tag type="success" @click="deleteCommodity(scope.$index, scope.row)">删除</el-tag>
                <el-tag type="success" @click="editCommodity(scope.row.itemId)">编辑</el-tag>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </listPanel>
    <div class="control-container">
      <el-button type="danger" @click="handleSub('N')">保存</el-button>
      <el-button type="danger" @click="handleSub('Y')">保存并开启</el-button>
      <el-button type="info" @click="handleCancle">取消</el-button>
    </div>
    <el-dialog
      title="提示"
      :visible.sync="centerDialogVisible"
      width="60%"
      class="dialog"
      center
      :lockScroll="false"
    >
      <listPanel class="grap" :title="dialogTitle">
        <el-table
          class="table-container"
          ref="multipleTable"
          :data="tableList"
          :row-key="getRowKeys"
          style="width: 100%"
          @selection-change="handleSelectionChange"
        >
          <el-table-column :reserve-selection="true" type="selection" width="55"></el-table-column>
          <el-table-column prop="itemId" align="center" label="编号" width="120"></el-table-column>
          <el-table-column align="center" prop="itemName" label="名称" width="120"></el-table-column>
          <el-table-column align="center" label="缩略图" show-overflow-tooltip>
            <template slot-scope="scope">
              <img style="width: 100%;height: 100%;" :src="scope.row.coverImg" alt />
            </template>
          </el-table-column>
          <el-table-column align="center" prop="price" label="价格" show-overflow-tooltip></el-table-column>
          <el-table-column align="center" prop="address" label="规格属性" show-overflow-tooltip></el-table-column>
          <el-table-column align="center" prop="categoryId" label="分类" show-overflow-tooltip></el-table-column>
        </el-table>
      </listPanel>
      <Pageinate :pageSize="pageSize" @receiveCurrent="handleCurrentChange" :total="selectRecord" />
      <span slot="footer" class="dialog-footer">
        <el-button type="default" @click="centerDialogVisible = false">取 消</el-button>
        <el-button type="default" @click="handleConfirm">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import Headline from '../../../components/headline.vue'
import listPanel from '../../../components/list.vue'
import Pageinate from '../../../components/paginate.vue'
import {
  remove,
  examineInfoComlete,
  alertInfo
} from '../../../assets/js/common'
import CommodityModel from '../../../Http/commoditymanage/commodityManage/commodityManage'

export default {
  data () {
    return {
      activityName: '',
      startTime: '',
      endTime: '',
      activityBanner: [],
      activityRule: '',
      dialogVisible: false,
      dialogImageUrl: '',
      tableData: [],
      centerDialogVisible: false,
      selectRecord: 30,
      pageSize: 5,
      dialogTitle: '',
      fileList: [],
      tableList: [],
      checkList: [],
      currentChange: false,
      disabled: false,
      verify: [
        { activityName: '活动名称为空' },
        { startTime: '开始时间为空' },
        { endTime: '结束时间为空' },
        { fileList: '缩略图为空' },
        { activityRule: '活动规则为空' },
        { tableData: '商品列表为空' }
      ]
    }
  },
  components: {
    Headline,
    listPanel,
    Pageinate
  },
  methods: {
    handleDownload (file) {},
    handleRemove (file) {},
    getRowKeys (row) {
      return row.itemId
    },
    // 图片上传
    upLoad (response, file, fileList) {
      let img = {}
      img.url = img.name = response.data
      this.fileList[0] = img
    },
    // 将日期格式化函数
    formatDate (date, fmt) {
      // author: meizz
      var o = {
        'M+': date.getMonth() + 1, // 月份
        'd+': date.getDate(), // 日
        'h+': date.getHours(), // 小时
        'm+': date.getMinutes(), // 分
        's+': date.getSeconds(), // 秒
        'q+': Math.floor((date.getMonth() + 3) / 3), // 季度
        S: date.getMilliseconds() // 毫秒
      }
      if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(
          RegExp.$1,
          (date.getFullYear() + '').substr(4 - RegExp.$1.length)
        )
      }
      for (var k in o) {
        if (new RegExp('(' + k + ')').test(fmt)) {
          fmt = fmt.replace(
            RegExp.$1,
            RegExp.$1.length === 1
              ? o[k]
              : ('00' + o[k]).substr(('' + o[k]).length)
          )
        }
      }
      return fmt
    },
    // 根据页码获取数据
    getPageData (index) {
      this.$http.activityManage.getAllPX('', index, this.pageSize).then(res => {
        this.selectRecord = res.data.data.itemCount
        this.tableList = res.data.data.itemInfos
      })
    },
    // 默认选中
    toggleSelection (rows) {
      if (rows) {
        rows.forEach(row => {
          this.$refs.multipleTable.toggleRowSelection(row, true)
        })
      }
    },
    // 点击添加按钮，将dialog显示
    activitySubmit () {
      this.centerDialogVisible = true
      this.getPageData(1)
      this.$nextTick(() => {
        this.toggleSelection(this.checkList)
      })
    },
    // dialog中页码改变重新获取数据
    handleCurrentChange ({ currentPage }) {
      this.getPageData(currentPage)
    },
    // 获取选中的商品
    handleSelectionChange (val) {
      this.checkList = val
    },
    // dialog中点击保存
    handleConfirm () {
      this.tableData = [...this.checkList]
      this.centerDialogVisible = false
    },
    // 页面保存按钮
    handleSub (status) {
      const suc = examineInfoComlete.call(this, this.verify)
      if (suc !== true) {
        this.$message({
          type: 'warning',
          message: suc,
          lockScroll: false
        })
        return
      }
      const activityItem = []
      this.tableData.forEach(item => {
        activityItem.push({
          itemId: item.itemId,
          activityPrice: item.price
        })
      })
      const mock = {
        activityInfo: {
          activityName: this.activityName,
          activityImg: this.fileList[0].url,
          startTime: this.formatDate(
            new Date(this.startTime),
            'yyyy-MM-dd hh:mm:ss'
          ),
          endTime: this.formatDate(
            new Date(this.endTime),
            'yyyy-MM-dd hh:mm:ss'
          ),
          activityStatus: status,
          rule: this.activityRule
        },
        activityItem
      }
      if (this.mode === 'update') {
        mock.activityInfo.activityId = this.$route.params.id
      }
      this.$http.activityManage.addCommodity(mock).then(res => {
        this.$message({
          type: 'success',
          message: '保存成功',
          lockScroll: false
        })
        this.$router.back(-1)
      })
    },
    // 点击取消，路由跳转
    handleCancle () {
      this.$router.back(-1)
    },
    // 删除一个活动商品
    deleteCommodity (index, item) {
      remove.call(this, () => {
        this.tableData.splice(index, 1)
      })
    },
    // 改变商品的状态
    changStatus (itemId, isShow, expect, index) {
      if (this.statusJuage(itemId, isShow, expect)) {
        CommodityModel.changeCommodityStatus(
          {
            itemId,
            isShow: expect
          },
          ({ data }) => {
            this.tableData[index].isShow = expect
            alertInfo.call(this, {
              type: 'success',
              title: '更改状态成功',
              cont: expect === 'Y' ? '商品上架成功' : '商品下架成功'
            })
          },
          function () {
            alertInfo.call(this, {
              type: 'success',
              cont: '更改失败！'
            })
          }
        )
      }
    },
    // 验证传入参数
    statusJuage (itemId, isShow, type) {
      if (itemId === undefined || isShow === undefined) {
        alertInfo.call(this, {
          type: 'error',
          cont: '系统错误'
        })
        return false
      }
      if (isShow === type) {
        alertInfo.call(this, {
          type: 'warning',
          cont: type === 'Y' ? '商品已上架' : '商品已下架'
        })
        return false
      }
      return true
    },
    // 跳转到编辑商品页
    editCommodity (id) {
      this.$router.push({
        path: '/editCommodity',
        query: {
          commodityId: id,
          type: 'edit'
        }
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.from-group {
  box-sizing: border-box;
  padding: 0px 13px;
  > div {
    margin-top: 20px;
    width: 660px;
  }
  .form-item {
    line-height: 20px;
    border-radius: 4px;
    text-align: left;
    box-sizing: border-box;
    width: 100%;
    border: 1px solid rgba(187, 187, 187, 1);
    padding: 10px 20px;
    display: flex;
    align-items: center;
    > span,
    > div {
      margin: 0px 15px;
    }
    .form-datetime,
    .form-input,
    textarea {
      width: 600px;
    }
    textarea {
      height: 114px;
      border-radius: 10px;
    }
  }
}
.add-button {
  position: absolute;
  border: 1px solid #ccc;
  right: 50px;
  margin-top: 20px;
  z-index: 100;
  width: 100px;
  height: 30px;
  cursor: pointer;
}
.control-container {
  justify-content: space-around;
  display: flex;
  margin: 40px 300px;
  text-align: center;
  border: none;
  margin-bottom: 120px;
  > button {
    width: 130px;
    height: 50px;
  }
}
.dialog {
  background: none;
}
</style>
