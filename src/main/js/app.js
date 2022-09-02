const React = require("react");
const ReactDOM = require("react-dom");
const client = require("./client");

class App extends React.Component {
    constructor(props) {
        super(props);
        this.state = { employees: [], selectedEmployee: null };
        this.selectEmployee = this.selectEmployee.bind(this);
        this.createEmployee = this.createEmployee.bind(this);
        this.updateEmployee = this.updateEmployee.bind(this);
        this.deleteEmployee = this.deleteEmployee.bind(this);
        this.render = this.render.bind(this);
    }

    componentDidMount() {
        client({ method: "GET", path: "/api/employees" }).done((response) => {
            this.setState({
                employees: response.entity._embedded.employees,
            });
        });
    }

    selectEmployee(employee) {
        this.setState({ selectedEmployee: employee });
    }

    createEmployee(employee) {
        client({ method: "POST", path: "/api/employees", entity: employee, headers: { "Content-Type": "application/json" } }).done((response) => {
            this.setState({
                employees: this.state.employees.concat(response.entity),
                selectedEmployee: response.entity,
            });
        });
    }

    updateEmployee(employee) {
        client({ method: "PUT", path: "/api/employees/" + employee.id, entity: employee, headers: { "Content-Type": "application/json" } }).done((response) => {
            this.setState({
                employees: this.state.employees.map((e) => (e.id === employee.id ? response.entity : e)),
                selectedEmployee: response.entity,
            });
        });
    }

    deleteEmployee(employee) {
        client({ method: "DELETE", path: "/api/employees/" + employee.id }).done((response) => {
            this.setState({
                employees: this.state.employees.filter((e) => e.id !== employee.id),
                selectedEmployee: null,
            });
        });
    }

    render() {
        return <EmployeeList employees={this.state.employees} onSelect={this.selectEmployee} />;
    }
}

class EmployeeList extends React.Component {
    render() {
        const employees = this.props.employees.map((employee) => (
            <Employee key={employee._links.self.href} employee={employee} onDelete={this.props.onDelete} onSelect={this.props.onSelect} />
        ));
        return (
            <table>
                <tbody>
                    <tr>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Description</th>
                    </tr>
                    {employees}
                </tbody>
            </table>
        );
    }
}

class Employee extends React.Component {
    render() {
        return (
            <tr>
                <td>
                    <a href="#" onClick={() => this.props.onSelect(this.props.employee)}>
                        {this.props.employee.firstName}
                    </a>
                </td>
                <td>{this.props.employee.lastName}</td>
                <td>{this.props.employee.description}</td>
                <td>
                    <button onClick={() => this.props.onDelete(this.props.employee)}>Delete</button>
                </td>
            </tr>
        );
    }
}

ReactDOM.render(<App />, document.getElementById("react"));
