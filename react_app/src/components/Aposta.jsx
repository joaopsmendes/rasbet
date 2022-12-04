
import React, { useState, useEffect } from "react";
import Grid from '@mui/material/Grid';
import { Box, Divider } from "@mui/material";
import CheckIcon from '@mui/icons-material/Check';
import CloseIcon from '@mui/icons-material/Close';
import Button from '@mui/material/Button';

function Aposta(props) {

    const [date, setDate] = useState();
    const [montante, setMontante] = useState(0);
    const [odds, setOdds] = useState([]);
    const [resultado, setResultado] = useState(false);
    const [idAposta, setIdAposta] = useState();

    useEffect(() => {
        console.log("Aposta");
        console.log(props.aposta);
        setDate(props.aposta.dataAposta);
        setMontante(props.aposta.montante);
        setIdAposta(props.aposta.idAposta);
        if (props.aposta.odd != undefined) {
            setOdds([props.aposta.odd]);
        }
        else {
            setOdds(props.aposta.oddList);
        }
        setResultado(props.aposta.resultado);
    }, [props.aposta, props.map]);

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

    const cashOut = async () => {
        const user = JSON.parse(sessionStorage.getItem('user'));
        const response = await fetch('http://localhost:8080/cashout',
            {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                  },
                body: JSON.stringify({
                    userId: user,
                    idAposta: idAposta
                })
            });
        const data = await response.text();
        console.log(data);
        props.update(user);
    }

    const formatDate = (date) => {
        let dtToday = new Date(date);
        let month = dtToday.getMonth() + 1;
        let day = dtToday.getDate();
        let year = dtToday.getFullYear();
        return day + '-' + month + '-' + year;
    }




    return (
        <Grid container>
            <Grid item xs={12} sm={6} >
                {odds.length > 0 && odds.map((odd) => (infoOdd(odd)))}
            </Grid>

            <Grid item xs={12} sm={6}>
                <Divider sx={{ display: { xs: 'block', sm: 'none' } }} />
                <Grid direction="column" container >

                    <Grid item >
                        <p>Dia: <b>{formatDate(date)}</b></p>
                    </Grid>
                    <Grid item>
                        <p>Montante Apostado: <b>{montante} €</b></p>
                    </Grid>
                    <Grid item>
                        <p>Possíveis Ganhos: <b>{totalGanhos()}</b></p>
                        {
                            resultado === null ?
                                <Button variant="contained" color="inherit" onClick={cashOut} >Cashout</Button>
                                : resultado ?
                                    <CheckIcon fontSize="large" color="success" />
                                    : <CloseIcon fontSize="large" color="error" />
                        }
                    </Grid>
                </Grid>
            </Grid>

        </Grid>
    );
}

export default Aposta;
