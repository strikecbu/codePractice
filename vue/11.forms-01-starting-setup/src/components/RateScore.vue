<template>
  <ul>
    <li :class="{active: bind === 'poor'}" @click="selectItem('poor')">Poor</li>
    <li :class="{active: bind === 'great'}" @click="selectItem('great')">Great</li>
    <li :class="{active: bind === 'excellent'}" @click="selectItem('excellent')">Excellent</li>
  </ul>
</template>

<script>
export default {
  props: ['modelValue'],
  emits: ['update:modelValue'],
  computed: {
    bind() {
      return this.modelValue;
    }
  },
  data() {
    return {
      selectedItem: null
    }
  },
  methods: {
    selectItem(item) {
      this.$emit('update:modelValue', item)
    }
  }
}
</script>

<style scoped>
.active {
  border-color: red;
  background-color: #1b995e;
}
ul {
  list-style: none;
  display: flex;
  gap: 2px;
}
li {
  border: #1f1f1f solid 1px;
  padding: 1rem;
}
li:hover {
  background-color: #2C9DD4;
}
</style>
