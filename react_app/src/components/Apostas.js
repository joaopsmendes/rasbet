
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
    const [mapIndex, setMapIndex] = useState([[0,0],[0,0]])
    const [index, setIndex] = useState(0)
    const [apostasMap, setApostasMap] = useState()
    const [apostas, setApostas] = useState([])



    const getApostas = async (email) => {
        const response = await fetch('http://localhost:8080/historicoAposta?' + new URLSearchParams({
            userId: email
        })
            , {
                method: 'GET',
            });
        const data = await response.json();
        const apostasMap = {
            ativas: {
                simples: [],
                multiplas: []
            },
            finalizadas: {
                simples: [],
                multiplas: []
            }
        }
        data.map((aposta) => {
            let map;
            if (aposta.resultado !== null) map = apostasMap["finalizadas"];
            else map = apostasMap["ativas"];
            let array;
            if (aposta.odd !== undefined) array = map["simples"];
            else array = map["multiplas"];
            array.push(aposta);
        })
        console.log("MAP", apostasMap);
        setApostasMap(apostasMap);
    }


    useEffect(() => {
        const user = JSON.parse(sessionStorage.getItem('user'));
        getApostas(user);
    }, [])

    useEffect(() => {
        if (apostasMap) {
            let array = props.ativas ? apostasMap["ativas"] : apostasMap["finalizadas"];
            array = props.simples ? array["simples"] : array["multiplas"];
            let arrayIndex = mapIndex.slice();
            setIndex(getIndex())
            setApostas(array);
        }
    }, [props.ativas, props.simples])

    const hasnext = () => {
        return index < apostas.length - 1;
    }

    const hasprev = () => {
        return index > 0;
    }

    const getIndex = () => {
        return mapIndex[props.ativas ? 0: 1][props.simples ? 0 : 1];
    }

    const increment = () => {
        const map = mapIndex.slice();
        map[props.ativas ? 0 : 1][props.simples ? 0 : 1] += 1;
        setMapIndex(map);
        setIndex(index + 1);
    }

    const decrement = () => {
        const map = mapIndex.slice();
        map[props.ativas ? 0 : 1][props.simples ? 0 : 1] -= 1;
        setMapIndex(map);
        setIndex(index - 1);
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
                        apostas.length > 0 &&
                        <Aposta index={index} aposta={apostas[index]} />
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
