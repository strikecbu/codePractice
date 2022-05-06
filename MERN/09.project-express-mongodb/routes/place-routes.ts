const express = require('express');
const { check } = require('express-validator');

const placesController = require('../controllers/place-controller');
const router = express.Router();

router.get('/:pid', placesController.findPlaceById);

router.get('/user/:uid', placesController.findPlaceByUserId);

router.post(
  '/',
  [
    check('title').not().isEmpty(),
    check('description').isLength({ min: 6 }),
    check('address').not().isEmpty(),
  ],
  placesController.createNewPlace
);

router.patch(
  '/:pid',
  [check('title').not().isEmpty(), check('description').isLength({ min: 6 })],
  placesController.patchPlace
);
router.delete('/:pid', placesController.deletePlace);

module.exports = router;
