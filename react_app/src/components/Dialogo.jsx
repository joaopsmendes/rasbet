import * as React from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import Box from '@mui/material/Box';
import { createTheme ,ThemeProvider} from '@mui/material/styles';

const theme = createTheme({
  palette: {
    custom: {
      light: '#ffa726',
      main: '#FFFFFF',
      dark: '#ef6c00',
      contrastText: 'rgba(0, 0, 0, 0.87)'
    }

}});



function Dialogo(props) {
  const [open, setOpen] = React.useState(false);

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  return (
    <ThemeProvider theme={theme}>
      <Button variant="text" color="custom" onClick={handleClickOpen}>
        {props.title}
      </Button>

      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>{props.title}</DialogTitle>
        <DialogContent>
            {props.form}
        </DialogContent>
      </Dialog>
  </ThemeProvider>

  );
}

export default Dialogo;