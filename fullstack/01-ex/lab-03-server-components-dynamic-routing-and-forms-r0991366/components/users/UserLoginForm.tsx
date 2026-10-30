"use client";

import UserService from "@services/UserService";
import { StatusMessage } from "@types"
import classNames from "classnames";
import { useRouter } from "next/navigation";
import React, { useState } from "react";

const UserLoginForm: React.FC = () => {
  const [name, setName] = useState("");
  const [password, setPassword] = useState("");
  const [nameError, setNameError] = useState<string | null>(null);
  const [passwordError, setPasswordError] = useState<string | null>(null);
  const [submitted, setSubmitted] = useState(false);
  const [statusMessages, setStatusMessages] = useState<StatusMessage[]>([]);
  const router = useRouter();

  const clearErrors = () => {
    setNameError("");
    setPasswordError("");
    setStatusMessages([])
  }

  const validate = (): boolean => {
    let valid = true;

    if (!name && name.trim() === "") {
      setNameError("Username is required");
      valid = false;
    } else {setNameError(null)}

    if (!password && password.trim() === "") {
      setPasswordError("Password is required");
      valid = false;
    }else {setNameError(null)}

    return valid;
  };

  const handleSubmit = async (event: { preventDefault: () => void }) => {
    clearErrors();
    event.preventDefault();
    setSubmitted(true);

    if (!validate()) {
      return;
    }

    const user = { username: name, password };
    console.log("Logging in user: \n", user)

    try {
      const loggedInUser = await UserService.login(user.username, user.password);
      setStatusMessages([{message: "Login successful. Redirecting to homepage...", type: "success"}]);
      sessionStorage.setItem("user", JSON.stringify(loggedInUser));0
      setTimeout(() => {
        router.push("/");
      }, 1000);

      setTimeout(() => {
        window.location.reload(); //reloading page to show stuff (couldnt put it in the same thingy)
      }, 1001);
      

    } catch (error) {
      setStatusMessages([{message: (error as Error).message, type: "error"}])
    }
  };

  return (
    <div className="max-w-sm m-auto">
      {statusMessages && (
        <div className="row">
          <ul className="list-none mb-3 mx-auto">
            {statusMessages.map(({message, type}, index) => (
              <li
              key={index}
              className={classNames({
                'text-red-800': type === 'error',
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
              className="block mb-2 text-sm font-medium"
            >
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
          </div>
          <div className="text-red-600 text-sm">
            <p>{submitted && nameError} </p>
          </div>
        </div>
        <div className="mt-2">
          <div>
            <label
              htmlFor="passwordInput"
              className="block mb-2 text-sm font-medium"
            >
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
          </div>
          <div className="text-red-600 text-sm">
            <p>{submitted && passwordError}</p> 
          </div>
        </div>
        <div className="row">
          <button
            className="text-white bg-blue-700 hover:bg-blue-800 font-medium rounded-lg text-sm px-5 py-2.5 text-center"
            type="submit"
          >
            Login
          </button>
        </div>
      </form>
    </div>
  );
};

export default UserLoginForm;
