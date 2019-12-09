
export default {
  data() {
    return {
      loading: true, dataList: [], page: 0, size: 5, total: 30
    }
  },
  methods: {
    // 初始化函数，被覆盖
    init(params) {
      this.dataList = []
    },
    pageChange(e) {
      this.page = e - 1
      this.init()
    },
    sizeChange(e) {
      this.page = 0
      this.size = e
      this.init()
    }
  }
}
