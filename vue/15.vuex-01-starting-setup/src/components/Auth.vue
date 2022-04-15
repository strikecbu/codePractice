<template>
    <button @click="login">Login</button>
    <button @click="logout">Logout</button>
</template>

<script>
import {mapActions} from 'vuex'
export default {
    setup() {
        
    },
    methods: {
        ...mapActions(['login', 'logout'])
    }
    
}
</script>