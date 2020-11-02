<template>
  <CChartBar
    :datasets="defaultDatasets"
    :plugins="defaultPlugins"
    :options="defaultOptions"
    :labels="labels"
  />
</template>

<script>
import { CChartBar } from "@coreui/vue-chartjs";
import ChartJsPluginDataLabels from "chartjs-plugin-datalabels";

export default {
  name: "CChartBarExample",
  components: { CChartBar, ChartJsPluginDataLabels },
  data() {
    return {
      colors: ["#007bff", "#DD1B16", "#41B883", "#E46651"],
    };
  },
  props: {
    datas: Array,
    maxValParam: {
      type: Number,
      default: () => 100,
    },
    maxValParamTick: {
      type: Number,
      default: () => 50,
    },
    label: Array,
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
      return [
        {
          label: this.label[0],
          data: this.datas[0],
          backgroundColor: this.colors[0],
        },
        {
          label: this.label[1],
          data: this.datas[1],
          backgroundColor: this.colors[1],
        },
      ];
    },
    defaultPlugins() {
      return [ChartJsPluginDataLabels];
    },
    defaultOptions() {
      return {
        plugins: {
          datalabels: {
            font: {
              weight: "bold",
              size: 14,
            },
            align: "top",
          },
        },
        legend: {
          display: true,
          position: "bottom",
          labels: {
            boxWidth: 20,
          },
        },
        scales: {
          xAxes: [
            {
              display: true,
              gridLines: {
                display: false,
              },
            },
          ],
          yAxes: [
            {
              display: true,
              ticks: {
                min: 0,
                max: Math.ceil(this.maxValParam / this.maxValParamTick) * this.maxValParamTick,
                maxTicksLimit: 3,
              },
            },
          ],
        },
      };
    },
  },
};
</script>
