import { StudentWithoutCouses } from "./StudentWithoutCourses";

export interface Course {
  courseId: number;
  courseName: string;
  professor: string;
  students: StudentWithoutCouses[] | null;
}
