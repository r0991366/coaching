import { User } from "@types";

// Example hardcoded users data
const hardcodedUsersData: User[] = [
  { username: "user1", password: "pass1" },
  { username: "admin", password: "admin" },
];

// Simulate an API call
const login = async (name: string, password: string): Promise<User | null> => {
  await new Promise((resolve) => setTimeout(resolve, 500)); // Simulate network delay

  const user = hardcodedUsersData.find(
    (u) => u.username === name && u.password === password
  );
  if (!user) {
    throw new Error("Invalid username or password");
  }
  return user;
};

const UserService = {
  login,
};

export default UserService;
