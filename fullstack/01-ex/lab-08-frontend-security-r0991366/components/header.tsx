'use client';

import Link from 'next/link';
import { useRouter } from 'next/navigation';
import { AuthContext } from '@context/AuthContext';
import { useContext } from 'react';


const Header: React.FC = () => {
  const router = useRouter();

  const { user, logout } = useContext(AuthContext)!;

  const handleLogout = async (event: React.MouseEvent<HTMLAnchorElement>) => {
    event.preventDefault();
    logout?.();
    router.push('/');

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

        {user ? (
          <>
            <a
              href="#"
              onClick={handleLogout}
              className="px-4 text-white text-xl hover:bg-gray-600 rounded-lg"
            >
              Logout
            </a>

            <div className="text-white ms-5 mt-2 md:mt-0 pt-1 md:pt-0 grow">
              Welcome, {user.username}!
            </div>
          </>
        ) : (
          <Link
            href="/login"
            className="px-4 text-white text-xl hover:bg-gray-600 rounded-lg"
          >
            Login
          </Link>
        )}
      </nav>
    </header>
  );
};

export default Header;
