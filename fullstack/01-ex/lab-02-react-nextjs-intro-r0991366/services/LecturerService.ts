import { Lecturer } from '@types';

const getAllLecturers = (): Lecturer[] => {
  return hardcodedLecturersData;
};

const LecturerService = {    //this is the weird thing
  getAllLecturers,            //the service object holds the method getAllLecturers
};

export default LecturerService; //exporting the service object so we can import it somewhere else

const hardcodedLecturersData = [
  {
    id: 1,
    user: {
      firstName: 'John',
      lastName: 'Doe',
      email: 'john.doe@ucll.be',
      username: 'johndoe',
      role: 'lecturer',
    },
    expertise: 'Web Development',
    courses: [
      {
        id: 1,
        name: 'Web Development',
        description: 'Learn to build web applications',
        phase: 1,
        credits: 6,
      },
      {
        id: 2,
        name: 'Advanced JavaScript',
        description: 'Deep dive into JavaScript',
        phase: 2,
        credits: 6,
      },
    ],
  },
  {
    id: 2,
    user: {
      firstName: 'Jane',
      lastName: 'Smith',
      email: 'jane.smith@ucll.be',
      username: 'janesmith',
      role: 'lecturer',
    },
    expertise: 'Data Science',
    courses: [
      {
        id: 3,
        name: 'Data Science 101',
        description: 'Introduction to Data Science',
        phase: 1,
        credits: 6,
      },
      {
        id: 4,
        name: 'Machine Learning',
        description: 'Basics of Machine Learning',
        phase: 2,
        credits: 6,
      },
    ],
  },
];
