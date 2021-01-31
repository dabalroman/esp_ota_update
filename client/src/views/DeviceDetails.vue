<template>
  <div class="deviceDetails">
    Details of {{ deviceId }}

    <table class="table table-hover table-dark table-borderless">
      <tbody>
      <tr v-for="(row, key) in deviceTable" :key="key">
        <td class="font-weight-bold">{{ row.name }}</td>
        <td :class="row.class">{{ row.value }}</td>
      </tr>
      </tbody>
    </table>

    <table class="table table-borderless table-dark table-hover">
      <thead>
      <tr>
        <th>#</th>
        <th>Timestamp</th>
        <th></th>
        <th>Status</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(entry, key) in deviceHistory" :key="entry.id">
        <td class="font-weight-bold">{{ deviceHistory.length - key }}</td>
        <td>{{ entry.timestamp }}</td>
        <td>{{ entry.baseVersion }}
          <b-icon-arrow-right style="margin: 0 1em"/>
          {{ entry.toVersion }}
        </td>
        <td :class="entry.statusClass">{{ entry.status }}</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>

import Device from '@/components/Device';
import DeviceHistory from '@/components/DeviceHistory';
import apiUrls from '@/data/apiUrls';

export default {
  name: 'DeviceDetails',

  data () {
    return {
      deviceId: this.$route.params['deviceId'],
      device: new Device, //To declare Device type
      deviceHistory: [new DeviceHistory] //To declare DeviceHistory type
    };
  },

  mounted () {
    this.fetchAPIData();
  },

  methods: {
    fetchAPIData () {
      fetch(apiUrls.device + this.deviceId)
          .then(response => response.json())
          .then(response => {
            let data = response.data;

            this.device = data.map(d => new Device(
                d['id'],
                d['name'],
                d['mac'],
                d['softwareNameScheme'],
                d['version'],
                d['status'],
                d['lastSoftwareCheck'],
                d['lastSoftwareUpdate']
            ))[0];
          });

      fetch(apiUrls.history + this.deviceId)
          .then(response => response.json())
          .then(response => {
            let data = response.data;

            this.deviceHistory = data.map(d => new DeviceHistory(
                d['updateId'],
                d['deviceId'],
                d['timestamp'],
                d['status'],
                d['baseVersion'],
                d['baseMd5'],
                d['baseFile'],
                d['baseCreatedAt'],
                d['toVersion'],
                d['toMd5'],
                d['toFile'],
                d['toCreatedAt']
            ));
          });
    }
  },

  computed: {
    deviceTable: function () {
      return [
        { name: 'Internal Id', value: this.device.id },
        { name: 'Device name', value: this.device.name },
        { name: 'Updates status', value: this.device.status, class: this.device.statusClass },
        { name: 'MAC address', value: this.device.mac },
        { name: 'Software name scheme', value: this.device.softwareNameScheme },
        { name: 'Software version', value: this.device.softwareVersion },
        { name: 'Last software check', value: this.device.lastSoftwareCheck },
        { name: 'Last software update', value: this.device.lastSoftwareUpdate },
      ];
    }
  }
};

</script>
