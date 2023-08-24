import { createAsyncThunk, createSlice, PayloadAction } from "@reduxjs/toolkit";
import { Course } from "../../Data/Course";
import { Student } from "../../Data/Student";

interface StudentState {
  course: Course;
}

const initialState: StudentState = {
  course: {} as Course,
};

export const currentCourseSlice = createSlice({
  name: "course",
  initialState: initialState,
  reducers: {
    setCurrentCourse: (state, action: PayloadAction<Course>) => {
      state.course = action.payload;
      return state;
    },
  },
});

export const { setCurrentCourse } = currentCourseSlice.actions;
export default currentCourseSlice.reducer;
