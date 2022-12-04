import React, { useState, useEffect } from "react";
import Jogo from "./Jogo";
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import Paper from '@mui/material/Paper';
import Pagamento from "./Pagamento";
import Boletim from "./Boletim";
import SearchIcon from '@mui/icons-material/Search';
import { styled, alpha } from '@mui/material/styles';
import InputBase from '@mui/material/InputBase';





function Jogos(props) {
    //passar os desportos no props
    //passar desporto ativo no props

    const [jogos, setJogos] = useState({})
    const [search, setSearch] = useState("")
    const [dataInicial, setDataInicial] = useState()
    const [dataFinal, setDataFinal] = useState()
    const [filtro, setFiltro] = useState(false)





    const getJogos = async () => {
        const response = await fetch('http://localhost:8080/jogos?' + new URLSearchParams({
            desporto: props.desportoAtivo
        })
            , {
                method: 'GET',
            });
        const data = await response.json();
        console.log("DATA");
        console.log(data);
        //const data =response.json();
        var result = Object.keys(data).map((key) => data[key]);
        console.log("JOGOS");
        console.log(data)
        setJogos(result);

    }



    useEffect(() => {
        getJogos();
    }, [props.desportoAtivo])

    /*
        const addOdd = (newAposta) => {
            var newApostaArray = aposta.slice();
            console.log(newAposta)
            for (var i = 0; i < newApostaArray.length; i++) {
                console.log(newApostaArray[i])
                if (newApostaArray[i].desJogo === newAposta.desJogo && newApostaArray[i].tema === newAposta.tema) {
                    newApostaArray[i] = newAposta;
                    setAposta(newApostaArray);
                    console.log("ja existe");
                    return;
                }
            }
            newApostaArray.push(newAposta);
            setAposta(newApostaArray);
        }
    
        const removeOdd = (idOdd) => {
            console.log(idOdd);
            var newApostaArray = aposta.slice();
            newApostaArray = newApostaArray.filter((aposta) => aposta.idOdd !== idOdd);
            setAposta(newApostaArray);
        }
        */

    const handleClick = (odd,) => {
        if (props.showBoletim) {
            props.handleClick(odd);
        }
        else {
            let idJogo = odd.desJogo;
            jogos.map((jogo) => {
                if (jogo.idJogo === idJogo) {
                    odd['titulo'] = jogo.titulo;
                    odd['idJogo'] = idJogo;
                    odd['estado'] = jogo.estado;
                }
            });
            props.handleClick(odd);
        }
    }


    const formatDateToday = () => {
        let dtToday = new Date();
        let month = dtToday.getMonth() + 1;
        let day = dtToday.getDate();
        let year = dtToday.getFullYear();
        if (month < 10)
            month = '0' + month.toString();
        if (day < 10)
            day = '0' + day.toString();
        return year + '-' + month + '-' + day;
    }


    const dateField = (nome, change) => {
        return (
            <TextField
                margin="normal"
                name="date"
                id="date"
                label={nome}
                type="date"
                fullWidth
                onChange={change}
                InputProps={{
                    inputProps: {
                        min: formatDateToday(),
                    }
                }}

                InputLabelProps={{
                    shrink: true,
                }}
            />);
    }

    const isSearch = (jogo) => {
        if (search === "") {
            return true;
        }
        else {
            return jogo.titulo.toLowerCase().includes(search.toLowerCase());
        }
    }

    const filterByDate = (jogo) => {
        if (!filtro) return true;
        if (dataInicial === undefined || dataFinal === undefined) {
            return true;
        }
        else {
            let dataJogo = new Date(jogo.data);
            dataJogo.setHours(0, 0, 0, 0);
            let dataInicialDate = new Date(dataInicial);
            let dataFinalDate = new Date(dataFinal);
            return dataJogo >= dataInicialDate && dataJogo <= dataFinalDate;
        }
    }




    const changeDataInicial = (event) => {
        setDataInicial(event.target.value);
    }
    const changeDataFinal = (event) => {
        setDataFinal(event.target.value);
    }


    return (
        <div>
            <Box sx={{ flexGrow: 1, m: 2 }}>
                {jogos.length > 0 &&
                    <Grid container spacing={2} >
                        <Grid item xs={12} >
                            <TextField
                                fullWidth
                                id="outlined-basic"
                                label="Pesquisar"
                                onChange={(event) => setSearch(event.target.value)}
                            />
                        </Grid>
                        <Grid item xs={12} md={2}>
                            <Button sx={{ mt: 3 }} fullWidth variant="contained" color={filtro ? "primary" : "inherit"} onClick={() => (setFiltro(!filtro))} >Filtrar por Data</Button>
                        </Grid>
                        {filtro &&
                            <Grid item xs={12} md={1}>
                                {dateField("Data Inicial", changeDataInicial)}
                            </Grid>
                        }
                        {filtro &&
                            <Grid item xs={12} md={1}>
                                {dateField("Data Final", changeDataFinal)}
                            </Grid>
                        }
                    </Grid>
                }
            </Box>

            <Grid container spacing={2}>
                <Grid item xs={12} md={9} xl={9}>
                    {jogos.length > 0 ?
                        jogos.map((jogo) => (isSearch(jogo) && filterByDate(jogo) && <Jogo showFavoritos={props.showBoletim} apostas={props.aposta} setAlignment={props.setAlignment} handleClick={handleClick} key={jogo.idJogo} jogo={jogo} />))
                        : <h1>Não existem jogos disponíveis neste momento</h1>}
                </Grid>
                {props.showBoletim &&
                    <Boletim handleClick={handleClick} user={props.user} login={props.login} jogos={jogos} apostas={props.aposta} setAposta={props.setAposta} />
                }
            </Grid>

        </div>
    );
}

export default Jogos;
