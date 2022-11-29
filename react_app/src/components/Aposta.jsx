
import React, { useState, useEffect } from "react";
import Grid from '@mui/material/Grid';
import { Box } from "@mui/material";
import CheckIcon from '@mui/icons-material/Check';
import CloseIcon from '@mui/icons-material/Close';


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
    }, [props.index]);

    const infoOdd = (odd) => {
        return (
            <div>
                <h3>{odd.desJogo}</h3>
                <p>{odd.opcao} : {odd.valor}</p>
            </div>
        );
    }

    const totalGanhos = () => {
        let total = 1;
        odds.map((odd) => {
            total *= odd.valor;
        });
        return (total * montante).toFixed(2);
    }


    return (
        <Grid container>
            <Grid item xs={12} sm={6} >
                {odds.length > 0 && odds.map((odd) => (infoOdd(odd)))}
            </Grid>
            <Grid item xs={12} sm={6}>
                <Grid direction="column" container >
                    <Grid item >
                        <p>Dia: <b>{date}</b></p>
                        <hr />
                    </Grid>
                    <Grid item>
                        <p>Montante Apostado: <b>{montante} €</b></p>
                    </Grid>
                    <Grid item>
                        <p>Possíveis Ganhos: <b>{totalGanhos()}</b></p>
                        {resultado ? <CheckIcon  fontSize="large" color="success"/> : <CloseIcon fontSize="large" color="error"/>}
                    </Grid>
                </Grid>
            </Grid>

        </Grid>
    );
}

export default Aposta;
