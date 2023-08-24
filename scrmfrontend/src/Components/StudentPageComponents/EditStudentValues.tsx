import * as React from "react";
import Button from "@mui/material/Button";
import Dialog from "@mui/material/Dialog";
import Slide from "@mui/material/Slide";
import { TransitionProps } from "@mui/material/transitions";
import { Grid, Paper, TextField, Typography } from "@mui/material";
import IconButton from "@mui/material/IconButton";
import CloseIcon from "@mui/icons-material/Close";
import { useState } from "react";
import { useAppSelector } from "../../Store/store";
import { Student } from "../../Data/Student";
import { errorToast } from "../../Helper/Toasts";

const Transition = React.forwardRef(function Transition(
  props: TransitionProps & {
    children: React.ReactElement<any, any>;
  },
  ref: React.Ref<unknown>
) {
  return <Slide direction='up' ref={ref} {...props} />;
});

interface CreateStudentProps {
  student: Student;
  open: boolean;
  setOpen: React.Dispatch<React.SetStateAction<boolean>>;
  onSave: { (student: Student): void };
}

const EditStudentValues = (props: CreateStudentProps) => {
  const student = useAppSelector((state) => state.currentStudentState.student);
  const [firstName, setFirstName] = useState<string>("");
  const [lastName, setLastName] = useState<string>("");
  const [mail, setMail] = useState<string>("");
  const [subject, setSubject] = useState<string>("");

  const studentFieldsAreValid = (): boolean => {
    if (firstName === undefined || firstName.length < 2) {
      errorToast("First Name must have at least 2 Characters");
      return false;
    }
    if (lastName === undefined || lastName.length < 2) {
      errorToast("Last name must have at least 2 Characters");
      return false;
    }
    if (subject === undefined || subject.length < 2) {
      errorToast("Name of the subject must at lest have 2 Characters");
      return false;
    }
    return true;
  };

  return (
    <Dialog
      open={props.open}
      TransitionComponent={Transition}
      onClose={() => props.setOpen(false)}
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
                <Typography variant='h1'>Student</Typography>
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
              <Grid item xs={4} pr={1}>
                <Paper sx={{ p: 1 }}>First Name</Paper>
              </Grid>
              <Grid container item xs={8} alignItems={"center"}>
                <TextField
                  fullWidth
                  size='small'
                  id='outlined-required'
                  onChange={(e) => setFirstName(e.target.value)}
                  placeholder={student.firstName === undefined ? "Required" : student.firstName}
                />
              </Grid>
            </Grid>
          </Grid>
          <Grid container item direction='row' py={1}>
            <Grid container item xs={12} px={1}>
              <Grid item xs={4} pr={1}>
                <Paper sx={{ p: 1 }}>Last Name</Paper>
              </Grid>
              <Grid container item xs={8} alignItems={"center"}>
                <TextField
                  fullWidth
                  size='small'
                  required
                  id='outlined-required'
                  placeholder={student.lastName === undefined ? "Required" : student.lastName}
                  onChange={(e) => setLastName(e.target.value)}
                />
              </Grid>
            </Grid>
          </Grid>
          <Grid container item direction='row' py={1}>
            <Grid container item xs={12} px={1}>
              <Grid item xs={4} pr={1}>
                <Paper sx={{ p: 1 }}>Mail</Paper>
              </Grid>
              <Grid container item xs={8} alignItems={"center"}>
                <TextField
                  fullWidth
                  size='small'
                  required
                  id='outlined-required'
                  placeholder={student.mail === undefined ? "Required" : student.mail}
                  onChange={(e) => setMail(e.target.value)}
                />
              </Grid>
            </Grid>
          </Grid>
          <Grid container item direction='row' py={1}>
            <Grid container item xs={12} px={1}>
              <Grid item xs={4} pr={1}>
                <Paper sx={{ p: 1 }}>Subject</Paper>
              </Grid>
              <Grid container item xs={8} alignItems={"center"}>
                <TextField
                  fullWidth
                  size='small'
                  required
                  id='outlined-required'
                  placeholder={student.subject === undefined ? "Required" : student.subject}
                  onChange={(e) => setSubject(e.target.value)}
                />
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
                    if (studentFieldsAreValid()) {
                      props.onSave({
                        studentId: "",
                        firstName: firstName ?? "",
                        lastName: lastName ?? "",
                        mail: mail ?? "",
                        subject: subject ?? "",
                        courses: [],
                      });
                      props.setOpen(false);
                    }
                  }}
                >
                  <Typography color={"white"}> Save/Close</Typography>
                </Button>
              </Grid>
              <Grid item xs={6} pl={0.5}>
                <Button fullWidth variant='contained' color='error' onClick={() => props.setOpen(false)}>
                  <Typography color={"white"}> Abort</Typography>
                </Button>
              </Grid>
            </Grid>
          </Grid>
        </Paper>
      </Grid>
    </Dialog>
  );
};

export default EditStudentValues;
