<template>
  <base-card>
    <template #header>
      <h3>{{ fullName }}</h3>
      <base-badge :type="role" :caption="role.toUpperCase()"></base-badge>
    </template>
    <template #default>
      <p>{{ infoText }}</p>
    </template>
  </base-card>
</template>

<script>
import BaseCard from "./BaseCard.vue";
import BaseBadge from "./BaseBadge.vue";
export default {
  components: { BaseCard, BaseBadge },
  props: ["fullName", "infoText", "role"],
};
</script>
