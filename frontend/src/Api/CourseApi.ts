import axios from "axios";
import { getAllCoursesParams } from "../Data/CourseFetchParams";

const baseUrl = "http://localhost:8080/course-api/";

export const getAllCourses = async (getAllCoursesParams: getAllCoursesParams) =>
  await axios.get(
    `${baseUrl}courses/${getAllCoursesParams.sortAscending ? "asc" : "desc"}/${getAllCoursesParams.field}/${
      getAllCoursesParams.offset
    }/${getAllCoursesParams.pageSize}`
  );
