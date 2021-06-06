<template>
  <div id="bottomLeft">
    <div class="bg-color-black">
      <div class="d-flex pt-2 pl-2">
        <span style="color:#5cd9e8">
          <icon name="chart-bar"></icon>
        </span>
        <div class="d-flex">
          <span class="fs-xl text mx-2">以某天为例机器人数量随时间变化数据统计图</span>
        </div>
      </div>
      <div>
        <BottomLeftChart />
      </div>
    </div>
  </div>
</template>

<script>
import BottomLeftChart from "@/components/echart/bottom/bottomLeftChart";
export default {
  data() {
    return {};
  },
  components: {
    BottomLeftChart
  },
  mounted() {},
  methods: {}
};
</script>

<style lang="scss">
#bottomLeft {
  padding: 0.3rem 0.2rem;
  height: 7.5rem;
  min-width: 15rem;
  border-radius: 0.0625rem;
  .bg-color-black {
    height: 7.25rem;
    border-radius: 0.125rem;
  }
  .text {
    color: #c3cbde;
  }
  .chart-box {
    margin-top: 0.2rem;
    width: 2.125rem;
    height: 2.125rem;
    .active-ring-name {
      padding-top: 0.125rem;
    }
  }
}
</style>