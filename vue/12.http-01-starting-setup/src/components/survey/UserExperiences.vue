<template>
  <section>
    <base-card>
      <h2>Submitted Experiences</h2>
      <div>
        <base-button @click="loadSurveys">Load Submitted Experiences</base-button>
      </div>
      <p v-if="isLoading">Loading...</p>
      <p v-else-if="!!error">{{error}}</p>
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
    loadSurveys() {
      this.results = [];
      this.isLoading = true;
      const url = 'https://vue-http-bbb10-default-rtdb.asia-southeast1.firebasedatabase.app/surveys.json'
      fetch(url
      ).then((data) => {
        if (!data.ok) {
          throw new Error('NONONO')
        }

        return data.json();
      }).then((data) => {
        console.log(`push data: ${data}`);
        Object.keys(data).forEach(key => {
          this.results.push({...data[key], id: key});
        })
        this.error = null
        this.isLoading = false;
      }).catch((err) => {
        this.isLoading = false;
        this.error = 'Loading survey data fail, please try again later!'
        console.log(err)
      })
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
