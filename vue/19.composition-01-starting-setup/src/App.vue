<template>
  <section class="container">
    <data-view
      :firstName="firstName"
      :lastName="lastName"
      :phoneNum="phoneNum"
      :userName="user.userName"
    ></data-view>


    <button @click="changeAge">ChangeAge</button>
    <div>
      <input type="text" @input="setFirstName" />
      <input type="text" v-model="lastName" />
    </div>
    <div>
      <label for="phone">Phone:</label>
      <input id="phone" type="text" ref="phoneRef" />
      <br />
      <button @click="changePhone">Change Phone</button>
    </div>
  </section>
</template>

<script>
import { computed, provide, reactive, ref, watch } from 'vue';
import DataView from './components/DataView.vue';
export default {
  components: { DataView },
  setup() {
    const user = reactive({
      userName: 'Andy',
      userAge: 34,
    });
    const firstName = ref('');
    const lastName = ref('');
    const phoneRef = ref(null);
    const phoneNum = ref('');

    const userAge = computed(() => {
      return user.userAge;
    })

    provide('userAge', userAge);

    function changeAge() {
      user.userAge = 18;
    }

    const setFirstName = (event) => {
      firstName.value = event.target.value;
    };
    // const setLastName = (event) => {
    //   lastName.value = event.target.value;
    // };

    function changePhone() {
      phoneNum.value = phoneRef.value.value;
    }

    const fullName = computed(() => {
      return firstName.value + ' ' + lastName.value;
    });

    watch(fullName, (value) => {
      user.userName = value;
    });

    provide();
    return {
      user,
      firstName,
      lastName,
      changeAge,
      setFirstName,
      changePhone,
      fullName,
      phoneRef,
      phoneNum,
    };
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
