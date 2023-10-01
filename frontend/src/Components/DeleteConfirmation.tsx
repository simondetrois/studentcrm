import * as React from "react";
import Button from "@mui/material/Button";
import Dialog from "@mui/material/Dialog";
import Slide from "@mui/material/Slide";
import { TransitionProps } from "@mui/material/transitions";
import { Grid, Paper, Typography } from "@mui/material";
import IconButton from "@mui/material/IconButton";
import CloseIcon from "@mui/icons-material/Close";

const Transition = React.forwardRef(function Transition(
  props: TransitionProps & {
    children: React.ReactElement<any, any>;
  },
  ref: React.Ref<unknown>
) {
  return <Slide direction='up' ref={ref} {...props} />;
});

interface DeleteConfirmationProps {
  open: boolean;
  setOpen: React.Dispatch<React.SetStateAction<boolean>>;
  onDelete: { (): void };
}

const DeleteConfirmation = (props: DeleteConfirmationProps) => {
  const handleClose = () => {
    props.setOpen(false);
  };

  return (
    <Dialog
      open={props.open}
      TransitionComponent={Transition}
      onClose={handleClose}
      aria-describedby='alert-dialog-slide-description'
      sx={{
        backdropFilter: props.open ? "blur(15px)" : 0,
      }}
    >
      <Grid container width={"500px"} direction={"column"} justifyContent={"space-between"}>
        <Paper>
          <Grid container item direction={"column"}>
            <Grid container px={2} py={1} item justifyContent={"space-between"} alignItems={"center"}>
              <Grid item>
                <Typography variant='h1'>Delete Student</Typography>
              </Grid>
              <Grid item>
                <IconButton
                  onClick={() => {
                    props.setOpen(false);
                  }}
                >
                  <CloseIcon />
                </IconButton>
              </Grid>
            </Grid>
          </Grid>
        </Paper>
        <Grid container item py={2}>
          <Grid container item direction='row' py={1}>
            <Grid container item xs={12} px={1}>
              <Grid item xs={12} pr={1}>
                <Paper sx={{ p: 1 }}>Are you sure you want to Delete this Student?</Paper>
              </Grid>
            </Grid>
          </Grid>
        </Grid>
        <Paper>
          <Grid container item direction={"column"}>
            <Grid container item p={1}>
              <Grid item xs={6} pr={0.5}>
                <Button
                  fullWidth
                  variant='contained'
                  color='success'
                  onClick={() => {
                    props.onDelete();
                    props.setOpen(false);
                  }}
                >
                  <Typography color={"white"}>Delete</Typography>
                </Button>
              </Grid>
              <Grid item xs={6} pl={0.5}>
                <Button fullWidth variant='contained' color='error' onClick={() => props.setOpen(false)}>
                  <Typography color={"white"}>Abort</Typography>
                </Button>
              </Grid>
            </Grid>
          </Grid>
        </Paper>
      </Grid>
    </Dialog>
  );
};

export default DeleteConfirmation;
