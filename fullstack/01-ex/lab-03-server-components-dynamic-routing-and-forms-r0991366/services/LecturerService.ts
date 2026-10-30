import { Lecturer } from "@types";

const getAllLecturers = async (): Promise<Lecturer[]> => {
  const response = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/lecturers`, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
    cache: "no-store",
    credentials: "include",
  });

  if (!response.ok) {
    const error = await response.json().catch(() => ({}));
    throw new Error(error?.message || "No lecturers found");
  }

  return response.json();
};

const getLecturerById = async (lecturerId: string): Promise<Lecturer> => {
  const response = await fetch(
    `${process.env.NEXT_PUBLIC_API_URL}/lecturers/${lecturerId}`,
    {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },
      cache: "no-store",
    }
  );

  if (!response.ok) {
    const error = await response.json().catch(() => ({}));
    throw new Error(
      error?.message || `Failed to fetch lecturer with id ${lecturerId}`
    );
  }

  return response.json();
};

const LecturerService = {
  getAllLecturers,
  getLecturerById,
};

export default LecturerService;
