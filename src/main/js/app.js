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

const api = Axios.create({
    baseURL: "http://localhost:8080/api",
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
    // TODO: implement actual authentication
    const [loggedIn, setLoggedIn] = useState(false);
    const [username, setUsername] = useState("janedoe");

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
                    label="Search…"
                    variant="standard"
                    // TODO: fix color
                    sx={{ flexGrow: 1 }}
                />
                <Box sx={{ flexGrow: 1 }} />
                <Box>
                    {loggedIn
                        ? (
                            <Button
                                variant="text"
                                color="inherit"
                                onClick={() => setLoggedIn(false)}
                            >
                                Log out {username}
                            </Button>
                        )
                        : (
                            <>
                                <Button
                                    variant="text"
                                    color="inherit"
                                    onClick={() => setLoggedIn(true)}
                                >Log in</Button>
                                <Button
                                    variant="text"
                                    color="inherit"
                                    onClick={() => window.alert("Pressed sign up")}
                                >Sign up</Button>
                            </>
                        )
                    }
                </Box>
            </Toolbar>
        </AppBar>
    );

    return (
        <React.StrictMode>
            <CssBaseline />
            { appToolbar }
            <RouterProvider router={router} />
        </React.StrictMode>
    );
}

const container = document.getElementById("react");
const root = createRoot(container);
root.render(<App />);
