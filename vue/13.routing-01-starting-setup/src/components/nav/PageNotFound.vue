<template>
  <h2>Page Not found! Go to page
    <router-link :to="{name: 'team'}">Teams</router-link>
    ?
  </h2>
  <h3>Your browser url: {{notFound}}</h3>
</template>

<script>
export default {
  props: ['notFound']
}
</script>
