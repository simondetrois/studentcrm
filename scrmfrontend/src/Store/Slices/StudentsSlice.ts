import { createAsyncThunk, createSlice, PayloadAction } from "@reduxjs/toolkit";
import { Student } from "../../Data/Student";
import { getAllStudents } from "../../Api/StudentApi";

interface StudentState {
  students: Student[];
}

const initialState: StudentState = {
  students: [] as Student[],
};

// asyncThunks for ExtraReducers
export const fetchStudents = createAsyncThunk("person/fetch", getAllStudents);

export const studentsSlice = createSlice({
  name: "students",
  initialState: initialState,
  reducers: {
    setStudents: (state, action: PayloadAction<Student[]>) => {
      state.students = action.payload;
      return state;
    },
  },
  extraReducers: (builder) => {
    builder.addCase(fetchStudents.fulfilled, (state, action) => {
      state.students = action.payload.data;
    });
  },
});

export const { setStudents } = studentsSlice.actions;
export default studentsSlice.reducer;
