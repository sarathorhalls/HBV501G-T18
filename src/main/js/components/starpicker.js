import React, { useState } from "react";
import Box from "@mui/material/Box";
import StarIcon from '@mui/icons-material/Star';
import StarOutlineIcon from '@mui/icons-material/StarOutline';

// TODO: add jsdoc, state that props.rating and props.setRating are required
export default function StarPicker(props) {
    const noOfStars = 5;

    // Current rating being hovered over (0 if not hovering)
    const [hoverRating, setHoverRating] = useState(0);

    // Create star array
    const stars = [];
    for (let i = 1; i <= noOfStars; i++) {
        stars.push(
            // Show star as filled if the displayed rating is smaller or equal to i (if hovering)
            // or if a rating has been selected and the user is not hovering over the stars
            i <= hoverRating || (hoverRating === 0 && i <= props.rating)
                ? (
                    <StarIcon
                        onMouseOver={() => setHoverRating(i)}
                        onClick={() => props.setRating(i)}
                        sx={{ cursor: "pointer" }}
                        key={i}
                    />
                )
                : (
                    <StarOutlineIcon
                        onMouseOver={() => setHoverRating(i)}
                        onClick={(() => props.setRating(i))}
                        sx={{ cursor: "pointer" }}
                        key={i}
                    />
                )
        );
    }

    // TODO: add accessible way of reading rating
    return (
        <Box
            // If box is left, no rating is being hovered over
            onMouseOut={() => setHoverRating(0)}
        >
            {stars}
        </Box>
    );
}
