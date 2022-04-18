<template>

  <h2 v-if="titles.showUserGoal">{{ titles.userGoal }}</h2>
  <!-- Task 1: Output your main course goal with help of the composition API -->
  <!-- Don't hardcode it into the template, instead hardcode it into the JS code -->
  <h3 v-else>{{ titles.courseGoal }}</h3>
  <p>{{ courseGoal }}</p>
  <!-- Task 2: Toggle (show/ hide) the goal with help of the button  -->
  <button @click="toggleGoal">Toggle Goal</button>
  <!-- Task 3: Manage data in three ways -->
  <!-- => Separate refs -->
  <!-- => Ref Object -->
  <!-- => Reactive Object -->
  <!-- Task 4: Also solve the assignment with the Options API -->
</template>

<script>
import {ref, reactive} from 'vue'

export default {
  setup() {
    const courseGoal = ref('Vue Master')
    const titles = reactive({
      userGoal: 'My Course Goal: ',
      courseGoal: 'OUTPUT COURSE GOAL',
      showUserGoal: true
    })
    const toggleGoal = () => {
      titles.showUserGoal = !titles.showUserGoal
    }
    return {courseGoal, titles, toggleGoal}
  }
}
</script>

<style>
html {
  font-family: sans-serif;
}

body {
  margin: 3rem;
  text-align: center;
}
</style>
