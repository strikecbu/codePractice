import { Schema, model } from 'mongoose';
export type IPlace = {
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
const productSchema = new Schema<IPlace>({
  title: { type: String, required: true },
  description: { type: String, required: true },
  address: { type: String, required: true },
  location: {
    lat: { type: Number, required: true },
    lng: { type: Number, required: true },
  },
  creator: { type: String, required: true },
});

export const Place = model<IPlace>('Place', productSchema);
