import { createAsyncThunk, createSlice, PayloadAction } from "@reduxjs/toolkit";
import { Course } from "../../Data/Course";
import { getAllCourses } from "../../Api/CourseApi";

interface CourseState {
  courses: Course[];
}

const initialState: CourseState = {
  courses: [] as Course[],
};

// asyncThunks for ExtraReducers
export const fetchCourses = createAsyncThunk("course/fetch", getAllCourses);

export const coursesSlice = createSlice({
  name: "courses",
  initialState: initialState,
  reducers: {
    setCourses: (state, action: PayloadAction<Course[]>) => {
      state.courses = action.payload;
      return state;
    },
  },
  extraReducers: (builder) => {
    builder.addCase(fetchCourses.fulfilled, (state, action) => {
      state.courses = action.payload.data;
    });
  },
});

export const { setCourses } = coursesSlice.actions;
export default coursesSlice.reducer;
