
import { useEffect, useState } from 'react';

import { Button, Container, Divider, Box, Grid, TextField } from '@mui/material';
import ResponsiveAppBar from '../Appbar';
import { Paper, Stack } from '@mui/material';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';

function CriarPromocao(props) {

    const [tipo, setTipo] = useState('');




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

                {tipo === "FreebetsAposDeposito" &&
                    <Grid container spacing={2}>
                        <Grid item xs={12} md={6}>
                            <TextField id="freebets" label="freebets" variant="outlined" />
                        </Grid>
                        <Grid item xs={12} md={6}>
                            <TextField id="deposito" label="deposito" variant="outlined" />
                        </Grid>
                        <Grid item xs={12}>
                            <Button variant="contained" color="primary" sx={{ m: 2 }} >
                                Criar Promoção
                            </Button>
                        </Grid>
                    </Grid>
                }
                {tipo === "apostaSegura" &&
                    <Grid container spacing={2}>
                        <Grid item xs={12}>
                            <TextField id="limite" label="limite" variant="outlined" />
                        </Grid>
                        <Grid item xs={12}>
                            <Button variant="contained" color="primary" sx={{ m: 2 }} >
                                Criar Promoção
                            </Button>
                        </Grid>
                    </Grid>
                }
            </Box>
        </div>
    );
}

export default CriarPromocao;