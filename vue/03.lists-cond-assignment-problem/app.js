Vue.createApp({
  data() {
    return {
      tasks: [],
      taskInput: "",
      showTask: true,
    };
  },
  methods: {
    addTask() {
      this.tasks.push(this.taskInput);
      this.taskInput = "";
    },
    removeTask(index) {
      this.tasks.splice(index, 1);
    },
    toggleShowTasks() {
      this.showTask = !this.showTask;
    },
  },
}).mount("#assignment");
