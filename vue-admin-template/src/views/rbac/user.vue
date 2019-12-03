<template>
  <div class="app-container">

    <div class="head-container">
      <!-- 搜索 -->
      <el-input v-model="entity.username" size="mini" clearable placeholder="输入登录名" style="width: 200px;" class="filter-item" @keyup.enter.native="toQuery"/>

      <el-button class="filter-item" size="mini" type="success" icon="el-icon-search" @click="toQuery">搜索</el-button>

      <el-button
        class="filter-item"
        size="mini"
        type="primary"
        icon="el-icon-plus"
        @click="add">新增</el-button>
    </div>
    <el-table
      v-loading="loading"
      :data="dataList"
      element-loading-text="Loading"
      size="small" style="width: 90%;"
      highlight-current-row>
      <el-table-column type="index"/>
      <el-table-column v-for="(col,index) in columns" :key="index" align="center" :show-overflow-tooltip="true"
                       :prop="col.prop" :label="col.label"
                       :width="col.width" :formatter="col.formatter">
      </el-table-column>

      <!--v-if="checkPermission(['admin','user:edit','user:del'])"-->

      <el-table-column  label="操作" width="125" align="center" fixed="right">
        <template slot-scope="scope">
          <el-button  size="mini" type="primary" icon="el-icon-edit" @click="edit(scope.row)"/>
          <el-popover
            :ref="scope.row.id"
            placement="top"
            width="180">
            <p>确定删除本条数据吗？</p>
            <div style="text-align: right; margin: 0">
              <el-button size="mini" type="text" @click="$refs[scope.row.id].doClose()">取消</el-button>
              <el-button  type="primary" size="mini" @click="delete(scope.row.id)">确定</el-button>
            </div>
            <el-button slot="reference" type="danger" icon="el-icon-delete" size="mini"/>
          </el-popover>
        </template>
      </el-table-column>
    </el-table>
    <!--分页组件-->
    <el-pagination
      :total="total"
      :current-page="page + 1"
      style="margin-top: 8px;"
      layout="total, prev, pager, next, sizes"
      @size-change="sizeChange"
      @current-change="pageChange"/>

    <el-dialog :visible.sync="dialog" :close-on-click-modal="false" :before-close="cancel" :title="isAdd ? '新增用户' : '编辑用户'" append-to-body width="570px">
        <el-form ref="form" :inline="true" :model="form" :rules="rules" size="small" label-width="66px">
          <el-form-item label="中文名" prop="cnname">
            <el-input v-model="form.cnname"  style="width:420px;"/>
          </el-form-item>
          <el-form-item label="用户名" prop="username">
            <el-input v-model="form.username"/>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input v-model="form.password"/>
          </el-form-item>

          <el-form-item style="margin-bottom: 0px;" label="角色">
            <el-select v-model="roleIds"  style="width:420px;" multiple placeholder="请选择">
              <el-option
                v-for="(item, index) in roles"
                :disabled="level !== 1 && item.level <= level"
                :key="item.name + index"
                :label="item.name"
                :value="item.id"/>
            </el-select>
          </el-form-item>
          <br><br>
          <el-form-item label="备注" prop="remark">
            <el-input v-model="form.remark" style="width:420px;"/>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="cancel">取消</el-button>
          <el-button :loading="loading" type="primary" @click="doSubmit">确认</el-button>
        </div>
      </el-dialog>
  </div>
</template>

<script>
  import {getList} from '@/api/user'

  import Pager from '@/api/Pager'

  export default {
    name: 'user',
    mixins: [Pager],
    data() {
      return {
        entity:{
          username:null
        },

        columns: [{label: '中文名', prop: 'cnname'},
          {label: '登录名', prop: 'username'},
          {label: '密码', prop: 'password'},
          {
            label: '角色', prop: 'roles', formatter: function (row, column, value) {
              return "";
            }
          },
          {label: '创建时间', prop: 'createTime'},
          {label: '备注', prop: 'remark'}
        ],

        dialog:false,
        form:{
          cnname:'',
          username:'',
          password:'',
          roles:[],
          roleIds:'',
          remark:''
        },
        rules:{
          username: [
            { required: true, message: '请输入用户名', trigger: 'blur' },
            { min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur' }
          ]
        }
      }
    },
    mounted() {
      this.init()
    },
    methods: {
      toQuery(){
        this.init();
      },
      init() {
        this.loading = true
        this.params = {page: this.page, size: this.size, sort: 'id','username':this.entity.username}
        getList(this.params).then(response => {
          let data = response.data.data;
          this.total = data.totalElements
          this.dataList = data.content
          this.loading = false
        })
      },
      add(){
        this.isAdd = true
        // this.$refs.form.getDepts()
        // this.$refs.form.getRoles()
        // this.$refs.form.getRoleLevel()
        this.dialog = true
      },
      edit(){
        this.isAdd = false
        // this.$refs.form.getDepts()
        // this.$refs.form.getRoles()
        // this.$refs.form.getRoleLevel()
        this.dialog = true
      },
      delete(){

      }
    }
  }
</script>


<style scoped>

</style>
