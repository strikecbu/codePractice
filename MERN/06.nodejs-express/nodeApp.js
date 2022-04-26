const http = require("http");

const server = http.createServer((req, resp) => {
  console.log(req.method);
  if (req.method === "POST") {
    console.log("post: " + req.method);
    let body = "";
    req.on("end", () => {
      resp.end("Got your data: " + body);
      // console.log(body);
    });
    req.on("data", (chunk) => {
      body += chunk;
      console.log(chunk + "");
    });
    console.log(body);
  } else {
    resp.setHeader("content-type", "text/html");
    resp.end(
      '<form action="" method="POST">Name:<input name="name" type="text"/>Age<input type="text" name="age"/><button type="submit">CreateUser</button></form>'
    );
  }
});

server.listen(3000);
