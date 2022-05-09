import { Request, Response, NextFunction } from 'express';
import { validationResult } from 'express-validator';
import mongoose, { Document } from 'mongoose';

import { Place } from '../models/Place';
import HttpError from '../models/http-error';
import { getCoordsLocation } from '../util/location';
import User from '../models/Users';

const findPlaceById = async (
  req: Request,
  res: Response,
  next: NextFunction
) => {
  const placeId = req.params['pid'];
  let place;
  try {
    place = await Place.findById(placeId);
  } catch (err) {
    console.log(err);
    return next(new HttpError('Something went wrong, try again later', 500));
  }
  if (!place) {
    return next(
      new HttpError('Could NOT found any place from provide pid!', 404)
    );
  }
  res.json({ place: place.toObject({ getters: true }) });
};

const findPlacesByUserId = async (
  req: Request,
  res: Response,
  next: NextFunction
) => {
  const userId = req.params['uid'];
  let places;
  try {
    places = await Place.find({ creator: userId });
  } catch (err) {
    console.log(err);
    return next(new HttpError('Something went wrong, try again later', 500));
  }
  if (!places || places.length === 0) {
    return next(
      new HttpError('Could NOT found any places from provide uid!', 404)
    );
  }
  res.json({
    places: places.map((place) =>
      place.toObject({ getters: false, virtuals: true })
    ),
  });
};

const createNewPlace = async (
  req: Request,
  res: Response,
  next: NextFunction
) => {
  const error = validationResult(req);
  if (!error.isEmpty()) {
    console.log(error);
    return next(
      new HttpError('Input validate fail, please check all inputs', 422)
    );
  }
  const { title, description, address, creator } = req.body;

  let coordinates;
  try {
    coordinates = await getCoordsLocation(address);
  } catch (error) {
    return next(error);
  }
  let user;
  try {
    user = await User.findById(creator);
  } catch (err) {
    return next(new HttpError('Found user fail, please try again later.', 500));
  }
  if (!user) {
    return next(new HttpError('Can not find user by provided id ', 404));
  }

  const newPlace: Document = new Place({
    title,
    description,
    address,
    location: coordinates,
    creator,
  });
  try {
    const session = await mongoose.startSession();
    session.startTransaction();
    await newPlace.save({ session });
    user.places.push(newPlace);
    await user.save({ session, validateModifiedOnly: true }); // 如果沒有validateModifiedOnly，save會失敗因為_id不為unique，這個參數能只驗證可變動屬性
    await session.commitTransaction();
  } catch (err) {
    console.log(err);
    const error = new HttpError('Fail to save data, please try again', 500);
    return next(error);
  }
  res.status(201);
  res.json(newPlace.toObject({ getters: true }));
};

export async function patchPlace(
  req: Request,
  res: Response,
  next: NextFunction
) {
  const error = validationResult(req);
  if (!error.isEmpty()) {
    console.log(error);
    return next(
      new HttpError('Patch input validate fail, please check all inputs', 422)
    );
  }
  const pid = req.params['pid'];
  const { title, description } = req.body; //兩個都一定有值

  let place;
  try {
    // 兩種做法 一個find到直接update，另一個取完後update再save
    // place = await Place.findById(pid).exec();
    place = await Place.findByIdAndUpdate(
      pid,
      { title, description },
      { returnDocument: 'after' }
    ).exec();
  } catch (err) {
    console.log(err);
    return next(new HttpError('Fail to patch place, try again', 500));
  }

  if (!place) {
    return next(
      new HttpError('Can not found the patch place by provided pid', 404)
    );
  }
  // 兩種做法
  // place.title = title;
  // place.description = description;
  // try {
  //   await place.save();
  // } catch (err) {
  //   console.log(err);
  //   return next(new HttpError('Fail to patch place, try again', 500));
  // }

  res.status(200);
  res.json(place.toObject({ getters: true }));
}

export async function deletePlace(
  req: Request,
  res: Response,
  next: NextFunction
) {
  const pid = req.params['pid'];
  let place;
  try {
    const session = await mongoose.startSession();
    session.startTransaction();
    place = await Place.findByIdAndDelete(pid, { session }).populate('creator'); // populate可以將具有ref的欄位取回ref的Model
    // place = await Place.findById(pid).populate('creator');
    if (!place) {
      return next(new HttpError('Fail to get place by provided id. ', 500));
    }

    // await place.remove({ session });
    const user: any = place.creator;
    user.places.pull(place);
    await user.save({ session, validateModifiedOnly: true });
    await session.commitTransaction();
    console.log(place);
  } catch (err) {
    console.log(err);
    return next(new HttpError('Something went wrong, try again later', 500));
  }
  res.status(200);
  res.json({ pid });
}

exports.findPlaceById = findPlaceById;
exports.findPlaceByUserId = findPlacesByUserId;
exports.createNewPlace = createNewPlace;
