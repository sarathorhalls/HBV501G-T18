import React, { Component } from "react";
import ReactDOM from "react-dom";
import Axios from "axios";

const api = Axios.create({
    baseURL: "http://localhost:8080/api",
});

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
            <div>
                {this.state.companies.map((company) => (
                    <div key={company.id}>
                        <h2>{company.name}</h2>
                        <button onClick={() => this.deleteCompany(company.id)}>X</button>
                    </div>
                ))}
                <button onClick={this.createCompany}>Create Company</button>
            </div>
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
