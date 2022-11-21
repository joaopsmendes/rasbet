// Define the Login form component
class LoginForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            email: "",
            password: "",
            alertMessage: ""
        };

        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.setAlertMessage = this.setAlertMessage.bind(this);
    }

    // Whenever an input changes value, change the corresponding state variable
    handleInputChange(event) {
        event.preventDefault();
        const target = event.target;
        this.setState({
            [target.name]: target.value
        });
    }

    postLogin(url = '', email,password ) {
        // Default options are marked with *
        const response = fetch(url, {
            method: 'POST', // *GET, POST, PUT, DELETE, etc.
            mode: 'no-cors', // no-cors, *cors, same-origin
            cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
            credentials: 'same-origin', // include, *same-origin, omit
            headers: {
                'Content-Type': 'application/json'
                // 'Content-Type': 'application/x-www-form-urlencoded',
            },
            redirect: 'follow', // manual, *follow, error
            body: JSON.stringify({
                email: email,
                password : password,
            }),
        });
        return response.json(); // parses JSON response into native JavaScript objects
    }

    handleSubmit(event) {
        event.preventDefault();
        // Reset the alert to empty
        this.setAlertMessage();
        // Call Userfront.login()
        let answer = postLogin("http://localhost:8080/api/login", this.state.email, this.state.password);
        if(answer){
            console.log("userLoggedIn");
        }

    }

    setAlertMessage(message) {
        this.setState({ alertMessage: message });
    }

    render() {
        return (
            <div>
                <Alert message={this.state.alertMessage} />
                <form onSubmit={this.handleSubmit}>
                    <label>
                        Email or username
                        <input
                            name="email"
                            type="text"
                            value={this.state.email}
                            onChange={this.handleInputChange}
                        />
                    </label>
                    <label>
                        Password
                        <input
                            name="password"
                            type="password"
                            value={this.state.password}
                            onChange={this.handleInputChange}
                        />
                    </label>
                    <button type="submit">Log in</button>
                </form>

                <p>or</p>

                <SSOButton provider="google" />
            </div>
        );
    }
}

function postLogin(url = '', email,password ) {
    // Default options are marked with *
    const response = fetch(url, {
        method: 'POST', // *GET, POST, PUT, DELETE, etc.
        mode: 'cors', // no-cors, *cors, same-origin
        cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
        credentials: 'same-origin', // include, *same-origin, omit
        headers: {
            'Content-Type': 'application/json'
            // 'Content-Type': 'application/x-www-form-urlencoded',
        },
        redirect: 'follow', // manual, *follow, error
        body: JSON.stringify({
            email: email,
            password : password,
        }),
    });
    return response.json(); // parses JSON response into native JavaScript objects
}

// Define the alert component
class Alert extends React.Component {
    constructor(props) {
        super(props);
    }
    render() {
        if (!this.props.message) return "";
        return <div id="alert">{this.props.message}</div>;
    }
}

// Define the Single Sign-on (SSO) button
class SSOButton extends React.Component {
    constructor(props) {
        super(props);
        this.handleClick = this.handleClick.bind(this);
    }

    handleClick(event) {
        postLogin({ method: this.props.provider });
        event.preventDefault();
    }

    render() {
        return (
            <button onClick={this.handleClick}>
                Log in with {this.props.provider}
            </button>
        );
    }
}

// Render the login form

ReactDOM.render(<LoginForm />, document.getElementById("login"));
