<template>
  <section>
    <h2>{{ teamName }}</h2>
    <ul>
      <user-item
          v-for="member in members"
          :key="member.id"
          :name="member.fullName"
          :role="member.role"
      ></user-item>
    </ul>
    <button @click="goTeam2">Go to team2</button>
  </section>
</template>

<script>
import UserItem from '../users/UserItem.vue';

export default {
  components: {
    UserItem
  },
  props: ['teamId'],
  data() {
    return {
      teamName: '',
      members: [],
    };
  },
  methods: {
    goTeam2() {
      this.$router.push('/teams/t2');
    },
    loadTeamInformation(teamId) {
      const team = this.teams.find(team => team.id === teamId);
      if (!team) {
        return
      }
      this.teamName = team.name
      this.members = this.users.filter(user => team.members.includes(user.id))
    }
  },
  created() {
    this.loadTeamInformation(this.teamId)
  },
  watch: {
    teamId(newTeamId) {
      this.loadTeamInformation(newTeamId)
    }
  },
  inject: ['teams', 'users']
};
</script>

<style scoped>
section {
  margin: 2rem auto;
  max-width: 40rem;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.26);
  padding: 1rem;
  border-radius: 12px;
}

h2 {
  margin: 0.5rem 0;
}

ul {
  list-style: none;
  margin: 0;
  padding: 0;
}
</style>
