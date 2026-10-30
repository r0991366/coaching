
import LecturerService from "@services/LecturerService";
import CourseService from "@services/CourseService";
import { Course, Lecturer } from "@types";
import { useState } from "react";

type PageProps = {
  params: Promise<{lecturerId: string; courseId: string;}>
};

type DataResult = {
  data: {lecturer: Lecturer; course: Course} | null;
  error: string | null
};

// fetch data for the page
// use LecturerService.getLecturerById and CourseService.getCourseById
const getData = async (lecturerId: string, courseId: string): Promise<DataResult> => {
  try {
    const lecturerRes = LecturerService.getLecturerById(lecturerId);
    const courseRes = CourseService.getCourseById(courseId);

    const[lecturer, course] = await Promise.all([lecturerRes, courseRes]);
    return {data: {lecturer, course}, error: null}; //DataResult
  } catch (errorRet) {
    return {data: null, error: (errorRet as Error).message}
  }
};

const CreateSchedulePage = async ({params}: PageProps) => {
  const paramsResolved = await params;
  const {data, error} = await getData(paramsResolved.lecturerId, paramsResolved.courseId)
  return (
    <>
      <h1>Create new schedule</h1>

      {error && (
        <div className="text-red-800" role="alert">
          {error}
        </div>
      )}

      {data && (
        <section>
          <p>Lecturer name: {data?.lecturer.user.firstName} {data.lecturer.user.firstName}</p>
          <p>Course name: {data?.course.name}</p>
        </section>
      )}
    </>
  );
};

export default CreateSchedulePage;
