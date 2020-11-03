<template>
  <CChartLine
    :datasets="defaultDatasets"
    :labels="labels"
    :plugins="defaultPlugins"
    :options="defaultOptions"
  />
</template>

<script>
import { CChartLine } from "@coreui/vue-chartjs";
import ChartJsPluginDataLabels from "chartjs-plugin-datalabels";

export default {
  name: "CChartLineAdv",
  components: { CChartLine, ChartJsPluginDataLabels },
  data() {
    return {
      backgroundColor: ["#007bff", "#DD1B16", "#41B883", "#E46651"],
    };
  },
  props: {
    datas: Array,
    label: Array,
    yMax: { type: Number, default: 200 },
    steppedLine: { type: Boolean, default: false },
    labels: {
      type: Array,
      default: () => [
        "1월",
        "2월",
        "3월",
        "4월",
        "5월",
        "6월",
        "7월",
        "8월",
        "9월",
        "10월",
        "11월",
        "12월",
      ],
    },
  },
  computed: {
    defaultDatasets() {
      return this.setData();
    },
    defaultPlugins() {
      return [ChartJsPluginDataLabels];
    },
    defaultOptions() {
      return {
        legend: {
          display: true,
          position: "top",
          labels: {
            boxWidth: 20,
          },
        },
        plugins: {
          datalabels: {
            color: this.color,
            font: {
              weight: "bold",
              size: 12,
            },
            align: "top",
          },
        },
        scales: {
          xAxes: [
            {
              display: true,
            },
          ],
          yAxes: [
            {
              display: true,
              ticks: {
                min: 0,
                max: this.yMax,
                maxTicksLimit: 200 / 50 + 1,
              },
            },
          ],
        },
        animation: {
           easing: "easeOutCirc",
        },
      };
    },
  },
  methods: {
    setData() {
      const arr = new Array();
      this.datas.map((o, index) => {
        arr.push({
          label: this.label[index],
          backgroundColor: this.backgroundColor[index],
          borderColor: this.backgroundColor[index],
          pointBorderWidth: 2,
          pointHoverBorderWidth: 6,
          data: o,
          fill: false,
          steppedLine: this.steppedLine,
        });
      });
      return arr;
    },
  },
};
</script>
