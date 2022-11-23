import React, { useState, useEffect } from "react";
import Container from "@mui/material/Container";
import Typography from "@mui/material/Typography";
import CircularProgress from "@mui/material/CircularProgress";
import Chip from "@mui/material/Chip";
import Stack from "@mui/material/Stack";
import Dialog from "@mui/material/Dialog";
import DialogTitle from "@mui/material/DialogTitle";
import DialogContent from "@mui/material/DialogContent";
import DialogActions from "@mui/material/DialogActions";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import Grid from "@mui/material/Unstable_Grid2";
import WebIcon from "@mui/icons-material/Web";
import PhoneIcon from "@mui/icons-material/Phone";
import ScheduleIcon from "@mui/icons-material/Schedule";
import BusinessIcon from "@mui/icons-material/Business";
import { useParams } from "react-router-dom";
import { StarPicker, StarRating } from "../components";
import Axios from "axios";

/**
 * Props object for the company page
 *
 * @typedef {object} CompanyProps
 * @property {Axios} api Axios object connected to the REST API
 */

/**
 * Company page component
 *
 * @component
 * @param {CompanyProps} props
 */
export default function Company(props) {
    // Data
    const { id } = useParams();
    const [company, setCompany] = useState(null);
    const [reviews, setReviews] = useState(null);
    const [questions, setQuestions] = useState(null);
    // Dialog open variables
    const [reviewDialogOpen, setReviewDialogOpen] = useState(false);
    const [questionDialogOpen, setQuestionDialogOpen] = useState(false);
    // State variable that keeps track of the selected star rating in the reviewDialog
    const [starPickerRating, setStarPickerRating] = useState(0);

    /**
     * Loads the company with the current specified ID and sets the state
     */
    async function loadCompany() {
        const response = await props.api.get(`/company/${id}`);
        response.data.openingHours = response.data.openingHours.split(",");
        if (response.data.openingHours.length === 7) {
            response.data.openingHours = response.data.openingHours[new Date().getDay()]
        } else {
            response.data.openingHours = response.data.openingHours[0]
        }
        setCompany(response.data);
        // TODO: throw error if nothing found
    }

    async function loadReviews() {
        const response = await props.api.get(`/company/${id}/reviews`);
        setReviews(response.data);
        console.log("running loadReviews");
    }

    async function loadQuestions() {
        const response = await props.api.get(`/company/${id}/questions`);
        setQuestions(response.data);
    }

    /**
     * Closes the "write review" dialog
     */
    function handleCloseReviewDialog() {
        setReviewDialogOpen(false);
        // Reset star picker rating since it's not a form input
        setStarPickerRating(0);
    }

    /**
     * Closes the "ask question" dialog
     */
    function handleCloseQuestionDialog() {
        setQuestionDialogOpen(false);
    }

    /**
     * Submits a review from the currently logged in user
     * @param {*} event Form submission event object, fired by a form containing the review text
     */
    async function submitReview(event) {
        // Prevent GET submission + reload
        event.preventDefault();

        // Get data
        const form = event.target;
        const text = form.text.value;

        // TODO: handle errors/success
        const response = await props.api.post(
            `/company/${id}/review`,
            { starRating: starPickerRating, reviewText: text },
            { headers: { Authorization: `Bearer ${props.authInfo.access_token}` } }
        );
        // Load updated company information
        loadReviews();
        loadCompany();

        // Close dialog
        handleCloseReviewDialog();
    }

    /**
     * Submits a question from the currently logged in user
     * @param {*} event Form submission event object, fired by a form containing the question text
     */
    async function submitQuestion(event) {
        // Prevent GET submission + reload
        event.preventDefault();

        // Get data
        const form = event.target;
        const text = form.text.value;

        // TODO: handle errors/success
        const response = await props.api.post(
            `/company/${id}/question`,
            { questionText: text },
            { headers: { Authorization: `Bearer ${props.authInfo.access_token}` } }
        );
        // Load updated company information
        loadQuestions();

        // Close dialog
        handleCloseQuestionDialog();
    }

    /**
     * Submit request to redeem company page as user
     */
    async function submitRedemptionRequest() {
        let response;
        try {
            response = await props.api.post(
                `/companyUser/${id}`,
                undefined,
                { headers: { Authorization: `Bearer ${props.authInfo.access_token}` } }
            );
        } catch (e) {
            window.alert("Eitthvað fór úrskeiðis, vinsamlegast reyndu aftur síðar");
            return;
        }
        window.alert("Beiðni send");
    }

    // Load company when component loads or props change
    useEffect(() => {
        loadCompany();
        loadReviews();
        loadQuestions();
    }, [props]);

    // "Write review" dialog
    const reviewDialog = (
        <Dialog open={reviewDialogOpen} onClose={handleCloseReviewDialog}>
            <DialogTitle>Skrifa umsögn</DialogTitle>
            <DialogContent>
                <form id="review-form" onSubmit={submitReview}>
                    <TextField autoFocus margin="dense" id="review-text" name="text" label="Umsögn" type="text" fullWidth multiline />
                    <Typography variant="subtitle2">Stjörnur</Typography>
                    <StarPicker rating={starPickerRating} setRating={setStarPickerRating} />
                    <DialogActions>
                        <Button onClick={handleCloseReviewDialog}>Hætta við</Button>
                        <Button type="submit">Senda</Button>
                    </DialogActions>
                </form>
            </DialogContent>
        </Dialog>
    );

    // "Ask question" dialog
    const questionDialog = (
        <Dialog open={questionDialogOpen} onClose={handleCloseQuestionDialog}>
            <DialogTitle>Spyrja spurningar</DialogTitle>
            <DialogContent>
                <form id="question-form" onSubmit={submitQuestion}>
                    <TextField autoFocus margin="dense" id="question-text" name="text" label="Spurning" type="text" fullWidth multiline />
                    <DialogActions>
                        <Button onClick={handleCloseQuestionDialog}>Hætta við</Button>
                        <Button type="submit">Senda</Button>
                    </DialogActions>
                </form>
            </DialogContent>
        </Dialog>
    );

    return (
        <>
            {reviewDialog}
            {questionDialog}
            <Container maxWidth="lg" sx={{ pt: 3, pb: 3 }}>
                {company ? (
                    <>
                        <Typography variant="h2" mb={1}>
                            {company.name}
                        </Typography>
                        <Stack direction="row" spacing={1} mb={1}>
                            <Chip icon={<WebIcon />} label="Vefur" component="a" href={company.website} target="_blank" clickable />
                            <Chip
                                icon={<PhoneIcon />}
                                label={company.phoneNumber}
                                component="a"
                                href={`tel:${company.phoneNumber}`}
                                clickable
                                aria-label="Símanúmer (hringir ef valið)"
                            />
                            <Chip icon={<ScheduleIcon />} label={company.openingHours} aria-label="Opnunartími" />
                            <Chip
                                icon={<BusinessIcon />}
                                label={company.address}
                                component="a"
                                href={`https://www.google.com/maps/search/?api=1&query=${encodeURI(company.address)}`}
                                target="_blank"
                                clickable
                                aria-label="Heimilisfang (skoðar á Google Maps ef valið)"
                            />
                        </Stack>
                        <StarRating rating={company.starRating} />
                        <Typography variant="body1" mt={1}>
                            {company.description}
                        </Typography>
                        <Typography variant="h3" mt={4} mb={1}>
                            Umsagnir
                        </Typography>
                        {reviews && reviews.length !== 0 ? (
                            <Grid container spacing={2} mb={1}>
                                {reviews.map((review) => (
                                    <Grid item xs={12} sm={6} md={3} key={review.id}>
                                        <Card>
                                            <CardContent>
                                                <Typography gutterBottom variant="h5" component="div">
                                                    {review.username}
                                                </Typography>
                                                <StarRating rating={review.starRating} />
                                                <Typography variant="body2" mt={1}>
                                                    {review.reviewText}
                                                </Typography>
                                            </CardContent>
                                        </Card>
                                    </Grid>
                                ))}
                            </Grid>
                        ) : (
                            <Typography variant="body1">Engar umsagnir hafa verið skrifaðar um þetta fyrirtæki.</Typography>
                        )}
                        <Button variant="contained" onClick={() => setReviewDialogOpen(true)} disabled={props.authInfo === null}>
                            Skrifa umsögn
                        </Button>
                        <Typography variant="h3" mt={4} mb={1}>
                            Spurningar
                        </Typography>
                        {questions && questions.length !== 0 ? (
                            <Grid container spacing={2} mb={1}>
                                {questions.map((question) => (
                                    <Grid item xs={12} sm={6} md={3} key={question.id}>
                                        <Card>
                                            <CardContent>
                                                <Typography gutterBottom variant="h5" component="div">
                                                    {question.username}
                                                </Typography>
                                                <Typography variant="body2">{question.questionText}</Typography>
                                                <Typography variant="h5" component="div" mt={2}>
                                                    Svar
                                                </Typography>
                                                {question.answerString ? (
                                                    <Typography variant="body2">{question.answerString}</Typography>
                                                ) : (
                                                    <Typography variant="body2" color="text.secondary">
                                                        Þessari spurningu hefur ekki verið svarað.
                                                    </Typography>
                                                )}
                                            </CardContent>
                                        </Card>
                                    </Grid>
                                ))}
                            </Grid>
                        ) : (
                            <Typography variant="body1">Engar spurningar hafa borist þessu fyrirtæki.</Typography>
                        )}
                        <Button variant="contained" onClick={() => setQuestionDialogOpen(true)} disabled={props.authInfo === null}>
                            Spyrja spurningar
                        </Button>
                        <Typography variant="h3" mt={4} mb={1}>
                            Stjórn
                        </Typography>
                        <Typography variant="body1">Ef þú ert fulltrúi þessa fyrirtækis getur þú sótt um að stjórna þessari síðu.</Typography>
                        <Button variant="contained" onClick={submitRedemptionRequest} disabled={props.authInfo === null}>
                            Sækja um stjórnunaraðgang
                        </Button>
                    </>
                ) : (
                    <Typography variant="body1">
                        <CircularProgress />
                    </Typography>
                )}
            </Container>
        </>
    );
}
