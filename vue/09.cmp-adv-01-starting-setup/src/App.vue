<template>
  <div>
    <the-header></the-header>
    <badge-list></badge-list>
    <user-info
      :full-name="activeUser.name"
      :info-text="activeUser.description"
      :role="activeUser.role"
    ></user-info>
    <button @click="selectCoponent('main-course')">main course</button>
    <button @click="selectCoponent('sub-course')">sub course</button>
    <keep-alive>
      <component :is="selectedComponent"></component>
    </keep-alive>
  </div>
</template>

<script>
import TheHeader from "./components/TheHeader.vue";
import BadgeList from "./components/BadgeList.vue";
import UserInfo from "./components/UserInfo.vue";
import MainCourse from "./components/MainCourse.vue";
import SubCourse from "./components/SubCourse.vue";

export default {
  components: {
    TheHeader,
    BadgeList,
    UserInfo,
    MainCourse,
    SubCourse,
  },
  data() {
    return {
      selectedComponent: "",
      activeUser: {
        name: "Maximilian Schwarzmüller",
        description: "Site owner and admin",
        role: "admin",
      },
    };
  },
  methods: {
    selectCoponent(comp) {
      this.selectedComponent = comp;
    },
  },
};
</script>

<style>
html {
  font-family: sans-serif;
}

body {
  margin: 0;
}
</style>
