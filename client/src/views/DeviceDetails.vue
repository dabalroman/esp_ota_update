<template>
  <div class="deviceDetails">
    Details of {{ deviceId }}
  </div>
</template>

<script>

export default {
  name: 'DeviceDetails',

  data: function () {
    return {
      deviceId: this.$route.params['deviceId']
    };
  }
};

</script>
