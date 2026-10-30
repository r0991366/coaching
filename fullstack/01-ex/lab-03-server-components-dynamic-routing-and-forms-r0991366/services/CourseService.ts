import { Course } from "@types";

const getCourseById = async (courseId: string): Promise<Course> => {
  const response = await fetch(
    `${process.env.NEXT_PUBLIC_API_URL}/courses/${courseId}`,
    {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },
      cache: "no-store",
    }
  );

  if (!response.ok) {
    throw new Error(`Failed to fetch course with id ${courseId}`);
  }
  return response.json();
};

const CourseService = {
  getCourseById,
};

export default CourseService;
