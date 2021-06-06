<template>
  <div>
    <Chart :cdata="cdata" />
  </div>
</template>

<script>
import Chart from './chart.vue';
export default {
  data () {
    return {
      cdata: {
        xData: ["撞库", "爬虫", "秒杀", "刷单"],
        seriesData: [
          { value: 36, name: "撞库" },
          { value: 361, name: "秒杀" },
          { value: 67, name: "爬虫" },
          { value: 257, name: "刷单" }
        ]
      }
    }
  },
  components: {
    Chart,
  },
  mounted () {
  },
  methods: {
  }
}
</script>

<style lang="scss" scoped>
</style>