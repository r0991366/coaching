'use client';

import React from 'react';
import { Lecturer } from '@types';

type Props = {
  lecturer: Lecturer;   //we are talking about courses from a single lecturer
                        //could've also used the array of courses here already butttt lets not
};

const CourseOverviewTable: React.FC<Props> = ({lecturer}: Props) => {
  return (
    <>
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
          <tr>
            <td>{course.name}</td>
            <td>{course.description}</td>
            <td>{course.phase}</td>
            <td>{course.credits}</td>
          </tr>
          ))}
        </tbody>
      </table>
    </>
  );
};

export default CourseOverviewTable;
