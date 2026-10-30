'use client';
import CourseOverviewTable from "@components/courses/CourseOverviewTable";
import LecturerOverviewTable from "@components/lecturers/LecturerOverviewTable";
import LecturerService from "@services/LecturerService";
import { Lecturer } from "@types";
import { useState } from "react";


const getData = () => {
  return LecturerService.getAllLecturers();  //from the component, we need to call a function/method
};

const LecturersPage = () => {

  const [selectedLecturer, setSelectedLecturer] = useState<Lecturer | null>(null);

  return (
    <>
      <h1>Lecturers</h1>
      <>
        <section>
          {!getData().length && <p>No lecturers data.</p>}  
          <LecturerOverviewTable lecturers={getData()} selectLecturer={setSelectedLecturer} />
        </section>
        <section className="mt-5">
          <h2 className="text-center">Courses taught by {selectedLecturer?.user.firstName}:</h2>
          {selectedLecturer && (
          <CourseOverviewTable lecturer={selectedLecturer} />
          )}
        </section>
      </>
    </>
  );
};

export default LecturersPage;

