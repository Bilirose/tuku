import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

export const useCounterStore = defineStore('counter', () => {
  const count = ref(0)
  //定义状态的初始值
  //定义变量的计算逻辑 getter
  const doubleCount = computed(() => count.value * 2)
  //定义怎么更改状态
  function increment() {
    count.value++
  }

  return { count, doubleCount, increment }
})
