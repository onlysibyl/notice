<template>
    <el-input v-model="memberNum" placeholder="Please input" />
    <el-button @click="updateMemberNum" type="primary">更新</el-button>
    <div :key="member" v-for="member in memberArray">
        <el-input v-model="member.text" placeholder="Please input" />
    </div>
</template>

<script lang="ts" setup>
import { ref, reactive } from 'vue';

const memberNum = ref(1);
const memberArray = reactive([{ text: "" }]);

const updateMemberNum = () => {
  memberArray.length = 0;
  for (let i = 0; i < memberNum.value; i++) {
    memberArray.push({ text: "" });
  }
};
/*import { defineComponent } from '@vue/composition-api'

export default defineComponent({
    data() {
        return {
            memberNum: 1,
            memberArray: [{text:""}]
        }
    },
    method: {
        updateMemberNum() {
            this.memberArray = [{text:""}]
            for (let i = 0; i < this.memberNum; i++) {
                this.memberArray[i] = {text:""}
            }
        }
    },
    setup() {
        
    },
})*/
</script>

<style>

</style>
