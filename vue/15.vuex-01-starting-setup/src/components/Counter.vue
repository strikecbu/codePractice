<template>
    <div>Normal: {{getCounter}}</div>
    <div>Double: {{ getDoubleCounter}}</div>
    <button @click="increasementCounter">Add 1</button>
    <counter-add></counter-add>
</template>

<script>
import CounterAdd from './CounterAdd.vue'
import{mapGetters, mapMutations} from 'vuex'
export default {
    components: {
        CounterAdd
    },
    data() {
        return {
            
        }
    },
    setup() {
        
    },
    computed: {
    //    counter() {
    //      return this.$store.getters.getCounter
    //    },
    //    doubleCounter() {
    //      return this.$store.getters.getDoubleCounter
    //    },
       ...mapGetters(['getCounter', 'getDoubleCounter'])
    },
    methods: {
        // addCounter() {
        //     this.$store.commit('increasementCounter')
        // },
        ...mapMutations(['increasementCounter'])
    },
    
}
</script>
