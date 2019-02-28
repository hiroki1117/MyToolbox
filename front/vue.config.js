module.exports = {
  outputDir: "../public/vue",
  filenameHashing: false,
  productionSourceMap: false,
  css: {
    extract: false
  },
  pages: {
    mytweet: {
      entry: "src/MyTweet/main.js",
      template: "public/index.html",
      filename: "mytweet.html"
    },
    mytodo: {
      entry: "src/MyTodo/main.js",
      template: "plublic/index.html",
      filename: "mytodo.html"
    }
  }
};
