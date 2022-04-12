<template>
  <button @click="saveData">SaveData</button>
  <router-view></router-view>
  <ul>
    <teams-item
      v-for="team in teams"
      :key="team.id"
      :name="team.name"
      :member-count="team.members.length"
      :id="team.id"
    ></teams-item>
  </ul>
</template>

<script>
import TeamsItem from './TeamsItem.vue';

export default {
  components: {
    TeamsItem,
  },
  data() {
    return {
      dataHaveSaved: false
    }
  },
  inject: ['teams'],
  beforeRouteEnter(to, from, next) {
    console.log('Before team cmp enter:', to , from);
    next()
  },
  beforeRouteLeave(to, from, next) {
    console.log('Before team cmp leave:', to, from)
    if (this.dataHaveSaved) {
      next()
    } else {
      const confirmResult = confirm('Your data is not save yet! Are you sure to leave?')
      next(confirmResult)
    }
  },
  methods: {
    saveData() {
      console.log('Already save data!')
      this.dataHaveSaved = true
    }
  }
};
</script>

<style scoped>
ul {
  list-style: none;
  margin: 2rem auto;
  max-width: 40rem;
  padding: 0;
}
</style>
