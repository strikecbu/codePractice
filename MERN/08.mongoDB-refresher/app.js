const express = require("express");
const bodyParser = require("body-parser");
const productHandler = require("./products");
const mongooseProdHandler = require("./mongoose");

const app = express();

app.use(bodyParser.json());

app.post("/products", mongooseProdHandler.createProducts);

app.get("/products", mongooseProdHandler.getProducts);
app.get("/products/:name", mongooseProdHandler.findProductsByName);

app.listen(3000);
