'use client';

// Handles stuff like sessionsSTorage

import React, {
  createContext,
  useContext,
  useState,
  useEffect,
  ReactNode,
} from 'react';
import { User } from '@types';
import UserService from '@services/UserService';

type AuthContextType = {
  user: User | null;
  login: (userData: User) => void;
  logout: () => void;
};

export const AuthContext = createContext<AuthContextType | undefined>(
  undefined
);

export const AuthProvider = ({ children }: { children: ReactNode }) => {
  const [user, setUser] = useState<User | null>(null);

  // restore user
  useEffect(() => {
    try {
      const stored = sessionStorage.getItem('loggedInUser');
      if (stored) {
        const parsed: User = JSON.parse(stored);
        setUser(parsed);
      }
    } catch (e) {
      console.error('Failed to restore user from sessionStorage', e);
    }
  }, []);

  // login: set state + persist to sessionStorage
  const login = (userData: User) => {
    setUser(userData);
    sessionStorage.setItem('loggedInUser', JSON.stringify(userData));
  };


  // imma do logout anyway :p
  const logout = async () => {
    setUser(null);
    sessionStorage.removeItem("loggedInUser");
  };

  return (
    <AuthContext.Provider value={{ user, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const ctx = useContext(AuthContext);
  if (!ctx) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return ctx;
};
