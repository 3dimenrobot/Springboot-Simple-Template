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
      layout="total, prev, pager, next, sizes"
      @size-change="sizeChange"
      @current-change="pageChange"/>

    <el-dialog :visible.sync="dialog" :close-on-click-modal="false" :before-close="cancel" :title="isAdd ? '新增影响因子' : '编辑影响因子'" append-to-body width="570px">
        <el-form ref="form" :inline="true" :model="form" :rules="rules" size="small" label-width="66px">
          <el-form-item label="温度" prop="temperature">
            <el-input v-model="form.temperature"  style="width:420px;" @input="change($event)"/>
          </el-form-item>
          <el-form-item label="湿度" prop="humidity">
            <el-input v-model="form.humidity" style="width:420px;" @input="change($event)"/>
          </el-form-item>
          <br><br>
          <el-form-item label="备注" prop="remark">
            <el-input v-model="form.remark" style="width:420px;" @input="change($event)"/>
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
  import {readList,create,remove} from '@/api/corn/factor'
  import {currentRouteArea} from '@/api/corn/area'

  import Pager from '@/api/Pager'

  export default {
    name: 'Factor',
    mixins: [Pager],
    data() {
      return {
        entity:{
          temperature:null
        },
        columns: [{label: '地区', prop: 'region'},
          {label: '省|市|区', prop: 'province'},
          {label: '温度', prop: 'temperature'},
          {label: '湿度', prop: 'humidity'},
          {label: '备注', prop: 'remark'}
        ],
        dialog:false,
        isAdd:true,
        form:{
          temperature:'',
          humidity:'',
          remark:'',
        },
        area:{
          region: '',
          province:''
        },

        rules:{
        }
      }
    },
    mounted() {
      this.area = currentRouteArea(this.$route);
      this.init();
      console.log("this.area",this.area)
    },
    methods: {
      toQuery(){
        this.init();
      },
      init() {
        this.loading = true
        this.params = {page: this.page, size: this.size, sort: 'createTime','temperature':this.entity.temperature, region:this.area.region,province:this.area.province }
        readList(this.params).then(response => {
          let data = response.data.data;
          this.total = data.totalElements
          this.dataList = data.content
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
        debugger
        this.form.region = this.area.region
        this.form.province = this.area.province
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
        this.form.region = this.area.region
        this.form.province = this.area.province
        let data = Object.assign({},this.form)
        create(data).then(response => {
          this.$notify({
            title: '添加成功',
            type: 'success',
            duration: 2500
          })
          this.loading = true
          this.toQuery();
          this.resetForm();
          console.log('form::::',this.form)
        })
      },
      doEdit(){
        this.form.region = this.area.region
        this.form.province = this.area.province
        let data = Object.assign({},this.form)
        create(data).then(response => {
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
      change(e){
        this.$forceUpdate()
      }
    }
  }
</script>


<style scoped>

</style>
