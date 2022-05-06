import express, { Express, NextFunction, Request, Response } from 'express';
import bodyParser from 'body-parser';
import HttpError from './models/http-error';
import mongoose from 'mongoose';

const placeRouter = require('./routes/place-routes');
import userRouter from './routes/user-routes';

const app: Express = express();
const port = 5001;

app.use(bodyParser.json());

app.use('/api/places', placeRouter);

app.use('/api/users', userRouter);

app.use((req: Request, res: Response, next: NextFunction) => {
  return next(new HttpError('Could not found this route!', 404));
});

app.use((error: HttpError, req: Request, res: Response, next: NextFunction) => {
  if (res.headersSent) {
    return next(error);
  }
  res.status(error.code || 500);
  res.json({ message: error.message || 'An unknown error occurred!' });
});

mongoose
  .connect(
    'mongodb+srv://andy:<password>@cluster0.t6wct.mongodb.net/places?retryWrites=true&w=majority'
  )
  .then(() => {
    app.listen(port, () => {
      console.log(`⚡️[server]: Server is running at http://localhost:${port}`);
    });
  })
  .catch((err) => console.log(`fail: ${err}`));
