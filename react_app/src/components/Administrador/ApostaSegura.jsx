



import { useEffect, useState } from 'react';

import { Button, Container, Divider, Box, Grid, TextField } from '@mui/material';

function ApostaSegura() {

    const [limite, setLimite] = useState(10);




    const submit = async () => {
        const sessionId = JSON.parse(sessionStorage.getItem('sessionId'));
        const response = await fetch('http://localhost:8080/promoAposta', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                sessionId: sessionId,
                limite : limite
            }),
        });
    }


    return (

        <Grid container spacing={2}>
            <Grid item xs={12}>
                <TextField type="number" id="limite" label="limite" variant="outlined" onChange={(e) => setLimite(e.target.value)} 
                value={limite}
                error={limite <= 0}
                helperText={limite <= 0 ? 'Valor inválido' : ' '}
                InputProps={{ inputProps: { min: 0.01} }}/>
            </Grid>
            <Grid item xs={12}>
                <Button variant="contained" color="primary" sx={{ m: 2 }} onClick={submit} disabled={limite <= 0}>
                    Criar Promoção
                </Button>
            </Grid>
        </Grid>
    );
}

export default ApostaSegura;