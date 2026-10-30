'use client';

import UserService from '@services/UserService';
import { StatusMessage, User } from '@types';
import classNames from 'classnames';
import { useRouter } from 'next/navigation';
import React, { useState } from 'react';
import { useAuth } from '@context/AuthContext';


const UserLoginForm: React.FC = () => {
  const router = useRouter();
  const [name, setName] = useState('');
  const [password, setPassword] = useState('');
  const [nameError, setNameError] = useState<string | null>(null);
  const [passwordError, setPasswordError] = useState<string | null>(null);
  const [statusMessages, setStatusMessages] = useState<StatusMessage[]>([]);
  

  const clearErrors = () => {
    setNameError(null);
    setPasswordError(null);
    setStatusMessages([]);
  };

  const validate = (): boolean => {
    let result = true;

    if (!name || name.trim() === '') {
      setNameError('Username is required');
      result = false;
    }

    if (!password || password.trim() === '') {
      setPasswordError('Password is required');
      result = false;
    }

    return result;
  };

  const { login } = useAuth();

  const handleSubmit = async (event: { preventDefault: () => void }) => {
    event.preventDefault();
    clearErrors();

    if (!validate()) {
      return;
    }

    const user = { username: name, password };

    try {
      // const loggedInUser = await UserService.login(
      //   user.username,
      //   user.password
      // );
      const loggedInUser = await UserService.login(name, password); //api call
      login(loggedInUser); // sets state + sessionStorage (AuthContext)

      setStatusMessages([
        {
          message: 'Login successful. Redirecting to homepage...',
          type: 'success',
        },
      ]);

      sessionStorage.setItem('loggedInUser', JSON.stringify(loggedInUser));

      setTimeout(() => {
        router.push('/');
      }, 1000);
    } catch (error) {
      setStatusMessages([{ message: (error as Error).message, type: 'error' }]);
    }
  };

  return (
    <div className="max-w-sm m-auto">
      {statusMessages && (
        <div className="row">
          <ul className="list-none mb-3 mx-auto ">
            {statusMessages.map(({ message, type }, index) => (
              <li
                key={index}
                className={classNames({
                  ' text-red-800': type === 'error',
                  'text-green-800': type === 'success',
                })}>
                {message}
              </li>
            ))}
          </ul>
        </div>
      )}
      <form onSubmit={handleSubmit}>
        <div>
          <div>
            <label
              htmlFor="nameInput"
              className="block mb-2 text-sm font-medium">
              Username:
            </label>
          </div>
          <div className="block mb-2 text-sm font-medium">
            <input
              id="nameInput"
              type="text"
              value={name}
              onChange={(event) => setName(event.target.value)}
              className="border border-gray-300 text-sm rounded-lg focus:ring-blue-500 focus:border-blue:500 block w-full p-2.5"
            />
            {nameError && <div className="text-red-800 ">{nameError}</div>}
          </div>
        </div>
        <div className="mt-2">
          <div>
            <label
              htmlFor="passwordInput"
              className="block mb-2 text-sm font-medium">
              Password:
            </label>
          </div>
          <div className="block mb-2 text-sm font-medium">
            <input
              id="passwordInput"
              type="password"
              value={password}
              onChange={(event) => setPassword(event.target.value)}
              className="border border-gray-300 text-sm rounded-lg focus:ring-blue-500 focus:border-blue:500 block w-full p-2.5"
            />
            {passwordError && (
              <div className=" text-red-800">{passwordError}</div>
            )}
          </div>
        </div>
        <div className="row">
          <button
            className="text-white bg-blue-700 hover:bg-blue-800 font-medium rounded-lg text-sm px-5 py-2.5 text-center"
            type="submit">
            Login
          </button>
        </div>
      </form>
    </div>
  );
};

export default UserLoginForm;

