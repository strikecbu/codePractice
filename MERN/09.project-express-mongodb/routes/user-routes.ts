import { Router } from 'express';
import { findAllUser, signupUser, login } from '../controllers/user-controller';
const { check } = require('express-validator');

const routes = Router();

routes.get('/', findAllUser);

routes.post(
  '/signup',
  [
    check('name').not().isEmpty(),
    check('email').isEmail(),
    check('password').isLength({ min: 6 }),
  ],
  signupUser
);

routes.post(
  '/login',
  [check('email').isEmail(), check('password').not().isEmpty()],
  login
);

export default routes;
