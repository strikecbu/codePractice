<template>
  <section class="container">
    <h2>{{ fullName }}</h2>
    <h2>{{ user.userAge }}</h2>
    <h3>{{ user.userName }}</h3>
    <button @click="changeAge">ChangeAge</button>
    <div>
      <input type="text" @input="setFirstName" />
      <input type="text" @input="setLastName" />
    </div>
  </section>
</template>

<script>
import { computed, reactive, ref, watch } from 'vue';

export default {
  setup() {
    const user = reactive({
      userName: 'Andy',
      userAge: 34,
    });
    const firstName = ref('');
    const lastName = ref('');
    function changeAge() {
      user.userAge = 18;
    }

    const setFirstName = (event) => {
      firstName.value = event.target.value;
    };
    const setLastName = (event) => {
      lastName.value = event.target.value;
    };

    const fullName = computed(() => {
      return firstName.value + ' ' + lastName.value;
    });

    watch(fullName, (value) => {
      user.userName = value
    })
    return { user, changeAge, setFirstName, setLastName, fullName };
  },
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
