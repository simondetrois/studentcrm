import axios from "axios";
import { Student } from "../Data/Student";
import { getAllStudentParams } from "../Data/StudentFetchParams";

const baseUrl = "http://localhost:8080/student-api";

export const createStudent = async (student: Student) => await axios.post(`${baseUrl}/student`, student);

export const deleteStudentBaId = async (studentId: number) => await axios.delete(`${baseUrl}/student/${studentId}`);

export const getAllStudents = async (getAllStudentParams: getAllStudentParams) =>
  await axios.get(
    `${baseUrl}/students/${getAllStudentParams.sortAscending ? "asc" : "desc"}/${getAllStudentParams.field}/${
      getAllStudentParams.offset
    }/${getAllStudentParams.pageSize}`
  );

export const updateStudent = async (student: Student) => await axios.put(`${baseUrl}/student`, student);
