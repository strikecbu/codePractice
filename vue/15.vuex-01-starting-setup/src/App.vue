<template>
  <base-container title="Vuex">
    <counter></counter>
  </base-container>
</template>

<script>
import BaseContainer from './components/BaseContainer.vue';
import Counter from './components/Counter.vue';

export default {
  components: {
    BaseContainer,
    Counter
  },
};
</script>

<style>
* {
  box-sizing: border-box;
}

html {
  font-family: sans-serif;
}

body {
  margin: 0;
}
</style>