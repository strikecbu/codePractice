import { createApp } from 'vue';

import App from './App.vue';
import TeamsList from './components/teams/TeamsList.vue';
import UsersList from './components/users/UsersList.vue';
import PageNotFound from './components/nav/PageNotFound.vue';
import TeamMembers from './components/teams/TeamMembers.vue';
import { createRouter, createWebHistory } from 'vue-router';

const app = createApp(App);
const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: '/', component: '/teams' },
    {
      name: 'team',
      path: '/teams',
      component: TeamsList,
      children: [
        {
          name: 'team-member',
          path: ':teamId',
          component: TeamMembers,
          props: true,
        },
      ],
    },

    { path: '/users', component: UsersList },
    { path: '/:notFound(.*)', component: PageNotFound, props: true },
  ],
});

app.use(router);
app.mount('#app');
