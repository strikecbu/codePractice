<template>
  <h2>Please fill your data here:</h2>
  <form class="data__form">
    <div>
      <label>
        name:
        <input type="text" v-model="name" @input="$emit('name-change', name)" />
      </label>
    </div>
    <div>
      <label>
        age:
        <input type="number" v-model="age" @input="$emit('age-change', age)" />
      </label>
    </div>
  </form>
</template>

<script>
export default {
  emits: ["name-change", "age-change"],
  data() {
    return {
      name: "",
      age: "",
    };
  },
  setup() {},
};
</script>

<style>
.data__form {
  display: flex;
  flex-direction: column;
  justify-content: center;
  gap: 5px;
}
</style>
