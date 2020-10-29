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
  props: {
    color: String,
    datas: Array,
    maxValParam: Number,
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
        "12월"
      ]
    }
  },
  computed: {
    defaultDatasets() {
      return [
        {
          label: this.labelNm,
          backgroundColor: this.color,
          data: this.datas
        }
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
              size: 14
            },
            align: "top"
          }
        },
        legend: {
          display: false
        },
        scales: {
          xAxes: [
            {
              display: true,
              gridLines: {
                display: false
              }
            }
          ],
          yAxes: [
            {
              display: true,
              ticks: {
                min: 0,
                max: this.getMaxVal(),
                maxTicksLimit: this.getMaxVal() / 50 + 1
              }
            }
          ]
        }
      };
    }
  },
  methods: {
    getMaxVal() {
      let maxVal = Math.max.apply(Math, this.datas);
      return this.maxValParam > 0
        ? this.maxValParam
        : maxVal < 50
        ? 50
        : Math.ceil(maxVal / 50) * 50;
    }
  }
};
</script>
