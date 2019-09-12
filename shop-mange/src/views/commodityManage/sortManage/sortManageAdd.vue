<template>
  <div class="sortManageAdd root-container">
    <Headline title="分类列表" :label="title"></Headline>
    <div class="from-group clearfix">
      <div class="form-item">
        <span>{{nameTitle}}：&nbsp;&nbsp;&nbsp;</span>
        <el-input :disabled="true" class="form-input" :value="name"></el-input>
      </div>
      <div class="form-item">
        <span>父级分类：</span>
        <el-select v-model="selectSort" placeholder="请选择">
          <el-option v-for="item in sortOption" :key="item" :label="item" :value="item"></el-option>
        </el-select>
      </div>
      <div class="form-item">
        <span>分类名称：</span>
        <el-input placeholder="请输入内容" class="form-input" v-model="sortName"></el-input>
      </div>
      <div class="form-item">
        <span>分类图片</span>
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
      <div class="control-container">
        <el-button type="danger" @click="handleSub('N')">保存</el-button>
        <el-button type="danger" @click="handleSub('Y')">保存并开启</el-button>
        <el-button type="info" @click="handleCancle">取消</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import Headline from '../../../components/headline.vue'
import { examineInfoComlete } from '../../../assets/js/common'
export default {
  data () {
    return {
      title: '新增分类',
      nameTitle: '创建人',
      name: '',
      sortName: '',
      selectSort: '',
      sortOption: [],
      fileList: [],
      disabled: false,
      dialogVisible: false,
      dialogImageUrl: '',
      verify: [
        { sortName: '分类名称为空' },
        { selectSort: '未选择父级分类' },
        { fileList: '未添加分类图片' }
      ]
    }
  },
  created () {
    this.name = this.$store.state.username
    this.$http.sortManage.sortAll().then(res => {
      this.sortOption = res.data.data
    })
  },
  methods: {
    handleSub (status) {
      const suc = examineInfoComlete.call(this, this.verify)
      if (suc !== true) {
        this.$message({
          type: 'warning',
          message: suc
        })
        return
      }
      const data = {
        categoryName: this.sortName,
        categoryStatus: status,
        categoryImage: this.fileList[0].url,
        listParent: this.selectSort
      }
      if (this.mode === 'update') {
        data.updateBy = this.name
        data.categoryId = this.id
        this.$http.sortManage.update(data).then(res => {
          this.$message({
            type: 'success',
            message: '修改成功'
          })
          this.$router.back(-1)
        })
        return
      }
      data.createBy = this.name
      this.$http.sortManage.sortAdd(data).then(res => {
        this.$message({
          type: 'success',
          message: '添加成功'
        })
        this.$router.back(-1)
      })
    },
    upLoad (response, file, fileList) {
      let img = {}
      img.url = img.name = response.data
      this.fileList[0] = img
    },
    handleDownload (file) {

    },
    handleRemove (file) {

    },
    handleCancle () {
      this.$router.back(-1)
    }
  },
  components: {
    Headline
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
}
</style>
