<template>
  <el-breadcrumb separator-icon="ArrowRight">
    <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
    <el-breadcrumb-item>控制台</el-breadcrumb-item>
    <el-breadcrumb-item>接口管理</el-breadcrumb-item>
  </el-breadcrumb>
  <el-card>
    <el-table :data="swaggerData" :style="{ width: '100%'}" :row-key="(row)=>{return row.id;}">
      <el-table-column type="expand" >
        <template #default="props">
          <div m="4">
            <el-form :inline="true" ref="apiFormRef" :model="apiForm" :rules="apiFormRules"
                     label-width="120px" status-icon :style="{ marginLeft: '20px'}">
              <el-row>
                <el-col>
              <el-form-item>
                <el-button type="primary" @click="sendJson(props.row)">发送JSON请求</el-button>
                <el-button type="danger" @click="confirmDelete(props.row.id)">删除案例</el-button>
              </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col>
              <el-form-item>
                <codemirror v-model=props.row.inputParam placeholder="Code goes here..."
                            :style="{ height: '300px',width: '1300px'}" :autofocus="true"
                            :indent-with-tab="true" :tab-size="2" :extensions="extensions"
                            @ready="handleReady" @blur="formatJson(props.row)"/>
              </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col>
              <el-form-item>
                <codemirror v-model=props.row.outputParam placeholder="Code goes here..."
                            :style="{ height: '300px',width: '1300px'}" :autofocus="true"
                            :indent-with-tab="true" :tab-size="2" :extensions="extensions"
                            @ready="handleReady" @blur="formatJson(props.row)"/>
              </el-form-item>
                </el-col>
              </el-row>
            </el-form>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="案例号" prop="caseNo" width="360px"/>
      <el-table-column label="案例描述" prop="caseDec" width="400px"/>
      <el-table-column label="请求地址" prop="serverUrl"/>
      <el-table-column label="接口地址" prop="apiUrl"/>
      <el-table-column label="请求方式" prop="method" width="80px"/>
      <el-table-column label="接口描述" prop="summary"/>
      <el-table-column label="接口所属分组" prop="tag"/>

    </el-table>
  </el-card>
  <el-card>
    <el-pagination
    @size-change="handleSizeChange"
    @current-change="handleCurrentChange"
    :current-page="currentPage"
    :page-sizes="[10, 20, 50]"
    :page-size="pageSize"
    layout="total, sizes, prev, pager, next, jumper"
    :total="total">
  </el-pagination>
  </el-card>
</template>
<script>
import {Codemirror} from 'vue-codemirror';
import {json} from '@codemirror/lang-json';
import {oneDark} from '@codemirror/theme-one-dark';
import {ref, shallowRef} from 'vue';
import axios from "axios";
export default {
  data() {
    return {
      // code1: '11111111',
      paraForm: {
        url: '1111',
      },
      isExpandVisible: true,
      tableData: [],
      swaggerData: [],
      apiForm: {},
      apiFormRules: [],
      records: [],
      tempData:[],
      currentPage: 1, // 当前页码
      pageSize: 10, // 每页显示记录数
      total: 0, // 总记录数
    };
  },
  mounted() {
    this.fetchData();
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
      formatJson
    };
  },
  methods: {
    async sent() {
      const {data: res} = await this.$http.post('/swagger/query', this.paraForm);
      console.log(res);
      if (res.code !== 200) return this.$message.error(res.msg);
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
      }
      this.swaggerData = res.data;
      console.log(this.swaggerData);
    },
    sendJson(data) {
      const requestData = {
        id: data.id,
        methods: data.method,
        inputParam: data.inputParam,
        inputParamDec: data.inputParamDec,
        outputParam: data.outputParam,
        apiUrl: data.apiUrl,
        serverUrl: data.serverUrl
      };
      if (data.method === 'get') {
        this.$http.get('/api/getAll', { params: requestData })
          .then(response => {
            // 处理成功回调
            console.log(response);
          })
          .catch(error => {
            // 处理错误回调
            console.error(error);
          });
      } else if (data.method === 'post') {
        this.$http.post('/api/sendPostRequest', requestData)
          .then(response => {
            // 处理成功回调
            console.log(response);
          })
          .catch(error => {
            // 处理错误回调
            console.error(error);
          });
      } else {
        console.error('Invalid HTTP method:', data.method);
      }
    },
    fetchData() {
      axios.get('/swagger/selectByPage', {
        params: {
          current: this.currentPage,
          size: this.pageSize,
        },
      })
        .then(response => {
          this.total = response.data.total;
          console.log(response.data.records)
          this.swaggerData = [...response.data.records]
        })
        .catch(error => {
          console.error(error);
        });
    },
    handleSizeChange(val) {
      this.pageSize = val;
      this.fetchData();
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      this.fetchData();
    }, confirmDelete(id) {
      this.$confirm('是否确认删除此案例?', '确认删除', {
        confirmButtonText: '是',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(() => {
        // 用户点击 '是'，执行删除操作
        console.log(1)
        this.deleteCase(id);
      }).catch(() => {
        // 用户点击 '取消'，不执行任何操作
      });
    },
    deleteCase(id) {
      this.$http.delete(`/swagger/deleteRecord/${id}`)
        .then(response => {
          this.$message.success('案例删除成功');
          this.fetchData();
        })
        .catch(error => {
          console.log("要删除的ID为"+id)
          // 处理错误响应
          this.$message.error('删除案例时出现错误');
          console.error(error);
        });
    }
  },
};
</script>
<style lang="less" scoped>
.el-card {
  margin: 15px;
}
</style>

