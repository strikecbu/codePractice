const express = require("express");
const bodyParser = require("body-parser");
const productHandler = require("./products");

const app = express();

app.use(bodyParser.json())

app.post("/products", productHandler.createProducts);

app.get("/products", productHandler.getProducts);

app.listen(3000);

