<template>
  <h2>Main Course</h2>
  <input type="text" ref="text" />
  <button @click="validValue">submit</button>
  <teleport to="body">
    <alert-box v-if="showAlert">
      <h2>Warning!</h2>
      <p>please keyin something...</p>
      <button @click="confirmAlert">OK</button>
    </alert-box>
  </teleport>
</template>

<script>
import AlertBox from "./AlertBox.vue";
export default {
  components: { AlertBox },
  data() {
    return {
      showAlert: false,
    };
  },
  setup() {},
  methods: {
    validValue() {
      if (this.$refs.text.value === "") {
        this.showAlert = true;
      }
    },
    confirmAlert() {
      this.showAlert = false;
    },
  },
};
</script>
