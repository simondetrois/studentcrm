import { Grid, Typography } from "@mui/material";
import { useState } from "react";
import CourseTable from "../Components/CourseTable";
import Options from "../Components/Options";
import { StudentsCourses } from "../Components/StudentPageComponents/StudentsCourses";
import StudentTable from "../Components/StudentPageComponents/StudentTable";

const Courses = () => {
  const [courseIndex, setCourseIndex] = useState<number>(-1);

  return (
    <Grid container item xs={12}>
      <Grid item xs={2} pl={2} pr={1}>
        <Options />
      </Grid>
      <Grid container item xs={8} px={1}>
        <CourseTable setSelectedStudentIndex={setCourseIndex} />
      </Grid>
      <Grid item xs={2} pl={1} pr={2}>
        <StudentsCourses selectedStudentIndex={courseIndex} />
      </Grid>
    </Grid>
  );
};

export default Courses;
