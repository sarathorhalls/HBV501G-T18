import React from "react";
import Box from "@mui/material/Box";
import StarIcon from '@mui/icons-material/Star';
import StarHalfIcon from '@mui/icons-material/StarHalf';
import StarOutlineIcon from '@mui/icons-material/StarOutline';

export default function StarRating(props) {
    // Array of star icons
    const stars = [];

    // Number of stars in total
    const noOfTotalStars = 5;
    // Number of completely filled star icons
    const noOfFilledStars = Math.floor(props.rating);
    
    // Add appropriate number of filled star icons
    let i = 0;
    for (; i < noOfFilledStars; i++) {
        stars.push(<StarIcon key={i} />);
    }

    // If star rating is not an integer, add half a star
    if (props.rating !== noOfFilledStars) {
        stars.push(<StarHalfIcon key={i} />);
        i++;  // Decrease number of remaining stars by one
    }

    // Add remaining empty stars
    for (; i < noOfTotalStars; i++) {
        stars.push(<StarOutlineIcon key={i} />);
    }

    // TODO: add accessible way of reading rating
    return (
        <Box>
            {stars}
        </Box>
    );
}