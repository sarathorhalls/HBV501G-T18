import React, { useState, useEffect } from "react";
import Container from "@mui/material/Container";
import Typography from "@mui/material/Typography";
import { useParams } from "react-router-dom";

export default function Company(props) {
    const { id } = useParams();
    const [company, setCompany] = useState(null);

    async function loadCompany() {
        /**
         * Loads the company with the current specified ID and sets the state
         */
        const response = await props.api.get(`/company/${id}`);
        setCompany(response.data);
        // TODO: throw error if nothing found
    }

    // Load company when component loads
    useEffect(() => {
        loadCompany();
    }, []);

    return (
        <Container maxWidth="lg">
            <Typography
                variant="h2"
            >
                {company ? company.name : 'Loading…'}
            </Typography>
            <Typography
                variant="body1"
            >
                {company ? company.description : 'Loading…'}
            </Typography>
            <Typography
                variant="h3"
            >
                Reviews
            </Typography>
            <div>
                {company ? JSON.stringify(company.reviews) : 'Loading…'}
            </div>
            <Typography
                variant="h3"
            >
                Questions
            </Typography>
            <div>
                {company ? JSON.stringify(company.questions) : 'Loading…'}
            </div>
        </Container>
    );
}
