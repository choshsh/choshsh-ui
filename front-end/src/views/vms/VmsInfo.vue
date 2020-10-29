<template>
  <table border="1" class="col-md-12 table-outline">
    <tbody>
      <tr>
        <th>업무명</th>
        <td>{{ item.vmNickname }}</td>
        <th>VM명</th>
        <td>{{ item.vmNm }}</td>
        <th>DNS</th>
        <td>{{ item.vmHostname }}</td>
      </tr>
      <tr>
        <th>운영 담당자</th>
        <td>{{ item.vmUseEmpl }}</td>
        <th>관리 담당자</th>
        <td>{{ item.vmMngEmpl }}</td>
        <th>생성일자</th>
        <td>{{ item.vmRegDate }}</td>
      </tr>
      <tr>
        <th>CPU</th>
        <td>{{ item.vmCpuCore }} Cores</td>
        <th>MEMORY</th>
        <td>{{ item.vmMemory }} GB</td>
        <th>DISK</th>
        <td>{{ item.vmDisk }} GB</td>
      </tr>
      <tr>
        <th>Comment</th>
        <td colspan="5">
          <CTextarea rows="5" :value="item.vmComment" />
        </td>
      </tr>
    </tbody>
  </table>
</template>

<script>
export default {
  name: "VmsInfo",
  props: {
    item: Object,
  },
};
</script>