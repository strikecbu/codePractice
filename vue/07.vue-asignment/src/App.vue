<template>
  <h2>Hello World</h2>
  <user-data-vue
    @name-change="nameHandle"
    @age-change="ageHandle"
  ></user-data-vue>
  <active-user-vue :userName="name" :age="age"></active-user-vue>
</template>

<script>
import ActiveUserVue from "./components/ActiveUser.vue";
import UserDataVue from "./components/UserData.vue";
export default {
  data() {
    return {
      name: "",
      age: 0,
    };
  },
  methods: {
    nameHandle(val) {
      this.name = val;
    },
    ageHandle(val) {
      this.age = +val;
    },
  },
  setup() {},
  components: { ActiveUserVue, UserDataVue },
};
</script>
