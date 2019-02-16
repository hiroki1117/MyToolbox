module.exports = {
  outputDir: "../public/vue",
  filenameHashing: false,
  productionSourceMap: false,
  css: {
    extract: false
  },
  pages: {
    index: {
      entry: "src/main.js",
      template: "public/index.html",
      filename: "index.html"
    },
    mytweet: {
      entry: "src/MyTweet/main.js",
      template: "public/index.html",
      filename: "mytweet.html"
    }
  }
};
