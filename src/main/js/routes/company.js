import React, { useState, useEffect } from "react";
import Container from "@mui/material/Container";
import Typography from "@mui/material/Typography";
import CircularProgress from "@mui/material/CircularProgress";
import Chip from "@mui/material/Chip";
import Box from "@mui/material/Box";
import Dialog from "@mui/material/Dialog";
import DialogTitle from "@mui/material/DialogTitle";
import DialogContent from "@mui/material/DialogContent";
import DialogContentText from "@mui/material/DialogContentText";
import DialogActions from "@mui/material/DialogActions"
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import WebIcon from '@mui/icons-material/Web';
import PhoneIcon from '@mui/icons-material/Phone';
import ScheduleIcon from '@mui/icons-material/Schedule';
import BusinessIcon from '@mui/icons-material/Business';
import { useParams } from "react-router-dom";
import { StarRating } from "../components";

export default function Company(props) {
    const { id } = useParams();
    const [company, setCompany] = useState(null);
    const [reviewDialogOpen, setReviewDialogOpen] = useState(false);

    /**
     * Loads the company with the current specified ID and sets the state
     */
    async function loadCompany() {
        const response = await props.api.get(`/company/${id}`);
        setCompany(response.data);
        // TODO: throw error if nothing found
    }

    async function submitReview(event) {
        // Prevent GET submission + reload
        event.preventDefault();

        // Get data
        const form = event.target;
        const text = form.text.value;
        const stars = parseFloat(form.stars.value);
        // TODO: add user id
        const response = await props.api.post(`/company/${id}/review`, { starRating: stars, reviewText: text }, { params: { userId: 7 } })
        console.log(response);
        // TODO: handle errors/success

        // Load updated company information
        loadCompany();
    }

    // Load company when component loads
    useEffect(() => {
        loadCompany();
    }, []);

    const reviewDialog = (
        <Dialog open={reviewDialogOpen} onClose={() => void(0)}>
            <DialogTitle>Skrifa umsögn</DialogTitle>
            <DialogContent>
                <DialogContentText>
                    lorem ipsum
                </DialogContentText>
                <form
                    id="review_form"
                    onSubmit={submitReview}
                >
                    <TextField
                        autoFocus
                        margin="dense"
                        id="review_text"
                        name="text"
                        label="Umsögn"
                        type="text"
                        fullWidth
                        multiline
                    />
                    <TextField
                        margin="dense"
                        id="review_stars"
                        name="stars"
                        label="Stjörnur"
                        type="number"
                        step="0.5"
                        fullWidth
                        // TODO: replace with custom star input
                    />
                    <DialogActions>
                        <Button
                            onClick={() => void(0)}
                        >
                            Hætta við
                        </Button>
                        <Button
                            type="submit"
                        >
                            Senda
                        </Button>
                    </DialogActions>
                </form>
            </DialogContent>
        </Dialog>
    )

    return (
        <>
            {reviewDialog}
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
                                Umsagnir
                            </Typography>
                            <Box>
                                {company.reviews && company.reviews.length !== 0
                                    ? (
                                        company.reviews.map(review => (
                                            <Card sx={{ minWidth: 275 }} key={review.id}>
                                                <CardContent>
                                                    <Typography gutterBottom variant="h5" component="div">
                                                        Notandanafn
                                                    </Typography>
                                                    <StarRating rating={review.starRating} />
                                                    <Typography variant="body2">
                                                        {review.reviewText}
                                                    </Typography>
                                                </CardContent>
                                            </Card>
                                        ))
                                    ) : (
                                        <Typography variant="body1">
                                            Engar umsagnir eru til um þetta fyrirtæki.
                                        </Typography>
                                    )
                                }
                            </Box>
                            <Typography
                                variant="h3"
                            >
                                Spurningar
                            </Typography>
                            <Box>
                                {company.questions && company.questions.length !== 0
                                    ? (
                                        company.questions.map(question => (
                                            <Card sx={{ minWidth: 275 }} key={question.id}>
                                                <CardContent>
                                                    <Typography gutterBottom variant="h5" component="div">
                                                        Notandanafn
                                                    </Typography>
                                                    <StarRating rating={review.starRating} />
                                                    <Typography variant="body2">
                                                        {question.questionText}
                                                    </Typography>
                                                    <Typography variant="h5" component="div">
                                                        Svar
                                                    </Typography>
                                                    <Typography variant="body2">
                                                        {question.answerString ? question.answerString : 'Þessari spurningu hefur ekki verið svarað.'}
                                                    </Typography>
                                                </CardContent>
                                            </Card>
                                        ))
                                    ) : (
                                        <Typography variant="body1">
                                            Engar spurningar hafa borist þessu fyrirtæki.
                                        </Typography>
                                    )
                                }
                            </Box>
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
        </>
    );
}
