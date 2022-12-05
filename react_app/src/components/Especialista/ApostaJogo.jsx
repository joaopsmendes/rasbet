import React, { useState, useEffect } from "react";
import ToggleButtonGroup from '@mui/material/ToggleButtonGroup';
import { getAccordionDetailsUtilityClass, ToggleButton } from "@mui/material";

import Odd from "./Odd"



function ApostaJogo(props) {

    const [odds, setOdds] = useState([]);
    const [nome, setNome] = useState('');
    const [alignment, setAlignment] = useState();



    useEffect(() => {
        setNome(props.name)
        setOdds(props.odds)
    }, []);





    const handleChange = (event, newAlignment) => {
        console.log("alignment");
        console.log(alignment);
        console.log(newAlignment);

    };


    const addOdd = (newAposta) => {
        newAposta["nome"] = nome;
    }

    const removeOdd = (newAposta) => {
    }



    return (
        <div className="ApostaJogo">
            <h3>{nome}</h3>
            <ToggleButtonGroup
                fullWidth
                color="warning"
                value={alignment}
                exclusive
                onChange={handleChange}
            >
                {
                    odds.length > 0 && odds.map((odd) => (
                        <ToggleButton style={{ outlineWidth: '2px', outlineStyle: 'solid', margin: '1%', borderRadius: "10px", padding: '1%' }} sx={{ m: 4, }}
                            value={odd.name}>
                                <Odd key={odd.name} odd={odd} />
                        </ToggleButton>))
                }
            </ToggleButtonGroup>
        </div>

    );
}

export default ApostaJogo;
