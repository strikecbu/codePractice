const express = require("express");

const router = express.Router();

const DUMMY_PLACES = [
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

router.get("/:pid", (req, res, next) => {
  const placeId = req.params["pid"];
  const place = DUMMY_PLACES.find((place) => place.id === placeId);
  if (!place) {
    const error = new Error("Could NOT found any place from provide pid!");
    error.code = 404;
    return next(error);
  }
  res.json({ place });
});

router.get("/user/:uid", (req, res, next) => {
  const userId = req.params["uid"];
  const place = DUMMY_PLACES.find((place) => place.creator === userId);
  if (!place) {
    const error = new Error("Could NOT found any place from provide uid!");
    error.code = 404;
    return next(error);
  }
  res.json({ place });
});

module.exports = router;
