<template>
  <div>
    <el-row>
      <el-col :span="8">
        <div id="users" style="width: 500px; height: 400px">
        </div>
      </el-col>
      <el-col :span="8">
        <div id="resinfos" style="width: 500px; height: 400px">
        </div>
      </el-col>
      <el-col :span="8">
        <div id="articles" style="width: 500px; height: 400px"></div>
      </el-col>
    </el-row>

  </div>
</template>

<script>
import * as  echarts from 'echarts'

export default {
  name: "Home",
  data() {
    return {}
  },
  mounted() {


    var resChartDom = document.getElementById('resinfos');
    var resChart = echarts.init(resChartDom);
    var resOption = {
      title: {
        text: '每周答疑人数',
        subtext: '统计图',
        left: 'center'
      },
      xAxis: {
        type: 'category',
        data: []
      },
      yAxis: {
        type: 'value'
      },
      series: [
        {
          data: [],
          type: 'line'
        }, {
          data: [],
          type: 'bar'
        }
      ]
    };
    this.request.get('/echarts/resinfos').then(resp => {
      console.log(resp.data)
      resOption.xAxis.data = resp.data.x;
      resOption.series[0].data = resp.data.y;
      resOption.series[1].data = resp.data.y;
      resChart.setOption(resOption);
    })

    var pieChartDom = document.getElementById('users');
    var pieChart = echarts.init(pieChartDom);
    var pieOption = {
      title: {
        text: '系统角色',
        subtext: '统计图',
        left: 'center'
      },
      tooltip: {
        trigger: 'item'
      },
      legend: {
        orient: 'vertical',
        left: 'left'
      },
      series: [
        {
          name: 'Access From',
          type: 'pie',
          radius: '65%',
          data: [],
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        }
      ]
    };
    this.request.get("/echarts/users").then(res => {

      pieOption.series[0].data = [
        {name: "管理员", value: res.data[0]},
        {name: "教师", value: res.data[1]},
        {name: "学生", value: res.data[2]},
      ]
      pieChart.setOption(pieOption)
    })


    var articleChartDom = document.getElementById('articles');
    var articleChart = echarts.init(articleChartDom);
    var articleOption = {
      title: {
        text: '系统文章',
        subtext: '统计图',
        left: 'center'
      },
      tooltip: {
        trigger: 'item'
      },
      legend: {
        orient: 'vertical',
        left: 'left'
      },
      series: [
        {
          name: 'Access From',
          type: 'pie',
          radius: '65%',
          data: [],
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        }
      ]
    };
    this.request.get("/echarts/articles").then(res => {
      articleOption.series[0].data = [
        {name: "公告", value: res.data[0]},
        {name: "新闻", value: res.data[1]},
      ]
      articleChart.setOption(articleOption)
    })


  },
  methods: {}
}
</script>

<style scoped>

</style>
