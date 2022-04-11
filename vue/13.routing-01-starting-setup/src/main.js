import { createApp } from 'vue';

import App from './App.vue';
import TeamsList from './components/teams/TeamsList.vue';
import UsersList from './components/users/UsersList.vue';
import { createRouter, createWebHistory } from 'vue-router';

const app = createApp(App);
const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: '/teams', component: TeamsList },
    { path: '/users', component: UsersList },
  ],
});

app.use(router);
app.mount('#app');
