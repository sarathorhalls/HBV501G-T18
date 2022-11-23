import React from "react";
import Container from "@mui/material/Container";
import TextField from "@mui/material/TextField";

export default function Root(props) {
    return (
        <Container maxWidth="lg">
            <form
                id="body_search_form"
                onSubmit={props.search}
                style={{ textAlign: "center" }}
            >
                <TextField
                    id="body_search_field"
                    name="search_query"
                    label="Leita …"
                    variant="filled"
                    sx={{ width: "50%" }}
                />
            </form>
        </Container>
    );
}
