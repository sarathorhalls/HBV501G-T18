import React, { Component } from "react";
import ReactDOM from "react-dom";
import Axios from "axios";
import { RouterProvider, createBrowserRouter } from "react-router-dom";

const api = Axios.create({
    baseURL: "http://localhost:8080/api",
});

const router = createBrowserRouter([
    {
        path: "/",
        element: <div>Hello world</div>  // replace with root element
    }
]);

class App extends Component {
    state = {
        companies: [],
    };

    constructor() {
        super();
        this.loadCompanies();
    }

    loadCompanies = async () => {
        let data = await api.get("/companies").then(({ data }) => data);
        this.setState({ companies: data });
    };

    createCompany = async () => {
        let response = await api.post("/companies", { name: "New Company" });
        console.log(response);
        this.loadCompanies();
    };

    deleteCompany = async (id) => {
        let response = await api.delete("/company/" + id);
        console.log(response);
        this.loadCompanies();
    };

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
