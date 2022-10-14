import React, { useState, useEffect } from "react";
import Container from "@mui/material/Container";
import Typography from "@mui/material/Typography";
import CircularProgress from "@mui/material/CircularProgress";
import Chip from "@mui/material/Chip";
import Box from "@mui/material/Box";
import WebIcon from '@mui/icons-material/Web';
import PhoneIcon from '@mui/icons-material/Phone';
import ScheduleIcon from '@mui/icons-material/Schedule';
import BusinessIcon from '@mui/icons-material/Business';
import { useParams } from "react-router-dom";
import { StarRating } from "../components";

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
                        <Box>
                            <Chip
                                icon={<WebIcon />}
                                label="Vefur"
                                component="a"
                                href={company.website}
                                target="_blank"
                                clickable
                            />
                            <Chip
                                icon={<PhoneIcon />}
                                label={company.phoneNumber}
                                component="a"
                                href={`tel:${company.phoneNumber}`}
                                clickable
                                // TODO: add aria-label or similar
                            />
                            <Chip
                                icon={<ScheduleIcon />}
                                label={company.openingHours}
                            />
                            <Chip
                                icon={<BusinessIcon />}
                                label={company.address}
                                component="a"
                                href={`https://www.google.com/maps/search/?api=1&query=${encodeURI(company.address)}`}
                                target="_blank"
                                clickable
                                // TODO: add aria-label or similar
                            />
                            <StarRating
                                rating={company.starRating}
                            />
                        </Box>
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
