'use client';

import { useState } from 'react';
import { Lecturer } from '@types';
import LecturersOverviewTable from '@components/lecturers/LecturerOverviewTable';
import CourseOverviewTable from '@components/courses/CourseOverviewTable';

type Props = {
  initialLecturers: Lecturer[];
};

const LecturersClientPage = ({ initialLecturers }: Props) => {
  const [lecturers] = useState<Lecturer[]>(initialLecturers);
  const [selectedLecturer, setSelectedLecturer] = useState<Lecturer | null>(
    null
  );

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
            Courses taught by {selectedLecturer.user.firstName}
          </h2>
          {selectedLecturer.courses && (
            <CourseOverviewTable lecturer={selectedLecturer} />
          )}
        </section>
      )}
    </>
  );
};

export default LecturersClientPage;
