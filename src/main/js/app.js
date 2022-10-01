import React, { Component } from "react";
import ReactDOM from "react-dom";
import Axios from "axios";

class App extends Component {
    render() {
        return (
            <div>
                <h1>React working!</h1>
            </div>
        );
    }
}

ReactDOM.render(<App />, document.getElementById("react"));
