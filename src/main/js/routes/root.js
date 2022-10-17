import React, { useState, useEffect } from "react";

export default function Root(props) {
    const [companies, setCompanies] = useState(null);
    const [users, setUsers] = useState(null);
    const [reviews, setReviews] = useState(null);
    const [questions, setQuestions] = useState(null);

    async function loadCompanies() {
        let data = await props.api.get("/companies").then(({ data }) => data);
        setCompanies(data);
    }

    async function loadUsers() {
        let data = await props.api.get("/users").then(({ data }) => data);
        setUsers(data);
    }

    async function loadReviews(companyId) {
        let data = await props.api.get(`/company/${companyId}/reviews`).then(({ data }) => data);
        console.log(data);
        setReviews(data);
    }

    async function loadQuestions(companyId) {
        let data = await props.api.get(`/company/${companyId}/questions`).then(({ data }) => data);
        console.log(data);
        setQuestions(data);
    }

    async function createCompany() {
        let response = await props.api.post("/companies", { name: "New Company" });
        console.log(response);
        loadCompanies();
    }

    async function createUser() {
        let response = await props.api.post("/users", { username: "New User", password: "password" });
        console.log(response);
        loadUsers();
    }

    async function addReview(companyId, userId) {
        let response = await props.api.post("/company/" + companyId + "/reviews", { starRating: 5.0, reviewText: "New Review" }, { params: { userId } });
        console.log(response);
        loadReviews(companyId);
    }

    async function addQuestion(companyId, userId) {
        let response = await props.api.post("/company/" + companyId + "/questions", { questionText: "New Question" }, { params: { userId } });
        console.log(response);
        loadQuestions(companyId);
    }

    async function addAnswer(questionId) {
        const answer = "New Answer";
        let response = await props.api.post("/question/" + questionId + "/answers", answer, { headers: { "Content-Type": "text/plain" } });
        console.log(response);
        loadQuestions(37);
    }

    async function deleteCompany(id) {
        let response = await props.api.delete("/company/" + id);
        console.log(response);
        loadCompanies();
    }

    async function deleteUser(id) {
        let response = await props.api.delete("/user/" + id);
        console.log(response);
        loadUsers();
    }

    useEffect(() => {
        loadCompanies();
        loadReviews(37);
        loadQuestions(37);
        loadUsers();
    }, []);

    return (
        <div>
            <button onClick={createCompany}>Create Company</button>
            <ul>
                {companies &&
                    companies.map((company) => (
                        <li key={company.id}>
                            {company.name}
                            {company.id}
                            <button onClick={() => addReview(company.id, users[0].id)}>Add Review</button>
                            <button onClick={() => addQuestion(company.id, users[0].id)}>Add Question</button>
                            <button onClick={() => deleteCompany(company.id)}>Delete</button>
                        </li>
                    ))}
            </ul>
            <ul>
                {reviews &&
                    reviews.map((review) => (
                        <li key={review.id}>
                            {review.reviewText}
                            {review.starRating}
                        </li>
                    ))}
            </ul>
            <ul>
                {questions &&
                    questions.map((question) => (
                        <li key={question.id}>
                            {question.questionText}
                            {question.answerText}
                            <button onClick={() => addAnswer(question.id)}>Add Answer</button>
                        </li>
                    ))}
            </ul>
            <button onClick={createUser}>Create User</button>
            <ul>
                {users &&
                    users.map((user) => (
                        <li key={user.id}>
                            {user.username}
                            {user.id}
                            <button onClick={() => deleteUser(user.id)}>Delete</button>
                        </li>
                    ))}
            </ul>
        </div>
    );
}
