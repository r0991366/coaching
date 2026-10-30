'use client';

import { Lecturer } from "@types";
import LecturersOverviewTable from "@components/lecturers/LecturerOverviewTable";
import { useState } from "react";
import CourseOverviewTable from "@components/courses/CourseOverviewTable";
import LecturerService from "@services/LecturerService";

type Props = {
  lecturers: Lecturer[];
};

function LecturersClient({ lecturers }: Props) {
  const [selectedLecturer, setSelectedLecturer] = useState<Lecturer | null>(null);

  return (
    <>
      <section>
        <LecturersOverviewTable
          lecturers={lecturers}
          selectLecturer={setSelectedLecturer}
        />
      </section>

      {selectedLecturer && (
        <section className="mt-5">
          <h2 className="text-center">
            Courses taught by {selectedLecturer.user.firstName} {selectedLecturer.user.lastName}
          </h2>
          {selectedLecturer.courses && (
            <CourseOverviewTable lecturer={selectedLecturer} />
          )}
        </section>
      )}
    </>
  );
}

export default LecturersClient