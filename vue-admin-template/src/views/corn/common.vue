<template>
  <div class="app-container">
    <el-tabs type="border-card">
      <el-tab-pane label="现状">
        <PresentSituation/>
      </el-tab-pane>
      <el-tab-pane label="新闻">
        <NewsItem/>
      </el-tab-pane>
      <el-tab-pane label="影响因子">
        <Factor/>
      </el-tab-pane>
      <el-tab-pane label="预测">
        <Prediction/>
      </el-tab-pane>
    </el-tabs>

  </div>
</template>

<script>
  import {readList,create,update,remove} from '@/api/user'
  import {readList as getRoles } from '@/api/role'

  import Pager from '@/api/Pager'
  import PresentSituation from './presentSituation'
  import NewsItem from './newsItem'
  import Factor from './factor'
  import Prediction from './prediction'

  export default {
    name: 'corn-common',
    mixins: [Pager],
    components:{
      PresentSituation,
      NewsItem,
      Factor,
      Prediction
    },
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
              let roles = [];
              value.forEach(function (role, index) {
                roles.push(role.name)
              })
              return JSON.stringify(roles);
            }
          },
          {label: '创建时间', prop: 'createTime'},
          {label: '备注', prop: 'remark'}
        ],

        dialog:false,
        isAdd:true,
        form:{
          cnname:'',
          username:'',
          password:'',
          roles:[],
          remark:''
        },
        roleIds:[],
        roleList:[],

        rules:{
          username: [
            { required: true, message: '请输入用户名', trigger: 'blur' },
            { min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur' }
          ]
        }
      }
    },
    mounted() {
      this.init();
      this.loadRoleList();
    },
    methods: {
      toQuery(){
        this.init();
      },
      init() {
        this.loading = true
        this.params = {page: this.page, size: this.size, sort: 'createTime','username':this.entity.username}
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
        this.form.roles = [];
        this.roleIds = [];
      },
      doSubmit() {
        this.$refs['form'].validate((valid) => {
          if (valid) {
            this.loading = true
            this.form.roles = []
            const _this = this
            this.roleIds.forEach(function (data, index) {
              const role = {id: data}
              _this.form.roles.push(role)
            })
            if (this.isAdd) {
              this.doAdd()
            } else this.doEdit()
          } else {
            return false
          }
        })
      },
      loadRoleList(){
        console.log( this.roleList )
        this.params = {page: 0, size: 20}
        getRoles(this.params).then(response => {
          let data = response.data.data;
          this.roleList = data.content
          console.log( this.roleList )
        })
      },
      add(){
        this.isAdd = true
        this.dialog = true
      },
      edit(row){
        this.isAdd = false
        this.form = Object.assign({},row);
        const _this = this
        this.form.roles.forEach(function (role, index) {
          _this.roleIds.push(role.id)
        })
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
    }
  }
</script>


<style scoped>

</style>
