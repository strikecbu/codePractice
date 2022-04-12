import TeamsList from './components/teams/TeamsList.vue';
import UsersList from './components/users/UsersList.vue';
import TeamsFooter from './components/teams/TeamsFooter.vue'
import UsersFooter from './components/users/UsersFooter.vue';


import PageNotFound from './components/nav/PageNotFound.vue';
import TeamMembers from './components/teams/TeamMembers.vue';
import {createRouter, createWebHistory} from 'vue-router';


const router = createRouter({
    history: createWebHistory(),
    routes: [
        {path: '/', redirect: '/teams'},
        {
            name: 'team',
            path: '/teams',
            components: {default: TeamsList, footer: TeamsFooter},
            children: [
                {
                    name: 'team-member',
                    path: ':teamId',
                    component: TeamMembers,
                    props: true,
                },
            ],
        },

        {path: '/users', components: {default: UsersList, footer: UsersFooter}},
        {path: '/:notFound(.*)', component: PageNotFound, props: true},
    ],
    scrollBehavior(_, _2, savedPosition) {
        if (savedPosition) {
            return savedPosition
        }
        return {top: 0, left: 0}
    }
});

router.beforeEach((to, from, next) => {
    console.log('Global before each: ', to, from);
    next();
});

router.afterEach(((to, from) => {
    console.log('Global after each: ', to, from)
}))



export default router;
