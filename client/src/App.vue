<template>
  <div id="app">
    <router-view/>
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
  font-family: 'Roboto', sans-serif;;
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
    color: $text-hover;
    cursor: pointer;
  }
}

.row-container {
  margin: 0 15px 15px;

  a, div {
    display: inline-block;
    background-color: $content-background;
    margin: 0 12px 0 0;
    width: fit-content;
    padding: 12px;
    font-family: 'JetBrains Mono', monospace;
  }
}

.button {
  display: inline-block;
  text-decoration: none;
  color: $text;
  background-color: $content-background;
  margin: 0 12px 0 0;
  width: fit-content;
  padding: 12px;
  font-family: 'JetBrains Mono', monospace;

  &:hover {
    cursor: pointer;
    color: $text;
    text-decoration: none;
    background-color: $content-background-hover;
  }
}

.row {
  margin-top: 15px;

  .col-container {
    background-color: $content-background;
    margin: 0 15px;

    h1 {
      padding: 12px;
      font-family: 'JetBrains Mono', monospace;
      font-size: 2.5em;
    }

    .no-data {
      padding: 12px;
      font-family: 'JetBrains Mono', monospace;
      font-size: 2em;
      color: darken($text, 40);
    }
  }
}

.version {
  font-family: 'JetBrains Mono', monospace;
}
</style>
