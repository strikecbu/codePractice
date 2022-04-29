import { Request, Response, NextFunction } from "express";

const uuid = require("uuid");
const HttpError = require("../models/http-error");

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
const DUMMY_PLACES: Place[] = [
  {
    id: "p1",
    title: "Empire State Building",
    description: "One of the most famous sky scrapers in the world!",
    imageUrl:
      "https://upload.wikimedia.org/wikipedia/commons/thumb/d/df/NYC_Empire_State_Building.jpg/640px-NYC_Empire_State_Building.jpg",
    address: "20 W 34th St, New York, NY 10001",
    location: {
      lat: 40.7484405,
      lng: -73.9878584,
    },
    creator: "u1",
  },
  {
    id: "p2",
    title: "Empire State Building",
    description: "One of the most famous sky scrapers in the world!",
    imageUrl:
      "https://upload.wikimedia.org/wikipedia/commons/thumb/d/df/NYC_Empire_State_Building.jpg/640px-NYC_Empire_State_Building.jpg",
    address: "20 W 34th St, New York, NY 10001",
    location: {
      lat: 40.7484405,
      lng: -73.9878584,
    },
    creator: "u2",
  },
];

const findPlaceById = (req: Request, res: Response, next: NextFunction) => {
  const placeId = req.params["pid"];
  const place = findPlace((place) => place.id === placeId);
  if (!place) {
    return next(
      new HttpError("Could NOT found any place from provide pid!", 404)
    );
  }
  res.json({ place });
};

const findPlaceByUserId = (req: Request, res: Response, next: NextFunction) => {
  const userId = req.params["uid"];
  const place = findPlace((place) => place.creator === userId);
  if (!place) {
    return next(
      new HttpError("Could NOT found any place from provide uid!", 404)
    );
  }
  res.json({ place });
};

const createNewPlace = (req: Request, res: Response, next: NextFunction) => {
  const { title, description, address, location, creator } = req.body;
  const newPlace: Place = {
    id: uuid.v4(),
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

const findPlace = (predicate: (place: Place) => boolean) => {
  return DUMMY_PLACES.find(predicate);
};

exports.findPlaceById = findPlaceById;
exports.findPlaceByUserId = findPlaceByUserId;
exports.createNewPlace = createNewPlace;
