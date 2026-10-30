'use client';

import { Lecturer } from '@types';
import React from 'react';

type Props = {
  lecturer: Lecturer;
};

const CourseOverviewTable: React.FC<Props> = ({ lecturer }) => {
  return (
    <>
      {lecturer.courses && (
        <table className="mt-4">
          <thead>
            <tr>
              <th scope="col">Name</th>
              <th scope="col">Description</th>
              <th scope="col">Phase</th>
              <th scope="col">Credits</th>
            </tr>
          </thead>
          <tbody>
            {lecturer.courses.map((course, index) => (
              <tr key={index}>
                <td>{course.name}</td>
                <td>{course.description}</td>
                <td>{course.phase}</td>
                <td>{course.credits}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </>
  );
};

export default CourseOverviewTable;
