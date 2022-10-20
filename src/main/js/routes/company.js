import React, { useState, useEffect } from "react";
import Container from "@mui/material/Container";
import Typography from "@mui/material/Typography";
import CircularProgress from "@mui/material/CircularProgress";
import Chip from "@mui/material/Chip";
import Stack from "@mui/material/Stack";
import Dialog from "@mui/material/Dialog";
import DialogTitle from "@mui/material/DialogTitle";
import DialogContent from "@mui/material/DialogContent";
import DialogContentText from "@mui/material/DialogContentText";
import DialogActions from "@mui/material/DialogActions"
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import Grid from "@mui/material/Unstable_Grid2";
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

    /**
     * Closes the "write review" dialog
     */
    function handleCloseReviewDialog() {
        setReviewDialogOpen(false);
    }

    // TODO: add jsdoc here because I don't understand how I'm supposed to type hint the event object
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

        // Close dialog
        handleCloseReviewDialog();
    }

    // Load company when component loads
    useEffect(() => {
        loadCompany();
    }, []);

    const reviewDialog = (
        <Dialog open={reviewDialogOpen} onClose={handleCloseReviewDialog}>
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
                            onClick={handleCloseReviewDialog}
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
                            <Stack direction="row" spacing={2}>
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
                            </Stack>
                            <StarRating
                                rating={company.starRating}
                            />
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
                            {company.reviews && company.reviews.length !== 0
                                ? (
                                    <Grid container spacing={2}>
                                        {company.reviews.map(review => (
                                            <Grid item xs={12} sm={6} md={3} key={review.id}>
                                                <Card>
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
                                            </Grid>
                                        ))}
                                    </Grid>
                                ) : (
                                    <Typography variant="body1">
                                        Engar umsagnir eru til um þetta fyrirtæki.
                                    </Typography>
                                )
                            }
                            <Button
                                variant="contained"
                                onClick={() => setReviewDialogOpen(true)}
                            >
                                Skrifa umsögn
                            </Button>
                            <Typography
                                variant="h3"
                            >
                                Spurningar
                            </Typography>
                            {company.questions && company.questions.length !== 0
                                ? (
                                    <Grid container spacing={2}>
                                        {company.questions.map(question => (
                                            <Grid item xs={12} sm={6} md={3} key={question.id}>
                                                <Card>
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
                                            </Grid>
                                        ))}
                                    </Grid>
                                ) : (
                                    <Typography variant="body1">
                                        Engar spurningar hafa borist þessu fyrirtæki.
                                    </Typography>
                                )
                            }
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
