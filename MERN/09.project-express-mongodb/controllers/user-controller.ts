import { NextFunction, Request, Response } from 'express';
import { validationResult } from 'express-validator';
import User from '../models/Users';
import HttpError from '../models/http-error';

export async function findAllUser(
  req: Request,
  res: Response,
  next: NextFunction
) {
  let users;
  try {
    users = await User.find({}, '-password');
  } catch (err) {
    return next(new HttpError('Signup fail, please try again later.', 500));
  }

  res.status(200);
  res.json({
    users: users.map((user) => {
      return user.toObject({ getters: true });
    }),
  });
}

export async function signupUser(
  req: Request,
  res: Response,
  next: NextFunction
) {
  const error = validationResult(req);
  if (!error.isEmpty()) {
    console.log(error);
    return next(
      new HttpError('Input validate fail, please check all inputs', 422)
    );
  }
  const { name, email, password } = req.body;

  let hasUser;
  try {
    hasUser = await User.findOne({ email });
  } catch (err) {
    return next(new HttpError('Signup fail, please try again later.', 500));
  }
  if (hasUser) {
    return next(
      new HttpError('Can not signup user, email already exist ', 422)
    );
  }

  const createdUser = new User({
    email,
    name,
    password,
    image:
      'https://scuffedentertainment.com/wp-content/uploads/2021/08/how-cool-are-you-quiz.jpg',
    places: [],
  });
  try {
    await createdUser.save();
  } catch (err) {
    console.log(err);
    const error = new HttpError('Fail to create user, please try again', 500);
    return next(error);
  }

  res.status(201);
  res.json(createdUser.toObject({ getters: true }));
}

export async function login(req: Request, res: Response, next: NextFunction) {
  const error = validationResult(req);
  if (!error.isEmpty()) {
    console.log(error);
    return next(
      new HttpError('Input validate fail, please check all inputs', 422)
    );
  }
  const { email, password } = req.body;
  let user;
  try {
    user = await User.findOne({ email, password });
  } catch (err) {
    return next(new HttpError('login fail, please try again later.', 500));
  }
  if (!user) {
    const error = new HttpError('User email or password is incorrect!', 400);
    return next(error);
  }
  res.status(200);
  res.json(user.toObject({ getters: true }));
}
