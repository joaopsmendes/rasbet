
import React, { useState, useEffect } from "react";
import Aposta from "./Aposta";
import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos';
import ArrowBackIosIcon from '@mui/icons-material/ArrowBackIos';
import IconButton from '@mui/material/IconButton';
import Grid from '@mui/material/Grid';
import Button from '@mui/material/Button';
import { Container } from "@mui/material";


function Apostas(props) {
    const [apostas, setApostas] = useState({})
    const [index, setIndex] = useState(0)


    const getApostas = async (email) => {
        console.log(email)
        const response = await fetch('http://localhost:8080/historicoAposta?' + new URLSearchParams({
            userId: email
        })
            , {
                method: 'GET',
            });
        const data = await response.json();
        setApostas(data);
        console.log(data);
    }

    useEffect(() => {
        const user = JSON.parse(sessionStorage.getItem('user'));
        getApostas(user);
    }, [])

    const hasnext = () => {
        if (index + 1 < apostas.length)
            return true;
        else return false;
    }

    const hasprev = () => {
        if (index - 1 >= 0)
            return true;
        else return false;
    }

    const increment = () => {
        setIndex(index + 1);
    }

    const decrement = () => {
        setIndex(index - 1);
    }

    const setButton = (value) => {
        if (value.odd !== undefined){
            props.simples(true);
            props.multipla(false);
        }
        else{
            props.multipla(true);
            props.simples(false);
        }
    }


    return (
        <div className="Apostas">
            <Container>
                <Grid container spacing={2}>
                    <Grid item xs={1}>
                        {hasprev() &&
                            <Button variant="standard" onClick={decrement}>
                                <ArrowBackIosIcon />
                            </Button>
                        }
                    </Grid>

                    {
                        apostas.length > 0 &&
                        <Grid item xs={10}>
                            {setButton(apostas[index])}
                            <Aposta index={index} aposta={apostas[index]} />
                        </Grid>
                    }
                    <Grid item xs={1}>

                        {hasnext() &&
                            <Button variant="standard" onClick={increment}>
                                <ArrowForwardIosIcon />
                            </Button>
                        }
                    </Grid>
                </Grid>
            </Container>
        </div>
    );
}

export default Apostas;
