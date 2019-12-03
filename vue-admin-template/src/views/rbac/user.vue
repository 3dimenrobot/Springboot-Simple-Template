<template>
  <div class="app-container">
    <el-table
      v-loading="loading"
      :data="dataList"
      element-loading-text="Loading"
      border
      fit
      highlight-current-row
    >
      <el-table-column align="center" label="ID" width="95">
        <template slot-scope="scope">
          {{ scope.$index }}
        </template>
      </el-table-column>
      <el-table-column label="Title">
        <template slot-scope="scope">
          {{ scope.row.title }}
        </template>
      </el-table-column>
      <el-table-column label="Author" width="110" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.author }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Pageviews" width="110" align="center">
        <template slot-scope="scope">
          {{ scope.row.pageviews }}
        </template>
      </el-table-column>
      <el-table-column class-name="status-col" label="Status" width="110" align="center">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status | statusFilter">{{ scope.row.status }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="created_at" label="Display_time" width="200">
        <template slot-scope="scope">
          <i class="el-icon-time"/>
          <span>{{ scope.row.display_time }}</span>
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
        // cnname: "admin"
        // createTime: 1574353056000
        // enabled: false
        // id: 2
        // password: "$2a$10$y8b4ZlcA8CNaOq3BbM7wWeqJIFO3Y3KIEUP37zLdveB2JB0JhywzW"
        // remark: "admin"
        // roles: []
        // updateTime: 1574353056000
        // username: "admin"
        columns: [{prop: '中文名', label: 'cnname'}]
      }
    },
    mounted() {
      this.init()
    },
    methods: {
      init() {
        this.loading = true
        this.params = {page: this.page, size: this.size, sort: 'id,desc'}
        getList(this.params).then(response => {
          this.total = response.totalElements
          this.data = response.content
          this.listLoading = false
        })
      }
    }
  }
</script>


<style scoped>

</style>
