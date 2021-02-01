<template>
  <div class="deviceDetails">
    <b-container fluid="lg">
      <b-row>
        <b-col class="row-container">
          <router-link :to="{name: 'Devices'}" class="button">
            &lt; Esc
          </router-link>
          <div>{{ device.name }}_{{ device.softwareVersion }}</div>
        </b-col>
      </b-row>
      <b-row cols="1" cols-lg="2">
        <b-col>
          <div class="col-container">
            <h1>&gt; Device info</h1>
            <table v-if="deviceTable.length" class="table table-hover table-dark table-borderless">
              <tbody>
              <tr v-for="(row, key) in deviceTable" :key="key">
                <td class="font-weight-bold">{{ row.name }}</td>
                <td :class="row.class">{{ row.value }}</td>
              </tr>
              </tbody>
            </table>
            <p v-else class="no-data">No data.</p>
          </div>
        </b-col>
        <b-col>
          <div class="col-container">
            <h1>&gt; Update history</h1>
            <table v-if="deviceHistory.length" class="table table-borderless table-dark table-hover">
              <thead>
              <tr>
                <th>#</th>
                <th>Timestamp</th>
                <th class="text-center">Change</th>
                <th>Status</th>
              </tr>
              </thead>
              <tbody>
              <tr v-for="(entry, key) in deviceHistory" :key="entry.id">
                <td class="font-weight-bold">{{ deviceHistory.length - key }}</td>
                <td>{{ entry.timestamp }}</td>
                <td class="text-center version">
                  <span
                      v-b-tooltip.hover.bottom
                      :title="entry.baseFile + ' ' + entry.baseCreatedAt + ' ' + entry.baseMd5"
                  >{{ entry.baseVersion }}</span>
                  -&gt;
                  <span
                      v-b-tooltip.hover.bottom
                      :title="entry.toFile + ' ' + entry.toCreatedAt + ' ' + entry.toMd5"
                  >{{ entry.toVersion }}</span>
                </td>
                <td :class="entry.statusClass">{{ entry.status }}</td>
              </tr>
              </tbody>
            </table>
            <p v-else class="no-data">No data.</p>
          </div>
        </b-col>
      </b-row>
    </b-container>
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
      device: null,
      deviceHistory: null
    };
  },

  mounted () {
    this.fetchAPIData();
    this.fetchInterval = setInterval(() => {this.fetchAPIData();}, 5000);
  },

  beforeDestroy () {
    clearInterval(this.fetchInterval);
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
        { name: 'Device name', value: this.device.name },
        { name: 'Updates status', value: this.device.status, class: this.device.statusClass },
        { name: 'Software version', value: this.device.softwareVersion, class: 'version' },
        { name: 'Software name scheme', value: this.device.softwareNameScheme },
        { name: 'MAC address', value: this.device.mac },
        { name: 'Last software check', value: this.device.lastSoftwareCheck },
        { name: 'Last software update', value: this.device.lastSoftwareUpdate },
      ];
    }
  }
};

</script>
