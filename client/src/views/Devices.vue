<template>
  <div class="devices">
    <b-table striped hover :items="items" :fields="fields"></b-table>
  </div>
</template>

<script>

import apiUrls from '@/data/apiUrls';
import Device from '@/components/Device';

export default {
  name: 'Devices',

  data () {
    return {
      items: [],
      fields: ['id', 'device_name', 'macAddress', 'status', 'softwareVersion', 'lastSoftwareUpdateCheck']
    };
  },

  mounted () {
    this.fetchAPIData();
  },

  methods: {
    fetchAPIData () {
      fetch(apiUrls.devices)
          .then(response => response.json())
          .then(response => {
            let data = response.data;

            console.log(data);

            for (let i = 0; i < data.length; i++) {
              let d = new Date(data[i]['lastSoftwareCheck']);
              console.log(d.toLocaleDateString());
              let dateString = d.getHours() + ':' + d.getMinutes() + ':' + d.getSeconds() + ' ' +
                  d.getDate() + '/' + (d.getMonth() + 1) + '/' + d.getFullYear();

              data[i] = {
                id: i + 1,
                device_name: data[i]['name'],
                macAddress: data[i]['mac'],
                status: Device.mapStatusToString(data[i]['status']),
                softwareVersion: data[i]['version'] ?? '-',
                lastSoftwareUpdateCheck: dateString
              };
            }

            this.items = data;
          });
    }
  }
};

</script>
