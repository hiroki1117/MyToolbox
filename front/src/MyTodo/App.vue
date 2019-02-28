<template>
  <div>
    <ul>
      <li v-for="data in todoList" :key="data">
        {{ data }}
      </li>
    </ul>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data: function() {
    return {
      todoList: []
    };
  },
  mounted() {
    axios
      .get("/mytodo/api/get", {
        headers: { "Content-Type": "application/json" },
        data: {}
      })
      .then(response => (this.todoList = response.data));
  }
};
</script>
