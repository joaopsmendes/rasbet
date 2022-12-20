import React, { useState, useEffect } from "react";
import ApostaJogo from "./ApostaJogo";
import Container from '@mui/material/Container';
import Box from '@mui/material/Box';
import Checkbox from '@mui/material/Checkbox';
import FavoriteBorder from '@mui/icons-material/FavoriteBorder';
import Favorite from '@mui/icons-material/Favorite';
import { Grid } from "@mui/material";
import Popover from '@mui/material/Popover';
import Button from '@mui/material/Button';
import IconButton from "@mui/material/IconButton";
import CheckBoxOutlineBlankIcon from '@mui/icons-material/CheckBoxOutlineBlank';
import CheckBoxIcon from '@mui/icons-material/CheckBox';


function Jogo(props) {

    const [apostas, setApostas] = useState([]);
    const [time, setTime] = useState();
    const [participantes, setParticipantes] = useState([]);
    const [id, setID] = useState('');
    const [anchorEl, setAnchorEl] = React.useState(null);
    const [seguir, setSeguir] = useState(false);

    const handleClick = (event) => {
        setAnchorEl(event.currentTarget);
    };

    const handleClose = () => {
        setAnchorEl(null);
    };

    const open = Boolean(anchorEl);
    const idPop = open ? 'simple-popover' : undefined;


    const formatarData = (data) => {
        let today = new Date();
        let dataFormatada = new Date(data);
        let dia = dataFormatada.getDate();
        let mes = dataFormatada.getMonth() + 1;
        let ano = dataFormatada.getFullYear();
        let hora = dataFormatada.getHours();
        let minuto = dataFormatada.getMinutes();
        if (today.getDate() == dia && today.getMonth() + 1 == mes && today.getFullYear() == ano) {
            return "Hoje às " + hora + ":" + minuto;
        }
        if (today.getDate() + 1 == dia && today.getMonth() + 1 == mes && today.getFullYear() == ano) {
            return "Amanhã às " + hora + ":" + minuto;
        }
        return dia + "/" + mes + "/" + ano + " " + hora + ":" + minuto;
    }



    useEffect(() => {
        setID(props.jogo.idJogo)
        setParticipantes(props.jogo.participantes)
        setTime(formatarData(props.jogo.data));
        let apostasArray = Object.keys(props.jogo.apostas).map((key) => [key, props.jogo.apostas[key]]);
        setApostas(apostasArray);
    }, [])




    const favoritar = () => {
        return (
            <Popover
                id={idPop}
                open={open}
                anchorEl={anchorEl}
                onClose={handleClose}
                sx={{ p: 2 }}
                anchorOrigin={{
                    vertical: 'bottom',
                    horizontal: 'left',
                }}
            >
                {participantes.length > 0 && participantes.map((participante) => {
                    return (<div>
                        {participante}{fav(participante)}
                    </div>
                    )
                }
                )}
            </Popover>
        )
    }


    const addFavorito = async (nome) => {
        const user = JSON.parse(sessionStorage.getItem('user'));
        const response = await fetch('http://localhost:8080/addFavorito', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                id: user,
                value: nome,
                desporto: props.desporto
            }),
        });
    }

    const removeFavorito = async (nome) => {
        const user = JSON.parse(sessionStorage.getItem('user'));

        const response = await fetch('http://localhost:8080/removeFavorito', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                id: user,
                value: nome,
                desporto: props.desporto
            }),
        });

    }

    const handleClickFav = (event) => {
        let obj = props.favoritos.slice();
        let participante = event.currentTarget.value;
        if (isInFav(participante)) {
            console.log("REMOVE");
            removeFavorito(participante);
            obj = obj.filter((item) => item !== participante);
        } else {
            console.log("ADD");
            addFavorito(participante);
            obj.push(event.currentTarget.value);
        }
        props.setFavoritos(obj);
    }

    const isInFav = (participante) => {
        for (let i = 0; i < props.favoritos.length; i++) {
            if (props.favoritos[i] === participante) {
                return true;
            }
        }
        return false;
    }



    const fav = (participante) => {
        return (
            <IconButton color="primary" value={participante} aria-label="add to favorites" onClick={handleClickFav}>
                {isInFav(participante) ? <Favorite /> : <FavoriteBorder />}
            </IconButton>
        );
    }


    const addASeguir = async () => {
        const user = JSON.parse(sessionStorage.getItem('user'));
        const response = await fetch('http://localhost:8080/addJogoASeguir', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                idUser: user,
                idJogo: id,
                desporto: props.desporto
            }),
        });
    }

    const removeASeguir = async () => {
        const user = JSON.parse(sessionStorage.getItem('user'));
        const response = await fetch('http://localhost:8080/removeJogoASeguir', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                idUser: user,
                idJogo: id,
                desporto: props.desporto
            }),
        });
    }



    const handleClickASeguir = (event) => {
        if (seguir){
            addASeguir();
        }
        else{
            removeASeguir();
        }
    
        setSeguir(!seguir);
        console.log("Seguir a este jogo" + id);
    }





    return (
        <div className="Jogo">
            <Box size="md" sx={{ m: '2%', p: '1%', border: 2, borderRadius: '30px' }} >
                <Container sx={{ maxWidth: '80%', margin: 'auto' }} maxWidth="false">
                    <Grid container spacing={2}>
                        {props.showFavoritos &&
                            <Grid item xs={2}>
                                Seguir Jogo
                                <IconButton color="primary" onClick={handleClickASeguir}>
                                    {seguir ?
                                        <CheckBoxIcon />
                                        : < CheckBoxOutlineBlankIcon />
                                    }
                                </IconButton>
                            </Grid>
                        }
                        <Grid item xs={props.showFavoritos ? 8 : 12}>
                            <h2>{props.jogo.titulo}</h2>
                        </Grid>
                        {props.showFavoritos &&
                            <Grid item xs={2}>
                                <IconButton onClick={handleClick} color="primary">
                                    <FavoriteBorder />
                                </IconButton>
                                {favoritar()}
                            </Grid>
                        }
                    </Grid>
                    <p>{time}</p>
                    <div className="Container">
                        {
                            apostas.length > 0 && apostas.map((aposta) => (<ApostaJogo apostas={props.apostas} setAlignment={props.setAlignment} handleClick={props.handleClick} removeOdd={props.removeOdd} addOdd={props.addOdd} key={aposta.tema} aposta={aposta} />))
                        }
                    </div>
                </Container>
            </Box>
        </div>
    );
}

export default Jogo;
