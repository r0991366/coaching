import { Lecturer } from '@types';
import { cookies } from 'next/headers';



// const getAllLecturers = async () => {
//   const cookieStore = cookies();
//   const token = (await cookieStore).get("authToken")?.value;

//   const response = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/lecturers`, {
//     headers: {
//       Authorization: `Bearer ${token}`,
//     },
//   });

//   return response.json();
// };

const getAllLecturers = async (cookieStore: ReturnType<typeof cookies>): Promise<Lecturer[]> => {
  const authToken = (await cookieStore).get("authToken")?.value;

  const response = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/lecturers`, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      // include the token manually in the Authorization header
      ...(authToken ? { Authorization: `Bearer ${authToken}` } : {}),
    },
    cache: "no-store",
  });

  if (!response.ok) {
    const error = await response.text().catch(() => "Error");
    throw new Error(error);
  }

  return response.json();
};


const LecturerService = {
  getAllLecturers,
};

export default LecturerService;

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
