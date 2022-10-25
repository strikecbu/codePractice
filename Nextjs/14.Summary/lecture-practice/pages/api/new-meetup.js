import { MongoClient } from 'mongodb';
import environment from '../../env/environment';

async function handle(req, res) {
    if (req.method === 'POST') {
        const request = req.body;
        const username = environment.mongodb.username;
        const password = environment.mongodb.password;
        const url = `mongodb+srv://${username}:${password}@cluster0.t6wct.mongodb.net/?retryWrites=true&w=majority`;
        const client = await MongoClient.connect(url);

        const db = client.db('next-meetups');
        const collection = db.collection('meetups');

        const result = await collection.insertOne(request);
        console.log(result);
        client.close();
        res.status(201).json({ message: 'insert done!' });
    }
}

export default handle;
