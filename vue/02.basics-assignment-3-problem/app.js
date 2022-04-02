Vue.createApp({
  data() {
    return {
      value: 0,
      result: "",
    };
  },
  computed: {
    showResult() {
      if (this.value < 37) {
        this.result = "Not there yet";
        console.log("Not there yet");
      } else if (this.value > 37) {
        this.result = "Too much!";
        console.log("Too much!");
      } else {
        this.result = "Got it!!";
        console.log("Got it!!");
      }
      return this.result;
    },
  },
  watch: {
    result(value) {
      const that = this;
      console.log("w-" + value);
      setTimeout(() => {
        that.value = 0;
        console.log("t-" + that.value);
      }, 5000);
    },
  },
  methods: {
    add(num) {
      this.value += num;
      console.log(this.value);
    },
  },
}).mount("#assignment");
