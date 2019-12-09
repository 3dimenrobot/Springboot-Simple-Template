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
   <el-table v-loading="loading" :data="dataList" :tree-props="{children: 'children', hasChildren: 'hasChildren'}" :default-expand-all="expand" row-key="id" size="small">
      <el-table-column :show-overflow-tooltip="true" label="菜单名称" width="225px" prop="name"/>

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

    <el-dialog :visible.sync="dialog" :close-on-click-modal="false" :before-close="cancel" :title="isAdd ? '新增资源' : '编辑资源'" append-to-body width="570px">
        <el-form ref="form" :inline="true" :model="form" :rules="rules" size="small" label-width="85px">
          <el-form-item label="资源类型">
            <el-radio-group v-model="form.level" size="mini" >
              <el-radio-button   v-for="(v, k) in levelOptions"     :key="k" :label="k">{{v}}</el-radio-button>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="资源名称" prop="name">
            <el-input v-model="form.name"  style="width:420px;"/>
          </el-form-item>
          <el-form-item label="上级资源" >
            <!-- ele 2.7 版本 change-on-select -->
            <el-cascader  v-model="form.pidArr" style="width:420px;"
              :options="resources"  @change="handleChangeParent"
              :props="parentOptions"  change-on-select
              clearable></el-cascader>
          </el-form-item>
          <el-form-item label="元素URL">
            <el-input v-model="form.atomUrl"  style="width:100px;"/>
          </el-form-item>
          <el-form-item label="资源URL">
            <el-input v-model="form.url"  style="width:220px;"/>
          </el-form-item>
          <el-form-item label="请求方法" v-if="form.level === 'Button_Method'">
            <el-select v-model="form.method"  style="width:100px;"  placeholder="请选择" >
              <el-option
                v-for="(v, k) in method_crud"
                :key="k"
                :label="k"
                :value="k"/>
            </el-select>
          </el-form-item>
          <el-form-item label="CRUD动作"  v-if="form.level === 'Button_Method'">
            <el-select v-model="form.crudName"  style="width:220px;"  placeholder="请选择" >
              <el-option
                v-for="(v, k) in method_crud"
                :key="v"
                :label="v"
                :value="v"/>
            </el-select>
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
  import {readList,create,update,remove} from '@/api/resource'

  import Pager from '@/api/Pager'

  export default {
    name: 'role',
    mixins: [Pager],
    data() {
      return {
        entity:{
          name:null
        },
        columns: [
          // {label: '菜单名称', prop: 'name',width:'300px'},
          {label: '类型', prop: 'level',formatter:function(r,c,v){
              let levelOptions = { Dir_Module:'目录_模块',Menu_Entity:'菜单_实体',Button_Method:'按钮_方法'}
             return levelOptions[v]
            }},
          {label: '元素URL', prop: 'atomUrl'},
          {label: '资源URL', prop: 'url'},
          {label: '请求方法', prop: 'method'},
          {label: 'CRUD动作', prop: 'crudName'},
          {label: '备注', prop: 'remark'}
        ],
        expand: true,
        dialog:false,
        isAdd:true,
        form:{
          name:'', // 菜单名称
          level:'Dir_Module', // 类型：目录,菜单,按钮 Dir_Module Menu_Entity Button_Method
          atomUrl:'', // 资源元素url
          url:'', // 资源访问url
          method:'', // 请求方法，对应后端的方法 // 如All|GET|POST|PUT|DETELE"
          crudName:'', // crud 动作，行为 如all|list|read|update|delete
          parent:{id:''}, //   提交前改为 parent:{id:''}
          pidArr:[],// 父级菜单id选项
          remark:'', // 备注
        },
        rules:{
          name: [
            { required: true, message: '请输入角色名', trigger: 'blur' },
            { min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur' }
          ]
        },
        parentOptions: {label:'name',value:'id', checkStrictly: true},
        resources:[],
        method_crud:{'-':'-','All':'all','GET':'read','POST':'create','PUT':'update','DETELE':'delete'},
        levelOptions:{ Dir_Module:'目录_模块',Menu_Entity:'菜单_实体',Button_Method:'按钮_方法'},
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
        this.params = {page: this.page, size: 10000, sort: 'createTime','name':this.entity.name}
        readList(this.params).then(response => {
          let data = response.data.data;
          this.total = data.totalElements
          console.log(this.total)
          this.dataList = data.content
          this.loading = false
        })
      },
      loadAllResource(){
        this.loading = true
        this.params = {page: this.page, size: 10000, sort: 'createTime'}
        readList(this.params).then(response => {
          let data = response.data.data;
          console.log(data);
          this.resources = data.content;
          this.loading = false
          if(!this.isAdd && this.form.parent){
            let reversePidArr = [];
            let pid = this.form.parent.id
            this.findIdArr(pid,this.resources,reversePidArr);
            console.log("reversePidArr",reversePidArr)
            this.form.pidArr = reversePidArr;
          }
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
        this.form.level = 'Dir_Module';
        this.form.parent = {id:''};
        this.form.pidArr = [];
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
        this.loadAllResource();
        this.isAdd = true
        this.dialog = true
        // this.form.pidArr = [];
      },
      /**
       * 找到当前元素的位置
       * @param id 当前元素的id
       * @param trees
       * @param arr cascade选择框 反向数组序列
       */
      findIdArr(id,trees,arr){
        console.log("look............",id,arr)
        if(id == '' || trees == null || trees.length ==0) return false;

        for (let i = 0; i < trees.length; i++) {
          console.log(i,"------",trees[i].id,id)
          if(trees[i].id === id){
            arr.push(id)
            return true;
          }else {
            if(trees[i].children && trees[i].children.length > 0){
              arr.push(trees[i].id)
              let isfind = this.findIdArr(id,trees[i].children,arr);
              if(isfind){
                return true;
              }else{
                arr.pop();
              }
            }
          }
        }
      },
      edit(row){
        this.isAdd = false

        this.form = Object.assign({},row);
        let pid = row.parent &&  row.parent.id;
        console.log('pid',pid);
        this.form.pidArr = [pid];
        this.loadAllResource();
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
        this.form.parent.id = this.form.pidArr.pop();
        if(!this.form.children){
          this.form.children = null;
        }
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
        this.form.parent.id = this.form.pidArr.pop();
        if(!this.form.children){
          this.form.children = null;
        }
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
      handleChangeParent(nv){
      console.log("handleChangeParent........",nv,this.form.parent);
        // this.form.pidArr = nv;
      }
    }
  }
</script>


<style scoped>

</style>
