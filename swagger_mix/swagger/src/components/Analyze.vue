<template>
  <el-breadcrumb separator-icon="ArrowRight">
    <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
    <el-breadcrumb-item>控制台</el-breadcrumb-item>
    <el-breadcrumb-item>swagger解析</el-breadcrumb-item>
  </el-breadcrumb>
  <el-card>
    <el-form
      :model="paraForm"
      ref="paraFormRef"
      label-width="150px"
      :inline="true">
      <el-form-item style='width: 60%' label="swagger API 地址">
        <el-input v-model="paraForm.url" placeholder="请输入API 地址(http(s)://)" >d
          <template #append>
            <el-button type="primary" icon="Promotion" @click="sent" />
          </template>
        </el-input>
      </el-form-item>
      <el-form-item style='width: 60%' label="登录的Cookie">
        <el-input v-model="paraForm.cookie" placeholder="请参考 'Cookie'：'JSESSIONID=***************'">
        </el-input>
      </el-form-item>
    </el-form>
  </el-card>
  <el-card>
    <el-table
      ref="swaggerTable"
      :data="swaggerData"
      :style="{ width: '100%'}"
      :row-key="(row)=>{return row.id;}"
      :highlight-current-row="true"
    >
      <el-table-column type="expand" >
        <template #default="props">
          <div m="4">
            <el-form
              :inline="true"
              ref="apiFormRef"
              :model="props.row"
              :rules="apiFormRules"
              label-width="120px"
              status-icon :style="{ marginLeft: '20px'}"
            >
              <el-form-item label="案例号" prop="caseNo" >
                <el-input v-model="props.row.caseNo" maxlength="50" style="width: 400px" clearable/>
              </el-form-item>
              <el-form-item label="案例描述" prop="caseDec">
                <el-input v-model="props.row.caseDec" maxlength="100" style="width: 400px" clearable/>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="showSaveConfirm(props.row)">保存案例</el-button>
              </el-form-item>
              <el-form-item>
                <codemirror
                  v-model=props.row.inputParam
                  placeholder="Code goes here..."
                  :style="{ minHeight: '300px', maxHeight: '1500px', width: '1300px'}"
                  :autofocus="true"
                  :indent-with-tab="true"
                  :tab-size="2"
                  :extensions="extensions"
                  @ready="handleReady"
                  @blur="formatJson(props.row)" />
              </el-form-item>
              <el-form-item>
                <codemirror
                  v-model=props.row.outputParam
                  placeholder="Code goes here..."
                  :style="{ minHeight: '300px', maxHeight: '1500px', width: '1300px'}"
                  :autofocus="true"
                  :indent-with-tab="true"
                  :tab-size="2"
                  :extensions="extensions"
                  @ready="handleReady"
                  @blur="formatJson(props.row)"
                />
              </el-form-item>
            </el-form>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="接口地址" prop="apiUrl" width="400px"/>
      <el-table-column label="请求方式" prop="method"/>
      <el-table-column label="接口描述" prop="summary" />
      <el-table-column label="接口所属分组" prop="tag" />
    </el-table>
  </el-card>
</template>
<script>
import { Codemirror } from 'vue-codemirror';
import { json } from '@codemirror/lang-json';
import { oneDark } from '@codemirror/theme-one-dark';
import {ref, shallowRef} from 'vue';
import {handleCurrentChange} from "element-plus/es/components/tree/src/model/util";
export default {
  mounted() {
  },
  data() {
    return {
      code1: '11111111',
      isExpandVisible: true,
      paraForm: {
        url: '',
      },formData: {
        caseNo: '',
        caseDec: '',
      },
      swaggerData: [],
      apiForm: {},
      apiFormRules: [],
      expandedRows: []
    };
  },
  components: {
    Codemirror,
  },
  setup() {
    const code = ref('console.log(\'Hello, world!\')');
    const extensions = [json(), oneDark];
    const view = shallowRef();
    const handleReady = (payload) => {
      view.value = payload.view;
    };
    const getCodemirrorStates = () => {
      const state = view.value.state;
      const ranges = state.selection.ranges;
      const selected = ranges.reduce(
        (r, range) => r + range.to - range.from,
        0
      );
      const cursor = ranges[0].anchor;
      const length = state.doc.length;
      const lines = state.doc.lines;
    };
    const formatJson = (data) => {
      data.outputParam = JSON.stringify(
        JSON.parse(data.outputParam),
        null,
        '\t'
      );
    };
    return {
      code,
      extensions,
      handleReady,
      log: console.log,
      formatJson,
    };
  },
  methods: {
    handleCurrentChange (row) {
      console.log(row)
    }, async sent() {
      const { data: res } = await this.$http.post('/swagger/analyze', this.paraForm);
      console.log(res);
      if (res.code !== 200) return this.$message.error(res.msg);
      this.$message.success('解析成功');
      for (let i = 0; i < res.data.length; i++) {
        res.data[i].inputParam = JSON.stringify(
          JSON.parse(res.data[i].inputParam),
          null,
          '\t'
        );
        res.data[i].outputParam = JSON.stringify(
          JSON.parse(res.data[i].outputParam),
          null,
          '\t'
        );
        res.data[i].inputParamDec = JSON.stringify(
          JSON.parse(res.data[i].inputParamDec),
          null,
          '\t'
        );
      }
      this.swaggerData = res.data;
    },
    showSaveConfirm(data) {
      // 校验输入框
      if (!data.caseNo) {
        this.$message.error('案例号不能为空');
        return;
      }

      if (!data.caseDec) {
        this.$message.error('案例描述不能为空');
        return;
      }
      this.$confirm('确定要保存此案例吗?', '确认', {
        cancelButtonText: '取消',
        confirmButtonText: '保存',
        type: 'warning',
      })
        .then(() => {
          this.saveData(data);
          this.$refs.swaggerTable.toggleRowExpansion(data);
        })
        .catch(() => {
        });
    }, saveData(data) {
      const requestData = {
        method: data.method,
        inputParam: data.inputParam,
        inputParamDec: data.inputParamDec,
        outputParam: data.outputParam,
        apiUrl: data.apiUrl,
        serverUrl: data.serverUrl,
        tag: data.tag,
        summary: data.summary,
        outputParamDec: data.outputParamDec,
        caseNo: data.caseNo,
        caseDec: data.caseDec
      };
      this.$http.post('/swagger/saveData', requestData)
        .then(response => {
          // 处理成功回调
          console.log(response);
          this.$message.success('保存成功');
          data.caseNo = '';
          data.caseDec = '';
        })
        .catch(error => {
          // 处理错误回调
          console.error(error);
          this.$message.error('保存异常，请检查错误日志');
        });
    }, handleRowClick(row) {
      const index = this.expandedRows.indexOf(row);
      if (index === -1) {
        this.expandedRows.push(row); // 展开行
      } else {
        this.expandedRows.splice(index, 1); // 折叠行
      }
    },
  },
};
</script>
<style lang="less" scoped>
.el-card {
  margin: 15px;
}
.hidden-column {
  display: none;
}
</style>
