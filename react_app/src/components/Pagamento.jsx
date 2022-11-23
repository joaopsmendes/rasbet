import * as React from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import Box from '@mui/material/Box';
import { createTheme ,ThemeProvider} from '@mui/material/styles';

import Stack from '@mui/material/Stack';




function Pagamento(props) {
    const [open, setOpen] = React.useState(false);



    const handleClickOpen = () => {
        setOpen(true);
      };
    
      const handleClose = () => {
        setOpen(false);
      };

    
    
  return (
    <div>
      <Button variant="text" onClick={handleClickOpen}>
        Pagamento
      </Button>
      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>PAGAMENTO</DialogTitle>
        <DialogContent>
            MÉTODO DE PAGAMENTO:
                <Button variant="Contained">MULTIBANCO</Button>
                <Button variant="Contained">MBWAY</Button>
                <Button variant="Contained">VISA</Button>
        </DialogContent>
      </Dialog>
</div>
  );
}

export default Pagamento;

