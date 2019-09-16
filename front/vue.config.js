module.exports = {
  outputDir: `${__dirname}/../src/main/resources/static`,
  devServer: {
    proxy: 'http://localhost:8080'
  }
}