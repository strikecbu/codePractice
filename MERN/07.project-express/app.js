const express = require("express");

const placeRouter = require("./routes/place-routes");

const app = express();

app.use("/api/places", placeRouter);

app.listen(5001);
