<template>
  <div id="app">
    <b-container>
      <router-view/>
    </b-container>
  </div>
</template>

<script>
export default {
  name: 'App',
};
</script>

<style lang="scss">
@import "./style/variables";

body, html {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;

  color: $text;
  background-color: $background;
  margin: 0;
}

#app {
  margin: 40px;
}

.link {
  color: $text;

  &:hover {
    color: #fff;
    cursor: pointer;
  }
}
</style>
