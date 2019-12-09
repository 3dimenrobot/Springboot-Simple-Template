<template>
  <div class="app-container">

    <div class="head-container">
      <!-- 搜索 -->
      <el-input v-model="entity.name" size="mini" clearable placeholder="输入角色名" style="width: 200px;" class="filter-item" @keyup.enter.native="toQuery"/>

      <el-button class="filter-item" size="mini" type="success" icon="el-icon-search" @click="toQuery">搜索</el-button>

      <el-button
        class="filter-item"
        size="mini"
        type="primary"
        icon="el-icon-plus"
        @click="add">新增</el-button>
    </div>
    <el-row>
      <el-col :xs="24" :sm="24" :md="16" :lg="16" :xl="17" style="margin-bottom: 10px">
        <el-table  @current-change="handleCurrentChange"
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
                  <el-button  type="primary" size="mini" @click="doDelete(scope.row.id)">确定</el-button>
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
          :page-sizes="[5, 20, 30, 40]"
          layout="total, prev, pager, next, sizes"
          @size-change="sizeChange"
          @current-change="pageChange"/>
      </el-col>
      <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="7">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <el-tooltip class="item" effect="dark" content="选择指定角色分配资源" placement="top">
              <span class="role-span">资源分配</span>
            </el-tooltip>
            <el-button
              icon="el-icon-check"
              size="mini"
              style="float: right; padding: 6px 9px"
              type="primary"
              @click="saveResource">保存</el-button>
          </div>
          <el-tree    default-expand-all
            ref="resource"
            :data="resourceTrees"
            :default-checked-keys="resourceIds"  :disabled="!showButton" :loading="resourceLoading"
            :props="defaultProps"
            check-strictly
            accordion
            show-checkbox
            node-key="id"/>
        </el-card>
      </el-col>
    </el-row>

    <el-dialog :visible.sync="dialog" :close-on-click-modal="false" :before-close="cancel" :title="isAdd ? '新增角色' : '编辑角色'" append-to-body width="570px">
        <el-form ref="form" :inline="true" :model="form" :rules="rules" size="small" label-width="66px">
          <el-form-item label="中文名" prop="cnname">
            <el-input v-model="form.cnname"  style="width:420px;"/>
          </el-form-item>
          <el-form-item label="角色名" prop="name">
            <el-input v-model="form.name" style="width:420px;"/>
          </el-form-item>
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
  import {readList,readOne,create,update,remove} from '@/api/role'
  import {readList as readAllResources } from '@/api/resource'

  import Pager from '@/api/Pager'

  export default {
    name: 'role',
    mixins: [Pager],
    data() {
      return {
        entity:{
          name:null
        },

        columns: [{label: '中文名', prop: 'cnname'},
          {label: '登录名', prop: 'name'},
          {label: '创建时间', prop: 'createTime'},
          {label: '备注', prop: 'remark'}
        ],

        dialog:false,
        isAdd:true,
        form:{
          cnname:'',
          name:'',
          remark:'',

        },
        rules:{
          name: [
            { required: true, message: '请输入角色名', trigger: 'blur' },
            { min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur' }
          ]
        },

        showButton:false,
        resourceLoading:false,
        defaultProps: {
          children: 'children',
          label: 'name'
        },
        resourceTrees:[], // 资源树
        resourceIds:[] // 选中的keys
      }
    },
    mounted() {
      this.init();
    },
    methods: {
      toQuery(){
        this.init();
      },
      init() {
        this.loading = true
        this.params = {page: this.page, size: this.size, sort: 'createTime','name':this.entity.name}
        readList(this.params).then(response => {
          let data = response.data.data;
          this.total = data.totalElements
          console.log(this.total)
          this.dataList = data.content
          this.loading = false
        })
        this.loading = true
        let resparams = {page: this.page, size: 10000, sort: 'createTime'}
        readAllResources(resparams).then(response => {
          let data = response.data.data;
          this.resourceTrees = data.content;
          this.loading = false
        })
      },
      cancel() {
        this.resetForm()
      },
      resetForm() {
        this.dialog = false
        for(let key in this.form){
          this.form[key]  = '';
        }
      },
      doSubmit() {
        this.$refs['form'].validate((valid) => {
          if (valid) {
            this.loading = true
            if (this.isAdd) {
              this.doAdd()
            } else this.doEdit()
          } else {
            return false
          }
        })
      },
      add(){
        this.isAdd = true
        this.dialog = true
      },
      edit(row){
        this.isAdd = false
        this.form = Object.assign({},row);
        this.dialog = true
      },
      doDelete(id){
        remove({id:id}).then(response => {
          this.$notify({
            title: '删除成功',
            type: 'success',
            duration: 2500
          })
          this.loading = true
          this.toQuery();
          this.resetForm();
        })
      },
      doAdd(){
        create(this.form).then(response => {
          this.$notify({
            title: '添加成功',
            type: 'success',
            duration: 2500
          })
          this.loading = true
          this.toQuery();
          this.resetForm();
        })
      },
      doEdit(){
        update(this.form).then(response => {
          this.$notify({
            title: '修改成功',
            type: 'success',
            duration: 2500
          })
          this.loading = true
          this.toQuery();
          this.resetForm();
        })
      },
      handleCurrentChange(val) {
        this.form = Object.assign({},val);
        if (val) {
          const _this = this
          // 清空菜单的选中
          this.$refs.resource.setCheckedKeys([])
          // 保存当前的角色id
          this.form.id = val.id
          // 点击后显示按钮
          this.showButton = true
          // 初始化
          this.resourceIds = []
          // 菜单数据需要特殊处理
          val.resources.forEach(function(data, index) {
            _this.resourceIds.push(data.id)
          })
        }
      },
      // 保存角色的资源
      saveResource(){
        this.resourceLoading = true
        const role = Object.assign({},this.form)
        role.resources = [];
        // 得到半选的父节点数据，保存起来
        this.$refs.resource.getHalfCheckedNodes().forEach(function(data, index) {
          const resource = { id: data.id }
          role.resources.push(resource)
        })
        // 得到已选中的 key 值
        this.$refs.resource.getCheckedKeys().forEach(function(data, index) {
          const resource = { id: data }
          role.resources.push(resource)
        })
        update(role).then(res => {
          this.$notify({
            title: '保存成功',
            type: 'success',
            duration: 2500
          })
          this.menuLoading = false
          debugger
          this.updateRowResource()
          this.resetForm();
        }).catch(err => {
          this.menuLoading = false
          console.log(err.response.data.message)
        })
      },
      updateRowResource(){
        // 无刷新更新 表格数据
        readOne(this.form.id).then(res => {
          res = res.data.data;
          for (let i = 0; i < this.data.length; i++) {
            if (res.id === this.dataList[i].id) {
              this.dataList[i] = res
              break
            }
          }
        })
      }
    }
  }
</script>


<style scoped>

</style>
