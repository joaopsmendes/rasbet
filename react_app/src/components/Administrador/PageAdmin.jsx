
import { useEffect, useState } from 'react';

import { Button, Container, Divider, Box, Grid } from '@mui/material';
import ResponsiveAppBar from '../Appbar';
import { Paper, Stack } from '@mui/material';
import CriarPromocao from './CriarPromocao';


function PageAdmin(props) {


    const [promocao, setPromocao] = useState(false);
    const [historico, setHistorico] = useState(false); 

    const settingsOptions = {
        'Terminar Sessão': props.loggout
    }


    const handlePromocao = () => {
        setPromocao(!promocao);
        setHistorico(false);
    }

    const handleHistorico = () => {
        setHistorico(!historico);
        setPromocao(false);
    }


    return (
        <div className="App">
            <ResponsiveAppBar
                showDesportos={false}
                settings={settingsOptions}
                isLogin={props.isLogin}
                showFavoritos={false}
            />

            <Container maxWidth="md" style={{ marginTop: "5vh" }}>
                <Grid container spacing={2}>
                    <Grid item xs={12} md={6}>
                        <Button fullWidth variant="contained" color={promocao ? "primary" : "inherit"} style={{ padding: 10 }} onClick={handlePromocao}>
                            Criar Promoção
                        </Button>
                    </Grid>
                    <Grid item xs={12} md={6}>
                        <Button fullWidth variant="contained" color={historico ? "primary" : "inherit"} style={{ padding: 10 }} onClick={handleHistorico} >
                            Histórico de Promoções
                        </Button>
                    </Grid>
                </Grid>
                {promocao && <CriarPromocao />}
            </Container>



        </div >
    );
}

export default PageAdmin;