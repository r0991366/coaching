import { Lecturer } from '@types';
import { ReadonlyRequestCookies } from 'next/dist/server/web/spec-extension/adapters/request-cookies';

const getAllLecturers = async (
  cookies: ReadonlyRequestCookies
): Promise<Lecturer[]> => {
  const token = cookies.get('authToken')?.value;
  const response = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/lecturers`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    },
    cache: 'no-store',
    credentials: 'include',
  });

  if (!response.ok) {
    const error = await response.json().catch(() => ({}));
    throw new Error(
      error?.message ||
        'You are not authorized to view this content. Please login first.'
    );
  }

  return response.json();
};

const LecturerService = {
  getAllLecturers,
};

export default LecturerService;
