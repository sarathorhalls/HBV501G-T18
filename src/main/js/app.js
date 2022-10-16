import React, { Component } from "react";
import ReactDOM from "react-dom";
import Axios from "axios";
import { RouterProvider, createBrowserRouter } from "react-router-dom";
import { Root } from "./routes";

const api = Axios.create({
    baseURL: "http://localhost:8080/api",
});

const router = createBrowserRouter([
    {
        path: "/",
        element: <Root api={api}/> // replace with root element
    }
]);

class App extends Component {
    render() {
        return (
            <React.StrictMode>
                <RouterProvider router={router} />
            </React.StrictMode>
        );
    }
}

class SearchBar extends Component {
    constructor(props) {
        super(props);
        this.state = { term: "" };
    }

    render() {
        return <div>a</div>;
    }
}

ReactDOM.render(<App />, document.getElementById("react"));
