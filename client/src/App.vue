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
  text-decoration: none;
  text-shadow: none;

  &:hover {
    color: #fff;
    transition: 0.2s;
    text-decoration: none;
    text-shadow: $background 2px 2px;
  }
}
</style>
