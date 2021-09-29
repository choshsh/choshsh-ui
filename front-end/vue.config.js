module.exports = {
  outputDir: "../src/main/resources/static",
  devServer: {
    port: 8082,
    proxy: {
      "/api": { target: "http://localhost:8080" },
      "/jenkins": { target: "http://localhost:8081" },
    },
  },
  lintOnSave: false,
  runtimeCompiler: true,
  configureWebpack: {
    //Necessary to run npm link https://webpack.js.org/configuration/resolve/#resolve-symlinks
    resolve: {
      symlinks: false,
    },
  },
  transpileDependencies: ["@coreui/utils"],
};
