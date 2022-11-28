
import React, { useState, useEffect } from "react";
import Grid from '@mui/material/Grid';

import {
    createTheme,
    responsiveFontSizes,
    ThemeProvider,
} from '@mui/material/styles';
import Typography from '@mui/material/Typography';

let theme = createTheme();
theme = responsiveFontSizes(theme);

function Aposta(props) {

    const [date, setDate] = useState();
    const [montante, setMontante] = useState(0);
    const [odds, setOdds] = useState([]);
    const [resultado, setResultado] = useState(false);

    useEffect(() => {
        console.log("Aposta");
        console.log(props.aposta);
        setDate(props.aposta.dataAposta);
        setMontante(props.aposta.montante);
        if (props.aposta.odd != undefined) {
            setOdds([props.aposta.odd]);
        }
        else {
            setOdds(props.aposta.oddList);
        }
        setResultado(props.aposta.resultado);
    }, [props.index])

    const infoOdd = (odd) => {
        return (
            <Grid direction="column" container spacing={2}>
                <Grid item xs={4}>
                    <h3>{odd.desJogo}</h3>
                </Grid>
                <Grid item xs={4}>
                    <h4>Opção: {odd.opcao} </h4>
                </Grid>
                <Grid item xs={4}>
                    <h4>Valor Odd: {odd.valor} </h4>
                </Grid>
            </Grid>
        )
    }


    return (
        <Grid container spacing={2}>
            <Grid item xs={12} sm={6} >
                {odds.length > 0 && odds.map((odd) => (infoOdd(odd)))}
            </Grid>
            <Grid item xs={12} sm={6}>
                <Grid direction="column" container spacing={2}>
                    <Grid item xs={6}>
                        <h4>Dia: {date}</h4>
                    </Grid>
                    <Grid item xs={6}>
                        <h4>Montante Apostado: {montante} €</h4>
                    </Grid>
                </Grid>
            </Grid>

        </Grid>
    );
}

export default Aposta;
