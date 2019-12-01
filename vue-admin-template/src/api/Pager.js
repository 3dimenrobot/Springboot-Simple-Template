
export default {
  data() {
    return {
      loading: true, dataList: [], page: 0, size: 10, total: 0
    }
  },
  methods: {
    // 初始化函数，被覆盖
    init(params) {
      this.data = []
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
