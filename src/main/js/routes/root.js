import React from "react";
import Container from "@mui/material/Container";
import TextField from "@mui/material/TextField";

export default function Root() {
    return (
        <Container maxWidth="sm">
            <TextField
                id="search"
                label="Search"
                variant="filled"
                onKeyPress={(e) => {
                    if (e.key === "Enter") {
                        window.alert(`Searched for ${e.target.value}`);
                    }
                }}
            />
        </Container>
    );
}
