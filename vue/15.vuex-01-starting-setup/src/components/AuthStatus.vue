<template>
    <div>Now your auth status: {{loginStatus}}</div>
</template>

<script>
export default {
    setup() {
        
    },
    computed: {
        loginStatus() {
            const isLogin = this.$store.getters.getLoginStatus;
            if(isLogin) {
                return 'OK'
            } else {
                return 'Not yet'
            }
        }
    }
}
</script>