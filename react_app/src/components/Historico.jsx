import React, { useState, useEffect } from "react";
import Button from '@mui/material/Button';
import HistoricoApostas from "./HistoricoApostas";
import HistoricoTransacoes from "./HistoricoTransacao";
import Box from '@mui/material/Box';
import { Grid } from "@mui/material";
import Container from '@mui/material/Container';
import TextField from '@mui/material/TextField';


function Historico(props) {

    const [apostas, setApostas] = useState(false);
    const [transacoes, setTransacoes] = useState(false);
    const [dataInicial, setDataInicial] = useState()
    const [dataFinal, setDataFinal] = useState()
    const [filtro, setFiltro] = useState(false)



    const handleClickApostas = () => {
        setApostas(!apostas);
        setTransacoes(false);
    };

    const handleClickTransacoes = () => {
        setTransacoes(!transacoes);
        setApostas(false);
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
                        max: formatDateToday(),
                    }
                }}

                InputLabelProps={{
                    shrink: true,
                }}
            />);
    }



    const changeDataInicial = (event) => {
        setDataInicial(event.target.value);
    }
    const changeDataFinal = (event) => {
        setDataFinal(event.target.value);
    }


    return (
        <div className="HistoricoApostas">
            <h1>Histórico</h1>
            <Container maxWidth="xs" >
                <Grid container spacing={2} >
                    <Grid item xs={6} >
                        <Button fullWidth sx={{ mb: '10%' }} variant="contained" color={apostas ? "primary" : "inherit"} onClick={handleClickApostas}>
                            Apostas
                        </Button>
                    </Grid>
                    <Grid item xs={6} >
                        <Button fullWidth sx={{ mb: '10%' }} variant="contained" color={transacoes ? "primary" : "inherit"} onClick={handleClickTransacoes}>
                            Transações
                        </Button>
                    </Grid>
                </Grid>
            </Container>
            <Container maxWidth="xs" >
                <Button sx={{ mb: "2%"}} fullWidth variant="contained" color={filtro ? "primary" : "inherit"} onClick={() => (setFiltro(!filtro))} >Filtrar por Data</Button>
            </Container>
            <Container maxWidth="xs" >
                <Grid container spacing={2} >
                    {filtro &&
                        <Grid item xs={12} md={6}>
                            {dateField("Data Inicial", changeDataInicial)}
                        </Grid>
                    }
                    {filtro &&
                        <Grid item xs={12} md={6}>
                            {dateField("Data Final", changeDataFinal)}
                        </Grid>
                    }
                </Grid>
            </Container>

            <Grid container spacing={2}>
                <Grid item xs={12} >
                    {apostas && <HistoricoApostas filtro={filtro} dataInicial={dataInicial} dataFinal={dataFinal} />}
                </Grid>
                <Grid item xs={12}  >
                    {transacoes && <HistoricoTransacoes filtro={filtro} dataInicial={dataInicial} dataFinal={dataFinal} />}
                </Grid>
            </Grid>

        </div >

    );
}

export default Historico;
