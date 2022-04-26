const express = require("express");
const parser = require("body-parser");

const app = express();

app.use(parser.urlencoded({ extended: false }));

app.post("/hello", (req, res, next) => {
  res.send(`<h2>Hello ${req.body.name}, your age is ${req.body.age}</h2>`);
});

app.get("/go", (req, resp, next) => {
  resp.send(
    '<form action="/hello" method="POST">Name:<input name="name" type="text"/>Age<input type="text" name="age"/><button type="submit">CreateUser</button></form>'
  );
});

app.use((req, resp, next) => {
  resp.send("<h2>Oh</h2>");
});

app.listen(3000);
