<template>
  <div class="root-container">
    <Headline title="商品管理" :label="pageTitle" />
    <div class="from-group clearfix">
      <div class="form-item">
        <span>商品名称：</span>
        <el-input :disabled="isDisable" placeholder="请输入商品名称" class="form-input" v-model="goodName"></el-input>
      </div>
      <div class="form-item">
        <span>商品价格：</span>
        <el-input
          :disabled="isDisable"
          placeholder="请输入商品价格"
          class="form-input"
          v-model="goodPrice"
        ></el-input>
      </div>
      <div class="form-item">
        <span>商品数量：</span>
        <el-input-number :disabled="isDisable" v-model="num" label="请输入商品数量"></el-input-number>
      </div>
      <div class="form-item">
        <span>商品产地：</span>
        <el-input :disabled="isDisable" placeholder="请输入商品产地" class="form-input" v-model="locality"></el-input>
      </div>
      <div class="form-item">
        <span>活动：</span>
        <!-- 是否可以选择 -->
        <el-checkbox :disabled="isDisable" v-model="isActivity">是否加入活动</el-checkbox>
      </div>
      <!-- 分类 -->
      <div class="form-item">
        <span>分类：</span>
        <el-select :disabled="isDisable" v-model="selectCategory" placeholder="请选择">
          <el-option
            v-for="item in categoryOPtions"
            :key="item.listName"
            :label="item.listName"
            :value="item.listName"
          ></el-option>
        </el-select>
      </div>
      <!-- 封面图 -->
      <div class="form-item">
        <span>封面图：</span>
        <el-upload
          :action="uploadUrl"
          :headers="{ ContentType: 'multipart/form-data' }"
          name="file"
          list-type="picture-card"
          :auto-upload="false"
          :limit="1"
          :file-list="coverImg"
          :on-success="fileUploadSuccess.bind(this, 'cover')"
          :on-error="fileUploadError"
          ref="coverImg"
          :onChange="onCoverImgChange"
          :disabled="isDisable"
        >
          <i slot="default" class="el-icon-plus"></i>
          <div slot="file" slot-scope="{file}">
            <img :src="file.url" class="cover-cont" />
            <span class="el-upload-list__item-actions">
              <a
                v-if="!coverDisabeld"
                class="el-upload-list__item-delete download-cont"
                :href="file.url"
                download
              >
                <i class="el-icon-download"></i>
              </a>
              <span
                v-if="!coverDisabeld"
                class="el-upload-list__item-delete"
                @click="handleRemove(file)"
              >
                <i class="el-icon-delete"></i>
              </span>
            </span>
          </div>
        </el-upload>
        <div class="toast">
          <p>限上传一张缩略图</p>
          <p>图片尺寸最佳为180X180像素</p>
        </div>
      </div>
      <!-- 详细商品图          -->
      <div class="form-item">
        <span>详情轮播顶图：</span>
        <el-upload
          :action="mutipartURL"
          :auto-upload="false"
          name="files"
          list-type="picture-card"
          :file-list="swiperImgArr"
          :on-success="fileUploadSuccess.bind(this, 'swiper')"
          :on-error="fileUploadError"
          ref="swiper"
          :multiple="true"
          :limit="8"
          :onChange="onSwiperImgChange"
          :disabled="isDisable"
        >
          <i slot="default" class="el-icon-plus"></i>
          <div slot="file" slot-scope="{file}">
            <img class="cover-cont" :src="file.url" />
            <span class="el-upload-list__item-actions">
              <a
                v-if="!swiperDisable"
                class="el-upload-list__item-delete download-cont"
                :href="file.url"
                download
              >
                <i class="el-icon-download"></i>
              </a>
              <span
                v-if="!swiperDisable"
                class="el-upload-list__item-delete"
                @click="swiperRemove(file)"
              >
                <i class="el-icon-delete"></i>
              </span>
            </span>
          </div>
        </el-upload>
      </div>
      <div class="form-item quill-container">
        <span>商品详情：</span>
        <!-- quill-editor -->
        <quill-editor
          class="my-quill"
          v-model="content"
          :options="editorOption"
          @change="onEditorChange($event)"
          @ready="onEditorReady($event)"
        >></quill-editor>
      </div>
    </div>
    <div class="control-container">
      <el-button type="danger" @click="checkoutDisable">预览</el-button>
      <el-button type="danger" @click="saveEnable">保存并开启</el-button>
      <el-button type="info" @click="cancelEdit">取消</el-button>
    </div>
  </div>
</template>
<script>
import Headline from '../../../components/headline.vue'
import CommodityModel from '../../../Http/commoditymanage/commodityManage/commodityManage'
import { alertInfo } from '../../../assets/js/common'
export default {
  data () {
    return {
      isDisable: false,
      // 默认为添加模式，在在mixins中会更变为编辑模式
      mode: 'add',
      pageTitle: '商品添加/编辑',
      // 商品名称
      goodName: null,
      // 商品价格
      goodPrice: null,
      // 商品数量
      num: null,
      // 产地
      locality: null,
      // 分类选中
      selectCategory: '',
      // 分类下拉
      categoryOPtions: [],
      // 封面图按钮是否禁用
      coverDisabeld: false,
      // 商品图
      coverImg: [],
      // ；轮播详细图
      swiperImgArr: [],
      // 是否显示按钮
      swiperDisable: false,
      // 富文本内容
      content: null,
      // 是否参加活动，该字段决定是否开启活动输入框
      isActivity: null,
      uploadUrl: 'http://192.168.3.88:8089/shop/uploadFile',
      mutipartURL: 'http://192.168.3.88:8089/shop/uploadMultipleFiles',
      // 该数据为富文本tabbar的显示模块
      editorOption: {
        modules: {
          toolbar: [
            [{ size: ['small', false, 'large'] }],
            ['bold', 'italic'],
            [{ list: 'ordered' }, { list: 'bullet' }],
            ['link', 'image']
          ]
        }
      },
      isFinish: [],
      isPreview: null
    }
  },
  methods: {
    /**
     * 缓存富文本的事件对象，作为禁用处理
     */
    onEditorReady ($event) {
      this.myQuillEditor = $event
    },
    checkoutDisable () {
      this.isPreview = this.$notify({
        title: '提示',
        message: '正在处于预览模式!关闭弹窗退出预览!',
        type: 'warning',
        duration: 0,
        onClose: () => {
          this.isDisable = false
          this.myQuillEditor.enable(true)
        }
      })
      this.isDisable = true
      this['myQuillEditor'].enable(false)
    },
    cancelEdit () {
      this.$router.push({
        path: '/commoditymanage'
      })
    },
    // 每个文件添加之后
    onSwiperImgChange (file) {
      if (file.status === 'ready') {
        this.swiperImgArr.push(file)
      }
    },
    // 添加封面图
    onCoverImgChange (file) {
      if (file.status === 'ready') {
        this.coverImg.push(file)
      }
    },
    // 上传成功的钩子
    fileUploadSuccess (type, file) {
      // 删除任务并得到服务端地址
      this.isFinish.pop()
      if (file.code === 200 && type === 'cover') {
        this.coverImg = [
          {
            url: file.data,
            status: 'success'
          }
        ]
      } else if (type === 'swiper') {
        const len = this.swiperImgArr.length
        for (var i = 0; i < len; i++) {
          if (this.swiperImgArr[i].status === 'ready') {
            this.swiperImgArr.splice(i, 1, {
              url: file.data,
              status: 'success'
            })
          }
        }
      }
      // 文件上传完毕，效验数据
      if (this.isFinish.length === 0) {
        // 启用数据效验
        this.submitFormData()
      }
    },
    // 文件上传失败的钩子
    fileUploadError () {
      alertInfo.call(this, {
        cont: '文件上传错误',
        type: 'error'
      })
    },
    // 封面图删除，只有有限
    handleRemove (file) {
      // 直接删除pop
      if (!this.isDisable) {
        this.coverImg.pop()
      }
    },
    // 富文本更新数据
    onEditorChange (event) {
      let { html } = event
      this.content = html
    },
    // 删除封面图
    swiperRemove (file) {
      if (!this.isDisable) {
        this.swiperImgArr.splice(this.swiperImgArr.indexOf(file), 1)
      }
    },
    //  效验表单是否填写
    examineInfoComlete () {
      var errorInfo = {
        [1 + 'goodName']: '商品标题未填',
        [2 + 'goodPrice']: '商品价格未填',
        [3 + 'num']: '商品数量未填写',
        [4 + 'locality']: '产地未填',
        [5 + 'selectCategory']: '分类未选择',
        [6 + 'coverImg']: '封面图未上传',
        [7 + 'swiperImgArr']: '轮播图未上传',
        [8 + 'content']: '商品详情未填写'
      }
      for (var key in errorInfo) {
        if (this.exanimeFormItem(key.slice(1))) {
          // 返回缺失信息
          return errorInfo[key]
        }
      }
      return true
    },
    exanimeFormItem (property) {
      // 判断是否为字符串，注意instanceof不能判断字符类型
      if (this[property] === undefined || this[property] === null) {
        return true
      } else if (typeof this[property] === 'string') {
        return this[property].trim() === ''
        // 判断是否为字符串，注意typeof不能判断数组
      } else if (this[property] instanceof Array) {
        return this[property].length === 0
      }
    },
    // 获取用户准备上传文件的总数
    exanimeImgIsReady () {
      const swiperLen = this.swiperImgArr.length
      for (var i = 0; i < swiperLen; i++) {
        if (
          this.swiperImgArr[i].status &&
          this.swiperImgArr[i].status === 'ready'
        ) {
          this.isFinish.push(i)
        }
      }
      return this.coverImg[0].status === 'ready' ? this.isFinish.push(i) : false
    },
    // 提交按钮
    saveEnable () {
      var isEmpty = this.examineInfoComlete()
      if (isEmpty !== true) {
        return this.$alert(isEmpty, '提交失败', '错误', {
          confirmButtonText: '确定'
        })
      }
      this.exanimeImgIsReady()
      // 提交文件上传，等待文件长传完成之后，开展回调
      if (this.isFinish.length > 0) {
        this.$refs['swiper'].submit()
        this.$refs['coverImg'].submit()
      } else {
        // 没有文件上传，并调用上传按钮，直接调用验证接口获取对应数据
        this.submitFormData()
      }
    },
    // 文件上传之后才会调用该函数
    submitFormData () {
      // 获取对应数据
      let goodsInfo = this.getQualifiedInfo()
      // 发起请求
      if (this.mode === 'add') {
        // 添加模式
        CommodityModel.addCommodity(
          goodsInfo,
          ({ data }) => {
            // 用户选择是否跳转会原页码
            this.$confirm('新增成功', '提示', {
              confirmButtonText: '返回商品管理页',
              cancelButtonText: '留在当前页面',
              type: 'success',
              lockScroll: false
            })
              .then(() => {
                this.$router.push({
                  name: 'commodityManage'
                })
              })
              .catch(rsp => {
                // 留在当前页码更新数据
                this.assignValue(data.data)
              })
          },
          ({ data }) => {
            this.$alert('添加失败', data.message, {
              confirmButtonText: '确定'
            })
          }
        )
      } else {
        // 修改模式
        this.editPut(goodsInfo)
      }
    },
    /**
     * 初始化赋值
     */
    assignValue (data) {
      // 初始化值
      this.goodName = data.itemName
      this.goodPrice = data.price
      this.locality = data.locality
      this.isActivity = data.isActivity === 'Y'
      this.selectCategory = data.categoryName
      this.num = data.num
      if (data.coverImg) {
        this.coverImg = [
          {
            name: 'coverImg',
            url: data.coverImg
          }
        ]
      }
      // 轮播图展示
      if (data.returnImg) {
        this.swiperImgArr = data.returnImg.split(',').map((item, index) => {
          return {
            name: index,
            url: item
          }
        })
      }
      if (typeof data.itemDetail === 'string' && data.itemDetail.length > 0) {
        this.content = data.itemDetail
        this.$nextTick(() => {
          window.scrollTo(0, 0)
        })
      }
    },
    // 组合符合接口参数的对象
    getQualifiedInfo () {
      // 得到符合请求的数据格式
      var goodsInfo = {
        itemName: this.goodName,
        categoryName: this.selectCategory,
        price: this.goodPrice.toString(),
        locality: this.locality,
        num: this.num,
        isShow: 'Y',
        isActivity: this.isActivity ? 'Y' : 'N',
        coverImg: this.coverImg
          .map(item => {
            return item.url
          })
          .join(','),
        returnImg: this.swiperImgArr
          .map(item => {
            return item.url
          })
          .join(','),
        itemDetail: this.content
      }
      return goodsInfo
    }
  },
  components: {
    Headline
  },
  created () {
    CommodityModel.getCategoryAll(({ data: { data } }) => {
      this.categoryOPtions = data
    })
  },
  beforeDestroy () {
    if (this.isPreview !== null) {
      this.isPreview.close()
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
  .quill-container {
    width: 800px;
    height: 400px;
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
    .cover-cont {
      width: 100%;
      height: 100%;
    }
    .my-quill {
      height: 300px;
      width: 800px;
      margin-top: -50px;
    }
    .specification {
      margin: 30px 0px;
      margin-left: 150px;
      > div {
        margin: 50px 0px;
        display: flex;
        align-items: center;
        > button,
        .el-color-picker {
          margin: 0px 20px;
        }
        > button {
          color: blue;
          font-size: 20px;
        }
        > .el-input {
          margin: 0px 20px;
        }
      }
      > i {
        display: block;
      }
      span,
      i {
        font-weight: 600;
      }
      span:first-child,
      i {
        margin-right: 150px;
      }
    }
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
  .add-button {
    margin: 10px;
    float: right;
    margin-right: 30px;
    width: 100px;
    height: 40px;
    cursor: pointer;
  }
}
.control-container {
  justify-content: space-around;
  display: flex;
  text-align: center;
  padding: 0px 100px;
  margin-top: -15px;
  padding-top: 20px;
  border: none;
  background: white;
  > button {
    width: 130px;
    height: 50px;
  }
}
</style>
