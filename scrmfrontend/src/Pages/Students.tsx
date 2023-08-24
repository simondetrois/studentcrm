import { Button, CssBaseline, Grid, Typography } from "@mui/material";
import { useState } from "react";
import { toast, ToastContainer } from "react-toastify";

import Options from "../Components/Options";
import { StudentsCourses } from "../Components/StudentPageComponents/StudentsCourses";
import StudentTable from "../Components/StudentPageComponents/StudentTable";

const Students = () => {
  const [selectedStudentIndex, setSelectedStudentIndex] = useState<number>(-1);

  return (
    <Grid container item xs={12}>
      <Grid item xs={2} pl={2} pr={1}>
        <Options />
      </Grid>
      <Grid container item xs={8} px={1}>
        <StudentTable setSelectedStudentIndex={setSelectedStudentIndex} />
      </Grid>
      <Grid item xs={2} pl={1} pr={2}>
        <StudentsCourses selectedStudentIndex={selectedStudentIndex} />
      </Grid>
    </Grid>
  );
};

export default Students;
