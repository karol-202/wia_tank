config.devServer = config.devServer || {}
config.devServer.port = 8081;
config.devServer.watchOptions = {
    "aggregateTimeout": 5000,
    "poll": 1000
}
