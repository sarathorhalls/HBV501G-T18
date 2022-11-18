var path = require("path");

module.exports = {
    entry: "/src/js/app.js",
    devtool: "inline-source-map",
    cache: true,
    mode: "development",
    output: {
        path: __dirname,
        filename: "./public/built/bundle.js",
    },
    module: {
        rules: [
            {
                test: path.join(__dirname, "."),
                exclude: /(node_modules)/,
                use: [
                    {
                        loader: "babel-loader",
                        options: {
                            presets: ["@babel/preset-env", "@babel/preset-react"],
                        },
                    },
                ],
            },
        ],
    },
};
