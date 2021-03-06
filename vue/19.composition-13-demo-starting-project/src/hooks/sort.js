import {computed, ref} from "vue";

export default function useSort(sortItems) {
    const sorting = ref(null);
    const displayedItems = computed(() => {
        if (!sorting.value) {
            return  sortItems.value;
        }
        return  sortItems.value.slice().sort((u1, u2) => {
            if (sorting.value === 'asc' && u1.fullName > u2.fullName) {
                return 1;
            } else if (sorting.value === 'asc') {
                return -1;
            } else if (sorting.value === 'desc' && u1.fullName > u2.fullName) {
                return -1;
            } else {
                return 1;
            }
        });
    });
    function sort(mode) {
        sorting.value = mode;
    }

    return {
        sorting,
        displayedItems,
        sort,
    }
}
