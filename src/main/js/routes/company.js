import React, { useState } from "react";
import Container from "@mui/material/Container";
import Typography from "@mui/material/Typography";
import { useParams } from "react-router-dom";

export default function Company(props) {
    const { id } = useParams();
    const [company, setCompany] = useState(null);

    async function loadCompany() {
        const response = await props.api.get(`/company/${id}`);
        const data = JSON.parse(response.data);
        setCompany(data);
    }

    loadCompany();

    return (
        <Container maxWidth="lg">
            <Typography
                variant="h2"
            >
                {JSON.stringify(company)}
            </Typography>
            <Typography
                variant="body1"
            >
                Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
            </Typography>
            <Typography
                variant="h3"
            >
                Reviews
            </Typography>
            <Typography
                variant="h3"
            >
                Questions
            </Typography>
        </Container>
    );
}
