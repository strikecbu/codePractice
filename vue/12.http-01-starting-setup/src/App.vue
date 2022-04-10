<template>
  <learning-survey ></learning-survey>
  <user-experiences :results="savedSurveyResults"></user-experiences>
</template>

<script>
import LearningSurvey from './components/survey/LearningSurvey.vue';
import UserExperiences from './components/survey/UserExperiences.vue';

export default {
  components: {
    LearningSurvey,
    UserExperiences,
  },
  data() {
    return {
      savedSurveyResults: [],
    };
  },
  methods: {
    // storeSurvey(surveyData) {
    //   const surveyResult = {
    //     name: surveyData.userName,
    //     rating: surveyData.rating,
    //   };
    //
    //   const url = 'https://vue-http-bbb10-default-rtdb.asia-southeast1.firebasedatabase.app/surveys.json'
    //   fetch(url, {
    //     method: 'POST',
    //     body: JSON.stringify(surveyResult),
    //     headers: {
    //       'content-type': 'application/json'
    //     }
    //   }).then((data) => {
    //     return data.json()
    //   }).then((data) => {
    //     console.log(data)
    //     surveyResult.id = data.name;
    //     this.savedSurveyResults.push(surveyResult);
    //   }).catch(error => {
    //     console.log(error)
    //   })
    //   console.log(surveyResult);
    // },
  },
};
</script>

<style>
* {
  box-sizing: border-box;
}

html {
  font-family: sans-serif;
}

body {
  margin: 0;
}
</style>
