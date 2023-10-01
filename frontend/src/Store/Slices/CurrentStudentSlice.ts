import { createAsyncThunk, createSlice, PayloadAction } from "@reduxjs/toolkit";
import { Student } from "../../Data/Student";

interface StudentState {
  student: Student;
}

const initialState: StudentState = {
  student: {} as Student,
};

export const currentStudentSlice = createSlice({
  name: "students",
  initialState: initialState,
  reducers: {
    setCurrentStudent: (state, action: PayloadAction<Student>) => {
      state.student = action.payload;
      return state;
    },
  },
});

export const { setCurrentStudent } = currentStudentSlice.actions;
export default currentStudentSlice.reducer;
