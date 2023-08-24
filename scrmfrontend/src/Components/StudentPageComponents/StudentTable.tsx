import { DataGrid, GridColDef, useGridApiRef, GridSelectionModel } from "@mui/x-data-grid";
import { useEffect, useState } from "react";
import { useAppDispatch, useAppSelector } from "../../Store/store";
import { fetchStudents } from "../../Store/Slices/StudentsSlice";
import { getAllStudentParams } from "../../Data/StudentFetchParams";
import { ClickAwayListener, Grid, IconButton, Paper, Popover, Typography } from "@mui/material";
import { setCurrentStudent } from "../../Store/Slices/CurrentStudentSlice";
import EditStudentValues from "./EditStudentValues";
import { Student } from "../../Data/Student";
import DeleteIcon from "@mui/icons-material/Delete";
import { deleteStudentBaId } from "../../Api/StudentApi";
import { successToast } from "../../Helper/Toasts";
import DeleteConfirmation from "../DeleteConfirmation";
import { PopOverMenu } from "../PopOverMenu";
import MoreVertIcon from "@mui/icons-material/MoreVert";
import ArrowLeftIcon from "@mui/icons-material/ArrowLeft";
import ArrowDropDownIcon from "@mui/icons-material/ArrowDropDown";

interface StudentTableProbs {
  setSelectedStudentIndex: React.Dispatch<React.SetStateAction<number>>;
}

function StudentTable(props: StudentTableProbs) {
  const dispatch = useAppDispatch();
  const students = useAppSelector((state) => state.studentsState.students);
  const fetchStudent = useAppSelector((state) => state.fetchData.fetchData);
  const currentStudent = useAppSelector((state) => state.currentStudentState.student);
  const [anchorEl, setAnchorEl] = useState<any>(null);

  const [selectionModel, setSelectionModel] = useState<GridSelectionModel>([]);
  const [deleteStudentDialog, setDeleteStudentDialog] = useState<boolean>(false);
  const [openCreateStudentDialog, setOpenCreateStudentDialog] = useState<boolean>(false);
  const [openCreateStudentsCoursesDialog, setOpenCreateStudentsCoursesDialog] = useState<boolean>(false);
  const [urlParams, setUrlParams] = useState<getAllStudentParams>({
    field: "studentId",
    sortAscending: true,
    offset: 0,
    pageSize: 10,
  });

  const onSave = (student: Student) => {};

  const deleteStudent = () => {
    dispatch(setCurrentStudent({} as Student));
    deleteStudentBaId(+currentStudent.studentId).then(() => {
      dispatch(fetchStudents(urlParams));
      successToast("Seccuessfully deletet Student");
    });
  };

  const onClose = () => {
    setAnchorEl(null);
  };

  useEffect(() => {
    dispatch(fetchStudents(urlParams));
  }, [dispatch, urlParams, fetchStudent]);

  const columns: GridColDef[] = [
    {
      field: "studentId",
      renderHeader: () => <Typography>Student ID</Typography>,
      flex: 1,
      sortable: false,
    },
    {
      field: "firstName",
      renderHeader: () => <Typography>First Name</Typography>,
      flex: 1,
      sortable: false,
    },
    {
      field: "lastName",
      renderHeader: () => <Typography>Last Name</Typography>,
      flex: 1,
      sortable: false,
    },
    {
      field: "mail",
      renderHeader: () => <Typography>Mail</Typography>,
      flex: 1,
      sortable: false,
    },
    {
      field: "subject",
      renderHeader: () => <Typography>Subject</Typography>,
      flex: 1,
      sortable: false,
    },
    {
      field: "yomama",
      renderHeader: () => "",
      flex: 0.1,
      renderCell: () => (
        <IconButton onClick={(e) => setAnchorEl(e.target)}>
          <MoreVertIcon
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
        //& setAnchorEl(null);
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
            getRowId={(row) => row.studentId}
            autoHeight
            rows={students}
            columns={columns}
            rowsPerPageOptions={[10, 20, 30, 100]}
            //  disableSelectionOnClick
            disableColumnMenu
            onSelectionModelChange={(newRowSelectionModel) => {
              setSelectionModel(newRowSelectionModel);
            }}
            selectionModel={selectionModel}
            onRowClick={(e) => {
              let currentStudentId: number = e.row.studentId;
              let indexOfCurrentStudent: number = students.findIndex(
                (student) => +student.studentId === currentStudentId
              );
              dispatch(setCurrentStudent(students[indexOfCurrentStudent]));
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
        <EditStudentValues
          open={openCreateStudentDialog}
          setOpen={setOpenCreateStudentDialog}
          onSave={onSave}
          student={currentStudent}
        />
        <DeleteConfirmation open={deleteStudentDialog} setOpen={setDeleteStudentDialog} onDelete={deleteStudent} />
        <PopOverMenu
          anchorElement={anchorEl}
          editStudent={setOpenCreateStudentDialog}
          editStudentsCourses={setOpenCreateStudentsCoursesDialog}
          deleteStudent={setDeleteStudentDialog}
          open={Boolean(anchorEl)}
          onClose={onClose}
        />
      </Grid>
    </ClickAwayListener>
  );
}

export default StudentTable;
