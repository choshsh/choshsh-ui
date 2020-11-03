<template>
  <CChartDoughnut
    :datasets="defaultDatasets"
    :labels="labels"
    :options="defaultOptions"
  />
</template>

<script>
import { CChartDoughnut } from "@coreui/vue-chartjs";

export default {
  name: "CChartDoughnutAdv",
  components: { CChartDoughnut },
  data() {
    return {
      backgroundColor: ["#007bff", "#DD1B16", "#41B883", "#E46651", "#f9b115"],
    };
  },
  props: {
    datas: Array,
    labels: Array,
  },
  computed: {
    defaultDatasets() {
      return [
        {
          data: this.datas,
          backgroundColor: this.backgroundColor,
        },
      ];
    },
    defaultOptions() {
      return {
        legend: {
          display: true,
          position: "top",
          labels: {
            boxWidth: 20,
          },
          /* reverse: true */
        },
        rotation: 0.5 * Math.PI,
        animation: {
          duration: 0, // general animation time
        },
        // responsiveAnimationDuration: 0,
      };
    },
  },
};
</script>
