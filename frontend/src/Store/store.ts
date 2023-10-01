import { configureStore } from "@reduxjs/toolkit";
import { useDispatch } from "react-redux";
import { useSelector } from "react-redux/es/exports";
import { TypedUseSelectorHook } from "react-redux/es/types";
import StudentsSlice from "./Slices/StudentsSlice";
import CurrentStudentSlice from "./Slices/CurrentStudentSlice";
import FetchData from "./Slices/FetchData";
import CoursesSlice from "./Slices/CoursesSlice";
import CurrentCourseSlice from "./Slices/CurrentCourseSlice";

const store = configureStore({
  reducer: {
    studentsState: StudentsSlice,
    currentStudentState: CurrentStudentSlice,
    coursesState: CoursesSlice,
    currentCourseState: CurrentCourseSlice,
    fetchData: FetchData,
  },
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({
      serializableCheck: {
        ignoredActionPaths: ["payload"],
      },
    }),
});

export type RootState = ReturnType<typeof store.getState>;

export type AppDispatch = typeof store.dispatch;

export const useAppDispatch = () => useDispatch<AppDispatch>();

export const useAppSelector: TypedUseSelectorHook<RootState> = useSelector;

export default store;
