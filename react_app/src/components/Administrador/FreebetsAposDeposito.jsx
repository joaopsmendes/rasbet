import { useEffect, useState } from 'react';

import { Button, Container, Divider, Box, Grid, TextField } from '@mui/material';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';

function FreebetsAposDeposito() {

    const [freebets, setFreebets] = useState(1);
    const [deposito, setDeposito] = useState(10);




    const submit = async () => {
        const sessionId = JSON.parse(sessionStorage.getItem('sessionId'));
        const response = await fetch('http://localhost:8080/promoDeposito', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                sessionId: sessionId,
                deposito: deposito,
                freebets: freebets
            }),
        });
    }



    return (
        <Grid container spacing={2}>
            <Grid item xs={12} md={6}>
                <TextField type="number" id="freebets" label="freebets" variant="outlined" onChange={(e) => setFreebets(e.target.value)}
                    value={freebets}
                    error={freebets <= 0}
                    helperText={freebets <= 0 ? 'Valor inválido' : ' '}
                    InputProps={{ inputProps: { min: 0.01 } }}
                />
            </Grid>
            <Grid item xs={12} md={6}>
                <TextField type="number" id="deposito" label="deposito" variant="outlined" onChange={(e) => setDeposito(e.target.value)}
                    value={deposito}
                    error={deposito <= 0}
                    helperText={deposito <= 0 ? 'Valor inválido' : ' '}
                    InputProps={{ inputProps: { min: 0.01 } }}
                />
            </Grid>
            <Grid item xs={12}>
                <Button variant="contained" color="primary" sx={{ m: 2 }} onClick={submit} disabled={freebets <= 0 || deposito <= 0} >
                    Criar Promoção
                </Button>
            </Grid>
        </Grid>
    );
}

export default FreebetsAposDeposito;