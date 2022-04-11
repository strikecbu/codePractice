<template>
  <section>
    <base-card>
      <h2>Submitted Experiences</h2>
      <div>
        <base-button @click="loadSurveys">Load Submitted Experiences</base-button>
      </div>
      <p v-if="isLoading">Loading...</p>
      <p v-else-if="!!error">{{ error }}</p>
      <p v-else-if="results.length === 0">Nothing here! Add new one first!</p>
      <ul v-else>
        <survey-result
            v-for="result in results"
            :key="result.id"
            :name="result.name"
            :rating="result.rating"
        ></survey-result>
      </ul>
    </base-card>
  </section>
</template>

<script>
import SurveyResult from './SurveyResult.vue';

export default {
  props: [],
  components: {
    SurveyResult,
  },
  data() {
    return {
      isLoading: false,
      error: null,
      results: []
    }
  },
  methods: {
    async loadSurveys() {
      this.results = [];
      this.isLoading = true;
      this.error = null
      const url = 'https://vue-http-bbb10-default-rtdb.asia-southeast1.firebasedatabase.app/surveys.json'
      const response = await fetch(url)
      try {
        if (!response.ok) {
          this.error = 'Can not load survey data, please try again later!'
          this.isLoading = false;
          return
        }
        if(await response.clone().text() === 'null') {
          this.isLoading = false;
          return
        }
        const data = await response.json();

        console.log(`push data: ${data}`);
        Object.keys(data).forEach(key => {
          this.results.push({...data[key], id: key});
        })

      } catch (err) {
        this.error = 'Loading survey data fail, please try again later!'
        console.log(err)
      }
      this.isLoading = false;
    }
  },
  mounted() {
    this.loadSurveys();
  }

};
</script>

<style scoped>
ul {
  list-style: none;
  margin: 0;
  padding: 0;
}
</style>
