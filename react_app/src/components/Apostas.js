
import React, { useState, useEffect } from "react";
import Aposta from "./Aposta";
import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos';
import ArrowBackIosIcon from '@mui/icons-material/ArrowBackIos';
import IconButton from '@mui/material/IconButton';
import Grid from '@mui/material/Grid';
import Button from '@mui/material/Button';
import { Container } from "@mui/material";
import Box from '@mui/material/Box';


function Apostas(props) {
    const [arraySimples, setSimples] = useState([])
    const [arrayMultipla, setMultipla] = useState([])

    const [indexSimples, setIndexSimples] = useState(0)

    const [indexMultipla, setIndexMultipla] = useState(0)



    const getApostas = async (email) => {
        console.log(email)
        const response = await fetch('http://localhost:8080/historicoAposta?' + new URLSearchParams({
            userId: email
        })
            , {
                method: 'GET',
            });
        const data = await response.json();
        const apostasSimples = [];
        const apostasMultiplas = [];
        data.map((aposta) => {
            if (aposta.odd !== undefined) {
                apostasSimples.push(aposta)
            }
            else {
                apostasMultiplas.push(aposta)
            }
        })
        setSimples(apostasSimples);
        setMultipla(apostasMultiplas);
    }

    useEffect(() => {
        const user = JSON.parse(sessionStorage.getItem('user'));
        getApostas(user);
    }, [])

    const hasnext = () => {
        let apostas = props.simples ? arraySimples : arrayMultipla;
        let index = props.simples ? indexSimples : indexMultipla;
        if (index + 1 < apostas.length)
            return true;
        else return false;
    }

    const hasprev = () => {
        let index = props.simples ? indexSimples : indexMultipla;
        if (index - 1 >= 0)
            return true;
        else return false;
    }

    const increment = () => {
        if (props.simples) 
            setIndexSimples(indexSimples + 1);
        else
            setIndexMultipla(indexMultipla + 1);
    }

    const decrement = () => {
        if (props.simples)
            setIndexSimples(indexSimples - 1);
        else
            setIndexMultipla(indexMultipla - 1);
    }


    return (
        <div className="Apostas">
                <Grid container spacing={2}>
                    <Grid item xs={1}>
                        {hasprev() &&
                            <Button variant="standard" onClick={decrement}>
                                <ArrowBackIosIcon />
                            </Button>
                        }
                    </Grid>
                    <Grid item xs={10}>

                        {
                            props.simples && arraySimples.length > 0 &&
                            <Aposta index={indexSimples} aposta={arraySimples[indexSimples]} />

                        }
                        {
                            props.multipla && arrayMultipla.length > 0 &&
                            <Aposta index={indexMultipla} aposta={arrayMultipla[indexMultipla]} />
                        }

                    </Grid>

                    <Grid item xs={1}>
                        {hasnext() &&
                            <Button variant="standard" onClick={increment}>
                                <ArrowForwardIosIcon />
                            </Button>
                        }
                    </Grid>
                </Grid>
        </div>
    );
}

export default Apostas;
