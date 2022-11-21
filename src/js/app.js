import React, { useEffect, useState } from "react";
import { createRoot } from "react-dom/client";
import Axios from "axios";
import { Routes, Route, BrowserRouter, Link } from "react-router-dom";
import { Root, Company } from "./routes";
import CssBaseline from "@mui/material/CssBaseline";
import AppBar from "@mui/material/AppBar";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import TextField from "@mui/material/TextField";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import MUILink from "@mui/material/Link";
import Dialog from "@mui/material/Dialog";
import DialogTitle from "@mui/material/DialogTitle";
import DialogContent from "@mui/material/DialogContent";
import DialogActions from "@mui/material/DialogActions";
import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemText from "@mui/material/ListItemText";
import jwt_decode from "jwt-decode";

const api = Axios.create({
    baseURL: "http://localhost:8338/api",
});

export default function App() {
    const [authInfo, setAuthInfo] = useState(null);
    // Dialog open variables
    const [loginDialogOpen, setLoginDialogOpen] = useState(false);
    const [signupDialogOpen, setSignupDialogOpen] = useState(false);
    const [searchResultsDialogOpen, setSearchResultsDialogOpen] = useState(false);
    // Search results array
    const [searchResults, setSearchResults] = useState([]);

    useEffect(() => {
        // Check if user is logged in on load
        const accessToken = localStorage.getItem("access_token");
        if (accessToken) {
            // Decode token
            const data = jwt_decode(accessToken);

            if (data.exp <= Date.now() / 1000) {
                // If token is expired, remove it
                // data.exp is in seconds but Date.now in ms
                localStorage.removeItem("access_token");
            } else {
                // Else, set authentication info state locally
                setAuthInfo(
                    {
                        "username": data.sub,
                        "access_token": accessToken
                    }
                );
            }
        }
    }, []);

    // TODO: add JSdoc
    async function search(event) {
        // Prevent GET submission + reload
        event.preventDefault();
        
        // Get data
        const form = event.target;
        const query = form.search_query.value;

        // Submit query
        const response = await api.get(`/findCompany/${query}`);

        // Set results state
        setSearchResults(response.data);

        // Show search results
        setSearchResultsDialogOpen(true);
    }

    /**
     * Closes the "log in" dialog
     */
    function handleCloseLoginDialog() {
        setLoginDialogOpen(false);
    }

    /**
     * Closes the "sign up" dialog
     */
    function handleCloseSignupDialog() {
        setSignupDialogOpen(false);
    }

    /**
     * Closes the search results dialog
     */
    function handleCloseSearchResultsDialog() {
        setSearchResultsDialogOpen(false);
    }

    /**
     * Logs the user out
     */
    function logOut() {
        // Set authentication state to null
        setAuthInfo(null);

        // Remove stored access key
        localStorage.removeItem("access_key");
    }

    // TODO: add jsdoc
    async function logIn(event) {
        // Prevent GET submission + reload
        event.preventDefault();
        
        // Get data
        const form = event.target;
        const username = form.username.value;
        const password = form.password.value;

        // Create URL search params to log in with
        const params = new URLSearchParams();
        params.append("username", username);
        params.append("password", password);
        
        let response;
        try {
            response = await api.post("/auth/signin", params);
        } catch (e) {
            if (e.response) {
                if (e.response.status === 401) {
                    window.alert("Rangt notandanafn eða lykilorð");
                } else {
                    window.alert(`Villa: ${e.response.data}`);
                }
            }
            return;
        }

        // Set authentication info state
        setAuthInfo(response.data);

        // Store token in localStorage
        localStorage.setItem("access_token", response.data.access_token);
        
        // Close dialog
        handleCloseLoginDialog();
    }

    // TODO: add jsdoc
    async function signUp(event) {
        // Prevent GET submission + reload
        event.preventDefault();
        
        // Get data
        const form = event.target;
        const username = form.username.value;
        const password = form.password.value;
        
        let response;
        try {
            response = await api.post(`/auth/signup`, { username, password });
        } catch (e) {
            if (e.response) {
                if (e.response.status === 400) {
                    window.alert("Villa: Vantar notandanafn og/eða lykilorð");
                } else if (e.response.status === 409) {
                    window.alert("Villa: Notandi er nú þegar til");
                }
            }
            return;
        }
        
        // Close dialog
        handleCloseSignupDialog();
    }

    const appToolbar = (
        <AppBar position="sticky">
            <Toolbar>
                <Typography
                    variant="h6"
                    noWrap
                    sx={{ flexGrow: 1 }}
                >
                    <MUILink
                        color="inherit"
                        underline="none"
                        component={Link}
                        to="/"
                    >
                        Krítíkin
                    </MUILink>
                </Typography>
                <form
                    id="search_form"
                    onSubmit={search}
                >
                    <TextField
                        id="search_field"
                        name="search_query"
                        label="Leita …"
                        variant="standard"
                        // TODO: fix color
                        sx={{ flexGrow: 1 }}
                    />
                </form>
                <Box sx={{ flexGrow: 1 }} />
                <Box>
                    {authInfo
                        ? (
                            <Button
                                variant="text"
                                color="inherit"
                                onClick={logOut}
                            >
                                Skrá út {authInfo.username}
                            </Button>
                        )
                        : (
                            <>
                                <Button
                                    variant="text"
                                    color="inherit"
                                    onClick={() => setLoginDialogOpen(true)}
                                >Skrá inn</Button>
                                <Button
                                    variant="text"
                                    color="inherit"
                                    onClick={() => setSignupDialogOpen(true)}
                                >Stofna reikning</Button>
                            </>
                        )
                    }
                </Box>
            </Toolbar>
        </AppBar>
    );

    const loginDialog = (
        <Dialog open={loginDialogOpen} onClose={handleCloseLoginDialog}>
            <DialogTitle>Skrá inn</DialogTitle>
            <DialogContent>
                <form id="question_form" onSubmit={logIn}>
                    <TextField
                        autoFocus
                        margin="dense"
                        id="login_username"
                        name="username"
                        label="Notandanafn"
                        type="text"
                        fullWidth
                    />
                    <TextField
                        autoFocus
                        margin="dense"
                        id="login_password"
                        name="password"
                        label="Lykilorð"
                        type="password"
                        fullWidth
                    />
                    <DialogActions>
                        <Button onClick={handleCloseLoginDialog}>
                            Loka
                        </Button>
                        <Button type="submit">
                            Skrá inn
                        </Button>
                    </DialogActions>
                </form>
            </DialogContent>
        </Dialog>
    );
    
    const signupDialog = (
        <Dialog open={signupDialogOpen} onClose={handleCloseSignupDialog}>
            <DialogTitle>Nýskrá</DialogTitle>
            <DialogContent>
                <form id="question_form" onSubmit={signUp}>
                    <TextField
                        autoFocus
                        margin="dense"
                        id="signup_username"
                        name="username"
                        label="Notandanafn"
                        type="text"
                        fullWidth
                    />
                    <TextField
                        autoFocus
                        margin="dense"
                        id="signup_password"
                        name="password"
                        label="Lykilorð"
                        type="password"
                        fullWidth
                    />
                    <DialogActions>
                        <Button onClick={handleCloseSignupDialog}>
                            Loka
                        </Button>
                        <Button type="submit">
                            Stofna reikning
                        </Button>
                    </DialogActions>
                </form>
            </DialogContent>
        </Dialog>
    );

    const searchResultsDialog = (
        <Dialog open={searchResultsDialogOpen} onClose={handleCloseSearchResultsDialog}>
            <DialogTitle>Leitarniðurstöður</DialogTitle>
            <DialogContent>
                {searchResults.length === 0
                    ? (
                        <Typography variant="body1">
                            Engin fyrirtæki fundust.
                        </Typography>
                    )
                    : (
                        <nav>
                            <List>
                                {searchResults.map(company => (
                                    <ListItem disablePadding key={company.id}>
                                        <ListItemButton
                                            onClick={handleCloseSearchResultsDialog}
                                            component={Link}
                                            to={`/company/${company.id}`}
                                        >
                                            <ListItemText primary={company.name} />
                                        </ListItemButton>
                                    </ListItem>
                                ))}
                            </List>
                        </nav>
                    )
                }
            </DialogContent>
        </Dialog>
    );

    return (
        <React.StrictMode>
            <CssBaseline />
            <BrowserRouter>
                { loginDialog }
                { signupDialog }
                { searchResultsDialog }
                { appToolbar }
                <Routes>
                    <Route path="/" element={<Root />} />
                    <Route path="/company/:id" element={<Company api={api} authInfo={authInfo} />} />
                </Routes>
            </BrowserRouter>
        </React.StrictMode>
    );
}

const container = document.getElementById("react");
const root = createRoot(container);
root.render(<App />);
