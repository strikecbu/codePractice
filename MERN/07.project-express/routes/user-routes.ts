import { Router } from 'express';
import { findAllUser, signupUser, login } from '../controllers/user-controller';

const routes = Router();

routes.get('/', findAllUser);

routes.post('/signup', signupUser);

routes.post('/login', login);

export default routes;
