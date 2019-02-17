<template>
  <div>
    <textarea v-model="tweet"></textarea>
    <button @click="send">send</button>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data: function() {
    return {
      tweet: ""
    };
  },
  methods: {
    send: function() {
      alert(this.tweet);
      axios
        .post("/mytweet/api/post", { tweet: this.tweet })
        .then(response => console.log(response.data));
      this.tweet = "";
    }
  }
};
</script>
