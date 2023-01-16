
import { useEffect, useState } from 'react';

import { Button, Container, Divider, Box, Grid, TextField } from '@mui/material';
import ResponsiveAppBar from '../Appbar';
import { Paper, Stack } from '@mui/material';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';
import ApostaSegura from './ApostaSegura';
import FreebetsAposDeposito from './FreebetsAposDeposito';

function CriarPromocao(props) {

    const [tipo, setTipo] = useState('');
    const [limite,setLimite] = useState(0);
    const [freebets,setFreebets] = useState (0);
    const [deposito,setDeposito] = useState (0);




    const handleChangeTipo = (event) => {
        setTipo(event.target.value);
    }

    return (
        <div>


            <Box sx={{ flexGrow: 1, border: 1, borderRadius: "10px", m: 5 }} >
                <Grid container spacing={2}>
                    <Grid item xs={12} >
                        <FormControl sx={{ m: 5, minWidth: 200 }}>
                            <InputLabel id="demo-simple-select-label">Tipo De Promoção</InputLabel>
                            <Select
                                defaultValue='None'
                                labelId="demo-simple-select-label"
                                color="primary"
                                id="demo-simple-select"
                                value={tipo}
                                label="Tipo De Promoção"
                                onChange={handleChangeTipo}
                                sx={{
                                    "&.Mui-selected,": {
                                        color: "#000000",
                                    },
                                }}
                            >
                                <MenuItem value="FreebetsAposDeposito" >FreebetsAposDeposito</MenuItem>
                                <MenuItem value="apostaSegura" >apostaSegura</MenuItem>
                            </Select>
                        </FormControl>

                    </Grid>
                </Grid>

                {tipo === "FreebetsAposDeposito" && <FreebetsAposDeposito />}
                {tipo === "apostaSegura" && <ApostaSegura />}
            </Box>
        </div>
    );
}

export default CriarPromocao;