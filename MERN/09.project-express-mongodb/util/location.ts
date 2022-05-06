import axios from 'axios';
import HttpError from '../models/http-error';

const API_KEY = '';

export async function getCoordsLocation(address: string) {
  const response = await axios.get(
    `https://maps.googleapis.com/maps/api/geocode/json?address=${encodeURIComponent(
      address
    )}&key=${API_KEY}`
  );

  const data = response.data;
  if (!data || data.status === 'ZERO_RESULTS') {
    throw new HttpError('Not found location by provided address.', 404);
  }
  console.log(data);
  return data.results[0].geometry.location;
}
