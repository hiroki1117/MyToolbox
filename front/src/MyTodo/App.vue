<template>
  <div>
    <ul>
      <li @click="deleteTodo(data)" v-for="data in sortedList" :key="data">
        {{ data.id }}
        {{ data.content }}
      </li>
    </ul>
    <input type="text" v-model="newTodo" /><button @click="createTodo">
      button
    </button>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data: function() {
    return {
      todoList: [],
      newTodo: ""
    };
  },
  computed: {
    sortedList: function() {
      return this.todoList.slice().sort((a, b) => a.id > b.id);
    }
  },
  methods: {
    createTodo: function() {
      axios
        .post(
          "/mytodo/api/create",
          { todo: this.newTodo },
          {
            headers: { "Content-Type": "application/json" },
            data: {}
          }
        )
        .then(response => {
          this.todoList.push(response.data);
          this.newTodo = "";
        });
    },
    deleteTodo: function(data) {
      if (confirm("削除します")) {
        axios.post("/mytodo/api/delete", data).then(response => {
          if (response.data.status) {
            this.todoList = this.todoList.filter(ele => ele.id !== data.id);
          }
        });
      }
    }
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
