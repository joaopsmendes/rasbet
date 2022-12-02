import React, { useState, useEffect } from "react";
import Button from '@mui/material/Button';
import HistoricoApostas from "./HistoricoApostas";
import HistoricoTransacoes from "./HistoricoTransacao";
import Box from '@mui/material/Box';
import { Grid } from "@mui/material";
import Container from '@mui/material/Container';


function Historico(props) {

    const [apostas, setApostas] = useState(false);
    const [transacoes, setTransacoes] = useState(false);



    const handleClickApostas = () => {
        setApostas(!apostas);
        setTransacoes(false);
    };

    const handleClickTransacoes = () => {
        setTransacoes(!transacoes);
        setApostas(false);
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


            <Grid container spacing={2}>
                <Grid item xs={12} >
                    {apostas && <HistoricoApostas />}
                </Grid>
                <Grid item xs={12}  >
                    {transacoes && <HistoricoTransacoes />}
                </Grid>
            </Grid>

        </div>

    );
}

export default Historico;
