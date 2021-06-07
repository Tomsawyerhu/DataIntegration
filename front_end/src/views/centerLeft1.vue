<template>
  <div id="centerLeft1">
    <div class="bg-color-black">
      <div class="d-flex pt-2 pl-2">
        <span style="color:#5cd9e8">
          <icon name="chart-bar"></icon>
        </span>
        <div class="d-flex">
          <span class="fs-xl text mx-2">机器人捕捉情况</span>
          <dv-decoration-3 style="width:1.25rem;height:.25rem; position:relative;top:-.0375rem;" />
        </div>
      </div>
      <div class="d-flex jc-center">
        <CenterLeft1Chart />
      </div>
      <!-- 4个主要的数据 -->
      <div class="bottom-data">
        <div class="item-box" v-for="(item,index) in numberData" :key="index">
          <div class="d-flex">
<!--            <span class="coin">￥</span>-->
            <dv-digital-flop :config="item.number" style="width:4rem;height:1rem;" />
          </div>
          <p class="text" style="text-align: center;">
            {{item.text}}
            <span class="colorYellow">(个)</span>
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import CenterLeft1Chart from "@/components/echart/centerLeft/centerLeft1Chart";
export default {
  data() {
    return {
      numberData: [
        {
          number: {
            number: [36],
            toFixed: 0,
            content: "{nt}"
          },
          text: "撞库机器人"
        },
        {
          number: {
            number: [67],
            toFixed: 0,
            content: "{nt}"
          },
          text: "爬虫机器人"
        },
        {
          number: {
            number: [361],
            toFixed: 0,
            content: "{nt}"
          },
          text: "秒杀机器人"
        },
        {
          number: {
            number: [257],
            toFixed: 0,
            content: "{nt}"
          },
          text: "刷单机器人"
        }
      ]
    };
  },
  components: {
    CenterLeft1Chart
  },
  // mounted() {
  //   this.changeTiming();
  // },
  // methods: {
  //   changeTiming() {
  //     setInterval(() => {
  //       this.changeNumber();
  //     }, 3000);
  //   },
  //   changeNumber() {
  //     this.numberData.forEach((item, index) => {
  //       item.number.number[0] += ++index;
  //       item.number = { ...item.number };
  //     });
  //   }
  // }
};
</script>

<style lang="scss">
#centerLeft1 {
  padding: 1rem;
  height: 5.125rem;
  min-width: 3.75rem;
  border-radius: 0.0625rem;
  .bg-color-black {
    height: 8rem;
    border-radius: .25rem;
  }
  .text {
    color: #c3cbde;
  }
  .chart-box {
    padding: 1rem;
    margin-top: 0.5rem;
    width: 5rem;
    height: 5rem;
    .active-ring-name {
      padding-top: 0.5rem;
    }
  }

  .bottom-data {
    .item-box {
      float: right;
      position: relative;
      width: 50%;
      color: #d3d6dd;
      // 金币
      .coin {
        position: absolute;
        left: 0.1rem;
        top: 0.2125rem;
        font-size: 0.25rem;
        color: #ffc107;
      }
      .colorYellow {
        color: yellowgreen;
      }
    }
  }
}
</style>