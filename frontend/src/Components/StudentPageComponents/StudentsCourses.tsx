import { Box, Button, Grid, Paper, Typography, useTheme } from "@mui/material";
import { useAppSelector } from "../../Store/store";

interface StudentsCoursesProps {
  selectedStudentIndex: number;
}

export const StudentsCourses = (props: StudentsCoursesProps) => {
  const student = useAppSelector((state) => state.currentStudentState.student);
  const theme = useTheme();
  return (
    <Box flex={1}>
      <Paper>
        <Grid container item xs={12} py={2} px={2} justifyContent='center'>
          <Paper elevation={5} sx={{ paddingY: 1, paddingX: 2, flex: 1 }}>
            <Grid container item justifyContent='center'>
              <Typography variant='h1'>
                {student.courses ? `${student.mail.slice(0, 4)}'s Courses` : "Students Courses"}
              </Typography>
            </Grid>
          </Paper>
        </Grid>
        {student.courses ? (
          <Grid container item xs={12} pb={2} px={2} justifyContent='center'>
            <Button fullWidth color='success' variant='contained'>
              Edit Courses
            </Button>
          </Grid>
        ) : (
          <></>
        )}
        <Grid container px={2} pb={2} item>
          {student.courses ? (
            student.courses.map((course, i) => (
              <Paper key={i}>
                <Grid
                  sx={{
                    background: theme.palette.primary.dark,
                    borderRadius: "10px",
                    "&:hover": {
                      boxShadow: 10,
                    },
                  }}
                  item
                  p={0.5}
                >
                  {course.courseName}
                </Grid>
              </Paper>
            ))
          ) : (
            <Paper>
              <Grid
                p={2}
                sx={{ backgroundColor: "grey", borderRadius: "10px", background: theme.palette.primary.dark }}
              >
                No Student is currently selected
              </Grid>
            </Paper>
          )}
        </Grid>
      </Paper>
    </Box>
  );
};
