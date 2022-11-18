import React, { useState } from "react";
import { createRoot } from "react-dom/client";
import Axios from "axios";
import { RouterProvider, createBrowserRouter } from "react-router-dom";
import { Root, ErrorPage, Company } from "./routes";
import CssBaseline from "@mui/material/CssBaseline";
import AppBar from "@mui/material/AppBar";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import TextField from "@mui/material/TextField";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import Link from "@mui/material/Link";
import Dialog from "@mui/material/Dialog";
import DialogTitle from "@mui/material/DialogTitle";
import DialogContent from "@mui/material/DialogContent";
import DialogActions from "@mui/material/DialogActions";

const api = Axios.create({
    baseURL: "http://localhost:8338/api",
});

const router = createBrowserRouter([
    {
        path: "/",
        element: <Root />,
        errorElement: <ErrorPage />
    },
    {
        path: "/company/:id",
        element: <Company api={api} />
        // TODO: define errorElement for not found
    }
]);

export default function App() {
    const [authInfo, setAuthInfo] = useState(null);
    // Dialog open variables
    const [loginDialogOpen, setLoginDialogOpen] = useState(false);
    const [signupDialogOpen, setSignupDialogOpen] = useState(false);

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
     * Logs the user out
     */
    function logOut() {
        setAuthInfo(null);
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
        
        // TODO: handle errors/success
        const response = await api.post("/auth/signin", params);

        // Set authentication info state
        setAuthInfo(response.data);
        
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
        
        // TODO: handle errors/success
        const response = await api.post(`/auth/signup`, { username, password });
        
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
                    <Link
                        href="/"
                        color="inherit"
                        underline="none"
                    >
                        Krítíkin
                    </Link>
                </Typography>
                <TextField
                    id="search"
                    label="Leita …"
                    variant="standard"
                    // TODO: fix color
                    sx={{ flexGrow: 1 }}
                />
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

    return (
        <React.StrictMode>
            <CssBaseline />
            { loginDialog }
            { signupDialog }
            { appToolbar }
            <RouterProvider router={router} />
        </React.StrictMode>
    );
}

const container = document.getElementById("react");
const root = createRoot(container);
root.render(<App />);
