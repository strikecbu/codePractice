<template>
  <section class="container">
    <h2>{{ user.userName }}</h2>
    <h2>{{ user.userAge }}</h2>
    <button @click="changeAge">ChangeAge</button>
  </section>
</template>

<script>
import {reactive} from "vue";

export default {
  setup() {
    const user = reactive({
      userName: 'Andy',
      userAge: 34
    })
    function changeAge() {
      user.userAge = 18
    }
    return {user, changeAge}
  }
  // data() {
  //   return {
  //     userName: 'Maximilian',
  //   };
  // },
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

.container {
  margin: 3rem auto;
  max-width: 30rem;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.26);
  padding: 1rem;
  text-align: center;
}
</style>
