<template>
  <div style="width: 70%;margin-left: 30px;margin-top: 30px;">
    <vue-json-editor
      v-model="resultInfo"
      :showBtns="false"
      :mode="'code'"

      @json-change="onJsonChange"
      @json-save="onJsonSave"
      @has-error="onError"
    />
    <br>
    <el-button type="primary" @click="checkJson">确定</el-button>
  </div>
</template>

<script>
// 导入模块
import vueJsonEditor from 'vue-json-editor'

export default {
  // 注册组件
  components: { vueJsonEditor },
  data() {
    return {
      hasJsonFlag:true,// json是否验证通过
      // json数据
      resultInfo: {
        'employees': [
          {
            'firstName': 'Bill',
            'lastName': 'Gates'
          },
          {
            'firstName': 'George',
            'lastName': 'Bush'
          },
          {
            'firstName': 'Thomas',
            'lastName': 'Carter'
          }
        ]
      }
    }
  },
  mounted: function() {
  },
  methods: {
    onJsonChange (value) {
      // console.log('更改value:', value);
      // 实时保存
      this.onJsonSave(value)
    },
    onJsonSave (value) {
      // console.log('保存value:', value);
      this.resultInfo = value
      this.hasJsonFlag = true
    },
    onError(value) {
      // console.log("json错误了value:", value);
      this.hasJsonFlag = false
    },
    // 检查json
    checkJson() {
      if (this.hasJsonFlag == false) {
        // console.log("json验证失败")
        // this.$message.error("json验证失败")
        alert("json验证失败")
        return false
      } else {
        // console.log("json验证成功")
        // this.$message.success("json验证成功")
        alert("json验证成功")
        return true
      }
    },
  }
}
</script>

<style>
</style>
