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
    legendName: String,
    maxTicksLimit: { type: Number, default: 4 },
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
                maxTicksLimit: this.maxTicksLimit,
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
      return [
        {
          label: this.legendName,
          backgroundColor: "rgb(228,102,81,0.9)",
          data: this.datas,
          backgroundColor: "#007bff",
          borderColor: "#007bff",
          pointBorderWidth: 2,
          pointHoverBorderWidth: 6,
          fill: false,
          steppedLine: this.steppedLine,
        },
      ];
    },
  },
};
</script>
