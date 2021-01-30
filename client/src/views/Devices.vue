<template>
  <div class="devices">
    <table class="table table-striped table-hover table-dark table-borderless">
      <thead>
      <tr>
        <th>#</th>
        <th>Device</th>
        <th class="text-center">Status</th>
        <th class="text-center">Software version</th>
        <th class="text-right">Last update check</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(device, key) in devices" :key="device.id">
        <td>{{ key + 1 }}</td>
        <td>{{ device.name }}</td>
        <td :class="[device.statusClass, 'text-center']">{{ device.status }}</td>
        <td class="text-center">{{ device.softwareVersion }}</td>
        <td class="text-right">{{ device.lastSoftwareCheck }}</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>

import apiUrls from '@/data/apiUrls';
import Device from '@/components/Device';

export default {
  name: 'Devices',

  data () {
    return {
      devices: []
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

            data = data.map(d => new Device(
                d['id'],
                d['name'],
                d['mac'],
                d['softwareNameScheme'],
                d['version'],
                d['status'],
                d['lastSoftwareCheck'],
                d['lastSoftwareUpdate']
            ));

            console.log(data);

            this.devices = data;
          });
    }
  }
};

</script>
