import { DataGrid, GridColDef, useGridApiRef, GridSelectionModel } from "@mui/x-data-grid";
import { useEffect, useState } from "react";
import { useAppDispatch, useAppSelector } from "../Store/store";
import { fetchStudents } from "../Store/Slices/StudentsSlice";
import { getAllStudentParams } from "../Data/StudentFetchParams";
import { ClickAwayListener, Grid, IconButton, Paper, Typography } from "@mui/material";
import { setCurrentStudent } from "../Store/Slices/CurrentStudentSlice";
import EditStudentValues from "./StudentPageComponents/EditStudentValues";
import { Student } from "../Data/Student";
import DeleteIcon from "@mui/icons-material/Delete";
import { deleteStudentBaId } from "../Api/StudentApi";
import { successToast } from "../Helper/Toasts";
import DeleteConfirmation from "./DeleteConfirmation";
import { setCurrentCourse } from "../Store/Slices/CurrentCourseSlice";
import { fetchCourses } from "../Store/Slices/CoursesSlice";
import { getAllCoursesParams } from "../Data/CourseFetchParams";

interface CourseTableProbs {
  setSelectedStudentIndex: React.Dispatch<React.SetStateAction<number>>;
}

function CourseTable(props: CourseTableProbs) {
  const dispatch = useAppDispatch();
  const courses = useAppSelector((state) => state.coursesState.courses);
  const fetchData = useAppSelector((state) => state.fetchData.fetchData);
  const currentCourse = useAppSelector((state) => state.currentCourseState.course);

  const [selectionModel, setSelectionModel] = useState<GridSelectionModel>([]);
  const [deleteStudentDialog, setDeleteStudentDialog] = useState<boolean>(false);
  const [openCreateStudentDialog, setOpenCreateStudentDialog] = useState<boolean>(false);
  const [urlParams, setUrlParams] = useState<getAllCoursesParams>({
    field: "courseId",
    sortAscending: true,
    offset: 0,
    pageSize: 10,
  });

  const onSave = (student: Student) => {};

  const deleteStudent = () => {
    dispatch(setCurrentStudent({} as Student));
    deleteStudentBaId(+currentCourse.courseId).then(() => {
      dispatch(fetchStudents(urlParams));
      successToast("Seccuessfully deletet Student");
    });
  };

  useEffect(() => {
    dispatch(fetchCourses(urlParams));
  }, [dispatch, urlParams, fetchData]);

  const columns: GridColDef[] = [
    {
      field: "courseId",
      renderHeader: () => <Typography>Course ID</Typography>,
      flex: 1,
      sortable: false,
    },
    {
      field: "courseName",
      renderHeader: () => <Typography>Course Name</Typography>,
      flex: 1,
      sortable: false,
    },
    {
      field: "professor",
      renderHeader: () => <Typography>Professor</Typography>,
      flex: 1,
      sortable: false,
    },
    {
      field: "na",
      renderHeader: () => "",
      flex: 0.1,
      renderCell: () => (
        <IconButton onClick={() => setDeleteStudentDialog(true)}>
          <DeleteIcon
            fontSize='small'
            sx={{
              "&:hover": {
                color: "red",
              },
            }}
          />
        </IconButton>
      ),
    },
  ];

  return (
    <ClickAwayListener
      onClickAway={() => {
        setSelectionModel({} as GridSelectionModel);
        dispatch(setCurrentStudent({} as Student));
      }}
    >
      <Grid item xs={12}>
        <Paper
          sx={{
            "&:hover": {
              boxShadow: 20,
            },
          }}
        >
          <DataGrid
            getRowId={(row) => row.courseId}
            autoHeight
            rows={courses}
            columns={columns}
            rowsPerPageOptions={[10, 20, 30, 100]}
            disableColumnMenu
            onSelectionModelChange={(newRowSelectionModel) => {
              setSelectionModel(newRowSelectionModel);
            }}
            selectionModel={selectionModel}
            onRowClick={(e) => {
              let currentCourseId: number = e.row.studentId;
              let indexOfCurrentCourse: number = courses.findIndex((course) => +course.courseId === currentCourseId);
              dispatch(setCurrentCourse(courses[indexOfCurrentCourse]));
            }}
            onRowDoubleClick={(e) => {
              setOpenCreateStudentDialog(true);
            }}
            sx={{
              "&:hover": {
                cursor: "pointer",
              },
              "& .MuiDataGrid-cell:focus": {
                outline: "none",
              },
            }}
          />
        </Paper>
        {/*<EditStudentValues
          open={openCreateStudentDialog}
          setOpen={setOpenCreateStudentDialog}
          onSave={onSave}
          student={currentStudent}
        />
        */}
        <DeleteConfirmation open={deleteStudentDialog} setOpen={setDeleteStudentDialog} onDelete={deleteStudent} />
      </Grid>
    </ClickAwayListener>
  );
}

export default CourseTable;
