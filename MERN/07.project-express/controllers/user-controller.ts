import { NextFunction, Request, Response } from 'express';
import { v4 } from 'uuid';
import HttpError from '../models/http-error';

interface User {
  id: string;
  email: string;
  password: string;
  name: string;
  image?: string;
  places: number;
}

const USERS: User[] = [
  {
    id: 'u1',
    email: 'test@test.com',
    password: '123456',
    name: 'Andy Chen',
    image:
      'https://startuplatte.com/wp-content/uploads/2019/10/Andy-Puddicombe.jpg',
    places: 3,
  },
];

type unSensitiveUser = {
  id: string;
  name: string;
  image?: string;
  places: number;
};
function removePassword(user: User): unSensitiveUser {
  return {
    id: user.id,
    name: user.name,
    image: user.image,
    places: user.places,
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
  const newUser: User = {
    id: v4(),
    name,
    email,
    password,
    places: 0,
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
