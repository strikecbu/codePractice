import { createApp } from 'vue';

import App from './App.vue';
import TeamsList from './components/teams/TeamsList.vue';
import UsersList from './components/users/UsersList.vue';
import TeamMembers from './components/teams/TeamMembers.vue';
import { createRouter, createWebHistory } from 'vue-router';

const app = createApp(App);
const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: '/users', component: UsersList },
    { path: '/teams', component: TeamsList },
    { path: '/teams/:id', component: TeamMembers },
  ],
});

app.use(router);
app.mount('#app');
