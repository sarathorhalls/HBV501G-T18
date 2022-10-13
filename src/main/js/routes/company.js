import React, { useState, useEffect } from "react";
import Container from "@mui/material/Container";
import Typography from "@mui/material/Typography";
import CircularProgress from "@mui/material/CircularProgress";
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
            {company
                ? (
                    <>
                        <Typography
                            variant="h2"
                        >
                            {company.name}
                        </Typography>
                        <Typography
                            variant="body1"
                        >
                            {company.description}
                        </Typography>
                        <Typography
                            variant="h3"
                        >
                            Reviews
                        </Typography>
                        <div>
                            {JSON.stringify(company.reviews)}
                        </div>
                        <Typography
                            variant="h3"
                        >
                            Questions
                        </Typography>
                        <div>
                            {JSON.stringify(company.questions)}
                        </div>
                    </>
                )
                : (
                    <Typography
                        variant="body1"
                    >
                        <CircularProgress />
                    </Typography>
                )
            }
        </Container>
    );
}
