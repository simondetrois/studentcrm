import { Box, Button, Grid, Paper, Switch, TextField, Typography } from "@mui/material";
import { useState } from "react";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { createStudent } from "../Api/StudentApi";
import { Student } from "../Data/Student";
import { successToast } from "../Helper/Toasts";
import { setCurrentStudent } from "../Store/Slices/CurrentStudentSlice";
import { setFetchData } from "../Store/Slices/FetchData";
import { useAppDispatch, useAppSelector } from "../Store/store";
import EditStudentValues from "./StudentPageComponents/EditStudentValues";

const Options = () => {
  const dispatch = useAppDispatch();
  const fetchStudents = useAppSelector((state) => state.fetchData.fetchData);
  const [openCreateStudentDialog, setOpenCreateStudentDialog] = useState<boolean>(false);

  const onSave = (student: Student) => {
    createStudent(student).then(() => {
      dispatch(setFetchData(!fetchStudents));
      successToast(`Student ${student.mail.slice(0, 4)} has been created`);
    });
  };

  return (
    <Box flex={1}>
      <Paper>
        <Grid container item xs={12} pt={2} px={2} justifyContent='center'>
          <Paper elevation={5} sx={{ paddingY: 1, paddingX: 2, flex: 1 }}>
            <Grid container item justifyContent='center'>
              <Typography variant='h1'>Options</Typography>
            </Grid>
          </Paper>
        </Grid>

        <Grid container item xs={12}>
          <Grid item xs={12} px={2} py={1} flex={1}>
            <TextField
              fullWidth
              margin='dense'
              id='filled-search'
              label='Search Student'
              type='search'
              variant='filled'
              size='small'
            />
          </Grid>
        </Grid>

        <Grid
          container
          item
          xs={12}
          py={1}
          justifyContent={"space-between"}
          alignItems={"center"}
          px={2}
          direction='row'
        >
          <Grid item>
            <Typography>Display Student-Card</Typography>
          </Grid>
          <Grid item alignItems={"center"}>
            <Switch
              color='default'
              checked={true}
              onChange={() => {
                toast.error("fuck");
              }}
              inputProps={{ "aria-label": "controlled" }}
            />
          </Grid>
        </Grid>
        <Grid item xs={12} justifyContent={"center"} px={2} pb={2}>
          <Button
            fullWidth
            color='success'
            variant={"contained"}
            onClick={() => {
              dispatch(setCurrentStudent({} as Student));
              setOpenCreateStudentDialog(true);
            }}
          >
            <Typography color='white'>Add Student</Typography>
          </Button>
        </Grid>
        <EditStudentValues
          open={openCreateStudentDialog}
          setOpen={setOpenCreateStudentDialog}
          onSave={onSave}
          student={{} as Student}
        />
      </Paper>
    </Box>
  );
};

export default Options;
