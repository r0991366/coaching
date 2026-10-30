import { User } from '@types';

// Talks to the backend


// Example hardcoded users data
const hardcodedUsersData: User[] = [
  { username: 'user1', password: 'pass1' },
  { username: 'admin', password: 'admin' },
];

const login = async (name: string, password: string): Promise<User> => {
  const response = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/users/login`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    credentials: "include",    // !!!
    body: JSON.stringify({
      username: name,
      password: password,
    }),
  });

  if (!response.ok) {
    const error = await response.json().catch(() => ({}));
    throw new Error(error?.message || "Login failed");
  }

  // Backend returns a user object WITHOUT the token
  return response.json();
};

const logout = async (): Promise<void> => {
  //not calling to the backend, handling everything here (yk clearing sessionStorage and all that)
};


//optional now? all this logic is in login now
// const authenticate = async (user: User): Promise<User> => {
//   const response = await fetch(
//     `${process.env.NEXT_PUBLIC_API_URL}/users/login`,
//     {
//       method: 'POST',
//       headers: {
//         'Content-Type': 'application/json',
//       },
//       body: JSON.stringify(user),
//       credentials: 'include',
//     }
//   );
//   if (!response.ok) {
//     const error = await response.json().catch(() => ({}));
//     throw new Error(error?.message || 'Authentication failed.');
//   }
//   return response.json();
// }

const UserService = {
  login,
  logout,
  //authenticate,
};

export default UserService;
