<template>
  <CChartBar
    :datasets="defaultDatasets"
    :plugins="plugins"
    :options="options"
    labels="months"
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
      plugins: [ChartJsPluginDataLabels],
      options: {
        plugins: {
          datalabels: {
            color: "#e55353",
            font: {
              weight: "bold"
            },
            align: "top"
          }
        }
      }
    };
  },
  computed: {
    defaultDatasets() {
      return [
        {
          label: "GitHub Commits",
          backgroundColor: "#e55353",
          data: [40, 20, 12, 39, 10, 40, 39, 80, 40, 20, 12, 11]
        }
      ];
    }
  }
};
</script>
