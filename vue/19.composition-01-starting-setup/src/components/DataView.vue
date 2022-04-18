<template>
  <h2>{{ fullName }}</h2>
  <h2>{{ age }}</h2>
  <h3>{{ userName }}</h3>
  <h3>{{ phoneNum }}</h3>
</template>

<script>
import { computed, inject } from '@vue/runtime-core';
export default {
  props: ['firstName', 'lastName', 'userName', 'phoneNum'],
  setup(props) {
    const fullName = computed(() => {
      return props.firstName + ' ' + props.lastName;
    });
    const age = inject('userAge');
    return { fullName, age };
  },
};
</script>
