const productModel = require("./models/Products");
const mongoose = require("mongoose");
const { model: Product } = productModel;
const url =
  "mongodb+srv://andy:<password>>@cluster0.t6wct.mongodb.net/products_test?retryWrites=true&w=majority";

mongoose
  .connect(url)
  .then((result) => console.log)
  .catch((error) => console.log);

const createProducts = async (req, res, next) => {
  const product = new Product({
    name: req.body.name,
    price: req.body.price,
  });
  const result = await product.save();
  res.json(result);
};

const getProducts = async (req, res, next) => {
  const products = await Product.find().exec();
  console.log(products);
  res.json(products);
};

const findProductsByName = async (req, res, next) => {
  const name = req.params.name;
  console.log(name);
  const result = await Product.find({ name }).exec();
  res.json(result);
};

module.exports.createProducts = createProducts;
module.exports.getProducts = getProducts;
module.exports.findProductsByName = findProductsByName;
