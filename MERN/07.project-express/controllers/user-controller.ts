import { NextFunction, Request, Response } from 'express';
import { v4 } from 'uuid';
import HttpError from '../models/http-error';

interface User {
  id: string;
  email: string;
  password: string;
  name: string;
}

const USERS: User[] = [
  {
    id: 'u1',
    email: 'test@test.com',
    password: '123456',
    name: 'Andy Chen',
  },
];

type unSensitiveUser = {
  id: string;
  name: string;
};
function removePassword(user: User): unSensitiveUser {
  return {
    id: user.id,
    name: user.name,
  };
}

export function findAllUser(req: Request, res: Response, next: NextFunction) {
  const result: unSensitiveUser[] = [];
  USERS.forEach((user) => {
    const unSensitiveUser: unSensitiveUser = removePassword(user);
    result.push(unSensitiveUser);
  });
  res.status(200);
  res.json({ users: result });
}

export function signupUser(req: Request, res: Response, next: NextFunction) {
  const { name, email, password } = req.body;
  const hasUser = USERS.find((user) => user.email === email);
  if (hasUser) {
    throw new HttpError('Can not signup user, email already exist ', 422);
  }

  const newUser: User = {
    id: v4(),
    name,
    email,
    password,
  };
  USERS.push(newUser);
  res.status(201);
  const result: unSensitiveUser = removePassword(newUser);
  res.json(result);
}

export function login(req: Request, res: Response, next: NextFunction) {
  const { email, password } = req.body;
  const user = USERS.find(
    (user) => user.email === email && user.password === password
  );
  if (!user) {
    const error = new HttpError('User email or password is incorrect!', 400);
    return next(error);
  }
  res.status(200);
  const result: unSensitiveUser = removePassword(user);
  res.json(result);
}
