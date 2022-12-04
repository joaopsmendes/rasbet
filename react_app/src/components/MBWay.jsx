import * as React from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import TextField from '@mui/material/TextField';
import CloseIcon from '@mui/icons-material/Close';
import IconButton from '@mui/material/IconButton';
import Grid from '@mui/material/Grid';


function MBWAY(props) {

    const [open, setOpen] = React.useState(true);
    const [confirm, setConfirm] = React.useState(false);


    const handleClose = () => {
        setOpen(false);
        props.close();
    };


    const handleConfirm = () => {
        setConfirm(true);
        setOpen(false);
        props.submit();
    };


    React.useEffect(() => {
        console.log("USE EFFECT");
        setOpen(props.mbway);
    }, [props.mbway]);


    return (
        <div>
            <Dialog open={open} onClose={handleClose}>
                <DialogTitle>PAGAMENTO</DialogTitle>
                <DialogContent>
                    {/** icon da mbway */}
                    <Grid container spacing={2}>
                        <Grid item xs={3} >
                            <TextField margin="normal" name="Email" id="Email"
                                type="text"
                                defaultValue="+351"
                                value="+351"
                            />
                        </Grid>
                        <Grid item xs={9} >
                            <TextField margin="normal" name="Email" id="Email" label="telemovel"
                                type="text"
                            />
                        </Grid>
                    </Grid>
                    <Button variant="submit" onClick={handleConfirm}>PAGAR</Button>
                    <Button variant="Contained" onClick={handleClose}>CANCELAR</Button>
                    <h6> Será enviada uma notificação para o seu telemóvel. </h6>
                    <h6> Aceite a transação com o seu PIN MB WAY dentro do tempo limite indicado </h6>
                </DialogContent>
            </Dialog>
        </div>


    );

}

export default MBWAY;