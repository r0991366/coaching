import { User } from '@types';

const login = async (user: User): Promise<User> => {
  const response = await fetch(
    `${process.env.NEXT_PUBLIC_API_URL}/users/login`,
    {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(user),
      credentials: 'include',
    }
  );

  if (!response.ok) {
    const error = await response.json().catch(() => ({}));
    throw new Error(error?.message || `Authentication failed.`);
  }

  return response.json();
};

const logout = async (): Promise<void> => {
  const response = await fetch(
    `${process.env.NEXT_PUBLIC_API_URL}/users/logout`,
    {
      method: 'POST',
      credentials: 'include',
    }
  );

  if (!response.ok) {
    const error = await response.json().catch(() => ({}));
    throw new Error(error?.message || `Logout failed. Check server logs.`);
  }
};

const UserService = {
  login,
  logout,
};

export default UserService;
