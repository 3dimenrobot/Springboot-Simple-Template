var data = [
    {
        path: '/home',
        name: '首页'
    },
    // 华北地区、华中地区、华东地区、华南地区、西北地区、东北地区、西南地区
// 1.华北地区：北京、天津、河北、山西、内蒙古
    {
        name: '华北地区',
        child: [
            {name: '介绍', path: '/components'},
            {name: '北京', path: '/components/pageTable/00/00'},
            {name: '天津', path: '/components/pageTable/00/01'},
            {name: '河北', path: '/components/pageTable/00/02'},
            {name: '山西', path: '/components/pageTable/00/03'},
            {name: '内蒙古', path: '/components/pageTable/00/04'},
        ]
    },

// 2.华东地区：上海、江苏、浙江，山东、安徽、江西、福建
    {
        name: '华东地区',
        child: [
            {name: '介绍', path: '/components'},
            {name: '上海', path: '/components/pageTable/01/00'},
            {name: '江苏', path: '/components/pageTable/01/01'},
            {name: '浙江', path: '/components/pageTable/01/02'},
            {name: '山东', path: '/components/pageTable/01/03'},
            {name: '安徽', path: '/components/pageTable/01/04'},
            {name: '江西', path: '/components/pageTable/01/05'},
            {name: '福建', path: '/components/pageTable/01/06'},
        ]
    },
// 3.东北地区：辽宁、吉林、黑龙江
    {
        name: '东北地区',
        child: [
            {name: '介绍', path: '/components'},
            {name: '辽宁', path: '/components/pageTable/02/00'},
            {name: '吉林', path: '/components/pageTable/02/01'},
            {name: '黑龙江', path: '/components/pageTable/02/02'},
        ]
    },
// 4.华中地区：湖、湖南、河南，
    {
        name: '华中地区',
        child: [
            {name: '介绍', path: '/components'},
            {name: '湖北', path: '/components/pageTable/03/00'},
            {name: '湖南', path: '/components/pageTable/03/01'},
            {name: '河南', path: '/components/pageTable/03/02'},
        ]
    },
// 5.华南地区：广东、广西、海南，
    {
        name: '华南地区',
        child: [
            {name: '介绍', path: '/components'},
            {name: '广东', path: '/components/pageTable/04/00'},
            {name: '广西', path: '/components/pageTable/04/01'},
            {name: '海南', path: '/components/pageTable/04/02'},
        ]
    },
// 6.西南地区：四川、重庆、贵州、云南、西藏
    {
        name: '西南地区',
        child: [
            {name: '介绍', path: '/components'},
            {name: '四川', path: '/components/pageTable/05/00'},
            {name: '重庆', path: '/components/pageTable/05/01'},
            {name: '贵州', path: '/components/pageTable/05/02'},
            {name: '云南', path: '/components/pageTable/05/03'},
            {name: '西藏', path: '/components/pageTable/05/04'},
        ]
    },
// 7.西北地区：陕西、甘肃、新疆、青海、宁夏
    {
        name: '西北地区',
        child: [
            {name: '介绍', path: '/components'},
            {name: '陕西', path: '/components/pageTable/06/00'},
            {name: '甘肃', path: '/components/pageTable/06/01'},
            {name: '新疆', path: '/components/pageTable/06/02'},
            {name: '青海', path: '/components/pageTable/06/03'},
            {name: '宁夏', path: '/components/pageTable/06/04'},
        ]
    },
// 8.港澳台地区：香港、澳门、台湾
    {
        name: '港澳台地区',
        child: [
            {name: '介绍', path: '/components'},
            {name: '香港', path: '/components/pageTable/07/00'},
            {name: '澳门', path: '/components/pageTable/07/01'},
            {name: '台湾', path: '/components/pageTable/07/02'},
        ]
    },
    {
        name: '系统组件',
        child: [
            {
                name: '介绍',
                path: '/components'
            },
            {
                name: '功能类',
                child: [
                    {
                        path: '/components/permission',
                        name: '详细鉴权'
                    },
                    {
                        path: '/components/pageTable',
                        name: '表格分页'
                    }
                ]
            },
            {
                name: '布局类',
                child: [
                    {
                        path: '/components/pageTitle',
                        name: '页面标题'
                    },
                    {
                        path: '/components/pageSection',
                        name: '子区域'
                    },
                    {
                        path: '/components/pageSearch',
                        name: '搜索条'
                    },
                    {
                        path: '/components/pageToolbar',
                        name: '工具条'
                    }
                ]
            },
            {
                name: '辅助类',
                child: [
                    {
                        path: '/components/pageNotes',
                        name: '引用说明'
                    }
                ]
            }
        ]
    },
    {
        name: '完整示例',
        child: [
            {
                path: '/example/table',
                name: '列表页面',
                permission: ['edit']
            },
            {
                path: '/example/charts',
                name: '图表页面'
            },
            {
                path: '/example/map',
                name: '地图页面'
            }
        ]
    },
    {
        path: '/i18n',
        name: '国际化'
    },
    {
        path: '/theme',
        name: '主题切换'
    }
]

export default [{
    path: '/user/navlist',
    data: data
}]
