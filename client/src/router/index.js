import Vue from 'vue';
import VueRouter from 'vue-router';
import Devices from '@/views/Devices';
import DeviceDetails from '@/views/DeviceDetails';

Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    name: 'Devices',
    component: Devices
  },
  {
    path: '/:deviceId',
    name: 'DeviceDetails',
    component: DeviceDetails
  }
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
});

export default router;
