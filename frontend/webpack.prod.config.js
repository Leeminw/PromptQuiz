const path = require('path');
const { merge } = require('webpack-merge');
const config = require('./webpack.config.js');
const mergedConfig = merge(config, {
  mode: 'production',
  devtool: 'hidden-source-map',
  performance: {
    hints: false,
    maxEntrypointSize: 512000,
    maxAssetSize: 512000,
  },
  module: {
    rules: [
      {
        test: /\.jsx?$/,
        loader: 'babel-loader',
        exclude: path.join(__dirname, 'node_modules'),
      },
    ],
  },
});

module.exports = mergedConfig;