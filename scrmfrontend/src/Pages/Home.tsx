import { Typography } from "@mui/material";
import Grid from "@mui/material/Grid";
import { fetchCourses } from "../Store/Slices/CoursesSlice";
import { fetchStudents, studentsSlice } from "../Store/Slices/StudentsSlice";
import { useAppDispatch } from "../Store/store";

const Home = () => {
  const dispatch = useAppDispatch();
  const courses = dispatch(
    fetchCourses({
      field: "courseId",
      sortAscending: true,
      offset: 0,
      pageSize: 100,
    })
  );
  const students = dispatch(
    fetchStudents({
      field: "studentId",
      sortAscending: true,
      offset: 0,
      pageSize: 100,
    })
  );

  return (
    <Grid container item xs={12} justifyContent={"center"}>
      <Typography variant='h1'>HOME</Typography>
    </Grid>
  );
};

export default Home;
