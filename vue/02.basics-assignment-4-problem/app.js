Vue.createApp({
  data() {
    return {
      userClass: "",
      toggleVisible: true,
      inlineColor: "gray",
    };
  },
  computed: {
    userChooseClass() {
      if (this.userClass === "user1") {
        return { user1: true };
      } else if (this.userClass === "user2") {
        return { user2: true };
      }
      return {};
    },
    toggleVisibleClass() {
      if (this.toggleVisible) {
        return { visible: true };
      }
      return { hidden: true };
    },
  },
  methods: {
    toggleParagraph() {
      this.toggleVisible = !this.toggleVisible;
    },
  },
}).mount("#assignment");
