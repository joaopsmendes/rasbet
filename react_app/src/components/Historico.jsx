import React, { useState, useEffect } from "react";
import Button from '@mui/material/Button';
import HistoricoApostas from "./HistoricoApostas";
import HistoricoTransacoes from "./HistoricoTransacao";
import Box from '@mui/material/Box';
import { Grid } from "@mui/material";


function Historico(props) {


    const [apostas, setApostas] = useState(false);
    const [transacoes, setTransacoes] = useState(false);


    const handleClickApostas = () => {
        setApostas(!apostas);
    };

    const handleClickTransacoes = () => {
        setTransacoes(!transacoes);
    }


    return (
        <div className="HistoricoApostas">
                <Button sx={{ margin: 2 }} variant="contained" color={apostas ? "primary" : "inherit"} onClick={handleClickApostas}>
                    Historico de Apostas
                </Button>
                <Button sx={{ margin: 2 }} variant="contained" color={transacoes ? "primary" : "inherit"} onClick={handleClickTransacoes}>
                    Historico de Transações
                </Button>
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
