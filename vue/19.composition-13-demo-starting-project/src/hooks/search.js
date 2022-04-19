import {ref, computed, watch} from 'vue'

export default function useSearch(items, filterProp) {
    const enteredSearchTerm = ref('');
    const activeSearchTerm = ref('');
    const availableItems = computed(() => {
        let filteredItems = [];
        if (activeSearchTerm.value) {
            filteredItems = items.value.filter((item) =>
                item[filterProp].includes(activeSearchTerm.value)
            );
        } else if (items.value) {
            filteredItems = items.value;
        }
        return filteredItems;
    });
    function updateSearch(val) {
        enteredSearchTerm.value = val;
    }

    watch(enteredSearchTerm, (val) => {
        setTimeout(() => {
            if (val === enteredSearchTerm.value) {
                activeSearchTerm.value = val;
            }
        }, 300);
    });

    return {
        enteredSearchTerm,
        availableItems,
        updateSearch
    }
}
