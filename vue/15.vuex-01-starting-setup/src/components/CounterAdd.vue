<template>
  <button @click="inc2sec({value: 10})">Add 10 after 2sec</button>
</template>

<script>
import {mapActions} from 'vuex'
export default {
  setup() {},
  methods: {
    // increaseCounter() {
    //   this.$store.dispatch('increaseAfter2second', { value: 10 });
    // },
    ...mapActions({
        inc2sec: 'increaseAfter2second'
    })
  },
};
</script>
