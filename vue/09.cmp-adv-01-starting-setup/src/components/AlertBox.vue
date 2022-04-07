<template>
  <dialog open class="box">
    <slot></slot>
  </dialog>
</template>

<style scoped>
.box {
    position: fixed;
    top: 20vh;
    left: 30%;
    width: 40%;
    box-shadow: 4px 3px 2px darkgrey;
    background-color: wheat;
    margin: 0;
}
</style>
