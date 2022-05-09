import mongoose, { Schema, model } from 'mongoose';
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
  creator: object;
};
const productSchema = new Schema<IPlace>({
  title: { type: String, required: true },
  description: { type: String, required: true },
  address: { type: String, required: true },
  location: {
    lat: { type: Number, required: true },
    lng: { type: Number, required: true },
  },
  creator: { type: mongoose.Types.ObjectId, required: true, ref: 'User' },
});

export const Place = model<IPlace>('Place', productSchema);
