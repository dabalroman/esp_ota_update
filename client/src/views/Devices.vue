<template>
  <div class="devices">
    <b-container fluid="lg">
      <b-row>
        <b-col class="row-container">
          <div>ESPOTA UPDATE SERVER SUPERVISOR</div>
        </b-col>
      </b-row>
      <b-row>
        <b-col>
          <div class="col-container">
            <h1>&gt; Device list</h1>
            <table v-if="devices.length" class="table table-hover table-dark table-borderless">
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
              <tr class="link"
                  v-for="(device, key) in devices" :key="device.id"
                  @click="goToDetails(device.id)">
                <td>{{ key + 1 }}</td>
                <td>{{ device.name }}</td>
                <td :class="[device.statusClass, 'text-center']">{{ device.status }}</td>
                <td class="text-center version">{{ device.softwareVersion }}</td>
                <td class="text-right">{{ device.lastSoftwareCheck }}</td>
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

import apiUrls from '@/data/apiUrls';
import Device from '@/components/Device';

export default {
  name: 'Devices',

  data () {
    return {
      devices: null, //To declare Device[] type
      fetchInterval: null
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
      fetch(apiUrls.devices)
          .then(response => response.json())
          .then(response => {
            let data = response.data;

            this.devices = data.map(d => new Device(
                d['id'],
                d['name'],
                d['mac'],
                d['softwareNameScheme'],
                d['version'],
                d['status'],
                d['lastSoftwareCheck'],
                d['lastSoftwareUpdate']
            ));
          });
    },

    goToDetails (deviceId) {
      this.$router.push({ name: 'DeviceDetails', params: { deviceId: deviceId } });
    }
  }
};

</script>
