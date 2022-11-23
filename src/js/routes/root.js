import React from "react";
import Container from "@mui/material/Container";
import TextField from "@mui/material/TextField";

export default function Root(props) {
    return (
        <Container maxWidth="lg">
            <img
                id="kritikin_logo"
                src={"/img/kritikin-logo.svg"}
                style={{ width: "40em", maxWidth: "80%", display: "block", margin: "0 auto"}}
            />
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
