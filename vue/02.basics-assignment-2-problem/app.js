Vue.createApp({
  data() {
    return {
      keydownValue: "",
      enteredValue: "",
    };
  },
  methods: {
    onAlert() {
      alert("Run for your life!!!");
    },
    showKeydownValue(event) {
      this.keydownValue = event.target.value;
    },
    showEnteredValue(event) {
      this.enteredValue = event.target.value;
    },
  },
}).mount("#assignment");
