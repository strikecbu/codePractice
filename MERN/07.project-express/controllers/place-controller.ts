import { Request, Response, NextFunction } from 'express';
import { v4 } from 'uuid';

import HttpError from '../models/http-error';

type Place = {
  id: string;
  title: string;
  description: string;
  imageUrl?: string;
  address: string;
  location: {
    lat: number;
    lng: number;
  };
  creator: string;
};
let DUMMY_PLACES: Place[] = [
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

const findPlaceById = (req: Request, res: Response, next: NextFunction) => {
  const placeId = req.params['pid'];
  const place = findPlace((place) => place.id === placeId);
  if (!place) {
    return next(
      new HttpError('Could NOT found any place from provide pid!', 404)
    );
  }
  res.json({ place });
};

const findPlacesByUserId = (
  req: Request,
  res: Response,
  next: NextFunction
) => {
  const userId = req.params['uid'];
  const places: Place[] = DUMMY_PLACES.filter(
    (place) => place.creator === userId
  );
  if (!places || places.length === 0) {
    return next(
      new HttpError('Could NOT found any places from provide uid!', 404)
    );
  }
  res.json(places);
};

const createNewPlace = (req: Request, res: Response) => {
  const { title, description, address, location, creator } = req.body;
  const newPlace: Place = {
    id: v4(),
    title,
    description,
    address,
    location,
    creator,
  };
  DUMMY_PLACES.push(newPlace);
  res.status(201);
  res.json(newPlace);
};

export function patchPlace(req: Request, res: Response, next: NextFunction) {
  const { title, description } = req.body;
  const pid = req.params['pid'];
  const place = findPlace((place) => place.id === pid);
  if (!place) {
    return next(new HttpError('Can not found any place by provided pid', 404));
  }
  const newPlace: Place = {
    ...place,
    title: title ?? place.title,
    description: description ?? place.description,
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

const findPlace = (predicate: (place: Place) => boolean) => {
  return DUMMY_PLACES.find(predicate);
};

exports.findPlaceById = findPlaceById;
exports.findPlaceByUserId = findPlacesByUserId;
exports.createNewPlace = createNewPlace;
