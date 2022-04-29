const express = require("express");

const placesController = require("../controllers/place-controller");
const router = express.Router();

router.get("/:pid", placesController.findPlaceById);

router.get("/user/:uid", placesController.findPlaceByUserId);

module.exports = router;
