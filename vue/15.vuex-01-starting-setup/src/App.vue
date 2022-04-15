<template>
  <base-container title="Vuex" v-if="isAuth">
    <counter></counter>
  </base-container>
  <base-container title="Auth">
    <auth-status></auth-status>
    <auth></auth>
  </base-container>
</template>

<script>
import BaseContainer from './components/BaseContainer.vue';
import Counter from './components/Counter.vue';
import Auth from './components/Auth.vue';
import AuthStatus from './components/AuthStatus.vue';

export default {
  components: {
    BaseContainer,
    Counter,
    Auth,
    AuthStatus

  },
  computed: {
        isAuth() {
           return this.$store.getters.getLoginStatus;
        }
    }
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