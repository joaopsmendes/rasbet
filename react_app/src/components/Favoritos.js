import {useEffect, useState} from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import Box from '@mui/material/Box';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import Stack from '@mui/material/Stack';
import TextField from '@mui/material/TextField';
import CloseIcon from '@mui/icons-material/Close';
import IconButton from '@mui/material/IconButton';

function Favoritos() {
    const [open, setOpen] = useState(false);
    const [fav, setFav] = useState(false);
    
    const handleClickOpen = () => {
        setOpen(true);
      };
    
    const handleClose = () => {
        setOpen(false);
      };

      const getFavoritos = async (email) => {
        console.log(email)
        const response = await fetch('http://localhost:8080/getFavorites?' + new URLSearchParams({
          userId: email
        })
          , {
            method: 'GET',
          });
    
        console.log("Favoritos");
        let data = await response.json();
        console.log(data);
        setFav(data['fav']);
      }

      useEffect(() => {
        const email = sessionStorage.getItem('email');
        getFavoritos(email);
      }, []);



    return(
        <div className="Favoritos">
            <Button variant="text" onClick={handleClickOpen}>
            Favoritos
            </Button>
            <Dialog open={open} onClose={handleClose}>
                <IconButton onClick={handleClose}>
                    <CloseIcon />
                </IconButton>
            <DialogTitle>Favoritos</DialogTitle>
            <DialogContent>
            </DialogContent>
            </Dialog>
        </div>

    );
}

export default Favoritos;