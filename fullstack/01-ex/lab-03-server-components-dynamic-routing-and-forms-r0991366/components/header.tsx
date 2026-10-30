"use client"
import { User } from '@types';
import Link from 'next/link';
import { useEffect, useState } from 'react';

const Header: React.FC = () => {
  const [loggedInUser, setLoggedInUser] = useState<User>();

  useEffect(() => {
    const storedUser = sessionStorage.getItem("user");
    if (storedUser) {
      setLoggedInUser(JSON.parse(storedUser));
    }
  }, []);

  const handleLogout = () => {
    sessionStorage.removeItem("user");
    setLoggedInUser(undefined);
  };

  return (
    <header className="p-3 mb-3 border-bottom bg-gradient-to-br from-gray-900 to-gray-600 flex flex-col items-center">
      <a className="flex  mb-2 md:mb-5 text-white-50 text-3xl text-gray-300">
        Courses App
      </a>
      <nav className="items-center flex md:flex-row flex-col">
        <Link
          href="/"
          className=" px-4 text-xl text-white  hover:bg-gray-600 rounded-lg">
          Home
        </Link>
        <Link
          href="/lecturers"
          className="px-4  text-white text-xl hover:bg-gray-600 rounded-lg">
        Lecturers
        </Link>

        {!loggedInUser ? (
        <Link
          href="/login"
          className="px-4 text-white text-xl hover:bg-gray-600 rounded-lg">
          Login
        </Link>
        ) : (
        <Link
          href="/"
          onClick={handleLogout}
          className="px-4 text-white text-xl hover:bg-gray-600 rounded-lg">
          Logout
        </Link>
      )}
      <p className="px-4  text-white text-l rounded-lg">
        {loggedInUser ? `Welcome ${loggedInUser?.username}!`:""}
      </p>
      </nav>
    </header>
  );
};

export default Header;
