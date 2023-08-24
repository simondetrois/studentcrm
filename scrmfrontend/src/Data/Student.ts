import { CourseWithoutStudents } from "./CourseWithoutStudents";

export interface Student {
  studentId: string;
  firstName: string;
  lastName: string;
  mail: string;
  subject: string;
  courses: CourseWithoutStudents[] | null;
}
