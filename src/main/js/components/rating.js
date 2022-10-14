import React from "react";
import Box from "@mui/material/Box";
import StarIcon from '@mui/icons-material/Star';
import StarHalfIcon from '@mui/icons-material/StarHalf';
import StarOutlineIcon from '@mui/icons-material/StarOutline';

export default function StarRating(props) {
    // Array of star icons
    const stars = [];

    // Number of completely filled star icons
    const filledStars = Math.floor(props.rating);
    // Add appropriate number of filled star icons
    for (let i = 0; i < filledStars; i++) {
        stars.push(<StarIcon />);
    }

    // Number of empty star icons
    let emptyStars = 5 - filledStars;
    
    // If star rating is not an integer, add half a star
    if (props.rating !== filledStars) {
        stars.push(<StarHalfIcon />);
        emptyStars--;  // Decrease number of empty stars by one
    }

    // Add remaining empty stars
    for (let i = 0; i < emptyStars; i++) {
        stars.push(<StarOutlineIcon />);
    }

    // TODO: add accessible way of reading rating
    return (
        <Box>
            {stars}
        </Box>
    );
}