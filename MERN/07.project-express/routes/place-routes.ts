const express = require('express');

const placesController = require('../controllers/place-controller');
const router = express.Router();

router.get('/:pid', placesController.findPlaceById);

router.get('/user/:uid', placesController.findPlaceByUserId);

router.post('/', placesController.createNewPlace);

router.patch('/:pid', placesController.patchPlace);
router.delete('/:pid', placesController.deletePlace);

module.exports = router;
