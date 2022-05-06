import { Request, Response, NextFunction } from 'express';
import { validationResult } from 'express-validator';
import { Document } from 'mongoose';

import { Place } from '../models/Place';
import HttpError from '../models/http-error';
import { getCoordsLocation } from '../util/location';

let DUMMY_PLACES = [
  {
    id: 'p1',
    title: 'Empire State Building',
    description: 'One of the most famous sky scrapers in the world!',
    imageUrl:
      'https://upload.wikimedia.org/wikipedia/commons/thumb/d/df/NYC_Empire_State_Building.jpg/640px-NYC_Empire_State_Building.jpg',
    address: '20 W 34th St, New York, NY 10001',
    location: {
      lat: 40.7484405,
      lng: -73.9878584,
    },
    creator: 'u1',
  },
  {
    id: 'p2',
    title: 'Empire State Building',
    description: 'One of the most famous sky scrapers in the world!',
    imageUrl:
      'https://upload.wikimedia.org/wikipedia/commons/thumb/d/df/NYC_Empire_State_Building.jpg/640px-NYC_Empire_State_Building.jpg',
    address: '20 W 34th St, New York, NY 10001',
    location: {
      lat: 40.7484405,
      lng: -73.9878584,
    },
    creator: 'u2',
  },
];

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

  const newPlace: Document = new Place({
    title,
    description,
    address,
    location: coordinates,
    creator,
  });
  try {
    await newPlace.save();
  } catch (err) {
    console.log(err);
    const error = new HttpError('Fail to save data, please try again', 500);
    return next(error);
  }
  res.status(201);
  res.json(newPlace);
};

export function patchPlace(req: Request, res: Response, next: NextFunction) {
  const error = validationResult(req);
  if (!error.isEmpty()) {
    console.log(error);
    throw new HttpError(
      'Patch input validate fail, please check all inputs',
      422
    );
  }
  const pid = req.params['pid'];
  const place = findPlace((place) => place.id === pid);
  if (!place) {
    return next(new HttpError('Can not found any place by provided pid', 404));
  }
  const { title = place.title, description = place.description } = req.body;

  const newPlace = {
    ...place,
    title: title,
    description: description,
  };
  DUMMY_PLACES = [
    ...DUMMY_PLACES.filter((place) => place.id !== pid),
    newPlace,
  ];
  res.status(200);
  res.json(newPlace);
}

export function deletePlace(req: Request, res: Response, next: NextFunction) {
  const pid = req.params['pid'];
  const place = findPlace((place) => place.id === pid);
  if (!place) {
    return next(new HttpError('Can not found any place by provided pid', 404));
  }
  DUMMY_PLACES = DUMMY_PLACES.filter((place) => place.id !== pid);
  res.status(200);
  res.json({ pid });
}

const findPlace = (predicate: (place: any) => boolean) => {
  return DUMMY_PLACES.find(predicate);
};

exports.findPlaceById = findPlaceById;
exports.findPlaceByUserId = findPlacesByUserId;
exports.createNewPlace = createNewPlace;
