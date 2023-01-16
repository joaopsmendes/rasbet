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
import Pagination from '@mui/material/Pagination';
import { Container } from "@mui/material";



function Jogos(props) {

    const jogosPerPage = 5;
    //passar os desportos no props
    //passar desporto ativo no props

    const [jogos, setJogos] = useState({})
    const [search, setSearch] = useState("")
    const [dataInicial, setDataInicial] = useState()
    const [dataFinal, setDataFinal] = useState()
    const [filtro, setFiltro] = useState(false)
    const [filtroFav, setFiltroFav] = useState(false)
    const [filtroAseguir, setFiltroAseguir] = useState(false)
    const [page, setPage] = useState(1);
    const [maxPage, setMaxPage] = useState(1)





    const getJogos = async () => {
        const response = await fetch('http://localhost:8080/jogos?' + new URLSearchParams({
            desporto: props.desportoAtivo
        })
            , {
                method: 'GET',
            });
        if (response.status !== 200) {
            console.log("Erro ao obter jogos");
            return;
        }
        const data = await response.json();
        console.log("JOGOS",data);
        console.log(props.desportoAtivo);
        //const data =response.json();
        var result = Object.keys(data).map((key) => data[key]);
        setJogos(result);
        setMaxPage(Math.ceil(jogos.length / jogosPerPage));


    }

    useEffect(() => {
        getJogos();
    }, [props.desportoAtivo])


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



    const isSearch = (jogo) => {
        if (search === "") {
            return true;
        }
        else {
            return jogo.titulo.toLowerCase().includes(search.toLowerCase());
        }
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


    const filtroFavorites = (jogo) => {
        if (!props.showFavoritos) return true;
        let favs = Array.from(props.favoritos);
        if (!filtroFav) return true;
        else {
            for (let i = 0; i < favs.length; i++) {
                for (let u = 0; u < jogo.participantes.length; u++) {
                    if (favs[i] === jogo.participantes[u]) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    const filtroByAseguir = (jogo) => {
        if (!filtroAseguir) return true;
        if (!props.seguir) return false;
        let aseguir = Array.from(props.seguir);
        return aseguir.includes(jogo.idJogo);
    }




    const handleChangePage = (event, value) => {
        console.log("Page changed" + value);
        setPage(value);
    };




    const jogosFilter = Object.entries(jogos).slice().map(entry => entry[1]).filter(jogo => isSearch(jogo) && filterByDate(jogo) && filtroFavorites(jogo) && filtroByAseguir(jogo));

    const jogosPage = jogosFilter.slice((page - 1) * jogosPerPage, page * jogosPerPage);
    /*
    const jogosPage = () => {
        console.log("JOGOS");
        console.log(typeof (jogos));
        let min = (page - 1) * jogosPerPage;
        let max = page * jogosPerPage;
        console.log(min + " - " + max)
        let array = Object.entries(jogos).slice(min, max).map(entry => entry[1]);
        array = array.filter(jogo => isSearch(jogo) && filterByDate(jogo) && filtroFavorites(jogo));
        console.log(array.length);
        return array;
    }
    */

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
                        {props.showBoletim && props.login &&
                            <Grid item xs={12} md={2}>
                                <Button sx={{ mt: 3 }} fullWidth variant="contained" color={filtroFav ? "primary" : "inherit"} onClick={() => (setFiltroFav(!filtroFav))} >Filtrar por Favoritos</Button>
                            </Grid>
                        }
                        {props.showBoletim && props.login &&
                            <Grid item xs={12} md={2}>
                                <Button sx={{ mt: 3 }} fullWidth variant="contained" color={filtroAseguir ? "primary" : "inherit"} onClick={() => (setFiltroAseguir(!filtroAseguir))} >Filtrar por Jogos a seguir</Button>
                            </Grid>
                        }
                    </Grid>
                }
            </Box>

            <Grid container spacing={2}>
                <Grid item xs={12} md={9} xl={9}>
                    {jogosPage != undefined && jogosPage.length > 0 ?
                        jogosPage.map((jogo) => (<Jogo updateSeguir={props.updateSeguir} seguir={props.seguir} setFavoritos={props.setFavoritos} favoritos={props.favoritos} desporto={props.desportoAtivo} showFavoritos={props.showFavoritos} apostas={props.aposta} setAlignment={props.setAlignment} handleClick={handleClick} key={jogo.idJogo} jogo={jogo} />))
                        : <h1>Não existem jogos disponíveis neste momento</h1>}
                </Grid>
                {props.showBoletim &&
                    <Boletim handleClick={handleClick} login={props.login} jogos={jogos} apostas={props.aposta} setAposta={props.setAposta} />
                }
            </Grid>
            {jogosPage.length > 0 &&
                <Container maxWidth="sm">
                    <Pagination color="primary" onChange={handleChangePage} count={Math.ceil(jogosFilter.length / jogosPerPage)} />
                </Container>
            }

        </div>
    );
}

export default Jogos;
