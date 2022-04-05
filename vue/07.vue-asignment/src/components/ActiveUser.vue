<template>
  <h1>My Data:</h1>
  <h2>{{ userName }}</h2>
  <h3>{{ age }}</h3>
</template>

<script>
export default {
  props: {
    userName: String,
    age: Number,
  },
  data() {
    return {};
  },
  setup() {},
};
</script>
