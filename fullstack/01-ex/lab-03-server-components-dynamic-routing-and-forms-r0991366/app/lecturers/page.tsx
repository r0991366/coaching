import { Lecturer } from "@types";
import LecturersOverviewTable from "@components/lecturers/LecturerOverviewTable";
import { useState } from "react";
import CourseOverviewTable from "@components/courses/CourseOverviewTable";
import LecturerService from "@services/LecturerService";
import LecturersClient from "./LecturerClient";

type DataResult = {
  data: Lecturer[] | null;
  error: string | null;
};

const getData = async (): Promise<DataResult> => {
  try {
    const lecturers = await LecturerService.getAllLecturers();

    return { data: lecturers, error: null };
  } catch (error) {
    return { data: null, error: (error as Error).message };
  }
};

const LecturersPage = async () => {
  const { data, error } = await getData();

  return (
    <>
      <h1>Lecturers</h1>
      {error && (
        <div className="text-red-800" role="alert">
          {error}
        </div>
      )}
      {!data?.length && <div>No lecturers found.</div>}

      {data?.length ? <LecturersClient lecturers={data} /> : null}
      </>
  );
};

export default LecturersPage;
