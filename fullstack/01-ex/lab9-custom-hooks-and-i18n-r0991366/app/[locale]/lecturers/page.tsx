import { Lecturer } from '@types';
import LecturerService from '@services/LecturerService';
import LecturersClientPage from './LecturersClientPage';
import { cookies } from 'next/headers';

type DataResult = {
  data: Lecturer[] | null;
  error: string | null;
};

const getData = async (): Promise<DataResult> => {
  try {
    const cookieStore = await cookies();
    const lecturers = await LecturerService.getAllLecturers(cookieStore);

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

      {data && <LecturersClientPage initialLecturers={data} />}
    </>
  );
};

export default LecturersPage;