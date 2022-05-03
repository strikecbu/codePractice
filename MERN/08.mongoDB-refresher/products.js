const MongoClient = require("mongodb").MongoClient;

const url =
  "mongodb+srv://andy:<password>@cluster0.t6wct.mongodb.net/products_test?retryWrites=true&w=majority";

const createProducts = async (req, res, next) => {
  const client = new MongoClient(url);
  const newProduct = {
    name: req.body.name,
    price: req.body.price,
  };
  try {
    await client.connect();
    const db = client.db();
    const result = await db.collection("products").insertOne(newProduct);
    console.log(result);
  } catch (error) {
    return res.json("Could not reach database");
  }
  res.json(newProduct);
};
const getProducts = async (req, res, next) => {
  const client = new MongoClient(url);
  let data;
  try {
    await client.connect();
    const db = client.db();
    data = await db.collection("products").find().toArray();
  } catch (error) {
    return res.json("Could not retrieve data");
  }
  console.log(data);
  res.json(data);
};

module.exports.createProducts = createProducts;
module.exports.getProducts = getProducts;
