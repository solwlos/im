<template>
  <div class="about">
    <!-- <h1>This is an home</h1> -->
    <div style="">

      <el-input
        v-for="(item, index) in msg"
        type="textarea"
        :placeholder= item
        v-model="textarea"
        autosize
        maxlength="30"
        show-word-limit
      >
      {{ item }}
      </el-input>
       <!-- <span v-for="(item, index) in msg" >
        {{ item }}
       </span> -->
    </div>
  </div>
</template>

<script setup>

const msg = [1,1,1,231,412,];

</script>
<style>
@media (min-width: 1024px) {
  .about {
    min-height: 100vh;
    display: flex;
    align-items: center;
  }
}
</style>