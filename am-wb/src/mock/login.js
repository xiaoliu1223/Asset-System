import Mock from 'mockjs'

export default Mock.mock('/postdata1','post',{
    success: true,
    message: '@cparagraph',
    // 属性 list 的值是一个数组，其中含有 1 到 5 个元素
    state:200,
    data:'请求成功',
    userinfo:{
        name:'李警官'
    }
})

