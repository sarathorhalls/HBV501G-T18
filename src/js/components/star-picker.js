import React, { useState } from "react";
import Box from "@mui/material/Box";
import StarIcon from '@mui/icons-material/Star';
import StarOutlineIcon from '@mui/icons-material/StarOutline';

/**
 * Returns true if n is a plural number in Icelandic, false otherwise
 * @param {number} n Number to check whether or not is plural
 * @returns true if the number is plural in Icelandic, false otherwise
 */
function plural(n) {
    return !(n % 10 === 1 && n % 100 !== 11);
}

/**
 * Props object for the star picker component
 * 
 * @typedef {object} StarPickerProps
 * @property {number} rating React state object containing selected rating
 * @property {Function} setRating React state setter for rating
 */

/**
 * Star picker component for review creation
 * 
 * @param {StarPickerProps} props 
 * @returns 
 */
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
                        aria-label={`${i} ${plural(i) ? "stjörnur" : "stjarna"}`}
                    />
                )
                : (
                    <StarOutlineIcon
                        onMouseOver={() => setHoverRating(i)}
                        onClick={(() => props.setRating(i))}
                        sx={{ cursor: "pointer" }}
                        key={i}
                        aria-label={`${i} ${plural(i) ? "stjörnur" : "stjarna"}`}
                    />
                )
        );
    }

    return (
        <Box
            // If mouse leaves box, no rating is being hovered over
            onMouseOut={() => setHoverRating(0)}
            aria-label={`Stjörnugjöf (${hoverRating === 0 ? `${props.rating} ${plural(props.rating) ? "stjörnur valdar" : "stjarna valin"}` : `bendill á ${hoverRating} ${plural(hoverRating) ? "stjörnum" : "stjörnu"}`})`}
        >
            {stars}
        </Box>
    );
}
