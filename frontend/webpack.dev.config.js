const path = require('path');
const { merge } = require('webpack-merge');
const config = require('./webpack.config.js');
const ReactRefreshWebpackPlugin = require('@pmmmwh/react-refresh-webpack-plugin');

module.exports = merge(config, {
  mode: 'development',
  devtool: 'eval',
  output: {
    publicPath: '/',
  },
  plugins: [new ReactRefreshWebpackPlugin()],
  devServer: {
    historyApiFallback: true,
    static: path.join(__dirname, 'public'),
    compress: true,
    port: 3000,
    open: true,
    hot: true,
  },
  module: {
    rules: [
      {
        test: /\.jsx?$/,
        loader: 'babel-loader',
        exclude: path.join(__dirname, 'node_modules'),
        options: {
          plugins: ['react-refresh/babel'],
        },
      },
    ],
  },
});
