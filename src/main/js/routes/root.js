import React from "react";
import Container from "@mui/material/Container";
import TextField from "@mui/material/TextField";

export default function Root() {
    return (
        <Container maxWidth="lg">
            <TextField
                id="body-search"
                label="Leita"
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
