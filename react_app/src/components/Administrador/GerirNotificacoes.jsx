import React, { useState, useEffect } from "react";
import Button from '@mui/material/Button';
import Stack from '@mui/material/Stack';
import IconButton from '@mui/material/IconButton';
import Fab from '@mui/material/Fab';
import AddIcon from '@mui/icons-material/Add';
import Container from '@mui/material/Container';
import Paper from '@mui/material/Paper';
import ToggleButtonGroup from '@mui/material/ToggleButtonGroup';
import { Divider, ToggleButton } from "@mui/material";
import Apostas from "./Apostas";
import Box from '@mui/material/Box';

function GerirNotificacoes(props) {
    
    return(
        <div>
            <Box>
                <TextField
                margin="normal"
                required
                fullWidth
                //size="sm"
                type="text"
                placeholder="Escreva a sua promoção de seguida..."                
                />
                <Button variant="Contained" onClick={props.submit}>ENVIAR!</Button>
            </Box>
        </div>
    );
}
export default GerirNotificacoes;
